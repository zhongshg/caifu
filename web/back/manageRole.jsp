<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/main.css" />

<%
	String rm = RequestUtil.getString(request, "rm");
	String id = RequestUtil.getString(request, "id");
	String rid = RequestUtil.getString(request, "rid");
	String rname = null;
	String remark = null;
	if(id != null){
	   Map<String ,String> role =  DaoFactory.getRolesDao().getById(id);
	   rname = role.get("name");
	   remark = role.get("remark");
	}
	String rmr = RequestUtil.getString(request, "rmr");
	System.out.println(rmr);
	System.out.println(rid);
	if(rmr!=null){
	    rname = RequestUtil.getString(request, "rname");
		remark = RequestUtil.getString(request, "remark");
	    if(rmr.equals("add")&&rname != null && rid != null){ //新增用户
			rname = URLDecoder.decode(rname, "utf-8");
			remark = URLDecoder.decode(remark, "utf-8");
			Map<String, String> roles = new HashMap<String, String>();
			roles.put("id", rid);
			roles.put("name", rname);
			roles.put("remark", remark);
			DaoFactory.getRolesDao().add(roles);
			Forward.forward(request, response, "manageRoles.jsp");
	    }else if(rmr.equals("edit")&& rname != null && rid != null){//修改角色
			rname = URLDecoder.decode(rname, "utf-8");
			remark = URLDecoder.decode(remark, "utf-8");
			System.out.println(rname);
			System.out.println(remark);
			Map<String, String> roles = new HashMap<String, String>();
			roles.put("id", rid);
			roles.put("name", rname);
			roles.put("remark", remark);
			DaoFactory.getRolesDao().update(roles);
			response.sendRedirect("manageRoles.jsp");
	    }else if(rmr.equals("del")){//删除角色
			DaoFactory.getRolesDao().delete(id,null);
			response.sendRedirect("manageRoles.jsp?msg=suc");
	    }
	}
%>
</head>
<body>
	I D:
	<input type="text" id="rid" value="<%=id==null?"":id%>" />
	<br> 角色:
	<input type="text" id="rname" value="<%=rname==null?"":rname %>" />
	<br> 备注:
	<input type="text" id="remark" value="<%=remark==null?"":remark %>" />
	<br>
	<input type="submit" id="submit" onclick="manageRole();"></input>
</body>
<script>
	function manageRole(){
		var id = document.getElementById("rid").value;
		var rname = document.getElementById("rname").value;
		var remark = document.getElementById("remark").value;
		var url = "manageRole.jsp?rmr="+"<%=rm%>";
		url += "&rid=" + id +"&rname=" + rname + "&remark="+ remark;
		window.location.href = encodeURI(encodeURI(url));
	}
</script>
</html>