/*
 * DBUtils.java
 *
 * Created on 2006��7��25��, ����11:22
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.db;
import java.sql.*;

import javax.sql.DataSource;

import job.tot.global.Sysconfig;
import job.tot.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/*
 * DBUtils.java
 *
 * Created on 2006��7��18��, ����11:52
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

/**
 *
 * @author ������
 */
public final class DBUtils {

    private static Log log = LogFactory.getLog(DBUtils.class);

    public static final int DATABASE_UNKNOWN     = 0;
    public static final int DATABASE_GENERAL     = 1;
    public static final int DATABASE_NOSCROLL    = 2;

    public static final int DATABASE_ORACLE      = 10;
    public static final int DATABASE_SQLSERVER   = 11;
    public static final int DATABASE_DB2         = 12;
    public static final int DATABASE_SYBASE      = 13;
    public static final int DATABASE_IMFORMIX    = 14;
    public static final int DATABASE_MYSQL       = 15;
    public static final int DATABASE_POSTGRESQL  = 16;
    public static final int DATABASE_HSQLDB      = 17;
    public static final int DATABASE_ACCESS      = 18;
    public static final int DATABASE_SAPDB       = 19;
    public static final int DATABASE_INTERBASE   = 20;
    public static final int DATABASE_FIREBIRD    = 21;

    private static int databaseType = DATABASE_UNKNOWN;

    private static boolean useDatasource = false;

    private static int maxTimeToWait = 2000;// 2 seconds

    private static int minutesBetweenRefresh = 30;// 30 minutes

    private static DBConnectionManager connectionManager = null;

    private static DataSource dataSource = null;

    private static long lastGetConnectionTime = 0;

    private static long lastCloseAllConnectionsTime = 0;

    // static init of the class
    static {
        //DBOptions option = new DBOptions();

        //databaseType = option.databaseType;
        databaseType = Sysconfig.getDatabaseType();
        if (databaseType != DATABASE_UNKNOWN) {
            log.info("Set DATABASE_TYPE = " + databaseType);
        }
        useDatasource = Sysconfig.isUseDataSource();
        if (useDatasource) {
            String dataSourceName = "";
            try {
                javax.naming.Context context = new javax.naming.InitialContext();
                // sample data source = java:comp/env/jdbc/MysqlDataSource
                dataSourceName = Sysconfig.getDataSourceName();
                dataSource = (DataSource) context.lookup(dataSourceName);
                log.info("DBUtils : use datasource = " + dataSourceName);
            } catch (javax.naming.NamingException e) {
                //log.error("Cannot get DataSource: datasource name = " + option.datasourceName, e);
                log.error("Cannot get DataSource: datasource name = " + dataSourceName, e);
            }
        } else {
            //maxTimeToWait = option.maxTimeToWait;
            maxTimeToWait = Sysconfig.getMaxTimeToWait();
            //minutesBetweenRefresh = option.minutesBetweenRefresh;
            minutesBetweenRefresh = Sysconfig.getMinutesBetweenRefresh();
            connectionManager = DBConnectionManager.getInstance(true/* a new method*/);
            log.info("DBUtils : use built-in DBConnectionManager (MAX_TIME_TO_WAIT = " + maxTimeToWait + ", MINUTES_BETWEEN_REFRESH = " + minutesBetweenRefresh + ")");
        }
       // log.info("DBUtils inited. Detailed info: " + MVNCoreInfo.getProductVersion() + " (Build: " + MVNCoreInfo.getProductReleaseDate() + ")");
    }

    private DBUtils() {// so cannot new an instance
    }

    /**
     * Use this method to get the database type. This method will automatically
     * detect the database type. You could override this value by modifying
     * the value in mvncore_db_DBOptions.properties
     * @return : the database type
     */
    public static int getDatabaseType() {
        if (databaseType == DATABASE_UNKNOWN) {
            Connection connection = null;
            try {
                connection = DBUtils.getConnection();
                DatabaseMetaData dbmd = connection.getMetaData();
                String databaseName = dbmd.getDatabaseProductName().toLowerCase();
                if (databaseName.indexOf("oracle") != -1) {
                    databaseType = DATABASE_ORACLE;
                } else if (databaseName.indexOf("sql server") != -1) {
                    databaseType = DATABASE_SQLSERVER;
                } else if (databaseName.indexOf("mysql") != -1) {// "MySQL"
                    databaseType = DATABASE_MYSQL;
                } else if (databaseName.indexOf("postgresql") != -1) {
                    databaseType = DATABASE_POSTGRESQL;
                } else if (databaseName.indexOf("hsql") != -1) {
                    databaseType = DATABASE_HSQLDB;
                } else if (databaseName.indexOf("sap") != -1) {// "SAP DB"
                    databaseType = DATABASE_SAPDB;
                } else if (databaseName.indexOf("firebird") != -1) {//"firebird"
                    databaseType = DATABASE_FIREBIRD;
                } else {
                    databaseType = DATABASE_GENERAL;
                }
                log.info("Auto detect DATABASE_TYPE = " + databaseType + " (" + getDatabaseTypeName(databaseType) + ")");
            } catch (Exception ex) {
                log.error("Error when running getDatabaseType", ex);
            } finally {
                DBUtils.closeConnection(connection);
            }
        }
        return databaseType;
    }

