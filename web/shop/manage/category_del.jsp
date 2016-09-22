<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<%
int id=RequestUtil.getInt(request,"id");
boolean bl=DaoFactory.getCategoryDAO().delCategory(id);
if(bl) response.sendRedirect("category_manage.jsp");
else out.print("error on delete category");
%> 