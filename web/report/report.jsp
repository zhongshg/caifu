<%-- 
    Document   : iframes
    Created on : 2014-7-7, 11:32:13
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../inc/common.jsp"%>
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


<frameset cols="70%,30%" border="1" framespacing="0" id="mainset"
	name="mainset">
	<frame src="mainReport.jsp" id="topFrame" name="topFrame">
	<frameset rows="50%,*">
		<frame src="weather.jsp" name="leftFrame" id="leftFrame" crolling="no"
			noresize="noresize" title="leftFrame"">
		<frame src="logininfo.jsp" name="mainFrame" id="mainFrame"
			scrolling="no" title="mainFrame">
	</frameset>
</frameset>
</html>
