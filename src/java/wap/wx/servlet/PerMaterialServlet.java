/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import wap.wx.dao.ItemsmDAO;
import wap.wx.dao.PerMaterialDAO;
import wap.wx.dao.NewsmDAO;
import wap.wx.menu.WxMenuUtils;
import wap.wx.util.Forward;
import wap.wx.util.PageUtil;

/**
 *
 * @author ASUS
 */
public class PerMaterialServlet extends BaseServlet {

    protected void getList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PerMaterialDAO perMaterialDAO = new PerMaterialDAO();
        PageUtil<Map<String, String>> pu = new PageUtil<Map<String, String>>();
        pu.setPageSize(9);
        pu.setPage(null != request.getParameter("page") && !"null".equals(request.getParameter("page")) ? Integer.parseInt(request.getParameter("page")) : 1);
        pu.setMaxSize(perMaterialDAO.getCount(Integer.parseInt(wx.get("id"))));
        pu.setList(perMaterialDAO.getList(pu, Integer.parseInt(wx.get("id"))));
        pu.setServletName("PerMaterialServlet");
        request.setAttribute("pu", pu);
        request.setAttribute("now", WxMenuUtils.format.format(new Date()));
        Forward.forward(request, response, "/back/message/permaterial.jsp");
    }

    protected void initManage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //上传素材管理不能修改
        PerMaterialDAO perMaterialDAO = new PerMaterialDAO();
        String id = request.getParameter("id");
        Map<String, String> material = new HashMap<String, String>();
        material.put("id", id);
        material = perMaterialDAO.getById(material);
        request.setAttribute("material", material);
        Forward.forward(request, response, "/back/message/permaterialmanage.jsp?page=" + request.getParameter("page"));
    }

    protected void initMsgManage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PerMaterialDAO perMaterialDAO = new PerMaterialDAO();
        String id = request.getParameter("id");
        Map<String, String> material = new HashMap<String, String>();
        material.put("id", id);
        material = perMaterialDAO.getById(material);
        request.setAttribute("material", material);
        List<Map<String, String>> materialList = perMaterialDAO.getList(Integer.parseInt(wx.get("id")));
        request.setAttribute("materialList", materialList);
        Forward.forward(request, response, "/back/message/permaterialmsgmanage.jsp?page=" + request.getParameter("page"));
    }

    protected void initMassMsgManage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PerMaterialDAO perMaterialDAO = new PerMaterialDAO();
        String id = request.getParameter("id");
        Map<String, String> material = new HashMap<String, String>();
        material.put("id", id);
        material = perMaterialDAO.getById(material);
        request.setAttribute("material", material);
        Forward.forward(request, response, "/back/message/permaterialmassmsgmanage.jsp?page=" + request.getParameter("page"));
    }

    //id,name,type,mediaid,times,ip,userid material
    protected void manage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PerMaterialDAO perMaterialDAO = new PerMaterialDAO();
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", request.getParameter("id"));
        map.put("name", request.getParameter("name"));
        map.put("type", request.getParameter("type"));
        map.put("url", request.getParameter("url"));
        if ("music".equals(map.get("type"))) {
//            map.put("mediaid", request.getParameter("mediaidmusic").split(",")[0]);
//            map.put("times", request.getParameter("mediaidmusic").split(",")[1]);
            map.put("mediaid", request.getParameter("mediaidmusic"));
        } else {
            map.put("mediaid", request.getParameter("mediaid"));
        }
        map.put("times", WxMenuUtils.format.format(new Date()));
        map.put("title", request.getParameter("title"));
        map.put("description", request.getParameter("description"));
        map.put("musicurl", request.getParameter("musicurl"));
        map.put("hqmusicurl", request.getParameter("hqmusicurl"));
        map.put("ip", request.getRemoteAddr());
        map.put("userid", ((Map<String, String>) request.getSession().getAttribute("users")).get("id"));
        map.put("viewcounts", "0");
        if ("0".equals(map.get("id"))) {
            perMaterialDAO.add(map, Integer.parseInt(wx.get("id")));
        } else {
            perMaterialDAO.update(map);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/PerMaterialServlet?method=getList&page=" + request.getParameter("page"));
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PerMaterialDAO perMaterialDAO = new PerMaterialDAO();
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", request.getParameter("id"));
        map = perMaterialDAO.getById(map);
        try {
            if (WxMenuUtils.delPerMaterial(map.get("mediaid"), wx)) {
                perMaterialDAO.delete(map);
            }
        } catch (Exception ex) {
            System.out.println("删除上传素材接口异常！");
            Logger.getLogger(PerMaterialServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/PerMaterialServlet?method=getList&page=" + request.getParameter("page"));
    }

    protected void delAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PerMaterialDAO perMaterialDAO = new PerMaterialDAO();
        Map<String, String> map = new HashMap<String, String>();
        String ids = request.getParameter("ids");
        String array[] = ids.split(",");
        for (String id : array) {
            map.put("id", !"".equals(id) ? id : "0");
            map = perMaterialDAO.getById(map);
            try {
                if (WxMenuUtils.delPerMaterial(map.get("mediaid"), wx)) {
                    perMaterialDAO.delete(map);
                }
            } catch (Exception ex) {
                System.out.println("删除上传素材接口异常！");
                Logger.getLogger(PerMaterialServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        response.sendRedirect(request.getContextPath() + "/servlet/PerMaterialServlet?method=getList&page=" + request.getParameter("page"));
    }

    protected void uploadmassmaterialinit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String, String>> newsmList = new NewsmDAO().getList(Integer.parseInt(wx.get("id")));
        request.setAttribute("newsmList", newsmList);
        Forward.forward(request, response, "/back/message/peruploadmassmaterial.jsp");
    }

    protected void uploadmassmaterial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String newsmid = request.getParameter("newsm");
        Map<String, String> newsm = new HashMap<String, String>();
        newsm.put("id", newsmid);
        ItemsmDAO itemsmDAO = new ItemsmDAO();
        List<Map<String, String>> itemsmList = itemsmDAO.getByNewsmList(newsm, Integer.parseInt(wx.get("id")));

        String mediaid = "";
        //上传图文消息
        String mediastr = "{"
                + "   \"articles\": [";
        int i = 0;
        for (Map<String, String> itemsm : itemsmList) {
            i++;
            mediaid = itemsm.get("permediaid");
            if ("".equals(mediaid) || null == mediaid) {//mediaid为空
                out.print("{\"msg\":\"invalid\"}");
                out.close();
                return;
            }
            if (1 != i) {
                mediastr += ",";
            }
            mediastr += "{"
                    + "                        \"thumb_media_id\":\"" + mediaid + "\","
                    + "                        \"author\":\"\","
                    + "			 \"title\":\"" + itemsm.get("name") + "\","
                    + "			 \"content_source_url\":\"" + itemsm.get("url") + "\","
                    + "			 \"content\":\"" + itemsm.get("describes") + "\","
                    + "			 \"digest\":\"" + itemsm.get("describes") + "\","
                    + "                        \"show_cover_pic\":\"1\""
                    + "		 }";
        }
        mediastr += " ]"
                + "}";
        System.out.println("mediastr  " + mediastr);
        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=" + WxMenuUtils.getAccessToken(wx));
        httpPost.setEntity(new StringEntity(mediastr, "UTF-8"));
        CloseableHttpClient httpclient = WxMenuUtils.httpclient();
        HttpResponse responses = httpclient.execute(httpPost);
        String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
        WxMenuUtils.close(httpclient);
        System.out.println(jsonStr);
        String media_id = "";
        try {
            JSONObject object = JSON.parseObject(jsonStr);
            media_id = object.getString("media_id");
            System.out.println("图文id  " + media_id);
            long created_at = object.getLong("created_at") * 1000;
            System.out.println("图文created_at  " + created_at);

            out.print("{\"msg\":\"success\",\"type\":\"" + object.getString("type") + "\",\"mediaid\":\"" + media_id + "\",\"times\":\"" + WxMenuUtils.format.format(new Date(created_at)) + "\"}");
            out.close();
        } catch (Exception e) {
            out.print("{\"msg\":\"upfalse\"}");
            out.close();
        }
    }
}
