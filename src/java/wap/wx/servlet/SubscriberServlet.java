/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.servlet;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import job.tot.bean.DataField;
import job.tot.dao.DaoFactory;
import wap.wx.dao.AreaDAO;
import wap.wx.dao.SubscriberDAO;
import wap.wx.dao.WxsDAO;
import wap.wx.menu.WxMenuUtils;
import wap.wx.service.SubscriberService;
import static wap.wx.servlet.BaseServlet.wx;
import wap.wx.util.DbConn;
import wap.wx.util.Forward;
import wap.wx.util.PageUtil;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "SubscriberServlet", urlPatterns = {"/SubscriberServlet"})
public class SubscriberServlet extends BaseServlet {

    protected void getList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sign = request.getParameter("sign");
        sign = null == sign ? "0" : sign;
        String text = request.getParameter("text");
        text = null == text ? "" : text;
        String selectsign = "0";
        if ("1".equals(sign)) {
            selectsign = request.getParameter("selectsign");
        } else if ("2".equals(sign)) {
            selectsign = request.getParameter("selectsign2");
        } else if ("3".equals(sign)) {//这是点击下级人数产生，虚拟为查询
            selectsign = request.getParameter("selectsign3");
        }

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, - 6);
        Date d = c.getTime();
        String pred = WxMenuUtils.formatdate.format(d);
        String starttime = request.getParameter("starttime");
        starttime = null == starttime ? pred + " 00:00:00" : starttime;
        String endtime = request.getParameter("endtime");
        Date date = new Date();
        long time = (date.getTime() / 1000) + 60 * 60 * 24;
        date.setTime(time * 1000);
        endtime = null == endtime ? WxMenuUtils.formatdate.format(date) + " 00:00:00" : endtime;
        PageUtil<Map<String, String>> pu = new PageUtil<Map<String, String>>();
        pu.setPageSize(30);
        pu.setPage(null != request.getParameter("page") && !"null".equals(request.getParameter("page")) ? Integer.parseInt(request.getParameter("page")) : 1);

        HttpSession session = request.getSession();
        session.removeAttribute(wx.get("id") + Integer.parseInt(sign) + Integer.parseInt(selectsign) + text + starttime + endtime);
        List<Map<String, String>> subscriberList = (List<Map<String, String>>) session.getAttribute(wx.get("id") + Integer.parseInt(sign) + Integer.parseInt(selectsign) + text + starttime + endtime);
        if (null == subscriberList || "1".equals(sign)) {
            subscriberList = new SubscriberService().getSubscriberSelectList(pu, wx.get("id"), Integer.parseInt(sign), Integer.parseInt(selectsign), text, starttime, endtime);
            session.setAttribute(wx.get("id") + Integer.parseInt(sign) + Integer.parseInt(selectsign) + text + starttime + endtime, subscriberList);
        }
        int maxsize = subscriberList.size();

        //特殊
        if (!"0".equals(selectsign) && !"8".equals(selectsign)) {
            maxsize = 300 < subscriberList.size() ? 300 : subscriberList.size();
        }

        float totaltotalYJ = 0;
        if ("8".equals(selectsign)) {
            for (Map<String, String> subscriber : subscriberList) {
                if (0 < Float.parseFloat(subscriber.get("totalYJ"))) {
                    totaltotalYJ += Float.parseFloat(subscriber.get("totalYJ"));
                }
            }
        }

        pu.setMaxSize(maxsize);
        List<Map<String, String>> pageSubscriberList = new LinkedList();
        int start = pu.getPageSize() * (0 < pu.getPage() ? pu.getPage() - 1 : 0);
        int end = pu.getPageSize() * pu.getPage() <= pu.getMaxSize() ? pu.getPageSize() * pu.getPage() : pu.getMaxSize();
        Map<String, String> tempsubscriber = null;
        for (int i = 0; i < maxsize; i++) {
            tempsubscriber = subscriberList.get(i);
            tempsubscriber.put("rank", String.valueOf(i + 1));
            if (i >= start && i < end) {
                pageSubscriberList.add(tempsubscriber);
            }
        }
        //注入区域代理
        AreaDAO areaDAO = new AreaDAO();
        List<Map<String, String>> prolist = areaDAO.getList(0);
        Map<String, List<Map<String, String>>> cityMaps = new HashMap<String, List<Map<String, String>>>();

        DataField address = null;
        DataField Sname = null;
        DataField Ssname = null;
        DataField Xname = null;
        List<Map<String, String>> citylist = null;
        for (Map<String, String> subscriber : pageSubscriberList) {
            //注入联系方式和地址
            address = DaoFactory.getAddressDAO().getUserId(subscriber.get("openid"), wx.get("id"));
            subscriber.put("phone", null != address ? address.getFieldValue("Phone") : "");
            Sname = DaoFactory.getAreaDaoImplJDBC().get(null != address ? address.getFieldValue("Sname") : "0");
            Ssname = DaoFactory.getAreaDaoImplJDBC().get(null != address ? address.getFieldValue("Ssname") : "0");
            Xname = DaoFactory.getAreaDaoImplJDBC().get(null != address ? address.getFieldValue("Xname") : "0");
            subscriber.put("address", (null != Sname ? Sname.getFieldValue("title") : "") + "," + (null != Ssname ? Ssname.getFieldValue("title") : "") + "," + (null != Xname ? Xname.getFieldValue("title") : "") + "," + (null != address ? address.getFieldValue("Address") : ""));

            citylist = areaDAO.getList(null != subscriber.get("areaproxyprovince") && !"0".equals(subscriber.get("areaproxyprovince")) ? Integer.parseInt(subscriber.get("areaproxyprovince")) : -1);
            cityMaps.put(subscriber.get("openid"), citylist);
        }
        request.setAttribute("prolist", prolist);
        request.setAttribute("cityMaps", cityMaps);

        request.setAttribute("totaltotalYJ", totaltotalYJ);

        //专区
