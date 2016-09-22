/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package job.tot.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import job.tot.bean.DataField;
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
public class AreaDaoImplJDBC extends AbstractDao {

    private static Log log = LogFactory.getLog(AreaDaoImplJDBC.class);

    public Collection getList(int pid) {
        String fields = "id,title,pid,sortid,lat,lng";
        String sql = "select " + fields + " from t_area where pid=" + pid;
        sql = sql + " order by sortid asc";
        return getData(sql, fields);
    }

    public boolean add(String title, int pid, int sortid, String lat, String lng) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "insert into t_area(title,pid,sortid,lat,lng) values(?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setInt(2, pid);
            ps.setInt(3, sortid);
            ps.setString(4, lat);
            ps.setString(5, lng);
            if (ps.executeUpdate() != 1) {
                returnValue = false;
            }
        } catch (SQLException e) {
            log.error("add Role error", e);
        } finally {
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }

    public boolean mod(int id, String title, int pid, int sortid, String lat, String lng) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_area set title=?,pid=?,sortid=?,lat=?,lng=? where id=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setInt(2, pid);
            ps.setInt(3, sortid);
            ps.setString(4, lat);
            ps.setString(5, lng);
            ps.setInt(6, id);
            if (ps.executeUpdate() != 1) {
                returnValue = false;
            }
        } catch (SQLException e) {
            log.error("mod role error", e);
        } finally {
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }

    public boolean del(int id) throws ObjectNotFoundException, DatabaseException {
        return exe("delete from t_area where id=" + id);
    }

    public Collection get_Limit(int pid, int currentpage, int pagesize) {
        String str = "id,title,pid,sortid,lat,lng";
        StringBuilder sql = new StringBuilder("select id,title,pid,sortid,lat,lng from t_area where pid=" + pid);
//        if (0 != parentid) {
//            sql.append(" and parentid=" + parentid );
//        }
//        if ((identity != null) && (!identity.equals(""))) {
//            sql.append(" and identity like '%" + identity + "%'");
//        }
//        if ((area != null) && (!area.equals(""))) {
//            sql.append(" and area like '%" + area + "%'");
//        }
//        if ((datef != null) && (datet != null) && (!datef.equals("")) && (!datet.equals(""))) {
//            sql.append(" and to_days(times)>=to_days('").append(datef).append("') and to_days(times)<=to_days('").append(datet).append("')");
//        }
        return getDataList_mysqlLimit(sql.toString(), str, pagesize, (currentpage - 1) * pagesize);
    }

    public DataField get(String id) {
        return getFirstData("select id,title,pid,sortid,lat,lng from t_area where id=" + id, "id,title,pid,sortid,lat,lng");
    }

    public int getTotalCount(int pid) {
        return getDataCount("select count(*) from t_area where pid=" + pid);
    }

    public int getMaxId() {
        return getDataCount("select max(id) from t_area");
    }
}
