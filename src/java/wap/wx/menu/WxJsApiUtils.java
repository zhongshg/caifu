/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.menu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import wap.wx.dao.ApiDAO;

/**
 *
 * @author Administrator
 */
public class WxJsApiUtils {

    //jsapi配置参数
    public Map<String, String> jsapi(String url, Map<String, String> wx) {
        ApiDAO apiDAO = new ApiDAO();
        Map<String, String> map = apiDAO.get("jsapi_ticket", Integer.parseInt(wx.get("id")));
        String jsapi_ticket = map.get("apivalue");
        long jsapi_timeslong = 0;
        try {
            jsapi_timeslong = WxMenuUtils.format.parse(map.get("times")).getTime();
        } catch (Exception e) {
            System.out.println("无原始数据！");
        }
        if (7000 * 1000 < System.currentTimeMillis() - jsapi_timeslong) {//jsapi_ticket超时
            try {
                HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + WxMenuUtils.getAccessToken(wx) + "&type=jsapi");
                CloseableHttpClient httpclient = WxMenuUtils.httpclient();
                HttpResponse responses = httpclient.execute(get);
                String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
                WxMenuUtils.close(httpclient);

                JSONObject object = JSON.parseObject(jsonStr);
                jsapi_ticket = object.getString("ticket");
                map.put("apikey", "jsapi_ticket");
                map.put("apivalue", jsapi_ticket);
                map.put("times", WxMenuUtils.format.format(new Date()));
                apiDAO.set(map, Integer.parseInt(wx.get("id")));
            } catch (IOException ex) {
                System.out.println("获取jsapi_ticket失败！");
            }
        }
        String noncestr = UUID.randomUUID().toString();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        map = new LinkedHashMap<String, String>();
        map.put("noncestr", noncestr);
        map.put("jsapi_ticket", jsapi_ticket);
        map.put("timestamp", timestamp);
        map.put("url", url);
        String signature = this.getSignature(map);
        map.put("signature", signature);
        map.put("appId", wx.get("appid"));
        //'checkJsApi','onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo','hideMenuItems','showMenuItems','hideAllNonBaseMenuItem','showAllNonBaseMenuItem','translateVoice','startRecord','stopRecord','onRecordEnd','playVoice','pauseVoice','stopVoice','uploadVoice','downloadVoice','chooseImage','previewImage','uploadImage','downloadImage','getNetworkType','openLocation','getLocation','hideOptionMenu','showOptionMenu','closeWindow','scanQRCode','chooseWXPay','openProductSpecificView','addCard','chooseCard','openCard'
        return map;
    }

    //生成签名
    public String getSignature(Map<String, String> map) {
        String[] tmpArr = new String[map.size()];
        Iterator it = map.keySet().iterator();
        for (int i = 0; it.hasNext(); i++) {
            String key = (String) it.next();
            tmpArr[i] = key;
        }
        Arrays.sort(tmpArr);
        StringBuffer bf = new StringBuffer();
        for (int i = 0; i < tmpArr.length; i++) {
            if (0 != i) {
                bf.append("&");
            }
            bf.append(tmpArr[i] + "=" + map.get(tmpArr[i]));
        }
        String tmpStr = bf.toString();
        tmpStr = this.SHA1Encode(tmpStr);
        return tmpStr;
    }

    //sha1加密
    public String SHA1Encode(String sourceString) {
        String resultString = null;
        try {
            resultString = new String(sourceString);
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            resultString = byte2hexString(md.digest(resultString.getBytes()));
        } catch (Exception ex) {
        }
        return resultString;
    }

    public final String byte2hexString(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buf.toString().toLowerCase();
    }
}
