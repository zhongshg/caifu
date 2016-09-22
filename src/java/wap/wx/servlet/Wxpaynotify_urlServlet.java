/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import job.tot.bean.DataField;
import job.tot.dao.DaoFactory;
import job.tot.exception.DatabaseException;
import job.tot.exception.ObjectNotFoundException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wap.wx.dao.SubscriberDAO;
import wap.wx.dao.WxsDAO;
import wap.wx.menu.WxMenuUtils;
import wap.wx.menu.WxPayUtils;
import static wap.wx.servlet.BaseServlet.wx;

/**
 *
 * @author Administrator
 */
public class Wxpaynotify_urlServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("异步通知到了");
        //此处接收异步通知 见ar文档
        WxPayUtils wxPayUtils = new WxPayUtils();
        PrintWriter out = response.getWriter();
        SubscriberDAO subscriberDAO = new SubscriberDAO();

        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String postStr = buffer.toString();
        try {
            postStr = new String(postStr.getBytes("gbk"), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != postStr && !postStr.isEmpty()) {
            Document document = null;
            try {
                document = DocumentHelper.parseText(postStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Element root = document.getRootElement();

            String return_code = root.elementTextTrim("return_code");
            System.out.println("return_code " + return_code);
            if ("SUCCESS".equals(return_code)) {
                String appid = root.elementTextTrim("appid");
                String mch_id = root.elementTextTrim("mch_id");
                String device_info = root.elementTextTrim("device_info");
                String nonce_str = root.elementTextTrim("nonce_str");
                //验证签名
                Map<String, String> tempMap = new HashMap<String, String>();
                List<Element> elementList = root.elements();
                for (Element element : elementList) {
                    if ("sign".equals(element.getName())) {
                        continue;
                    }
                    tempMap.put(element.getName(), element.getText());
                }
                //此处应用微信信息
                wx = new HashMap<String, String>();
                wx.put("appid", appid);
                wx = new WxsDAO().getByAppid(wx);
                String sign = wxPayUtils.getSignature(tempMap, wx.get("wxpaykey"));
                System.out.println("分销系统 sign " + root.elementTextTrim("sign") + " mysign " + sign);
//            if (sign.equals(root.elementTextTrim("sign"))) {
                System.out.println("返回值签名验证成功！");
                String result_code = root.elementTextTrim("result_code");
                if ("SUCCESS".equals(result_code)) {
                    String openid = root.elementTextTrim("openid");
                    String is_subscribe = root.elementTextTrim("is_subscribe");
                    String trade_type = root.elementTextTrim("trade_type");
                    String bank_type = root.elementTextTrim("bank_type");
                    String total_fee = root.elementTextTrim("total_fee");
                    String coupon_fee = root.elementTextTrim("coupon_fee");
                    String fee_type = root.elementTextTrim("fee_type");
                    String transaction_id = root.elementTextTrim("transaction_id");
                    String out_trade_no = root.elementTextTrim("out_trade_no");
                    String attach = root.elementTextTrim("attach");
                    String time_end = root.elementTextTrim("time_end");
                    System.out.println("out_trade_no " + out_trade_no);
                    //支付成功处理
                    //判断有无处理
                    DataField order = DaoFactory.getOrderDAO().getByout_trade_no(out_trade_no);
                    System.out.println("order.getFieldValue(\"IsPay\")处理过没？ " + order.getFieldValue("IsPay"));
                    if ("1".equals(order.getFieldValue("IsPay"))) {
                        //更改订单状态 
                        DaoFactory.getOrderDAO().modIsPayByout_trade_no(out_trade_no, 2);
                        try {
                            DaoFactory.getOrderDAO().modStsByout_trade_no(out_trade_no, 1);
                            //添加transaction_id
                            DaoFactory.getOrderDAO().modtransaction_id(transaction_id, out_trade_no);
                        } catch (ObjectNotFoundException ex) {
                            Logger.getLogger(Wxpaynotify_urlServlet.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (DatabaseException ex) {
                            Logger.getLogger(Wxpaynotify_urlServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        String content = "";
                        Map<String, String> subscriber = subscriberDAO.getByOpenid(wx.get("id"), openid);
                        //判断是否通过购买能成为会员，判断多少购买额可以成为会员
                        if ("0".equals(subscriber.get("isvip"))) {
                            if ("0".equals(wx.get("isbuytovip"))) {
                                //取出总购买额
                                float totalorderMoney = DaoFactory.getOrderDAO().getTotalMoney(wx.get("id"), openid);
                                if (Float.parseFloat(wx.get("vipmoneylimit")) <= totalorderMoney) {
                                    //更改会员身份
                                    subscriberDAO.updateVip(openid, wx.get("id"), 1);
                                    //二维码通知
                                    content = wx.get("messageqrcodewarns");
                                    WxMenuUtils.sendCustomService(openid, content, wx);
                                }
                            }
                        }

                        //判断有无优惠
                        String str = "";
                        String str2 = "";
                        String[] Demonss = order.getFieldValue("Demons").split(",");
                        if (1 < Demonss.length) {
                            if ("1".equals(Demonss[0])) {
                                str = "；实付金额为：" + order.getFieldValue("SF_Price") + "元；共计优惠：" + order.getFieldValue("CF_Price") + "元";
                            }
                            if ("2".equals(Demonss[0])) {
                                str2 = "；交易完成后将收到返现：" + ((float) ((int) (Float.parseFloat(Demonss[1]) * 100))) / 100 + "元";
                            }
                        }

                        Map<String, String> parentsubscriber = subscriberDAO.getById(wx.get("id"), subscriber.get("parentopenid"));

                        try {
                            //发送本级通知
                            content = "您在" + WxMenuUtils.format.format(new Date(System.currentTimeMillis())) + "成功付款，订单号为：" + order.getFieldValue("F_No") + "；应付金额为：" + order.getFieldValue("TF_Price") + "元" + str + str2 + "。";
                            WxMenuUtils.sendCustomService(openid, content, wx);

                            content = "您的" + wx.get("subtitle1") + "【" + subscriber.get("nickname") + "】在" + WxMenuUtils.format.format(new Date(System.currentTimeMillis())) + "【已付款】，订单号为：" + order.getFieldValue("F_No") + "；应付金额为：" + order.getFieldValue("TF_Price") + "元" + str + "；您将获得的提成为：" + order.getFieldValue("FirstYJ") + "元。";
                            //判断有无上级
                            if (null != parentsubscriber.get("openid")) {
                                WxMenuUtils.sendCustomService(parentsubscriber.get("openid"), content, wx);
                            } else {
                                //给商家用户发送通知
                                if (!"0".equals(wx.get("adminsubscriber"))) {
                                    WxMenuUtils.sendCustomService(wx.get("adminsubscriber"), content, wx);
                                }
                            }

                            //往上再取两级，三级都通知
                            if (!"0".equals(parentsubscriber.get("parentopenid"))) {
                                Map<String, String> secondparentsubscriber = subscriberDAO.getById(wx.get("id"), parentsubscriber.get("parentopenid"));
                                content = "您的" + wx.get("subtitle2") + "【" + subscriber.get("nickname") + "】在" + WxMenuUtils.format.format(new Date(System.currentTimeMillis())) + "【已付款】，订单号为：" + order.getFieldValue("F_No") + "；应付金额为：" + order.getFieldValue("TF_Price") + "元" + str + "；您将获得的提成为：" + order.getFieldValue("SecondYJ") + "元。";
                                if (null != secondparentsubscriber.get("id")) {
                                    WxMenuUtils.sendCustomService(secondparentsubscriber.get("openid"), content, wx);
                                    if (!"0".equals(secondparentsubscriber.get("parentopenid"))) {
                                        Map<String, String> thirdparentsubscriber = subscriberDAO.getById(wx.get("id"), secondparentsubscriber.get("parentopenid"));
                                        content = "您的" + wx.get("subtitle3") + "【" + subscriber.get("nickname") + "】在" + WxMenuUtils.format.format(new Date(System.currentTimeMillis())) + "【已付款】，订单号为：" + order.getFieldValue("F_No") + "；应付金额为：" + order.getFieldValue("TF_Price") + "元" + str + "；您将获得的提成为：" + order.getFieldValue("ThirdYJ") + "元。";
                                        if (null != thirdparentsubscriber.get("id")) {
                                            WxMenuUtils.sendCustomService(thirdparentsubscriber.get("openid"), content, wx);
                                        }
                                    }
                                }
                            }

                            System.out.println("异步工作成功完成！");
                        } catch (Exception e) {
                            System.out.println("order " + order);
                            System.out.println("openid " + openid);
                            System.out.println("parentsubscriber " + parentsubscriber);
                            System.out.println("无上级掌柜异常！");
                        }

                    }
                } else {
                    System.out.println("err_code  " + root.elementTextTrim("err_code"));
                    System.out.println("err_code_des  " + root.elementTextTrim("err_code_des"));
                    //支付异常，被动查询订单
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("appid", appid);
                    map.put("mch_id", mch_id);
                    map.put("out_trade_no", root.elementTextTrim("out_trade_no"));
                    map.put("nonce_str", nonce_str);
                    map = wxPayUtils.payorderquery(map, wx);
                    System.out.println("查询订单！");
                }
//            } else {
//                System.out.println("返回值签名验证失败！");
//            }
            } else {
                System.out.println("return_msg  " + root.elementTextTrim("return_msg"));
                System.out.println("异步工作失败成！");
            }
            String params = "<xml>"
                    + "   <return_code>" + root.elementTextTrim("return_code") + "</return_code>"
                    + "   <return_msg>" + root.elementTextTrim("return_msg") + "</return_msg>"
                    + "</xml>";
            out.print(params);
            out.close();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
