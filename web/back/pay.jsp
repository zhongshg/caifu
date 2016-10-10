<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common.jsp"%>
<%@page import="job.tot.db.DBUtils" %>
<%
    String id = RequestUtil.getString(request, "id");
	String money = RequestUtil.getString(request, "money");
	boolean flag = false;
	if(money!=null && money.length() > 0){
		Map<String,String> assets = new HashMap<String,String>();
		DataField assetsDF = DaoFactory.getAssetsDao().getByCol("id="+id);
		Float new_assets = assetsDF.getFloat("assets") + Float.valueOf(money);
		Float new_balance = assetsDF.getFloat("balance") + Float.valueOf(money);
		assets.put("id", id);
		assets.put("assets", String.valueOf(new_assets));
		assets.put("balance", String.valueOf(new_balance));
		Connection conn = DBUtils.getConnection();
		conn.setAutoCommit(false);
		try{
			//更新用户资产
			flag = DaoFactory.getAssetsDao().update(conn,assets);
			if(flag){
				//新增资产收入
				Map<String,String> assets_in = new HashMap<String,String>();
				//uid,amount,type,oid
				assets_in.put("uid", id);
				assets_in.put("amount", money);
				assets_in.put("type", "14");//充值
				assets_in.put("oid", null);
				assets_in.put("dr", "1");
				flag = DaoFactory.getAssetsINDao().add(assets_in, conn);
				if(flag){
				    conn.commit();
				}
			}
			if(!flag){
			    conn.rollback(); 
			}
		}catch(Exception e){
		    e.printStackTrace();
		}finally{
		    DBUtils.closeConnection(conn);
		}
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
					<label class="field" for="uid">会员号</label> <input type="text"
						value="<%=id %>"
						class="input-text-user noPic input-click" id="uid"
						name="uid" disabled="disabled">
				</div>
				<div class="row">
					<label class="field" for="money">充值额度</label> <input type="text"
						value=""
						class="input-text-user noPic input-click" id="money"
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