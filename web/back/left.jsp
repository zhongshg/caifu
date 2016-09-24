<%-- 
    Document   : index
    Created on : 2014-7-7, 9:30:55
    Author     : Administrator
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"
	errorPage="error.jsp"%>
<%@ include file="../inc/common.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    DataField df = (DataField) request.getSession().getAttribute("user");
    String act = RequestUtil.getString(request, "act");
    String mainJsp = RequestUtil.getString(request, "rjsp");
    if (df == null || (act != null && act.equals("exit"))) {
		session = request.getSession(false);//防止创建Session  
		if (session == null) {
		    response.sendRedirect("../login.jsp");
		    return;
		}
		session.removeAttribute("user");
		response.sendRedirect("../login.jsp");
    }
    String name = null;
    Map<Map<String, String>, List<Map<String, String>>> powersPS = (Map<Map<String, String>, List<Map<String, String>>>) request.getSession().getAttribute("powersPS");
    if (act != null && act.equals("login") && powersPS == null) {
		int roleid = -1;
		int id = -1;
		int rid = -1;
		if (df != null) {
		    name = df.getString("name");
		    roleid = Integer.parseInt(df.getFieldValue("roleid") == null ? "-1" : df.getFieldValue("roleid"));
		    id = Integer.parseInt(df.getFieldValue("id") == null ? "-1" : df.getFieldValue("id"));
		    rid = Integer.parseInt(df.getFieldValue("roleid") == null ? "-1" : df.getFieldValue("roleid"));
		}
		powersPS = new LinkedHashMap<Map<String, String>, List<Map<String, String>>>();
		Map<String, String> roles = DaoFactory.getRolesDao().getByUsers(roleid);
		Map<String, String> powers = new LinkedHashMap<String, String>();
		powers.put("id", "0");
		List<Map<String, String>> powersFirstList = DaoFactory.getPowersDao().getListByRP(roles, powers);
		for (Map<String, String> powersFirst : powersFirstList) {
		    List<Map<String, String>> powersSubList = DaoFactory.getPowersDao().getListByRP(roles, powersFirst);
		    powersPS.put(powersFirst, powersSubList);
		}
		request.getSession().setAttribute("powersPS", powersPS);
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>财富客户管理系统</title>
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/main.css" />
<script type="text/javascript" src="js/libs/modernizr.min.js"></script>
</head>

<body>
	<div class="container clearfix">
		<div class="sidebar-wrap">
			<div class="sidebar-title">
				<h1>菜单</h1>
			</div>
			<div class="sidebar-content">
				<ul class="sidebar-list">
					<c:forEach items="${sessionScope.powersPS}" var="powersPSMap">
						<li><a href="#"><i class="icon-font">&#xe003;</i>${powersPSMap.key.name}</a>
							<ul class="sub-menu">
							<c:forEach items="${powersPSMap.value}" var="subPowersPSMap">
								<li><a href="${subPowersPSMap.url}" target="mainFrame">
								<i class="icon-font">&#xe008;</i>${subPowersPSMap.name}</a></li>
									</c:forEach>
							</ul></li>
					</c:forEach>
				</ul>
			</div>
		</div>

		
			<div class="left02down01">
				<a onclick="show_menuB(${powersPSMap.key.id})" href="javascript:;"><div
						id="Bf0${powersPSMap.key.id}" class="left02down01_img"></div>${powersPSMap.key.name}</a>
			</div>

			<div class="left02down01_xia noneBox" id="Bli0${powersPSMap.key.id}">
				<ul>
					
						<li id="f0${subPowersPSMap.id}" class=''><a
							onclick="show_menu('${powersPSMap.key.name}',this.text)"
							href="${subPowersPSMap.url}" target="mainFrame">&middot;${subPowersPSMap.name}</a></li>
				
				</ul>
			</div>
		
</body>
</html>
