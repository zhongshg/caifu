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
public class TextDAO {
    
    public List<Map<String, String>> getList(int wxsid) {
        List<Map<String, String>> textList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,describers from text where wxsid=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> text = new HashMap<String, String>();
                text.put("id", rs.getString("id"));
                text.put("name", rs.getString("name"));
                text.put("describers", rs.getString("describers"));
                textList.add(text);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TextDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return textList;
    }
    
    public List<Map<String, String>> getList(PageUtil pu, int wxsid) {
        List<Map<String, String>> textList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,describers from text where wxsid=? order by id desc limit " + pu.getPageSize() * (0 < pu.getPage() ? pu.getPage() - 1 : 0) + "," + pu.getPageSize();
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> text = new HashMap<String, String>();
                text.put("id", rs.getString("id"));
                text.put("name", rs.getString("name"));
                text.put("describers", rs.getString("describers"));
                textList.add(text);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TextDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return textList;
    }
    
    public int getConut(int wxsid) {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select count(id) count from text where wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TextDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }
    
    public Map<String, String> getById(Map<String, String> text) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,describers from text where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(text.get("id")));
            rs = ptst.executeQuery();
            if (rs.next()) {
                text = new HashMap<String, String>();
                text.put("id", rs.getString("id"));
                text.put("name", rs.getString("name"));
                text.put("describers", rs.getString("describers"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TextDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return text;
    }
    
    public void add(Map<String, String> text, int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "insert into text(name,describers,wxsid) values (?,?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, text.get("name"));
            ptst.setString(2, text.get("describers"));
            ptst.setInt(3, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TextDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
    
    public void update(Map<String, String> text) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update text set name=?,describers=? where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, text.get("name"));
            ptst.setString(2, text.get("describers"));
            ptst.setString(3, text.get("id"));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TextDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
    
    public void delete(Map<String, String> text) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "delete from text where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, text.get("id"));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TextDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
}
