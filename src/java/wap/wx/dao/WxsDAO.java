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
public class WxsDAO {

    public List<Map<String, String>> getList() {
        List<Map<String, String>> wxList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,appid,appsecret,token,encodingaeskey,uid,status,mch_id,wxpaykey,certificate,endduring,weishopcolor,weishoptextcolor,qrcodewarns,weimyshopcolor,messageqrcodewarns,title1,title2,subtitle1,subtitle2,subtitle3,weimyshoptextcolor,send_name,wishing,redremark,qrcodewarnsfontstyle,qrcodewarnsfontcolor,qrcodewarnsfontsize,isshowsubscriber,vipidbasic,sendtoreceivelimit,receivetowithdrawlimit,withdrawmoneylimit,orderlimitperday,commissionsendtype,vipmoneylimit,isbuytovip,img,issharesubscriber,preferentialtype,discountratio,returnratio,adminsubscriber,myqrcodetype from wxs order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> wx = new HashMap<String, String>();
                wx.put("id", rs.getString("id"));
                wx.put("name", rs.getString("name"));
                wx.put("appid", rs.getString("appid"));
                wx.put("appsecret", rs.getString("appsecret"));
                wx.put("token", rs.getString("token"));
                wx.put("encodingaeskey", rs.getString("encodingaeskey"));
                wx.put("uid", rs.getString("uid"));
                wx.put("status", rs.getString("status"));
                wx.put("mch_id", rs.getString("mch_id"));
                wx.put("wxpaykey", rs.getString("wxpaykey"));
                wx.put("certificate", rs.getString("certificate"));
                wx.put("endduring", rs.getString("endduring"));
                wx.put("weishopcolor", rs.getString("weishopcolor"));
                wx.put("weishoptextcolor", rs.getString("weishoptextcolor"));
                wx.put("qrcodewarns", rs.getString("qrcodewarns"));
                wx.put("weimyshopcolor", rs.getString("weimyshopcolor"));
                wx.put("messageqrcodewarns", rs.getString("messageqrcodewarns"));
                wx.put("title1", rs.getString("title1"));
                wx.put("title2", rs.getString("title2"));
                wx.put("subtitle1", rs.getString("subtitle1"));
                wx.put("subtitle2", rs.getString("subtitle2"));
                wx.put("subtitle3", rs.getString("subtitle3"));
                wx.put("weimyshoptextcolor", rs.getString("weimyshoptextcolor"));
                wx.put("send_name", rs.getString("send_name"));
                wx.put("wishing", rs.getString("wishing"));
                wx.put("redremark", rs.getString("redremark"));
                wx.put("qrcodewarnsfontstyle", rs.getString("qrcodewarnsfontstyle"));
                wx.put("qrcodewarnsfontcolor", rs.getString("qrcodewarnsfontcolor"));
                wx.put("qrcodewarnsfontsize", rs.getString("qrcodewarnsfontsize"));
                wx.put("isshowsubscriber", rs.getString("isshowsubscriber"));
                wx.put("vipidbasic", rs.getString("vipidbasic"));
//                sendtoreceivelimit,receivetowithdrawlimit,withdrawmoneylimit,orderlimitperday,commissionsendtype,vipmoneylimit,isbuytovip
                wx.put("sendtoreceivelimit", rs.getString("sendtoreceivelimit"));
                wx.put("receivetowithdrawlimit", rs.getString("receivetowithdrawlimit"));
                wx.put("withdrawmoneylimit", rs.getString("withdrawmoneylimit"));
                wx.put("orderlimitperday", rs.getString("orderlimitperday"));
                wx.put("commissionsendtype", rs.getString("commissionsendtype"));
                wx.put("vipmoneylimit", rs.getString("vipmoneylimit"));
                wx.put("isbuytovip", rs.getString("isbuytovip"));
                wx.put("img", rs.getString("img"));
                wx.put("issharesubscriber", rs.getString("issharesubscriber"));
                wx.put("preferentialtype", rs.getString("preferentialtype"));
                wx.put("discountratio", rs.getString("discountratio"));
                wx.put("returnratio", rs.getString("returnratio"));
                wx.put("adminsubscriber", rs.getString("adminsubscriber"));
                wx.put("myqrcodetype", rs.getString("myqrcodetype"));
                wxList.add(wx);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WxsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return wxList;
    }

