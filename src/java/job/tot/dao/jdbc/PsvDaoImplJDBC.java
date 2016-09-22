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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Administrator
 */
public class PsvDaoImplJDBC extends AbstractDao {

    private static Log log = LogFactory.getLog(PsvDaoImplJDBC.class);

    /**
     * Creates a new instance of MessageDaoImplJDBC
     */
    public PsvDaoImplJDBC() {
    }

    public Collection getList(int pid, int signid, String wxsid) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,pid,svids,price,stock,psvcode,signid,wxsid";

        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_psv where pid=" + pid + " and signid=" + signid + " and wxsid=" + wxsid + " order by id");
        return this.getData(sql.toString(), fieldArr);
    }

    //库存预警
    public Collection getByStockList(int pid, int signid, String wxsid) {
        StringBuffer sql = new StringBuffer(512);
        sql.append("select tpsv.id,tpsv.pid,tpsv.svids,tpsv.price,tpsv.stock,tpsv.psvcode,tpsv.signid,tpsv.wxsid,tp.JiangShi stockwarn from t_psv tpsv,t_product tp where tpsv.stock<=tp.JiangShi and  tpsv.pid=tp.id and tpsv.pid=" + pid + " and tpsv.signid=" + signid + " and tpsv.wxsid=" + wxsid);
        return this.getData(sql.toString(), "id,pid,svids,price,stock,psvcode,signid,wxsid,stockwarn");
    }

    public Collection getBySvids(String svids, int signid, String wxsid) {
        String svidsArr[] = svids.split(",");
        String svidsStr = "";
        for (int i = 0; i < svidsArr.length; i++) {
            if ("".equals(svidsArr[i])) {
                continue;
            }
            svidsStr += svidsArr[i] + ",%";
        }
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,pid,svids,price,stock,psvcode,signid,wxsid";

        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_psv where svids like '%" + svidsStr + "%' and signid=" + signid + " and wxsid=" + wxsid);
        return this.getData(sql.toString(), fieldArr);
    }

    public DataField getBySvids(String svids, int pid, int signid, String wxsid) {
        String svidsArr[] = svids.split(",");
        String svidsStr = "";
        for (int i = 0; i < svidsArr.length; i++) {
            if ("".equals(svidsArr[i])) {
                continue;
            }
            svidsStr += svidsArr[i] + ",%";
        }
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,pid,svids,price,stock,psvcode,signid,wxsid";

        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_psv where svids like '%" + svidsStr + "%' and pid=" + pid + " and signid=" + signid + " and wxsid=" + wxsid + " order by id");
        return this.getFirstData(sql.toString(), fieldArr);
    }

    public boolean add(int pid, String svids, float price, int stock, String psvcode, int signid, String wxsid) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "insert into t_psv(pid,svids,price,stock,psvcode,signid,wxsid) values(?,?,?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, pid);
            ps.setString(2, svids);
            ps.setFloat(3, price);
            ps.setInt(4, stock);
            ps.setString(5, psvcode);
            ps.setInt(6, signid);
            ps.setString(7, wxsid);
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

    public boolean modSvids(String id, String svids, int signid, String wxsid) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_psv set svids='" + svids + "'  where id=" + id + " and signid=" + signid + " and wxsid=" + wxsid;
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
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

    public boolean modStock(String svids, int stock, int pid, int signid, String wxsid) {
        String svidsArr[] = svids.split(",");
        String svidsStr = "";
        for (int i = 0; i < svidsArr.length; i++) {
            if ("".equals(svidsArr[i])) {
                continue;
            }
            svidsStr += svidsArr[i] + ",%";
        }
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_psv set stock=stock-" + stock + "  where svids like '%" + svidsStr + "%' and pid=" + pid + " and signid=" + signid + " and wxsid=" + wxsid;
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
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

    public boolean delList(int pid, int signid, String wxsid) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = false;
        String sql = "delete from t_psv where pid=" + pid + " and signid=" + signid + " and wxsid=" + wxsid;
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            if (ps.executeUpdate() != -1) {
                returnValue = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            returnValue = false;
        } finally {
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }
}
