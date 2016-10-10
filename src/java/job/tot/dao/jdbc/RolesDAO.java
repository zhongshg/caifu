/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

import org.apache.commons.collections.bidimap.DualHashBidiMap;

import job.tot.db.DBUtils;
import job.tot.util.DbConn;
import job.tot.util.PageUtil;

/**
 *
 * @author Administrator
 */
public class RolesDAO {

    public List<Map<String, String>> getList(PageUtil pu) throws SQLException {
	List<Map<String, String>> rolesList = new ArrayList<Map<String, String>>();
	Connection conn = DBUtils.getConnection();
	PreparedStatement ptst = null;
	ResultSet rs = null;
	String sql = "select id,name,remark from roles order by id desc limit " + pu.getPageSize() * (0 < pu.getPage() ? pu.getPage() - 1 : 0) + "," + pu.getPageSize();
	try {
	    ptst = conn.prepareStatement(sql);
	    rs = ptst.executeQuery();
	    while (rs.next()) {
		Map<String, String> roles = new HashMap<String, String>();
		roles.put("id", rs.getString("id"));
		roles.put("name", rs.getString("name"));
		roles.put("remark", rs.getString("remark"));
		rolesList.add(roles);
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(RolesDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    DBUtils.closePrepareStatement(ptst);
	    DBUtils.closeResultSet(rs);
	    DBUtils.closeConnection(conn);
	}
	return rolesList;
    }

    public int getConut() throws SQLException {
	int count = 0;
	Connection conn = DBUtils.getConnection();
	PreparedStatement ptst = null;
	ResultSet rs = null;
	String sql = "select count(id) count from roles";
	try {
	    ptst = conn.prepareStatement(sql);
	    rs = ptst.executeQuery();
	    while (rs.next()) {
		count = rs.getInt("count");
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(RolesDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    DBUtils.closePrepareStatement(ptst);
	    DBUtils.closeResultSet(rs);
	    DBUtils.closeConnection(conn);
	}
	return count;
    }

    public Map<String, String> getByUsers(int rid) throws SQLException {
	Map<String, String> roles = new HashMap<String, String>();
	Connection conn = DBUtils.getConnection();
	PreparedStatement ptst = null;
	ResultSet rs = null;
	String sql = "select id,name,remark from roles where id=?";
	try {
	    ptst = conn.prepareStatement(sql);
	    ptst.setInt(1, rid);
	    rs = ptst.executeQuery();
	    if (rs.next()) {
		roles.put("id", rs.getString("id"));
		roles.put("name", rs.getString("name"));
		roles.put("remark", rs.getString("remark"));
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(RolesDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    DBUtils.closePrepareStatement(ptst);
	    DBUtils.closeResultSet(rs);
	    DBUtils.closeConnection(conn);
	}
	return roles;
    }

    public List<Map<String, String>> getList() throws SQLException {
	List<Map<String, String>> rolesList = new ArrayList<Map<String, String>>();
	Connection conn = DBUtils.getConnection();
	PreparedStatement ptst = null;
	ResultSet rs = null;
	String sql = "select id,name,remark from roles order by id ";
	try {
	    ptst = conn.prepareStatement(sql);
	    rs = ptst.executeQuery();
	    while (rs.next()) {
		Map<String, String> roles = new HashMap<String, String>();
		roles.put("id", rs.getString("id"));
		roles.put("name", rs.getString("name"));
		roles.put("remark", rs.getString("remark"));
		rolesList.add(roles);
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(RolesDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    DBUtils.closePrepareStatement(ptst);
	    DBUtils.closeResultSet(rs);
	    DBUtils.closeConnection(conn);
	}
	return rolesList;
    }

    public Map<String, String> getById(String roleid) throws SQLException {
	Connection conn = DBUtils.getConnection();
	PreparedStatement ptst = null;
	ResultSet rs = null;
	Map<String, String> roles = new HashMap<String, String>();
	String sql = "select id,name,remark from roles where id=?";
	try {
	    ptst = conn.prepareStatement(sql);
	    ptst.setString(1, roleid);
	    rs = ptst.executeQuery();
	    if (rs.next()) {
		roles = new HashMap<String, String>();
		roles.put("id", roleid);
		roles.put("name", rs.getString("name"));
		roles.put("remark", rs.getString("remark"));
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(RolesDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    DBUtils.closePrepareStatement(ptst);
	    DBUtils.closeResultSet(rs);
	    DBUtils.closeConnection(conn);
	}
	return roles;
    }

    public void add(Map<String, String> roles) throws SQLException {
	Connection conn = DBUtils.getConnection();
	PreparedStatement ptst = null;
	ResultSet rs = null;
	String sql = "insert into roles(id,name,remark) values (?,?,?)";
	try {
	    ptst = conn.prepareStatement(sql);
	    ptst.setInt(1, Integer.parseInt(roles.get("id")));
	    ptst.setString(2, roles.get("name"));
	    ptst.setString(3, roles.get("remark"));
	    ;
	    ptst.executeUpdate();
	} catch (SQLException ex) {
	    Logger.getLogger(RolesDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    DBUtils.closePrepareStatement(ptst);
	    DBUtils.closeResultSet(rs);
	    DBUtils.closeConnection(conn);
	}
    }

    public void update(Map<String, String> roles) throws SQLException {
	Connection conn = DBUtils.getConnection();
	PreparedStatement ptst = null;
	ResultSet rs = null;
	String sql = "update roles set name=?,remark=?,id=? where id=?";
	try {
	    ptst = conn.prepareStatement(sql);
	    ptst.setString(1, roles.get("name"));
	    ptst.setString(2, roles.get("remark"));
	    ptst.setInt(3, Integer.parseInt(roles.get("id")));
	    ptst.setInt(4, Integer.parseInt(roles.get("id")));
	    ptst.executeUpdate();
	} catch (SQLException ex) {
	    Logger.getLogger(RolesDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    DBUtils.closePrepareStatement(ptst);
	    DBUtils.closeResultSet(rs);
	    DBUtils.closeConnection(conn);
	}
    }

    public void delete(String id, Connection conn) throws SQLException {
	if (conn == null) {
	    conn = DBUtils.getConnection();
	}
	PreparedStatement ptst = null;
	String sql = "delete from roles where id=?";
	try {
	    ptst = conn.prepareStatement(sql);
	    ptst.setInt(1, Integer.parseInt(id));
	    ptst.executeUpdate();
	    ptst.close();
	} catch (SQLException ex) {
	    Logger.getLogger(RolesDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    DBUtils.closePrepareStatement(ptst);
	    DBUtils.closeConnection(conn);
	}
    }
}
