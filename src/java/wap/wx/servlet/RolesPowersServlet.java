/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wap.wx.dao.RolesDAO;
import wap.wx.dao.RolesPowersDAO;
import wap.wx.util.Forward;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "RolesPowersServlet", urlPatterns = {"/servlet/RolesPowersServlet"})
public class RolesPowersServlet extends BaseServlet {

    protected void initManage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RolesPowersDAO rolesPowersDAO = new RolesPowersDAO();
        String id = request.getParameter("id");
        Map<String, String> roles = new LinkedHashMap<String, String>();
        roles.put("id", id);
        roles = new RolesDAO().getById(roles);
        List<Map<String, String>> powersBRList = rolesPowersDAO.getListByRoles(roles);
        Map<Map<String, String>, List<Map<String, String>>> sessionMap = new LinkedHashMap<Map<String, String>, List<Map<String, String>>>();
        Map<String, String> powers = new LinkedHashMap<String, String>();
        powers.put("id", "0");
        List<Map<String, String>> rolespowersList = rolesPowersDAO.getSubListByPowers(powers);
        for (Map<String, String> p : rolespowersList) {

            for (Map<String, String> powersBR : powersBRList) {
                if (p.get("id").equals(powersBR.get("id"))) {
                    p.put("selects", "1");
                    break;
                } else {
                    p.put("selects", "0");
                }
            }
            List<Map<String, String>> rolespowersSubList = new ArrayList<Map<String, String>>();
            List<Map<String, String>> rpSubList = rolesPowersDAO.getSubListByPowers(p);
            for (Map<String, String> sp : rpSubList) {

                for (Map<String, String> powersBR : powersBRList) {
                    if (sp.get("id").equals(powersBR.get("id"))) {
                        sp.put("selects", "1");
                        break;
                    } else {
                        sp.put("selects", "0");
                    }
                }
                rolespowersSubList.add(sp);
            }
            sessionMap.put(p, rolespowersSubList);
        }
        request.getSession().setAttribute("roles", roles);
        request.getSession().setAttribute("sessionMap", sessionMap);
        Forward.forward(request, response, "/users/rolespowers.jsp?page=" + request.getParameter("page"));
    }

    protected void manage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> roles = (Map<String, String>) request.getSession().getAttribute("roles");
        RolesPowersDAO rolesPowersDAO = new RolesPowersDAO();
        rolesPowersDAO.deleteByRoles(roles);
        Map<Map<String, String>, List<Map<String, String>>> sM = (Map<Map<String, String>, List<Map<String, String>>>) request.getSession().getAttribute("sessionMap");
        for (Map<String, String> p : sM.keySet()) {
            List<Map<String, String>> pSL = sM.get(p);
            for (Map<String, String> pS : pSL) {
                if ("1".equals(pS.get("selects"))) {
                    rolesPowersDAO.add(roles, pS);
                }
            }
            if ("1".equals(p.get("selects"))) {
                rolesPowersDAO.add(roles, p);
            }
        }
        response.sendRedirect(request.getContextPath() + "/servlet/RolesServlet?method=getList&page=" + request.getParameter("page"));
    }

    protected void change(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pid = request.getParameter("pid");
        String spid = request.getParameter("spid");
        Map<Map<String, String>, List<Map<String, String>>> sessionMap = new LinkedHashMap<Map<String, String>, List<Map<String, String>>>();
        Map<Map<String, String>, List<Map<String, String>>> sM = (Map<Map<String, String>, List<Map<String, String>>>) request.getSession().getAttribute("sessionMap");
        for (Map<String, String> p : sM.keySet()) {
            List<Map<String, String>> powersSubList = null;
            List<Map<String, String>> pSL = sM.get(p);
            if (pid.equals(p.get("id"))) {
                powersSubList = new ArrayList<Map<String, String>>();
                if ("".equals(spid)) {
                    if ("1".equals(p.get("selects"))) {
                        p.put("selects", "0");
                        for (Map<String, String> pS : pSL) {
                            pS.put("selects", "0");
                            powersSubList.add(pS);
                        }
                    } else {
                        p.put("selects", "1");
                        for (Map<String, String> pS : pSL) {
                            pS.put("selects", "1");
                            powersSubList.add(pS);
                        }
                    }
                } else {
                    boolean flag = true;
                    for (Map<String, String> pS : pSL) {
                        if (spid.equals(pS.get("id"))) {
                            if ("1".equals(pS.get("selects"))) {
                                pS.put("selects", "0");
                            } else {
                                pS.put("selects", "1");
                                p.put("selects", "1");
                            }
                        }
                        powersSubList.add(pS);
                    }
                    for (Map<String, String> pS : powersSubList) {
                        if ("1".equals(pS.get("selects"))) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        p.put("selects", "0");
                    }
                }
            } else {
                powersSubList = pSL;
            }
            sessionMap.put(p, powersSubList);
        }
        request.getSession().setAttribute("sessionMap", sessionMap);
        Forward.forward(request, response, "/users/rolespowers.jsp?page=" + request.getParameter("page"));
    }
}
