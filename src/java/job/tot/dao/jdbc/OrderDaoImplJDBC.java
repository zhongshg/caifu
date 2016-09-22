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
public class OrderDaoImplJDBC extends AbstractDao {

    private static Log log = LogFactory.getLog(OrderDaoImplJDBC.class);

    /**
     * Creates a new instance of UserDaoImplJDBC
     */
    public OrderDaoImplJDBC() {
    }

    /*
     * insert new user
     Id,F_No,F_Date,F_Price,TSF_Price,TF_Price,SF_Price,CF_Price,UserId,UserName,Ip,Sts,Type,IsPay,F_Name,F_Address,F_Mobile,F_Tel,Demons,ShipNo,ShipName,PayType,PostType,S_Name,S_Mobile,Percent,IsInvoice,liuyan
     */
    public boolean add(String F_No, Timestamp F_Date, float F_Price, float TSF_Price, float TF_Price, float SF_Price, float CF_Price, String UserId, String SuserId, String Ip, int Sts, int State, int Cate, int Agree, int Type,
            int IsPay, String F_Name, String F_Address, String F_Mobile, String F_Tel, String Demons, String ShipNo, String ShipName, int PayType, int PostType, String S_Name, String S_Mobile,
            float Percent, int IsInv, String liuyan, Float FirstYJ, Float SecondYJ, Float ThirdYJ, String Name, String Phone, String Weixin, String Address, String Remark, int endduring, String provience, String city, String area, String confirmtimes, String sendtimes, int isdel) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "insert into t_order(F_No,F_Date,F_Price,TSF_Price,TF_Price,SF_Price,CF_Price,UserId,SuserId,Ip,Sts,State,Cate,Agree,Type,IsPay,F_Name,F_Address,F_Mobile,F_Tel,Demons,ShipNo,ShipName,PayType,PostType,S_Name,S_Mobile,Percent,IsInv,liuyan,FirstYJ,SecondYJ,ThirdYJ,Name,Phone,Weixin,Address,Remark,endduring,provience,city,area,confirmtimes,sendtimes,isdel) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, F_No);
            ps.setTimestamp(2, F_Date);
            ps.setFloat(3, F_Price);
            ps.setFloat(4, TSF_Price);
            ps.setFloat(5, TF_Price);
            ps.setFloat(6, SF_Price);
            ps.setFloat(7, CF_Price);
            ps.setString(8, UserId);
            ps.setString(9, SuserId);
            ps.setString(10, Ip);
            ps.setInt(11, Sts);
            ps.setInt(12, State);
            ps.setInt(13, Cate);
            ps.setInt(14, Agree);
            ps.setInt(15, Type);
            ps.setInt(16, IsPay);
            ps.setString(17, F_Name);
            ps.setString(18, F_Address);
            ps.setString(19, F_Mobile);
            ps.setString(20, F_Tel);
            ps.setString(21, Demons);
            ps.setString(22, ShipNo);
            ps.setString(23, ShipName);
            ps.setInt(24, PayType);
            ps.setInt(25, PostType);
            ps.setString(26, S_Name);
            ps.setString(27, S_Mobile);
            ps.setFloat(28, Percent);
            ps.setInt(29, IsInv);
            ps.setString(30, liuyan);
            ps.setFloat(31, FirstYJ);
            ps.setFloat(32, SecondYJ);
            ps.setFloat(33, ThirdYJ);
            ps.setString(34, Name);
            ps.setString(35, Phone);
            ps.setString(36, Weixin);
            ps.setString(37, Address);
            ps.setString(38, Remark);
            ps.setInt(39, endduring);
            ps.setString(40, provience);
            ps.setString(41, city);
            ps.setString(42, area);
            ps.setString(43, confirmtimes);
            ps.setString(44, sendtimes);
            ps.setInt(45, isdel);
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

