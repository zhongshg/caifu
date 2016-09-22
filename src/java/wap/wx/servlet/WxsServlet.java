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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wap.wx.dao.WxsDAO;
import wap.wx.service.SubscriberService;
import wap.wx.service.WxsService;
import static wap.wx.servlet.BaseServlet.wx;
import wap.wx.util.Forward;
import wap.wx.util.PageUtil;

/**
 *
 * @author Administrator
 */
public class WxsServlet extends BaseServlet {

    protected void getList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WxsDAO wxsDAO = new WxsDAO();
        PageUtil<Map<String, String>> pu = new PageUtil<Map<String, String>>();
        pu.setPageSize(9);
        pu.setPage(null != request.getParameter("page") && !"null".equals(request.getParameter("page")) ? Integer.parseInt(request.getParameter("page")) : 1);
        pu.setMaxSize(wxsDAO.getConut());
        pu.setList(wxsDAO.getList(pu));
        pu.setServletName("WxsServlet");
        request.setAttribute("pu", pu);
        Forward.forward(request, response, "/back/wxs/wxs.jsp");
    }

    protected void getById(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WxsDAO wxsDAO = new WxsDAO();
        PageUtil<Map<String, String>> pu = new PageUtil<Map<String, String>>();
        pu.setPageSize(9);
        pu.setPage(null != request.getParameter("page") && !"null".equals(request.getParameter("page")) ? Integer.parseInt(request.getParameter("page")) : 1);
        pu.setMaxSize(1);
        pu.setList(wxsDAO.getList(wx));
        pu.setServletName("WxsServlet");
        request.setAttribute("pu", pu);
        Forward.forward(request, response, "/back/wxs2/wxs.jsp");
    }

    protected void initManage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WxsDAO wxsDAO = new WxsDAO();
        String id = request.getParameter("id");
        Map<String, String> wx = new HashMap<String, String>();
        wx.put("id", id);
        wx = wxsDAO.getById(wx);
        request.setAttribute("wx", wx);
        Forward.forward(request, response, "/back/wxs/wxsmanage.jsp?page=" + request.getParameter("page"));
    }

    protected void manage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WxsDAO wxsDAO = new WxsDAO();
        Map<String, String> wx = new HashMap<String, String>();
        wx.put("id", request.getParameter("id"));
        wx.put("name", request.getParameter("name"));
        wx.put("appid", request.getParameter("appid"));
        wx.put("appsecret", request.getParameter("appsecret"));
        wx.put("token", request.getParameter("token"));
        wx.put("encodingaeskey", request.getParameter("encodingaeskey"));
        wx.put("uid", user.get("id"));
        wx.put("status", request.getParameter("status"));
        wx.put("mch_id", request.getParameter("mch_id"));
        wx.put("wxpaykey", request.getParameter("wxpaykey"));
        wx.put("certificate", request.getParameter("certificate"));
        wx.put("endduring", "7");
        wx.put("weishopcolor", "");
        wx.put("weishoptextcolor", "");
        wx.put("qrcodewarns", "");
        wx.put("weimyshopcolor", "");
        wx.put("messageqrcodewarns", "");
        wx.put("title1", "");
        wx.put("title2", "");
        wx.put("subtitle1", "");
        wx.put("subtitle2", "");
        wx.put("subtitle3", "");
        wx.put("weimyshoptextcolor", "");
        wx.put("send_name", request.getParameter("send_name"));
        wx.put("wishing", request.getParameter("wishing"));
        wx.put("redremark", request.getParameter("redremark"));
        wx.put("qrcodewarnsfontstyle", "宋体");
        wx.put("qrcodewarnsfontcolor", "#ffffff");
        wx.put("qrcodewarnsfontsize", "26");
        wx.put("isshowsubscriber", "0");
        wx.put("vipidbasic", request.getParameter("vipidbasic"));

        wx.put("sendtoreceivelimit", "0");
        wx.put("receivetowithdrawlimit", "0");
        wx.put("withdrawmoneylimit", "0");
        wx.put("orderlimitperday", "0");
        wx.put("commissionsendtype", "1");
        wx.put("vipmoneylimit", "0");
        wx.put("isbuytovip", "0");
        wx.put("img", "");
        wx.put("issharesubscriber", "0");
