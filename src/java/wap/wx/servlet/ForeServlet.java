/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jspsmart.upload.File;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import job.tot.bean.DataField;
import job.tot.dao.DaoFactory;
import job.tot.exception.DatabaseException;
import job.tot.exception.ObjectNotFoundException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import wap.wx.dao.NewsDAO;
import wap.wx.dao.SubscriberDAO;
import wap.wx.dao.WxsDAO;
import wap.wx.menu.HttpClientConnectionManager;
import wap.wx.menu.WxJsApiUtils;
import wap.wx.menu.WxMenuUtils;
import wap.wx.menu.WxPayUtils;
import wap.wx.util.DbConn;
import wap.wx.util.ExportExcel;
import wap.wx.util.Forward;

/**
 *
 * @author Administrator
 */
public class ForeServlet extends BaseServlet {

    protected void newsdemo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        NewsDAO newsDAO = new NewsDAO();
        Map<String, String> news = new HashMap<String, String>();
        news.put("id", id);
        news = newsDAO.getById(news);
        request.setAttribute("news", news);
        request.setAttribute("now", WxMenuUtils.format.format(new Date()));
        String uid = request.getParameter("wx");
        wx = new HashMap<String, String>();
        wx.put("id", uid);
        wx = new WxsDAO().getById(wx);
        request.setAttribute("wx", wx);

        String url = request.getRequestURL() + "?" + request.getQueryString();
        Map<String, String> jsapi = new WxJsApiUtils().jsapi(url, wx);
        String jsApiList = "'previewImage'";
        jsapi.put("jsApiList", jsApiList);
        request.setAttribute("jsapi", jsapi);

        //阅读量
        newsDAO.updateViewcounts(id);
        Forward.forward(request, response, "/fore/news/newsdemo.jsp");
    }

    //网页获取openid
    protected void shop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetopenid = request.getParameter("targetopenid");//分享目标
        if (null == targetopenid) {
            targetopenid = "";
        }
        String uid = request.getParameter("wx");
        wx = new HashMap<String, String>();
        wx.put("id", uid);
        wx = new WxsDAO().getById(wx);
        String redirectUri = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/ForeServlet?method=shop_do&wxsid=" + wx.get("id") + "&targetopenid=" + targetopenid;
        response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wx.get("appid") + "&redirect_uri=" + URLEncoder.encode(redirectUri, "utf-8") + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
    }

    //回调url
    protected void shop_do(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String wxsid = request.getParameter("wxsid");
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        wx.put("id", wxsid);
        wx = new WxsDAO().getById(wx);
        HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wx.get("appid") + "&secret=" + wx.get("appsecret") + "&code=" + code + "&grant_type=authorization_code");
        CloseableHttpClient httpclient = WxMenuUtils.httpclient();
        CloseableHttpResponse responses = httpclient.execute(get);
        String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
        WxMenuUtils.close(httpclient);
        JSONObject object = JSON.parseObject(jsonStr);
        String targetopenid = request.getParameter("targetopenid");
        response.sendRedirect("shop2/shop.jsp?openid=" + object.getString("openid") + "&wx=" + wx.get("id") + "&targetopenid=" + targetopenid);
//        Forward.forward(request, response, "shop/index.jsp?openid=" + object.getString("openid") + "&wx=" + wx.get("id"));
//        String openid = object.getString("openid");
//        String accessToken2 = object.getString("access_token");
//        get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken2 + "&openid=" + openid + "&lang=zh_CN");
//        httpclient = WxMenuUtils.httpclient();
//        responses = httpclient.execute(get);
//        jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
//        WxMenuUtils.close(httpclient);
//        System.out.println("jsonStr  " + jsonStr);
//        object = JSON.parseObject(jsonStr);
//        System.out.println(object.getString("nickname"));
    }

    //网页获取openid
    protected void vip(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("wx");
        wx = new HashMap<String, String>();
        wx.put("id", uid);
        wx = new WxsDAO().getById(wx);
        String redirectUri = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/ForeServlet?method=vip_do&wxsid=" + wx.get("id");
        response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wx.get("appid") + "&redirect_uri=" + URLEncoder.encode(redirectUri, "utf-8") + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
    }

    //回调url
    protected void vip_do(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String wxsid = request.getParameter("wxsid");
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        wx.put("id", wxsid);
        wx = new WxsDAO().getById(wx);
        HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wx.get("appid") + "&secret=" + wx.get("appsecret") + "&code=" + code + "&grant_type=authorization_code");
        CloseableHttpClient httpclient = WxMenuUtils.httpclient();
        CloseableHttpResponse responses = httpclient.execute(get);
        String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
        WxMenuUtils.close(httpclient);
        JSONObject object = JSON.parseObject(jsonStr);
        response.sendRedirect("shop2/vip.jsp?openid=" + object.getString("openid") + "&wx=" + wx.get("id"));
