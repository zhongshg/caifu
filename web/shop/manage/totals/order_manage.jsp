<%@page import="wap.wx.dao.SubscriberDAO"%>
<%@page import="wap.wx.menu.WxMenuUtils"%>
<%@page import="wap.wx.util.ExportExcel"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../../inc/common.jsp"%>
<%@ include file="../session.jsp"%>
<%    int CurrentPage = RequestUtil.getInt(request, "page");
    String wxqueryid = RequestUtil.getString(request, "wxqueryid");
    int FSts = RequestUtil.getInt(request, "FSts");
    int IsPay = RequestUtil.getInt(request, "IsPay");
    int State = RequestUtil.getInt(request, "State");
    String FNo = RequestUtil.getString(request, "FNo");
    String FromDate = RequestUtil.getString(request, "FromDate");
    String ToDate = RequestUtil.getString(request, "ToDate");
    String FromDateZhuan = FromDate;
    String ToDateZhuan = ToDate;
    String orderlatertime = RequestUtil.getString(request, "orderlatertime");
    String confirmlatertime = RequestUtil.getString(request, "confirmlatertime");
    String sendlatertime = RequestUtil.getString(request, "sendlatertime");
//    if (FromDate != null && !FromDate.equals("null") && !FromDate.equals("") && ToDate != null && !ToDate.equals("null") && !ToDate.equals("")) {
//        FromDateZhuan = DateUtil.timezhuan(FromDate);
//        ToDateZhuan = DateUtil.timezhuan(ToDate);
//   }

    if (FNo == null) {
        FNo = "";
    }
    if (FromDate == null) {
        FromDate = "";
    }
    if (ToDate == null) {
        ToDate = "";
    }
    if (orderlatertime == null) {
        orderlatertime = "";
    }
    if (confirmlatertime == null) {
        confirmlatertime = "";
    }
    if (sendlatertime == null) {
        sendlatertime = "";
    }
    if (CurrentPage < 1) {
        CurrentPage = 1;
    }
    int PageSize = 30;
    int TotalNum = 0;
    int PageNum = 0;
    String act = RequestUtil.getString(request, "act");
    if (act != null) {
        if (act.equals("confirmthings")) {
            String[] objid = request.getParameterValues("objid");
            if (objid != null) {
                for (int i = 0; i < objid.length; i++) {
                    DaoFactory.getOrderDAO().modSts(objid[i], 3);
                    DaoFactory.getOrderDAO().modConfirmtimes(objid[i], WxMenuUtils.format.format(new java.util.Date()));
                }
                out.print("<p align=\"center\">操作成功!</p>");
            } else {
                out.print("<p align=\"center\">请选择要操作的选项!</p>");
            }
        }
        if (act.equals("successthings")) {
            String[] objid = request.getParameterValues("objid");
            if (objid != null) {
                for (int i = 0; i < objid.length; i++) {
                    DaoFactory.getOrderDAO().modSts(objid[i], 5);
                    DaoFactory.getOrderDAO().modState(objid[i], 2);
                }
                out.print("<p align=\"center\">操作成功!</p>");
            } else {
                out.print("<p align=\"center\">请选择要操作的选项!</p>");
            }
        }
        if (act.equals("bat")) {
            String[] objid = request.getParameterValues("objid");
            if (objid != null) {
                DaoFactory.getOrderDAO().modisdel(objid);
//                DaoFactory.getOrderDAO().del(objid);
                out.print("<p align=\"center\">批量删除成功!</p>");
            } else {
                out.print("<p align=\"center\">请选择要操作的选项!</p>");
            }
        }

        if ("export".equals(act)) {
            String ids = RequestUtil.getString(request, "ids");
            String sign = RequestUtil.getString(request, "sign");
            long nonce = System.currentTimeMillis();
            String path = request.getServletContext().getRealPath("/upload/" + nonce + ".xls");
            String webpath = "/upload/" + nonce + ".xls";
            Map<String, Object[][]> map = new LinkedHashMap<String, Object[][]>();
            ArrayList list;
            if ("1".equals(sign)) {
                String[] objid = ids.split(",");
                list = new ArrayList();
                for (String id : objid) {
                    if ("".equals(id)) {
                        continue;
                    }
                    DataField temp = DaoFactory.getOrderDAO().get(id);
                    list.add(temp);
                }
            } else {
                list = (ArrayList) DaoFactory.getOrderDAO().getListDate(FSts, State, 1, 0, FNo, null, wxqueryid, IsPay, 0, FromDateZhuan, ToDateZhuan, orderlatertime, confirmlatertime, sendlatertime, 0, 0, 0);
            }
            Object[][] object = new Object[list.size() + 1][30];
            object[0][0] = "订单号";
            object[0][1] = "订单总额";
            object[0][2] = "openid";
            object[0][3] = "昵称";
            object[0][4] = "订单状态";
            object[0][5] = "支付状态";
            object[0][6] = "姓名";
            object[0][7] = "电话";
            object[0][8] = "微信";
            object[0][9] = "地址";
            object[0][10] = "日期";
            object[0][11] = "物流单号";
            object[0][12] = "物流名称";
            object[0][13] = "商户订单号";
            object[0][14] = "微信订单号";
            object[0][15] = "微信";
            float price = 0.0f;
            int i = 1;
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                DataField df = (DataField) iter.next();
                String id = df.getFieldValue("F_No");
                // price=350.12f+0.02f;
                price = (float) (Math.round((price + df.getFloat("SF_Price")) * 100)) / 100;
                String States = "";
                String Stss = "";
                String IsPays = "";
                if (df.getInt("State") == 1) {
                    States = "未完成";
                } else if (df.getInt("State") == 2) {
                    States = "已完成";
                }
                switch (df.getInt("Sts")) {
                    case 1:
                        Stss = "未发货";
                        break;
                    case 2:
                        Stss = "已发货";
                        break;
                    case 3:
                        Stss = "已收货";
                        break;
                    case 4:
                        Stss = "未评价";
                        break;
                    case 5:
                        Stss = "已完成";
                        break;
                    case 6:
                        Stss = "退货中";
                        break;
                    case 7:
                        Stss = "已退货";
                        break;
                    default:
                        Stss = "未付款";
                }
                if (df.getInt("IsPay") == 1) {
                    IsPays = "未支付";
                } else if (df.getInt("IsPay") == 2) {
                    IsPays = "已支付";
                } else if (df.getInt("IsPay") == 3) {
                    IsPays = "货到付款";
                }

                DataField province = DaoFactory.getAreaDaoImplJDBC().get(df.getFieldValue("provience"));
                DataField city = DaoFactory.getAreaDaoImplJDBC().get(df.getFieldValue("city"));
                DataField area = DaoFactory.getAreaDaoImplJDBC().get(df.getFieldValue("area"));

                Map<String, String> subscriber = new SubscriberDAO().getByOpenid(df.getFieldValue("SuserId"), df.getFieldValue("UserId"));
                Map<String, String> wxtemp = new HashMap<String, String>();
                wxtemp.put("id", df.getFieldValue("SuserId"));
                wxtemp = new WxsDAO().getById(wxtemp);
                object[i][0] = id;
                object[i][1] = df.getFieldValue("SF_Price");
                object[i][2] = df.getFieldValue("UserId");
                object[i][3] = null != subscriber ? subscriber.get("nickname") : "";
                object[i][4] = Stss;
                object[i][5] = IsPays;
                object[i][6] = df.getFieldValue("Name");
                object[i][7] = df.getFieldValue("Phone");
                object[i][8] = df.getFieldValue("Weixin");
                object[i][9] = (null != province ? province.getFieldValue("title") : "") + (null != city ? city.getFieldValue("title") : "") + (null != area ? area.getFieldValue("title") : "") + df.getFieldValue("Address");
                object[i][10] = df.getFieldValue("F_Date");
                object[i][11] = df.getFieldValue("ShipNo");
                object[i][12] = df.getFieldValue("ShipName");
                object[i][13] = df.getFieldValue("out_trade_no");
                object[i][14] = df.getFieldValue("transaction_id");
                object[i][15] = wxtemp.get("name");

                //创建明细表
                /*
                 List<DataField> basketlist = (ArrayList) DaoFactory.getBasketDAO().getListByOrderNo(df.getFieldValue("F_No"));
                 Object[][] subobject = new Object[(basketlist.size() + 4)][30];
                 subobject[0][0] = "订单信息";
                 subobject[0][1] = df.getFieldValue("F_No");
                 subobject[0][2] = "金额合计";
                 subobject[0][3] = df.getFieldValue("SF_Price");
                 subobject[0][4] = "订单状态";
                 subobject[0][5] = Stss;
                 subobject[0][6] = "支付状态";
                 subobject[0][7] = IsPays;
                 subobject[0][8] = "交易状态";
                 subobject[0][9] = States;
                 subobject[0][8] = "IP";
                 subobject[0][9] = df.getString("Ip");
                 subobject[0][8] = "下单时间";
                 subobject[0][9] = df.getString("F_Date");
                 subobject[1][0] = "联系人信息";
                 subobject[1][1] = df.getFieldValue("UserId");
                 subobject[1][2] = "姓名";
                 subobject[1][3] = df.getFieldValue("F_Name");
                 subobject[1][4] = "电话";
                 subobject[1][5] = df.getFieldValue("F_Mobile");
                 subobject[1][6] = "地址";
                 subobject[1][7] = df.getFieldValue("F_Address");
                 subobject[1][8] = "微信";
                 subobject[1][9] = df.getFieldValue("Weixin");
                 subobject[1][10] = "留言";
                 subobject[1][11] = df.getFieldValue("Remark");
                 subobject[2][0] = "物流信息";
                 subobject[2][1] = df.getFieldValue("ShipNo");
                 subobject[2][2] = "快递公司";
                 subobject[2][3] = df.getFieldValue("ShipName");
                 subobject[2][4] = "发货时间";
                 subobject[2][5] = df.getString("ShipTime");
                 //商品信息
                 subobject[3][0] = "商品编号";
                 subobject[3][1] = "商品名称";
                 subobject[3][2] = "商品单价";
                 subobject[3][3] = "商品数量";
                 subobject[3][4] = "商品金额";
                 int j = 4;
                 for (Iterator iterpro = basketlist.iterator(); iterpro.hasNext();) {
                 DataField dfSub = (DataField) iterpro.next();
                 subobject[j][0] = dfSub.getFieldValue("Pcode");
                 subobject[j][1] = dfSub.getFieldValue("Pname");
                 subobject[j][2] = dfSub.getFieldValue("Per_Price");
                 subobject[j][3] = dfSub.getFieldValue("Pnum");
                 subobject[j][4] = dfSub.getFieldValue("Tot_Price");
                 j++;
                 }
                 //                map.put("订单明细表" + df.getFieldValue("F_No"), subobject);
                 //明细表结束
                 */
                i++;
            }
            map.put("订单表", object);
            boolean flag = ExportExcel.createExcel(path, map);
            if (flag) {
                DaoFactory.getExportlogDaoImplJDBC().add("订单表", webpath, WxMenuUtils.format.format(new java.util.Date(nonce)), "0", Integer.parseInt(((Map<String, String>) session.getAttribute("users")).get("id")));
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
        <meta HTTP-EQUIV = "content-type" CONTENT = "text/html; charset=UTF-8" />
        <title> 订单管理 </title>
        <link href = "../style/global.css" rel = "stylesheet" type = "text/css" />
        <link rel = "stylesheet" type = "text/css" href = "${pageContext.servletContext.contextPath}/css/time/themes/icon.css" />
        <link rel = "stylesheet" type = "text/css" href = "${pageContext.servletContext.contextPath}/css/time/demo.css" />
        <link rel = "stylesheet" type = "text/css" href = "${pageContext.servletContext.contextPath}/css/time/themes/default/easyui.css" />
        <script type="text/javascript" src="/js/jquery.js"></script>
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
            var gourl = "?FSts=<%=FSts%>&IsPay=<%=IsPay%>&FNo=<%=FNo%>&State=<%=State%>&FromDate=<%=FromDate%>&ToDate=<%=ToDate%>&orderlatertime=<%=orderlatertime%>&sendlatertime=<%=sendlatertime%>&confirmlatertime=<%=confirmlatertime%>&wxqueryid=<%=wxqueryid%>&";
            gourl += "page=" + (frm.page.value);
            var hid = parseInt(frm.hid.value);
            if (parseInt(frm.page.value) > hid || frm.page.value <= 0) {
                alert("1-" + hid);
                return false;
            }
            window.location.href = gourl;
        }
        function exportExcel(sign) {
            var box = document.getElementsByName("objid");
            var ids = "";
            for (var i = 0; i < box.length; i++) {
                if (box[i].checked) {
                    ids = ids + box[i].value + ",";
                }
            }
            if ("1" == sign && ids == "") {
                alert("请选择要操作的选项!");
                return false;
            } else {
                if (confirm("确认导出？")) {
                    window.location.href = "?FSts=<%=FSts%>&IsPay=<%=IsPay%>&FNo=<%=FNo%>&State=<%=State%>&FromDate=<%=FromDate%>&ToDate=<%=ToDate%>&orderlatertime=<%=orderlatertime%>&sendlatertime=<%=sendlatertime%>&confirmlatertime=<%=confirmlatertime%>&act=export&ids=" + ids + "&sign=" + sign;
                }
            }
        }
    </script>
    <body>
        <%@ include file="../top.jsp"%>
        <form action="" method="get">
            <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td height="32">
                        微信：<select name="wxqueryid">
                            <option value="" <%="".equals(wxqueryid) ? "selected='selected'" : ""%>>全部</option>
                            <%
                                List<Map<String, String>> mapList = new WxsDAO().getList();
                                for (Map<String, String> map : mapList) {
                            %>
                            <option value="<%=map.get("id")%>" <%=map.get("id").equals(wxqueryid) ? "selected='selected'" : ""%>><%=map.get("name")%></option>
                            <%
                                }
                            %>
                        </select>
                        订单号：
                        <input type="text" name="FNo" id="FNo" value="<%=FNo == null ? "" : FNo%>" />
                        订单状态：
                        <select name="FSts" id="FSts">
                            <option value="0">...</option>
                            <option value="1"<%if (FSts == 1) {
                                    out.print(" selected=\"selected\"");
                                }%>>未发货</option>
                            <option value="2"<%if (FSts == 2) {
                                    out.print(" selected=\"selected\"");
                                }%>>已发货</option>
                            <option value="3"<%if (FSts == 3) {
                                    out.print(" selected=\"selected\"");
                                }%>>确认收货</option>
                            <!--                            <option value="4"<%if (FSts == 4) {
                                    out.print(" selected=\"selected\"");
                                }%>>未评价</option>-->
                            <option value="5"<%if (FSts == 5) {
                                    out.print(" selected=\"selected\"");
                                }%>>已完成</option>
                            <option value="6"<%if (FSts == 6) {
                                    out.print(" selected=\"selected\"");
                                }%>>退货中</option>
                            <option value="7"<%if (FSts == 7) {
                                    out.print(" selected=\"selected\"");
                                }%>>已退货</option>
                        </select>
                        支付状态：
                        <select name="IsPay" id="IsPay">
                            <option value="-1">...</option>
                            <option value="1"<%if (IsPay == 1) {
                                    out.print(" selected=\"selected\"");
                                }%>>未支付</option>
                            <option value="2"<%if (IsPay == 2) {
                                    out.print(" selected=\"selected\"");
                                }
                                    %>>已支付</option>
                            <!--      <option value="3"<%if (IsPay == 3) {
                                    out.print(" selected=\"selected\"");
                                }
                            %>>货到付款</option>     -->   
                        </select>
                        <!--                        交易状态：
                                                <select name="State" id="State">
                                                    <option value="0">...</option>
                                                    <option value="1"<%if (State == 1) {
                                                            out.print(" selected=\"selected\"");
                                                        }%>>未完成</option>
                                                    <option value="2"<%if (State == 2) {
                                                            out.print(" selected=\"selected\"");
                                                        }
                        %>>已完成</option>

            </select>-->
                        时间： <input class="easyui-datetimebox" type="text" name="FromDate" id="FromDate" value="<%=FromDate == null || FromDate.equals("null") ? "" : FromDate%>"  onfocus="WdatePicker({skin: 'whyGreen', dateFmt: 'yyyy-MM-dd'})" class="Wdate"/>-
                        <input class="easyui-datetimebox" type="text" name="ToDate" id="ToDate" value="<%=ToDate == null || ToDate.equals("null") ? "" : ToDate%>"  onfocus="WdatePicker({skin: 'whyGreen', dateFmt: 'yyyy-MM-dd'})" class="Wdate"/><br/>
                        订单超过<input type="number" name="orderlatertime" id="orderlatertime" value="<%=orderlatertime == null || orderlatertime.equals("null") ? "" : orderlatertime%>" />天
                        发货超过<input type="number" name="sendlatertime" id="sendlatertime" value="<%=sendlatertime == null || sendlatertime.equals("null") ? "" : sendlatertime%>" />天
                        确认收货超过<input type="number" name="confirmlatertime" id="confirmlatertime" value="<%=confirmlatertime == null || confirmlatertime.equals("null") ? "" : confirmlatertime%>" />天
                        <input type="submit" name="button" id="button" value="查询" /></td>
                </tr>
            </table>
        </form>

        <form action="?FSts=<%=FSts%>&IsPay=<%=IsPay%>&FNo=<%=FNo%>&State=<%=State%>" method="post">
            <table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A3B2CC">
                <tr>
                    <td height="22" colspan="15" bgcolor="#FFFFFF">
                        <span class="style3">订单管理</span>
                        &nbsp;&nbsp;</td>	
                </tr>
                <tr>
                    <td width="3%" height="22" bgcolor="#3872b2"><div align="center" class="style2"></div></td>
                    <td width="13%" height="22" bgcolor="#3872b2"><div align="center" class="style2">订单号</div></td>
                    <td width="7%" align="center" bgcolor="#3872b2" class="style2">订单总额</td>
                    <td width="6%" align="center" bgcolor="#3872b2" class="style2">openid</td>
                    <td width="5%" align="center" bgcolor="#3872b2" class="style2">昵称</td>
                    <td width="6%" align="center" bgcolor="#3872b2" class="style2">订单状态</td>
                    <td width="6%" align="center" bgcolor="#3872b2" class="style2">支付</td>
                    <td width="5%" align="center" bgcolor="#3872b2" class="style2">姓名</td>
                    <td width="13%" align="center" bgcolor="#3872b2" class="style2">地址</td>
                    <td width="7%" bgcolor="#3872b2"><div align="center" class="style2">日期</div></td>
                    <td width="5%" align="center" bgcolor="#3872b2" class="style2">物流名称</td>
                    <td width="13%" align="center" bgcolor="#3872b2" class="style2">物流单号</td>
                    <td width="3%" align="center" bgcolor="#3872b2" class="style2">首单</td>
                    <td width="10%" bgcolor="#3872b2"><div align="center" class="style2">微信</div></td>
                    <%--<td width="6%" align="center" bgcolor="#3872b2" class="style2">会员操作</td>--%>
                </tr>
                <%
                    TotalNum = DaoFactory.getOrderDAO().getTotalNumDate(FSts, State, 1, 0, FNo, null, wxqueryid, IsPay, 0, FromDateZhuan, ToDateZhuan, orderlatertime, confirmlatertime, sendlatertime, 0);

                    //取出所有首单
                    ArrayList shoudanList = (ArrayList) DaoFactory.getOrderDAO().getShoudanList(-1);

                    PageNum = (TotalNum - 1 + PageSize) / PageSize;
                    ArrayList list = (ArrayList) DaoFactory.getOrderDAO().getListDate(FSts, State, 1, 0, FNo, null, wxqueryid, IsPay, 0, FromDateZhuan, ToDateZhuan, orderlatertime, confirmlatertime, sendlatertime, 0, CurrentPage, PageSize);
                    float price = 0.0f;
                    for (Iterator iter = list.iterator(); iter.hasNext();) {
                        DataField df = (DataField) iter.next();
                        String id = df.getFieldValue("F_No");
                        // price=350.12f+0.02f;
                        price = (float) (Math.round((price + df.getFloat("SF_Price")) * 100)) / 100;
                        //System.out.println("id:"+id+"         price:"+df.getFloat("F_Price")+"       zprice:"+price);
                        Map<String, String> subscriber = new SubscriberDAO().getByOpenid(df.getFieldValue("SuserId"), df.getFieldValue("UserId"));

                        //判断是否首单
                        df.setField("isShoudan", "否", 0);
                        for (Iterator shoudaniter = shoudanList.iterator(); shoudaniter.hasNext();) {
                            DataField shoudan = (DataField) shoudaniter.next();
                            if (shoudan.getFieldValue("Id").equals(df.getFieldValue("Id"))) {
                                df.setField("isShoudan", "<font color='red'>是</font>", 0);
                                break;
                            }
                        }

                        DataField province = DaoFactory.getAreaDaoImplJDBC().get(df.getFieldValue("provience"));
                        DataField city = DaoFactory.getAreaDaoImplJDBC().get(df.getFieldValue("city"));
                        DataField area = DaoFactory.getAreaDaoImplJDBC().get(df.getFieldValue("area"));

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("id", df.getFieldValue("SuserId"));
                        map = new WxsDAO().getById(map);
                %>
                <tr>
                    <td bgcolor="#FDFDFD">
                        <div align="center"><input name="objid" type="checkbox" value="<%=id%>" /></div></td>
                    <td align="center" bgcolor="#FDFDFD"><%=id%></td>
                    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("SF_Price")%></td>
                    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("UserId")%></td>
                    <td align="center" bgcolor="#FDFDFD"><%=null != subscriber ? subscriber.get("nickname") : ""%></td>
                    <td align="center" bgcolor="#FDFDFD">
                        <%
                            switch (df.getInt("Sts")) {
                                case 1:
                                    out.print("<font color='red'>未发货</font>");
                                    break;
                                case 2:
                                    out.print("<font color='red'>已发货</font>");
                                    break;
                                case 3:
                                    out.print("<font color='red'>已收货</font>");
                                    break;
                                case 4:
                                    out.print("<font color='red'>未评价</font>");
                                    break;
                                case 5:
                                    out.print("已完成");
                                    break;
                                case 6:
                                    out.print("<font color='red'>申请退货</font>");
                                    break;
                                case 7:
                                    out.print("<font color='green'>已退货</font>");
                                    break;
                                default:
                                    out.print("<font color='red'>未付款</font>");
                            }
                        %>
                    </td>
                    <td align="center" bgcolor="#FDFDFD"><%if (df.getInt("IsPay") == 1) {%>未支付<%} else if (df.getInt("IsPay") == 2) {%>已支付<%} else if (df.getInt("IsPay") == 3) {%>货到付款<%}%></td>
                    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("F_Name")%><br /><%=df.getString("F_Mobile")%></td>
                    <td align="center" bgcolor="#FDFDFD"><%=(null != province ? province.getFieldValue("title") : "") + (null != city ? city.getFieldValue("title") : "") + (null != area ? area.getFieldValue("title") : "")%><br/><%=df.getString("Address")%></td>

                    <td bgcolor="#FDFDFD"><div align="center"><%=df.getFieldValue("F_Date")%></div></td>
                    <td bgcolor="#FDFDFD"><div align="center"><%=df.getFieldValue("ShipNo")%></div></td>
                    <td bgcolor="#FDFDFD"><div align="center"><%=df.getFieldValue("ShipName")%></div></td>
                    <td bgcolor="#FDFDFD"><div align="center"><%=df.getFieldValue("isShoudan")%></div></td>
                    <td bgcolor="#FDFDFD"><div align="center"><%=map.get("name")%></div></td>
                        <%--                    <td bgcolor="#FDFDFD">
                                                %
                                                    if ("1".equals(subscriber.get("isvip"))) {
                                                %>
                                                <a href="javascript:isvip('<%=subscriber.get("openid")%>',0);" style="text-decoration: none;"><font color='red'>取消会员</font></a>
                                                        %
                                                        } else if ("0".equals(subscriber.get("isvip"))) {
                                                        %>
                                                <a href="javascript:isvip('<%=subscriber.get("openid")%>',1);" style="text-decoration: none;"><font color='green'>成为会员</font></a>
                                                        %
                                                            }
                                                        %>
                                            </td>--%>
                </tr>
                <%
                    }
                %>
                <tr>
                    <td colspan="15" valign="top" bgcolor="#FDFDFD"><div align="center">金额：￥<%=price%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <%--                            <select name="act">
                                                            <option value="">请选择</option>
                                                            <option value="confirmthings">确认收货</option>
                                                            <option value="successthings">已完成</option>
                                                            <option value="bat">删除</option>
                                                        </select>--%>
                            <input class="Button" type="button" name="chkall" value="全选" onclick="CheckAll(this.form)" />
                            <input class="Button" type="button" name="chksel" value="反选" onclick="ContraSel(this.form)" />
                            <%--<input type="submit" name="Submit3" value="执行" onClick="return confirm('您确认执行此操作？');" />--%>
                            <input type="button" name="Submit3" value="导出" onClick="exportExcel('1');" />
                            <input type="button" name="Submit3" value="全部导出" onClick="exportExcel('0');" />
                        </div></td>
                </tr>
            </table>
        </form>
        <form>
            <div align="center"><%=CurrentPage%>/<%=PageNum%>&nbsp;&nbsp;共:<%=TotalNum%>&nbsp;&nbsp;
                <%if (CurrentPage > 1) {%>
                <a href="?FSts=<%=FSts%>&IsPay=<%=IsPay%>&FNo=<%=FNo%>&State=<%=State%>&wxqueryid=<%=wxqueryid%>&page=<%=CurrentPage - 1%>&FromDate=<%=FromDate%>&ToDate=<%=ToDate%>">上一页</a>&nbsp;&nbsp;
                <%} else {%>
                上一页&nbsp;&nbsp;
                <%}%>
                <%if (CurrentPage >= PageNum) {%>
                下一页
                <%} else {%>
                <a href="?FSts=<%=FSts%>&IsPay=<%=IsPay%>&FNo=<%=FNo%>&State=<%=State%>&wxqueryid=<%=wxqueryid%>&page=<%=CurrentPage + 1%>&FromDate=<%=FromDate%>&ToDate=<%=ToDate%>">下一页</a>
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
        function isvip(openid, isvip) {
            if (confirm("不可撤销，确认？")) {
                $.post("/servlet/SubscriberServlet?method=isvip", {"openid": openid, "wxsid": "<%=wx.get("id")%>", "isvip": isvip}, function(result) {
                    window.location.reload();
                });
            }
        }
        </script>
    </body>
</html>
