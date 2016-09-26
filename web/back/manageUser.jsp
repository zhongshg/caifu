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
	String rm = RequestUtil.getString(request, "rm");
    String rname = RequestUtil.getString(request, "rname");
	String remark = RequestUtil.getString(request, "remark");
	String id = RequestUtil.getString(request, "id");
	String rmr = RequestUtil.getString(request, "rmr");
	if(rmr!=null){
	    if(rmr.equals("add")&&rname != null && id != null){ //新增用户
			rname = URLDecoder.decode(rname, "utf-8");
			remark = URLDecoder.decode(remark, "utf-8");
			Map<String, String> roles = new HashMap<String, String>();
			roles.put("id", id);
			roles.put("name", rname);
			roles.put("remark", remark);
			DaoFactory.getRolesDao().add(roles);
			Forward.forward(request, response, "manageRoles.jsp");
	    }else if(rmr.equals("edit")&& rname != null && id != null){//修改角色
			rname = URLDecoder.decode(rname, "utf-8");
			remark = URLDecoder.decode(remark, "utf-8");
			Map<String, String> roles = new HashMap<String, String>();
			roles.put("id", id);
			roles.put("name", rname);
			roles.put("remark", remark);
			DaoFactory.getRolesDao().update(roles);
			Forward.forward(request, response, "manageRoles.jsp");
	    }else if(rmr.equals("del")){//删除角色
			DaoFactory.getRolesDao().delete(id,null);
			response.sendRedirect("manageRoles.jsp?msg=suc");
	    }
	}
%>
</head>
<body>
	I D:
	<input type="text" id="rid" value="<%=id==null?"":id%>" disabled="false"/>
	<br> 角色:
	<input type="text" id="rname" value="<%=rname==null?"":rname %>" />
	<br> 备注:
	<input type="text" id="remark" value="<%=remark==null?"":remark %>" />
	<br>
	<input type="submit" id="submit" onclick="javascript:addRole()"></input>
</body>
<script>
	function addRole(){
		var id = document.getElementById("rid");
		var rname = document.getElementById("rname");
		var remark = document.getElementById("remark");
		if("<%=rm%>"!=null && "<%=rm%>"=="edit"){
			document.getElementById('rid').disabled = false;
		}
		var url = "manageRole.jsp?rmr="+"<%=rm%>"+"&id=" + id.value + "&rname=" + rname.value + "&remark="
			+ remark.value;
		window.location.href = encodeURI(encodeURI(url));
	}
</script>
</html>