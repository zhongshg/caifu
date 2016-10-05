<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="common.jsp"%>
<%
    String rm = RequestUtil.getString(request, "rm");
	String id = RequestUtil.getString(request, "id");
	String onum = null;
	String oUserName = null;
	String ocount = null;
	String oprice = null;
	String oamountmoney = null;
	String odt = null;
	String osenddt = null;
	String ostatus = null;
	String olastupdatedt = null;
	if (rm != null && rm.equals("view")) {
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
						id="onum" name="onum" disabled="disabled">
				</div>
				<div class="row">
					<label class="field" for="oUserName">下单人</label> <input type="text"
						value="<%=oUserName==null?"":oUserName%>" class="input-text-password noPic input-click"
						id="oUserName" name="oUserName" disabled="disabled">
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
			</div>
		</div>
	</div>
</body>
</html>