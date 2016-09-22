<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<fmt:bundle basename="resources.totcms_i18n">

<head>
<meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
<title>preview</title>
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
<%
int id=RequestUtil.getInt(request,"id");
String savepath="";
DataField df=DaoFactory.getCategoryDAO().getCategory(id);
if(df!=null){
	int ischannel=Integer.parseInt(df.getFieldValue("IsChannel"));
	savepath=df.getFieldValue("SavePath");
	if(ischannel==1){
		savepath=savepath+"/index.htm";
	}else{
		savepath=savepath+"/index_1.htm";
	}
}
response.sendRedirect("../"+savepath);
%>
</fmt:bundle>
</body>
</html>