//        List<Map<String, String>> subscriberList = subscriberDAO.getList(pu, Integer.parseInt(wx.get("id")), id, openid, isvip);
//        Connection conn = DbConn.getConn();
//        for (Map<String, String> subscriber : subscriberList) {
//            float totalcanmoney = new SubscriberService().totalcanmoney(conn, subscriberDAO, subscriber, wx);
//            subscriber.put("totalcanmoney", new DecimalFormat("##0.00").format(totalcanmoney));
//        }
//        try {
//            conn.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(SubscriberServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
        pu.setList(pageSubscriberList);
        pu.setSign(sign);
        pu.setServletName("SubscriberServlet");
        request.setAttribute("pu", pu);
        request.setAttribute("sign", sign);
        request.setAttribute("text", text);
        if ("1".equals(sign)) {
            request.setAttribute("selectsign", selectsign);
        } else if ("2".equals(sign)) {
            request.setAttribute("selectsign2", selectsign);
        } else if ("3".equals(sign)) {
            request.setAttribute("selectsign3", selectsign);
        }
        request.setAttribute("starttime", starttime);
        request.setAttribute("endtime", endtime);

        Forward.forward(request, response, "/back/subscriber/subscriber.jsp");
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
        response.sendRedirect(request.getContextPath() + "/servlet/SubscriberServlet?method=getList");
    }

    protected void isvip(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String openid = request.getParameter("openid");
        String wxsid = request.getParameter("wxsid");
        int isvip = Integer.parseInt(request.getParameter("isvip"));
        new SubscriberDAO().updateVip(openid, wxsid, isvip);
//        response.sendRedirect(request.getContextPath() + "/servlet/SubscriberServlet?method=getList&page=" + request.getParameter("page"));
        PrintWriter out = response.getWriter();
        out.print(isvip);
        out.close();
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

    protected void getTotalList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String wxqueryid = request.getParameter("wxqueryid");
        wxqueryid = null == wxqueryid ? "-1" : wxqueryid;
        String id = request.getParameter("id");
        id = null == id ? "" : id;
        String openid = request.getParameter("openid");
        openid = null == openid ? "" : openid;
        String isvip = request.getParameter("isvip");
        isvip = null == isvip ? "" : isvip;
        SubscriberDAO subscriberDAO = new SubscriberDAO();
        PageUtil<Map<String, String>> pu = new PageUtil<Map<String, String>>();
        pu.setPageSize(9);
        pu.setPage(null != request.getParameter("page") && !"null".equals(request.getParameter("page")) ? Integer.parseInt(request.getParameter("page")) : 1);
        pu.setMaxSize(subscriberDAO.getCount(Integer.parseInt(wxqueryid), id, openid, isvip));

        //专区
        List<Map<String, String>> subscriberList = subscriberDAO.getList(pu, Integer.parseInt(wxqueryid), id, openid, isvip);
        Connection conn = DbConn.getConn();
        Map<String, String> wxTemp = new HashMap<String, String>();
        WxsDAO wxsDAO = new WxsDAO();
        for (Map<String, String> subscriber : subscriberList) {
            wxTemp.put("id", subscriber.get("wxsid"));
            wxTemp = wxsDAO.getById(wxTemp);
            float totalcanmoney = new SubscriberService().totalcanmoney(conn, subscriberDAO, subscriber, wxTemp);
            subscriber.put("totalcanmoney", new DecimalFormat("##0.00").format(totalcanmoney));
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        pu.setList(subscriberList);
        pu.setServletName("SubscriberServlet?method=getTotalList");
        request.setAttribute("wxqueryid", wxqueryid);
        request.setAttribute("pu", pu);
        request.setAttribute("id", id);
        request.setAttribute("openid", openid);
        request.setAttribute("isvip", isvip);

        List<Map<String, String>> mapList = new WxsDAO().getList();
        request.setAttribute("mapList", mapList);
        Forward.forward(request, response, "/shop/manage/totals/subscriber.jsp");
    }

    protected void delTotalAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String wxqueryid = request.getParameter("wxqueryid");
        Map<String, String> subscriber = new HashMap<String, String>();
        String ids = request.getParameter("ids");
        String array[] = ids.split(",");
        for (String id : array) {
            subscriber.put("id", id);
            subscriber = new SubscriberDAO().getById(wxqueryid, id);
            new SubscriberService().delete(subscriber, Integer.parseInt(wxqueryid), request);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/SubscriberServlet?method=getTotalList&wxqueryid=" + wxqueryid);
    }

    protected void isvipTotal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String wxqueryid = request.getParameter("wxqueryid");
        String openid = request.getParameter("openid");
        int isvip = Integer.parseInt(request.getParameter("isvip"));
        new SubscriberDAO().updateVip(openid, wxqueryid, isvip);
        response.sendRedirect(request.getContextPath() + "/servlet/SubscriberServlet?method=getTotalList&page=" + request.getParameter("page") + "&wxqueryid=" + wxqueryid);
    }

    protected void updatePercent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String openid = request.getParameter("openid");
        String wxsid = request.getParameter("wxsid");
        String percent = request.getParameter("percent");
        new SubscriberDAO().updatePercent(openid, wxsid, Float.parseFloat(percent));
        PrintWriter out = response.getWriter();
        out.println(percent);
        out.close();
    }

    protected void updateInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String openid = request.getParameter("openid");
        String wxsid = request.getParameter("wxsid");
        Map<String, String> subscriber = new HashMap<String, String>();
        Map<String, String> wx = new HashMap<String, String>();
        wx.put("id", wxsid);
        JSONObject object = WxMenuUtils.getUserInfo(openid, new WxsDAO().getById(wx));
        object = null != object ? object : new JSONObject();
        subscriber.put("nickname", object.getString("nickname"));
