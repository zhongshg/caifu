<%@page import="org.apache.lucene.document.DateField"%>
<%@page import="java.awt.event.ItemEvent"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="error.jsp" %>
<%@ include file="inc/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!doctype html>
<html>
    <%
    float Bas_Price=0.0f;
    float Per_Price=0.0f;
    String[] objid=request.getParameterValues("objid");
    if(objid!=null){
         session.setAttribute("objid",objid); 
    } 
     String UserId=null;
    if((String)session.getAttribute("totjjob_userid")!=null ){
    UserId=(String)session.getAttribute("totjjob_userid");
    }
    float Price=0.0f;
    ArrayList list=null;   
    DataField df=null;
    %>
<head>
<meta charset="utf-8">
<title>订单</title>
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
<link href="zhongjian/css/tijiaodingdan.css" rel="stylesheet" type="text/css">
<link href="zhongjian/css/global_style.css" rel="stylesheet" type="text/css">
<script src="zhongjian/js/index_gezhong.js"></script>
 <script src="zhongjian/js/jquery-1.7.2.min.js"></script>
</head>
<script type="text/javascript">
    //页面自动加载地址默认选择
   
   window.onload=function(){
         var address=$.trim($("input[name='Address']:checked").val());
         var a=address.split(",");
            document.getElementById("zhanshiprice").innerHTML=a[0]+"("+a[1]+"收)"+a[2];
            document.getElementById("F_Address").value=a[0];
            document.getElementById("F_Name").value=a[1];
            document.getElementById("F_Phone").value=a[2];
              document.getElementById("TSF_Price").value=a[3];
               document.getElementById("yunfei").innerHTML=a[3];
    }
     </script>
<script type="text/javascript">
    //省市县三级级联
         function getXMLRequester( )
                        {
                            var xmlhttp_request = false;
                            try {
                                if (window.ActiveXObject)
                                {
                                    for (var i = 5; i; i--) {
                                        try {
                                            if (i == 2)
                                            {
                                                xmlhttp_request = new ActiveXObject("Microsoft.XMLHTTP");
                                            }
                                            else
                                            {
                                                xmlhttp_request = new ActiveXObject("Msxml2.XMLHTTP." + i + ".0");
                                                xmlhttp_request.setRequestHeader("Content-Type", "text/xml");
                                                xmlhttp_request.setRequestHeader("Content-Type", "gb2312");
                                            }
                                            break;
                                        }
                                        catch (e) {
                                            xmlhttp_request = false;
                                        }
                                    }
                                }
                                else if (window.XMLHttpRequest)
                                {
                                    xmlhttp_request = new XMLHttpRequest();
                                    if (xmlhttp_request.overrideMimeType)
                                    {
                                        xmlhttp_request.overrideMimeType('text/xml');
                                    }
                                }
                            }
                            catch (e) {
                                xmlhttp_request = false;
                            }
                            return xmlhttp_request;
                        }

                        function GetEduArea(areaid, eduareaid, jilian) {
                            //var id=document.getElementById();
                            var areaSelect = document.getElementById(jilian);
                            // alert(jilian);
                            areaSelect.options.length = 0;
                            var xmlhttp;
                            xmlhttp = getXMLRequester();
                            if (xmlhttp) {
                                xmlhttp.onreadystatechange = function doAction() {
                                    if (xmlhttp.readyState == 4)
                                    {
                                        /*alert(xmlhttp.responseText);*/
                                        var response = xmlhttp.responseXML.documentElement;
                                        var classidList = response.getElementsByTagName('area');
                                        areaSelect.options[0] = new Option("选择片区", 0);
                                        for (var i = 0; i < classidList.length; i++) {
                                            xx = classidList[i].getElementsByTagName('id');
                                            yy = classidList[i].getElementsByTagName('title');
                                            classid = xx[0].firstChild.data;
                                            classtitle = yy[0].firstChild.data;
                                            //alert( classid);
                                            areaSelect.options[i + 1] = new Option(classtitle, classid);
                                            if (eduareaid == classid) {
                                                areaSelect.options[i + 1].selected = true;
                                            }
                                        }
                                    }
                                };
                                xmlhttp.open("POST", "${pageContext.servletContext.contextPath}/test.jsp?id=" + areaid + "&jilian=" + jilian, true);
                                xmlhttp.send("");
                            }
                        }
    </script>

