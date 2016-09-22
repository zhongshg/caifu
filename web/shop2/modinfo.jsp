<%-- 
    Document   : vip
    Created on : 2015-7-30, 9:42:56
    Author     : Administrator
--%>

<%@page import="wap.wx.service.SubscriberService"%>
<%@page import="wap.wx.util.DbConn"%>
<%@page import="wap.wx.dao.NewstypesDAO"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="job.tot.bean.DataField"%>
<%@page import="job.tot.dao.DaoFactory"%>
<%@page import="java.util.List"%>
<%@page import="wap.wx.dao.WxsDAO"%>
<%@page import="wap.wx.dao.SubscriberDAO"%>
<%@page import="wap.wx.menu.WxMenuUtils"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="job.tot.util.RequestUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../shop/inc/common.jsp"%>
<!DOCTYPE html>
<html>
    <%        if (null != RequestUtil.getString(request, "wx")) {
            wxsid = RequestUtil.getString(request, "wx");
            session.setAttribute("wxsid", wxsid);
        }
        if (null != RequestUtil.getString(request, "openid")) {
            //此处可嵌入网页获取openid代码，也可通过图文信息获得
            openid = RequestUtil.getString(request, "openid");
            session.setAttribute("openid", openid);
        }

        SubscriberDAO subscriberDAO = new SubscriberDAO();

        //取出微信信息
        Map<String, String> wx = new HashMap<String, String>();
        wx.put("id", wxsid);
        wx = new WxsDAO().getById(wx);

        //取出用户信息
        Map<String, String> subscriber = subscriberDAO.getByOpenid(wxsid, openid);
