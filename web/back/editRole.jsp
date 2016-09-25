<%@page contentType="text/html" pageEncoding="UTF-8" language="java"
	errorPage="error.jsp"%>
<%@ include file="../inc/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/main.css" />

<%
    String rname = RequestUtil.getString(request, "rname");
			String remark = RequestUtil.getString(request, "remark");
			String id = RequestUtil.getString(request, "id");
			if (rname != null && id != null) {
				rname = URLDecoder.decode(rname, "utf-8");
				remark = URLDecoder.decode(remark, "utf-8");
				Map<String, String> roles = new HashMap<String, String>();
				roles.put("id", id);
				roles.put("name", rname);
				roles.put("remark", remark);
				DaoFactory.getRolesDao().add(roles);
				Forward.forward(request, response, "manageRoles.jsp");
			}
%>
</head>
<body>
	I D:
	<input type="text" id="rid"></input>
	<br> 角色:
	<input type="text" id="rname"></input>
	<br> 备注:
	<input type="text" id="remark"></input>
	<br>
	<input type="submit" id="submit" onclick="javascript:addRole()"></input>
</body>
<script>
	function addRole(){
		var id = document.getElementById("rid");
		var rname = document.getElementById("rname");
		var remark = document.getElementById("remark");
		var url = "aeRole.jsp?id=" + id.value + "&rname=" + rname.value + "&remark="
				+ remark.value;
		window.location.href = encodeURI(encodeURI(url));
	}
</script>
</html>