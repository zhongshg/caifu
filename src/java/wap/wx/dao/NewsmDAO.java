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
public class NewsmDAO {
    
    public List<Map<String, String>> getList(int wxsid) {
        List<Map<String, String>> newsmList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name from newsm where wxsid=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> newsm = new HashMap<String, String>();
                newsm.put("id", rs.getString("id"));
                newsm.put("name", rs.getString("name"));
                newsmList.add(newsm);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return newsmList;
    }
    
    public List<Map<String, String>> getList(PageUtil pu, int wxsid) {
        List<Map<String, String>> newsmList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name from newsm where wxsid=? order by id desc limit " + pu.getPageSize() * (0 < pu.getPage() ? pu.getPage() - 1 : 0) + "," + pu.getPageSize();
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> newsm = new HashMap<String, String>();
                newsm.put("id", rs.getString("id"));
                newsm.put("name", rs.getString("name"));
                newsmList.add(newsm);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return newsmList;
    }
    
    public int getConut(int wxsid) {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select count(id) count from newsm where wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }
    
    public Map<String, String> getById(Map<String, String> newsm) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name from newsm where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(newsm.get("id")));
            rs = ptst.executeQuery();
            if (rs.next()) {
                newsm = new HashMap<String, String>();
                newsm.put("id", rs.getString("id"));
                newsm.put("name", rs.getString("name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return newsm;
    }
    
    public void add(Map<String, String> newsm, int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "insert into newsm(name,wxsid) values (?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, newsm.get("name"));
            ptst.setInt(2, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NewsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
    
    public void update(Map<String, String> newsm) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update newsm set name=? where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, newsm.get("name"));
            ptst.setInt(2, Integer.parseInt(newsm.get("id")));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NewsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
    
    public void delete(Map<String, String> newsm, Connection conn) {
        String sql = "delete from newsm where id=?";
        try {
            PreparedStatement ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(newsm.get("id")));
            ptst.executeUpdate();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(NewsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
