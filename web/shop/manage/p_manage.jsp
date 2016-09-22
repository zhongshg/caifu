<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<%    int CurrentPage = RequestUtil.getInt(request, "page");
    int cid = RequestUtil.getInt(request, "cid");
    int sid = RequestUtil.getInt(request, "id");
    int sts = RequestUtil.getInt(request, "sts");
    int rem = RequestUtil.getInt(request, "rem");
    int isnew = RequestUtil.getInt(request, "isnew");
    if (CurrentPage < 1) {
        CurrentPage = 1;
    }
    int PageSize = 30;
    int TotalNum = 0;
    int PageNum = 0;
    boolean bl = false;
    String key = RequestUtil.getString(request, "key");
    if (key != null) {
        key = URLDecoder.decode(new String(key.getBytes("iso-8859-1"), "utf-8"), "utf-8");
    }
    String act = RequestUtil.getString(request, "act");
    if (act != null) {
        if (act.equals("bat")) {
            String[] objid = request.getParameterValues("objid");
            if (objid != null) {
                DaoFactory.getProductDAO().batDel(objid);
                out.print("<p align=\"center\">批量删除成功!</p>");
            } else {
                out.print("<p align=\"center\">请选择要操作的选项!</p>");
            }
        } else if (act.equals("shang")) {
            bl = DaoFactory.getProductDAO().modSaleNumadd(sid);
            if (bl) {
                response.sendRedirect("p_manage.jsp");

            }
        } else if (act.equals("xia")) {
            bl = DaoFactory.getProductDAO().modSaleNumdel(sid);
            if (bl) {
                response.sendRedirect("p_manage.jsp");

            }
        }
    }
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
            <title>商品管理</title>
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
            <script src="/js/jquery.js"></script>
    </head>
    <script>
        function goUrl(frm)
        {
            var gourl = "?cid=<%=cid%>&key=<%=(key == null ? "" : key)%>&sts=<%=sts%>&rem=<%=rem%>&isnew=<%=isnew%>&";
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
        <form action="" method="get">
            <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td height="32">关键字：
                        <input type="text" name="key" id="key" value="<%=key == null ? "" : key%>" />
                        <!--<input type="hidden" name="cid" va //lue="<%=cid%>" />-->
                        分类：<select name="cid">
                            <option value="0" <%=cid == 0 ? "selected='selected'" : ""%>>全部</option>
                            <%
                                Collection<DataField> cList = DaoFactory.getCategoryDAO().getListByParentid(wx.get("id"), 0);
                                for (DataField c : cList) {
                            %>
                            <option value="<%=c.getFieldValue("id")%>" <%=cid == c.getInt("id") ? "selected='selected'" : ""%>><%=c.getFieldValue("Title")%></option>
                            <%
                                }
                            %>
                        </select>
                        <input type="submit" name="button" id="button" value="查询" /></td>
                </tr>
            </table>
        </form>
        <form action="?cid=<%=cid%>&act=bat" method="post">
            <table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A3B2CC">
                <tr>
                    <td height="22" colspan="10" bgcolor="#FFFFFF">
                        <span class="style3">商品管理</span>
                        &nbsp;&nbsp;<a href="p_add.jsp">+添加</a>
                    </td>	
                </tr>
                <tr>
                    <td width="4%" height="22" bgcolor="#3872b2"><div align="center" class="style2"></div></td>
                    <td width="10%" height="22" bgcolor="#3872b2"><div align="left" class="style2">排序</div></td>
                    <td width="10%" height="22" bgcolor="#3872b2"><div align="left" class="style2">商品号</div></td>
                    <td width="10%" bgcolor="#3872b2">图片</td>
                    <td width="10%" height="22" bgcolor="#3872b2"><div align="left" class="style2">名称</div></td>
                    <td width="20%" height="22" bgcolor="#3872b2"><div align="left" class="style2">分类</div></td>
                    <td width="9%" height="22" bgcolor="#3872b2"><div align="left" class="style2">库存</div></td>
                    <td width="9%" align="center" bgcolor="#3872b2" class="style2">价格</td>
                    <td width="9%" bgcolor="#3872b2"><div align="center" class="style2">日期</div></td>
                    <td width="10%" bgcolor="#3872b2"><div align="center" class="style2">修改</div></td>
                </tr>
                <%

                    TotalNum = DaoFactory.getProductDAO().getTotalCount(null, wx.get("id"), null, 0, 0, cid, 0, 0, 0, 0, 0, null, null, null, null, null, key, null, 0, null, null, 0, 0, 0, null, null, null, 1, 0, 0, null, null, null, null, sts, isnew, 0, null, 0, 0, null, null, 0, 0);
                    PageNum = (TotalNum - 1 + PageSize) / PageSize;
                    ArrayList list = (ArrayList) DaoFactory.getProductDAO().getList(null, wx.get("id"), null, 0, 0, cid, 0, 0, 0, 0, 0, null, null, null, null, null, key, null, 0, null, null, 0, 0, 0, null, null, null, 1, 0, 0, null, null, null, null, sts, isnew, 0, 0, 0, null, 0, 0, null, null, 0, 0, "SaleNum desc", CurrentPage, PageSize);
                    for (Iterator iter = list.iterator(); iter.hasNext();) {
                        DataField df = (DataField) iter.next();
                        String id = df.getFieldValue("id");
                        DataField c = DaoFactory.getCategoryDAO().getCategory(df.getInt("Cid"));
                %>
                <tr>
                    <td bgcolor="#FDFDFD">
                        <div align="center"><input name="objid" type="checkbox" value="<%=id%>"/><%=id%>
                        </div></td>
                    <td bgcolor="#FDFDFD"><div align="left" id="SaleNum_<%=id%>"><%=df.getFieldValue("SaleNum")%>
                            <input type='button' onclick="javascipt:updateSaleNum('<%=id%>', '<%=df.getFieldValue("SaleNum")%>', '<%=wx.get("id")%>');" value="修改" style="color: blue;"/>
                        </div></td>
                    <td bgcolor="#FDFDFD"><div align="left"><%=df.getFieldValue("ProCode")%></div></td>
                    <td align="center" bgcolor="#FDFDFD"><img src="<%=df.getString("ViewImg")%>" width="66" height="59" border="0" /></td>
                    <td bgcolor="#FDFDFD"><div align="left"><%=df.getFieldValue("Title")%></div></td>
                    <td bgcolor="#FDFDFD"><div align="left"><%=null != c ? c.getFieldValue("Title") : ""%></div></td>
                    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("IsRem")%></td>
                    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("Price")%></td>
                     <!--<td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("SaleNum")%></td>-->
                  <!--   <td bgcolor="#FDFDFD"><div align="center"><a href="p_manage.jsp?act=shang&&id=<%=id%>">↑&nbsp;</a><a href="p_manage.jsp?act=xia&&id=<%=id%>">↓</a></div></td>-->
                    <td bgcolor="#FDFDFD"><div align="center"><%=df.getFieldValue("ModTime")%></div></td>
                    <td bgcolor="#FDFDFD"><div align="center"><a href="p_mod.jsp?id=<%=id%>"><img src="images/icon/edit.gif" alt="Edit" width="15" height="15" border="0" /></a></div></td>
                </tr>
                <%
                    }
                %>
                <tr>
                    <td colspan="10" valign="top" bgcolor="#FDFDFD"><div align="center">
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
                <a href="?cid=<%=cid%>&key=<%=(key == null ? "" : key)%>&sts=<%=sts%>&rem=<%=rem%>&isnew=<%=isnew%>&page=<%=CurrentPage - 1%>">上一页</a>&nbsp;&nbsp;
                <%} else {%>
                上一页&nbsp;&nbsp;
                <%}%>
                <%if (CurrentPage >= PageNum) {%>
                下一页
                <%} else {%>
                <a href="?cid=<%=cid%>&key=<%=(key == null ? "" : key)%>&sts=<%=sts%>&rem=<%=rem%>&isnew=<%=isnew%>&page=<%=CurrentPage + 1%>">下一页</a>
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
        function updateSaleNum(id, SaleNum, wxsid) {
            SaleNum = window.prompt("请输入排序号：", SaleNum);
            $.post("public_do.jsp", {"act": "updateSaleNum", "id": id, "SaleNum": SaleNum, "wxsid": wxsid}, function(result) {
                alert("修改成功！");
                $("#SaleNum_" + id).html(result);
            });
        }
        </script>
    </body>
</html>
