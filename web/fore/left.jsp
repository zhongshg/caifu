<%-- 
    Document   : index
    Created on : 2014-7-7, 9:30:55
    Author     : Administrator
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"
	errorPage="error.jsp"%>
<%@ include file="common.jsp"%>
<%
    Map<Map<String, String>, List<Map<String, String>>> powersPS = 
	    (Map<Map<String, String>, List<Map<String, String>>>) request.getAttribute("powersPS");
    if (powersPS == null) {
		String roleid_str = (String)session.getAttribute("user_roleid") ;
		int roleid = Integer.parseInt( roleid_str == null? "-1" : roleid_str);
		powersPS = new LinkedHashMap<Map<String, String>, List<Map<String, String>>>();
		Map<String, String> roles = DaoFactory.getRolesDao().getByUsers(roleid);
		Map<String, String> powers = new LinkedHashMap<String, String>();
		powers.put("id", "999");//根节点
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
					<c:forEach items="${powersPS}" var="powersPSMap">
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
