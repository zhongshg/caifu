<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../../inc/common.jsp"%>
<%@ include file="../session.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
            <title>快递公司修改</title>
            <link href="../style/global.css" rel="stylesheet" type="text/css" />
            <link href="../../edit/edit.css" rel="stylesheet" type="text/css" />
            <style type="text/css">
                <!--
                .style1 {color: #FFFFFF}
                .style2 {
                    color: #FFFFFF;
                    font-weight: bold;
                    font-size: 14px;
                }
                -->
            </style>
            <script src="../js/common.js"></script>
            <script type="text/javascript" src="/js/jquery.js"></script>
            <script type="text/javascript" src="/swfupload/swfupload.js"></script>
            <script type="text/javascript" src="../js/upinit_1.js"></script>           
            <script type="text/javascript" src="../js/handlers1_1.js"></script>
    </head>
    <script>
        function check(obj) {
            obj.Submit.disabled = true;
            if (obj.Title.value == "") {
                alert('公司名称不能为空');
                obj.Title.focus();
                obj.Title.select();
                obj.Submit.disabled = false;
                return false;
            }
            return true;
        }
        function upimg() {
            var pt = window.showModalDialog('upimg.htm', '', 'dialogHeight:160px;dialogWidth:410px;help:no;status:no;scroll:no');
            if (pt != undefined)
                document.getElementById('ViewImg').value = pt;
        }
    </script>
    <body>
        <%@ include file="../top.jsp"%>
        <%    String act = RequestUtil.getString(request, "act");
            int id = RequestUtil.getInt(request, "id");
            if (act != null && act.equals("do")) {
                String Title = RequestUtil.getString(request, "Title");
                String Content = RequestUtil.getString(request, "Content");
                float Price = 0f;//RequestUtil.getFloat(request, "Price");

                boolean bl = DaoFactory.getCourierDaoImplJDBC().mod(id, Title, Price, Content);
                if (bl) {
                    out.print("<p align=\"center\">成功修改!<br /><a href=\"javascript:history.back()\">继续修改</a>&nbsp;&nbsp;<a href=\"courier_manage.jsp\">物流管理</a></p>");
                } else {
                    out.print("<script>alert('修改失败!');history.back();</script>");
                }
            } else {

                DataField df = DaoFactory.getCourierDaoImplJDBC().get(id);
                if (df != null) {

        %>
        <br />
        <form id="addCategory" name="addCategory" method="post" action="?act=do&id=<%=id%>" onSubmit="return check(this);">
            <table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border">
                <tr>
                    <td height="25" colspan="2" bgcolor="#3872b2"><div align="center" class="style1"><strong><span class="style2">
                                    修改物流公司</span></strong></div></td>
                </tr>
                <tr>
                    <td width="172" bgcolor="#FDFDFD"><div align="right">公司名称：</div></td>
                    <td width="791" bgcolor="#FDFDFD"><div align="left">
                            <input name="Title" type="text" id="Title" style="width:246px; border:1px solid #666666;" maxlength="250" value="<%=df.getFieldValue("Title")%>" />
                        </div></td>
                </tr>
                <!--  <tr>
                    <td bgcolor="#FDFDFD"><div align="right">费用：</div></td>
                    <td bgcolor="#FDFDFD">
                     <input name="Price" type="text" id="Price" value="<%=df.getFloat("Price")%>" size="12" onclick="if (this.value == '0.00')
                                                this.value = '';" onblur="if (this.value == '')
                                                            this.value = '0.00';" />
                                        元
                    </td>
                  </tr>
                 
                  <tr>
                    <td bgcolor="#FDFDFD"><div align="right">内容：</div></td>
                    <td bgcolor="#FDFDFD"><div align="left">
                %
                    String content = df.getFieldValue("Content");
                    content = content.replace((char) (10), ' ');
                    content = content.replace((char) (13), ' ');
                    content = content.replace((char) (39), ' ');
                    content = content.replaceAll("<", "&lt;");
                    content = content.replaceAll(">", "&gt;");
                %>
                <textarea style="display:none;" name="Content">%=content%></textarea>
                <script charset="utf-8" src="/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script>
var editor1;
KindEditor.ready(function(K) {
  editor1 = K.create('textarea[name="Content"]', {
    uploadJson : '/kindeditor/jsp/upload_json.jsp',
    fileManagerJson : '/kindeditor/jsp/file_manager_json.jsp',
    allowFileManager : true,
    width:'100%',
    height:'300px'
  });
});
</script>
</div>
</td>
</tr>-->
                <tr>
                    <td bgcolor="#FDFDFD">&nbsp;</td>
                    <td height="55" bgcolor="#FDFDFD"><input name="Submit" type="submit" class="btn_submit" value="确 定" />
                        &nbsp;
                        <input name="Submit2" type="button" class="btn_cancel" onclick="javascript:history.back();" value="取 消" /></td>
                </tr>
            </table>
        </form>
        <%}
            }%>
    </body>
</html>
