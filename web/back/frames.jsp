<%-- 
    Document   : iframes
    Created on : 2014-7-7, 11:32:13
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../inc/common.jsp"%>
<!DOCTYPE html>
<%
    DataField df = (DataField) request.getSession().getAttribute("user");
    String act = RequestUtil.getString(request, "act");
    if (df == null || (act != null && act.equals("exit"))) {
		session = request.getSession(false);//防止创建Session  
		if (session == null) {
		    response.sendRedirect("../login.jsp");
		    return;
		}
		session.removeAttribute("user");
		response.sendRedirect("../login.jsp");
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.servletContext.contextPath}/img/logourl.png"
	rel="Shortcut Icon">
<title>后台管理系统</title>
</head>
<frameset rows="10%,*" border="1" framespacing="1" >
   <frame src="top.jsp" id="topFrame" name="topFrame">
   <frameset cols="230,*">
    <frame src="left.jsp?act=login" name="leftFrame" id="leftFrame" 
    crolling="no" noresize="noresize" title="leftFrame" ">
    <frame src="main.jsp" name="mainFrame" id="mainFrame" scrolling="yes" title="mainFrame">
   </frameset>
</html>
