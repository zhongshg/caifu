/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package job.tot.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import job.tot.bean.DataField;
import job.tot.dao.AbstractDao;
import job.tot.dao.DaoFactory;
import job.tot.db.DBUtils;
import job.tot.exception.DatabaseException;
import job.tot.exception.ObjectNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Administrator
 */
public class PropertysDaoImplJDBC extends AbstractDao {

    private static Log log = LogFactory.getLog(PropertysDaoImplJDBC.class);

    /**
     * Creates a new instance of MessageDaoImplJDBC
     */
    public PropertysDaoImplJDBC() {
    }

    public DataField getById(int id) {
        String fieldArr = "id,svid,svname,sid,signid,wxsid";
        return getFirstData("select " + fieldArr + " from t_propertys where id='" + id + "'", fieldArr);
    }

    public DataField get(String svid) {
        String fieldArr = "id,svid,svname,sid,signid,wxsid";
        return getFirstData("select " + fieldArr + " from t_propertys where svid='" + svid + "'", fieldArr);
    }

    public Collection getList(String sid, int signid, String wxsid) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,svid,svname,sid,signid,wxsid";

        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_propertys where sid='" + sid + "' and signid=" + signid + " and wxsid='" + wxsid + "'");
        return this.getData(sql.toString(), fieldArr);
    }

    public void batDel(String[] s) {
        this.bat("delete from t_propertys where id=?", s);
    }

    public DataField getMaxId() {
        String fieldArr = "max(id) counts";
        return getFirstData("select " + fieldArr + " from t_propertys", "counts");
    }

    public int getMaxIdInt() {
        return getDataCount("select max(id) from t_propertys");
    }

    public boolean add(String svid, String svname, String sid, int signid, String wxsid) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "insert into t_propertys(svid,svname,sid,signid,wxsid) values(?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, svid);
            ps.setString(2, svname);
            ps.setString(3, sid);
            ps.setInt(4, signid);
            ps.setString(5, wxsid);
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

    public boolean mod(int id, String svname) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_propertys set svname=? where id=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, svname);
            ps.setInt(2, id);
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

    public boolean del(String svid, String signid, String wxsid) throws ObjectNotFoundException, DatabaseException {
        return exe("delete from t_propertys where svid='" + svid + "' and signid=" + signid + " and wxsid=" + wxsid);
    }

    public void delList(DataField df, String signid, String wxsid) {
        //删除产品内属性
        List<DataField> productList = (List<DataField>) DaoFactory.getProductDAO().getList(wxsid);
        for (DataField product : productList) {
            String propertys = product.getFieldValue("propertys");
            //只限于两级判断带“-”，同时出现两杠时不适用本方法
            if (-1 != df.getFieldValue("svid").indexOf("-")) {
                propertys = propertys.replace((signid + "," + df.getFieldValue("svid")), "");
            }
            DaoFactory.getProductDAO().modpropertys(product.getInt("id"), propertys.trim());
        }

        //删除属性组内属性
        List<DataField> psvList = (List<DataField>) DaoFactory.getPsvDaoImplJDBC().getBySvids(("*" + df.getString("svid") + ","), Integer.parseInt(signid), wxsid);
        for (DataField psv : psvList) {
            String svids = psv.getFieldValue("svids");
            svids = svids.replace(("*" + df.getString("svid") + ","), "");
            DaoFactory.getPsvDaoImplJDBC().modSvids(psv.getFieldValue("id"), svids, Integer.parseInt(signid), wxsid);
        }
        try {
            //删除属性本身
            DaoFactory.getPropertysDaoImplJDBC().del(df.getString("svid"), signid, wxsid);
        } catch (ObjectNotFoundException ex) {
            Logger.getLogger(PropertysDaoImplJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(PropertysDaoImplJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<DataField> list = (List<DataField>) DaoFactory.getPropertysDaoImplJDBC().getList(df.getString("svid"), Integer.parseInt(signid), wxsid);
        for (DataField tempmap : list) {
            delList(tempmap, signid, wxsid);
        }
    }

    public Collection get_Limit(String sid, String signid, String wxsid, int currentpage, int pagesize) {
        String str = "id,svid,svname,sid,signid,wxsid";
        StringBuilder sql = new StringBuilder("select id,svid,svname,sid,signid,wxsid from t_propertys where sid='" + sid + "' and signid=" + signid + " and wxsid=" + wxsid);
        return getDataList_mysqlLimit(sql.toString(), str, pagesize, (currentpage - 1) * pagesize);
    }

    public int getTotalCount(String sid, String signid, String wxsid) {
        return getDataCount("select count(*) from t_propertys where sid='" + sid + "' and signid=" + signid + " and wxsid=" + wxsid);
    }
}
