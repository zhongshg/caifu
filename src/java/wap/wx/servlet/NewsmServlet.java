/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wap.wx.dao.ItemsmDAO;
import wap.wx.dao.NewsmDAO;
import wap.wx.service.NewsmItemsmService;
import wap.wx.util.Forward;
import wap.wx.util.PageUtil;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "NewsmServlet", urlPatterns = {"/servlet/NewsmServlet"})
public class NewsmServlet extends BaseServlet {

    protected void getList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NewsmDAO newsmDAO = new NewsmDAO();
        PageUtil<Map<String, String>> pu = new PageUtil<Map<String, String>>();
        pu.setPageSize(9);
        pu.setPage(null != request.getParameter("page") && !"null".equals(request.getParameter("page")) ? Integer.parseInt(request.getParameter("page")) : 1);
        pu.setMaxSize(newsmDAO.getConut(Integer.parseInt(guid.get("id"))));
        pu.setList(newsmDAO.getList(pu, Integer.parseInt(guid.get("id"))));
        pu.setServletName("NewsmServlet");
        request.setAttribute("pu", pu);
        Forward.forward(request, response, "/back/message/news.jsp");
    }

    protected void initManage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NewsmDAO newsmDAO = new NewsmDAO();
        String id = request.getParameter("id");
        Map<String, String> newsm = new HashMap<String, String>();
        newsm.put("id", id);
        newsm = newsmDAO.getById(newsm);
        request.setAttribute("newsm", newsm);
        Forward.forward(request, response, "/back/message/newsmanage.jsp?page=" + request.getParameter("page"));
    }

    protected void manage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NewsmDAO newsmDAO = new NewsmDAO();
        Map<String, String> newsm = new HashMap<String, String>();
        newsm.put("id", request.getParameter("id"));
        newsm.put("name", request.getParameter("name"));
        if ("0".equals(newsm.get("id"))) {
            newsmDAO.add(newsm, Integer.parseInt(guid.get("id")));
        } else {
            newsmDAO.update(newsm);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/NewsmServlet?method=getList&page=" + request.getParameter("page"));
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> newsm = new HashMap<String, String>();
        newsm.put("id", request.getParameter("id"));
        new NewsmItemsmService().deleteNewsm(newsm, Integer.parseInt(guid.get("id")));
        response.sendRedirect(request.getContextPath() + "/servlet/NewsmServlet?method=getList");
    }

    protected void delAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> newsm = new HashMap<String, String>();
        String ids = request.getParameter("ids");
        String array[] = ids.split(",");
        for (String id : array) {
            newsm.put("id", id);
            new NewsmItemsmService().deleteNewsm(newsm, Integer.parseInt(guid.get("id")));
        }
        response.sendRedirect(request.getContextPath() + "/servlet/NewsmServlet?method=getList");
    }

    protected void initSelect(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ItemsmDAO itemsmDAO = new ItemsmDAO();
        String id = request.getParameter("id");
        Map<String, String> newsm = new LinkedHashMap<String, String>();
        newsm.put("id", id);
        List<Map<String, String>> nimList = itemsmDAO.getNimByNewsMList(newsm, Integer.parseInt(guid.get("id")));
        List<Map<String, String>> itemsmList = itemsmDAO.getList(Integer.parseInt(guid.get("id")));
        for (Map<String, String> itemsm : itemsmList) {
            itemsm.put("selects", "0");
            for (Map<String, String> nim : nimList) {
                if (itemsm.get("id").equals(nim.get("iid"))) {
                    itemsm.remove("selects");
                    itemsm.put("selects", "1");
                    break;
                }
            }
        }
        request.setAttribute("newsm", newsm);
        request.setAttribute("itemsmList", itemsmList);
        Forward.forward(request, response, "/back/message/nim.jsp?page=" + request.getParameter("page"));
    }

    protected void change(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String itemsmckb[] = request.getParameterValues("itemsmckb");
        new NewsmItemsmService().change(id, itemsmckb, Integer.parseInt(guid.get("id")));
        Forward.forward(request, response, "/servlet/NewsmServlet?method=getList&page=" + request.getParameter("page"));
    }
}
