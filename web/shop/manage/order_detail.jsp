<%@page import="wap.wx.dao.UsersDAO"%>
<%@page import="wap.wx.menu.WxPayUtils"%>
<%@page import="wap.wx.menu.WxMenuUtils"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<%    String id = RequestUtil.getString(request, "id");
    DataField order = DaoFactory.getOrderDAO().get(id);
    ArrayList list = null;
    DataField df = null;
    DataField d = DaoFactory.getOrderDAO().get(id);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
            <title>订单明细</title>
            <link href="style/global.css" rel="stylesheet" type="text/css" />
            <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/time/themes/icon.css"/>
            <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/time/demo.css"/>
            <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/time/themes/default/easyui.css"/>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/css/time/jquery-1.8.0.min.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/css/time/jquery.easyui.min.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/wdp/WdatePicker.js" charset="utf-8" defer="defer"></script>
            <style type="text/css">
                <!--
                .style1 {color: #FFFFFF}
                .style2 {
                    color: #FFFFFF;
                    font-weight: bold;
                    font-size: 14px;
                }
                .tt{ color:#039; font-size:12px}
                -->
            </style>
            <script src="js/common.js"></script>
    </head>
    <body>
        <%@ include file="top.jsp"%>
        <br />
        <%
            String act = RequestUtil.getString(request, "act");
            if (act != null) {
                if (act.equals("modsts")) {
                    int sts = RequestUtil.getInt(request, "sts");
                    DaoFactory.getOrderDAO().modSts(id, sts);
                    DaoFactory.getOrderDAO().modConfirmtimes(id, WxMenuUtils.format.format(new java.util.Date()));

                    DaoFactory.getOrderDAO().modOp(id, Integer.parseInt(((Map<String, String>) session.getAttribute("users")).get("id")));

                    if (0 == sts) {
                        DaoFactory.getOrderDAO().modIsPay(id, 1);
                    }
                    if (1 == sts) {
                        DaoFactory.getOrderDAO().modIsPay(id, 2);
                    }
                    if (2 == sts) {
                        DaoFactory.getOrderDAO().modSendtimes(id, WxMenuUtils.format.format(new java.util.Date()));
                        //发货提醒
                        String content = "";
                        if ("".equals(order.getFieldValue("ShipName")) || "".equals(order.getFieldValue("ShipNo"))) {
                            content = "您的宝贝已经发货，请注意查收。订单号：" + order.getFieldValue("F_No") + "。";
                        } else {
                            content = "您的宝贝已经发货，请注意查收。订单号：" + order.getFieldValue("F_No") + "，物流名称：" + order.getFieldValue("ShipName") + "，物流单号：" + order.getFieldValue("ShipNo") + "。";
                        }
                        WxMenuUtils.sendCustomService(order.getFieldValue("UserId"), content, wx);
                    }
                    if (3 == sts) {
                        DaoFactory.getOrderDAO().modConfirmtimes(id, WxMenuUtils.format.format(new java.util.Date()));
                    }

                    if (5 == sts || 7 == sts) {
                        DaoFactory.getOrderDAO().modState(id, 2);
                    }
                } else if (act.equals("modfahuo")) {
                    String ShipNameSelect = RequestUtil.getString(request, "ShipNameSelect");
                    String ShipName = RequestUtil.getString(request, "ShipName");
                    if (!"".equals(ShipNameSelect)) {
                        ShipName = ShipNameSelect;
                    }
                    String ShipNo = RequestUtil.getString(request, "ShipNo");
                    int ShipType = RequestUtil.getInt(request, "ShipType");
                    Timestamp ShipTime = new Timestamp(WxMenuUtils.format.parse(RequestUtil.getString(request, "ShipTime")).getTime());
//                            DateUtil.string2Time1(DateUtil.timezhuan(RequestUtil.getString(request, "ShipTime")));

                    DaoFactory.getOrderDAO().modPost(id, ShipNo, ShipName, ShipType, 2, ShipTime);
                    //发货提醒
                    String content = "您的宝贝已经发货，请注意查收。订单号：" + id + "，物流名称：" + ShipName + "，物流单号：" + ShipNo + "。";
                    WxMenuUtils.sendCustomService(order.getFieldValue("UserId"), content, wx);
                } else if (act.equals("modstate")) {
                    int state = RequestUtil.getInt(request, "state");
                    DaoFactory.getOrderDAO().modState(id, state);
                } else if (act.equals("modendduring")) {
                    int endduring = RequestUtil.getInt(request, "endduring");
                    DaoFactory.getOrderDAO().modendduring(id, endduring);
                } else if (act.equals("modSF_Price")) {
                    float SF_Price = RequestUtil.getFloat(request, "SF_Price");
                    DaoFactory.getOrderDAO().modPrice(id, SF_Price);
                } else if (act.equals("backmoney")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("appid", wx.get("appid"));
                    map.put("mch_id", wx.get("mch_id"));
//        map.put("device_info", "");//否
                    map.put("nonce_str", String.valueOf(System.currentTimeMillis() / 1000));
                    //                       map.put("transaction_id", d.getFieldValue("transaction_id"));//微信订单号
                    map.put("out_trade_no", d.getFieldValue("out_trade_no"));//商户订单号
                    map.put("out_refund_no", "-" + d.getFieldValue("out_trade_no"));//商户退款单号
                    map.put("total_fee", String.valueOf((int) (d.getFloat("SF_Price") * 100)));//总金额
                    map.put("refund_fee", String.valueOf((int) (d.getFloat("SF_Price") * 100)));//退款金额
//        map.put("refund_fee_type", "CNY");//货币种类 否
                    map.put("op_user_id", wx.get("mch_id"));//操作员
                    map = new WxPayUtils().payrefund(request, map, wx);
                }
            }
            d = DaoFactory.getOrderDAO().get(id);
            if (d != null) {
                DataField province = DaoFactory.getAreaDaoImplJDBC().get(d.getFieldValue("provience"));
                DataField city = DaoFactory.getAreaDaoImplJDBC().get(d.getFieldValue("city"));
                DataField area = DaoFactory.getAreaDaoImplJDBC().get(d.getFieldValue("area"));

                Map<String, String> op = new HashMap<String, String>();
                op.put("id", d.getFieldValue("op"));
                op = new UsersDAO().getById(op);
        %>
        <table width="98%" height="190" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border">
            <tr>
                <td height="25" bgcolor="#3872b2"><div align="center" class="style1"><strong><span class="style2">
                                订单明细</span></strong></div></td>
            </tr>
            <tr>
                <td height="55" bgcolor="#FDFDFD"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="52%" valign="top"><p><strong>订单信息</strong>：<%=id%></p>
                                <p>商户订单号：<%=null != d.getFieldValue("out_trade_no") ? d.getFieldValue("out_trade_no") : ""%></p>
                                <p>微信订单号：<%=null != d.getFieldValue("transaction_id") ? d.getFieldValue("transaction_id") : ""%></p>
                                <p> <span class="tt">金额合计</span>：<%=d.getFloat("SF_Price")%>元 
                                    <form id="form3" name="form3" method="post" action="?act=modSF_Price&amp;id=<%=id%>">
                                        改价：
                                        <input type="text" name="SF_Price" id="SF_Price" value="<%=d.getString("SF_Price")%>" />元</p>
                                <input type="submit" name="button" id="button" value="修改" />
                                </form>
<!--                                    <span class="tt">折扣金额合计</span>：<%=d.getFloat("CF_Price")%>元 </p>
                                <p> <span class="tt">实收金额合计</span>：<%=d.getFloat("SF_Price")%>元
                                    <span class="tt">运费</span>：<%=d.getString("TSF_Price")%>元
                                    <span class="tt"> 订单金额（含运费）</span>：<%=d.getFloat("F_Price")%>元 -->
                                </p>
                                <p> 
                                    <span class="tt">订单状态</span>：
                                    <%
                                        switch (d.getInt("Sts")) {
                                            case 1:
                                                out.print("未发货");
                                                break;
                                            case 2:
                                                out.print("已发货");
                                                break;
                                            case 3:
                                                out.print("确认收货");
                                                break;
                                            case 4:
                                                out.print("未评价");
                                                break;
                                            case 5:
                                                out.print("已评价");
                                                break;
                                            case 6:
                                                out.print("退货中");
                                                break;
                                            case 7:
                                                out.print("已退货");
                                                break;
                                            default:
                                                out.print("未付款");
                                        }
                                    %>
                                    <span class="tt">支付状态</span>：<%if (d.getInt("IsPay") == 1) {%>未支付<%} else if (d.getInt("IsPay") == 2) {%>已支付<%} else if (d.getInt("IsPay") == 3) {%>货到付款<%}%> <span class="tt">交易状态</span>：<%if (d.getInt("State") == 1) {%>未完成<%} else {%>已完成<%}%></p>
                                <p><span class="tt">IP</span>：<%=d.getString("Ip")%> <span class="tt">下单日期</span>：<%=d.getString("F_Date")%></p></td>
                            <!--                            <td width="48%" valign="top"><p><strong>订单处理</strong>：</p>
                                                            <form id="form1" name="form1" method="post" action="?act=modsts&id=<%=id%>">
                                                                状态：
                                                                <select name="sts" id="sts"> 
                                                                    <option value="0" <%if (d.getInt("Sts") == 0) {%>selected<%}%>>未付款</option>
                                                                    <option value="1" <%if (d.getInt("Sts") == 1) {%>selected<%}%>>已付款</option>
                                                                    <option value="2" <%if (d.getInt("Sts") == 2) {%>selected<%}%>>已发货</option>
                                                                    <option value="3" <%if (d.getInt("Sts") == 3) {%>selected<%}%>>已收货</option>
                                                                    <option value="4" <%if (d.getInt("Sts") == 4) {%>selected<%}%>>未评价</option> 
                                                                    <option value="5" <%if (d.getInt("Sts") == 5) {%>selected<%}%>>已完成</option>
                                                                    <option value="6" <%if (d.getInt("Sts") == 6) {%>selected<%}%>>退货中</option>
                                                                    <option value="7" <%if (d.getInt("Sts") == 7) {%>selected<%}%>>已退货</option>                              
                                                                </select>
                                                                <input type="submit" name="button" id="button" value="修改" />
                            <%
                                if (d.getInt("Sts") == 7) {
                                    if (0 == d.getInt("PayType")) {
                            %>
                            <input type='button' onclick="javascript:if (confirm('确认退款？')) {
                                        window.location.href = '?act=backmoney&id=<%=id%>';
                                    }" value="确认退款"/>
                            <%
                            } else {
                            %>
                            <input type='button' value="已退款"/>
                            <%                                            }
                                }
                            %>
                        </form>
                        <p><strong>操作员</strong>：<%=null != op.get("username") ? op.get("username") : ""%></p>

                        <form id="form2" name="form2" method="post" action="?act=modstate&amp;id=<%=id%>">
<p><strong>交易处理</strong>：</p> 状态：
<select name="state" id="state">                               
<option value="1" <%if (d.getInt("State") == 1) {%>selected<%}%>>未完成</option>
<option value="2" <%if (d.getInt("State") == 2) {%>selected<%}%>>已完成</option>                                                               
</select>
<input type="submit" name="button" id="button" value="修改" />
</form>
                        <form id="form3" name="form3" method="post" action="?act=modendduring&amp;id=<%=id%>">
<p><strong>允许退货</strong>：</p> 期限：
<input type="text" name="endduring" id="endduring" value="<%=d.getString("endduring")%>" />天</p>
<input type="submit" name="button" id="button" value="修改" />
</form>
                    </td>
                </tr>-->
                            <tr>
                                <td valign="top"><p><strong>收货信息</strong>：</p>
                                    <p><span class="tt">姓名</span>：<%=d.getString("F_Name")%> 
                                        <!--span class="tt">手机</span>：<%=d.getString("F_Mobile")%> -->
                                        <span class="tt"> 电话</span>：<%=d.getString("F_Tel")%></p>
                                    <p><span class="tt">地址</span>： <%=(null != province ? province.getFieldValue("title") : "") + (null != city ? city.getFieldValue("title") : "") + (null != area ? area.getFieldValue("title") : "") + d.getString("Address")%></p> 
                                    <p> <span class="tt">微信</span>：<%=d.getString("Weixin")%> </p>
                                    <p> <span class="tt">留言</span>：<%=d.getString("Remark")%> </p>
                                    <p><strong>物流信息</strong>：</p>
                                    <p> <span class="tt">快递公司</span>：<%=d.getString("ShipName")%></p>
                                    <p><span class="tt">物流单号</span>：<%=d.getString("ShipNo")%></p>
                                    <p><span class="tt">发货时间</span>：<%=null != d.getString("ShipTime") ? d.getString("ShipTime") : ""%></p>
                                </td>
                                <!--                            <td valign="top">
                                
                                                                <form id="form1" name="form1" method="post" action="?act=modfahuo&id=<%=id%>">
                                                                    <p><strong>填写物流信息</strong>：</p>
                                                                    <p>
                                                                        物流单号：
                                                                        <input type="text" name="ShipNo" id="ShipNo" value="<%=d.getString("ShipNo")%>" />
                                                                    </p>
                                                                    <p>
                                                                        快递公司：
                                                                        <select name="ShipNameSelect" id="ShipNameSelect"> 
                                                                            <option value="">自定义</option>
                                <%
                                    ArrayList wuliuList = (ArrayList) DaoFactory.getCourierDaoImplJDBC().getList(-1, -1);
                                    for (Iterator iter = wuliuList.iterator(); iter.hasNext();) {
                                        DataField wuliu = (DataField) iter.next();
                                %>
                                <option value="<%=wuliu.getFieldValue("Title")%>"><%=wuliu.getFieldValue("Title")%></option>
                                <%
                                    }
                                %>
                            </select>
                            <input type="text" name="ShipName" id="ShipName" value="<%=d.getString("ShipName")%>" /></p>
                        <input type="hidden" name="PostType" value="1"/>
                                                            <p>    
                                                                发货类型：
                                                                <select name="PostType" id="PostType">
                                                                    <option value="1" <%if (d.getInt("PostType") == 1) {%>selected<%}%>>工作日、双休日与假日均可送货</option>
                                                                    <option value="2" <%if (d.getInt("PostType") == 2) {%>selected<%}%>>只工作日送货</option>
                                                                    <option value="3" <%if (d.getInt("PostType") == 3) {%>selected<%}%>>只双休日、节假日送货</option>    
                                                                </select></p>

                        <p>
                            发货时间： <input class="easyui-datetimebox" type="text" name="ShipTime" id="ShipTime" value="<%=null != d.getString("ShipTime") ? d.getString("ShipTime") : WxMenuUtils.format.format(new java.util.Date())%>" onfocus="WdatePicker({skin: 'whyGreen', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" class="Wdate"/>
                        </p>
                        <p>
                            <input type="submit" name="button2" id="button2" value="发货" onclick="return confirm('确定发货吗？');" />
                        </p>
                    </form></td>-->
                            </tr>
                    </table></td>
            </tr>
            <tr>
                <td height="55" bgcolor="#FDFDFD">

                    <table width="100%" border="0"  cellspacing="1" bgcolor="#eeeeee">      

                        <tr>
                            <td height="32" width="12%" align="center" bgcolor="#F9F9F9" class="settle_b_table_first"><strong>产品图</strong></td>
                           <td width="12%" align="center" bgcolor="#F9F9F9"><strong>商品编号</strong></td>
                            <td width="12%" bgcolor="#F9F9F9"><strong>商品信息</strong></td>
                            <td width="12%" bgcolor="#F9F9F9"><strong>属性</strong></td>
                            <td width="12%" align="center" bgcolor="#F9F9F9"><strong>单价(元)</strong></td>
                            <td width="52%" align="center" bgcolor="#F9F9F9"><strong>数量</strong></td>
                            <td width="12%" align="center" bgcolor="#F9F9F9"><strong>金额</strong></td>
                        </tr>
                        <%
                            list = (ArrayList) DaoFactory.getBasketDAO().getListByOrderNoBak(id);
                            for (Iterator iter = list.iterator(); iter.hasNext();) {
                                df = (DataField) iter.next();
                        %>
                        <tr>
                            <td width="12%" height="60" align="center" bgcolor="#FFFFFF" class="settle_b_table_first"><a href="../p_show.jsp?id=" target="_blank"><img src="<%=df.getString("ViewImg")%>" width="56" height="43" /></a><strong name="26789d21612b4edbbc55ac87268404c8" class="orange"></strong></td>
                             <td width="12%" align="center" bgcolor="#FFFFFF"><%=df.getString("ProCode")%></td>
                            <td width="12%" bgcolor="#FFFFFF"><span class="settle_b_table_first"><%=df.getString("Pname")%> </span></td>
                            <td width="12%" align="center" bgcolor="#FFFFFF">
                                <%
                                    String propertys = df.getFieldValue("propertys").trim();
                                    if (!"".equals(propertys)) {
                                        String propertysArr[] = propertys.split(" ");
                                        for (int i = 0; i < propertysArr.length; i++) {
                                            if ("".equals(propertysArr[i])) {
                                                continue;
                                            }
                                            DataField property = DaoFactory.getPropertysDaoImplJDBC().get(propertysArr[i].split("-")[0]);
                                            DataField propertySub = DaoFactory.getPropertysDaoImplJDBC().get(propertysArr[i]);
                                            if (null == property || null == propertySub) {
                                                continue;
                                            }
                                %>
                                <%=property.getFieldValue("svname")%>:<%=propertySub.getFieldValue("svname")%>&nbsp;
                                <%
                                        }
                                    }
                                %>
                            </td>
                            <td width="12%" align="center" bgcolor="#FFFFFF">
                                <%=df.getFloat("Per_Price")%></td>
                            <td width="52%" align="center" bgcolor="#FFFFFF"><%=df.getInt("Pnum")%></td>
                            <td width="12%" align="center" bgcolor="#FFFFFF"><font class="subtotalPrice">￥<%=df.getFloat("Tot_Price")%></font></td>
                        </tr>
                        <%}%>




                    </table></td>
            </tr>
            <tr>
                <td height="55" align="center" bgcolor="#FDFDFD">&nbsp;
                    <input name="Submit2" type="button" class="btn_cancel" onclick="javascript:history.back();" value="返 回" />
                    <input name="Submit3" type="button" class="btn_cancel" onclick="javascript:window.print();" value="打印" /></td>
            </tr>
        </table>
        <%}%>
    </body>
</html>
