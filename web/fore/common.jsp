<%@ page import="job.tot.exception.*"%>
<%@ page import="job.tot.util.*"%>
<%@ page import="job.tot.bean.*"%>
<%@ page import="job.tot.dao.DaoFactory"%>
<%@ page import="job.tot.filter.IpFilter"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.net.*"%>
<%@ page import="job.tot.global.Sysconfig"%>
<%@ page import="job.tot.util.Forward" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page pageEncoding="UTF-8" language="java"
	errorPage="error.jsp"%>
<%
    request.setCharacterEncoding("utf-8");
    response.setContentType("text/html; charset=UTF-8");
    if (!IpFilter.filter(request.getRemoteAddr())) {
		response.sendRedirect("404.jsp");
    }
    String user_id = (String) session.getAttribute("user_id");
    if (user_id == null) {
		session = request.getSession(false);
		if (session == null) {
		    response.sendRedirect("../login.jsp");
		    return;
		}
		session.removeAttribute("user_id");
		response.sendRedirect("../login.jsp");
    }
%>