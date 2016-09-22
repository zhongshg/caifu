<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../../inc/common.jsp"%>
<%@ include file="../session.jsp"%>
<%//---------------------------------------------------------
//    if (!DaoFactory.getAdminDAO().ifHasPrivilege(site_role, "p02-3")) {
//        throw new NoPrivilegeException("没有操作权限");
//    }
//---------------------------------------------------------
    String signid = RequestUtil.getString(request, "signid");
    String sid = null != RequestUtil.getString(request, "sid") ? RequestUtil.getString(request, "sid") : "0";
    String act = RequestUtil.getString(request, "act");
    if (act == null) {
        act = "";
    }

    if (act.equals("del")) {
        String svid = RequestUtil.getString(request, "svid");
        DataField df = DaoFactory.getPropertysDaoImplJDBC().get(svid);
        DaoFactory.getPropertysDaoImplJDBC().delList(df, signid, wx.get("id"));
    }
    if ("up".equals(act)) {
        DataField df = DaoFactory.getPropertysDaoImplJDBC().get(sid);
        sid = null != df ? df.getString("sid") : "0";
    }
    if ("down".equals(act)) {
        sid = RequestUtil.getString(request, "svid");
    }

    int CurrentPage = RequestUtil.getInt(request, "page");
    if (CurrentPage < 1) {
        CurrentPage = 1;
    }
    int PageSize = 30;
    int TotalNum = 0;
    int PageNum = 0;
    String urls = "&sid=" + sid + "&signid=" + signid;

//    String name = RequestUtil.getString(request, "name");
//    name = null != name ? new String(name.getBytes("iso-8859-1"), "utf-8") : null;
//    String identity = RequestUtil.getString(request, "identity");
//    String area = RequestUtil.getString(request, "area");
//    area = null != area ? new String(area.getBytes("iso-8859-1"), "utf-8") : null;
//    String datef = RequestUtil.getString(request, "datef");
//    String datet = RequestUtil.getString(request, "datet");
//    if (name != null) {
//        name = URLDecoder.decode(new String(name.getBytes("iso-8859-1"), "utf-8"), "utf-8");
//        urls += "&name=" + name;
//    }
//    if (identity != null) {
//        urls += "&identity=" + identity;
//    }
//    if (telephone != null) {
//        urls += "&telephone=" + telephone;
//    }

%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
            <title>属性管理</title>
            <link href="../style/global.css" rel="stylesheet" type="text/css" />
            <script type="text/javascript" src="../js/dhtmlgoodies_calendar.js"></script>
            <link href="../js/dhtmlgoodies_calendar.css" rel="stylesheet" type="text/css" />
    </head>
    <script>
        function goUrl(frm)
        {
            var gourl = "?<%=urls%>&";
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
        <table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" bgcolor="#A3B2CC" class="form_table">
            <tr>
                <td colspan="8" bgcolor="#FFFFFF" class="border_btm"><span class="list_title">属性管理</span>&nbsp;<a href="index_edit.jsp?act=mod&id=0&sid=<%=sid%>&signid=<%=signid%>">添加</a>&nbsp;<a href="?act=up&sid=<%=sid%>&signid=<%=signid%>">←</a></td></td>
            </tr>
        </table>
        <div class="br"></div>
        <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="form_table">
            <tr>
                <td height="22" bgcolor="#3872b2" class="list_th">ID</td>
                <td height="22" bgcolor="#3872b2" class="list_th">名称</td>
                <td bgcolor="#3872b2" class="list_th">操作</td>
            </tr>
            <%
                TotalNum = DaoFactory.getPropertysDaoImplJDBC().getTotalCount(sid, signid, wx.get("id"));
                PageNum = (TotalNum - 1 + PageSize) / PageSize;
                ArrayList list = (ArrayList) DaoFactory.getPropertysDaoImplJDBC().get_Limit(sid, signid, wx.get("id"), CurrentPage, PageSize);
                for (Iterator iter = list.iterator(); iter.hasNext();) {
                    DataField df = (DataField) iter.next();
                    String id = df.getFieldValue("id");
            %>
            <tr>
                <td width="59" bgcolor="#FDFDFD" class="list_td"><%=id%></td>
                <td width="333" bgcolor="#FDFDFD" class="list_td"><%=df.getFieldValue("svname")%></td>
                <td width="87" bgcolor="#FDFDFD" class="list_td">
                    <a href="index_edit.jsp?act=mod&id=<%=id%>&sid=<%=sid%>&signid=<%=signid%>"><img src="../images/icon/edit.gif" width="15" height="15" border="0" /></a> &nbsp; 
                    <a href="?act=del&svid=<%=df.getFieldValue("svid")%>&sid=<%=sid%>&signid=<%=signid%>" onclick="return confirm('确定删除？');"><img src="../images/icon/del.gif" width="15" height="15" border="0" /></a>
                    &nbsp; 
                    <a href="?act=down&svid=<%=df.getFieldValue("svid")%>&sid=<%=sid%>&signid=<%=signid%>">设置</a>
                </td>
            </tr>
            <%
                }
            %>
            <tr>
                <td colspan="8" valign="top" bgcolor="#FDFDFD" class="list_td"><form>
                        <div align="center"><%=CurrentPage%>/<%=PageNum%>&nbsp;&nbsp;
                            共
                            :<%=TotalNum%>&nbsp;&nbsp;
                            <%if (CurrentPage > 1) {%>
                            <a href="?page=<%=CurrentPage - 1%>&<%=urls%>">
                                上一页
                            </a>&nbsp;&nbsp;
                            <%} else {%>
                            上一页
                            &nbsp;&nbsp;
                            <%}%>
                            <%if (CurrentPage >= PageNum) {%>
                            下一页
                            <%} else {%>
                            <a href="?page=<%=CurrentPage + 1%>&<%=urls%>">下一页</a>
                            <%}%>
                            跳至
                            <input type="hidden" name="hid" value="<%=PageNum%>" />
                            <input name="page" type="text" size="2" />
                            页
                            <input type="button" name="Button2" onclick="goUrl(this.form)" value="确定" style="font-size:12px " />
                        </div>
                    </form></td>
            </tr>
        </table>
    </body>
</html>