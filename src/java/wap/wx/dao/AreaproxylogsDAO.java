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
 * @author Administrator
 */
public class AreaproxylogsDAO {

    public List<Map<String, String>> getList(int wxsid, String openid) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String tempStr = "";
        if (0 != wxsid) {
            tempStr += " and wxsid='" + wxsid + "'";
        }
        if (null != openid && !"".equals(openid)) {
            tempStr += " and openid='" + openid + "'";
        }
        String sql = "select id,openid,areaproxymoney,times,remark,wxsid from areaproxylogs where 1=1" + tempStr + " order by orders desc";
        try {
            ptst = conn.prepareStatement(sql);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("openid", rs.getString("openid"));
                map.put("areaproxymoney", rs.getString("areaproxymoney"));
                map.put("times", rs.getString("times"));
                map.put("remark", rs.getString("remark"));
                map.put("wxsid", rs.getString("wxsid"));
                list.add(map);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AreaproxylogsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return list;
    }

    public void add(Map<String, String> map, String wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "insert into areaproxylogs(openid,areaproxymoney,times,remark,wxsid) values (?,?,?,?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, map.get("openid"));
            ptst.setFloat(2, Float.parseFloat(map.get("areaproxymoney")));
            ptst.setString(3, map.get("times"));
            ptst.setString(4, map.get("remark"));
            ptst.setString(5, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AreaproxylogsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }
}
