<%@page contentType="text/html" pageEncoding="UTF-8" language="java"
	errorPage="error.jsp"%>
<%@ include file="common.jsp"%>
<%
	String id = (String)session.getAttribute("user_id");
	String uname = (String)session.getAttribute("user_name");
	DataField uassets = DaoFactory.getAssetsDao().getByCol("id="+id);
	String assets = uassets.getString("assets");
	String balance = uassets.getString("balance");
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>余额管理</title>
<link href="../css/styleRe.css" rel="stylesheet" type="text/css" />
</head>
<body class="loginbody">
	<div class="dataEye">
		<div class="loginbox registbox">
			<div class="login-content reg-content">
				<div class="loginbox-title">
					<h3 align="center">余额管理</h3>
				</div>
				<div class="login-error"></div>
				<div class="row">
					<label class="field" for="uid">会员号</label> <input type="text"
						value="<%=id==null?"":id %>" class="input-text-password noPic input-click" id="uid"
						name="uid" disabled="disabled">
				</div>
				<div class="row">
					<label class="field" for="uname">用户名</label> <input type="text"
						value="<%=uname==null?"":uname %>" class="input-text-password noPic input-click" id="uname"
						name="uname">
				</div>
				<div class="row">
					<label class="field" for="assets">余额总额</label> <input type="text"
						value="<%=assets==null?"0.00":assets %>" class="input-text-user noPic input-click" id="assets"
						name="assets" disabled="disabled">
				</div>
				<div class="row">
					<label class="field" for="balance">可提现余额</label> <input type="text"
						value="<%=balance==null?"0.00":balance %>" class="input-text-user noPic input-click" id="balance"
						name="balance" disabled="disabled">
				</div>
				<div class="row"></div>
				<hr style="border:1px solid gray" >
				<div class="row">
					<input type="text"
						value="" class="input-text-user noPic input-click" id="balanceout"
						name="balanceout" style="border:1px solid blue" >
				</div>
				<div class="row btnArea">
					<a class="login-btn" id="submit" onclick="balanceout();">取现</a>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	function balanceout(){
		var balanceout = document.getElementById("balanceout").value;
		if(balanceout==null || balanceout==""){
			alert("请输入取现额度");
			return;
		}
		var balance = document.getElementById("balance").value;
		if(balance < balanceout){
			alert("取现额度高于可取现额度");
			return;
		}
		//进行取现操作
	}
</script>
</html>