<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common.jsp"%>
<%
    String id = RequestUtil.getString(request, "id");
	String money = RequestUtil.getString(request, "money");
	if(money!=null && money.length() > 0){
		Map<String,String> assets = new HashMap<String,String>();
		assets.put("id", id);
		assets.put("assets", money);
		boolean flag = DaoFactory.getAssetsDao().update(assets);
		if(flag){
			response.sendRedirect("manageUsers.jsp?msg=suct");
		}else{
		    response.sendRedirect("manageUsers.jsp?msg=sucf"); 
		}
	}
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户充值</title>
<link href="../css/styleRe.css" rel="stylesheet" type="text/css" />
</head>
<body class="loginbody">
	<div class="dataEye">
		<div class="loginbox registbox">
			<div class="login-content reg-content">
				<div class="loginbox-title">
					<h3 align="center">用户充值</h3>
				</div>
				<div class="login-error"></div>
				<div class="row">
					<label class="field" for="money">充值额度</label> <input type="text"
						value=""
						class="input-text-password noPic input-click" id="money"
						name="money">
				</div>
				<div class="row btnArea">
					<input class="login-btn" id="submit" onclick="pay(<%=id%>)" value="提交">
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	function pay(id){
		var money = document.getElementById("money").value;
		if(money!=null && money!=""){
			window.location.href="pay.jsp?money="+money+"&id="+id;
		}
	}
</script>
</html>