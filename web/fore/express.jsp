<%@page import="job.tot.util.CodeUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"
	errorPage="error.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="job.tot.global.Sysconfig"%>
<%@ page import="job.tot.db.DBUtils"%>
<%@ page import="job.tot.util.RequestUtil"%>
<%@ page import="job.tot.util.Forward"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="job.tot.dao.DaoFactory"%>
<%@ page import="job.tot.bean.DataField"%>
<%@ include file="common.jsp"%>
<%
	String act = RequestUtil.getString(request, "act");
	String oid = RequestUtil.getString(request, "oid");
	if (act != null && oid != null) {
	    DataField order = DaoFactory.getOrdersDao().getByCol("oid=" + oid);
		if (act.equals("ok")) {//修改订单
			Map<String, String> orders = new HashMap<String, String>();
			orders.put("osenddt", DateUtil.getStringDate());//发货时间
			orders.put("ostatus", "2");//发货
			orders.put("olastupdatedt", DateUtil.getStringDate());//修改最后一次的状态
			boolean flag = DaoFactory.getOrdersDao().update(oid, orders);
			if(flag){
				response.sendRedirect("manageExpresses.jsp?msg=suce");
			}else{
			    response.sendRedirect("manageExpresses.jsp?msg=error");
			}
		} else if (act.equals("cancel")) {//取消订单
		    Map<String, String> orders = new HashMap<String, String>();
			orders.put("ostatus", "1");//订单取消
			orders.put("olastupdatedt", DateUtil.getStringDate());//修改最后一次的状态
			boolean flag = DaoFactory.getOrdersDao().update(oid, orders);
			if(flag){
				response.sendRedirect("manageExpresses.jsp?msg=sucd");
			}else{
			    response.sendRedirect("manageExpresses.jsp?msg=error");
			}
		}
	}
%>
