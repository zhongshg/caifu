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
import job.tot.global.GlobalEnum;
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
	System.out.println("select " + fieldArr + " from assets_out where " + where);
	return getFirstData("select " + fieldArr + " from assets_out where " + where, fieldArr);
    }

    public List<Map<String, String>> searchBywhere(String where) throws SQLException {
	String fieldArr = "id,uid,amount,type,ts,oid";
	StringBuffer sql = new StringBuffer("select ");
	sql.append(fieldArr);
	sql.append(" from assets_out  ");
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
		// id,uid,amount,type,ts,oid
		Map<String, String> assets_out = new HashMap<String, String>();
		assets_out.put("uid", rs.getString("uid"));
		assets_out.put("amount", rs.getString("amount"));
		assets_out.put("type", GlobalEnum.ASSETS.get(rs.getString("type")));
		assets_out.put("dr", GlobalEnum.ASSETS_FLAG.get(rs.getString("dr")));
		assets_out.put("oid", rs.getString("oid"));
		assets_out.put("ts", rs.getString("ts"));
		assetsList.add(assets_out);
	    }
	} catch (SQLException ex) {
	    log.log(Level.SEVERE, null, ex);
	    ex.printStackTrace();
	} finally {
	    DBUtils.closePrepareStatement(ptst);
	    DBUtils.closeResultSet(rs);
	    DBUtils.closeConnection(conn);
	}
	return assetsList;
    }

    /**
     * assets_out id,uid,amount,type,ts,oid
     * 
     */
    public boolean add(Map<String, String> assets_out, Connection conn) {
	PreparedStatement ps = null;
	boolean returnValue = true;
	// id,uid,amount,type,ts,oid
	String sql = "insert into assets_out(uid,amount,type,oid,dr,remark) values(?,?,?,?,?,?)";
	try {
	    ps = conn.prepareStatement(sql);
	    ps.setString(1, assets_out.get("uid"));
	    ps.setString(2, assets_out.get("amount"));
	    ps.setString(3, assets_out.get("type"));
	    ps.setString(4, assets_out.get("oid"));
	    ps.setInt(5, Integer.parseInt(assets_out.get("dr")));
	    ps.setString(6, assets_out.get("remark"));
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

    // public boolean del(String id) throws ObjectNotFoundException, DatabaseException {
    // return exe("delete from assets_out where id=" + id);
    // }

    public int getTotalCount(String uid) {
	return getDataCount("select count(1) from assets_out where id=" + uid);
    }

    public List<Map<String, String>> get_Limit(int currentpage, int pagesize, String where) throws SQLException {
	String str = "id,amount,type,dr,ts,oid";
	StringBuilder sql = new StringBuilder("select ");
	sql.append(str);
	sql.append(" from assets_out ");
	if (where != null && where != "") {
	    sql.append(" where " + where);
	}
	sql.append(" order by ts ");
	int offset = currentpage == 1 ? 0 : (currentpage - 1) * pagesize;
	sql.append(" limit " + offset + "," + pagesize);
	List<Map<String, String>> assets_outList = new ArrayList<Map<String, String>>();
	Connection conn = DBUtils.getConnection();
	PreparedStatement ptst = null;
	ResultSet rs = null;
	try {
	    ptst = conn.prepareStatement(sql.toString());
	    rs = ptst.executeQuery();
	    while (rs.next()) {
		Map<String, String> assets_out = new HashMap<String, String>();
		// id,uid,amount,type,remark,dr,ts,oid
		assets_out.put("id", rs.getString("id"));
		assets_out.put("amount", rs.getString("amount"));
		assets_out.put("type", GlobalEnum.ASSETS.get(rs.getString("type")));
		assets_out.put("dr", GlobalEnum.ASSETS_FLAG.get(rs.getString("dr")));
		assets_out.put("ts", rs.getString("ts").substring(0, 19));
		assets_out.put("oid", rs.getString("oid"));
		assets_outList.add(assets_out);
	    }
	} catch (SQLException ex) {
	    log.log(Level.SEVERE, null, ex);
	    ex.printStackTrace();
	} finally {
	    DBUtils.closePrepareStatement(ptst);
	    DBUtils.closeResultSet(rs);
	    DBUtils.closeConnection(conn);
	}
	return assets_outList;
    }

    public boolean update(Connection conn, Map<String, String> assets_out) {
	// Connection conn = null;
	Statement ps = null;
	boolean returnValue = true;
	String sql = "update assets_out set ";
	List<String> values = new ArrayList<String>();
	for (String key : assets_out.keySet()) {
	    if (key.equals("id")) {
		continue;
	    }
	    sql += key + "=" + assets_out.get(key) + ",";
	}
	sql = sql.substring(0, sql.length() - 1);
	sql += "  where id=" + assets_out.get("id");
	try {
	    if (conn == null) {
		conn = DBUtils.getConnection();
	    }
	    System.out.println(sql);
	    ps = conn.createStatement();
	    int count = ps.executeUpdate(sql);
	    System.out.println("count=" + count);
	    if (count != 1) {
		System.out.println("程序B" + returnValue);
		returnValue = false;
		System.out.println("程序C" + returnValue);
	    }
	} catch (SQLException e) {
	    DBUtils.closeConnection(conn);
	    log.log(Level.SEVERE, null, e);
	    e.printStackTrace();
	    return false;
	} finally {
	    DBUtils.closeStatement(ps);
	}
	System.out.println("程序D" + returnValue);
	return returnValue;
    }
}
