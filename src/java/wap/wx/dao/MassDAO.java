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
public class MassDAO {
    
    public List<Map<String, String>> getList(PageUtil pu, Map<String, String> mass, int wxsid) {
        List<Map<String, String>> massList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,mediaid,times,sid from mass where sid=? and wxsid=? order by id desc limit " + pu.getPageSize() * (0 < pu.getPage() ? pu.getPage() - 1 : 0) + "," + pu.getPageSize();
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, mass.get("sid"));
            ptst.setInt(2, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                mass = new HashMap<String, String>();
                mass.put("id", rs.getString("id"));
                mass.put("mediaid", rs.getString("mediaid"));
                mass.put("times", rs.getString("times"));
                mass.put("sid", rs.getString("sid"));
                massList.add(mass);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MassDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return massList;
    }
    
    public List<Map<String, String>> getList(Map<String, String> mass, int wxsid) {
        List<Map<String, String>> massList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,mediaid,times,sid from mass where sid=? and wxsid=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, mass.get("sid"));
            ptst.setInt(2, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                mass = new HashMap<String, String>();
                mass.put("id", rs.getString("id"));
                mass.put("mediaid", rs.getString("mediaid"));
                mass.put("times", rs.getString("times"));
                mass.put("sid", rs.getString("sid"));
                massList.add(mass);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MassDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return massList;
    }
    
    public int getConut(Map<String, String> mass, int wxsid) {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select count(id) count from mass where sid=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, mass.get("sid"));
            ptst.setInt(2, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MassDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }
    
    public void add(Map<String, String> mass, int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "insert into mass(mediaid,times,sid,wxsid) values (?,?,?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, mass.get("mediaid"));
            ptst.setString(2, mass.get("times"));
            ptst.setInt(3, Integer.parseInt(mass.get("sid")));
            ptst.setInt(4, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MassDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
    
    public void delete(Map<String, String> mass, int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "delete from mass where mediaid=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, mass.get("mediaid"));
            ptst.setInt(2, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MassDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
}
