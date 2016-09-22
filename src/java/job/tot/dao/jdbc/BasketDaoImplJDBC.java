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
public class BasketDaoImplJDBC extends AbstractDao {

    private static Log log = LogFactory.getLog(BasketDaoImplJDBC.class);

    /**
     * Creates a new instance of UserDaoImplJDBC
     */
    public BasketDaoImplJDBC() {
    }

    /*
     * insert new user
     * 含义变更
     * 单件商品原价Bas_Price
     * 单件商品优惠价Sale_Price
     * 总计原价Per_Price
     * 总计优惠价Tot_Price
     */
    public boolean add(int Pid, String Pname, String Uid, String SuserId, String Ip, String F_No, String Pcode, int Pnum, int Sts, Timestamp AddTime, float Bas_Price, float Sale_Price, float Per_Price, float Tot_Price, String ViewImg, String Name, String Phone, String Weixin, String Address, String Remark, Float FirstYJ, Float SecondYJ, Float ThirdYJ, String propertys) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "insert into t_basket(Pid,Pname,Uid,SuserId,Ip,F_No,Pcode,Pnum,Sts,AddTime,Bas_Price,Sale_Price,Per_Price,Tot_Price,ViewImg,Name,Phone,Weixin,Address,Remark,FirstYJ,SecondYJ,ThirdYJ,propertys) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, Pid);
            ps.setString(2, Pname);
            ps.setString(3, Uid);
            ps.setString(4, SuserId);
            ps.setString(5, Ip);
            ps.setString(6, F_No);
            ps.setString(7, Pcode);
            ps.setInt(8, Pnum);
            ps.setInt(9, Sts);
            ps.setTimestamp(10, AddTime);
            ps.setFloat(11, Bas_Price);
            ps.setFloat(12, Sale_Price);
            ps.setFloat(13, Per_Price);
            ps.setFloat(14, Tot_Price);
            ps.setString(15, ViewImg);
            ps.setString(16, Name);
            ps.setString(17, Phone);
            ps.setString(18, Weixin);
            ps.setString(19, Address);
            ps.setString(20, Remark);
            ps.setFloat(21, FirstYJ);
            ps.setFloat(22, SecondYJ);
            ps.setFloat(23, ThirdYJ);
            ps.setString(24, propertys);
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

    public DataField get(String fno) {
        String fieldArr = "id,Pid,Pname,Uid,SuserId,Ip,F_No,Pcode,Pnum,Psku,Sts,AddTime,Bas_Price,Sale_Price,Percent,Activity_Id,Per_Price,Tot_Price,ViewImg,PJ,Cate,Day";
        String sql = "select " + fieldArr + " from t_basket where F_No='" + fno + "'";
        return getFirstData(sql, fieldArr);
    }

    //查找未形成订单的购物车产品
    public DataField get(int pid, String uid) {
        String fieldArr = "id,Pid,Pname,Uid,SuserId,Ip,F_No,Pcode,Pnum,Psku,Sts,AddTime,Bas_Price,Sale_Price,Percent,Activity_Id,Per_Price,Tot_Price,ViewImg,Pj,Cate,Day";
        String sql = "select " + fieldArr + " from t_basket where Uid='" + uid + "' and Pid=" + pid + " and Sts=0";
        return getFirstData(sql, fieldArr);
    }

    //根据ID查找订单
    public DataField getId(int id) {
        String fieldArr = "id,Pid,Pname,Uid,SuserId,Ip,F_No,Pcode,Pnum,Psku,Sts,AddTime,Bas_Price,Sale_Price,Percent,Activity_Id,Per_Price,Tot_Price,ViewImg,Pj,Cate,Day,Name,Phone,Weixin,Address,Remark,FirstYJ,SecondYJ,ThirdYJ,propertys";
        String sql = "select " + fieldArr + " from t_basket where id=" + id;

        return getFirstData(sql, fieldArr);
    }

