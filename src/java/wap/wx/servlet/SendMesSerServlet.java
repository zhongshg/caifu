/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import wap.wx.dao.ItemsmDAO;
import wap.wx.dao.NewsmDAO;
import wap.wx.dao.PerMaterialDAO;
import wap.wx.dao.SubscriberDAO;
import wap.wx.dao.TextDAO;
import wap.wx.menu.HttpClientConnectionManager;
import wap.wx.menu.WxMenuUtils;
import wap.wx.util.Forward;

/**
 *
 * @author Administrator
 */
public class SendMesSerServlet extends BaseServlet {

    private TextDAO textDAO = new TextDAO();
    private ItemsmDAO itemsmDAO = new ItemsmDAO();
    private PerMaterialDAO permaterialDAO = new PerMaterialDAO();
    private Map<String, String> texts = new HashMap<String, String>();
    private Map<String, String> newsm = new HashMap<String, String>();
    private Map<String, String> material = new HashMap<String, String>();
    private Map<String, String> permaterial = new HashMap<String, String>();

    protected void initManage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Map<String, String>> subscriberList = new SubscriberDAO().getList(Integer.parseInt(wx.get("id")));
        List<Map<String, String>> textList = new TextDAO().getList(Integer.parseInt(wx.get("id")));
        List<Map<String, String>> newsList = new NewsmDAO().getList(Integer.parseInt(wx.get("id")));
        List<Map<String, String>> permaterialList = new PerMaterialDAO().getList(Integer.parseInt(wx.get("id")));
        request.setAttribute("subscriberList", subscriberList);
        request.setAttribute("textList", textList);
        request.setAttribute("newsList", newsList);
        request.setAttribute("permaterialList", permaterialList);
        Forward.forward(request, response, "/back/native/sendmesser.jsp?page=" + request.getParameter("page"));
    }

    protected void manage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String openid = request.getParameter("openid");
        String lid = request.getParameter("lid");
        String thumb = request.getParameter("thumb");
        String remark = request.getParameter("remark");
        String params = this.getContent(request, response, openid, lid, thumb, remark);
//        System.out.println("params  " + params);
        HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + WxMenuUtils.getAccessToken(wx));
        httpost.setEntity(new StringEntity(params, "UTF-8"));
        CloseableHttpClient httpclient = WxMenuUtils.httpclient();
        HttpResponse responses = httpclient.execute(httpost);
        String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
        WxMenuUtils.close(httpclient);
        System.out.println("jsonStr " + jsonStr);
        JSONObject object = JSON.parseObject(jsonStr);
        out.print(object.getString("errcode"));
        out.close();
    }

    //回复
    public String getContent(HttpServletRequest request, HttpServletResponse response, String openid, String event_key, String thumb, String remark) {
        String return_text = "";
        String[] messageType = event_key.split(",");
        if ("text".equals(messageType[0])) {
            //id,name,describers
            texts.put("id", messageType[1]);
            texts = textDAO.getById(texts);
            return_text = "{"
                    + "\"touser\":\"" + openid + "\","
                    + "\"msgtype\":\"text\","
                    + "\"text\":"
                    + "{"
                    + " \"content\":\"" + texts.get("describers").replace("\"", "\'") + "\""
                    + "}"
                    + "}";
        } else if ("news".equals(messageType[0])) {
            //id,name,describes,img,url,remark
            newsm.put("id", messageType[1]);
            List<Map<String, String>> itemsmList = itemsmDAO.getByNewsmList(newsm, Integer.parseInt(wx.get("id")));
            String contentStr = "";
            for (int i = 0; i < itemsmList.size(); i++) {
                Map<String, String> itemsm = itemsmList.get(i);
                String picUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + itemsm.get("img");
                String urlAf = itemsm.get("url").indexOf("?") < 0 ? "?openid=" : "&openid=";
                String url = itemsm.get("url") + urlAf + openid + "#mp.weixin.qq.com";
                if (i != 0) {
                    contentStr += ",";
                }
                contentStr += ("{"
                        + "\"title\":\"" + itemsm.get("name") + "\","
                        + "\"description\":\"" + itemsm.get("describes") + "\","
                        + "\"url\":\"" + url + "\","
                        + "\"picurl\":\"" + picUrl + "\""
                        + "}");
            }
            return_text = "{"
                    + "\"touser\":\"" + openid + "\","
                    + "\"msgtype\":\"news\","
                    + "\"news\":{"
                    + " \"articles\": ["
                    + contentStr
                    + "]"
                    + "}"
                    + "}";
        } else if ("image".equals(messageType[0]) || "thumb".equals(messageType[0])) {
            //id,name,describers
            permaterial.put("id", messageType[1]);
            permaterial = permaterialDAO.getById(permaterial);
            return_text = "{"
                    + "\"touser\":\"" + openid + "\","
                    + "\"msgtype\":\"image\","
                    + "\"image\":"
                    + "{"
                    + "\"media_id\":\"" + permaterial.get("mediaid") + "\""
                    + "}"
                    + "}";
        } else if ("voice".equals(messageType[0])) {
            //id,name,describers
            permaterial.put("id", messageType[1]);
            permaterial = permaterialDAO.getById(permaterial);
            return_text = "{"
                    + "\"touser\":\"" + openid + "\","
                    + "\"msgtype\":\"voice\","
                    + "\"voice\":"
                    + "{"
                    + "\"media_id\":\"" + permaterial.get("mediaid") + "\""
                    + "}"
                    + "}";
        } else if ("video".equals(messageType[0])) {
            //id,name,describers
            permaterial.put("id", messageType[1]);
            permaterial = permaterialDAO.getById(permaterial);
            return_text = "{"
                    + "\"touser\":\"" + openid + "\","
                    + "\"msgtype\":\"video\","
                    + "\"video\":"
                    + "{"
                    + "\"media_id\":\"" + permaterial.get("mediaid") + "\","
                    + "\"thumb_media_id\":\"" + thumb + "\","
                    + "\"title\":\"" + permaterial.get("title") + "\","
                    + "\"description\":\"" + permaterial.get("description") + "\""
                    + "}"
                    + "}";
        } else if ("music".equals(messageType[0])) {
            //id,name,describers
            permaterial.put("id", messageType[1]);
            permaterial = permaterialDAO.getById(permaterial);
            return_text = "{"
                    + "\"touser\":\"" + openid + "\","
                    + "\"msgtype\":\"music\","
                    + "\"music\":"
                    + "{"
                    + "\"title\":\"" + permaterial.get("title") + "\","
                    + "\"description\":\"" + permaterial.get("description") + "\","
                    + "\"musicurl\":\"" + permaterial.get("musicurl") + "\","
                    + "\"hqmusicurl\":\"" + permaterial.get("hqmusicurl") + "\","
                    + "\"thumb_media_id\":\"" + permaterial.get("mediaid") + "\" "
                    + "}"
                    + "}";
        } else {
            return_text = "{"
                    + "\"touser\":\"" + openid + "\","
                    + "\"msgtype\":\"text\","
                    + "\"text\":"
                    + "{"
                    + " \"content\":\"" + remark.replace("\"", "\'") + "\""
                    + "}"
                    + "}";
        }
        //返回输出值
        return return_text;
    }
}
