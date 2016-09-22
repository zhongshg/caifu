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
        <title></title>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $("#exeall").click(function() {
                    var ids = "";
                    var dropdown = $("#dropdown").val();
                    if ("delall" == dropdown) {
                        if (confirm("不可恢复，确认？")) {
                            $("input[name=rolessckb]:checked").each(function() {
                                ids += "," + $(this).val();
                            });
                        }
                    }
                    $("#ids").val(ids);
                    var form1 = document.getElementById("form1");
                    form1.action = "${pageContext.servletContext.contextPath}/servlet/RolesServlet?method=delAll";
                    form1.submit();
                });
            });
        </script>
        <!--                       CSS                       -->
        <!-- Reset Stylesheet -->
        <jsp:include page="../public/css.jsp"></jsp:include>
            <script type="text/javascript">
                function del(id) {
                    if (confirm("不可恢复,确认？")) {
                        window.location.href = "${pageContext.servletContext.contextPath}/servlet/RolesServlet?method=delete&id=" + id;
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
                    <li><a class="shortcut-button" href="${pageContext.servletContext.contextPath}/servlet/RolesServlet?method=initManage&id=0"><span> 
                                <img src="${pageContext.servletContext.contextPath}/resources/images/icons/pencil_48.png" alt="icon" /><br />
                                添加 </span></a></li>
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
                        <h3>角色管理</h3>
                        <ul class="content-box-tabs">
                            <li><a href="#tab1" class="default-tab">Table</a></li>
                            <!-- href must be unique and match the id of target div -->
                            <!--<li><a href="#tab2">Forms</a></li>-->
                        </ul>
                        <div class="clear"></div>
                    </div>
                    <!-- End .content-box-header -->
                    <div class="content-box-content">
                        <form id="form1" method="post">
                            <input type="hidden" id="ids" name="ids"/>
                        </form>
                        <div class="tab-content default-tab" id="tab1">
                            <!-- This is the target div. id must match the href of this div's tab -->
<!--                            <div class="notification attention png_bg"> <a href="#" class="close"><img src="${pageContext.servletContext.contextPath}/resources/images/icons/cross_grey_small.png" title="Close this notification" alt="close" /></a>
                            </div>-->
                            <table>
                                <thead>
                                    <tr>
                                        <th>
                                            <input class="check-all" type="checkbox" />
                                        </th>
                                        <th>角色名</th>
                                        <th>备注</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tfoot>
                                    <tr>
                                        <td colspan="15">
                                            <div class="bulk-actions align-left">
                                                <select id="dropdown">
                                                    <option value="delall">批量删除</option>
                                                </select>
                                                <a class="button" id="exeall" href="#">执行</a> &nbsp;
                                                总计：${pu.maxSize}
                                            </div>
                                            <div class="pagination"> 
                                                <!--<a href="#" title="First Page">&laquo; First</a><a href="#" title="Previous Page">&laquo; Previous</a> <a href="#" class="number" title="1">1</a> <a href="#" class="number" title="2">2</a> <a href="#" class="number current" title="3">3</a> <a href="#" class="number" title="4">4</a> <a href="#" title="Next Page">Next &raquo;</a><a href="#" title="Last Page">Last &raquo;</a>--> 
                                                ${pu.style}
                                            </div>
                                            <!-- End .pagination -->
                                            <div class="clear"></div>
                                        </td>
                                    </tr>
                                </tfoot>
                                <tbody>
                                    <c:forEach items="${pu.list}" var="roles">
                                        <tr>
                                            <td>
                                                <input type="checkbox" name="rolessckb" value="${roles.id}"/>
                                            </td>
                                            <td>${roles.name}</td>
                                            <td>${roles.remark}</td>
                                            <td>
                                                <!-- Icons -->
                                                <a href="${pageContext.servletContext.contextPath}/servlet/RolesServlet?method=initManage&id=${roles.id}&page=${pu.page}" title="Edit"><img src="${pageContext.servletContext.contextPath}/resources/images/icons/pencil.png" alt="Edit" />编辑</a> 
                                                <a href="javascript:del('${roles.id}');" title="Delete"><img src="${pageContext.servletContext.contextPath}/resources/images/icons/cross.png" alt="Delete" />删除</a> 
                                                <a href="${pageContext.servletContext.contextPath}/servlet/RolesPowersServlet?method=initManage&id=${roles.id}" title="Edit Meta"><img src="${pageContext.servletContext.contextPath}/resources/images/icons/hammer_screwdriver.png" alt="Edit Meta" />设置权限</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- End #tab1 -->
                        <div class="tab-content" id="tab2">
                            <form action="#" method="post">
                                <fieldset>
                                    <!-- Set class to "column-left" or "column-right" on fieldsets to divide the form into columns -->
                                    <p>
                                        <label>Small form input</label>
                                        <input class="text-input small-input" type="text" id="small-input" name="small-input" />
                                        <span class="input-notification success png_bg">Successful message</span>
                                        <!-- Classes for input-notification: success, error, information, attention -->
                                        <br />
                                        <small>A small description of the field</small> </p>
                                    <p>
                                        <label>Medium form input</label>
                                        <input class="text-input medium-input datepicker" type="text" id="medium-input" name="medium-input" />
                                        <span class="input-notification error png_bg">Error message</span> </p>
                                    <p>
                                        <label>Large form input</label>
                                        <input class="text-input large-input" type="text" id="large-input" name="large-input" />
                                    </p>
                                    <p>
                                        <label>Checkboxes</label>
                                        <input type="checkbox" name="checkbox1" />
                                        This is a checkbox
                                        <input type="checkbox" name="checkbox2" />
                                        And this is another checkbox </p>
                                    <p>
                                        <label>Radio buttons</label>
                                        <input type="radio" name="radio1" />
                                        This is a radio button<br />
                                        <input type="radio" name="radio2" />
                                        This is another radio button </p>
                                    <p>
                                        <label>This is a drop down list</label>
                                        <select name="dropdown" class="small-input">
                                            <option value="option1">Option 1</option>
                                            <option value="option2">Option 2</option>
                                            <option value="option3">Option 3</option>
                                            <option value="option4">Option 4</option>
                                        </select>
                                    </p>
                                    <p>
                                        <label>Textarea with WYSIWYG</label>
                                        <textarea class="text-input textarea wysiwyg" id="textarea" name="textfield" cols="79" rows="15"></textarea>
                                    </p>
                                    <p>
                                        <input class="button" type="submit" value="Submit" />
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