    //根据ID查询购物车中选中的产品在前台订单中显示
    public DataField getFNoId(int id) {
        String fieldArr = "id,Pid,Pname,Uid,Ip,F_No,Pcode,Pnum,Psku,Sts,AddTime,Bas_Price,Sale_Price,Percent,Activity_Id,Per_Price,Tot_Price,ViewImg,PJ,Cate,Day,Descr,propertys";
        String sql = "select t_basket.id,t_basket.Pid,t_basket.Pname,t_basket.Uid,t_basket.Ip,t_basket.F_No,t_basket.Pcode,t_basket.Pnum,t_basket.Psku,t_basket.Sts,t_basket.AddTime,t_basket.Bas_Price,t_basket.Sale_Price,t_basket.Percent,t_basket.Activity_Id,t_basket.Per_Price,t_basket.Tot_Price,t_basket.ViewImg,t_basket.PJ,t_basket.Cate,t_basket.Day,t_product.Descr,t_basket.propertys from t_basket,t_product where t_product.id=t_basket.Pid and t_basket.id=" + id;
        return getFirstData(sql, fieldArr);
    }

    //根据SuserId,Uid查询购物车中选中的产品在前台订单中显示(order.jsp sts=0  商品为未形成订单的产品 )//单个产品暂时未使用
    public DataField getFNoSuserId(String SuserId, String Uid, int sts) {
        String fieldArr = "id,SuserId,Pid,Pname,Uid,Ip,F_No,Pcode,Pnum,Psku,Sts,AddTime,Bas_Price,Sale_Price,Percent,Activity_Id,Per_Price,Tot_Price,ViewImg,PJ,Cate,Day,Descr,propertys";
        String sql = "select t_basket.id,t_basket.SuserId,t_basket.Pid,t_basket.Pname,t_basket.Uid,t_basket.Ip,t_basket.F_No,t_basket.Pcode,t_basket.Pnum,t_basket.Psku,t_basket.Sts,t_basket.AddTime,t_basket.Bas_Price,t_basket.Sale_Price,t_basket.Percent,t_basket.Activity_Id,t_basket.Per_Price,t_basket.Tot_Price,t_basket.ViewImg,t_basket.PJ,t_basket.Cate,t_basket.Day,t_product.Descr,t_basket.propertys from t_basket,t_product where t_product.id=t_basket.Pid and t_basket.sts=" + sts + " and t_basket.SuserId='" + SuserId + "',t_basket.Uid='" + Uid + "'";
        return getFirstData(sql, fieldArr);
    }

//通过用户查找订单
    public DataField getuid(int sts, String uid) {
        String fieldArr = "id,Pid,Pname,Uid,Ip,F_No,Pcode,Pnum,Psku,Sts,AddTime,Bas_Price,Sale_Price,Percent,Activity_Id,Per_Price,Tot_Price,ViewImg,PJ,Cate,Day,propertys";
        String sql = "select " + fieldArr + " from t_basket where Uid='" + uid + "' and Sts=" + sts;
        return getFirstData(sql, fieldArr);
    }
//修改是否形成订单

    public void modSts(String fno, int num) throws ObjectNotFoundException, DatabaseException {
        exe("update t_basket set Sts=Sts+" + num + " where F_No='" + fno + "'");
    }
//修改产品数量

    public void modNum(int id, int num) throws ObjectNotFoundException, DatabaseException {
        exe("update t_basket set Pnum=" + num + " where id=" + id);
    }

    public void modNum(int id, int pid, String uid, int num) throws ObjectNotFoundException, DatabaseException {
        exe("update t_basket set Pnum=Pnum+" + num + " where uid='" + uid + "' and id=" + id + " and pid=" + pid);
    }

    public void modPJ(int id, int pid, int num) throws ObjectNotFoundException, DatabaseException {
        exe("update t_basket set PJ=" + num + " where id=" + id + " and pid=" + pid);
    }

    public boolean mod(int pid, String uid, int num) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_basket set pnum=pnum+" + num + " where uid='" + uid + "' and pid=" + pid + " and sts=0 ";
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