    public boolean modadd(String F_No, Timestamp F_Date, float F_Price, float TSF_Price, float TF_Price, float SF_Price, float CF_Price, Float FirstYJ, Float SecondYJ, Float ThirdYJ, int Sts, String Remark) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_order set F_Price=F_Price+?,F_Date=?,TSF_Price=TSF_Price+?,TF_Price=TF_Price+?,SF_Price=SF_Price+?,CF_Price=CF_Price+?,FirstYJ=FirstYJ+?,SecondYJ=SecondYJ+?,ThirdYJ=ThirdYJ+?,Remark=Remark+? where F_No=? and Sts=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setFloat(1, F_Price);
            ps.setTimestamp(2, F_Date);
            ps.setFloat(3, TSF_Price);
            ps.setFloat(4, TF_Price);
            ps.setFloat(5, SF_Price);
            ps.setFloat(6, CF_Price);
            ps.setFloat(7, FirstYJ);
            ps.setFloat(8, SecondYJ);
            ps.setFloat(9, ThirdYJ);
            ps.setString(10, Remark + " ");
            ps.setString(11, F_No);
            ps.setInt(12, Sts);
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
        String fieldArr = "Id,F_No,F_Date,F_Price,TSF_Price,TF_Price,SF_Price,CF_Price,UserId,SuserId,Ip,Sts,State,Cate,Agree,Type,IsPay,F_Name,F_Address,F_Mobile,F_Tel,Demons,ShipNo,ShipName,ShipTime,ShipType,PayType,PostType,S_Name,S_Mobile,Percent,IsInv,liuyan,FirstYJ,SecondYJ,ThirdYJ,Name,Phone,Weixin,Address,Remark,out_trade_no,transaction_id,endduring,provience,city,area,confirmtimes,sendtimes,op";
        String sql = "select " + fieldArr + " from t_order where F_No='" + fno + "'";
        return getFirstData(sql, fieldArr);
    }

    public DataField getByout_trade_no(String out_trade_no) {
        String fieldArr = "Id,F_No,F_Date,F_Price,TSF_Price,TF_Price,SF_Price,CF_Price,UserId,SuserId,Ip,Sts,State,Cate,Agree,Type,IsPay,F_Name,F_Address,F_Mobile,F_Tel,Demons,ShipNo,ShipName,ShipTime,ShipType,PayType,PostType,S_Name,S_Mobile,Percent,IsInv,liuyan,FirstYJ,SecondYJ,ThirdYJ,Name,Phone,Weixin,Address,Remark,out_trade_no,transaction_id,endduring,provience,city,area,confirmtimes,sendtimes";
        String sql = "select " + fieldArr + " from t_order where out_trade_no='" + out_trade_no + "'";
        return getFirstData(sql, fieldArr);
    }

    public boolean modDemons(String F_No, String Demons) throws ObjectNotFoundException, DatabaseException {
        return exe("update t_order set Demons='" + Demons + "' where F_No='" + F_No + "'");
    }

    //锟斤拷锟铰斤拷锟?//修改订单状态
    public boolean modSts(String F_No, int num) throws ObjectNotFoundException, DatabaseException {
        return exe("update t_order set Sts=" + num + " where F_No='" + F_No + "'");
    }

    public boolean modConfirmtimes(String F_No, String confirmtimes) throws ObjectNotFoundException, DatabaseException {
        return exe("update t_order set confirmtimes='" + confirmtimes + "' where F_No='" + F_No + "'");
    }

    public boolean modSendtimes(String F_No, String sendtimes) throws ObjectNotFoundException, DatabaseException {
        return exe("update t_order set sendtimes='" + sendtimes + "' where F_No='" + F_No + "'");
    }

    public boolean modStsByout_trade_no(String out_trade_no, int num) throws ObjectNotFoundException, DatabaseException {
        return exe("update t_order set Sts=" + num + " where out_trade_no='" + out_trade_no + "'");
    }

    //修改交易状态
    public void modState(String fno, int num) throws ObjectNotFoundException, DatabaseException {
        exe("update t_order set State=" + num + " where F_No='" + fno + "'");
    }

    public void modStateByout_trade_no(String out_trade_no, int num) throws ObjectNotFoundException, DatabaseException {
        exe("update t_order set State=" + num + " where out_trade_no='" + out_trade_no + "'");
    }

    //讲师订单确认
    //修改退货期限
    public void modendduring(String fno, int num) throws ObjectNotFoundException, DatabaseException {
        exe("update t_order set endduring=" + num + " where F_No='" + fno + "'");
    }

    public void modout_trade_no(String fno, String out_trade_no) throws ObjectNotFoundException, DatabaseException {
        exe("update t_order set out_trade_no='" + out_trade_no + "' where F_No='" + fno + "'");
    }

    public void modtransaction_id(String transaction_id, String out_trade_no) throws ObjectNotFoundException, DatabaseException {
        exe("update t_order set transaction_id='" + transaction_id + "' where out_trade_no='" + out_trade_no + "'");
    }

    //消费记录，支付记录
    public void modStsPost(String fno, int posttype) throws ObjectNotFoundException, DatabaseException {
        exe("update t_order set PostType=" + posttype + " where F_No='" + fno + "'");
    }

    public void modPay(String fno, int num) throws ObjectNotFoundException, DatabaseException {
        exe("update t_order set IsPay=" + num + " where F_No='" + fno + "'");
    }

    public boolean modIsPay(String F_No, int num) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_order set IsPay=? where F_No=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, num);
            ps.setString(2, F_No);
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

    public boolean modIsPayByout_trade_no(String out_trade_no, int num) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_order set IsPay=? where out_trade_no=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, num);
            ps.setString(2, out_trade_no);
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

    public boolean modPayTypeByout_trade_no(String out_trade_no, int num) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_order set PayType=? where out_trade_no=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, num);
            ps.setString(2, out_trade_no);
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

    public Map<String, String> get(Connection conn, String F_No) {
        Map<String, String> map = new HashMap<String, String>();
        String sql = "select id,F_No,UserId,Sts,ShipNo,ShipName,ShipTime,sendtimes,op from t_order where F_No=?";
        try {
            PreparedStatement ptst = conn.prepareStatement(sql);
            ptst.setString(1, F_No);
            ResultSet rs = ptst.executeQuery();
            if (rs.next()) {
                map.put("id", rs.getString("id"));
                map.put("F_No", rs.getString("F_No"));
                map.put("UserId", rs.getString("UserId"));
                map.put("Sts", rs.getString("Sts"));
                map.put("ShipNo", rs.getString("ShipNo"));
                map.put("ShipName", rs.getString("ShipName"));
                map.put("ShipTime", rs.getString("ShipTime"));
                map.put("sendtimes", rs.getString("sendtimes"));
                map.put("op", rs.getString("op"));
            }
            rs.close();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDaoImplJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    public boolean modLogis(Connection conn, String F_No, String ShipNo, String ShipName, Timestamp ShipTime, int Sts, int op) {//导入物流信息
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_order set ShipNo=?,ShipName=?,ShipTime=?,Sts=?,op=? where F_No=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, ShipNo);
            ps.setString(2, ShipName);
            ps.setTimestamp(3, ShipTime);
            ps.setInt(4, Sts);
            ps.setInt(5, op);
            ps.setString(6, F_No);
            if (ps.executeUpdate() != 1) {
                returnValue = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closePrepareStatement(ps);
        }
        return returnValue;
    }

    public void modPrice(String fno, float price) throws ObjectNotFoundException, DatabaseException {
        exe("update t_order set SF_Price=" + price + " where F_No='" + fno + "'");
    }

    public void modPost(String fno, String shipno, String shipname, int ShipType, int Sts, Timestamp ShipTime) throws ObjectNotFoundException, DatabaseException {
        exe("update t_order set ShipNo='" + shipno + "',ShipName='" + shipname + "',ShipType=" + ShipType + ",Sts=" + Sts + ",ShipTime='" + ShipTime + "' where F_No='" + fno + "'");
    }

    public boolean modOp(String F_No, int op) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_order set op=? where F_No=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, op);
            ps.setString(2, F_No);
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

    public Collection getList(int sts, int State, int Cate, int Agree, String fno, String userid, String suserid, int pays, int posttype, int currentpage, int pagesize) {
        StringBuffer sql = new StringBuffer(512);
        String order = "UserId";
        String sort = " asc";
        String fieldArr = "Id,F_No,F_Date,F_Price,TSF_Price,TF_Price,SF_Price,CF_Price,UserId,SuserId,Ip,Sts,State,Cate,Agree,Type,IsPay,F_Name,F_Address,F_Mobile,F_Tel,Demons,ShipNo,ShipName,ShipTime,ShipType,PayType,PostType,S_Name,S_Mobile,Percent,IsInv,liuyan,FirstYJ,SecondYJ,ThirdYJ,Name,Phone,Weixin,Address,Remark,out_trade_no,transaction_id,endduring,provience,city,area,confirmtimes,sendtimes";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_order");
        sql.append(" where 1=1");
        if (sts > 0) {
            sql.append(" and Sts=").append(sts);
        }
        if (fno != null && !fno.equals("")) {
            sql.append(" and F_No='").append(fno).append("'");
        }
        if (userid != null && !userid.equals("") && !userid.equals("null")) {
            sql.append(" and UserId='").append(userid).append("'");
        }
        if (suserid != null && !suserid.equals("") && !suserid.equals("null")) {
            sql.append(" and SuserId='").append(suserid).append("'");
        }
        if (pays > 0) {
            sql.append(" and IsPay=").append(pays);
        }
        if (posttype > 0) {
            sql.append(" and PostType=").append(posttype);
        }
        if (State > 0) {
            sql.append(" and State=").append(State);
        }
        if (Cate > 0) {
            sql.append(" and Cate=").append(Cate);
        }
        if (Agree > 0) {
            sql.append(" and Agree=").append(Agree);
        }

        sql.append(" order by id desc ");
        if (currentpage > 0 && pagesize > 0) {
            sql.append("limit ");
            sql.append((currentpage - 1) * pagesize);
            sql.append(",");
            sql.append(pagesize);
        }
        return this.getData(sql.toString(), fieldArr);
    }

    public int getTotalNum(int sts, int State, int Cate, int Agree, String fno, String userid, String suserid, int pays, int posttype) {
        int returnValue = 0;
        StringBuffer sql = new StringBuffer(512);
        sql.append("select count(*) from t_order");
        sql.append(" where 1=1");
        if (sts > 0) {
            sql.append(" and Sts=").append(sts);
        }
        if (fno != null && !fno.equals("")) {
            sql.append(" and F_No='").append(fno).append("'");
        }
        if (userid != null && !userid.equals("") && !userid.equals("null")) {
            sql.append(" and UserId='").append(userid).append("'");
        }
        if (suserid != null && !suserid.equals("") && !suserid.equals("null")) {
            sql.append(" and SuserId='").append(suserid).append("'");
        }
        if (pays > 0) {
            sql.append(" and IsPay=").append(pays);
        }
        if (posttype > 0) {
            sql.append(" and PostType=").append(posttype);
        }
        if (State > 0) {
            sql.append(" and State=").append(State);
        }
        if (Cate > 0) {
            sql.append(" and Cate=").append(Cate);
        }
        if (Agree > 0) {
            sql.append(" and Agree=").append(Agree);
        }

        returnValue = getDataCount(sql.toString());
        return returnValue;
    }

    public int getTotalNum(String SuserId, int Sts, String key, String value, int isdel, String fromdate, String todate) {
        int returnValue = 0;
        StringBuffer sql = new StringBuffer(512);
        sql.append("select count(*) from t_order");
        sql.append(" where 1=1");
        if (fromdate != null && !fromdate.equals("") && todate != null && !todate.equals("")) {
            sql.append(" and to_days(F_Date)>=to_days('").append(fromdate).append("') and to_days(F_Date)<=to_days('").append(todate).append("')");
        }
        if (SuserId != null && !SuserId.equals("") && !SuserId.equals("null")) {
            sql.append(" and SuserId='").append(SuserId).append("'");
        }
        if (-1 != Sts) {
            sql.append(" and Sts=").append(Sts);
        }
        if (value != null && !value.equals("")) {
            sql.append(" and " + key + "=" + value);
        }
        if (isdel != -1) {
            sql.append(" and isdel=").append(isdel);
        }
        returnValue = getDataCount(sql.toString());
        return returnValue;
    }

    public Collection getList(String SuserId, String UserId, int Sts, Connection conn) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "Id,F_No,F_Date,F_Price,TSF_Price,TF_Price,SF_Price,CF_Price,UserId,SuserId,Ip,Sts,State,Cate,Agree,Type,IsPay,F_Name,F_Address,F_Mobile,F_Tel,Demons,ShipNo,ShipName,ShipTime,ShipType,PayType,PostType,S_Name,S_Mobile,Percent,IsInv,liuyan,FirstYJ,SecondYJ,ThirdYJ,Name,Phone,Weixin,Address,Remark,out_trade_no,transaction_id,endduring,provience,city,area,confirmtimes,sendtimes";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_order");
        sql.append(" where SuserId='" + SuserId + "' and UserId='" + UserId + "'");
        if (-1 != Sts) {
            sql.append(" and Sts=").append(Sts);
        }
        sql.append(" order by Sts asc");
        return this.getData(sql.toString(), fieldArr, conn);
    }

    public Collection getList(String SuserId, String UserId, int Sts, int isdel) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "Id,F_No,F_Date,F_Price,TSF_Price,TF_Price,SF_Price,CF_Price,UserId,SuserId,Ip,Sts,State,Cate,Agree,Type,IsPay,F_Name,F_Address,F_Mobile,F_Tel,Demons,ShipNo,ShipName,ShipTime,ShipType,PayType,PostType,S_Name,S_Mobile,Percent,IsInv,liuyan,FirstYJ,SecondYJ,ThirdYJ,Name,Phone,Weixin,Address,Remark,out_trade_no,transaction_id,endduring,provience,city,area,confirmtimes,sendtimes";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_order");
        sql.append(" where SuserId='" + SuserId + "' and UserId='" + UserId + "'");
        if (-1 != Sts) {
            sql.append(" and Sts=").append(Sts);
        }
        if (-1 != isdel) {
            sql.append(" and isdel=").append(isdel);
        }
        sql.append(" order by Id desc");
        return this.getData(sql.toString(), fieldArr);
    }

    public Collection getListSE(String SuserId, String UserId, int Sts, String starttime, String endtime, Connection conn) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "Id,F_No,F_Date,F_Price,TSF_Price,TF_Price,SF_Price,CF_Price,UserId,SuserId,Ip,Sts,State,Cate,Agree,Type,IsPay,F_Name,F_Address,F_Mobile,F_Tel,Demons,ShipNo,ShipName,ShipTime,ShipType,PayType,PostType,S_Name,S_Mobile,Percent,IsInv,liuyan,FirstYJ,SecondYJ,ThirdYJ,Name,Phone,Weixin,Address,Remark,out_trade_no,transaction_id,endduring,provience,city,area,confirmtimes,sendtimes";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_order");
        sql.append(" where SuserId='" + SuserId + "' and UserId='" + UserId + "'");
        if (starttime != null && !starttime.equals("")) {
            sql.append(" and to_days(F_Date)>=to_days('").append(starttime).append("')");
        }
        if (endtime != null && !endtime.equals("")) {
            sql.append(" and to_days(F_Date)<=to_days('").append(endtime).append("')");
        }
        if (-1 != Sts) {
            sql.append(" and Sts=").append(Sts);
        }
        sql.append(" order by Sts asc");
        return this.getData(sql.toString(), fieldArr, conn);
    }

    public List<DataField> getAutoList(String SuserId, int oldSts, String limitkey, String limitvalue) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "Id,F_No,F_Date,F_Price,TSF_Price,TF_Price,SF_Price,CF_Price,UserId,SuserId,Ip,Sts,State,Cate,Agree,Type,IsPay,F_Name,F_Address,F_Mobile,F_Tel,Demons,ShipNo,ShipName,ShipTime,ShipType,PayType,PostType,S_Name,S_Mobile,Percent,IsInv,liuyan,FirstYJ,SecondYJ,ThirdYJ,Name,Phone,Weixin,Address,Remark,out_trade_no,transaction_id,endduring,provience,city,area,confirmtimes,sendtimes";
        sql.append("select " + fieldArr + " from t_order");
        sql.append(" where SuserId='" + SuserId + "'");
        sql.append(" and timestampdiff(second, " + limitkey + ",now())>").append(limitvalue);
        if (-1 != oldSts) {
            sql.append(" and Sts=").append(oldSts);
        }
        List<DataField> orderList = (List<DataField>) this.getData(sql.toString(), fieldArr);

        return orderList;
    }

    public boolean modStsAuto(String SuserId, int oldSts, int newSts, String limitkey, String limitvalue, String key, String value) {
        boolean flag = false;
        try {
            StringBuffer sql = new StringBuffer(512);
//            String fieldArr = "Id,F_No,F_Date,F_Price,TSF_Price,TF_Price,SF_Price,CF_Price,UserId,SuserId,Ip,Sts,State,Cate,Agree,Type,IsPay,F_Name,F_Address,F_Mobile,F_Tel,Demons,ShipNo,ShipName,ShipTime,ShipType,PayType,PostType,S_Name,S_Mobile,Percent,IsInv,liuyan,FirstYJ,SecondYJ,ThirdYJ,Name,Phone,Weixin,Address,Remark,out_trade_no,transaction_id,endduring,provience,city,area,confirmtimes,sendtimes";
            sql.append("update t_order set Sts=" + newSts);
            if (null != key && null != value) {
                sql.append("," + key + "=").append(value);
            }
            sql.append(" where SuserId='" + SuserId + "'");
            sql.append(" and timestampdiff(second, " + limitkey + ",now())>").append(limitvalue);
            if (-1 != oldSts) {
                sql.append(" and Sts=").append(oldSts);
            }
            flag = exe(sql.toString());
        } catch (ObjectNotFoundException ex) {
            Logger.getLogger(OrderDaoImplJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(OrderDaoImplJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public Collection getShoudanList(int Sts) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "Id,F_No,F_Date,F_Price,TSF_Price,TF_Price,SF_Price,CF_Price,UserId,SuserId,Ip,Sts,State,Cate,Agree,Type,IsPay,F_Name,F_Address,F_Mobile,F_Tel,Demons,ShipNo,ShipName,ShipTime,ShipType,PayType,PostType,S_Name,S_Mobile,Percent,IsInv,liuyan,FirstYJ,SecondYJ,ThirdYJ,Name,Phone,Weixin,Address,Remark,out_trade_no,transaction_id,endduring,provience,city,area,confirmtimes,sendtimes";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_order where 1=1");
        if (-1 != Sts) {
            sql.append(" and Sts=").append(Sts);
        }
        sql.append(" group by UserId order by Id asc");
        return this.getData(sql.toString(), fieldArr);
    }

    public Collection getShoudanList(String SuserId, int Sts) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "Id,F_No,F_Date,F_Price,TSF_Price,TF_Price,SF_Price,CF_Price,UserId,SuserId,Ip,Sts,State,Cate,Agree,Type,IsPay,F_Name,F_Address,F_Mobile,F_Tel,Demons,ShipNo,ShipName,ShipTime,ShipType,PayType,PostType,S_Name,S_Mobile,Percent,IsInv,liuyan,FirstYJ,SecondYJ,ThirdYJ,Name,Phone,Weixin,Address,Remark,out_trade_no,transaction_id,endduring,provience,city,area,confirmtimes,sendtimes";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_order where SuserId='" + SuserId + "'");
        if (-1 != Sts) {
            sql.append(" and Sts=").append(Sts);
        }
        sql.append(" group by UserId order by Id asc");
        return this.getData(sql.toString(), fieldArr);
    }

    public int getTotalNum(String SuserId, String UserId, int IsPay, int Sts) {
        int returnValue = 0;
        StringBuffer sql = new StringBuffer(512);
        sql.append("select count(*) from t_order where SuserId='" + SuserId + "' and UserId='" + UserId + "' ");
        if (Sts > 0) {
            sql.append(" and Sts=").append(Sts);
        }
        if (IsPay > 0) {
            sql.append(" and IsPay=").append(IsPay);
        }
        returnValue = getDataCount(sql.toString());
        return returnValue;
    }

    public Collection get(String suserid, int Sts, String key, String value, String fromdate, String todate, int isdel, int currentpage, int pagesize) {
        String fieldArr = "Id,F_No,F_Date,F_Price,TSF_Price,TF_Price,SF_Price,CF_Price,UserId,SuserId,Ip,Sts,State,Cate,Agree,Type,IsPay,F_Name,F_Address,F_Mobile,F_Tel,Demons,ShipNo,ShipName,ShipTime,ShipType,PayType,PostType,S_Name,S_Mobile,Percent,IsInv,liuyan,FirstYJ,SecondYJ,ThirdYJ,Name,Phone,Weixin,Address,Remark,out_trade_no,transaction_id,endduring,provience,city,area,confirmtimes,sendtimes,op";
        String sql = "select " + fieldArr + " from t_order where 1=1";
        if (fromdate != null && !fromdate.equals("")) {
            sql += (" and to_days(F_Date)>=to_days('" + fromdate + "')");
        }
        if (todate != null && !todate.equals("")) {
            sql += (" and to_days(F_Date)<=to_days('" + todate + "')");
        }
        if (suserid != null && !suserid.equals("") && !suserid.equals("null")) {
            sql += (" and SuserId='") + (suserid) + ("'");
        }
        if (-1 != Sts) {
            sql += " and Sts=" + Sts;
        }
        if (value != null && !value.equals("")) {
            sql += " and  " + key + "=" + value;
        }
        sql += " and isdel=0 order by id desc";
        if (0 != currentpage && 0 != pagesize) {
            sql += (" limit ");
            sql += ((currentpage - 1) * pagesize);
            sql += (",");
            sql += (pagesize);
        }
        return getData(sql, fieldArr);
    }

    public int getTodayNum(String SuserId) {
        int returnValue = 0;
        StringBuffer sql = new StringBuffer(512);
        sql.append("select count(*) from t_order where to_days(F_Date) = to_days(now())");
        returnValue = getDataCount(sql.toString());
        return returnValue;
    }

    public float getTotalMoney(String SuserId, String UserId) {
        float returnValue = 0;
        StringBuffer sql = new StringBuffer(512);
        sql.append("select sum(F_Price) sums from t_order where 1=1");
        if (UserId != null && !UserId.equals("") && !UserId.equals("null")) {
            sql.append(" and UserId='").append(UserId).append("'");
        }
        if (SuserId != null && !SuserId.equals("") && !SuserId.equals("null")) {
            sql.append(" and SuserId='").append(SuserId).append("'");
        }
        returnValue = getFirstData(sql.toString(), "sums").getFloat("sums");
        return returnValue;
    }

//以时间 为条件进行订单查询，计算规定时间内所有订单金额
    public Collection getListDate(int sts, int State, int Cate, int Agree, String fno, String userid, String suserid, int pays, int posttype, String fromdate, String todate, String orderlatertime, String confirmlatertime, String sendlatertime, int isdel, int currentpage, int pagesize) {
        StringBuffer sql = new StringBuffer(512);
        String order = "UserId";
        String sort = " asc";
        String fieldArr = "Id,F_No,F_Date,F_Price,TSF_Price,TF_Price,SF_Price,CF_Price,UserId,SuserId,Ip,Sts,State,Cate,Agree,Type,IsPay,F_Name,F_Address,F_Mobile,F_Tel,Demons,ShipNo,ShipName,ShipTime,ShipType,PayType,PostType,S_Name,S_Mobile,Percent,IsInv,liuyan,FirstYJ,SecondYJ,ThirdYJ,Name,Phone,Weixin,Address,Remark,out_trade_no,transaction_id,endduring,provience,city,area,confirmtimes,sendtimes";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_order");
        sql.append(" where 1=1");
        if (fromdate != null && !fromdate.equals("")) {
            sql.append(" and to_days(F_Date)>=to_days('").append(fromdate).append("')");

        }
        if (todate != null && !todate.equals("")) {
            sql.append(" and to_days(F_Date)<=to_days('").append(todate).append("')");

        }
        if (orderlatertime != null && !orderlatertime.equals("")) {
            sql.append(" and to_days(now())-to_days(F_Date)>=").append(orderlatertime);
        }

        if (confirmlatertime != null && !confirmlatertime.equals("")) {
            sql.append(" and to_days(now())-to_days(confirmtimes)>=").append(confirmlatertime);
        }

        if (sendlatertime != null && !sendlatertime.equals("")) {
            sql.append(" and to_days(now())-to_days(sendtimes)>=").append(sendlatertime);
        }
        if (sts > 0) {
            sql.append(" and Sts=").append(sts);
        }
        if (fno != null && !fno.equals("")) {
            sql.append(" and F_No='").append(fno).append("'");
        }
        if (userid != null && !userid.equals("") && !userid.equals("null")) {
            sql.append(" and UserId='").append(userid).append("'");
        }
        if (suserid != null && !suserid.equals("") && !suserid.equals("null")) {
            sql.append(" and SuserId='").append(suserid).append("'");
        }
        if (pays > 0) {
            sql.append(" and IsPay=").append(pays);
        }
        if (posttype > 0) {
            sql.append(" and PostType=").append(posttype);
        }
        if (State > 0) {
            sql.append(" and State=").append(State);
        }
//        if (Cate > 0) {
//            sql.append(" and Cate=").append(Cate);
//        }
        if (Agree > 0) {
            sql.append(" and Agree=").append(Agree);
        }
        if (isdel != -1) {
            sql.append(" and isdel=").append(isdel);
        }
        sql.append(" order by id desc");
        if (0 != currentpage && 0 != pagesize) {
            sql.append(" limit ");
            sql.append((currentpage - 1) * pagesize);
            sql.append(",");
            sql.append(pagesize);
        }
        return this.getData(sql.toString(), fieldArr);
    }

    //
    public int getTotalNumDate(int sts, int State, int Cate, int Agree, String fno, String userid, String suserid, int pays, int posttype, String fromdate, String todate, String orderlatertime, String confirmlatertime, String sendlatertime, int isdel) {
        int returnValue = 0;
        StringBuffer sql = new StringBuffer(512);
        sql.append("select count(*) from t_order");
        sql.append(" where 1=1");
        if (fromdate != null && !fromdate.equals("") && todate != null && !todate.equals("")) {
            sql.append(" and to_days(F_Date)>=to_days('").append(fromdate).append("') and to_days(F_Date)<=to_days('").append(todate).append("')");
        }

        if (orderlatertime != null && !orderlatertime.equals("")) {
            sql.append(" and to_days(now())-to_days(F_Date)>=").append(orderlatertime);
        }

        if (confirmlatertime != null && !confirmlatertime.equals("")) {
            sql.append(" and to_days(now())-to_days(confirmtimes)>=").append(confirmlatertime);
        }

        if (sendlatertime != null && !sendlatertime.equals("")) {
            sql.append(" and to_days(now())-to_days(sendtimes)>=").append(sendlatertime);
        }
        if (sts > 0) {
            sql.append(" and Sts=").append(sts);
        }
        if (fno != null && !fno.equals("")) {
            sql.append(" and F_No='").append(fno).append("'");
        }
        if (userid != null && !userid.equals("") && !userid.equals("null")) {
            sql.append(" and UserId='").append(userid).append("'");
        }
        if (suserid != null && !suserid.equals("") && !suserid.equals("null")) {
            sql.append(" and SuserId='").append(suserid).append("'");
        }
        if (pays > 0) {
            sql.append(" and IsPay=").append(pays);
        }
        if (posttype > 0) {
            sql.append(" and PostType=").append(posttype);
        }
        if (State > 0) {
            sql.append(" and State=").append(State);
        }
        if (Cate > 0) {
            sql.append(" and Cate=").append(Cate);
        }
        if (Agree > 0) {
            sql.append(" and Agree=").append(Agree);
        }
        if (isdel != -1) {
            sql.append(" and isdel=").append(isdel);
        }
        returnValue = getDataCount(sql.toString());
        return returnValue;
    }

    public void del(String[] s) {
        this.bat_String("delete from t_order where F_No=?", s);
    }

    public void modisdel(String[] s) {
        this.bat_String("update t_order set isdel=1 where F_No=?", s);
    }

    //特殊,删除用户特例
    public void delete(String SuserId, String UserId, Connection conn) {
        PreparedStatement ptst = null;
        String sql = "delete from t_order where SuserId='" + SuserId + "' and UserId='" + UserId + "'";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.executeUpdate();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDaoImplJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //优化区
    //订单总额
    public float getordermoney(String SuserId, String UserId, int Sts, String starttime, String endtime) {
        float ordermoney = 0;
        StringBuffer sql = new StringBuffer(512);
        sql.append("select");
        sql.append(" sum(F_Price) ordermoney");
        sql.append(" from t_order");
        sql.append(" where SuserId='" + SuserId + "' and UserId='" + UserId + "'");
        if (starttime != null && !starttime.equals("")) {
            sql.append(" and to_days(F_Date)>=to_days('").append(starttime).append("')");
        }
        if (endtime != null && !endtime.equals("")) {
            sql.append(" and to_days(F_Date)<=to_days('").append(endtime).append("')");
        }
        if (-1 != Sts) {
            sql.append(" and Sts=").append(Sts);
        }
        DataField ordermoneyDF = this.getFirstData(sql.toString(), "ordermoney");
        ordermoney = null != ordermoneyDF.getFieldValue("ordermoney") ? ordermoneyDF.getFloat("ordermoney") : 0;
        return ordermoney;
    }
    //下级购买数

    public int getdownordercount(String SuserId, String UserId, int Sts, String starttime, String endtime) {
        int downordercount = 0;
        StringBuffer sql = new StringBuffer(512);
        sql.append("select");
        sql.append(" count(id) downordercount");
        sql.append(" from t_order");
        sql.append(" where SuserId='" + SuserId + "' and UserId='" + UserId + "'");
        if (starttime != null && !starttime.equals("")) {
            sql.append(" and to_days(F_Date)>=to_days('").append(starttime).append("')");
        }
        if (endtime != null && !endtime.equals("")) {
            sql.append(" and to_days(F_Date)<=to_days('").append(endtime).append("')");
        }
        if (-1 != Sts) {
            sql.append(" and Sts=").append(Sts);
        }
        DataField downordercountDF = this.getFirstData(sql.toString(), "downordercount");
        downordercount = null != downordercountDF.getFieldValue("downordercount") ? downordercountDF.getInt("downordercount") : 0;
        return downordercount;
    }
    //销售额

    public float getsalemoney(String SuserId, String UserId, int Sts, String starttime, String endtime, String otherCondition) {
        float salemoney = 0;
        StringBuffer sql = new StringBuffer(512);
        sql.append("select");
        sql.append(" sum(F_Price) salemoney");
        sql.append(" from t_order");
        sql.append(" where 1=1");
        sql.append(otherCondition);
        if (starttime != null && !starttime.equals("")) {
            sql.append(" and to_days(F_Date)>=to_days('").append(starttime).append("')");
        }
        if (endtime != null && !endtime.equals("")) {
            sql.append(" and to_days(F_Date)<=to_days('").append(endtime).append("')");
        }
        if (-1 != Sts) {
            sql.append(" and Sts=").append(Sts);
        }
        sql.append("and SuserId='" + SuserId + "' and UserId='" + UserId + "'");
        DataField salemoneyDF = this.getFirstData(sql.toString(), "salemoney");
        salemoney = null != salemoneyDF.getFieldValue("salemoney") ? salemoneyDF.getFloat("salemoney") : 0;
        return salemoney;
    }
    //佣金

    public DataField getyj(String SuserId, String UserId, int Sts, String starttime, String endtime, String otherCondition) {
        StringBuffer sql = new StringBuffer(512);
        sql.append("select");
        sql.append(" sum(FirstYJ) FirstYJ,sum(SecondYJ) SecondYJ,sum(ThirdYJ) ThirdYJ");
        sql.append(" from t_order");
        sql.append(" where SuserId='" + SuserId + "' and UserId='" + UserId + "'");
        sql.append(otherCondition);
        if (starttime != null && !starttime.equals("")) {
            sql.append(" and to_days(F_Date)>=to_days('").append(starttime).append("')");
        }
        if (endtime != null && !endtime.equals("")) {
            sql.append(" and to_days(F_Date)<=to_days('").append(endtime).append("')");
        }
        if (-1 != Sts) {
            sql.append(" and Sts=").append(Sts);
        }
        DataField yjDF = this.getFirstData(sql.toString(), "FirstYJ,SecondYJ,ThirdYJ");
        if (null == yjDF.getFieldValue("FirstYJ")) {
            yjDF.setField("FirstYJ", "0", 1);
        }
        if (null == yjDF.getFieldValue("SecondYJ")) {
            yjDF.setField("SecondYJ", "0", 1);
        }
        if (null == yjDF.getFieldValue("ThirdYJ")) {
            yjDF.setField("ThirdYJ", "0", 1);
        }
        return yjDF;
    }
    //优化区结束
}
