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

/**
 *
 * @author Administrator
 */
public class MenusDAO {

    public List<Map<String, String>> getByParentList(Map<String, String> menus, int wxsid) {
        List<Map<String, String>> menusList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,menukey,name,parentid,menutype,url,sendid,sendtype,orders from menus where parentid=? and wxsid=? order by orders,id asc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(menus.get("id")));
            ptst.setInt(2, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                menus = new HashMap<String, String>();
                menus.put("id", rs.getString("id"));
                menus.put("menukey", rs.getString("menukey"));
                menus.put("name", rs.getString("name"));
                menus.put("parentid", rs.getString("parentid"));
                menus.put("menutype", rs.getString("menutype"));
                menus.put("url", rs.getString("url"));
                menus.put("sendid", rs.getString("sendid"));
                menus.put("sendtype", rs.getString("sendtype"));
                menus.put("orders", rs.getString("orders"));
                menusList.add(menus);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return menusList;
    }

    public int getMaxId() {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select max(id) count from menus";
        try {
            ptst = conn.prepareStatement(sql);
            rs = ptst.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }

    public Map<String, String> getById(Map<String, String> menus) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,menukey,name,parentid,menutype,url,sendid,sendtype,orders from menus where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(menus.get("id")));
            rs = ptst.executeQuery();
            while (rs.next()) {
                menus = new HashMap<String, String>();
                menus.put("id", rs.getString("id"));
                menus.put("menukey", rs.getString("menukey"));
                menus.put("name", rs.getString("name"));
                menus.put("parentid", rs.getString("parentid"));
                menus.put("menutype", rs.getString("menutype"));
                menus.put("url", rs.getString("url"));
                menus.put("sendid", rs.getString("sendid"));
                menus.put("sendtype", rs.getString("sendtype"));
                menus.put("orders", rs.getString("orders"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return menus;
    }

    public void add(Map<String, String> menus, int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "insert into menus(menukey,name,parentid,menutype,url,sendid,sendtype,orders,wxsid) values (?,?,?,?,?,?,?,?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, menus.get("menukey"));
            ptst.setString(2, menus.get("name"));
            ptst.setInt(3, Integer.parseInt(menus.get("parentid")));
            ptst.setInt(4, Integer.parseInt(menus.get("menutype")));
            ptst.setString(5, menus.get("url"));
            ptst.setInt(6, Integer.parseInt(menus.get("sendid")));
            ptst.setString(7, menus.get("sendtype"));
            ptst.setInt(8, Integer.parseInt(menus.get("orders")));
            ptst.setInt(9, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MenusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void update(Map<String, String> menus) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update menus set menukey=?,name=?,parentid=?,menutype=?,url=?,sendid=?,sendtype=?,orders=? where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, menus.get("menukey"));
            ptst.setString(2, menus.get("name"));
            ptst.setInt(3, Integer.parseInt(menus.get("parentid")));
            ptst.setInt(4, Integer.parseInt(menus.get("menutype")));
            ptst.setString(5, menus.get("url"));
            ptst.setInt(6, Integer.parseInt(menus.get("sendid")));
            ptst.setString(7, menus.get("sendtype"));
            ptst.setInt(8, Integer.parseInt(menus.get("orders")));
            ptst.setInt(9, Integer.parseInt(menus.get("id")));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MenusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void updateOrders(Map<String, String> menus, Connection conn) {
        String sql = "update menus set orders=? where id=?";
        try {
            PreparedStatement ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(menus.get("orders")));
            ptst.setInt(2, Integer.parseInt(menus.get("id")));
            ptst.executeUpdate();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(MenusDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(Map<String, String> menus, Connection conn) {
        String sql = "delete from menus where id=?";
        try {
            PreparedStatement ptst = conn.prepareStatement(sql);
            ptst.setString(1, menus.get("id"));
            ptst.executeUpdate();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(MenusDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(Map<String, String> menus) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "delete from menus where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, menus.get("id"));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MenusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void deleteAll(int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "delete from menus where wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MenusDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
}
