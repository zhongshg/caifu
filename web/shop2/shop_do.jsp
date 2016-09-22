<%-- 
    Document   : shop_do
    Created on : 2015-7-31, 10:18:16
    Author     : Administrator
--%>

<%@page import="wap.wx.menu.WxMenuUtils"%>
<%@page import="wap.wx.util.DbConn"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.HashMap"%>
<%@page import="wap.wx.dao.WxsDAO"%>
<%@page import="wap.wx.menu.WxPayUtils"%>
<%@page import="job.tot.bean.DataField"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="job.tot.util.DateUtil"%>
<%@page import="job.tot.dao.DaoFactory"%>
<%@page import="wap.wx.dao.SubscriberDAO"%>
<%@page import="java.util.Map"%>
<%@page import="job.tot.util.RequestUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String act = RequestUtil.getString(request, "act");
    if ("tx".equals(act)) {
        String wxsid = RequestUtil.getString(request, "wxsid");
        String openid = RequestUtil.getString(request, "openid");
        String price = RequestUtil.getString(request, "price");
        Map<String, String> wx = new HashMap<String, String>();
        wx.put("id", wxsid);
        wx = new WxsDAO().getById(wx);
        //获取已完成佣金
        float totalorderalendYJ = 0;//已完成订单总佣金 5
        SubscriberDAO subscriberDAO = new SubscriberDAO();
        //获取用户信息
        Connection conn = DbConn.getConn();
        Map<String, String> subscriber = subscriberDAO.getByOpenid(wxsid, openid);
        List<Map<String, String>> firstsubscriberList = subscriberDAO.getByParentopenidList(wxsid, subscriber.get("id"));
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

        for (Map<String, String> firstsubscriber : firstsubscriberList) {
            List<DataField> firstorderList = (List<DataField>) DaoFactory.getOrderDAO().getList(wxsid, firstsubscriber.get("openid"), 5, conn);
            for (DataField order : firstorderList) {
                totalorderalendYJ += order.getFloat("FirstYJ");
            }
            List<Map<String, String>> secondsubscriberList = subscriberDAO.getByParentopenidList(wxsid, firstsubscriber.get("id"));
            for (Map<String, String> secondsubscriber : secondsubscriberList) {
                List<DataField> secondorderList = (List<DataField>) DaoFactory.getOrderDAO().getList(wxsid, secondsubscriber.get("openid"), 5, conn);
                for (DataField order : secondorderList) {
                    totalorderalendYJ += order.getFloat("SecondYJ");
                }
                List<Map<String, String>> thirdsubscriberList = subscriberDAO.getByParentopenidList(wxsid, secondsubscriber.get("id"));
                for (Map<String, String> thirdsubscriber : thirdsubscriberList) {
                    List<DataField> thirdorderList = (List<DataField>) DaoFactory.getOrderDAO().getList(wxsid, thirdsubscriber.get("openid"), 5, conn);
                    for (DataField order : thirdorderList) {
                        totalorderalendYJ += order.getFloat("ThirdYJ");
                    }
                }
            }
        }
        //已提现佣金(含申请)
        float totaltx = 0;
        List<DataField> txList = (List<DataField>) DaoFactory.getFundsDao().getList(0, 0, "", openid, "", -1, wxsid, -1, -1);
        for (DataField tx : txList) {
            totaltx += tx.getFloat("F_Price");
        }
        System.out.println("totalorderalendYJ " + totalorderalendYJ + " totaltx " + totaltx + " totalorderalendYJ - totaltx " + (totalorderalendYJ - totaltx) + " wxsid " + wxsid);

        //判断客户是否有已完成订单
        List<DataField> successendList = (List<DataField>) DaoFactory.getOrderDAO().getList(wxsid, openid, 5, conn);
        conn.close();

        if (null == subscriber.get("id")) {
            out.print("{'success':false,'message':'系统繁忙，请稍候再试！'}");
        } else if ("0".equals(subscriber.get("isvip"))) {
            out.print("{'success':false,'message':'只有成为东家才能完成提现！'}");
        } else if (0 == successendList.size()) {
            out.print("{'success':false,'message':'只有已完成订单才能提现！'}");
        } else if ("".equals(price) || 0 == Float.parseFloat(price)) {
            out.print("{'success':false,'message':'请完善提现信息！'}");
        } else if (Float.parseFloat(wx.get("withdrawmoneylimit")) > Float.parseFloat(price)) {
            out.print("{'success':false,'message':'提现金额不得少于" + wx.get("withdrawmoneylimit") + "元！'}");
        } else if (200 < Float.parseFloat(price)) {
            out.print("{'success':false,'message':'提现金额不得多于200元！'}");
        } else if (Float.parseFloat(price) > (totalorderalendYJ + Float.parseFloat(subscriber.get("areaproxymoney")) + Float.parseFloat(subscriber.get("callbackmoney")) - totaltx)) {
            out.print("{'success':false,'message':'佣金不足，无法提现！'}");
        } else {
            //发送提现红包
            String total_amount = String.valueOf((int) (Float.parseFloat(price) * 100));
            String flag = new WxPayUtils().sendredpack(request, wx, openid, total_amount, request.getRemoteAddr());
            String results[] = flag.split(",");
            if ("SUCCESS".equals(results[0])) {
                String F_No = WxMenuUtils.format2.format(new java.util.Date()) + String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
                if (DaoFactory.getFundsDao().add(F_No, new Timestamp(System.currentTimeMillis()), Float.parseFloat(price), openid, subscriber.get("nickname"), "", request.getRemoteAddr(), 1, 1, "", "系统提现", 0, wxsid)) {
//                out.print("{'success':true,'message':'提现申请已提交，请耐心等待！'}");
                    out.print("{'success':true,'message':'提现成功！'}");
                }
            } else {
                out.print("{'success':false,'message':'提现失败！" + results[1] + "'}");
            }
        }
    }
%>
