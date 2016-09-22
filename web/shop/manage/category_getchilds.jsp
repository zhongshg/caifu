<?xml version="1.0" encoding ="UTF-8"?>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<%
    response.setContentType("text/xml");
//---------------------------------------------------------
//if(!DaoFactory.getAdminDAO().ifHasPrivilege(haspriv,"p01"))
//throw new NoPrivilegeException("you don't have privilege");
//---------------------------------------------------------
    int classid = RequestUtil.getInt(request, "classid");
    ArrayList categoryList = (ArrayList) DaoFactory.getCategoryDAO().getCategoryByParentId(classid, -1, wx.get("id"));
    out.println("<root>");
    for (Iterator iter = categoryList.iterator(); iter.hasNext();) {
        DataField df = (DataField) iter.next();
        String id = df.getFieldValue("id");
        out.println("<category>");
        out.println("	<id>" + id + "</id>");
        out.println("	<title>" + df.getFieldValue("Title") + "</title>");
        out.println("</category>");
    }
    out.println("</root>");
%>