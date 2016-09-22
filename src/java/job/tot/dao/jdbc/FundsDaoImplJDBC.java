/*
 * UserDaoImplJDBC.java
 *
 * Created on 2007锟斤拷4锟斤拷16锟斤拷, 锟斤拷锟斤拷4:12
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import job.tot.bean.DataField;

/**
 *
 * @author Useristrator
 */
public class FundsDaoImplJDBC extends AbstractDao {

    private static Log log = LogFactory.getLog(FundsDaoImplJDBC.class);

    /**
     * Creates a new instance of UserDaoImplJDBC
     */
    public FundsDaoImplJDBC() {
    }
    /*
     * insert new user
     Id,F_No,F_Date,F_Price,TSF_Price,TF_Price,SF_Price,CF_Price,UserId,UserName,Ip,Sts,Type,IsPay,F_Name,F_Address,F_Mobile,F_Tel,Demons,ShipNo,ShipName,PayType,PostType,S_Name,S_Mobile,Percent,IsInvoice,liuyan
     */

    public boolean add(String F_No, Timestamp F_Date, float F_Price, String UserId, String UserName, String Alipay, String Ip, int Sts, int Type, String F_Phone, String Descr, int isdel, String wxsid) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "insert into t_funds(F_No,F_Date,F_Price,UserId,UserName,Alipay,Ip,Sts,Type,F_Phone,Descr,isdel,wxsid) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, F_No);
            ps.setTimestamp(2, F_Date);
            ps.setFloat(3, F_Price);
            ps.setString(4, UserId);
            ps.setString(5, UserName);
            ps.setString(6, Alipay);
            ps.setString(7, Ip);
            ps.setInt(8, Sts);
            ps.setInt(9, Type);
            ps.setString(10, F_Phone);
            ps.setString(11, Descr);
            ps.setInt(12, isdel);
            ps.setInt(13, Integer.parseInt(wxsid));
            if (ps.executeUpdate() != 1) {
                returnValue = false;
            }
        } catch (SQLException e) {
            log.error("addUser error ", e);
        } finally {
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }

    public DataField get(String fno) {
        String fieldArr = "id,F_No,F_Date,F_Price,UserId,UserName,Alipay,Ip,Sts,Type,F_Phone,Descr,isdel,wxsid";
        String sql = "select " + fieldArr + " from t_funds where F_No='" + fno + "'";
        return getFirstData(sql, fieldArr);
    }

    //锟斤拷锟铰斤拷锟?//修改状态,1未完成2完成
    public void modSts(String fno, int num) throws ObjectNotFoundException, DatabaseException {
        exe("update t_funds set Sts=" + num + " where F_No='" + fno + "'");
    }

    public void modOp(String fno, int op) throws ObjectNotFoundException, DatabaseException {
        exe("update t_funds set op=" + op + " where F_No='" + fno + "'");
    }

    public Collection getList(int Sts, int Type, String fno, String UserId, String Alipay, int isdel, String wxsid, int currentpage, int pagesize) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,F_No,F_Date,F_Price,UserId,UserName,Alipay,Ip,Sts,Type,F_Phone,Descr,isdel,wxsid,op";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_funds");
        sql.append(" where 1=1");
        if (Sts > 0) {
            sql.append(" and Sts=").append(Sts);
        }
        if (fno != null && !fno.equals("")) {
            sql.append(" and F_No='").append(fno).append("'");
        }
        if (UserId != null && !UserId.equals("") && !UserId.equals("null")) {
            sql.append(" and UserId='").append(UserId).append("'");
        }
        if (Alipay != null && !Alipay.equals("") && !Alipay.equals("null")) {
            sql.append(" and Alipay='").append(Alipay).append("'");
        }
        if (Type > 0) {
            sql.append(" and Type=").append(Type);
        }
        if (isdel != -1) {
            sql.append(" and isdel=").append(isdel);
        }
        if (wxsid != null && !wxsid.equals("")) {
            sql.append(" and wxsid=").append(wxsid);
        }
        sql.append(" order by id desc ");
        if (currentpage >= 0 && pagesize > 0) {
            sql.append(" limit ");
            sql.append((currentpage - 1) * pagesize);
            sql.append(",");
            sql.append(pagesize);
        }
        return this.getData(sql.toString(), fieldArr);
    }

    public Collection getList(int Sts, int Type, String fno, String UserId, String Alipay, int isdel, String wxsid, String fromdate, String todate, int currentpage, int pagesize) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,F_No,F_Date,F_Price,UserId,UserName,Alipay,Ip,Sts,Type,F_Phone,Descr,isdel,wxsid,op";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_funds");
        sql.append(" where 1=1");
        if (Sts > 0) {
            sql.append(" and Sts=").append(Sts);
        }
        if (fno != null && !fno.equals("")) {
            sql.append(" and F_No='").append(fno).append("'");
        }
        if (UserId != null && !UserId.equals("") && !UserId.equals("null")) {
            sql.append(" and UserId='").append(UserId).append("'");
        }
        if (Alipay != null && !Alipay.equals("") && !Alipay.equals("null")) {
            sql.append(" and Alipay='").append(Alipay).append("'");
        }
        if (Type > 0) {
            sql.append(" and Type=").append(Type);
        }
        if (isdel != -1) {
            sql.append(" and isdel=").append(isdel);
        }
        if (wxsid != null && !wxsid.equals("")) {
            sql.append(" and wxsid=").append(wxsid);
        }
        if (fromdate != null && !fromdate.equals("")) {
            sql.append(" and to_days(F_Date)>=to_days('").append(fromdate).append("')");

        }
        if (todate != null && !todate.equals("")) {
            sql.append(" and to_days(F_Date)<=to_days('").append(todate).append("')");

        }
        sql.append(" order by id desc ");
        if (currentpage >= 0 && pagesize > 0) {
            sql.append(" limit ");
            sql.append((currentpage - 1) * pagesize);
            sql.append(",");
            sql.append(pagesize);
        }
        return this.getData(sql.toString(), fieldArr);
    }

    public int getTotalNum(int Sts, int Type, String fno, String UserId, String Alipay, int isdel, String wxsid) {
        int returnValue = 0;
        StringBuffer sql = new StringBuffer(512);
        sql.append("select count(*) from t_funds");
        sql.append(" where 1=1");
        if (Sts > 0) {
            sql.append(" and Sts=").append(Sts);
        }
        if (fno != null && !fno.equals("")) {
            sql.append(" and F_No='").append(fno).append("'");
        }
        if (UserId != null && !UserId.equals("") && !UserId.equals("null")) {
            sql.append(" and UserId='").append(UserId).append("'");
        }
        if (Alipay != null && !Alipay.equals("") && !Alipay.equals("null")) {
            sql.append(" and Alipay='").append(Alipay).append("'");
        }
        if (Type > 0) {
            sql.append(" and Type=").append(Type);
        }
        if (isdel != -1) {
            sql.append(" and isdel=").append(isdel);
        }
        if (wxsid != null && !wxsid.equals("")) {
            sql.append(" and wxsid=").append(wxsid);
        }
        returnValue = getDataCount(sql.toString());
        return returnValue;
    }

    public int getTotalNum(int Sts, int Type, String fno, String UserId, String Alipay, int isdel, String wxsid, String fromdate, String todate) {
        int returnValue = 0;
        StringBuffer sql = new StringBuffer(512);
        sql.append("select count(*) from t_funds");
        sql.append(" where 1=1");
        if (Sts > 0) {
            sql.append(" and Sts=").append(Sts);
        }
        if (fno != null && !fno.equals("")) {
            sql.append(" and F_No='").append(fno).append("'");
        }
        if (UserId != null && !UserId.equals("") && !UserId.equals("null")) {
            sql.append(" and UserId='").append(UserId).append("'");
        }
        if (Alipay != null && !Alipay.equals("") && !Alipay.equals("null")) {
            sql.append(" and Alipay='").append(Alipay).append("'");
        }
        if (Type > 0) {
            sql.append(" and Type=").append(Type);
        }
        if (isdel != -1) {
            sql.append(" and isdel=").append(isdel);
        }
        if (wxsid != null && !wxsid.equals("")) {
            sql.append(" and wxsid=").append(wxsid);
        }
        if (fromdate != null && !fromdate.equals("")) {
            sql.append(" and to_days(F_Date)>=to_days('").append(fromdate).append("')");

        }
        if (todate != null && !todate.equals("")) {
            sql.append(" and to_days(F_Date)<=to_days('").append(todate).append("')");

        }
        returnValue = getDataCount(sql.toString());
        return returnValue;
    }

    public void del(String[] s) {
        this.bat_String("delete from t_funds where F_No=?", s);
    }

    public void modisdel(String[] s) {
        this.bat_String("update t_funds set isdel=1 where F_No=?", s);
    }

    public void successBal(String[] s) {
        this.bat_String("update t_funds set Sts=2 where F_No=?", s);
    }

    public void success(String s, int sts) {
        try {
            this.exe("update t_funds set Sts=" + sts + " where F_No='" + s + "'");
        } catch (ObjectNotFoundException ex) {
            Logger.getLogger(FundsDaoImplJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(FundsDaoImplJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //特殊,删除用户特例
    public void delete(String wxsid, String UserId, Connection conn) {
        PreparedStatement ptst = null;
        String sql = "delete from t_funds where wxsid=" + wxsid + " and UserId='" + UserId + "'";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.executeUpdate();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(FundsDaoImplJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public float getTotalMoney(int Sts, int Type, String fno, String UserId, String Alipay, int isdel, String wxsid, int currentpage, int pagesize) {
        float totalmoney = 0;
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ");
        sql.append(" sum(F_Price) totalmoney");
        sql.append(" from t_funds");
        sql.append(" where 1=1");
        if (Sts > 0) {
            sql.append(" and Sts=").append(Sts);
        }
        if (fno != null && !fno.equals("")) {
            sql.append(" and F_No='").append(fno).append("'");
        }
        if (UserId != null && !UserId.equals("") && !UserId.equals("null")) {
            sql.append(" and UserId='").append(UserId).append("'");
        }
        if (Alipay != null && !Alipay.equals("") && !Alipay.equals("null")) {
            sql.append(" and Alipay='").append(Alipay).append("'");
        }
        if (Type > 0) {
            sql.append(" and Type=").append(Type);
        }
        if (isdel != -1) {
            sql.append(" and isdel=").append(isdel);
        }
        if (wxsid != null && !wxsid.equals("")) {
            sql.append(" and wxsid=").append(wxsid);
        }
        sql.append(" order by id desc ");
        if (currentpage >= 0 && pagesize > 0) {
            sql.append(" limit ");
            sql.append((currentpage - 1) * pagesize);
            sql.append(",");
            sql.append(pagesize);
        }
        DataField df = this.getFirstData(sql.toString(), "totalmoney");
        totalmoney = null != df.getFieldValue("totalmoney") ? df.getFloat("totalmoney") : 0;
        return totalmoney;
    }
}
