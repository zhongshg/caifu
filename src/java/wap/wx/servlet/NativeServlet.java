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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wap.wx.dao.NativeDAO;
import wap.wx.dao.NewsmDAO;
import wap.wx.dao.PerMaterialDAO;
import wap.wx.dao.TextDAO;
import wap.wx.util.Forward;
import wap.wx.util.PageUtil;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "NativeServlet", urlPatterns = {"/servlet/NativeServlet"})
public class NativeServlet extends BaseServlet {

    protected void getList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NativeDAO nativeDAO = new NativeDAO();
        PageUtil<Map<String, String>> pu = new PageUtil<Map<String, String>>();
        pu.setPageSize(9);
        pu.setPage(null != request.getParameter("page") && !"null".equals(request.getParameter("page")) ? Integer.parseInt(request.getParameter("page")) : 1);
        pu.setMaxSize(nativeDAO.getConut(Integer.parseInt(guid.get("id"))));

        List<Map<String, String>> nativeList = nativeDAO.getList(pu, Integer.parseInt(guid.get("id")));
        for (Map<String, String> natives : nativeList) {
            Map<String, String> message = new HashMap<String, String>();
            if ("text".equals(natives.get("type"))) {
                Map<String, String> text = new HashMap<String, String>();
                text.put("id", natives.get("lid"));
                message = new TextDAO().getById(text);
            } else if ("news".equals(natives.get("type"))) {
                Map<String, String> news = new HashMap<String, String>();
                news.put("id", natives.get("lid"));
                message = new NewsmDAO().getById(news);
            } else {
                Map<String, String> permaterial = new HashMap<String, String>();
                permaterial.put("id", natives.get("lid"));
                message = new PerMaterialDAO().getById(permaterial);
            }
            natives.put("messagename", message.get("name"));
        }

        pu.setList(nativeList);
        pu.setServletName("NativeServlet");
        request.setAttribute("pu", pu);
        Forward.forward(request, response, "/back/native/native.jsp");
    }

    protected void initManage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NativeDAO nativeDAO = new NativeDAO();
        String id = request.getParameter("id");
        String wds = request.getParameter("wds");
        Map<String, String> natives = new HashMap<String, String>();
        natives.put("id", id);
        natives = nativeDAO.getById(natives);
        if (null == natives.get("id")) {
            natives.put("id", "0");
            natives.put("wds", wds);
        }
        List<Map<String, String>> textList = new TextDAO().getList(Integer.parseInt(guid.get("id")));
        List<Map<String, String>> newsList = new NewsmDAO().getList(Integer.parseInt(guid.get("id")));
        List<Map<String, String>> permaterialList = new PerMaterialDAO().getList(Integer.parseInt(guid.get("id")));
        request.setAttribute("natives", natives);
        request.setAttribute("textList", textList);
        request.setAttribute("newsList", newsList);
        request.setAttribute("permaterialList", permaterialList);
        Forward.forward(request, response, "/back/native/nativemanage.jsp?page=" + request.getParameter("page"));
    }

    protected void initWdsManage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NativeDAO nativeDAO = new NativeDAO();
        String wds = request.getParameter("wds");
        Map<String, String> natives = new HashMap<String, String>();
        natives.put("wds", wds);
        natives = nativeDAO.getByWds(natives, Integer.parseInt(guid.get("id")));
        if (null == natives.get("id")) {
            natives.put("id", "0");
            natives.put("wds", wds);
        }
        List<Map<String, String>> textList = new TextDAO().getList(Integer.parseInt(guid.get("id")));
        List<Map<String, String>> newsList = new NewsmDAO().getList(Integer.parseInt(guid.get("id")));
        List<Map<String, String>> permaterialList = new PerMaterialDAO().getList(Integer.parseInt(guid.get("id")));
        request.setAttribute("natives", natives);
        request.setAttribute("textList", textList);
        request.setAttribute("newsList", newsList);
        request.setAttribute("permaterialList", permaterialList);
        Forward.forward(request, response, "/back/native/nativemanage.jsp");
    }

    protected void manage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NativeDAO nativeDAO = new NativeDAO();
        Map<String, String> natives = new HashMap<String, String>();
        natives.put("id", request.getParameter("id"));
        natives.put("name", request.getParameter("name"));
        String[] lid = request.getParameter("lid").split(",");
        natives.put("type", lid[0]);
        natives.put("lid", lid[1]);
        natives.put("remark", request.getParameter("remark"));
        natives.put("wds", request.getParameter("wds"));
        if ("0".equals(natives.get("id"))) {
            nativeDAO.add(natives, Integer.parseInt(guid.get("id")));
        } else {
            nativeDAO.update(natives);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/NativeServlet?method=getList&page=" + request.getParameter("page"));
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> natives = new HashMap<String, String>();
        natives.put("id", request.getParameter("id"));
        new NativeDAO().delete(natives);
        response.sendRedirect(request.getContextPath() + "/servlet/NativeServlet?method=getList");
    }

    protected void delAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> natives = new HashMap<String, String>();
        String ids = request.getParameter("ids");
        String array[] = ids.split(",");
        for (String id : array) {
            natives.put("id", id);
            new NativeDAO().delete(natives);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/NativeServlet?method=getList");
    }
}
