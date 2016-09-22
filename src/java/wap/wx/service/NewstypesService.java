/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import wap.wx.dao.NewstypesDAO;
import wap.wx.util.DbConn;

/**
 *
 * @author ASUS
 */
public class NewstypesService {

    public void delete(String id, int wxsid, HttpServletRequest request) {
        Connection conn = DbConn.getConn();
        try {
            conn.setAutoCommit(false);
            NewstypesDAO newstypesDAO = new NewstypesDAO();
            Map<String, String> newstypes = new HashMap<String, String>();
            newstypes.put("id", id);
            newstypes = newstypesDAO.getById(newstypes);
            newstypesDAO.deleteList(newstypes, newstypesDAO, request, conn, wxsid);
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
