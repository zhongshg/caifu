<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/main.css" />
<script type="text/javascript" src="js/libs/modernizr.min.js"></script>
<%@ include file="common.jsp"%>
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
		totalCount = DaoFactory.getAssetsDao().getTotalCount(user_id);
		List<Map<String, String>> assetsList = DaoFactory.getAssetsDao().get_Limit(currentpage, pagesize,user_id);
		request.setAttribute("assetsList", assetsList);
		String msg = RequestUtil.getString(request, "msg");
		if (msg != null && msg.equals("sucd")) {
			out.print("<script>alert(\"删除成功\");  </script>");
		} else if (msg != null && msg.equals("suce")) {
			out.print("<script>alert(\"修改成功\");  </script>");
		} 
	} else {
		String search = RequestUtil.getString(request, "search");
		String value = RequestUtil.getString(request, "value");
		if (sr != null) {
			if (sr.equals("search")) {//查询用户信息
				String where = SearchUtil.orderSearchMap.get(search) +" like '%" + value + "%'  "; 
				List<Map<String, String>> assetsList = DaoFactory.getOrdersDao().searchBywhere(where);
				request.setAttribute("assetsList", assetsList);
				currentpage = 1;
				pagesize = assetsList.size();
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
				<i class="icon-font"></i><a href="#">资金管理</a><span
					class="crumb-step">&gt;</span><span class="crumb-name">交易明细</span>
			</div>
		</div>
		<div class="search-wrap">
			<div class="search-content">
				<table class="search-tab">
					<tr>
						<th width="120">选择类型:</th>
						<td><select name="search-sort" id="search-sort">
							<%
								out.print(SearchUtil.getOrdersSelect("1"));
							%>
						</select></td>
						<th width="70">关键字:</th>
						<td><input class="common-text" name="keywords" value=""
							id="keywords" type="text"></td>
							<th width="50">日期:</th>
							<td><input type="date" value="<%=DateUtil.getStringDateShort()%>"/></td>
						<td><input class="btn btn-primary btn2" name="query"
							value="查询" type="submit" onclick="javascript:search()"></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="result-wrap">
			<form name="myform" id="myform" method="post">
				<div class="result-title">
				</div>
				<div class="result-content">
					<table class="result-tab" width="100%">
						<tr>
							<th class="tc" width="5%"><input class="allChoose" name=""
								type="checkbox"></th>
								<!-- id,amount,type,dr,ts,oid -->
							<th>ID</th>
							<th>金额</th>
							<th>类型</th>
							<th>交易时间</th>
							<th>交易状态</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${assetsList}" var="assetsMap">
							<tr>
								<td class="tc"><input name="id[]" value="${assetsMap.id}"
									type="checkbox"></td>
								<td>${assetsMap.id}</td>
								<td>${assetsMap.amount}</td>
								<td>${assetsMap.type}</td>
								<td>${assetsMap.ts}</td>
								<td>${assetsMap.dr}</td>
								<td><a class="link-update"
									href="tradeDetailView.jsp?rm=view&id=${assetsMap.id}">查看</a> </td>
							</tr>
						</c:forEach>
					</table>
					<div class="list-page">
						第<%=currentpage%>页（共<%=totalPage!=0?totalPage:currentpage%>页） <br> <a
							href="manageOrders.jsp?currentpage=1">首页</a> <a
							href="manageOrders.jsp?currentpage=<%=currentpage > 1 ? currentpage - 1 : currentpage%>">上一页</a>
						<a
							href="manageOrders.jsp?currentpage=<%=currentpage < totalPage ? currentpage + 1 : currentpage%>">下一页</a>
						<a href="manageOrders.jsp?currentpage=<%=totalPage%>">末页</a>
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
		var src_to = "manageOrders.jsp?sr=search&search=" + search + "&value="
				+ value;
		window.location.href = encodeURI(encodeURI(src_to));
	}
</script>
</html>