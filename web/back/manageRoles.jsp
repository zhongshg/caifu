<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/main.css" />
<script type="text/javascript" src="js/libs/modernizr.min.js"></script>
<%@ include file="../inc/common.jsp"%>

<%
    List<Map<String, String>> roleList = DaoFactory.getRolesDao().getList();
	request.setAttribute("roleList", roleList);
	String msg = RequestUtil.getString(request, "msg");
	if(msg!=null && msg.equals("suc")){
	    out.print("<script>alert(\"删除成功\");  </script>");
	}
%>
</head>
<body>
	<div class="main-wrap">
		<div class="crumb-wrap">
			<div class="crumb-list">
				<i class="icon-font"></i><a href="#">首页</a><span class="crumb-step">&gt;</span><span
					class="crumb-name">角色管理</span>
			</div>
		</div>
		<div class="search-wrap">
			<div class="search-content">
				<form action="#" method="post">
					<table class="search-tab">
						<tr>
							<th width="120">选择类型:</th>
							<td><select name="search-sort" id="">
									<option value="0">全部</option>
									<c:forEach items="${roleList}" var="roleMap">
										<option value="${roleMap.id}">${roleMap.name}</option>
									</c:forEach>
							</select></td>
							<th width="70">关键字:</th>
							<td><input class="common-text" placeholder="关键字"
								name="keywords" value="" id="keywords" type="text"></td>
							<td><input class="btn btn-primary btn2" name="sub"
								value="查询" type="submit" onclick="javascript:query()"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div class="result-wrap">
			<form name="myform" id="myform" method="post">
				<div class="result-title">
					<div class="result-list">
						<a href="manageRole.jsp"><i class="icon-font"></i>新增角色</a> <a
							id="batchDel" href="javascript:void(0)"
							onclick="javascript:aeRole(this)"><i class="icon-font"></i>批量删除</a>
						<a id="updateOrd" href="javascript:void(0)"><i
							class="icon-font"></i>更新排序</a>
					</div>
				</div>
				<div class="result-content">
					<table class="result-tab" width="100%">
						<tr>
							<th class="tc" width="5%"><input class="allChoose" name=""
								type="checkbox"></th>
							<th>ID</th>
							<th>角色名</th>
							<th>备注</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${roleList}" var="roleMap">
							<tr>
								<td class="tc"><input name="id[]" value="${roleMap.id}"
									type="checkbox"></td>
								<td>${roleMap.id}</td>
								<td>${roleMap.name}</td>
								<td>${roleMap.remark}</td>
								<td><a class="link-update" href="manageRole.jsp?rm=edit&id=${roleMap.id}&rname=${roleMap.name}&remark=${roleMap.remark}">修改</a> <a
									class="link-del" href="manageRole.jsp?rmr=del&id=${roleMap.id}">删除</a></td>
							</tr>
						</c:forEach>
					</table>
					<div class="list-page"><%=roleList.size()%>
						条 1/1 页
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
<script language="javascript" type="text/javascript">
</script>
</html>