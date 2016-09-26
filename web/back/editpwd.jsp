<%-- 
    Document   : identity
    Created on : 2014-7-7, 11:40:11
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../inc/common.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String oldpwd = RequestUtil.getString(request, "oldpwd");
	String newpwd1 = RequestUtil.getString(request, "newpwd1");
	String newpwd2 = RequestUtil.getString(request, "newpwd2");
	String id = (String) session.getAttribute("admin_id");
	Map<String, String> users = new HashMap<String, String>();
	if (oldpwd != null && oldpwd != "" && newpwd1 != null && 
		newpwd1 != "" &&newpwd2!=null&&newpwd2!="") {
	    if (!newpwd1.equals(newpwd2)) {
			out.print("<script>alert(\"新密码两次输入不一致\");</script>");
			return;
		}
		if (oldpwd.equals(newpwd1)) {
		    out.print("<script>alert(\"新旧密码一致,请修改新密码\");</script>");
			return;
		}
		newpwd1 = new MD5().getMD5of32(newpwd1).toLowerCase();
		users.put("pwd", newpwd1);
		boolean flag = false;
		try{
			flag = DaoFactory.getUserDao().update(id, users);
		}catch(Exception e){
		    e.printStackTrace();
		    out.println("修改密码失败"+e.getMessage());
		}
		if (flag) {
			session.removeAttribute("admin_id");
		    out.println("<html>");  
		    out.println("<script>");  
		    out.println("window.open('../login.jsp','_top')");  
		    out.println("</script>");  
		    out.println("</html>");  
		} else {
			out.print("<script>alert(\"修改密码失败，请稍后重试！\");</script>");
		}
	}
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>重置密码</title>
<link type="text/css" rel="stylesheet" media="all" href="css/global.css" />
<link type="text/css" rel="stylesheet" media="all"
	href="css/global_color.css" />
</head>
<body>
	<!--导航区域结束-->
	<div id="main">
		<!--保存失败，旧密码错误！-->
		<form id ="editForm" action="#" method="post" class="main_form">
			<div class="text_info clearfix">
				<span>旧密码：</span>
			</div>
			<div class="input_info">
				<input type="password" id="oldpwd" name="oldpwd" class="width200" /><span
					class="required">*</span>
			</div>
			<div class="text_info clearfix">
				<span>新密码：</span>
			</div>
			<div class="input_info">
				<input type="password" id="newpwd1" name="newpwd1" class="width200" /><span
					class="required">*</span>
			</div>
			<div class="text_info clearfix">
				<span>重复新密码：</span>
			</div>
			<div class="input_info">
				<input type="password" id="newpwd2" name="newpwd2"  class="width200" /><span
					class="required">*</span>
			</div>
			<div class="button_info clearfix">
				<input type="submit" value="保存" class="btn_save"/> 
				<input type="submit" value="取消" class="btn_save" />
			</div>
		</form>
	</div>
	<!--主要区域结束-->

</body>
</html>