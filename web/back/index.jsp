<%-- 
    Document   : index
    Created on : 2014-7-7, 9:30:55
    Author     : Administrator
--%>

<%@page import="org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID"%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"
	errorPage="error.jsp"%>
<%@ include file="../inc/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>财富客户管理系统后台</title>
<jsp:include page="../public/css.jsp"></jsp:include>
</head>
<!--  scrolling="NO"  -->
<%
    DataField df = (DataField) request.getSession().getAttribute("user");
    if (df == null) {
		session = request.getSession(false);//防止创建Session  
        if(session == null){  
            response.sendRedirect("../login.jsp");  
            return;  
        }  
        session.removeAttribute("user");  
        response.sendRedirect("../login.jsp");
    }
    String mainJsp = RequestUtil.getString(request, "act");
%>
<frameset rows="59,*" cols="*" frameborder="no" border="0"
	framespacing="0">
	<frame src="top.jsp" name="topFrame" noresize="noresize" id="topFrame"
		title="topFrame" />
	<frameset cols="213,*" frameborder="no" border="0" framespacing="0">
		<frame src="left.jsp" name="leftFrame" noresize="noresize"
			id="leftFrame" title="leftFrame" />
		<frame src="<%=mainJsp==null?"main.jsp":mainJsp %>" name="mainFrame" id="mainFrame"
			title="mainFrame" />
	</frameset>
</frameset>
<noframes>
	<body>
	</body>
</noframes>
</html>
