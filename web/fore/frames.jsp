<%-- 
    Document   : iframes
    Created on : 2014-7-7, 11:32:13
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="common.jsp"%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", -10);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.servletContext.contextPath}/img/logourl.png"
	rel="Shortcut Icon">
<title>后台管理系统</title>
</head>
<frameset rows="10%,*" border="1" framespacing="0" id="mainset"
	name="mainset">
	<frame src="top.jsp" id="topFrame" name="topFrame">
	<frameset cols="230,*">
		<frame src="left.jsp?act=login" name="leftFrame" id="leftFrame"
			crolling="no" noresize="noresize" title="leftFrame"">
		<frame src="manageUsers.jsp" name="mainFrame" id="mainFrame"
			scrolling="yes" title="mainFrame">
	</frameset>
</html>
