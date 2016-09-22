/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wap.wx.dao.ApiDAO;
import wap.wx.dao.AreaproxylogsDAO;
import wap.wx.dao.PowersDAO;
import wap.wx.dao.RolesDAO;
import wap.wx.dao.SubscriberDAO;
import wap.wx.dao.UsersDAO;
import wap.wx.dao.WxsDAO;
import wap.wx.menu.WxMenuUtils;
import wap.wx.service.PublicService;
import wap.wx.util.Forward;
import wap.wx.util.MD5;
import wap.wx.util.PageUtil;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "UsersServlet", urlPatterns = {"/UsersServlet"})
public class UsersServlet extends BaseServlet {

    private MD5 md5 = new MD5();
    private SubscriberDAO subscriberDAO = new SubscriberDAO();
    private AreaproxylogsDAO areaproxylogsDAO = new AreaproxylogsDAO();

    protected void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsersDAO usersDAO = new UsersDAO();
        Map<String, String> users = new HashMap<String, String>();
        users.put("username", null != request.getParameter("username") ? request.getParameter("username") : "admin");
        users.put("passwords", md5.getMD5of16(null != request.getParameter("passwords") ? request.getParameter("passwords") : "admin"));
        users = usersDAO.login(users);
        if (null != users) {
            request.getSession().setAttribute("users", users);
            // id,username,logintime,ip,remark logininfo
            Map<String, String> loginInfo = new HashMap<String, String>();
            loginInfo.put("username", users.get("username"));
            loginInfo.put("logintime", WxMenuUtils.format.format(new Date()));
            loginInfo.put("ip", !request.getRemoteAddr().equals("0:0:0:0:0:0:0:1") ? request.getRemoteAddr() : "127.0.0.1");
            loginInfo.put("remark", "");
            usersDAO.saveLoginInfo(loginInfo);
            int count = usersDAO.getLoginCounts(loginInfo);
            request.setAttribute("count", count);

            //api内填入绝对路径
            ApiDAO apiDAO = new ApiDAO();
            String realpath = request.getServletContext().getRealPath("/");
            realpath = realpath.substring(0, realpath.length() - 1);
            String ip = request.getRemoteAddr();
            Map<String, String> api = new HashMap<String, String>();
            String apikey = "realpath";
            api.put("apikey", apikey);
            api = apiDAO.get(api.get("apikey"), 0);
            if (null == api || !realpath.equals(api.get("apivalue"))) {
                api.put("apikey", apikey);
                api.put("apivalue", realpath);
                api.put("times", WxMenuUtils.format.format(new Date()));
                apiDAO.set(api, 0);
            }

            apikey = "ip";
            api.put("apikey", apikey);
            api = apiDAO.get(api.get("apikey"), 0);
            if (null == api || !ip.equals(api.get("apivalue"))) {
                api.put("apikey", apikey);
                api.put("apivalue", ip);
                api.put("times", WxMenuUtils.format.format(new Date()));
                apiDAO.set(api, 0);
            }

            //补充定时功能自动更新收完货
            List<Map<String, String>> wxsList = null;
            String result = PublicService.modOrderAuto(wxsList, subscriberDAO, areaproxylogsDAO);
            System.out.println("分销手动更新：" + result);
            //补充定时功能自动更新收完货完成
            request.getRequestDispatcher("/frames.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp?message=3");
        }
    }

    protected void getLeftPowers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String count = request.getParameter("count");//登录次数
        Map<Map<String, String>, List<Map<String, String>>> powersPS = new LinkedHashMap<Map<String, String>, List<Map<String, String>>>();
        Map<String, String> users = (Map<String, String>) request.getSession().getAttribute("users");
        Map<String, String> roles = new RolesDAO().getByUsers(users);
        Map<String, String> powers = new LinkedHashMap<String, String>();
        powers.put("id", "0");
        List<Map<String, String>> powersFirstList = new PowersDAO().getListByRP(roles, powers);
        for (Map<String, String> powersFirst : powersFirstList) {
            List<Map<String, String>> powersSubList = new PowersDAO().getListByRP(roles, powersFirst);
            powersPS.put(powersFirst, powersSubList);
        }
        request.setAttribute("count", count);
        request.setAttribute("powersPS", powersPS);
        Forward.forward(request, response, "/main/left.jsp");
    }

    protected void getMain(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("wx", wx);
        Forward.forward(request, response, "/main/main.jsp");
    }

    protected void back(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().removeAttribute("users");
        request.setAttribute("back", "1");
        Forward.forward(request, response, "/index.jsp");
    }

    protected void checkIdentity(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> users = new HashMap<String, String>();
        users.put("username", request.getParameter("username"));
        users.put("name", request.getParameter("name"));
        users.put("identity", request.getParameter("identity"));
        users = new UsersDAO().checkIdentity(users);
        if (null != users) {
            request.setAttribute("users", users);
            Forward.forward(request, response, "/safes/reset.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/safes/identity.jsp?message=1");
        }
    }

    protected void checkPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> users = new HashMap<String, String>();
        users.put("id", null != request.getParameter("id") ? request.getParameter("id") : "0");
        users.put("passwords", md5.getMD5of16(null != request.getParameter("password1") ? request.getParameter("password1") : "123456"));
        new UsersDAO().updatePassword(users);
        response.sendRedirect(request.getContextPath() + "/index.jsp?message=2");
    }

    protected void modPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Forward.forward(request, response, "/safes/modpasswords.jsp");
    }

    protected void checkModPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> users = new HashMap<String, String>();
        users.put("id", request.getParameter("id"));
        users.put("passwords", md5.getMD5of16(request.getParameter("password1")));
        new UsersDAO().updatePassword(users);
        request.getSession().removeAttribute("users");
        request.setAttribute("back", "2");
        Forward.forward(request, response, "/index.jsp");
    }

    protected void testUsername(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Map<String, String> users = new HashMap<String, String>();
        users.put("id", "0");//初始id
        users.put("username", request.getParameter("username"));
        users = new UsersDAO().getByUserName(users);
        if ((0 < Integer.parseInt(users.get("id"))) && (!request.getParameter("id").equals(users.get("id")))) {
            out.print(true);
        } else {
            out.print(false);
        }
    }

    protected void testIdentity(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Map<String, String> users = new HashMap<String, String>();
        users.put("id", "0");//初始id
        users.put("identity", request.getParameter("identity"));
        users = new UsersDAO().getByIdentity(users);
        if ((0 < Integer.parseInt(users.get("id"))) && (!request.getParameter("id").equals(users.get("id")))) {
            out.print(true);
        } else {
            out.print(false);
        }
    }

    protected void getList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String wxsid = request.getParameter("wxsid");
        wxsid = null == wxsid || "".equals(wxsid) ? "0" : wxsid;
        UsersDAO usersDAO = new UsersDAO();
        PageUtil<Map<String, String>> pu = new PageUtil<Map<String, String>>();
        pu.setPageSize(9);
        pu.setPage(null != request.getParameter("page") && !"null".equals(request.getParameter("page")) ? Integer.parseInt(request.getParameter("page")) : 1);
        pu.setMaxSize(usersDAO.getCount(wxsid));
        pu.setList(usersDAO.getList(pu, wxsid));
        pu.setServletName("UsersServlet");
        request.setAttribute("pu", pu);
        List<Map<String, String>> wxsList = new WxsDAO().getList();
        request.setAttribute("wxsList", wxsList);
        request.setAttribute("wxsid", wxsid);
        Forward.forward(request, response, "/users/users.jsp");
    }

    protected void initManage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Map<String, String> users = new HashMap<String, String>();
        users.put("id", id);
        users = new UsersDAO().getById(users);
        List<Map<String, String>> rolesList = new RolesDAO().getList();
        List<Map<String, String>> wxsList = new WxsDAO().getList();
        request.setAttribute("users", users);
        request.setAttribute("rolesList", rolesList);
        request.setAttribute("wxsList", wxsList);
        request.setAttribute("wxsidselect", request.getParameter("wxsidselect"));
        Forward.forward(request, response, "/users/usersmanage.jsp?page=" + request.getParameter("page"));
    }

    protected void manage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsersDAO usersDAO = new UsersDAO();
        Map<String, String> users = new HashMap<String, String>();
        users.put("id", request.getParameter("id"));
        users.put("username", request.getParameter("username"));
        users.put("name", request.getParameter("name"));
        users.put("identity", request.getParameter("identity"));
        users.put("sex", request.getParameter("sex"));
        users.put("ages", !"".equals(request.getParameter("ages")) ? request.getParameter("ages") : "0");
        users.put("birthday", !"".equals(request.getParameter("birthday")) ? request.getParameter("birthday") : WxMenuUtils.format.format(new Date()));
        users.put("img", request.getParameter("img"));
        users.put("rid", request.getParameter("rid"));
        users.put("remark", request.getParameter("remark"));
        users.put("wxsid", request.getParameter("wxsid"));
        if ("0".equals(users.get("id"))) {
            users.put("passwords", md5.getMD5of16("123456"));
            usersDAO.add(users);
        } else {
            usersDAO.update(users);
        }
        response.sendRedirect(request.getContextPath() + "/UsersServlet?method=getList&page=" + request.getParameter("page") + "&wxsid=" + request.getParameter("wxsidselect"));
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsersDAO usersDAO = new UsersDAO();
        Map<String, String> users = new HashMap<String, String>();
        users.put("id", request.getParameter("id"));
        users = usersDAO.getById(users);
        if (!"".equals(users.get("img")) && null != users.get("img")) {
            java.io.File oldFile = new java.io.File(this.getServletContext()
                    .getRealPath(users.get("img")));
            oldFile.delete();
        }
        usersDAO.delete(users);
        response.sendRedirect(request.getContextPath() + "/UsersServlet?method=getList");
    }

    protected void delAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsersDAO usersDAO = new UsersDAO();
        Map<String, String> users = new HashMap<String, String>();
        String ids = request.getParameter("ids");
        String array[] = ids.split(",");
        for (String id : array) {
            users.put("id", !"".equals(id) ? id : "0");
            users = usersDAO.getById(users);
            if (!"".equals(users.get("img")) && null != users.get("img")) {
                java.io.File oldFile = new java.io.File(this.getServletContext()
                        .getRealPath(users.get("img")));
                oldFile.delete();
            }
            usersDAO.delete(users);
        }
        response.sendRedirect(request.getContextPath() + "/UsersServlet?method=getList");
    }
}
