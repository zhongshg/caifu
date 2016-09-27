<%@page contentType="text/html" pageEncoding="UTF-8" language="java"
	errorPage="error.jsp"%>
<%@ include file="../inc/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/main.css" />

<%
	String rmr = RequestUtil.getString(request, "rmr");
	String id = RequestUtil.getString(request, "uid");
	String uname = null;
	String pwd = null;
	String bankcard = null;
	String cardid = null;
	String phone = null;
	String roleid = null;
	System.out.println("程序到达A点");
	if (rmr != null) {
		if (rmr.equals("edit")) {//修改角色
		    System.out.println("程序到达B点");
		    //name,pwd,code,cardid,bankcard,phone,parentid
			uname = URLDecoder.decode(RequestUtil.getString(request, "uname"), "utf-8");
			pwd = URLDecoder.decode(RequestUtil.getString(request, "pwd"), "utf-8");
			bankcard = URLDecoder.decode(RequestUtil.getString(request, "bankcard"), "utf-8");
			cardid = URLDecoder.decode(RequestUtil.getString(request, "cardid"), "utf-8");
			phone = URLDecoder.decode(RequestUtil.getString(request, "phone"), "utf-8");
			roleid = URLDecoder.decode(RequestUtil.getString(request, "roleid"), "utf-8");
			Map<String, String> user = new HashMap<String, String>();
			if(pwd!=null) {
			    System.out.println("程序到达C点");
			    pwd = new MD5().getMD5of32(pwd);
			    user.put("pwd", pwd);
			}
			user.put("name", uname);
			user.put("bankcard", bankcard);
			user.put("cardid", cardid);
			user.put("phone", phone);
			user.put("roleid", roleid);
			System.out.println("程序到达D点");
			DaoFactory.getUserDao().update(id, user);
			System.out.println("程序到达E点");
			Forward.forward(request, response, "manageuser.jsp");
		} else if (rmr.equals("del")) {//删除角色
			DaoFactory.getUserDao().del(id);
			response.sendRedirect("manageUsers.jsp?msg=suc");
		}
	}else if(id!=null){
	    DataField udf = DaoFactory.getUserDao().getByCol("id", id, "name,pwd,cardid,bankcard,phone,roleid,parentid,indate");
		uname = udf.getString("name");
		bankcard = udf.getString("bankcard");
		cardid = udf.getString("cardid");
		phone = udf.getString("phone");
		roleid = udf.getString("roleid");
	}
%>
</head>
<body>
	<form action="" method="post">
		I D: <input type="text" id="uid" name="uid" value="<%=id == null ? "" : id%>" disabled="disabled" /> <br> 
		用户名: <input type="text" id="uname" name="phone" value="<%=uname == null ? "" : uname%>" /> <br>
		密码: <input type="text" id="pwd" name="pwd" value="" /> <br>
		银行卡号: <input type="text" id="bankcard" name="bankcard" value="<%=bankcard == null ? "" : bankcard%>" /> <br>
		身份证号:<input type="text" id="cardid" name="cardid" value="<%=cardid == null ? "" : cardid%>" /> <br>
		联系方式:<input type="text" id="phone" name="phone" name="phone" value="<%=phone == null ? "" : phone%>" /> <br>
		角色:<input type="text" id=roleid name="roleid" value="<%=roleid == null ? "" : roleid%>" /> <br>
		<input type="submit" id="submit" onclick="manageUser();"></input>
	</form>
</body>
<script>
	function manageUser(){
		var id = document.getElementById("uid").value;
		var uname = document.getElementById("uname").value;
		var pwd = document.getElementById("pwd").value;
		var bankcard = document.getElementById("bankcard").value;
		var cardid = document.getElementById("cardid").value;
		var phone = document.getElementById("phone").value;
		var roleid = document.getElementById("roleid").value;
		document.getElementById('uid').disabled = false;
		var url = "manageUser.jsp?rmr=edit&uid=" + id
				+ "&uname=" + uname + "&pwd=" + pwd+"&bankcard="+bankcard
				+"&cardid="+cardid+"&phone="+phone+"&roleid="+roleid;
		window.location.href = encodeURI(encodeURI(url));
	}
</script>
</html>