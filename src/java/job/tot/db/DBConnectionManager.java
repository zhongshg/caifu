/*
 * DBConnectionManager.java
 *
 * Created on 2006��7��18��, ����2:05
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.db;
import java.sql.*;
import java.util.Enumeration;
import java.util.Vector;
import job.tot.global.Sysconfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is a Singleton that provides access to the
 * connection pool. A client gets access to the single
 * instance through the static getInstance() method
 * and can then check-out and check-in connections from a pool.
 * When the client shuts down it should call the release() method
 * to close all opened connections and do other clean up.
 */
class DBConnectionManager {

    private static Log log = LogFactory.getLog(DBConnectionManager.class);
    private static final int TIME_BETWEEN_RETRIES = 500; // O.5 second
    // static variable
    static private DBConnectionManager instance = null;       // The single instance
    // instance variable
    private DBConnectionPool pool = null;// please be careful if u want to make this variable static
    /**
     * A private constructor since this is a Singleton
     * Note: This constructor is lightweight since DBConnectionPool is lightweight,
     *       so no connection is created until the first time getConnection() is called
     */
    private DBConnectionManager(DBOptions option) {
        try {
            Class.forName(option.driverClassName).newInstance();
        } catch (Exception e) {
            log.fatal("DBConnectionManager: Unable to load driver = " + option.driverClassName);
        }
        //always new the pool because pool is an instance variable
        pool = new DBConnectionPool(option.databaseURL, option.databaseUser, option.databasePassword, option.maxConnection);
    }
    private DBConnectionManager() {
        String driverClassName = Sysconfig.getDriverClassName();
        try {
            Class.forName(driverClassName).newInstance();
        } catch (Exception e) {
            log.fatal("DBConnectionManager: Unable to load driver = " + driverClassName);
        }
        String url = Sysconfig.getDatabaseURL();
        String user = Sysconfig.getDatabaseUser();
        String password = Sysconfig.getDatabasePassword();
        int maxConnection = Sysconfig.getMaxConnection();
        //always new the pool because pool is an instance variable
        pool = new DBConnectionPool(url, user, password, maxConnection);
    }
    /**
     * Returns the single instance, creating one if it's the
     * first time this method is called.
     *
     * @return DBConnectionManager The single instance.
     */
    /*
    public static synchronized DBConnectionManager getInstance() {
        if (instance == null) {
            DBOptions option = new DBOptions();
            instance = new DBConnectionManager(option);
        }
        return instance;
    }*/

    /**
     * Returns the single instance, creating one if it's the
     * first time this method is called.
     *
     * @return DBConnectionManager The single instance.
     */
    /*
    private static synchronized DBConnectionManager getInstance(DBOptions option) {
        if (instance == null) {
            if (option == null) {
                option = new DBOptions();
            }
            instance = new DBConnectionManager(option);
        }
        return instance;
    }*/
    public static synchronized DBConnectionManager getInstance(boolean useConfig) {
        if (instance == null) {
            instance = new DBConnectionManager();
        }
        return instance;
    }
    /**
     * Returns a connection to the pool.
     *
     * @param con The Connection
     */
    void freeConnection(Connection con) {
        pool.freeConnection(con);
    }

    /**
     * Returns an open connection. If no one is available, and the max
     * number of connections has not been reached, a new connection is
     * created.
     *
     * @return Connection The connection or null
     */
    Connection getConnection() {
        return pool.getConnection();
    }

    /**
     * Returns an open connection. If no one is available, and the max
     * number of connections has not been reached, a new connection is
     * created. If the max number has been reached, waits until one
     * is available or the specified time has elapsed.
     *
     * @param time The number of milliseconds to wait
     * @return Connection The connection or null
     */
    Connection getConnection(long time) {
        return pool.getConnection(time);
    }

    /**
     * Closes all open connections.
     * @return true if the pool is empty and balance
     *         false if the pool has returned some connection to outside
     */
    boolean release() {
        return pool.release();
    }

    /**
     * This inner class represents a connection pool. It creates new
     * connections on demand, up to a max number if specified.
     * It also checks to make sure that the connection is still open
     * before it is returned to a client.
     */
    class DBConnectionPool {
        private int checkedOut  = 0;//NOTE: this variable should be changed in synchronized method only
        private Vector freeConnections = new Vector();

        private int    maxConn  = 0;
        private String password = null;
        private String URL      = null;
        private String user     = null;

        /**
         * Creates new connection pool.
         * NOTE: new an instance of this class is lightweight since it does not create any connections
         *
         * @param URL The JDBC URL for the database
         * @param user The database user, or null
         * @param password The database user password, or null
         * @param maxConn The maximal number of connections, or 0 for no limit
         */
        public DBConnectionPool(String URL, String user, String password, int maxConn) {
            this.URL = URL;
            this.user = user;
            this.password = password;
            this.maxConn = maxConn;
        }

