<%@page import="sun.security.pkcs11.Secmod.DbMode"%>
<%@page import="job.tot.util.DbConn"%>
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
<%@page import="job.tot.util.DateUtil"%>
<%@ page import="java.sql.Connection"%>
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
    String allid = RequestUtil.getString(request, "allid");
    String allVAL = RequestUtil.getString(request, "allVAL");
    String allPrice = RequestUtil.getString(request, "allPrice");
    String allName = RequestUtil.getString(request, "allName");
    String sum = RequestUtil.getString(request, "sum");
    int code = -1;
    boolean flag = false;
    try {
		String parentid = String.valueOf(session.getAttribute("admin_id"));
		String parengname = String.valueOf(session.getAttribute("admin_name"));
		if (uname == null || password == null || uid == null || bankcard == null || cardid == null) {
		    code = 2;
		} else {
		    password = new MD5().getMD5of32(password);
		    //拼接用户信息
		    Map<String, String> new_users = new HashMap<String, String>();
		    new_users.put("uname", uname);
		    new_users.put("password", password);
		    new_users.put("parentid", "0");
		    new_users.put("cardid", cardid);
		    new_users.put("bankcard", bankcard);
		    new_users.put("tel", tel);
		    new_users.put("uid", uid);
		    new_users.put("nick", nick);
		    new_users.put("store", store);
		    new_users.put("proleid", "0");//上级会员为普通用户

		    //填充额外信息
		    new_users.put("allid", allid);
		    new_users.put("allVAL", allVAL);
		    new_users.put("allPrice", allPrice);
		    new_users.put("allName", allName);
		    new_users.put("sum", sum);
		    new_users.put("user_viplvl", String.valueOf(session.getAttribute("admin_viplvl")));
		    Connection conn = DBUtils.getConnection();
		    try {
				code = DaoFactory.getUserDao().validate(conn, new_users);
		    } catch (Exception e) {
				e.printStackTrace();
				code = -1;
		    } finally {
			DBUtils.closeConnection(conn);
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