    public boolean modFno(int id, String F_No) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_basket set F_No='" + F_No + "' where id=" + id;
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }
    //订单生成后 根据用户Uid 和商家帐号  将购物车中的产品更改为订单中的商品

    public boolean modUidFno(String F_No, String SuserId, String Uid) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_basket set F_No='" + F_No + "',Sts=1,BiaoZhi=3 where SuserId='" + SuserId + "' and Uid='" + Uid + "' and Sts=0 ";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }

    public boolean modWxUidId(String Uid, String SuserId, String id, int Sts) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_basket set Sts=" + Sts + " where SuserId='" + SuserId + "' and Uid='" + Uid + "' and id=" + id;
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }

    public void del(int id, String uid) throws ObjectNotFoundException, DatabaseException {
        exe("delete from t_basket where id=" + id + " and Uid='" + uid + "'");
    }

    //完整购物车中搜索产品，表中含有商品基本信息，以用户帐号为搜索条件。
    public Collection getListProduct(String uid) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,Pid,Pname,Uid,Ip,F_No,Pcode,Pnum,Psku,Sts,AddTime,Bas_Price,Sale_Price,Percent,Activity_Id,Per_Price,Tot_Price,ViewImg,PJ,Cate,Day";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_basket");
        sql.append(" where Sts=0 and Uid='");
        sql.append(uid);
        sql.append("'");
        sql.append(" order by id desc");
        return this.getData(sql.toString(), fieldArr);
    }

    //完整购物车中搜索产品，表中含有商品基本信息，以订单号为搜索条件
    public Collection getListNo(String orderno) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,Pid,Pname,Uid,Ip,F_No,Pcode,Pnum,Psku,Sts,AddTime,Bas_Price,Sale_Price,Percent,Activity_Id,Per_Price,Tot_Price,ViewImg,PJ,Cate,Day,propertys";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_basket");
        sql.append(" where F_No='");
        sql.append(orderno);
        sql.append("'");
        sql.append(" order by id desc");
        return this.getData(sql.toString(), fieldArr);
    }

    public DataField getBySuserIdUidAddress(String SuserId, String Uid, String Name, String Phone, String Weixin, String Address, String Remark, int Sts) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,Pid,Pname,Uid,Ip,F_No,Pcode,Pnum,Psku,Sts,AddTime,Bas_Price,Sale_Price,Percent,Activity_Id,Per_Price,Tot_Price,ViewImg,PJ,Cate,Day,Name,Phone,Weixin,Address,Remark,FirstYJ,SecondYJ,ThirdYJ,propertys";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_basket where SuserId='" + SuserId + "' and Uid='" + Uid + "' ");
        sql.append(" and Name='");
        sql.append(Name);
        sql.append("'");

        sql.append(" and Phone='");
        sql.append(Phone);
        sql.append("'");
        sql.append(" and Weixin='");
        sql.append(Weixin);
        sql.append("'");
        sql.append(" and Address='");
        sql.append(Address);
        sql.append("'");
        sql.append(" and Remark='");
        sql.append(Remark);
        sql.append("'");
        sql.append(" and Sts=");
        sql.append(Sts);
        sql.append(" order by id desc");
        return this.getFirstData(sql.toString(), fieldArr);
    }

    public Collection getBySuserIdUidNoList(String SuserId, String Uid, String orderno) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,Pid,Pname,Uid,Ip,F_No,Pcode,Pnum,Psku,Sts,AddTime,Bas_Price,Sale_Price,Percent,Activity_Id,Per_Price,Tot_Price,ViewImg,PJ,Cate,Day,Name,Phone,Weixin,Address,Remark,propertys";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_basket where SuserId='" + SuserId + "' and Uid='" + Uid + "' ");
        sql.append(" and F_No='");
        sql.append(orderno);
        sql.append("'");
        sql.append(" order by id desc");
        return this.getData(sql.toString(), fieldArr);
    }
