<%@page import="wap.wx.dao.WxsDAO"%>
<%@ page import="job.tot.exception.*"%>
<%@ page import="job.tot.util.*" %>
<%@ page import="job.tot.bean.*" %>
<%@ page import="job.tot.dao.DaoFactory" %>
<%@ page import="job.tot.filter.IpFilter" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.net.*"%>
<%@ page import="job.tot.global.Sysconfig" %>
<%
    request.setCharacterEncoding("utf-8");
    response.setContentType("text/html; charset=UTF-8");
    if (!IpFilter.filter(request.getRemoteAddr())) {
        response.sendRedirect("404.jsp");
    }
    String wxsid = (String) session.getAttribute("wxsid");
    String openid = (String) session.getAttribute("openid");
%>