//        preferentialtype,discountratio,returnratio
        wx.put("preferentialtype", "0");
        wx.put("discountratio", "100");
        wx.put("returnratio", "0");

        String adminsubscriber = request.getParameter("adminsubscriber");
        if (!"0".equals(adminsubscriber)) {
            adminsubscriber = request.getParameter("adminsubscriberopenid");
        }
        wx.put("adminsubscriber", adminsubscriber);
        wx.put("myqrcodetype", request.getParameter("myqrcodetype"));

        if ("0".equals(wx.get("id"))) {
            wxsDAO.add(wx);
        } else {
            wxsDAO.update1(wx);
        }

        //清理二维码
        new SubscriberService().deleteqrcode(wx.get("id"), request);

        response.sendRedirect(request.getContextPath() + "/servlet/WxsServlet?method=getList&page=" + request.getParameter("page"));
    }

    protected void initManage2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WxsDAO wxsDAO = new WxsDAO();
        String id = request.getParameter("id");
        Map<String, String> wx = new HashMap<String, String>();
        wx.put("id", id);
        wx = wxsDAO.getById(wx);
        request.setAttribute("wx", wx);
        Forward.forward(request, response, "/back/wxs2/wxsmanage.jsp?page=" + request.getParameter("page"));
    }

    protected void manage2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WxsDAO wxsDAO = new WxsDAO();
        Map<String, String> wx = new HashMap<String, String>();
        wx.put("id", request.getParameter("id"));
        wx.put("endduring", request.getParameter("endduring"));
        wx.put("weishopcolor", request.getParameter("weishopcolor"));
        wx.put("weishoptextcolor", request.getParameter("weishoptextcolor"));
        wx.put("qrcodewarns", request.getParameter("qrcodewarns"));
        wx.put("weimyshopcolor", request.getParameter("weimyshopcolor"));
        wx.put("messageqrcodewarns", request.getParameter("messageqrcodewarns"));
        wx.put("title1", request.getParameter("title1"));
        wx.put("title2", request.getParameter("title2"));
        wx.put("subtitle1", request.getParameter("subtitle1"));
        wx.put("subtitle2", request.getParameter("subtitle2"));
        wx.put("subtitle3", request.getParameter("subtitle3"));
        wx.put("weimyshoptextcolor", request.getParameter("weimyshoptextcolor"));
        wx.put("qrcodewarnsfontstyle", request.getParameter("qrcodewarnsfontstyle"));
        wx.put("qrcodewarnsfontcolor", request.getParameter("qrcodewarnsfontcolor"));
        wx.put("qrcodewarnsfontsize", request.getParameter("qrcodewarnsfontsize"));
        wx.put("isshowsubscriber", request.getParameter("isshowsubscriber"));
//sendtoreceivelimit,receivetowithdrawlimit,withdrawmoneylimit,orderlimitperday,commissionsendtype,vipmoneylimit,isbuytovip
        wx.put("sendtoreceivelimit", request.getParameter("sendtoreceivelimit"));
        wx.put("receivetowithdrawlimit", request.getParameter("receivetowithdrawlimit"));
        wx.put("withdrawmoneylimit", request.getParameter("withdrawmoneylimit"));
        wx.put("orderlimitperday", request.getParameter("orderlimitperday"));
        wx.put("commissionsendtype", request.getParameter("commissionsendtype"));
        wx.put("vipmoneylimit", request.getParameter("vipmoneylimit"));
        wx.put("isbuytovip", request.getParameter("isbuytovip"));
        wx.put("img", request.getParameter("img"));
        wx.put("issharesubscriber", request.getParameter("issharesubscriber"));
        wx.put("preferentialtype", request.getParameter("preferentialtype"));
        wx.put("discountratio", null != request.getParameter("discountratio") ? request.getParameter("discountratio") : "100");
        wx.put("returnratio", null != request.getParameter("returnratio") ? request.getParameter("returnratio") : "0");
//        if ("0".equals(wx.get("id"))) {
//            wxsDAO.add(wx);
//        } else {
        wxsDAO.update2(wx);
        //清理二维码特例
        new WxsService().deleteqrcode(wx.get("id"), request);
//        }
        response.sendRedirect(request.getContextPath() + "/servlet/WxsServlet?method=getById&page=" + request.getParameter("page"));
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WxsService wxsService = new WxsService();
        Map<String, String> wx = new HashMap<String, String>();
        wx.put("id", request.getParameter("id"));
        wx = new WxsDAO().getById(wx);
        wxsService.delete(wx);
        response.sendRedirect(request.getContextPath() + "/servlet/WxsServlet?method=getList");
    }

    protected void delAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WxsService wxsService = new WxsService();
        Map<String, String> wx = new HashMap<String, String>();
        String ids = request.getParameter("ids");
        String array[] = ids.split(",");
        for (String id : array) {
            wx.put("id", !"".equals(id) ? id : "0");
            wx = new WxsDAO().getById(wx);
            wxsService.delete(wx);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/WxsServlet?method=getList");
    }

    protected void upstatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Map<String, String>> list = new WxsDAO().getList(user);
        for (Map<String, String> map : list) {
            map.put("status", "0");
        }
        WxsService wxsService = new WxsService();
        Map<String, String> wx = new HashMap<String, String>();
        wx.put("id", request.getParameter("id"));
        wx.put("status", "1");
        new WxsService().upstatus(wx, list);
        response.sendRedirect(request.getContextPath() + "/servlet/WxsServlet?method=getList");
    }
}
