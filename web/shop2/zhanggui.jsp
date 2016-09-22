<%-- 
    Document   : test
    Created on : 2015-7-30, 13:30:19
    Author     : Administrator
--%>

<%@page import="wap.wx.util.DbConn"%>
<%@page import="java.util.ArrayList"%>
<%@page import="job.tot.bean.DataField"%>
<%@page import="wap.wx.dao.SubscriberDAO"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="job.tot.util.RequestUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../shop/inc/common.jsp"%>
<!DOCTYPE html>

<html>
    <%        if (null != RequestUtil.getString(request, "wxsid")) {
            wxsid = RequestUtil.getString(request, "wxsid");
            session.setAttribute("wxsid", wxsid);
        }
        if (null != RequestUtil.getString(request, "openid")) {
            //此处可嵌入网页获取openid代码，也可通过图文信息获得
            openid = RequestUtil.getString(request, "openid");
            session.setAttribute("openid", openid);
        }
        //取出微信信息
        Map<String, String> wx = new HashMap<String, String>();
        wx.put("id", wxsid);
        wx = new WxsDAO().getById(wx);
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%=wx.get("name")%></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <link href="/Application/Tpl/App/shop/Public/Static/css/foods.css?444" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="/Application/Tpl/App/shop/Public/Static/js/jquery.min.js"></script>
        <script type="text/javascript" src="/Application/Tpl/App/shop/Public/Static/js/wemall.js"></script>
        <script type="text/javascript" src="/Application/Tpl/App/shop/Public/Static/js/alert.js"></script>
        <script type="text/javascript">
            var appurl = '/index.php';
            var rooturl = '';

        </script>

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

        <style>

            .pagination {
                display: inline-block;
                padding-left: 0;
                margin: 20px 0;
                border-radius: 4px
            }

            .pagination > li {
                display: inline
            }

            .pagination > li > a, .pagination > li > span {
                position: relative;
                float: left;
                padding: 6px 12px;
                margin-left: -1px;
                line-height: 1.428571429;
                text-decoration: none;
                background-color: #fff;
                border: 1px solid #ddd
            }

            .pagination > li:first-child > a, .pagination > li:first-child > span {
                margin-left: 0;
                border-bottom-left-radius: 4px;
                border-top-left-radius: 4px
            }

            .pagination > li:last-child > a, .pagination > li:last-child > span {
                border-top-right-radius: 4px;
                border-bottom-right-radius: 4px
            }

            .pagination > li > a:hover, .pagination > li > span:hover, .pagination > li > a:focus, .pagination > li > span:focus {
                background-color: #eee
            }

            .pagination > .active > a, .pagination > .active > span, .pagination > .active > a:hover, .pagination > .active > span:hover, .pagination > .active > a:focus, .pagination > .active > span:focus {
                z-index: 2;
                color: #fff;
                cursor: default;
                background-color: #428bca;
                border-color: #428bca
            }

            .pagination > .disabled > span, .pagination > .disabled > a, .pagination > .disabled > a:hover, .pagination > .disabled > a:focus {
                color: #999;
                cursor: not-allowed;
                background-color: #fff;
                border-color: #ddd
            }

            .pagination-lg > li > a, .pagination-lg > li > span {
                padding: 10px 16px;
                font-size: 18px
            }

            .pagination-lg > li:first-child > a, .pagination-lg > li:first-child > span {
                border-bottom-left-radius: 6px;
                border-top-left-radius: 6px
            }

            .pagination-lg > li:last-child > a, .pagination-lg > li:last-child > span {
                border-top-right-radius: 6px;
                border-bottom-right-radius: 6px
            }

            .pagination-sm > li > a, .pagination-sm > li > span {
                padding: 5px 10px;
                font-size: 12px
            }

            .pagination-sm > li:first-child > a, .pagination-sm > li:first-child > span {
                border-bottom-left-radius: 3px;
                border-top-left-radius: 3px
            }

            .pagination-sm > li:last-child > a, .pagination-sm > li:last-child > span {
                border-top-right-radius: 3px;
                border-bottom-right-radius: 3px
            }

            .pager {
                padding-left: 0;
                margin: 20px 0;
                text-align: center;
                list-style: none
            }

            .pager:before, .pager:after {
                display: table;
                content: " "
            }

            .pager:after {
                clear: both
            }

            .pager:before, .pager:after {
                display: table;
                content: " "
            }

            .pager:after {
                clear: both
            }

            .pager li {
                display: inline
            }

            .pager li > a, .pager li > span {
                display: inline-block;
                padding: 5px 14px;
                background-color: #fff;
                border: 1px solid #ddd;
                border-radius: 15px
            }

            .pager li > a:hover, .pager li > a:focus {
                text-decoration: none;
                background-color: #eee
            }

            .pager .next > a, .pager .next > span {
                float: right
            }

            .pager .previous > a, .pager .previous > span {
                float: left
            }

            .pager .disabled > a, .pager .disabled > a:hover, .pager .disabled > a:focus, .pager .disabled > span {
                color: #999;
                cursor: not-allowed;
                background-color: #fff
            }

        </style>
    </head>
    <%
        String type = RequestUtil.getString(request, "type");
    %>
    <body class="sanckbg mode_webapp">

        <div id="member-container" style="display: block;">

            <jsp:include page="top.jsp">
                <jsp:param name="wxsid" value="<%=wxsid%>"></jsp:param>
                <jsp:param name="openid" value="<%=openid%>"></jsp:param>
            </jsp:include>

            <%
                SubscriberDAO subscriberDAO = new SubscriberDAO();

                List<Map<String, String>> list = null;
                String title = "";
                Connection conn = DbConn.getConn();
                Map<String, String> sub = subscriberDAO.getByOpenid(wxsid, openid);
                List<Map<String, String>> firstsubscriberList = subscriberDAO.getByParentopenidList(wxsid, sub.get("id"), conn);
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
                }
