<%-- 
    Document   : public_do
    Created on : 2015-9-13, 16:33:36
    Author     : ASUS
--%>

<%@page import="wap.wx.service.SubscriberService"%>
<%@page import="wap.wx.util.DbConn"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.HashMap"%>
<%@page import="wap.wx.dao.SubscriberDAO"%>
<%@page import="java.util.Map"%>
<%@page import="job.tot.util.RequestUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String wxsid = RequestUtil.getString(request, "wxsid");
    String UserId = RequestUtil.getString(request, "UserId");
    SubscriberDAO subscriberDAO = new SubscriberDAO();
    Map<String, String> subscriber = subscriberDAO.getByOpenid(wxsid, UserId);
    if (null != subscriber.get("id")) {
        Map<String, String> wx = new HashMap<String, String>();
        wx.put("id", wxsid);
        Connection conn = DbConn.getConn();
        float totalcanmoney = new SubscriberService().totalcanmoney(conn, subscriberDAO, subscriber, wx);
        conn.close();
        System.out.println("totalcanmoney " + totalcanmoney);
        out.print(subscriber.get("nickname") + "," + totalcanmoney);
    } else {
        out.print("");
    }
%>