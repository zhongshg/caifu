/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import job.tot.util.RequestUtil;
import wap.wx.dao.PowersDAO;
import wap.wx.util.Forward;
import wap.wx.util.PageUtil;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "PowersServlet", urlPatterns = {"/servlet/PowersServlet"})
public class PowersServlet extends BaseServlet {

    protected void getList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PowersDAO powersDAO = new PowersDAO();
        PageUtil<Map<String, String>> pu = new PageUtil<Map<String, String>>();
        pu.setPageSize(9);
        pu.setPage(null != request.getParameter("page") && !"null".equals(request.getParameter("page")) ? Integer.parseInt(request.getParameter("page")) : 1);
        pu.setMaxSize(powersDAO.getConut());
        pu.setList(powersDAO.getList(pu));
        pu.setServletName("PowersServlet");
        request.setAttribute("pu", pu);
        Forward.forward(request, response, "/users/powers.jsp");
    }

    protected void initManage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PowersDAO powersDAO = new PowersDAO();
        String id = request.getParameter("id");
        Map<String, String> powers = new HashMap<String, String>();
        powers.put("id", id);
        powers = powersDAO.getById(powers);
        if ("0".equals(id)) {
            powers.put("orders", String.valueOf(powersDAO.getMaxId() + 1));
        }
        List<Map<String, String>> powersList = powersDAO.getList();
        request.setAttribute("powers", powers);
        request.setAttribute("powersList", powersList);
        Forward.forward(request, response, "/users/powersmanage.jsp?page=" + request.getParameter("page"));
    }

    protected void manage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PowersDAO powersDAO = new PowersDAO();
        Map<String, String> powers = new HashMap<String, String>();
        powers.put("id", request.getParameter("id"));
        powers.put("name", request.getParameter("name"));
        powers.put("parentid", request.getParameter("parentid"));
        powers.put("url", request.getParameter("url"));
        powers.put("remark", request.getParameter("remark"));
        powers.put("orders", request.getParameter("orders"));
        if ("0".equals(powers.get("id"))) {
            powersDAO.add(powers);
        } else {
            powersDAO.update(powers);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/PowersServlet?method=getList&page=" + request.getParameter("page"));
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> powers = new HashMap<String, String>();
        powers.put("id", request.getParameter("id"));
        new PowersDAO().delete(powers);
        response.sendRedirect(request.getContextPath() + "/servlet/PowersServlet?method=getList");
    }

    protected void delAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> powers = new HashMap<String, String>();
        String ids = request.getParameter("ids");
        String array[] = ids.split(",");
        for (String id : array) {
            powers.put("id", id);
            new PowersDAO().delete(powers);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/PowersServlet?method=getList");
    }

    protected void updateorders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = RequestUtil.getInt(request, "id");
        int orders = RequestUtil.getInt(request, "orders");
        new PowersDAO().updateOrders(id, orders);
        PrintWriter out = response.getWriter();
        out.print(orders);
        out.close();
    }
}
