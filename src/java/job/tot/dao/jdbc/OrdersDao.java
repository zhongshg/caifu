package job.tot.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import job.tot.bean.DataField;
import job.tot.dao.AbstractDao;
import job.tot.dao.DaoFactory;
import job.tot.db.DBUtils;
import job.tot.exception.DatabaseException;
import job.tot.exception.ObjectNotFoundException;
import job.tot.util.CodeUtils;
import job.tot.util.DateUtil;
import job.tot.util.DbConn;

/**
 * @author zhongshg
 * @since 2016年9月21日 15:51:19 
 * 订单数据库操作DAO类
 */
public class OrdersDao extends AbstractDao {
    private static Log log = LogFactory.getLog(OrdersDao.class);

    /**
     * @param col
     *            where判断条件字段
     * @param id
     *            col对应的值
     * @param fieldArr
     *            要查询的字段
     */
    public DataField getByCol(String where) {
	String fieldArr = "oid,otitle,odt,osenddt,olastupdatedt,ostatus,onum,ocount,oamountmoney,oprice,ouserid,ousername,pid,oproducttitle,opt,optint,mark";
	return getFirstData("select " + fieldArr + " from orders where " + where, fieldArr);
    }

    public List<Map<String, String>> searchBywhere(String where) {
	String fieldArr = "oid,otitle,odt,osenddt,olastupdatedt,ostatus,onum,ocount,oamountmoney,oprice,ouserid,ousername,pid,oproducttitle,opt,optint,mark";
	StringBuffer sql = new StringBuffer("select ");
	sql.append(fieldArr);
	sql.append(" from orders  ");
	if (where != null && where != "") {
	    sql.append("where " + where);
	}
	List<Map<String, String>> orderList = new ArrayList<Map<String, String>>();
	Connection conn = DbConn.getConn();
	PreparedStatement ptst = null;
	ResultSet rs = null;
	try {
	    ptst = conn.prepareStatement(sql.toString());
	    rs = ptst.executeQuery();
	    while (rs.next()) {
		//oid,otitle,odt,osenddt,olastupdatedt,ostatus,onum,ocount,oamountmoney,oprice,ouserid,ousername,pid,oproducttitle,opt,optint,mark
		Map<String, String> orders = new HashMap<String, String>();
		orders.put("oid", rs.getString("oid"));
		orders.put("otitle", rs.getString("otitle"));
		orders.put("odt", rs.getString("odt"));
		orders.put("osenddt", rs.getString("osenddt"));
		orders.put("olastupdatedt", rs.getString("olastupdatedt"));
		orders.put("ostatus", rs.getString("ostatus"));
		orders.put("onum", rs.getString("onum"));
		orders.put("ocount", rs.getString("ocount"));
		orders.put("oamountmoney", rs.getString("oamountmoney"));
		orders.put("oprice", rs.getString("oprice"));
		orders.put("ouserid", rs.getString("ouserid"));
		orders.put("ousername", rs.getString("ousername"));
		orderList.add(orders);
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(OrdersDao.class.getName()).log(Level.SEVERE, null, ex);
	    ex.printStackTrace();
	} finally {
	    DbConn.getAllClose(conn, ptst, rs);
	}
	return orderList;
    }

    public void batDel(String[] s) {
	this.bat("delete from orders where oid=?", s);
    }

    public DataField getMaxId() {
	String fieldArr = "max(id) counts";
	return getFirstData("select " + fieldArr + " from orders", "counts");
    }

    public int getMaxIdInt() {
	return getDataCount("select max(oid) from orders");
    }

    public boolean add(Map<String,String> orders) {
	Connection conn = null;
	PreparedStatement ps = null;
	boolean returnValue = true;
	//oid,otitle,odt,osenddt,olastupdatedt,ostatus,onum,ocount,oamountmoney,oprice,ouserid,ousername,pid,oproducttitle,opt,optint,mark
	String sql = "insert into orders(oid,otitle,odt,osenddt,olastupdatedt,ostatus,onum,ocount,oamountmoney,oprice,ouserid,ousername,pid,oproducttitle,opt,optint,mark) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	try {
	    conn = DBUtils.getConnection();
	    ps = conn.prepareStatement(sql);
	    ps.setInt(0,Integer.parseInt(orders.get("oId")));
	    ps.setString(1,orders.get("oTitle"));
	    ps.setTimestamp(2,Timestamp.valueOf(orders.get("oDt")));
	    ps.setTimestamp(3,Timestamp.valueOf(orders.get("oSendDt")));
	    ps.setTimestamp(4,Timestamp.valueOf(orders.get("oLastUpdateDt")));
	    ps.setInt(5,Integer.parseInt(orders.get("oStatus")));
	    ps.setString(6,orders.get("oNum"));
	    ps.setInt(7,Integer.parseInt(orders.get("oCount")));
	    ps.setFloat(8,Float.parseFloat(orders.get("oAmountMoney")));
	    ps.setFloat(9,Float.parseFloat(orders.get("oPrice")));
	    ps.setInt(10,Integer.parseInt(orders.get("ouserid")));
	    ps.setString(11,orders.get("ousername"));
	    if (ps.executeUpdate() != 1) {
		returnValue = false;
	    }
	} catch (SQLException e) {
	    Logger.getLogger(OrdersDao.class.getName()).log(Level.SEVERE, null, e);
	    e.printStackTrace();
	    return false;
	} finally {
	    DBUtils.closePrepareStatement(ps);
	    DBUtils.closeConnection(conn);
	}
	return returnValue;
    }

