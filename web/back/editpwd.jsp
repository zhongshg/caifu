<%-- 
    Document   : identity
    Created on : 2014-7-7, 11:40:11
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../inc/common.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String opwd = RequestUtil.getString(request, "opwd");
	String npwd = RequestUtil.getString(request, "npwd");
	String id = (String)session.getAttribute("admin_id");
	System.out.println("dasdsasds");
	Map<String,String> users = new HashMap<String,String>();
	System.out.println(opwd);
	System.out.println(npwd);
	if(opwd != null && opwd!="" &&npwd != null && npwd!=""){
	    System.out.println("啊啊啊啊啊啊啊");
	    users.put("pwd", npwd);
	    DaoFactory.getUserDao().update(id, users);
	    session.removeAttribute("admin_id");
	    response.sendRedirect("../login.jsp");
	}
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>重置密码</title>
<link type="text/css" rel="stylesheet" media="all"
	href="css/global.css" />
<link type="text/css" rel="stylesheet" media="all"
	href="css/global_color.css" />
<script type="text/javascript">
	function showResult() {
		var oldpwd1 = document.getElementById("oldpwd1").value;
		var oldpwd2 = document.getElementById("oldpwd2").value;
		var newpwd = document.getElementById("newpwd").value;
		if (password1 != password2 ) {
			alert("旧密码两次输入不一致");
			return false;
		}
		if(password1==newpwd){
			alert("新旧密码一致,请修改新密码");
			return false;
		}
		window.location.href="editpwd.jsp?opwd="+oldpwd1+"&npwd="+newpwd;
	}
</script>
</head>
<body>
	<!--导航区域结束-->
	<div id="main">
		<!--保存失败，旧密码错误！-->
		<form action="" method="" class="main_form">
			<div class="text_info clearfix">
				<span>旧密码：</span>
			</div>
			<div class="input_info">
				<input type="password" id="oldpwd1" class="width200" /><span class="required">*</span>
			</div>
			<div class="text_info clearfix">
				<span>新密码：</span>
			</div>
			<div class="input_info">
				<input type="password" id="oldpwd2" class="width200" /><span class="required">*</span>
			</div>
			<div class="text_info clearfix">
				<span>重复新密码：</span>
			</div>
			<div class="input_info">
				<input type="password" id="newpwd" class="width200" /><span class="required">*</span>
			</div>
			<div class="button_info clearfix">
				<input type="button" value="保存" class="btn_save"
					onclick="showResult();" /> <input type="button" value="取消"
					class="btn_save" />
			</div>
		</form>
	</div>
	<!--主要区域结束-->

</body>
<!-- Download From www.exet.tk-->
</html>