<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<%
String act=RequestUtil.getString(request,"act");
int adminid=RequestUtil.getInt(request,"id");
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
<title>修改密码</title>
<link href="style/global.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.style1 {color: #FFFFFF}
.style2 {
	color: #FFFFFF;
	font-weight: bold;
	font-size: 14px;
}
-->
</style>
<script src="js/common.js"></script>
</head>
<script>
function check(obj){
	with(obj){
		if(PassWord.value==""){
			alert('请输入密码');
			PassWord.focus();
			PassWord.select();
			return false;
		}	
		else{
			return true;
		}
	}
}
</script>
<body>
<%@ include file="top.jsp"%><br />
<%
if(act!=null && act.equals("do")){	
	String UserName =(String)session.getAttribute("tot_texpert_admin");			
	String PassWord=RequestUtil.getString(request,"PassWord");	
	MD5 md5 = new MD5();
    String password = md5.getMD5of32(PassWord);
	boolean bl=DaoFactory.getAdminDAO().changePass(UserName,password);
	if(bl){	
		out.print("<br><p align=\"center\">Success!</p><br>");
%>
		<p align="center">成功修改密码</p>
<%	
	}
	else{
		out.print("<script>alert('Error');history.back();</script>");
	}
}
%>
<form id="addAdmin" name="addAdmin" method="post" action="?act=do" onSubmit="return check(this);">
<table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A3B2CC">
  <tr>
    <td height="22" colspan="2" bgcolor="#3872b2"><div align="center" class="style1"><span class="style2">
      修改密码</span></div></td>
    </tr>
  <tr>
    <td width="334" bgcolor="#FDFDFD"><div align="right">用户名:</div></td>
    <td width="631" bgcolor="#FDFDFD"><div align="left"><%=session.getAttribute("tot_texpert_admin")%></div></td>
  </tr>
  <tr>
    <td valign="top" bgcolor="#FDFDFD"><div align="right">密 码:</div></td>
    <td bgcolor="#FDFDFD">
		<div align="left">
		  <input name="PassWord" type="text" id="PassWord" />
		  <br />			
		</div>	</td>
  </tr>
  <tr>
    <td colspan="2" bgcolor="#FDFDFD"><div align="center">
		<input type="hidden" name="id" value="<%=adminid%>" />
        <input type="submit" name="Submit" value="提 交" />&nbsp;&nbsp;      
        <input type="reset" name="Submit2" value="重 写" />
    </div></td>
    </tr>
</table>
</form>
</body>
</html>
