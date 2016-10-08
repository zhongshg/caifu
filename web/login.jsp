<%@page contentType="text/html" pageEncoding="UTF-8" language="java" errorPage="error.jsp"%>
<%@ page import="job.tot.exception.*"%>
<%@ page import="job.tot.util.*" %>
<%@ page import="job.tot.bean.*" %>
<%@ page import="job.tot.dao.DaoFactory" %>
<%@ page import="job.tot.filter.IpFilter" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.net.*"%>
<%@ page import="job.tot.global.Sysconfig" %>
<%@ page import="job.tot.util.Forward" %>
<%@ page import="job.tot.dao.jdbc.UsersDao" %>
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
	 	request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html; charset=UTF-8");
	    if (!IpFilter.filter(request.getRemoteAddr())) {
	        response.sendRedirect("404.jsp");
	    }
		String act = RequestUtil.getString(request, "act");
		if(act!=null && act.equals("exit")){
		    session = request.getSession(false);//防止创建Session  
			if (session == null) {
			    return;
			}
			session.removeAttribute("user");
		}
		String id = RequestUtil.getString(request, "code");
		String pwd = RequestUtil.getString(request, "pwd");
		if (id != null && pwd != null) {
			try {
			    pwd = new MD5().getMD5of32(pwd);
				String fieldArr= "id,name,pwd,age,viplvl,cardid,bankcard,phone,roleid,parentid";
				UsersDao userDao = DaoFactory.getUserDao();
				DataField dfUser = userDao.getByIdAndPwd(id, pwd, fieldArr);
				if(dfUser != null){
				    userDao.remarkLogininfo(dfUser.getString("id"));
					if(dfUser.getString("roleid").equals("1")){//管理员
					    request.getSession().setAttribute("admin_name", dfUser.getString("name"));
						request.getSession().setAttribute("admin_id", dfUser.getString("id"));
						request.getSession().setAttribute("admin_viplvl", dfUser.getString("viplvl"));
						request.getSession().setAttribute("admin_roleid", dfUser.getString("roleid"));
						request.getSession().setAttribute("admin_parentid", dfUser.getString("parentid"));
						request.getSession().setAttribute("admin_bankcard", dfUser.getString("bankcard"));
						response.sendRedirect("./back/frames.jsp");
					}else{//普通用户
					    request.getSession().setAttribute("user_name", dfUser.getString("name"));
						request.getSession().setAttribute("user_id", dfUser.getString("id"));
						request.getSession().setAttribute("user_viplvl", dfUser.getString("viplvl"));
						request.getSession().setAttribute("user_roleid", dfUser.getString("roleid"));
						request.getSession().setAttribute("user_parentid", dfUser.getString("parentid"));
						request.getSession().setAttribute("user_bankcard", dfUser.getString("bankcard"));
					    response.sendRedirect("./fore/frames.jsp"); 
					}
				}else{
				    response.sendRedirect("login.jsp?msg=nouser");
				}
			} catch (Exception e) {
				out.print("登录失败"+e.getMessage());
			}
		}
		String msg = RequestUtil.getString(request, "msg");
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
				src="images/top03.gif" width="100%" height="147" />
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