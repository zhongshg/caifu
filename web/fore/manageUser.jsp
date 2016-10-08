<%@page contentType="text/html" pageEncoding="UTF-8" language="java"
	errorPage="error.jsp"%>
<%@ include file="common.jsp"%>
<%
    String rmr = RequestUtil.getString(request, "rmr");
	String uid = RequestUtil.getString(request, "uid");
	String id = RequestUtil.getString(request, "id");
	String uname = null;
	String pwd = null;
	String bankcard = null;
	String cardid = null;
	String phone = null;
	String roleid = null;
	String viplvl = null;
	//String isvip = null;
	String secpwd = null;
	String address = null;
	if (rmr != null) {
		if (rmr.equals("edit")) {//修改会员信息
			//name,pwd,code,cardid,bankcard,phone,parentid
		    uname = URLDecoder.decode(RequestUtil.getString(request, "uname"), "utf-8");
			bankcard = URLDecoder.decode(RequestUtil.getString(request, "bankcard"), "utf-8");
			cardid = URLDecoder.decode(RequestUtil.getString(request, "cardid"), "utf-8");
			phone = URLDecoder.decode(RequestUtil.getString(request, "phone"), "utf-8");
			roleid = URLDecoder.decode(RequestUtil.getString(request, "roleid"), "utf-8");
			//isvip = URLDecoder.decode(RequestUtil.getString(request, "isvip"), "utf-8");
			address = URLDecoder.decode(RequestUtil.getString(request, "address"), "utf-8");
			Map<String, String> user = new HashMap<String, String>();
			if (RequestUtil.getString(request, "pwd") != null && RequestUtil.getString(request, "pwd")!="") {
			    pwd = URLDecoder.decode(RequestUtil.getString(request, "pwd"), "utf-8");
				pwd = new MD5().getMD5of32(pwd);
				user.put("pwd", pwd);
			}
			if(RequestUtil.getString(request, "secpwd")!=null && RequestUtil.getString(request, "secpwd")!=""){
			    secpwd = URLDecoder.decode(RequestUtil.getString(request, "secpwd"), "utf-8");
			    secpwd = new MD5().getMD5of32(secpwd);
			    user.put("secpwd",secpwd);
			}
			user.put("name", uname);
			user.put("bankcard", bankcard);
			user.put("cardid", cardid);
			user.put("phone", phone);
			user.put("roleid", roleid);
			//user.put("isvip",isvip);
			user.put("address",address);
			DaoFactory.getUserDao().update(uid, user);
			response.sendRedirect("manageUsers.jsp?msg=suce");
		} else if (rmr.equals("del")) {//删除会员
			DaoFactory.getUserDao().del(uid);
			response.sendRedirect("manageUsers.jsp?msg=sucd");
		}
	} else if (id != null) {
		DataField udf = DaoFactory.getUserDao().getByCol("id="+ id,
			"name,pwd,cardid,bankcard,phone,roleid,parentid,viplvl,isvip,secpwd,address");
		uname = udf.getString("name");
		bankcard = udf.getString("bankcard");
		cardid = udf.getString("cardid");
		phone = udf.getString("phone");
		roleid = udf.getString("roleid");
		viplvl = udf.getString("viplvl");
		//isvip = udf.getString("isvip");
		secpwd = udf.getString("secpwd");
		address = udf.getString("address");
	}
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户管理</title>
<link href="../css/styleRe.css" rel="stylesheet" type="text/css" />
</head>
<body class="loginbody">
	<div class="dataEye">
		<div class="loginbox registbox">
			<div class="login-content reg-content">
				<div class="loginbox-title">
					<h3 align="center">用户管理</h3>
				</div>
				<div class="login-error"></div>
				<div class="row">
					<label class="field" for="uid">会员号</label> <input type="text"
						value="<%=id %>" class="input-text-password noPic input-click" id="uid"
						name="uid" disabled="disabled">
				</div>
				<div class="row">
					<label class="field" for="uname">用户名</label> <input type="text"
						value="<%=uname %>" class="input-text-password noPic input-click" id="uname"
						name="uname">
				</div>
				<div class="row">
					<label class="field" for="pwd">密码</label> <input type="password"
						value="" class="input-text-user noPic input-click" id="pwd"
						name="pwd">
				</div>
				<div class="row">
					<label class="field" for="secpwd">二级密码</label> <input type="password"
						value="" class="input-text-user noPic input-click" id="secpwd"
						name="secpwd">
				</div>
				<div class="row">
					<label class="field" for="bankcard">银行卡号</label> <input type="text"
						value="<%=bankcard %>" class="input-text-user noPic input-click" id="bankcard"
						name="bankcard">
				</div>
				<div class="row">
					<label class="field" for="cardid">身份证号</label> <input type="text"
						value="<%=cardid %>" class="input-text-user noPic input-click" id="cardid"
						name="cardid">
				</div>
				<div class="row">
					<label class="field" for="phone">联系电话</label> <input type="text"
						value="<%=phone %>" class="input-text-user noPic input-click" id="phone"
						name="phone">
				</div>
				<div class="row">
					<label class="field" for="roleid">角色</label> <input type="text"
						value="<%=roleid %>" class="input-text-user noPic input-click" id="roleid"
						name="roleid">
				</div>
				<div class="row">
					<label class="field" for="viplvl">会员等级</label> <input type="text"
						value="<%=viplvl %>" class="input-text-user noPic input-click" id="viplvl"
						name="viplvl">
				</div>
				<div class="row">
					<label class="field" for="address">地址</label> <input type="text"
						value="<%=address %>" class="input-text-user noPic input-click" id="address"
						name="address">
				</div>
				<div class="row btnArea">
					<a class="login-btn" id="submit" onclick="manageUser();">提交</a>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	function manageUser() {
		var uname = document.getElementById("uname").value;
		var pwd = document.getElementById("pwd").value;
		var bankcard = document.getElementById("bankcard").value;
		var cardid = document.getElementById("cardid").value;
		var phone = document.getElementById("phone").value;
		var roleid = document.getElementById("roleid").value;
		var secpwd = document.getElementById("secpwd").value;
		var address = document.getElementById("address").value;
		document.getElementById('uid').disabled = false;
		var url = "manageUser.jsp?rmr=edit&uid=" + <%=id%> + "&uname=" + uname
				+ "&pwd=" + pwd + "&bankcard=" + bankcard + "&cardid=" + cardid
				+ "&phone=" + phone + "&roleid=" + roleid
				+ "&secpwd="+secpwd + "&address="+address;
		window.location.href = encodeURI(encodeURI(url));
	}
</script>
</html>