//订单中搜索

    public Collection getListByOrderNo(String orderno) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,Pid,Pname,Uid,SuserId,Ip,F_No,Pcode,Pnum,Psku,Sts,AddTime,Bas_Price,Sale_Price,Percent,Activity_Id,Per_Price,Tot_Price,ViewImg,PJ,Cate,Day,Descr,propertys";
        sql.append("select  t_basket.id,t_basket.Pid,t_basket.Pname,t_basket.Uid,t_basket.SuserId,t_basket.Ip,t_basket.F_No,t_basket.Pcode,t_basket.Pnum,t_basket.Psku,t_basket.Sts,t_basket.AddTime,t_basket.Bas_Price,t_basket.Sale_Price,t_basket.Percent,t_basket.Activity_Id,t_basket.Per_Price,t_basket.Tot_Price,t_basket.ViewImg,t_basket.PJ,t_basket.Cate,t_basket.Day,t_product.Descr,t_basket.propertys from t_basket,t_product");
        sql.append(" where t_product.id=t_basket.Pid and t_basket.Sts=2 and t_basket.F_No='");
        sql.append(orderno);
        sql.append("'");
        sql.append(" order by t_basket.id desc");
        return this.getData(sql.toString(), fieldArr);
    }
    

    //基于上面的方法修改而来的订单查询方法，新增了产品编号字段ProCode
    //add_zhongshg 2016年9月12日 15:51:13
    public Collection getListByOrderNoBak(String orderno) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,Pid,Pname,Uid,SuserId,Ip,F_No,Pcode,Pnum,Psku,Sts,AddTime,Bas_Price,Sale_Price,Percent,Activity_Id,Per_Price,Tot_Price,ViewImg,PJ,Cate,Day,Descr,propertys,ProCode";
        sql.append("select  t_basket.id,t_basket.Pid,t_basket.Pname,t_basket.Uid,t_basket.SuserId,t_basket.Ip,t_basket.F_No,t_basket.Pcode,t_basket.Pnum,t_basket.Psku,t_basket.Sts,t_basket.AddTime,t_basket.Bas_Price,t_basket.Sale_Price,t_basket.Percent,t_basket.Activity_Id,t_basket.Per_Price,t_basket.Tot_Price,t_basket.ViewImg,t_basket.PJ,t_basket.Cate,t_basket.Day,t_product.Descr,t_basket.propertys,t_product.ProCode from t_basket,t_product");
        sql.append(" where t_product.id=t_basket.Pid and t_basket.Sts=2 and t_basket.F_No='");
        sql.append(orderno);
        sql.append("'");
        sql.append(" order by t_basket.id desc");
        return this.getData(sql.toString(), fieldArr);
    }