        /**
         * Checks in a connection to the pool. Notify other Threads that
         * may be waiting for a connection.
         *
         * @todo: Maybe we dont need notifyAll(); ???
         *
         * @param con The connection to check in
         */
        synchronized void freeConnection(Connection con) {
            // Put the connection at the end of the Vector
            if (con != null) {//make sure that the connection is not null
                if (checkedOut <= 0) {
                    // this means that connection is open too much
                    // There are 2 cases:
                    // 1. Not get from this connection pool (maybe get directly)
                    // 2. this connection is gotten and then the whole pool is released
                    // In these case, just close the connection
                    try {
                        log.debug("DBConnectionManager: about to close the orphan connection.");
                        con.close();
                    } catch (SQLException ex) { }
                } else {
                    // Return this connection to the pool
                    // note that we dont have to check if the connection is not connected
                    // this will be check in the getConnection method
                    freeConnections.addElement(con);
                    // FIXME: posible negative value
                    // NOTE: checkOut should never be negative here
                    checkedOut--; // NOTE: this number can be negative (in case connection does not come from the pool)
                    notifyAll(); // can I remove it ???
                }
            }
        }

        /**
         * Checks out a connection from the pool. If no free connection
         * is available, a new connection is created unless the max
         * number of connections has been reached. If a free connection
         * has been closed by the database, it's removed from the pool
         * and this method is called again recursively.
         */
        synchronized Connection getConnection() {
            Connection con = null;

            while ( (freeConnections.size() > 0) && (con == null) ) {
                // Pick the first Connection in the Vector
                // to get round-robin usage
                con = (Connection) freeConnections.firstElement();
                freeConnections.removeElementAt(0);
                try {
                    if (con.isClosed()) {
                        log.info("Removed bad connection in DBConnectionPool.");
                        con = null; // to make the while loop to continue
                    }
                } catch (SQLException e) {
                    con = null; // to make the while loop to continue
                }
            } // while

            if (con == null) {// cannot get any connection from the pool
                if (maxConn == 0 || checkedOut < maxConn) {// maxConn = 0 means unlimited connections
                    con = newConnection();
                }
            }
            if (con != null) {
                checkedOut++;
            }
            return con;
        }

        /**
         * Checks out a connection from the pool. If no free connection
         * is available, a new connection is created unless the max
         * number of connections has been reached. If a free connection
         * has been closed by the database, it's removed from the pool
         * and this method is called again recursively.
         * <P>
         * If no connection is available and the max number has been
         * reached, this method waits the specified time for one to be
         * checked in.
         *
         * @param timeout The timeout value in milliseconds
         */
        /**
         * Note that this method is not synchronized since it relies on the getConnection(void) method
         * I also believe that this method SHOULD NOT synchronized because I use #sleep() method
         * @todo: check if we should synchronize this method and use wait instead of sleep ???
         */
        Connection getConnection(long timeout) {
            long startTime = System.currentTimeMillis();
            Connection con;
            while ((con = getConnection()) == null) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime >= timeout) {
                    // Timeout has expired
                    return null;
                }

                long timeToWait = timeout - elapsedTime;
                if (timeToWait > TIME_BETWEEN_RETRIES) timeToWait = TIME_BETWEEN_RETRIES;// we dont want to wait for more than TIME_BETWEEN_RETRIES second each time
                try {
                    Thread.sleep(timeToWait);
                } catch (InterruptedException e) {}
            }
            return con;
        }

        /**
         * Closes all available connections.
         * @return true if the pool is empty and balance
         *         false if the pool has returned some connection to outside
         */
        synchronized boolean release() {
            boolean retValue = true;
            Enumeration allConnections = freeConnections.elements();
            while (allConnections.hasMoreElements()) {
                Connection con = (Connection) allConnections.nextElement();
                try {
                    con.close();
                } catch (SQLException e) {
                    log.error("Cannot close connection in DBConnectionPool.");
                }
            }
            freeConnections.removeAllElements();
            if (checkedOut != 0) {
                retValue = false;
                log.warn("DBConnectionManager: the built-in connection pool is not balanced.");
            }
            checkedOut = 0;
            return retValue;
        }

        /**
         * Creates a new connection, using a userid and password
         * if specified.
         * @todo: check if this method need synchronized
         */
        private Connection newConnection() {
            Connection con = null;
            try {
                if (user == null) {
                    con = DriverManager.getConnection(URL);
                } else {
                    con = DriverManager.getConnection(URL, user, password);
                }
                con.setAutoCommit(true);//thread 804 by trulore
            } catch (SQLException e) {
                log.error("Cannot create a new connection in DBConnectionPool. URL = " + URL, e);
                return null;
            }
            return con;
        }
    }
}
