<%-- 
    Document   : index
    Created on : 2014-7-7, 9:30:55
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java" errorPage="error.jsp"%>
<%@ include file="inc/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>财富客户管理系统会员登录</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="css/css.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%
		String code = RequestUtil.getString(request, "code");
		String pwd = RequestUtil.getString(request, "pwd");
		String msg = RequestUtil.getString(request, "msg");
		String act = RequestUtil.getString(request, "act");
		if(act!=null && act.equals("out")){
		    session.removeAttribute("user");
		}
		if (code != null && pwd != null) {
			try {
			    pwd = new MD5().getMD5of32(pwd).toLowerCase();
				String fieldArr= "id,name,pwd,code,age,viplvl,cardid,bankcard,phone,roleid,parentid,indate";
				DataField df = DaoFactory.getUserDao().getByNameAndPwd(code, pwd, fieldArr);
				if(df != null){
					request.getSession().setAttribute("user", df);
					//Forward.forward(request, response, "/back/index.jsp");
					response.sendRedirect("./back/index.jsp?act=login");
				}else{
				    response.sendRedirect("login.jsp?msg=nouser");
				}
			} catch (Exception e) {
				out.print("登录失败"+e.getMessage());
			}
		}
		if(msg!=null && msg.equals("nouser")){
		    StringBuffer tip = new StringBuffer("<script> ");
			tip.append("alert(\"用户不存在或者密码错误!\")");
			tip.append("</script>");
			out.print(tip.toString());
		}
	%>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td height="147" background="images/top02.gif"><img
				src="images/top03.gif" width="776" height="147" />
			</td>
		</tr>
	</table>
	<table width="562" border="0" align="center" cellpadding="0"
		cellspacing="0" class="right-table03">
		<tr>
			<td width="221"><table width="95%" border="0" cellpadding="0"
					cellspacing="0" class="login-text01">

					<tr>
						<td><table width="100%" border="0" cellpadding="0"
								cellspacing="0" class="login-text01">
								<tr>
									<td align="center"><img src="images/ico13.gif" width="107"
										height="97" />
									</td>
								</tr>
								<tr>
									<td height="40" align="center">&nbsp;</td>
								</tr>

							</table>
						</td>
						<td><img src="images/line01.gif" width="5" height="292" />
						</td>
					</tr>
				</table>
			</td>
			<td><table width="100%" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<td width="31%" height="35" class="login-text02">用户名称：<br />
						</td>
						<td width="69%"><input name="textfield" id="name" type="text"
							size="30" />
						</td>
					</tr>
					<tr>
						<td height="35" class="login-text02">密 码：<br />
						</td>
						<td><input name="textfield2" id="pwd" type="password"
							size="30" />
						</td>
					</tr>
					<tr>
						<td height="35">&nbsp;</td>
						<td><a href="registed.jsp">注册用户</a>
							<a href="editpwd.jsp">忘记密码</a>
						</td> 
					</tr>
					<tr>
						<td height="35">&nbsp;</td>
						<td><input name="Submit2" type="submit"
							class="right-button01" value="确认登陆"
							onclick="javascript:formsubmit();" /> <input name="Submit232"
							type="submit" class="right-button02" value="重 置" /></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<script type="text/javascript">
		function formsubmit() {
			var name = document.getElementById("name").value;
			var pwd = document.getElementById("pwd").value;
			if (name != null && pwd != null && pwd != "" && name != "") {
				var src_to = "login.jsp?code=" + name + "&pwd=" + pwd;
				window.location.href = src_to;
			}
		}
	</script>
</body>
</html>