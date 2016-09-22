<%@page import="wap.wx.service.PublicService"%>
<%@page import="org.apache.lucene.document.DateField"%>
<%@page import="org.apache.catalina.connector.Request"%>
<%@page import="org.jfree.data.DataUtilities"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<% ArrayList list = null;
    ArrayList list1 = null;
    DataField df = null;
    DataField df1 = null;
    int leibie_pid = 0;
    Random random = new Random();
    String sRand = "";
    String rand = "";
    String session_pid = "";

    for (int j = 0; j < 4; j++) {
        rand = String.valueOf(random.nextInt(10));
        sRand += rand;
    }
    if (session.getAttribute("session_pid") == null) {
        session_pid = System.currentTimeMillis() + sRand;
        session.setAttribute("session_pid", session_pid);
    }

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8"/>
        <title>添加商品</title>
        <link rel="stylesheet" type="text/css" href="../css/time/themes/icon.css"/>
        <link rel="stylesheet" type="text/css" href="../css/time/demo.css"/>
        <link rel="stylesheet" type="text/css" href="../css/time/themes/default/easyui.css"/>
        <script type="text/javascript" src="../css/time/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="../css/time/jquery.easyui.min.js"></script>
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
            #thumbnails1{}
            #thumbnails1 li{ float:left; list-style:none; margin:0px 5px; padding:0px;}
            -->
        </style>
        <script type="text/javascript" src="/js/jquery.js"></script>
        <script type="text/javascript" src="../swfupload/swfupload.js"></script>
        <script type="text/javascript" src="js/handlers.js"></script>
        <script type="text/javascript" src="js/upinit.js"></script>
        <script type="text/javascript" src="js/handlers1.js"></script>
        <script>
            function check(obj) {
                obj.Submit.disabled = true;
                if (obj.Title.value == "") {
                    alert('标题不能为空');
                    obj.Title.focus();
                    obj.Title.select();
                    obj.Submit.disabled = false;
                    return false;
                } else if (obj.ViewImg.value == "") {
                    alert('首页图片不能为空');
                    obj.ViewImg.focus();
                    obj.ViewImg.select();
                    obj.Submit.disabled = false;
                    return false;
                }
                //添加属性
                var totalstr = "";
                $('#tb').find('tr').each(function() {
                    $(this).find('td input').each(function() {
                        totalstr += "*" + $(this).val().trim() + ',';
                    });
                    totalstr += ' ';
                });
                $("#totalstr").val(totalstr);
                return true;
            }
            function upimg() {
                var pt = window.showModalDialog('upimg.htm', '', 'dialogHeight:160px;dialogWidth:410px;help:no;status:no;scroll:no');
                if (pt != undefined)
                    document.getElementById('ViewImg').value = pt;
            }

            //显示属性标签
            function xsshuxing() {
                $("#xsshuxing").css("display", "block");
                $("#shuxing").attr("disabled", false);
            }
