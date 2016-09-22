<%-- 
    Document   : top
    Created on : 2015-7-30, 15:09:52
    Author     : Administrator
--%>

<%@page import="wap.wx.service.SubscriberService"%>
<%@page import="wap.wx.dao.WxsDAO"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="java.util.Date"%>
<%@page import="wap.wx.menu.WxMenuUtils"%>
<%@page import="wap.wx.dao.SubscriberDAO"%>
<%@page import="java.util.Map"%>
<%@page import="job.tot.util.RequestUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String wxsid = RequestUtil.getString(request, "wxsid");
    String openid = RequestUtil.getString(request, "openid");
    Map<String, String> subscriber = new SubscriberDAO().getByOpenid(wxsid, openid);
    Map<String, String> wx = new HashMap<String, String>();
    wx.put("id", wxsid);
    wx = new WxsDAO().getById(wx);
//为空时存入
    if (null == subscriber.get("id")) {
        subscriber = new SubscriberService().addSubscriber(wx, openid);
    }

    //补充 更新vipid
    SubscriberDAO subscriberDAO = new SubscriberDAO();
    int paiming = subscriberDAO.getPaiMing(wxsid, Integer.parseInt(subscriber.get("id")));
    System.out.println("paiming " + paiming);
    subscriberDAO.updateVipid(openid, wxsid, String.valueOf(paiming + 1));
    subscriber = new SubscriberDAO().getByOpenid(wxsid, openid);

%>
<div class="div_header">
    <span style='float:left;margin-left:10px;margin-right:10px;'>
        <img src='<%=null != subscriber.get("headimgurl") ? subscriber.get("headimgurl") : "/Application/Tpl/App/shop/Public/Static/images/defult.jpg"%>' width='70px;' height='70px;'>			</span>
    <!--<span class="header_right">-->
    <div class="header_l_di"><span>昵称：<%=null != subscriber.get("username") && !"".equals(subscriber.get("username")) ? subscriber.get("username") : (null != subscriber.get("nickname") ? subscriber.get("nickname") : "")%></span>&nbsp;&nbsp;					
        <a style='color:red' href="${pageContext.servletContext.contextPath}/shop2/modinfo.jsp?wx=<%=wxsid%>&openid=<%=openid%>">账号设置</a></div>
    <div><span>会员ID：<%="1".equals(subscriber.get("isvip")) ? subscriber.get("id") : "否(<a style='color:red' href='/shop2/shop.jsp?wx=" + wxsid + "&openid=" + openid + "'>点击链接成为会员</a>)"%></span></div>
    <div>会员：<%=Integer.parseInt(subscriber.get("vipid")) + Integer.parseInt(wx.get("vipidbasic"))%> </div>
    <div><span>关注时间：<%=subscriber.get("times").split(" ")[0]%></span></div>
    <!--span>积分：0</span-->
    <!--</span>-->
</div>
</div>