<%-- 
    Document   : area_do
    Created on : 2015-7-15, 11:26:22
    Author     : Administrator
--%>

<%@page import="java.util.Iterator"%>
<%@page import="job.tot.dao.DaoFactory"%>
<%@page import="java.util.List"%>
<%@page import="job.tot.bean.DataField"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int pid = Integer.parseInt(request.getParameter("pid"));
    response.setContentType("text/xml;charset=utf-8");

    List<DataField> citylist = (List<DataField>) DaoFactory.getAreaDaoImplJDBC().getList(pid);
    Iterator<DataField> cityit = citylist.iterator();
    StringBuilder sub = new StringBuilder();
    sub.append("<result>");
    sub.append("<city>");
    sub.append("<id>0</id>");
    sub.append("<title>" + (String.valueOf(pid).indexOf("0000") != -1 ? "城市" : "区域") + "</title>");
    sub.append("</city>");
    while (cityit.hasNext()) {
        DataField city = cityit.next();
        sub.append("<city>");
        sub.append("<id>" + city.getFieldValue("id") + "</id>");
        sub.append("<title>" + city.getFieldValue("title") + "</title>");
        sub.append("</city>");
    }
    sub.append("</result>");
    out.println(sub.toString());
%>
