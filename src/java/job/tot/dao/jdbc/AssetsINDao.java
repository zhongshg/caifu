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
import job.tot.global.GlobalEnum;
import job.tot.util.CodeUtils;
import job.tot.util.DateUtil;
import job.tot.util.DbConn;

/**
 * @author zhongshg
 * @since 2016年9月21日 15:51:19 资产收入数据库操作DAO类
 */
public class AssetsINDao extends AbstractDao {
    private static Logger log = Logger.getLogger(AssetsINDao.class.getName());

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
	return getFirstData("select " + fieldArr + " from assets_in where " + where, fieldArr);
    }

    public List<Map<String, String>> searchBywhere(String where) {
	String fieldArr = "id,uid,amount,type,ts,oid";
	StringBuffer sql = new StringBuffer("select ");
	sql.append(fieldArr);
	sql.append(" from assets_in  ");
	if (where != null && where != "") {
	    sql.append("where " + where);
	}
	List<Map<String, String>> assets_inList = new ArrayList<Map<String, String>>();
	Connection conn = DbConn.getConn();
	PreparedStatement ptst = null;
	ResultSet rs = null;
	try {
	    ptst = conn.prepareStatement(sql.toString());
	    rs = ptst.executeQuery();
	    while (rs.next()) {
		// id,assets_in,balance,wealth
		Map<String, String> assets_in = new HashMap<String, String>();
		assets_in.put("id", rs.getString("id"));
		assets_in.put("uid", rs.getString("uid"));
		assets_in.put("amount", rs.getString("amount"));
		assets_in.put("type", GlobalEnum.ASSETS.get(rs.getString("type")));
		assets_in.put("dr", GlobalEnum.ASSETS_FLAG.get(rs.getString("dr")));
		assets_in.put("oid", rs.getString("oid"));
		assets_in.put("ts", rs.getString("ts"));
		assets_inList.add(assets_in);
	    }
	} catch (SQLException ex) {
	    log.log(Level.SEVERE, null, ex);
	    ex.printStackTrace();
	} finally {
	    DbConn.getAllClose(conn, ptst, rs);
	}
	return assets_inList;
    }

    public boolean add(Map<String, String> assets_in, Connection conn) {
	PreparedStatement ps = null;
	boolean returnValue = true;
	// id,assets_in,balance,wealth
	String sql = "insert into assets_in(uid,amount,type,oid,dr) values(?,?,?,?,?)";
	try {
	    ps = conn.prepareStatement(sql);
	    ps.setString(1, assets_in.get("uid"));
	    ps.setString(2, assets_in.get("amount"));
	    ps.setString(3, assets_in.get("type"));
	    ps.setString(4, assets_in.get("oid"));
	    ps.setString(5, assets_in.get("dr"));
	    if (ps.executeUpdate() != 1) {
		returnValue = false;
	    }
	} catch (SQLException e) {
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

//    public boolean del(String id) throws ObjectNotFoundException, DatabaseException {
//	return exe("delete from assets_in where id=" + id);
//    }

    public boolean update(Map<String, String> assets_in) throws ObjectNotFoundException, DatabaseException {
	try {
	    String sql = "update assets_in set ";
	    for (String key : assets_in.keySet()) {
		if (key.equals("id")) {
		    continue;
		}
		sql += key;
		sql += "=";
		sql += assets_in.get(key);
		sql += ",";
	    }
	    sql = sql.substring(0, sql.length() - 1);
	    sql += " where id=" + assets_in.get("id");
	    System.out.println(sql.toString());
	    return exe(sql.toString());
	} catch (ObjectNotFoundException e) {
	    e.printStackTrace();
	} catch (DatabaseException e) {
	    e.printStackTrace();
	}
	return false;
    }

    public int getTotalCount(String uid) {
	return getDataCount("select count(1) from assets_in where id=" + uid);
    }

    public List<Map<String, String>> get_Limit(int currentpage, int pagesize, String uid) {
	String str = "id,amount,type,dr,ts,oid";
	StringBuilder sql = new StringBuilder("select ");
	sql.append(str);
	sql.append(" from assets_in ");
	sql.append(" where uid=" + uid);
	sql.append(" order by ts ");
	int offset = currentpage == 1 ? 0 : (currentpage - 1) * pagesize;
	sql.append(" limit " + offset + "," + pagesize);
	List<Map<String, String>> assets_inList = new ArrayList<Map<String, String>>();
	Connection conn = DbConn.getConn();
	PreparedStatement ptst = null;
	ResultSet rs = null;
	try {
	    ptst = conn.prepareStatement(sql.toString());
	    rs = ptst.executeQuery();
	    while (rs.next()) {
		Map<String, String> assets_in = new HashMap<String, String>();
		// id,uid,amount,type,remark,dr,ts,oid
		assets_in.put("id", rs.getString("id"));
		assets_in.put("amount", rs.getString("amount"));
		assets_in.put("type", GlobalEnum.ASSETS.get(rs.getString("type")));
		assets_in.put("dr", GlobalEnum.ASSETS_FLAG.get(rs.getString("dr")));
		assets_in.put("ts", rs.getString("ts").substring(0, 19));
		assets_in.put("oid", rs.getString("oid"));
		assets_inList.add(assets_in);
	    }
	} catch (SQLException ex) {
	    log.log(Level.SEVERE, null, ex);
	    ex.printStackTrace();
	} finally {
	    DbConn.getAllClose(conn, ptst, rs);
	}
	return assets_inList;
    }
}
