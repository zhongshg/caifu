/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import wap.wx.dao.SubscriberDAO;
import wap.wx.dao.WxsDAO;
import wap.wx.util.DbConn;

/**
 *
 * @author Administrator
 */
public class WxsService {

    public void delete(Map<String, String> wx) {
        Connection conn = DbConn.getConn();
        try {
            conn.setAutoCommit(false);
            new WxsDAO().delete(wx, conn);
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

    public void deleteqrcode(String wxsid, HttpServletRequest request) {
        Connection conn = DbConn.getConn();
        try {
            conn.setAutoCommit(false);
            SubscriberDAO subscriberDAO = new SubscriberDAO();
            List<Map<String, String>> subscriberList = subscriberDAO.getList(Integer.parseInt(wxsid));
            for (Map<String, String> subscriber : subscriberList) {
                if (!"".equals(subscriber.get("qrcode")) && null != subscriber.get("qrcode")) {
                    java.io.File oldFile = new java.io.File(request.getServletContext()
                            .getRealPath(subscriber.get("qrcode")));
                    oldFile.delete();
                }
            }
            new SubscriberDAO().deleteqrcode(wxsid, "", "", conn);
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

    public void upstatus(Map<String, String> wx, List<Map<String, String>> list) {
        Connection conn = DbConn.getConn();
        try {
            conn.setAutoCommit(false);
            WxsDAO wxsDAO = new WxsDAO();
            for (Map<String, String> map : list) {
                wxsDAO.upstatus(map);
            }
            wxsDAO.upstatus(wx);
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
