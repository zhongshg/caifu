<%-- 
    Document   : identity
    Created on : 2014-7-7, 11:40:11
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	function test() {
		var password1 = document.getElementById("password1").value;
		var password2 = document.getElementById("password2").value;
		if (password1 != password2 || 5 > password1.length) {
			return false;
		}
	}
</script>
</head>
<body>
	<!--导航区域结束-->
	<div id="main">
		<!--保存操作后的提示信息：成功或者失败-->
		<div id="save_result_info" class="save_success">保存成功！</div>
		<!--保存失败，旧密码错误！-->
		<form action="" method="" class="main_form">
			<div class="text_info clearfix">
				<span>旧密码：</span>
			</div>
			<div class="input_info">
				<input type="password" class="width200" /><span class="required">*</span>
				<div class="validate_msg_medium">30长度以内的字母、数字和下划线的组合</div>
			</div>
			<div class="text_info clearfix">
				<span>新密码：</span>
			</div>
			<div class="input_info">
				<input type="password" class="width200" /><span class="required">*</span>
				<div class="validate_msg_medium">30长度以内的字母、数字和下划线的组合</div>
			</div>
			<div class="text_info clearfix">
				<span>重复新密码：</span>
			</div>
			<div class="input_info">
				<input type="password" class="width200" /><span class="required">*</span>
				<div class="validate_msg_medium">两次新密码必须相同</div>
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