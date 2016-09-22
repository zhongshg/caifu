/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.dao;

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
import wap.wx.util.DbConn;
import wap.wx.util.PageUtil;

/**
 *
 * @author Administrator
 */
public class RolesDAO {

    public List<Map<String, String>> getList(PageUtil pu) {
        List<Map<String, String>> rolesList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
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
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return rolesList;
    }

    public int getConut() {
        int count = 0;
        Connection conn = DbConn.getConn();
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
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }

    public Map<String, String> getByUsers(Map<String, String> users) {
        Map<String, String> roles = new HashMap<String, String>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,remark from roles where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, users.get("rid"));
            rs = ptst.executeQuery();
            if (rs.next()) {
                roles.put("id", rs.getString("id"));
                roles.put("name", rs.getString("name"));
                roles.put("remark", rs.getString("remark"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RolesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return roles;
    }

    public List<Map<String, String>> getList() {
        List<Map<String, String>> rolesList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,remark from roles";
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
            DbConn.getAllClose(conn, ptst, rs);
        }
        return rolesList;
    }

    public Map<String, String> getById(Map<String, String> roles) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,remark from roles where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, roles.get("id"));
            rs = ptst.executeQuery();
            if (rs.next()) {
                roles = new HashMap<String, String>();
                roles.put("id", rs.getString("id"));
                roles.put("name", rs.getString("name"));
                roles.put("remark", rs.getString("remark"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RolesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return roles;
    }

    public void add(Map<String, String> roles) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "insert into roles(name,remark) values (?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, roles.get("name"));
            ptst.setString(2, roles.get("remark"));;
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void update(Map<String, String> roles) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update roles set name=?,remark=? where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, roles.get("name"));
            ptst.setString(2, roles.get("remark"));
            ptst.setString(3, roles.get("id"));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void delete(Map<String, String> roles, Connection conn) {
        PreparedStatement ptst = null;
        String sql = "delete from roles where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, roles.get("id"));
            ptst.executeUpdate();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
