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
import job.tot.dao.AbstractDao;
import job.tot.db.DBUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Administrator
 */
public class ExportlogDaoImplJDBC extends AbstractDao {

    private static Log log = LogFactory.getLog(BasketDaoImplJDBC.class);

    public Collection getList(String wxsid, int CurrentPage, int PageSize) {
        StringBuffer sql = new StringBuffer(512);
        sql.append("select id,name,path,times,wxsid,uid from exportlog");
        if (wxsid != null && !wxsid.equals("")) {
            sql.append(" where  wxsid='");
            sql.append(wxsid);
            sql.append("'");
        }
        sql.append(" order by id desc");
        if (CurrentPage >= 0 && PageSize > 0) {

            sql.append(" limit ");
            sql.append((CurrentPage - 1) * PageSize);
            sql.append(",");
            sql.append(PageSize);
        }
        return this.getData(sql.toString(), "id,name,path,times,wxsid,uid");
    }

    public int getTotalNum(String wxsid) {
        int returnValue = 0;
        StringBuffer sql = new StringBuffer(512);
        sql.append("select count(*) from exportlog");
        if (wxsid != null && !wxsid.equals("")) {
            sql.append(" where  wxsid='");
            sql.append(wxsid);
            sql.append("'");
        }
        returnValue = getDataCount(sql.toString());
        return returnValue;
    }

    public boolean add(String name, String path, String times, String wxsid, int uid) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "insert into exportlog(name,path,times,wxsid,uid) values(?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, path);
            ps.setString(3, times);
            ps.setString(4, wxsid);
            ps.setInt(5, uid);
            if (ps.executeUpdate() != 1) {
                returnValue = false;
            }
        } catch (SQLException e) {
            log.error("add basket error ", e);
        } finally {
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }

    public void batDel(String[] s) {
        this.bat("delete from exportlog where id=?", s);
    }
}
