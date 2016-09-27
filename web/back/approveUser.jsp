<%@page contentType="text/html" pageEncoding="UTF-8" language="java"
	errorPage="error.jsp"%>
<%@ include file="../inc/common.jsp"%>
<%
    String rmr = RequestUtil.getString(request, "rmr");
	
	if (rmr != null) {
		if (rmr.equals("approve")) {//修改会员信息
			//name,pwd,code,cardid,bankcard,phone,parentid
			String id = RequestUtil.getString(request, "id");
			Map<String, String> user = new HashMap<String, String>();
			user.put("isvip","1");
			DaoFactory.getUserDao().update(id, user);
			response.sendRedirect("manageUsers.jsp?msg=suce");
		} 
	} 
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户审批</title>
<link href="../css/styleRe.css" rel="stylesheet" type="text/css" />
</head>
<body class="loginbody">
</body>
</html>