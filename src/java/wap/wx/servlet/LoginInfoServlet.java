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
import wap.wx.dao.LoginInfoDAO;
import wap.wx.util.Forward;
import wap.wx.util.PageUtil;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "LoginInfoServlet", urlPatterns = {"/servlet/LoginInfoServlet"})
public class LoginInfoServlet extends BaseServlet {

    protected void getList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LoginInfoDAO loginInfoDAO = new LoginInfoDAO();
        PageUtil<Map<String, String>> pu = new PageUtil<Map<String, String>>();
        pu.setPageSize(9);
        pu.setPage(null != request.getParameter("page") && !"null".equals(request.getParameter("page")) ? Integer.parseInt(request.getParameter("page")) : 1);
        pu.setMaxSize(loginInfoDAO.getCount());
        pu.setList(loginInfoDAO.getList(pu));
        pu.setServletName("LoginInfoServlet");
        request.setAttribute("pu", pu);
        Forward.forward(request, response, "/safes/logininfo.jsp");
    }

    protected void delAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> loginInfo = new HashMap<String, String>();
        String ids = request.getParameter("ids");
        String array[] = ids.split(",");
        for (String id : array) {
            loginInfo.put("id", id);
            new LoginInfoDAO().delete(loginInfo);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/LoginInfoServlet?method=getList");
    }
}
