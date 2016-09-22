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
public class NewsDAO {
    
    public List<Map<String, String>> getList(int wxsid) {
        List<Map<String, String>> newsList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select n.id,n.name,n.author,n.source,n.img,n.briefintro,n.content,n.times,n.typeid,nt.name typename,n.sid,n.uid,n.viewcounts from news n left join newstypes nt on n.typeid=nt.id where n.wxsid=? order by n.orders desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> news = new HashMap<String, String>();
                news.put("id", rs.getString("id"));
                news.put("name", rs.getString("name"));
                news.put("author", rs.getString("author"));
                news.put("source", rs.getString("source"));
                news.put("img", rs.getString("img"));
                news.put("briefintro", rs.getString("briefintro"));
                news.put("content", rs.getString("content"));
                news.put("times", rs.getString("times"));
                news.put("typeid", rs.getString("typeid"));
                news.put("typename", rs.getString("typename"));
                news.put("sid", rs.getString("sid"));
                news.put("uid", rs.getString("uid"));
                news.put("viewcounts", rs.getString("viewcounts"));
                newsList.add(news);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return newsList;
    }
    
    public List<Map<String, String>> getList(String sid, int wxsid) {
        List<Map<String, String>> newsList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select n.id,n.name,n.author,n.source,n.img,n.briefintro,n.content,n.times,n.typeid,nt.name typename,n.sid,n.uid,n.viewcounts from news n left join newstypes nt on n.typeid=nt.id where n.sid=? and n.wxsid=? order by n.orders desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(sid));
            ptst.setInt(2, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> news = new HashMap<String, String>();
                news.put("id", rs.getString("id"));
                news.put("name", rs.getString("name"));
                news.put("author", rs.getString("author"));
                news.put("source", rs.getString("source"));
                news.put("img", rs.getString("img"));
                news.put("briefintro", rs.getString("briefintro"));
                news.put("content", rs.getString("content"));
                news.put("times", rs.getString("times"));
                news.put("typeid", rs.getString("typeid"));
                news.put("typename", rs.getString("typename"));
                news.put("sid", rs.getString("sid"));
                news.put("uid", rs.getString("uid"));
                news.put("viewcounts", rs.getString("viewcounts"));
                newsList.add(news);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return newsList;
    }
    
    public List<Map<String, String>> getList(String sid, String stypeid, int wxsid) {
        String stypestr = !"0".equals(stypeid) ? " and typeid=" + stypeid : "";
        List<Map<String, String>> newsList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select n.id,n.name,n.author,n.source,n.img,n.briefintro,n.content,n.times,n.typeid,nt.name typename,n.sid,n.uid,n.viewcounts from news n left join newstypes nt on n.typeid=nt.id where n.sid=? " + stypestr + " and n.wxsid=? order by n.orders desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(sid));
            ptst.setInt(2, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> news = new HashMap<String, String>();
                news.put("id", rs.getString("id"));
                news.put("name", rs.getString("name"));
                news.put("author", rs.getString("author"));
                news.put("source", rs.getString("source"));
                news.put("img", rs.getString("img"));
                news.put("briefintro", rs.getString("briefintro"));
                news.put("content", rs.getString("content"));
                news.put("times", rs.getString("times"));
                news.put("typeid", rs.getString("typeid"));
                news.put("typename", rs.getString("typename"));
                news.put("sid", rs.getString("sid"));
                news.put("uid", rs.getString("uid"));
                news.put("viewcounts", rs.getString("viewcounts"));
                newsList.add(news);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return newsList;
    }
    
