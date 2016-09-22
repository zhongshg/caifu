<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../../inc/common.jsp"%>
<%@ include file="../session.jsp"%>
<%//---------------------------------------------------------
//if(!DaoFactory.getAdminDAO().ifHasPrivilege(site_role,"p02-1"))
//throw new NoPrivilegeException("没有操作权限");
//---------------------------------------------------------
    String act = RequestUtil.getString(request, "act");
    if (act == null) {
        act = "";
    }
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
            <title>属性修改</title>
            <link href="../style/global.css" rel="stylesheet" type="text/css" />
            <script type="text/javascript" src="../js/dhtmlgoodies_calendar.js"></script>
            <link href="../js/dhtmlgoodies_calendar.css" rel="stylesheet" type="text/css" />
            <link href="../../swfupload/swfupload.css" rel="stylesheet" type="text/css" />
            <script type="text/javascript" src="../js/jquery.js"></script>
    </head>
    <script>
        function check(obj) {
            obj.Submit.disabled = true;
            if (obj.svname.value == "") {
                alert('属性名称不能为空');
                obj.svname.focus();
                obj.svname.select();
                obj.Submit.disabled = false;
                return false;
            }
            return true;
        }
    </script>
    <body>
        <%@ include file="../top.jsp"%>
        <%
            int id = RequestUtil.getInt(request, "id");
            String sid = RequestUtil.getString(request, "sid");
            String signid = RequestUtil.getString(request, "signid");
            if (act.equals("domod")) {
                /**
                 * id,name,remark,parentid,orders,sid
                 */
                int maxId = DaoFactory.getPropertysDaoImplJDBC().getMaxIdInt() + 1;
                String svid = String.valueOf(maxId);
                if (!"0".equals(sid)) {
                    svid = sid + "-" + svid;
                }
                String svname = RequestUtil.getString(request, "svname");
                boolean bl = false;
                if (0 < id) {
                    bl = DaoFactory.getPropertysDaoImplJDBC().mod(id, svname);
                } else {
                    bl = DaoFactory.getPropertysDaoImplJDBC().add(svid, svname, sid, Integer.parseInt(signid), wx.get("id"));
                }
                if (bl) {
                    out.print("<br /><br /><p align=\"center\">操作成功!<br /><br /><br /><a href=\"javascript:history.go(-2)\">继续</a>&nbsp;&nbsp;<a href=\"index.jsp?sid=" + sid + "&signid=" + signid + "\">管理</a></p>");
                } else {
                    out.print("<script>alert('Error');history.back();</script>");
                }
            } else if (act.equals("mod")) {
                DataField d = DaoFactory.getPropertysDaoImplJDBC().getById(id);
        %>
        <br />
        <form id="addCategory" name="addCategory" method="post" action="?act=domod" onSubmit="return check(this);">
            <input type="hidden" name="id" value="<%=id%>"/>
            <input type="hidden" name="sid" value="<%=sid%>"/>
            <input type="hidden" name="signid" value="<%=signid%>"/>
            <table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="form_table">
                <tr>
                    <td height="25" colspan="10" bgcolor="#3872b2" class="subth"><strong><span class="style2">属性编辑</span></strong></td>
                </tr>

                <tr>
                    <td width="100" align="right" bgcolor="#FDFDFD" class="form_td">属性：</td>
                    <td width="100" bgcolor="#FDFDFD" colspan="9" class="form_td"><input name="svname" type="text" class="input_big" id="svname" maxlength="250" value="<%=null != d ? d.getFieldValue("svname") : ""%>" />
                    </td>
                </tr>
                <tr>
                    <td bgcolor="#FDFDFD" class="form_td">&nbsp;</td>
                    <td height="55" bgcolor="#FDFDFD" colspan="3" class="form_td"><input name="Submit" type="submit" class="btn_submit" value="确 定" />
                        &nbsp;
                        <input name="Submit2" type="button" class="btn_back" onclick="javascript:history.back();" value="取 消" /></td>
                </tr>
            </table>
        </form>
        <%
            }
        %>
    </body>
</html>
