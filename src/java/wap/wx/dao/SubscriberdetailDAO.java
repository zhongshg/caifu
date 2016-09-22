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
public class SubscriberdetailDAO {

    public List<Map<String, String>> getList(PageUtil pu, int wxsid) {
        List<Map<String, String>> subscriberdetailList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,openid,dates,msgtype,event,content,remark from subscriberdetail where wxsid=? order by id desc limit " + pu.getPageSize() * (0 < pu.getPage() ? pu.getPage() - 1 : 0) + "," + pu.getPageSize();
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> subscriberdetail = new HashMap<String, String>();
                subscriberdetail.put("id", rs.getString("id"));
                subscriberdetail.put("openid", rs.getString("openid"));
                subscriberdetail.put("dates", rs.getString("dates"));
                subscriberdetail.put("msgtype", rs.getString("msgtype"));
                subscriberdetail.put("event", rs.getString("event"));
                subscriberdetail.put("content", rs.getString("content"));
                subscriberdetail.put("remark", rs.getString("remark"));
                subscriberdetailList.add(subscriberdetail);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberdetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return subscriberdetailList;
    }

    public int getCount(int wxsid) {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select count(id) count from subscriberdetail where wxsid=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberdetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }

    public void delete(Map<String, String> subscriberdetail) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "delete from subscriberdetail where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, subscriberdetail.get("id"));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberdetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void add(Map<String, String> subscriberdetail, int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "insert into subscriberdetail(openid,dates,msgtype,event,content,remark,wxsid) values (?,?,?,?,?,?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, subscriberdetail.get("openid"));
            ptst.setString(2, subscriberdetail.get("dates"));
            ptst.setString(3, subscriberdetail.get("msgtype"));
            ptst.setString(4, subscriberdetail.get("event"));
            ptst.setString(5, subscriberdetail.get("content"));
            ptst.setString(6, subscriberdetail.get("remark"));
            ptst.setInt(7, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberdetailDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
}
