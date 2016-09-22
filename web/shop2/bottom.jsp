<%-- 
    Document   : bottom
    Created on : 2015-7-31, 8:35:28
    Author     : Administrator
--%>

<%@page import="job.tot.util.RequestUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String wxsid = RequestUtil.getString(request, "wxsid");
    String openid = RequestUtil.getString(request, "openid");
    String act = RequestUtil.getString(request, "act");
%>
<div class="footermenu">
    <ul>
        <!--%="order".equals(act) ? "class=\"active\"" : ""%>-->
        <li ><a href="/shop2/shop.jsp?act=&wx=<%=wxsid%>&openid=<%=openid%>"> <img src="/img/index.png">
                <p class="textcolor" style="margin-top: -3px;">首页</p>
            </a></li>
        <li id="cart"><a href="/shop2/vip.jsp?act=order&wx=<%=wxsid%>&openid=<%=openid%>"> <img src="/img/order.png">
                <p class="textcolor" style="margin-top: -3px;">我的订单</p>
            </a></li>
        <li id="home"><a href="/shop2/vip.jsp?act=&wx=<%=wxsid%>&openid=<%=openid%>"> <img src="/img/vip.png">
                <p class="textcolor" style="margin-top: -3px;">会员中心</p>
            </a></li>
        <li id="ticket"><a href="/shop2/vip.jsp?act=qrcode&wx=<%=wxsid%>&openid=<%=openid%>"> <img src="/img/qrcode.png">
                <p class="textcolor" style="margin-top: -3px;">我的二维码</p>
            </a></li>
        <li style='display:none;' id="tx"><a href="javascript:void(0);"> <img src="/Application/Tpl/App/shop/Public/Static/images/24.png">
                <p class="textcolor" style="margin-top: -3px;">我的二维码</p>
            </a></li>
    </ul>
</div>