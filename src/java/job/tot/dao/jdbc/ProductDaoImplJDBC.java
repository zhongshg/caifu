/*
 * MessageDaoImplJDBC.java
 *
 * Created on 2007��6��21��, ����3:44
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
public class ProductDaoImplJDBC extends AbstractDao {

    private static Log log = LogFactory.getLog(ProductDaoImplJDBC.class);

    /**
     * Creates a new instance of MessageDaoImplJDBC
     */
    public ProductDaoImplJDBC() {
    }

    public DataField get(int id) {
        String fieldArr = "id,ProCode,SuserId,SuserName,Rid,Pid,Cid,LiuLan,GongNeng,RenQun,JiXing,JiangShi,WebDescr,WebKeywords,WebTitle,Title,Abbreviation,Keywords,Descr,MeasurId,Remarks,YongTu,IsSale,BrandId,IsShelves,ViewImg,Supplier,Photos,Cate,Photo,Price,PriceM,Content,AddTime,ModTime,Shelves,PSts,IsNew,IsRem,Tags,SaleNum,IsShi,BeginTime,EndTime,KuaiDi,wxid,distributionmoney,distributionfirstdiscount,distributionseconddiscount,distributionthirddiscount,KuaiDi,distributiontype,propertys";
        String fieldArr1 = "id,ProCode,SuserId,SuserName,Rid,Pid,Cid,LiuLan,GongNeng,RenQun,JiXing,JiangShi,WebDescr,WebKeywords,WebTitle,Title,Abbreviation,Keywords,Descr,MeasurId,Remarks,YongTu,IsSale,BrandId,IsShelves,ViewImg,Supplier,Photos,Cate,Photo,Price,PriceM,Content,AddTime,ModTime,Shelves,PSts,IsNew,IsRem,Tags,SaleNum,IsShi,BeginTime,EndTime,KuaiDi,wxid,distributionmoney,distributionfirstdiscount,distributionseconddiscount,distributionthirddiscount,KuaiDi,distributiontype,propertys";
        return getFirstData("select " + fieldArr1 + " from t_product where id=" + id, fieldArr);
    }

    public DataField get(int id, String propertys) {
        String fieldArr = "id,ProCode,SuserId,SuserName,Rid,Pid,Cid,LiuLan,GongNeng,RenQun,JiXing,JiangShi,WebDescr,WebKeywords,WebTitle,Title,Abbreviation,Keywords,Descr,MeasurId,Remarks,YongTu,IsSale,BrandId,IsShelves,ViewImg,Supplier,Photos,Cate,Photo,Price,PriceM,Content,AddTime,ModTime,Shelves,PSts,IsNew,IsRem,Tags,SaleNum,IsShi,BeginTime,EndTime,KuaiDi,wxid,distributionmoney,distributionfirstdiscount,distributionseconddiscount,distributionthirddiscount,KuaiDi,distributiontype,propertys";
        return getFirstData("select " + fieldArr + " from t_product where id=" + id + " and propertys like '%" + propertys + "%'", fieldArr);
    }

    //获取最后商品的ID 
    public int getLastId() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int returnValue = 0;
        String sql = null;
        if (DBUtils.getDatabaseType() == DBUtils.DATABASE_SQLSERVER) {
            sql = "select top 1 id from t_product order by id desc";
        } else if (DBUtils.getDatabaseType() == DBUtils.DATABASE_MYSQL) {
            sql = "select id from t_product order by id desc limit 0,1";
        }
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                returnValue = rs.getInt(1) + 1;
            } else {
                returnValue = 1;
            }
        } catch (SQLException e) {
            log.error("get last id", e);
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }

    public int getLastId(String wxsid) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int returnValue = 0;
        String sql = null;
        if (DBUtils.getDatabaseType() == DBUtils.DATABASE_SQLSERVER) {
            sql = "select top 1 id from t_product where SuserId='" + wxsid + "' order by id desc";
        } else if (DBUtils.getDatabaseType() == DBUtils.DATABASE_MYSQL) {
            sql = "select id from t_product where SuserId='" + wxsid + "' order by id desc limit 0,1";
        }
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                returnValue = rs.getInt(1) + 1;
            } else {
                returnValue = 1;
            }
        } catch (SQLException e) {
            log.error("get last id", e);
        } finally {
            DBUtils.closeResultSet(rs);
            DBUtils.closePrepareStatement(ps);
            DBUtils.closeConnection(conn);
        }
        return returnValue;
    }
    //商品排序上升

    public boolean modSaleNumadd(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_product set SaleNum=SaleNum+1 where id=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
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
    //商品排序下降

    public boolean modSaleNumdel(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_product set SaleNum=SaleNum-1 where id=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

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
    //修改排序数

    public boolean modSaleNum(int id, float SaleNum) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_product set SaleNum=? where id=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setFloat(1, SaleNum);
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
    //修改库存

    public boolean modIsRem(int id, int number) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_product set IsRem=IsRem-" + number + " where id=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

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

    public boolean modpropertys(int id, String propertys) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_product set propertys='" + propertys + "' where id=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
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

    public Collection getList(String SuserId) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,ProCode,SuserId,SuserName,Rid,Pid,Cid,LiuLan,GongNeng,RenQun,JiXing,JiangShi,WebDescr,WebKeywords,WebTitle,Title,Abbreviation,Keywords,Descr,MeasurId,Remarks,YongTu,IsSale,BrandId,IsShelves,ViewImg,Supplier,Photos,Cate,Photo,Price,PriceM,Content,AddTime,ModTime,Shelves,PSts,IsNew,IsRem,Sales,Praice,Tags,SaleNum,IsShi,BeginTime,EndTime,KuaiDi,wxid,distributionmoney,distributionfirstdiscount,distributionseconddiscount,distributionthirddiscount,distributiontype,propertys";
        String fieldArr1 = "id,ProCode,SuserId,SuserName,Rid,Pid,Cid,LiuLan,GongNeng,RenQun,JiXing,JiangShi,WebDescr,WebKeywords,WebTitle,left(Title,35) as Title,Abbreviation,Keywords,Descr,MeasurId,Remarks,YongTu,IsSale,BrandId,IsShelves,ViewImg,Supplier,Photos,Cate,Photo,Price,PriceM,Content,AddTime,ModTime,Shelves,PSts,IsNew,IsRem,Sales,Praice,Tags,SaleNum,IsShi,BeginTime,EndTime,KuaiDi,wxid,distributionmoney,distributionfirstdiscount,distributionseconddiscount,distributionthirddiscount,distributiontype,propertys";

        sql.append("select ");
        sql.append(fieldArr1);
        sql.append(" from t_product where 1=1");
        if (SuserId != null && !SuserId.equals("")) {
            sql.append(" and SuserId='").append(SuserId).append("'");
        }
        sql.append(" order by SaleNum desc,id desc");
        return this.getData(sql.toString(), fieldArr);
    }

    public Collection getIsRemList(String SuserId) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,ProCode,SuserId,SuserName,Rid,Pid,Cid,LiuLan,GongNeng,RenQun,JiXing,JiangShi,WebDescr,WebKeywords,WebTitle,Title,Abbreviation,Keywords,Descr,MeasurId,Remarks,YongTu,IsSale,BrandId,IsShelves,ViewImg,Supplier,Photos,Cate,Photo,Price,PriceM,Content,AddTime,ModTime,Shelves,PSts,IsNew,IsRem,Sales,Praice,Tags,SaleNum,IsShi,BeginTime,EndTime,KuaiDi,wxid,distributionmoney,distributionfirstdiscount,distributionseconddiscount,distributionthirddiscount,distributiontype,propertys";
        sql.append("select ");
        sql.append(fieldArr);
        sql.append(" from t_product where 1=1");
        if (SuserId != null && !SuserId.equals("")) {
            sql.append(" and SuserId='").append(SuserId).append("'");
        }
        sql.append(" and IsRem<=JiangShi");
        sql.append(" order by SaleNum desc,id desc");
        return this.getData(sql.toString(), fieldArr);
    }

    public Collection getList(String ProCode, String SuserId, String SuserName, int Rid, int Pid, int Cid, int LiuLan, int GongNeng, int RenQun, int JiXing, int JiangShi, String WebDescr, String WebKeywords, String WebTitle, String Title, String Abbreviation, String Keywords, String Descr, int MeasurId, String Remarks, String YongTu, int IsSale, int BrandId, int IsShelves, String ViewImg, String Supplier, String Photos, int Cate, float Price, float PriceM, String Content, Timestamp AddTime, Timestamp ModTime, Timestamp Shelves, int PSts, String Tags, int SaleNum, int IsShi, Timestamp Begintime, Timestamp EndTime, float PriceLeft, float PriceRight, String Order, int CurrentPage, int PageSize) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,ProCode,SuserId,SuserName,Rid,Pid,Cid,LiuLan,GongNeng,RenQun,JiXing,JiangShi,WebDescr,WebKeywords,WebTitle,Title,Abbreviation,Keywords,Descr,MeasurId,Remarks,YongTu,IsSale,BrandId,IsShelves,ViewImg,Supplier,Photos,Cate,Photo,Price,PriceM,Content,AddTime,ModTime,Shelves,PSts,IsNew,IsRem,Sales,Praice,Tags,SaleNum,IsShi,BeginTime,EndTime,KuaiDi,wxid,distributionmoney,distributionfirstdiscount,distributionseconddiscount,distributionthirddiscount,distributiontype,propertys";
        String fieldArr1 = "id,ProCode,SuserId,SuserName,Rid,Pid,Cid,LiuLan,GongNeng,RenQun,JiXing,JiangShi,WebDescr,WebKeywords,WebTitle,left(Title,35) as Title,Abbreviation,Keywords,Descr,MeasurId,Remarks,YongTu,IsSale,BrandId,IsShelves,ViewImg,Supplier,Photos,Cate,Photo,Price,PriceM,Content,AddTime,ModTime,Shelves,PSts,IsNew,IsRem,Sales,Praice,Tags,SaleNum,IsShi,BeginTime,EndTime,KuaiDi,wxid,distributionmoney,distributionfirstdiscount,distributionseconddiscount,distributionthirddiscount,distributiontype,propertys";

        sql.append("select ");
        sql.append(fieldArr1);
        sql.append(" from t_product where 1=1");
        if (SuserId != null && !SuserId.equals("")) {
            sql.append(" and SuserId='").append(SuserId).append("'");
        }
