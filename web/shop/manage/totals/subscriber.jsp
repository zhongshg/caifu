<%-- 
    Document   : subscriber
    Created on : 2014-7-21, 16:45:55
    Author     : Administrator
--%>

<%@page import="wap.wx.menu.WxMenuUtils"%>
<%@page import="job.tot.dao.DaoFactory"%>
<%@page import="wap.wx.util.ExportExcel"%>
<%@page import="wap.wx.service.SubscriberService"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="wap.wx.dao.WxsDAO"%>
<%@page import="wap.wx.util.DbConn"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="job.tot.util.RequestUtil"%>
<%@page import="wap.wx.dao.SubscriberDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <%
        if ("export".equals(request.getParameter("act"))) {
            SubscriberDAO subscriberDAO = new SubscriberDAO();
            String wxsid = request.getParameter("wxsid");
            Map<String, String> wx = new HashMap<String, String>();
            wx.put("id", wxsid);
            wx = new WxsDAO().getById(wx);
            String sign = RequestUtil.getString(request, "sign");
            String ids = RequestUtil.getString(request, "ids");
            String id = RequestUtil.getString(request, "id");
            String openid = RequestUtil.getString(request, "openid");
            String isvip = RequestUtil.getString(request, "isvip");
            long nonce = System.currentTimeMillis();
            String path = request.getServletContext().getRealPath("/upload/" + nonce + ".xls");
            String webpath = "/upload/" + nonce + ".xls";
            Map<String, Object[][]> map = new LinkedHashMap<String, Object[][]>();
            ArrayList<Map<String, String>> list;
            Connection conn = DbConn.getConn();
            if ("1".equals(sign)) {
                String[] objid = ids.split(",");
                list = new ArrayList();
                for (String tempid : objid) {
                    if ("".equals(tempid)) {
                        continue;
                    }
                    Map<String, String> subscriber = subscriberDAO.getById(wxsid, tempid);
                    float totalcanmoney = new SubscriberService().totalcanmoney(conn, subscriberDAO, subscriber, wx);
                    subscriber.put("totalcanmoney", new DecimalFormat("##0.00").format(totalcanmoney));
                    list.add(subscriber);
                }
            } else {
                list = (ArrayList) subscriberDAO.getList(Integer.parseInt(wxsid), id, openid, isvip);
                for (Map<String, String> subscriber : list) {
                    float totalcanmoney = new SubscriberService().totalcanmoney(conn, subscriberDAO, subscriber, wx);
                    subscriber.put("totalcanmoney", new DecimalFormat("##0.00").format(totalcanmoney));
                }
            }
            conn.close();
            Object[][] object = new Object[list.size() + 1][30];
            object[0][0] = "id";
            object[0][1] = "openid";
            object[0][2] = "昵称";
            object[0][3] = "性别";
            object[0][4] = "市";
            object[0][5] = "省";
            object[0][6] = "国";
            object[0][7] = "是否会员";
            object[0][8] = "关注日期";
            object[0][9] = "可提现金额";
            float price = 0.0f;
            int i = 0;
            for (Map<String, String> subscriber : list) {
                i++;
                String tempid = subscriber.get("id");
                object[i][0] = tempid;
                object[i][1] = subscriber.get("openid");
                object[i][2] = subscriber.get("nickname");
                object[i][3] = "1".equals(subscriber.get("sex")) ? "男" : "女";
                object[i][4] = subscriber.get("city");
                object[i][5] = subscriber.get("province");
                object[i][6] = subscriber.get("country");
                object[i][7] = "0".equals(subscriber.get("isvip")) ? "否" : "是";
                object[i][8] = subscriber.get("times");
                object[i][9] = subscriber.get("totalcanmoney");
            }
            map.put("会员表", object);
            boolean flag = ExportExcel.createExcel(path, map);
            if (flag) {
                DaoFactory.getExportlogDaoImplJDBC().add("会员表", webpath, WxMenuUtils.format.format(new java.util.Date(nonce)), wx.get("id"), Integer.parseInt(((Map<String, String>) request.getSession().getAttribute("users")).get("id")));
                out.println("<script>alert('导出成功！');window.history.go(-1);</script>");
            } else {
                out.println("<script>alert('导出失败！');window.history.go(-1);</script>");
            }
        }
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>已关注用户</title>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $("#exeall").click(function() {
                    var ids = "";
                    var dropdown = $("#dropdown").val();
                    if ("delall" == dropdown) {
                        if (confirm("不可恢复，确认？")) {
                            $("input[name=subscriberckb]:checked").each(function() {
                                ids += "," + $(this).val();
                            });
                        }
                    }
                    $("#ids").val(ids);
                    var form1 = document.getElementById("form1");
                    form1.action = "${pageContext.servletContext.contextPath}/servlet/SubscriberServlet?method=delTotalAll";
                    form1.submit();
                });
            });
            function isvip(openid, isvip) {
                if (confirm("不可撤销，确认？")) {
                    window.location.href = "${pageContext.servletContext.contextPath}/servlet/SubscriberServlet?method=isvipTotal&openid=" + openid + "&wxqueryid=${wxqueryid}&isvip=" + isvip + "&page=${pu.page}";
                }
            }
            function exportExcel(sign) {
                var box = document.getElementsByName("subscriberckb");
                var value = "";
                for (var i = 0; i < box.length; i++) {
                    if (box[i].checked) {
                        value = value + box[i].value + ",";
                    }
                }
                if ("1" == sign && value == "") {
                    alert("请选择要操作的选项!");
                    return false;
                } else {
                    if (confirm("确认导出？")) {
                        document.getElementById("ids").value = value;
                        var form1 = document.getElementById("form1");
                        form1.action = "${pageContext.servletContext.contextPath}/back/subscriber/subscriber.jsp?act=export&wxsid=${wxqueryid}&sign=" + sign;
                        form1.submit();
                    }
                }
            }
        </script>
        <!--                       CSS                       -->
        <!-- Reset Stylesheet -->
        <jsp:include page="/public/css.jsp"></jsp:include>
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
                        <h3>已关注用户</h3>
                        <ul class="content-box-tabs">
                            <li><a href="#tab1" class="default-tab">Table</a></li>
                            <!-- href must be unique and match the id of target div -->
                            <!--<li><a href="#tab2">Forms</a></li>-->
                        </ul>
                        <div class="clear"></div>
                    </div>
                    <!-- End .content-box-header -->
                    <div class="content-box-content">
                        <form id="form1" method="post" action="${pageContext.servletContext.contextPath}/servlet/SubscriberServlet?method=getTotalList">
                            <input type="hidden" id="ids" name="ids"/>
                            微信：<select name="wxqueryid">
                                <option value="-1" ${"-1"==wxqueryid?"selected='selected'":""}>全部</option>
                                <c:forEach items="${mapList}" var="map">
                                    <option value="${map.id}" ${map.id==wxqueryid?"selected='selected'":""}>${map.name}</option>
                                </c:forEach>
                            </select>
                            id:<input type="text" id="id" name="id" value="${id}"/>
                            openid:<input type="text" id="openid" name="openid"  value="${openid}"/>
                            是否会员：<input type="radio" name="isvip" value="" checked="true"/>全部&nbsp;<input type="radio" name="isvip" value="0" ${"0"==isvip?"checked=\"true\"":""}/>否&nbsp;<input type="radio" name="isvip" value="1" ${"1"==isvip?"checked=\"true\"":""}/>是
                            <input type="submit" value="查询"/>
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
                                        <th>会员id</th>
                                        <th>openid</th>
                                        <th>昵称</th>
                                        <th>性别</th>
                                        <!--<th>语言</th>-->
                                        <th>市</th>
                                        <th>省</th>
                                        <th>国</th>
                                        <th>头像</th>
                                        <th>会员</th>
                                        <!--<th>unionid</th>-->
                                        <!--<th>积分</th>-->
                                        <th>备注</th>
                                        <th>关注日期</th>
                                        <!--<th>可提现金额</th>-->
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
                                                <a class="button" id="exeall1" href="javascript:void(0);" onClick="exportExcel('1');">导出</a> 
                                                <a class="button" id="exeall2" href="javascript:void(0);" onClick="exportExcel('0');">导出全部</a>
                                            </div>
                                            <div class="pagination"> 
                                                <!--<a href="#" title="First Page">&laquo; First</a><a href="#" title="Previous Page">&laquo; Previous</a> <a href="#" class="number" title="1">1</a> <a href="#" class="number" title="2">2</a> <a href="#" class="number current" title="3">3</a> <a href="#" class="number" title="4">4</a> <a href="#" title="Next Page">Next &raquo;</a><a href="#" title="Last Page">Last &raquo;</a>--> 
                                                ${pu.style2}
                                            </div>
                                            <!-- End .pagination -->
                                            <div class="clear"></div>
                                        </td>
                                    </tr>
                                </tfoot>
                                <tbody>
                                    <c:forEach items="${pu.list}" var="subscriber" varStatus="s">
                                        <tr>
                                            <td>
                                                <input type="checkbox" name="subscriberckb" value="${subscriber.id}"/>
                                            </td>
                                            <td>${subscriber.id}</td>
                                            <td>${subscriber.openid}</td>
                                            <td>${subscriber.nickname}</td>
                                            <td>${"1"==subscriber.sex?"男":("2"==subscriber.sex?"女":"")}</td>
                                            <!--<td>${subscriber.language}</td>-->
                                            <td>${subscriber.city}</td>
                                            <td>${subscriber.province}</td>
                                            <td>${subscriber.country}</td>
                                            <td><img src="${subscriber.headimgurl}" width="60px" height="40px"/></td>
                                            <td>${"1"==subscriber.isvip?"是":"否"}</td>
                                            <!--<td>${subscriber.unionid}</td>-->
                                            <!--<td>${subscriber.percent}</td>-->
                                            <td>${subscriber.remark}</td>
                                            <td>${subscriber.times}</td>
                                            <!--<td>{subscriber.totalcanmoney}</td>-->
                                            <td>
                                                <c:if test="${'1'==subscriber.isvip}">
                                                    <a href="javascript:isvip('${subscriber.openid}',0);" style="text-decoration: none;">取消会员</a>
                                                </c:if>
                                                <c:if test="${'0'==subscriber.isvip}">
                                                    <a href="javascript:isvip('${subscriber.openid}',1);" style="text-decoration: none;">成为会员</a>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
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
                <%@include file="/public/foot.jsp" %>
                <!-- End #footer -->
            </div>
            <!-- End #main-content -->
        </div>
    </body>
    <!-- Download From www.exet.tk-->
</html>