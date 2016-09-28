<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common.jsp"%>
<%
    String rm = RequestUtil.getString(request, "rm");
	String id = RequestUtil.getString(request, "id");
	String pid = RequestUtil.getString(request, "pid");
	String procode = null;
	String proname = null;
	String propertys = null;
	String price = null;
	String stock = null;
	String rmr = RequestUtil.getString(request, "rmr");
	if (rmr != null) {
		procode = RequestUtil.getString(request, "procode");
		proname = RequestUtil.getString(request, "proname");
		propertys = RequestUtil.getString(request, "propertys");
		price = RequestUtil.getString(request, "price");
		stock = RequestUtil.getString(request, "stock");
		if (rmr.equals("add") && proname != null) { //新增商品
			procode = URLDecoder.decode(procode, "utf-8");
			proname = URLDecoder.decode(proname, "utf-8");
			propertys = URLDecoder.decode(propertys, "utf-8");
			price = URLDecoder.decode(price, "utf-8");
			stock = URLDecoder.decode(stock, "utf-8");
			Map<String, String> roles = new HashMap<String, String>();
			roles.put("procode", procode);
			roles.put("proname", proname);
			roles.put("propertys", propertys);
			roles.put("price", price);
			roles.put("stock", stock);
			boolean flag = DaoFactory.getProductDao().add(procode, proname, propertys, null, null, null, price, stock);
			if(flag){
			    response.sendRedirect("manageProducts.jsp?msg=suca");
				//Forward.forward(request, response, "manageProducts.jsp?msg=suca");
			}else{
			    response.sendRedirect("manageProducts.jsp?msg=error");
			}
		} else if (rmr.equals("edit") && proname != null ) {//修改商品
			procode = URLDecoder.decode(procode, "utf-8");
			proname = URLDecoder.decode(proname, "utf-8");
			propertys = URLDecoder.decode(propertys, "utf-8");
			price = URLDecoder.decode(price, "utf-8");
			stock = URLDecoder.decode(stock, "utf-8");
			Map<String, String> roles = new HashMap<String, String>();
			roles.put("procode", procode);
			roles.put("proname", proname);
			roles.put("propertys", propertys);
			roles.put("price", price);
			roles.put("stock", stock);
			boolean flag = DaoFactory.getProductDao().update(pid, roles);
			if(flag){
				response.sendRedirect("manageProducts.jsp?msg=suce");
			}else{
			    response.sendRedirect("manageProducts.jsp?msg=error");
			}
		} else if (rmr.equals("del") && pid!=null) {//删除商品
			boolean flag = DaoFactory.getProductDao().del(pid);
			if(flag){
				response.sendRedirect("manageProducts.jsp?msg=sucd");
			}else{
			    response.sendRedirect("manageProducts.jsp?msg=error");
			}
		}
	}else if (rm != null && rm.equals("edit")) {
		DataField product = DaoFactory.getProductDao().getByCol("id=" + id, null);
		procode = product.getString("procode");
		proname = product.getString("proname");
		propertys = product.getString("propertys");
		price = product.getString("price");
		stock = product.getString("stock");
	}
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>商品管理</title>
<link href="../css/styleRe.css" rel="stylesheet" type="text/css" />
</head>
<body class="loginbody">
	<div class="dataEye">
		<div class="loginbox registbox">
			<div class="login-content reg-content">
				<div class="loginbox-title">
					<h3 align="center">商品管理</h3>
				</div>
				<div class="login-error"></div>
				<div class="row">
					<label class="field" for="procode">商品编号</label> <input type="text"
						value="<%=procode==null?"":procode%>" class="input-text-password noPic input-click"
						id="procode" name="procode" placeholder="如果为空则自动生成商品编号">
				</div>
				<div class="row">
					<label class="field" for="proname">商品名称</label> <input type="text"
						value="<%=proname==null?"":proname%>" class="input-text-password noPic input-click"
						id="proname" name="proname">
				</div>
				<div class="row">
					<label class="field" for="propertys">商品属性</label> <input type="text"
						value="<%=propertys==null?"":propertys%>" class="input-text-user noPic input-click"
						id="propertys" name="propertys">
				</div>
				<div class="row">
					<label class="field" for="price">商品价格</label> <input type="text"
						value="<%=price==null?"":price%>" class="input-text-user noPic input-click"
						id="price" name="price">
				</div>
				<div class="row">
					<label class="field" for="stock">商品库存</label> <input type="text"
						value="<%=stock==null?"":price%>" class="input-text-user noPic input-click"
						id="stock" name="stock">
				</div>
				<div class="row btnArea">
					<a class="login-btn" id="submit" onclick="manageProduct();">提交</a>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	function manageProduct(){
		var procode = document.getElementById("procode").value;
		var proname = document.getElementById("proname").value;
		var propertys = document.getElementById("propertys").value;
		var price = document.getElementById("price").value;
		var stock = document.getElementById("stock").value;
		var url = "manageProduct.jsp?rmr=<%=rm%>";
		<%
			if(rm!=null&&rm.equals("edit")){
		%>
			url +="&pid=<%=id%>";
		<%	
			}
		%>
		url += "&procode=" + procode + "&proname=" + proname
				+ "&propertys=" + propertys + "&price=" + price + "&stock="+ stock;
		window.location.href = encodeURI(encodeURI(url));
	}
</script>
</html>