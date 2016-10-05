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
	int currentpage = 1;
	int pagesize = 10;
	int totalCount = 0;
	int totalPage = 0;
    String sr = RequestUtil.getString(request, "sr");
	if (sr == null) {
		currentpage = RequestUtil.getString(request, "currentpage") == null 
			|| RequestUtil.getString(request, "currentpage").equals("")
				? currentpage
				: RequestUtil.getInt(request, "currentpage");
		totalCount = DaoFactory.getUserDao().getTotalCount();
		String where = " isvip = 1 ";
		List<Map<String, String>> usersList = DaoFactory.getUserDao().get_Limit(currentpage, pagesize,where);
		request.setAttribute("usersList", usersList);
		String msg = RequestUtil.getString(request, "msg");
		if (msg != null && msg.equals("sucd")) {
			out.print("<script>alert(\"删除成功\");  </script>");
		} else if (msg != null && msg.equals("suce")) {
			out.print("<script>alert(\"修改成功\");  </script>");
		} else if (msg != null && msg.equals("sucr")) {
			out.print("<script>alert(\"新增用户成功\");  </script>");
		}else if (msg != null && msg.equals("suct")) {
			out.print("<script>alert(\"充值成功\");  </script>");
		}else if (msg != null && msg.equals("suc")) {
			out.print("<script>alert(\"充值失败,请稍候尝试!\");  </script>");
		}
	} else {
		String search = RequestUtil.getString(request, "search");
		String value = RequestUtil.getString(request, "value");
		if (sr != null) {
			if (sr.equals("search")) {//查询用户信息
				String where = SearchUtil.userSearchMap.get(search).split("#")[0] + " like '%" + value + "%' and isvip=1 ";
				List<Map<String, String>> usersList = DaoFactory.getUserDao().searchBywhere(where, null);
				request.setAttribute("usersList", usersList);
				currentpage = 1;
				pagesize = usersList.size();
				if (pagesize > 50) {
					out.print("<script>alert(\"数据量太大,请精确查询条件\");  </script>");
					return;
				}
			}
		}
	}
%>
</head>
<body>
	<div class="main-wrap">
		<div class="crumb-wrap">
			<div class="crumb-list">
				<i class="icon-font"></i><a href="#">会员管理</a><span
					class="crumb-step">&gt;</span><span class="crumb-name">会员管理</span>
			</div>
		</div>
		<div class="search-wrap">
			<div class="search-content">
				<table class="search-tab">
					<tr>
						<th width="120">选择类型:</th>
						<td><select name="search-sort" id="search-sort">
						<%
							out.print(SearchUtil.getUsersSelect("1"));
						%>
						</select></td>
						<th width="70">关键字:</th>
						<td><input class="common-text" name="keywords" value=""
							id="keywords" type="text"></td>
						<td><input class="btn btn-primary btn2" name="query"
							value="查询" type="submit" onclick="javascript:search()"></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="result-wrap">
			<form name="myform" id="myform" method="post">
				<div class="result-title">
					<div class="result-list">
						<a href="registed.jsp"><i class="icon-font"></i>新增用户</a>
					</div>
				</div>
				<div class="result-content">
					<table class="result-tab" width="100%">
						<tr>
							<th class="tc" width="5%"><input class="allChoose" name=""
								type="checkbox"></th>
							<th>会员号</th>
							<th>用户名</th>
							<th>会员等级</th>
							<th>身份证号</th>
							<th>银行卡号</th>
							<th>手机号</th>
							<th>上级会员号</th>
							<th>加入时间</th>
							<th>角色</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${usersList}" var="userMap">
							<tr>
								<td class="tc"><input name="id[]" value="${userMap.id}"
									type="checkbox"></td>
								<td><a href="../showUsers.jsp?uid=${userMap.id}">${userMap.id}</a></td>
								<td>${userMap.name}</td>
								<td>${userMap.viplvl}</td>
								<td>${userMap.cardid}</td>
								<td>${userMap.bankcard}</td>
								<td>${userMap.phone}</td>
								<td>${userMap.parentid}</td>
								<td>${userMap.ts}</td>
								<td><c:if test="${userMap.roleid=='0'}">普通用户</c:if>
								<c:if test="${userMap.roleid=='1'}">管理员</c:if>
								</td>
								<td>
								<a class="link-update"
									href="pay.jsp?id=${userMap.id}">充值</a>
								<a class="link-update"
									href="manageUser.jsp?rm=edit&id=${userMap.id}">修改</a> <a
									class="link-del"
									href="manageUser.jsp?rmr=del&uid=${userMap.id}">删除</a></td>
							</tr>
						</c:forEach>
					</table>
					<div class="list-page">
						第<%=currentpage%>页（共<%=totalPage==0?currentpage:totalPage%>页） <br> <a
							href="manageUsers.jsp?currentpage=1">首页</a> <a
							href="manageUsers.jsp?currentpage=<%=currentpage > 1 ? currentpage - 1 : currentpage%>">上一页</a>
						<a
							href="manageUsers.jsp?currentpage=<%=currentpage < totalPage ? currentpage + 1 : currentpage%>">下一页</a>
						<a href="manageUsers.jsp?currentpage=<%=totalPage%>">末页</a>
						<!-- 通过表单提交用户想要显示的页数 -->
						<form action="#" method="get">
							跳转到第<input type="text" name="currentpage" size="4">页 <input
								type="submit" name="submit" value="跳转">
						</form>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	function search() {
		var search = document.getElementById("search-sort").value;
		var value = document.getElementById("keywords").value;
		var src_to = "manageUsers.jsp?sr=search&search=" + search + "&value="
				+ value;
		window.location.href = encodeURI(encodeURI(src_to));
	}
</script>
</html>