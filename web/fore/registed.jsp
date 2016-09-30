<%-- 
    Document   : index
    Created on : 2014-7-7, 9:30:55
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"
	errorPage="error.jsp"%>
<%@ include file="common.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="job.tot.global.Sysconfig"%>
<%@ page import="job.tot.db.DBUtils"%>
<%@ page import="job.tot.util.RequestUtil"%>
<%@ page import="job.tot.util.Forward"%>
<%
	String newCode = DaoFactory.getuCodeDao().getNewCode();
%>
<!DOCTYPE html>
<html>
<head>
<title>财富客户管理系统注册</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<link href="../css/styleRe.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery.validate.js"></script>
<script type="text/javascript" src="js/page_regist.js"></script>

</head>
<body class="loginbody">
<div class="dataEye">
	<div class="loginbox registbox">
		<div class="login-content reg-content">
			<div class="loginbox-title">
				<h3 align="center">注册新用户</h3>
			</div>
			<form id="signupForm">
			<div class="login-error"></div>
			<div class="row">
				<label class="field" for="uid">会员号</label>
				<input type="text" value="<%=newCode %>" class="input-text-user noPic input-click" name="uid" id="uid">
			</div>
			<div class="row">
				<label class="field" for="nick">昵称</label>
				<input type="text" value="" class="input-text-user noPic input-click" name="nick" id="nick">
			</div>
			<div class="row">
				<label class="field" for="uname">姓名</label>
				<input type="text" value="" class="input-text-user noPic input-click" name="uname" id="uname">
			</div>
			<div class="row">
				<label class="field" for="password">密码</label>
				<input type="password" value="" class="input-text-password noPic input-click" name="password" id="password">
			</div>
			<div class="row">
				<label class="field" for="passwordAgain">确认密码</label>
				<input type="password" value="" class="input-text-password noPic input-click" name="passwordAgain" id="passwordAgain">
			</div>
			<div class="row">
				<label class="field" for="storecode">专卖店号</label>
				<input type="text" value="" class="input-text-user noPic input-click" name="storecode" id="storecode">
			</div>
			<div class="row">
				<label class="field" for="cardid">身份证号</label>
				<input type="text" value="" class="input-text-user noPic input-click" name="cardid" id="cardid">
			</div>
			<div class="row">
				<label class="field" for="bankcard">银行卡号</label>
				<input type="text" value="" class="input-text-user noPic input-click" name="bankcard" id="bankcard">
			</div>
			<div class="row">
				<label class="field" for="tel">手机号</label>
				<input type="text" value="" class="input-text-user noPic input-click" name="tel" id="tel">
			</div>
			
			<div class="row btnArea">
				<a class="login-btn" id="submit">注册</a>
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

