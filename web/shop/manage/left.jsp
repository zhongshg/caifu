<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="" %>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
    <fmt:bundle basename="resources.totcms_i18n">
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
            <title>navigation</title>
            <link href="style/global.css" rel="stylesheet" type="text/css" />
            <style type="text/css">
                <!--
                body {
                    SCROLLBAR-FACE-COLOR: #6a84ae;
                    SCROLLBAR-HIGHLIGHT-COLOR: #aaaaff;
                    SCROLLBAR-SHADOW-COLOR: #335997;
                    SCROLLBAR-3DLIGHT-COLOR: #335997;
                    SCROLLBAR-ARROW-COLOR: #ffffff;
                    SCROLLBAR-TRACK-COLOR: #335997;
                    SCROLLBAR-DARKSHADOW-COLOR: black;
                    margin:0 0 0 5px;
                    background-color:#029B2F;
                }
                .divHead {
                    BORDER-RIGHT: #029B2F 1px solid;
                    BORDER-TOP: #029B2F 1px solid;
                    BORDER-LEFT: #029B2F 0px solid;
                    WIDTH: 90%;
                    height:24px;
                    CURSOR: default;
                    BORDER-BOTTOM: #029B2F 1px solid;
                    BACKGROUND-COLOR: #029B2F;
                    margin:0;
                    padding:0;
                }
                .divChild {
                    BORDER-RIGHT: #02CC3F 1px solid;
                    BORDER-TOP: #003300 1px solid;
                    BORDER-LEFT: #003300 1px solid;
                    WIDTH: 90%;
                    BORDER-BOTTOM: #02CC3F 1px solid;
                    BACKGROUND-COLOR: #029B2F;
                    margin:0;
                    padding:0;
                }
                .childLeft {
                    BORDER-RIGHT: #029B2F 1px solid;
                    BORDER-TOP: #029B2F 1px solid;
                    BORDER-LEFT: #029B2F 1px solid;
                    WIDTH: 90%;
                    height:24px;
                    CURSOR: default;
                    BORDER-BOTTOM: #029B2F 1px solid;
                    BACKGROUND-COLOR: #029B2F;
                    margin:0;
                    padding:0 0 0 20px;
                }
                .style1 {color: #FFFFFF}
                -->
            </style></head>
        <SCRIPT>
            var MSIE = 0;
            if (navigator.appName == 'Microsoft Internet Explorer')
                MSIE = 1;
            function MouseOver(item, type) {
                item.style.border = '1px solid';
                if (type == 0) {
                    item.style.backgroundColor = '#02CC3F';
                    item.style.borderLeftColor = '#02CC3F';
                    item.style.borderTopColor = '#02CC3F';
                    item.style.borderRightColor = '#003300';
                    item.style.borderBottomColor = '#003300';
                } else if (type == 1) {
                    item.style.backgroundColor = '#02CC3F';
                    item.style.borderLeftColor = '#02CC3F';
                    item.style.borderTopColor = '#02CC3F';
                    item.style.borderRightColor = '#003300';
                    item.style.borderBottomColor = '#003300';
                }
            }

            function MouseOut(item, type) {
                item.style.border = '1px solid';
                if (type == 0) {
                    item.style.backgroundColor = '#029B2F';
                    item.style.borderColor = '#029B2F';
                } else if (type == 1) {
                    item.style.backgroundColor = '#029B2F';
                    item.style.borderColor = '#029B2F';
                }
            }
            function showHide(obj) {
                if (document.getElementById(obj).style.display == "none") {
                    document.getElementById(obj).style.display = "block";
                }
                else {
                    document.getElementById(obj).style.display = "none";
                }
            }
        </SCRIPT>
        <body>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <!--                <tr>
                                    <td align="center"><span class="style1" style="padding-top:5px;"><a href="/" target="_blank"><img src="/img/logo.png" alt="" height="46" border="0" /></a></span></td>
                                </tr>-->
                <tr>
                    <td><div class="childLeft" onmouseover="javascript:MouseOver(this, 1)" onmouseout="javascript:MouseOut(this, 1)"><img src="images/icon/home.gif" width="22" height="22" align="absmiddle" /> <a href="chanpass.jsp" target="mainFrame" class="white">更改密码</a> <img src="images/icon/logoff.gif" width="22" height="22" align="absmiddle" /> <a href="logout.jsp" target="_parent" class="white">退出</a></div></td>
                </tr>
            </table>
            <div style="clear:both;"></div>

            <div class="divHead" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)" onClick="showHide('d03')">
                <img src="images/icon/folder.gif" width="22" height="22" align="absmiddle"><span class="style10 style1">商品管理</span></div>
            <div class="divChild" id="d03">
                <div class="childLeft" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)"><img src="images/icon/folder1.gif" width="18" height="18" align="absmiddle" /> <a href="category_manage.jsp" target="mainFrame" class="white"> | 分类管理 |</a></div>   
                <div class="childLeft" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)"><img src="images/icon/folder1.gif" width="18" height="18" align="absmiddle" /> <a href="p_add.jsp" target="mainFrame" class="white"> | 添加商品 |</a></div>
                <div class="childLeft" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)"><img src="images/icon/folder1.gif" width="18" height="18" align="absmiddle" /> <a href="p_manage.jsp" target="mainFrame" class="white"> | 管理商品 |</a></div>
                <!--<div class="childLeft" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)"><img src="images/icon/folder1.gif" width="18" height="18" align="absmiddle" /> <a href="pj_pmanage.jsp" target="mainFrame" class="white"> | 评价管理 |</a></div>-->
            </div>






            <!--            <div class="divHead" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)" onClick="showHide('d07')">
                            <img src="images/icon/folder.gif" width="22" height="22" align="absmiddle"><span class="style10 style1">会员管理</span></div>
                        <div class="divChild" id="d07">
                           
                              <div class="childLeft" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)"><img src="images/icon/folder1.gif" width="18" height="18" align="absmiddle" /> <a href="u_manage.jsp?cate=3" target="mainFrame" class="white"> | 用户管理 |</a></div>
                            
                        </div>-->



            <div class="divHead" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)" onClick="showHide('d10')">
                <img src="images/icon/folder.gif" width="22" height="22" align="absmiddle"><span class="style10 style1">订单管理</span></div>
            <div class="divChild" id="d10">	
                <div class="childLeft" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)"><img src="images/icon/folder1.gif" width="18" height="18" align="absmiddle" /> <a href="order_manage.jsp?IsPay=-1" target="mainFrame" class="white"> | 订单管理 |</a></div>
                <div class="childLeft" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)"><img src="images/icon/folder1.gif" width="18" height="18" align="absmiddle" /> <a href="order_manage_pro.jsp?IsPay=-1" target="mainFrame" class="white"> | 订单产品 |</a></div>
                <div class="childLeft" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)"><img src="images/icon/folder1.gif" width="18" height="18" align="absmiddle" /> <a href="funds_manage.jsp" target="mainFrame" class="white"> | 提现管理 |</a></div>
                <div class="childLeft" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)"><img src="images/icon/folder1.gif" width="18" height="18" align="absmiddle" /> <a href="exportlog_manage.jsp" target="mainFrame" class="white"> | 导出管理 |</a></div>

                <!--                  <div class="childLeft" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)"><img src="images/icon/folder1.gif" width="18" height="18" align="absmiddle" /> <a href="swapbill_manage.jsp?IsPay=-1" target="mainFrame" class="white"> | 退货单管理 |</a></div>
                                   <div class="childLeft" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)"><img src="images/icon/folder1.gif" width="18" height="18" align="absmiddle" /> <a href="delvbill_manage.jsp?IsPay=-1" target="mainFrame" class="white"> | 补货单管理 |</a></div>
                                    <div class="childLeft" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)"><img src="images/icon/folder1.gif" width="18" height="18" align="absmiddle" /> <a href="invoice_manage.jsp?IsPay=-1" target="mainFrame" class="white"> | 发票管理 |</a></div>-->

            </div>





            <!--            <div class="divHead" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)" onClick="showHide('d14')">
                            <img src="images/icon/folder.gif" width="22" height="22" align="absmiddle"><span class="style10 style1">友情链接</span></div>
                        <div class="divChild" id="d14">
                            <div class="childLeft" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)"><img src="images/icon/folder1.gif" width="18" height="18" align="absmiddle" /> <a href="flink_add.jsp" target="mainFrame" class="white"> | 添加 |</a></div>
                            <div class="childLeft" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)"><img src="images/icon/folder1.gif" width="18" height="18" align="absmiddle" /> <a href="flink_manage.jsp" target="mainFrame" class="white"> | 管理 |</a></div>
                        </div>-->

            <div class="divHead" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)" onClick="showHide('d15')">
                <img src="images/icon/folder.gif" width="22" height="22" align="absmiddle"><span class="style10 style1">幻灯片</span></div>
            <div class="divChild" id="d15">
                <div class="childLeft" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)"><img src="images/icon/folder1.gif" width="18" height="18" align="absmiddle" /> <a href="slide.jsp?act=add" target="mainFrame" class="white"> | 添加 |</a></div>
                <div class="childLeft" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)"><img src="images/icon/folder1.gif" width="18" height="18" align="absmiddle" /> <a href="slide.jsp" target="mainFrame" class="white"> | 管理 |</a></div>
            </div>

            <!--            <div class="divHead" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)" onClick="showHide('d16')">
                            <img src="images/icon/system.gif" width="22" height="22" align="absmiddle"><span class="style10 style1"> 
                                    系统配置</span></div>
                        <div class="divChild" id="d16">
                            <div class="childLeft" onMouseOver="javascript:MouseOver(this, 1)" onMouseOut="javascript:MouseOut(this, 1)"><img src="images/icon/folder1.gif" width="18" height="18" align="absmiddle" /> <a href="config.jsp" target="mainFrame" class="white"> | 参数修改 |</a></div>config.jsp
                        </div>-->
            <script language="javascript">
                function showHide(obj) {
                    if (document.getElementById(obj).style.display == "none") {
                        document.getElementById(obj).style.display = "block";
                    }
                    else {
                        document.getElementById(obj).style.display = "none";
                    }
                }
                function exitsDiv(divid) {
                    var chkid = document.getElementById(divid);
                    if (chkid != null) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                function unfold() {
                    for (i = 0; i <= 30; i++) {
                        var s = (i < 10 ? ("0" + i) : (i + ""));
                        if (exitsDiv("d" + s)) {
                            document.getElementById("d" + s).style.display = "none";
                        }
                    }
                    document.getElementById("trigFold").innerHTML = '<a href="javascript:foldAll()" class="w"><img src="images/tree/plus.gif" alt="unfold" width="16" height="16" border="0" align="absmiddle"></a>'
                }
                function foldAll() {
                    for (i = 0; i <= 30; i++) {
                        var s = (i < 10 ? ("0" + i) : (i + ""));
                        if (exitsDiv("d" + s)) {
                            document.getElementById("d" + s).style.display = "block";
                        }
                    }
                    document.getElementById("trigFold").innerHTML = '<a href="javascript:unfold()" class="w"><img src="images/tree/minus.gif" alt="fold" width="16" height="16" border="0" align="absmiddle"></a>'
                }
            </script>
    </fmt:bundle>
</body>
</html>
