<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<%    String act = RequestUtil.getString(request, "act");
    String User = wx.get("id");
    if (act != null) {
        if (act.equals("bat")) {
            String[] objid = request.getParameterValues("objid");
            if (objid != null) {
                DaoFactory.getSlideDAO().batDel(objid);
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
            <title>幻灯片管理</title>
            <link href="style/global.css" rel="stylesheet" type="text/css" />
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
            <script src="js/common.js"></script>
            <script>
                function LinkUrlselectchange() {
                    document.getElementById("LinkUrl").value = document.getElementById("LinkUrl2").value;
                }
            </script>
    </head>
    <script>
        function goUrl(frm)
        {
            var gourl = "?";
            gourl += "page=" + (frm.page.value);
            var hid = parseInt(frm.hid.value);
            if (parseInt(frm.page.value) > hid || frm.page.value <= 0) {
                alert("1-" + hid);
                return false;
            }
            window.location.href(gourl);
        }
        function upimg() {
            var pt = window.showModalDialog('upimg.htm', '', 'dialogHeight:160px;dialogWidth:410px;help:no;status:no;scroll:no');
            if (pt != undefined)
                document.getElementById('Photo').value = pt;
        }
    </script>
    <body>
        <%@ include file="top.jsp"%><br />
        <%
            if (act != null && act.equals("add")) {
        %>
        <form id="addLevel" name="addLevel" method="post" action="?act=doadd" onSubmit="return check(this);">
            <table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border">
                <tr>
                    <td height="25" colspan="2" bgcolor="#3872b2"><div align="center" class="style1"><strong><span class="style2">
                                    添加幻灯片</span></div></td>
                </tr>
                <tr>
                    <td width="208" bgcolor="#FDFDFD"><div align="right">名称：</div></td>
                    <td width="794" bgcolor="#FDFDFD"><div align="left">
                            <input name="Title" type="text" id="Title" style="width:246px; border:1px solid #666666;" maxlength="250" />
                        </div></td>
                </tr>
                <tr>
                    <td width="208" bgcolor="#FDFDFD"><div align="right">网址：</div></td>
                    <td width="794" bgcolor="#FDFDFD"><div align="left">
                            <select name="LinkUrl2" size="1" id="LinkUrl2" style="width:246px; border:1px solid #666666;" onchange="javascript:LinkUrlselectchange();">
                                <option value="" >自定义网址</option>
                                <%
                                    List<DataField> productlist = (ArrayList) DaoFactory.getProductDAO().getList(null, wx.get("id"), null, 0, 0, -1, 0, 0, 0, 0, 0, null, null, null, null, null, null, null, 0, null, null, 0, 0, 1, null, null, null, 1, 0, 0, null, null, null, null, 1, null, 0, 0, null, null, 0, 0, "SaleNum asc", 0, 0);
                                    for (Iterator productiter = productlist.iterator(); productiter.hasNext();) {
                                        DataField product = (DataField) productiter.next();
                                %>
                                <option value="<%=request.getScheme() + "://" + request.getServerName() + request.getContextPath()%>/shop2/shop.jsp?id=<%=product.getInt("id")%>&act=detail&wx=<%=wx.get("id")%>">&nbsp;<%=product.getFieldValue("ProCode")%></option>
                                <%                                }
                                %>
                            </select><br/>
                            <input name="LinkUrl" type="text" id="LinkUrl" maxlength="250" style="width:396px; border:1px solid #666666;" />必须带http://
                        </div></td>
                </tr>
                <tr>
                    <td bgcolor="#FDFDFD"><div align="right">排序：</div></td>
                    <td bgcolor="#FDFDFD"><div align="left">
                            <input name="SortId" type="text" id="SortId" maxlength="250" style="width:246px; border:1px solid #666666;" value="0" />
                            越小越靠前
                        </div></td>
                </tr>
                <tr>
                    <td bgcolor="#FDFDFD"><div align="right">图像：</div></td>
                    <td bgcolor="#FDFDFD"><div align="left">
                            <input name="Photo" type="text" id="Photo" maxlength="250" style="width:246px; border:1px solid #666666;" />		
                            <input name="UpLoad" type="button" id="UpLoad" value="上传" onClick="upimg()" /></div></td>
                </tr>   
                <tr>
                    <td bgcolor="#FDFDFD"><div align="center"></div></td>
                    <td height="55" bgcolor="#FDFDFD"><input type="submit" name="Submit" value="确 定" /></td>
                </tr>
            </table>
        </form>
        <%}%>
        <%
            if (act != null && act.equals("doadd")) {
                String Title = RequestUtil.getString(request, "Title");
                String Photo = RequestUtil.getString(request, "Photo");
                String LinkUrl = RequestUtil.getString(request, "LinkUrl");
                int SortId = RequestUtil.getInt(request, "SortId");
                User = wx.get("id");
                boolean bl = DaoFactory.getSlideDAO().add(User, Title, Photo, LinkUrl, SortId);
                if (bl) {
                    out.print("<p align=\"center\">成功添加!<br /><a href=\"javascript:history.back()\">继续添加</a>&nbsp;&nbsp;<a href=\"slide.jsp\">返回管理</a></p>");
                } else {
                    out.print("<script>alert('Error');history.back();</script>");
                }
        %>
        <%}%>
        <%
            if (act != null && act.equals("mod")) {
                int sid = RequestUtil.getInt(request, "id");
                DataField df = DaoFactory.getSlideDAO().get(sid);
                if (df != null) {
        %>
        <form id="addLevel" name="addLevel" method="post" action="?act=domod&id=<%=sid%>" onSubmit="return check(this);">
            <table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border">
                <tr>
                    <td height="25" colspan="2" bgcolor="#3872b2"><div align="center" class="style1"><strong><span class="style2">
                                    修改</span></div></td>
                </tr>
                <tr>
                    <td width="208" bgcolor="#FDFDFD"><div align="right">名称：</div></td>
                    <td width="794" bgcolor="#FDFDFD"><div align="left">
                            <input name="Title" type="text" id="Title" style="width:246px; border:1px solid #666666;" maxlength="250" value="<%=df.getFieldValue("Title")%>" />
                        </div></td>
                </tr>
                <tr>
                    <td width="208" bgcolor="#FDFDFD"><div align="right">网址：</div></td>
                    <td width="794" bgcolor="#FDFDFD"><div align="left">
                            <select name="LinkUrl2" size="1" id="LinkUrl2" style="width:246px; border:1px solid #666666;" onchange="javascript:LinkUrlselectchange();">
                                <option value="" >自定义网址</option>
                                <%
                                    List<DataField> productlist = (ArrayList) DaoFactory.getProductDAO().getList(null, wx.get("id"), null, 0, 0, -1, 0, 0, 0, 0, 0, null, null, null, null, null, null, null, 0, null, null, 0, 0, 1, null, null, null, 1, 0, 0, null, null, null, null, 1, null, 0, 0, null, null, 0, 0, "SaleNum asc", 0, 0);
                                    for (Iterator productiter = productlist.iterator(); productiter.hasNext();) {
                                        DataField product = (DataField) productiter.next();
                                %>
                                <option value="<%=request.getScheme() + "://" + request.getServerName() + request.getContextPath()%>/shop2/shop.jsp?id=<%=product.getInt("id")%>&act=detail&wx=<%=wx.get("id")%>" <%=(request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/shop2/shop.jsp?id=" + product.getInt("id") + "&act=detail&wx=" + wx.get("id")).equals(df.getFieldValue("LinkUrl")) ? "selected='selected'" : ""%>>&nbsp;<%=product.getFieldValue("ProCode")%></option>
                                <%                                }
                                %>
                            </select><br/>
                            <input name="LinkUrl" type="text" id="LinkUrl" maxlength="250" style="width:396px; border:1px solid #666666;" value="<%=df.getFieldValue("LinkUrl")%>" />必须带http://
                        </div></td>
                </tr>
                <tr>
                    <td bgcolor="#FDFDFD"><div align="right">排序：</div></td>
                    <td bgcolor="#FDFDFD"><div align="left">
                            <input name="SortId" type="text" id="SortId" maxlength="250" style="width:246px; border:1px solid #666666;" value="<%=df.getFieldValue("SortId")%>" />
                            越小越靠前</div></td>
                </tr>
                <tr>
                    <td bgcolor="#FDFDFD"><div align="right">图像：</div></td>
                    <td bgcolor="#FDFDFD"><div align="left">
                            <input name="Photo" type="text" id="Photo" maxlength="250" style="width:246px; border:1px solid #666666;" value="<%=df.getFieldValue("Photo")%>" />		
                            <input name="UpLoad" type="button" id="UpLoad" value="上传" onClick="upimg()" /></div></td>
                </tr> 
                <tr>
                    <td bgcolor="#FDFDFD"><div align="center"></div></td>
                    <td height="55" bgcolor="#FDFDFD"><input type="submit" name="Submit4" value="确 定" /></td>
                </tr>
            </table>
        </form>
        <%}
            }%>
        <%
            if (act != null && act.equals("domod")) {
                int sid = RequestUtil.getInt(request, "id");
                String Title = RequestUtil.getString(request, "Title");
                String Photo = RequestUtil.getString(request, "Photo");
                String LinkUrl = RequestUtil.getString(request, "LinkUrl");
                int SortId = RequestUtil.getInt(request, "SortId");
                User = wx.get("id");

                boolean bl = DaoFactory.getSlideDAO().mod(sid, User, Title, Photo, LinkUrl, SortId);
                if (bl) {
                    out.print("<p align=\"center\">成功修改!<br /><a href=\"javascript:history.go(-2)\">成功修改</a>&nbsp;&nbsp;<a href=\"slide.jsp\">返回管理</a></p>");
                } else {
                    out.print("<script>alert('Error');history.back();</script>");
                }
        %>
        <%}%>
        <%
            int CurrentPage = RequestUtil.getInt(request, "page");
            if (CurrentPage < 1) {
                CurrentPage = 1;
            }
            int PageSize = 30;
            int TotalNum = 0;
            int PageNum = 0;
        %>
        <form action="?act=bat" method="post">
            <table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A3B2CC">
                <tr>
                    <td height="22" colspan="3" bgcolor="#FFFFFF">
                        <span class="style3">
                            幻灯片管理    </span>
                        &nbsp;&nbsp;<a href="?act=add">+添加</a>	</td>	
                </tr>
                <tr>
                    <td width="14%" height="22" bgcolor="#3872b2"><div align="center" class="style2">ID</div></td>
                    <td width="60%" height="22" bgcolor="#3872b2"><div align="center" class="style2">名称</div></td>
                    <td width="26%" bgcolor="#3872b2"><div align="center" class="style2">修改</div></td>
                </tr>
                <%
                    TotalNum = DaoFactory.getSlideDAO().getTotalCount(User);
                    PageNum = (TotalNum - 1 + PageSize) / PageSize;
                    ArrayList list = (ArrayList) DaoFactory.getSlideDAO().getList(User, CurrentPage, PageSize);
                    for (Iterator iter = list.iterator(); iter.hasNext();) {
                        DataField df = (DataField) iter.next();
                        String id = df.getFieldValue("id");
                %>
                <tr>
                    <td bgcolor="#FDFDFD">
                        <div align="center"><input name="objid" type="checkbox" value="<%=id%>" /><%=id%></div></td>
                    <td bgcolor="#FDFDFD"><div align="center"><%=df.getFieldValue("Title")%></div></td>
                    <td bgcolor="#FDFDFD"><div align="center"><a href="?act=mod&id=<%=id%>"><img src="images/icon/edit.gif" alt="Edit" width="15" height="15" border="0" /></a></div></td>
                </tr>
                <%
                    }
                %>
                <tr>
                    <td colspan="3" valign="top" bgcolor="#FDFDFD"><div align="center">
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
