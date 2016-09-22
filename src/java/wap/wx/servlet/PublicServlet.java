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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import wap.wx.dao.ItemsmDAO;
import wap.wx.dao.NewsmDAO;
import wap.wx.menu.WxMenuUtils;
import wap.wx.util.Forward;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "PublicServlet", urlPatterns = {"/servlet/PublicServlet"})
public class PublicServlet extends BaseServlet {

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
        if (!"".equals(su.getRequest().getParameter("oldimg").trim())) {
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
        request.getRequestDispatcher("/public/upload.jsp").forward(request, response);
    }

    protected void peruploadmaterial_init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
//取出图文列表
        List<Map<String, String>> newsmList = new NewsmDAO().getList(Integer.parseInt(wx.get("id")));
        request.setAttribute("newsmList", newsmList);
        Forward.forward(request, response, "/public/peruploadmaterial.jsp?type=" + type);
        //        ${pageContext.servletContext.contextPath}/public/peruploadmaterial.jsp?type=${material.type}
    }

    protected void peruploadmaterial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SmartUpload su = new SmartUpload();
        su.initialize(this.getServletConfig(), request, response);
        try {
            su.upload();
        } catch (Exception e) {
            System.out.println("本地上传失败！");
        }

        String type = su.getRequest().getParameter("type");
        String jsonStr = "";//结果
        CloseableHttpClient httpclient = WxMenuUtils.httpclient();
        if ("news".equals(type)) {//图文上传
            String newsmid = su.getRequest().getParameter("newsm");
            Map<String, String> newsm = new HashMap<String, String>();
            newsm.put("id", newsmid);
            ItemsmDAO itemsmDAO = new ItemsmDAO();
            List<Map<String, String>> itemsmList = itemsmDAO.getByNewsmList(newsm, Integer.parseInt(wx.get("id")));

            String mediaid = "";
            String mediastr = "{"
                    + "   \"articles\": [";
            int i = 0;
            for (Map<String, String> itemsm : itemsmList) {
                i++;
                mediaid = itemsm.get("permediaid");//此处mediaid必为永久素材
                if ("".equals(mediaid) || null == mediaid) {//mediaid为空
                    request.setAttribute("message", "media无效！");
                    Forward.forward(request, response, "/public/peruploadmaterial.jsp");
                    return;
                }
                if (1 != i) {
                    mediastr += ",";
                }
                mediastr += "{"
                        + "			 \"title\":\"" + itemsm.get("name") + "\","
                        + "                        \"thumb_media_id\":\"" + mediaid + "\","
                        + "                        \"author\":\"\","
                        + "			 \"digest\":\"" + itemsm.get("describes") + "\","
                        + "                        \"show_cover_pic\":\"1\","
                        + "			 \"content\":\" " + itemsm.get("describes") + "\","
                        + "			 \"content_source_url\":\"" + itemsm.get("url") + "\""
                        + "		 }";
            }
            mediastr += " ]"
                    + "}";
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=" + WxMenuUtils.getAccessToken(wx));
            httpPost.setEntity(new StringEntity(mediastr, "UTF-8"));
            HttpResponse responses = httpclient.execute(httpPost);
            jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
        } else {

            com.jspsmart.upload.File file = su.getFiles().getFile(0);
            //创建图片路径
            StringBuilder path = new StringBuilder("upload/")
                    .append(String.valueOf(System.currentTimeMillis())).append(".")
                    .append(file.getFileExt());
            try {
                file.saveAs(path.toString());
            } catch (SmartUploadException e) {
                e.printStackTrace();
            }

            java.io.File media = new java.io.File(this.getServletContext().getRealPath("/" + path.toString()));

            String description = "";
            if ("video".equals(type)) {//视频麻烦点
                String title = su.getRequest().getParameter("title");
                String introduction = su.getRequest().getParameter("introduction");
                description = "{"
                        + "  \"title\":\"" + title + "\","
                        + "  \"introduction\":\"" + introduction + "\""
                        + "}";
            }

            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=" + WxMenuUtils.getAccessToken(wx) + "&type=" + type);
            FileBody fileBody = new FileBody(media);
            StringBody stringBody = new StringBody(description, ContentType.TEXT_PLAIN);
            HttpEntity entity = MultipartEntityBuilder.create()
                    .addPart("media", fileBody)
                    .addPart("description", stringBody)
                    .build();
            httpPost.setEntity(entity);
            HttpResponse responses = httpclient.execute(httpPost);
            jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");

            //        删除本地图片
            media.delete();
        }
        WxMenuUtils.close(httpclient);
        JSONObject object = JSON.parseObject(jsonStr);
        try {
            String media_id = object.getString("media_id");
            String url = "";
            try {
                url = object.getString("url");
            } catch (Exception e) {
                System.out.println("又不是图片，没有url!");
            }
            request.setAttribute("type", type);
            request.setAttribute("media_id", media_id);
            request.setAttribute("url", url);
        } catch (Exception e) {
            //错误定义
            request.setAttribute("errcode", object.getString("errcode"));
        }
        Forward.forward(request, response, "/public/peruploadmaterial.jsp");
    }
}
