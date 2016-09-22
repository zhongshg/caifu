/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wap.wx.dao.MassDAO;
import wap.wx.dao.TextDAO;
import wap.wx.util.Forward;
import wap.wx.util.PageUtil;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.Date;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import wap.wx.dao.PerMaterialDAO;
import wap.wx.dao.SubscriberDAO;
import wap.wx.menu.WxMenuUtils;

/**
 *
 * @author Administrator
 */
public class MassServlet extends BaseServlet {

    protected void getList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*
         * 1.群发记录
         * 2.备用
         */
        Map<String, String> mass = new HashMap<String, String>();
        String sid = "1";
        mass.put("sid", sid);
        MassDAO massDAO = new MassDAO();
        PageUtil<Map<String, String>> pu = new PageUtil<Map<String, String>>();
        pu.setPageSize(9);
        pu.setPage(null != request.getParameter("page") && !"null".equals(request.getParameter("page")) ? Integer.parseInt(request.getParameter("page")) : 1);
        pu.setMaxSize(massDAO.getConut(mass, Integer.parseInt(wx.get("id"))));
        pu.setList(massDAO.getList(pu, mass, Integer.parseInt(wx.get("id"))));
        pu.setSign(sid);
        pu.setServletName("MassServlet");
        request.setAttribute("pu", pu);
        Forward.forward(request, response, "/back/mass/mass.jsp");
    }

    protected void initManage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sid = request.getParameter("sid");
        if ("1".equals(sid)) {
            List<Map<String, String>> subscriberList = new SubscriberDAO().getList(Integer.parseInt(wx.get("id")));
            List<Map<String, String>> textList = new TextDAO().getList(Integer.parseInt(wx.get("id")));
            List<Map<String, String>> permaterialList = new PerMaterialDAO().getList(Integer.parseInt(wx.get("id")));
            //原上传图文已作废
//            Map<String, String> mass = new HashMap<String, String>();
//            mass.put("sid", "2");
//            List<Map<String, String>> massList = new MassDAO().getList(mass);
            request.setAttribute("subscriberList", subscriberList);
            request.setAttribute("textList", textList);
            request.setAttribute("permaterialList", permaterialList);
        } else {
//            List<Map<String, String>> newsmList = new NewsmDAO().getList();
//            request.setAttribute("newsmList", newsmList);
        }
        request.setAttribute("sid", sid);
        Forward.forward(request, response, "/back/mass/massmanage.jsp?page=" + request.getParameter("page"));
    }

    protected void manage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sid = request.getParameter("sid");
        if ("1".equals(sid)) {
            String preopenid = request.getParameter("preopenid");
            String sendstr = "";
            String flagstr = "";//判断字符串
            String sendurl = "";//判断接口
            //按分组全发
            String sendall = "\"filter\":{"
                    + "      \"is_to_all\":true,"
                    + "      \"group_id\":\"\""
                    + "   },";
            //预览
            String sendpre = "\"touser\":\"" + preopenid + "\",";
            if (null != preopenid && !"".equals(preopenid)) {
                flagstr = sendpre;//预览
                sendurl = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=" + WxMenuUtils.getAccessToken(wx);
            } else {
                flagstr = sendall;//直接群发
                sendurl = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + WxMenuUtils.getAccessToken(wx);
            }

            String lid = request.getParameter("lid");
            if (!"0,0".equals(lid)) {
                PerMaterialDAO permaterialDAO = new PerMaterialDAO();
                String[] str = lid.split(",");
                if ("text".equals(str[0])) {//文本消息
                    Map<String, String> text = new HashMap<String, String>();
                    text.put("id", str[1]);
                    text = new TextDAO().getById(text);
                    sendstr = "{"
                            + flagstr
                            + "   \"text\":{"
                            + "      \"content\":\"" + text.get("describers").replace("\"", "\'") + "\""
                            + "   },"
                            + "    \"msgtype\":\"text\""
                            + "}";
                } else if ("news".equals(str[0])) {//图文消息
                    Map<String, String> news = new HashMap<String, String>();
                    news.put("id", str[1]);
                    news = permaterialDAO.getById(news);
                    sendstr = "{"
                            + flagstr
                            + "   \"mpnews\":{"
                            + "      \"media_id\":\"" + news.get("mediaid") + "\""
                            + "   },"
                            + "    \"msgtype\":\"mpnews\""
                            + "}";
                } else if ("video".equals(str[0])) {//视频消息
                    Map<String, String> video = new HashMap<String, String>();
                    video.put("id", str[1]);
                    video = permaterialDAO.getById(video);
                    String strvideo = "{"
                            + "  \"media_id\":\"" + video.get("mediaid") + "\","
                            + "  \"title\":\"" + video.get("title") + "\","
                            + "  \"description\":\"" + video.get("description") + "\""
                            + "}";
                    System.out.println("strvideo " + strvideo);
                    HttpPost httpPost = new HttpPost("https://file.api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token=" + WxMenuUtils.getAccessToken(wx));
                    httpPost.setEntity(new StringEntity(strvideo, "UTF-8"));
                    CloseableHttpClient httpclient = WxMenuUtils.httpclient();
                    HttpResponse responses = httpclient.execute(httpPost);
                    String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
                    WxMenuUtils.close(httpclient);
                    System.out.println(jsonStr);
                    JSONObject object = JSON.parseObject(jsonStr);
                    String media_id = object.getString("media_id");
                    System.out.println("media_id  " + media_id);
                    sendstr = "{"
                            + flagstr
                            + "   \"mpvideo\":{"
                            + "      \"media_id\":\"" + media_id + "\""
                            + "   },"
                            + "    \"msgtype\":\"mpvideo\""
                            + "}";
                } else if ("voice".equals(str[0]) || "image".equals(str[0])) {//其他消息
                    Map<String, String> permaterial = new HashMap<String, String>();
                    permaterial.put("id", str[1]);
                    permaterial = permaterialDAO.getById(permaterial);
                    sendstr = "{"
                            + flagstr
                            + "   \"" + permaterial.get("type") + "\":{"
                            + "      \"media_id\":\"" + permaterial.get("mediaid") + "\""
                            + "   },"
                            + "    \"msgtype\":\"" + permaterial.get("type") + "\""
                            + "}";
                } else if ("thumb".equals(str[0])) {//缩略图当做图片消息
                    Map<String, String> thumb = new HashMap<String, String>();
                    thumb.put("id", str[1]);
                    thumb = permaterialDAO.getById(thumb);
                    sendstr = "{"
                            + flagstr
                            + "   \"image\":{"
                            + "      \"media_id\":\"" + thumb.get("mediaid") + "\""
                            + "   },"
                            + "    \"msgtype\":\"image\""
                            + "}";
                } else {
                    System.out.println("群发信息选择error!");
                }
            } else {//自定义文本消息
                String content = request.getParameter("content");
                sendstr = "{"
                        + flagstr
                        + "   \"text\":{"
                        + "      \"content\":\"" + content.replace("\"", "\'") + "\""
                        + "   },"
                        + "    \"msgtype\":\"text\""
                        + "}";
            }
            System.out.println(" sendstr  " + sendstr);

            //发送
            HttpPost httpPost = new HttpPost(sendurl);
            httpPost.setEntity(new StringEntity(sendstr, "UTF-8"));
            CloseableHttpClient httpclient = WxMenuUtils.httpclient();
            HttpResponse responses = httpclient.execute(httpPost);
            String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
            WxMenuUtils.close(httpclient);
            System.out.println(jsonStr);
            JSONObject object = JSON.parseObject(jsonStr);
            String msg_id = object.getString("msg_id");

            System.out.println("正在发送  " + msg_id);
            System.out.println(object.getString("errcode"));

            //发送记录
            if ("0".equals(object.getString("errcode"))) {
                Map<String, String> mass = new HashMap<String, String>();
                mass.put("mediaid", msg_id);
                mass.put("times", WxMenuUtils.format.format(new Date()));
                mass.put("sid", sid);
                new MassDAO().add(mass, Integer.parseInt(wx.get("id")));
            }
        }
