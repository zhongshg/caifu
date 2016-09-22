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
 * @author ASUS
 */
public class PerMaterialDAO {

    public List<Map<String, String>> getList(int wxsid) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,type,mediaid,imgurl,title,description,musicurl,hqmusicurl,times,ip,userid,viewcounts from permaterial where to_days(now())-to_days(times) <3 and wxsid=? order by type,id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("name", rs.getString("name"));
                map.put("type", rs.getString("type"));
                map.put("mediaid", rs.getString("mediaid"));
                map.put("imgurl", rs.getString("imgurl"));
                map.put("title", rs.getString("title"));
                map.put("description", rs.getString("description"));
                map.put("musicurl", rs.getString("musicurl"));
                map.put("hqmusicurl", rs.getString("hqmusicurl"));
                map.put("times", rs.getString("times"));
                map.put("ip", rs.getString("ip"));
                map.put("userid", rs.getString("userid"));
                map.put("viewcounts", rs.getString("viewcounts"));
                list.add(map);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PerMaterialDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return list;
    }

    public List<Map<String, String>> getList(PageUtil pu, int wxsid) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select u.id uid,u.username uusername,u.name uname,m.id,m.name,type,mediaid,imgurl,title,description,musicurl,hqmusicurl,times,ip,userid,viewcounts from permaterial m,users u where m.userid=u.id and m.wxsid=? order by type,id desc limit " + pu.getPageSize() * (0 < pu.getPage() ? pu.getPage() - 1 : 0) + "," + pu.getPageSize();
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("uid", rs.getString("uid"));
                map.put("uusername", rs.getString("uusername"));
                map.put("uname", rs.getString("uname"));
                map.put("id", rs.getString("id"));
                map.put("name", rs.getString("name"));
                map.put("type", rs.getString("type"));
                map.put("mediaid", rs.getString("mediaid"));
                map.put("imgurl", rs.getString("imgurl"));
                map.put("title", rs.getString("title"));
                map.put("description", rs.getString("description"));
                map.put("musicurl", rs.getString("musicurl"));
                map.put("hqmusicurl", rs.getString("hqmusicurl"));
                map.put("times", rs.getString("times"));
                map.put("ip", rs.getString("ip"));
                map.put("userid", rs.getString("userid"));
                map.put("viewcounts", rs.getString("viewcounts"));
                list.add(map);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PerMaterialDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return list;
    }

    public int getCount(int wxsid) {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select count(id) count from permaterial where wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PerMaterialDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }

    public Map<String, String> getById(Map<String, String> map) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,type,mediaid,imgurl,title,description,musicurl,hqmusicurl,times,ip,userid,viewcounts from permaterial where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(map.get("id")));
            rs = ptst.executeQuery();
            while (rs.next()) {
                map.put("id", rs.getString("id"));
                map.put("name", rs.getString("name"));
                map.put("type", rs.getString("type"));
                map.put("mediaid", rs.getString("mediaid"));
                map.put("imgurl", rs.getString("imgurl"));
                map.put("title", rs.getString("title"));
                map.put("description", rs.getString("description"));
                map.put("musicurl", rs.getString("musicurl"));
                map.put("hqmusicurl", rs.getString("hqmusicurl"));
                map.put("times", rs.getString("times"));
                map.put("ip", rs.getString("ip"));
                map.put("userid", rs.getString("userid"));
                map.put("viewcounts", rs.getString("viewcounts"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PerMaterialDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return map;
    }

    public void add(Map<String, String> map, int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "insert into permaterial(name,type,mediaid,imgurl,title,description,musicurl,hqmusicurl,times,ip,userid,viewcounts,wxsid) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, map.get("name"));
            ptst.setString(2, map.get("type"));
            ptst.setString(3, map.get("mediaid"));
            ptst.setString(4, map.get("imgurl"));
            ptst.setString(5, map.get("title"));
            ptst.setString(6, map.get("description"));
            ptst.setString(7, map.get("musicurl"));
            ptst.setString(8, map.get("hqmusicurl"));
            ptst.setString(9, map.get("times"));
            ptst.setString(10, map.get("ip"));
            ptst.setInt(11, Integer.parseInt(map.get("userid")));
            ptst.setInt(12, Integer.parseInt(map.get("viewcounts")));
            ptst.setInt(13, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PerMaterialDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void update(Map<String, String> map) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update permaterial set name=?,type=?,mediaid=?,imgurl=?,title=?,description=?,musicurl=?,hqmusicurl=?,times=?,ip=?,userid=? where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, map.get("name"));
            ptst.setString(2, map.get("type"));
            ptst.setString(3, map.get("mediaid"));
            ptst.setString(4, map.get("imgurl"));
            ptst.setString(5, map.get("title"));
            ptst.setString(6, map.get("description"));
            ptst.setString(7, map.get("musicurl"));
            ptst.setString(8, map.get("hqmusicurl"));
            ptst.setString(9, map.get("times"));
            ptst.setString(10, map.get("ip"));
            ptst.setInt(11, Integer.parseInt(map.get("userid")));
            ptst.setInt(12, Integer.parseInt(map.get("id")));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PerMaterialDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void updateViewcounts(Map<String, String> map) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update permaterial set viewcounts=viewcounts+1 where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(map.get("id")));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PerMaterialDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void delete(Map<String, String> map) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "delete from permaterial where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(map.get("id")));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PerMaterialDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
}