//隐藏属性的方法
            function shuxingsq() {
                $("#xsshuxing").css("display", "none");
            }

            function shuxingsx(shuxing, signid, d) {
                $("#propertyadd").css("display", "none");
                var shuxing = $("#shuxing").val();
                if (0 == shuxing) {
                    $("#shuxing_value").html("");
                    return false;
                }
                $.post(
                        "p_add_mod.jsp",
                        {
                            "act": "shuxingsx",
                            "shuxing": shuxing,
                            "signid": signid,
                            "pid": d
                        },
                function(result) {
                    if ("0" == result.trim()) {
                        //alert("返回为0！");
                        $("#tjshuxing").attr("disabled", false);
                        alert("属性不存在，请刷新页面重试！");
//                                   
                    } else {
                        $("#shuxinghidden").val(shuxing);
                        $("#shuxing_value").css("display", "block");
                        $("#shuxing_value").html(result);
                    }
                }
                );
            }
            function addproperty(sign) {
                $("#propertyadd").css("display", "block");
                $("#propertyname").val("");
                $("#temppropertysign").val(sign);
                if ("0" == sign) {
                    $("#shuxing_value").css("display", "none");
                }
            }
            function shuxingtj(signid, id) {
                var sx_con_id = "";
                $("input[name='propertycheckbox']:checkbox").each(function() {
                    if ($(this).attr("checked")) {
                        sx_con_id += " " + signid + "," + $(this).val();
                    }
                });
//            if (sx_con_id != "") {
                $.post("p_add_mod.jsp", {"act": "sxdo", "signid": signid, "pid": id, "sx_id": $("#shuxinghidden").val(), "sx_con_id": sx_con_id}, function(result) {
                    if ("1" == result.trim()) {
                        alert("添加失败！");
                    }
                    else {
//                        $("#tjshuxing").attr("disabled", true);
                        $("#shuxingdiv").html(result.trim());
                    }
                });
//            }
            }
        </script>

    </head>


    <body>
        <%@ include file="top.jsp"%>
        <%    String act = RequestUtil.getString(request, "act");
            if (act != null && act.equals("do")) {
                int Cid = RequestUtil.getInt(request, "Cid");
                String ProCode = RequestUtil.getString(request, "ProCode");
                String SuserId = wx.get("id");
                String SuserName = wx.get("name");
                int BaoJian = 0;
                int GongNeng = 0;
                int RenQun = 0;
                int JiXing = 0;
                int JiangShi = RequestUtil.getInt(request, "JiangShi");
                int Rid = DaoFactory.getCategoryDAO().getRootId(Cid);
                int Pid = DaoFactory.getCategoryDAO().getParentId(Cid);
                int Cate = 1;
                String PiZhun = "pizhun";
                String ChangMing = "changming";
                String ChangZhi = "changzhi";
                String FangShi = "fangshi";
                String PeiLiao = "peiliao";
                String ChuCang = "chucang";
                String BaoZhi = "biaozhi";
                String TianJiaJi = "tianjiaji";
                String NoRenQun = "norenqun";
                String YuanLiao = "yuanliao";
                String WenHao = "wenhao";
                String GuiGe = "guige";
                String JinKou = "jinkou";
                String RiQi = "riqi";
                Float KuaiDi = RequestUtil.getFloat(request, "KuaiDi");
                int IsRem = RequestUtil.getInt(request, "IsRem");
                String WebDescr = RequestUtil.getString(request, "WebDescr");
                String WebKeywords = "," + PiZhun + "," + ChangMing + "," + ChangZhi + "," + FangShi + "," + PeiLiao + "," + ChuCang + "," + BaoZhi + "," + TianJiaJi + "," + NoRenQun + "," + YuanLiao + "," + WenHao + "," + GuiGe + "," + JinKou + "," + RiQi;
                String WebTitle = RequestUtil.getString(request, "WebTitle");
                String Title = RequestUtil.getString(request, "Title");
                String Abbreviation = RequestUtil.getString(request, "Abbreviation");
                String Keywords = RequestUtil.getString(request, "Keywords");
                String Descr = RequestUtil.getString(request, "Descr");
                String Remarks = RequestUtil.getString(request, "Remarks");
                String YongTu = RequestUtil.getString(request, "YongTu");
                int IsSale = RequestUtil.getInt(request, "IsSale");
                int BrandId = RequestUtil.getInt(request, "BrandId");
                int IsShelves = RequestUtil.getInt(request, "IsShelves");
                int MeasurId = RequestUtil.getInt(request, "MeasurId");
                Timestamp Shelves = DateUtil.getCurrentGMTTimestamp();
                String shangjia = RequestUtil.getString(request, "Shelves");
                String Supplier = wx.get("id");
                String ViewImg = RequestUtil.getString(request, "ViewImg");
                String Photos = RequestUtil.getString(request, "Photos");
                String Photo = RequestUtil.getString(request, "Photo");
                float Price = RequestUtil.getFloat(request, "Price");
                float PriceM = RequestUtil.getFloat(request, "Price");//这里用一个价
                String Content = RequestUtil.getString(request, "Content");
                Timestamp AddTime = DateUtil.getCurrentGMTTimestamp();
                int PSts = RequestUtil.getInt(request, "PSts");
                int IsNew = RequestUtil.getInt(request, "IsNew");
                String Tags = RequestUtil.getString(request, "Tags");
                Timestamp ModTime = AddTime;
                float SaleNum = RequestUtil.getFloat(request, "SaleNum");
                int IsTuan = 2;
                int IsFree = 0;
                Timestamp BeginTime = AddTime;
                Timestamp EndTime = ModTime;
                int IsShi = RequestUtil.getInt(request, "IsShi");
                String begintime = "";
                String endtime = "";
                if (begintime == null || "".equals(begintime)) {
                    BeginTime = AddTime;
                } else {
                    BeginTime = DateUtil.string2Time1(DateUtil.timezhuan(begintime));
                }
                if (endtime == null || "".equals(endtime)) {
                    EndTime = AddTime;
                } else {
                    EndTime = DateUtil.string2Time1(DateUtil.timezhuan(endtime));
                }
                if (shangjia != null && !shangjia.equals("")) {
                    Shelves = DateUtil.string2Time1(DateUtil.timezhuan(shangjia));

                }
                //wxid,distributionmoney,distributionfirstdiscount,distributionseconddiscount,distributionthirddiscount
                String wxid = RequestUtil.getString(request, "wxid");
                float distributionmoney = Price;// "".equals(RequestUtil.getString(request, "distributionmoney")) ? 0 : RequestUtil.getFloat(request, "distributionmoney");
                float distributionfirstdiscount = "".equals(RequestUtil.getString(request, "distributionfirstdiscount")) ? 0 : RequestUtil.getFloat(request, "distributionfirstdiscount");
                float distributionseconddiscount = "".equals(RequestUtil.getString(request, "distributionseconddiscount")) ? 0 : RequestUtil.getFloat(request, "distributionseconddiscount");
                float distributionthirddiscount = "".equals(RequestUtil.getString(request, "distributionthirddiscount")) ? 0 : RequestUtil.getFloat(request, "distributionthirddiscount");
                int distributiontype = RequestUtil.getInt(request, "distributiontype");
                String propertys = (String) session.getAttribute("propertysDF");
                boolean bl = DaoFactory.getProductDAO().add_BqTime_pid(ProCode, SuserId, SuserName, Rid, Pid, Cid, BaoJian, GongNeng, RenQun, JiXing, JiangShi, WebDescr, WebKeywords, WebTitle, Title, Abbreviation, Keywords, Descr, MeasurId, Remarks, YongTu, IsSale, BrandId, IsShelves, ViewImg, Supplier, Photos, Cate, Photo, Price, PriceM, Content, AddTime, ModTime, Shelves, PSts, IsNew, IsRem, Tags, SaleNum, IsShi, BeginTime, EndTime, KuaiDi, session.getAttribute("session_pid").toString(), wxid, distributionmoney, distributionfirstdiscount, distributionseconddiscount, distributionthirddiscount, distributiontype, propertys);

//                3.保存psv
                int id = DaoFactory.getProductDAO().getLastId(SuserId) - 1;
                int signid = 0;
                new PublicService().setPsv(signid, id, wx, request);

                if (bl) {
                    out.print("<p align=\"center\">成功添加!<br /><a href=\"javascript:history.back()\">继续添加</a>&nbsp;&nbsp;<a href=\"p_manage.jsp\">商品管理</a></p>");
                } else {
                    out.print("<script>alert('添加失败');history.back();</script>");
                }
            }
            session.setAttribute("propertysDF", "");
            //1.初始化属性totalstr
            int signid = 0;
            String totalstr = "";

            //设置排序为预计id
            float SaleNum = DaoFactory.getProductDAO().getLastId(wx.get("id"));
        %>
        <br />
        <form id="addCategory" name="addCategory" method="post" action="?act=do" onSubmit="return check(this);">
            <input type="hidden" name="wxid" value="0"/>
            <%--2.设置隐藏域--%>
            <input type="hidden" name="totalstr" id="totalstr" value="<%=totalstr%>"/>
            <table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border">
                <tr>
                    <td height="25" colspan="2" bgcolor="#3872b2"><div align="center" class="style1"><strong><span class="style2">
                                    添加商品 </span></strong>

                        </div></td>
                </tr>
                <tr>
                    <td width="135" bgcolor="#FDFDFD"><div align="right">商品编号：</div></td>
                    <td width="828" bgcolor="#FDFDFD"><div align="left">
                            <input name="ProCode" type="ProCode" id="ProCode" style="width:246px; border:1px solid #666666;" maxlength="250" />
                        </div></td>
                </tr>
                <tr>
                    <td width="135" bgcolor="#FDFDFD"><div align="right">商品名称：</div></td>
                    <td width="828" bgcolor="#FDFDFD"><div align="left">
                            <input name="Title" type="text" id="Title" style="width:246px; border:1px solid #666666;" maxlength="250" />
                        </div></td>
                </tr>
                <!--                <tr>
                                    <td width="135" bgcolor="#FDFDFD"><div align="right">商品简称：</div></td>
                                    <td width="828" bgcolor="#FDFDFD"><div align="left">
                                            <input name="Abbreviation" type="text" id="Abbreviation" style="width:246px; border:1px solid #666666;" maxlength="250" />
                                        </div></td>
                                </tr>-->
                <!--                <tr>
                                    <td width="135" bgcolor="#FDFDFD"><div align="right">商家账号：</div></td>
                                    <td width="828" bgcolor="#FDFDFD"><div align="left">
                                            <input name="SuserId" type="hidden" id="SuserId" style="width:246px; border:1px solid #666666;" maxlength="250" value="15254139923" />（*商家手机号）
                                        </div></td>
                                </tr>-->
                <!--                <tr>
                                    <td width="135" bgcolor="#FDFDFD"><div align="right">商家名称：</div></td>
                                    <td width="828" bgcolor="#FDFDFD"><div align="left">
                                            <input name="SuserName" type="hidden" id="SuserName" style="width:246px; border:1px solid #666666;" maxlength="250" value="万巷坊" />（*商家名称）
                                        </div></td>
                                </tr>-->

                <!--                <tr>
                                    <td width="135" bgcolor="#FDFDFD"><div align="right">供应商：</div></td>
                                    <td width="828" bgcolor="#FDFDFD"><div align="left">
                                            <input name="Supplier" type="hidden" id="Supplier" style="width:246px; border:1px solid #666666;" maxlength="250" value="wanxiangfang" />
                                        </div></td>
                                </tr>-->
                <tr>
                    <td bgcolor="#FDFDFD"><div align="right">图片：<br/>640*370</div></td>
                    <td bgcolor="#FDFDFD"><input name="ViewImg" type="text" id="ViewImg" style="width:246px; border:1px solid #666666;" maxlength="250" />
                        <div id="thumbnails1"></div>       
                        <div id="swfu_container" style="margin: 0px 10px;">
                            <div id="divFileProgressContainer1"></div>          
                        </div>
                        <div style="clear:both;"></div>
                        <span id="spanButtonPlaceholder1" style="cursor:pointer;"></span>
                        <input name="UpLoad" type="button" id="UpLoad" value="上传" onClick="upimg()" />
                    </td>
                </tr>  
                <!--                <tr>
                                    <td align="right" bgcolor="#FDFDFD">产品图片：<br/>620*230</td>
                
                                    <td bgcolor="#FDFDFD">
                                        <textarea name="Photos" id="Photos" style="display:none;"></textarea>
                                        <div id="thumbnails"></div>       
                                        <div id="swfu_container" style="margin: 0px 10px;">
                                            <div id="divFileProgressContainer"></div>          
                                        </div>
                                        <div style="clear:both;"></div>
                                        <span id="spanButtonPlaceholder" style="cursor:pointer;"></span>
                                    </td>
                                </tr>-->
                <tr>
                    <td align="right" bgcolor="#FDFDFD">价格：</td>
                    <td bgcolor="#FDFDFD"><input name="Price" type="text" id="Price" value="0.00" size="12" onclick="if (this.value == '0.00')
                    this.value = '';" onblur="if (this.value == '')
                    this.value = '0.00';" />
                        元 
                        <!--                        (市场价格：
                                                <input name="PriceM" type="text" id="PriceM" value="0.00" size="12" onclick="if (this.value == '0.00')
                                                this.value = '';" onblur="if (this.value == '')
                                                this.value = '0.00';" />
                                                元) -->
                    </td>
                </tr>
                <tr>
                    <td width="135" bgcolor="#FDFDFD"><div align="right">分类：</div></td>
                    <td width="828" bgcolor="#FDFDFD">
                        <div align="left">
                            <select name="Cid" size="1" id="Cid" onChange="GetEduArea(this.options[this.selectedIndex].value, 0)">
                                <%=DaoFactory.getCategoryDAO().getSelectTree("", 0, 0, wx.get("id"))%>
                            </select>

                        </div>
                    </td>
                </tr>                                                                                 

                <!--                <tr>
                                    <td align="right" bgcolor="#FDFDFD">产品状态：</td>
                                    <td bgcolor="#FDFDFD"><label>
                                            <select name="PSts" id="PSts">
                                                <option value="1">正常</option>
                                                <option value="2">暂停出售</option>
                                            </select>是否上架
                                            <select name="IsShelves" id="IsShelves"  >
                                                <option value="1">是</option>
                                                <option value="2">否</option>
                                            </select>
                
                                        </label></td>
                                </tr>-->
                <tr>
                    <td align="right" bgcolor="#FDFDFD">排序：</td>
                    <td bgcolor="#FDFDFD"><label>
                            <input name="SaleNum" type="text" id="SaleNum" value="<%=SaleNum%>" size="12"/>
                        </label></td>
                </tr>
                <tr>
                    <td width="135" bgcolor="#FDFDFD"><div align="right">属性：</div></td>
                    <td width="828" bgcolor="#FDFDFD">
                        <%
                            List<DataField> firstPropertysList = (List<DataField>) DaoFactory.getPropertysDaoImplJDBC().getList("0", signid, wx.get("id"));
                        %>
                        <input type="button" value="设置商品属性"  onclick="xsshuxing()" />
                        <input type="button" value="添加属性"  onclick="addproperty('0')" />
                        <input type="button" value="添加属性值"  onclick="addproperty('1')" />
                        <div align="left" name="xsshuxing" id="xsshuxing" style="border:1px solid #FFA500;min-height: 50px;display: none;padding: 10px; " >

                            属性:<input type="hidden" id="shuxinghidden" value="0"/>
                            <select name="shuxing" size="1" id="shuxing" onChange="shuxingsx(this.options[this.selectedIndex].value,<%=signid%>, 0)">
                                <option value="0" >请选择</option>
                                <%
                                    for (DataField firstProperty : firstPropertysList) {
                                %>
                                <option value="<%=firstProperty.getFieldValue("svid")%>">&nbsp;<%=firstProperty.getFieldValue("svname")%></option>
                                <%                                }
                                %>
                            </select>
                            <div id="propertyadd" name="propertyadd" style="display: none; ">
                                <input type="hidden" id="temppropertysign" value="0"/>
                                <input type="hidden" id="signid" value="<%=signid%>"/>
                                <input type="text" value="" id="propertyname" />
                                <input id="propertybutton" value="确定" type="button"/>
                            </div>
                            <div id="shuxing_value" name="shuxing_value" style="display: none; "></div>

                            <div style="margin-top: 10px;">
                                <input name="tjshuxing" id="tjshuxing" value="确定" type="button" onclick="shuxingtj(<%=signid%>, 0)"/>
                                <input name="sqshuxing" id="sqshuxing" value="收起" type="button" onclick="shuxingsq()" />

                            </div>
                        </div>
                    </td>
                </tr> 
                <tr><td align="rught" bgcolor="#FDFDFD" ></td>
                    <td bgcolor="#FDFDFD" >
                        <div id="shuxingdiv" name="shuxingdiv">
                            <%
                                String property = "";
                                /*
                                 if (null != property) {
                                 StringBuilder param = new StringBuilder("<table id=\"tb\" width=\"100%\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" bgcolor=\"#A3B2CC\" >");
                                 String propertys[] = property.split(" ");
                                 String sign = "";
                                 param.append("<tr>");
                                 for (int i = 0; i < propertys.length; i++) {
                                 if ("".equals(propertys[i])) {
                                 continue;
                                 }
                                 String propertyArr[] = propertys[i].split(",");
                                 if (0 == propertyArr.length) {
                                 continue;
                                 }
                                 String propertyname = DaoFactory.getPropertysDaoImplJDBC().get(propertyArr[1].split("-")[0]).getFieldValue("svname");
                                 if (!sign.equals(propertyname)) {
                                 sign = propertyname;
                                 param.append("</tr><tr> <td bgcolor=\"#F2F2F2\">" + propertyname + ":</td>");
                                 }
                                 String propertyvaluename = DaoFactory.getPropertysDaoImplJDBC().get(propertyArr[1]).getFieldValue("svname");
                                 param.append("<td bgcolor=\"#F2F2F2\"><div align=\"center\">" + propertyvaluename + "</div></td>");
                                 }
                                 param.append("</tr>");
                                 param.append("</table>");
                                 out.print(param);
                                 }
                                 */
                                int pid = 0;
                                if (null != property) {
                                    out.print(new PublicService().getPsvDiv(property, signid, pid, wx));
                                }
                            %>
                        </div>

                    </td>

                </tr>

                <!--属性标签结束-->

                <!--wxid,distributionmoney,distributionfirstdiscount,distributionseconddiscount,distributionthirddiscount,distributiontype-->

                <tr>
                    <td width="135" bgcolor="#FDFDFD"><div align="right">分销方式：</div></td>
                    <td width="828" bgcolor="#FDFDFD"><div align="left">
                            <input name="distributiontype" type="radio" style="width:66px; border:1px solid #666666;" maxlength="250" value="0" checked="checked"/>按金额&nbsp;
                            <input name="distributiontype" type="radio" style="width:66px; border:1px solid #666666;" maxlength="250" value="1" />按比例
                        </div></td>
                </tr>
                <!--                <tr>
                                    <td width="135" bgcolor="#FDFDFD"><div align="right">分销金额：</div></td>
                                    <td width="828" bgcolor="#FDFDFD"><div align="left">
                                            <input name="distributionmoney" type="text" id="distributionmoney" style="width:246px; border:1px solid #666666;" maxlength="250" />
                                        </div></td>
                                </tr>-->
                <tr>
                    <td width="135" bgcolor="#FDFDFD"><div align="right">一级分销：</div></td>
                    <td width="828" bgcolor="#FDFDFD"><div align="left">
                            <input name="distributionfirstdiscount" type="text" id="distributionfirstdiscount" style="width:246px; border:1px solid #666666;" maxlength="250" value="0"/>
                        </div></td>
                </tr>
                <tr>
                    <td width="135" bgcolor="#FDFDFD"><div align="right">二级分销：</div></td>
                    <td width="828" bgcolor="#FDFDFD"><div align="left">
                            <input name="distributionseconddiscount" type="text" id="distributionseconddiscount" style="width:246px; border:1px solid #666666;" maxlength="250" value="0"/>
                        </div></td>
                </tr>
                <tr>
                    <td width="135" bgcolor="#FDFDFD"><div align="right">三级分销：</div></td>
                    <td width="828" bgcolor="#FDFDFD"><div align="left">
                            <input name="distributionthirddiscount" type="text" id="distributionthirddiscount" style="width:246px; border:1px solid #666666;" maxlength="250" value="0"/>
                        </div></td>
                </tr>


                <!--                <tr>
                
                                    <td align="right" bgcolor="#FDFDFD">  首页位置：</td>
                                    <td bgcolor="#FDFDFD"> 
                                        <select name="IsNew" id="IsNew">
                                            <option value="1" selected="selected" >NO</option>
                                            <option value="2" >首页</option>
                
                                        </select>
                                                               生产日期:<input type="text" name="RiQi" id="RiQi" value="2014-11-11至2018-11-11" />如2014-11-11至2018-11-11
                                </tr>-->
                <tr>
                    <td align="right" bgcolor="#FDFDFD">库存:</td>
                    <td bgcolor="#FDFDFD">

                        <input type="text" name="IsRem" id="IsRem" value="1" />
                        <!--                        快递费用：￥<input type="text" name="KuaiDi" id="KuaiDi" value="0.00" />*只能接收数值如12.00；0.00为免运费
                                                *以上数据请不要含有符号“，”-->
                        <!--                        开始时间： <input class="easyui-datetimebox" type="text" name="beginTime" id="beginTime"/>
                                                结束时间： <input class="easyui-datetimebox" type="text" name="endTime" id="endTime"/>*以上数据请不要含有符号“，”-->
                    </td>
                </tr>
                <tr>
                    <td align="right" bgcolor="#FDFDFD">库存预警量:</td>
                    <td bgcolor="#FDFDFD">
                        <input type="tel" name="JiangShi" id="JiangShi" value="1" />
                    </td>
                </tr>
                <!-- <tr>
                     <td align="right" bgcolor="#FDFDFD">关键字：</td>
                     <td bgcolor="#FDFDFD"><textarea name="Keywords" cols="45" rows="5" class="textarea_normal" id="Keywords"></textarea></td>
                 </tr>-->
                <!--                <tr>
                                    <td align="right" bgcolor="#FDFDFD">描述：</td>
                                    <td bgcolor="#FDFDFD"><textarea name="Descr" cols="45" rows="5" class="textarea_normal" id="Descr"></textarea></td>
                                </tr>-->
                <!-- <tr>
                     <td align="right" bgcolor="#FDFDFD">用途：</td>
                     <td bgcolor="#FDFDFD"><textarea name="YongTu" cols="45" rows="5" class="textarea_normal" id="YongTu"></textarea></td>
                 </tr>-->
                <tr>
                    <td align="right" bgcolor="#FDFDFD">备注：</td>
                    <td bgcolor="#FDFDFD"><textarea name="Remarks" cols="45" rows="5" class="textarea_normal" id="Remarks"></textarea></td>
                </tr>
                <!-- <tr>
                     <td width="135" bgcolor="#FDFDFD"><div align="right">网页标题：</div></td>
                     <td width="828" bgcolor="#FDFDFD"><div align="left">
                             <input name="WebTitle" type="text" id="WebTitle" style="width:246px; border:1px solid #666666;" maxlength="250" />
                         </div></td>
                 </tr>-->
                <!-- <tr>
                     <td align="right" bgcolor="#FDFDFD">网页关键字：</td>
                     <td bgcolor="#FDFDFD"><textarea name="WebKeywords" cols="45" rows="5" class="textarea_normal" id="WebKeywords"></textarea></td>
                 </tr>-->
                <!-- <tr>
                     <td align="right" bgcolor="#FDFDFD">网页描述：</td>
                     <td bgcolor="#FDFDFD"><textarea name="WebDescr" cols="45" rows="5" class="textarea_normal" id="WebDescr"></textarea></td>
                 </tr>-->
                <tr>
                    <td valign="top" bgcolor="#FDFDFD"><div align="right">商品详情：</div></td>
                    <td bgcolor="#FDFDFD">
                        <textarea name="Content" style="display:none; "></textarea>
                        <script charset="utf-8" src="/kindeditor/kindeditor.js"></script>
                        <script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
                        <script>
            var editor1;
            KindEditor.ready(function(K) {
                editor1 = K.create('textarea[name="Content"]', {
                    uploadJson: '/kindeditor/jsp/upload_json.jsp',
                    fileManagerJson: '/kindeditor/jsp/file_manager_json.jsp',
                    allowFileManager: true,
                    width: '100%',
                    height: '300px'
                });
            });
                        </script>
                    </td>
                </tr>
                <tr>
                    <td bgcolor="#FDFDFD"><div align="center"></div></td>
                    <td height="55" bgcolor="#FDFDFD"><input name="Submit" type="submit" class="btn_submit" value="确 定" />
                        &nbsp;
                        <input name="Submit2" type="button" class="btn_cancel" onclick="javascript:history.back();" value="取 消" /></td>
                </tr>
            </table>
        </form>
        <script type="text/javascript">
            $(document).ready(function() {
                $("#propertybutton").click(function() {
                    var signid = $("#signid").val();
                    var propertyname = $("#propertyname").val();
                    if ("" == propertyname) {
                        alert("属性名称不能为空!");
                        return false;
                    } else {
                        var temppropertysign = $("#temppropertysign").val();
                        if ("0" == temppropertysign) {
                            $.post("p_add_mod.jsp", {"pid": "0", "act": "addproperty", "signid": signid, "propertyname": propertyname, "wxsid": "<%=wx.get("id")%>"}, function(result) {
                                if ("-1" == result.trim()) {
                                    alert("添加失败，请重试！");
                                } else {
                                    $("#shuxing").prepend("<option value='" + result.trim() + "' selected>" + propertyname + "</option>");
                                    $("#shuxinghidden").val(result.trim());
                                    $("#shuxing_value").html("");
                                }
                            });
                            $("#propertyadd").css("display", "none");
                        } else {

                            $.post("p_add_mod.jsp", {"pid": $("#shuxinghidden").val(), "act": "addproperty", "signid": signid, "propertyname": propertyname, "wxsid": "<%=wx.get("id")%>"}, function(result) {
                                if ("-1" == result.trim()) {
                                    alert("添加失败，请重试！");
                                } else {
                                    $("#shuxing_value").append("<input name =\"propertycheckbox\" id=\"propertycheckbox\" type=\"checkbox\" value=\"" + result.trim() + "\"> " + propertyname + "</input><!--属性标签-->");
                                    $("#shuxing_value").css("display", "block");
                                }
                                $("#propertyadd").css("display", "none");
                            });
                        }
                    }
                });
            });
        </script>
    </body>
</html>
