/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CourierDaoImplJDBC extends AbstractDao {

    private static Log log = LogFactory.getLog(CourierDaoImplJDBC.class);

    public DataField get(int id) {
        String fieldArr = "id,Title,Price,Content";
        return getFirstData("select " + fieldArr + " from t_wuliu where id=" + id, fieldArr);
    }

    public boolean add(String title, float price, String content) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "insert into t_wuliu(Title,Price,Content) values(?,?,?)";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setFloat(2, price);

            ps.setString(3, content);

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

    public boolean mod(int id, String title, float price, String content) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_wuliu set Title=?,Price=?,Content=? where id=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setFloat(2, price);

            ps.setString(3, content);

            ps.setInt(4, id);
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

    public Collection getList(int currentpage, int pagesize) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,Title,Price,Content";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_wuliu");
        sql.append(" where 1=1");

        sql.append(" order by id desc ");
        if (currentpage >= 0 && pagesize > 0) {
            sql.append(" limit ");
            sql.append((currentpage - 1) * pagesize);
            sql.append(",");
            sql.append(pagesize);
        }

        return getData(sql.toString(), fieldArr);
    }

    public int getTotalCount() {
        StringBuffer sql = new StringBuffer(512);
        sql.append("select count(*) from t_wuliu");
        sql.append(" where 1=1");

        return getDataCount(sql.toString());
    }

    public void batDel(String[] s) {
        bat("delete from t_wuliu where id=?", s);
    }
}
