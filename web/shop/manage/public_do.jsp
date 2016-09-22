<%-- 
    Document   : public_do
    Created on : 2015-12-13, 0:03:02
    Author     : ASUS
--%>

<%@page import="wap.wx.menu.WxPayUtils"%>
<%@page import="wap.wx.dao.WxsDAO"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="job.tot.bean.DataField"%>
<%@page import="wap.wx.menu.WxMenuUtils"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="job.tot.dao.DaoFactory"%>
<%@page import="job.tot.util.RequestUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String act = RequestUtil.getString(request, "act");
    String FNo = RequestUtil.getString(request, "FNo");
    if ("modship".equals(act)) {
        String ShipNo = RequestUtil.getString(request, "ShipNo");
        String ShipName = RequestUtil.getString(request, "ShipName");
        DaoFactory.getOrderDAO().modPost(FNo, ShipNo, ShipName, 1, 1, new Timestamp(System.currentTimeMillis()));
        out.print("0");
    }
    if ("send".equals(act)) {
        DataField order = DaoFactory.getOrderDAO().get(FNo);
        String wxsid = RequestUtil.getString(request, "wxsid");
        Map<String, String> wx = new HashMap<String, String>();
        wx.put("id", wxsid);
        wx = new WxsDAO().getById(wx);
        DaoFactory.getOrderDAO().modSts(FNo, 2);
        DaoFactory.getOrderDAO().modSendtimes(FNo, WxMenuUtils.format.format(new java.util.Date()));
        //发货提醒
        String content = "";
        if ("".equals(order.getFieldValue("ShipName")) || "".equals(order.getFieldValue("ShipNo"))) {
            content = "您的宝贝已经发货，请注意查收。订单号：" + order.getFieldValue("F_No") + "。";
        } else {
            content = "您的宝贝已经发货，请注意查收。订单号：" + order.getFieldValue("F_No") + "，物流名称：" + order.getFieldValue("ShipName") + "，物流单号：" + order.getFieldValue("ShipNo") + "。";
        }
        WxMenuUtils.sendCustomService(order.getFieldValue("UserId"), content, wx);
        out.print("0");
    }
    if ("backthing".equals(act)) {
        DataField order = DaoFactory.getOrderDAO().get(FNo);
        String wxsid = RequestUtil.getString(request, "wxsid");
        Map<String, String> wx = new HashMap<String, String>();
        wx.put("id", wxsid);
        wx = new WxsDAO().getById(wx);
        DaoFactory.getOrderDAO().modIsPay(FNo, 4);
        DaoFactory.getOrderDAO().modSts(FNo, 7);
        DaoFactory.getOrderDAO().modState(FNo, 2);
        out.print("0");
    }
    if ("backmoney".equals(act)) {
        DataField order = DaoFactory.getOrderDAO().get(FNo);
        String wxsid = RequestUtil.getString(request, "wxsid");
        Map<String, String> wx = new HashMap<String, String>();
        wx.put("id", wxsid);
        wx = new WxsDAO().getById(wx);
        DaoFactory.getOrderDAO().modIsPay(FNo, 4);
        //退款
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", wx.get("appid"));
        map.put("mch_id", wx.get("mch_id"));
//        map.put("device_info", "");//否
        map.put("nonce_str", String.valueOf(System.currentTimeMillis() / 1000));
        //                       map.put("transaction_id", d.getFieldValue("transaction_id"));//微信订单号
        map.put("out_trade_no", order.getFieldValue("out_trade_no"));//商户订单号
        map.put("out_refund_no", "-" + order.getFieldValue("out_trade_no"));//商户退款单号
        map.put("total_fee", String.valueOf((int) (order.getFloat("SF_Price") * 100)));//总金额
        map.put("refund_fee", String.valueOf((int) (order.getFloat("SF_Price") * 100)));//退款金额
//        map.put("refund_fee_type", "CNY");//货币种类 否
        map.put("op_user_id", wx.get("mch_id"));//操作员
        map = new WxPayUtils().payrefund(request, map, wx);
        out.print("0");
    }

    if ("updateSaleNum".equals(act)) {
        int id = RequestUtil.getInt(request, "id");
        Float SaleNum = RequestUtil.getFloat(request, "SaleNum");
        String wxsid = RequestUtil.getString(request, "wxsid");
        DaoFactory.getProductDAO().modSaleNum(id, SaleNum);
        out.print(SaleNum);
    }
%>
