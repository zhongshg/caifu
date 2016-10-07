<%@page import="job.tot.db.DBUtils"%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common.jsp"%>
<%
    String rm = RequestUtil.getString(request, "rm");
	String id = RequestUtil.getString(request, "id");
	String oid = RequestUtil.getString(request, "oid");
	String onum = null;
	String oUserName = null;
	String ocount = null;
	String oprice = null;
	String oamountmoney = null;
	String odt = null;
	String osenddt = null;
	String ostatus = null;
	String olastupdatedt = null;
	String pName = null;
	String rmr = RequestUtil.getString(request, "rmr");
	if (rmr != null) {
		onum = RequestUtil.getString(request, "onum");
		oUserName = RequestUtil.getString(request, "oUserName");
		ocount = RequestUtil.getString(request, "ocount");
		oprice = RequestUtil.getString(request, "oprice");
		oamountmoney = RequestUtil.getString(request, "oamountmoney");
		odt = RequestUtil.getString(request, "odt");
		osenddt = RequestUtil.getString(request, "osenddt");
		ostatus = RequestUtil.getString(request, "ostatus");
		olastupdatedt = RequestUtil.getString(request, "olastupdatedt");
		if (rmr.equals("add") && oUserName != null) { //新增订单
			onum = URLDecoder.decode(onum, "utf-8");
			oUserName = URLDecoder.decode(oUserName, "utf-8");
			ocount = URLDecoder.decode(ocount, "utf-8");
			oprice = URLDecoder.decode(oprice, "utf-8");
			oamountmoney = URLDecoder.decode(oamountmoney, "utf-8");
			odt = URLDecoder.decode(odt, "utf-8");
			osenddt = URLDecoder.decode(osenddt, "utf-8");
			ostatus = URLDecoder.decode(ostatus, "utf-8");
			olastupdatedt = URLDecoder.decode(olastupdatedt, "utf-8");
			Map<String, String> orders = new HashMap<String, String>();
			orders.put("onum", onum);
			orders.put("oUserName", oUserName);
			orders.put("ocount", ocount);
			orders.put("oprice", oprice);
			orders.put("oamountmoney", oamountmoney);
			orders.put("odt", odt);
			orders.put("osenddt", osenddt);
			orders.put("ostatus", ostatus);
			orders.put("olastupdatedt", olastupdatedt);
			Connection conn = DbConn.getConn();
			boolean flag = DaoFactory.getOrdersDao().add(conn,orders);
			DBUtils.closeConnection(conn);
			if(flag){
			    response.sendRedirect("manageOrders.jsp?msg=suca");
				//Forward.forward(request, response, "manageOrders.jsp?msg=suca");
			}else{
			    response.sendRedirect("manageOrders.jsp?msg=error");
			}
		} else if (rmr.equals("edit") && oUserName != null ) {//修改订单
			onum = URLDecoder.decode(onum, "utf-8");
			oUserName = URLDecoder.decode(oUserName, "utf-8");
			ocount = URLDecoder.decode(ocount, "utf-8");
			oprice = URLDecoder.decode(oprice, "utf-8");
			ocount = URLDecoder.decode(ocount, "utf-8");
			oprice = URLDecoder.decode(oprice, "utf-8");
			oamountmoney = URLDecoder.decode(oamountmoney, "utf-8");
			odt = URLDecoder.decode(odt, "utf-8");
			osenddt = URLDecoder.decode(osenddt, "utf-8");
			ostatus = URLDecoder.decode(ostatus, "utf-8");
			olastupdatedt = URLDecoder.decode(olastupdatedt, "utf-8");
			Map<String, String> orders = new HashMap<String, String>();
			orders.put("onum", onum);
			orders.put("oUserName", oUserName);
			orders.put("ocount", ocount);
			orders.put("oprice", oprice);
			orders.put("oamountmoney", oamountmoney);
			orders.put("odt", odt);
			orders.put("osenddt", osenddt);
			orders.put("ostatus", ostatus);
			orders.put("olastupdatedt", olastupdatedt);
			boolean flag = DaoFactory.getOrdersDao().update(oid, orders);
			if(flag){
				response.sendRedirect("manageOrders.jsp?msg=suce");
			}else{
			    response.sendRedirect("manageOrders.jsp?msg=error");
			}
		} else if (rmr.equals("del") && oid!=null) {//删除订单
			boolean flag = DaoFactory.getOrdersDao().del(oid);
			if(flag){
				response.sendRedirect("manageOrders.jsp?msg=sucd");
			}else{
			    response.sendRedirect("manageOrders.jsp?msg=error");
			}
		}
	}else if (rm != null && rm.equals("view")) {
	    DataField order = DaoFactory.getOrdersDao().getByCol("oid=" + id);
		onum = order.getString("onum");
		oUserName = order.getString("oUserName");
		ocount = order.getString("ocount");
		oprice = order.getString("oprice");
		oamountmoney = order.getString("oamountmoney");
		odt = order.getString("odt");
		osenddt = order.getString("osenddt");
		ostatus = order.getString("ostatus");
		olastupdatedt = order.getString("olastupdatedt");
		pName = order.getString("pName");
	}
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>订单管理</title>
<link href="../css/styleRe.css" rel="stylesheet" type="text/css" />
</head>
<body class="loginbody">
	<div class="dataEye">
		<div class="loginbox registbox">
			<div class="login-content reg-content">
				<div class="loginbox-title">
					<h3 align="center">订单管理</h3>
				</div>
				<div class="login-error"></div>
				<div class="row">
					<label class="field" for="onum">订单编号</label> <input type="text"
						value="<%=onum==null?"":onum%>" class="input-text-password noPic input-click"
						id="onum" name="onum" placeholder="如果为空则自动生成订单编号" disabled="disabled">
				</div>
				<div class="row">
					<label class="field" for="oUserName">下单人</label> <input type="text"
						value="<%=oUserName==null?"":oUserName%>" class="input-text-password noPic input-click"
						id="oUserName" name="oUserName" disabled="disabled">
				</div>
				<div class="row">
					<label class="field" for="pName">商品名称</label> <input type="text"
						value="<%=pName==null?"":pName%>" class="input-text-user noPic input-click"
						id="pName" name="pName" disabled="disabled">
				</div>
				<div class="row">
					<label class="field" for="oprice">商品价格</label> <input type="text"
						value="<%=oprice==null?"":oprice%>" class="input-text-user noPic input-click"
						id="oprice" name="oprice" disabled="disabled">
				</div>
				<div class="row">
					<label class="field" for="ocount">商品数量</label> <input type="text"
						value="<%=ocount==null?"":ocount%>" class="input-text-user noPic input-click"
						id="ocount" name="ocount" disabled="disabled">
				</div>
				<div class="row">
					<label class="field" for="oamountmoney">订单总额</label> <input type="text"
						value="<%=oamountmoney==null?"":oamountmoney%>" class="input-text-user noPic input-click"
						id="oamountmoney" name="oamountmoney" disabled="disabled">
				</div>
				<div class="row">
					<label class="field" for="odt">下单时间</label> <input type="text"
						value="<%=odt==null?"":odt%>" class="input-text-user noPic input-click"
						id="odt" name="odt" disabled="disabled">
				</div>
				<div class="row">
					<label class="field" for="osenddt">发货时间</label> <input type="text"
						value="<%=osenddt==null?"":osenddt%>" class="input-text-user noPic input-click"
						id="osenddt" name="osenddt" disabled="disabled">
				</div>
				<div class="row">
					<label class="field" for="ostatus">订单状态</label> <input type="text"
						value="<%=ostatus==null?"":ostatus%>" class="input-text-user noPic input-click"
						id="ostatus" name="ostatus" disabled="disabled">
				</div>
				<div class="row">
					<label class="field" for="olastupdatedt">完成时间</label> <input type="text"
						value="<%=olastupdatedt==null?"":olastupdatedt%>" class="input-text-user noPic input-click"
						id="olastupdatedt" name="olastupdatedt" disabled="disabled">
				</div>
				<!-- 
				<div class="row btnArea">
					<a class="login-btn" id="submit" onclick="manageOrder();">提交</a>
				</div>
				 -->
			</div>
		</div>
	</div>
</body>
<script>
	function manageOrder(){
		var onum = document.getElementById("onum").value;
		var oUserName = document.getElementById("oUserName").value;
		var oamountmoney = document.getElementById("oamountmoney").value;
		var oprice = document.getElementById("oprice").value;
		var ocount = document.getElementById("ocount").value;
		var odt = document.getElementById("odt").value;
		var ostatus = document.getElementById("ostatus").value;
		var olastupdatedt = document.getElementById("olastupdatedt").value;
		var url = "manageOrder.jsp?rmr=<%=rm%>";
		<%
			if(rm!=null&&rm.equals("edit")){
		%>
			url +="&pid=<%=id%>";
		<%	
			}
		%>
		url += "&onum=" + onum + "&oUserName=" + oUserName
				+ "&oamountmoney=" + oamountmoney + "&oprice=" + oprice + "&ocount="+ ocount;
		url +="&odt=" + odt + "&osenddt=" + osenddt;
				+ "&ostatus=" + ostatus + "&olastupdatedt=" + olastupdatedt;
		window.location.href = encodeURI(encodeURI(url));
	}
</script>
</html>