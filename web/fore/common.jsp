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
<%@ page import="job.tot.global.GlobalEnum" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page pageEncoding="UTF-8" language="java"
	errorPage="error.jsp"%>
<%
    request.setCharacterEncoding("utf-8");
    response.setContentType("text/html; charset=UTF-8");
    if (!IpFilter.filter(request.getRemoteAddr())) {
		response.sendRedirect("404.jsp");
    }
    String user_id = (String) session.getAttribute("user_id");
    int int_user_id=0;
    if(user_id!=null){
		int_user_id=Integer.parseInt(user_id);
    }
    String user_name = (String)session.getAttribute("user_name");
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