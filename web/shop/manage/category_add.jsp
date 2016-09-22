<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<%
    int parentid = RequestUtil.getInt(request, "parentid");
    String parentname = "";
    if (parentid == 0) {
        parentname = "根栏目";
    } else {
        parentname = DaoFactory.getCategoryDAO().getCategoryName(parentid);
    }

%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
            <title>添加栏目</title>
            <link href="style/global.css" rel="stylesheet" type="text/css" />
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
            <script src="js/common.js"></script>
    </head>
    <script>
        function check(obj) {
            with (obj) {
                if (Title.value == "") {
                    alert('请输入栏目名称');
                    Title.focus();
                    Title.select();
                    return false;
                }
                else {
                    return true;
                }
            }
        }
        function upimg() {
            var pt = window.showModalDialog('upimg.htm', '', 'dialogHeight:160px;dialogWidth:410px;help:no;status:no;scroll:no');
            if (pt != undefined)
                document.getElementById('Photo').value = pt;
        }
    </script>
    <body>
        <%@ include file="top.jsp"%>
        <%
            String act = RequestUtil.getString(request, "act");
            if (act != null && act.equals("do")) {
                String CateCode = RequestUtil.getString(request, "CateCode");
                String Title = RequestUtil.getString(request, "Title");
                String Demons = RequestUtil.getString(request, "Demons");
                Timestamp addtime = DateUtil.getCurrentGMTTimestamp();
                String Photo = RequestUtil.getString(request, "Photo");
                boolean bl = DaoFactory.getCategoryDAO().addCategory(wx.get("id"), CateCode, Title, Demons, parentid, Photo);
                if (bl) {
                    out.print("<p align=\"center\">成功添加栏目![" + Title + "]<br /><a href=\"javascript:history.back()\">继续添加</a>&nbsp;&nbsp;<a href=\"category_manage.jsp\">栏目管理</a></p>");
                } else {
                    out.print("<script>alert('Error');history.back();</script>");
                }
            }
        %>
        <br />
        <form id="addCategory" name="addCategory" method="post" action="?act=do" onSubmit="return check(this);">
            <table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border">
                <tr>
                    <td height="25" colspan="2" bgcolor="#3872b2"><div align="center" class="style1"><strong><span class="style2">
                                    添加栏目</span></div></td>
                </tr>
                <tr>
                    <td width="418" bgcolor="#FDFDFD"><div align="right">栏目编码：</div></td>
                    <td width="552" bgcolor="#FDFDFD"><div align="left">
                            <input name="CateCode" type="text" id="CateCode" maxlength="250" style="width:246px; border:1px solid #666666;" />
                        </div></td>
                </tr>
                <tr>
                    <td width="418" bgcolor="#FDFDFD"><div align="right">栏目名称：</div></td>
                    <td width="552" bgcolor="#FDFDFD"><div align="left">
                            <input name="Title" type="text" id="Title" maxlength="250" style="width:246px; border:1px solid #666666;" />
                        </div></td>
                </tr>
                <tr>
                    <td bgcolor="#FDFDFD"><div align="right">图像：</div></td>
                    <td bgcolor="#FDFDFD"><div align="left">
                            <input name="Photo" type="text" id="Photo" maxlength="250" style="width:246px; border:1px solid #666666;" />		
                            <input name="UpLoad" type="button" id="UpLoad" value="上传" onClick="upimg()" /></div></td>
                </tr>   
                <tr>
                    <td bgcolor="#FDFDFD"><div align="right">栏目描述：</div></td>
                    <td bgcolor="#FDFDFD"><div align="left">
                            <textarea name="Demons" cols="40" rows="3"></textarea>      
                        </div></td>
                </tr>
                <tr>
                    <td bgcolor="#FDFDFD"><div align="right">父栏目：</div></td>
                    <td bgcolor="#FDFDFD"><div align="left">
                            <input name="parentname" type="text" id="parentname" maxlength="250" value="<%=parentname%>" style="width:246px; border:1px solid #666666;" readonly />
                        </div></td>
                </tr>
                <tr>
                    <td colspan="2" bgcolor="#FDFDFD"><div align="center">   	
                            <input type="hidden" name="parentid" value="<%=parentid%>" /> 	
                            <input type="submit" name="Submit" value="确 定" />

                            <input type="reset" name="Submit2" value="取 消" />
                        </div></td>
                </tr>
            </table>
        </form>
    </body>
</html>
