<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/main.css" />
<script type="text/javascript" src="js/libs/modernizr.min.js"></script>
<%@ include file="../inc/common.jsp"%>
<%!int currentpage = 0;
	int pagesize = 20;
	int totalCount = 0;%>
<%
	currentpage = request.getAttribute("currentpage")==null?currentpage:RequestUtil.getInt(request, "currentpage");
	totalCount = DaoFactory.getUserDao().getTotalCount();
	totalCount = (totalCount%pagesize)==0?(totalCount/pagesize):(totalCount/pagesize+1);
	List<Map<String, String>> usersList = DaoFactory.getUserDao().get_Limit(currentpage, pagesize);
	String msg = RequestUtil.getString(request, "msg");
	if (msg != null && msg.equals("suc")) {
		out.print("<script>alert(\"删除成功\");  </script>");
	}
%>
</head>
<body>
	<div class="main-wrap">
		<div class="crumb-wrap">
			<div class="crumb-list">
				<i class="icon-font"></i><a href="#">首页</a><span class="crumb-step">&gt;</span><span
					class="crumb-name">会员管理</span>
			</div>
		</div>
		<div class="search-wrap">
			<div class="search-content">
				<form action="#" method="post">
					<table class="search-tab">
						<tr>
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
						<a href="registed.jsp"><i class="icon-font"></i>新增用户</a>
					</div>
				</div>
				<div class="result-content">
					<table class="result-tab" width="100%">
						<tr>
							<th class="tc" width="5%"><input class="allChoose" name=""
								type="checkbox"></th>
							<th>ID</th>
							<th>用户名</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${usersList}" var="userMap">
							<tr>
								<td class="tc"><input name="id[]" value="${userMap.id}"
									type="checkbox"></td>
								<td>${userMap.id}</td>
								<td>${userMap.name}</td>
								<td><a class="link-update"
									href="manageUser.jsp?rm=edit&id=${userMap.id}">修改</a> <a
									class="link-del" href="manageUser.jsp?rmr=del&id=${userMap.id}">删除</a></td>
							</tr>
						</c:forEach>
					</table>
					<div class="list-page">
						第<%=currentpage%>页（共<%=totalCount%>页） <br> <a
							href="manageUsers.jsp?currentpage=1">首页</a> <a
							href="manageUsers.jsp?currentpage=<%=currentpage - 1%>">上一页</a>
						<%
						    //根据pageCount的值显示每一页的数字并附加上相应的超链接
						   
							for (int i = 1; i <= currentpage; i++) {
							    if(i > 3){
									break;
							    }
						%>			
								<a href="manageUsers.jsp?currentpage=<%=i%>"><%=i%></a>
						<%
						    }
						%>
						<a href="manageUsers?currentpage=<%=currentpage + 1%>">下一页</a> <a
							href="manageUsers?currentpage=<%=totalCount%>">末页</a>
						<!-- 通过表单提交用户想要显示的页数 -->
						<form action="#" method="get">
							跳转到第<input type="text" name="showPage" size="4">页 <input
								type="submit" name="submit" value="跳转">
						</form>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
<script language="javascript" type="text/javascript">
	
</script>
</html>