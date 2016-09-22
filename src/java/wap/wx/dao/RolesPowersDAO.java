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
public class RolesPowersDAO {

    public List<Map<String, String>> getListByRoles(Map<String, String> roles) {
        List<Map<String, String>> powersList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,parentid,url,remark from powers where id in (select pid from rolespowers where rid=?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, roles.get("id"));
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> powers = new HashMap<String, String>();
                powers.put("id", rs.getString("id"));
                powers.put("name", rs.getString("name"));
                powers.put("parentid", rs.getString("parentid"));
                powers.put("url", rs.getString("url"));
                powers.put("remark", rs.getString("remark"));
                powersList.add(powers);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return powersList;
    }

    public List<Map<String, String>> getSubListByPowers(Map<String, String> powers) {
        List<Map<String, String>> powersList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,parentid,url,remark from powers where parentid=? order by id asc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, powers.get("id"));
            rs = ptst.executeQuery();
            while (rs.next()) {
                powers = new HashMap<String, String>();
                powers.put("id", rs.getString("id"));
                powers.put("name", rs.getString("name"));
                powers.put("parentid", rs.getString("parentid"));
                powers.put("url", rs.getString("url"));
                powers.put("remark", rs.getString("remark"));
                powersList.add(powers);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return powersList;
    }

    public void add(Map<String, String> roles, Map<String, String> powers) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "insert into rolespowers(rid,pid) values (?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, roles.get("id"));
            ptst.setString(2, powers.get("id"));;
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void deleteByRoles(Map<String, String> roles, Connection conn) {
        String sql = "delete from rolespowers where rid=?";
        try {
            PreparedStatement ptst = conn.prepareStatement(sql);
            ptst.setString(1, roles.get("id"));
            ptst.executeUpdate();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteByRoles(Map<String, String> roles) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "delete from rolespowers where rid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, roles.get("id"));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
//    public void deleteAll() {
//        Connection conn = DbConn.getConn();
//        PreparedStatement ptst = null;
//        ResultSet rs = null;
//        String sql = "delete from rolespowers";
//        try {
//            ptst = conn.prepareStatement(sql);
//            ptst.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            DbConn.getAllClose(conn, ptst, rs);
//        }
//    }
}
