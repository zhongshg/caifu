<%-- 
    Document   : identity
    Created on : 2014-7-7, 11:40:11
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>忘记密码</title>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/public.js"></script>
        <!--                       CSS                       -->
        <!-- Reset Stylesheet -->
        <jsp:include page="../public/css.jsp"></jsp:include>
            <script type="text/javascript">
                function test() {
                    var username = document.getElementById("username").value;
                    var name = document.getElementById("name").value;
                    var identity = document.getElementById("identity").value;
                    if (5 > username.length || "" == name || !/^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/.test(identity)) {
                        return false;
                    }
                }
            </script>
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
                        <h3>请认真填写以下资料</h3>
                        <ul class="content-box-tabs">
                            <li><a href="#tab2" class="default-tab">Forms</a></li>
                        </ul>
                        <div class="clear"></div>
                    </div>
                    <!-- End .content-box-header -->
                    <div class="content-box-content">
                        <div class="tab-content default-tab" id="tab2">
                            <form action="${pageContext.servletContext.contextPath}/UsersServlet?method=checkIdentity" method="post" onsubmit="return test();">
                                <fieldset>
                                    <!-- Set class to "column-left" or "column-right" on fieldsets to divide the form into columns -->
                                    <p>
                                        <label>用户名</label>
                                        <input class="text-input large-input" type="text" id="username" name="username" />
                                        <span id="usernamespan" class="input-notification error png_bg"></span>
                                    </p>
                                    <p>
                                        <label>姓名</label>
                                        <input class="text-input large-input datepicker" type="text" id="name" name="name" />
                                        <span id="namespan" class="input-notification error png_bg"></span> </p>
                                    <p>
                                        <label>身份证号码</label>
                                        <input class="text-input large-input" type="text" id="identity" name="identity" />
                                        <span id="identityspan" class="input-notification error png_bg"></span>
                                    </p>
                                    <p>
                                        <input class="button" id="button" type="submit" value="提交" />
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
                <!--                <div class="content-box column-left">
                                    <div class="content-box-header">
                                        <h3>Content box left</h3>
                                    </div>
                                     End .content-box-header 
                                    <div class="content-box-content">
                                        <div class="tab-content default-tab">
                                            <h4>Maecenas dignissim</h4>
                                            <p> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in porta lectus. Maecenas dignissim enim quis ipsum mattis aliquet. Maecenas id velit et elit gravida bibendum. Duis nec rutrum lorem. Donec egestas metus a risus euismod ultricies. Maecenas lacinia orci at neque commodo commodo. </p>
                                        </div>
                                         End #tab3 
                                    </div>
                                     End .content-box-content 
                                </div>
                                 End .content-box 
                                <div class="content-box column-right closed-box">
                                    <div class="content-box-header">
                                         Add the class "closed" to the Content box header to have it closed by default 
                                        <h3>Content box right</h3>
                                    </div>
                                     End .content-box-header 
                                    <div class="content-box-content">
                                        <div class="tab-content default-tab">
                                            <h4>This box is closed by default</h4>
                                            <p> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in porta lectus. Maecenas dignissim enim quis ipsum mattis aliquet. Maecenas id velit et elit gravida bibendum. Duis nec rutrum lorem. Donec egestas metus a risus euismod ultricies. Maecenas lacinia orci at neque commodo commodo. </p>
                                        </div>
                                         End #tab3 
                                    </div>
                                     End .content-box-content 
                                </div>-->
                <!-- End .content-box -->
                <div class="clear"></div>
                <!-- Start Notifications -->
                <c:if test="${'1'==param.message}">
                    <div id="messages1" class="notification attention png_bg"> <a href="#" class="close"><img src="${pageContext.servletContext.contextPath}/resources/images/icons/cross_grey_small.png" title="Close this notification" alt="close" /></a>
                        <div>资料填写有误</div>
                    </div>
                </c:if>
                <div id="messages2" class="notification attention png_bg" style="display: none;"> <a href="#" class="close"><img src="${pageContext.servletContext.contextPath}/resources/images/icons/cross_grey_small.png" title="Close this notification" alt="close" /></a>
                    <div id='error'></div>
                </div>
                <!-- End Notifications -->
                <%@include file="../public/foot.jsp" %>
                <!-- End #footer -->
            </div>
            <!-- End #main-content -->
        </div>
    </body>
    <!-- Download From www.exet.tk-->
</html>