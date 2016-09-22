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
public class ItemsmDAO {

    public List<Map<String, String>> getList(int wxsid) {
        List<Map<String, String>> itemsmList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,describes,img,url,remark,orders,mediaid,mediatimes from itemsm where wxsid=? order by orders desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> itemsm = new HashMap<String, String>();
                itemsm.put("id", rs.getString("id"));
                itemsm.put("name", rs.getString("name"));
                itemsm.put("describes", rs.getString("describes"));
                itemsm.put("img", rs.getString("img"));
                itemsm.put("url", rs.getString("url"));
                itemsm.put("remark", rs.getString("remark"));
                itemsm.put("orders", rs.getString("orders"));
                itemsm.put("mediaid", rs.getString("mediaid"));
                itemsm.put("mediatimes", rs.getString("mediatimes"));
                itemsmList.add(itemsm);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return itemsmList;
    }

    public List<Map<String, String>> getList(PageUtil pu, int wxsid) {
        List<Map<String, String>> itemsmList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,describes,img,url,remark,orders,mediaid,mediatimes from itemsm where wxsid=? order by orders desc limit " + pu.getPageSize() * (0 < pu.getPage() ? pu.getPage() - 1 : 0) + "," + pu.getPageSize();
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> itemsm = new HashMap<String, String>();
                itemsm.put("id", rs.getString("id"));
                itemsm.put("name", rs.getString("name"));
                itemsm.put("describes", rs.getString("describes"));
                itemsm.put("img", rs.getString("img"));
                itemsm.put("url", rs.getString("url"));
                itemsm.put("remark", rs.getString("remark"));
                itemsm.put("orders", rs.getString("orders"));
                itemsm.put("mediaid", rs.getString("mediaid"));
                itemsm.put("mediatimes", rs.getString("mediatimes"));
                itemsmList.add(itemsm);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return itemsmList;
    }

    public int getConut(int wxsid) {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select count(id) count from itemsm where wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }

    public int getMaxId() {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select max(id) count from itemsm";
        try {
            ptst = conn.prepareStatement(sql);
            rs = ptst.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }

    public Map<String, String> getById(Map<String, String> itemsm) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,describes,img,url,remark,orders,mediaid,mediatimes from itemsm where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(itemsm.get("id")));
            rs = ptst.executeQuery();
            if (rs.next()) {
                itemsm = new HashMap<String, String>();
                itemsm.put("id", rs.getString("id"));
                itemsm.put("name", rs.getString("name"));
                itemsm.put("describes", rs.getString("describes"));
                itemsm.put("img", rs.getString("img"));
                itemsm.put("url", rs.getString("url"));
                itemsm.put("remark", rs.getString("remark"));
                itemsm.put("orders", rs.getString("orders"));
                itemsm.put("mediaid", rs.getString("mediaid"));
                itemsm.put("mediatimes", rs.getString("mediatimes"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return itemsm;
    }

    public List<Map<String, String>> getByNewsmList(Map<String, String> newsm, int wxsid) {
        List<Map<String, String>> itemsmList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,describes,img,url,remark,orders,mediaid,mediatimes from itemsm where id in (select iid from nim where nid=? and wxsid=?) and wxsid=? order by orders desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(newsm.get("id")));
            ptst.setInt(2, wxsid);
            ptst.setInt(3, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> itemsm = new HashMap<String, String>();
                itemsm.put("id", rs.getString("id"));
                itemsm.put("name", rs.getString("name"));
                itemsm.put("describes", rs.getString("describes"));
                itemsm.put("img", rs.getString("img"));
                itemsm.put("url", rs.getString("url"));
                itemsm.put("remark", rs.getString("remark"));
                itemsm.put("orders", rs.getString("orders"));
                itemsm.put("mediaid", rs.getString("mediaid"));
                itemsm.put("mediatimes", rs.getString("mediatimes"));
                itemsmList.add(itemsm);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return itemsmList;
    }

    public void add(Map<String, String> itemsm, int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "insert into itemsm(name,describes,img,url,remark,orders,mediaid,mediatimes,wxsid) values (?,?,?,?,?,?,?,?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, itemsm.get("name"));
            ptst.setString(2, itemsm.get("describes"));
            ptst.setString(3, itemsm.get("img"));
            ptst.setString(4, itemsm.get("url"));
            ptst.setString(5, itemsm.get("remark"));
            ptst.setString(6, itemsm.get("orders"));
            ptst.setString(7, itemsm.get("mediaid"));
            ptst.setString(8, itemsm.get("mediatimes"));
            ptst.setInt(9, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ItemsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void update(Map<String, String> itemsm) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update itemsm set name=?,describes=?,img=?,url=?,remark=?,mediaid=?,mediatimes=? where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, itemsm.get("name"));
            ptst.setString(2, itemsm.get("describes"));
            ptst.setString(3, itemsm.get("img"));
            ptst.setString(4, itemsm.get("url"));
            ptst.setString(5, itemsm.get("remark"));
            ptst.setString(6, itemsm.get("mediaid"));
            ptst.setString(7, itemsm.get("mediatimes"));
            ptst.setString(8, itemsm.get("id"));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ItemsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void updateOrders(Map<String, String> itemsm, Connection conn) {
        String sql = "update itemsm set orders=? where id=?";
        try {
            PreparedStatement ptst = conn.prepareStatement(sql);
            ptst.setString(1, itemsm.get("orders"));
            ptst.setString(2, itemsm.get("id"));
            ptst.executeUpdate();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(ItemsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateMedia(Map<String, String> itemsm) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update itemsm set mediaid=?,mediatimes=? where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, itemsm.get("mediaid"));
            ptst.setString(2, itemsm.get("mediatimes"));
            ptst.setString(3, itemsm.get("id"));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ItemsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void delete(Map<String, String> itemsm, Connection conn) {
        String sql = "delete from itemsm where id=?";
        try {
            PreparedStatement ptst = conn.prepareStatement(sql);
            ptst.setString(1, itemsm.get("id"));
            ptst.executeUpdate();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(ItemsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Map<String, String>> getNimByNewsMList(Map<String, String> newsm, int wxsid) {
        List<Map<String, String>> nimList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,nid,iid from nim where nid=? and wxsid=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(newsm.get("id")));
            ptst.setInt(2, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> nim = new HashMap<String, String>();
                nim.put("id", rs.getString("id"));
                nim.put("nid", rs.getString("nid"));
                nim.put("iid", rs.getString("iid"));
                nimList.add(nim);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return nimList;
    }

    public void delNimByNewsm(Map<String, String> newsm, Connection conn, int wxsid) {
        String sql = "delete from nim where nid=? and wxsid=?";
        try {
            PreparedStatement ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(!"".equals(newsm.get("id")) ? newsm.get("id") : "0"));
            ptst.setInt(2, wxsid);
            ptst.executeUpdate();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(ItemsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delNimByItemsm(Map<String, String> itemsm, Connection conn, int wxsid) {
        String sql = "delete from nim where iid=? and wxsid=?";
        try {
            PreparedStatement ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(!"".equals(itemsm.get("id")) ? itemsm.get("id") : "0"));
            ptst.setInt(2, wxsid);
            ptst.executeUpdate();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(ItemsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addNim(Map<String, String> nim, int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "insert into nim(nid,iid,wxsid) values (?,?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(nim.get("nid")));
            ptst.setInt(2, Integer.parseInt(nim.get("iid")));
            ptst.setInt(3, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ItemsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
}
