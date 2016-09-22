<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<%
int CurrentPage=RequestUtil.getInt(request,"page");
int FSts=RequestUtil.getInt(request,"FSts");
int IsPay=RequestUtil.getInt(request,"IsPay");
int State=RequestUtil.getInt(request, "State");
int Agree=RequestUtil.getInt(request,"Agree");
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
<title>课程订单管理</title>
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
	var gourl ="?FSts=<%=FSts%>&IsPay=<%=IsPay%>&FNo=<%=FNo%>&State=<%=State%>&Agree=<%=Agree%>&";
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
          <option value="4"<%if(FSts==4) out.print(" selected=\"selected\"");%>>未评价</option>
           <option value="5"<%if(FSts==5) out.print(" selected=\"selected\"");%>>已评价</option>            
        </select>
        支付状态：
        <select name="IsPay" id="IsPay">
          <option value="-1">...</option>
          <option value="1"<%if(IsPay==1) out.print(" selected=\"selected\"");%>>未支付</option>
          <option value="2"<%if(IsPay==2) out.print(" selected=\"selected\"");%>>已支付</option>
           <option value="3"<%if(IsPay==3) out.print(" selected=\"selected\"");%>>站内币支付</option>        
        </select>
         授权状态：
        <select name="Agree" id="Agree">
          <option value="0">...</option>
          <option value="1"<%if(Agree==1) out.print(" selected=\"selected\"");%>>未授权</option>
          <option value="2"<%if(Agree==2) out.print(" selected=\"selected\"");%>>已授权</option>  
        </select>
         交易状态：
        <select name="State" id="State">
          <option value="0">...</option>
          <option value="1"<%if(State==1) out.print(" selected=\"selected\"");%>>未完成</option>
          <option value="2"<%if(State==2) out.print(" selected=\"selected\"");%>>已完成</option>  
        </select>
        <input type="submit" name="button" id="button" value="查询" /></td>
    </tr>
  </table>
</form>

<form action="?FSts=<%=FSts%>&IsPay=<%=IsPay%>&FNo=<%=FNo%>&State=<%=State%>&act=bat" method="post">
<table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A3B2CC">
  <tr>
    <td height="22" colspan="11" bgcolor="#FFFFFF">
	<span class="style3">订单管理</span>
	&nbsp;&nbsp;</td>	
  </tr>
  <tr>
    <td width="3%" height="22" bgcolor="#3872b2"><div align="center" class="style2"></div></td>
    <td width="13%" height="22" bgcolor="#3872b2"><div align="center" class="style2">订单号</div></td>
    <td width="7%" align="center" bgcolor="#3872b2" class="style2">价格</td>
    <td width="10%" align="center" bgcolor="#3872b2" class="style2">会员ID</td>
    <td width="6%" align="center" bgcolor="#3872b2" class="style2">交易状态</td>
    <td width="6%" align="center" bgcolor="#3872b2" class="style2">订单状态</td>
    <td width="6%" align="center" bgcolor="#3872b2" class="style2">支付状态</td>
    <td width="5%" align="center" bgcolor="#3872b2" class="style2">是否授权</td>
    <td width="5%" align="center" bgcolor="#3872b2" class="style2">姓名</td>
    <td width="13%" align="center" bgcolor="#3872b2" class="style2">地址</td>
   
    <td width="8%" bgcolor="#3872b2"><div align="center" class="style2">日期</div></td>
    <!--<td width="8%" align="center" bgcolor="#3872b2" class="style2">淘客</td>-->
    </tr>
  <%
  TotalNum=DaoFactory.getOrderDAO().getTotalNum(FSts,State,2,Agree,FNo,null,null,IsPay,0);
  PageNum=(TotalNum-1+PageSize)/PageSize;
  ArrayList list=(ArrayList)DaoFactory.getOrderDAO().getList(FSts,State,2,Agree,FNo,null,null,IsPay,0,CurrentPage,PageSize);
  for (Iterator iter = list.iterator(); iter.hasNext(); ) {
  	DataField df=(DataField)iter.next();
	String id=df.getFieldValue("F_No");
     
  %>
  <tr>
    <td bgcolor="#FDFDFD">
    <div align="center"><input name="objid" type="checkbox" value="<%=id%>" /></div></td>
    <td align="center" bgcolor="#FDFDFD"><a href="order_kcdetail.jsp?id=<%=id%>"><%=id%></a></td>
    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("F_Price")%></td>
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
                                            case 4:
                                                out.print("未评价");
                                                break;
                                            case 5:
                                                out.print("已评价");
                                                break;
                                                                                
                                        }
                                    %>
    </td>
    <td align="center" bgcolor="#FDFDFD"><%if(df.getInt("IsPay")==1){%>未支付<%}else if(df.getInt("IsPay")==2){%>已支付<%}else if(df.getInt("IsPay")==3){%>站内币支付<%} %></td>
    <td align="center" bgcolor="#FDFDFD">
     <%
                                        switch (df.getInt("Agree")) {                                         
                                            case 1:
                                                out.print("未授权");
                                                break;
                                            case 2:
                                                out.print("已授权");
                                                break;
                                                                                
                                        }
                                    %>
    </td>
    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("F_Name")%><br /><%=df.getString("F_Mobile")%></td>
    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("F_Address")%></td>
   
    <td bgcolor="#FDFDFD"><div align="center"><%=df.getFieldValue("F_Date")%></div></td>
  <!--  <td align="center" bgcolor="#FDFDFD"><a href="u_modtk.jsp?taokeid=<%=df.getFieldValue("TaoKe")%>"><%=df.getFieldValue("TaoKe")%></a></td>-->
    </tr>
  <%
  	}
  %>
  <tr>
    <td colspan="11" valign="top" bgcolor="#FDFDFD"><div align="center">
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
    <a href="?FSts=<%=FSts%>&IsPay=<%=IsPay%>&FNo=<%=FNo%>&State=<%=State%>&page=<%=CurrentPage-1%>">上一页</a>&nbsp;&nbsp;
    <%}else{%>
  上一页&nbsp;&nbsp;
  <%}%>
  <%if(CurrentPage>=PageNum){%>
  下一页
  <%}else{%>
  <a href="?FSts=<%=FSts%>&IsPay=<%=IsPay%>&FNo=<%=FNo%>&State=<%=State%>&page=<%=CurrentPage+1%>">下一页</a>
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
