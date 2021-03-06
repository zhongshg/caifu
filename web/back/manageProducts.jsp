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
			? currentpage : RequestUtil.getInt(request, "currentpage");
		totalCount = DaoFactory.getProductDao().getTotalCount();
		List<Map<String, String>> productsList = DaoFactory.getProductDao().get_Limit(currentpage, pagesize,null);
		request.setAttribute("productsList", productsList);
		String msg = RequestUtil.getString(request, "msg");
		if (msg != null && msg.equals("sucd")) {
			out.print("<script>alert(\"删除成功\");  </script>");
		} else if (msg != null && msg.equals("suce")) {
			out.print("<script>alert(\"修改成功\");  </script>");
		} else if (msg != null && msg.equals("suca")) {
			out.print("<script>alert(\"新增商品成功\");  </script>");
		}else if(msg != null && msg.equals("error")){
		    out.print("<script>alert(\"操作失败,请重试\");  </script>");
		}
	} else {
		String search = RequestUtil.getString(request, "search");
		String value = RequestUtil.getString(request, "value");
		if (sr.equals("search")) {//查询商品信息
			String where = SearchUtil.productSearchMap.get(search).split("#")[0] + " like '%" + value + "%' ";
			List<Map<String, String>> productsList = DaoFactory.getProductDao().searchBywhere(where, null);
			request.setAttribute("productsList", productsList);
			currentpage = 1;
			pagesize = productsList.size();
			if (pagesize > 50) {
				out.print("<script>alert(\"数据量太大,请精确查询条件\");  </script>");
				return;
			}
		}
	}
%>
</head>
<body>
	<div class="main-wrap">
		<div class="crumb-wrap">
			<div class="crumb-list">
				<i class="icon-font"></i><a href="#">商品管理</a><span
					class="crumb-step">&gt;</span><span class="crumb-name">商品管理</span>
			</div>
		</div>
		<div class="search-wrap">
			<div class="search-content">
				<table class="search-tab">
					<tr>
						<th width="120">选择类型:</th>
						<td><select name="search-sort" id="search-sort">
								<%
									out.print(SearchUtil.getProductSelect("1"));
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
						<a href="manageProduct.jsp?rm=add"><i class="icon-font"></i>新增商品</a>
					</div>
				</div>
				<div class="result-content">
					<table class="result-tab" width="100%">
						<tr>
							<th class="tc" width="5%"><input class="allChoose" name=""
								type="checkbox"></th>
							<th>商品编号</th>
							<th>商品名称</th>
							<th>商品属性</th>
							<th>商品价格</th>
							<th>库存</th>
						</tr>
						<c:forEach items="${productsList}" var="productMap">
							<tr>
								<td class="tc"><input name="id[]" value="${productMap.id}"
									type="checkbox"></td>
								<td>${productMap.procode}</td>
								<td>${productMap.proname}</td>
								<td>${productMap.propertys}</td>
								<td>${productMap.price}</td>
								<td>${productMap.stock}</td>
								<td><a class="link-update"
									href="manageProduct.jsp?rm=edit&id=${productMap.id}">修改</a> <a
									class="link-del"
									href="manageProduct.jsp?rmr=del&pid=${productMap.id}">删除</a></td>
							</tr>
						</c:forEach>
					</table>
					<div class="list-page">
						第<%=currentpage%>页（共<%=totalPage%>页） <br> <a
							href="manageProducts.jsp?currentpage=1">首页</a> <a
							href="manageProducts.jsp?currentpage=<%=currentpage > 1 ? currentpage - 1 : currentpage%>">上一页</a>
						<a
							href="manageProducts.jsp?currentpage=<%=currentpage < totalPage ? currentpage + 1 : currentpage%>">下一页</a>
						<a href="manageProducts.jsp?currentpage=<%=totalPage%>">末页</a>
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
		var src_to = "manageProducts.jsp?sr=search&search=" + search + "&value="
				+ value;
		window.location.href = encodeURI(encodeURI(src_to));
	}
</script>
</html>