<script type="text/javascript">
    $(document).ready(function() {
        $("#guanbi2").click(function() {
            var Sname = $("#Sname").val().trim();
            var Ssname = $("#Ssname").val().trim();
            var Xname = $("#Xname").val().trim();
            var Xname = $("#Xname").val().trim();
            var FullAddress = $("#fulladdress").val().trim();
            var FullName=$("#fullname").val().trim();
            var FullPhone=$("#fullphone").val().trim();
            var FullTelePhone=$("#fulltelephone").val().trim();
            var DefaultAddress;
        
            if ("" == Sname || Sname==null || Sname==0) {
                alert("请输入省级！");
            } else if ("" == Ssname || Ssname==null || Ssname==0) {
                alert("请输入市级！");
            } else if ("" == Xname || Xname==null || Xname==0) {
                alert("请输入县区！");
            } else if(FullAddress==null || FullAddress==""){
              $("#fulladdress_error").show();
               $("#fullname_error").hide();
              $("#fullphone_error").hide();
            } else if(FullName==null || FullName==""){
               $("#fullname_error").show();
                $("#fulladdress_error").hide();
                 $("#fullphone_error").hide();
            } else if(FullPhone==null || FullPhone==""){
                $("#fullphone_error").show();
                  $("#fullname_error").hide();
                $("#fulladdress_error").hide();
            }
            else {
                
                 if (FullPhone != "" && FullPhone!=null) {
                     $("#fullname_error").hide();
                           $("#fulladdress_error").hide();
                        if (!/^(13|15|18)[0-9]{9}$/.test(FullPhone)) {
                              $("#fullphone_error").show();
                            
                            $("#fullphone").focus();
                         
                            return false;
                        }
                    }
                      $("#fullphone_error").hide();
                  $("#fullname_error").hide();
                $("#fulladdress_error").hide();
                if ($("#J_SetDefault").attr("checked")) {
                    DefaultAddress = 1;
                } else {
                    DefaultAddress = 2;
                }
                $.post(
                        "order_do.jsp?act=address",
                        {
                            "Sname": Sname,
                            "Ssname":Ssname,
                            "Xname": Xname,
                            "DefaultAddress":DefaultAddress,
                            "FullAddress":FullAddress,
                            "FullName":FullName,
                            "FullPhone":FullPhone,
                            "FullTelePhone":FullTelePhone                  
                        },
                function(result) {
                    if (1 != result) {
                        alert("地址添加失败！");
                    } else {
                     
	                 $("#huei2").fadeOut(300);
	                  $("#chuangkou2").fadeOut(300);
		           $(window).unbind();  
                        
                        window.location.href = "/order.jsp";
                    }
                });
            }
        });
       
    });
    function xuanze(){
            var address=$.trim($("input[name='Address']:checked").val());
             var a=address.split(",");
            document.getElementById("zhanshiprice").innerHTML=address;
             document.getElementById("F_Address").value=a[0];
            document.getElementById("F_Name").value=a[1];
            document.getElementById("F_Phone").value=a[2];
               document.getElementById("TSF_Price").value=a[3];
               document.getElementById("yunfei").innerHTML=a[3];
         
    }
