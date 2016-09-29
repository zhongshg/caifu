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
<%@ page import="job.tot.util.Forward"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="job.tot.dao.DaoFactory"%>
<%@ page import="job.tot.bean.DataField"%>
<%@ page import="job.tot.util.MD5"%>

<%
    // 返回json数据根据code判断是否成功
    String uname = RequestUtil.getString(request, "uname");
    String password = RequestUtil.getString(request, "password");
    String uid = RequestUtil.getString(request, "uid");
    String cardid = RequestUtil.getString(request, "cardid");
    String bankcard = RequestUtil.getString(request, "bankcard");
    String tel = RequestUtil.getString(request, "tel");
    String nick = RequestUtil.getString(request, "nick");
    String store = RequestUtil.getString(request, "store");
    int code = -1;

    try {
		if (uname == null || password == null || uid == null || bankcard == null || cardid == null) {
		    code = 2;
		} else {
		    boolean flag = DaoFactory.getUserDao().validate(uid);
		    if(flag){
			    DataField count = DaoFactory.getUserDao().getByCol("phone=" + tel, "id");
			    if (count != null && count.getInt("id") > 0) {
				code = 4;
			    } else {
				DataField bcCount = DaoFactory.getUserDao().getByCol("cardid=" + cardid, "id");
				if (bcCount != null && bcCount.getInt("id") > 0) {
				    code = 3;
				} else {
				    DataField identityCount = DaoFactory.getUserDao().getByCol("bankcard=" + bankcard, "id");
				    if (identityCount != null && identityCount.getInt("id") > 0) {
					code = 5;
				    } else {
					/* String id = DaoFactory.getuCodeDao().getNewCode();
					if (id == null) {
					    DaoFactory.getuCodeDao().createCode();
					    id = DaoFactory.getuCodeDao().getNewCode();
					} */
					password = new MD5().getMD5of32(password);
					//管理员账户添加的会员上级都为88888
					flag = DaoFactory.getUserDao().add(uname, password, "88888", cardid, bankcard, tel, uid, nick, store);
					if (flag) {
					    DaoFactory.getuCodeDao().del(uid);
					    code = 0;
					} else {
					    code = 6;
					}
				    }
				}
			    }
		    }else{
				code = 7;
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
