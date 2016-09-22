/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wap.wx.dao.MenusDAO;
import wap.wx.dao.NewsDAO;
import wap.wx.dao.NewsmDAO;
import wap.wx.dao.PerMaterialDAO;
import wap.wx.dao.TextDAO;
import wap.wx.menu.WxMenuUtils;
import wap.wx.service.MenusService;
import static wap.wx.servlet.BaseServlet.guid;
import wap.wx.util.Forward;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "MenuServlet", urlPatterns = {"/servlet/MenuServlet"})
public class MenuServlet extends BaseServlet {

    public void getWxList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String isr = request.getParameter("isr");
        MenusDAO menusDAO = new MenusDAO();
        if ("1".equals(isr)) {
            menusDAO.deleteAll(Integer.parseInt(guid.get("id")));
        }
        Map<Map<String, String>, List<Map<String, String>>> menuMap = new LinkedHashMap<Map<String, String>, List<Map<String, String>>>();
        try {
            String access_token = WxMenuUtils.getAccessToken(wx);
            String jsonStr = WxMenuUtils.getMenuInfo(access_token);

            JSONObject jsonObject = JSONObject.parseObject(jsonStr);

            JSONObject root = jsonObject.getJSONObject("menu");
            if (null != root) {
                JSONArray buttonJSONArray = root.getJSONArray("button");
                Map<String, String> obj;
                for (int i = 0; i < buttonJSONArray.size(); i++) {
                    JSONObject buttonJSONObject = (JSONObject) buttonJSONArray
                            .get(i);
                    Map<String, String> menus = new HashMap<String, String>();
                    //id,menukey,name,parentid,menutype,url,sendid,sendtype
                    menus.put("menukey", null != buttonJSONObject.getString("key") ? buttonJSONObject.getString("key") : "0,0");
                    menus.put("name", buttonJSONObject.getString("name"));
                    menus.put("parentid", "0");
                    menus.put("menutype", "click".equals(buttonJSONObject.getString("type")) ? "0" : "1");
                    menus.put("url", buttonJSONObject.getString("url"));
                    String[] messageType = menus.get("menukey").split(",");
                    menus.put("sendid", messageType[1]);
                    menus.put("sendtype", messageType[0]);
                    obj = new HashMap<String, String>();
                    obj.put("id", menus.get("sendid"));
                    if ("text".equals(menus.get("sendtype"))) {
                        obj = new TextDAO().getById(obj);
                    } else if ("news".equals(menus.get("sendtype"))) {
                        obj = new NewsmDAO().getById(obj);
                    }
                    menus.put("sendname", obj.get("name"));
                    menus.put("orders", String.valueOf(i + 1));
                    if ("1".equals(isr)) {
                        menusDAO.add(menus, Integer.parseInt(guid.get("id")));
                    }
                    int menusId = menusDAO.getMaxId();
                    menus.put("id", String.valueOf(menusId));

                    JSONArray sub_buttonJSONArray = buttonJSONObject
                            .getJSONArray("sub_button");
                    List<Map<String, String>> subMenusList = new ArrayList<Map<String, String>>();
                    for (int j = 0; j < sub_buttonJSONArray.size(); j++) {
                        JSONObject sub_buttonJSONObject = (JSONObject) sub_buttonJSONArray
                                .get(j);
                        Map<String, String> subMenus = new HashMap<String, String>();
                        //id,menukey,name,parentid,menutype,url,sendid,sendtype
                        subMenus.put("menukey", null != sub_buttonJSONObject.getString("key") ? sub_buttonJSONObject.getString("key") : "0,0");
                        subMenus.put("name", sub_buttonJSONObject.getString("name"));
                        subMenus.put("parentid", String.valueOf(menusId));
                        subMenus.put("parentname", subMenus.get("name"));
                        subMenus.put("menutype", "click".equals(sub_buttonJSONObject.getString("type")) ? "0" : "1");
                        subMenus.put("url", sub_buttonJSONObject.getString("url"));
                        String[] subMessageType = subMenus.get("menukey").split(",");
                        subMenus.put("sendid", subMessageType[1]);
                        subMenus.put("sendtype", subMessageType[0]);
                        obj = new HashMap<String, String>();
                        obj.put("id", subMenus.get("sendid"));
                        if ("text".equals(subMenus.get("sendtype"))) {
                            obj = new TextDAO().getById(obj);
                        } else if ("news".equals(subMenus.get("sendtype"))) {
                            obj = new NewsmDAO().getById(obj);
                        }
                        subMenus.put("sendname", obj.get("name"));
                        subMenus.put("orders", String.valueOf(j + 1));
                        if ("1".equals(isr)) {
                            menusDAO.add(subMenus, Integer.parseInt(guid.get("id")));
                        }
                        int subMenusId = menusDAO.getMaxId();
                        subMenus.put("id", String.valueOf(subMenusId));
                        subMenusList.add(subMenus);
                    }
                    menuMap.put(menus, subMenusList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("isr", isr);
        request.setAttribute("menuMap", menuMap);
        request.setAttribute("message", "获取成功!");
        Forward.forward(request, response, "/back/menus/menus.jsp");
    }

    public void issue(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StringBuilder params = new StringBuilder();
        params.append("");
        params.append("{");
        params.append("     \"button\":[");
        Map<String, String> menus = new HashMap<String, String>();
        menus.put("id", "0");
        List<Map<String, String>> menusList = (List<Map<String, String>>) new MenusDAO().getByParentList(menus, Integer.parseInt(guid.get("id")));
        Iterator<Map<String, String>> menusIt = menusList.iterator();
        while (menusIt.hasNext()) {
            Map<String, String> m = menusIt.next();
            params.append("      {");
            params.append("           \"name\":\"")
                    .append(m.get("name")).append("\",");
            List<Map<String, String>> subMenusList = (List<Map<String, String>>) new MenusDAO().getByParentList(m, Integer.parseInt(guid.get("id")));
            if (null == subMenusList || 0 == subMenusList.size()) {
                params.append("         \"type\":\"")
                        .append("0".equals(m.get("menutype")) ? "click" : "view").append("\"");
                if ("0".equals(m.get("menutype"))) {
                    params.append(",     \"key\":\"")
                            .append(m.get("menukey")).append("\"");
                } else {
                    params.append(",       \"url\":\"")
                            .append(m.get("url")).append("\"");
                }
                params.append("     }");
            } else {
                params.append("      \"sub_button\":[");
                Iterator<Map<String, String>> subMenusIt = subMenusList.iterator();
                while (subMenusIt.hasNext()) {
                    Map<String, String> subMenus = subMenusIt.next();
                    params.append("        {");
                    params.append("         \"name\":\"")
                            .append(subMenus.get("name")).append("\",");
                    params.append("          \"type\":\"")
                            .append("0".equals(subMenus.get("menutype")) ? "click" : "view").append("\"");
                    if ("0".equals(subMenus.get("menutype"))) {
                        params.append(",        \"key\":\"")
                                .append(subMenus.get("menukey")).append("\"");
                    } else {
                        params.append(",        \"url\":\"")
                                .append(subMenus.get("url")).append("\"");
                    }
                    params.append("      }");
                    if (subMenusIt.hasNext()) {
                        params.append(",");
                    }
                }
                params.append("]}");
            }
            if (menusIt.hasNext()) {
                params.append(",");
            }
        }
        params.append("]}");
        try {
            String res = WxMenuUtils.createMenu(params.toString().trim(),
                    WxMenuUtils.getAccessToken(wx));
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("message", "发布成功!");
        this.getList(request, response);
    }

    protected void getList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<Map<String, String>, List<Map<String, String>>> menuMap = new LinkedHashMap<Map<String, String>, List<Map<String, String>>>();
        //id,menukey,name,parentid,menutype,url,sendid,sendtype,orders
        Map<String, String> menus = new HashMap<String, String>();
        menus.put("id", "0");
        List<Map<String, String>> menusList = (List<Map<String, String>>) new MenusDAO().getByParentList(menus, Integer.parseInt(guid.get("id")));
        Map<String, String> obj;
        for (Map<String, String> m : menusList) {
            obj = new HashMap<String, String>();
            obj.put("id", m.get("sendid"));
            if ("text".equals(m.get("sendtype"))) {
                obj = new TextDAO().getById(obj);
            } else if ("news".equals(m.get("sendtype"))) {
                obj = new NewsmDAO().getById(obj);
            }
            m.put("sendname", obj.get("name"));
            List<Map<String, String>> subMenusList = (List<Map<String, String>>) new MenusDAO().getByParentList(m, Integer.parseInt(guid.get("id")));
            for (Map<String, String> subMenus : subMenusList) {
                obj = new HashMap<String, String>();
                obj.put("id", subMenus.get("sendid"));
                if ("text".equals(subMenus.get("sendtype"))) {
                    obj = new TextDAO().getById(obj);
                } else if ("news".equals(subMenus.get("sendtype"))) {
                    obj = new NewsmDAO().getById(obj);
                }
                subMenus.put("sendname", obj.get("name"));
            }
            menuMap.put(m, subMenusList);
        }
        request.setAttribute("isr", "1");
        request.setAttribute("menuMap", menuMap);
        Forward.forward(request, response, "/back/menus/menus.jsp");
    }

    protected void initManage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MenusDAO menusDAO = new MenusDAO();
        String id = request.getParameter("id");
        String parentid = request.getParameter("parentid");
        Map<String, String> menus = new HashMap<String, String>();
        menus.put("id", id);
        menus.put("parentid", parentid);
        menus.put("orders", String.valueOf(menusDAO.getMaxId()));
        menus = menusDAO.getById(menus);
        List<Map<String, String>> textList = new TextDAO().getList(Integer.parseInt(guid.get("id")));
        List<Map<String, String>> newsList = new NewsmDAO().getList(Integer.parseInt(guid.get("id")));
        List<Map<String, String>> newssList = new NewsDAO().getList(Integer.parseInt(guid.get("id")));
        List<Map<String, String>> permaterialList = new PerMaterialDAO().getList(Integer.parseInt(guid.get("id")));
        List orderList = new ArrayList();
        for (int i = 1; i <= 3; i++) {
            orderList.add(i);
        }
        request.setAttribute("wxsid", guid.get("id"));
        request.setAttribute("menus", menus);
        request.setAttribute("textList", textList);
        request.setAttribute("newsList", newsList);
        request.setAttribute("newssList", newssList);
        request.setAttribute("permaterialList", permaterialList);
        request.setAttribute("orderList", orderList);
        Forward.forward(request, response, "/back/menus/menusmanage.jsp");
    }

    protected void manage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MenusDAO menusDAO = new MenusDAO();
        Map<String, String> menus = new HashMap<String, String>();
        //id,menukey,name,parentid,menutype,url,sendid,sendtype,orders
        String[] sendid = request.getParameter("sendid").split(",");
        menus.put("id", request.getParameter("id"));
        menus.put("menukey", request.getParameter("sendid"));
        menus.put("name", request.getParameter("name"));
        menus.put("parentid", request.getParameter("parentid"));
        menus.put("menutype", request.getParameter("menutype"));
        menus.put("url", request.getParameter("url"));
        menus.put("sendid", sendid[1]);
        menus.put("sendtype", sendid[0]);
        menus.put("orders", request.getParameter("orders"));
        if ("0".equals(menus.get("id"))) {
            menusDAO.add(menus, Integer.parseInt(guid.get("id")));
        } else {
            menusDAO.update(menus);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/MenuServlet?method=getList");
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MenusDAO menusDAO = new MenusDAO();
        MenusService menusService = new MenusService();
        Map<String, String> menus = new HashMap<String, String>();
        String id = request.getParameter("id");
        String parentid = request.getParameter("parentid");
        menus.put("id", id);
        menus.put("parentid", parentid);
        if ("0".equals(parentid)) {
            menusService.delete(menus, menusDAO, Integer.parseInt(guid.get("id")));
        } else {
            menusDAO.delete(menus);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/MenuServlet?method=getList");
    }

    protected void order(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //子菜单排序
        String id = request.getParameter("id");
        String dir = request.getParameter("dir");
        MenusDAO menusDAO = new MenusDAO();
        MenusService menusService = new MenusService();
        Map<String, String> menus = new HashMap<String, String>();
        menus.put("id", request.getParameter("parentid"));
        List<Map<String, String>> menusList = menusDAO.getByParentList(menus, Integer.parseInt(guid.get("id")));
        for (int i = 0; i < menusList.size(); i++) {
            menus = menusList.get(i);
            if (id.equals(menus.get("id"))) {
                //排序
                String orders = menus.get("orders");
                if ("0".equals(dir) && 0 < i) {
                    Map<String, String> beforeMenus = menusList.get(i - 1);
                    menus.put("orders", beforeMenus.get("orders"));
                    beforeMenus.put("orders", orders);
                    menusService.updateOrders(beforeMenus, menus, menusDAO);
                }
                if ("1".equals(dir) && i < menusList.size() - 1) {
                    Map<String, String> afterMenus = menusList.get(i + 1);
                    menus.put("orders", afterMenus.get("orders"));
                    afterMenus.put("orders", orders);
                    menusService.updateOrders(menus, afterMenus, menusDAO);
                }
                break;
            }
        }
        response.sendRedirect(request.getContextPath() + "/servlet/MenuServlet?method=getList");
    }
}
