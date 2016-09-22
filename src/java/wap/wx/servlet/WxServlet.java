/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wap.wx.dao.ItemsmDAO;
import wap.wx.dao.NativeDAO;
import wap.wx.dao.NewsDAO;
import wap.wx.dao.NewstypesDAO;
import wap.wx.dao.PerMaterialDAO;
import wap.wx.dao.SubscriberDAO;
import wap.wx.dao.SubscriberdetailDAO;
import wap.wx.dao.TextDAO;
import wap.wx.dao.WxsDAO;
import wap.wx.menu.WxMenuUtils;
import wap.wx.service.SubscriberService;

public class WxServlet extends HttpServlet {

    private final SubscriberDAO subscriberDAO = new SubscriberDAO();
    private final SubscriberdetailDAO subscriberdetailDAO = new SubscriberdetailDAO();
    private final NativeDAO nativeDAO = new NativeDAO();
    private final TextDAO textDAO = new TextDAO();
    private final ItemsmDAO itemsmDAO = new ItemsmDAO();
    private PerMaterialDAO permaterialDAO = new PerMaterialDAO();
    private SubscriberService subscriberService = new SubscriberService();
    private Map<String, String> subscriber = new HashMap<String, String>();
    private final Map<String, String> subscriberdetail = new HashMap<String, String>();
    private Map<String, String> natives = new HashMap<String, String>();
    private Map<String, String> texts = new HashMap<String, String>();
    private final Map<String, String> newsm = new HashMap<String, String>();
    private Map<String, String> permaterial = new HashMap<String, String>();
    private int wxsid = 0;
    private int uid = 0;

