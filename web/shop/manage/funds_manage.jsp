<%@page import="wap.wx.dao.UsersDAO"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="wap.wx.menu.WxMenuUtils"%>
<%@page import="wap.wx.util.ExportExcel"%>
<%@page import="wap.wx.dao.SubscriberDAO"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<%    int CurrentPage = RequestUtil.getInt(request, "page");
    int Type = RequestUtil.getInt(request, "Type");
    String FNo = RequestUtil.getString(request, "FNo");
    String UserId = RequestUtil.getString(request, "UserId");
    String subscriberid = RequestUtil.getString(request, "subscriberid");
    if (null != subscriberid && !"".equals(subscriberid)) {
        Map<String, String> subscriber = new SubscriberDAO().getById(wx.get("id"), subscriberid);
        UserId = subscriber.get("openid");
    }
    String FromDate = RequestUtil.getString(request, "FromDate");
    String ToDate = RequestUtil.getString(request, "ToDate");
    if (FNo == null) {
        FNo = "";
    }
    if (FromDate == null) {
        FromDate = "";
    }
    if (ToDate == null) {
        ToDate = "";
    }
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
            String actType = RequestUtil.getString(request, "actType");
            if (objid != null) {
                if (actType != null && actType.equals("del")) {
                    DaoFactory.getFundsDao().modisdel(objid);
//                    DaoFactory.getFundsDao().del(objid);
                    out.print("<p align=\"center\">批量删除成功!</p>");
                } else if (actType != null && actType.equals("success")) {
                    for (String fno : objid) {
                        DataField df = DaoFactory.getFundsDao().get(fno);
                        SubscriberDAO subscriberDAO = new SubscriberDAO();
                        Map<String, String> map = subscriberDAO.getByOpenid(wx.get("id"), df.getFieldValue("UserId"));
                        if ("2".equals(df.getFieldValue("Sts"))) {
                            out.print("<p align=\"center\">提现失败，本单已提现!</p>");
                            //} else if (Float.parseFloat(map.get("commission")) < df.getFloat("F_Price")) {
                            //   out.print("<p align=\"center\">提现失败，佣金不足!</p>");
                            //   DaoFactory.getFundsDao().success(fno, 3);
                        } else if (!subscriberDAO.updateTx(df.getFieldValue("UserId"), wx.get("id"), df.getFloat("F_Price"))) {
                            out.print("<p align=\"center\">提现失败，请稍后再试!</p>");
                        } else {
                            DaoFactory.getFundsDao().success(fno, 2);
                            DaoFactory.getFundsDao().modOp(fno, Integer.parseInt(((Map<String, String>) session.getAttribute("users")).get("id")));
                            out.print("<p align=\"center\">提现成功!</p>");
                        }
                    }

                }
            } else {
                out.print("<p align=\"center\">请选择要操作的选项!</p>");
            }
        }

        if ("export".equals(act)) {
            String sign = RequestUtil.getString(request, "sign");
            String value = RequestUtil.getString(request, "value");
            long nonce = System.currentTimeMillis();
            String path = request.getServletContext().getRealPath("/upload/" + nonce + ".xls");
            String webpath = "/upload/" + nonce + ".xls";
            Map<String, Object[][]> map = new LinkedHashMap<String, Object[][]>();
            ArrayList list;
            if ("1".equals(sign)) {
                String[] objid = value.split(",");
                list = new ArrayList();
                for (String id : objid) {
                    if ("".equals(id)) {
                        continue;
                    }
                    DataField temp = DaoFactory.getFundsDao().get(id);
                    list.add(temp);
                }
            } else {
                list = (ArrayList) DaoFactory.getFundsDao().getList(0, Type, FNo, UserId, null, 0, wx.get("id"), FromDate, ToDate, -1, -1);
            }
            Object[][] object = new Object[list.size() + 1][30];
            object[0][0] = "单号";
            object[0][1] = "金额";
            object[0][2] = "openid";
            object[0][3] = "昵称";
            object[0][4] = "提现原因";
            object[0][5] = "备注";
            object[0][6] = "日期";
            float price = 0.0f;
            int i = 1;
            for (Iterator iter = list.iterator(); iter.hasNext(); i++) {
                DataField df = (DataField) iter.next();
                String id = df.getFieldValue("F_No");
                // price=350.12f+0.02f;
                price = (float) (Math.round((price + df.getFloat("F_Price")) * 100)) / 100;
                object[i][0] = id;
                object[i][1] = df.getFieldValue("F_Price");
                object[i][2] = df.getFieldValue("UserId");
                object[i][3] = df.getFieldValue("UserName");
                object[i][4] = "1".equals(df.getFieldValue("Type")) ? "系统提现" : "人工提现";
                object[i][5] = df.getFieldValue("Descr");
                object[i][6] = df.getFieldValue("F_Date");
            }
            map.put("提现表", object);
            boolean flag = ExportExcel.createExcel(path, map);
            if (flag) {
                DaoFactory.getExportlogDaoImplJDBC().add("提现表", webpath, WxMenuUtils.format.format(new java.util.Date(nonce)), wx.get("id"), Integer.parseInt(((Map<String, String>) session.getAttribute("users")).get("id")));
                out.println("<script>alert('导出成功！');</script>");
            } else {
                out.println("<script>alert('导出失败！');</script>");
            }
        }
    }
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
            <title>提现申请</title>
            <link href="style/global.css" rel="stylesheet" type="text/css" />
            <script type = "text/javascript" src = "${pageContext.servletContext.contextPath}/css/time/jquery-1.8.0.min.js" ></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/css/time/jquery.easyui.min.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/wdp/WdatePicker.js" charset="utf-8" defer="defer"></script>
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
            var gourl = "?Type=<%=Type%>&FNo=<%=FNo%>&FromDate=<%=FromDate%>&ToDate=<%=ToDate%>&";
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
                    <td height="32">单号：
                        <input type="text" name="FNo" id="FNo" value="<%=FNo == null ? "" : FNo%>" />
                        会员ID：
                        <input type="text" name="subscriberid" id="subscriberid" value="<%=subscriberid == null ? "" : subscriberid%>" />
                        原因：
                        <select name="Type" id="Type">
                            <option value="0">...</option>
                            <option value="1"<%if (Type == 1) {
                                    out.print(" selected=\"selected\"");
                                }%>>系统提现</option>
                            <option value="2"<%if (Type == 2) {
                                    out.print(" selected=\"selected\"");
                                }%>>人工提现</option>

                        </select>
                        时间： <input class="easyui-datetimebox" type="text" name="FromDate" id="FromDate" value="<%=FromDate == null || FromDate.equals("null") ? "" : FromDate%>"  onfocus="WdatePicker({skin: 'whyGreen', dateFmt: 'yyyy-MM-dd'})" class="Wdate"/>-
                        <input class="easyui-datetimebox" type="text" name="ToDate" id="ToDate" value="<%=ToDate == null || ToDate.equals("null") ? "" : ToDate%>"  onfocus="WdatePicker({skin: 'whyGreen', dateFmt: 'yyyy-MM-dd'})" class="Wdate"/><br/>

                        <input type="submit" name="button" id="button" value="查询" /></td>
                </tr>
            </table>
        </form>

        <form action="?Type=<%=Type%>&FNo=<%=FNo%>&UserId=<%=UserId%>&act=bat" method="post">
            <table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A3B2CC">
                <tr>
                    <td height="22" colspan="12" bgcolor="#FFFFFF">
                        <span class="style3">提现申请</span>
                        &nbsp;&nbsp;<a href="funds_add.jsp">+添加</a>
                    </td>	
                </tr>
                <tr>
                    <td width="3%" height="22" bgcolor="#3872b2"><div align="center" class="style2"></div></td>
                    <td width="13%" height="22" bgcolor="#3872b2"><div align="center" class="style2">单号</div></td>
                    <td width="7%" align="center" bgcolor="#3872b2" class="style2">金额</td>
                    <td width="10%" align="center" bgcolor="#3872b2" class="style2">会员ID</td>
                    <td width="10%" align="center" bgcolor="#3872b2" class="style2">openid</td>
                    <!--<td width="9%" align="center" bgcolor="#3872b2" class="style2">支付宝</td>-->
                    <!--<td width="7%" align="center" bgcolor="#3872b2" class="style2">状态</td>-->
                    <td width="7%" align="center" bgcolor="#3872b2" class="style2">昵称</td>
                    <!--<td width="5%" align="center" bgcolor="#3872b2" class="style2">电话</td>-->
                    <td width="13%" align="center" bgcolor="#3872b2" class="style2">原因</td>
                    <td width="8%" bgcolor="#3872b2"><div align="center" class="style2">日期</div></td>
                    <td width="8%" bgcolor="#3872b2"><div align="center" class="style2">操作员</div></td>
                    <!--<td width="8%" align="center" bgcolor="#3872b2" class="style2">淘客</td>-->
                </tr>
                <%
                    float totalprice = 0.0f;
                    ArrayList list = (ArrayList) DaoFactory.getFundsDao().getList(0, Type, FNo, UserId, null, 0, wx.get("id"), FromDate, ToDate, -1, -1);
                    for (Iterator iter = list.iterator(); iter.hasNext();) {
                        DataField df = (DataField) iter.next();
                        totalprice = (float) (Math.round((totalprice + df.getFloat("F_Price")) * 100)) / 100;
                    }

                    TotalNum = DaoFactory.getFundsDao().getTotalNum(0, Type, FNo, UserId, null, 0, wx.get("id"), FromDate, ToDate);
                    PageNum = (TotalNum - 1 + PageSize) / PageSize;
                    list = (ArrayList) DaoFactory.getFundsDao().getList(0, Type, FNo, UserId, null, 0, wx.get("id"), FromDate, ToDate, CurrentPage, PageSize);
                    float price = 0.0f;
                    for (Iterator iter = list.iterator(); iter.hasNext();) {
                        DataField df = (DataField) iter.next();
                        String id = df.getFieldValue("F_No");
                        price = (float) (Math.round((price + df.getFloat("F_Price")) * 100)) / 100;

                        Map<String, String> op = new HashMap<String, String>();
                        op.put("id", df.getFieldValue("op"));
                        op = new UsersDAO().getById(op);

                        Map<String, String> subscriber = new SubscriberDAO().getByOpenid(wx.get("id"), df.getFieldValue("UserId"));
                %>
                <tr>
                    <td bgcolor="#FDFDFD">
                        <div align="center"><input name="objid" type="checkbox" value="<%=id%>" /></div></td>
                    <td align="center" bgcolor="#FDFDFD"><%=id%></td>
                    <td align="center" bgcolor="#FDFDFD"><%=new DecimalFormat("##0.00").format(df.getFloat("F_Price"))%></td>
                    <td align="center" bgcolor="#FDFDFD"><%=subscriber.get("id")%></td>
                    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("UserId")%></td>
                    <!--<td align="center" bgcolor="#FDFDFD">  <%=df.getFieldValue("Alipay")%></td>-->
                    <!--                    <td align="center" bgcolor="#FDFDFD">
                                            %
                                                switch (df.getInt("Sts")) {
                                                    case 1:
                                                        out.print("申请");
                                                        break;
                                                    case 2:
                                                        out.print("完成");
                                                        break;
                                                    case 3:
                                                        out.print("余额不足");
                                                        break;
                                                }
                                            %>
                                        </td>-->
                    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("UserName")%></td>
                    <!--<td align="center" bgcolor="#FDFDFD">%=df.getString("F_Phone")%></td-->
                    <td align="center" bgcolor="#FDFDFD"><%="1".equals(df.getFieldValue("Type")) ? "系统提现" : "人工提现"%></td>

                    <td bgcolor="#FDFDFD"><div align="center"><%=df.getFieldValue("F_Date")%></div></td>
                    <td bgcolor="#FDFDFD"><div align="center"><%=null != op.get("username") ? op.get("username") : ""%></div></td>

                </tr>
                <%
                    }
                %>
                <tr>
                    <td colspan="12" valign="top" bgcolor="#FDFDFD"><div align="center">总金额：￥<%=totalprice%>&nbsp;&nbsp;&nbsp;当前页金额：￥<%=price%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <select name="actType">
                                <!--<option value="success">提现</option>-->
                                <option value="del"><font color="red">删除</font> </option>
                            </select>
                            <input class="Button" type="button" name="chkall" value="全选" onclick="CheckAll(this.form)" />
                            <input class="Button" type="button" name="chksel" value="反选" onclick="ContraSel(this.form)" />
                            <input type="submit" name="Submit3" value="执行" onClick="return confirm('您确认执行此操作？');" />
                            <input type="button" name="Submit3" value="导出" onClick="exportExcel('1');" />
                            <input type="button" name="Submit3" value="全部导出" onClick="exportExcel('0');" />
                        </div></td>
                </tr>
            </table>
        </form>
        <form>
            <div align="center"><%=CurrentPage%>/<%=PageNum%>&nbsp;&nbsp;共:<%=TotalNum%>&nbsp;&nbsp;
                <%if (CurrentPage > 1) {%>
                <a href="?Type=<%=Type%>&FNo=<%=FNo%>&UserId=<%=UserId%>&FromDate=<%=FromDate%>&ToDate=<%=ToDate%>&page=<%=CurrentPage - 1%>">上一页</a>&nbsp;&nbsp;
                <%} else {%>
                上一页&nbsp;&nbsp;
                <%}%>
                <%if (CurrentPage >= PageNum) {%>
                下一页
                <%} else {%>
                <a href="?Type=<%=Type%>&FNo=<%=FNo%>&UserId=<%=UserId%>&FromDate=<%=FromDate%>&ToDate=<%=ToDate%>&page=<%=CurrentPage + 1%>">下一页</a>
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

        function exportExcel(sign) {
            var box = document.getElementsByName("objid");
            var value = "";
            for (var i = 0; i < box.length; i++) {
                if (box[i].checked) {
                    value = value + box[i].value + ",";
                }
            }
            if ("1" == sign && value == "") {
                alert("请选择要操作的选项!");
                return false;
            } else {
                if (confirm("确认导出？")) {
                    window.location.href = "?act=export&Type=<%=Type%>&FNo=<%=FNo%>&FromDate=<%=FromDate%>&ToDate=<%=ToDate%>&page=<%=CurrentPage%>&value=" + value + "&sign=" + sign;
                }
            }
        }
        </script>
    </body>
</html>
