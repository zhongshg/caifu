<%-- 
    Document   : test
    Created on : 2015-7-30, 13:30:19
    Author     : Administrator
--%>

<%@page import="wap.wx.menu.WxMenuUtils"%>
<%@page import="job.tot.dao.DaoFactory"%>
<%@page import="java.util.ArrayList"%>
<%@page import="job.tot.bean.DataField"%>
<%@page import="wap.wx.dao.SubscriberDAO"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="job.tot.util.RequestUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv=”Expires” CONTENT=”0″>
        <meta http-equiv=”Cache-Control” CONTENT=”no-cache”>
        <meta http-equiv=”Pragma” CONTENT=”no-cache”>

        <title></title>
        <meta name="viewport"
              content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <link href="/Application/Tpl/App/shop/Public/Static/css/foods.css?t=555" rel="stylesheet"
              type="text/css">
        <script type="text/javascript" src="/Application/Tpl/App/shop/Public/Static/js/jquery.min.js"></script>
        <script type="text/javascript" src="/Application/Tpl/App/shop/Public/Static/js/wemall.js?14115"></script>
        <script type="text/javascript" src="/Application/Tpl/App/shop/Public/Static/js/alert.js"></script>

        <style>
            .bg_color{background-color: #fff }
            .submit{background-color: #fff }
            .submit:active {background-color: #fff}
            .menu_topbar {background-color: #fff ;background: -webkit-linear-gradient(top, #fff , #fff);border-bottom: 1px solid #fff;}
            .footermenu ul {border-top: 1px solid #fff ;background-color: #fff;background: -webkit-linear-gradient(top, #fff, #fff);}
            .footermenu ul li a.active {background: -webkit-linear-gradient(top, #fff , #fff);}
            .bg_total{background-color:#FF0000}
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
    <%
        String F_No = RequestUtil.getString(request, "F_No");
        String wxsid = RequestUtil.getString(request, "wxsid");
        String openid = RequestUtil.getString(request, "openid");
        DataField order = DaoFactory.getOrderDAO().get(F_No);
    %>
    <body class="sanckbg mode_webapp">
        <div id="user-container" >

            <div class="menu_header">
                <div class="menu_topbar">
                    <div id="menu" class="sort ">
                        <a href="">查看我的订单</a>
                    </div>
                </div>
            </div>

            <div>
                <ul class="round" style="margin:0;padding:0;border-radius:0;border:0px;border-bottom:1px solid #C6C6C6">
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="cpbiaoge">
                        <tr>
                            <td> <span>订单详情</span> <span style='float:right'></span> </td>
                        </tr>
                    </table>
                </ul>
            </div>

            <div class="cardexplain">
                <div id="page_tag_load" hidefocus="true"
                     style="display: none; z-index: 10;">
                    <div class="btn-group"
                         style="position: fixed; font-size: 12px; width: 220px; bottom: 80px; left: 50%; margin-left: -110px; z-index: 999;">
                        <div class="del" style="font-size: 14px;">
                            <img src="/Application/Tpl/App/shop/Public/Static/images/ajax-loader.gif" alt="loader">正在获取订单...
                        </div>
                    </div>
                </div>
                <ul class="round"  id="orderlistinsert" style='color:#000;font-size:12px;'>
                    <!--插入订单ul-->

                    <li style="border: 1px solid #d0d0d0;border-radius: 10px;margin-bottom:10px;background-color:#FFF;">
                        <table><tbody>
                                <tr><td style="border-bottom:0px">订单编号:<%=order.getFieldValue("F_No")%></td></tr>
                                <tr><td style="border-bottom:0px">订单金额:<%=order.getFieldValue("SF_Price")%></td></tr>
                                <tr><td style="border-bottom:0px">订单时间:<%=order.getFieldValue("F_Date")%></td></tr>
                                <tr><td style="border-bottom:0px">支付状态:
                                        <!--                                        <em class="no">-->
                                        <%="1".equals(order.getFieldValue("IsPay")) ? "<font color='red'>未支付</font>" : ("2".equals(order.getFieldValue("IsPay")) ? "已支付" : ("3".equals(order.getFieldValue("IsPay")) ? "货到付款" : "未知"))%>
                                        <!--</em>-->
                                        <!--<a href="http://zbt.winbz.com/index.php?g=App&amp;m=Index&amp;a=pay&amp;totalprice=380.00&amp;cart_name=圆领三粒扣短袖14M1014&amp;uid=103369&amp;orderid=201507301501351033696">(已经支付?)</a></td>-->
                                </tr>
                                <tr><td style="border-bottom:0px">订单状态:
                                        <!--<em class="no">-->
                                        <%
                                            //订单状态：1未发货2已发货3确认收货4未评价5已评价6退货中7已退货
                                            if ("1".equals(order.getFieldValue("Sts"))) {
                                                out.print("未发货");
                                            } else if ("2".equals(order.getFieldValue("Sts"))) {
                                                out.print("已发货");
                                            } else if ("3".equals(order.getFieldValue("Sts"))) {
                                                out.print("已收货");
//                                    } else if ("4".equals(order.getFieldValue("Sts"))) {
//                                        out.print("未评价");
                                            } else if ("5".equals(order.getFieldValue("Sts"))) {
                                                out.print("已完成");
                                            } else if ("6".equals(order.getFieldValue("Sts"))) {
                                                out.print("退货中");
                                            } else if ("7".equals(order.getFieldValue("Sts"))) {
                                                out.print("已退货");
                                            } else {
                                                out.print("未支付");
                                            }%>
                                        <!--</em>-->
                                    </td></tr>
                                    <%
                                        List<DataField> orderdetailList = (List<DataField>) DaoFactory.getBasketDAO().getBySuserIdUidNoList(wxsid, openid, order.getFieldValue("F_No"));
                                        for (DataField orderdetail : orderdetailList) {
                                    %>
                                <tr><td style="border-bottom:0px">商品编号:<%=orderdetail.getFieldValue("Pcode")%></td></tr>
                                <tr><td style="border-bottom:0px">商品名称:<%=orderdetail.getFieldValue("Pname")%></td></tr>
                                <tr><td style="border-bottom:0px">商品单价:<%=orderdetail.getFieldValue("Per_Price")%></td></tr>
                                <tr><td style="border-bottom:0px">购买数量:<%=orderdetail.getFieldValue("Pnum")%></td></tr>
                                <tr><td style="border-bottom:0px">商品总价:<%=orderdetail.getFieldValue("Tot_Price")%></td></tr>
                                <%
                                    }
                                %>
<!--                                <tr><td style="border-bottom:0px">联系人:<%=order.getFieldValue("Name")%></td></tr>
                                <tr><td style="border-bottom:0px">联系电话:<%=order.getFieldValue("Phone")%></td></tr>
                                <tr><td style="border-bottom:0px">联系地址:<%=order.getFieldValue("Address")%></td></tr>-->
                                <%
                                    if ("2".equals(order.getFieldValue("Sts"))) {
                                %>
<!--                                <tr><td style="border-bottom:0px">快递公司:<%=order.getFieldValue("ShipName")%></td></tr>
                                <tr><td style="border-bottom:0px">物流单号:<%=order.getFieldValue("ShipNo")%></td></tr>
                                <tr><td style="border-bottom:0px">发货时间:<%=order.getFieldValue("ShipTime")%></td></tr>-->
                                <%
                                    }
                                %>
                            </tbody></table>
                    </li>
                </ul>
            </div>
        </div>
    </body>
</html>
