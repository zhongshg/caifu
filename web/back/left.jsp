<%-- 
    Document   : index
    Created on : 2014-7-7, 9:30:55
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"
	errorPage="error.jsp"%>
<%@ include file="../inc/common.jsp"%>
<%@page import="wap.wx.util.Forward"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/js/system.js"></script>
<!--                       CSS                       -->
<!-- Reset Stylesheet -->
<jsp:include page="../public/css.jsp"></jsp:include>
</head>
<body>
	<%
	    DataField df = (DataField) request.getSession().getAttribute("user");
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
	<div id="body-wrapper">
		<div id="sidebar">
			<div id="sidebar-wrapper">
				<p style="margin-top: 30px;">&nbsp;</p>
				<div id="profile-links">
					欢迎您, <a href="#" title="Edit your profile"><%=name == null ? "" : name%></a>
				</div>
				<ul id="main-nav">
					<c:forEach items="${powersPS}" var="powersPSMap">
						<li><a href="#" class="nav-top-item"
							id="${powersPSMap.key.id}" name="menu"
							onclick="javascript:checkscss('${powersPSMap.key.id}')">
								${powersPSMap.key.name} </a>
							<ul>
								<c:forEach items="${powersPSMap.value}" var="subPowersPSMap">
									<li><a
										href="${pageContext.servletContext.contextPath}${subPowersPSMap.url}"
										id="${subPowersPSMap.id}" target="mainFrame">${subPowersPSMap.name}</a></li>
								</c:forEach>
							</ul></li>
					</c:forEach>
				</ul>
				<div id="messages" style="display: none">
					<!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
					<h3>3 Messages</h3>
					<p>
						<strong>17th May 2009</strong> by Admin<br /> Lorem ipsum dolor
						sit amet, consectetur adipiscing elit. Vivamus magna. Cras in mi
						at felis aliquet congue. <small><a href="#"
							class="remove-link" title="Remove message">Remove</a></small>
					</p>
					<p>
						<strong>2nd May 2009</strong> by Jane Doe<br /> Ut a est eget
						ligula molestie gravida. Curabitur massa. Donec eleifend, libero
						at sagittis mollis, tellus est malesuada tellus, at luctus turpis
						elit sit amet quam. Vivamus pretium ornare est. <small><a
							href="#" class="remove-link" title="Remove message">Remove</a></small>
					</p>
					<p>
						<strong>25th April 2009</strong> by Admin<br /> Lorem ipsum dolor
						sit amet, consectetur adipiscing elit. Vivamus magna. Cras in mi
						at felis aliquet congue. <small><a href="#"
							class="remove-link" title="Remove message">Remove</a></small>
					</p>
					<form action="#" method="post">
						<h4>New Message</h4>
						<fieldset>
							<textarea class="textarea" name="textfield" cols="79" rows="5"></textarea>
						</fieldset>
						<fieldset>
							<select name="dropdown" class="small-input">
								<option value="option1">Send to...</option>
								<option value="option2">Everyone</option>
								<option value="option3">Admin</option>
								<option value="option4">Jane Doe</option>
							</select> <input class="button" type="submit" value="Send" />
						</fieldset>
					</form>
				</div>
				<!-- End #messages -->
			</div>
		</div>
		<!-- End #sidebar -->
	</div>
</body>
</html>
