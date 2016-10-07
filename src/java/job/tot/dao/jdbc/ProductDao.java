/*
 * MessageDaoImplJDBC.java
 *
 * Created on 2007��6��21��, ����3:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package job.tot.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
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
import job.tot.util.CodeUtils;
import job.tot.util.DateUtil;
import job.tot.util.DbConn;

/**
 *
 * @author Administrator
 */
public class ProductDao extends AbstractDao {
    Logger log = Logger.getLogger(ProductDao.class.getName());
    /**
     * @param col
     *            where判断条件字段
     * @param id
     *            col对应的值
     * @param fieldArr
     *            要查询的字段
     */
   // private String fieldArr = "id,procode,proname,propertys,picpath,ts,starttime,endtime,price,stock";
    public DataField getByCol(String where, String fieldArr) {
	if(fieldArr==null){
	    fieldArr = "id,procode,proname,propertys,picpath,ts,starttime,endtime,price,stock";
	}
	return getFirstData("select " + fieldArr + " from product where " + where, fieldArr);
    }

    public List<Map<String, String>> searchBywhere(String where, String fieldArr) {
	if (fieldArr == null) {
	    fieldArr = "id,procode,proname,propertys,picpath,ts,starttime,endtime,price,stock";
	}
	StringBuffer sql = new StringBuffer("select ");
	sql.append(fieldArr);
	sql.append(" from product  ");
	if (where != null && where != "") {
	    sql.append("where " + where);
	}
	List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
	Connection conn = DbConn.getConn();
	PreparedStatement ptst = null;
	ResultSet rs = null;
	try {
	    ptst = conn.prepareStatement(sql.toString());
	    rs = ptst.executeQuery();
	    while (rs.next()) {
		//id,procode,proname,propertys,picpath,ts
		Map<String, String> products = new HashMap<String, String>();
		products.put("id", rs.getString("id"));
		products.put("procode", rs.getString("procode"));
		products.put("proname", rs.getString("proname"));
		products.put("propertys", rs.getString("propertys"));
		products.put("picpath", rs.getString("picpath"));
		products.put("price", rs.getString("price"));
		products.put("endtime", rs.getString("endtime"));
		products.put("stock", rs.getString("stock"));
		products.put("ts", rs.getString("ts"));
		userList.add(products);
	    }
	} catch (SQLException ex) {
	    log.log(Level.SEVERE, null, ex);
	    ex.printStackTrace();
	} finally {
	    DbConn.getAllClose(conn, ptst, rs);
	}
	return userList;
    }

    public void batDel(String[] s) {
	this.bat("delete from product where id=?", s);
    }

    public DataField getMaxId() {
	String fieldArr = "max(id) counts";
	return getFirstData("select " + fieldArr + " from product", "counts");
    }

    public int getMaxIdInt() {
	return getDataCount("select max(id) from product");
    }

    public boolean add(String proCode,String proname, String propertys, String picpath,String starttime,String endtime,String price,String stock) {
	Connection conn = null;
	PreparedStatement ps = null;
	boolean returnValue = true;
	if(proCode==null||proCode==""){
	    proCode = getNewProcode();
	}
	String field = "procode,proname,propertys,picpath,starttime,endtime,price,stock";
	String sql = "insert into product("+field+") values(?,?,?,?,?,?,?,?)";
	try {
	    conn = DBUtils.getConnection();
	    ps = conn.prepareStatement(sql);
	    //id,procode,proname,propertys,picpath,ts,starttime,endtime,price,stock
	    ps.setString(1, proCode);
	    ps.setString(2, proname);
	    ps.setString(3, propertys);
	    ps.setString(4, picpath);
	    ps.setString(5, starttime );
	    ps.setString(6, endtime);
	    ps.setFloat(7, Float.parseFloat(price));
	    ps.setInt(8, Integer.parseInt(stock));
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

    public boolean update(String id, Map<String, String> products) {
	Connection conn = null;
	PreparedStatement ps = null;
	boolean returnValue = true;
	String sql = "update product set ";
	List<String> values = new ArrayList<String>();
	for (String key : products.keySet()) {
	    sql += key + "=?,";
	    values.add(products.get(key));
	}
	sql = sql.substring(0, sql.length() - 1);

	sql += "  where id=?";
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
	return exe("delete from product where id=" + id);
    }

    public List<Map<String, String>> get_Limit(int currentpage, int pagesize, String where) {
	String fieldArr = "id,procode,proname,propertys,picpath,ts,starttime,endtime,price,stock";
	StringBuilder sql = new StringBuilder("select ");
	sql.append(fieldArr);
	sql.append(" from product ");
	if (where != null && where != "") {
	    sql.append(" where " + where);
	}
	sql.append(" order by id ");
	int offset = currentpage == 1 ? 0 : (currentpage - 1) * pagesize;
	sql.append(" limit " + offset + "," + pagesize);
	List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
	Connection conn = DbConn.getConn();
	PreparedStatement ptst = null;
	ResultSet rs = null;
	try {
	    ptst = conn.prepareStatement(sql.toString());
	    rs = ptst.executeQuery();
	    while (rs.next()) {
		//procode,proname,propertys,picpath,starttime,endtime,price,stock
		Map<String, String> products = new HashMap<String, String>();
		products.put("id", rs.getString("id"));
		products.put("procode", rs.getString("procode"));
		products.put("proname", rs.getString("proname"));
		products.put("propertys", rs.getString("propertys"));
		products.put("picpath", rs.getString("picpath"));
		products.put("price", rs.getString("price"));
		products.put("endtime", rs.getString("endtime"));
		products.put("stock", rs.getString("stock"));
		products.put("ts", rs.getString("ts"));
		userList.add(products);
	    }
	} catch (SQLException ex) {
	    log.log(Level.SEVERE, null, ex);
	    ex.printStackTrace();
	} finally {
	    DbConn.getAllClose(conn, ptst, rs);
	}
	return userList;
    }

    public int getTotalCount() {
	return getDataCount("select count(1) from product ");
    }

    /**
     * 生成新商品号
     * @throws  
     * 
     */
    public String getNewProcode(){
	//获取精确到秒的当前时间,保证编码永远不会重复
	//String date = DateUtil.getStringDateShort().replace("-", "").trim();
	//生成四位随机数
	List<String> code = new CodeUtils().generate_list(4, 1);
	String proCode = code.get(0);
	return proCode;
    }
}
