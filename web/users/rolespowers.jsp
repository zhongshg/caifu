<%-- 
    Document   : rolespowers
    Created on : 2014-7-16, 14:49:40
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>设置权限</title>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery.js"></script>
        <script type="text/javascript">
            function pclick(id) {
                document.getElementById("pid").value = id;
                document.getElementById("spid").value = "";
                document.getElementById("form1").submit();
            }
            function subclick(subid, id) {
                document.getElementById("pid").value = id;
                document.getElementById("spid").value = subid;
                document.getElementById("form1").submit();
            }
        </script>
        <!--                       CSS                       -->
        <!-- Reset Stylesheet -->
        <jsp:include page="../public/css.jsp"></jsp:include>
        </head>
        <body>
            <div id="body-wrapper">
                <div id="main-content">
                    <!-- Main Content Section with everything -->
                    <noscript>
                        <!-- Show a notification if the user has disabled javascript -->
                        <div class="notification error png_bg">
                            <div> Javascript is disabled or is not supported by your browser. Please <a href="http://browsehappy.com/" title="Upgrade to a better browser">upgrade</a> your browser or <a href="http://www.google.com/support/bin/answer.py?answer=23852" title="Enable Javascript in your browser">enable</a> Javascript to navigate the interface properly.
                                Download From <a href="http://www.exet.tk">exet.tk</a></div>
                        </div>
                    </noscript>
                    <!-- Page Head -->
                    <p id="page-intro">What would you like to do?</p>
                    <ul class="shortcut-buttons-set">
                        <li><a class="shortcut-button" href="#"><span> 
                                    <img src="${pageContext.servletContext.contextPath}/resources/images/icons/pencil_48.png" alt="icon" /><br />
                                &nbsp;</span></a></li>
                    <li><a class="shortcut-button" href="#"><span> <img src="${pageContext.servletContext.contextPath}/resources/images/icons/paper_content_pencil_48.png" alt="icon" /><br />
                                &nbsp;</span></a></li>
                    <li><a class="shortcut-button" href="#"><span> <img src="${pageContext.servletContext.contextPath}/resources/images/icons/image_add_48.png" alt="icon" /><br />
                                &nbsp;</span></a></li>
                    <li><a class="shortcut-button" href="#"><span> <img src="${pageContext.servletContext.contextPath}/resources/images/icons/clock_48.png" alt="icon" /><br />
                                &nbsp;</span></a></li>
                    <li><a class="shortcut-button" href="#"><span> <img src="${pageContext.servletContext.contextPath}/resources/images/icons/comment_48.png" alt="icon" /><br />
                                &nbsp;</span></a></li>
                </ul>
                <!-- End .shortcut-buttons-set -->
                <div class="clear"></div>
                <!-- End .clear -->
                <div class="content-box">
                    <!-- Start Content Box -->
                    <div class="content-box-header">
                        <h3>${roles.name}</h3>
                        <ul class="content-box-tabs">
                            <li><a href="#tab1" class="default-tab">Table</a></li>
                            <!-- href must be unique and match the id of target div -->
                            <!--<li><a href="#tab2">Forms</a></li>-->
                        </ul>
                        <div class="clear"></div>
                    </div>
                    <!-- End .content-box-header -->
                    <div class="content-box-content">
                        <form id="form1" action="${pageContext.servletContext.contextPath}/servlet/RolesPowersServlet?method=change" method="post" style="display: none;">
                            <input type="hidden" id="pid" name="pid"/>
                            <input type="hidden" id="spid" name="spid"/>
                            <input name="page" value="${param.page}"/>
                            <input type="submit"/>
                        </form>
                        <form id="form2" action="${pageContext.servletContext.contextPath}/servlet/RolesPowersServlet?method=manage" method="post">
                            <input type="hidden" name="page" value="${param.page}"/>
                            <div class="tab-content default-tab" id="tab1">
                                <!-- This is the target div. id must match the href of this div's tab -->
<!--                                <div class="notification attention png_bg"> <a href="#" class="close"><img src="${pageContext.servletContext.contextPath}/resources/images/icons/cross_grey_small.png" title="Close this notification" alt="close" /></a>
                                </div>-->
                                <table>
                                    <thead>
                                        <tr>
                                            <th width="10%">
                                                <input type="checkbox"/>
                                            </th>
                                            <th width="20%">权限名</th>
                                            <th width="20%">&nbsp;</th>
                                            <th width="50%">链接路径</th>
                                        </tr>
                                    </thead>
                                </table>
                                <c:forEach items="${sessionMap}" var="powersMap">
                                    <table>
                                        <thead>
                                            <tr>
                                                <td width="10%">
                                                    <input type="checkbox" ${"1"==powersMap.key.selects? "checked='checked'" : ""} onclick="javascript:pclick('${powersMap.key.id}');" />
                                                </td>
                                                <td width="20%"><b>${powersMap.key.name}</b></td>
                                                <td width="20%"><b>${powersMap.key.parentname}</b></td>
                                                <td width="50%"><b>${powersMap.key.url}</b></td>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${powersMap.value}" var="subPowersMap">
                                                <tr>
                                                    <td>
                                                        <input type="checkbox" ${"1"==subPowersMap.selects ? "checked='checked'" : ""} onclick="javascript:subclick('${subPowersMap.id}', '${powersMap.key.id}')"/>
                                                    </td>
                                                    <td>${subPowersMap.name}</td>
                                                    <td>${subPowersMap.parentname}</td>
                                                    <td>${subPowersMap.url}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:forEach>
                            </div>
                            <p>
                                <input class="button" type="submit" value="确认" />
                            </p>
                        </form>
                        <!-- End #tab1 -->
                    </div>
                    <!-- End .content-box-content -->
                </div>
                <!-- End .content-box -->

                <div class="clear"></div>
                <!-- Start Notifications -->
<!--                <div class="notification attention png_bg"> <a href="#" class="close"><img src="${pageContext.servletContext.contextPath}/resources/images/icons/cross_grey_small.png" title="Close this notification" alt="close" /></a>
                    <div> Attention notification. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin vulputate, sapien quis fermentum luctus, libero. </div>
                </div>-->
                <!-- End Notifications -->
                <%@include file="../public/foot.jsp" %>
                <!-- End #footer -->
            </div>
            <!-- End #main-content -->
        </div>
    </body>
    <!-- Download From www.exet.tk-->
</html>