    public List<Map<String, String>> getList(Map<String, String> wx) {
        List<Map<String, String>> wxList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,appid,appsecret,token,encodingaeskey,uid,status,mch_id,wxpaykey,certificate,endduring,weishopcolor,weishoptextcolor,qrcodewarns,weimyshopcolor,messageqrcodewarns,title1,title2,subtitle1,subtitle2,subtitle3,weimyshoptextcolor,send_name,wishing,redremark,qrcodewarnsfontstyle,qrcodewarnsfontcolor,qrcodewarnsfontsize,isshowsubscriber,vipidbasic,sendtoreceivelimit,receivetowithdrawlimit,withdrawmoneylimit,orderlimitperday,commissionsendtype,vipmoneylimit,isbuytovip,img,issharesubscriber,preferentialtype,discountratio,returnratio,adminsubscriber,myqrcodetype from wxs where id=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(wx.get("id")));
            rs = ptst.executeQuery();
            if (rs.next()) {
                wx.put("id", rs.getString("id"));
                wx.put("name", rs.getString("name"));
                wx.put("appid", rs.getString("appid"));
                wx.put("appsecret", rs.getString("appsecret"));
                wx.put("token", rs.getString("token"));
                wx.put("encodingaeskey", rs.getString("encodingaeskey"));
                wx.put("uid", rs.getString("uid"));
                wx.put("status", rs.getString("status"));
                wx.put("mch_id", rs.getString("mch_id"));
                wx.put("wxpaykey", rs.getString("wxpaykey"));
                wx.put("certificate", rs.getString("certificate"));
                wx.put("endduring", rs.getString("endduring"));
                wx.put("weishopcolor", rs.getString("weishopcolor"));
                wx.put("weishoptextcolor", rs.getString("weishoptextcolor"));
                wx.put("qrcodewarns", rs.getString("qrcodewarns"));
                wx.put("weimyshopcolor", rs.getString("weimyshopcolor"));
                wx.put("messageqrcodewarns", rs.getString("messageqrcodewarns"));
                wx.put("title1", rs.getString("title1"));
                wx.put("title2", rs.getString("title2"));
                wx.put("subtitle1", rs.getString("subtitle1"));
                wx.put("subtitle2", rs.getString("subtitle2"));
                wx.put("subtitle3", rs.getString("subtitle3"));
                wx.put("weimyshoptextcolor", rs.getString("weimyshoptextcolor"));
                wx.put("send_name", rs.getString("send_name"));
                wx.put("wishing", rs.getString("wishing"));
                wx.put("redremark", rs.getString("redremark"));
                wx.put("qrcodewarnsfontstyle", rs.getString("qrcodewarnsfontstyle"));
                wx.put("qrcodewarnsfontcolor", rs.getString("qrcodewarnsfontcolor"));
                wx.put("qrcodewarnsfontsize", rs.getString("qrcodewarnsfontsize"));
                wx.put("isshowsubscriber", rs.getString("isshowsubscriber"));
                wx.put("vipidbasic", rs.getString("vipidbasic"));
                wx.put("sendtoreceivelimit", rs.getString("sendtoreceivelimit"));
                wx.put("receivetowithdrawlimit", rs.getString("receivetowithdrawlimit"));
                wx.put("withdrawmoneylimit", rs.getString("withdrawmoneylimit"));
                wx.put("orderlimitperday", rs.getString("orderlimitperday"));
                wx.put("commissionsendtype", rs.getString("commissionsendtype"));
                wx.put("vipmoneylimit", rs.getString("vipmoneylimit"));
                wx.put("isbuytovip", rs.getString("isbuytovip"));
                wx.put("img", rs.getString("img"));
                wx.put("issharesubscriber", rs.getString("issharesubscriber"));
                wx.put("preferentialtype", rs.getString("preferentialtype"));
                wx.put("discountratio", rs.getString("discountratio"));
                wx.put("returnratio", rs.getString("returnratio"));
                wx.put("adminsubscriber", rs.getString("adminsubscriber"));
                wx.put("myqrcodetype", rs.getString("myqrcodetype"));
                wxList.add(wx);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WxsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return wxList;
    }