</script>
   
   
<%
String act=RequestUtil.getString(request, "act");
if(act!=null && ("do").equals(act)){
     java.util.Date currentTime = new java.util.Date();
    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
    String dateString = formatter.format(currentTime);
    String F_No = dateString + StringUtils.getRandomNumber(1, 9);
    Timestamp F_Date = DateUtil.getCurrentGMTTimestamp();
    String Ip = request.getRemoteAddr();
    float F_Price=RequestUtil.getFloat(request, "Price")+RequestUtil.getFloat(request, "TSF_Price");
    float TSF_Price=RequestUtil.getFloat(request,"TSF_Price");
    float TF_Price=RequestUtil.getFloat(request,"Bas_Price");
    float SF_Price=RequestUtil.getFloat(request, "Per_Price");
    float CF_Price=TF_Price-SF_Price;
    String F_Name=RequestUtil.getString(request,"F_Name");
    String F_Address=RequestUtil.getString(request, "F_Address");
    String F_Mobile=RequestUtil.getString(request, "F_Phone");
    String F_Tel=RequestUtil.getString(request,"TelePhone");
    String LiuYan=RequestUtil.getString(request,"LiuYan"); 
     int PostType = 1; 
      if((String[])session.getAttribute("objid")!=null && !((String[])session.getAttribute("objid")).equals("") ){       
  objid=(String[])session.getAttribute("objid");
     boolean iso=DaoFactory.getOrderDAO().add(F_No, F_Date, F_Price, TSF_Price, TF_Price, SF_Price, CF_Price, UserId, wx.get("id"), Ip, 1, 0, 2, F_Name, F_Address, F_Mobile, F_Tel, null, null, null, 0, PostType, null, null, 0, 2, LiuYan);
    if(iso){
     DaoFactory.getBasketDAO().batnod(objid, F_No);
     session.removeAttribute("objid");
       out.print("<script> alert('提交成功！');</script>");
          out.print("<script>parent.location.href='index.jsp';</script>");
        //response.sendRedirect("/index.jsp");
       
    }
      }else{
          out.print("<script> alert('订单错误！');</script>");
             out.print("<script>parent.location.href='order.jsp';</script>");
           //response.sendRedirect("/order.jsp");
     }

   
    

}
%>
<body style=" position:relative">
<div data-role="page">
<div data-role="header" data-theme="d">
    <%@include file="head.jsp" %>
