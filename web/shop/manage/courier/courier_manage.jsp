<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../../inc/common.jsp"%>
<%@ include file="../session.jsp"%>
<%    int CurrentPage = RequestUtil.getInt(request, "page");
    int categoryid = RequestUtil.getInt(request, "categoryid");
    if (CurrentPage < 1) {
        CurrentPage = 1;
    }
    int PageSize = 30;
    int TotalNum = 0;
    int PageNum = 0;
    String act = RequestUtil.getString(request, "act");
    if (act != null) {
        if (act.equals("bat")) {
            String[] objid = request.getParameterValues("objid");
            if (objid != null) {
                DaoFactory.getCourierDaoImplJDBC().batDel(objid);
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
            <title>快递公司管理</title>
            <link href="../style/global.css" rel="stylesheet" type="text/css" />
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
            <script src="../js/common.js"></script>
    </head>
    <script>
        function goUrl(frm)
        {
            var gourl = "?categoryid=<%=categoryid%>&";
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
        <%@ include file="../top.jsp"%><br />
        <form action="?categoryid=<%=categoryid%>&act=bat" method="post">
            <table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A3B2CC">
                <tr>
                    <td height="22" colspan="4" bgcolor="#FFFFFF">
                        <span class="style3">快递公司管理</span>
                        &nbsp;&nbsp;<a href="courier_add.jsp">+添加</a>	
                    </td>	
                </tr>
                <tr>
                    <td width="7%" height="22" bgcolor="#3872b2"><div align="center" class="style2">ID</div></td>
                    <td width="65%" height="22" bgcolor="#3872b2"><div align="center" class="style2">公司名称</div></td>
                    <!--<td width="17%" bgcolor="#3872b2"><div align="center" class="style2">费用</div></td>-->
                    <td width="11%" bgcolor="#3872b2"><div align="center" class="style2">修改</div></td>
                </tr>
                <%

                    TotalNum = DaoFactory.getCourierDaoImplJDBC().getTotalCount();
                    PageNum = (TotalNum - 1 + PageSize) / PageSize;

                    ArrayList list = (ArrayList) DaoFactory.getCourierDaoImplJDBC().getList(CurrentPage, PageSize);
                    for (Iterator iter = list.iterator(); iter.hasNext();) {
                        DataField df = (DataField) iter.next();
                        String id = df.getFieldValue("id");
                %>
                <tr>
                    <td bgcolor="#FDFDFD">
                        <div align="center"><input name="objid" type="checkbox" value="<%=id%>" /><%=id%></div></td>
                    <td bgcolor="#FDFDFD"><div align="left"><%=df.getFieldValue("Title")%></div></td>
                    <!--<td bgcolor="#FDFDFD"><div align="center"><%=df.getFieldValue("Price")%></div></td>-->
                    <td bgcolor="#FDFDFD"><div align="center"><a href="courier_mod.jsp?id=<%=id%>"><img src="../images/icon/edit.gif" alt="Edit" width="15" height="15" border="0" /></a></div></td>
                </tr>
                <%
                    }
                %>
                <tr>
                    <td colspan="4" valign="top" bgcolor="#FDFDFD"><div align="center">
                            <select name="actType">
                                <option value="bat">批量删除</option>
                            </select>
                            <input class="Button" type="button" name="chkall" value="全选" onclick="CheckAll(this.form)" />
                            <input class="Button" type="button" name="chksel" value="反选" onclick="ContraSel(this.form)" />
                            <input type="submit" name="Submit3" value="执行" onClick="return ConfirmDel('您确认执行此操作？');" />
                        </div></td>
                </tr>
            </table>
        </form>
        <form>
            <div align="center"><%=CurrentPage%>/<%=PageNum%>&nbsp;&nbsp;共:<%=TotalNum%>&nbsp;&nbsp;
                <%if (CurrentPage > 1) {%>
                <a href="?categoryid=<%=categoryid%>&page=<%=CurrentPage - 1%>">上一页</a>&nbsp;&nbsp;
                <%} else {%>
                上一页&nbsp;&nbsp;
                <%}%>
                <%if (CurrentPage >= PageNum) {%>
                下一页
                <%} else {%>
                <a href="?categoryid=<%=categoryid%>&page=<%=CurrentPage + 1%>">下一页</a>
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
