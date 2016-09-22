<%-- 
    Document   : main
    Created on : 2014-7-7, 11:52:14
    Author     : Administrator
--%>

<%@page import="job.tot.dao.DaoFactory"%>
<%@page import="java.util.Map"%>
<%@page import="job.tot.bean.DataField"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title></title>
    </head>
    <%
        Map<String, String> wx = (Map<String, String>) request.getAttribute("wx");
        //取出库存不足产品 10
        List<DataField> productList = (List<DataField>) DaoFactory.getProductDAO().getIsRemList(wx.get("id"));
        System.out.println("productList " + productList.size());
    %>
    <body>
    </body>
</html>