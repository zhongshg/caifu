<%-- 
    Document   : left
    Created on : 2014-7-7, 11:50:18
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title></title>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/system.js"></script>
        <!--                       CSS                       -->
        <!-- Reset Stylesheet -->
        <jsp:include page="../public/css.jsp"></jsp:include>
        </head>
        <body>
            <div id="body-wrapper">
                <!-- Wrapper for the radial gradient background -->
                <div id="sidebar">
                    <div id="sidebar-wrapper">
                        <!-- Sidebar with logo and menu -->
                        <h1 id="sidebar-title"><a href="#">微营销</a></h1>
                        <!-- Logo (221px wide) -->
                        <!--<a href="http://www.tl-kj.com/" target="_black"><img id="logo" src="${pageContext.servletContext.contextPath}/img/logo.png" width="100%"/></a>-->
                    <!-- Sidebar Profile links -->
                    <p style="margin-top: 30px;">&nbsp;</p>
                    <div id="profile-links"> 欢迎您, <a href="#" title="Edit your profile">${users.username}</a><br/>这是您第 ${count} 次登录<br/><br/>
                        <a onclick="history.forward();" title="前进">前进</a> | <a onclick="history.back();" title="后退">后退</a> | <a href="${pageContext.servletContext.contextPath}/UsersServlet?method=back" title="退出">退出</a> | <a href="${pageContext.servletContext.contextPath}/UsersServlet?method=getMain" target="mainFrame">返回首页</a>  </div>
                    <ul id="main-nav">
                        <!-- Accordion Menu -->
                        <!--                        <li> <a href="#" onclick="window.open('http://www.tl-kj.com/')" class="nav-top-item no-submenu">
                                                         Add the class "no-submenu" to menu items with no sub menu 
                                                        天澜科技 
                                                    </a> 
                                                </li>-->
                        <c:forEach items="${powersPS}" var="powersPSMap">
                            <li>
                                <a href="#" class="nav-top-item" id="${powersPSMap.key.id}" name="menu" onclick="javascript:checkscss('${powersPSMap.key.id}')">
                                    <!-- Add the class "current" to current menu item -->
                                    ${powersPSMap.key.name}
                                </a>
                                <ul>
                                    <c:forEach items="${powersPSMap.value}" var="subPowersPSMap">
                                        <li><a href="${pageContext.servletContext.contextPath}${subPowersPSMap.url}" id="${subPowersPSMap.id}" target="mainFrame">${subPowersPSMap.name}</a></li>
                                        </c:forEach>
                                    <!--                                    <li><a href="#" target="mainFrame">Write a new Article</a></li>
                                                                        <li><a class="current" href="#" target="mainFrame">Manage Articles</a></li>
                                                                         Add class "current" to sub menu items also 
                                                                        <li><a href="#">Manage Comments</a></li>
                                                                        <li><a href="#">Manage Categories</a></li>-->
                                </ul>
                            </li>
                        </c:forEach>
<!--                        <li> <a href="#" onclick="window.open('${pageContext.request.scheme}://${pageContext.request.serverName}${pageContext.servletContext.contextPath}/shop/manage')" class="nav-top-item current">
                                微商城后台 </a>
                        </li>-->
                        <!--
                <li> <a href="#" class="nav-top-item"> Pages </a>
                    <ul>
                        <li><a href="#">Create a new Page</a></li>
                        <li><a href="#">Manage Pages</a></li>
                    </ul>
                </li>
                <li> <a href="#" class="nav-top-item"> Image Gallery </a>
                    <ul>
                        <li><a href="#">Upload Images</a></li>
                        <li><a href="#">Manage Galleries</a></li>
                        <li><a href="#">Manage Albums</a></li>
                        <li><a href="#">Gallery Settings</a></li>
                    </ul>
                </li>
                <li> <a href="#" class="nav-top-item"> Events Calendar </a>
                    <ul>
                        <li><a href="#">Calendar Overview</a></li>
                        <li><a href="#">Add a new Event</a></li>
                        <li><a href="#">Calendar Settings</a></li>
                    </ul>
                </li>
                <li> <a href="#" class="nav-top-item"> Settings </a>
                    <ul>
                        <li><a href="#">General</a></li>
                        <li><a href="#">Design</a></li>
                        <li><a href="#">Your Profile</a></li>
                        <li><a href="#">Users and Permissions</a></li>
                    </ul>
                </li>-->
                    </ul>
                    <!-- End #main-nav -->
                    <div id="messages" style="display: none">
                        <!-- Messages are shown when a link with these attributes are clicked: href="#messages" rel="modal"  -->
                        <h3>3 Messages</h3>
                        <p> <strong>17th May 2009</strong> by Admin<br />
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus magna. Cras in mi at felis aliquet congue. <small><a href="#" class="remove-link" title="Remove message">Remove</a></small> </p>
                        <p> <strong>2nd May 2009</strong> by Jane Doe<br />
                            Ut a est eget ligula molestie gravida. Curabitur massa. Donec eleifend, libero at sagittis mollis, tellus est malesuada tellus, at luctus turpis elit sit amet quam. Vivamus pretium ornare est. <small><a href="#" class="remove-link" title="Remove message">Remove</a></small> </p>
                        <p> <strong>25th April 2009</strong> by Admin<br />
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus magna. Cras in mi at felis aliquet congue. <small><a href="#" class="remove-link" title="Remove message">Remove</a></small> </p>
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
                                </select>
                                <input class="button" type="submit" value="Send" />
                            </fieldset>
                        </form>
                    </div>
                    <!-- End #messages -->
                </div>
            </div>
            <!-- End #sidebar -->
        </div>
    </body>
    <!-- Download From www.exet.tk-->
</html>