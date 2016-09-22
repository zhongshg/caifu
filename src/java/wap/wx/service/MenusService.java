/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import wap.wx.dao.MenusDAO;
import wap.wx.util.DbConn;

/**
 *
 * @author Administrator
 */
public class MenusService {

    public void delete(Map<String, String> menus, MenusDAO menusDAO, int wxsid) {
        Connection conn = DbConn.getConn();
        try {
            conn.setAutoCommit(false);
            List<Map<String, String>> subMenusList = menusDAO.getByParentList(menus, wxsid);
            for (Map<String, String> subMenus : subMenusList) {
                menusDAO.delete(subMenus);
            }
            menusDAO.delete(menus);
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

    public void updateOrders(Map<String, String> beforeMenus, Map<String, String> afterMenus, MenusDAO menusDAO) {
        Connection conn = DbConn.getConn();
        try {
            conn.setAutoCommit(false);
            menusDAO.updateOrders(beforeMenus, conn);
            menusDAO.updateOrders(afterMenus, conn);
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
