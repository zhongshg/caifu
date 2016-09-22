<%-- 
    Document   : users
    Created on : 2014-7-9, 9:01:38
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>用户管理</title>
        <!--                       CSS                       -->
        <!-- Reset Stylesheet -->
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/public.js"></script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/basic.js"></script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/users.js"></script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/wdp/WdatePicker.js" charset="utf-8" defer="defer"></script>
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
                        <li><a class="shortcut-button" href="#"><span> <img src="${pageContext.servletContext.contextPath}/resources/images/icons/pencil_48.png" alt="icon" /><br />
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
                        <h3>用户管理</h3>
                        <ul class="content-box-tabs">
                            <!--<li><a href="#tab1">Table</a></li>-->
                            <!-- href must be unique and match the id of target div -->
                            <li><a href="#tab2" class="default-tab">Forms</a></li>
                        </ul>
                        <div class="clear"></div>
                    </div>
                    <!-- End .content-box-header -->
                    <div class="content-box-content">
                        <div class="tab-content default-tab" id="tab2">
                            <form action="${pageContext.servletContext.contextPath}/UsersServlet?method=manage" id="form1" method="post">
                                <input type="hidden" id="id" name="id" value="${users.id}"/>
                                <input type="hidden" id="img" name="img" value="${users.img}"/><%--图片路径--%>
                                <input type="hidden" name="page" value="${param.page}"/>
                                <input type="hidden" name="wxsidselect" value="${wxsidselect}"/>
                                <fieldset>
                                    <!-- Set class to "column-left" or "column-right" on fieldsets to divide the form into columns -->
                                    <p>
                                        <label>用户名</label>
                                        <input class="text-input large-input" type="text" id="username" name="username" value="${users.username}" />
                                        <span id="usernamespan" class="input-notification error png_bg"></span></p>
                                    <p>
                                        <label>姓名</label>
                                        <input class="text-input large-input datepicker" type="text" id="name" name="name" value="${users.name}"/>
                                        <span id="namespan" class="input-notification error png_bg"></span></p>
                                    <p>
                                        <label>身份证号</label>
                                        <input class="text-input large-input" type="text" id="identity" name="identity" value="${users.identity}"/>
                                        <span id="identityspan" class="input-notification error png_bg"></span>
                                    </p>
                                    <p>
                                        <label>性别</label>
                                        <input type="radio" name="sex" value="0" checked="checked"/>
                                        男&nbsp;
                                        <input type="radio" name="sex" value="1" ${"1"==users.sex?"checked='checked'":""}/>
                                        女 <span id="identityspan" class="input-notification success png_bg"></span></p>
                                    <p>
                                        <label>年龄</label>
                                        <input class="text-input large-input" type="text" id="ages" name="ages" value="${users.ages}"/>
                                        <span id="agesspan" class="input-notification error png_bg"></span></p>
                                    <p>
                                        <label>出生日期</label>
                                        <input type="text" id="birthday" name="birthday" value="${users.birthday}" onfocus="WdatePicker({skin: 'whyGreen', dateFmt: 'yyyy-MM-dd'})" class="Wdate"/>
                                    </p>
                                    <p>
                                        <label>图片上传</label>
                                        <iframe src="${pageContext.servletContext.contextPath}/public/upload.jsp?oldimg=${users.img}" class="text-input medium-input" width="300" height="26" frameborder="0" marginheight="0" marginwidth="0" scrolling="no"></iframe>
                                    </p>
                                    <p>
                                        <label>图片预览</label>
                                        <div id="result" name="result">
                                            <c:if test="${!empty users.img}">
                                                <img src='${pageContext.servletContext.contextPath}${users.img}' width='200' height='200'/>
                                            </c:if>
                                            <c:if test="${empty users.img}">
                                                请上传图片
                                            </c:if>
                                        </div>
                                    </p>
                                    <p>
                                        <label>角色</label>
                                        <select name="rid" class="large-input">
                                            <option value="0">请选择角色</option>
                                            <c:forEach items="${rolesList}" var="roles">
                                                <option value="${roles.id}" ${users.rid==roles.id?"selected='selected'":""}>${roles.name}</option>
                                            </c:forEach>
                                        </select>
                                    </p>
                                    <p>
                                        <label>微信</label>
                                        <select name="wxsid" class="large-input">
                                            <option value="0">请选择微信</option>
                                            <c:forEach items="${wxsList}" var="wxs">
                                                <option value="${wxs.id}" ${users.wxsid==wxs.id?"selected='selected'":""}>${wxs.name}</option>
                                            </c:forEach>
                                        </select>
                                    </p>
                                    <p>
                                        <label>备注</label>
                                        <textarea class="text-input" id="remark" name="remark" cols="79" rows="15">${users.remark}</textarea>
                                    </p>
                                    <p id="submit">
                                        <input class="button" id="testbutton" type="button" value="提交" />
                                    </p>
                                </fieldset>
                                <div class="clear"></div>
                                <!-- End .clear -->
                            </form>
                        </div>
                        <!-- End #tab2 -->
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