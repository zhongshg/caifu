<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<%
int CurrentPage=RequestUtil.getInt(request,"page");
int FSts=RequestUtil.getInt(request,"FSts");
String FNo=RequestUtil.getString(request,"FNo");
String UserId=RequestUtil.getString(request, "UserId");
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
			DaoFactory.getFundsDao().del(objid);
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
<title>充值管理</title>
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
	var gourl ="?FSts=<%=FSts%>&FNo=<%=FNo%>&";
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
      <td height="32">单号：
        <input type="text" name="FNo" id="FNo" value="<%=FNo==null?"":FNo%>" />
        会员ID：
        <input type="text" name="UserId" id="UserId" value="<%=UserId==null?"":UserId%>" />
        状态：
        <select name="FSts" id="FSts">
          <option value="0">...</option>
          <option value="1"<%if(FSts==1) out.print(" selected=\"selected\"");%>>充值</option>
          <option value="2"<%if(FSts==2) out.print(" selected=\"selected\"");%>>完成</option>
        
        </select>
            
        <input type="submit" name="button" id="button" value="查询" /></td>
    </tr>
  </table>
</form>

<form action="?FSts=<%=FSts%>&FNo=<%=FNo%>&UserId=<%=UserId%>&act=bat" method="post">
<table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A3B2CC">
  <tr>
    <td height="22" colspan="9" bgcolor="#FFFFFF">
	<span class="style3">充值管理</span>
	&nbsp;&nbsp;</td>	
  </tr>
  <tr>
    <td width="3%" height="22" bgcolor="#3872b2"><div align="center" class="style2"></div></td>
    <td width="13%" height="22" bgcolor="#3872b2"><div align="center" class="style2">单号</div></td>
    <td width="7%" align="center" bgcolor="#3872b2" class="style2">金额</td>
    <td width="10%" align="center" bgcolor="#3872b2" class="style2">会员ID</td>
    <td width="9%" align="center" bgcolor="#3872b2" class="style2">支付宝</td>
    <td width="7%" align="center" bgcolor="#3872b2" class="style2">状态</td>
    <td width="10%" align="center" bgcolor="#3872b2" class="style2">姓名</td>
    <td width="10%" align="center" bgcolor="#3872b2" class="style2">电话</td>
    <td width="13%" bgcolor="#3872b2"><div align="center" class="style2">日期</div></td>
    </tr>
  <%
  
  TotalNum=DaoFactory.getFundsDao().getTotalNum(FSts, 1, FNo, UserId, null,0);
  PageNum=(TotalNum-1+PageSize)/PageSize;
 ArrayList list=(ArrayList)DaoFactory.getFundsDao().getList(FSts, 1, FNo, UserId, null,0, CurrentPage, PageSize);
  for (Iterator iter = list.iterator(); iter.hasNext(); ) {
  	DataField df=(DataField)iter.next();
	String id=df.getFieldValue("F_No");
     
  %>
  <tr>
    <td bgcolor="#FDFDFD">
    <div align="center"><input name="objid" type="checkbox" value="<%=id%>" /></div></td>
    <td align="center" bgcolor="#FDFDFD"><%=id%></td>
    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("F_Price")%></td>
    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("UserId")%></td>
    <td align="center" bgcolor="#FDFDFD">  <%=df.getFieldValue("Alipay") %></td>
    <td align="center" bgcolor="#FDFDFD">
     <%
                                        switch (df.getInt("Sts")) {
                                            case 1:
                                                out.print("充值");
                                                break;
                                            case 2:
                                                out.print("完成");
                                                break;                                                                           
                                        }
                                    %>
    </td>
    <td align="center" bgcolor="#FDFDFD"><%=df.getFieldValue("UserName")%></td>
    <td align="center" bgcolor="#FDFDFD"><%=df.getString("F_Phone")%></td>
    <td bgcolor="#FDFDFD"><div align="center"><%=df.getFieldValue("F_Date")%></div></td>
  
    </tr>
  <%
  	}
  %>
  <tr>
    <td colspan="9" valign="top" bgcolor="#FDFDFD"><div align="center">
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
    <a href="?FSts=<%=FSts%>&FNo=<%=FNo%>&UserId=<%=UserId%>&page=<%=CurrentPage-1%>">上一页</a>&nbsp;&nbsp;
    <%}else{%>
  上一页&nbsp;&nbsp;
  <%}%>
  <%if(CurrentPage>=PageNum){%>
  下一页
  <%}else{%>
  <a href="?FSts=<%=FSts%>&FNo=<%=FNo%>&UserId=<%=UserId%>&page=<%=CurrentPage+1%>">下一页</a>
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
