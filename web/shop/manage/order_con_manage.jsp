<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<%
int CurrentPage=RequestUtil.getInt(request,"page");
int FSts=RequestUtil.getInt(request,"FSts");
int IsPay=RequestUtil.getInt(request,"IsPay");
String FNo=RequestUtil.getString(request,"FNo");
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
<title>消费管理</title>
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
</head>
<script>
function goUrl(frm)
{
	var gourl ="?FSts=<%=FSts%>&IsPay=<%=IsPay%>&FNo=<%=FNo%>&";
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
        状态：
        <input type="submit" name="button" id="button" value="查询" /></td>
    </tr>
  </table>
</form>

<form action="?FSts=<%=FSts%>&IsPay=<%=IsPay%>&FNo=<%=FNo%>&act=bat" method="post">
<table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A3B2CC">
  <tr>
    <td height="22" colspan="12" bgcolor="#FFFFFF">
	<span class="style3">消费管理</span>
	&nbsp;&nbsp;</td>	
  </tr>
  <tr>
    <td width="3%" height="22" bgcolor="#3872b2"><div align="center" class="style2"></div></td>
    <td width="13%" height="22" bgcolor="#3872b2"><div align="center" class="style2">订单号</div></td>
    <td width="7%" align="center" bgcolor="#3872b2" class="style2">价格</td>
    <td width="10%" align="center" bgcolor="#3872b2" class="style2">会员ID</td>
    <td width="9%" align="center" bgcolor="#3872b2" class="style2">IP</td>
    <td width="7%" align="center" bgcolor="#3872b2" class="style2">类型</td>
    <td width="7%" align="center" bgcolor="#3872b2" class="style2">支付</td>
    <td width="5%" align="center" bgcolor="#3872b2" class="style2">电话</td>
   <!-- <td width="13%" align="center" bgcolor="#3872b2" class="style2">地址</td>
    <td width="10%" align="center" bgcolor="#3872b2" class="style2">物流</td>
    <td width="8%" align="center" bgcolor="#3872b2" class="style2">淘客</td>-->
    <td width="8%" bgcolor="#3872b2"><div align="center" class="style2">日期</div></td>

    </tr>
  <%
  TotalNum=DaoFactory.getOrderDAO().getTotalNum(FSts,FNo,null,IsPay,3);
  PageNum=(TotalNum-1+PageSize)/PageSize;
  ArrayList list=(ArrayList)DaoFactory.getOrderDAO().getList(FSts,FNo,null,IsPay,3,CurrentPage,PageSize);
  for (Iterator iter = list.iterator(); iter.hasNext(); ) {
  	DataField df=(DataField)iter.next();
	String id=df.getFieldValue("F_No");	
  %>
  <tr>
    <td bgcolor="#FDFDFD">
    <div align="center"><input name="objid" type="checkbox" value="<%=id%>" /></div></td>
    <td align="center" bgcolor="#FDFDFD"><a href="order_detail.jsp?id=<%=id%>"><%=id%></a></td>
    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("F_Price")%></td>
    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("UserId")%></td>
    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("Ip")%></td>
    <td align="center" bgcolor="#FDFDFD">
   
			<%out.print("消费");%>
				
    </td>
    <td align="center" bgcolor="#FDFDFD"><%if(df.getInt("IsPay")==1){%>在线支付<%}else if(df.getInt("IsPay")==2){%>等待支付<%}else if(df.getInt("IsPay")==3){%>上门支付<%} else{%>余额支付<%}%></td>
    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("F_Name")%><br /><%=df.getString("F_Tel")%></td>
   <!-- <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("F_Address")%></td>
    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("ShipNo")%><br /></td>
    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("Taoke")%></td> -->
    <td bgcolor="#FDFDFD"><div align="center"><%=df.getFieldValue("F_Date")%></div></td>
    
    </tr>
  <%
  	}
  %>
  <tr>
    <td colspan="12" valign="top" bgcolor="#FDFDFD"><div align="center">
      <select name="actType">
        <option value="bat">批量删除</option>
      </select>
      <input class="Button" type="button" name="chkall" value="全选" onclick="CheckAll(this.form)" />
      <input class="Button" type="button" name="chksel" value="反选" onclick="ContraSel(this.form)" />
      <input type="submit" name="Submit3" value="执行" onClick="return confirm('您确认执行此操作？');" />
    </div></td>
    </tr>
</table>
</form>
<form>
<div align="center"><%=CurrentPage%>/<%=PageNum%>&nbsp;&nbsp;共:<%=TotalNum%>&nbsp;&nbsp;
  <%if(CurrentPage>1){%>
    <a href="?FSts=<%=FSts%>&IsPay=<%=IsPay%>&FNo=<%=FNo%>&page=<%=CurrentPage-1%>">上一页</a>&nbsp;&nbsp;
    <%}else{%>
  上一页&nbsp;&nbsp;
  <%}%>
  <%if(CurrentPage>=PageNum){%>
  下一页
  <%}else{%>
  <a href="?FSts=<%=FSts%>&IsPay=<%=IsPay%>&FNo=<%=FNo%>&page=<%=CurrentPage+1%>">下一页</a>
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
