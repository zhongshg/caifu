/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.dao;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
public class SubscriberDAO {

    public List<Map<String, String>> getList(int wxsid) {
        List<Map<String, String>> subscriberList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,openid,nickname,sex,language,city,province,country,headimgurl,unionid,percent,remark,times,wxsid,parentopenid,salemoney,commission,isvip,vipid,qrcode,qrcodemediaid,qrcodemediaidtimes,username,areaproxymoney,areaproxyprovince,areaproxycity,areaproxydiscount,callbackmoney,isurlcode,isorder,isdownorder,isdownsubscriber,isyj from subscriber where wxsid=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
//                username,areaproxymoney,areaproxyprovince,areaproxycity,areaproxydiscount
                Map<String, String> subscriber = new HashMap<String, String>();
                subscriber.put("id", rs.getString("id"));
                subscriber.put("openid", rs.getString("openid"));
                String nickname = rs.getString("nickname");
                try {
                    nickname = 1 == rs.getInt("isurlcode") ? URLDecoder.decode(nickname, "utf-8") : nickname;
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                subscriber.put("nickname", nickname);
                subscriber.put("sex", rs.getString("sex"));
                subscriber.put("language", rs.getString("language"));
                subscriber.put("city", rs.getString("city"));
                subscriber.put("province", rs.getString("province"));
                subscriber.put("country", rs.getString("country"));
                subscriber.put("headimgurl", rs.getString("headimgurl"));
                subscriber.put("unionid", rs.getString("unionid"));
                subscriber.put("percent", rs.getString("percent"));
                subscriber.put("remark", rs.getString("remark"));
                subscriber.put("times", rs.getString("times"));
                subscriber.put("wxsid", rs.getString("wxsid"));
                subscriber.put("parentopenid", rs.getString("parentopenid"));
                subscriber.put("salemoney", rs.getString("salemoney"));
                subscriber.put("commission", rs.getString("commission"));
                subscriber.put("isvip", rs.getString("isvip"));
                subscriber.put("vipid", rs.getString("vipid"));
                subscriber.put("qrcode", rs.getString("qrcode"));
                subscriber.put("qrcodemediaid", rs.getString("qrcodemediaid"));
                subscriber.put("qrcodemediaidtimes", rs.getString("qrcodemediaidtimes"));
//                username,areaproxymoney,areaproxyprovince,areaproxycity,areaproxydiscount
                subscriber.put("username", rs.getString("username"));
                subscriber.put("areaproxymoney", rs.getString("areaproxymoney"));
                subscriber.put("areaproxyprovince", rs.getString("areaproxyprovince"));
                subscriber.put("areaproxycity", rs.getString("areaproxycity"));
                subscriber.put("areaproxydiscount", rs.getString("areaproxydiscount"));
                subscriber.put("callbackmoney", rs.getString("callbackmoney"));
                subscriber.put("isorder", rs.getString("isorder"));
                subscriber.put("isdownorder", rs.getString("isdownorder"));
                subscriber.put("isdownsubscriber", rs.getString("isdownsubscriber"));
                subscriber.put("isyj", rs.getString("isyj"));
                subscriberList.add(subscriber);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return subscriberList;
    }

    public List<Map<String, String>> getList(int wxsid, String id, String openid, String isvip) {
        List<Map<String, String>> subscriberList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String temp = "";
        if (null != isvip && !"".equals(isvip) && !"-1".equals(isvip)) {
            temp += " and isvip=" + isvip;
        }
        if (null != openid && !"".equals(openid)) {
            temp += " and openid='" + openid + "'";
        }
        if (null != id && !"".equals(id)) {
            temp += " and id=" + id;
        }
        String sql = "select id,openid,nickname,sex,language,city,province,country,headimgurl,unionid,percent,remark,times,wxsid,parentopenid,salemoney,commission,isvip,vipid,qrcode,qrcodemediaid,qrcodemediaidtimes,username,areaproxymoney,areaproxyprovince,areaproxycity,areaproxydiscount,callbackmoney,isurlcode,isorder,isdownorder,isdownsubscriber,isyj from subscriber where wxsid=? " + temp + " order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> subscriber = new HashMap<String, String>();
                subscriber.put("id", rs.getString("id"));
                subscriber.put("openid", rs.getString("openid"));
                String nickname = rs.getString("nickname");
                try {
                    nickname = 1 == rs.getInt("isurlcode") ? URLDecoder.decode(nickname, "utf-8") : nickname;
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                subscriber.put("nickname", nickname);
                subscriber.put("sex", rs.getString("sex"));
                subscriber.put("language", rs.getString("language"));
                subscriber.put("city", rs.getString("city"));
                subscriber.put("province", rs.getString("province"));
                subscriber.put("country", rs.getString("country"));
                subscriber.put("headimgurl", rs.getString("headimgurl"));
                subscriber.put("unionid", rs.getString("unionid"));
                subscriber.put("percent", rs.getString("percent"));
                subscriber.put("remark", rs.getString("remark"));
                subscriber.put("times", rs.getString("times"));
                subscriber.put("wxsid", rs.getString("wxsid"));
                subscriber.put("parentopenid", rs.getString("parentopenid"));
                subscriber.put("salemoney", rs.getString("salemoney"));
                subscriber.put("commission", rs.getString("commission"));
                subscriber.put("isvip", rs.getString("isvip"));
                subscriber.put("vipid", rs.getString("vipid"));
                subscriber.put("qrcode", rs.getString("qrcode"));
                subscriber.put("qrcodemediaid", rs.getString("qrcodemediaid"));
                subscriber.put("qrcodemediaidtimes", rs.getString("qrcodemediaidtimes"));
                subscriber.put("username", rs.getString("username"));
                subscriber.put("areaproxymoney", rs.getString("areaproxymoney"));
                subscriber.put("areaproxyprovince", rs.getString("areaproxyprovince"));
                subscriber.put("areaproxycity", rs.getString("areaproxycity"));
                subscriber.put("areaproxydiscount", rs.getString("areaproxydiscount"));
                subscriber.put("callbackmoney", rs.getString("callbackmoney"));
                subscriber.put("isorder", rs.getString("isorder"));
                subscriber.put("isdownorder", rs.getString("isdownorder"));
                subscriber.put("isdownsubscriber", rs.getString("isdownsubscriber"));
                subscriber.put("isyj", rs.getString("isyj"));
                subscriberList.add(subscriber);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return subscriberList;
    }

    public List<Map<String, String>> getList(PageUtil pu, String wxsid, int sign, int selectsign, String text, String starttime, String endtime) {
        StringBuilder sqlcondition = new StringBuilder();
        String sqlcondition2 = "";
        if (1 == sign) {
            if (1 == selectsign && null != text && !"".equals(text)) {
                sqlcondition.append(" and id=" + text);
            } else if (2 == selectsign && null != text && !"".equals(text)) {
                sqlcondition.append(" and username='" + text + "'");
            } else if (3 == selectsign && null != text && !"".equals(text)) {
                sqlcondition.append(" and openid in (select UserId from t_address where Phone='" + text + "')");
            } else if (4 == selectsign) {
                sqlcondition.append(" and isvip=1");
            } else if (5 == selectsign) {
                sqlcondition.append(" and isvip=0");
            }
        } else if (2 == sign || 3 != sign) {
            if (0 == selectsign) {//只有默认情况下查询时间段关注用户，其他为全部用户，时间与之无关
                if (null != starttime && !"".equals("starttime")) {
                    sqlcondition.append(" and to_days(times)>=to_days('" + starttime + "')");
                }
                if (null != endtime && !"".equals("endtime")) {
                    sqlcondition.append(" and to_days(times)<=to_days('" + endtime + "')");
                }
            } else if (1 == selectsign || 2 == selectsign || 3 == selectsign || 4 == selectsign) {
                sqlcondition.append(" and isdownorder=1");
            } else if (5 == selectsign || 6 == selectsign || 7 == selectsign) {
                sqlcondition.append(" and isdownsubscriber=1");
            } else if (8 == selectsign) {
                sqlcondition.append(" and isyj=1");
            } else if (9 == selectsign) {
                sqlcondition.append(" and areaproxymoney>0");
            } else if (10 == selectsign) {
                sqlcondition.append(" and callbackmoney>0");
            }
        }
        List<Map<String, String>> subscriberList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,openid,nickname,sex,language,city,province,country,headimgurl,unionid,percent,remark,times,wxsid,parentopenid,salemoney,commission,isvip,vipid,qrcode,qrcodemediaid,qrcodemediaidtimes,username,areaproxymoney,areaproxyprovince,areaproxycity,areaproxydiscount,callbackmoney,isurlcode,isorder,isdownorder,isdownsubscriber,isyj from subscriber where 1=1 " + sqlcondition.toString() + " and wxsid=? order by id desc";
        if (null != pu) {
            sql += " limit " + pu.getPageSize() * (0 < pu.getPage() ? pu.getPage() - 1 : 0) + "," + pu.getPageSize();
        } else {
            sql += sqlcondition2;
        }
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, Integer.parseInt(wxsid));
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> subscriber = new HashMap<String, String>();
                subscriber.put("id", rs.getString("id"));
                subscriber.put("openid", rs.getString("openid"));
                String nickname = rs.getString("nickname");
                try {
                    nickname = 1 == rs.getInt("isurlcode") ? URLDecoder.decode(nickname, "utf-8") : nickname;
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                subscriber.put("nickname", nickname);
                subscriber.put("sex", rs.getString("sex"));
                subscriber.put("language", rs.getString("language"));
                subscriber.put("city", rs.getString("city"));
                subscriber.put("province", rs.getString("province"));
                subscriber.put("country", rs.getString("country"));
                subscriber.put("headimgurl", rs.getString("headimgurl"));
                subscriber.put("unionid", rs.getString("unionid"));
                subscriber.put("percent", rs.getString("percent"));
                subscriber.put("remark", rs.getString("remark"));
                subscriber.put("times", rs.getString("times"));
                subscriber.put("wxsid", rs.getString("wxsid"));
                subscriber.put("parentopenid", rs.getString("parentopenid"));
                subscriber.put("salemoney", rs.getString("salemoney"));
                subscriber.put("commission", rs.getString("commission"));
                subscriber.put("isvip", rs.getString("isvip"));
                subscriber.put("vipid", rs.getString("vipid"));
                subscriber.put("qrcode", rs.getString("qrcode"));
                subscriber.put("qrcodemediaid", rs.getString("qrcodemediaid"));
                subscriber.put("qrcodemediaidtimes", rs.getString("qrcodemediaidtimes"));
                subscriber.put("username", rs.getString("username"));
                subscriber.put("areaproxymoney", rs.getString("areaproxymoney"));
                subscriber.put("areaproxyprovince", rs.getString("areaproxyprovince"));
                subscriber.put("areaproxycity", rs.getString("areaproxycity"));
                subscriber.put("areaproxydiscount", rs.getString("areaproxydiscount"));
                subscriber.put("callbackmoney", rs.getString("callbackmoney"));
                subscriber.put("isorder", rs.getString("isorder"));
                subscriber.put("isdownorder", rs.getString("isdownorder"));
                subscriber.put("isdownsubscriber", rs.getString("isdownsubscriber"));
                subscriber.put("isyj", rs.getString("isyj"));
                subscriberList.add(subscriber);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return subscriberList;
    }

    public List<Map<String, String>> getList(PageUtil pu, int wxsid, String id, String openid, String isvip) {
        List<Map<String, String>> subscriberList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String temp = "";
        if (-1 != wxsid) {
            temp += " and wxsid=" + wxsid;
        }
        if (null != isvip && !"".equals(isvip) && !"-1".equals(isvip)) {
            temp += " and isvip=" + isvip;
        }
        if (null != openid && !"".equals(openid)) {
            temp += " and openid='" + openid + "'";
        }
        if (null != id && !"".equals(id)) {
            temp += " and id=" + id;
        }
        String sql = "select id,openid,nickname,sex,language,city,province,country,headimgurl,unionid,percent,remark,times,wxsid,parentopenid,salemoney,commission,isvip,vipid,qrcode,qrcodemediaid,qrcodemediaidtimes,wxsid,username,areaproxymoney,areaproxyprovince,areaproxycity,areaproxydiscount,callbackmoney,isurlcode,isorder,isdownorder,isdownsubscriber,isyj from subscriber where 1=1 " + temp + " order by id desc ";
        if (null != pu) {
            sql += "limit " + pu.getPageSize() * (0 < pu.getPage() ? pu.getPage() - 1 : 0) + "," + pu.getPageSize();
        }
        try {
            ptst = conn.prepareStatement(sql);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> subscriber = new HashMap<String, String>();
                subscriber.put("id", rs.getString("id"));
                subscriber.put("openid", rs.getString("openid"));
                String nickname = rs.getString("nickname");
                try {
                    nickname = 1 == rs.getInt("isurlcode") ? URLDecoder.decode(nickname, "utf-8") : nickname;
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                subscriber.put("nickname", nickname);
                subscriber.put("sex", rs.getString("sex"));
                subscriber.put("language", rs.getString("language"));
                subscriber.put("city", rs.getString("city"));
                subscriber.put("province", rs.getString("province"));
                subscriber.put("country", rs.getString("country"));
                subscriber.put("headimgurl", rs.getString("headimgurl"));
                subscriber.put("unionid", rs.getString("unionid"));
                subscriber.put("percent", rs.getString("percent"));
                subscriber.put("remark", rs.getString("remark"));
                subscriber.put("times", rs.getString("times"));
                subscriber.put("wxsid", rs.getString("wxsid"));
                subscriber.put("parentopenid", rs.getString("parentopenid"));
                subscriber.put("salemoney", rs.getString("salemoney"));
                subscriber.put("commission", rs.getString("commission"));
                subscriber.put("isvip", rs.getString("isvip"));
                subscriber.put("vipid", rs.getString("vipid"));
                subscriber.put("qrcode", rs.getString("qrcode"));
                subscriber.put("qrcodemediaid", rs.getString("qrcodemediaid"));
                subscriber.put("qrcodemediaidtimes", rs.getString("qrcodemediaidtimes"));
                subscriber.put("wxsid", rs.getString("wxsid"));
                subscriber.put("username", rs.getString("username"));
                subscriber.put("areaproxymoney", rs.getString("areaproxymoney"));
                subscriber.put("areaproxyprovince", rs.getString("areaproxyprovince"));
                subscriber.put("areaproxycity", rs.getString("areaproxycity"));
                subscriber.put("areaproxydiscount", rs.getString("areaproxydiscount"));
                subscriber.put("callbackmoney", rs.getString("callbackmoney"));
                subscriber.put("isorder", rs.getString("isorder"));
                subscriber.put("isdownorder", rs.getString("isdownorder"));
                subscriber.put("isdownsubscriber", rs.getString("isdownsubscriber"));
                subscriber.put("isyj", rs.getString("isyj"));
                subscriberList.add(subscriber);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return subscriberList;
    }

    public List<Map<String, String>> getByareaproxyList(int wxsid, String key, String value) {
        List<Map<String, String>> subscriberList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String temp = "";
        if (null != key && !"".equals(key) && null != value && !"".equals(value)) {
            temp += " and " + key + "='" + value + "'";
        }
        String sql = "select id,openid,nickname,sex,language,city,province,country,headimgurl,unionid,percent,remark,times,wxsid,parentopenid,salemoney,commission,isvip,vipid,qrcode,qrcodemediaid,qrcodemediaidtimes,username,areaproxymoney,areaproxyprovince,areaproxycity,areaproxydiscount,callbackmoney,isurlcode,isorder,isdownorder,isdownsubscriber,isyj from subscriber where wxsid=? " + temp + " order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> subscriber = new HashMap<String, String>();
                subscriber.put("id", rs.getString("id"));
                subscriber.put("openid", rs.getString("openid"));
                String nickname = rs.getString("nickname");
                try {
                    nickname = 1 == rs.getInt("isurlcode") ? URLDecoder.decode(nickname, "utf-8") : nickname;
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                subscriber.put("nickname", nickname);
                subscriber.put("sex", rs.getString("sex"));
                subscriber.put("language", rs.getString("language"));
                subscriber.put("city", rs.getString("city"));
                subscriber.put("province", rs.getString("province"));
                subscriber.put("country", rs.getString("country"));
                subscriber.put("headimgurl", rs.getString("headimgurl"));
                subscriber.put("unionid", rs.getString("unionid"));
                subscriber.put("percent", rs.getString("percent"));
                subscriber.put("remark", rs.getString("remark"));
                subscriber.put("times", rs.getString("times"));
                subscriber.put("wxsid", rs.getString("wxsid"));
                subscriber.put("parentopenid", rs.getString("parentopenid"));
                subscriber.put("salemoney", rs.getString("salemoney"));
                subscriber.put("commission", rs.getString("commission"));
                subscriber.put("isvip", rs.getString("isvip"));
                subscriber.put("vipid", rs.getString("vipid"));
                subscriber.put("qrcode", rs.getString("qrcode"));
                subscriber.put("qrcodemediaid", rs.getString("qrcodemediaid"));
                subscriber.put("qrcodemediaidtimes", rs.getString("qrcodemediaidtimes"));
                subscriber.put("username", rs.getString("username"));
                subscriber.put("areaproxymoney", rs.getString("areaproxymoney"));
                subscriber.put("areaproxyprovince", rs.getString("areaproxyprovince"));
                subscriber.put("areaproxycity", rs.getString("areaproxycity"));
                subscriber.put("areaproxydiscount", rs.getString("areaproxydiscount"));
                subscriber.put("callbackmoney", rs.getString("callbackmoney"));
                subscriber.put("isorder", rs.getString("isorder"));
                subscriber.put("isdownorder", rs.getString("isdownorder"));
                subscriber.put("isdownsubscriber", rs.getString("isdownsubscriber"));
                subscriber.put("isyj", rs.getString("isyj"));
                subscriberList.add(subscriber);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return subscriberList;
    }

    public int getCount(int wxsid, String id, String openid, String isvip) {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String temp = "";
        if (-1 != wxsid) {
            temp += " and wxsid=" + wxsid;
        }
        if (null != isvip && !"".equals(isvip) && !"-1".equals(isvip)) {
            temp += " and isvip=" + isvip;
        }
        if (null != openid && !"".equals(openid)) {
            temp += " and openid='" + openid + "'";
        }
        if (null != id && !"".equals(id)) {
            temp += " and id=" + id;
        }
        String sql = "select count(id) count from subscriber where 1=1 " + temp + " order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            rs = ptst.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }

        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }

    /**
     * 特区
     *
     * @param wxsid
     * @param parentopenid
     * @return
     */
    public List<Map<String, String>> getByParentopenidList(String wxsid, String parentopenid, Connection conn) {
        List<Map<String, String>> subscriberList = new ArrayList<Map<String, String>>();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,openid,nickname,sex,language,city,province,country,headimgurl,unionid,percent,remark,times,wxsid,parentopenid,salemoney,commission,isvip,vipid,qrcode,qrcodemediaid,qrcodemediaidtimes,username,areaproxymoney,areaproxyprovince,areaproxycity,areaproxydiscount,callbackmoney,isurlcode,isorder,isdownorder,isdownsubscriber,isyj from subscriber where wxsid=? and parentopenid=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, wxsid);
            ptst.setString(2, parentopenid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> subscriber = new HashMap<String, String>();
                subscriber.put("id", rs.getString("id"));
                subscriber.put("openid", rs.getString("openid"));
                String nickname = rs.getString("nickname");
                try {
                    nickname = 1 == rs.getInt("isurlcode") ? URLDecoder.decode(nickname, "utf-8") : nickname;
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                subscriber.put("nickname", nickname);
                subscriber.put("sex", rs.getString("sex"));
                subscriber.put("language", rs.getString("language"));
                subscriber.put("city", rs.getString("city"));
                subscriber.put("province", rs.getString("province"));
                subscriber.put("country", rs.getString("country"));
                subscriber.put("headimgurl", rs.getString("headimgurl"));
                subscriber.put("unionid", rs.getString("unionid"));
                subscriber.put("percent", rs.getString("percent"));
                subscriber.put("remark", rs.getString("remark"));
                subscriber.put("times", rs.getString("times"));
                subscriber.put("wxsid", rs.getString("wxsid"));
                subscriber.put("parentopenid", rs.getString("parentopenid"));
                subscriber.put("salemoney", rs.getString("salemoney"));
                subscriber.put("commission", rs.getString("commission"));
                subscriber.put("isvip", rs.getString("isvip"));
                subscriber.put("vipid", rs.getString("vipid"));
                subscriber.put("qrcode", rs.getString("qrcode"));
                subscriber.put("qrcodemediaid", rs.getString("qrcodemediaid"));
                subscriber.put("qrcodemediaidtimes", rs.getString("qrcodemediaidtimes"));
                subscriber.put("username", rs.getString("username"));
                subscriber.put("areaproxymoney", rs.getString("areaproxymoney"));
                subscriber.put("areaproxyprovince", rs.getString("areaproxyprovince"));
                subscriber.put("areaproxycity", rs.getString("areaproxycity"));
                subscriber.put("areaproxydiscount", rs.getString("areaproxydiscount"));
                subscriber.put("callbackmoney", rs.getString("callbackmoney"));
                subscriber.put("isorder", rs.getString("isorder"));
                subscriber.put("isdownorder", rs.getString("isdownorder"));
                subscriber.put("isdownsubscriber", rs.getString("isdownsubscriber"));
                subscriber.put("isyj", rs.getString("isyj"));
                subscriberList.add(subscriber);
            }
            rs.close();
            ptst.close();;
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subscriberList;
    }

    public List<Map<String, String>> getByParentopenidList(String wxsid, String parentopenid, String starttime, String endtime, Connection conn) {
//        if (null == starttime || "".equals(starttime) || "null".equals(starttime)) {
//            starttime = "2015-01-01 00:00:00";
//        }
//        if (null == endtime || "".equals(endtime) || "null".equals(endtime)) {
//            endtime = WxMenuUtils.format.format(new Date());
//        }
        StringBuilder conditionstr = new StringBuilder("");
        if (starttime != null && !starttime.equals("")) {
            conditionstr.append(" and to_days(times)>=to_days('").append(starttime).append("')");
        }
        if (endtime != null && !endtime.equals("")) {
            conditionstr.append(" and to_days(times)<=to_days('").append(endtime).append("')");
        }
        List<Map<String, String>> subscriberList = new ArrayList<Map<String, String>>();
        PreparedStatement ptst = null;
        ResultSet rs = null;
//        times between ? and ?
        String sql = "select id,openid,nickname,sex,language,city,province,country,headimgurl,unionid,percent,remark,times,wxsid,parentopenid,salemoney,commission,isvip,vipid,qrcode,qrcodemediaid,qrcodemediaidtimes,username,areaproxymoney,areaproxyprovince,areaproxycity,areaproxydiscount,callbackmoney,isurlcode,isorder,isdownorder,isdownsubscriber,isyj from subscriber where 1=1 " + conditionstr.toString() + " and parentopenid=? and wxsid=? order by id desc limit 0,300";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, parentopenid);
            ptst.setString(2, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> subscriber = new HashMap<String, String>();
                subscriber.put("id", rs.getString("id"));
                subscriber.put("openid", rs.getString("openid"));
                String nickname = rs.getString("nickname");
                try {
                    nickname = 1 == rs.getInt("isurlcode") ? URLDecoder.decode(nickname, "utf-8") : nickname;
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                subscriber.put("nickname", nickname);
                subscriber.put("sex", rs.getString("sex"));
                subscriber.put("language", rs.getString("language"));
                subscriber.put("city", rs.getString("city"));
                subscriber.put("province", rs.getString("province"));
                subscriber.put("country", rs.getString("country"));
                subscriber.put("headimgurl", rs.getString("headimgurl"));
                subscriber.put("unionid", rs.getString("unionid"));
                subscriber.put("percent", rs.getString("percent"));
                subscriber.put("remark", rs.getString("remark"));
                subscriber.put("times", rs.getString("times"));
                subscriber.put("wxsid", rs.getString("wxsid"));
                subscriber.put("parentopenid", rs.getString("parentopenid"));
                subscriber.put("salemoney", rs.getString("salemoney"));
                subscriber.put("commission", rs.getString("commission"));
                subscriber.put("isvip", rs.getString("isvip"));
                subscriber.put("vipid", rs.getString("vipid"));
                subscriber.put("qrcode", rs.getString("qrcode"));
                subscriber.put("qrcodemediaid", rs.getString("qrcodemediaid"));
                subscriber.put("qrcodemediaidtimes", rs.getString("qrcodemediaidtimes"));
                subscriber.put("username", rs.getString("username"));
                subscriber.put("areaproxymoney", rs.getString("areaproxymoney"));
                subscriber.put("areaproxyprovince", rs.getString("areaproxyprovince"));
                subscriber.put("areaproxycity", rs.getString("areaproxycity"));
                subscriber.put("areaproxydiscount", rs.getString("areaproxydiscount"));
                subscriber.put("callbackmoney", rs.getString("callbackmoney"));
                subscriber.put("isorder", rs.getString("isorder"));
                subscriber.put("isdownorder", rs.getString("isdownorder"));
                subscriber.put("isdownsubscriber", rs.getString("isdownsubscriber"));
                subscriber.put("isyj", rs.getString("isyj"));
                subscriberList.add(subscriber);
            }
            rs.close();
            ptst.close();;
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subscriberList;
    }

    public List<Map<String, String>> getByParentopenidList(String wxsid, String parentopenid, String starttime, String endtime, Connection conn, int sign, int selectsign) {
//        if (null == starttime || "".equals(starttime) || "null".equals(starttime)) {
//            starttime = "2015-01-01 00:00:00";
//        }
//        if (null == endtime || "".equals(endtime) || "null".equals(endtime)) {
//            endtime = WxMenuUtils.format.format(new Date());
//        }
        StringBuilder conditionstr = new StringBuilder("");
        if (starttime != null && !starttime.equals("")) {
            conditionstr.append(" and to_days(times)>=to_days('").append(starttime).append("')");
        }
        if (endtime != null && !endtime.equals("")) {
            conditionstr.append(" and to_days(times)<=to_days('").append(endtime).append("')");
        }

        if (2 == sign) {
            if (1 == selectsign || 2 == selectsign || 3 == selectsign || 4 == selectsign) {
                conditionstr.append(" and isdownorder=1");
            } else if (5 == selectsign || 6 == selectsign || 7 == selectsign) {
                conditionstr.append(" and isdownsubscriber=1");
            } else if (8 == selectsign) {
                conditionstr.append(" and isyj=1");
            }
        }
        List<Map<String, String>> subscriberList = new ArrayList<Map<String, String>>();
        PreparedStatement ptst = null;
        ResultSet rs = null;
//        times between ? and ?
        String sql = "select id,openid,nickname,sex,language,city,province,country,headimgurl,unionid,percent,remark,times,wxsid,parentopenid,salemoney,commission,isvip,vipid,qrcode,qrcodemediaid,qrcodemediaidtimes,username,areaproxymoney,areaproxyprovince,areaproxycity,areaproxydiscount,callbackmoney,isurlcode,isorder,isdownorder,isdownsubscriber,isyj from subscriber where 1=1 " + conditionstr.toString() + " and parentopenid=? and wxsid=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, parentopenid);
            ptst.setString(2, wxsid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> subscriber = new HashMap<String, String>();
                subscriber.put("id", rs.getString("id"));
                subscriber.put("openid", rs.getString("openid"));
                String nickname = rs.getString("nickname");
                try {
                    nickname = 1 == rs.getInt("isurlcode") ? URLDecoder.decode(nickname, "utf-8") : nickname;
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                subscriber.put("nickname", nickname);
                subscriber.put("sex", rs.getString("sex"));
                subscriber.put("language", rs.getString("language"));
                subscriber.put("city", rs.getString("city"));
                subscriber.put("province", rs.getString("province"));
                subscriber.put("country", rs.getString("country"));
                subscriber.put("headimgurl", rs.getString("headimgurl"));
                subscriber.put("unionid", rs.getString("unionid"));
                subscriber.put("percent", rs.getString("percent"));
                subscriber.put("remark", rs.getString("remark"));
                subscriber.put("times", rs.getString("times"));
                subscriber.put("wxsid", rs.getString("wxsid"));
                subscriber.put("parentopenid", rs.getString("parentopenid"));
                subscriber.put("salemoney", rs.getString("salemoney"));
                subscriber.put("commission", rs.getString("commission"));
                subscriber.put("isvip", rs.getString("isvip"));
                subscriber.put("vipid", rs.getString("vipid"));
                subscriber.put("qrcode", rs.getString("qrcode"));
                subscriber.put("qrcodemediaid", rs.getString("qrcodemediaid"));
                subscriber.put("qrcodemediaidtimes", rs.getString("qrcodemediaidtimes"));
                subscriber.put("username", rs.getString("username"));
                subscriber.put("areaproxymoney", rs.getString("areaproxymoney"));
                subscriber.put("areaproxyprovince", rs.getString("areaproxyprovince"));
                subscriber.put("areaproxycity", rs.getString("areaproxycity"));
                subscriber.put("areaproxydiscount", rs.getString("areaproxydiscount"));
                subscriber.put("callbackmoney", rs.getString("callbackmoney"));
                subscriber.put("isorder", rs.getString("isorder"));
                subscriber.put("isdownorder", rs.getString("isdownorder"));
                subscriber.put("isdownsubscriber", rs.getString("isdownsubscriber"));
                subscriber.put("isyj", rs.getString("isyj"));
                subscriberList.add(subscriber);
            }
            rs.close();
            ptst.close();;
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subscriberList;
    }

    public List<Map<String, String>> getByParentopenidList(String wxsid, String parentopenid) {
        List<Map<String, String>> subscriberList = new ArrayList<Map<String, String>>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,openid,nickname,sex,language,city,province,country,headimgurl,unionid,percent,remark,times,wxsid,parentopenid,salemoney,commission,isvip,vipid,qrcode,qrcodemediaid,qrcodemediaidtimes,username,areaproxymoney,areaproxyprovince,areaproxycity,areaproxydiscount,callbackmoney,isurlcode,isorder,isdownorder,isdownsubscriber,isyj from subscriber where wxsid=? and parentopenid=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, wxsid);
            ptst.setString(2, parentopenid);
            rs = ptst.executeQuery();
            while (rs.next()) {
                Map<String, String> subscriber = new HashMap<String, String>();
                subscriber.put("id", rs.getString("id"));
                subscriber.put("openid", rs.getString("openid"));
                String nickname = rs.getString("nickname");
                try {
                    nickname = 1 == rs.getInt("isurlcode") ? URLDecoder.decode(nickname, "utf-8") : nickname;
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                subscriber.put("nickname", nickname);
                subscriber.put("sex", rs.getString("sex"));
                subscriber.put("language", rs.getString("language"));
                subscriber.put("city", rs.getString("city"));
                subscriber.put("province", rs.getString("province"));
                subscriber.put("country", rs.getString("country"));
                subscriber.put("headimgurl", rs.getString("headimgurl"));
                subscriber.put("unionid", rs.getString("unionid"));
                subscriber.put("percent", rs.getString("percent"));
                subscriber.put("remark", rs.getString("remark"));
                subscriber.put("times", rs.getString("times"));
                subscriber.put("wxsid", rs.getString("wxsid"));
                subscriber.put("parentopenid", rs.getString("parentopenid"));
                subscriber.put("salemoney", rs.getString("salemoney"));
                subscriber.put("commission", rs.getString("commission"));
                subscriber.put("isvip", rs.getString("isvip"));
                subscriber.put("vipid", rs.getString("vipid"));
                subscriber.put("qrcode", rs.getString("qrcode"));
                subscriber.put("qrcodemediaid", rs.getString("qrcodemediaid"));
                subscriber.put("qrcodemediaidtimes", rs.getString("qrcodemediaidtimes"));
                subscriber.put("username", rs.getString("username"));
                subscriber.put("areaproxymoney", rs.getString("areaproxymoney"));
                subscriber.put("areaproxyprovince", rs.getString("areaproxyprovince"));
                subscriber.put("areaproxycity", rs.getString("areaproxycity"));
                subscriber.put("areaproxydiscount", rs.getString("areaproxydiscount"));
                subscriber.put("callbackmoney", rs.getString("callbackmoney"));
                subscriber.put("isorder", rs.getString("isorder"));
                subscriber.put("isdownorder", rs.getString("isdownorder"));
                subscriber.put("isdownsubscriber", rs.getString("isdownsubscriber"));
                subscriber.put("isyj", rs.getString("isyj"));
                subscriberList.add(subscriber);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return subscriberList;
    }

    public Map<String, String> getById(String id) {
        Map<String, String> subscriber = new HashMap<String, String>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,openid,nickname,sex,language,city,province,country,headimgurl,unionid,percent,remark,times,wxsid,parentopenid,salemoney,commission,isvip,vipid,qrcode,qrcodemediaid,qrcodemediaidtimes,username,areaproxymoney,areaproxyprovince,areaproxycity,areaproxydiscount,callbackmoney,isurlcode,isorder,isdownorder,isdownsubscriber,isyj from subscriber where id=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, id);
            rs = ptst.executeQuery();
            if (rs.next()) {
                subscriber.put("id", rs.getString("id"));
                subscriber.put("openid", rs.getString("openid"));
                String nickname = rs.getString("nickname");
                try {
                    nickname = 1 == rs.getInt("isurlcode") ? URLDecoder.decode(nickname, "utf-8") : nickname;
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                subscriber.put("nickname", nickname);
                subscriber.put("sex", rs.getString("sex"));
                subscriber.put("language", rs.getString("language"));
                subscriber.put("city", rs.getString("city"));
                subscriber.put("province", rs.getString("province"));
                subscriber.put("country", rs.getString("country"));
                subscriber.put("headimgurl", rs.getString("headimgurl"));
                subscriber.put("unionid", rs.getString("unionid"));
                subscriber.put("percent", rs.getString("percent"));
                subscriber.put("remark", rs.getString("remark"));
                subscriber.put("times", rs.getString("times"));
                subscriber.put("wxsid", rs.getString("wxsid"));
                subscriber.put("parentopenid", rs.getString("parentopenid"));
                subscriber.put("salemoney", rs.getString("salemoney"));
                subscriber.put("commission", rs.getString("commission"));
                subscriber.put("isvip", rs.getString("isvip"));
                subscriber.put("vipid", rs.getString("vipid"));
                subscriber.put("qrcode", rs.getString("qrcode"));
                subscriber.put("qrcodemediaid", rs.getString("qrcodemediaid"));
                subscriber.put("qrcodemediaidtimes", rs.getString("qrcodemediaidtimes"));
                subscriber.put("username", rs.getString("username"));
                subscriber.put("areaproxymoney", rs.getString("areaproxymoney"));
                subscriber.put("areaproxyprovince", rs.getString("areaproxyprovince"));
                subscriber.put("areaproxycity", rs.getString("areaproxycity"));
                subscriber.put("areaproxydiscount", rs.getString("areaproxydiscount"));
                subscriber.put("callbackmoney", rs.getString("callbackmoney"));
                subscriber.put("isorder", rs.getString("isorder"));
                subscriber.put("isdownorder", rs.getString("isdownorder"));
                subscriber.put("isdownsubscriber", rs.getString("isdownsubscriber"));
                subscriber.put("isyj", rs.getString("isyj"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return subscriber;
    }

    public Map<String, String> getById(String wxsid, String id) {
        Map<String, String> subscriber = new HashMap<String, String>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,openid,nickname,sex,language,city,province,country,headimgurl,unionid,percent,remark,times,wxsid,parentopenid,salemoney,commission,isvip,vipid,qrcode,qrcodemediaid,qrcodemediaidtimes,username,areaproxymoney,areaproxyprovince,areaproxycity,areaproxydiscount,callbackmoney,isurlcode,isorder,isdownorder,isdownsubscriber,isyj from subscriber where wxsid=? and id=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, wxsid);
            ptst.setString(2, id);
            rs = ptst.executeQuery();
            if (rs.next()) {
                subscriber.put("id", rs.getString("id"));
                subscriber.put("openid", rs.getString("openid"));
                String nickname = rs.getString("nickname");
                try {
                    nickname = 1 == rs.getInt("isurlcode") ? URLDecoder.decode(nickname, "utf-8") : nickname;
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                subscriber.put("nickname", nickname);
                subscriber.put("sex", rs.getString("sex"));
                subscriber.put("language", rs.getString("language"));
                subscriber.put("city", rs.getString("city"));
                subscriber.put("province", rs.getString("province"));
                subscriber.put("country", rs.getString("country"));
                subscriber.put("headimgurl", rs.getString("headimgurl"));
                subscriber.put("unionid", rs.getString("unionid"));
                subscriber.put("percent", rs.getString("percent"));
                subscriber.put("remark", rs.getString("remark"));
                subscriber.put("times", rs.getString("times"));
                subscriber.put("wxsid", rs.getString("wxsid"));
                subscriber.put("parentopenid", rs.getString("parentopenid"));
                subscriber.put("salemoney", rs.getString("salemoney"));
                subscriber.put("commission", rs.getString("commission"));
                subscriber.put("isvip", rs.getString("isvip"));
                subscriber.put("vipid", rs.getString("vipid"));
                subscriber.put("qrcode", rs.getString("qrcode"));
                subscriber.put("qrcodemediaid", rs.getString("qrcodemediaid"));
                subscriber.put("qrcodemediaidtimes", rs.getString("qrcodemediaidtimes"));
                subscriber.put("username", rs.getString("username"));
                subscriber.put("areaproxymoney", rs.getString("areaproxymoney"));
                subscriber.put("areaproxyprovince", rs.getString("areaproxyprovince"));
                subscriber.put("areaproxycity", rs.getString("areaproxycity"));
                subscriber.put("areaproxydiscount", rs.getString("areaproxydiscount"));
                subscriber.put("callbackmoney", rs.getString("callbackmoney"));
                subscriber.put("isorder", rs.getString("isorder"));
                subscriber.put("isdownorder", rs.getString("isdownorder"));
                subscriber.put("isdownsubscriber", rs.getString("isdownsubscriber"));
                subscriber.put("isyj", rs.getString("isyj"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return subscriber;
    }

    public Map<String, String> getByOpenid(String openid) {
        Map<String, String> subscriber = new HashMap<String, String>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,openid,nickname,sex,language,city,province,country,headimgurl,unionid,percent,remark,times,wxsid,parentopenid,salemoney,commission,isvip,vipid,qrcode,qrcodemediaid,qrcodemediaidtimes,username,areaproxymoney,areaproxyprovince,areaproxycity,areaproxydiscount,callbackmoney,isurlcode,isorder,isdownorder,isdownsubscriber,isyj from subscriber where openid=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, openid);
            rs = ptst.executeQuery();
            if (rs.next()) {
                subscriber.put("id", rs.getString("id"));
                subscriber.put("openid", rs.getString("openid"));
                String nickname = rs.getString("nickname");
                try {
                    nickname = 1 == rs.getInt("isurlcode") ? URLDecoder.decode(nickname, "utf-8") : nickname;
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                subscriber.put("nickname", nickname);
                subscriber.put("sex", rs.getString("sex"));
                subscriber.put("language", rs.getString("language"));
                subscriber.put("city", rs.getString("city"));
                subscriber.put("province", rs.getString("province"));
                subscriber.put("country", rs.getString("country"));
                subscriber.put("headimgurl", rs.getString("headimgurl"));
                subscriber.put("unionid", rs.getString("unionid"));
                subscriber.put("percent", rs.getString("percent"));
                subscriber.put("remark", rs.getString("remark"));
                subscriber.put("times", rs.getString("times"));
                subscriber.put("wxsid", rs.getString("wxsid"));
                subscriber.put("parentopenid", rs.getString("parentopenid"));
                subscriber.put("salemoney", rs.getString("salemoney"));
                subscriber.put("commission", rs.getString("commission"));
                subscriber.put("isvip", rs.getString("isvip"));
                subscriber.put("vipid", rs.getString("vipid"));
                subscriber.put("qrcode", rs.getString("qrcode"));
                subscriber.put("qrcodemediaid", rs.getString("qrcodemediaid"));
                subscriber.put("qrcodemediaidtimes", rs.getString("qrcodemediaidtimes"));
                subscriber.put("username", rs.getString("username"));
                subscriber.put("areaproxymoney", rs.getString("areaproxymoney"));
                subscriber.put("areaproxyprovince", rs.getString("areaproxyprovince"));
                subscriber.put("areaproxycity", rs.getString("areaproxycity"));
                subscriber.put("areaproxydiscount", rs.getString("areaproxydiscount"));
                subscriber.put("callbackmoney", rs.getString("callbackmoney"));
                subscriber.put("isorder", rs.getString("isorder"));
                subscriber.put("isdownorder", rs.getString("isdownorder"));
                subscriber.put("isdownsubscriber", rs.getString("isdownsubscriber"));
                subscriber.put("isyj", rs.getString("isyj"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return subscriber;
    }

    public Map<String, String> getByOpenid(String wxsid, String openid) {
        Map<String, String> subscriber = new HashMap<String, String>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,openid,nickname,sex,language,city,province,country,headimgurl,unionid,percent,remark,times,wxsid,parentopenid,salemoney,commission,isvip,vipid,qrcode,qrcodemediaid,qrcodemediaidtimes,username,areaproxymoney,areaproxyprovince,areaproxycity,areaproxydiscount,callbackmoney,isurlcode,isorder,isdownorder,isdownsubscriber,isyj from subscriber where wxsid=? and openid=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, wxsid);
            ptst.setString(2, openid);
            rs = ptst.executeQuery();
            if (rs.next()) {
                subscriber.put("id", rs.getString("id"));
                subscriber.put("openid", rs.getString("openid"));
                String nickname = rs.getString("nickname");
                try {
                    nickname = 1 == rs.getInt("isurlcode") ? URLDecoder.decode(nickname, "utf-8") : nickname;
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                subscriber.put("nickname", nickname);
                subscriber.put("sex", rs.getString("sex"));
                subscriber.put("language", rs.getString("language"));
                subscriber.put("city", rs.getString("city"));
                subscriber.put("province", rs.getString("province"));
                subscriber.put("country", rs.getString("country"));
                subscriber.put("headimgurl", rs.getString("headimgurl"));
                subscriber.put("unionid", rs.getString("unionid"));
                subscriber.put("percent", rs.getString("percent"));
                subscriber.put("remark", rs.getString("remark"));
                subscriber.put("times", rs.getString("times"));
                subscriber.put("wxsid", rs.getString("wxsid"));
                subscriber.put("parentopenid", rs.getString("parentopenid"));
                subscriber.put("salemoney", rs.getString("salemoney"));
                subscriber.put("commission", rs.getString("commission"));
                subscriber.put("isvip", rs.getString("isvip"));
                subscriber.put("vipid", rs.getString("vipid"));
                subscriber.put("qrcode", rs.getString("qrcode"));
                subscriber.put("qrcodemediaid", rs.getString("qrcodemediaid"));
                subscriber.put("qrcodemediaidtimes", rs.getString("qrcodemediaidtimes"));
                subscriber.put("username", rs.getString("username"));
                subscriber.put("areaproxymoney", rs.getString("areaproxymoney"));
                subscriber.put("areaproxyprovince", rs.getString("areaproxyprovince"));
                subscriber.put("areaproxycity", rs.getString("areaproxycity"));
                subscriber.put("areaproxydiscount", rs.getString("areaproxydiscount"));
                subscriber.put("callbackmoney", rs.getString("callbackmoney"));
                subscriber.put("isorder", rs.getString("isorder"));
                subscriber.put("isdownorder", rs.getString("isdownorder"));
                subscriber.put("isdownsubscriber", rs.getString("isdownsubscriber"));
                subscriber.put("isyj", rs.getString("isyj"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return subscriber;
    }

    public Map<String, String> getByVipid(String wxsid, String vipid) {
        Map<String, String> subscriber = new HashMap<String, String>();
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,openid,nickname,sex,language,city,province,country,headimgurl,unionid,percent,remark,times,wxsid,parentopenid,salemoney,commission,isvip,vipid,qrcode,qrcodemediaid,qrcodemediaidtimes,username,areaproxymoney,areaproxyprovince,areaproxycity,areaproxydiscount,callbackmoney,isurlcode,isorder,isdownorder,isdownsubscriber,isyj from subscriber where wxsid=? and vipid=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, wxsid);
            ptst.setString(2, vipid);
            rs = ptst.executeQuery();
            if (rs.next()) {
                subscriber.put("id", rs.getString("id"));
                subscriber.put("openid", rs.getString("openid"));
                String nickname = rs.getString("nickname");
                try {
                    nickname = 1 == rs.getInt("isurlcode") ? URLDecoder.decode(nickname, "utf-8") : nickname;
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                subscriber.put("nickname", nickname);
                subscriber.put("sex", rs.getString("sex"));
                subscriber.put("language", rs.getString("language"));
                subscriber.put("city", rs.getString("city"));
                subscriber.put("province", rs.getString("province"));
                subscriber.put("country", rs.getString("country"));
                subscriber.put("headimgurl", rs.getString("headimgurl"));
                subscriber.put("unionid", rs.getString("unionid"));
                subscriber.put("percent", rs.getString("percent"));
                subscriber.put("remark", rs.getString("remark"));
                subscriber.put("times", rs.getString("times"));
                subscriber.put("wxsid", rs.getString("wxsid"));
                subscriber.put("parentopenid", rs.getString("parentopenid"));
                subscriber.put("salemoney", rs.getString("salemoney"));
                subscriber.put("commission", rs.getString("commission"));
                subscriber.put("isvip", rs.getString("isvip"));
                subscriber.put("vipid", rs.getString("vipid"));
                subscriber.put("qrcode", rs.getString("qrcode"));
                subscriber.put("qrcodemediaid", rs.getString("qrcodemediaid"));
                subscriber.put("qrcodemediaidtimes", rs.getString("qrcodemediaidtimes"));
                subscriber.put("username", rs.getString("username"));
                subscriber.put("areaproxymoney", rs.getString("areaproxymoney"));
                subscriber.put("areaproxyprovince", rs.getString("areaproxyprovince"));
                subscriber.put("areaproxycity", rs.getString("areaproxycity"));
                subscriber.put("areaproxydiscount", rs.getString("areaproxydiscount"));
                subscriber.put("callbackmoney", rs.getString("callbackmoney"));
                subscriber.put("isorder", rs.getString("isorder"));
                subscriber.put("isdownorder", rs.getString("isdownorder"));
                subscriber.put("isdownsubscriber", rs.getString("isdownsubscriber"));
                subscriber.put("isyj", rs.getString("isyj"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return subscriber;
    }

    public int getCount(int wxsid) {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select count(id) count from subscriber where wxsid=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }

    public int getCount(int wxsid, String parentopenid) {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select count(id) count from subscriber where wxsid=? and parentopenid=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            ptst.setString(2, parentopenid);
            rs = ptst.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }

    public int getMaxid() {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select max(id) count from subscriber";
        try {
            ptst = conn.prepareStatement(sql);
            rs = ptst.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }

    public int getMaxVipid(int wxsid) {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select max(convert(vipid,signed)) count from subscriber where wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, wxsid);
            rs = ptst.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }

    public void delete(Map<String, String> subscriber, int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "delete from subscriber where id=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, subscriber.get("id"));
            ptst.setInt(2, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void delete(Map<String, String> subscriber, int wxsid, Connection conn) {
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "delete from subscriber where id=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, subscriber.get("id"));
            ptst.setInt(2, wxsid);
            ptst.executeUpdate();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteByOpenid(Map<String, String> subscriber, int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "delete from subscriber where openid=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, subscriber.get("openid"));
            ptst.setInt(2, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void add(Map<String, String> subscriber, int wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "insert into subscriber(openid,nickname,sex,language,city,province,country,headimgurl,unionid,percent,remark,times,wxsid,parentopenid,salemoney,commission,isvip,vipid,qrcode,qrcodemediaid,qrcodemediaidtimes,username,areaproxymoney,areaproxyprovince,areaproxycity,areaproxydiscount,callbackmoney,isurlcode,isorder,isdownorder,isdownsubscriber,isyj) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, subscriber.get("openid"));
            String nickname = subscriber.get("nickname");
            int isurlcode = 0;
            try {
                nickname = URLEncoder.encode(nickname, "utf-8");
                isurlcode = 1;
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            ptst.setString(2, nickname);
            ptst.setInt(3, Integer.parseInt(subscriber.get("sex")));
            ptst.setString(4, subscriber.get("language"));
            ptst.setString(5, subscriber.get("city"));
            ptst.setString(6, subscriber.get("province"));
            ptst.setString(7, subscriber.get("country"));
            ptst.setString(8, subscriber.get("headimgurl"));
            ptst.setString(9, subscriber.get("unionid"));
            ptst.setInt(10, Integer.parseInt(subscriber.get("percent")));
            ptst.setString(11, subscriber.get("remark"));
            ptst.setString(12, subscriber.get("times"));
            ptst.setInt(13, wxsid);
            ptst.setString(14, subscriber.get("parentopenid"));
            ptst.setBigDecimal(15, new BigDecimal(subscriber.get("salemoney")));
            ptst.setBigDecimal(16, new BigDecimal(subscriber.get("commission")));
            ptst.setInt(17, Integer.parseInt(subscriber.get("isvip")));
            ptst.setString(18, subscriber.get("vipid"));
            ptst.setString(19, subscriber.get("qrcode"));
            ptst.setString(20, subscriber.get("qrcodemediaid"));
            ptst.setString(21, subscriber.get("qrcodemediaidtimes"));
            ptst.setString(22, subscriber.get("username"));
            ptst.setString(23, subscriber.get("areaproxymoney"));
            ptst.setString(24, subscriber.get("areaproxyprovince"));
            ptst.setString(25, subscriber.get("areaproxycity"));
            ptst.setString(26, subscriber.get("areaproxydiscount"));
            ptst.setString(27, subscriber.get("callbackmoney"));
            ptst.setInt(28, isurlcode);
            ptst.setInt(29, Integer.parseInt(subscriber.get("isorder")));
            ptst.setInt(30, Integer.parseInt(subscriber.get("isdownorder")));
            ptst.setInt(31, Integer.parseInt(subscriber.get("isdownsubscriber")));
            ptst.setInt(32, Integer.parseInt(subscriber.get("isyj")));
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void updateInfo(String openid, int wxsid, Map<String, String> subscriber) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update subscriber set nickname=?,sex=?,language=?,city=?,province=?,country=?,headimgurl=?,unionid=?,isurlcode=? where openid=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            String nickname = subscriber.get("nickname");
            int isurlcode = 0;
            try {
                nickname = URLEncoder.encode(nickname, "utf-8");
                isurlcode = 1;
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            ptst.setString(1, nickname);
            ptst.setInt(2, Integer.parseInt(subscriber.get("sex")));
            ptst.setString(3, subscriber.get("language"));
            ptst.setString(4, subscriber.get("city"));
            ptst.setString(5, subscriber.get("province"));
            ptst.setString(6, subscriber.get("country"));
            ptst.setString(7, subscriber.get("headimgurl"));
            ptst.setString(8, subscriber.get("unionid"));
            ptst.setInt(9, isurlcode);
            ptst.setString(10, openid);
            ptst.setInt(11, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void updateInfo(String openid, int wxsid, String username, String headimgurl) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String temp = "";
        if (!"".equals(headimgurl)) {
            temp += "headimgurl='" + headimgurl + "'";
        }
        if (!"".equals(username)) {
            temp += ",username='" + username + "'";
        }
        String sql = "update subscriber set " + temp + " where openid=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, openid);
            ptst.setInt(2, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void updateareaproxy(String openid, int wxsid, Map<String, String> subscriber) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update subscriber set areaproxyprovince=?,areaproxycity=?,areaproxydiscount=? where openid=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, subscriber.get("areaproxyprovince"));
            ptst.setString(2, subscriber.get("areaproxycity"));
            ptst.setFloat(3, Float.parseFloat(subscriber.get("areaproxydiscount")));
            ptst.setString(4, openid);
            ptst.setInt(5, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void updateParentopenid(String openid, String wxsid, String parentopenid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update subscriber set parentopenid=? where openid=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, parentopenid);
            ptst.setString(2, openid);
            ptst.setString(3, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void updateVip(String openid, String wxsid, int isvip) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update subscriber set isvip=? where openid=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, isvip);
            ptst.setString(2, openid);
            ptst.setString(3, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void updateIsdownsubscriber(int id, String wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update subscriber set isdownsubscriber=1 where id=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, id);
            ptst.setString(2, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void updateIsorder(int id, String wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update subscriber set isorder=1 where id=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, id);
            ptst.setString(2, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void updateIsdownorder(int id, String wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update subscriber set isdownorder=1 where id=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, id);
            ptst.setString(2, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void updateIsyj(int id, String wxsid) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update subscriber set isyj=1 where id=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, id);
            ptst.setString(2, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    //qrcode,qrcodemediaid,qrcodemediaidtimes
    public void updateqrcode(String openid, String wxsid, String qrcode, String qrcodemediaid, String qrcodemediaidtimes) {
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update subscriber set qrcode=?,qrcodemediaid=?,qrcodemediaidtimes=? where openid=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, qrcode);
            ptst.setString(2, qrcodemediaid);
            ptst.setString(3, qrcodemediaidtimes);
            ptst.setString(4, openid);
            ptst.setString(5, wxsid);
            ptst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
    }

    public void deleteqrcode(String wxsid, String qrcode, String qrcodemediaid, Connection conn) {
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update subscriber set qrcode=?,qrcodemediaid=? where wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, qrcode);
            ptst.setString(2, qrcodemediaid);
            ptst.setString(3, wxsid);
            ptst.executeUpdate();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * parentopenid,salemoney,commission,isvip,vipid
     */
//    public void updateDistribution(String id, String wxsid,float nopaycommission) {
//        Connection conn = DbConn.getConn();
//        PreparedStatement ptst = null;
//        ResultSet rs = null;
//        String sql = "update subscriber set commission=commission+?,nopaycommission=nopaycommission+? where id=? and wxsid=?";
//        try {
//            ptst = conn.prepareStatement(sql);
//            ptst.setFloat(1, nopaycommission);
//            ptst.setFloat(2, nopaycommission);
//            ptst.setString(3, id);
//            ptst.setString(4, wxsid);
//            ptst.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            DbConn.getAllClose(conn, ptst, rs);
//        }
//    }
    public boolean updateTx(String openid, String wxsid, float commission) {
        boolean flag = false;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update subscriber set commission=commission-? where openid=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setFloat(1, commission);
            ptst.setString(2, openid);
            ptst.setString(3, wxsid);
            int i = ptst.executeUpdate();
            if (i > 0) {
                flag = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return flag;
    }

    public boolean updatePercent(String openid, String wxsid, float percent) {
        boolean flag = false;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update subscriber set percent=percent+? where openid=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setFloat(1, percent);
            ptst.setString(2, openid);
            ptst.setString(3, wxsid);
            int i = ptst.executeUpdate();
            if (i > 0) {
                flag = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return flag;
    }

    public boolean updateAreaproxymoney(String openid, String wxsid, float areaproxymoney) {
        boolean flag = false;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update subscriber set areaproxymoney=areaproxymoney+? where openid=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setFloat(1, areaproxymoney);
            ptst.setString(2, openid);
            ptst.setString(3, wxsid);
            int i = ptst.executeUpdate();
            if (i > 0) {
                flag = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return flag;
    }

    public boolean updateCallbackmoney(String openid, String wxsid, float callbackmoney) {
        boolean flag = false;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update subscriber set callbackmoney=callbackmoney+? where openid=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setFloat(1, callbackmoney);
            ptst.setString(2, openid);
            ptst.setString(3, wxsid);
            int i = ptst.executeUpdate();
            if (i > 0) {
                flag = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return flag;
    }

    //优化区
    //按id查询
    public Map<String, String> getDataById(String wxsid, String id, Connection conn) {
        Map<String, String> subscriber = new HashMap<String, String>();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,openid,parentopenid,areaproxymoney,callbackmoney from subscriber where wxsid=? and id=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, wxsid);
            ptst.setString(2, id);
            rs = ptst.executeQuery();
            if (rs.next()) {
                subscriber.put("id", rs.getString("id"));
                subscriber.put("openid", rs.getString("openid"));
                subscriber.put("parentopenid", rs.getString("parentopenid"));
                subscriber.put("areaproxymoney", rs.getString("areaproxymoney"));
                subscriber.put("callbackmoney", rs.getString("callbackmoney"));
            }
            rs.close();
            ptst.close();;
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subscriber;
    }

    //按parentopenid查询
    public List<Map<String, String>> getDataByParentopenidList(String wxsid, String parentopenid, String starttime, String endtime, Connection conn) {
//        if (null == starttime || "".equals(starttime) || "null".equals(starttime)) {
//            starttime = "2015-01-01 00:00:00";
//        }
//        if (null == endtime || "".equals(endtime) || "null".equals(endtime)) {
//            endtime = WxMenuUtils.format.format(new Date());
//        }
        StringBuilder conditionstr = new StringBuilder("");
        if (starttime != null && !starttime.equals("")) {
            conditionstr.append(" and to_days(times)>=to_days('").append(starttime).append("')");
        }
        if (endtime != null && !endtime.equals("")) {
            conditionstr.append(" and to_days(times)<=to_days('").append(endtime).append("')");
        }
        List<Map<String, String>> subscriberList = new ArrayList<Map<String, String>>();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,openid,parentopenid,areaproxymoney,callbackmoney from subscriber where 1=1 " + conditionstr.toString() + " and parentopenid=? and wxsid=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, parentopenid);
            ptst.setString(2, wxsid);
            rs = ptst.executeQuery();
            Map<String, String> subscriber = null;
            while (rs.next()) {
                subscriber = new HashMap<String, String>();
                subscriber.put("id", rs.getString("id"));
                subscriber.put("openid", rs.getString("openid"));
                subscriber.put("parentopenid", rs.getString("parentopenid"));
                subscriber.put("areaproxymoney", rs.getString("areaproxymoney"));
                subscriber.put("callbackmoney", rs.getString("callbackmoney"));
                subscriberList.add(subscriber);
            }
            rs.close();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subscriberList;
    }

    public List<Map<String, String>> getDataByParentopenidList(String wxsid, String parentopenid, String starttime, String endtime, Connection conn, int sign, int selectsign) {
//        if (null == starttime || "".equals(starttime) || "null".equals(starttime)) {
//            starttime = "2015-01-01 00:00:00";
//        }
//        if (null == endtime || "".equals(endtime) || "null".equals(endtime)) {
//            endtime = WxMenuUtils.format.format(new Date());
//        }
        StringBuilder conditionstr = new StringBuilder("");
        if (starttime != null && !starttime.equals("")) {
            conditionstr.append(" and to_days(times)>=to_days('").append(starttime).append("')");
        }
        if (endtime != null && !endtime.equals("")) {
            conditionstr.append(" and to_days(times)<=to_days('").append(endtime).append("')");
        }

        if (2 == sign) {
            if (1 == selectsign || 2 == selectsign || 3 == selectsign || 4 == selectsign) {
                conditionstr.append(" and isorder=1");
            } else if (5 == selectsign || 6 == selectsign || 7 == selectsign) {
                conditionstr.append(" and isdownsubscriber=1");
            } else if (8 == selectsign) {//只有有订单时，上级才能有佣金（宽泛）
                conditionstr.append(" and isorder=1");
            }
        }
        List<Map<String, String>> subscriberList = new ArrayList<Map<String, String>>();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,openid,parentopenid,areaproxymoney,callbackmoney from subscriber where 1=1 " + conditionstr.toString() + " and parentopenid=? and wxsid=? order by id desc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, parentopenid);
            ptst.setString(2, wxsid);
            rs = ptst.executeQuery();
            Map<String, String> subscriber = null;
            while (rs.next()) {
                subscriber = new HashMap<String, String>();
                subscriber.put("id", rs.getString("id"));
                subscriber.put("openid", rs.getString("openid"));
                subscriber.put("parentopenid", rs.getString("parentopenid"));
                subscriber.put("areaproxymoney", rs.getString("areaproxymoney"));
                subscriber.put("callbackmoney", rs.getString("callbackmoney"));
                subscriberList.add(subscriber);
            }
            rs.close();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subscriberList;
    }

    //粉丝数
    public int getfans(String wxsid, String parentopenid, String starttime, String endtime) {
//        if (null == starttime || "".equals(starttime) || "null".equals(starttime)) {
//            starttime = "2015-01-01 00:00:00";
//        }
//        if (null == endtime || "".equals(endtime) || "null".equals(endtime)) {
//            endtime = WxMenuUtils.format.format(new Date());
//        }
        StringBuilder conditionstr = new StringBuilder("");
        if (starttime != null && !starttime.equals("")) {
            conditionstr.append(" and to_days(times)>=to_days('").append(starttime).append("')");
        }
        if (endtime != null && !endtime.equals("")) {
            conditionstr.append(" and to_days(times)<=to_days('").append(endtime).append("')");
        }
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select count(id) count from subscriber where 1=1 " + conditionstr.toString() + " and parentopenid=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, parentopenid);
            ptst.setString(2, wxsid);
            rs = ptst.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }

    public List<Map<String, String>> getTotalDataByParentopenidList(String wxsid, String parentopenid, String starttime, String endtime, Connection conn) {
        //复合数据
        String downordercountStr = "select count(id) downordercount from t_order where SuserId='" + wxsid + "' and UserId=sub.openid";
        String SaleMoneyStr = "select sum(F_Price) salemoney from t_order where 1=1 and Sts!=7 and Sts!=0 and to_days(F_Date)>=to_days('" + starttime + "') and to_days(F_Date)<=to_days('" + endtime + "')  and SuserId='" + wxsid + "' and UserId=sub.openid";
        String SaleMoney2Str = "select sum(F_Price) salemoney from t_order where 1=1 and Sts!=7 and to_days(F_Date)>=to_days('" + starttime + "') and to_days(F_Date)<=to_days('" + endtime + "') and SuserId='" + wxsid + "' and UserId=sub.openid";
        String FirstYJStr = "select sum(FirstYJ) FirstYJ from t_order where to_days(F_Date)>=to_days('" + starttime + "') and to_days(F_Date)<=to_days('" + endtime + "') and Sts=5 and SuserId='" + wxsid + "' and UserId=sub.openid";
        String SecondYJStr = "select sum(SecondYJ) SecondYJ from t_order where to_days(F_Date)>=to_days('" + starttime + "') and to_days(F_Date)<=to_days('" + endtime + "') and Sts=5 and SuserId='" + wxsid + "' and UserId=sub.openid";
        String ThirdYJStr = "select sum(ThirdYJ) ThirdYJ from t_order where to_days(F_Date)>=to_days('" + starttime + "') and to_days(F_Date)<=to_days('" + endtime + "') and Sts=5 and SuserId='" + wxsid + "' and UserId=sub.openid";
        List<Map<String, String>> subscriberList = new ArrayList<Map<String, String>>();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select id,openid,parentopenid,areaproxymoney,callbackmoney,(" + downordercountStr + ") as downordercount,(" + SaleMoneyStr + ") as salemoney1,(" + SaleMoney2Str + ") as salemoney2,(" + FirstYJStr + ") as FirstYJ,(" + SecondYJStr + ") as SecondYJ,(" + ThirdYJStr + ") as ThirdYJ from subscriber sub where parentopenid=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, parentopenid);
            ptst.setString(2, wxsid);
            rs = ptst.executeQuery();
            Map<String, String> subscriber = null;
            while (rs.next()) {
                subscriber = new HashMap<String, String>();
                subscriber.put("id", rs.getString("id"));
                subscriber.put("openid", rs.getString("openid"));
                subscriber.put("parentopenid", rs.getString("parentopenid"));
                subscriber.put("areaproxymoney", rs.getString("areaproxymoney"));
                subscriber.put("callbackmoney", rs.getString("callbackmoney"));
                subscriber.put("downordercount", rs.getString("downordercount"));
                subscriber.put("salemoney1", rs.getString("salemoney1"));
                subscriber.put("salemoney2", rs.getString("salemoney2"));
                subscriber.put("FirstYJ", rs.getString("FirstYJ"));
                subscriber.put("SecondYJ", rs.getString("SecondYJ"));
                subscriber.put("ThirdYJ", rs.getString("ThirdYJ"));
                subscriberList.add(subscriber);
            }
            rs.close();
            ptst.close();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subscriberList;
    }
    //优化区结束

    //补充
    public int getPaiMing(String wxsid, int id) {
        int count = 0;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "select count(*) count from subscriber where id<? and wxsid=? order by id asc";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setInt(1, id);
            ptst.setString(2, wxsid);
            rs = ptst.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return count;
    }

    public boolean updateVipid(String openid, String wxsid, String vipid) {
        boolean flag = false;
        Connection conn = DbConn.getConn();
        PreparedStatement ptst = null;
        ResultSet rs = null;
        String sql = "update subscriber set vipid=? where openid=? and wxsid=?";
        try {
            ptst = conn.prepareStatement(sql);
            ptst.setString(1, vipid);
            ptst.setString(2, openid);
            ptst.setString(3, wxsid);
            int i = ptst.executeUpdate();
            if (i > 0) {
                flag = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbConn.getAllClose(conn, ptst, rs);
        }
        return flag;
    }
}
