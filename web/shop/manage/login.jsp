<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="error.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="job.tot.image.CaptchaServiceSingleton" %>
<%@ include file="../inc/common.jsp"%>
<%
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragrma","no-cache");
response.setDateHeader("Expires",0); 
String act=RequestUtil.getString(request,"act");
if(act!=null && act.equals("login")){
	Boolean isResponseCorrect =Boolean.FALSE;
	boolean bl;
	String captchaId = request.getSession().getId();
	String getcode =RequestUtil.getString(request,"CertCode");
	String session_code=session.getAttribute("totcms_vertify")+"";
	if(getcode!=null && getcode.equals(session_code)){
		isResponseCorrect=true;
	}
	if(isResponseCorrect){
		String UserName=RequestUtil.getString(request,"UserName");	
		String PassWord=RequestUtil.getString(request,"PassWord");	
		bl=DaoFactory.getAdminDAO().adminLogin(UserName,PassWord);
		if(bl){			
				session.setAttribute("tot_texpert_admin",UserName);					
				response.sendRedirect("index.jsp");			
		}
		else{
			out.print("登录失败!");
		}
	}
	else{
		out.print("<script>alert(\"您输入的验证码不正确！\");history.back();</script>");
	}
}
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<fmt:bundle basename="resources.totcms_i18n">

<head>
<meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
<title>管理员登录</title>
<link href="style/global.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.style1 {color: #ffffff}
.style2 {
	color: #ffffff;
	font-weight: bold;
	font-size: 14px;
}
-->
</style>
</head>
<script>
function check(obj){
	with(obj){
		if(UserName.value==""){
			alert('请输入用户名');
			UserName.focus();
			UserName.select();
			return false;
		}
		else if(PassWord.value==""){
			alert('请输入密码');
			PassWord.focus();
			PassWord.select();
			return false;
		}
		else if(CertCode.value==""){
			alert('验证码不能为空');
			CertCode.focus();
			CertCode.select();
			return false;
		}
		else{
			return true;
		}
	}
}
function Verification(){

 document.getElementById("Ver").src="/ImageCode?ran="+Math.random();
 
    
}
</script>
<body onload="document.form1.UserName.focus()">
<form id="form1" name="form1" method="post" action="?act=login" onSubmit="return check(this);">
<p>&nbsp;</p>
<p>&nbsp;</p>
<table width="50%" height="227" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#ffffff" style="border:1px solid #cccccc;">
  <tr>
    <td height="35" colspan="2" bgcolor="#0066CC"><div align="center" class="style1"><span class="style2">管理登录</span></div></td>
    </tr>
  <tr>
    <td height="10" colspan="2" bgcolor="#FFFFFF">&nbsp;</td>
    </tr>
  <tr>
    <td width="185" height="35" bgcolor="#FFFFFF"><div align="right">用户名：</div></td>
    <td width="307" bgcolor="#FFFFFF"><div align="left">
      <input name="UserName" type="text" id="UserName" maxlength="50" style="width:146px; height:26px; line-height:26px; border:1px solid #dddddd;" />
    </div></td>
  </tr>
  <tr>
    <td height="35" bgcolor="#FFFFFF"><div align="right">密码：</div></td>
    <td bgcolor="#FFFFFF"><div align="left">
      <input name="PassWord" type="PassWord" id="Passwd" maxlength="50" style="width:146px; height:26px; line-height:26px; border:1px solid #dddddd;" />
    </div></td>
  </tr>
  <tr>
    <td height="35" bgcolor="#FFFFFF"><div align="right">验证码：</div></td>
    <td bgcolor="#FFFFFF"><div align="left">
      <input type='text' name='CertCode' value='' style="width:80px; height:26px; line-height:26px; border:1px solid #dddddd;" />
      <img id="Ver" name="Ver" src="/ImageCode" align="absmiddle" onclick="Verification()"/></div></td>
  </tr>
  <tr>
    <td height="45" bgcolor="#FFFFFF">&nbsp;</td>
    <td height="45" bgcolor="#FFFFFF"><input type="submit" name="Submit" value="提交" style="background-color:#06F; width:80px; height:28px; line-height:28px; text-align:center; color:#FFF; font-size:14px; font-weight:bold; border:1px solid #ddd; cursor:pointer;" /></td>
    </tr>
</table>
</form>

</fmt:bundle>
</body>
</html>
