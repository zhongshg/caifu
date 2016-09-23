<%-- 
    Document   : index
    Created on : 2014-7-7, 9:30:55
    Author     : Administrator
--%>

<%@page import="job.tot.util.CodeUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"
	errorPage="error.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="job.tot.global.Sysconfig"%>
<%@ page import="job.tot.db.DBUtils"%>
<%@ page import="job.tot.util.RequestUtil"%>
<%@ page import="wap.wx.util.Forward"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="job.tot.dao.DaoFactory"%>
<%@ page import="job.tot.bean.DataField"%>

<%
    // 返回json数据根据code判断是否成功
    String uname = RequestUtil.getString(request, "uname");
    String password = RequestUtil.getString(request, "password");
    String parentid = RequestUtil.getString(request, "parentid");
    String cardid = RequestUtil.getString(request, "cardid");
    String bankcard = RequestUtil.getString(request, "bankcard");
    String tel = RequestUtil.getString(request, "tel");

    int code = -1;
    if (uname == null || password == null || parentid == null || tel == null) {
		code = 2;
    }
    try {
		DataField count = DaoFactory.getUserDao().getByCol("phone", tel, "id");
		if (count != null && count.getInt("id") > 0) {
		    code = 4;
		} else {
		    DataField bcCount = DaoFactory.getUserDao().getByCol("bankcard", tel, "id");
		    if (bcCount != null && bcCount.getInt("id") > 0) {
			code = 3;
		    } else {
			DataField identityCount = DaoFactory.getUserDao().getByCol("bankcard", tel, "id");
			if (identityCount != null && identityCount.getInt("id") > 0) {
			    code = 5;
			} else {
			    String ucode = DaoFactory.getuCodeDao().getNewCode();
			    if (ucode == null) {
					DaoFactory.getuCodeDao().createCode();
					ucode = DaoFactory.getuCodeDao().getNewCode();
			    }
			    boolean flag = DaoFactory.getUserDao().add(uname, password, parentid, cardid, bankcard, tel, ucode);
			    if (flag) {
				DaoFactory.getuCodeDao().del(ucode);
				code = 0;
			    } else {
				code = 6;
			    }
			}
		    }
		}

		// 返回json数据根据code判断是否成功
		String jsons = "{\"code\":" + code + "}";
		PrintWriter out1 = response.getWriter();
		out1.print(jsons);
		out1.flush();
    } catch (Exception e) {
		e.printStackTrace();
		String jsons = "{\"code\":11}";
		PrintWriter out1 = response.getWriter();
		out1.print(jsons);
		out1.flush();
    }
%>