//为空时存入
        if (null == subscriber.get("id")) {
            new SubscriberService().addSubscriber(wx, openid);
        }

    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv=”Expires” CONTENT=”0″>
        <meta http-equiv=”Cache-Control” CONTENT=”no-cache”>
        <meta http-equiv=”Pragma” CONTENT=”no-cache”>

        <title><%=wx.get("name")%></title>
        <meta name="viewport"
              content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <link href="/Application/Tpl/App/shop/Public/Static/css/foods.css?t=555" rel="stylesheet"
              type="text/css">
        <script type="text/javascript" src="/Application/Tpl/App/shop/Public/Static/js/jquery.min.js"></script>
        <script type="text/javascript" src="/Application/Tpl/App/shop/Public/Static/js/wemall.js?14115"></script>
        <script type="text/javascript" src="/Application/Tpl/App/shop/Public/Static/js/alert.js"></script>

        <style>
            .bg_color{background-color: <%=wx.get("weishopcolor")%> }
            .submit{background-color: <%=wx.get("weishopcolor")%> }
            .submit:active {background-color: <%=wx.get("weishopcolor")%>}
            .menu_topbar {background-color: <%=wx.get("weishopcolor")%> ;background: -webkit-linear-gradient(top, <%=wx.get("weishopcolor")%> , <%=wx.get("weishopcolor")%>);border-bottom: 1px solid <%=wx.get("weishopcolor")%>;}
            .footermenu ul {border-top: 1px solid <%=wx.get("weishopcolor")%> ;background-color: <%=wx.get("weishopcolor")%>;background: -webkit-linear-gradient(top, <%=wx.get("weishopcolor")%>, <%=wx.get("weishopcolor")%>);}
            .footermenu ul li a.active {background: -webkit-linear-gradient(top, <%=wx.get("weishopcolor")%> , <%=wx.get("weishopcolor")%>);}
            .bg_total{background-color:#FF0000}
            .textcolor{
                color: <%=wx.get("weishoptextcolor")%>;
            }
        </style>

        <script type="text/javascript">
            var appurl = 'shop2/vip.jsp';
            var rooturl = '';

            $(function() {
                $("#all_cnt").click(function() {
                    $(".member_cnt").toggle();
                    if ($(this).find('img').attr("src") == "/Application/Tpl/App/shop/Public/Static/images/arrow_unclick.png")
                    {
                        $(this).find('img').attr("src", "/Application/Tpl/App/shop/Public/Static/images/arrow_click.png");
                    }
                    else
                    {
                        $(this).find('img').attr("src", "/Application/Tpl/App/shop/Public/Static/images/arrow_unclick.png");
                    }
                });

                $("#all_price").click(function() {
                    $(".price_cnt").toggle();
                    if ($(this).find('img').attr("src") == "/Application/Tpl/App/shop/Public/Static/images/arrow_unclick.png")
                    {
                        $(this).find('img').attr("src", "/Application/Tpl/App/shop/Public/Static/images/arrow_click.png");
                    }
                    else
                    {
                        $(this).find('img').attr("src", "/Application/Tpl/App/shop/Public/Static/images/arrow_unclick.png");
                    }
                });

                $("#all_buy").click(function() {
                    $(".buy_cnt").toggle();
                    if ($(this).find('img').attr("src") == "/Application/Tpl/App/shop/Public/Static/images/arrow_unclick.png")
                    {
                        $(this).find('img').attr("src", "/Application/Tpl/App/shop/Public/Static/images/arrow_click.png");
                    }
                    else
                    {
                        $(this).find('img').attr("src", "/Application/Tpl/App/shop/Public/Static/images/arrow_unclick.png");
                    }
                });

                $("#memeber_url").click(function() {
                    $(".memeber_url").toggle();
                    if ($(this).find('img').attr("src") == "/Application/Tpl/App/shop/Public/Static/images/arrow_unclick.png")
                    {
                        $(this).find('img').attr("src", "/Application/Tpl/App/shop/Public/Static/images/arrow_click.png");
                    }
                    else
                    {
                        $(this).find('img').attr("src", "/Application/Tpl/App/shop/Public/Static/images/arrow_unclick.png");
                    }
                });

            });

        </script>
    </head>

    <body class="sanckbg mode_webapp">

        <div id="tx-container" style="">

            <div class="menu_header">
                <div class="menu_topbar">
                    <div id="menu" class="sort ">
                        <a href="">用户信息</a>
                    </div>
                </div>
            </div>

            <jsp:include page="top.jsp">
                <jsp:param name="wxsid" value="<%=wxsid%>"></jsp:param>
                <jsp:param name="openid" value="<%=openid%>"></jsp:param>
            </jsp:include>

            <!--            <div class="div_header">
                            <span style='float:left;margin-left:10px;margin-right:10px;'>
                                <img src='/Application/Tpl/App/shop/Public/Static/images/defult.jpg' width='70px;' height='70px;'>			</span>
                            <span class="header_right">
                                <div><span>昵称：temp</span></div>
                                <div><span>会员：否(<a style='color:red' href='./index.php?g=App&m=Index&a=index'>点击链接成为会员</a>)</span></div>
                                <div><span>关注时间：2015-07-30</span></div>
                                <div><span>会员ID：103369 </span></div>
                            </span>
                        </div>-->

            <section class="order">
                <form name="txinfoForm" id="txinfoForm" method="post" action="">
                    <input type="hidden" id="wxsid" value="<%=wxsid%>"/>
                    <input type="hidden" id="openid" value="<%=openid%>"/>
                    <div class="contact-info">
                        <ul>
                            <li class="title">用户信息</li>
                            <li>
                                <input type="hidden" id="submitflag" value="0"/>
                                <input type="hidden" id="img" name="headimgurl" value="<%=null != subscriber.get("headimgurl") ? subscriber.get("headimgurl") : ""%>"/>
                                <table style="padding: 0; margin: 0; width: 100%;">
                                    <tbody>

                                        <tr>
                                            <td width="80px"><label for="price" class="ui-input-text">用户名：</label></td>
                                            <td>
                                                <div class="ui-input-text">
                                                    <input id="username" name="username" placeholder="" placeholder="0.00" type="text"
                                                           class="ui-input-text" value="<%=subscriber.get("username")%>">
                                                </div></td>
                                        </tr>
                                        <tr>
                                            <td width="80px"><label for="price" class="ui-input-text">头像设置：</label></td>
                                            <td>
                                                <div class="ui-input-text" id="result" style="border: none;">
                                                    <img src='<%=null != subscriber.get("headimgurl") ? subscriber.get("headimgurl") : "/Application/Tpl/App/shop/Public/Static/images/defult.jpg"%>' width='50px;' height='50px;'>
                                                </div>
                                                <iframe src="${pageContext.servletContext.contextPath}/publicfore/uploadfore.jsp?oldimg=<%=subscriber.get("headimgurl")%>" class="text-input medium-input" width="100%" height="35" frameborder="0" marginheight="0" marginwidth="0" scrolling="no"></iframe>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>

                                <div class="footReturn">
                                    <a id="submitInfo" class="submit" href="javascript:void(0);">确定提交</a>
                                </div>

                            </li>
                        </ul>
                    </div>
                </form>
            </section>

            <!-- 正在提交数据 -->
            <div id="tx-menu-shadow" hidefocus="true"
                 style="display: none; z-index: 10;">
                <div class="btn-group"
                     style="position: fixed; font-size: 12px; width: 220px; bottom: 80px; left: 50%; margin-left: -110px; z-index: 999;">
                    <div class="del" style="font-size: 14px;">
                        <img src="/Application/Tpl/App/shop/Public/Static/images/ajax-loader.gif" alt="loader">正在提交申请...
                    </div>
                </div>
            </div>
            <!--
                        <ul class="round">
                            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="cpbiaoge">
                                <tr>
                                    <th>编号</th>
                                    <th class="cc">金额</th>
                                    <th class="cc">原因</th>
                                </tr>
            <%
                List<DataField> fundsList = (List<DataField>) DaoFactory.getFundsDao().getList(0, 0, "", openid, "", 0, wxsid, -1, -1);
                for (DataField df : fundsList) {
            %>
            <tr>
                <th><%=df.getFieldValue("F_No")%></th>
                <th class="cc"><%=WxMenuUtils.decimalFormat.format(df.getFloat("F_Price"))%></th>
                <th class="cc"><%="2".equals(df.getFieldValue("Type")) ? "人工提现" : ("1".equals(df.getFieldValue("Type")) ? "系统提现" : "余额不足")%></th>
            </tr>
            <%
                }
            %>
            <tbody>
            </tbody>
        </table>
    </ul>-->
        </div>
        <script>
            $(document).ready(function() {
                function updateInfo() {
                    var submitflag = $("#submitflag").val();
                    if ("1" == submitflag)
                        return false;
                    $(this).unbind("click");
                    var username = $("#username").val();
                    var headimgurl = $("#img").val();//"${pageContext.request.scheme}://${pageContext.request.serverName}${pageContext.servletContext.contextPath}" + 
                    if ("" != username || "" != headimgurl) {
                        $.post("${pageContext.servletContext.contextPath}/ForeServlet?method=updateInfo", {"wxsid": "<%=wxsid%>", "openid": "<%=openid%>", "username": username, "headimgurl": headimgurl}, function(result) {
                            if (result) {
                                result = eval("(" + result + ")");
                                alert("修改成功！");
                                window.setTimeout(function() {
                                    location.replace("${pageContext.servletContext.contextPath}/shop2/modinfo.jsp?wx=<%=wxsid%>&openid=<%=openid%>");
                                }, 3000);
                            }
                        });
                    } else {
                        alert("请填写用户名！");
                        $(this).bind("click", updateInfo);
                        return false;
                    }
                }
                $("#submitInfo").bind("click", updateInfo);
            });
        </script>
    </body>
</html>
