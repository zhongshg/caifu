/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import wap.wx.util.DbConn;

/**
 *
 * @author Administrator
 */
public class ApiDAO {

    public Map<String, String> get(String apikey, int wxsid) {
        Map<String, String> api = new HashMap<String, String>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select apikey,apivalue,times from api where apikey=? and wxsid=? order by times desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, apikey);
            ptst.setInt(2, wxsid);
            rs = ptst.executeQuery();
            if (rs.next()) {
                api.put("apikey", rs.getString("apikey"));
                api.put("apivalue", rs.getString("apivalue"));
                api.put("times", rs.getString("times"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ApiDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return api;
    }

    public void set(Map<String, String> api, int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "insert into api(apikey,apivalue,times,wxsid) values (?,?,?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, api.get("apikey"));
            ptst.setString(2, api.get("apivalue"));
            ptst.setString(3, api.get("times"));
            ptst.setInt(4, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ApiDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
}
