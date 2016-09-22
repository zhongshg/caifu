/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wap.wx.dao.RolesDAO;
import wap.wx.service.RolesService;
import wap.wx.util.Forward;
import wap.wx.util.PageUtil;

@WebServlet(name = "RolesServlet", urlPatterns = {"/servlet/RolesServlet"})
public class RolesServlet extends BaseServlet {

    protected void getList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RolesDAO rolesDAO = new RolesDAO();
        PageUtil<Map<String, String>> pu = new PageUtil<Map<String, String>>();
        pu.setPageSize(9);
        pu.setPage(null != request.getParameter("page") && !"null".equals(request.getParameter("page")) ? Integer.parseInt(request.getParameter("page")) : 1);
        pu.setMaxSize(rolesDAO.getConut());
        pu.setList(rolesDAO.getList(pu));
        pu.setServletName("RolesServlet");
        request.setAttribute("pu", pu);
        Forward.forward(request, response, "/users/roles.jsp");
    }

    protected void initManage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RolesDAO rolesDAO = new RolesDAO();
        String id = request.getParameter("id");
        Map<String, String> roles = new HashMap<String, String>();
        roles.put("id", id);
        roles = rolesDAO.getById(roles);
        request.setAttribute("roles", roles);
        Forward.forward(request, response, "/users/rolesmanage.jsp?page=" + request.getParameter("page"));
    }

    protected void manage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RolesDAO rolesDAO = new RolesDAO();
        Map<String, String> roles = new HashMap<String, String>();
        roles.put("id", request.getParameter("id"));
        roles.put("name", request.getParameter("name"));
        roles.put("remark", request.getParameter("remark"));
        if ("0".equals(roles.get("id"))) {
            rolesDAO.add(roles);
        } else {
            rolesDAO.update(roles);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/RolesServlet?method=getList&page=" + request.getParameter("page"));
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> roles = new HashMap<String, String>();
        roles.put("id", request.getParameter("id"));
        new RolesService().delete(roles);
        response.sendRedirect(request.getContextPath() + "/servlet/RolesServlet?method=getList");
    }

    protected void delAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> roles = new HashMap<String, String>();
        String ids = request.getParameter("ids");
        String array[] = ids.split(",");
        for (String id : array) {
            roles.put("id", id);
            new RolesService().delete(roles);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/RolesServlet?method=getList");
    }
}
