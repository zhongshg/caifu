<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<%
int CurrentPage=RequestUtil.getInt(request,"page");
int FSts=RequestUtil.getInt(request,"FSts");
int IsPay=RequestUtil.getInt(request,"IsPay");
int State=RequestUtil.getInt(request, "State");
String FNo=RequestUtil.getString(request,"FNo");
String FromDate=RequestUtil.getString(request,"FromDate");
String ToDate=RequestUtil.getString(request, "ToDate");
String FromDateZhuan="";
String ToDateZhuan="";
if(FromDate!=null &&!FromDate.equals("null") && !FromDate.equals("") &&  ToDate!=null && !ToDate.equals("null")  && !ToDate.equals("")){
FromDateZhuan=DateUtil.timezhuan(FromDate);
ToDateZhuan=DateUtil.timezhuan(ToDate);
}

if(FNo==null) FNo="";
if(CurrentPage<1) CurrentPage=1;
int PageSize=20;
int TotalNum=0;
int PageNum=0;
String act=RequestUtil.getString(request,"act");
if(act!=null){
	if(act.equals("bat")){
		String[] objid=request.getParameterValues("objid");
		if(objid!=null){
			DaoFactory.getOrderDAO().del(objid);
			out.print("<p align=\"center\">批量删除成功!</p>");
		}else{
			out.print("<p align=\"center\">请选择要操作的选项!</p>");
		}
	}	
}
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
<title>订单管理</title>
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
	var gourl ="?FSts=<%=FSts%>&IsPay=<%=IsPay%>&FNo=<%=FNo%>&State=<%=State%>&FromDate=<%=FromDate%>&ToDate=<%=ToDate%>&";
	gourl += "page=" + (frm.page.value);
     var hid=parseInt(frm.hid.value);
	 if(parseInt(frm.page.value)>hid||frm.page.value<=0){
	 alert("1-"+hid);
	 return false;
	 }
	window.location.href=gourl;
}
</script>
<body>
<%@ include file="top.jsp"%>
<form action="" method="get">
  <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="32">订单号：
        <input type="text" name="FNo" id="FNo" value="<%=FNo==null?"":FNo%>" />
        订单状态：
        <select name="FSts" id="FSts">
          <option value="0">...</option>
          <option value="1"<%if(FSts==1) out.print(" selected=\"selected\"");%>>未发货</option>
          <option value="2"<%if(FSts==2) out.print(" selected=\"selected\"");%>>已发货</option>
          <option value="3"<%if(FSts==3) out.print(" selected=\"selected\"");%>>确认收货</option>
          <option value="4"<%if(FSts==4) out.print(" selected=\"selected\"");%>>未评价</option>
           <option value="5"<%if(FSts==5) out.print(" selected=\"selected\"");%>>已评价</option>
            <option value="6"<%if(FSts==6) out.print(" selected=\"selected\"");%>>退货中</option>
             <option value="7"<%if(FSts==7) out.print(" selected=\"selected\"");%>>已退货</option>
        </select>
        支付状态：
        <select name="IsPay" id="IsPay">
          <option value="-1">...</option>
          <option value="1"<%if(IsPay==1) out.print(" selected=\"selected\"");%>>未支付</option>
          <option value="2"<%if(IsPay==2) out.print(" selected=\"selected\"");%>>已支付</option>
           <option value="3"<%if(IsPay==3) out.print(" selected=\"selected\"");%>>货到付款</option>        
        </select>
         交易状态：
        <select name="State" id="State">
          <option value="0">...</option>
          <option value="1"<%if(State==1) out.print(" selected=\"selected\"");%>>未完成</option>
          <option value="2"<%if(State==2) out.print(" selected=\"selected\"");%>>已完成</option>
          
        </select>
          时间： <input class="easyui-datetimebox" type="text" name="FromDate" id="FromDate" value="<%=FromDate==null||FromDate.equals("null")?"":FromDate%>"/>-
                 <input class="easyui-datetimebox" type="text" name="ToDate" id="ToDate" value="<%=ToDate==null||ToDate.equals("null")?"":ToDate%>"/>
        <input type="submit" name="button" id="button" value="查询" /></td>
    </tr>
  </table>
</form>

