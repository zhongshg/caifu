<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ page import="job.tot.global.ConfigModule"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
<title>修改配置信息</title>
<link href="style/global.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.style1 {color: #FFFFFF}
.style2 {
	color: #FFFFFF;
	font-weight: bold;
	font-size: 14px;
}
-->
</style>
<script src="js/common.js"></script>
</head>
<body>
<%@ include file="top.jsp"%>
<%
String act=RequestUtil.getString(request,"act");
if(act!=null && act.equals("do")){	
	ConfigModule bbsconfig=new ConfigModule();
	bbsconfig.updateConfig(request);
	out.print("<p align=\"center\">成功修改系统配置信息!<br /><a href=\"config.jsp\">返回</a></p>");		
	Sysconfig.load();
}else{
	
%>
<br />
<form id="addGroup" name="addGroup" method="post" action="?act=do" style="margin:0px; padding:0px;">
<table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border">
  <tr>
    <td height="25" colspan="2" bgcolor="#3872b2"><div align="center" class="style1"><strong><span class="style2">
      修改网站参数</span></div></td>
    </tr>
</table>

<div>
  <div align="center">支付接口参数</div>
</div>
<table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border">
  <tr>
    <td width="31%" bgcolor="#FDFDFD"><div align="right">登录提交地址：</div></td>
    <td width="69%" bgcolor="#FDFDFD"><div align="left">
      <input name="login_action_url" type="text" id="login_action_url" maxlength="250" value="<%=Sysconfig.getLoginActionUrl()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
  </tr>
  <tr>
    <td bgcolor="#FDFDFD"><div align="right">会员ID字段参数：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="field_userid" type="text" id="field_userid" maxlength="250" value="<%=Sysconfig.getFieldUserId()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
  </tr>
  <tr>
    <td bgcolor="#FDFDFD"><div align="right">会员密码字段参数：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="field_password" type="text" id="field_password" maxlength="250" value="<%=Sysconfig.getFieldPassWord()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
  </tr>
  <tr>
    <td bgcolor="#FDFDFD"><div align="right">支付宝帐号：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="cookie_name" type="text" id="cookie_name" maxlength="250" value="<%=Sysconfig.getCookieName()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
  </tr>
  <tr>
    <td bgcolor="#FDFDFD"><div align="right">支付宝合作者ID：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="member_gate" type="text" id="member_gate" maxlength="250" value="<%=Sysconfig.getMemberGate()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
  </tr>
  <tr>
    <td bgcolor="#FDFDFD"><div align="right">密钥：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="member_key" type="text" id="member_key" maxlength="250" value="<%=Sysconfig.getMemberKey()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
  </tr>
</table>


<div>
  <div align="center">数据库参数</div>
</div>
<table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border">
  <tr>
    <td width="31%" bgcolor="#FDFDFD"><div align="right">数据库类型：</div></td>
    <td width="69%" bgcolor="#FDFDFD"><div align="left">
      <input name="database_type" type="text" id="database_type" maxlength="250" value="<%=Sysconfig.getDatabaseType()%>" style="width:200px; border:1px solid #666666;" /><br />
	  不需修改，仅当你的数据库不支持回滚的时候，设置为2
    </div></td>
  </tr>
  <tr>
    <td bgcolor="#FDFDFD"><div align="right">使用数据源：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="use_datasource" type="text" id="use_datasource" maxlength="250" value="<%=Sysconfig.isUseDataSource()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
  </tr>
  <tr>
    <td bgcolor="#FDFDFD"><div align="right">数据源地址：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="datasource_name" type="text" id="datasource_name" maxlength="250" value="<%=Sysconfig.getDataSourceName()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
  </tr>
  <tr>
    <td bgcolor="#FDFDFD"><div align="right">驱动类：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="driver_class_name" type="text" id="driver_class_name" maxlength="250" value="<%=Sysconfig.getDriverClassName()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
  </tr>
  <tr>
    <td bgcolor="#FDFDFD"><div align="right">数据库URL：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="database_url" type="text" id="database_url" maxlength="250" value="<%=Sysconfig.getDatabaseURL()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
  </tr>
  <tr>
    <td bgcolor="#FDFDFD"><div align="right">用户名：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="database_user" type="text" id="database_user" maxlength="250" value="<%=Sysconfig.getDatabaseUser()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
  </tr>
  <tr>
    <td bgcolor="#FDFDFD"><div align="right">密码：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="database_password" type="text" id="database_password" maxlength="250" value="<%=Sysconfig.getDatabasePassword()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
  </tr>
  <tr>
    <td bgcolor="#FDFDFD"><div align="right">最大连接数：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="max_connection" type="text" id="max_connection" maxlength="250" value="<%=Sysconfig.getMaxConnection()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
  </tr>
  <tr>
    <td bgcolor="#FDFDFD"><div align="right">连接等待时间：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="max_time_to_wait" type="text" id="max_time_to_wait" maxlength="250" value="<%=Sysconfig.getMaxTimeToWait()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
  </tr>
  <tr>
    <td bgcolor="#FDFDFD"><div align="right">刷新间隔时间：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="minutes_between_refresh" type="text" id="minutes_between_refresh" maxlength="250" value="<%=Sysconfig.getMinutesBetweenRefresh()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
  </tr>  
</table>


<div>
  <div align="center">邮件参数</div>
</div>
<table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border">
<tr>
    <td width="31%" bgcolor="#FDFDFD"><div align="right">发送邮件地址：</div></td>
    <td width="69%" bgcolor="#FDFDFD"><div align="left">
      <input name="default_mail_from" type="text" id="default_mail_from" maxlength="250" value="<%=Sysconfig.getMailSourceName()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr> 
<tr>
    <td bgcolor="#FDFDFD"><div align="right">使用邮件源：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="use_mailsource" type="text" id="use_mailsource" maxlength="250" value="<%=Sysconfig.isUseMailSource()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr>  
<tr>
    <td bgcolor="#FDFDFD"><div align="right">邮件源地址：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="mailsource_name" type="text" id="mailsource_name" maxlength="250" value="<%=Sysconfig.getMailSourceName()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr>  
<tr>
    <td bgcolor="#FDFDFD"><div align="right">邮件服务器地址：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="mail_server" type="text" id="mail_server" maxlength="250" value="<%=Sysconfig.getMailServer()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr>  
<tr>
    <td bgcolor="#FDFDFD"><div align="right">用户名：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="username" type="text" id="username" maxlength="250" value="<%=Sysconfig.getMailUserName()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr>  
<tr>
    <td bgcolor="#FDFDFD"><div align="right">密码：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="password" type="text" id="password" maxlength="250" value="<%=Sysconfig.getMailPassword()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr>  
<tr>
    <td bgcolor="#FDFDFD"><div align="right">端口：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="port" type="text" id="port" maxlength="250" value="<%=Sysconfig.getMailServerPort()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr>  
</table>

<!--网站配置-->

<div>
  <div align="center">网站参数</div>
</div>
<table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border">
<tr>
    <td width="31%" bgcolor="#FDFDFD"><div align="right">相对路径：</div></td>
    <td width="69%" bgcolor="#FDFDFD"><div align="left">
      <input name="context_path" type="text" id="context_path" maxlength="250" value="<%=Sysconfig.getContextPath()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr> 
<tr>
    <td bgcolor="#FDFDFD"><div align="right">域名：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="ask_url" type="text" id="ask_url" maxlength="250" value="<%=Sysconfig.getAskUrl()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr>  
<tr>
    <td bgcolor="#FDFDFD"><div align="right">名称：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="ask_name" type="text" id="ask_name" maxlength="250" value="<%=Sysconfig.getAskName()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr>  
<tr>
    <td bgcolor="#FDFDFD"><div align="right">cookie域：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="cookie_domain" type="text" id="cookie_domain" maxlength="250" value="<%=Sysconfig.getCookieDomain()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr>  
<tr>
    <td bgcolor="#FDFDFD"><div align="right">开放附件上传：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="enable_upload" type="text" id="enable_upload" maxlength="250" value="<%=Sysconfig.isEnableUpload()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr>  
<tr>
    <td bgcolor="#FDFDFD"><div align="right">允许上传的文件扩展名：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="upload_photo_ext" type="text" id="upload_photo_ext" maxlength="350" value="<%=Sysconfig.getUploadPhotoExt()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr>
<tr>
    <td bgcolor="#FDFDFD"><div align="right">最大上传文件限制：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="upload_photo_maxsize" type="text" id="upload_photo_maxsize" maxlength="250" value="<%=Sysconfig.getUploadPhotoMaxsize()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr>  
<tr>
    <td bgcolor="#FDFDFD"><div align="right">1小时内最多发表主题数：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="max_posts_per_hour" type="text" id="max_posts_per_hour" maxlength="250" value="<%=Sysconfig.getMaxPostsPerHour()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr>
<tr>
    <td bgcolor="#FDFDFD"><div align="right">查看详细信息设置：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
	<%
		int max_replys_per_hour=Sysconfig.getMaxReplysPerHour();
	%>
      <select name="max_replys_per_hour">
        <option value="0" <%if(max_replys_per_hour==0) out.print("selected=\"selected\"");%>>任何人</option>
				<option value="1" <%if(max_replys_per_hour==1) out.print("selected=\"selected\"");%>>普通会员</option>
				<option value="2" <%if(max_replys_per_hour==2) out.print("selected=\"selected\"");%>>黄金会员</option>
      </select>
      </div></td>
</tr>
<tr>
    <td bgcolor="#FDFDFD"><div align="right">信息是否需要审核才能显示：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
	<%
		int default_check=Sysconfig.getDefaultCheck();
	%>
      <select name="default_check">
	  	<option value="0" <%if(default_check==0) out.print("selected=\"selected\"");%>>是</option>
		<option value="1" <%if(default_check==1) out.print("selected=\"selected\"");%>>否</option>
	  </select>
    </div></td>
</tr>
</table>

<!--索引配置-->

<div>
  <div align="center">索引配置</div>
</div>
<table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border">
<tr>
    <td width="31%" bgcolor="#FDFDFD"><div align="right">索引文件保存路径：</div></td>
    <td width="69%" bgcolor="#FDFDFD"><div align="left">
      <input name="indexdir" type="text" id="indexdir" maxlength="250" value="<%=Sysconfig.getIndexDir()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr> 
<tr>
    <td bgcolor="#FDFDFD"><div align="right">索引分析类：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="lucene_analyzer_implementation" type="text" id="lucene_analyzer_implementation" maxlength="250" value="<%=Sysconfig.getLuceneAnalyzerClassName()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr>  
</table>

<!--时区配置-->

<div>
  <div align="center">时区配置</div>
</div>
<table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border">
<tr>
    <td width="31%" bgcolor="#FDFDFD"><div align="right">时区：</div></td>
    <td width="69%" bgcolor="#FDFDFD"><div align="left">
      <input name="server_hour_offset" type="text" id="server_hour_offset" maxlength="250" value="<%=Sysconfig.getServerHourOffset()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr> 
</table>

<!--锁定IP-->

<div>
  <div align="center">锁定IP</div>
</div>
<table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border">
<tr>
    <td width="31%" bgcolor="#FDFDFD"><div align="right">IP列表：</div></td>
    <td width="69%" bgcolor="#FDFDFD"><div align="left">      
      <textarea name="blocked_ip" cols="45" rows="5"><%=Sysconfig.getBlockedIPs()%></textarea>
      <br />
    多个以;分隔</div></td>
  </tr>
</table>

<!--积分配置-->
<div  style="display:none">
  <div align="center">积分配置</div>
</div>
<table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border" style="display:none">
<tr>
    <td width="31%" bgcolor="#FDFDFD"><div align="right">发布任务积分：</div></td>
    <td width="69%" bgcolor="#FDFDFD"><div align="left">
      <input name="postpernum" type="text" id="postpernum" maxlength="250" value="<%=Sysconfig.getPostPerNum()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr> 
<tr>
    <td bgcolor="#FDFDFD"><div align="right">参与任务积分：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="joinpernum" type="text" id="joinpernum" maxlength="250" value="<%=Sysconfig.getJoinPerNum()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr> 
<tr>
    <td bgcolor="#FDFDFD"><div align="right">至少提现金额：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="minfetchmoney" type="text" id="minfetchmoney" maxlength="250" value="<%=Sysconfig.getMinFetchMoney()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr> 
<tr>
    <td bgcolor="#FDFDFD"><div align="right">标准任务(金额)：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="generaltask" type="text" id="generaltask" maxlength="250" value="<%=Sysconfig.getGeneralAskNum()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr> 
<tr>
    <td bgcolor="#FDFDFD"><div align="right">高级任务(金额)：</div></td>
    <td bgcolor="#FDFDFD"><div align="left">
      <input name="mediumtask" type="text" id="mediumtask" maxlength="250" value="<%=Sysconfig.getMediumAsk()%>" style="width:200px; border:1px solid #666666;" />
    </div></td>
</tr> 
</table>
<table width="98%" height="25" border="0" align="center" cellpadding="2" cellspacing="0" class="black_border" style="display:block">
<tr>
    <td colspan="2" bgcolor="#FDFDFD"><div align="center">
      <input type="submit" name="Submit" value="确 定" />
      
        <input type="reset" name="Submit2" value="取 消" />
    </div></td>
    </tr>
</table>
</form>
<%
}
%>
</body>
</html>
