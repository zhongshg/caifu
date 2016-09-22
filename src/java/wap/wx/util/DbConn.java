/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class DbConn {

    private static final String URL = DbReader.getDbInfo().get("url");

    static {
        try {
            Class.forName(DbReader.getDbInfo().get("driverClassName"));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbConn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Connection getConn() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException ex) {
            Logger.getLogger(DbConn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public static void getAllClose(Connection conn, PreparedStatement ptst, ResultSet rs) {
        try {
            if (null != rs) {
                rs.close();
            }
            if (null != ptst) {
                ptst.close();
            }
            if (null != conn) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbConn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