//        if (Rid > 0) {
//            sql.append(" and Rid=").append(Rid);
//        }
//        if (Pid > 0) {
//            sql.append(" and Pid=").append(Pid);
//        }
        if (Cid > 0) {
            sql.append(" and Cid=").append(Cid);
        }
//        if (LiuLan > 0) {
//            sql.append(" and LiuLan=").append(LiuLan);
//        }
//        if (GongNeng > 0) {
//            sql.append(" and GongNeng=").append(GongNeng);
//        }
//        if (RenQun > 0) {
//            sql.append(" and RenQun=").append(RenQun);
//        }
//        if (JiXing > 0) {
//            sql.append(" and JiXing=").append(JiXing);
//        }
//        if (JiangShi >= 0) {
//            sql.append(" and JiangShi=").append(JiangShi);
//        }

//        if (WebKeywords != null && !WebKeywords.equals("") && !WebKeywords.equals("null")) {
//            sql.append(" and WebKeywords like '%").append(WebKeywords).append("%'");
//            // sql.append(" or Keywords like '%").append(key).append("%'");
//        }
//
//        if (PriceLeft > 0 && PriceRight >= PriceLeft) {
//            sql.append(" and Price>=").append(PriceLeft).append(" and Price<=").append(PriceRight);
//        }
//        if (Cate > 0) {
//            sql.append(" and Cate=").append(Cate);
//        }
//
//        if (IsSale > 0) {
//            sql.append(" and IsSale=").append(IsSale);
//        }
//        if (PSts > 0) {
//            sql.append(" and PSts=").append(PSts);
//        }
//        if (BrandId > 0) {
//            sql.append(" and BrandId=").append(BrandId);
//
//        }
//        if (Keywords != null && !Keywords.equals("") && !Keywords.equals("null")) {
//            sql.append(" and Title like '%").append(Keywords).append("%'");
//            sql.append(" or Keywords like '%").append(Keywords).append("%'");
//        }
        sql.append(" order by ").append(Order);
        if (CurrentPage >= 0 && PageSize > 0) {

            sql.append(" limit ");
            sql.append((CurrentPage - 1) * PageSize);
            sql.append(",");
            sql.append(PageSize);
        }
        return this.getData(sql.toString(), fieldArr);
    }

    public int getTotalCount(String ProCode, String SuserId, String SuserName, int Rid, int Pid, int Cid, int LiuLan, int GongNeng, int RenQun, int JiXing, int JiangShi, String WebDescr, String WebKeywords, String WebTitle, String Title, String Abbreviation, String Keywords, String Descr, int MeasurId, String Remarks, String YongTu, int IsSale, int BrandId, int IsShelves, String ViewImg, String Supplier, String Photos, int Cate, float Price, float PriceM, String Content, Timestamp AddTime, Timestamp ModTime, Timestamp Shelves, int PSts, String Tags, int SaleNum, int IsShi, Timestamp Begintime, Timestamp EndTime, float PriceLeft, float PriceRight) {
        StringBuffer sql = new StringBuffer(512);
        sql.append("select count(*) from t_product where 1=1");

        if (SuserId != null && !SuserId.equals("")) {
            sql.append(" and SuserId='").append(SuserId).append("'");
        }
        if (Rid > 0) {
            sql.append(" and Rid=").append(Rid);
        }
        if (Pid > 0) {
            sql.append(" and Pid=").append(Pid);
        }
        if (Cid > 0) {
            sql.append(" and Cid=").append(Cid);
        }
        if (LiuLan > 0) {
            sql.append(" and LiuLan=").append(LiuLan);
        }
        if (GongNeng > 0) {
            sql.append(" and GongNeng=").append(GongNeng);
        }
        if (RenQun > 0) {
            sql.append(" and RenQun=").append(RenQun);
        }
        if (JiXing > 0) {
            sql.append(" and JiXing=").append(JiXing);
        }
        if (JiangShi > 0) {
            sql.append(" and JiangShi=").append(JiangShi);
        }
        if (Cate > 0) {
            sql.append(" and Cate=").append(Cate);
        }
        if (WebKeywords != null && !WebKeywords.equals("") && !WebKeywords.equals("null")) {
            sql.append(" and WebKeywords like '%").append(WebKeywords).append("%'");
            // sql.append(" or Keywords like '%").append(key).append("%'");
        }

        if (PriceLeft > 0 && PriceRight >= PriceLeft) {
            sql.append(" and Price>=").append(PriceLeft).append(" and Price<=").append(PriceRight);
        }

        if (IsSale > 0) {
            sql.append(" and IsSale=").append(IsSale);
        }
        if (BrandId > 0) {
            sql.append(" and BrandId=").append(BrandId);

        }
        if (Keywords != null && !Keywords.equals("") && !Keywords.equals("null")) {
            sql.append(" and Title like '%").append(Keywords).append("%'");
            sql.append(" or Keywords like '%").append(Keywords).append("%'");
        }
        return (this.getDataCount(sql.toString()));
    }

    public void batDel(String[] s) {
        this.bat("delete from t_product where id=?", s);
    }

    public void batRem(String tbl, String[] s) {
        this.bat("update t_product set IsRem=1 where id=?", s);
    }

    public boolean modSaleNum(int pid, String sign) throws ObjectNotFoundException, DatabaseException {
        return exe("update t_product set SaleNum=SaleNum" + sign + " where id=" + pid);
    }

    public boolean delByDate(String fromdate, String todate) throws ObjectNotFoundException, DatabaseException {
        StringBuffer sql = new StringBuffer(512);
        if (fromdate == null || todate == null) {
            return false;
        }
        sql.append("delete from t_product");
        if (DBUtils.getDatabaseType() == DBUtils.DATABASE_MYSQL) {
            sql.append(" where to_days(ModiTime)>to_days('").append(fromdate).append("') and to_days(ModiTime)<to_days('").append(todate).append("')");
        } else if (DBUtils.getDatabaseType() == DBUtils.DATABASE_SQLSERVER) {
            sql.append(" where dateDiff(d,'").append(fromdate).append("',ModiTime)>0 and dateDiff(d,'").append(todate).append("',ModiTime)<0");
        }
        return exe(sql.toString());
    }
    //12.2 根据客户要求对产品进行修改后的方法
    //商品添加

    public boolean add(String ProCode, String SuserId, String SuserName, int Rid, int Pid, int Cid, int LiuLan, int GongNeng, int RenQun, int JiXing, int JiangShi, String WebDescr, String WebKeywords, String WebTitle, String Title, String Abbreviation, String Keywords, String Descr, int MeasurId, String Remarks, String Use, int IsSale, int BrandId, int IsShelves, String ViewImg, String Supplier, String Photos, int Cate, String Photo, float Price, float PriceM, String Content, Timestamp AddTime, Timestamp ModTime, Timestamp Shelves, int PSts, int IsNew, int IsRem, String Tags, int SaleNum, int IsShi, Timestamp Begintime, Timestamp EndTime, Float KuaiDi, String wxid, Float distributionmoney, Float distributionfirstdiscount, Float distributionseconddiscount, Float distributionthirddiscount, int distributiontype, String propertys) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        int lastid = getLastId();
        String sql = "insert into t_product(ProCode,SuserId,SuserName,Rid,Pid,Cid,LiuLan,GongNeng,RenQun,JiXing,JiangShi,WebDescr,WebKeywords,WebTitle,Title,Abbreviation,Keywords,Descr,MeasurId,Remarks,YongTu,IsSale,BrandId,IsShelves,ViewImg,Supplier,Photos,Cate,Photo,Price,PriceM,Content,AddTime,ModTime,Shelves,PSts,IsNew,IsRem,Tags,SaleNum,IsShi,BeginTime,EndTime,KuaiDi,wxid,distributionmoney,distributionfirstdiscount,distributionseconddiscount,distributionthirddiscount,distributiontype,propertys) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, ProCode);
            ps.setString(2, SuserId);
            ps.setString(3, SuserName);
            ps.setInt(4, Rid);
            ps.setInt(5, Pid);
            ps.setInt(6, Cid);
            ps.setInt(7, LiuLan);
            ps.setInt(8, GongNeng);
            ps.setInt(9, RenQun);
            ps.setInt(10, JiXing);
            ps.setInt(11, JiangShi);
            ps.setString(12, WebDescr);
            ps.setString(13, WebKeywords);
            ps.setString(14, WebTitle);
            ps.setString(15, Title);
            ps.setString(16, Abbreviation);
            ps.setString(17, Keywords);
            ps.setString(18, Descr);
            ps.setInt(19, MeasurId);
            ps.setString(20, Remarks);
            ps.setString(21, Use);
            ps.setInt(22, IsSale);
            ps.setInt(23, BrandId);
            ps.setInt(24, IsShelves);
            ps.setString(25, ViewImg);
            ps.setString(26, Supplier);
            ps.setString(27, Photos);
            ps.setInt(28, Cate);
            ps.setString(29, Photo);
            ps.setFloat(30, Price);
            ps.setFloat(31, PriceM);
            ps.setString(32, Content);
            ps.setTimestamp(33, AddTime);
            ps.setTimestamp(34, ModTime);
            ps.setTimestamp(35, Shelves);
            ps.setInt(36, PSts);
            ps.setInt(37, IsNew);
            ps.setInt(38, IsRem);
            ps.setString(39, Tags);
            ps.setInt(40, lastid);
            ps.setInt(41, IsShi);
            ps.setTimestamp(42, Begintime);
            ps.setTimestamp(43, EndTime);
            ps.setFloat(44, KuaiDi);//wxid,distributionmoney,distributionfirstdiscount,distributionseconddiscount,distributionthirddiscount
            ps.setString(45, wxid);
            ps.setFloat(46, distributionmoney);
            ps.setFloat(47, distributionfirstdiscount);
            ps.setFloat(48, distributionseconddiscount);
            ps.setFloat(49, distributionthirddiscount);
            ps.setInt(50, distributiontype);
            ps.setString(51, propertys);
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
    //含有标签时间戳的 商品添加方法

    public boolean add_BqTime_pid(String ProCode, String SuserId, String SuserName, int Rid, int Pid, int Cid, int LiuLan, int GongNeng, int RenQun, int JiXing, int JiangShi, String WebDescr, String WebKeywords, String WebTitle, String Title, String Abbreviation, String Keywords, String Descr, int MeasurId, String Remarks, String Use, int IsSale, int BrandId, int IsShelves, String ViewImg, String Supplier, String Photos, int Cate, String Photo, float Price, float PriceM, String Content, Timestamp AddTime, Timestamp ModTime, Timestamp Shelves, int PSts, int IsNew, int IsRem, String Tags, float SaleNum, int IsShi, Timestamp Begintime, Timestamp EndTime, Float KuaiDi, String Time_pid, String wxid, Float distributionmoney, Float distributionfirstdiscount, Float distributionseconddiscount, Float distributionthirddiscount, int distributiontype, String propertys) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        int lastid = getLastId();
        String sql = "insert into t_product(ProCode,SuserId,SuserName,Rid,Pid,Cid,LiuLan,GongNeng,RenQun,JiXing,JiangShi,WebDescr,WebKeywords,WebTitle,Title,Abbreviation,Keywords,Descr,MeasurId,Remarks,YongTu,IsSale,BrandId,IsShelves,ViewImg,Supplier,Photos,Cate,Photo,Price,PriceM,Content,AddTime,ModTime,Shelves,PSts,IsNew,IsRem,Tags,SaleNum,IsShi,BeginTime,EndTime,KuaiDi,Time_pid,wxid,distributionmoney,distributionfirstdiscount,distributionseconddiscount,distributionthirddiscount,distributiontype,propertys) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, ProCode);
            ps.setString(2, SuserId);
            ps.setString(3, SuserName);
            ps.setInt(4, Rid);
            ps.setInt(5, Pid);
            ps.setInt(6, Cid);
            ps.setInt(7, LiuLan);
            ps.setInt(8, GongNeng);
            ps.setInt(9, RenQun);
            ps.setInt(10, JiXing);
            ps.setInt(11, JiangShi);
            ps.setString(12, WebDescr);
            ps.setString(13, WebKeywords);
            ps.setString(14, WebTitle);
            ps.setString(15, Title);
            ps.setString(16, Abbreviation);
            ps.setString(17, Keywords);
            ps.setString(18, Descr);
            ps.setInt(19, MeasurId);
            ps.setString(20, Remarks);
            ps.setString(21, Use);
            ps.setInt(22, IsSale);
            ps.setInt(23, BrandId);
            ps.setInt(24, IsShelves);
            ps.setString(25, ViewImg);
            ps.setString(26, Supplier);
            ps.setString(27, Photos);
            ps.setInt(28, Cate);
            ps.setString(29, Photo);
            ps.setFloat(30, Price);
            ps.setFloat(31, PriceM);
            ps.setString(32, Content);
            ps.setTimestamp(33, AddTime);
            ps.setTimestamp(34, ModTime);
            ps.setTimestamp(35, Shelves);
            ps.setInt(36, PSts);
            ps.setInt(37, IsNew);
            ps.setInt(38, IsRem);
            ps.setString(39, Tags);
            ps.setFloat(40, SaleNum);
            ps.setInt(41, IsShi);
            ps.setTimestamp(42, Begintime);
            ps.setTimestamp(43, EndTime);
            ps.setFloat(44, KuaiDi);
            ps.setString(45, Time_pid);
            ps.setString(46, wxid);
            ps.setFloat(47, distributionmoney);
            ps.setFloat(48, distributionfirstdiscount);
            ps.setFloat(49, distributionseconddiscount);
            ps.setFloat(50, distributionthirddiscount);
            ps.setInt(51, distributiontype);
            ps.setString(52, propertys);
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
    //含有标签时间戳的 商品获的方法，获得添加的商品的id 以时间戳为条件进行搜索

    public DataField get_BqTime_pid(String Time_pid) {
        String fieldArr = "id";
        return getFirstData("select " + fieldArr + " from t_product where Time_pid='" + Time_pid + "'", fieldArr);
    }

    //修改商品
    public boolean mod(int id, String ProCode, String SuserId, String SuserName, int Rid, int Pid, int Cid, int LiuLan, int GongNeng, int RenQun, int JiXing, int JiangShi, String WebDescr, String WebKeywords, String WebTitle, String Title, String Abbreviation, String Keywords, String Descr, int MeasurId, String Remarks, String YongTu, int IsSale, int BrandId, int IsShelves, String ViewImg, String Supplier, String Photos, int Cate, String Photo, float Price, float PriceM, String Content, Timestamp ModTime, Timestamp Shelves, int PSts, int IsNew, int IsRem, String Tags, float SaleNum, int IsShi, Timestamp Begintime, Timestamp EndTime, Float KuaiDi, String wxid, Float distributionmoney, Float distributionfirstdiscount, Float distributionseconddiscount, Float distributionthirddiscount, int distributiontype, String propertys) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean returnValue = true;
        String sql = "update t_product set ProCode=?,SuserId=?,SuserName=?, Rid=?,Pid=?,Cid=?,LiuLan=?,GongNeng=?,RenQun=?,JiXing=?,JiangShi=?,WebDescr=?,WebKeywords=?,WebTitle=?,Title=?,Abbreviation=?,Keywords=?,Descr=?,MeasurId=?,Remarks=?,YongTu=?,IsSale=?,BrandId=?,IsShelves=?,ViewImg=?,Supplier=?,Photos=?,Cate=?,Photo=?,Price=?,PriceM=?,Content=?,ModTime=?,Shelves=?,PSts=?,IsNew=?,IsRem=?,Tags=?,SaleNum=?,IsShi=?,Begintime=?,EndTime=?,KuaiDi=?,wxid=?,distributionmoney=?,distributionfirstdiscount=?,distributionseconddiscount=?,distributionthirddiscount=?,distributiontype=?,propertys=? where id=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, ProCode);
            ps.setString(2, SuserId);
            ps.setString(3, SuserName);
            ps.setInt(4, Rid);
            ps.setInt(5, Pid);
            ps.setInt(6, Cid);
            ps.setInt(7, LiuLan);
            ps.setInt(8, GongNeng);
            ps.setInt(9, RenQun);
            ps.setInt(10, JiXing);
            ps.setInt(11, JiangShi);
            ps.setString(12, WebDescr);
            ps.setString(13, WebKeywords);
            ps.setString(14, WebTitle);
            ps.setString(15, Title);
            ps.setString(16, Abbreviation);
            ps.setString(17, Keywords);
            ps.setString(18, Descr);
            ps.setInt(19, MeasurId);
            ps.setString(20, Remarks);
            ps.setString(21, YongTu);
            ps.setInt(22, IsSale);
            ps.setInt(23, BrandId);
            ps.setInt(24, IsShelves);
            ps.setString(25, ViewImg);
            ps.setString(26, Supplier);
            ps.setString(27, Photos);
            ps.setInt(28, Cate);
            ps.setString(29, Photo);
            ps.setFloat(30, Price);
            ps.setFloat(31, PriceM);
            ps.setString(32, Content);
            ps.setTimestamp(33, ModTime);
            ps.setTimestamp(34, Shelves);
            ps.setInt(35, PSts);
            ps.setInt(36, IsNew);
            ps.setInt(37, IsRem);
            ps.setString(38, Tags);
            ps.setFloat(39, SaleNum);
            ps.setInt(40, IsShi);
            ps.setTimestamp(41, Begintime);
            ps.setTimestamp(42, EndTime);
            ps.setFloat(43, KuaiDi);
            ps.setString(44, wxid);
            ps.setFloat(45, distributionmoney);
            ps.setFloat(46, distributionfirstdiscount);
            ps.setFloat(47, distributionseconddiscount);
            ps.setFloat(48, distributionthirddiscount);
            ps.setInt(49, distributiontype);
            ps.setString(50, propertys);
            ps.setInt(51, id);
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
    //搜索商品

    public Collection getList(String ProCode, String SuserId, String SuserName, int Rid, int Pid, int Cid, int LiuLan, int GongNeng, int RenQun, int JiXing, int JiangShi, String WebDescr, String WebKeywords, String WebTitle, String Title, String Abbreviation, String Keywords, String Descr, int MeasurId, String Remarks, String YongTu, int IsSale, int BrandId, int IsShelves, String ViewImg, String Supplier, String Photos, int Cate, float Price, float PriceM, String Content, Timestamp AddTime, Timestamp ModTime, Timestamp Shelves, int PSts, int IsNew, int IsRem, int Sales, int Praice, String Tags, int SaleNum, int IsShi, Timestamp Begintime, Timestamp EndTime, float PriceLeft, float PriceRight, String Order, int CurrentPage, int PageSize) {
        StringBuffer sql = new StringBuffer(512);
        String fieldArr = "id,ProCode,SuserId,SuserName,Rid,Pid,Cid,LiuLan,GongNeng,RenQun,JiXing,JiangShi,WebDescr,WebKeywords,WebTitle,Title,Abbreviation,Keywords,Descr,MeasurId,Remarks,YongTu,IsSale,BrandId,IsShelves,ViewImg,Supplier,Photos,Cate,Photo,Price,PriceM,Content,AddTime,ModTime,Shelves,PSts,IsNew,IsRem,Sales,Praice,Tags,SaleNum,IsShi,BeginTime,EndTime,KuaiDi,wxid,distributionmoney,distributionfirstdiscount,distributionseconddiscount,distributionthirddiscount,distributiontype";
        String fieldArr1 = "id,ProCode,SuserId,SuserName,Rid,Pid,Cid,LiuLan,GongNeng,RenQun,JiXing,JiangShi,WebDescr,WebKeywords,WebTitle,left(Title,10) as Title,Abbreviation,Keywords,Descr,MeasurId,Remarks,YongTu,IsSale,BrandId,IsShelves,ViewImg,Supplier,Photos,Cate,Photo,Price,PriceM,Content,AddTime,ModTime,Shelves,PSts,IsNew,IsRem,Sales,Praice,Tags,SaleNum,IsShi,BeginTime,EndTime,KuaiDi,wxid,distributionmoney,distributionfirstdiscount,distributionseconddiscount,distributionthirddiscount,distributiontype";

        sql.append("select ");
        sql.append(fieldArr1);
        sql.append(" from t_product where 1=1");
        if (SuserId != null && !SuserId.equals("")) {
            sql.append(" and SuserId='").append(SuserId).append("'");
        }
        if (Rid > 0) {
            sql.append(" and Rid=").append(Rid);
        }
        if (Pid > 0) {
            sql.append(" and Pid=").append(Pid);
        }
        if (Cid > 0) {
            sql.append(" and Cid=").append(Cid);
        }
        if (LiuLan > 0) {
            sql.append(" and LiuLan=").append(LiuLan);
        }
        if (GongNeng > 0) {
            sql.append(" and GongNeng=").append(GongNeng);
        }
        if (RenQun > 0) {
            sql.append(" and RenQun=").append(RenQun);
        }
        if (JiXing > 0) {
            sql.append(" and JiXing=").append(JiXing);
        }
        if (JiangShi > 0) {
            sql.append(" and JiangShi=").append(JiangShi);
        }

        if (WebKeywords != null && !WebKeywords.equals("") && !WebKeywords.equals("null")) {
            sql.append(" and WebKeywords like '%").append(WebKeywords).append("%'");
            // sql.append(" or Keywords like '%").append(key).append("%'");
        }

        if (PriceLeft > 0 && PriceRight >= PriceLeft) {
            sql.append(" and Price>=").append(PriceLeft).append(" and Price<=").append(PriceRight);
        }
        if (Cate > 0) {
            sql.append(" and Cate=").append(Cate);
        }
        if (PSts > 0) {
            sql.append(" and PSts=").append(PSts);
        }
        if (IsShelves > 0) {
            sql.append(" and IsShelves=").append(IsShelves);
        }
        if (IsNew > 0) {
            sql.append(" and IsNew=").append(IsNew);
        }
        if (IsRem > 0) {
            sql.append(" and IsRem=").append(IsRem);
        }
        if (IsSale > 0) {
            sql.append(" and IsSale=").append(IsSale);
        }
        if (BrandId > 0) {
            sql.append(" and BrandId=").append(BrandId);

        }
        if (Keywords != null && !Keywords.equals("") && !Keywords.equals("null")) {
            sql.append(" and Title like '%").append(Keywords).append("%'");
            sql.append(" or Keywords like '%").append(Keywords).append("%'");
        }
        if (Sales > 0) {
            sql.append(" order by Sale desc");
        } else {
            sql.append(" order by ").append(Order);
        }
        if (CurrentPage >= 0 && PageSize > 0) {

            sql.append(" limit ");
            sql.append((CurrentPage - 1) * PageSize);
            sql.append(",");
            sql.append(PageSize);
        }
        return this.getData(sql.toString(), fieldArr);
    }

    public int getTotalCount(String ProCode, String SuserId, String SuserName, int Rid, int Pid, int Cid, int LiuLan, int GongNeng, int RenQun, int JiXing, int JiangShi, String WebDescr, String WebKeywords, String WebTitle, String Title, String Abbreviation, String Keywords, String Descr, int MeasurId, String Remarks, String YongTu, int IsSale, int BrandId, int IsShelves, String ViewImg, String Supplier, String Photos, int Cate, float Price, float PriceM, String Content, Timestamp AddTime, Timestamp ModTime, Timestamp Shelves, int PSts, int IsNew, int IsRem, String Tags, int SaleNum, int IsShi, Timestamp Begintime, Timestamp EndTime, float PriceLeft, float PriceRight) {
        StringBuffer sql = new StringBuffer(512);
        sql.append("select count(*) from t_product where 1=1");

        if (SuserId != null && !SuserId.equals("")) {
            sql.append(" and SuserId='").append(SuserId).append("'");
        }
        if (Rid > 0) {
            sql.append(" and Rid=").append(Rid);
        }
        if (Pid > 0) {
            sql.append(" and Pid=").append(Pid);
        }
        if (Cid > 0) {
            sql.append(" and Cid=").append(Cid);
        }
        if (LiuLan > 0) {
            sql.append(" and LiuLan=").append(LiuLan);
        }
        if (GongNeng > 0) {
            sql.append(" and GongNeng=").append(GongNeng);
        }
        if (RenQun > 0) {
            sql.append(" and RenQun=").append(RenQun);
        }
        if (JiXing > 0) {
            sql.append(" and JiXing=").append(JiXing);
        }
        if (JiangShi > 0) {
            sql.append(" and JiangShi=").append(JiangShi);
        }
        if (Cate > 0) {
            sql.append(" and Cate=").append(Cate);
        }
        if (PSts > 0) {
            sql.append(" and PSts=").append(PSts);
        }
        if (IsNew > 0) {
            sql.append(" and IsNew=").append(IsNew);
        }
        if (IsRem > 0) {
            sql.append(" and IsRem=").append(IsRem);
        }
        if (WebKeywords != null && !WebKeywords.equals("") && !WebKeywords.equals("null")) {
            sql.append(" and WebKeywords like '%").append(WebKeywords).append("%'");
            // sql.append(" or Keywords like '%").append(key).append("%'");
        }

        if (PriceLeft > 0 && PriceRight >= PriceLeft) {
            sql.append(" and Price>=").append(PriceLeft).append(" and Price<=").append(PriceRight);
        }

        if (IsSale > 0) {
            sql.append(" and IsSale=").append(IsSale);
        }
        if (BrandId > 0) {
            sql.append(" and BrandId=").append(BrandId);

        }
        if (Keywords != null && !Keywords.equals("") && !Keywords.equals("null")) {
            sql.append(" and Title like '%").append(Keywords).append("%'");
            sql.append(" or Keywords like '%").append(Keywords).append("%'");
        }
        sql.append(" order by SaleNum desc,id desc");
        return (this.getDataCount(sql.toString()));

    }
}