//        else {
//            String lid = request.getParameter("lid");
//            String[] str = lid.split(",");
//            Map<String, String> newsm = new HashMap<String, String>();
//            newsm.put("id", str[1]);
//            ItemsmDAO itemsmDAO = new ItemsmDAO();
//            List<Map<String, String>> itemsmList = itemsmDAO.getByNewsmList(newsm);
//
//            //上传多媒体文件（判断media是否有效）
//            String mediaid = "";
//            //上传图文消息
//            String mediastr = "{"
//                    + "   \"articles\": [";
//            int i = 0;
//            for (Map<String, String> itemsm : itemsmList) {
//                i++;
//                String mediatimes = itemsm.get("mediatimes");
//                long mediatimeslong = 0;
//                try {
//                    if (null == mediatimes) {
//                        mediatimes = "2015-1-1 00:00:00";
//                    }
//                    mediatimeslong = WxMenuUtils.format.parse(mediatimes).getTime();
//                } catch (ParseException ex) {
//                    Logger.getLogger(MassServlet.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                if (258600 * 1000 < System.currentTimeMillis() - mediatimeslong) {//mediaid超时，重新上传多媒体
//                    File media = new File(this.getServletContext().getRealPath("/" + itemsm.get("img")));
//                    HttpPost httpPost = new HttpPost("http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=" + WxMenuUtils.getAccessToken() + "&type=thumb");
//                    FileBody fileBody = new FileBody(media);
//                    StringBody stringBody = new StringBody("文件的描述");
//                    MultipartEntity entity = new MultipartEntity();
//                    entity.addPart("media", fileBody);
//                    entity.addPart("desc", stringBody);
//                    httpPost.setEntity(entity);
//        CloseableHttpClient httpclient = WxMenuUtils.httpclient();
//        HttpResponse responses = httpclient.execute(httpPost);
//        String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
//        WxMenuUtils.close(httpclient);
//                    System.out.println(jsonStr);
//
//                    try {
//                        JSONObject object = JSON.parseObject(jsonStr);
//                        String type = object.getString("type");
//                        mediaid = object.getString("thumb_media_id");
//                        long created_at = object.getLong("created_at");
//                        System.out.println(type + "  " + mediaid + "  " + created_at);
//                        itemsm.put("mediaid", mediaid);
//                        itemsm.put("mediatimes", WxMenuUtils.format.format(new Date(created_at * 1000)));
//                        itemsmDAO.updateMedia(itemsm);
//                    } catch (Exception e) {
//                        System.out.println("上传图片错误！");
//                    }
//                } else {//mediaid可用
//                    mediaid = itemsm.get("mediaid");
//                }
//
//                if (1 != i) {
//                    mediastr += ",";
//                }
//                mediastr += "{"
//                        + "                        \"thumb_media_id\":\"" + mediaid + "\","
//                        + "                        \"author\":\"\","
//                        + "			 \"title\":\"" + itemsm.get("name") + "\","
//                        + "			 \"content_source_url\":\"" + itemsm.get("url") + "\","
//                        + "			 \"content\":\"" + itemsm.get("describes") + "\","
//                        + "			 \"digest\":\"" + itemsm.get("describes") + "\","
//                        + "                        \"show_cover_pic\":\"1\""
//                        + "		 }";
//            }
//            mediastr += " ]"
//                    + "}";
//            System.out.println("mediastr  " + mediastr);
//            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=" + WxMenuUtils.getAccessToken());
//            httpPost.setEntity(new StringEntity(mediastr, "UTF-8"));
//        CloseableHttpClient httpclient = WxMenuUtils.httpclient();
//        HttpResponse responses = httpclient.execute(httpPost);
//        String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
//        WxMenuUtils.close(httpclient);
////                System.out.println(jsonStr);
//            String media_id = "";
//            try {
//                JSONObject object = JSON.parseObject(jsonStr);
//                media_id = object.getString("media_id");
//                System.out.println("图文id  " + media_id);
//                long created_at = object.getLong("created_at") * 1000;
//                System.out.println("图文created_at  " + created_at);
//
//                //保存已上传的图文消息
//                Map<String, String> mass = new HashMap<String, String>();
//                mass.put("mediaid", media_id);
//                mass.put("times", WxMenuUtils.format.format(new Date(created_at)));
//                mass.put("sid", sid);
//                new MassDAO().add(mass);
//            } catch (Exception e) {
//                System.out.println("上传消息错误！");
//            }
//        }

        response.sendRedirect(request.getContextPath() + "/servlet/MassServlet?method=getList&page=" + request.getParameter("page") + "&sid=" + sid);
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mediaid = request.getParameter("mediaid");
        String param = "{\n"
                + "   \"msg_id\":" + mediaid + "\n"
                + "}";
        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=" + WxMenuUtils.getAccessToken(wx));
        httpPost.setEntity(new StringEntity(param, "UTF-8"));
        CloseableHttpClient httpclient = WxMenuUtils.httpclient();
        HttpResponse responses = httpclient.execute(httpPost);
        String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
        WxMenuUtils.close(httpclient);
        System.out.println(jsonStr);
        JSONObject object = JSON.parseObject(jsonStr);
        if ("0".equals(object.getString("errcode"))) {
            Map<String, String> mass = new HashMap<String, String>();
            mass.put("mediaid", mediaid);
            new MassDAO().delete(mass, Integer.parseInt(wx.get("id")));
        }

        response.sendRedirect(request.getContextPath() + "/servlet/MassServlet?method=getList&sid=" + request.getParameter("sid"));
    }

    protected void delAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> mass = new HashMap<String, String>();
        String ids = request.getParameter("ids");
        String array[] = ids.split(",");
        for (String mediaid : array) {

            if (!"".equals(mediaid)) {
                String param = "{\n"
                        + "   \"msg_id\":" + mediaid + "\n"
                        + "}";
                HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=" + WxMenuUtils.getAccessToken(wx));
                httpPost.setEntity(new StringEntity(param, "UTF-8"));
                CloseableHttpClient httpclient = WxMenuUtils.httpclient();
                HttpResponse responses = httpclient.execute(httpPost);
                String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
                WxMenuUtils.close(httpclient);
                System.out.println(jsonStr);
                JSONObject object = JSON.parseObject(jsonStr);
                if ("0".equals(object.getString("errcode"))) {
                    mass.put("mediaid", mediaid);
                    new MassDAO().delete(mass, Integer.parseInt(wx.get("id")));
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/servlet/MassServlet?method=getList&sid=" + request.getParameter("sid"));
    }
}