<form action="?FSts=<%=FSts%>&IsPay=<%=IsPay%>&FNo=<%=FNo%>&State=<%=State%>&act=bat" method="post">
<table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A3B2CC">
  <tr>
    <td height="22" colspan="12" bgcolor="#FFFFFF">
	<span class="style3">订单管理</span>
	&nbsp;&nbsp;</td>	
  </tr>
  <tr>
    <td width="10%" height="22" bgcolor="#3872b2"><div align="center" class="style2">产品图片</div></td>
     <td width="15%" align="center" bgcolor="#3872b2" class="style2">产品名称</td>
    <td width="10%" height="22" bgcolor="#3872b2"><div align="center" class="style2">订单号</div></td>
    <td width="7%" align="center" bgcolor="#3872b2" class="style2">价格</td>
    <td width="10%" align="center" bgcolor="#3872b2" class="style2">会员ID</td>
    <td width="6%" align="center" bgcolor="#3872b2" class="style2">交易状态</td>
    <td width="5%" align="center" bgcolor="#3872b2" class="style2">订单状态</td>
    <td width="5%" align="center" bgcolor="#3872b2" class="style2">支付</td>
    <td width="5%" align="center" bgcolor="#3872b2" class="style2">姓名</td>
    <td width="13%" align="center" bgcolor="#3872b2" class="style2">地址</td>
   
    <td width="8%" bgcolor="#3872b2"><div align="center" class="style2">日期</div></td>
    <!--<td width="8%" align="center" bgcolor="#3872b2" class="style2">淘客</td>-->
    </tr>
  <%
  TotalNum=DaoFactory.getOrderDAO().getTotalNumDate(FSts,State,1,0,FNo,null,wx.get("id"),IsPay,0,FromDateZhuan,ToDateZhuan);
 
  PageNum=(TotalNum-1+PageSize)/PageSize;
    ArrayList listpro=null;
    DataField dfpro=null;
  ArrayList list=(ArrayList)DaoFactory.getOrderDAO().getListDate(FSts,State,1,0,FNo,null,wx.get("id"),IsPay,0,FromDateZhuan,ToDateZhuan,CurrentPage,PageSize);
  float price=0.0f;
  for (Iterator iter = list.iterator(); iter.hasNext(); ) {
  	DataField df=(DataField)iter.next();
	String id=df.getFieldValue("F_No");
       // price=price+df.getFloat("F_Price");
         price=(float) (Math.round((price+df.getFloat("F_Price")) * 100)) / 100;
        listpro=(ArrayList) DaoFactory.getBasketDAO().getListByOrderNo(id);
        for(Iterator iterpro=listpro.iterator();iterpro.hasNext();){
            dfpro=(DataField)iterpro.next();
        
     
  %>
  <tr>
    <td bgcolor="#FDFDFD">
    <div align="center"><img src="<%=dfpro.getString("ViewImg")%>" width="66" height="59" border="0" /></div></td>
       <td bgcolor="#FDFDFD">
    <div align="center"><%=dfpro.getString("Pname")%></div></td>
    <td align="center" bgcolor="#FDFDFD"><a href="order_detail.jsp?id=<%=id%>"><%=id%></a></td>
    <td align="center" bgcolor="#FDFDFD"><%=dfpro.getFieldValue("Per_Price")%></td>
    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("UserId")%></td>
    <td align="center" bgcolor="#FDFDFD">  <%
                                        switch (df.getInt ("State")) {
                                            case 1:
                                                out.print("未完成");
                                                break;
                                            case 2:
                                                out.print("已完成");
                                                break;
                                                                               
                                        }
                                    %></td>
    <td align="center" bgcolor="#FDFDFD">
     <%
                                        switch (df.getInt("Sts")) {
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
    </td>
    <td align="center" bgcolor="#FDFDFD"><%if(df.getInt("IsPay")==1){%>未支付<%}else if(df.getInt("IsPay")==2){%>已支付<%}else if(df.getInt("IsPay")==3){%>货到付款<%} %></td>
    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("F_Name")%><br /><%=df.getString("F_Mobile")%></td>
    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("F_Address")%></td>
   
    <td bgcolor="#FDFDFD"><div align="center"><%=df.getFieldValue("F_Date")%></div></td>
 
    </tr>
  <%}
  	}
  %>
  <tr>
      <td colspan="12" valign="top" bgcolor="#FDFDFD"><div align="center">金额：￥<%=price%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<!--      <select name="actType">
        <option value="bat">批量删除</option>
      </select>
      <input class="Button" type="button" name="chkall" value="全选" onclick="CheckAll(this.form)" />
      <input class="Button" type="button" name="chksel" value="反选" onclick="ContraSel(this.form)" />
      <input type="submit" name="Submit3" value="执行" onClick="return confirm('您确认执行此操作？');" />-->
    </div></td>
    </tr>
</table>
</form>
<form>
<div align="center"><%=CurrentPage%>/<%=PageNum%>&nbsp;&nbsp;共:<%=TotalNum%>&nbsp;&nbsp;
  <%if(CurrentPage>1){%>
    <a href="?FSts=<%=FSts%>&IsPay=<%=IsPay%>&FNo=<%=FNo%>&State=<%=State%>&page=<%=CurrentPage-1%>&FromDate=<%=FromDate%>&ToDate=<%=ToDate%>">上一页</a>&nbsp;&nbsp;
    <%}else{%>
  上一页&nbsp;&nbsp;
  <%}%>
  <%if(CurrentPage>=PageNum){%>
  下一页
  <%}else{%>
  <a href="?FSts=<%=FSts%>&IsPay=<%=IsPay%>&FNo=<%=FNo%>&State=<%=State%>&page=<%=CurrentPage+1%>&FromDate=<%=FromDate%>&ToDate=<%=ToDate%>">下一页</a>
  <%}%>
  跳转：
  <input type="hidden" name="hid" value="<%=PageNum%>" />
  <input name="page" type="text" size="2" />
  <input type="button" name="Button2" onclick="goUrl(this.form)" value="GO" style="font-size:12px " />
</div>
</form>
<script language="javascript">
function CheckAll(form) {  
	for (var i=0;i<form.elements.length;i++)  {  
		var e = form.elements[i];  
		if (e.name == 'objid')  
		e.checked = true // form.chkall.checked;  
	}  
} 
 
function ContraSel(form) {
	for (var i=0;i<form.elements.length;i++){
		var e = form.elements[i];
		if (e.name == 'objid')
		e.checked=!e.checked;
	}
}
</script>
</body>
</html>