//        Forward.forward(request, response, "shop/index.jsp?openid=" + object.getString("openid") + "&wx=" + wx.get("id"));
//        String openid = object.getString("openid");
//        String accessToken2 = object.getString("access_token");
//        get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken2 + "&openid=" + openid + "&lang=zh_CN");
//        httpclient = WxMenuUtils.httpclient();
//        responses = httpclient.execute(get);
//        jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
//        WxMenuUtils.close(httpclient);
//        System.out.println("jsonStr  " + jsonStr);
//        object = JSON.parseObject(jsonStr);
//        System.out.println(object.getString("nickname"));
    }

    /**
     * 微信支付块
     */
    /**
     * 外来参数，不包含微信
     *
     * @param request 订单号(商品描述，总订单号，总金额)， wxsid，openid
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String wxsid = request.getParameter("wxsid");
        //注：如果感觉不安全，建议重新获取一下openid，这里忽略
        String openid = request.getParameter("openid");
        String F_No = request.getParameter("F_No");
        System.out.println("监视付款操作：" + F_No);
        //微信信息
        wx = new HashMap<String, String>();
        wx.put("id", wxsid);
        wx = new WxsDAO().getById(wx);
        //订单信息
        String remark = "";
        DataField order = DaoFactory.getOrderDAO().get(F_No);
        List<DataField> prolist = (List<DataField>) DaoFactory.getBasketDAO().getListNo(F_No);
        for (DataField pro : prolist) {
            remark += pro.getFieldValue("Pname") + "，" + pro.getFieldValue("Pnum") + "件，" + pro.getFieldValue("Tot_Price") + "元\n";
        }
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String out_trade_no = order.getFieldValue("out_trade_no");
        if (null == out_trade_no || "".equals(out_trade_no)) {
            out_trade_no = wx.get("mch_id") + WxMenuUtils.format2.format(new Date()) + String.valueOf(new Random().nextInt(10000));//异步传输，异常查询使用;
        }
        System.out.println("out_trade_no " + out_trade_no);
        //这里判断一下订单状态 支付/未支付
        if ("1".equals(order.getFieldValue("IsPay"))) {

            try {
                //        out_trade_no = F_No;//重新定义
                DaoFactory.getOrderDAO().modout_trade_no(F_No, out_trade_no);
            } catch (ObjectNotFoundException ex) {
                Logger.getLogger(ForeServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DatabaseException ex) {
                Logger.getLogger(ForeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            String total_fee = String.valueOf((int) (order.getFloat("SF_Price") * 100));//分
            //统一下单参数
            WxPayUtils wxPayUtils = new WxPayUtils();
            Map map = new HashMap();
            map.put("appid", wx.get("appid"));//公众账号id
            map.put("mch_id", wx.get("mch_id"));//商户号
//        map.put("device_info", "WEB");//设备号 否
            map.put("nonce_str", String.valueOf(System.currentTimeMillis() / 1000));//随机字符串
            map.put("body", remark);//商品描述
//        map.put("detail", "商品详情");//商品详情 否
//        map.put("attach", "订单自定义数据");//附加数据 否
            map.put("out_trade_no", out_trade_no);//商户订单号
//        map.put("fee_type", "CNY");//货币类型 否
            map.put("total_fee", total_fee);//总金额 分
            map.put("spbill_create_ip", request.getRemoteAddr());//终端ip
//        map.put("time_start", WxMenuUtils.format.format(new Date()));//交易起始时间 否
//        map.put("time_expire", WxMenuUtils.format.format(new Date()));//交易结束时间 否
//        map.put("goods_tag", "商品标记");//商品标记 否
            map.put("notify_url", request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/Wxpaynotify_urlServlet");//通知地址
            map.put("trade_type", "JSAPI");//交易类型
//        map.put("product_id", "");//商品id 否 native必传
            map.put("openid", openid);//用户标识 否 jsapi必传
            //下单
            map = wxPayUtils.payUnifiedorder(map, wx.get("wxpaykey"));
            String prepay_id = (String) map.get("prepay_id");
            System.out.println("prepay_id   " + prepay_id);

            if (null != prepay_id) {
                Map jsapimap = new HashMap<String, String>();
                jsapimap.put("appId", map.get("appid"));
                jsapimap.put("timeStamp", timeStamp);
                jsapimap.put("nonceStr", map.get("nonce_str"));
                jsapimap.put("package", "prepay_id=" + prepay_id);
                jsapimap.put("signType", "MD5");
                String paySign = wxPayUtils.getSignature(jsapimap, wx.get("wxpaykey"));
                jsapimap.put("packages", "prepay_id=" + prepay_id);//package疑似关键字，用packages代替传输
                jsapimap.put("paySign", paySign);
                System.out.println(jsapimap.get("appId") + " " + jsapimap.get("timeStamp") + " " + jsapimap.get("nonceStr") + " " + prepay_id + " " + "MD5" + " " + paySign);
                //附加参数
                jsapimap.put("F_No", F_No);
                jsapimap.put("out_trade_no", out_trade_no);
                jsapimap.put("openid", openid);

                request.setAttribute("jsapimap", jsapimap);
                request.setAttribute("wx", wx);
                Forward.forward(request, response, "/fore/jsapi/jsapi.jsp");
            } else {
                //错误处理
                System.out.println("统一下单失败！");
                Forward.forward(request, response, "/shop2/vip.jsp?act=order&wx=" + wxsid + "&openid=" + openid);
            }
        } else {
            //已处理
            System.out.println("订单支付失败，该订单已支付！" + out_trade_no);
            Forward.forward(request, response, "/shop2/vip.jsp?act=order&wx=" + wxsid + "&openid=" + openid);
        }
    }

//    protected void wxpaynotify_url(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        
//    }
    //主动查询订单
    protected void orderquery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", "");
        map.put("mch_id", "");
        map.put("transaction_id", "");
        System.out.println("out_trade_no   " + request.getParameter("out_trade_no"));
        map.put("out_trade_no", request.getParameter("out_trade_no"));
        map.put("nonce_str", String.valueOf(UUID.randomUUID()));
        map = new WxPayUtils().payorderquery(map, wx);
        //订单查询处理
    }

    //主动关闭订单
    protected void closeorder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", "");
        map.put("mch_id", "");
        System.out.println("out_trade_no   " + request.getParameter("out_trade_no"));
        map.put("out_trade_no", request.getParameter("out_trade_no"));
        map.put("nonce_str", String.valueOf(UUID.randomUUID()));
        map = new WxPayUtils().paycloseorder(map, wx.get("wxpaykey"));
        //订单关闭处理
    }

    //退款
    protected void refund(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String wxsid = request.getParameter("wxsid");
        String openid = request.getParameter("openid");
//        String transaction_id=request.getParameter("transaction_id");
        String F_No = request.getParameter("F_No");
        System.out.println("监视退货操作：" + F_No);
        System.out.println("wxsid " + wxsid + " openid " + openid + " F_No " + F_No);
        wx = new HashMap<String, String>();
        wx.put("id", wxsid);
        wx = new WxsDAO().getById(wx);
        DataField order = DaoFactory.getOrderDAO().get(F_No);

        if (null != order && ("1".equals(order.getFieldValue("Sts")) || "3".equals(order.getFieldValue("Sts")))) {
            System.out.println("退货out_trade_no " + order.getFieldValue("out_trade_no"));
            //更改订单状态
            try {
                DaoFactory.getOrderDAO().modStsByout_trade_no(order.getFieldValue("out_trade_no"), 6);
            } catch (ObjectNotFoundException ex) {
                Logger.getLogger(WxPayUtils.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DatabaseException ex) {
                Logger.getLogger(WxPayUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("该状态不允许退款out_trade_no " + order.getFieldValue("out_trade_no") + " Sts" + order.getFieldValue("Sts"));
        }

//        Map<String, String> map = new HashMap<String, String>();
//        map.put("appid", wx.get("appid"));
//        map.put("mch_id", wx.get("mch_id"));
////        map.put("device_info", "");//否
//        map.put("nonce_str", String.valueOf(System.currentTimeMillis() / 1000));
////        map.put("transaction_id", transaction_id);//微信订单号
//        map.put("out_trade_no", order.getFieldValue("out_trade_no"));//商户订单号
//        map.put("out_refund_no", "-" + order.getFieldValue("out_trade_no"));//商户退款单号
//        map.put("total_fee", String.valueOf((int) (order.getFloat("SF_Price") * 100)));//总金额
//        map.put("refund_fee", String.valueOf((int) (order.getFloat("SF_Price") * 100)));//退款金额
////        map.put("refund_fee_type", "CNY");//货币种类 否
//        map.put("op_user_id", wx.get("mch_id"));//操作员
//        map = new WxPayUtils().payrefund(map, wx);
        Forward.forward(request, response, "/shop2/vip.jsp?act=order&wx=" + wxsid + "&openid=" + openid);
    }

    //查询退款
    protected void refundquery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", "");
        map.put("mch_id", "");
        map.put("device_info", "");//否
        map.put("nonce_str", String.valueOf(UUID.randomUUID()));
        map.put("transaction_id", "");//微信订单号
        map.put("out_trade_no", "");//商户订单号
        map.put("out_refund_no", "");//商户退款单号
        map.put("refund_id", "");//微信退款单号
        map = new WxPayUtils().refundquery(map, wx.get("wxpaykey"));
    }

    //对账单
    protected void downloadbill(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", "");
        map.put("mch_id", "");
        map.put("device_info", "");//否
        map.put("nonce_str", String.valueOf(UUID.randomUUID()));
        map.put("bill_date", "");//下载对账单的日期
        map.put("bill_type", "ALL");//订单类型
//        ALL，返回当日所有订单信息，默认值
//SUCCESS，返回当日成功支付的订单
//REFUND，返回当日退款订单
//REVOKED，已撤销的订单
        map = new WxPayUtils().downloadbill(map, wx.get("wxpaykey"), this.getServletContext());
        System.out.println("path   " + map.get("path"));
    }

    /**
     * 微信支付块结束
     */
    //补：商品-商品消息 获取openid 暂未用
    //网页获取openid
    protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String wxsid = request.getParameter("wx");
        String id = request.getParameter("id");
        String redirectUri = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/ForeServlet?method=detail_do&id=" + id + "&wxsid=" + wxsid;
        response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wx.get("appid") + "&redirect_uri=" + URLEncoder.encode(redirectUri, "utf-8") + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
    }

    //回调url
    protected void detail_do(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wx.get("appid") + "&secret=" + wx.get("appsecret") + "&code=" + code + "&grant_type=authorization_code");
        CloseableHttpClient httpclient = WxMenuUtils.httpclient();
        CloseableHttpResponse responses = httpclient.execute(get);
        String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
        WxMenuUtils.close(httpclient);
        JSONObject object = JSON.parseObject(jsonStr);

        String openid = object.getString("openid");
        String wxsid = request.getParameter("wxsid");
        String id = request.getParameter("id");
        Forward.forward(request, response, "/shop2/shop.jsp?id=" + id + "&act=detail&wx=" + wxsid + "&openid=" + openid);
    }

    protected void startinputexcel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String img = request.getParameter("img");
        int successcount = 0;
        int failcount = 0;
        int noordercount = 0;
        StringBuilder message = new StringBuilder();
        List<Map<String, String>> list = ExportExcel.readExcel(request.getServletContext().getRealPath("/" + img));

        //获取操作员
        Map<String, String> user = (Map<String, String>) request.getSession().getAttribute("users");
        int op = Integer.parseInt(user.get("id"));
        PrintWriter out = response.getWriter();
        Timestamp times = new Timestamp(System.currentTimeMillis());
        Connection conn = DbConn.getConn();
        for (Map<String, String> map : list) {
//           String orderno=map.get("orderno");
//           String logisno=map.get("logisno");
//           String logisname=map.get("logisname");
            if (!"".equals(map.get("orderno"))) {
                //取出订单
                Map<String, String> order = DaoFactory.getOrderDAO().get(conn, map.get("orderno"));
                if (null != order.get("id")) {
                    if ("1".equals(order.get("Sts"))) {//未发货正常
                        successcount++;
                        DaoFactory.getOrderDAO().modLogis(conn, map.get("orderno"), map.get("logisno"), map.get("logisname"), times, 2, op);
                        //发货提醒
                        String content = "";
                        if ("".equals(map.get("logisno")) || "".equals(map.get("logisname"))) {
                            content = "您的宝贝已经发货，请注意查收。订单号：" + map.get("orderno") + "。";
                        } else {
                            content = "您的宝贝已经发货，请注意查收。订单号：" + map.get("orderno") + "，物流名称：" + map.get("logisname") + "，物流单号：" + map.get("logisno") + "。";
                        }
                        WxMenuUtils.sendCustomService(order.get("UserId"), content, wx);
                    } else {
                        failcount++;
                        switch (Integer.parseInt(order.get("Sts"))) {
                            case 0:
                                message.append(map.get("orderno") + ":未支付！&nbsp;");
                                break;
                            case 1:
                                message.append(map.get("orderno") + ":未发货！&nbsp;");
                                break;
                            case 2:
                                message.append(map.get("orderno") + ":已发货，发货单号：" + order.get("ShipNo") + ",物流名称：" + order.get("ShipName") + ",发货时间：" + order.get("ShipTime") + "！&nbsp;");
                                break;
                            case 3:
                                message.append(map.get("orderno") + ":已确认收货！&nbsp;");
                                break;
//                                   case 4:message.append(map.get("orderno")+":未评价！&nbsp;");break;
                            case 5:
                                message.append(map.get("orderno") + ":已完成！&nbsp;");
                                break;
                            case 6:
                                message.append(map.get("orderno") + ":退货中！&nbsp;");
                                break;
                            case 7:
                                message.append(map.get("orderno") + ":已退货！&nbsp;");
                                break;
                        }

                    }
                } else {
                    noordercount++;
                    message.append(map.get("orderno") + ":订单不存在！&nbsp;");
                }
            }
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ForeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        message.append("<br/>总计：成功" + successcount + "条，失败" + failcount + "条,订单不存在" + noordercount + "条！");
        DaoFactory.getExportlogDaoImplJDBC().add("物流信息导入", img, WxMenuUtils.format.format(new Date()), wx.get("id"), op);
        out.print(message.toString());
        out.close();
    }

    protected void chatroom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String openid = request.getParameter("openid");
        String nickname = request.getParameter("nickname");
        String headimgurl = request.getParameter("headimgurl");
        String targetopenid = request.getParameter("targetopenid");
        String content = request.getParameter("content");
        String sign = request.getParameter("sign");
        String wxsid = request.getParameter("wxsid");
        wx = new HashMap<String, String>();
        wx.put("id", wxsid);
        wx = new WxsDAO().getById(wx);
        String times = WxMenuUtils.format.format(new Date());

        PrintWriter out = response.getWriter();
        if ("0".equals(sign)) {//0:第一次请求服务器，要给对方发送邀请信息
            //            保存数据 id,openid.targetopenid,content,isreader.times chatroom
            DaoFactory.getMysqlDao().add("insert into chatroom(openid,targetopenid,content,isreader,times) values ('" + openid + "','" + targetopenid + "','" + URLEncoder.encode(content, "utf-8") + "',0,'" + times + "')");
            String url = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/shop/chatroom.jsp?act=callback&openid=" + targetopenid + "&targetopenid=" + openid + "&wx=" + wxsid;
            String sendStr = "您有来自上家[" + nickname + "]的未读消息\\n请点击 <a href='" + url + "'>查看详情</a>\\n" + times;
            WxMenuUtils.sendCustomService(targetopenid, sendStr, wx);
        } else {//已经交互上
            //更新已读记录
            DaoFactory.getMysqlDao().mod("update chatroom set isreader=1 where openid='" + targetopenid + "' and targetopenid='" + openid + "'");
            DaoFactory.getMysqlDao().add("insert into chatroom(openid,targetopenid,content,isreader,times) values ('" + openid + "','" + targetopenid + "','" + URLEncoder.encode(content, "utf-8") + "',0,'" + times + "')");
        }
        out.print("{\"nickname\":\"" + nickname + "\",\"headimgurl\":\"" + headimgurl + "\",\"content\":\"" + content + "\",\"times\":\"" + times + "\"}");
        out.close();
    }

    protected void chatroomAsync(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String openid = request.getParameter("openid");
        String targetopenid = request.getParameter("targetopenid");
        String nickname = request.getParameter("nickname");
        String headimgurl = request.getParameter("headimgurl");
        String resultStr = "";
        PrintWriter out = response.getWriter();
        //查询有无新纪录
        int id = null != request.getParameter("id") ? Integer.parseInt(request.getParameter("id")) : 0;
        String fieldArr = "id,openid,targetopenid,content,isreader,times";
        List<DataField> newList = (List<DataField>) DaoFactory.getMysqlDao().getList("select " + fieldArr + " from chatroom where openid='" + targetopenid + "' and targetopenid='" + openid + "' and isreader=0 and id>" + id, fieldArr);
        if (0 < newList.size()) {
            resultStr += "[";
            int i = 0;
            for (DataField df : newList) {
                if (0 != i) {
                    resultStr += ",";
                    i++;
                }
                try {
                    resultStr += "{\"nickname\":\"" + nickname + "\",\"headimgurl\":\"" + headimgurl + "\",\"content\":\"" + URLDecoder.decode(df.getFieldValue("content"), "utf-8") + "\",\"times\":\"" + WxMenuUtils.format.format(WxMenuUtils.format.parse(df.getFieldValue("times"))) + "\",\"id\":\"" + df.getFieldValue("id") + "\"}";
                } catch (ParseException ex) {
                    resultStr += "{\"nickname\":\"" + nickname + "\",\"headimgurl\":\"" + headimgurl + "\",\"content\":\"" + URLDecoder.decode(df.getFieldValue("content"), "utf-8") + "\",\"times\":\"" + df.getFieldValue("times") + "\",\"id\":\"" + df.getFieldValue("id") + "\"}";
                }
            }
            resultStr += "]";
        }
        out.print(resultStr);
        out.close();
    }

    /*
     * 公共区
     */
    protected void upload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SmartUpload su = new SmartUpload();
        su.initialize(this.getServletConfig(), request, response);
        try {
            su.upload();
        } catch (Exception e) {
            // TODO: handle exception
        }

        //获取上传的文件
        File file = su.getFiles().getFile(0);

        //删除旧图片
        if (-1 == su.getRequest().getParameter("oldimg").trim().indexOf("http") && !"".equals(su.getRequest().getParameter("oldimg").trim())) {
            java.io.File oldFile = new java.io.File(this.getServletContext()
                    .getRealPath(su.getRequest().getParameter("oldimg")));
            oldFile.delete();
        }
        if (!"".equals(su.getRequest().getParameter("img").trim())) {
            java.io.File oldFile = new java.io.File(this.getServletContext()
                    .getRealPath(su.getRequest().getParameter("img")));
            oldFile.delete();
        }

        //创建图片路径
        StringBuilder path = new StringBuilder("upload/")
                .append(String.valueOf(System.currentTimeMillis())).append(".")
                .append(file.getFileExt());
        try {
            file.saveAs(path.toString());
        } catch (SmartUploadException e) {
            e.printStackTrace();
        }
        request.setAttribute("img", "/" + path.toString());
        request.getRequestDispatcher("/publicfore/uploadfore.jsp").forward(request, response);
    }

    /*
     * 公共区结束
     */
    protected void updateInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String wxsid = request.getParameter("wxsid");
        String openid = request.getParameter("openid");
        String username = request.getParameter("username");
        String headimgurl = request.getParameter("headimgurl");
        SubscriberDAO subscriberDAO = new SubscriberDAO();
        subscriberDAO.updateInfo(openid, Integer.parseInt(wxsid), username, headimgurl);
        Map<String, String> subscriber = subscriberDAO.getByOpenid(wxsid, openid);
        if (!"".equals(subscriber.get("qrcode")) && null != subscriber.get("qrcode")) {
            java.io.File oldFile = new java.io.File(request.getServletContext()
                    .getRealPath(subscriber.get("qrcode")));
            oldFile.delete();
        }
        new SubscriberDAO().updateqrcode(openid, wxsid, "", "", "2016-01-01 00:00:00");
        PrintWriter out = response.getWriter();
        out.print("{\"username\":\"" + subscriber.get("username") + "\",\"headimgurl\":\"" + subscriber.get("headimgurl") + "\"}");
        out.close();
    }

    protected void getPsv(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String propertys = request.getParameter("propertys");
        String pid = request.getParameter("pid");
        String wxsid = request.getParameter("wxsid");
        DataField psv = DaoFactory.getPsvDaoImplJDBC().getBySvids(propertys, Integer.parseInt(pid), 0, wxsid);
        PrintWriter out = response.getWriter();
        out.print("{\"price\":\"" + psv.getFieldValue("price") + "\",\"stock\":\"" + psv.getFieldValue("stock") + "\",\"psvcode\":\"" + psv.getFieldValue("psvcode") + "\"}");
        out.close();
    }
}
