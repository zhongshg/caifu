<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<%
    int id = RequestUtil.getInt(request, "id");
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
            <title>修改栏目</title>
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
                String Managers = RequestUtil.getString(request, "Managers");
                int SortNum = RequestUtil.getInt(request, "SortNum");
                int parentid = RequestUtil.getInt(request, "CategoryId");
                int IsOpen = RequestUtil.getInt(request, "IsOpen");
                String Photo = RequestUtil.getString(request, "Photo");
                boolean bl = DaoFactory.getCategoryDAO().modCategory(id, CateCode, Title, Demons, parentid, SortNum, IsOpen, Photo);
                if (bl) {
                    out.print("<p align=\"center\">成功修改栏目!<br /><a href=\"javascript:history.back()\">继续修改</a>&nbsp;&nbsp;<a href=\"category_manage.jsp\">栏目管理</a></p>");
                } else {
                    out.print("<script>alert('Error');history.back();</script>");
                }
            } else {
                DataField df = DaoFactory.getCategoryDAO().getCategory(id);
                if (df != null) {
                    String parentname = "";
                    int parid = Integer.parseInt(df.getFieldValue("ParentId"));
                    if (parid == 0) {
                        parentname = "根栏目";
                    } else {
                        parentname = DaoFactory.getCategoryDAO().getCategoryName(parid);
                    }
                    int isopen = Integer.parseInt(df.getFieldValue("IsOpen"));
        %>
        <br />
        <form id="addCategory" name="addCategory" method="post" action="?act=do&id=<%=id%>" onSubmit="return check(this);">
            <table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border">
                <tr>
                    <td height="25" colspan="2" bgcolor="#3872b2"><div align="center" class="style1"><strong><span class="style2">
                                    修改栏目</span></div></td>
                </tr>
                <tr>
                    <td width="418" bgcolor="#FDFDFD"><div align="right">栏目编码：</div></td>
                    <td width="552" bgcolor="#FDFDFD"><div align="left">
                            <input name="CateCode" type="text" id="CateCode" maxlength="250" value="<%=df.getFieldValue("CateCode")%>" style="width:246px; border:1px solid #666666;" />
                        </div></td>
                </tr>
                <tr>
                    <td width="418" bgcolor="#FDFDFD"><div align="right">栏目名称：</div></td>
                    <td width="552" bgcolor="#FDFDFD"><div align="left">
                            <input name="Title" type="text" id="Title" maxlength="250" value="<%=df.getFieldValue("Title")%>" style="width:246px; border:1px solid #666666;" />
                        </div></td>
                </tr>
                <tr>
                    <td bgcolor="#FDFDFD"><div align="right">图像：</div></td>
                    <td bgcolor="#FDFDFD"><div align="left">
                            <input name="Photo" type="text" id="Photo" maxlength="250" value="<%=df.getFieldValue("Photo")%>" style="width:246px; border:1px solid #666666;" />		
                            <input name="UpLoad" type="button" id="UpLoad" value="上传" onClick="upimg()" /></div></td>
                </tr>   
                <tr>
                    <td bgcolor="#FDFDFD"><div align="right">栏目描述：</div></td>
                    <td bgcolor="#FDFDFD"><div align="left">
                            <textarea name="Demons" cols="40" rows="3"><%=df.getFieldValue("Demons")%></textarea>
                        </div></td>
                </tr>
                <tr>
                    <td bgcolor="#FDFDFD"><div align="right">父栏目：</div></td>
                    <td bgcolor="#FDFDFD"><div align="left">
                            <select name="CategoryId" id="CategoryId">
                                <option value="<%=parid%>"><%=parentname%></option>
                            </select>
                            <a href="#" onclick="popupWindow('category_tree.jsp', 'category_tree', 400, 300, 1)">＋</a>
                        </div></td>
                </tr>
                <tr>
                    <td bgcolor="#FDFDFD"><div align="right">排序：</div></td>
                    <td bgcolor="#FDFDFD"><div align="left">
                            <input name='SortNum' type='text' style="width:50px; border:1px solid #666666;"  value="<%=df.getFieldValue("SortNumber")%>"  maxlength="250" /> 
                            (数字越小，排名越靠前)     
                        </div></td>
                </tr>
                <tr>
                    <td bgcolor="#FDFDFD"><div align="right">状态：</div></td>
                    <td bgcolor="#FDFDFD"><div align="left">
                            <select name="IsOpen" size="1">
                                <option value="0" <%if (isopen == 0) {
                        out.print("selected=\"selected\"");
                    }%>>关闭</option>
                                <option value="1" <%if (isopen == 1) {
                        out.print("selected=\"selected\"");
                    }%>>开启</option>
                            </select>
                        </div></td>
                </tr>
                <tr>
                    <td colspan="2" bgcolor="#FDFDFD"><div align="center">		 	
                            <input type="submit" name="Submit" value="确 定" />

                            <input type="button" name="Submit2" value="取 消" onclick="javascript:history.back();" />
                        </div></td>
                </tr>
            </table>
        </form>
        <%
                }
            }
        %>
    </body>
</html>