//cart.jsp    获取购物车中含有几个商家的产品  获取商家SuserId

    public Collection getListByUidSuserId(String uid) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "SuserId";
        sql.append("select distinct SuserId from t_basket");
        sql.append(" where  Sts=0 and Uid='");
        sql.append(uid);
        sql.append("'");
        sql.append(" order by id desc");
        return this.getData(sql.toString(), fieldArr);
    }
    //cart.jsp  购物车中按照商家SuserId 和用户uid 进行产品展示

    public Collection getListByUidp(String uid, String SuserId) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,SuserId,Pid,Pname,Uid,Ip,F_No,Pcode,Pnum,Psku,Sts,AddTime,Bas_Price,Sale_Price,Percent,Activity_Id,Per_Price,Tot_Price,ViewImg,PJ,Cate,Day,Descr,KuaiDi,propertys";
        sql.append("select  t_basket.id,t_basket.SuserId,t_basket.Pid,t_basket.Pname,t_basket.Uid,t_basket.Ip,t_basket.F_No,t_basket.Pcode,t_basket.Pnum,t_basket.Psku,t_basket.Sts,t_basket.AddTime,t_basket.Bas_Price,t_basket.Sale_Price,t_basket.Percent,t_basket.Activity_Id,t_basket.Per_Price,t_basket.Tot_Price,t_basket.ViewImg,t_basket.PJ,t_basket.Cate,t_basket.Day,t_product.Descr,t_product.KuaiDi,t_basket.propertys from t_basket,t_product");
        sql.append(" where t_product.id=t_basket.Pid  and t_basket.Sts=0 and t_basket.Uid='");
        sql.append(uid);
        sql.append("'");
        sql.append(" and t_basket.SuserId='").append(SuserId).append("'");
        sql.append(" order by t_basket.id desc");
        return this.getData(sql.toString(), fieldArr);
    }

    public Collection getListByUidp(String uid, String SuserId, int Sts, int CurrentPage, int PageSize) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,SuserId,Pid,Pname,Uid,Ip,F_No,Pcode,Pnum,Psku,Sts,AddTime,Bas_Price,Sale_Price,Percent,Activity_Id,Per_Price,Tot_Price,ViewImg,PJ,Cate,Day,Descr,KuaiDi,Name,Phone,Weixin,Address,Remark,FirstYJ,SecondYJ,ThirdYJ,propertys";
        sql.append("select  t_basket.id,t_basket.SuserId,t_basket.Pid,t_basket.Pname,t_basket.Uid,t_basket.Ip,t_basket.F_No,t_basket.Pcode,t_basket.Pnum,t_basket.Psku,t_basket.Sts,t_basket.AddTime,t_basket.Bas_Price,t_basket.Sale_Price,t_basket.Percent,t_basket.Activity_Id,t_basket.Per_Price,t_basket.Tot_Price,t_basket.ViewImg,t_basket.PJ,t_basket.Cate,t_basket.Day,t_product.Descr,t_product.KuaiDi,t_basket.Name,t_basket.Phone,t_basket.Weixin,t_basket.Address,t_basket.Remark,t_basket.FirstYJ,t_basket.SecondYJ,t_basket.ThirdYJ,t_basket.propertys from t_basket,t_product");
        sql.append(" where t_product.id=t_basket.Pid and t_basket.Uid='");
        sql.append(uid);
        sql.append("'");
        sql.append(" and t_basket.SuserId='").append(SuserId).append("'");
        sql.append("  and t_basket.Sts=").append(Sts);
        sql.append(" order by t_basket.id desc ");
        if (CurrentPage >= 0 && PageSize > 0) {

            sql.append(" limit ");
            sql.append((CurrentPage - 1) * PageSize);
            sql.append(",");
            sql.append(PageSize);
        }
        return this.getData(sql.toString(), fieldArr);
    }

    public boolean delByUidp(String uid, String SuserId, int Sts) {
        boolean flag = false;
        StringBuffer sql = new StringBuffer(512);
        sql.append("delete from t_basket");
        sql.append(" where Uid='");
        sql.append(uid);
        sql.append("'");
        sql.append(" and SuserId='").append(SuserId).append("'");
        sql.append(" and Sts=").append(Sts);
        try {
            flag = this.exe(sql.toString());
        } catch (ObjectNotFoundException ex) {
            Logger.getLogger(BasketDaoImplJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(BasketDaoImplJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

//购物车中搜索
    public Collection getListByUidp(String uid) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,SuserId,Pid,Pname,Uid,Ip,F_No,Pcode,Pnum,Psku,Sts,AddTime,Bas_Price,Sale_Price,Percent,Activity_Id,Per_Price,Tot_Price,ViewImg,PJ,Cate,Day,Descr,propertys";
        sql.append("select  t_basket.id,t_basket.SuserId,t_basket.Pid,t_basket.Pname,t_basket.Uid,t_basket.Ip,t_basket.F_No,t_basket.Pcode,t_basket.Pnum,t_basket.Psku,t_basket.Sts,t_basket.AddTime,t_basket.Bas_Price,t_basket.Sale_Price,t_basket.Percent,t_basket.Activity_Id,t_basket.Per_Price,t_basket.Tot_Price,t_basket.ViewImg,t_basket.PJ,t_basket.Cate,t_basket.Day,t_product.Descr,t_basket.propertys from t_basket,t_product");
        sql.append(" where t_product.id=t_basket.Pid  and t_basket.Sts=0 and t_basket.Uid='");
        sql.append(uid);
        sql.append("'");
        sql.append(" order by t_basket.id desc");
        return this.getData(sql.toString(), fieldArr);
    }

    //update t_basket set pnum=pnum+" + num + " where uid='" + uid + "' and pid=" + pid + " and sts=0 ";
    public void batDel(String[] s) {
        this.bat("delete from t_basket where id=?", s);
    }

    //将购物车中数据更改为购物车与订单过度数据
    public void batBiaoZhi(String[] fieldvalue) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sqlStr = "update t_basket set BiaoZhi=2 where Sts=0 and id=?";
        String str = null;
        int returnInt = 0;
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sqlStr);
            for (int i = 0; i < fieldvalue.length; i++) {

                int f = Integer.parseInt(fieldvalue[i].split(",")[0]);
                ps.setInt(1, f);
                ps.executeUpdate();

            }

        } catch (SQLException e) {
            log.error("Sql Exception Error:", e);
        } finally {

            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }

    }
