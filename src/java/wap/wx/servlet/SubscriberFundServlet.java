/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wap.wx.dao.SubscriberDAO;
import wap.wx.service.SubscriberService;
import static wap.wx.servlet.BaseServlet.wx;
import wap.wx.util.DbConn;
import wap.wx.util.Forward;
import wap.wx.util.PageUtil;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "SubscriberFundServlet", urlPatterns = {"/SubscriberFundServlet"})
public class SubscriberFundServlet extends BaseServlet {

    protected void getList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        id = null == id ? "" : id;
        String openid = request.getParameter("openid");
        openid = null == openid ? "" : openid;
        String isvip = "1";// request.getParameter("isvip");
        isvip = null == isvip ? "" : isvip;

        //补参数
        float totalcanmoneys = 0f;//总计可提现金额
        float totalpagecanmoneys = 0f;//总计当前页可提现金额

        SubscriberDAO subscriberDAO = new SubscriberDAO();

        PageUtil<Map<String, String>> pu = new PageUtil<Map<String, String>>();
        pu.setPageSize(9);
        pu.setPage(null != request.getParameter("page") && !"null".equals(request.getParameter("page")) ? Integer.parseInt(request.getParameter("page")) : 1);
        pu.setMaxSize(subscriberDAO.getCount(Integer.parseInt(wx.get("id")), id, openid, isvip));

        //专区
        List<Map<String, String>> totalsubscriberList = subscriberDAO.getList(Integer.parseInt(wx.get("id")), id, openid, isvip);//总计
        List<Map<String, String>> subscriberList = subscriberDAO.getList(pu, Integer.parseInt(wx.get("id")), id, openid, isvip);//总计当前页
        Connection conn = DbConn.getConn();
        for (Map<String, String> subscriber : totalsubscriberList) {
            float totalcanmoney = new SubscriberService().totalcanmoney(conn, subscriberDAO, subscriber, wx);
            totalcanmoneys += totalcanmoney;
        }
        for (Map<String, String> subscriber : subscriberList) {
            float totalcanmoney = new SubscriberService().totalcanmoney(conn, subscriberDAO, subscriber, wx);
            subscriber.put("totalcanmoney", new DecimalFormat("##0.00").format(totalcanmoney));
            totalpagecanmoneys += totalcanmoney;
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberFundServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        pu.setList(subscriberList);
        pu.setServletName("SubscriberFundServlet");
        request.setAttribute("pu", pu);
        request.setAttribute("id", id);
        request.setAttribute("openid", openid);
        request.setAttribute("isvip", isvip);

        request.setAttribute("totalcanmoneys", new DecimalFormat("##0.00").format(totalcanmoneys));
        request.setAttribute("totalpagecanmoneys", new DecimalFormat("##0.00").format(totalpagecanmoneys));
        Forward.forward(request, response, "/back/subscriber/subscriberfund.jsp");
    }

    protected void delAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> subscriber = new HashMap<String, String>();
        String ids = request.getParameter("ids");
        String array[] = ids.split(",");
        for (String id : array) {
            subscriber.put("id", id);
            subscriber = new SubscriberDAO().getById(wx.get("id"), id);
            new SubscriberService().delete(subscriber, Integer.parseInt(wx.get("id")), request);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/SubscriberFundServlet?method=getList");
    }

    protected void isvip(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String openid = request.getParameter("openid");
        String wxsid = request.getParameter("wxsid");
        int isvip = Integer.parseInt(request.getParameter("isvip"));
        new SubscriberDAO().updateVip(openid, wxsid, isvip);
        response.sendRedirect(request.getContextPath() + "/servlet/SubscriberFundServlet?method=getList&page=" + request.getParameter("page"));
    }

    protected void orderisvip(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String openid = request.getParameter("openid");
        String wxsid = request.getParameter("wxsid");
        int isvip = Integer.parseInt(request.getParameter("isvip"));
        new SubscriberDAO().updateVip(openid, wxsid, isvip);
        out.print("0");
        out.close();
    }
}
