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
 * @author ASUS
 */
public class AreaDAO {

    public List<Map<String, String>> getList(int pid) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,title,pid,sortid,lat,lng from t_area where pid=? order by sortid asc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, pid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("title", rs.getString("title"));
                map.put("pid", rs.getString("pid"));
                map.put("sortid", rs.getString("sortid"));
                map.put("lat", rs.getString("lat"));
                map.put("lng", rs.getString("lng"));
                list.add(map);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemsmDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return list;
    }
}
