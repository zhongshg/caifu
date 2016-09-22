/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wap.wx.dao.NewstypesDAO;
import wap.wx.dao.WxsDAO;
import wap.wx.menu.WxMenuUtils;
import wap.wx.service.NewstypesService;
import wap.wx.service.SubscriberService;
import wap.wx.util.Forward;
import wap.wx.util.PageUtil;

/**
 *
 * @author Administrator
 */
public class NewstypesServlet extends BaseServlet {

    protected void getList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String parentid = request.getParameter("parentid");
        parentid = null != parentid ? parentid : "0";
        String sid = request.getParameter("sid");
        NewstypesDAO newstypesDAO = new NewstypesDAO();
        PageUtil<Map<String, String>> pu = new PageUtil<Map<String, String>>();
        pu.setPageSize(9);
        pu.setPage(null != request.getParameter("page") && !"null".equals(request.getParameter("page")) ? Integer.parseInt(request.getParameter("page")) : 1);
        pu.setMaxSize(newstypesDAO.getConut(sid, parentid, Integer.parseInt(guid.get("id"))));
        pu.setList(newstypesDAO.getList(pu, sid, parentid, Integer.parseInt(guid.get("id"))));
        pu.setServletName("NewstypesServlet");
        String typeStr = newstypesDAO.getSelectTreeDir("", parentid, Integer.parseInt(guid.get("id")));
        request.setAttribute("parentid", parentid);
        request.setAttribute("sid", sid);
        request.setAttribute("pu", pu);
        request.setAttribute("typeStr", typeStr);
        Forward.forward(request, response, "/back/news/newstypes.jsp");
    }

    protected void getUpDownList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NewstypesDAO newstypesDAO = new NewstypesDAO();
        String dir = request.getParameter("dir");
        String parentid = request.getParameter("parentid");
        String sid = request.getParameter("sid");
        if ("0".equals(dir)) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", parentid);
            map = newstypesDAO.getById(map);
            parentid = null != map.get("parentid") ? map.get("parentid") : parentid;
        }
        PageUtil<Map<String, String>> pu = new PageUtil<Map<String, String>>();
        pu.setPageSize(9);
        pu.setPage(null != request.getParameter("page") && !"null".equals(request.getParameter("page")) ? Integer.parseInt(request.getParameter("page")) : 1);
        pu.setMaxSize(newstypesDAO.getConut(sid, parentid, Integer.parseInt(guid.get("id"))));
        pu.setList(newstypesDAO.getList(pu, sid, parentid, Integer.parseInt(guid.get("id"))));
        pu.setServletName("NewstypesServlet");
        String typeStr = newstypesDAO.getSelectTreeDir("", parentid, Integer.parseInt(guid.get("id")));
        request.setAttribute("parentid", parentid);
        request.setAttribute("sid", sid);
        request.setAttribute("pu", pu);
        request.setAttribute("typeStr", typeStr);
        Forward.forward(request, response, "/back/news/newstypes.jsp");
    }

    protected void initManage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String parentid = request.getParameter("parentid");
        String sid = request.getParameter("sid");
        NewstypesDAO newstypesDAO = new NewstypesDAO();
        String id = request.getParameter("id");
        Map<String, String> newstypes = new HashMap<String, String>();
        newstypes.put("id", id);
        newstypes = newstypesDAO.getById(newstypes);
        if (null == newstypes.get("orders")) {
            newstypes.put("orders", String.valueOf(newstypesDAO.getMaxId(Integer.parseInt(guid.get("id")))));
        }
        request.setAttribute("newstypes", newstypes);
        request.setAttribute("parentid", parentid);
        request.setAttribute("sid", sid);

        String optionStr = newstypesDAO.getSelectTree("", parentid, "0", sid, Integer.parseInt(guid.get("id")));
        request.setAttribute("optionStr", optionStr);
//        List<Map<String, String>> list = newstypesDAO.getList(sid);
//        request.setAttribute("list", list);

        //附加部分wxs调整内容
        request.setAttribute("wx", wx);
        Forward.forward(request, response, "/back/news/newstypesmanage.jsp?page=" + request.getParameter("page"));
    }

    protected void manage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String parentid = request.getParameter("parentid");
        String sid = request.getParameter("sid");
        NewstypesDAO newstypesDAO = new NewstypesDAO();
        Map<String, String> newstypes = new HashMap<String, String>();
        newstypes.put("id", request.getParameter("id"));
        newstypes.put("name", request.getParameter("name"));
        newstypes.put("img", request.getParameter("img"));
        newstypes.put("parentid", parentid);
        newstypes.put("remark", request.getParameter("remark"));
        newstypes.put("times", WxMenuUtils.format.format(new Date()));
        newstypes.put("sid", sid);
        newstypes.put("uid", ((Map<String, String>) request.getSession().getAttribute("users")).get("id"));
        newstypes.put("orders", request.getParameter("orders"));
        if ("0".equals(newstypes.get("id"))) {
            newstypesDAO.add(newstypes, Integer.parseInt(guid.get("id")));
        } else {
            newstypesDAO.update(newstypes);
        }

        //附加，修改二维码水印
        WxsDAO wxsDAO = new WxsDAO();
        wx.put("id", request.getParameter("wxid"));
        wx.put("qrcodewarns", request.getParameter("qrcodewarns"));
        wx.put("messageqrcodewarns", request.getParameter("messageqrcodewarns"));
        wx.put("qrcodewarnsfontstyle", request.getParameter("qrcodewarnsfontstyle"));
        wx.put("qrcodewarnsfontcolor", request.getParameter("qrcodewarnsfontcolor"));
        wx.put("qrcodewarnsfontsize", request.getParameter("qrcodewarnsfontsize"));
        wxsDAO.update3(wx);

        //清理二维码特例
        if ("1".equals(sid)) {
            new SubscriberService().deleteqrcode(wx.get("id"), request);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/NewstypesServlet?method=getList&page=" + request.getParameter("page") + "&parentid=" + parentid + "&sid=" + sid);
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        new NewstypesService().delete(id, Integer.parseInt(guid.get("id")), request);
        response.sendRedirect(request.getContextPath() + "/servlet/NewstypesServlet?method=getList&parentid=" + request.getParameter("parentid") + "&sid=" + request.getParameter("sid"));
    }

    protected void delAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NewstypesDAO newstypesDAO = new NewstypesDAO();
        Map<String, String> newstypes = new HashMap<String, String>();
        String ids = request.getParameter("ids");
        String array[] = ids.split(",");
        for (String id : array) {
            if ("".equals(id)) {
                continue;
            }
            new NewstypesService().delete(id, Integer.parseInt(guid.get("id")), request);
        }
        response.sendRedirect(request.getContextPath() + "/servlet/NewstypesServlet?method=getList&parentid=" + request.getParameter("parentid") + "&sid=" + request.getParameter("sid"));
    }
}
