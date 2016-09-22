<%-- 
    Document   : u_manage
    Created on : 2014-10-27, 13:55:37
    Author     : Administrator
--%>
<%@page import="wap.wx.dao.SubscriberDAO"%>
<%@page import="wap.wx.menu.WxMenuUtils"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
            <title>添加提现</title>
            <link href="style/global.css" rel="stylesheet" type="text/css" />
            <style type="text/css">
                <!--
                .style1 {color: #FFFFFF}
                .style2 {
                    color: #FFFFFF;
                    font-weight: bold;
                    font-size: 14px;
                }
                #thumbnails{}
                #thumbnails li{ float:left; list-style:none; margin:0px 5px; padding:0px;}
                -->
            </style>


    </head>
    <script>
        function check(obj) {
            obj.Submit.disabled = true;
            if (obj.UserId.value == "") {
                alert('会员openid不能为空');
                obj.UserId.focus();
                obj.UserId.select();
                obj.Submit.disabled = false;
                return false;
            }
//                  if (obj.UserName.value == "") {
//                    alert('姓名不能为空');
//                    obj.UserName.focus();
//                    obj.UserName.select();
//                    obj.Submit.disabled = false;
//                    return false;
//                }
//                 if (obj.F_Phone.value == "") {
//                    alert('电话不能为空');
//                    obj.F_Phone.focus();
//                    obj.F_Phone.select();
//                    obj.Submit.disabled = false;
//                    return false;
//                }
//                 if (obj.F_Phone.value == "") {
//                    alert('电话不能为空');
//                    obj.F_Phone.focus();
//                    obj.F_Phone.select();
//                    obj.Submit.disabled = false;
//                    return false;
//                }
//                 if (obj.Alipay.value == "") {
//                    alert('支付宝帐号不能为空');
//                    obj.Alipay.focus();
//                    obj.Alipay.select();
//                    obj.Submit.disabled = false;
//                    return false;
//                }
            if (obj.F_Price.value == "") {
                alert('提现金额不能为空');
                obj.F_Price.focus();
                obj.F_Price.select();
                obj.Submit.disabled = false;
                return false;
            }

            if (isNaN(obj.F_Price.value)) {
                alert('提现金额必须为数值');
                obj.F_Price.focus();
                obj.F_Price.select();
                obj.Submit.disabled = false;
                return false;
            }
            return true;
        }
        function upimg() {
            var pt = window.showModalDialog('upimg.htm', '', 'dialogHeight:160px;dialogWidth:410px;help:no;status:no;scroll:no');
            if (pt != undefined)
                document.getElementById('Avatar').value = pt;
        }
    </script>
    <body>
        <%@ include file="top.jsp"%>
        <%    String act = RequestUtil.getString(request, "act");

            ArrayList list = null;
            DataField df = null;
            if (act != null && act.equals("do")) {
                int Type = 2;
                String UserId = RequestUtil.getString(request, "UserId");
                Map<String, String> subscriber = new SubscriberDAO().getByOpenid(wx.get("id"), UserId);
                String UserName = subscriber.get("nickname");// RequestUtil.getString(request, "UserName");             
//                String F_Phone = RequestUtil.getString(request, "F_Phone");
//                String Alipay=RequestUtil.getString(request,"Alipay");
                String Descr = RequestUtil.getString(request, "Descr");
                Timestamp F_Date = DateUtil.getCurrentGMTTimestamp();
                String Ip = request.getRemoteAddr();
                java.util.Date currentTime = new java.util.Date();
                String F_No = WxMenuUtils.format2.format(new java.util.Date()) + String.valueOf((int) ((Math.random() * 9 + 1) * 100000));

                float F_Price = RequestUtil.getFloat(request, "F_Price");
                boolean bl = DaoFactory.getFundsDao().add(F_No, F_Date, F_Price, UserId, UserName, "", Ip, 2, Type, "", Descr, 0, wx.get("id"));
                if (bl) {
                    out.print("<p align=\"center\">提现成功!<br /><a href=\"javascript:history.back()\">继续提现</a>&nbsp;&nbsp;</p>");
                    DaoFactory.getFundsDao().modOp(F_No, Integer.parseInt(((Map<String, String>) session.getAttribute("users")).get("id")));
                } else {
                    out.print("<script>alert('Error');history.back();</script>");
                }
            } else {

        %>

        <form id="addCategory" name="addCategory" method="post" action="?act=do" onSubmit="return check(this);">
            <table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0">
                <tr>
                    <td width="15%"><div align="right">会员openid：</div></td>
                    <td width="50%" ><div align="left">
                            <input name="UserId" type="text" id="UserId" class="form-control"  value="" style="width:246px; border:1px solid #666666;" maxlength="250"/>
                        </div></td>
                </tr>
                <tr id="showmessage1" style="display: none;">
                    <td><div align="right">会员昵称：</div></td>
                    <td><div align="left" id="showresult1"></div></td>
                </tr>
                <tr id="showmessage2" style="display: none;">
                    <td><div align="right">可提现财富：</div></td>
                    <td><div align="left" id="showresult2"></div></td>
                </tr>
                <!--                                        <tr>
                                                            <td><div align="right">姓名：</div></td>
                                                            <td><div align="left">
                                                                    <input name="UserName" type="text" id="UserName" class="form-control" value="" />
                                                                    
                                                                </div></td>
                                                        </tr>
                                                         <tr>
                                                            <td><div align="right">电话：</div></td>
                                                            <td><div align="left">
                                                                   
                                                                    <input type="text" name="F_Phone" id="F_Phone" class="form-control" value=""/>
                                                                </div></td>
                                                        </tr>
                                                        <tr>
                                                            <td align="right">支付宝帐号：</td>
                                                            <td><input name="Alipay" type="text" id="Alipay" class="form-control" value="" /></td>
                                                        </tr>-->
                <tr>
                    <td align="right">提现金额：</td>
                    <td><input name="F_Price" type="number" id="F_Price" class="form-control" style="float: left;" placeholder="0.00" style="width:246px; border:1px solid #666666;" maxlength="250"/></td>
                </tr>
                <tr>
                    <td align="right">备注：</td>
                    <td><textarea name="Descr" type="text" id="Descr" class="form-control" style="width:246px; border:1px solid #666666;" maxlength="250"></textarea></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td height="55"><input name="Submit" type="submit" class="btn btn-default" value="确 定" />
                        &nbsp;
                        <input name="Submit2" type="button" class="btn btn-default" onclick="javascript:history.back();" value="取 消" /></td>
                </tr>
            </table>
        </form>
        <script type="text/javascript" src="/js/jquery.js"></script>
        <script type="text/javascript">
        $(document).ready(function() {
            $("#UserId").blur(function() {
                $.post("public/public_do.jsp", {"wxsid": "<%=wx.get("id")%>", "UserId": $("#UserId").val()}, function(result) {
                    if ("" != result.trim()) {
                        var results = result.split(",");
                        $("#showresult1").html(results[0].trim());
                        $("#showmessage1").show();
                        $("#showresult2").html(results[1].trim());
                        $("#showmessage2").show();
                    } else {
                        $("#showmessage1").hide();
                        $("#showmessage2").hide();
                    }
                });
            });
        });
        </script>
        <%}
        %>

    </body>
</html>
