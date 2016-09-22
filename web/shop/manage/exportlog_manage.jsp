<%@page import="wap.wx.menu.WxMenuUtils"%>
<%@page import="wap.wx.dao.UsersDAO"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<%    int CurrentPage = RequestUtil.getInt(request, "page");

    if (CurrentPage < 1) {
        CurrentPage = 1;
    }
    int PageSize = 20;
    int TotalNum = 0;
    int PageNum = 0;
    String act = RequestUtil.getString(request, "act");
    if (act != null) {
        if (act.equals("bat")) {
            String[] objid = request.getParameterValues("objid");
            if (objid != null) {
                DaoFactory.getExportlogDaoImplJDBC().batDel(objid);
                out.print("<p align=\"center\">批量删除成功!</p>");
            } else {
                out.print("<p align=\"center\">请选择要操作的选项!</p>");
            }
        }

    }
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
            <title>导出管理</title>
            <link href="style/global.css" rel="stylesheet" type="text/css" />
            <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/time/themes/icon.css"/>
            <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/time/demo.css"/>
            <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/time/themes/default/easyui.css"/>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/css/time/jquery-1.8.0.min.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/css/time/jquery.easyui.min.js"></script>
            <style type="text/css">
                <!--
                .style1 {color: #FFFFFF}
                .style2 {
                    color: #FFFFFF;
                    font-weight: bold;
                    font-size: 12px;
                }
                .style3 {
                    color: #FF9900;
                    font-weight: bold;
                }
                -->
            </style>
    </head>
    <script>
        function goUrl(frm)
        {
            var gourl = "？";
            gourl += "page=" + (frm.page.value);
            var hid = parseInt(frm.hid.value);
            if (parseInt(frm.page.value) > hid || frm.page.value <= 0) {
                alert("1-" + hid);
                return false;
            }
            window.location.href = gourl;
        }
    </script>
    <body>
        <%@ include file="top.jsp"%>
        <form action="?act=bat" method="post">
            <table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A3B2CC">
                <tr>
                    <td height="22" colspan="12" bgcolor="#FFFFFF">
                        <span class="style3">导出管理</span>
                        &nbsp;&nbsp;</td>	
                </tr>
                <tr>
                    <td width="3%" height="22" bgcolor="#3872b2"><div align="center" class="style2"></div></td>
                    <td width="13%" height="22" bgcolor="#3872b2"><div align="center" class="style2">id</div></td>
                    <td width="7%" align="center" bgcolor="#3872b2" class="style2">名称</td>
                    <td width="10%" align="center" bgcolor="#3872b2" class="style2">路径</td>
                    <td width="9%" align="center" bgcolor="#3872b2" class="style2">导出时间</td>
                    <td width="7%" align="center" bgcolor="#3872b2" class="style2">导出人</td>
                </tr>
                <%
                    TotalNum = DaoFactory.getExportlogDaoImplJDBC().getTotalNum(wx.get("id"));

                    PageNum = (TotalNum - 1 + PageSize) / PageSize;
                    ArrayList list = (ArrayList) DaoFactory.getExportlogDaoImplJDBC().getList(wx.get("id"), CurrentPage, PageSize);
                    float price = 0.0f;
                    for (Iterator iter = list.iterator(); iter.hasNext();) {
                        DataField df = (DataField) iter.next();
                        Map<String, String> user = new HashMap<String, String>();
                        user.put("id", df.getFieldValue("uid"));
                        user = new UsersDAO().getById(user);
                %>
                <tr>
                    <td bgcolor="#FDFDFD">
                        <div align="center"><input name="objid" type="checkbox" value="<%=df.getFieldValue("id")%>" /></div></td>
                    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("id")%></td>
                    <td align="center" bgcolor="#FDFDFD"><a href="<%=df.getFieldValue("path")%>" style="color: blue;"><%=df.getFieldValue("name")%></a></td>
                    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("path")%></td>
                    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("times")%></td>
                    <td align="center" bgcolor="#FDFDFD"><%=user.get("username")%></td>
                </tr>
                <%
                    }
                %>
                <tr>
                    <td colspan="12" valign="top" bgcolor="#FDFDFD"><div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <select name="actType">
                                <option value="bat">批量删除</option>
                            </select>
                            <input class="Button" type="button" name="chkall" value="全选" onclick="CheckAll(this.form)" />
                            <input class="Button" type="button" name="chksel" value="反选" onclick="ContraSel(this.form)" />
                            <input type="submit" name="Submit3" value="执行" onClick="return confirm('您确认执行此操作？');" />
                        </div></td>
                </tr>
            </table>
        </form>
        <form>
            <div align="center"><%=CurrentPage%>/<%=PageNum%>&nbsp;&nbsp;共:<%=TotalNum%>&nbsp;&nbsp;
                <%if (CurrentPage > 1) {%>
                <a href="?page=<%=CurrentPage - 1%>">上一页</a>&nbsp;&nbsp;
                <%} else {%>
                上一页&nbsp;&nbsp;
                <%}%>
                <%if (CurrentPage >= PageNum) {%>
                下一页
                <%} else {%>
                <a href="?page=<%=CurrentPage + 1%>">下一页</a>
                <%}%>
                跳转：
                <input type="hidden" name="hid" value="<%=PageNum%>" />
                <input name="page" type="text" size="2" />
                <input type="button" name="Button2" onclick="goUrl(this.form)" value="GO" style="font-size:12px " />
            </div>
        </form>
        <script language="javascript">
        function CheckAll(form) {
            for (var i = 0; i < form.elements.length; i++) {
                var e = form.elements[i];
                if (e.name == 'objid')
                    e.checked = true // form.chkall.checked;  
            }
        }

        function ContraSel(form) {
            for (var i = 0; i < form.elements.length; i++) {
                var e = form.elements[i];
                if (e.name == 'objid')
                    e.checked = !e.checked;
            }
        }
        </script>
    </body>
</html>
