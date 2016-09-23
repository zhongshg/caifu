<%@ page contentType="text/html;charset=GBK" %>
<%@ page session="false" isErrorPage="true"%>
<%@ page import="java.io.PrintWriter" %> 
<%@ page import="org.apache.commons.logging.Log" %>
<%@ page import="org.apache.commons.logging.LogFactory" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>Error</title>
</head>

<body>
<h1>error</h1>
<%=exception%>
<%
log.fatal("error", exception);
System.out.println(exception);
%>
</body>
</html>
<%!
    private static Log log = LogFactory.getLog("error.jsp");
%>
