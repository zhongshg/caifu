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
public class UsersDAO {

    public Map<String, String> login(Map<String, String> users) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select u.id,u.username,u.passwords,u.name,u.identity,u.sex,u.ages,u.birthday,u.img,u.rid,r.name rname,u.remark,u.wxsid,w.name wxsname from users u left join roles r on u.rid=r.id left join wxs w on u.wxsid=w.id where username=? and passwords=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, users.get("username").trim());
            ptst.setString(2, users.get("passwords").trim());
            rs = ptst.executeQuery();
            while (rs.next()) {
                users = new HashMap<String, String>();
                users.put("id", rs.getString("id"));
                users.put("username", rs.getString("username"));
                users.put("passwords", rs.getString("passwords"));
                users.put("name", rs.getString("name"));
                users.put("identity", rs.getString("identity"));
                users.put("sex", rs.getString("sex"));
                users.put("ages", rs.getString("ages"));
                users.put("birthday", rs.getString("birthday"));
                users.put("img", rs.getString("img"));
                users.put("rid", rs.getString("rid"));
                users.put("rname", rs.getString("rname"));
                users.put("remark", rs.getString("remark"));
                users.put("wxsid", rs.getString("wxsid"));
                users.put("wxsname", rs.getString("wxsname"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        if (null == users.get("id")) {
            return null;
        }
        return users;
    }

    public int getLoginCounts(Map<String, String> loginInfo) {
        int times = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select count(id) counts from logininfo where username=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, loginInfo.get("username").trim());
            rs = ptst.executeQuery();
            while (rs.next()) {
                times = rs.getInt("counts");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return times;
    }

    public void saveLoginInfo(Map<String, String> loginInfo) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        // id,username,logintime,ip,remark logininfo
        String sql = "insert into logininfo(username,logintime,ip,remark) values (?,?,?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, loginInfo.get("username"));
            ptst.setString(2, loginInfo.get("logintime"));
            ptst.setString(3, loginInfo.get("ip"));
            ptst.setString(4, loginInfo.get("remark"));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public Map<String, String> checkIdentity(Map<String, String> users) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,username,passwords,name,identity,sex,ages,birthday,img,rid,remark,wxsid from users where username=? and name=? and identity=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, users.get("username"));
            ptst.setString(2, users.get("name"));
            ptst.setString(3, users.get("identity"));
            rs = ptst.executeQuery();
            while (rs.next()) {
                users = new HashMap<String, String>();
                users.put("id", rs.getString("id"));
                users.put("username", rs.getString("username"));
                users.put("passwords", rs.getString("passwords"));
                users.put("name", rs.getString("name"));
                users.put("identity", rs.getString("identity"));
                users.put("sex", rs.getString("sex"));
                users.put("ages", rs.getString("ages"));
                users.put("birthday", rs.getString("birthday"));
                users.put("img", rs.getString("img"));
                users.put("rid", rs.getString("rid"));
                users.put("remark", rs.getString("remark"));
                users.put("wxsid", rs.getString("wxsid"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        if (null == users.get("id")) {
            return null;
        }
        return users;
    }

    public void updatePassword(Map<String, String> users) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update users set passwords=? where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, users.get("passwords"));
            ptst.setString(2, users.get("id"));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public List<Map<String, String>> getList(PageUtil pu, String wxsid) {
        List<Map<String, String>> usersList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String temp = "";
        if (!"0".equals(wxsid)) {
            temp += " and wxsid='" + wxsid + "'";
        }
        String sql = "select u.id,u.username,u.passwords,u.name,u.identity,u.sex,u.ages,u.birthday,u.img,u.rid,r.name rname,u.remark,u.wxsid,w.name wxsname from users u left join roles r on u.rid=r.id left join wxs w on u.wxsid=w.id where 1=1 " + temp + "limit " + pu.getPageSize() * (0 < pu.getPage() ? pu.getPage() - 1 : 0) + "," + pu.getPageSize();
        try {
            ptst = conn.prepareStatement(sql);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> users = new HashMap<String, String>();
                users.put("id", rs.getString("id"));
                users.put("username", rs.getString("username"));
                users.put("passwords", rs.getString("passwords"));
                users.put("name", rs.getString("name"));
                users.put("identity", rs.getString("identity"));
                users.put("sex", rs.getString("sex"));
                users.put("ages", rs.getString("ages"));
                users.put("birthday", rs.getString("birthday"));
                users.put("img", rs.getString("img"));
                users.put("rid", rs.getString("rid"));
                users.put("rname", rs.getString("rname"));
                users.put("remark", rs.getString("remark"));
                users.put("wxsid", rs.getString("wxsid"));
                users.put("wxsname", rs.getString("wxsname"));
                usersList.add(users);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return usersList;
    }

    public int getCount(String wxsid) {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String temp = "";
        if (!"0".equals(wxsid)) {
            temp += " and wxsid='" + wxsid + "'";
        }
        String sql = "select count(u.id) count from users u,roles r where u.rid=r.id" + temp;
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

    public Map<String, String> getById(Map<String, String> users) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select u.id,u.username,u.passwords,u.name,u.identity,u.sex,u.ages,u.birthday,u.img,u.rid,r.name rname,u.remark,u.wxsid,w.name wxsname from users u left join roles r on u.rid=r.id left join wxs w on u.wxsid=w.id where u.id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(users.get("id")));
            rs = ptst.executeQuery();
            if (rs.next()) {
                users = new HashMap<String, String>();
                users.put("id", rs.getString("id"));
                users.put("username", rs.getString("username"));
                users.put("passwords", rs.getString("passwords"));
                users.put("name", rs.getString("name"));
                users.put("identity", rs.getString("identity"));
                users.put("sex", rs.getString("sex"));
                users.put("ages", rs.getString("ages"));
                users.put("birthday", rs.getString("birthday"));
                users.put("img", rs.getString("img"));
                users.put("rid", rs.getString("rid"));
                users.put("rname", rs.getString("rname"));
                users.put("remark", rs.getString("remark"));
                users.put("wxsid", rs.getString("wxsid"));
                users.put("wxsname", rs.getString("wxsname"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return users;
    }

    public Map<String, String> getByUserName(Map<String, String> users) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select u.id from users u where u.username=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, users.get("username").trim());
            rs = ptst.executeQuery();
            if (rs.next()) {
                users = new HashMap<String, String>();
                users.put("id", rs.getString("id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return users;
    }

    public Map<String, String> getByIdentity(Map<String, String> users) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select u.id from users u where u.identity=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, users.get("identity").trim());
            rs = ptst.executeQuery();
            if (rs.next()) {
                users = new HashMap<String, String>();
                users.put("id", rs.getString("id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return users;
    }

    public void add(Map<String, String> users) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "insert into users(username,passwords,name,identity,sex,ages,birthday,img,rid,remark,wxsid) values (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, users.get("username"));
            ptst.setString(2, users.get("passwords"));
            ptst.setString(3, users.get("name"));
            ptst.setString(4, users.get("identity"));
            ptst.setString(5, users.get("sex"));
            ptst.setString(6, users.get("ages"));
            ptst.setString(7, users.get("birthday"));
            ptst.setString(8, users.get("img"));
            ptst.setString(9, users.get("rid"));
            ptst.setString(10, users.get("remark"));
            ptst.setString(11, users.get("wxsid"));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void update(Map<String, String> users) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update users set username=?,name=?,identity=?,sex=?,ages=?,birthday=?,img=?,rid=?,remark=?,wxsid=? where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, users.get("username"));
            ptst.setString(2, users.get("name"));
            ptst.setString(3, users.get("identity"));
            ptst.setString(4, users.get("sex"));
            ptst.setString(5, users.get("ages"));
            ptst.setString(6, users.get("birthday"));
            ptst.setString(7, users.get("img"));
            ptst.setString(8, users.get("rid"));
            ptst.setString(9, users.get("remark"));
            ptst.setString(10, users.get("wxsid"));
            ptst.setString(11, users.get("id"));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void delete(Map<String, String> users) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "delete from users where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, users.get("id"));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
}