    public List<Map<String, String>> getList(PageUtil pu) {
        List<Map<String, String>> wxList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,appid,appsecret,token,encodingaeskey,uid,status,mch_id,wxpaykey,certificate,endduring,weishopcolor,weishoptextcolor,qrcodewarns,weimyshopcolor,messageqrcodewarns,title1,title2,subtitle1,subtitle2,subtitle3,weimyshoptextcolor,send_name,wishing,redremark,qrcodewarnsfontstyle,qrcodewarnsfontcolor,qrcodewarnsfontsize,isshowsubscriber,vipidbasic,sendtoreceivelimit,receivetowithdrawlimit,withdrawmoneylimit,orderlimitperday,commissionsendtype,vipmoneylimit,isbuytovip,img,issharesubscriber,preferentialtype,discountratio,returnratio,adminsubscriber,myqrcodetype from wxs order by id desc limit " + pu.getPageSize() * (0 < pu.getPage() ? pu.getPage() - 1 : 0) + "," + pu.getPageSize();
        try {
            ptst = conn.prepareStatement(sql);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> wx = new HashMap<String, String>();
                wx.put("id", rs.getString("id"));
                wx.put("name", rs.getString("name"));
                wx.put("appid", rs.getString("appid"));
                wx.put("appsecret", rs.getString("appsecret"));
                wx.put("token", rs.getString("token"));
                wx.put("encodingaeskey", rs.getString("encodingaeskey"));
                wx.put("uid", rs.getString("uid"));
                wx.put("status", rs.getString("status"));
                wx.put("mch_id", rs.getString("mch_id"));
                wx.put("wxpaykey", rs.getString("wxpaykey"));
                wx.put("certificate", rs.getString("certificate"));
                wx.put("endduring", rs.getString("endduring"));
                wx.put("weishopcolor", rs.getString("weishopcolor"));
                wx.put("weishoptextcolor", rs.getString("weishoptextcolor"));
                wx.put("qrcodewarns", rs.getString("qrcodewarns"));
                wx.put("weimyshopcolor", rs.getString("weimyshopcolor"));
                wx.put("messageqrcodewarns", rs.getString("messageqrcodewarns"));
                wx.put("title1", rs.getString("title1"));
                wx.put("title2", rs.getString("title2"));
                wx.put("subtitle1", rs.getString("subtitle1"));
                wx.put("subtitle2", rs.getString("subtitle2"));
                wx.put("subtitle3", rs.getString("subtitle3"));
                wx.put("weimyshoptextcolor", rs.getString("weimyshoptextcolor"));
                wx.put("send_name", rs.getString("send_name"));
                wx.put("wishing", rs.getString("wishing"));
                wx.put("redremark", rs.getString("redremark"));
                wx.put("qrcodewarnsfontstyle", rs.getString("qrcodewarnsfontstyle"));
                wx.put("qrcodewarnsfontcolor", rs.getString("qrcodewarnsfontcolor"));
                wx.put("qrcodewarnsfontsize", rs.getString("qrcodewarnsfontsize"));
                wx.put("isshowsubscriber", rs.getString("isshowsubscriber"));
                wx.put("vipidbasic", rs.getString("vipidbasic"));
                wx.put("sendtoreceivelimit", rs.getString("sendtoreceivelimit"));
                wx.put("receivetowithdrawlimit", rs.getString("receivetowithdrawlimit"));
                wx.put("withdrawmoneylimit", rs.getString("withdrawmoneylimit"));
                wx.put("orderlimitperday", rs.getString("orderlimitperday"));
                wx.put("commissionsendtype", rs.getString("commissionsendtype"));
                wx.put("vipmoneylimit", rs.getString("vipmoneylimit"));
                wx.put("isbuytovip", rs.getString("isbuytovip"));
                wx.put("img", rs.getString("img"));
                wx.put("issharesubscriber", rs.getString("issharesubscriber"));
                wx.put("preferentialtype", rs.getString("preferentialtype"));
                wx.put("discountratio", rs.getString("discountratio"));
                wx.put("returnratio", rs.getString("returnratio"));
                wx.put("adminsubscriber", rs.getString("adminsubscriber"));
                wx.put("myqrcodetype", rs.getString("myqrcodetype"));
                wxList.add(wx);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WxsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return wxList;
    }

    public int getConut() {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select count(id) count from wxs";
        try {
            ptst = conn.prepareStatement(sql);
            rs = ptst.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(WxsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }

    public Map<String, String> getById(Map<String, String> wx) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,appid,appsecret,token,encodingaeskey,uid,status,mch_id,wxpaykey,certificate,endduring,weishopcolor,weishoptextcolor,qrcodewarns,weimyshopcolor,messageqrcodewarns,title1,title2,subtitle1,subtitle2,subtitle3,weimyshoptextcolor,send_name,wishing,redremark,qrcodewarnsfontstyle,qrcodewarnsfontcolor,qrcodewarnsfontsize,isshowsubscriber,vipidbasic,sendtoreceivelimit,receivetowithdrawlimit,withdrawmoneylimit,orderlimitperday,commissionsendtype,vipmoneylimit,isbuytovip,img,issharesubscriber,preferentialtype,discountratio,returnratio,adminsubscriber,myqrcodetype from wxs where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(wx.get("id")));
            rs = ptst.executeQuery();
            if (rs.next()) {
                wx = new HashMap<String, String>();
                wx.put("id", rs.getString("id"));
                wx.put("name", rs.getString("name"));
                wx.put("appid", rs.getString("appid"));
                wx.put("appsecret", rs.getString("appsecret"));
                wx.put("token", rs.getString("token"));
                wx.put("encodingaeskey", rs.getString("encodingaeskey"));
                wx.put("uid", rs.getString("uid"));
                wx.put("status", rs.getString("status"));
                wx.put("mch_id", rs.getString("mch_id"));
                wx.put("wxpaykey", rs.getString("wxpaykey"));
                wx.put("certificate", rs.getString("certificate"));
                wx.put("endduring", rs.getString("endduring"));
                wx.put("weishopcolor", rs.getString("weishopcolor"));
                wx.put("weishoptextcolor", rs.getString("weishoptextcolor"));
                wx.put("qrcodewarns", rs.getString("qrcodewarns"));
                wx.put("weimyshopcolor", rs.getString("weimyshopcolor"));
                wx.put("messageqrcodewarns", rs.getString("messageqrcodewarns"));
                wx.put("title1", rs.getString("title1"));
                wx.put("title2", rs.getString("title2"));
                wx.put("subtitle1", rs.getString("subtitle1"));
                wx.put("subtitle2", rs.getString("subtitle2"));
                wx.put("subtitle3", rs.getString("subtitle3"));
                wx.put("weimyshoptextcolor", rs.getString("weimyshoptextcolor"));
                wx.put("send_name", rs.getString("send_name"));
                wx.put("wishing", rs.getString("wishing"));
                wx.put("redremark", rs.getString("redremark"));
                wx.put("qrcodewarnsfontstyle", rs.getString("qrcodewarnsfontstyle"));
                wx.put("qrcodewarnsfontcolor", rs.getString("qrcodewarnsfontcolor"));
                wx.put("qrcodewarnsfontsize", rs.getString("qrcodewarnsfontsize"));
                wx.put("isshowsubscriber", rs.getString("isshowsubscriber"));
                wx.put("vipidbasic", rs.getString("vipidbasic"));
                wx.put("sendtoreceivelimit", rs.getString("sendtoreceivelimit"));
                wx.put("receivetowithdrawlimit", rs.getString("receivetowithdrawlimit"));
                wx.put("withdrawmoneylimit", rs.getString("withdrawmoneylimit"));
                wx.put("orderlimitperday", rs.getString("orderlimitperday"));
                wx.put("commissionsendtype", rs.getString("commissionsendtype"));
                wx.put("vipmoneylimit", rs.getString("vipmoneylimit"));
                wx.put("isbuytovip", rs.getString("isbuytovip"));
                wx.put("img", rs.getString("img"));
                wx.put("issharesubscriber", rs.getString("issharesubscriber"));
                wx.put("preferentialtype", rs.getString("preferentialtype"));
                wx.put("discountratio", rs.getString("discountratio"));
                wx.put("returnratio", rs.getString("returnratio"));
                wx.put("adminsubscriber", rs.getString("adminsubscriber"));
                wx.put("myqrcodetype", rs.getString("myqrcodetype"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(WxsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return wx;
    }

    public Map<String, String> getByAppid(Map<String, String> wx) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,name,appid,appsecret,token,encodingaeskey,uid,status,mch_id,wxpaykey,certificate,endduring,weishopcolor,weishoptextcolor,qrcodewarns,weimyshopcolor,messageqrcodewarns,title1,title2,subtitle1,subtitle2,subtitle3,weimyshoptextcolor,send_name,wishing,redremark,qrcodewarnsfontstyle,qrcodewarnsfontcolor,qrcodewarnsfontsize,isshowsubscriber,vipidbasic,sendtoreceivelimit,receivetowithdrawlimit,withdrawmoneylimit,orderlimitperday,commissionsendtype,vipmoneylimit,isbuytovip,img,issharesubscriber,preferentialtype,discountratio,returnratio,adminsubscriber,myqrcodetype from wxs where appid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, wx.get("appid"));
            rs = ptst.executeQuery();
            if (rs.next()) {
                wx = new HashMap<String, String>();
                wx.put("id", rs.getString("id"));
                wx.put("name", rs.getString("name"));
                wx.put("appid", rs.getString("appid"));
                wx.put("appsecret", rs.getString("appsecret"));
                wx.put("token", rs.getString("token"));
                wx.put("encodingaeskey", rs.getString("encodingaeskey"));
                wx.put("uid", rs.getString("uid"));
                wx.put("status", rs.getString("status"));
                wx.put("mch_id", rs.getString("mch_id"));
                wx.put("wxpaykey", rs.getString("wxpaykey"));
                wx.put("certificate", rs.getString("certificate"));
                wx.put("endduring", rs.getString("endduring"));
                wx.put("weishopcolor", rs.getString("weishopcolor"));
                wx.put("weishoptextcolor", rs.getString("weishoptextcolor"));
                wx.put("qrcodewarns", rs.getString("qrcodewarns"));
                wx.put("weimyshopcolor", rs.getString("weimyshopcolor"));
                wx.put("messageqrcodewarns", rs.getString("messageqrcodewarns"));
                wx.put("title1", rs.getString("title1"));
                wx.put("title2", rs.getString("title2"));
                wx.put("subtitle1", rs.getString("subtitle1"));
                wx.put("subtitle2", rs.getString("subtitle2"));
                wx.put("subtitle3", rs.getString("subtitle3"));
                wx.put("weimyshoptextcolor", rs.getString("weimyshoptextcolor"));
                wx.put("send_name", rs.getString("send_name"));
                wx.put("wishing", rs.getString("wishing"));
                wx.put("redremark", rs.getString("redremark"));
                wx.put("qrcodewarnsfontstyle", rs.getString("qrcodewarnsfontstyle"));
                wx.put("qrcodewarnsfontcolor", rs.getString("qrcodewarnsfontcolor"));
                wx.put("qrcodewarnsfontsize", rs.getString("qrcodewarnsfontsize"));
                wx.put("isshowsubscriber", rs.getString("isshowsubscriber"));
                wx.put("vipidbasic", rs.getString("vipidbasic"));
                wx.put("sendtoreceivelimit", rs.getString("sendtoreceivelimit"));
                wx.put("receivetowithdrawlimit", rs.getString("receivetowithdrawlimit"));
                wx.put("withdrawmoneylimit", rs.getString("withdrawmoneylimit"));
                wx.put("orderlimitperday", rs.getString("orderlimitperday"));
                wx.put("commissionsendtype", rs.getString("commissionsendtype"));
                wx.put("vipmoneylimit", rs.getString("vipmoneylimit"));
                wx.put("isbuytovip", rs.getString("isbuytovip"));
                wx.put("img", rs.getString("img"));
                wx.put("issharesubscriber", rs.getString("issharesubscriber"));
                wx.put("preferentialtype", rs.getString("preferentialtype"));
                wx.put("discountratio", rs.getString("discountratio"));
                wx.put("returnratio", rs.getString("returnratio"));
                wx.put("adminsubscriber", rs.getString("adminsubscriber"));
                wx.put("myqrcodetype", rs.getString("myqrcodetype"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(WxsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return wx;
    }

    public void add(Map<String, String> wx) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "insert into wxs(name,appid,appsecret,token,encodingaeskey,uid,status,mch_id,wxpaykey,certificate,endduring,weishopcolor,weishoptextcolor,qrcodewarns,weimyshopcolor,messageqrcodewarns,title1,title2,subtitle1,subtitle2,subtitle3,weimyshoptextcolor,send_name,wishing,redremark,qrcodewarnsfontstyle,qrcodewarnsfontcolor,qrcodewarnsfontsize,isshowsubscriber,vipidbasic,sendtoreceivelimit,receivetowithdrawlimit,withdrawmoneylimit,orderlimitperday,commissionsendtype,vipmoneylimit,isbuytovip,img,issharesubscriber,preferentialtype,discountratio,returnratio,adminsubscriber,myqrcodetype) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, wx.get("name"));
            ptst.setString(2, wx.get("appid"));
            ptst.setString(3, wx.get("appsecret"));
            ptst.setString(4, wx.get("token"));
            ptst.setString(5, wx.get("encodingaeskey"));
            ptst.setInt(6, Integer.parseInt(wx.get("uid")));
            ptst.setInt(7, Integer.parseInt(wx.get("status")));
            ptst.setString(8, wx.get("mch_id"));
            ptst.setString(9, wx.get("wxpaykey"));
            ptst.setString(10, wx.get("certificate"));
            ptst.setInt(11, Integer.parseInt(wx.get("endduring")));
            ptst.setString(12, wx.get("weishopcolor"));
            ptst.setString(13, wx.get("weishoptextcolor"));
            ptst.setString(14, wx.get("qrcodewarns"));
            ptst.setString(15, wx.get("weimyshopcolor"));
            ptst.setString(16, wx.get("messageqrcodewarns"));
            ptst.setString(17, wx.get("title1"));
            ptst.setString(18, wx.get("title2"));
            ptst.setString(19, wx.get("subtitle1"));
            ptst.setString(20, wx.get("subtitle2"));
            ptst.setString(21, wx.get("subtitle3"));
            ptst.setString(22, wx.get("weimyshoptextcolor"));
            ptst.setString(23, wx.get("send_name"));
            ptst.setString(24, wx.get("wishing"));
            ptst.setString(25, wx.get("redremark"));
            ptst.setString(26, wx.get("qrcodewarnsfontstyle"));
            ptst.setString(27, wx.get("qrcodewarnsfontcolor"));
            ptst.setInt(28, Integer.parseInt(wx.get("qrcodewarnsfontsize")));
            ptst.setInt(29, Integer.parseInt(wx.get("isshowsubscriber")));
            ptst.setInt(30, Integer.parseInt(wx.get("vipidbasic")));
            ptst.setInt(31, Integer.parseInt(wx.get("sendtoreceivelimit")));
            ptst.setInt(32, Integer.parseInt(wx.get("receivetowithdrawlimit")));
            ptst.setFloat(33, Float.parseFloat(wx.get("withdrawmoneylimit")));
            ptst.setInt(34, Integer.parseInt(wx.get("orderlimitperday")));
            ptst.setInt(35, Integer.parseInt(wx.get("commissionsendtype")));
            ptst.setFloat(36, Float.parseFloat(wx.get("vipmoneylimit")));
            ptst.setInt(37, Integer.parseInt(wx.get("isbuytovip")));
            ptst.setString(38, wx.get("img"));
            ptst.setInt(39, Integer.parseInt(wx.get("issharesubscriber")));
            ptst.setInt(40, Integer.parseInt(wx.get("preferentialtype")));
            ptst.setFloat(41, Float.parseFloat(wx.get("discountratio")));
            ptst.setFloat(42, Float.parseFloat(wx.get("returnratio")));
            ptst.setString(43, wx.get("adminsubscriber"));
            ptst.setInt(44, Integer.parseInt(wx.get("myqrcodetype")));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WxsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void update(Map<String, String> wx) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update wxs set name=?,appid=?,appsecret=?,token=?,encodingaeskey=?,uid=?,mch_id=?,wxpaykey=?,certificate=?,endduring=?,weishopcolor=?,weishoptextcolor=?,qrcodewarns=?,weimyshopcolor=?,messageqrcodewarns=?,title1=?,title2=?,subtitle1=?,subtitle2=?,subtitle3=?,weimyshoptextcolor=?,send_name=?,wishing=?,redremark=?,qrcodewarnsfontstyle=?,qrcodewarnsfontcolor=?,qrcodewarnsfontsize=?,isshowsubscriber=?,vipidbasic=? where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, wx.get("name"));
            ptst.setString(2, wx.get("appid"));
            ptst.setString(3, wx.get("appsecret"));
            ptst.setString(4, wx.get("token"));
            ptst.setString(5, wx.get("encodingaeskey"));
            ptst.setInt(6, Integer.parseInt(wx.get("uid")));
            ptst.setString(7, wx.get("mch_id"));
            ptst.setString(8, wx.get("wxpaykey"));
            ptst.setString(9, wx.get("certificate"));
            ptst.setInt(10, Integer.parseInt(wx.get("endduring")));
            ptst.setString(11, wx.get("weishopcolor"));
            ptst.setString(12, wx.get("weishoptextcolor"));
            ptst.setString(13, wx.get("qrcodewarns"));
            ptst.setString(14, wx.get("weimyshopcolor"));
            ptst.setString(15, wx.get("messageqrcodewarns"));
            ptst.setString(16, wx.get("title1"));
            ptst.setString(17, wx.get("title2"));
            ptst.setString(18, wx.get("subtitle1"));
            ptst.setString(19, wx.get("subtitle2"));
            ptst.setString(20, wx.get("subtitle3"));
            ptst.setString(21, wx.get("weimyshoptextcolor"));
            ptst.setString(22, wx.get("send_name"));
            ptst.setString(23, wx.get("wishing"));
            ptst.setString(24, wx.get("redremark"));
            ptst.setString(25, wx.get("qrcodewarnsfontstyle"));
            ptst.setString(26, wx.get("qrcodewarnsfontcolor"));
            ptst.setInt(27, Integer.parseInt(wx.get("qrcodewarnsfontsize")));
            ptst.setInt(28, Integer.parseInt(wx.get("isshowsubscriber")));
            ptst.setInt(29, Integer.parseInt(wx.get("vipidbasic")));
            ptst.setInt(30, Integer.parseInt(wx.get("id")));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WxsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void update1(Map<String, String> wx) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update wxs set name=?,appid=?,appsecret=?,token=?,encodingaeskey=?,uid=?,mch_id=?,wxpaykey=?,certificate=?,send_name=?,wishing=?,redremark=?,vipidbasic=?,adminsubscriber=?,myqrcodetype=? where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, wx.get("name"));
            ptst.setString(2, wx.get("appid"));
            ptst.setString(3, wx.get("appsecret"));
            ptst.setString(4, wx.get("token"));
            ptst.setString(5, wx.get("encodingaeskey"));
            ptst.setInt(6, Integer.parseInt(wx.get("uid")));
            ptst.setString(7, wx.get("mch_id"));
            ptst.setString(8, wx.get("wxpaykey"));
            ptst.setString(9, wx.get("certificate"));
            ptst.setString(10, wx.get("send_name"));
            ptst.setString(11, wx.get("wishing"));
            ptst.setString(12, wx.get("redremark"));
            ptst.setInt(13, Integer.parseInt(wx.get("vipidbasic")));
            ptst.setString(14, wx.get("adminsubscriber"));
            ptst.setInt(15, Integer.parseInt(wx.get("myqrcodetype")));
            ptst.setInt(16, Integer.parseInt(wx.get("id")));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WxsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void update2(Map<String, String> wx) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update wxs set endduring=?,weishopcolor=?,weishoptextcolor=?,qrcodewarns=?,weimyshopcolor=?,messageqrcodewarns=?,title1=?,title2=?,subtitle1=?,subtitle2=?,subtitle3=?,weimyshoptextcolor=?,qrcodewarnsfontstyle=?,qrcodewarnsfontcolor=?,qrcodewarnsfontsize=?,isshowsubscriber=?,sendtoreceivelimit=?,receivetowithdrawlimit=?,withdrawmoneylimit=?,orderlimitperday=?,commissionsendtype=?,vipmoneylimit=?,isbuytovip=?,img=?,issharesubscriber=?,preferentialtype=?,discountratio=?,returnratio=? where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(wx.get("endduring")));
            ptst.setString(2, wx.get("weishopcolor"));
            ptst.setString(3, wx.get("weishoptextcolor"));
            ptst.setString(4, wx.get("qrcodewarns"));
            ptst.setString(5, wx.get("weimyshopcolor"));
            ptst.setString(6, wx.get("messageqrcodewarns"));
            ptst.setString(7, wx.get("title1"));
            ptst.setString(8, wx.get("title2"));
            ptst.setString(9, wx.get("subtitle1"));
            ptst.setString(10, wx.get("subtitle2"));
            ptst.setString(11, wx.get("subtitle3"));
            ptst.setString(12, wx.get("weimyshoptextcolor"));
            ptst.setString(13, wx.get("qrcodewarnsfontstyle"));
            ptst.setString(14, wx.get("qrcodewarnsfontcolor"));
            ptst.setInt(15, Integer.parseInt(wx.get("qrcodewarnsfontsize")));
            ptst.setInt(16, Integer.parseInt(wx.get("isshowsubscriber")));
            ptst.setInt(17, Integer.parseInt(wx.get("sendtoreceivelimit")));
            ptst.setInt(18, Integer.parseInt(wx.get("receivetowithdrawlimit")));
            ptst.setFloat(19, Float.parseFloat(wx.get("withdrawmoneylimit")));
            ptst.setInt(20, Integer.parseInt(wx.get("orderlimitperday")));
            ptst.setInt(21, Integer.parseInt(wx.get("commissionsendtype")));
            ptst.setFloat(22, Float.parseFloat(wx.get("vipmoneylimit")));
            ptst.setInt(23, Integer.parseInt(wx.get("isbuytovip")));
            ptst.setString(24, wx.get("img"));
            ptst.setInt(25, Integer.parseInt(wx.get("issharesubscriber")));
            ptst.setInt(26, Integer.parseInt(wx.get("preferentialtype")));
            ptst.setFloat(27, Float.parseFloat(wx.get("discountratio")));
            ptst.setFloat(28, Float.parseFloat(wx.get("returnratio")));
            ptst.setInt(29, Integer.parseInt(wx.get("id")));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WxsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void update3(Map<String, String> wx) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update wxs set qrcodewarns=?,messageqrcodewarns=?,qrcodewarnsfontstyle=?,qrcodewarnsfontcolor=?,qrcodewarnsfontsize=? where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, wx.get("qrcodewarns"));
            ptst.setString(2, wx.get("messageqrcodewarns"));
            ptst.setString(3, wx.get("qrcodewarnsfontstyle"));
            ptst.setString(4, wx.get("qrcodewarnsfontcolor"));
            ptst.setInt(5, Integer.parseInt(wx.get("qrcodewarnsfontsize")));
            ptst.setInt(6, Integer.parseInt(wx.get("id")));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WxsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void upstatus(Map<String, String> wx) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update wxs set status=? where id=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(wx.get("status")));
            ptst.setInt(2, Integer.parseInt(wx.get("id")));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(WxsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void delete(Map<String, String> wx, Connection conn) {
        String sql = "delete from wxs where id=?";
        try {
            PreparedStatement ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(wx.get("id")));
            ptst.executeUpdate();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(WxsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Map<String, String> getWx(Map<String, String> users) {
        Map<String, String> wx = new HashMap<String, String>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select w.id,w.name,appid,appsecret,token,encodingaeskey,uid,status,mch_id,wxpaykey,certificate,endduring,weishopcolor,weishoptextcolor,qrcodewarns,weimyshopcolor,messageqrcodewarns,title1,title2,subtitle1,subtitle2,subtitle3,weimyshoptextcolor,send_name,wishing,redremark,qrcodewarnsfontstyle,qrcodewarnsfontcolor,qrcodewarnsfontsize,isshowsubscriber,vipidbasic,sendtoreceivelimit,receivetowithdrawlimit,withdrawmoneylimit,orderlimitperday,commissionsendtype,vipmoneylimit,isbuytovip,w.img,issharesubscriber,preferentialtype,discountratio,returnratio,adminsubscriber,myqrcodetype from wxs w,users u where w.id=u.wxsid and u.id=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(users.get("id")));
            rs = ptst.executeQuery();
            if (rs.next()) {
                wx.put("id", rs.getString("id"));
                wx.put("name", rs.getString("name"));
                wx.put("appid", rs.getString("appid"));
                wx.put("appsecret", rs.getString("appsecret"));
                wx.put("token", rs.getString("token"));
                wx.put("encodingaeskey", rs.getString("encodingaeskey"));
                wx.put("uid", rs.getString("uid"));
                wx.put("status", rs.getString("status"));
                wx.put("mch_id", rs.getString("mch_id"));
                wx.put("wxpaykey", rs.getString("wxpaykey"));
                wx.put("certificate", rs.getString("certificate"));
                wx.put("endduring", rs.getString("endduring"));
                wx.put("weishopcolor", rs.getString("weishopcolor"));
                wx.put("weishoptextcolor", rs.getString("weishoptextcolor"));
                wx.put("qrcodewarns", rs.getString("qrcodewarns"));
                wx.put("weimyshopcolor", rs.getString("weimyshopcolor"));
                wx.put("messageqrcodewarns", rs.getString("messageqrcodewarns"));
                wx.put("title1", rs.getString("title1"));
                wx.put("title2", rs.getString("title2"));
                wx.put("subtitle1", rs.getString("subtitle1"));
                wx.put("subtitle2", rs.getString("subtitle2"));
                wx.put("subtitle3", rs.getString("subtitle3"));
                wx.put("weimyshoptextcolor", rs.getString("weimyshoptextcolor"));
                wx.put("send_name", rs.getString("send_name"));
                wx.put("wishing", rs.getString("wishing"));
                wx.put("redremark", rs.getString("redremark"));
                wx.put("qrcodewarnsfontstyle", rs.getString("qrcodewarnsfontstyle"));
                wx.put("qrcodewarnsfontcolor", rs.getString("qrcodewarnsfontcolor"));
                wx.put("qrcodewarnsfontsize", rs.getString("qrcodewarnsfontsize"));
                wx.put("isshowsubscriber", rs.getString("isshowsubscriber"));
                wx.put("vipidbasic", rs.getString("vipidbasic"));
                wx.put("sendtoreceivelimit", rs.getString("sendtoreceivelimit"));
                wx.put("receivetowithdrawlimit", rs.getString("receivetowithdrawlimit"));
                wx.put("withdrawmoneylimit", rs.getString("withdrawmoneylimit"));
                wx.put("orderlimitperday", rs.getString("orderlimitperday"));
                wx.put("commissionsendtype", rs.getString("commissionsendtype"));
                wx.put("vipmoneylimit", rs.getString("vipmoneylimit"));
                wx.put("isbuytovip", rs.getString("isbuytovip"));
                wx.put("img", rs.getString("img"));
                wx.put("issharesubscriber", rs.getString("issharesubscriber"));
                wx.put("preferentialtype", rs.getString("preferentialtype"));
                wx.put("discountratio", rs.getString("discountratio"));
                wx.put("returnratio", rs.getString("returnratio"));
                wx.put("adminsubscriber", rs.getString("adminsubscriber"));
                wx.put("myqrcodetype", rs.getString("myqrcodetype"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(WxsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return wx;
    }
}