    public List<Map<String, String>> getList(PageUtil pu, String sid, String stypeid, int wxsid) {
        String stypestr = !"0".equals(stypeid) ? " and typeid=" + stypeid : "";
        List<Map<String, String>> newsList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select n.id,n.name,n.author,n.source,n.img,n.briefintro,n.content,n.times,n.typeid,nt.name typename,n.sid,n.uid,n.viewcounts from news n left join newstypes nt on n.typeid=nt.id where n.sid=? " + stypestr + " and n.wxsid=? order by n.orders desc limit " + pu.getPageSize() * (0 < pu.getPage() ? pu.getPage() - 1 : 0) + "," + pu.getPageSize();
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(sid));
            ptst.setInt(2, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> news = new HashMap<String, String>();
                news.put("id", rs.getString("id"));
                news.put("name", rs.getString("name"));
                news.put("author", rs.getString("author"));
                news.put("source", rs.getString("source"));
                news.put("img", rs.getString("img"));
                news.put("briefintro", rs.getString("briefintro"));
                news.put("content", rs.getString("content"));
                news.put("times", rs.getString("times"));
                news.put("typeid", rs.getString("typeid"));
                news.put("typename", rs.getString("typename"));
                news.put("sid", rs.getString("sid"));
                news.put("uid", rs.getString("uid"));
                news.put("viewcounts", rs.getString("viewcounts"));
                newsList.add(news);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return newsList;
    }
    
    public int getConut(String sid, String stypeid, int wxsid) {
        String stypestr = !"0".equals(stypeid) ? " and typeid=" + stypeid : "";
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select count(id) count from news where sid=? " + stypestr + " and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(sid));
            ptst.setInt(2, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }
    
    public int getMaxId(int wxsid) {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select max(id) count from news where wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }
    
    public Map<String, String> getById(Map<String, String> news) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select n.id,n.name,n.author,n.source,n.img,n.briefintro,n.content,n.times,n.typeid,nt.name typename,n.sid,n.uid,n.orders,n.viewcounts from news n left join newstypes nt on n.typeid=nt.id where n.id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(news.get("id")));
            rs = ptst.executeQuery();
            if (rs.next()) {
                news = new HashMap<String, String>();
                news.put("id", rs.getString("id"));
                news.put("name", rs.getString("name"));
                news.put("author", rs.getString("author"));
                news.put("source", rs.getString("source"));
                news.put("img", rs.getString("img"));
                news.put("briefintro", rs.getString("briefintro"));
                news.put("content", rs.getString("content"));
                news.put("times", rs.getString("times"));
                news.put("typeid", rs.getString("typeid"));
                news.put("typename", rs.getString("typename"));
                news.put("sid", rs.getString("sid"));
                news.put("uid", rs.getString("uid"));
                news.put("orders", rs.getString("orders"));
                news.put("viewcounts", rs.getString("viewcounts"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return news;
    }
    
    public void add(Map<String, String> news, int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "insert into news(name,author,source,img,briefintro,content,times,typeid,sid,uid,orders,viewcounts,wxsid) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, news.get("name"));
            ptst.setString(2, news.get("author"));
            ptst.setString(3, news.get("source"));
            ptst.setString(4, news.get("img"));
            ptst.setString(5, news.get("briefintro"));
            ptst.setString(6, news.get("content"));
            ptst.setString(7, news.get("times"));
            ptst.setInt(8, Integer.parseInt(news.get("typeid")));
            ptst.setInt(9, Integer.parseInt(news.get("sid")));
            ptst.setInt(10, Integer.parseInt(news.get("uid")));
            ptst.setInt(11, Integer.parseInt(news.get("orders")));
            ptst.setInt(12, Integer.parseInt(news.get("viewcounts")));
            ptst.setInt(13, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
    
    public void update(Map<String, String> news) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update news set name=?,author=?,source=?,img=?,briefintro=?,content=?,times=?,typeid=?,sid=?,uid=?,orders=? where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, news.get("name"));
            ptst.setString(2, news.get("author"));
            ptst.setString(3, news.get("source"));
            ptst.setString(4, news.get("img"));
            ptst.setString(5, news.get("briefintro"));
            ptst.setString(6, news.get("content"));
            ptst.setString(7, news.get("times"));
            ptst.setInt(8, Integer.parseInt(news.get("typeid")));
            ptst.setInt(9, Integer.parseInt(news.get("sid")));
            ptst.setInt(10, Integer.parseInt(news.get("uid")));
            ptst.setInt(11, Integer.parseInt(news.get("orders")));
            ptst.setInt(12, Integer.parseInt(news.get("id")));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
    
    public void updateViewcounts(String id) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update news set viewcounts=viewcounts+1 where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(id));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
    
    public void delete(Map<String, String> news) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "delete from news where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(news.get("id")));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NewsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
}
