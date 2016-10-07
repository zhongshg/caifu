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
	DataField assetsDF = DaoFactory.getAssetsDao().getByCol("id="+user_id);
	Float balance = assetsDF.getFloat("balance");
	String newCode = "";
	List<Map<String,String>> products = null;
	if(balance >= 3000){
		newCode = DaoFactory.getuCodeDao().getNewCode();
		products = DaoFactory.getProductDao().searchBywhere(null, null);
	}else{
	    response.sendRedirect("manageUsers.jsp?msg=noMny");
	}
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
				<input type="text" value="<%=newCode %>" class="input-text-user noPic input-click" name="uid" id="uid" disabled="disabled">
			</div>
			<div class="row">
				<label class="field" for="nick">昵称</label>
				<input type="text" value="" class="input-text-user noPic input-click" name="nick" id="nick">
			</div>
			<div class="row">
				<label class="field" for="uname" maxlength="20">姓名</label>
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
				<input type="text" value="" class="input-text-user noPic input-click" name="storecode" id="storecode" maxlength="6">
			</div>
			<div class="row">
				<label class="field" for="cardid">身份证号</label>
				<input type="text" value="" class="input-text-user noPic input-click" name="cardid" id="cardid" maxlength="18">
			</div>
			<div class="row">
				<label class="field" for="bankcard">银行卡号</label>
				<input type="text" value="" class="input-text-user noPic input-click" name="bankcard" id="bankcard" maxlength="18">
			</div>
			<div class="row">
				<label class="field" for="tel">手机号</label>
				<input type="text" value="" class="input-text-user noPic input-click" name="tel" id="tel" maxlength="11">
			</div>
			<div class="row">
			<label class="field" for="pronum">选择商品</label>
			</div>
			<% for(Map<String,String> product:products){%>
			<div class="row">
				<input type="checkbox" onchange="check(<%=product.get("id") %>);" id="check<%=product.get("id") %>"
				value="<%=product.get("id") %>"><%=product.get("proname")+"(单价:"+product.get("price")+")" %>
				<input type="hidden" id="price<%=product.get("id") %>" value="<%=product.get("price")%>">
				<input type="hidden" id="pname<%=product.get("id") %>" value="<%=product.get("proname")%>">
				<input type="text" style="display:none;height:30px;width:60px;" maxlength="4" value="" class="input-text" 
				name="pro<%=product.get("id")%>" id="pro<%=product.get("id")%>" placeholder="输入数量" onchange="selectPro(<%=product.get("id")%>);">
			</div>
			<% } %>
			<div class="row">
			总金额：<b><span id="spanMoney" style=" font-size: 18px; color: red;">0</span></b>&nbsp;元
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

