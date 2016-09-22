<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
session.removeAttribute("tot_task_admin");
session.removeAttribute("role");
session.invalidate();
response.sendRedirect("login.jsp");
%>