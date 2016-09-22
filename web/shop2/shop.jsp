<%-- 
    Document   : index
    Created on : 2015-7-30, 9:32:21
    Author     : Administrator
--%>

<%@page import="wap.wx.menu.WxJsApiUtils"%>
<%@page import="wap.wx.service.SubscriberService"%>
<%@page import="wap.wx.menu.WxPayUtils"%>
<%@page import="wap.wx.dao.NewsDAO"%>
<%@page import="wap.wx.dao.SubscriberDAO"%>
<%@page import="wap.wx.menu.WxMenuUtils"%>
<%@page import="job.tot.dao.DaoFactory"%>
<%@page import="java.util.List"%>
<%@page import="job.tot.util.RequestUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../shop/inc/common.jsp"%>

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

        Map<String, String> wx = new HashMap<String, String>();
        wx.put("id", wxsid);
        wx = new WxsDAO().getById(wx);

        SubscriberDAO subscriberDAO = new SubscriberDAO();
        Map<String, String> subscriber = subscriberDAO.getByOpenid(wxsid, openid);
        //为空时存入
        if (null == subscriber.get("id")) {
            String parentid = "0";
            Map<String, String> targetsubscriber = new HashMap<String, String>();
            String targetopenid = RequestUtil.getString(request, "targetopenid");
            if (!"".equals(targetopenid) && !openid.equals(targetopenid)) {
                targetsubscriber = subscriberDAO.getByOpenid(wxsid, targetopenid);
                parentid = targetsubscriber.get("id");
                System.out.println("分享成为关系 " + parentid);
                //发送信息
                String content = "有人通过分享关注了本公众号，成为您的" + wx.get("subtitle1") + "。";
                WxMenuUtils.sendCustomService(targetopenid, content, wx);

                //往上再取两级，三级都通知
                if (!"0".equals(targetsubscriber.get("parentopenid"))) {
                    Map<String, String> secondparentsubscriber = subscriberDAO.getById(wxsid, targetsubscriber.get("parentopenid"));
                    content = "有人通过分享关注了本公众号，成为您的" + wx.get("subtitle2") + "。";
                    WxMenuUtils.sendCustomService(secondparentsubscriber.get("openid"), content, wx);
                    
                }
            }
            new SubscriberService().addSubscriber(wx, openid, parentid);
        }
    %>
    <%--
    if (!"0".equals(secondparentsubscriber.get("parentopenid"))) {
                        Map<String, String> thirdparentsubscriber = subscriberDAO.getById(wxsid, secondparentsubscriber.get("parentopenid"));
                        content = "有人通过分享关注了本公众号，成为您的" + wx.get("subtitle3") + "。";
                        WxMenuUtils.sendCustomService(thirdparentsubscriber.get("openid"), content, wx);
                    }
     --%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv=”Expires” CONTENT=”0″>
        <meta http-equiv=”Cache-Control” CONTENT=”no-cache”>
        <meta http-equiv=”Pragma” CONTENT=”no-cache”>
        <title><%=wx.get("name")%></title>
        <meta name="viewport"
              content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
        <link href="/Application/Tpl/App/shop/Public/Static/css/foods.css?555" rel="stylesheet"
              type="text/css"/>
        <script type="text/javascript" src="/Application/Tpl/App/shop/Public/Static/js/jquery.min.js"></script>
        <script type="text/javascript" src="/Application/Tpl/App/shop/Public/Static/js/wemall.js?33"></script>
        <script type="text/javascript" src="/Application/Tpl/App/shop/Public/Static/js/alert.js"></script>
        <script type="text/javascript" src="/Application/Tpl/App/shop/Public/Static/js/area.js"></script>
        <script type="text/javascript" src="/Application/Tpl/App/shop/Public/Static/js/jquery.scrollLoading-min.js"></script>

        <link href="/Application/Tpl/App/shop/Public/Static/css/swiper.min.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="/Application/Tpl/App/shop/Public/Static/js/swiper.min.js"></script>
        <style>
            .swiper-container {
                width: 100%;
            }
            .swiper-slide {
                text-align: center;
                font-size: 18px;
                background: #fff;

                /* Center slide text vertically */
                display: -webkit-box;
                display: -ms-flexbox;
                display: -webkit-flex;
                display: flex;
                -webkit-box-pack: center;
                -ms-flex-pack: center;
                -webkit-justify-content: center;
                justify-content: center;
                -webkit-box-align: center;
                -ms-flex-align: center;
                -webkit-align-items: center;
                align-items: center;
            }
            .button_img{margin-left:10px;height:40px;float:left;margin-top:3px;}
            .button_buy{margin-right:10px;float:right;}
            .button_buy a p{height: 3em;overflow: hidden;}


            .mui-sku input:checked+label {
                border-color: #b10000;
                color: #000;
            }
            .mui-sku label {
                -webkit-tap-highlight-color: transparent;
                display: inline-block;
                border: 1px solid #e5e5e5;
                background-color: <%=wx.get("weishopcolor")%>;
                min-width: 2em;
                padding: .5em 1.2em;
                margin: 0 .5em .5em 0;
                max-width: 100%;
                text-align: center;
                -webkit-box-sizing: border-box;
                box-sizing: border-box;
                -webkit-border-radius: 3px;
                border-radius: 3px;
            }

            .mui-sku input {
                display: none;
                -webkit-tap-highlight-color: transparent;
            }


            .bg_color{background-color: <%=wx.get("weishopcolor")%> }
            .submit{background-color: <%=wx.get("weishopcolor")%> }
            .submit:active {background-color: <%=wx.get("weishopcolor")%>}
            .menu_topbar {background-color: <%=wx.get("weishopcolor")%> ;background: -webkit-linear-gradient(top, <%=wx.get("weishopcolor")%> , <%=wx.get("weishopcolor")%>);border-bottom: 1px solid <%=wx.get("weishopcolor")%>;}
            .footermenu ul {border-top: 1px solid <%=wx.get("weishopcolor")%> ;background-color: <%=wx.get("weishopcolor")%>;background: -webkit-linear-gradient(top, <%=wx.get("weishopcolor")%>, <%=wx.get("weishopcolor")%>);}
            .footermenu ul li a.active {background: -webkit-linear-gradient(top, <%=wx.get("weishopcolor")%> , <%=wx.get("weishopcolor")%>);}

            .head{background-color: <%=wx.get("weishopcolor")%> }

            .sort_on ul{background: -webkit-linear-gradient(top, <%=wx.get("weishopcolor")%> , <%=wx.get("weishopcolor")%>);}

            .textcolor{
                color: <%=wx.get("weishoptextcolor")%>;
            }

            .gonggao {
                background-color: <%=wx.get("weishopcolor")%>;
                padding: 10px;
                font-size: 14px;
                line-height: 20px;
                margin: 0;
                position: relative;
            }

            .gonggao .content {
                text-indent: 2em;
                color: <%=wx.get("weishoptextcolor")%>;
            }
        </style>

        <script type="text/javascript">
            var appurl = '/index.php';
            var rooturl = '';
            $(function() {
                $("#menu_id img").scrollLoading();
            });
        </script>


        <style>
            .transparent {
                position: fixed;
                top: 0;
                left: 0;
                right: 0;
                z-index: 1000;
                -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
                filter:alpha(opacity=55);
                -moz-opacity:0.55;
                opacity:0.55;
                background-color:#FFC1E0;
                color:<%=wx.get("weishopcolor")%>;
            }

            .transparent ul {
                border-top: 1px solid <%=wx.get("weishopcolor")%>;
                position: fixed;
                z-index: 1000;
                top: 0;
                left: 0;
                right: 0;
                margin: auto;
                display: block;
                width: 100%;
                height: 48px;
                display: -webkit-box;
                display: box;
                -webkit-box-orient: horizontal;
                background-color: <%=wx.get("weishopcolor")%>;
            }

            .transparent ul li {
                width: auto !important;
                height: 100%;
                position: static !important;
                margin: 0;
                border-radius: 0 !important;
                -webkit-box-sizing: border-box;
                box-sizing: border-box;
                -webkit-box-flex: 1;
                box-flex: 1;
                -webkit-box-sizing: border-box;
                box-shadow: none !important;
                background: none;
            }

            .transparent ul li a {
                color: <%=wx.get("weishopcolor")%>;
                font-size: 20px;
                line-height: 20px;
                text-align: center;
                display: block;
                text-decoration: none;
                padding-top: 5px;
                font-size: 12px;
                position: relative;
                height: 48px;
                text-shadow: 0 1px rgba(0, 0, 0, 0.2);
            }

            .button_img{margin-left:10px;height:40px;float:left;margin-top:3px;}
            .button_buy{margin-right:10px;float:right;}
            .button_buy a p{height: 3em;overflow: hidden;}
        </style>
        <!--    <div class="transparent" >
                <ul>
                    <li><p  class='button_img' style='line-height: 3em; color:#fff;width:100%;text-align:center; margin:0 auto;'>关注我们</p></li><li class='button_buy'><a href='http://mp.weixin.qq.com/s?__biz=MzAxMzU3MTkyMQ==&mid=208318379&idx=1&sn=c179b308d2bbea21f19eb2ab79cb3a42#rd'><p style='line-height: 3em;'><span style='padding:5px;border:1px solid #B3EE3A;'><strong>立即关注</strong></span></p></a></li>
                </ul>
            </div>-->
        <%
            //分享注入
            String url = request.getRequestURL() + "?" + request.getQueryString();
            Map<String, String> jsapi = new WxJsApiUtils().jsapi(url, wx);
            String jsApiList = "'onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo'";
            jsapi.put("jsApiList", jsApiList);
            request.setAttribute("jsapi", jsapi);
            //判断一下是否允许显示会员昵称
            String sharetitle = "";
            if ("1".equals(wx.get("issharesubscriber"))) {
                sharetitle = (subscriber.get("nickname") + " 的店铺");
                if (!"".equals(subscriber.get("username"))) {
                    sharetitle = (subscriber.get("username") + " 的店铺");
                }
            } else {
                sharetitle = (wx.get("name") + " 的店铺");
            }
        %>
        <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
        <script>
            wx.config({
                debug: false,
                appId: '${jsapi.appId}',
                timestamp: ${jsapi.timestamp},
                nonceStr: '${jsapi.noncestr}',
                signature: '${jsapi.signature}',
                jsApiList: [${jsapi.jsApiList}]
            });
            wx.ready(function() {
                wx.checkJsApi({
                    jsApiList: [${jsapi.jsApiList}],
                    success: function(res) {
                    },
                    fail: function(res) {
                    }
                });
                var shareData = {
                    title: '<%=sharetitle%>',
                    desc: '<%=sharetitle%>',
                    link: '${pageContext.request.scheme}://${pageContext.request.serverName}${pageContext.servletContext.contextPath}/ForeServlet?method=shop&wx=<%=wxsid%>&targetopenid=<%=openid%>',
                    imgUrl: '${pageContext.request.scheme}://${pageContext.request.serverName}${pageContext.servletContext.contextPath}<%=wx.get("img")%>'
                };
                wx.onMenuShareAppMessage(shareData);
                wx.onMenuShareTimeline(shareData);
                wx.onMenuShareQQ(shareData);
                wx.onMenuShareWeibo(shareData);
            };
            wx.error(function(res) {
                alert(res.errMsg);
            });
        </script>
    </head>
    <%    DataField df = null;
        ArrayList list = null;
        String act = RequestUtil.getString(request, "act");

        String F_No = "";
//        if ("addshop".equals(act)) {//|| "buy".equals(act)
//            int id = RequestUtil.getInt(request, "id");
//            int count = RequestUtil.getInt(request, "count");
////            String name = RequestUtil.getString(request, "name");
////            String phone = RequestUtil.getString(request, "phone");
////            String weixin = RequestUtil.getString(request, "weixin");
////            String address = RequestUtil.getString(request, "address");
//            String remark = RequestUtil.getString(request, "note");
//            String propertys = RequestUtil.getString(request, "propertys");
//
//            df = DaoFactory.getProductDAO().get(id);
//            String fNum = String.valueOf(System.currentTimeMillis());
//            Float FirstYJ = df.getFloat("distributionfirstdiscount") * count;
//            Float SecondYJ = df.getFloat("distributionseconddiscount") * count;
//            Float ThirdYJ = df.getFloat("distributionthirddiscount") * count;
//            if (1 == df.getInt("distributiontype")) {
//                FirstYJ = df.getFloat("distributionmoney") * df.getFloat("distributionfirstdiscount") * count;
//                SecondYJ = df.getFloat("distributionmoney") * df.getFloat("distributionseconddiscount") * count;
//                ThirdYJ = df.getFloat("distributionmoney") * df.getFloat("distributionthirddiscount") * count;
//            }
////            //判断有无地址相同
////            DataField temp = DaoFactory.getBasketDAO().getBySuserIdUidAddress(wxsid, openid, name, phone, weixin, address, remark, 1);
////            if (null != temp) {
////                fNum = temp.getFieldValue("F_No");
////            } else {
////                DaoFactory.getAddressDAO().add(openid, name, address, "", "", "", phone, "", weixin, remark);
////            }
//            boolean bl = DaoFactory.getBasketDAO().add(id, df.getFieldValue("Title"), openid, wxsid, request.getRemoteAddr(), fNum, df.getFieldValue("ProCode"), count, 1, new Timestamp(System.currentTimeMillis()), df.getFloat("Price"), df.getFloat("Price") * count, df.getString("ViewImg"), "", "", "", "", remark, FirstYJ, SecondYJ, ThirdYJ, propertys);
//            if (bl) {
////                if ("buy".equals(act)) {
//////                    response.sendRedirect(request.getContextPath() + "/shop2/shop.jsp?act=cart&wx=" + wxsid + "&openid=" + openid);
////                    response.sendRedirect(request.getContextPath() + "/shop3/myshop.jsp?wx=" + wxsid + "&openid=" + openid);
////                }
//                act = null;
//            }
//        }
        if ("delshop".equals(act)) {
            String id = RequestUtil.getString(request, "id");
            String s[] = {id};
            DaoFactory.getBasketDAO().batDel(s);
            act = "cart";
        }
//        if ("addorder".equals(act)) {
//            String name = RequestUtil.getString(request, "name");
//            String phone = RequestUtil.getString(request, "phone");
//            String weixin = RequestUtil.getString(request, "weixin");
//            String address = RequestUtil.getString(request, "address");
//            //保存地址
//            DaoFactory.getAddressDAO().add(openid, name, address, "", "", "", phone, "", weixin, "");
//
//            String carts = RequestUtil.getString(request, "carts");
//            String[] cartArr = carts.split(",");
//            boolean flag1 = false;
//            boolean flag2 = false;
//            boolean flag3 = false;
//            Timestamp times = new Timestamp(System.currentTimeMillis());
//            String Fnos = String.valueOf(System.currentTimeMillis());
//            String totalFnos = "";
//            for (int i = 0; i < cartArr.length; i++) {
//                if ("".equals(cartArr[i])) {
//                    continue;
//                }
//                DataField cart = DaoFactory.getBasketDAO().getId(Integer.parseInt(cartArr[i]));
//                DaoFactory.getBasketDAO().modFno(Integer.parseInt(cartArr[i]), Fnos);
//                //添加订单
//                int count = DaoFactory.getOrderDAO().getDataCount("select count(*) counts from t_order where F_No='" + Fnos + "'");
//                if (0 < count) {
//                    flag1 = DaoFactory.getOrderDAO().modadd(Fnos, times, cart.getFloat("Tot_Price"), cart.getFloat("Tot_Price"), cart.getFloat("Tot_Price"), cart.getFloat("Tot_Price"), cart.getFloat("Tot_Price"), cart.getFloat("FirstYJ"), cart.getFloat("SecondYJ"), cart.getFloat("ThirdYJ"), 0, cart.getFieldValue("Remark"));
//                    totalFnos = Fnos;
//                } else {
//                    flag1 = DaoFactory.getOrderDAO().add(Fnos, times, cart.getFloat("Tot_Price"), cart.getFloat("Tot_Price"), cart.getFloat("Tot_Price"), cart.getFloat("Tot_Price"), cart.getFloat("Tot_Price"), openid, wxsid, request.getRemoteAddr(), 0, 1, 0, 0, 0, 1, name, address, phone, phone, "", "", "", 0, 0, "", "", 0, 0, cart.getFieldValue("Remark"), cart.getFloat("FirstYJ"), cart.getFloat("SecondYJ"), cart.getFloat("ThirdYJ"), name, phone, weixin, address, cart.getFieldValue("Remark"), 7, "", "", "");
//                    totalFnos = Fnos;
//                }
//                //更新购物车状态
//                flag2 = DaoFactory.getBasketDAO().modWxUidId(openid, wxsid, cartArr[i], 2);
//                //减少商品数量
//                flag3 = DaoFactory.getProductDAO().modIsRem(cart.getInt("Pid"), cart.getInt("Pnum"));
//
//                if (!(flag1 && flag2 && flag3)) {
//                    break;
//                }
//            }
//            if (flag1 && flag2 && flag3) {
//                if (!"".equals(totalFnos)) {
//                    DataField order = DaoFactory.getOrderDAO().get(totalFnos);
//                    //发送上级通知
////                    Map<String, String> map = new HashMap<String, String>();
////                    Map<String, Map<String, String>> mapmap = new HashMap<String, Map<String, String>>();
////                    Map<String, String> tempmap = null;
////                    if (null != parentsubscriber.get("openid")) {
////                        map.put("touser", parentsubscriber.get("openid"));
////                        map.put("template_id", "DvHJD_a2xiJFR2ZiL5GZIWK95r_pmrwFqsR-8GpMsio");
////                        map.put("url", request.getScheme() + "://" + request.getServerName()+request.getContextPath() + "/shop2/orderlist.jsp?wxsid=" + wxsid + "&openid=" + openid + "&F_No=" + cart.getFieldValue("F_No"));
////                        map.put("topcolor", "#FF0000");
////                        //nickname
////                        tempmap = new HashMap<String, String>();
////                        tempmap.put("value", subscriber.get("nickname"));
////                        tempmap.put("color", "#173177");
////                        mapmap.put("nickname", tempmap);
////                        //times
////                        tempmap = new HashMap<String, String>();
////                        tempmap.put("value", WxMenuUtils.format.format(times));
////                        tempmap.put("color", "#173177");
////                        mapmap.put("times", tempmap);
////                        //orderno
////                        tempmap = new HashMap<String, String>();
////                        tempmap.put("value", cart.getFieldValue("F_No"));
////                        tempmap.put("color", "#173177");
////                        mapmap.put("orderno", tempmap);
////                        //ordermoney
////                        tempmap = new HashMap<String, String>();
////                        tempmap.put("value", cart.getFieldValue("Tot_Price"));
////                        tempmap.put("color", "#173177");
////                        mapmap.put("ordermoney", tempmap);
////                        //commission
////                        tempmap = new HashMap<String, String>();
////                        tempmap.put("value", cart.getFieldValue("FirstYJ"));
////                        tempmap.put("color", "#173177");
////                        mapmap.put("commission", tempmap);
////                        wxPayUtils.sendtemplatemessage(map, mapmap);
////                    }
//                    String content = "您的"+wx.get("subtitle1")+"【" + subscriber.get("nickname") + "】在" + WxMenuUtils.format.format(times) + "下单，订单号为：" + order.getFieldValue("F_No") + "；订单金额为：" + order.getFieldValue("SF_Price") + "元；您将获得的提成为：" + order.getFieldValue("FirstYJ") + "元。";
//                    WxMenuUtils.sendCustomService(parentsubscriber.get("openid"), content, wx);
//                    act = "orderdetail";
//                    F_No = order.getFieldValue("F_No");
////                    out.print("<script>location.replace('/shop2/shop.jsp?act=orderdetail&F_No=" + order.getFieldValue("F_No") + "&wx=" + wxsid + "&openid=" + openid + "');</script>");
////                    out.print("<script>location.replace(\"/ForeServlet?method=pay&F_No=" + order.getFieldValue("F_No") + "&wxsid=" + wxsid + "&openid=" + openid + "\");</script>");
//
//                    //            response.sendRedirect(request.getContextPath() + "/shop2/shop.jsp");
//                } else {
//                    out.print("<script>alert('系统繁忙，请稍后重试！');window.go(-1);</script>");
//                }
//            }
//        }

        //购物车信息
        int cartnum = 0;
        float totolmoney = 0f;
        List<DataField> cartlist = (ArrayList) DaoFactory.getBasketDAO().getListByUidp(openid, wxsid, 1, -1, -1);
        for (DataField cart : cartlist) {
            cartnum += cart.getInt("Pnum");
            totolmoney += cart.getFloat("Tot_Price");
        }
    %>
    <body class="sanckbg mode_webapp" style="display:none;">
        <div id="menu-container" style="display: block;">
            <%            if (null == act || "".equals(act)) {
            %>
            <div class="menu_header" style="display:block;border:1px solid #ccc;">
                <div class="menu_topbar">
                    <div id="menu" class="sort sort_on">
                        <a href="" class="textcolor"><%=wx.get("name")%></a>
                        <ul>
                            <%                List<DataField> connTypeList = (List<DataField>) DaoFactory.getCategoryDAO().getCategorys(0, wxsid);
                                for (int i = 0; i < connTypeList.size(); i++) {
                                    DataField connType = connTypeList.get(i);
                            %>
                            <li><a href="?cid=<%=connType.getFieldValue("id")%>&wx=<%=wxsid%>&openid=<%=openid%>"><%=connType.getFieldValue("Title")%></a></li>
                                <%
                                    }
                                %>
                            <li><a href="?cid=0&wx=<%=wxsid%>&openid=<%=openid%>">所有商品</a></li>
                            <!--<li><a href="javascript:showProducts('1')">男士</a></li><li><a href="javascript:showProducts('3')">女士</a></li><li><a href="javascript:showProducts('4')">布衣</a></li>						<li><a href="javascript:showAll()">所有商品</a></li>-->
                        </ul>
                    </div>
                    <a class="head_btn_right" href="javascript:showMenu();" style="width: 110px;font-size: 14px;color: #4f4d4e;">
                        商品分类<i class="menu_header_home" style="margin: 8px 20px 8px 0px;float: right;"></i> 
                    </a>
                </div>
            </div>

            <div class="gonggao" style="margin-top:40px;">
                <%--<div class="hot">
                    <strong>公告</strong>
                </div> --%>
                <div class="content"> 
                    <div align="">
                        <%
                            List<Map<String, String>> newsList = new NewsDAO().getList("1", Integer.parseInt(wx.get("id")));
                            if (0 < newsList.size()) {
                        %><%=newsList.get(0).get("content")%><%
                            }
                        %>
                    </div>
                </div>
            </div>

            <!-- Swiper -->
            <div class="swiper-container" style="width:100%;">
                <div class="swiper-wrapper">
                    <% list = (ArrayList) DaoFactory.getSlideDAO().getList(wxsid, -1, -1);
                        for (Iterator iter = list.iterator(); iter.hasNext();) {
                            df = (DataField) iter.next();
                    %>
                    <div class="swiper-slide">
                        <a href="<%=df.getFieldValue("LinkUrl")%>&openid=<%=openid%>">
                            <img style="width:100%;" src='<%=df.getString("Photo")%>'>
                        </a>
                    </div>
                    <%
                        }

                    %>
                </div>
                <div class="swiper-pagination"></div>
            </div>

            <!-- Initialize Swiper -->
            <script>
                $(window).load(function() {
                    var swiper = new Swiper('.swiper-container', {
                        pagination: '.swiper-pagination',
                        paginationClickable: true,
                        spaceBetween: 30,
                        centeredSlides: true,
                        autoplay: 2500,
                        speed: 300,
                        autoplayDisableOnInteraction: false
                    });

                    $(function() {
                        var height = $('.swiper-slide img:eq(0)').height();
                        if (height <= 0) {
                            height = 320;
                        }
                        $('.swiper-wrapper').css('height', height);
                    })
                })
            </script>


            <!--div class="div_header">
                    <span style='float:left;margin-left:10px;margin-right:10px;'>
                            <img src='/Application/Tpl/App/shop/Public/Static/images/defult.jpg' width='70px;' height='70px;'>			</span>
                    <span class="header_right" >
                            <div class="header_l_di" style="margin-top: 25px;"><span>temp 的店铺</span></div>
                    </span>
            </div-->

            <section class="menu" style='margin-top: 10px;'>
                <section class="list listimg">
                    <dl>

                        <style>
                            .promotion{width:100%;margin:0 auto;}
                            .promotion ul{width:100%;overflow:hidden;}
                            .promotion li{width:100%;display:inline;margin:10px 10px 0 10px;}
                            .promotion .pic{width:100%;overflow:hidden;background:<%=wx.get("weishopcolor")%>;}
                            .promotion .title{overflow:hidden;margin-top:5px;}
                            .promotion .price{font-weight:bold;color:#e00000;line-height:20px;}
                            .promotion .price del{font-weight:normal;color:#999;}
                        </style>

                        <div class="promotion" data-type="productGroup" data-id="product_promotion" data-spec="280X280" style="margin-bottom: 50px;">
                            <ul class="clearfix" id="menu_id">
                                <!--促销文章列表-->
                                <%
                                    List<Map<String, String>> cuxiaonewsList = new NewsDAO().getList("3", Integer.parseInt(wx.get("id")));
                                    for (Map<String, String> cuxiaonews : cuxiaonewsList) {
                                %>
                                <li style="position:relative;" menu='3'><div class="pic" style="position: relative">
                                        <a href="${pageContext.servletContext.contextPath}/ForeServlet?method=newsdemo&id=<%=cuxiaonews.get("id")%>&wx=<%=wx.get("id")%>">
                                            <img src="${pageContext.servletContext.contextPath}<%=cuxiaonews.get("img")%>" data-url="<%=cuxiaonews.get("img")%>" width="100%">
                                        </a>
                                    </div>
                                    <div style="margin-top:15px;">
                                        <div class="title" style='display:none;'>
                                            <a href="${pageContext.servletContext.contextPath}/ForeServlet?method=newsdemo&id=<%=cuxiaonews.get("id")%>&wx=<%=wx.get("id")%>"><%=cuxiaonews.get("name")%></a>
                                            <div class="price" style="line-height:40px;"></div>
                                        </div>
                                    </div>
                                </li>
                                <%
                                    }
                                %>
                                <!--产品列表-->
                                <%                                int cid = RequestUtil.getInt(request, "cid");
                                    list = (ArrayList) DaoFactory.getProductDAO().getList(null, wxsid, null, 0, 0, cid, 0, 0, 0, 0, 0, null, null, null, null, null, null, null, 0, null, null, 0, 0, 1, null, null, null, 1, 0, 0, null, null, null, null, 1, null, 0, 0, null, null, 0, 0, "SaleNum desc", 0, 0);
                                    for (Iterator iter = list.iterator(); iter.hasNext();) {
                                        df = (DataField) iter.next();
                                %>
                                <li style="position:relative;" menu='3'><div class="pic" style="position: relative">
                                        <!--<a href="/ForeServlet?method=detail&id=<%=df.getInt("id")%>&wx=<%=wxsid%>">-->
                                        <a href="?id=<%=df.getInt("id")%>&act=detail&wx=<%=wxsid%>&openid=<%=openid%>">
                                            <img src="/Application/Tpl/App/shop/Public/Static/images/ajax-loader.gif" data-url="<%=df.getString("ViewImg")%>" width="100%">
                                        </a>
                                    </div>
                                    <div style="margin-top:15px;">
                                        <div class="title" style='display:none;'>
                                            <a href="#/product/p_780070"><%=df.getString("Title")%></a>
                                            <div class="price" style="line-height:40px;">￥<%=df.getFloat("Price")%></div>
                                        </div>
                                    </div>
                                </li>
                                <%}%>
                            </ul>
                        </div>
                    </dl>
                </section>

                <div class="footermenu">
                    <ul>
                        <!--%="order".equals(act) ? "class=\"active\"" : ""%>-->
                        <li ><a href="/shop2/shop.jsp?wx=<%=wxsid%>&openid=<%=openid%>&act="> <img src="/img/index.png">
                                <p class="textcolor">首页</p>
                            </a></li>
                        <li id="add_cart"><a href="/shop3/myshop.jsp?wx=<%=wxsid%>&openid=<%=openid%>&act="> <span class="num" id="cartN3"><%=cartnum%></span> <img
                                    src="/img/shop.png">
                                <p class="textcolor">购物车</p>
                            </a></li>
                        <li id="cart"><a href="/shop2/vip.jsp?act=order&wx=<%=wxsid%>&openid=<%=openid%>"> <img src="/img/order.png">
                                <p class="textcolor">我的订单</p>
                            </a></li>
                        <li id="home"><a href="/shop2/vip.jsp?wx=<%=wxsid%>&openid=<%=openid%>&act="> <img src="/img/vip.png">
                                <p class="textcolor">会员中心</p>
                            </a></li>
                    </ul>
                </div>

                <%
                    }
                    if ("detail".equals(act)) {
                        int id = RequestUtil.getInt(request, "id");
                        DataField product = DaoFactory.getProductDAO().get(id);
                %>
                <div>

                    <section class="head"> 
                        <a href="javascript:void(0)" onclick="window.history.go(-1)" class="head_back">
                            <!--<i class="icon-back"></i>--><
                        </a> 
                        <div class="head-title">商品详情</div>
                    </section>

                    <div id="Popup" style="display: block;margin-top:40px;">
                        <div class="imgPopup" style="padding:0px;">
                            <img id="detailpic" src="" style="display:none;">
                            <h3 id="detailtitle"></h3>
                            <p class="jianjie" id="detailinfo">
                                <!--                            明细
                                                        <p><img src="http://zbt.winbz.com/Public/Plugin/umeditor/php/upload/20150723/1437653896219.jpg" _src="http://zbt.winbz.com/Public/Plugin/umeditor/php/upload/20150723/1437653896219.jpg" style=""></p><p><img src="http://zbt.winbz.com/Public/Plugin/umeditor/php/upload/20150723/14376538995451.jpg" _src="http://zbt.winbz.com/Public/Plugin/umeditor/php/upload/20150723/14376538995451.jpg" style=""></p><p><img src="http://zbt.winbz.com/Public/Plugin/umeditor/php/upload/20150723/1437653901710.jpg" _src="http://zbt.winbz.com/Public/Plugin/umeditor/php/upload/20150723/1437653901710.jpg" style=""></p><p><img src="http://zbt.winbz.com/Public/Plugin/umeditor/php/upload/20150723/14376539043917.jpg" _src="http://zbt.winbz.com/Public/Plugin/umeditor/php/upload/20150723/14376539043917.jpg" style=""></p><p><img src="http://zbt.winbz.com/Public/Plugin/umeditor/php/upload/20150723/14376539069076.jpg" _src="http://zbt.winbz.com/Public/Plugin/umeditor/php/upload/20150723/14376539069076.jpg" style=""></p><p><img src="http://zbt.winbz.com/Public/Plugin/umeditor/php/upload/20150723/14376539098915.jpg" _src="http://zbt.winbz.com/Public/Plugin/umeditor/php/upload/20150723/14376539098915.jpg" style=""></p><p><img src="http://zbt.winbz.com/Public/Plugin/umeditor/php/upload/20150723/14376539119979.jpg" _src="http://zbt.winbz.com/Public/Plugin/umeditor/php/upload/20150723/14376539119979.jpg" style=""></p><p><img src="http://zbt.winbz.com/Public/Plugin/umeditor/php/upload/20150723/14376539132623.jpg" _src="http://zbt.winbz.com/Public/Plugin/umeditor/php/upload/20150723/14376539132623.jpg" style=""></p><p><img src="http://zbt.winbz.com/Public/Plugin/umeditor/php/upload/20150723/14376539156114.jpg" _src="http://zbt.winbz.com/Public/Plugin/umeditor/php/upload/20150723/14376539156114.jpg" style=""></p>                -->
                                <%=product.getString("Content")%>
                            <p style="height: 50px;">&nbsp;</p>
                            </p>
                        </div>
                    </div>

                    <div class="footermenu" style='z-index:1000'>
                        <ul>
                            <li><a href="/shop2/shop.jsp?wx=<%=wxsid%>&openid=<%=openid%>&act="> <img src="/img/index.png">
                                    <p class="textcolor">首页</p>
                                </a></li>
    <!--                            <li><a href="?wx=<%=wxsid%>&openid=<%=openid%>"> <img
                                            src="/Application/Tpl/App/shop/Public/Static/images/home.png">
                                        <p>返回</p>
                                    </a></li>-->

<!--                            <li id="add_cart"><a href="?act=cart"> <span class="num" id="cartN3"><%=cartnum%></span> <img
                                        src="/Application/Tpl/App/shop/Public/Static/images/buy.png">
                                    <p>购物车</p>
                                </a></li>-->

                            <li><a href="?id=<%=product.getInt("id")%>&act=address&wx=<%=wxsid%>&openid=<%=openid%>">  <img src="/img/order.png">
                                    <p class="textcolor">立刻购买</p>
                                </a></li>
                        </ul>
                    </div>
                </div>
                <%}%>
            </section>
        </div>
        <%
            if ("address".equals(act)) {
                int id = RequestUtil.getInt(request, "id");
                DataField procuct = DaoFactory.getProductDAO().get(id);
        %>
        <input type="hidden" id="price" value="<%=procuct.getFieldValue("Price")%>"/>
        <input type="hidden" id="psvcode" value="<%=procuct.getFieldValue("ProCode")%>"/>
        <div id="cart-container">
            <div class="menu_header">
                <div class="menu_topbar">
                    <div id="menu" class="sort">
                        <a href="">购买</a>
                    </div>
                </div>
            </div>

            <img style="width:100%;" id="good_pic" src="<%=procuct.getString("ViewImg")%>">

            <section class="order">
                <div class="orderlist">

                    <ul id="ullist">
                        <dt  style="display:none;">已选购的</dt>
                        <li class="ccbg2" id="wemall_<%=procuct.getString("id")%>">
                            <div class="orderdish">
                                <span class="idss" style="display:none;"><%=procuct.getString("id")%></span>
                                <span name="title"><%=procuct.getFieldValue("ProCode")%>&nbsp;&nbsp;&nbsp;<%=procuct.getString("Title")%></span>
                                <span class="price" id="v_0" style="display:none;"><%=procuct.getString("Price")%></span>
                                <span style="display:none;" class="price">元</span>
                            </div>
                            <div class="orderchange">
                                <a href='javascript:addProductN("wemall_<%=procuct.getString("id")%>")' class="increase">
                                    <b class="ico_increase">加一份</b>
                                </a>
                                <span class="count" id="num_<%=procuct.getString("id")%>">1</span>
                                <a href='javascript:reduceProductN("wemall_<%=procuct.getString("id")%>")' class="reduce">
                                    <b class="ico_reduce">减一份</b>
                                </a>
                            </div>
                        </li>
                    </ul>


                    <ul>
                        <li class="ccbg2" >今日限购剩余：<span style='color:red' id='last_cnt'><%=procuct.getFieldValue("IsRem")%></span></li>
                    </ul>
                    <ul id="cartinfo">
                        <dt>本次总计</dt>
                        <li class="ccbg2" id="emptyLii">选择：<span id="thistotalNum">1</span>份　共计：￥<span id="thistotalPrice"><%=WxMenuUtils.decimalFormat.format(procuct.getFloat("Price"))%></span>元</li>

                        <!--这里判断有无优惠（折扣）-->
                        <%
                            //判断有无已完成订单
                            List<DataField> successendList = (List<DataField>) DaoFactory.getOrderDAO().getList(wxsid, openid, 5, -1);
                        %>
                        <input type="hidden" id="discountratio" value="<%="1".equals(wx.get("preferentialtype")) && 0 != successendList.size() ? wx.get("discountratio") : 100%>"/>
                        <li class="ccbg2" id="emptyLii" style="display: <%="1".equals(wx.get("preferentialtype")) && 0 != successendList.size() ? "block" : "none"%>;"><span style="margin-right: 36px;">&nbsp;</span>会员折扣价：￥<span id="thistotaldiscountratioPrice" style="color:#D00A0A;"><%=WxMenuUtils.decimalFormat.format((float) ((int) (Float.parseFloat(procuct.getString("Price")) * Float.parseFloat(wx.get("discountratio")))) / 100)%></span>元</li>
                    </ul>

                    <ul id="cartinfo">
                        <dt>购物车总计</dt>
                        <li class="ccbg2" id="emptyLii">数量：<span id="totalNum"><%=cartnum%></span>份　共计：￥<span id="totalPrice"><%=WxMenuUtils.decimalFormat.format(totolmoney)%></span>元</li>
                    </ul>			
                </div>
                <!--属性-->
                <%
                    String propertys = procuct.getFieldValue("propertys").trim();
                    if (!"".equals(propertys) && null != propertys) {
                        String propertyArr[] = propertys.split(" ");
                        String testSign = "";
                %>
                <div class="contact-info">
                    <ul>
                        <li class="ccbg2" id="emptyLii">
                            <%
                            //0,3-10 0,3-12 0,3-13 0,3-14 0,15-16 0,15-18
                                for (int i = 0; i < propertyArr.length; i++) {
                                    if ("".equals(propertyArr[i])) {
                                        continue;
                                    }
                                    String propertySubStr = propertyArr[i].split(",")[1];
                                    String propertySubArr[] = propertySubStr.split("-");
                                    DataField property = DaoFactory.getPropertysDaoImplJDBC().get(propertySubArr[0]);
                                    DataField propertySub = DaoFactory.getPropertysDaoImplJDBC().get(propertySubStr);
                                    if (null == property || null == propertySub) {
                                        continue;
                                    }
                                    boolean flag = false;
                                    if (!property.getFieldValue("svname").equals(testSign)) {
                                        flag = true;
                                        testSign = property.getFieldValue("svname");
                            %>
                        </li>
                    </ul>
                </div>
                <div class="contact-info">
                    <ul>
                        <li class="ccbg2" id="emptyLii"><span><%=property.getFieldValue("svname")%>：</span>
                            <%
                                }
                                if (flag) {
                            %>
                            <span id="<%=propertySub.getFieldValue("svid")%>" name="<%=property.getFieldValue("svid")%>" sign="1" style="border-color: red;border-style: solid;border-width: 1px;margin:2px;padding: 3px;" onclick="javascript:propertychange('<%=propertySub.getFieldValue("svid")%>');"><%=propertySub.getFieldValue("svname")%></span>
                            <%
                            } else {
                            %>
                            <span id="<%=propertySub.getFieldValue("svid")%>"  name="<%=property.getFieldValue("svid")%>" sign="0" style="border-color: gray;border-style: solid;border-width: 1px;margin:2px;padding: 3px;" onclick="javascript:propertychange('<%=propertySub.getFieldValue("svid")%>');"><%=propertySub.getFieldValue("svname")%></span>
                            <%
                                    }
                                }
                            %>
                        </li>
                    </ul>
                </div>
                <script type="text/javascript">
                function propertychange(id) {
                    var parid = id.split("-")[0];
                    $("span[name=" + parid + "]").attr("sign", "0").css("border-color", "gray");
                    $("#" + id).attr("sign", "1").css("border-color", "red");

                    //修改价格、库存、编码
                    var propertys = "";
                    $("span[sign='1']").each(function(index) {
                        propertys += "*" + $(this).attr("id") + ",";
                    });
                    $.post("${pageContext.servletContext.contextPath}/ForeServlet?method=getPsv", {"propertys": propertys, "pid": "<%=procuct.getString("id")%>", "wxsid": "<%=wxsid%>"}, function(result) {
                        result = eval("(" + result + ")");
                        $("#price").val(parseFloat(result.price).toFixed(2));
                        $("#last_cnt").html(result.stock);
                        $("#psvcode").val(result.psvcode);
//同步数据
                        var jqueryid = "num_<%=procuct.getString("id")%>";
                        var productN = parseFloat($('#' + jqueryid).html());
                        if (Number(productN) > Number(result.stock)) {
                            $('#' + jqueryid).html(result.stock);//数量
                            $('#thistotalNum').html(result.stock);//显示
                        }
                        var thistotalNum = parseFloat($('#thistotalNum').html());
                        $('#thistotalPrice').html((parseFloat(result.price) * parseFloat(thistotalNum)).toFixed(2));
                        var thistotalPrice = $('#thistotalPrice').html();

                        var discountratio = $("#discountratio").val();
                        var thistotaldiscountratioPrice = parseFloat(thistotalPrice) * parseFloat(discountratio) / 100;
                        $("#thistotaldiscountratioPrice").html(thistotaldiscountratioPrice.toFixed(2));
                    });
                }
                </script>
                <%
                    }
                %>
                <!--属性结束-->         
                <form name="infoForm" id="infoForm" method="post" action="">
                    <div class="contact-info">
                        <ul>
                            <!--<li class="title">联系信息</li>-->
                            <%
//                                    df = DaoFactory.getAddressDAO().getUserId(openid);
                            %>
                            <li>
                                <table style="padding: 0; margin: 0; width: 100%;">
                                    <tbody>
                                        <!--                                        <tr>
                                                                                    <td width="90px"><label for="name" class="ui-input-text"><span style="color:red">*</span>联系人：</label></td>
                                                                                    <td>
                                                                                        <div class="ui-input-text">
                                                                                            <input id="name" name="name" placeholder="" value="<%=null != df ? df.getString("Name") : ""%>" type="text"
                                                                                                   class="ui-input-text">
                                                                                        </div></td>
                                                                                </tr>
                                        
                                                                                <tr>
                                                                                    <td width="90px"><label for="phone" class="ui-input-text"><span style="color:red">*</span>联系电话：</label></td>
                                                                                    <td>
                                                                                        <div class="ui-input-text">
                                                                                            <input id="phone" name="phone" placeholder="" value="<%=null != df ? df.getString("Phone") : ""%>" type="tel"
                                                                                                   class="ui-input-text">
                                                                                        </div>
                                                                                    </td>
                                                                                </tr>
                                                                                tr>
                                                                                        <td width="80px"><label for="pay" class="ui-input-text">支付方式：</label></td>
                                                                                        <td colspan="2"><select name="pay" class="selectstyle"
                                                                                                id="select1">
                                                                                                        <option value="0">货到付款</option>
                                                                                                                                                                                                    <option value="2">微信支付</option>
                                                                                        </select></td>
                                                                                </tr
                                                                                <tr>
                                                                                    <td width="90px"><label for="weixin" class="ui-input-text"><span style="color:red">*</span>微信号：</label></td>
                                                                                    <td>
                                                                                        <div class="ui-input-text">
                                                                                            <input id="weixin" name="weixin" placeholder="" value="<%=null != df ? df.getString("Weixin") : ""%>" type="text"
                                                                                                   class="ui-input-text">
                                                                                        </div></td>
                                                                                </tr>
                                                                                <tr>
                                                                                    <td width="90px"><label for="address"
                                                                                                            class="ui-input-text"><span style="color:red">*</span>联系地址：</label></td>
                                                                                    <td>
                                                                                        <select id="s_province" name="s_province" style="display:none;"></select>  
                                                                                        <select id="s_city" name="s_city"  style="display:none;"></select>  
                                                                                        <select id="s_county" name="s_county"  style="display:none;"></select>
                                                                                        <script type="text/javascript">_init_area();</script>
                                                                                        <div id="show"></div>
                                        
                                                                                        <textarea id="address" name="address" placeholder=""
                                                                                                  value="" class="ui-input-text"><%=null != df ? df.getString("Address") : ""%></textarea>
                                        
                                                                                        <script>
                                                                                            var province_check = false;
                                                                                        </script>
                                                                                    </td>
                                                                                </tr>
                                        
                                                                            <script type="text/javascript">
                                                                                var Gid = document.getElementById;
                                                                                var showArea = function() {
                                                                                    Gid('show').innerHTML = "<h3>省" + Gid('s_province').value + " - 市" +
                                                                                            Gid('s_city').value + " - 县/区" +
                                                                                            Gid('s_county').value + "</h3>"
                                                                                }
                                                                                Gid('s_county').setAttribute('onchange', 'showArea()');
                                                                            </script>
                                        
                                                                            <tr id='type_siz1' style="display:none;" show=0>
                                                                                <td width="90px"><label for="note" class="ui-input-text" id="guigename1"></label></td>
                                                                                <td>
                                                                                    <div class="mui-sku" id='guigevalue1'>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                        
                                                                            <tr id='type_siz2' style="display:none;" show=0>
                                                                                <td width="90px"><label for="note" class="ui-input-text" id="guigename2"></label></td>
                                                                                <td>
                                                                                    <div class="mui-sku" id='guigevalue2'>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>-->

                                        <tr>
                                            <td width="90px"><label for="note" class="ui-input-text">备注：</label></td>
                                            <td><textarea name="note" placeholder=""
                                                          class="ui-input-text"><%=null != df ? df.getString("Remark") : ""%></textarea></td>
                                        </tr>
                                    </tbody>
                                </table>
                                <p style="height: 50px;">&nbsp;</p>
                                <div class="footermenu">
                                    <ul>
                                        <li><a href="/shop2/shop.jsp?wx=<%=wxsid%>&openid=<%=openid%>&act="> <img src="/img/index.png">
                                                <p class="textcolor">首页</p>
                                            </a></li>
                                        <li id="add_cart"><a href="javascript:void(0);" id="addshopbutton" onclick="javascript:act('<%=id%>', 'addshop');"> <span class="num" id="cartN3"><%=cartnum%></span> <img
                                                    src="/img/shop.png">
                                                <p class="textcolor">加入购物车</p>
                                            </a></li>

                                        <li><a href="javascript:void(0);" id="buybutton" onclick="javascript:act('<%=id%>', 'buy');">  <img src="/img/order.png">
                                                <p class="textcolor">立刻下单</p>
                                            </a></li>
                                    </ul>
                                    <!--<a id="showcard" class="submit" href="javascript:submitOrder();">立即购买</a>-->
                                </div>

                            </li>
                        </ul>
                    </div>
                </form>		
            </section>
            <script type='text/javascript'>
                function act(id, act) {
                    var last_cnt = $("#last_cnt").html();
                    var count = $(".count").html();
                    var propertys = "";
                    $("span[sign='1']").each(function(index) {
                        propertys += " " + $(this).attr("id");
                    });
//                    var name = $('#name').val();
//                    var phone = $('#phone').val();
//                    var weixin = $('#weixin').val();
//                    var address = $('#address').val();
                    if (Number(last_cnt) < Number(count) || 0 == Number(last_cnt)) {
                        alert("该规格的产品已经卖完了！");
                        return false;
                    } else
                    if (0 == Number(count)) {
                        alert("购买数量不正确！");
                        return false;
                    }
//                    else if (name.length <= 0)
//                    {
//                        alert('请输入联系人');
//                        return false;
//                    } else
//
//                    if (phone.length <= 0)
//                    {
//                        alert('请输入电话');
//                        return false;
//                    } else
//                    if (weixin.length <= 0)
//                    {
//                        alert('请输入微信号');
//                        return false;
//                    } else
//
//                    if (address.length <= 0)
//                    {
//                        alert('请输入地址');
//                        return false;
//                    } 
                    else {
                        $("#addshopbutton").attr("disabled", true);
                        $("#buybutton").attr("disabled", true);
                        var infoForm = $("#infoForm").serialize();
                        if ("addshop" == act) {
                            $.post("../shop3/public_do.jsp?id=" + id + "&count=" + count + "&act=" + act + "&propertys=" + propertys + "&wx=<%=wxsid%>&openid=<%=openid%>", infoForm, function(result) {
                                if ("1" != result.trim()) {
                                    alert("加入购物车！");
                                    setTimeout(function() {
                                        window.location.replace("../shop2/shop.jsp?wx=<%=wxsid%>&openid=<%=openid%>");
                                    }, 3000);
                                } else {
                                    alert('系统繁忙，请稍后重试！');
                                    window.go(-1);
                                }
//                                var form = document.getElementById("infoForm");
//                                form.action = "?id=" + id + "&count=" + count + "&act=" + act + "&propertys=" + propertys + "&wx=<%=wxsid%>&openid=<%=openid%>";
//                                form.submit();
                            });
                        } else if ("buy" == act) {
                            $.post("../shop3/public_do.jsp?id=" + id + "&count=" + count + "&act=" + act + "&propertys=" + propertys + "&wx=<%=wxsid%>&openid=<%=openid%>", infoForm, function(result) {
                                if ("1" != result.trim()) {
                                    window.location.replace("../shop3/myshop.jsp?id=" + id + "&count=" + count + "&act=" + act + "&propertys=" + propertys + "&wx=<%=wxsid%>&openid=<%=openid%>");
                                } else {
                                    alert('系统繁忙，请稍后重试！');
                                    window.go(-1);
                                }
                            });
//                            var form = document.getElementById("infoForm");
//                            form.action = "/shop3/myshop.jsp?id=" + id + "&count=" + count + "&act=" + act + "&propertys=" + propertys + "&wx=<%=wxsid%>&openid=<%=openid%>";
//                            form.submit();
                        }
                    }
                }
            </script>

            <!-- 正在提交数据 -->
            <div id="menu-shadow" hidefocus="true"
                 style="display: none; z-index: 10;">
                <div class="btn-group"
                     style="position: fixed; font-size: 12px; width: 220px; bottom: 80px; left: 50%; margin-left: -110px; z-index: 999;">
                    <div class="del" style="font-size: 14px;">
                        <img src="/Application/Tpl/App/shop/Public/Static/images/ajax-loader.gif" alt="loader">正在提交订单...
                    </div>
                </div>
            </div>

        </div>
        <%}
            if ("cart".equals(act)) {
                List<DataField> cartList = (List<DataField>) DaoFactory.getBasketDAO().getListByUidp(openid, wxsid, 1, -1, -1);
        %>
        <script type='text/javascript'>
            function delshops(id) {
                if (confirm("确认删除？")) {
                    location.replace("?act=delshop&id=" + id + "&wx=<%=wxsid%>&openid=<%=openid%>");
                }
            }
        </script>
        <div id="user-container">

            <div class="menu_header">
                <div class="menu_topbar">
                    <div id="menu" class="sort ">
                        <a href="?act=cart&wx=<%=wxsid%>&openid=<%=openid%>">我的购物车</a>
                    </div>
                </div>
            </div>

            <div>
                <ul class="round" style="margin:0;padding:0;border-radius:0;border:0px;border-bottom:1px solid #C6C6C6">
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="cpbiaoge">
                        <tr>
                            <td> <span>购物详情</span> <span style='float:right'><a href='?'>继续购物>>></a></span> </td>
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
                            <img src="/Application/Tpl/App/shop/Public/Static/images/ajax-loader.gif" alt="loader">正在获取购物车...
                        </div>
                    </div>
                </div>
                <ul class="round"  id="orderlistinsert" style='color:#000;font-size:12px;'>
                    <!--插入订单ul-->
                    <%
                        if (0 < cartList.size()) {
                            for (DataField cart : cartList) {
                    %>

                    <li style="border: 1px solid #d0d0d0;border-radius: 10px;margin-bottom:10px;background-color:<%=wx.get("weishopcolor")%>;">
                        <table><tbody>
                                <tr>
                                    <td style="border-bottom:0px" colspan="3">
                                        <input type="checkbox" name="carts" value="<%=cart.getFieldValue("id")%>" checked=""/>
                                        <p/><input type="button" value="删除" onclick="delshops(<%=cart.getFieldValue("id")%>);"/>
                                    </td>
                                    <td style="border-bottom:0px" colspan="3"><img src="<%=cart.getFieldValue("ViewImg")%>" width="90px;" height="60px;"/></td>
                                    <td style="border-bottom:0px"><%=cart.getFieldValue("Pid")%><p/>
                                        <%=cart.getFieldValue("Pname")%><p/>
                                        <font color="red"><%=cart.getFieldValue("Tot_Price")%></font>
                                    </td></tr>
                            </tbody>
                        </table>
                    </li>
                    <%}
                    } else {
                    %>
                    <li style="border: 1px solid #d0d0d0;border-radius: 10px;margin-bottom:10px;background-color:<%=wx.get("weishopcolor")%>;">
                        <table><tbody>
                                <tr><td style="border-bottom:0px">您的购物车暂无任何商品！</td></tr>
                            </tbody>
                        </table>
                    </li>
                    <%
                        }
                    %>
                </ul>
            </div>
        </div>
        <%
            if (0 < cartList.size()) {
        %>
        <form name="infoForm2" id="infoForm2" method="post" action="">
            <div class="contact-info">
                <ul>
                    <li class="title">联系信息</li>
                        <%
                            df = DaoFactory.getAddressDAO().getUserId(openid, wxsid);
                        %>
                    <li>
                        <table style="padding: 0; margin: 0; width: 100%;">
                            <tbody>
                                <tr>
                                    <td width="90px"><label for="name" class="ui-input-text"><span style="color:red">*</span>联系人：</label></td>
                                    <td>
                                        <div class="ui-input-text">
                                            <input id="name" name="name" placeholder="" value="<%=null != df ? df.getString("Name") : ""%>" type="text"
                                                   class="ui-input-text">
                                        </div></td>
                                </tr>

                                <tr>
                                    <td width="90px"><label for="phone" class="ui-input-text"><span style="color:red">*</span>联系电话：</label></td>
                                    <td>
                                        <div class="ui-input-text">
                                            <input id="phone" name="phone" placeholder="" value="<%=null != df ? df.getString("Phone") : ""%>" type="tel"
                                                   class="ui-input-text">
                                        </div>
                                    </td>
                                </tr>
                                <!--                                tr>
                                                            <td width="80px"><label for="pay" class="ui-input-text">支付方式：</label></td>
                                                            <td colspan="2"><select name="pay" class="selectstyle"
                                                                                    id="select1">
                                                                    <option value="0">货到付款</option>
                                                                    <option value="2">微信支付</option>
                                                                </select></td>
                                                            </tr-->
                                <tr>
                                    <td width="90px"><label for="weixin" class="ui-input-text"><span style="color:red">*</span>微信号：</label></td>
                                    <td>
                                        <div class="ui-input-text">
                                            <input id="weixin" name="weixin" placeholder="" value="<%=null != df ? df.getString("Weixin") : ""%>" type="text"
                                                   class="ui-input-text">
                                        </div></td>
                                </tr>
                                <tr>
                                    <td width="90px"><label for="address"
                                                            class="ui-input-text"><span style="color:red">*</span>联系地址：</label></td>
                                    <td>
                                        <select id="s_province" name="s_province" style="display:none;"></select>  
                                        <select id="s_city" name="s_city"  style="display:none;"></select>  
                                        <select id="s_county" name="s_county"  style="display:none;"></select>
                                        <script type="text/javascript">_init_area();</script>
                                        <div id="show"></div>

                                        <textarea id="address" name="address" placeholder=""
                                                  value="" class="ui-input-text"><%=null != df ? df.getString("Address") : ""%></textarea>

                                        <script>
                                            var province_check = false;
                                        </script>
                                    </td>
                                </tr>

                            <script type="text/javascript">
                                var Gid = document.getElementById;
                                var showArea = function() {
                                    Gid('show').innerHTML = "<h3>省" + Gid('s_province').value + " - 市" +
                                            Gid('s_city').value + " - 县/区" +
                                            Gid('s_county').value + "</h3>"
                                };
                                Gid('s_county').setAttribute('onchange', 'showArea()');
                            </script>

                            <tr id='type_siz1' style="display:none;" show=0>
                                <td width="90px"><label for="note" class="ui-input-text" id="guigename1"></label></td>
                                <td>
                                    <div class="mui-sku" id='guigevalue1'>
                                    </div>
                                </td>
                            </tr>

                            <tr id='type_siz2' style="display:none;" show=0>
                                <td width="90px"><label for="note" class="ui-input-text" id="guigename2"></label></td>
                                <td>
                                    <div class="mui-sku" id='guigevalue2'>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </li>
                </ul>
            </div>
        </form>		
        <%}%>
        <p style="height: 50px;">&nbsp;</p>
        <div class="footermenu">
            <ul>
                <li id="add_cart"><a href="/shop2/shop.jsp?act=&wx=<%=wxsid%>&openid=<%=openid%>"> <span class="num" id="cartN3"><%=cartnum%></span> <img
                            src="/img/index.png">
                        <p class="textcolor">继续购物</p>
                    </a></li>

                <li><a href="javascript:addorder();">  <img src="/img/order.png">
                        <p class="textcolor">立刻下单</p>
                    </a></li>
            </ul>
            <!--<a id="showcard" class="submit" href="javascript:submitOrder();">立即购买</a>-->
        </div>
        <script type='text/javascript'>
            function addorder() {
                var cartArr = $('input:checkbox[name=carts]:checked');
                if (0 >= cartArr.length) {
                    alert("您没有选择任何产品，请先选择产品吧！");
//                    setTimeout(function() {
//                        window.location.replace("/shop2/shop.jsp?wx=<%=wxsid%>&openid=<%=openid%>")
//                    }, 3000);
                    return false;

                } else {
                    var carts = "";
                    for (var i = 0; i < cartArr.length; i++) {
                        carts += cartArr[i].value + ",";
                    }

                    var name = $('#name').val();
                    var phone = $('#phone').val();
                    var weixin = $('#weixin').val();
                    var address = $('#address').val();
                    if (name.length <= 0)
                    {
                        alert('请输入联系人');
                        return false;
                    } else

                    if (phone.length <= 0)
                    {
                        alert('请输入电话');
                        return false;
                    } else
                    if (weixin.length <= 0)
                    {
                        alert('请输入微信号');
                        return false;
                    } else

                    if (address.length <= 0)
                    {
                        alert('请输入地址');
                        return false;
                    } else {
                        var form = document.getElementById("infoForm2");
                        form.action = "?carts=" + carts + "&act=addorder&wx=<%=wxsid%>&openid=<%=openid%>";
                        form.submit();
                    }
                }
            }
        </script>
        <%}
            if ("orderdetail".equals(act)) {
                DataField order = DaoFactory.getOrderDAO().get(F_No);
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
                            <td> <span>订单详情</span></td>
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
                    <li style="border: 1px solid #d0d0d0;border-radius: 10px;margin-bottom:10px;background-color:<%=wx.get("weishopcolor")%>;">收货人信息</li>
                    <li style="border: 1px solid #d0d0d0;border-radius: 10px;margin-bottom:10px;background-color:<%=wx.get("weishopcolor")%>;">
                        <table><tbody>
                                <tr><td style="border-bottom:0px">联系人:<%=order.getFieldValue("Name")%></td></tr>
                                <tr><td style="border-bottom:0px">联系电话:<%=order.getFieldValue("Phone")%></td></tr>
                                <tr><td style="border-bottom:0px">联系地址:<%=order.getFieldValue("Address")%></td></tr>
                            </tbody></table>
                    </li>
                    <p>&nbsp;</p>
                    <li style="border: 1px solid #d0d0d0;border-radius: 10px;margin-bottom:10px;background-color:<%=wx.get("weishopcolor")%>;">产品信息</li>
                        <%
                            List<DataField> orderdetailList = (List<DataField>) DaoFactory.getBasketDAO().getBySuserIdUidNoList(wxsid, openid, order.getFieldValue("F_No"));
                            for (DataField orderdetail : orderdetailList) {
                        %>
                    <li style="border: 1px solid #d0d0d0;border-radius: 10px;margin-bottom:10px;background-color:<%=wx.get("weishopcolor")%>;">
                        <table><tbody>
                                <tr><td style="border-bottom:0px;" colspan="2"><img src='<%=orderdetail.getFieldValue("ViewImg")%>' width='90px' height="90px"/></td>
                                    <td style="border-bottom:0px"><%=orderdetail.getFieldValue("Pname")%></td>
                                    <td style="border-bottom:0px">￥<%=orderdetail.getFieldValue("Per_Price")%>*<%=orderdetail.getFieldValue("Pnum")%></td></tr>
                            </tbody></table>
                    </li>
                    <%
                        }
                    %>
                    <li style="border: 1px solid #d0d0d0;border-radius: 10px;margin-bottom:10px;background-color:<%=wx.get("weishopcolor")%>; color: red">购物合计总金额：<%=order.getFieldValue("SF_Price")%>元</li>
                </ul>
                <a id="showcard" class="submit" href="/ForeServlet?method=pay&F_No=<%=order.getFieldValue("F_No")%>&wxsid=<%=wxsid%>&openid=<%=openid%>">微信支付</a>
            </div>
        </div>
        <%
            }
        %>
        <style>

        </style>
        <!--dl class="sub-nav nav-b5">
                  <dd class="active">
                        <div class="nav-b5-relative"><a href="index.aspx"><i class="icon-nav-store"></i>首页</a></div>
                </dd>
                <dd>
                        <div class="nav-b5-relative"><a href="category.aspx"><i class="icon-nav-search"></i>订单</a></div>
                </dd>
                <dd>
                          <div class="nav-b5-relative"><a href="javascript:void(0)"><i class="icon-nav-bag"></i>购买</a></div>
                </dd>
                <dd>
                        <div class="nav-b5-relative"><a href="member/member.aspx"><i class="icon-nav-heart"></i>会员</a></div>
                </dd>
        </dl-->



        <!--    <div class="footermenu">
        
                <ul>
                    <li><a class="active" href="./index.php?g=App&m=Index&a=index_info"> <img
                                src="/Application/Tpl/App/shop/Public/Static/images/home.png">
                            <p>首页</p>
                        </a></li>
        
                    <li><a href="./index.php?g=App&m=Index&a=member&page_type=order">  <img
                                src="/Application/Tpl/App/shop/Public/Static/images/22.png">
                            <p>订单</p>
                        </a></li>
        
                    <li id="cart" style="display:none;"><a href="javascript:void(0);"> <span class="num" id="cartN2">0</span> <img
                                src="/Application/Tpl/App/shop/Public/Static/images/buy.png">
                            <p>购买</p>
                        </a></li>
        
                    <li><a href="./index.php?g=App&m=Index&a=member">  <img
                                src="/Application/Tpl/App/shop/Public/Static/images/menu.png">
                            <p>掌柜中心</p>
                        </a></li>
                </ul>
        
            </div>-->

        <!--    <div class="footermenu" style="z-index:1000">
                                                <ul>
                                                                                                        <li><a href="./index.php?g=App&amp;m=Index&amp;a=index"> <img src="/Application/Tpl/App/shop/Public/Static/images/home.png">
                                                                        <p>返回</p>
                                                        </a></li>
        
                                                        <li id="add_cart" style="display:none;"><a href="javascript:void(0);"> <span class="num" id="cartN3">0</span> <img src="/Application/Tpl/App/shop/Public/Static/images/cart.png">
                                                                        <p>加入购物车</p>
                                                        </a></li>
        
                                                        <li><a onclick="now_buy();">  <img src="/Application/Tpl/App/shop/Public/Static/images/22.png">
                                                                <p>立刻购买</p>
                                                        </a></li>
                                                                                                </ul>
                                        </div>-->

        <script>
            $('#good').click(function() {
                showMenu();
            });
            $('body').show();
        </script>
    </body>
</html>