</div>
<div data-role="content" data-theme="d">
  <div class="index_vessel ">
    <div class="goumuce">
      <div class="denglu">
        <table width="1154" border="0" cellspacing="0" cellpadding="0">
          <tbody>
            <tr>
              <td width="68">&nbsp;</td>
              <td width="295"><div class="zhuce_yuan">1</div>
                &nbsp;&nbsp;填写账户信息</td>
              <td width="327"><div class="huei_zhuce_yuan">2</div>
                &nbsp;&nbsp;登录支付宝</td>
              <td width="259"><div class="huei_zhuce_yuan">3</div>
                &nbsp;&nbsp;确认支付</td>
              <td width="205"><div class="huei_zhuce_yuan">3</div>
                &nbsp;&nbsp;双方互评</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="huadong_weikuang">
            <form id="dingdan" name="dingdan" method="post" action="order.jsp?act=do" >
        <div class="huadong1" id="f1">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tbody>
              <tr>
                <td colspan="3" >
                <br>
                <br>
                    <% 
                    list=(ArrayList)DaoFactory.getAddressDAO().getList(UserId);
                    if(list.size()>0 ){
                    for(Iterator iter=list.iterator();iter.hasNext();){
                    df=(DataField) iter.next();
                    %>
                    <label style="color:#999; font-family:'宋体'; font-size:9px;">
                        <input type="radio" name="Address" value="<%=df.getString("Address")%>,<%=df.getString("Name")%>,<%=df.getString("Phone")%>,<%=DaoFactory.getShengDao().getXian(df.getInt("Xid")).getFloat("Yfei") %>" <%if(df.getInt("MoRen")==1){%>checked<%}%> onclick="xuanze()" >
                     <%=df.getString("Address")%>(<%=df.getString("Name")%> 收) <%=df.getString("Phone")%></label>
                 <%}}%>
                       <div class="ui-grid-a">
     <div class="ui-block-a"><a href="" data-role="button" data-inline="true" data-corners="false" data-icon="forward" id="jiadizhi">添加新的地址+</a><br>
  </div>
     
                    </td>
                  
              </tr>
            </tbody>
          </table>
        </div>
        <div class="shangpinkuang">
        
        
        <div class="goumuce">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="28" height="25px">&nbsp;</td>
    <td width="170">&nbsp;</td>
    <td width="435">商品信息</td>
    <td width="150">单价（元）</td>
    <td  width="130">数量</td>
    <td width="158">金额（元）</td>
   
  </tr>
  <% 
  if((String[])session.getAttribute("objid")!=null && !((String[])session.getAttribute("objid")).equals("") ){       
  objid=(String[])session.getAttribute("objid");
  for(int i=0;i<objid.length;i++ ){   
      int id=Integer.parseInt(objid[i]);
      df=DaoFactory.getBasketDAO().getFNoId(id);   
    Price=Price+df.getFloat("Tot_Price");
Bas_Price=Bas_Price+df.getFloat("Bas_Price")*df.getInt("Pnum");
Per_Price=Per_Price+df.getFloat("Per_Price")*df.getInt("Pnum");
%>
   <tr>
    <td>&nbsp;</td>
    <td colspan="2" height="35">店铺：测试店铺000 &nbsp;&nbsp;&nbsp;<img src="zhongjian/images/huanguan.jpg" width="16" height="16"  alt=""/><img src="zhongjian/images/huanguan.jpg" width="16" height="16"  alt=""/></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  
  </tr>
  <tr>
    <td colspan="7" height="140"><div class="xuanzihou">
    
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="27" height="108">&nbsp;</td>
    <td width="169"><div class="tupian"><img src="<%=df.getString("ViewImg")%>" width="106" height="106"  alt=""/>
    </div></td>
    <td width="206"><%=df.getString("Pname")%></td>
     <td width="10" ></td>
            <td width="207"><span>
        <%=df.getString("Descr")%>
        </span></td>
      <td width="10" ></td>
    <td width="149"><strong><%=df.getFloat("Bas_Price")%></strong><br>
      <samp><%=df.getFloat("Sale_Price")%></samp><br>
     <div class="cuxiao"> 卖家促销</div></td>
    <td width="129" align="center"><p><%=df.getInt("Pnum")%></p></td>
    <td width="158"><div class="shiji"><%=df.getFloat("Tot_Price")%></div></td>
   
    </tr>
</table>

    </div></td>
    </tr>
   <%}}%>
</table>

  </div>
        
        
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tbody>
    <tr>
      <td width="44%" height="50"> <label for="fname" style="font-family:'宋体'; color:#646464; font-size:13px;">给卖家的留言：</label><input type="text" name="LiuYan" id="LiuYan"></td>
      <td width="56%" align="right" valign="middle">
        <div data-role="fieldcontain">
        <label for="switch" style="font-family:'宋体'; color:#646464; font-size:13px; line-height:40px;">选择配送方式：</label>
        <select name="switch" id="switch" data-role="slider">
          <option value="on" >到付</option>
          <option value="off" selected>快递</option>
        </select>
      </div>
      </td>
    </tr>
    <tr>
      <td height="60px" align="right"></td>
      <td align="right"><div style="border:#f1522b 1px solid; width:100%; height:60px;">
     实付款：<span style="color:#f1522b; font-size:16px; font-weight:bold"> ¥ <%=Price%></span><br>
         寄送至：<label id="zhanshiprice" name="zhanshiprice"></label><br/>
        运费：<label id="yunfei" name="yunfei"></label>
         <!--全部隐藏域-->
         <input type="hidden" id="Prcie" name="Price" value="<%=Price%>"/>
         <input type="hidden" id="F_Name" name="F_Name" value="Name"/>
         <input type="hidden" id="F_Phone" name="F_Phone" value="Phone"/>
         <input type="hidden" id="F_Address" name="F_Address" value="Address"/>
          <input type="hidden" id="TSF_Price" name="TSF_Price" value="0.0"/>
            <input type="hidden" id="Bas_Price" name="Bas_Price" value="0.0"/>
              <input type="hidden" id="Per_Price" name="Per_Price" value="0.0"/>

