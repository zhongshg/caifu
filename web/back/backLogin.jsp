<%@ page import="job.tot.exception.*"%>
<%@ page import="job.tot.util.*" %>
<%@ page import="job.tot.bean.*" %>
<%@ page import="job.tot.dao.DaoFactory" %>
<%@ page import="job.tot.filter.IpFilter" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.net.*"%>
<%@ page import="job.tot.global.Sysconfig" %>
<%@ page import="job.tot.util.Forward" %>
<%@ page import="job.tot.dao.jdbc.UsersDao" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    request.setCharacterEncoding("utf-8");
    response.setContentType("text/html; charset=UTF-8");
    if (!IpFilter.filter(request.getRemoteAddr())) {
		response.sendRedirect("404.jsp");
    }
    String act = RequestUtil.getString(request, "act");
    if (act != null && act.equals("exit")) {
		session = request.getSession(false);//防止创建Session  
		if (session == null) {
		    return;
		}
		session.removeAttribute("user");
    }
    String id = RequestUtil.getString(request, "uid");
    if (id != null) {
		try {
		    String fieldArr = "id,name,pwd,age,viplvl,cardid,bankcard,phone,roleid,parentid";
		    UsersDao userDao = DaoFactory.getUserDao();
		    DataField dfUser = userDao.getById(id, fieldArr);
			userDao.remarkLogininfo(dfUser.getString("id"));
			if (dfUser.getString("roleid").equals("1")) {//管理员
			    session.setAttribute("admin_name", dfUser.getString("name"));
			    session.setAttribute("admin_id", dfUser.getString("id"));
			    session.setAttribute("admin_viplvl", dfUser.getString("viplvl"));
			    session.setAttribute("admin_roleid", dfUser.getString("roleid"));
			    session.setAttribute("admin_parentid", dfUser.getString("parentid"));
			    session.setAttribute("admin_bankcard", dfUser.getString("bankcard"));
			    response.sendRedirect("frames.jsp");
			} else {//普通用户
			    session.setAttribute("user_name", dfUser.getString("name"));
			    session.setAttribute("user_id", dfUser.getString("id"));
			    session.setAttribute("user_viplvl", dfUser.getString("viplvl"));
			    session.setAttribute("user_roleid", dfUser.getString("roleid"));
			    session.setAttribute("user_parentid", dfUser.getString("parentid"));
			    session.setAttribute("user_bankcard", dfUser.getString("bankcard"));
			    response.sendRedirect("../fore/frames.jsp");
			}
		} catch (Exception e) {
		    out.print("登录失败" + e.getMessage());
		}
    }
%>