//                System.out.println("firstsubscriber " + firstsubscriberList.size());
                List<Map<String, String>> secondsubscriberList = new ArrayList<Map<String, String>>();
                List<Map<String, String>> thirdsubscriberList = new ArrayList<Map<String, String>>();
                for (Map<String, String> firstsubscriber : firstsubscriberList) {
                    List<Map<String, String>> tempsecondsubscriberList = subscriberDAO.getByParentopenidList(wxsid, firstsubscriber.get("id"), conn);
                    secondsubscriberList.addAll(tempsecondsubscriberList);
//                    System.out.println("secondsubscriberList " + secondsubscriberList.size());
                    for (Map<String, String> secondsubscriber : tempsecondsubscriberList) {
                        thirdsubscriberList.addAll(subscriberDAO.getByParentopenidList(wxsid, secondsubscriber.get("id"), conn));
//                        System.out.println("thirdsubscriberList " + thirdsubscriberList.size());
                    }
                }
                if ("1".equals(type)) {
                    list = firstsubscriberList;
                    title = wx.get("subtitle1");
                }
                if ("2".equals(type)) {
                    list = secondsubscriberList;
                    title = wx.get("subtitle2");
                }
                if ("3".equals(type)) {
                    list = thirdsubscriberList;
                    title = wx.get("subtitle3");
                }

            %>
            <div class="div_table bg_color" style='height:20px;padding:10px;'>
                <table>
                    <tr><td style="color: <%=wx.get("weishoptextcolor")%>"><%=wx.get("title1")%>：<%=title%>(<%=null != list ? list.size() : 0%>)人</td></tr>
                </table>
            </div>

            <div style="text-align:center; margin:5 auto;">
                <span ><input style="width:70%;border: 1px solid #D00A0A;height: 22px;"  id="name" name="name" placeholder="请输入会员ID" value="" type="text" ></span>
                <span><input style="width:16%;margin-left:10px;border: 1px solid #D00A0A;height: 22px;" type='button' onclick='search_user();' value='搜索'></span>
            </div>
            <p>&nbsp;</p>

            <%
                if ("0".equals(type)) {
                    list = new ArrayList<Map<String, String>>();
                    list.clear();
                    String vipid = RequestUtil.getString(request, "vipid");
                    Map<String, String> map = subscriberDAO.getById(wxsid, vipid);// .getByVipid(wxsid, vipid);
                    if (null != map.get("openid")) {
                        list.add(map);
                    }
                }
                for (Map<String, String> subscriber : list) {
            %>
            <div class="">
                <span style='float:left;margin-left:10px;margin-right:10px;'>
                    <img src='<%=null != subscriber.get("headimgurl") ? subscriber.get("headimgurl") : "/Application/Tpl/App/shop/Public/Static/images/defult.jpg"%>' width='70px;' height='70px;'>			</span>
                <!--<span class="header_right">-->
                <div class="header_l_di"><span>昵称：<%=null != subscriber.get("nickname") ? subscriber.get("nickname") : ""%></span>&nbsp;&nbsp;
                    <div><span>会员：<%="1".equals(subscriber.get("isvip")) ? "是" : "否"%></span></div>
                    <div>会员ID：<%=subscriber.get("id")%> </div>
                    <div>
                        <span>关注时间：<%=subscriber.get("times").split(" ")[0]%></span>
                        <a href="${pageContext.servletContext.contextPath}/shop/chatroom.jsp?openid=<%=openid%>&targetopenid=<%=subscriber.get("openid")%>&wx=<%=wxsid%>" style="color: red;">留言</a>
                    </div>
                    <!--/fore/chatroom/index.jsp-->
                    <!--span>积分：0</span-->
                    <!--</span>-->
                </div>
                <%                    }
                %>
                <div style="text-align:center; margin:5 auto;">
                    <span ><textarea style="width:80%;border:1px solid #D00A0A;display:none;" placeholder="请输入留言内容" rows="3" cols="20"  class="liuyna" id="liuyna"> </textarea></span>
                    <span><input style="width:10%;margin-left:10px;border: 1px solid #D00A0A;height: 22px;display:none;" type='button' class="liuyna" onclick='send_user();' value='发送'></span>

                </div>

                <div class="cardexplain">
                    <ul class="round_user">
                    </ul>
                </div>
            </div>
            <p style="height: 50px;">&nbsp;</p>
            <jsp:include page="bottom.jsp">
                <jsp:param name="wxsid" value="<%=wxsid%>"></jsp:param>
                <jsp:param name="openid" value="<%=openid%>"></jsp:param>
            </jsp:include>

            <script>
            var send_user_id = 0;
            function liuyna(user_id)
            {
                send_user_id = user_id;
                $('.liuyna').show();
            }

            function send_user()
            {
                var liuyan = $('#liuyna').val();

                if (liuyan.length <= 0)
                {
                    alert('请设置留言内容');
                    return false;
                }

                if (send_user_id.length <= 0)
                {
                    alert('请选择留言对象');
                    return false;
                }

                $.ajax({
                    type: 'post',
                    url: appurl + '/App/Index/sendliuyan',
                    data: {
                        send_user_id: send_user_id,
                        liuyan: liuyan
                    },
                    success: function(response, status, xhr) {
                        alert('留言已经发出');
                    }
                });
            }

            function search_user()
            {
                var user = $('#name').val();
                location.href = "zhanggui.jsp?type=0&wxsid=<%=wxsid%>&openid=<%=openid%>&vipid=" + user;
            }
            </script>

            <div class="pagination" style="margin:0 auto;">
            </div>
    </body>
</html>
