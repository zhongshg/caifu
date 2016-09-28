<%@page import="org.apache.commons.collections.functors.ForClosure"%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"
	errorPage="error.jsp"%>
<%@ include file="../inc/common.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="job.tot.global.Sysconfig"%>
<%@ page import="job.tot.db.DBUtils"%>
<%@ page import="job.tot.util.RequestUtil"%>
<%@ page import="job.tot.util.Forward"%>
<%
   // String oldpwd = RequestUtil.getString(request, "oldpwd");oldpwd != null && oldpwd != ""&&
    String newpwd1 = RequestUtil.getString(request, "newpwd1");
    String newpwd2 = RequestUtil.getString(request, "newpwd2");
    Map<String, String> users = new HashMap<String, String>();
    if ( newpwd1 != null && newpwd1 != "" && newpwd2 != null && newpwd2 != "") {
		if (!newpwd1.equals(newpwd2)) {
		    out.print("<script>alert(\"新密码两次输入不一致\");window.location.href=\"editpwd.jsp\";</script>");
		    return;
		}
	/* 	if (oldpwd.equals(newpwd1)) {
		    out.print("<script>alert(\"新旧密码一致,请修改新密码\");window.location.href=\"editpwd.jsp\";</script>");
		    return;
		} */
		newpwd1 = new MD5().getMD5of32(newpwd1);
		users.put("pwd", newpwd1);
		boolean flag = false;
		try {
		    flag = DaoFactory.getUserDao().update(admin_id, users);
		} catch (Exception e) {
		    e.printStackTrace();
		    out.println("修改密码失败" + e.getMessage());
		}
		if (flag) {
		    session.removeAttribute("admin_id");
		    out.println("<html>");
		    out.println("<script>");
		    out.println("$('.loading').hide();");
		    out.println("window.open('../login.jsp','_top')");
		    out.println("</script>");
		    out.println("</html>");
		} else {
		    out.println("$('.loading').hide();");
		    out.print("<script>alert(\"修改密码失败，请稍后重试！\");</script>");
		}
    }
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>重置密码</title>
<link href="../css/styleRe.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="js/page_editpwd.js"></script>
</head>
<body class="loginbody">
	<div class="dataEye">
		<div class="loginbox registbox">
			<div class="login-content reg-content">
				<div class="loginbox-title">
					<h3 align="center">重置密码</h3>
				</div>
				<form id="editForm" action="" method="get">
					<div class="login-error"></div>
					<!-- <div class="row">
						<label class="field" for="oldpwd">旧密码</label> <input
							type="password" value=""
							class="input-text-password noPic input-click" id="oldpwd"
							name="oldpwd">
					</div> -->
					<div class="row">
						<label class="field" for="newpwd1">新密码</label> <input
							type="password" value=""
							class="input-text-password noPic input-click" id="newpwd1"
							name="newpwd1">
					</div>
					<div class="row">
						<label class="field" for="newpwd2">确认新密码</label> <input
							type="text" value="" class="input-text-user noPic input-click"
							id="newpwd2" name="newpwd2">
					</div>
					<div class="row btnArea">
						<input class="login-btn" type="submit" value="保存" />
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="loading">
		<div class="mask">
			<div class="loading-img">
				<img src="../images/loading.gif" width="31" height="31">
			</div>
		</div>
	</div>

</body>
</html>
