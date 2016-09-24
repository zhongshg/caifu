<%@page import="java.util.ArrayList"%>
<%@page import="job.tot.dao.DaoFactory"%>
<%@page import="job.tot.bean.DataField"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 1px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #4AA3D8;
}

html {
	overflow-x:;
	overflow-y:;
	border: 0;
}
-->
</style>
</head>
<body>
	<div class="rrcc" id="RightBox">
		<div class="center" id="Mobile" onclick="show_menuC()"></div>
		<div class="right" id="li010">
			<div class="right01">
				<img src="../images/04.gif" /> 用户信息查询 &gt; <span>精确查询</span>
			</div>
		</div>

		<div class="right noneBox" id="li011">
			<div class="right01">
				<img src="../images/04.gif" /> 用户信息查询 &gt; <span>组合条件查询</span>
			</div>
		</div>
		<div class="right noneBox" id="li012">
			<div class="right01">
				<img src="../images/04.gif" /> 用户密码管理 &gt; <span>找回密码</span>
			</div>
		</div>
		<div class="right noneBox" id="li013">
			<div class="right01">
				<img src="../images/04.gif" /> 用户密码管理 &gt; <span>更改密码</span>
			</div>
		</div>
	</div>
</body>
</html>
