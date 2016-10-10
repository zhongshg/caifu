package job.tot.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import job.tot.bean.DataField;
import job.tot.dao.AbstractDao;
import job.tot.db.DBUtils;
import job.tot.exception.DatabaseException;
import job.tot.exception.ObjectNotFoundException;
import job.tot.global.GlobalEnum;
import job.tot.util.CodeUtils;
import job.tot.util.DateUtil;
import job.tot.util.DbConn;

/**
 * @author zhongshg
 * @since 2016年9月21日 15:51:19 
 * 订单数据库操作DAO类
 */
public class OrdersDao extends AbstractDao {
    private static Logger log = Logger.getLogger(OrdersDao.class.getName());

    /**
     * @param col
     *            where判断条件字段
     * @param id
     *            col对应的值
     * @param fieldArr
     *            要查询的字段
     */
    public DataField getByCol(String where) {
	String fieldArr = "oid,otitle,odt,osenddt,olastupdatedt,ostatus,onum,ocount,oamountmoney,oprice,ouserid,ousername,pid,oproducttitle,opt,optint,mark,pname";
	return getFirstData("select " + fieldArr + " from orders where " + where, fieldArr);
    }

    public List<Map<String, String>> searchBywhere(String where) throws SQLException {
	String fieldArr = "oid,otitle,odt,osenddt,olastupdatedt,ostatus,onum,ocount,oamountmoney,oprice,ouserid,ousername,pid,oproducttitle,opt,optint,mark,pname";
	StringBuffer sql = new StringBuffer("select ");
	sql.append(fieldArr);
	sql.append(" from orders  ");
	if (where != null && where != "") {
	    sql.append("where " + where);
	}
	List<Map<String, String>> orderList = new ArrayList<Map<String, String>>();
	Connection conn = DBUtils.getConnection();
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
		orders.put("odt", rs.getString("odt").substring(0, 19));
		orders.put("osenddt", rs.getString("osenddt")==null?"":rs.getString("osenddt").substring(0, 19));
		orders.put("olastupdatedt", rs.getString("olastupdatedt").substring(0, 19));
		orders.put("ostatus", GlobalEnum.ORDERSTATUS.get(rs.getString("ostatus")));
		orders.put("onum", rs.getString("onum"));
		orders.put("ocount", rs.getString("ocount"));
		orders.put("oamountmoney", rs.getString("oamountmoney"));
		orders.put("oprice", rs.getString("oprice"));
		orders.put("ouserid", rs.getString("ouserid"));
		orders.put("ousername", rs.getString("ousername"));
		orders.put("pname", rs.getString("pname"));
		orderList.add(orders);
	    }
	} catch (SQLException ex) {
	    log.log(Level.SEVERE, null, ex);
	    ex.printStackTrace();
	} finally {
	    DBUtils.closePrepareStatement(ptst);
	    DBUtils.closeResultSet(rs);
	    DBUtils.closeConnection(conn);
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

    public boolean add(Connection conn,Map<String,String> orders) throws SQLException {
	if(conn==null){
	    conn = DBUtils.getConnection();
	}
	//Connection conn = null;
	PreparedStatement ps = null;
	boolean returnValue = true;
	//oid,otitle,odt,osenddt,olastupdatedt,ostatus,onum,ocount,oamountmoney,oprice,ouserid,ousername,pid,oproducttitle,opt,optint,mark
	String sql = "insert into orders(odt,olastupdatedt,ostatus,onum,ocount,oamountmoney,oprice,ouserid,ousername,pid,pName) values(?,?,?,?,?,?,?,?,?,?,?)";
	try {
	   // conn = DBUtils.getConnection();
	    ps = conn.prepareStatement(sql);
	    ps.setString(1,orders.get("oDt"));
	    ps.setString(2,orders.get("oLastUpdateDt"));
	    ps.setInt(3,0);
	    ps.setString(4,orders.get("oNum"));
	    ps.setString(5,orders.get("oCount"));
	    ps.setFloat(6,Float.parseFloat(orders.get("oAmountMoney")));
	    ps.setString(7,orders.get("oPrice"));
	    ps.setInt(8,Integer.parseInt(orders.get("ouserid")));
	    ps.setString(9,orders.get("ousername"));
	    ps.setString(10,orders.get("pid"));
	    ps.setString(11,orders.get("pName"));
	    if (ps.executeUpdate() != 1) {
		returnValue = false;
	    }
	} catch (SQLException e) {
	    DBUtils.closeConnection(conn);
	    log.log(Level.SEVERE, null, e);
	    e.printStackTrace();
	    return false;
	} finally {
	    DBUtils.closePrepareStatement(ps);
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
	    log.log(Level.SEVERE, null, e);
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

    public List<Map<String, String>> get_Limit(int currentpage, int pagesize, String where) throws SQLException {
	String str = "oid,otitle,odt,osenddt,olastupdatedt,ostatus,onum,ocount,oamountmoney,oprice,ouserid,ousername,pid,oproducttitle,opt,optint,mark,pname";
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
	Connection conn = DBUtils.getConnection();
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
		orders.put("odt", rs.getString("odt")!=null?rs.getString("odt").substring(0, 19):null);
		orders.put("osenddt", rs.getString("osenddt")!=null?rs.getString("osenddt").substring(0, 19):null);
		orders.put("olastupdatedt", rs.getString("olastupdatedt")!=null?rs.getString("olastupdatedt").substring(0, 19):null);
		orders.put("ostatus", GlobalEnum.ORDERSTATUS.get(rs.getString("ostatus")));
		orders.put("onum", rs.getString("onum"));
		orders.put("ocount", rs.getString("ocount"));
		orders.put("oamountmoney", rs.getString("oamountmoney"));
		orders.put("oprice", rs.getString("oprice"));
		orders.put("ouserid", rs.getString("ouserid"));
		orders.put("ousername", rs.getString("ousername"));
		orders.put("pname", rs.getString("pname"));
		orderList.add(orders);
	    }
	} catch (SQLException ex) {
	    log.log(Level.SEVERE, null, ex);
	    ex.printStackTrace();
	} finally {
	    DBUtils.closePrepareStatement(ptst);
	    DBUtils.closeResultSet(rs);
	    DBUtils.closeConnection(conn);
	}
	return orderList;
    }

    public int getTotalCount() {
	return getDataCount("select count(1) from orders ");
    }
    
    /**
     * 获取新订单编码
     * 格式为
     * 当前时间(精确到秒)+随机4位号码
     */
    public String getNewProcode(){
	//获取精确到秒的当前时间,保证编码永远不会重复
	String date = DateUtil.getStringDate().split(" ")[1].replace(":", "");
	//生成八位随机数
	List<String> code = new CodeUtils().generate_list(4, 1);
	String proCode = date+code.get(0);
	return proCode;
    }
}
