/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.menu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import job.tot.dao.DaoFactory;
import job.tot.exception.DatabaseException;
import job.tot.exception.ObjectNotFoundException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wap.wx.servlet.ForeServlet;

/**
 *
 * @author Administrator
 */
public class WxPayUtils {

    //统一下单
    public Map<String, String> payUnifiedorder(Map<String, String> map, String key) {
        String sign = this.getSignature(map, key);
        String params = "<xml>"
                + "   <appid>" + map.get("appid") + "</appid>"
                + "   <body>" + map.get("body") + "</body>"
                + "   <mch_id>" + map.get("mch_id") + "</mch_id>"
                + "   <nonce_str>" + map.get("nonce_str") + "</nonce_str>"
                + "   <notify_url>" + map.get("notify_url") + "</notify_url>"
                + "   <out_trade_no>" + map.get("out_trade_no") + "</out_trade_no>"
                + "   <spbill_create_ip>" + map.get("spbill_create_ip") + "</spbill_create_ip>"
                + "   <total_fee>" + map.get("total_fee") + "</total_fee>"
                + "   <trade_type>" + map.get("trade_type") + "</trade_type>"
                + "   <sign>" + sign + "</sign>"
                //以下为可选项
                + "   <openid>" + map.get("openid") + "</openid>"
                //                + "   <product_id>" + map.get("product_id") + "</product_id>"
                //                + "   <attach>" + map.get("attach") + "</attach>"
                //                + "   <device_info>" + map.get("device_info") + "</device_info>"
                //                + "   <detail>" + map.get("detail") + "</detail>"
                //                + "   <time_start>" + map.get("time_start") + "</time_start>"
                //                + "   <time_expire>" + map.get("time_expire") + "</time_expire>"
                //                + "   <goods_tag>" + map.get("goods_tag") + "</goods_tag>"
                + "</xml>";
        HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.mch.weixin.qq.com/pay/unifiedorder");
        httpost.setEntity(new StringEntity(params, "UTF-8"));
        try {
            CloseableHttpClient httpclient = WxMenuUtils.httpclient();
            HttpResponse responses = httpclient.execute(httpost);
            String xmlStr = EntityUtils.toString(responses.getEntity(), "utf-8");
            WxMenuUtils.close(httpclient);
            Document document = null;
            try {
                document = DocumentHelper.parseText(xmlStr);
            } catch (DocumentException e) {
                System.out.println("统一下单解析错误！");
            }
            Element root = document.getRootElement();
            String return_code = root.elementTextTrim("return_code");
            if ("SUCCESS".equals(return_code)) {
                String appid = root.elementTextTrim("appid");
                map.put("appid", appid);
                String mch_id = root.elementTextTrim("mch_id");
                String device_info = "";
                try {
                    device_info = root.elementTextTrim("device_info");
                } catch (Exception e) {
                }
                String nonce_str = root.elementTextTrim("nonce_str");
                map.put("nonce_str", nonce_str);
                //验证签名
                Map<String, String> tempMap = new HashMap<String, String>();
                tempMap.put("appid", appid);
                tempMap.put("mch_id", mch_id);
                tempMap.put("device_info", device_info);
                tempMap.put("nonce_str", nonce_str);
                sign = this.getSignature(map, key);
//                if (sign.equals(root.elementTextTrim("sign"))) {
                String result_code = root.elementTextTrim("result_code");
                if ("SUCCESS".equals(result_code)) {
                    String trade_type = root.elementTextTrim("trade_type");
                    String prepay_id = root.elementTextTrim("prepay_id");
                    map.put("prepay_id", prepay_id);
                    try {
                        String code_url = root.elementTextTrim("code_url");
                        map.put("code_url", code_url);
                        System.out.println("prepay_id  " + prepay_id);
                        System.out.println("code_url  " + code_url);
                    } catch (Exception e) {
                        System.out.println("非NATIVE方法！");
                    }
                    return map;
                } else {
                    System.out.println("err_code  " + root.elementTextTrim("err_code"));
                    System.out.println("err_code_des  " + root.elementTextTrim("err_code_des"));
                }
//                } else {
//                    System.out.println("返回值签名验证失败！");
//                }
            } else {
                System.out.println("return_msg  " + root.elementTextTrim("return_msg"));
            }
        } catch (IOException ex) {
            Logger.getLogger(WxPayUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new HashMap();
    }

    //查询订单
    public Map<String, String> payorderquery(Map<String, String> map, Map<String, String> wx) {
        String sign = this.getSignature(map, wx.get("wxpaykey"));
        map.put("sign", sign);
        String tempStr = null != map.get("transaction_id") ? "   <transaction_id>" + map.get("transaction_id") + "</transaction_id>" : "   <out_trade_no>" + map.get("out_trade_no") + "</out_trade_no>";
        String params = "<xml>"
                + "   <appid>" + map.get("appid") + "</appid>"
                + "   <mch_id>" + map.get("mch_id") + "</mch_id>"
                + "   <nonce_str>" + map.get("nonce_str") + "</nonce_str>"
                + "   <sign>" + sign + "</sign>"
                //以下为可选项（二选一）
                + tempStr
                + "</xml>";
        HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.mch.weixin.qq.com/pay/orderquery");
        httpost.setEntity(new StringEntity(params, "UTF-8"));
        try {
            CloseableHttpClient httpclient = WxMenuUtils.httpclient();
            HttpResponse responses = httpclient.execute(httpost);
            String xmlStr = EntityUtils.toString(responses.getEntity(), "utf-8");
            WxMenuUtils.close(httpclient);
            Document document = null;
            try {
                document = DocumentHelper.parseText(xmlStr);
            } catch (DocumentException e) {
                System.out.println("查询订单解析错误！");
            }
            Element root = document.getRootElement();
            String return_code = root.elementTextTrim("return_code");
            System.out.println("return_code  " + root.elementTextTrim("return_msg"));
            if ("SUCCESS".equals(return_code)) {
                String appid = root.elementTextTrim("appid");
                String mch_id = root.elementTextTrim("mch_id");
                String nonce_str = root.elementTextTrim("nonce_str");
                //验证签名
                Map<String, String> tempMap = new HashMap<String, String>();
                tempMap.put("appid", appid);
                tempMap.put("mch_id", mch_id);
                tempMap.put("nonce_str", nonce_str);
                sign = this.getSignature(map, wx.get("wxpaykey"));
                if (sign.equals(root.elementTextTrim("sign"))) {
                    System.out.println("查询订单验证签名成功！");
                    String result_code = root.elementTextTrim("result_code");
                    if ("SUCCESS".equals(result_code)) {
                        String trade_state = root.elementTextTrim("trade_state");
                        String openid = root.elementTextTrim("openid");
                        String is_subscribe = root.elementTextTrim("is_subscribe");
                        String trade_type = root.elementTextTrim("trade_type");
                        String bank_type = root.elementTextTrim("bank_type");
                        String total_fee = root.elementTextTrim("total_fee");
                        String time_end = root.elementTextTrim("time_end");

                        try {
                            String device_info = root.elementTextTrim("device_info");
                            String coupon_fee = root.elementTextTrim("coupon_fee");
                            String fee_type = root.elementTextTrim("fee_type");
                            String transaction_id = root.elementTextTrim("transaction_id");
                            String out_trade_no = root.elementTextTrim("out_trade_no");
                            String attach = root.elementTextTrim("attach");
                            //订单成功选处理 参数封装
                            //根据订单编号，修改订单支付状态
                            //更改订单状态 
                            DaoFactory.getOrderDAO().modIsPay(out_trade_no, 2);
                            try {
                                DaoFactory.getOrderDAO().modSts(out_trade_no, 1);
                            } catch (ObjectNotFoundException ex) {
                                Logger.getLogger(ForeServlet.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (DatabaseException ex) {
                                Logger.getLogger(ForeServlet.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (Exception e) {
                            System.out.println("查询订单取无返回值异常！");
                        }

                    } else {
                        System.out.println("err_code  " + root.elementTextTrim("err_code"));
                        System.out.println("err_code_des  " + root.elementTextTrim("err_code_des"));
                    }
                } else {
                    System.out.println("查询订单验证签名失败！");
                }
            } else {
                System.out.println("err_code_des  " + root.elementTextTrim("err_code_des"));
            }
        } catch (IOException ex) {
            Logger.getLogger(WxPayUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new HashMap();
    }

    //关闭订单
    public Map<String, String> paycloseorder(Map<String, String> map, String key) {
        String sign = this.getSignature(map, key);
        map.put("sign", sign);
        String tempStr = null != map.get("out_trade_no") ? "   <out_trade_no>" + map.get("out_trade_no") + "</out_trade_no>" : "";
        String params = "<xml>"
                + "   <appid>" + map.get("appid") + "</appid>"
                + "   <mch_id>" + map.get("mch_id") + "</mch_id>"
                + tempStr
                + "   <nonce_str>" + map.get("nonce_str") + "</nonce_str>"
                + "   <sign>" + sign + "</sign>"
                + "</xml>";
        HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.mch.weixin.qq.com/pay/closeorder");
        httpost.setEntity(new StringEntity(params, "UTF-8"));
        try {
            CloseableHttpClient httpclient = WxMenuUtils.httpclient();
            HttpResponse responses = httpclient.execute(httpost);
            String xmlStr = EntityUtils.toString(responses.getEntity(), "utf-8");
            WxMenuUtils.close(httpclient);
            Document document = null;
            try {
                document = DocumentHelper.parseText(xmlStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Element root = document.getRootElement();
            String return_code = root.elementTextTrim("return_code");
            System.out.println("return_code  " + root.elementTextTrim("return_msg"));
            if ("SUCCESS".equals(return_code)) {
                String appid = root.elementTextTrim("appid");
                String mch_id = root.elementTextTrim("mch_id");
                String nonce_str = root.elementTextTrim("nonce_str");
                //验证签名
                Map<String, String> tempMap = new HashMap<String, String>();
                tempMap.put("appid", appid);
                tempMap.put("mch_id", mch_id);
                tempMap.put("nonce_str", nonce_str);
                sign = this.getSignature(map, key);
                if (sign.equals(root.elementTextTrim("sign"))) {
                    System.out.println("查询订单验证签名成功！");
                    String result_code = root.elementTextTrim("result_code");
                    if ("SUCCESS".equals(result_code)) {
                        //关单成功处理 参数封装
                    } else {
                        System.out.println("err_code  " + root.elementTextTrim("err_code"));
                        System.out.println("err_code_des  " + root.elementTextTrim("err_code_des"));
                    }
                } else {
                    System.out.println("查询订单验证签名失败！");
                }
            } else {
                System.out.println("err_code_des  " + root.elementTextTrim("err_code_des"));
            }
        } catch (IOException ex) {
            Logger.getLogger(WxPayUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new HashMap();
    }

    //退款
    public Map<String, String> payrefund(HttpServletRequest request, Map<String, String> map, Map<String, String> wx) {
        String sign = this.getSignature(map, wx.get("wxpaykey"));
        map.put("sign", sign);
        String tempStr = null != map.get("device_info") ? "   <device_info>" + map.get("device_info") + "</device_info>" : "";
        String params = "<xml>"
                + "   <appid>" + map.get("appid") + "</appid>"
                + "   <mch_id>" + map.get("mch_id") + "</mch_id>"
                + "   <nonce_str>" + map.get("nonce_str") + "</nonce_str>"
                + "   <op_user_id>" + map.get("op_user_id") + "</op_user_id>"
                + "   <out_refund_no>" + map.get("out_refund_no") + "</out_refund_no>"
                + "   <out_trade_no>" + map.get("out_trade_no") + "</out_trade_no>"
                + "   <refund_fee>" + map.get("refund_fee") + "</refund_fee>"
                + "   <total_fee>" + map.get("total_fee") + "</total_fee>"
                //   + "   <transaction_id>" + map.get("transaction_id") + "</transaction_id>"
                //   + tempStr
                //   + "   <refund_fee_type>" + map.get("refund_fee_type") + "</refund_fee_type>"
                + "   <sign>" + sign + "</sign>"
                + "</xml>";
        HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.mch.weixin.qq.com/secapi/pay/refund");
        httpost.setEntity(new StringEntity(params, "UTF-8"));
        try {
            CloseableHttpClient httpsclient = WxMenuUtils.httpsclient(request, wx.get("mch_id"), wx.get("certificate"));
            HttpResponse responses = httpsclient.execute(httpost);
            String xmlStr = EntityUtils.toString(responses.getEntity(), "utf-8");
            WxMenuUtils.close(httpsclient);
            Document document = null;
            try {
                document = DocumentHelper.parseText(xmlStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Element root = document.getRootElement();
            String return_code = root.elementTextTrim("return_code");
            System.out.println("return_code  " + root.elementTextTrim("return_msg"));
            if ("SUCCESS".equals(return_code)) {
                String appid = root.elementTextTrim("appid");
                String mch_id = root.elementTextTrim("mch_id");
                String nonce_str = root.elementTextTrim("nonce_str");
                String transaction_id = root.elementTextTrim("transaction_id");
                String out_trade_no = root.elementTextTrim("out_trade_no");
                String out_refund_no = root.elementTextTrim("out_refund_no");
                String refund_id = root.elementTextTrim("refund_id");
                String refund_fee = root.elementTextTrim("refund_fee");
                String total_fee = root.elementTextTrim("total_fee");
                String cash_fee = root.elementTextTrim("cash_fee");
                try {
                    String device_info = root.elementTextTrim("device_info");
                    String refund_channel = root.elementTextTrim("refund_channel");
                    String fee_type = root.elementTextTrim("fee_type");
                    String cash_refund_fee = root.elementTextTrim("cash_refund_fee");
                    String coupon_refund_fee = root.elementTextTrim("coupon_refund_fee");
                    String coupon_refund_count = root.elementTextTrim("coupon_refund_count");
                    String coupon_refund_id = root.elementTextTrim("coupon_refund_id");
                } catch (Exception e) {
                }
//                //验证签名
//                Map<String, String> tempMap = new HashMap<String, String>();
//                tempMap.put("appid", appid);
//                tempMap.put("mch_id", mch_id);
//                tempMap.put("nonce_str", nonce_str);
//                sign = this.getSignature(map, key);
//                System.out.println(sign + " 4 " + root.elementTextTrim("sign"));
//                if (sign.equals(root.elementTextTrim("sign"))) {
//                    System.out.println("查询订单验证签名成功！");
                String result_code = root.elementTextTrim("result_code");
                if ("SUCCESS".equals(result_code)) {
                    //退款成功处理 参数封装
                    System.out.println("out_trade_no " + out_trade_no);
                    //更改订单状态
                    DaoFactory.getOrderDAO().modPayTypeByout_trade_no(out_trade_no, 1);
                    DaoFactory.getOrderDAO().modIsPayByout_trade_no(out_trade_no, 5);
                    try {
                        DaoFactory.getOrderDAO().modStateByout_trade_no(out_trade_no, 2);
                    } catch (ObjectNotFoundException ex) {
                        Logger.getLogger(WxPayUtils.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (DatabaseException ex) {
                        Logger.getLogger(WxPayUtils.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    System.out.println("err_code  " + root.elementTextTrim("err_code"));
                    System.out.println("err_code_des  " + root.elementTextTrim("err_code_des"));
                }
//                } else {
//                    System.out.println("查询订单验证签名失败！");
//                }
            } else {
                System.out.println("return_msg  " + root.elementTextTrim("return_msg"));
            }
        } catch (IOException ex) {
            Logger.getLogger(WxPayUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //查询退款
    public Map<String, String> refundquery(Map<String, String> map, String key) {
        String sign = this.getSignature(map, key);
        map.put("sign", sign);
        String tempStr = null != map.get("device_info") ? "   <device_info>" + map.get("device_info") + "</device_info>" : "";
        String params = "<xml>"
                + "   <appid>" + map.get("appid") + "</appid>"
                + "   <mch_id>" + map.get("mch_id") + "</mch_id>"
                + "   <nonce_str>" + map.get("nonce_str") + "</nonce_str>"
                + "   <out_refund_no>" + map.get("out_refund_no") + "</out_refund_no>"
                + "   <out_trade_no>" + map.get("out_trade_no") + "</out_trade_no>"
                + "   <refund_id>" + map.get("refund_id") + "</refund_id>"
                + "   <transaction_id>" + map.get("transaction_id") + "</transaction_id>"
                + tempStr
                + "   <sign>" + sign + "</sign>"
                + "</xml>";
        HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.mch.weixin.qq.com/pay/refundquery");
        httpost.setEntity(new StringEntity(params, "UTF-8"));
        try {
            CloseableHttpClient httpclient = WxMenuUtils.httpclient();
            HttpResponse responses = httpclient.execute(httpost);
            String xmlStr = EntityUtils.toString(responses.getEntity(), "utf-8");
            WxMenuUtils.close(httpclient);
            Document document = null;
            try {
                document = DocumentHelper.parseText(xmlStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Element root = document.getRootElement();
            String return_code = root.elementTextTrim("return_code");
            System.out.println("return_code  " + root.elementTextTrim("return_msg"));
            if ("SUCCESS".equals(return_code)) {
                String appid = root.elementTextTrim("appid");
                String mch_id = root.elementTextTrim("mch_id");
                String nonce_str = root.elementTextTrim("nonce_str");
                String transaction_id = root.elementTextTrim("transaction_id");
                String out_trade_no = root.elementTextTrim("out_trade_no");
                String total_fee = root.elementTextTrim("total_fee");
                String cash_fee = root.elementTextTrim("cash_fee");
                String coupon_refund_fee = root.elementTextTrim("coupon_refund_fee");
                String refund_fee = root.elementTextTrim("refund_fee");
                String refund_count = root.elementTextTrim("refund_count");
                try {
                    String device_info = root.elementTextTrim("device_info");
                    String fee_type = root.elementTextTrim("fee_type");
                    String cash_fee_type = root.elementTextTrim("cash_fee_type");
                } catch (Exception e) {
                }
//                //验证签名
//                Map<String, String> tempMap = new HashMap<String, String>();
//                tempMap.put("appid", appid);
//                tempMap.put("mch_id", mch_id);
//                tempMap.put("nonce_str", nonce_str);
//                sign = this.getSignature(map, key);
//                System.out.println(sign + " 4 " + root.elementTextTrim("sign"));
//                if (sign.equals(root.elementTextTrim("sign"))) {
//                    System.out.println("查询订单验证签名成功！");
                String result_code = root.elementTextTrim("result_code");
                if ("SUCCESS".equals(result_code)) {
                    //退款查询成功处理 参数封装
                } else {
                    System.out.println("err_code  " + root.elementTextTrim("err_code"));
                    System.out.println("err_code_des  " + root.elementTextTrim("err_code_des"));
                }
//                } else {
//                    System.out.println("查询订单验证签名失败！");
//                }
            } else {
                System.out.println("return_msg  " + root.elementTextTrim("return_msg"));
            }
        } catch (IOException ex) {
            Logger.getLogger(WxPayUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new HashMap();
    }

    //对账单
    public Map<String, String> downloadbill(Map<String, String> map, String key, ServletContext context) {
        String sign = this.getSignature(map, key);
        map.put("sign", sign);
        String tempStr = null != map.get("device_info") ? "   <device_info>" + map.get("device_info") + "</device_info>" : "";
        String params = "<xml>"
                + "   <appid>" + map.get("appid") + "</appid>"
                + "   <mch_id>" + map.get("mch_id") + "</mch_id>"
                + "   <nonce_str>" + map.get("nonce_str") + "</nonce_str>"
                + "   <bill_date>" + map.get("bill_date") + "</bill_date>"
                + "   <bill_type>" + map.get("bill_type") + "</bill_type>"
                + tempStr
                + "   <sign>" + sign + "</sign>"
                + "</xml>";
        HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.mch.weixin.qq.com/pay/downloadbill");
        httpost.setEntity(new StringEntity(params, "UTF-8"));
        try {
            CloseableHttpClient httpclient = WxMenuUtils.httpclient();
            HttpResponse responses = httpclient.execute(httpost);
            String xmlStr = EntityUtils.toString(responses.getEntity(), "utf-8");
            WxMenuUtils.close(httpclient);
            Document document = null;
            try {
                document = DocumentHelper.parseText(xmlStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Element root = document.getRootElement();
            String return_code = root.elementTextTrim("return_code");
            System.out.println("return_code  " + root.elementTextTrim("return_msg"));
            if ("SUCCESS".equals(return_code)) {
                byte[] bytes = EntityUtils.toByteArray(responses.getEntity());
                //获取图片信息
                String type = responses.getHeaders("Content-Type")[0].getValue().split("/")[0];
                String fileExt = responses.getHeaders("Content-Type")[0].getValue().split("/")[1];
                System.out.println(type + "   " + fileExt);
                StringBuilder path = new StringBuilder("upload/")
                        .append(String.valueOf(System.currentTimeMillis())).append(".")
                        .append(fileExt);
                FileOutputStream fileOutputStream = new FileOutputStream(new File(context.getRealPath("/" + path.toString())));
                fileOutputStream.write(bytes);
                fileOutputStream.close();
                map.put("path", path.toString());
                return map;
            } else {
                System.out.println("return_msg  " + root.elementTextTrim("return_msg"));
            }
        } catch (IOException ex) {
            Logger.getLogger(WxPayUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new HashMap();
    }

    //生成签名
    public String getSignature(Map<String, String> map, String paykey) {
        Iterator it = map.keySet().iterator();
        String[] tempArr = new String[map.size()];
        int index = 0;
        while (it.hasNext()) {
            String key = (String) it.next();
            String value = map.get(key);
            if (!"".equals(value) && null != value) {
                tempArr[index] = key;
                index++;
            }
        }
        String[] tmpArr = new String[index];
        for (int i = 0; i < index; i++) {
            tmpArr[i] = tempArr[i];
        }
        Arrays.sort(tmpArr);
        StringBuffer bf = new StringBuffer();
        for (int i = 0; i < tmpArr.length; i++) {
            if (0 != i) {
                bf.append("&");
            }
            bf.append(tmpArr[i] + "=" + map.get(tmpArr[i]));
        }
        String tmpStr = bf.toString() + "&key=" + paykey;
        tmpStr = this.MD5(tmpStr).toUpperCase();
        return tmpStr;
    }

    //MD5加密
    public String MD5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.toString();
    }

    /**
     * 发送模板消息
     *
     * @param map(touser,template_id,url,topcolor)
     * @param mapmap(key,value(value,color))
     * @param wx(id)
     */
    public void sendtemplatemessage(Map<String, String> map, Map<String, Map<String, String>> mapmap, Map<String, String> wx) {
        StringBuilder params = new StringBuilder("{");
        for (String key : map.keySet()) {
            params.append("\"" + key + "\":\"" + map.get(key) + "\",");
        }
        params.append("\"data\":{");
        int sign = 0;
        for (String key : mapmap.keySet()) {
            map = mapmap.get(key);
            if (0 != sign) {
                params.append(",");
            }
            params.append("\"" + key + "\": {");
            params.append("\"value\":\"" + map.get("value") + "\",");
            params.append("\"color\":\"" + map.get("color") + "\"");
            params.append(" }");
            sign++;
        }
        params.append("}}");
        HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + WxMenuUtils.getAccessToken(wx));
        httpost.setEntity(new StringEntity(params.toString(), "UTF-8"));
        try {
            CloseableHttpClient httpclient = WxMenuUtils.httpclient();
            HttpResponse responses = httpclient.execute(httpost);
            String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
            WxMenuUtils.close(httpclient);
            JSONObject object = JSON.parseObject(jsonStr);
            String errcode = object.getString("errcode");
            String errmsg = object.getString("errmsg");
            String msgid = object.getString("msgid");
            System.out.println("模板消息：errcode " + errcode + "errmsg " + errmsg + " msgid " + msgid);
        } catch (IOException ex) {
            Logger.getLogger(WxPayUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //发送红包
    public String sendredpack(HttpServletRequest request, Map<String, String> wx, String openid, String total_amount, String ip) {
        String flag = "";
        WxPayUtils wxPayUtils = new WxPayUtils();
        Map<String, String> tempmap = new HashMap();
        tempmap.put("nonce_str", String.valueOf(System.currentTimeMillis()));//随机字符串
        tempmap.put("mch_billno", wx.get("mch_id") + WxMenuUtils.format2.format(new java.util.Date()) + String.valueOf((int) ((Math.random() * 9 + 1) * 1000)));//商户订单号
        tempmap.put("mch_id", wx.get("mch_id"));//商户号
        tempmap.put("wxappid", wx.get("appid"));//公众账号id
//        tempmap.put("nick_name", map.get("nick_name"));//提供方名称
        tempmap.put("send_name", wx.get("send_name"));//商户名称
        tempmap.put("re_openid", openid);//用户openid
        tempmap.put("total_amount", total_amount);//付款金额 分
//        tempmap.put("min_value", total_amount);//最小红包金额 分
//        tempmap.put("max_value",total_amount);//最大红包金额 分 这三个相等
        tempmap.put("total_num", "1");//红包发送人数 1
        tempmap.put("wishing", wx.get("wishing"));//红包祝福语
        tempmap.put("client_ip", ip);//ip
        tempmap.put("act_name", "佣金提现红包");//活动名称
        tempmap.put("remark", wx.get("redremark"));//备注
//        tempmap.put("logo_imgurl", map.get("logo_imgurl"));//商户logo的url 否

        //签名
        String sign = wxPayUtils.getSignature(tempmap, wx.get("wxpaykey"));
        tempmap.put("sign", sign);

        StringBuilder params = new StringBuilder();
        params.append("<xml>");
        for (Object key : tempmap.keySet()) {
            params.append("<" + key + "><![CDATA[" + tempmap.get(key) + "]]></" + key + ">");
        }
        params.append("</xml>");

        HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack");
        httpost.setEntity(new StringEntity(params.toString(), "UTF-8"));
        try {
            CloseableHttpClient httpsclient = WxMenuUtils.httpsclient(request, wx.get("mch_id"), wx.get("certificate"));
            HttpResponse responses = httpsclient.execute(httpost);
            String xmlStr = EntityUtils.toString(responses.getEntity(), "utf-8");
            WxMenuUtils.close(httpsclient);
            Document document = null;
            try {
                document = DocumentHelper.parseText(xmlStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Element root = document.getRootElement();
            String return_code = root.elementTextTrim("return_code");
            if ("SUCCESS".equals(return_code)) {
                sign = root.elementTextTrim("sign");
                String result_code = root.elementTextTrim("result_code");
                if ("SUCCESS".equals(result_code)) {
//                    String mch_billno = root.elementTextTrim("mch_billno");
//                    String mch_id = root.elementTextTrim("mch_id");
//                    String wxappid = root.elementTextTrim("wxappid");
//                    String re_openid = root.elementTextTrim("re_openid");
//                    total_amount = root.elementTextTrim("total_amount");
//                    String send_time = root.elementTextTrim("send_time");
//                    String send_listid = root.elementTextTrim("send_listid");

                    //发放成功处理 参数封装
                    //id,openid,mch_billno,nick_name,send_name,total_amount,wishing,client_ip,act_name,remark
                    flag = result_code + ",";
                } else {
                    flag = result_code + "," + root.elementTextTrim("err_code_des");
                    System.out.println("err_code  " + root.elementTextTrim("err_code"));
                    System.out.println("err_code_des  " + root.elementTextTrim("err_code_des"));
                }
            } else {
                flag = return_code + "," + root.elementTextTrim("return_msg");
                System.out.println("return_msg  " + root.elementTextTrim("return_msg"));
            }
        } catch (IOException ex) {
            System.out.println("发放红包错误：" + openid);
        }
        System.out.println("flag " + flag);
        return flag;
    }
}
