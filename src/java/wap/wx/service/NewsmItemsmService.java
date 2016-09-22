/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import wap.wx.dao.ItemsmDAO;
import wap.wx.dao.NewsmDAO;
import wap.wx.util.DbConn;

/**
 *
 * @author Administrator
 */
public class NewsmItemsmService {

    public void change(String id, String[] itemsmckb, int wxsid) {
        ItemsmDAO itemsmDAO = new ItemsmDAO();
        Map<String, String> newsm = new HashMap<String, String>();
        Connection conn = DbConn.getConn();
        try {
            conn.setAutoCommit(false);
            newsm.put("id", id);
            itemsmDAO.delNimByNewsm(newsm, conn, wxsid);
            if (null != itemsmckb) {
                for (String iid : itemsmckb) {
                    Map<String, String> nim = new HashMap<String, String>();
                    nim.put("nid", id);
                    nim.put("iid", iid);
                    itemsmDAO.addNim(nim, wxsid);
                }
            }
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteNewsm(Map<String, String> newsm, int wxsid) {
        Connection conn = DbConn.getConn();
        try {
            conn.setAutoCommit(false);
            new NewsmDAO().delete(newsm, conn);
            new ItemsmDAO().delNimByNewsm(newsm, conn, wxsid);
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteItemsm(Map<String, String> itemsm, int wxsid) {
        Connection conn = DbConn.getConn();
        try {
            conn.setAutoCommit(false);
            new ItemsmDAO().delete(itemsm, conn);
            new ItemsmDAO().delNimByItemsm(itemsm, conn, wxsid);
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void order(Map<String, String> beforeItemsm, Map<String, String> afterItemsm, ItemsmDAO itemsmDAO) {
        Connection conn = DbConn.getConn();
        try {
            conn.setAutoCommit(false);
            itemsmDAO.updateOrders(beforeItemsm, conn);
            itemsmDAO.updateOrders(afterItemsm, conn);
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
