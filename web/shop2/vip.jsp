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
        String act = RequestUtil.getString(request, "act");

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
        //取出上家信息
        Map<String, String> parentsubscriber = subscriberDAO.getById(wxsid, subscriber.get("parentopenid"));
        if (null == parentsubscriber.get("id")) {
            parentsubscriber.put("recommend", wx.get("name"));
        } else {
            parentsubscriber.put("recommend", parentsubscriber.get("nickname"));
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
    <%
        /*
         * 此处需要取：
         * 1.各级掌柜数量及总数量
         * 2.各级掌柜单数及总单数
         * 3.不同状态单数佣金金额及总金额
         * 4.各级掌柜单数金额及总金额（待办）
         */
        int firstsubscribercount = 0;//大掌柜数量
        int orderfirstsubscribercount = 0;//有订单大掌柜数量
        int secondsubscribercount = 0;//二掌柜数量
        int ordersecondsubscribercount = 0;//有订单二掌柜数量
        int thirdsubscribercount = 0;//小掌柜数量
        int orderthirdsubscribercount = 0;//有订单小掌柜数量
        int firstordercount = 0;//大掌柜订单数量
        int secondordercount = 0;//二掌柜订单数量
        int thirdordercount = 0;//小掌柜订单数量
        int firstordernobuycount = 0;//大掌柜未购买订单数量
        int secondordernobuycount = 0;//二掌柜未购买订单数量
        int thirdordernobuycount = 0;//小掌柜未购买订单数量

        float totalordernobuymoney = 0;//未付款订单总额
        float totalordermoney = 0;//已付款订单总额

        float totalordernobuyYJ = 0;//未付款订单总佣金 0
        float totalorderYJ = 0;//已付款订单总佣金 1
        float totalorderalthingYJ = 0;//已收获订单总佣金 3
        float totalorderalendYJ = 0;//已完成订单总佣金 5

        Connection conn = DbConn.getConn();
        List<Map<String, String>> firstsubscriberList = subscriberDAO.getByParentopenidList(wxsid, subscriber.get("id"), conn);
        firstsubscribercount = firstsubscriberList.size();

        //准一级掌柜 公众号商家用户（商家用户限设一个）
        if (openid.equals(wx.get("adminsubscriber"))) {//是商家用户
            List<Map<String, String>> associatefirstsubscriberList = subscriberDAO.getByParentopenidList(wxsid, "0", conn);
            //删除自己内容
            List<Map<String, String>> tempassociatefirstsubscriberList = associatefirstsubscriberList;
            for (Map<String, String> associatefirstsubscriber : tempassociatefirstsubscriberList) {
                if (openid.equals(associatefirstsubscriber.get("openid"))) {
                    associatefirstsubscriberList.remove(associatefirstsubscriber);
                    break;
                }
            }
            firstsubscriberList.addAll(associatefirstsubscriberList);
            firstsubscribercount += associatefirstsubscriberList.size();
        }

        for (Map<String, String> firstsubscriber : firstsubscriberList) {
            //计算单数、金额
            List<DataField> firstorderList = (List<DataField>) DaoFactory.getOrderDAO().getList(wxsid, firstsubscriber.get("openid"), -1, conn);
            if (0 != firstorderList.size()) {
                orderfirstsubscribercount++;
            }
            for (DataField order : firstorderList) {
                switch (order.getInt("Sts")) {
                    case 0:
                        totalordernobuyYJ += order.getFloat("FirstYJ");
                        totalordernobuymoney += order.getFloat("SF_Price");
                        firstordernobuycount++;
                        break;
                    case 1:
                        totalorderYJ += order.getFloat("FirstYJ");
                        totalordermoney += order.getFloat("SF_Price");
                        firstordercount++;
                        break;
                    case 2://已发货视为已付款
                        totalorderYJ += order.getFloat("FirstYJ");
                        totalordermoney += order.getFloat("SF_Price");
                        firstordercount++;
                        break;
                    case 3:
                        totalorderalthingYJ += order.getFloat("FirstYJ");
                        totalordermoney += order.getFloat("SF_Price");
                        firstordercount++;
                        break;
                    case 5:
                        totalorderalendYJ += order.getFloat("FirstYJ");
                        totalordermoney += order.getFloat("SF_Price");
                        firstordercount++;
                        break;
                }
            }

            List<Map<String, String>> secondsubscriberList = subscriberDAO.getByParentopenidList(wxsid, firstsubscriber.get("id"), conn);
            secondsubscribercount += secondsubscriberList.size();
            for (Map<String, String> secondsubscriber : secondsubscriberList) {

                //计算单数、金额
                List<DataField> secondorderList = (List<DataField>) DaoFactory.getOrderDAO().getList(wxsid, secondsubscriber.get("openid"), -1, conn);
                if (0 != secondorderList.size()) {
                    ordersecondsubscribercount++;
                }
                for (DataField order : secondorderList) {
                    switch (order.getInt("Sts")) {
                        case 0:
                            totalordernobuyYJ += order.getFloat("SecondYJ");
                            totalordernobuymoney += order.getFloat("SF_Price");
                            secondordernobuycount++;
                            break;
                        case 1:
                            totalorderYJ += order.getFloat("SecondYJ");
                            totalordermoney += order.getFloat("SF_Price");
                            secondordercount++;
                            break;
                        case 2://已发货视为已付款
                            totalorderYJ += order.getFloat("SecondYJ");
                            totalordermoney += order.getFloat("SF_Price");
                            secondordercount++;
                            break;
                        case 3:
                            totalorderalthingYJ += order.getFloat("SecondYJ");
                            totalordermoney += order.getFloat("SF_Price");
                            secondordercount++;
                            break;
                        case 5:
                            totalorderalendYJ += order.getFloat("SecondYJ");
                            totalordermoney += order.getFloat("SF_Price");
                            secondordercount++;
                            break;
                    }
                }

                List<Map<String, String>> thirdsubscriberList = subscriberDAO.getByParentopenidList(wxsid, secondsubscriber.get("id"), conn);
                thirdsubscribercount += thirdsubscriberList.size();

                for (Map<String, String> thirdsubscriber : thirdsubscriberList) {

                    //计算单数、金额
                    List<DataField> thirdorderList = (List<DataField>) DaoFactory.getOrderDAO().getList(wxsid, thirdsubscriber.get("openid"), -1, conn);
                    if (0 != thirdorderList.size()) {
                        orderthirdsubscribercount++;
                    }
                    for (DataField order : thirdorderList) {
                        switch (order.getInt("Sts")) {
                            case 0:
                                totalordernobuyYJ += order.getFloat("ThirdYJ");
                                totalordernobuymoney += order.getFloat("SF_Price");
                                thirdordernobuycount++;
                                break;
                            case 1:
                                totalorderYJ += order.getFloat("ThirdYJ");
                                totalordermoney += order.getFloat("SF_Price");
                                thirdordercount++;
                                break;
                            case 2://已发货视为已付款
                                totalorderYJ += order.getFloat("ThirdYJ");
                                totalordermoney += order.getFloat("SF_Price");
                                thirdordercount++;
                                break;
                            case 3:
                                totalorderalthingYJ += order.getFloat("ThirdYJ");
                                totalordermoney += order.getFloat("SF_Price");
                                thirdordercount++;
                                break;
                            case 5:
                                totalorderalendYJ += order.getFloat("ThirdYJ");
                                totalordermoney += order.getFloat("SF_Price");
                                thirdordercount++;
                                break;
                        }
                    }
                }
            }
        }
        conn.close();
        int totalsubscribercount = firstsubscribercount + secondsubscribercount + thirdsubscribercount;
        int ordertotalsubscribercount = orderfirstsubscribercount + ordersecondsubscribercount + orderthirdsubscribercount;
        int totalordercount = firstordercount + secondordercount + thirdordercount;
        int totalordernobuycount = firstordernobuycount + secondordernobuycount + thirdordernobuycount;

        //已提现财富(含申请)
        float totaltx = 0;
        List<DataField> txList = (List<DataField>) DaoFactory.getFundsDao().getList(0, 0, "", openid, "", -1, wxsid, -1, -1);
        for (DataField tx : txList) {
            totaltx += tx.getFloat("F_Price");
        }
    %>
    <body class="sanckbg mode_webapp">
        <%
            if (null == act || "".equals(act)) {
        %>
        <div id="member-container" >
            <div class="menu_header">
                <div class="menu_topbar">
                    <div id="menu" class="sort ">
                        <a href="">分销详情</a>
                    </div>
                </div>
            </div>

            <jsp:include page="top.jsp">
                <jsp:param name="wxsid" value="<%=wxsid%>"></jsp:param>
                <jsp:param name="openid" value="<%=openid%>"></jsp:param>
            </jsp:include>

            <div class="div_table">
                <table class="bg_color"  style='height:35px;text-align:center;border:1px solid #ccc' border=0>
                    <tr style="border:0px"  border=0><td class="bg_color">销售额：<%=WxMenuUtils.decimalFormat.format(totalordernobuymoney + totalordermoney)%>元</td><td class="bg_color" style="border-left:1px solid #ccc;">我的佣金：<%=WxMenuUtils.decimalFormat.format(totalordernobuyYJ + totalorderYJ + totalorderalthingYJ + totalorderalendYJ + Float.parseFloat(subscriber.get("areaproxymoney")) + Float.parseFloat(subscriber.get("callbackmoney")))%>元</td></tr>
                </table>
            </div>

            <div class="cardexplain" style="TEXT-ALIGN: center;color:#000;font-size:14px;">您是由【<%=parentsubscriber.get("recommend")%>】推荐</div>

            <div class="cardexplain">
                <div class="div_ul" id="all_cnt" ><span><img style='margin-left:5px;' src="/Application/Tpl/App/shop/Public/Static/images/arrow_unclick.png"><%=wx.get("title1")%></span><span class="bg_total"><%=totalsubscribercount%>(<%=ordertotalsubscribercount%>)  人</span></div>
                        <%
                            if ("0".equals(wx.get("isshowsubscriber"))) {
                        %>
                <ul class="round">
                    <li class="member_cnt" style=""><a href="zhanggui.jsp?type=1&wxsid=<%=wxsid%>&openid=<%=openid%>"><span><img style="width:20px;height:20px;" src="/Application/Tpl/App/shop/Public/Static/images/bullet_blue_expand.png"><%=wx.get("subtitle1")%><span style="float:right;color:red;"><%=firstsubscribercount%>(<%=orderfirstsubscribercount%>) </span></span></a></li>
                    <li class="member_cnt"><a href="zhanggui.jsp?type=2&wxsid=<%=wxsid%>&openid=<%=openid%>"><span><img style="width:20px;height:20px;"  src="/Application/Tpl/App/shop/Public/Static/images/bullet_blue_expand.png"><%=wx.get("subtitle2")%><span style="float:right;color:red;"><%=secondsubscribercount%>(<%=ordersecondsubscribercount%>)  </span></span></a></li>				
                    <li class="member_cnt"><a href="zhanggui.jsp?type=3&wxsid=<%=wxsid%>&openid=<%=openid%>"><span><img style="width:20px;height:20px;" src="/Application/Tpl/App/shop/Public/Static/images/bullet_blue_expand.png"><%=wx.get("subtitle3")%><span style="float:right;color:red;"><%=thirdsubscribercount%>(<%=orderthirdsubscribercount%>)  </span></span></a></li>
                </ul>
                <%
                } else {
                %>
                <ul class="round">
                    <li class="member_cnt" style=""><span><img style="width:20px;height:20px;" src="/Application/Tpl/App/shop/Public/Static/images/bullet_blue_expand.png"><%=wx.get("subtitle1")%><span style="float:right;color:red;"><%=firstsubscribercount%> </span></span></li>
                    <li class="member_cnt"><span><img style="width:20px;height:20px;"  src="/Application/Tpl/App/shop/Public/Static/images/bullet_blue_expand.png"><%=wx.get("subtitle2")%><span style="float:right;color:red;"><%=secondsubscribercount%> </span></span></li>				
                    <li class="member_cnt"><span><img style="width:20px;height:20px;" src="/Application/Tpl/App/shop/Public/Static/images/bullet_blue_expand.png"><%=wx.get("subtitle3")%><span style="float:right;color:red;"><%=thirdsubscribercount%> </span></span></li>
                </ul>
                <%
                    }
                %>
            </div>

            <div class="cardexplain">
                <div class="div_ul" id="all_buy"><span><img style='margin-left:5px;' src="/Application/Tpl/App/shop/Public/Static/images/arrow_unclick.png"><%=wx.get("title2")%><span class="bg_total"><%=totalordernobuycount + totalordercount%>单(￥<%=WxMenuUtils.decimalFormat.format(totalordermoney + totalordernobuymoney)%>)</span></span></div>
                <ul class="round">
                    <li class="buy_cnt"><span>下单未购买<span style="float:right;color:red;"><%=totalordernobuycount%> (￥<%=totalordernobuymoney%>)</span></span></li>
                    <li class="buy_cnt"><span>下单已购买<span style="float:right;color:red;"><%=totalordercount%> (￥<%=totalordermoney%>)</span></span></li>
                </ul>
            </div>

            <div class="cardexplain">
                <div class="div_ul" id="all_price"><span><img style='margin-left:5px;'  src="/Application/Tpl/App/shop/Public/Static/images/arrow_unclick.png">我的财富<span class="bg_total"><%=WxMenuUtils.decimalFormat.format(totalordernobuyYJ + totalorderYJ + totalorderalthingYJ + totalorderalendYJ + Float.parseFloat(subscriber.get("areaproxymoney")) + Float.parseFloat(subscriber.get("callbackmoney")))%> 元</span></span></div>
                <ul class="round">
                    <li class="price_cnt"><span>未付款定单财富<span style="float:right;color:red;"><%=WxMenuUtils.decimalFormat.format(totalordernobuyYJ)%></span></span></li>
                    <li class="price_cnt"><span>已付款定单财富<span style="float:right;color:red;"><%=WxMenuUtils.decimalFormat.format(totalorderYJ)%></span></span></li>
                    <li class="price_cnt"><span>已收货定单财富<span style="float:right;color:red;"><%=WxMenuUtils.decimalFormat.format(totalorderalthingYJ)%></span></span></li>
                    <li class="price_cnt"><span>已完成定单财富<span style="float:right;color:red;"><%=WxMenuUtils.decimalFormat.format(totalorderalendYJ)%></span></span></li>
                    <!--区域代理佣金开始-->
                    <%
                        if (!"0".equals(subscriber.get("areaproxyprovince")) || !"0".equals(subscriber.get("areaproxycity"))) {
                    %>
                    <li class="price_cnt"><span>区域代理财富<span style="float:right;color:red;"><%=WxMenuUtils.decimalFormat.format(Float.parseFloat(subscriber.get("areaproxymoney")))%></span></span></li>
                            <%
                                }
                            %>
                    <!--区域代理佣金结束-->
                    <!--订单返现佣金开始 判断有无-->
                    <%
                        if ("2".equals(wx.get("preferentialtype"))) {
                    %>
                    <li class="price_cnt"><span>订单返现财富<span style="float:right;color:red;"><%=WxMenuUtils.decimalFormat.format(Float.parseFloat(subscriber.get("callbackmoney")))%></span></span></li>
                            <%
                                }
                            %>
                    <!--订单返现佣金结束-->
                    <li class="price_cnt"><span>已提现财富<span style="float:right;color:red;"><%=WxMenuUtils.decimalFormat.format(totaltx)%></span></span></li>
                    <li class="price_cnt"><span>可提现财富<span style="float:right;color:red;"><%=WxMenuUtils.decimalFormat.format(totalorderalendYJ + Float.parseFloat(subscriber.get("areaproxymoney")) + Float.parseFloat(subscriber.get("callbackmoney")) - totaltx)%></span></span></li>
                </ul>
            </div>

            <div class="cardexplain">
                <div class="div_ul" onClick="javascript:withdraw();"><span><img style='margin-left:5px;' src="/Application/Tpl/App/shop/Public/Static/images/arrow_unclick.png">申请提现</span></div>
            </div>

            <!--div class="cardexplain">
                    <div class="div_ul" id="memeber_url"><span><img style='margin-left:5px;' src="/Application/Tpl/App/shop/Public/Static/images/arrow_unclick.png">复制推广链接</span></div>
                    <span class="memeber_url" style='display:none;'>http://zbt.winbz.com/index.php?g=App&m=Member&a=register&mid=103369</span>
            </div-->

            <!--div class="cardexplain">
                    <a href = './index.php?g=App&m=Index&a=member_top&id=103369'><div class="div_ul" id="top_url"><span><img style='margin-left:5px;' src="/Application/Tpl/App/shop/Public/Static/images/arrow_unclick.png">销售排行榜</span></div></a>
            </div-->

        </div>
        <%
        } else if ("qrcode".equals(act)) {
        %>

        <div id="ticket-container" >
            <!--            %
                            List<Map<String, String>> newstypesList = new NewstypesDAO().getList("1", Integer.parseInt(wx.get("id")));
                            if (0 < newstypesList.size()) {
                        %><img src="%=newstypesList.get(0).get("img")%>" style="width:100%;position:absolute; top:0px; left:0px; ">%
                            }
                        %>-->
            <%
                List<Map<String, String>> newstypesList = new NewstypesDAO().getList("1", Integer.parseInt(wx.get("id")));
                if ("1".equals(subscriber.get("isvip"))) {
                    String qrcode = subscriber.get("qrcode");
                    boolean tempflag = false;
                    if (!"0".equals(wx.get("myqrcodetype")) && (System.currentTimeMillis() / 1000 - Long.parseLong(subscriber.get("qrcodemediaidtimes")) > 2591700)) {
                        //删除之前二维码
                        if (!"".equals(qrcode)) {
                            java.io.File oldFile = new java.io.File(this.getServletContext()
                                    .getRealPath(qrcode));
                            oldFile.delete();
                        }
                        tempflag = true;
                    }
                    if ("".equals(qrcode) || tempflag) {
                        if (0 < newstypesList.size()) {
                            System.out.println("新生成二维码");
                            qrcode = WxMenuUtils.getShopQRCode(request, wx, subscriber.get("id"));
                            //处理二维码
                            qrcode = WxMenuUtils.doShopQRCode(request, qrcode, wx, subscriber);
                            //同时上传临时文件
                            Map<String, String> qrcodemap = WxMenuUtils.uploadQrcode(request, response, this.getServletConfig(), qrcode, wx);
                            subscriberDAO.updateqrcode(openid, wxsid, qrcode, qrcodemap.get("media_id"), qrcodemap.get("created_at"));
            %>
            <!--<img src='/<%=qrcode%>' style="width:50%;position: relative;margin-left: 25%;margin-top: 75%;">-->
            <img src="/<%=qrcode%>" style="width:100%;position:absolute; top:0px; left:0px; ">
            <%
            } else {
            %>
            <font color='red'>暂无相关图片！</font>
            <%                }
            } else {
            %>
            <!--<img src='/<%=qrcode%>' style="width:50%;position: relative;margin-left: 25%;margin-top: 75%;">-->
            <img src="/<%=qrcode%>" style="width:100%;position:absolute; top:0px; left:0px; ">
            <%
                }
            } else if (0 < newstypesList.size()) {
            %>
            <img src="<%=newstypesList.get(0).get("img")%>" style="width:100%;position:absolute; top:0px; left:0px; ">
            <%
            } else {
            %>
            <font color='red'>暂无相关图片！</font>
            <%                }
            %>
        </div>
        <%                    } else if ("withdraw".equals(act)) {
        %>

        <div id="tx-container" style="display: <%="withdraw".equals(act) ? "block" : "none"%>;">

            <div class="menu_header">
                <div class="menu_topbar">
                    <div id="menu" class="sort ">
                        <a href="">申请提现</a>
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
                            <li class="title">提现信息</li>
                            <li>
                                <table style="padding: 0; margin: 0; width: 100%;">
                                    <tbody>
                                        <tr>
                                            <td width="99px"><label for="price" class="ui-input-text">可提现财富：</label></td>
                                            <td>
                                                <%=WxMenuUtils.decimalFormat.format(totalorderalendYJ + Float.parseFloat(subscriber.get("areaproxymoney")) + Float.parseFloat(subscriber.get("callbackmoney")) - totaltx)%>
                                            </td>
                                        </tr>
                                        <tr>
                                            <!--<td width="80px"><label for="price" class="ui-input-text"></label></td>-->
                                            <td style="color: red;" colspan="2">
                                                每次提现最高金额为200元，提现次数不限。<br/>
                                                注：“可提现财富”金额大于等于<%=wx.get("withdrawmoneylimit")%>元，才可申请提现。
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="80px"><label for="price" class="ui-input-text">提现金额：</label></td>
                                            <td>
                                                <div class="ui-input-text">
                                                    <input id="price" name="price" placeholder="" placeholder="0.00" type="number"
                                                           class="ui-input-text">
                                                </div></td>
                                        </tr>

                                    </tbody>
                                </table>

                                <div class="footReturn">
                                    <a id="txshowcard" class="submit" href="javascript:submitTxOrder();">确定提交</a>
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
            </ul>


        </div>
        <%
        } else if ("order".equals(act)) {
        %>

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
                            <td> <span>订单详情</span> <span style='float:right'><a href='/shop2/vip.jsp?act=qrcode&wx=<%=wxsid%>&openid=<%=openid%>' style='color:red;'>获取推广二维码>>></a></span> </td>
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
                    <%
                        List<DataField> orderList = (List<DataField>) DaoFactory.getOrderDAO().getList(wxsid, openid, -1, 0);
                        for (DataField order : orderList) {
                            DataField province = DaoFactory.getAreaDaoImplJDBC().get(order.getFieldValue("provience"));
                            DataField city = DaoFactory.getAreaDaoImplJDBC().get(order.getFieldValue("city"));
                            DataField area = DaoFactory.getAreaDaoImplJDBC().get(order.getFieldValue("area"));
                    %>
                    <a href="/shop3/orderbuy.jsp?wx=<%=wxsid%>&openid=<%=openid%>&F_No=<%=order.getFieldValue("F_No")%>&act=orderdetail">
                        <li style="border: 1px solid #d0d0d0;border-radius: 10px;margin-bottom:10px;background-color:#FFF;">
                            <table><tbody>
                                    <tr><td style="border-bottom:0px">订单编号:<%=order.getFieldValue("F_No")%></td></tr>
                                    <tr><td style="border-bottom:0px">订单金额:<%=order.getFieldValue("SF_Price")%></td></tr>
                                    <tr><td style="border-bottom:0px">订单时间:<%=order.getFieldValue("F_Date")%></td></tr>
                                    <tr><td style="border-bottom:0px">支付状态:
                                            <!--                                        <em class="no">-->
                                            <%="1".equals(order.getFieldValue("IsPay")) ? "<a href=\"/ForeServlet?method=pay&F_No=" + order.getFieldValue("F_No") + "&wxsid=" + wxsid + "&openid=" + openid + "\"><font color='red'>未支付</font></a>" : ("2".equals(order.getFieldValue("IsPay")) ? "已支付" : ("3".equals(order.getFieldValue("IsPay")) ? "货到付款" : ("4".equals(order.getFieldValue("IsPay")) ? "未退款" : ("5".equals(order.getFieldValue("IsPay")) ? "已退款" : "未知"))))%>
                                            <!--</em>-->
                                            <!--<a href="http://zbt.winbz.com/index.php?g=App&amp;m=Index&amp;a=pay&amp;totalprice=380.00&amp;cart_name=圆领三粒扣短袖14M1014&amp;id=103369&amp;orderid=201507301501351033696">(已经支付?)</a></td>-->
                                    </tr>
                                    <tr><td style="border-bottom:0px">订单状态:
                                            <!--<em class="no">-->
                                            <%
                                                //订单状态：1未发货2已发货3确认收货4未评价5已评价6退货中7已退货
                                                if ("1".equals(order.getFieldValue("Sts"))) {
                                                    out.print("未发货");
                                                } else if ("2".equals(order.getFieldValue("Sts"))) {
                                                    out.print("已发货&nbsp;<a href=\"javascript:void(0);\" id=\"confirmreceiptbutton\" onclick=\"confirmreceipt('" + order.getFieldValue("F_No") + "','" + wxsid + "','" + openid + "')\"><font color='red'>确认收货</font></a>");
                                                } else if ("3".equals(order.getFieldValue("Sts"))) {
                                                    out.print("已收货");
                                                    //                                    } else if ("4".equals(order.getFieldValue("Sts"))) {
                                                    //                                        out.print("未评价");
                                                } else if ("5".equals(order.getFieldValue("Sts"))) {
                                                    out.print("已完成");
                                                } else if ("6".equals(order.getFieldValue("Sts"))) {
                                                    out.print("<font color='red'>退货中,请等待客服联系</font>");
                                                } else if ("7".equals(order.getFieldValue("Sts"))) {
                                                    out.print("已退货");
                                                } else {
                                                    out.print("未支付");
                                                }%>
                                            <!--</em>-->
                                        </td></tr>
                                        <%
                                            long f_DateT = WxMenuUtils.format.parse(order.getFieldValue("F_Date")).getTime();
                                            if (System.currentTimeMillis() - f_DateT < Integer.parseInt(wx.get("endduring")) * 24 * 60 * 60 * 1000) {
                                                if (("1".equals(order.getFieldValue("Sts")) && "2".equals(order.getFieldValue("IsPay"))) || "3".equals(order.getFieldValue("Sts"))) {
                                                    //判断完成订单数
                                                    int successendcount = DaoFactory.getOrderDAO().getTotalNum(wx.get("id"), openid, 0, 5);
                                        %>
                                    <tr><td style="border-bottom:0px">退货:<a href="javascript:void(0);" id="backthingbutton" onclick="backthing('<%=order.getFieldValue("F_No")%>', '<%=wxsid%>', '<%= openid%>', '<%= successendcount%>')"><font color='red'>申请退货</font></a></td></tr>
                                                <%
                                                        }
                                                    }
                                                    List<DataField> orderdetailList = (List<DataField>) DaoFactory.getBasketDAO().getBySuserIdUidNoList(wxsid, openid, order.getFieldValue("F_No"));
                                                    for (DataField orderdetail : orderdetailList) {
                                                %>
                                    <tr><td style="border-bottom:0px">商品编号:<%=orderdetail.getFieldValue("Pcode")%></td></tr>
                                    <tr><td style="border-bottom:0px">商品名称:<%=orderdetail.getFieldValue("Pname")%></td></tr>
                                    <tr><td style="border-bottom:0px">商品详情:
                                            <%
                                                String propertys = orderdetail.getFieldValue("propertys").trim();
                                                if (!"".equals(propertys)) {
                                                    String propertysArr[] = propertys.split(" ");
                                                    for (int i = 0; i < propertysArr.length; i++) {
                                                        if ("".equals(propertysArr[i])) {
                                                            continue;
                                                        }
                                                        DataField property = DaoFactory.getPropertysDaoImplJDBC().get(propertysArr[i].split("-")[0]);
                                                        DataField propertySub = DaoFactory.getPropertysDaoImplJDBC().get(propertysArr[i]);
                                                        if (null == property || null == propertySub) {
                                                            continue;
                                                        }
                                            %>
                                            <%=property.getFieldValue("svname")%>:<%=propertySub.getFieldValue("svname")%>&nbsp;
                                            <%
                                                    }
                                                }
                                            %>
                                        </td></tr>
                                    <!--<tr><td style="border-bottom:0px">商品详情:%=orderdetail.getFieldValue("Per_Price")%></td></tr>-->
                                    <%
                                        }
                                    %>
                                    <!--                                    <tr><td style="border-bottom:0px">联系人:%=order.getFieldValue("Name")%></td></tr>
                                                                        <tr><td style="border-bottom:0px">联系电话:%=order.getFieldValue("Phone")%></td></tr>
                                                                        <tr><td style="border-bottom:0px">联系地址:%=(null != province ? province.getFieldValue("title") : "") + (null != city ? city.getFieldValue("title") : "") + (null != area ? area.getFieldValue("title") : "") + order.getFieldValue("Address")%></td></tr>-->
                                    <%
                                        if ("2".equals(order.getFieldValue("Sts")) || "3".equals(order.getFieldValue("Sts"))) {
                                    %>
                                    <tr><td style="border-bottom:0px">快递公司:<%=order.getFieldValue("ShipName")%></td></tr>
                                    <tr><td style="border-bottom:0px">物流单号:<%=order.getFieldValue("ShipNo")%></td></tr>
                                    <tr><td style="border-bottom:0px">发货时间:<%=order.getFieldValue("ShipTime")%></td></tr>
                                    <%
                                        }
                                    %>
                                </tbody></table>
                        </li>
                    </a>
                    <%
                        }
                    %>
                </ul>
            </div>
        </div>
        <%
            }
        %>
        <p style="height: 50px;">&nbsp;</p>
        <jsp:include page="bottom.jsp">
            <jsp:param name="wxsid" value="<%=wxsid%>"></jsp:param>
            <jsp:param name="openid" value="<%=openid%>"></jsp:param>
        </jsp:include>
        <script>
            window.onload = function() {
                if ($_GET['page_type'] == 'order')
                {
                    user();
                }
                if ($_GET['page_type'] == 'ticket')
                {
                    $("#ticket").click();
                }
            }

            function withdraw() {
                window.location.href = "?act=withdraw&wx=<%=wxsid%>&openid=<%=openid%>";
            }
            function backthing(F_No, wxsid, openid, successendcount) {
                $("#backthingbutton").attr("disabled", true);
                if ("0" == successendcount) {
                    if (confirm("退货后将取消您的会员资格，确认？")) {
                        window.location.replace("/ForeServlet?method=refund&F_No=" + F_No + "&wxsid=" + wxsid + "&openid=" + openid);
                    }
                } else {
                    if (confirm("您确认退货吗？")) {
                        window.location.replace("/ForeServlet?method=refund&F_No=" + F_No + "&wxsid=" + wxsid + "&openid=" + openid);
                    }
                }
                $("#backthingbutton").attr("disabled", false);
            }

            function confirmreceipt(F_No, wxsid, openid) {
                if (confirm("请收到货再进行确认，确认？")) {
                    $("#confirmreceiptbutton").attr("disabled", true);
                    $.post("../shop3/public_do.jsp?act=confirmreceipt", {"F_No": F_No, "wxsid": wxsid, "openid": openid}, function(result) {
                        if ("0" == result.trim()) {
                            window.location.replace("/shop2/vip.jsp?act=order&wx=" + wxsid + "&openid=" + openid);
                        } else {
                            alert("系统错误，请稍后重试！");
                            window.history.go(-1);
                        }
                    });
                }
            }
        </script>
    </body>
</html>