</div></td>
    </tr>
    <td>
    </td>
    <td align="right">  <a href="javascript:document:dingdan.submit();" data-role="button" data-inline="true" data-corners="false" style="background-color:#f1522b" data-icon="star" data-transition="slide">确认支付</a></td>
  </tbody>
</table>
        
        
        </div>
         </form>
       </div>
    </div>
  </div>
  <div data-role="footer" data-theme="d">
    <div style="background-color:#fff;">
      <div class="index_vessel" style=" background-color:#fff;">
          <%@include file="root.jsp" %>
      </div>
    </div>
  </div>
</div>
<div id="huei2" class="hueibeijing">
<div id="chuangkou2">
<div class="h4">&nbsp;&nbsp;&nbsp;添加新地址</div>
 <form id="dizhitijiao" name="dizhitijiao" method="post" action="order.jsp?act=address"  >
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="background-color:#fff">
  <tbody>
    <tr>
      <td width="14%" align="right"><span style=" color:#D70003">新增收货地址</span></td>
      <td width="50%">电话号码、手机号选填一项,其余均为必填项</td>
      <td width="36%">
      </td>
    </tr>
    <tr>
      <td align="right">所在地区：</td>
      <td colspan="2" >
        <fieldset data-role="controlgroup" data-type="horizontal">
            <label for="day"></label>
            <select name="Sname" id="Sname" onChange="GetEduArea(this.options[this.selectedIndex].value, 0, 'Ssname')">
              <option value="0">省名</option>
             <%
                                                list = (ArrayList) DaoFactory.getShengDao().getList();
                                                for (Iterator iter = list.iterator(); iter.hasNext();) {
                                                   df = (DataField) iter.next();
                                                        
                                            %>

                                            <option value="<%=df.getInt("S_id")%>"><%=df.getString("Sming")%></option>
                                            <%}%>
            </select>
           <label for="time"></label>
            <select name="Ssname" id="Ssname" onChange="GetEduArea(this.options[this.selectedIndex].value, 0, 'Xname')">
              <option value="0">地市</option>
             
            </select>
              <label for="time"></label>
            <select name="Xname" id="Xname">
              <option value="0">县区</option>
             
            </select>
        </fieldset></td>
        
    </tr>
       <tr>
      <td align="right">收货地址：</td>
      <td>  <input type="text" name="fulladdress" id="fulladdress"></td><br>
  <td><div id="fulladdress_error" name="fulladdress_error" style="display:none;">*请填写收货地址</div>
     
      </td>
    </tr>
    <tr>
      <td align="right">收货人姓名：</td>
      <td>  <input type="text" name="fullname" id="fullname"></td><br>
          <td><div id="fullname_error" name="fullname_error" style="display:none;">*请填写姓名</div>
             
      </td>
    </tr>
    <tr>
      <td align="right">手机号码：</td>
      <td>  <input type="text" name="fullphone" id="fullphone"></td>
        <td>
            <div id="fullphone_error" name="fullphone_error" style="display:none;">*请填写手机号</div>
           
      </td>
    </tr>
    <tr>
      <td align="right">电话号码：</td>
      <td>  <input type="text" name="fulltelephone" id="fulltelephone"></td>
        <td>
            <div id="fulltelephone_error" name="fulltelephone_eror" style="display:none;"></div>
            
      </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><input type="checkbox" name="J_SetDefault" id="J_SetDefault">
         
          <label for="J_SetDefault" >设置为默认收货地址</label></td>
            <td>
      </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td> <a href="#" data-role="button" data-inline="true" data-corners="false" id="guanbi2" name="guanbi2">保存</a></td>
        <td>
      </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
        <td>
      </td>
    </tr>
  </tbody>
</table>
 </form>

</div>
</div>
          <%@include file="bottom.jsp" %>



</body>

</body>
</html>


