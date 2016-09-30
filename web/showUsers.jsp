<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../inc/common.jsp"%>
<%
    String uid = RequestUtil.getString(request, "uid");
    //开始往上查找代理级别
    List<DataField> updf = DaoFactory.getAgencyDao().getByCol(" agencylvl in(1,2,3,4,5,6,7,8,9,10) and uid=" + uid);
    List<String> upList = new ArrayList<String>();
    for (int i = 0; i < updf.size(); i++) {
		upList.add(updf.get(i).getString("parentid"));
    }

    //开始往下查找代理级别
    List<DataField> downdf = DaoFactory.getAgencyDao().getByCol(" agencylvl in(1,2,3,4,5,6,7,8,9,10) and parentid=" + uid + " order by agencylvl asc");
    List<DataField> down1 = new ArrayList<DataField>();
    List<DataField> down2 = new ArrayList<DataField>();
    List<DataField> down3 = new ArrayList<DataField>();
    List<DataField> down4 = new ArrayList<DataField>();
    List<DataField> down5 = new ArrayList<DataField>();
    List<DataField> down6 = new ArrayList<DataField>();
    List<DataField> down7 = new ArrayList<DataField>();
    List<DataField> down8 = new ArrayList<DataField>();
    List<DataField> down9 = new ArrayList<DataField>();
    List<DataField> down10 = new ArrayList<DataField>();
    for (DataField df : downdf) {
		int lvl = Integer.parseInt(df.getString("agencylvl"));

		switch (lvl) {
		case 1:
		    down1.add(df);
		    break;
		case 2:
		    down2.add(df);
		    break;
		case 3:
		    down3.add(df);
		    break;
		case 4:
		    down4.add(df);
		    break;
		case 5:
		    down5.add(df);
		    break;
		case 6:
		    down6.add(df);
		    break;
		case 7:
		    down7.add(df);
		    break;
		case 8:
		    down8.add(df);
		    break;
		case 9:
		    down9.add(df);
		    break;
		case 10:
		    down10.add(df);
		    break;
		}
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>会员代理关系列表图</title>
</head>
<body>
	<%=uid%>
	<ul>
		<li>一级代理
			<ul>
				<%
				    for (int i = 0; i < down1.size(); i++) {
				%>
				<li><%=down1.get(i).getString("uid")%></li>
				<%
				    }
				%>
			</ul>
		</li>

	</ul>

	<%
	    if (down2 != null && down2.size() > 0) {
	%>
	<ul>
		<li>二级代理
			<ul>
				<%
				    for (int i = 0; i < down2.size(); i++) {
				%>
				<li><%=down2.get(i).getString("uid") %></li>
				<%
				    }
				%>
			</ul>
		</li>

	</ul>
	<%
	    }
	%>
	<%
	    if (down3 != null && down3.size() > 0) {
	%>
	<ul>
		<li>三级代理
			<ul>
				<%
				    for (int i = 0; i < down3.size(); i++) {
				%>
				<li><%=down3.get(i).getString("uid")%></li>
				<%
				    }
				%>
			</ul>
		</li>

	</ul>
	<%
	    }
	%>
	<%
	    if (down4 != null && down4.size() > 0) {
	%>
	<ul>
		<li>四级代理
			<ul>
				<%
				    for (int i = 0; i < down4.size(); i++) {
				%>
				<li><%=down4.get(i).getString("uid")%></li>
				<%
				    }
				%>
			</ul>
		</li>

	</ul>
	<%
	    }
	%>
	<%
	    if (down5 != null && down5.size() > 0) {
	%>
	<ul>
		<li>五级代理
			<ul>
				<%
				    for (int i = 0; i < down5.size(); i++) {
				%>
				<li><%=down5.get(i).getString("uid")%></li>
				<%
				    }
				%>
			</ul>
		</li>

	</ul>
	<%
	    }
	%>
	<%
	    if (down6 != null && down6.size() > 0) {
	%>
	<ul>
		<li>六级代理
			<ul>
				<%
				    for (int i = 0; i < down6.size(); i++) {
				%>
				<li><%=down6.get(i).getString("uid")%></li>
				<%
				    }
				%>
			</ul>
		</li>

	</ul>
	<%
	    }
	%>
	<%
	    if (down7 != null && down7.size() > 0) {
	%>
	<ul>
		<li>七级代理
			<ul>
				<%
				    for (int i = 0; i < down7.size(); i++) {
				%>
				<li><%=down7.get(i).getString("uid")%></li>
				<%
				    }
				%>
			</ul>
		</li>

	</ul>
	<%
	    }
	%>
	<%
	    if (down8 != null && down8.size() > 0) {
	%>
	<ul>
		<li>八级代理
			<ul>
				<%
				    for (int i = 0; i < down8.size(); i++) {
				%>
				<li><%=down8.get(i).getString("uid")%></li>
				<%
				    }
				%>
			</ul>
		</li>

	</ul>
	<%
	    }
	%>
	<%
	    if (down9 != null && down9.size() > 0) {
	%>
	<ul>
		<li>九级代理
			<ul>
				<%
				    for (int i = 0; i < down9.size(); i++) {
				%>
				<li><%=down9.get(i).getString("uid")%></li>
				<%
				    }
				%>
			</ul>
		</li>

	</ul>
	<%
	    }
	%>

	<%
	    if (down10 != null && down10.size() > 0) {
	%>
	<ul>
		<li>十级代理
			<ul>
				<%
				    for (int i = 0; i < down10.size(); i++) {
				%>
				<li><%=down10.get(i).getString("uid")%></li>
				<%
				    }
				%>
			</ul>
		</li>

	</ul>
	<%
	    }
	%>

</body>
</html>