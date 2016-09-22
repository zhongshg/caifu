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
import job.tot.exception.ObjectNotFoundException;
import job.tot.exception.DatabaseException;
import java.sql.*;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import job.tot.bean.DataField;

/**
 *
 * @author Administrator
 */
public class AddressDaoImplJDBC extends AbstractDao {

    private static Log log = LogFactory.getLog(AddressDaoImplJDBC.class);

    /**
     * Creates a new instance of FriendLinkDaoImplJDBC
     */
    public AddressDaoImplJDBC() {
    }

    public boolean exitsaddress(String userid, String wxsid) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean returnValue = true;
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement("select * from t_address where UserId=? and wxsid=?");
            ps.setString(1, userid);
            ps.setInt(2, Integer.parseInt(wxsid));
            rs = ps.executeQuery();
            if (rs.next()) {
                returnValue = true;
            } else {
                returnValue = false;
            }
        } catch (SQLException e) {
            log.error("exitsAddress error ", e);
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }

    public DataField get(int id) {
        String fieldArr = "id,UserId,Name,Address,Sname,Ssname,Xname,Phone,TelePhone,ZipCode,MoRen,Xid";
        return getFirstData("select " + fieldArr + " from t_address where id=" + id, fieldArr);
    }

    public DataField getUserId(String UserId, String wxsid) {
        String fieldArr = "id,UserId,Name,Address,Sname,Ssname,Xname,Phone,TelePhone,ZipCode,MoRen,Xid,Weixin,Remark";
        return getFirstData("select " + fieldArr + " from t_address where UserId='" + UserId + "' and wxsid=" + wxsid + " order by id desc limit 0 ,1", fieldArr);
    }

    public DataField getDizhi(int X_id) {
        StringBuffer sql = new StringBuffer(512);

        String fieldArr = "xian.id,Xming,xian.Yfei,Weight,Cweight,Cmoney,Smoney,Sming,Ssming";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_fxian xian join t_fshi shi on xian.Ss_id=shi.Ss_id join t_fsheng sheng on shi.S_id=sheng.S_id");
        sql.append(" where 1=1 and xian.X_id=" + X_id);

        return this.getFirstData(sql.toString(), fieldArr);
    }
    /*
     * add friend link
     * @param f_type 1 is photo link,0 is text link
     */

    public boolean add(String UserId, String Name, String Address, String Sname, String Ssname, String Xname, String Phone, String TelePhone, String weixin, String remark, String wxsid) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "insert into t_address(UserId,Name,Address,Sname,Ssname,Xname,Phone,TelePhone,weixin,remark,wxsid) values(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, UserId);
            ps.setString(2, Name);
            ps.setString(3, Address);
            ps.setString(4, Sname);
            ps.setString(5, Ssname);
            ps.setString(6, Xname);
            ps.setString(7, Phone);
            ps.setString(8, TelePhone);
            ps.setString(9, weixin);
            ps.setString(10, remark);
            ps.setInt(11, Integer.parseInt(wxsid));
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

    public boolean modadd(String UserId, String Name, String Address, String Sname, String Ssname, String Xname, String Phone, String TelePhone, String weixin, String remark, String wxsid) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_address set Name=?, Address=?,Sname=?,Ssname=?,Xname=?,Phone=?,TelePhone=?,weixin=?,remark=? where wxsid=? and UserId=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, Name);
            ps.setString(2, Address);
            ps.setString(3, Sname);
            ps.setString(4, Ssname);
            ps.setString(5, Xname);
            ps.setString(6, Phone);
            ps.setString(7, TelePhone);
            ps.setString(8, weixin);
            ps.setString(9, remark);
            ps.setString(10, wxsid);
            ps.setString(11, UserId);
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

    public boolean mod(int id, String UserId, String Name, String Address, String Sname, String Ssname, String Xname, String Phone, String TelePhone, String ZipCode, int MoRen, int Xid) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_address set Name=?, Address=?,Sname=?,Ssname=?,Xname=?,Phone=?,TelePhone=?,ZipCode=?,MoRen=?,Xid=? where id=? and UserId=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, Name);
            ps.setString(2, Address);
            ps.setString(3, Sname);
            ps.setString(4, Ssname);
            ps.setString(5, Xname);
            ps.setString(6, Phone);
            ps.setString(7, TelePhone);
            ps.setString(8, ZipCode);
            ps.setInt(9, MoRen);
            ps.setInt(10, Xid);
            ps.setInt(11, id);
            ps.setString(12, UserId);
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

    public boolean modUserId(String UserId, String Name, String Address, String Sname, String Ssname, String Xname, String Phone, String TelePhone, String ZipCode, int MoRen, int Xid) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_address set Name=?, Address=?,Sname=?,Ssname=?,Xname=?,Phone=?,TelePhone=?,ZipCode=?,MoRen=?,Xid=? where UserId=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, Name);
            ps.setString(2, Address);
            ps.setString(3, Sname);
            ps.setString(4, Ssname);
            ps.setString(5, Xname);
            ps.setString(6, Phone);
            ps.setString(7, TelePhone);
            ps.setString(8, ZipCode);
            ps.setInt(9, MoRen);
            ps.setInt(10, Xid);

            ps.setString(11, UserId);
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

    public Collection getList(String userid) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,UserId,Name,Address,Sname,Ssname,Xname,Phone,TelePhone,ZipCode,MoRen,Xid";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_address address where address.UserId='").append(userid).append("'");
        sql.append(" order by MoRen asc");
        return this.getData(sql.toString(), fieldArr);
    }

    public int getTotalCount(String userid) {
        StringBuffer sql = new StringBuffer(512);
        sql.append("select count(*) from t_address where UserId='").append(userid).append("'");
        return (this.getDataCount(sql.toString()));
    }

    public void modMoRen(String uid) throws ObjectNotFoundException, DatabaseException {
        exe("update t_address set MoRen=2 where UserId='" + uid + "'");
    }

    public void batDel(String[] s) {
        this.bat("delete from t_address where id=?", s);
    }

    public boolean del(int id, String userid) throws ObjectNotFoundException, DatabaseException {
        return exe("delete from t_address where id=" + id + " and UserId='" + userid + "'");
    }
}
