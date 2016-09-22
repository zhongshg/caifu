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
public class NativeDAO {
    
    public List<Map<String, String>> getList(PageUtil pu, int wxsid) {
        List<Map<String, String>> nativeList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,type,lid,remark,wds from native where wxsid=? order by id desc limit " + pu.getPageSize() * (0 < pu.getPage() ? pu.getPage() - 1 : 0) + "," + pu.getPageSize();
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> natives = new HashMap<String, String>();
                natives.put("id", rs.getString("id"));
                natives.put("name", rs.getString("name"));
                natives.put("type", rs.getString("type"));
                natives.put("lid", rs.getString("lid"));
                natives.put("remark", rs.getString("remark"));
                natives.put("wds", rs.getString("wds"));
                nativeList.add(natives);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NativeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return nativeList;
    }
    
    public int getConut(int wxsid) {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select count(id) count from native where wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(NativeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }
    
    public Map<String, String> getById(Map<String, String> natives) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,type,lid,remark,wds from native where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(natives.get("id")));
            rs = ptst.executeQuery();
            natives = new HashMap<String, String>();
            if (rs.next()) {
                natives.put("id", rs.getString("id"));
                natives.put("name", rs.getString("name"));
                natives.put("type", rs.getString("type"));
                natives.put("lid", rs.getString("lid"));
                natives.put("remark", rs.getString("remark"));
                natives.put("wds", rs.getString("wds"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NativeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return natives;
    }
    
    public Map<String, String> getByName(Map<String, String> natives, int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,type,lid,remark,wds from native where name=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, natives.get("name"));
            ptst.setInt(2, wxsid);
            rs = ptst.executeQuery();
            natives = new HashMap<String, String>();
            if (rs.next()) {
                natives.put("id", rs.getString("id"));
                natives.put("name", rs.getString("name"));
                natives.put("type", rs.getString("type"));
                natives.put("lid", rs.getString("lid"));
                natives.put("remark", rs.getString("remark"));
                natives.put("wds", rs.getString("wds"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NativeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return natives;
    }
    
    public Map<String, String> getByWds(Map<String, String> natives, int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,type,lid,remark,wds from native where wds=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(natives.get("wds")));
            ptst.setInt(2, wxsid);
            rs = ptst.executeQuery();
            natives = new HashMap<String, String>();
            if (rs.next()) {
                natives.put("id", rs.getString("id"));
                natives.put("name", rs.getString("name"));
                natives.put("type", rs.getString("type"));
                natives.put("lid", rs.getString("lid"));
                natives.put("remark", rs.getString("remark"));
                natives.put("wds", rs.getString("wds"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NativeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return natives;
    }
    
    public void add(Map<String, String> natives, int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "insert into native(name,type,lid,remark,wds,wxsid) values (?,?,?,?,?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, natives.get("name"));
            ptst.setString(2, natives.get("type"));
            ptst.setInt(3, Integer.parseInt(natives.get("lid")));
            ptst.setString(4, natives.get("remark"));
            ptst.setInt(5, Integer.parseInt(natives.get("wds")));
            ptst.setInt(6, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NativeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
    
    public void update(Map<String, String> natives) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update native set name=?,type=?,lid=?,remark=?,wds=? where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, natives.get("name"));
            ptst.setString(2, natives.get("type"));
            ptst.setInt(3, Integer.parseInt(natives.get("lid")));
            ptst.setString(4, natives.get("remark"));
            ptst.setInt(5, Integer.parseInt(natives.get("wds")));
            ptst.setInt(6, Integer.parseInt(natives.get("id")));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NativeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
    
    public void delete(Map<String, String> natives) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "delete from native where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, natives.get("id"));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NativeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
}
