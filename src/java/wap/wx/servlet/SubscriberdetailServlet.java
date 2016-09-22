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
import wap.wx.dao.SubscriberdetailDAO;
import wap.wx.util.Forward;
import wap.wx.util.PageUtil;

public class SubscriberdetailServlet extends BaseServlet {

    protected void getList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SubscriberdetailDAO subscriberdetailDAO = new SubscriberdetailDAO();
        PageUtil<Map<String, String>> pu = new PageUtil<Map<String, String>>();
        pu.setPageSize(9);
        pu.setPage(null != request.getParameter("page") && !"null".equals(request.getParameter("page")) ? Integer.parseInt(request.getParameter("page")) : 1);
        pu.setMaxSize(subscriberdetailDAO.getCount(Integer.parseInt(wx.get("id"))));
        pu.setList(subscriberdetailDAO.getList(pu, Integer.parseInt(wx.get("id"))));
        pu.setServletName("SubscriberdetailServlet");
        request.setAttribute("pu", pu);
        Forward.forward(request, response, "/back/subscriber/subscriberdetail.jsp");
    }

    protected void delAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> subscriberdetail = new HashMap<String, String>();
        String ids = request.getParameter("ids");
        String array[] = ids.split(",");
        for (String id : array) {
            subscriberdetail.put("id", id);
            new SubscriberdetailDAO().delete(subscriberdetail);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/SubscriberdetailServlet?method=getList");
    }
}