    public boolean update(String id, Map<String, String> orders) {
	Connection conn = null;
	PreparedStatement ps = null;
	boolean returnValue = true;
	String sql = "update orders set ";
	List<String> values = new ArrayList<String>();
	for (String key : orders.keySet()) {
	    sql += key + "=?,";
	    values.add(orders.get(key));
	}
	sql = sql.substring(0, sql.length() - 1);

	sql += "  where oId=?";
	try {
	    conn = DBUtils.getConnection();
	    ps = conn.prepareStatement(sql);
	    for (int i = 0; i < values.size(); i++) {
		ps.setString(i + 1, values.get(i));
	    }
	    ps.setInt(values.size() + 1, Integer.parseInt(id));
	    if (ps.executeUpdate() != 1) {
		returnValue = false;
	    }
	} catch (SQLException e) {
	    Logger.getLogger(OrdersDao.class.getName()).log(Level.SEVERE, null, e);
	    e.printStackTrace();
	    return false;
	} finally {
	    DBUtils.closePrepareStatement(ps);
	    DBUtils.closeConnection(conn);
	}
	return returnValue;
    }

    public boolean del(String id) throws ObjectNotFoundException, DatabaseException {
	return exe("delete from orders where oid=" + id);
    }

    public List<Map<String, String>> get_Limit(int currentpage, int pagesize, String where) {
	String str = "oid,otitle,odt,osenddt,olastupdatedt,ostatus,onum,ocount,oamountmoney,oprice,ouserid,ousername,pid,oproducttitle,opt,optint,mark";
	StringBuilder sql = new StringBuilder("select ");
	sql.append(str);
	sql.append(" from orders ");
	if (where != null && where != "") {
	    sql.append(" where " + where);
	}
	sql.append(" order by oid ");
	int offset = currentpage == 1 ? 0 : (currentpage - 1) * pagesize;
	sql.append(" limit " + offset + "," + pagesize);
	List<Map<String, String>> orderList = new ArrayList<Map<String, String>>();
	Connection conn = DbConn.getConn();
	PreparedStatement ptst = null;
	ResultSet rs = null;
	try {
	    ptst = conn.prepareStatement(sql.toString());
	    rs = ptst.executeQuery();
	    while (rs.next()) {
		Map<String, String> orders = new HashMap<String, String>();
		//oid,otitle,odt,osenddt,olastupdatedt,ostatus,onum,ocount,oamountmoney,oprice,ouserid,ousername,pid,oproducttitle,opt,optint,mark
		orders.put("oid", rs.getString("oid"));
		orders.put("otitle", rs.getString("otitle"));
		orders.put("odt", rs.getString("odt"));
		orders.put("osenddt", rs.getString("osenddt"));
		orders.put("olastupdatedt", rs.getString("olastupdatedt"));
		orders.put("ostatus", rs.getString("ostatus"));
		orders.put("onum", rs.getString("onum"));
		orders.put("ocount", rs.getString("ocount"));
		orders.put("oamountmoney", rs.getString("oamountmoney"));
		orders.put("oprice", rs.getString("oprice"));
		orders.put("ouserid", rs.getString("ouserid"));
		orders.put("ousername", rs.getString("ousername"));
		orderList.add(orders);
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(OrdersDao.class.getName()).log(Level.SEVERE, null, ex);
	    ex.printStackTrace();
	} finally {
	    DbConn.getAllClose(conn, ptst, rs);
	}
	return orderList;
    }

    public int getTotalCount() {
	return getDataCount("select count(1) from orders ");
    }
    
    /**
     * 
     */
    public String getNewProcode(){
	//获取精确到秒的当前时间,保证编码永远不会重复
	String date = DateUtil.getStringDate().replace("-", "").replace(":","").replace(" ", "").trim();
	//生成八位随机数
	List<String> code = new CodeUtils().generate(4, 1);
	String proCode = date+code.get(0);
	return proCode;
    }
}
