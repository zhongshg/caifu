/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wap.wx.dao.ItemsmDAO;
import wap.wx.dao.NewsDAO;
import wap.wx.menu.WxMenuUtils;
import wap.wx.service.NewsmItemsmService;
import wap.wx.util.Forward;
import wap.wx.util.PageUtil;

public class ItemsmServlet extends BaseServlet {

    protected void getList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ItemsmDAO itemsmDAO = new ItemsmDAO();
        PageUtil<Map<String, String>> pu = new PageUtil<Map<String, String>>();
        pu.setPageSize(9);
        pu.setPage(null != request.getParameter("page") && !"null".equals(request.getParameter("page")) ? Integer.parseInt(request.getParameter("page")) : 1);
        pu.setMaxSize(itemsmDAO.getConut(Integer.parseInt(guid.get("id"))));
        pu.setList(itemsmDAO.getList(pu, Integer.parseInt(guid.get("id"))));
        pu.setServletName("ItemsmServlet");
        request.setAttribute("pu", pu);
        request.setAttribute("now", WxMenuUtils.format.format(new Date()));
        Forward.forward(request, response, "/back/message/items.jsp");
    }

    protected void initManage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ItemsmDAO itemsmDAO = new ItemsmDAO();
        Map<String, String> itemsm = new HashMap<String, String>();
        itemsm.put("id", request.getParameter("id"));
        itemsm.put("orders", String.valueOf(itemsmDAO.getMaxId()));
        itemsm = itemsmDAO.getById(itemsm);
        List<Map<String, String>> newssList = new NewsDAO().getList(Integer.parseInt(guid.get("id")));
        request.setAttribute("itemsm", itemsm);
        request.setAttribute("newssList", newssList);
        Forward.forward(request, response, "/back/message/itemsmanage.jsp?page=" + request.getParameter("page"));
    }

    protected void manage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ItemsmDAO itemsmDAO = new ItemsmDAO();
        Map<String, String> itemsm = new HashMap<String, String>();
        itemsm.put("id", request.getParameter("id"));
        itemsm.put("name", request.getParameter("name"));
        itemsm.put("describes", request.getParameter("describes"));
        itemsm.put("img", request.getParameter("img"));
        itemsm.put("url", request.getParameter("url"));
        itemsm.put("remark", request.getParameter("remark"));
        itemsm.put("orders", request.getParameter("orders"));
        try {
            String mediaid[] = request.getParameter("mediaid").split(",");
            itemsm.put("mediaid", mediaid[0]);
            itemsm.put("mediatimes", mediaid[1]);
        } catch (Exception e) {
            itemsm.put("mediaid", "");
            itemsm.put("mediatimes", "2015-01-01 00:00:00");
        }
        if ("0".equals(itemsm.get("id"))) {
            itemsmDAO.add(itemsm, Integer.parseInt(guid.get("id")));
        } else {
            itemsmDAO.update(itemsm);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/ItemsmServlet?method=getList&page=" + request.getParameter("page"));
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> itemsm = new HashMap<String, String>();
        itemsm.put("id", request.getParameter("id"));
        //删除图片
        itemsm = new ItemsmDAO().getById(itemsm);
        if (!"".equals(itemsm.get("img")) && null != itemsm.get("img")) {
            java.io.File oldFile = new java.io.File(this.getServletContext()
                    .getRealPath(itemsm.get("img")));
            oldFile.delete();
        }
        new NewsmItemsmService().deleteItemsm(itemsm, Integer.parseInt(guid.get("id")));
        response.sendRedirect(request.getContextPath() + "/servlet/ItemsmServlet?method=getList");
    }

    protected void delAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> itemsm = new HashMap<String, String>();
        String ids = request.getParameter("ids");
        String array[] = ids.split(",");
        for (String id : array) {
            itemsm.put("id", !"".equals(id) ? id : "0");
            itemsm = new ItemsmDAO().getById(itemsm);
            if (!"".equals(itemsm.get("img")) && null != itemsm.get("img")) {
                java.io.File oldFile = new java.io.File(this.getServletContext()
                        .getRealPath(itemsm.get("img")));
                oldFile.delete();
            }
            new NewsmItemsmService().deleteItemsm(itemsm, Integer.parseInt(guid.get("id")));
        }
        response.sendRedirect(request.getContextPath() + "/servlet/ItemsmServlet?method=getList");
    }

    protected void order(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ItemsmDAO itemsmDAO = new ItemsmDAO();
        NewsmItemsmService newsmItemsmService = new NewsmItemsmService();
        String newsmid = request.getParameter("newsmid");
        String id = request.getParameter("id");
        String dir = request.getParameter("dir");
//        Map<String, String> newsm = new HashMap<String, String>();
//        newsm.put("id", newsmid);
//        List<Map<String, String>> itemsmList = itemsmDAO.getByNewsmList(newsm);
        List<Map<String, String>> itemsmList = itemsmDAO.getList(Integer.parseInt(guid.get("id")));
        for (int i = 0; i < itemsmList.size(); i++) {
            Map<String, String> itemsm = itemsmList.get(i);
            if (id.equals(itemsm.get("id"))) {
                String orders = itemsm.get("orders");
                if ("0".equals(dir) && 0 < i) {
                    Map<String, String> beforeItemsm = itemsmList.get(i - 1);
                    itemsm.put("orders", beforeItemsm.get("orders"));
                    beforeItemsm.put("orders", orders);
                    newsmItemsmService.order(beforeItemsm, itemsm, itemsmDAO);
                }
                if ("1".equals(dir) && i < itemsmList.size() - 1) {
                    Map<String, String> afterItemsm = itemsmList.get(i + 1);
                    itemsm.put("orders", afterItemsm.get("orders"));
                    afterItemsm.put("orders", orders);
                    newsmItemsmService.order(itemsm, afterItemsm, itemsmDAO);
                }
                break;
            }
        }
        response.sendRedirect(request.getContextPath() + "/servlet/NewsmServlet?method=initSelect&id=" + newsmid);
    }
}
