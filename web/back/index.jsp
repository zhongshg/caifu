<%-- 
    Document   : index
    Created on : 2014-7-7, 9:30:55
    Author     : Administrator
--%>

<%@page import="org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID"%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"
	errorPage="error.jsp"%>
<%@ include file="../inc/common.jsp"%>
<%
    DataField df = (DataField) request.getSession().getAttribute("user");
			if (df == null) {
				session = request.getSession(false);//防止创建Session  
				if (session == null) {
					response.sendRedirect("../login.jsp");
					return;
				}
				session.removeAttribute("user");
				response.sendRedirect("../login.jsp");
			}
			String name = null;
			int roleid = -1;
			int id = -1;
			int rid = -1;
			if (df != null) {
				name = df.getString("name");
				roleid = Integer.parseInt(df.getFieldValue("roleid") == null ? "-1" : df.getFieldValue("roleid"));
				id = Integer.parseInt(df.getFieldValue("id") == null ? "-1" : df.getFieldValue("id"));
				rid = Integer.parseInt(df.getFieldValue("roleid") == null ? "-1" : df.getFieldValue("roleid"));
			}
			// List<DataField> nodeList = (List<DataField>)DaoFactory.getNodesDao().getList(id);
			Map<Map<String, String>, List<Map<String, String>>> powersPS = new LinkedHashMap<Map<String, String>, List<Map<String, String>>>();
			System.out.println("roleid=" + roleid);
			Map<String, String> roles = DaoFactory.getRolesDao().getByUsers(roleid);
			Map<String, String> powers = new LinkedHashMap<String, String>();
			powers.put("id", "0");
			List<Map<String, String>> powersFirstList = DaoFactory.getPowersDao().getListByRP(roles, powers);
			for (Map<String, String> powersFirst : powersFirstList) {
				List<Map<String, String>> powersSubList = DaoFactory.getPowersDao().getListByRP(roles, powersFirst);
				powersPS.put(powersFirst, powersSubList);
			}
			request.setAttribute("powersPS", powersPS);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>财富客户管理系统</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/JavaScript" src="js/back.js"></script>
</head>

<body>
	<div class="header">
		<div class="header03"></div>
		<div class="header01"></div>
		<div class="header02">财富客户管理系统</div>
	</div>
	<div class="left" id="LeftBox">
		<div class="left01">
			<div class="left01_right"></div>
			<div class="left01_left"></div>
			<div class="left01_c"><%=name %></div>
		</div>
		<div class="left02">
			<div class="left02top">
				<div class="left02top_right"></div>
				<div class="left02top_left"></div>
				<div class="left02top_c">用户信息管理</div>
			</div>
			<div class="left02down">
				<div class="left02down01">
					<a onclick="show_menuB(80)" href="javascript:;"><div id="Bf080"
							class="left02down01_img"></div>用户信息查询</a>
				</div>
				<div class="left02down01_xia noneBox" id="Bli080">
					<ul>
						<li onmousemove="show_menu(10)" id="f010"><a href="#">&middot;精确查询</a></li>
						<li onmousemove="show_menu(11)" id="f011"><a href="#">&middot;组合条件查询</a></li>
					</ul>
				</div>
				<div class="left02down01">
					<a onclick="show_menuB(81)" href="javascript:;">
						<div id="Bf081" class="left02down01_img"></div> 用户密码管理
					</a>
				</div>
				<div class="left02down01_xia noneBox" id="Bli081">
					<ul>
						<li onmousemove="show_menu(12)" id="f012"><a href="#">&middot;找回密码</a></li>
						<li onmousemove="show_menu(13)" id="f013"><a href="#">&middot;更改密码</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="left02">
			<div class="left02top">
				<div class="left02top_right"></div>
				<div class="left02top_left"></div>
				<div class="left02top_c">用户分析</div>
			</div>
			<div class="left02down">
				<div class="left02down01">
					<a onclick="show_menuB(82)" href="javascript:;"><div id="Bf082"
							class="left02down01_img"></div>用户注册统计</a>
				</div>
				<div class="left02down01">
					<a href="#"><div class="left02down01_img"></div>用户登录统计</a>
				</div>
				<div class="left02down01">
					<a href="#"><div class="left02down01_img"></div>用户激活统计</a>
				</div>
			</div>
		</div>
		<div class="left02">
			<div class="left02top">
				<div class="left02top_right"></div>
				<div class="left02top_left"></div>
				<div class="left02top_c">用户过滤</div>
			</div>
			<div class="left02down">
				<div class="left02down01">
					<a href="#"><div class="left02down01_img"></div>过滤IP(段)</a>
				</div>
				<div class="left02down01">
					<a href="#"><div class="left02down01_img"></div>过滤用户名</a>
				</div>
			</div>
		</div>
		<div class="left02">
			<div class="left02top">
				<div class="left02top_right"></div>
				<div class="left02top_left"></div>
				<div class="left02top_c">系统管理</div>
			</div>
			<div class="left02down">
				<div class="left02down01">
					<a href="#"><div class="left02down01_img"></div>权限管理</a>
				</div>
				<div class="left02down01">
					<a href="#"><div class="left02down01_img"></div>用户组管理</a>
				</div>
				<div class="left02down01">
					<a href="#"><div class="left02down01_img"></div>操作日志</a>
				</div>
			</div>
		</div>
		<div class="left01">
			<div class="left03_right"></div>
			<div class="left01_left"></div>
			<div class="left03_c">安全退出</div>
		</div>
	</div>
	<div class="rrcc" id="RightBox">
		<div class="center" id="Mobile" onclick="show_menuC()"></div>
		<div class="right" id="li010">
			<div class="right01">
				<img src="images/04.gif" /> 用户信息查询 &gt; <span>精确查询</span>
			</div>
		</div>

		<div class="right noneBox" id="li011">
			<div class="right01">
				<img src="images/04.gif" /> 用户信息查询 &gt; <span>组合条件查询</span>
			</div>
		</div>
		<div class="right noneBox" id="li012">
			<div class="right01">
				<img src="images/04.gif" /> 用户密码管理 &gt; <span>找回密码</span>
			</div>
		</div>
		<div class="right noneBox" id="li013">
			<div class="right01">
				<img src="images/04.gif" /> 用户密码管理 &gt; <span>更改密码</span>
			</div>
		</div>
	</div>
</body>
</html>