    public static String getDatabaseTypeName(int databaseType) {
        String databaseTypeName = "Cannot find databaseType = " + databaseType;
        switch (databaseType) {
        case DATABASE_UNKNOWN:
            databaseTypeName = "DATABASE_UNKNOWN";
            break;
        case DATABASE_GENERAL:
            databaseTypeName = "DATABASE_GENERAL";
            break;
        case DATABASE_NOSCROLL:
            databaseTypeName = "DATABASE_NOSCROLL";
            break;
        case DATABASE_ORACLE:
            databaseTypeName = "DATABASE_ORACLE";
            break;
        case DATABASE_SQLSERVER:
            databaseTypeName = "DATABASE_SQLSERVER";
            break;
        case DATABASE_DB2:
            databaseTypeName = "DATABASE_DB2";
            break;
        case DATABASE_SYBASE:
            databaseTypeName = "DATABASE_SYBASE";
            break;
        case DATABASE_IMFORMIX:
            databaseTypeName = "DATABASE_IMFORMIX";
            break;
        case DATABASE_MYSQL:
            databaseTypeName = "DATABASE_MYSQL";
            break;
        case DATABASE_POSTGRESQL:
            databaseTypeName = "DATABASE_POSTGRESQL";
            break;
        case DATABASE_HSQLDB:
            databaseTypeName = "DATABASE_HSQLDB";
            break;
        case DATABASE_ACCESS:
            databaseTypeName = "DATABASE_ACCESS";
            break;
        case DATABASE_SAPDB:
            databaseTypeName = "DATABASE_SAPDB";
            break;
        case DATABASE_INTERBASE:
            databaseTypeName = "DATABASE_INTERBASE";
            break;
        case DATABASE_FIREBIRD:
            databaseTypeName = "DATABASE_FIREBIRD";
            break;
        }
        return databaseTypeName;
    }

    /**
     * Get a connection from the connection pool. The returned connection
     * must be closed by calling DBUtils.closeConnection()
     * @return : a new connection from the pool if succeed
     * @throws SQLException : if cannot get a connection from the pool
     */
    public static Connection getConnection() throws SQLException {

        long now = System.currentTimeMillis();
        lastGetConnectionTime = now;
        // now check if we have not close all connections to refresh
        // after MINUTES_BETWEEN_REFRESH minutes, then will do it now
        if (now - lastCloseAllConnectionsTime > DateUtil.MINUTE * minutesBetweenRefresh) {
            boolean isBalance = closeAllConnections();
            if (isBalance == false) {
                try {
                    // wait for the checked-out connections to be returned and closed
                    Thread.sleep(2000);
                    log.debug("DBUtils: sleep 2 seconds for checked-out connections to returned and closed.");
                } catch (Exception ex) { }
            }
        }

        Connection conection = null;

        if (useDatasource) {
            if (dataSource != null) {
                conection = dataSource.getConnection();
            }
        } else {
            if (connectionManager != null) {
                conection = connectionManager.getConnection(maxTimeToWait);
            } else {
                log.fatal("Assertion: DBUtils.connectionManager == null");
            }
        }

        if (conection == null) {
            throw new SQLException("DBUtils: Cannot get connection from Connection Pool.");
        }
        return conection;
    }

    /**
     * Close all the connections that currently in the pool
     * This method could be used to refresh the database connection
     * @return true if the pool is empty and balance
     *         false if the pool has returned some connection to outside
     */
    public static boolean closeAllConnections() {
        log.debug("DBUtils.closeAllConnections is called.");
        boolean retValue = true;// balance (default)
        lastCloseAllConnectionsTime = System.currentTimeMillis();
        if (useDatasource) {
            if (dataSource != null) {
                // do nothing here now
            }
        } else {
            if (connectionManager != null) {
                retValue = connectionManager.release();
            } else {
                log.fatal("Assertion: DBUtils.connectionManager == null");
            }
        }
        return retValue;
    }

    /**
     * Use this method to return the connection to the connection pool
     * Do not use this method to close connection that is not from
     * the connection pool
     * @param connection : the connection that needs to be returned to the pool
     */
    public static void closeConnection(Connection connection) {
        if (connection == null) return;

        if (useDatasource) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("DBUtils: Cannot close connection.", e);
            }
        } else {
            connectionManager.freeConnection(connection);
        }
    }

    /**
     * Use this method to reset the MaxRows and FetchSize of the Statement
     * to the default values
     * @param statement : the statement that needs to be reseted
     */
    public static void resetStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.setMaxRows(0); //reset to the default value
            } catch (SQLException e) {
                log.error("DBUtils: Cannot reset statement MaxRows.", e);
            }

            try {
                statement.setFetchSize(0); //reset to the default value
            } catch (SQLException sqle) {
                //do nothing, postgreSQL doesnt support this method
            }
        }
    }

    /**
     * Use this method to close the Statement
     * @param statement : the statement that needs to be closed
     */
    public static void closeStatement(Statement statement) {
        try {
            if (statement != null) statement.close();
        } catch (SQLException e) {
            log.error("DBUtils: Cannot close statement.", e);
        }
    }
/**
     * Use this method to close the PrepareStatement
     * @param ps : the statement that needs to be closed
     */
    public static void closePrepareStatement(PreparedStatement ps) {
        try {
            if (ps != null){
                ps.clearParameters();
                ps.close();
            }
        } catch (SQLException e) {
            log.error("DBUtils: Cannot close PreparedStatement.", e);
        }
    }
    /**
     * Use this method to close the ResultSet
     * @param rs : the resultset that needs to be closed
     */
    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            log.error("DBUtils: Cannot close resultset.", e);
        }
    }

    /*
    public static void main(String[] args) {
        //DBUtils DBUtils1 = new DBUtils();
        //log.info("i = " + dataSource1);
    }*/
}