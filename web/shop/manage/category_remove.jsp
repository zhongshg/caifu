<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
<title>移动栏目</title>
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
<body>
<%@ include file="top.jsp"%>
<%
String act=RequestUtil.getString(request,"act");
if(act!=null && act.equals("do")){		
	int fromid=RequestUtil.getInt(request,"fromid");
	int toid=RequestUtil.getInt(request,"toid");
	if(fromid==0 || toid==0 || fromid==toid){
		out.print("<script>alert('请选择栏目');history.back();</script>");
	}else{
		boolean bl=DaoFactory.getCategoryDAO().removeCategory(fromid,toid);
		out.print("<p align=\"center\">成功移动栏目!<br /><a href=\"javascript:history.back()\">继续移动</a>&nbsp;&nbsp;<a href=\"category_manage.jsp\">栏目管理</a></p>");	
		
	}	
	
}else{
	String sele=DaoFactory.getCategoryDAO().getSelectTree("",0,0,"zhongjian");
%>
<br />
<form id="addCategory" name="addCategory" method="post" action="?act=do">
<table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border">
  <tr>
    <td height="25" bgcolor="#3872b2"><div align="center" class="style1"><span class="style2">
      移动栏目</span></div></td>
    </tr>  
  <tr>
    <td height="100" bgcolor="#FDFDFD"><div align="center">将栏目
      <select name="fromid">
	  <option value="0">选择栏目..</option>
	  <%=sele%>
      </select>
      中的贴子移至
	  <select name="toid">
	  <option value="0">选择栏目..</option>
	  <%=sele%>
      </select></div></td>
    </tr>
  <tr>
    <td bgcolor="#FDFDFD"><div align="center">
        <input type="submit" name="Submit" value="确 定" />
      
        <input type="reset" name="Submit2" value="取 消" />
    </div></td>
    </tr>
</table>
</form>
<%}%>
</body>
</html>
