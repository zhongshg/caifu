<%-- 
    Document   : index
    Created on : 2014-7-7, 9:30:55
    Author     : Administrator
--%>

<%@page import="org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID"%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"
	errorPage="error.jsp"%>
<%@ include file="../inc/common.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    DataField df = (DataField) request.getSession().getAttribute("user");
    String act = RequestUtil.getString(request, "act");
    if (df == null || act.equals("exit")) {
		session = request.getSession(false);//防止创建Session  
		if (session == null) {
		    response.sendRedirect("../login.jsp");
		    return;
		}
		session.removeAttribute("user");
		response.sendRedirect("../login.jsp");
    }
    String name = null;
    if (act != null || act != "") {
		int roleid = -1;
		int id = -1;
		int rid = -1;
		if (df != null) {
		    name = df.getString("name");
		    roleid = Integer.parseInt(df.getFieldValue("roleid") == null ? "-1" : df.getFieldValue("roleid"));
		    id = Integer.parseInt(df.getFieldValue("id") == null ? "-1" : df.getFieldValue("id"));
		    rid = Integer.parseInt(df.getFieldValue("roleid") == null ? "-1" : df.getFieldValue("roleid"));
		}
		Map<Map<String, String>, List<Map<String, String>>> powersPS = new LinkedHashMap<Map<String, String>, List<Map<String, String>>>();
		Map<String, String> roles = DaoFactory.getRolesDao().getByUsers(roleid);
		Map<String, String> powers = new LinkedHashMap<String, String>();
		powers.put("id", "0");
		List<Map<String, String>> powersFirstList = DaoFactory.getPowersDao().getListByRP(roles, powers);
		for (Map<String, String> powersFirst : powersFirstList) {
		    List<Map<String, String>> powersSubList = DaoFactory.getPowersDao().getListByRP(roles, powersFirst);
		    powersPS.put(powersFirst, powersSubList);
		}
		request.setAttribute("powersPS", powersPS);
    }
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
			<div class="left01_c">
				欢迎您 <a href=""><%=name%></a>
			</div>
		</div>
		<div class="left02">
			<div class="left02top">
				<div class="left02top_right"></div>
				<div class="left02top_left"></div>
				<div class="left02top_c">管理菜单</div>
			</div>
			<div class="left02down">
				<c:forEach items="${powersPS}" var="powersPSMap">
					<div class="left02down01">
						<a onclick="show_menuB(${powersPSMap.key.id})" href="javascript:;"><div
								id="Bf0${powersPSMap.key.id}" class="left02down01_img"></div>${powersPSMap.key.name}</a>
					</div>

					<div class="left02down01_xia noneBox"
						id="Bli0${powersPSMap.key.id}">
						<ul>
							<c:forEach items="${powersPSMap.value}" var="subPowersPSMap">
								<li id="f0${subPowersPSMap.id}" class=''><a onclick="show_menu('${powersPSMap.key.name}',this.text)"
									href="${subPowersPSMap.url}">&middot;${subPowersPSMap.name}</a></li>
							</c:forEach>
						</ul>
					</div>
				</c:forEach>
			</div>

		</div>

		<!-- 左侧底部推出DIV -->
		<div class="left01">
			<div class="left03_right"></div>
			<div class="left01_left"></div>
			<div class="left03_c">
				<a href="?act=exit">安全退出</a>
			</div>
		</div>
		<!-- END  左侧底部推出DIV-->
	</div>

	<!-- 主页右侧 -->
	<div class="rrcc" id="RightBox">
		<div class="center" id="Mobile" onclick="show_menuC()"></div>
		<div class="right" id="li001">
			<div class="right01">
				<img src="images/04.gif" /><span id='module'></span>  <span id='func'></span>
			</div>
			<div class="right02"></div>
		</div>
	</div>
</body>
</html>