    //自动回复内容
    public void responseMsg(HttpServletRequest request, HttpServletResponse response) {
        String postStr = null;
        try {
            postStr = this.readStreamParameter(request.getInputStream());
            postStr = new String(postStr.getBytes("gbk"), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
//            System.out.println(postStr);
        if (null != postStr && !postStr.isEmpty()) {
            Document document = null;
            try {
                document = DocumentHelper.parseText(postStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (null == document) {
                this.print(request, response, "");
                return;
            }
            Element root = document.getRootElement();
            String msgType = root.elementTextTrim("MsgType");
            String fromUsername = root.elementText("FromUserName");
            String toUsername = root.elementText("ToUserName");
            String keyword = "";
            try {
                keyword = root.elementTextTrim("Content");
            } catch (Exception e) {
            }
            String time = new Date().getTime() + "";
            String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

            String appid = request.getParameter("wx");
            Map<String, String> wx = new HashMap<String, String>();
            wx.put("appid", appid);
            wx = new WxsDAO().getByAppid(wx);
            try {
                wxsid = Integer.parseInt(wx.get("id"));
                uid = wxsid;
            } catch (Exception e) {
                System.out.println("微信不存在！");
            }

            //id,openid,nickname,sex,language,city,province,country,headimgurl,unionid,percent,remark,times
            subscriber = subscriberDAO.getByOpenid(String.valueOf(wxsid), fromUsername);

            //用户发送语音消息时转为文本消息
            if ("voice".equals(msgType)) {
                keyword = root.elementText("Recognition");
//                System.out.println("recognition   " + keyword);
                msgType = "text";
            }

            //用户输入文本消息时自动回复
            String event_key = "";
            if ("text".equals(msgType)) {

                //id,openid,dates,msgtype,event,content,mark  subscriberdetail
                subscriberdetail.put("openid", fromUsername);
                subscriberdetail.put("dates", date);
                subscriberdetail.put("msgtype", msgType);
                subscriberdetail.put("event", "");
                subscriberdetail.put("content", keyword);
                subscriberdetail.put("mark", "");
                subscriberdetailDAO.add(subscriberdetail, wxsid);

                natives.put("name", keyword);
                natives = nativeDAO.getByName(natives, uid);
                if (null == natives.get("id")) {
                    natives.put("wds", "2");
                    natives = nativeDAO.getByWds(natives, uid);
                    if (null == natives.get("id")) {
                        return;
                    }
                }
                //id,name,type,lid,remark,wds
                event_key = natives.get("type") + "," + natives.get("lid");
                this.print(request, response, this.getContent(request, response, toUsername, fromUsername, time, event_key));
            }

            // 事件推送
            if (msgType.equals("event")) {
                // 事件类型
                String eventType = root.elementTextTrim("Event");

                //id,openid,dates,msgtype,event,content,mark  subscriberdetail
                subscriberdetail.put("openid", fromUsername);
                subscriberdetail.put("dates", date);
                subscriberdetail.put("msgtype", msgType);
                subscriberdetail.put("event", eventType);
                subscriberdetail.put("content", "");
                subscriberdetail.put("mark", "");
                subscriberdetailDAO.add(subscriberdetail, wxsid);

                // 订阅发送文本消息
                if (eventType.equals("subscribe")) {
                    String parentopenid = "0";
                    try {
                        String eventKey = root.elementTextTrim("EventKey");
                        String Ticket = root.elementTextTrim("Ticket");
                        if (!eventKey.split("_")[1].equals(fromUsername)) {
                            parentopenid = eventKey.split("_")[1];
                        } else {
                            System.out.println("扫描自己二维码无效!");
                        }
                    } catch (Exception e) {
                        System.out.println("新关注未扫描二维码！");
                    }

                    if (null != subscriber.get("id")) {
                        //一次绑定永久有效 
                        //判断用户有无更换微信昵称头像等信息
                        subscriber = subscriberService.addSubscriber(wx, fromUsername, parentopenid);
//                        subscriberDAO.updateParentopenid(fromUsername, String.valueOf(wxsid), parentopenid);
                    } else {

                        subscriber = subscriberService.addSubscriber(wx, fromUsername, parentopenid);
                        //发送被扫二维码推送消息
                        Map<String, String> parentsubscriber = subscriberDAO.getById(wx.get("id"), parentopenid);
                        String content = "【" + subscriber.get("nickname") + "】通过二维码关注了本公众号，成为您的" + wx.get("name") + wx.get("subtitle1");// "一级家族成员！";

                        //判断有无上级
                        if (null != parentsubscriber.get("openid")) {
                            WxMenuUtils.sendCustomService(parentsubscriber.get("openid"), content, wx);

                            //往上再取两级，三级都通知
                            if (!"0".equals(parentsubscriber.get("parentopenid"))) {
                                Map<String, String> secondparentsubscriber = subscriberDAO.getById(wx.get("id"), parentsubscriber.get("parentopenid"));
                                content = "【" + subscriber.get("nickname") + "】通过二维码关注了本公众号，成为您的" + wx.get("name") + wx.get("subtitle2");// "二级家族成员！";
                                WxMenuUtils.sendCustomService(secondparentsubscriber.get("openid"), content, wx);
//                                if (!"0".equals(secondparentsubscriber.get("parentopenid"))) {
//                                    Map<String, String> thirdparentsubscriber = subscriberDAO.getById(wx.get("id"), secondparentsubscriber.get("parentopenid"));
//                                    content = "【" + subscriber.get("nickname") + "】通过二维码关注了本公众号，成为您的" + wx.get("name") + wx.get("subtitle3");// "三级家族成员！";
//                                    WxMenuUtils.sendCustomService(thirdparentsubscriber.get("openid"), content, wx);
//                                }
                            }

                        } else {
                            //给商家用户发送通知
                            if (!"0".equals(wx.get("adminsubscriber"))) {
                                WxMenuUtils.sendCustomService(wx.get("adminsubscriber"), content, wx);
                            }
                        }
                    }

                    //模式解说特例
                    List<Map<String, String>> newsList = new NewsDAO().getList("2", Integer.parseInt(wx.get("id")));
                    String tempStr = "";
                    if (0 < newsList.size()) {
                        tempStr = "如果想了解我们的模式<a href='" + request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/ForeServlet?method=newsdemo&id=" + newsList.get(0).get("id") + "&wx=" + wxsid + "#mp.weixin.qq.com'>点击这里</a>。";
                    }

                    //发送关注信息
//                    natives.put("wds", "1");
//                    natives = nativeDAO.getByWds(natives, uid);
//                    if (null == natives.get("id")) {
//                        return;
//                    }
//                    //id,name,type,lid,remark,wds
//                    event_key = natives.get("type") + "," + natives.get("lid");
                    subscriber = subscriberDAO.getByOpenid(String.valueOf(wxsid), fromUsername);
                    String text = "<xml>"
                            + "<ToUserName><![CDATA[%1$s]]></ToUserName>"
                            + "<FromUserName><![CDATA[%2$s]]></FromUserName>"
                            + "<CreateTime>%3$s</CreateTime>"
                            + "<MsgType><![CDATA[%4$s]]></MsgType>"
                            + "<Content><![CDATA[%5$s]]></Content>"
                            + "</xml>";
                    String content = "恭喜您已经成为【" + wx.get("name") + "】的" + wx.get("title1") + "候选人，您只需到【" + wx.get("name") + "】<a href='" + request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/ForeServlet?method=shop&wx=" + wxsid + "#mp.weixin.qq.com'>点击这里</a>选购一件商品，即可成为【" + wx.get("name") + "】的" + wx.get("title1") + "。" + tempStr;
                    this.print(request, response, text.format(text, fromUsername, toUsername, time, "text", content));
                } else if (eventType.equals("SCAN")) {//关注后扫描事件
                    String parentopenid = "0";
//                    try {
//                        String eventKey = root.elementTextTrim("EventKey");
//                        String Ticket = root.elementTextTrim("Ticket");
//                        System.out.println("eventKey " + eventKey);
//                        if (!eventKey.equals(fromUsername)) {
//                            parentopenid = eventKey;
//                        } else {
//                            System.out.println("扫描自己二维码无效!");
//                        }
//                    } catch (Exception e) {
//                        System.out.println("已关注未扫描二维码！");
//                    }
                    //id,openid,nickname,sex,language,city,province,country,headimgurl,unionid,percent,remark,times
                    subscriber = subscriberDAO.getByOpenid(String.valueOf(wxsid), fromUsername);
                    if (null != subscriber.get("id")) {
//                        System.out.println("updateparentopenid " + parentopenid);
//                        subscriberDAO.updateParentopenid(fromUsername, String.valueOf(wxsid), parentopenid);
                    } else {
                        subscriber = subscriberService.addSubscriber(wx, fromUsername, parentopenid);
                    }

                    this.print(request, response, "");
//                    natives.put("wds", "1");
//                    natives = nativeDAO.getByWds(natives, uid);
//                    if (null == natives.get("id")) {
//                        return;
//                    }
//                    //id,name,type,lid,remark,wds
//                    event_key = natives.get("type") + "," + natives.get("lid");
//                    this.print(request, response, this.getContent(request, response, toUsername, fromUsername, time, event_key));
                } // 取消订阅
                else if (eventType.equals("unsubscribe")) {
                    //id,openid,mark 分销永久原因，不能删除，永久有效
//                    subscriber.put("openid", fromUsername);
//                    subscriberDAO.deleteByOpenid(subscriber, wxsid);
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                } // 自定义菜单点击事件
                else if (eventType.equals("CLICK")) {
                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应
                    String eventKey = root.elementTextTrim("EventKey");

                    //特殊菜单二维码
                    if ("qrcode,0".equals(eventKey)) {
                        List<Map<String, String>> newstypesList = new NewstypesDAO().getList("1", Integer.parseInt(wx.get("id")));
                        String text = "<xml>"
                                + "<ToUserName><![CDATA[%1$s]]></ToUserName>"
                                + "<FromUserName><![CDATA[%2$s]]></FromUserName>"
                                + "<CreateTime>%3$s</CreateTime>"
                                + "<MsgType><![CDATA[%4$s]]></MsgType>"
                                + "<Content><![CDATA[%5$s]]></Content>"
                                + "</xml>";
                        if ("1".equals(subscriber.get("isvip"))) {
                            String qrcodemediaid = subscriber.get("qrcodemediaid");
                            String qrcode = subscriber.get("qrcode");
                            boolean tempflag = false;
                            System.out.println("当前系统时间为"+System.currentTimeMillis());
                            System.out.println("临时二维码时间为"+(System.currentTimeMillis() / 1000 - Long.parseLong(subscriber.get("qrcodemediaidtimes"))));
                            System.out.println("临时二维码类型"+wx.get("myqrcodetype"));
                            if (!"0".equals(wx.get("myqrcodetype")) && (System.currentTimeMillis() / 1000 - Long.parseLong(subscriber.get("qrcodemediaidtimes")) > 2591700)) {
                            	System.out.println("开始删除临时二维码");
                                //删除之前二维码
                                if (!"".equals(qrcode)) {
                                    java.io.File oldFile = new java.io.File(this.getServletContext()
                                            .getRealPath(qrcode));
                                    oldFile.delete();
                                }
                                tempflag = true;
                            }
                            if ("".equals(qrcodemediaid) || "".equals(subscriber.get("qrcode")) || tempflag) {
                                if (0 < newstypesList.size()) {
                                    System.out.println("开始新生成二维码");
                                    qrcode = WxMenuUtils.getShopQRCode(request, wx, subscriber.get("id"));
                                    //处理二维码
                                    qrcode = WxMenuUtils.doShopQRCode(request, qrcode, wx, subscriber);
                                    //同时上传临时文件
                                    Map<String, String> qrcodemap = WxMenuUtils.uploadQrcode(request, response, this.getServletConfig(), qrcode, wx);
                                    qrcodemediaid = qrcodemap.get("media_id");
                                    subscriberDAO.updateqrcode(fromUsername, wx.get("id"), qrcode, qrcodemap.get("media_id"), qrcodemap.get("created_at"));
                                    this.print(request, response, this.getContent(request, response, toUsername, fromUsername, time, "qrcode," + qrcodemediaid));
                                } else {
                                    this.print(request, response, text.format(text, fromUsername, toUsername, time, "text", "暂无相关图片！"));
                                }
                            } else if (System.currentTimeMillis() / 1000 - Long.parseLong(subscriber.get("qrcodemediaidtimes")) > 604500) {
                            	System.out.println("开始上传临时文件");
                                //同时上传临时文件
                                Map<String, String> qrcodemap = WxMenuUtils.uploadQrcode(request, response, this.getServletConfig(), subscriber.get("qrcode"), wx);
                                qrcodemediaid = qrcodemap.get("media_id");
                                subscriberDAO.updateqrcode(fromUsername, wx.get("id"), subscriber.get("qrcode"), qrcodemap.get("media_id"), qrcodemap.get("created_at"));
                                this.print(request, response, this.getContent(request, response, toUsername, fromUsername, time, "qrcode," + qrcodemediaid));
                            } else {
                            	System.out.println("程序走到了最后一个else");
                                this.print(request, response, this.getContent(request, response, toUsername, fromUsername, time, "qrcode," + qrcodemediaid));
                            }
                        } else {
                            this.print(request, response, text.format(text, fromUsername, toUsername, time, "text", "您还不是会员，不能为您生成推广图片，立即购买成为会员"));
                        }
                    } else {
                        this.print(request, response, this.getContent(request, response, toUsername, fromUsername, time, eventKey));
                    }
                }
            }
        } else {
            this.print(request, response, "");
        }
    }

    //回复
    public String getContent(HttpServletRequest request, HttpServletResponse response, String toUsername, String fromUsername, String time, String event_key) {
        String return_text = "";
        String[] messageType = event_key.split(",");
        if ("text".equals(messageType[0])) {
            //id,name,describers
            texts.put("id", messageType[1]);
            texts = textDAO.getById(texts);
            String text = "<xml>"
                    + "<ToUserName><![CDATA[%1$s]]></ToUserName>"
                    + "<FromUserName><![CDATA[%2$s]]></FromUserName>"
                    + "<CreateTime>%3$s</CreateTime>"
                    + "<MsgType><![CDATA[%4$s]]></MsgType>"
                    + "<Content><![CDATA[%5$s]]></Content>"
                    + "</xml>";
            return_text = text.format(text, fromUsername, toUsername, time, "text", texts.get("describers"));
        } else if ("news".equals(messageType[0])) {
            //id,name,describes,img,url,remark
            newsm.put("id", messageType[1]);
            List<Map<String, String>> itemsmList = itemsmDAO.getByNewsmList(newsm, uid);
            String contentStr = "";
            for (Map<String, String> itemsm : itemsmList) {
                String picUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + itemsm.get("img");
                String urlAf = itemsm.get("url").indexOf("?") < 0 ? "?openid=" : "&openid=";
                String urlWf = "&wx=" + wxsid;
                String url = itemsm.get("url") + urlAf + fromUsername + urlWf + "#mp.weixin.qq.com";
                contentStr += ("<item><Title><![CDATA[" + itemsm.get("name") + "]]></Title> "
                        + "<Description><![CDATA[" + itemsm.get("describes") + "]]></Description>"
                        + "<PicUrl><![CDATA[" + picUrl + "]]></PicUrl>"
                        + "<Url><![CDATA[" + url + "]]></Url>"
                        + "</item>");
            }
            String text = "<xml>"
                    + "<ToUserName><![CDATA[%1$s]]></ToUserName>"
                    + "<FromUserName><![CDATA[%2$s]]></FromUserName>"
                    + "<CreateTime>%3$s</CreateTime>"
                    + "<MsgType><![CDATA[%4$s]]></MsgType>"
                    + "<ArticleCount>" + itemsmList.size() + "</ArticleCount>"
                    + "<Articles>"
                    + contentStr
                    + "</Articles>"
                    + "</xml>";
            return_text = text.format(text, fromUsername, toUsername, time, "news");
        } else if ("qrcode".equals(messageType[0])) {
            //id,name,describers
            String text = "<xml>"
                    + "<ToUserName><![CDATA[%1$s]]></ToUserName>"
                    + "<FromUserName><![CDATA[%2$s]]></FromUserName>"
                    + "<CreateTime>%3$s</CreateTime>"
                    + "<MsgType><![CDATA[%4$s]]></MsgType>"
                    + "<Image>"
                    + "<MediaId><![CDATA[%5$s]]></MediaId>"
                    + "</Image>"
                    + "</xml>";
            return_text = text.format(text, fromUsername, toUsername, time, "image", messageType[1]);
            System.out.println("返回内容为"+return_text);
        } else if ("image".equals(messageType[0]) || "thumb".equals(messageType[0])) {
            //id,name,describers
            permaterial.put("id", messageType[1]);
            permaterial = permaterialDAO.getById(permaterial);
            String text = "<xml>"
                    + "<ToUserName><![CDATA[%1$s]]></ToUserName>"
                    + "<FromUserName><![CDATA[%2$s]]></FromUserName>"
                    + "<CreateTime>%3$s</CreateTime>"
                    + "<MsgType><![CDATA[%4$s]]></MsgType>"
                    + "<Image>"
                    + "<MediaId><![CDATA[%5$s]]></MediaId>"
                    + "</Image>"
                    + "</xml>";
            return_text = text.format(text, fromUsername, toUsername, time, "image", permaterial.get("mediaid"));
            permaterialDAO.updateViewcounts(permaterial);
        } else if ("voice".equals(messageType[0])) {
            //id,name,describers
            permaterial.put("id", messageType[1]);
            permaterial = permaterialDAO.getById(permaterial);
            String text = "<xml>"
                    + "<ToUserName><![CDATA[%1$s]]></ToUserName>"
                    + "<FromUserName><![CDATA[%2$s]]></FromUserName>"
                    + "<CreateTime>%3$s</CreateTime>"
                    + "<MsgType><![CDATA[%4$s]]></MsgType>"
                    + "<Voice>"
                    + "<MediaId><![CDATA[%5$s]]></MediaId>"
                    + "</Voice>"
                    + "</xml>";
            return_text = text.format(text, fromUsername, toUsername, time, "voice", permaterial.get("mediaid"));
            permaterialDAO.updateViewcounts(permaterial);
        } else if ("video".equals(messageType[0])) {
            //id,name,describers
            permaterial.put("id", messageType[1]);
            permaterial = permaterialDAO.getById(permaterial);
            String text = "<xml>"
                    + "<ToUserName><![CDATA[%1$s]]></ToUserName>"
                    + "<FromUserName><![CDATA[%2$s]]></FromUserName>"
                    + "<CreateTime>%3$s</CreateTime>"
                    + "<MsgType><![CDATA[%4$s]]></MsgType>"
                    + "<Video>"
                    + "<MediaId><![CDATA[%5$s]]></MediaId>"
                    + "<Title>" + permaterial.get("title") + "</Title>"
                    + "<Description>" + permaterial.get("description") + "</Description>"
                    + "</Video>"
                    + "</xml>";
            return_text = text.format(text, fromUsername, toUsername, time, "video", permaterial.get("mediaid"));
            permaterialDAO.updateViewcounts(permaterial);
        } else if ("music".equals(messageType[0])) {
            //id,name,describers
            permaterial.put("id", messageType[1]);
            permaterial = permaterialDAO.getById(permaterial);
            String text = "<xml>"
                    + "<ToUserName><![CDATA[%1$s]]></ToUserName>"
                    + "<FromUserName><![CDATA[%2$s]]></FromUserName>"
                    + "<CreateTime>%3$s</CreateTime>"
                    + "<MsgType><![CDATA[%4$s]]></MsgType>"
                    + "<Music>"
                    + "<Title>" + permaterial.get("title") + "</Title>"
                    + "<Description>" + permaterial.get("description") + "</Description>"
                    + "<MusicUrl>" + permaterial.get("musicurl") + "</MusicUrl>"
                    + "<HQMusicUrl>" + permaterial.get("hqmusicurl") + "</HQMusicUrl>"
                    + "<ThumbMediaId><![CDATA[%5$s]]></ThumbMediaId>"
                    + "</Music>"
                    + "</xml>";
            return_text = text.format(text, fromUsername, toUsername, time, "music", permaterial.get("mediaid"));
            permaterialDAO.updateViewcounts(permaterial);
        }
        //返回输出值
        return return_text;
    }

    protected void valid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String echostr = request.getParameter("echostr");
        if (null == echostr || echostr.isEmpty()) {
            responseMsg(request, response);
        } else {
//            if (this.checkSignature(request, response)) {
            this.print(request, response, echostr);
//            } else {
//                this.print(request, response, echostr);
//            }
        }
    }

    //微信接口验证
//    public boolean checkSignature(HttpServletRequest request, HttpServletResponse response) {
//        String signature = request.getParameter("signature");
//        String timestamp = request.getParameter("timestamp");
//        String nonce = request.getParameter("nonce");
//        String[] tmpArr = {token, timestamp, nonce};
//        Arrays.sort(tmpArr);
//        String tmpStr = this.ArrayToString(tmpArr);
//        tmpStr = this.SHA1Encode(tmpStr);
//        if (tmpStr.equalsIgnoreCase(signature)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
    //向请求端发送返回数据
    public void print(HttpServletRequest request, HttpServletResponse response, String content) {
        try {
            PrintWriter out = response.getWriter();
            out.print(content);
            out.flush();
            out.close();
        } catch (Exception e) {
        }
    }

    //数组转字符串
    public String ArrayToString(String[] arr) {
        StringBuffer bf = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            bf.append(arr[i]);
        }
        return bf.toString();
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
        return buf.toString().toUpperCase();
    }

    //从输入流读取post参数
    public String readStreamParameter(ServletInputStream in) {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in));
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
        return buffer.toString();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        valid(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
