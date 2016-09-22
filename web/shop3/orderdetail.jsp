<%-- 
    Document   : orderdetail
    Created on : 2015-8-27, 16:49:08
    Author     : Administrator
--%>

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
//取出微信信息
        Map<String, String> wx = new HashMap<String, String>();
        wx.put("id", wxsid);
        wx = new WxsDAO().getById(wx);
    %>
    <head>
        <meta http-equiv="Content-Type" content="textml; charset=utf-8">
        <meta http-equiv=”Expires” CONTENT=”0″>
        <meta http-equiv=”Cache-Control” CONTENT=”no-cache”>
        <meta http-equiv=”Pragma” CONTENT=”no-cache”>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,  user-scalable=0">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="format-detection" content="telephone=no">
        <title><%=wx.get("name")%></title>
        <link rel="stylesheet" type="text/css" href="css/css.css">
        <style>
            .xz001{ width:100%; height:50px; line-height:50px; font-size:20px; text-align:center; background-color:<%=wx.get("weimyshopcolor")%>; color:<%=wx.get("weimyshoptextcolor")%>; text-decoration:none}
            .xz003 input{ width:90%; height:45px; line-height:45px; font-size:18px; text-align:center; background-color:<%=wx.get("weimyshopcolor")%>; color:#fff; border-radius:30px; border:none; line-height:40px;}
            .xz003 input[type=button]{ width:90%; height:45px; line-height:45px; font-size:18px; text-align:center; background-color:<%=wx.get("weimyshopcolor")%>; color:<%=wx.get("weimyshoptextcolor")%>; border-radius:30px; border:none; line-height:40px;cursor: pointer; -webkit-appearance: none;}
        </style>
    </head>
    <%    String F_No = RequestUtil.getString(request, "F_No");
        DataField order = DaoFactory.getOrderDAO().get(F_No);
        DataField province = DaoFactory.getAreaDaoImplJDBC().get(order.getFieldValue("provience"));
        DataField city = DaoFactory.getAreaDaoImplJDBC().get(order.getFieldValue("city"));
        DataField area = DaoFactory.getAreaDaoImplJDBC().get(order.getFieldValue("area"));
    %>
    <body>
        <div class="xz001"><%=wx.get("name")%></div>
        <div class="zong">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="xz006">
                <tr>
                    <td height="32">订单号：<%=order.getFieldValue("F_No")%></td>
                </tr>
                <tr>
                    <td width="100%" height="29" bgcolor="#DADADA">&nbsp;&nbsp;收货人信息</td>
                </tr>
                <tr>
                    <td height="32">联系人：<%=order.getFieldValue("Name")%></td>
                </tr>
                <tr>
                    <td height="33">联系电话：<%=order.getFieldValue("Phone")%></td>
                </tr>
                <tr>
                    <td height="36">联系地址：<%=(null != province ? province.getFieldValue("title") : "") + (null != city ? city.getFieldValue("title") : "") + (null != area ? area.getFieldValue("title") : "") + order.getFieldValue("Address")%></td>
                </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="10" class="dingdan00">
                <tr>
                    <td height="40" style="padding-left:10px;">商品信息</td>
                </tr>
                <%
                    List<DataField> orderdetailList = (List<DataField>) DaoFactory.getBasketDAO().getBySuserIdUidNoList(wxsid, openid, order.getFieldValue("F_No"));
                    for (DataField orderdetail : orderdetailList) {
                %>
                <tr>
                    <td height="96" style=" background-color:#f6f6f6; border-radius: 5px; "><table width="100%" border="0" cellspacing="0" cellpadding="10">
                            <tr>
                                <td width="100" rowspan="3" align="center"><img src="<%=orderdetail.getFieldValue("ViewImg")%>" width="80" height="80"/></td>
                                <td width="" height="26">商品编号：<%=orderdetail.getFieldValue("Pcode")%></td>
                            </tr>
                            <tr>
                                <td width="" height="26">商品名称：<%=orderdetail.getFieldValue("Pname")%></td>
                            </tr>
                            <tr>
                                <td height="26">价格：<font color="#FF0000"><%=orderdetail.getFieldValue("Tot_Price")%></font>元</td>
                            </tr>
                        </table></td>
                </tr>
                <%}%>
                <tr>
                    <td align="center" style="padding:10px;color:#b9b9b9"><font color="#FF0000">购物合计总金额：<font color="#FF0000"><%=order.getFieldValue("SF_Price")%></font>元</font></td>
                </tr>
            </table>
            <%
                if ("result".equals(RequestUtil.getString(request, "act"))) {
            %>
            <table width="100%" border="0" cellspacing="0" cellpadding="10" class="dingdan00">
                <tr>
                    <td height="40" style="padding-left:10px;">支付结果</td>
                </tr>
                <tr>
                    <td align="center" style="padding:10px;color:#b9b9b9"><font color="#FF0000"><%="1".equals(RequestUtil.getString(request, "sign")) ? "支付成功" : "支付失败"%></font></td>
                </tr>
            </table>
            <%
                if ("1".equals(RequestUtil.getString(request, "sign"))) {
            %>
            <div class="xz003">
                <input type="button" class="" value="返回订单" onclick="javascript:window.location.href = '/shop2/vip.jsp?act=order&wx=<%=wxsid%>&openid=<%=openid%>';">
            </div>
            <%
            } else {
            %>
            <div class="xz003">
                <input type="button" class="" value="重新进行支付" onclick="javascript:window.location.href = '/ForeServlet?method=pay&F_No=<%=order.getFieldValue("F_No")%>&wxsid=<%=wxsid%>&openid=<%=openid%>';">
            </div>
            <%
                    }
                }
            %>
        </div>
    </body>
</html>
