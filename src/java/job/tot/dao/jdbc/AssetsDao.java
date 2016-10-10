package job.tot.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import job.tot.util.DbConn;

/**
 * @author zhongshg
 * @since 2016年9月21日 15:51:19 资产数据库操作DAO类
 */
public class AssetsDao extends AbstractDao {
    private static Logger log = Logger.getLogger(AssetsDao.class.getName());

    /**
     * @param col
     *            where判断条件字段
     * @param id
     *            col对应的值
     * @param fieldArr
     *            要查询的字段
     */
    public DataField getByCol(String where) {
	String fieldArr = "id,assets,balance,wealth";
	String sql = "select " + fieldArr + " from assets where " + where;
	System.out.println(sql);
	return getData(sql, fieldArr).get(0);
    }    
    
    public DataField get(int id) {
        String fieldArr = "id,assets,balance,wealth";
        return getFirstData("select " + fieldArr + " from assets where id=" + id, fieldArr);
    }
    

    public List<Map<String, String>> searchBywhere(String where) throws SQLException {
	String fieldArr = "id,assets,balance,wealth";
	StringBuffer sql = new StringBuffer("select ");
	sql.append(fieldArr);
	sql.append(" from assets  ");
	if (where != null && where != "") {
	    sql.append("where " + where);
	}
	List<Map<String, String>> assetsList = new ArrayList<Map<String, String>>();
	Connection conn = DBUtils.getConnection();
	PreparedStatement ptst = null;
	ResultSet rs = null;
	try {
	    ptst = conn.prepareStatement(sql.toString());
	    rs = ptst.executeQuery();
	    while (rs.next()) {
		// id,assets,balance,wealth
		Map<String, String> assets = new HashMap<String, String>();
		assets.put("id", rs.getString("id"));
		assets.put("assets", rs.getString("assets"));
		assets.put("balance", rs.getString("balance"));
		assets.put("wealth", rs.getString("wealth"));
		assetsList.add(assets);
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(AssetsDao.class.getName()).log(Level.SEVERE, null, ex);
	    ex.printStackTrace();
	} finally {
	    DBUtils.closePrepareStatement(ptst);
	    DBUtils.closeResultSet(rs);
	    DBUtils.closeConnection(conn);
	}
	return assetsList;
    }

    public boolean add(String id, Connection conn) {
	PreparedStatement ps = null;
	boolean returnValue = true;
	// id,assets,balance,wealth
	String sql = "insert into assets(id) values(?)";
	try {
	    ps = conn.prepareStatement(sql);
	    ps.setString(1, id);
	    if (ps.executeUpdate() != 1) {
		returnValue = false;
	    }
	} catch (SQLException e) {
	    DBUtils.closeConnection(conn);
	    Logger.getLogger(AssetsDao.class.getName()).log(Level.SEVERE, null, e);
	    e.printStackTrace();
	    returnValue = false;
	    return returnValue;
	} finally {
	    DBUtils.closePrepareStatement(ps);
	}
	return returnValue;
    }

    public boolean del(String id) throws ObjectNotFoundException, DatabaseException {
	return exe("delete from assets where id=" + id);
    }

    public boolean update(Connection conn, Map<String, String> assets) throws ObjectNotFoundException, DatabaseException {
	try {
	    String sql = "update assets set ";
	    for (String key : assets.keySet()) {
		if ("id".equals(key)) {
		    continue;
		}
		sql += key;
		sql += "=";
		sql += assets.get(key);
		sql += ",";
	    }
	    sql = sql.substring(0, sql.length() - 1);
	    sql += " where id=" + assets.get("id");
	    Statement stmt = null;
	    boolean returnValue = false;
	    try {
		if (conn == null) {
		    conn = DBUtils.getConnection();
		}
		stmt = conn.createStatement();
		if (stmt.executeUpdate(sql.toString()) != 0) {
		    returnValue = true;
		} else {
		    returnValue = false;
		}
	    } catch (SQLException e) {
		DBUtils.closeConnection(conn);
		log.log(Level.SEVERE, "Sql Exception Error:", e);
		throw new DatabaseException("Got Exception on Call Medthod exe in tot.dao.AbstractDao");
	    } finally {
		DBUtils.closeStatement(stmt);
	    }
	    return returnValue;
	} catch (DatabaseException e) {
	    e.printStackTrace();
	}
	return false;
    }

    public boolean updateAssetsAndBalance(Connection conn, String assets, String balance, String id) throws ObjectNotFoundException, DatabaseException {
	try {
	    String sql = "update assets set assets=assets+" + assets;
	    if (balance != null) {
		sql += ",balance=balance+" + balance;
	    }
	    sql += " where id=" + id;
	    Statement stmt = null;
	    boolean returnValue = false;
	    try {
		if (conn == null) {
		    conn = DBUtils.getConnection();
		}
		stmt = conn.createStatement();
		if (stmt.executeUpdate(sql.toString()) != 0) {
		    returnValue = true;
		} else {
		    returnValue = false;
		}
	    } catch (SQLException e) {
		DBUtils.closeConnection(conn);
		log.log(Level.SEVERE, "Sql Exception Error:", e);
		throw new DatabaseException("Got Exception on Call Medthod exe in tot.dao.AbstractDao");
	    } finally {
		DBUtils.closeStatement(stmt);
	    }
	    return returnValue;
	} catch (DatabaseException e) {
	    e.printStackTrace();
	}
	return false;
    }

    public List<Map<String, String>> get_Limit(int currentpage, int pagesize, String uid) throws SQLException {
	String str = "t.id,t.amount,t.type,t.dr,t.ts,t.oid";
	String ostr = "id,amount,type,dr,ts,oid";
	StringBuilder sql = new StringBuilder("select ");
	sql.append(str);
	sql.append(" from (select ").append(ostr);
	sql.append(" from assets_in where uid=").append(uid);
	sql.append(" union all ");
	sql.append(" select ").append(ostr);
	sql.append(" from assets_out where uid=").append(uid).append(") t");
	sql.append(" order by t.ts ");
	int offset = currentpage == 1 ? 0 : (currentpage - 1) * pagesize;
	sql.append(" limit " + offset + "," + pagesize);
	List<Map<String, String>> assets_inList = new ArrayList<Map<String, String>>();
	Connection conn = DBUtils.getConnection();
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
		String type = rs.getString("type");
		assets_in.put("type", GlobalEnum.ASSETS.get(type));
		assets_in.put("dr", GlobalEnum.ASSETS_FLAG.get(rs.getString("dr")));
		assets_in.put("ts", rs.getString("ts").substring(0, 19));
		assets_in.put("oid", rs.getString("oid"));
		assets_inList.add(assets_in);
	    }
	} catch (SQLException ex) {
	    log.log(Level.SEVERE, null, ex);
	    ex.printStackTrace();
	} finally {
	    DBUtils.closePrepareStatement(ptst);
	    DBUtils.closeResultSet(rs);
	    DBUtils.closeConnection(conn);
	}
	return assets_inList;
    }

    public int getTotalCount(String uid) {
	String str = "count(1)";
	String ostr = "id";
	StringBuilder sql = new StringBuilder("select count(1)");
	sql.append(" from (select id ");
	sql.append(" from assets_in where uid=").append(uid);
	sql.append(" uinion all ");
	sql.append(" select id");
	sql.append(" from assets_out where uid=").append(uid).append(") t");
	return getDataCount(sql.toString());
    }
}
