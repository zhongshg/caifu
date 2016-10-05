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
 * @since 2016年9月21日 15:51:19 资产数据库操作DAO类
 */
public class AssetsDao extends AbstractDao {
    private static Log log = LogFactory.getLog(AssetsDao.class);

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
	return getFirstData("select " + fieldArr + " from assets where " + where, fieldArr);
    }

    public List<Map<String, String>> searchBywhere(String where) {
	String fieldArr = "id,assets,balance,wealth";
	StringBuffer sql = new StringBuffer("select ");
	sql.append(fieldArr);
	sql.append(" from assets  ");
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
	    DbConn.getAllClose(conn, ptst, rs);
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
	    DBUtils.closePrepareStatement(ps);
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

    public boolean update(Map<String, String> assets) throws ObjectNotFoundException, DatabaseException {
	try {
	    String sql = "update assets set ";
	    for (String key : assets.keySet()) {
		sql +=key;
		sql += "=";
		sql += assets.get(key);
		sql += ",";
	    }
	    sql =  sql.substring(0, sql.length() - 1);
	    sql +=" where id=" + assets.get("id");
	    System.out.println(sql.toString());
	    return exe(sql.toString());
	} catch (ObjectNotFoundException e) {
	    e.printStackTrace();
	} catch (DatabaseException e) {
	    e.printStackTrace();
	}
	return false;
    }
}
