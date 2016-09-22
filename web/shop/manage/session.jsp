<%@page import="java.util.Map"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%//String tot_texpert_admin=null;
//
//if(session.getAttribute("tot_texpert_admin")!=null){
//	tot_texpert_admin=(String)session.getAttribute("tot_texpert_admin");
//}
//
//if(tot_texpert_admin==null || tot_texpert_admin.equals("")){
//     out.print("<script>parent.location.href='login.jsp';</script>");
//   return;
////	response.sendRedirect("login.jsp");
//}
    Map<String, String> wx = (Map<String, String>) session.getAttribute("wx");
%>