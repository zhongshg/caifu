<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../../inc/common.jsp"%>
<%@ include file="../session.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
            <title>添加物流公司</title>
            <link href="../style/global.css" rel="stylesheet" type="text/css" />
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
            if (act != null && act.equals("do")) {
                String Title = RequestUtil.getString(request, "Title");
                String Content = RequestUtil.getString(request, "Content");
                Float Price = 0f;//RequestUtil.getFloat(request, "Price");
                boolean bl = DaoFactory.getCourierDaoImplJDBC().add(Title, Price, Content);
                if (bl) {
                    out.print("<p align=\"center\">成功添加!<br /><a href=\"javascript:history.back()\">继续添加</a>&nbsp;&nbsp;<a href=\"courier_manage.jsp\">物流管理</a></p>");
                } else {
                    out.print("<script>alert('添加失败!');history.back();</script>");
                }
            }
        %>
        <br />
        <form id="addCategory" name="addCategory" method="post" action="?act=do" onSubmit="return check(this);">
            <table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border">
                <tr>
                    <td height="25" colspan="2" bgcolor="#3872b2"><div align="center" class="style1"><strong><span class="style2">
                                    添加物流公司</span></strong></div></td>
                </tr>
                <tr>
                    <td width="199" bgcolor="#FDFDFD"><div align="right">公司名称：</div></td>
                    <td width="764" bgcolor="#FDFDFD"><div align="left">
                            <input name="Title" type="text" id="Title" style="width:246px; border:1px solid #666666;" maxlength="250" />
                        </div></td>
                </tr>
                <!--  <tr>
                    <td bgcolor="#FDFDFD"><div align="right">费用：</div></td>
                    <td bgcolor="#FDFDFD">
                       <input name="Price" type="text" id="Price" value="0.00" size="12" onclick="if (this.value == '0.00')
                                                this.value = '';" onblur="if (this.value == '')
                                                            this.value = '0.00';" />元
                    </td>
                  </tr>-->

                <!--  <tr>
                    <td bgcolor="#FDFDFD"><div align="right">内容：</div></td>
                    <td bgcolor="#FDFDFD">
                                <textarea name="Content" style="display:none; "></textarea>
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
                        </td>
                  </tr>-->
                <tr>
                    <td bgcolor="#FDFDFD"><div align="center"></div></td>
                    <td height="55" bgcolor="#FDFDFD"><input name="Submit" type="submit" class="btn_submit" value="确 定" />
                        &nbsp;
                        <input name="Submit2" type="button" class="btn_cancel" onclick="javascript:history.back();" value="取 消" /></td>
                </tr>
            </table>
        </form>
    </body>
</html>