//        subscriber.put("username", object.getString("nickname"));
        subscriber.put("sex", null != object.getString("sex") ? object.getString("sex") : "0");
        subscriber.put("language", object.getString("language"));
        subscriber.put("city", object.getString("city"));
        subscriber.put("province", object.getString("province"));
        subscriber.put("country", object.getString("country"));
        subscriber.put("headimgurl", object.getString("headimgurl"));
        subscriber.put("unionid", object.getString("unionid"));
        new SubscriberDAO().updateInfo(openid, Integer.parseInt(wxsid), subscriber);
        PrintWriter out = response.getWriter();
        out.println("{\"nickname\":\"" + subscriber.get("nickname") + "\",\"headimgurl\":\"" + subscriber.get("headimgurl") + "\"}");
        out.close();
    }

    protected void updateareaproxy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String openid = request.getParameter("openid");
        String wxsid = request.getParameter("wxsid");
        String areaproxyprovince = request.getParameter("areaproxyprovince");
        String areaproxycity = request.getParameter("areaproxycity");
        String areaproxydiscount = request.getParameter("areaproxydiscount");
        Map<String, String> subscriber = new HashMap<String, String>();
        subscriber.put("areaproxyprovince", areaproxyprovince);
        subscriber.put("areaproxycity", areaproxycity);
        subscriber.put("areaproxydiscount", areaproxydiscount);
        new SubscriberDAO().updateareaproxy(openid, Integer.parseInt(wxsid), subscriber);
        PrintWriter out = response.getWriter();
        out.println("{\"areaproxyprovince\":\"" + subscriber.get("areaproxyprovince") + "\",\"areaproxycity\":\"" + subscriber.get("areaproxycity") + "\",\"areaproxydiscount\":\"" + subscriber.get("areaproxydiscount") + "\"}");
        out.close();
    }
}
