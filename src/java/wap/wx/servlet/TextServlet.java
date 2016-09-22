/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wap.wx.dao.TextDAO;
import wap.wx.util.Forward;
import wap.wx.util.PageUtil;

/**
 *
 * @author Administrator
 */
public class TextServlet extends BaseServlet {

    protected void getList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TextDAO textDAO = new TextDAO();
        PageUtil<Map<String, String>> pu = new PageUtil<Map<String, String>>();
        pu.setPageSize(9);
        pu.setPage(null != request.getParameter("page") && !"null".equals(request.getParameter("page")) ? Integer.parseInt(request.getParameter("page")) : 1);
        pu.setMaxSize(textDAO.getConut(Integer.parseInt(guid.get("id"))));
        pu.setList(textDAO.getList(pu, Integer.parseInt(guid.get("id"))));
        pu.setServletName("TextServlet");
        request.setAttribute("pu", pu);
        Forward.forward(request, response, "/back/message/text.jsp");
    }

    protected void initManage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TextDAO textDAO = new TextDAO();
        String id = request.getParameter("id");
        Map<String, String> text = new HashMap<String, String>();
        text.put("id", id);
        text = textDAO.getById(text);
        request.setAttribute("text", text);
        Forward.forward(request, response, "/back/message/textmanage.jsp?page=" + request.getParameter("page"));
    }

    protected void manage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TextDAO textDAO = new TextDAO();
        Map<String, String> text = new HashMap<String, String>();
        text.put("id", request.getParameter("id"));
        text.put("name", request.getParameter("name"));
        text.put("describers", request.getParameter("describers"));
        if ("0".equals(text.get("id"))) {
            textDAO.add(text, Integer.parseInt(guid.get("id")));
        } else {
            textDAO.update(text);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/TextServlet?method=getList&page=" + request.getParameter("page"));
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> text = new HashMap<String, String>();
        text.put("id", request.getParameter("id"));
        new TextDAO().delete(text);
        response.sendRedirect(request.getContextPath() + "/servlet/TextServlet?method=getList");
    }

    protected void delAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> text = new HashMap<String, String>();
        String ids = request.getParameter("ids");
        String array[] = ids.split(",");
        for (String id : array) {
            text.put("id", !"".equals(id) ? id : "0");
            new TextDAO().delete(text);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/TextServlet?method=getList");
    }
}
