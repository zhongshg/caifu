<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<%    String id = RequestUtil.getString(request, "id");
    ArrayList list = null;
    DataField df = null;
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
                } else if (act.equals("modfahuo")) {
                    String ShipName = RequestUtil.getString(request, "ShipName");
                    String ShipNo = RequestUtil.getString(request, "ShipNo");
                    int ShipType = RequestUtil.getInt(request, "ShipType");
                    Timestamp ShipTime = DateUtil.string2Time1(DateUtil.timezhuan(RequestUtil.getString(request, "ShipTime")));

                    DaoFactory.getOrderDAO().modPost(id, ShipNo, ShipName, ShipType,2, ShipTime);
                } else if (act.equals("modstate")) {
                    int state=RequestUtil.getInt(request, "state");    
                    DaoFactory.getOrderDAO().modState(id, state);
                }
                 else if (act.equals("modagree")) {
                    int agree=RequestUtil.getInt(request, "agree");    
                    DaoFactory.getOrderDAO().modAgree(id, agree);
                }
            }
            DataField d = DaoFactory.getOrderDAO().get(id);
            if (d != null) {
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
                                <p> <span class="tt">商品金额合计</span>：<%=d.getFloat("TF_Price")%>元 
                                    <span class="tt">折扣金额合计</span>：<%=d.getFloat("CF_Price")%>元 </p>
                                <p> <span class="tt">实收金额合计</span>：<%=d.getFloat("SF_Price")%>元
                                     <span class="tt"> 订单金额（含运费）</span>：<%=d.getFloat("F_Price")%>元 </p>
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
                                        }
                                    %>
                                    <span class="tt">支付状态</span>：<%if(d.getInt("IsPay") == 1) {%>未支付<%} else if (d.getInt("IsPay") == 2) {%>已支付<%} else if (d.getInt("IsPay") == 3) {%>站内币支付<%} %> <span class="tt">交易状态</span>：<%if (d.getInt("State") == 1) {%>未完成<%} else {%>已完成<%} %></p>
                                <p><span class="tt">IP</span>：<%=d.getString("Ip")%> <span class="tt">下单日期</span>：<%=d.getString("F_Date")%></p></td>
                            <td width="48%" valign="top"><p><strong>订单处理</strong>：</p>
                                 <form id="form1" name="form1" method="post" action="?act=modsts&amp;id=<%=id%>">
                                    状态：
                                    <select name="sts" id="sts">                               
                                     
                                        <option value="4" <%if (d.getInt("Sts") == 4) {%>selected<%}%>>未评价</option>
                                         <option value="5" <%if (d.getInt("Sts") == 5) {%>selected<%}%>>已评价</option>                                                           
                                    </select>
                                   <input type="submit" name="button" id="button" value="修改" />
                                </form>
                                 <form id="form2" name="form2" method="post" action="?act=modstate&amp;id=<%=id%>">
                                   <p><strong>交易处理</strong>：</p> 状态：
                                    <select name="state" id="state">                               
                                        <option value="1" <%if (d.getInt("State") == 1) {%>selected<%}%>>未完成</option>
                                        <option value="2" <%if (d.getInt("State") == 2) {%>selected<%}%>>已完成</option>                                                               
                                    </select>
                                   <input type="submit" name="button" id="button" value="修改" />
                                </form>
                                        <% if(d.getInt("Cate")>1){ %>
                                 <form id="form3" name="form3" method="post" action="?act=modagree&amp;id=<%=id%>">
                                   <p><strong>讲师确认</strong>：</p> 状态：
                                    <select name="agree" id="agree">                               
                                        <option value="1" <%if (d.getInt("Agree") == 1) {%>selected<%}%>>未授权</option>
                                        <option value="2" <%if (d.getInt("Agree") == 2) {%>selected<%}%>>已授权</option>
                                                                
                                    </select>
                                   <input type="submit" name="button" id="button" value="修改" />
                                </form>
                                <%}%>
                              </td>
                        </tr>
                        <tr>
                            <td valign="top"><p><strong>收货信息</strong>：</p>
                                <p><span class="tt">姓名</span>：<%=d.getString("F_Name")%> <span class="tt">手机</span>：<%=d.getString("F_Mobile")%> <span class="tt"> 电话</span>：<%=d.getString("F_Tel")%></p>
                                    <p><span class="tt">地址</span>： <%=d.getString("F_Address")%></p> 
                                   <p> <span class="tt">留言</span>：<%=d.getString("LiuYan")%> </p>
                                      </td>
                            <td valign="top">
                               
                              </td>
                        </tr>
                    </table></td>
            </tr>
            <tr>
                <td height="55" bgcolor="#FDFDFD">

                    <table width="100%" border="0"  cellspacing="1" bgcolor="#eeeeee">      

                        <tr>
                            <td height="32" width="12%" align="center" bgcolor="#F9F9F9" class="settle_b_table_first"><strong>课程图</strong></td>
                            <td width="12%" bgcolor="#F9F9F9"><strong>课程信息</strong></td>
                             <td width="30%" align="center" bgcolor="#F9F9F9"><strong>时间</strong></td>
                            <td width="12%" align="center" bgcolor="#F9F9F9"><strong>单价(元)</strong></td>
                            <td width="22%" align="center" bgcolor="#F9F9F9"><strong>数量</strong></td>
                            <td width="12%" align="center" bgcolor="#F9F9F9"><strong>金额</strong></td>
                        </tr>
                        <%
                            list = (ArrayList) DaoFactory.getBasketDAO().getListByOrderNo(id);
                            for (Iterator iter = list.iterator(); iter.hasNext();) {
                                df = (DataField) iter.next();
                        %>
                        <tr>
                            <td width="12%" height="60" align="center" bgcolor="#FFFFFF" class="settle_b_table_first"><a href="../p_show.jsp?id=" target="_blank"><img src="<%=df.getString("ViewImg")%>" width="56" height="43" /></a><strong name="26789d21612b4edbbc55ac87268404c8" class="orange"></strong></td>
                            <td width="12%" bgcolor="#FFFFFF"><span class="settle_b_table_first"><%=df.getString("Pname")%> </span></td>
                              <td width="30%" align="center" bgcolor="#FFFFFF"><%if(df.getInt("Cate")==2){ for(int j=0;j<df.getString("Day").split(",").length;j++){%><%=DateUtil.getYear() %>-<%=DateUtil.getMonth() %>-<%=df.getString("Day").split(",")[j]%> <%if(j!=(df.getString("Day").split(",").length-1)){%><br><%}}}%></td>
                            <td width="12%" align="center" bgcolor="#FFFFFF">
                                <%=df.getFloat("Bas_Price")%></td>
                            <td width="22%" align="center" bgcolor="#FFFFFF"><%=df.getInt("Pnum")%></td>
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
