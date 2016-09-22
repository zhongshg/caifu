/*
 * FriendLinkDaoImplJDBC.java
 *
 * Created on 2007��7��12��, ����3:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package job.tot.dao.jdbc;

import job.tot.dao.AbstractDao;
import job.tot.db.DBUtils;
import java.sql.*;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import job.tot.bean.DataField;

/**
 *
 * @author Administrator
 */
public class SlideDaoImplJDBC extends AbstractDao {

    private static Log log = LogFactory.getLog(SlideDaoImplJDBC.class);

    /**
     * Creates a new instance of FriendLinkDaoImplJDBC
     */
    public SlideDaoImplJDBC() {
    }

    public DataField get(int id) {
        String fieldArr = "User,Title,Photo,LinkUrl,SortId";
        return getFirstData("select " + fieldArr + " from t_slide where id=" + id, fieldArr);
    }
    /*
     * add friend link
     * @param f_type 1 is photo link,0 is text link
     */

    public boolean add(String User, String Title, String Photo, String LinkUrl, int SortId) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "insert into t_slide(User,Title,Photo,LinkUrl,SortId) values(?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, User);
            ps.setString(2, Title);
            ps.setString(3, Photo);
            ps.setString(4, LinkUrl);
            ps.setInt(5, SortId);
            if (ps.executeUpdate() != 1) {
                returnValue = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }
    /*
     * modify friendlink
     */

    public boolean mod(int id, String User, String Title, String Photo, String LinkUrl, int SortId) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_slide set User=?,Title=?,Photo=?,LinkUrl=?,SortId=? where id=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, User);
            ps.setString(2, Title);
            ps.setString(3, Photo);
            ps.setString(4, LinkUrl);
            ps.setInt(5, SortId);
            ps.setInt(6, id);
            if (ps.executeUpdate() != 1) {
                returnValue = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }

    public Collection getList(String user, int currentpage, int pagesize) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,User,Title,Photo,LinkUrl,SortId";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_slide");
        if (user != null && !user.equals("")) {
            sql.append(" where User='").append(user).append("'");
        }
        sql.append(" order by SortId ASC ");
        if (currentpage >= 0 && pagesize > 0) {
            sql.append(" limit ");
            sql.append((currentpage - 1) * pagesize);
            sql.append(",");
            sql.append(pagesize);
        }
        return this.getData(sql.toString(), fieldArr);
    }

    public int getTotalCount(String user) {
        StringBuffer sql = new StringBuffer(512);
        sql.append("select count(*) from t_slide");
        if (user != null && !user.equals("")) {
            sql.append(" where User='").append(user).append("'");
        }
        return (this.getDataCount(sql.toString()));
    }

    public void batDel(String[] s) {
        this.bat("delete from t_slide where id=?", s);
    }
}
