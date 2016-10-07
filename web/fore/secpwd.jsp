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
<%@page pageEncoding="UTF-8" language="java" %>
<%
    Object user_id = session.getAttribute("user_id");
	Object admin_id = session.getAttribute("admin_id");
    if (user_id != null) {
		response.sendRedirect("frames.jsp?msg=nop");
    }else if(admin_id!=null){
		response.sendRedirect("frames.jsp?msg=nop");
    }
%>