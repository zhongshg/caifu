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
import job.tot.util.DbConn;

/**
 * @author zhongshg
 * @since 2016年9月21日 15:51:19 资产支出数据库操作DAO类
 */
public class AssetsOUTDao extends AbstractDao {
    private static Logger log = Logger.getLogger(AssetsOUTDao.class.getName());

    /**
     * @param col
     *            where判断条件字段
     * @param id
     *            col对应的值
     * @param fieldArr
     *            要查询的字段
     */
    public DataField getByCol(String where) {
	String fieldArr = "id,uid,amount,type,ts,oid";
	return getFirstData("select " + fieldArr + " from assets_out where " + where, fieldArr);
    }

    public List<Map<String, String>> searchBywhere(String where) {
	String fieldArr = "id,uid,amount,type,ts,oid";
	StringBuffer sql = new StringBuffer("select ");
	sql.append(fieldArr);
	sql.append(" from assets_out  ");
	if (where != null && where != "") {
	    sql.append("where " + where);
	}
	List<Map<String, String>> assetsList = new ArrayList<Map<String, String>>();
	Connection conn = DbConn.getConn();
	PreparedStatement ptst = null;
	ResultSet rs = null;
	try {
	    ptst = conn.prepareStatement(sql.toString());
	    rs = ptst.executeQuery();
	    while (rs.next()) {
		// id,uid,amount,type,ts,oid
		Map<String, String> assets_out = new HashMap<String, String>();
		assets_out.put("uid", rs.getString("uid"));
		assets_out.put("amount", rs.getString("amount"));
		assets_out.put("type", rs.getString("type"));
		assets_out.put("oid", rs.getString("oid"));
		assets_out.put("ts", rs.getString("ts"));
		assetsList.add(assets_out);
	    }
	} catch (SQLException ex) {
	    log.log(Level.SEVERE, null, ex);
	    ex.printStackTrace();
	} finally {
	    DbConn.getAllClose(conn, ptst, rs);
	}
	return assetsList;
    }

    /**
     * assets_out
     * 	id,uid,amount,type,ts,oid
     * 
     * */
    public boolean add(Map<String,String> assets_out, Connection conn) {
	PreparedStatement ps = null;
	boolean returnValue = true;
	// id,uid,amount,type,ts,oid
	String sql = "insert into assets_out(uid,amount,type,oid) values(?,?,?,?)";
	try {
	    ps = conn.prepareStatement(sql);
	    ps.setString(1, assets_out.get("uid"));
	    ps.setString(2, assets_out.get("amount"));
	    ps.setString(3, assets_out.get("type"));
	    ps.setString(4, assets_out.get("oid"));
	    if (ps.executeUpdate() != 1) {
		returnValue = false;
	    }
	} catch (SQLException e) {
	    DBUtils.closePrepareStatement(ps);
	    DBUtils.closeConnection(conn);
	    log.log(Level.SEVERE, null, e);
	    e.printStackTrace();
	    returnValue = false;
	    return returnValue;
	} finally {
	    DBUtils.closePrepareStatement(ps);
	}
	return returnValue;
    }

    public boolean del(String id) throws ObjectNotFoundException, DatabaseException {
	return exe("delete from assets_out where id=" + id);
    }

//    public boolean update(Map<String, String> assets_out) throws ObjectNotFoundException, DatabaseException {
//	try {
//	    String sql = "update assets_out set ";
//	    for (String key : assets_out.keySet()) {
//		sql +=key;
//		sql += "=";
//		sql += assets_out.get(key);
//		sql += ",";
//	    }
//	    sql =  sql.substring(0, sql.length() - 1);
//	    sql +=" where uid=" + assets_out.get("uid");
//	    return exe(sql.toString());
//	} catch (ObjectNotFoundException e) {
//	    e.printStackTrace();
//	} catch (DatabaseException e) {
//	    e.printStackTrace();
//	}
//	return false;
//    }
}