//为购物车中数据添加订单号

    public void batnod(String[] fieldvalue, String Str) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sqlStr = "update t_basket set F_No=?, Sts=1 where Sts=0 and id=?";
        int returnInt = 0;
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sqlStr);
            for (int i = 0; i < fieldvalue.length; i++) {
                int f = Integer.parseInt(fieldvalue[i]);
                ps.setString(1, Str);
                ps.setInt(2, f);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error("Sql Exception Error:", e);
        } finally {
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
    }

    //为购物车中数据添加通用订单号
    public void batord(String[] fieldvalue, String Str) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sqlStr = "update t_basket set F_Order=?, Sts=1 where Sts=0 and id=?";
        int returnInt = 0;
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sqlStr);
            for (int i = 0; i < fieldvalue.length; i++) {
                int f = Integer.parseInt(fieldvalue[i]);
                ps.setString(1, Str);
                ps.setInt(2, f);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error("Sql Exception Error:", e);
        } finally {
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
    }

    public void del(String[] s) {
        this.bat_String("delete from t_basket where F_No=?", s);
    }

    public int getNum(String uid) {
        int returnValue = 0;
        StringBuffer sql = new StringBuffer(512);
        sql.append("select count(*) from t_basket where Uid='").append(uid).append("'");
        sql.append(" and Sts=0");
        returnValue = getDataCount(sql.toString());
        return returnValue;
    }

    //判断订单中所有商品是否全部评论
    public int getNumPJ(String F_No) {
        int returnValue = 0;
        StringBuffer sql = new StringBuffer(512);
        sql.append("select count(*) from t_basket where F_NO='").append(F_No).append("'");
        sql.append(" and PJ=2");
        returnValue = getDataCount(sql.toString());
        return returnValue;
    }

    //商品成交记录搜所在购物车中按照订单号模糊查询保证为已经购买了的商品
    public Collection getList(int pid, String F_No, int CurrentPage, int PageSize) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,Pid,Pname,Uid,Ip,F_No,Pcode,Pnum,Psku,Sts,AddTime,Bas_Price,Sale_Price,Percent,Activity_Id,Per_Price,Tot_Price,ViewImg,PJ,Cate,Day,propertys";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_basket where 1=1");
        if (pid > 0) {
            sql.append(" and Pid=").append(pid);
        }
        if (F_No != null && !F_No.equals("")) {
            sql.append(" and F_No like '%").append(F_No).append("%'");

        }
        sql.append(" order by id desc ");
        if (CurrentPage >= 0 && PageSize > 0) {

            sql.append(" limit ");
            sql.append((CurrentPage - 1) * PageSize);
            sql.append(",");
            sql.append(PageSize);
        }

        return this.getData(sql.toString(), fieldArr);
    }

    //商品成交记录搜所在购物车中按照订单号模糊查询保证为已经购买了的商品    数量
    public int getTotalCount(int pid, String F_No, int CurrentPage, int PageSize) {
        StringBuffer sql = new StringBuffer(512);
        sql.append("select count(*) from t_basket where 1=1");
        if (pid > 0) {
            sql.append(" and Pid=").append(pid);
        }
        if (F_No != null && !F_No.equals("")) {
            sql.append(" and F_No like '%").append(F_No).append("%'");
            // sql.append(" or Keywords like '%").append(key).append("%'");
        }

        return (this.getDataCount(sql.toString()));
    }
}
