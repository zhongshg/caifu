package job.tot.dao.jdbc;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.*;
import java.util.*;
import job.tot.dao.AbstractDao;
import job.tot.db.DBUtils;
import job.tot.exception.DatabaseException;
import job.tot.exception.ObjectNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Administrator
 */
public class MysqlDaoImplJDBC extends AbstractDao {

    private static Log log = LogFactory.getLog(MysqlDaoImplJDBC.class);

    /**
     * Creates a new instance of IndustryDaoImplJDBC
     */
    public MysqlDaoImplJDBC() {
    }

    public Collection getList(String sql, String fieldArr) {
        return this.getData(sql, fieldArr);
    }

    public boolean mod(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            if (ps.executeUpdate() != 1) {
                returnValue = false;
            }
        } catch (SQLException e) {
            log.error("insert into " + sql + " error ", e);
        } finally {
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }

    public boolean add(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            if (ps.executeUpdate() != 1) {
                returnValue = false;
            }
        } catch (SQLException e) {
            log.error("insert into " + sql + " error ", e);
        } finally {
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }

    public boolean del(String sql) {
        boolean returnValue = false;
        try {
            returnValue = this.exe(sql);
        } catch (ObjectNotFoundException ex) {
            Logger.getLogger(MysqlDaoImplJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(MysqlDaoImplJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnValue;
    }

    public boolean creatTable(String sql) throws ObjectNotFoundException, DatabaseException {
        return exe(sql);
    }
}
