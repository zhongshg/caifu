<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="error.jsp" %>
<%@ include file="inc/common.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="job.tot.global.Sysconfig" %>
<%@ page import="job.tot.db.DBUtils" %>
<%@ page import="job.tot.util.RequestUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<fmt:bundle basename="resources.totcms_i18n">
<head>
<title>Database Manage</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="style/global.css" rel="stylesheet" type="text/css" />
<style type="text/css">
legend,field{font-size:12px;}
ul{list-style:none; list-style-type:none; margin:0; padding:0px;}
li{list-style:none; list-style-type:none; margin:3px 0px; padding:0px;}
.style1 {
	color: #FFFFFF;
	font-weight: bold;
}
.tblist{ list-style:none;}
.tblist li{ width:25%; float:left; text-align:left;}
</style>
<script src="js/common.js" type="text/javascript"></script>
</head>
<body style="text-align:center;">
   <div align="center" > <a href="" onclick="javascript:history.back(-1);" >返回</a>
    <a href="d.jsp" >首页</a></div>
<%
String act=RequestUtil.getString(request,"act");
if(act!=null){
	if(act.equals("listfields")){
		String tblname=RequestUtil.getString(request,"table");
		Connection conn = DBUtils.getConnection();
        PreparedStatement statement = null;
        ResultSet rs=null;
        if(conn!=null && tblname!=null){
            try{
                statement=conn.prepareStatement("select * from "+tblname+" where 1=0");
                rs=statement.executeQuery();
                ResultSetMetaData rm=rs.getMetaData();
				out.print("表："+tblname+" &nbsp;&nbsp; 字段总数："+rm.getColumnCount()+"<br><br>\n");
				out.print("<table width=\"98%\"  border=\"0\" cellspacing=\"1\" cellpadding=\"0\" bgcolor=\"#D4D0C8\">\n");
				out.print("<tr>\n");
				out.print("    <td height=\"22\"><div align=\"center\">字段名称</div></td>\n");
				out.print("    <td style=\"border-left:1px solid #eeeeee;\">字段属性</td>\n");
				out.print("    <td style=\"border-left:1px solid #eeeeee;\" height=\"22\"><div align=\"center\">修 改</div></td>\n");
				out.print("    <td style=\"border-left:1px solid #eeeeee;\" height=\"22\"><div align=\"center\">删 除</div></td>\n");
 				out.print(" </tr>\n");
                for(int i=1;i<=rm.getColumnCount();i++){
					out.print("<tr bgcolor=\"#FFFFFF\">");
					out.print("<td height=\"22\"><div align=\"center\">"+rm.getColumnName(i)+"</div></td>");
					out.println("<td>");
					out.print(rm.getColumnTypeName(i)+":"+rm.getColumnType(i));
					if(rm.getColumnType(i)==12){
						out.print("&nbsp;[Length:"+rm.getPrecision(i)+"]");
					}
					out.println("</td>");                    
					out.print("    <td height=\"22\"><div align=\"center\">");
					out.print("<a href=\"#modfields\" onClick=\"document.form_mod.fieldname.value='"+rm.getColumnName(i)+"'\">修 改</a>");
					out.print("</div></td>\n");
					out.print("    <td height=\"22\"><div align=\"center\">");
					out.print("<a href=\"?act=delfield&fieldname="+rm.getColumnName(i)+"&table="+tblname+"\" onClick=\"return ConfirmDel('确认删除此字段?')\">删 除</a></div></td>\n");
					out.print("</tr>\n");
                }
				out.print("</table>\n");
                StringBuffer s1=new StringBuffer(512);
                StringBuffer s2=new StringBuffer(512);
                StringBuffer s3=new StringBuffer(512);
                StringBuffer s4=new StringBuffer(512);
                StringBuffer s5=new StringBuffer(512);
								StringBuffer s6=new StringBuffer(512);
								StringBuffer s7=new StringBuffer(512);
                out.print("<textarea style=\"width:100%; height:500px;\">");
                s1.append("insert into "+tblname+"(");
                s2.append("update "+tblname+" set ");
                for(int i=1;i<=rm.getColumnCount();i++){
                  s1.append(rm.getColumnName(i));
                  if(i>1){
                    s2.append(rm.getColumnName(i)+"=?");
                  }
                  if(i<rm.getColumnCount()){
                    s1.append(",");
                    if(i>1){
                    s2.append(",");
                    }
                  }
                  
                  int t=rm.getColumnType(i);
                  if(t==4 || t==5 || t==-6 || t==-7){
                    s3.append("ps.setInt("+(i-1)+","+rm.getColumnName(i).toLowerCase()+");\n");
                    s4.append("int "+rm.getColumnName(i).toLowerCase());
                    s5.append("int "+rm.getColumnName(i).toLowerCase()+" =RequestUtil.getInt(request,\""+rm.getColumnName(i)+"\");\n");
                  }else if(t==12 || t==-1 || t==-9 || t==2005 || t==-16){
                    s3.append("ps.setString("+(i-1)+","+rm.getColumnName(i).toLowerCase()+");\n");
                    s4.append("String "+rm.getColumnName(i).toLowerCase());
                    s5.append("String "+rm.getColumnName(i).toLowerCase()+" =RequestUtil.getString(request,\""+rm.getColumnName(i)+"\");\n");
                  }else if(t==93){
                    s3.append("ps.setTimestamp("+(i-1)+","+rm.getColumnName(i).toLowerCase()+");\n");
                    s4.append("Timestamp "+rm.getColumnName(i).toLowerCase());
                    s5.append("Timestamp "+rm.getColumnName(i).toLowerCase()+" =RequestUtil.getDate(request,\""+rm.getColumnName(i)+"\");\n");
                  }else if(t==7 || t==3){
                    s3.append("ps.setFloat("+(i-1)+","+rm.getColumnName(i).toLowerCase()+");\n");
                    s4.append("float "+rm.getColumnName(i).toLowerCase());
                    s5.append("float "+rm.getColumnName(i).toLowerCase()+" =RequestUtil.getFloat(request,\""+rm.getColumnName(i)+"\");\n");
                  }
                  if(i<rm.getColumnCount()){
                    s4.append(",");
                  }
									s6.append(""+rm.getColumnName(i)+" =df.getStr(\""+rm.getColumnName(i)+"\");\n");
									s7.append(rm.getColumnName(i).toLowerCase()+",");
                }
                s1.append(") values(");
                s2.append(" where id=?");
                for(int i=1;i<=rm.getColumnCount();i++){
                  s1.append("?");
                  if(i<rm.getColumnCount()){
                    s1.append(",");
                  }
                }
                s1.append(")");
								out.print(s7.toString()+"\n");
                out.print(s4.toString()+"\n");
                out.print(s1.toString()+"\n");
                out.print(s2.toString()+"\n");
                out.print(s3.toString()+"\n");
                out.print(s5.toString()+"\n");
								out.print(s6.toString()+"\n");
                out.print("</textarea>");
            } catch(SQLException sqle) {
            } finally {
                try{
                    DBUtils.closeResultSet(rs);
        			DBUtils.closePrepareStatement(statement);
        			DBUtils.closeConnection(conn);
                } catch(Exception sqle) {}
            }
%>
<!--修改字段---------------------------------------------------->
<br>
<div align="center" style="width:98%; margin:0px auto;">
<fieldset>
<legend>修改字段</legend>
<script language="javascript">
function seleChan1(str){	
	if(str=="varchar"){	
		document.getElementById("fieldsize").style.display="";
	}
	else{
		document.getElementById("fieldsize").style.display="none";
	}
}
function chkfield(obj){
	if(obj.fieldname.value==""){
		alert("字段名称不能为空!");
		obj.fieldname.focus();
		obj.fieldname.select();
		return false;
	}
	return true;
}
</script>
<a name="modfields"></a>
<table width="100%"  border="0" cellpadding="0" cellspacing="0" bgcolor="#D4D0C8">
  <tr>
    <td height="30" bgcolor="#FFFFFF">
      <form name="form_mod" method="post" action="?act=modfield&table=<%=tblname%>" style="margin:0; padding:0; " onsubmit="return chkfield(this)"><div align="center">字段名称：
          <input name="fieldname" type="text" size="20" maxlength="25" readonly="true">
          <select name="fieldtype" onChange="seleChan1(this.options[this.selectedIndex].value)">
            <option value="int">int</option>
			<option value="float">float</option>
			<option value="numeric">numeric</option>
			<option value="smallint">smallint</option>
			<option value="varchar">varchar</option>
			<option value="datetime">datetime</option>
			<option value="text">text</option>
			<option value="timestamp">timestamp</option>
          </select>
		  <div id="fieldsize" style="display:none; float:inherit;">长度：<input name="varchar_len" type="text" id="varchar_len" size="10" value="50"></div>		  
          <input type="submit" name="Submit" value="修 改">
      </div>
      </form>    </td>
  </tr>
</table>
</fieldset>
</div>
<!--添加字段---------------------------------------------------->
<br>
<div align="center" style="width:98%; margin:0px auto;">
<fieldset>
<legend>添加字段</legend>
<script language="javascript">
function seleChan(str){	
	if(str=="varchar"){	
		document.getElementById("charlen").style.display="";
	}
	else{
		document.getElementById("charlen").style.display="none";
	}
}
</script>
<table width="98%"  border="0" cellpadding="0" cellspacing="0" bgcolor="#D4D0C8">
  <tr>
    <td height="30" bgcolor="#FFFFFF">
      <form name="form1" method="post" action="?act=addfield&table=<%=tblname%>" style="margin:0; padding:0; " onsubmit="return chkfield(this)"><div align="center">字段名称：
          <input name="fieldname" type="text" size="20" maxlength="25">
          <select name="fieldtype" onChange="seleChan(this.options[this.selectedIndex].value)">
            <option value="int">int</option>
			<option value="float">float</option>
			<option value="numeric">numeric</option>
			<option value="smallint">smallint</option>
			<option value="varchar">varchar</option>
			<option value="datetime">datetime</option>
			<option value="text">text</option>
			<option value="timestamp">timestamp</option>
          </select>
		  <div id="charlen" style="display:none; float:inherit;">长度：<input name="varchar_len" type="text" id="varchar_len" size="10" value="50"></div>
          <input type="submit" name="Submit" value="添 加">
      </div>
      </form>    </td>
  </tr>
</table>
</fieldset>
</div>
<!--执行SQL语句---------------------------------------------------->
<br>
<div align="center" style="width:98%; margin:0px auto;">
<fieldset>
<legend>执行SQL语句</legend>
<table width="100%"  border="0" cellpadding="0" cellspacing="0" bgcolor="#D4D0C8">
  <tr>
    <td height="30" bgcolor="#FFFFFF">
      <form name="form1" method="post" action="?act=execute" style="margin:0; padding:0; "><div align="center">sql语句：
          <textarea name="sqlexe" cols="40" rows="4" id="sqlexe">在此处输入sql语句，注：请慎用此功能!</textarea>          
          <input type="submit" name="Submit" value="执 行">
      </div>
      </form>    </td>
  </tr>
</table>
</fieldset>
</div>

<%
        }else{
            out.print("数据库无法连接或表名称为空");
        }
	}else if(act.equals("create")){//创建新表
		String tblname=RequestUtil.getString(request,"table");
		Connection conn = DBUtils.getConnection();
		Statement stmt=null;		
		if(conn!=null && tblname!=null){
			String sql="";
			if(DBUtils.getDatabaseType() == 11)
        	{
            	sql ="create table "+tblname+" (id int IDENTITY (1, 1) NOT NULL,primary key(id))";
        	} else if(DBUtils.getDatabaseType() == 15)
        	{
            	sql ="create table "+tblname+" (id int(11) NOT NULL auto_increment,primary key(id))";
        	}
            try{
                stmt=conn.createStatement();
                stmt.execute(sql);
				out.print("成功创建表:"+tblname);
				out.print("<br /><a href=\""+request.getHeader("Referer")+"\">←Go Back</a>");
            } catch(SQLException sqle) {
            } finally {
                try{
        			DBUtils.closeStatement(stmt);
        			DBUtils.closeConnection(conn);
                } catch(Exception sqle) {}
            }
        }else{
            out.print("数据库无法连接或表名称为空");
        }
	}else if(act.equals("addfield")){
		String tblname=RequestUtil.getString(request,"table");
		String fieldname=RequestUtil.getString(request,"fieldname");
		String fieldtype=RequestUtil.getString(request,"fieldtype");		
		Connection conn = DBUtils.getConnection();
		Statement stmt=null;
		if(conn!=null && fieldname!=null){
			String sql="";
            try{
				if(fieldtype.equals("varchar")){
					int varchar_len=RequestUtil.getInt(request,"varchar_len");
					sql="alter table "+tblname+" add "+fieldname+" "+fieldtype+" ("+varchar_len+") null";
				}else{
					sql="alter table "+tblname+" add "+fieldname+" "+fieldtype;
				}
                stmt=conn.createStatement();
                stmt.execute(sql);
				out.print("成功添加字段:"+fieldname);
				out.print("<br /><a href=\""+request.getHeader("Referer")+"\">←Go Back</a>");
            } catch(SQLException sqle) {
				out.print(sqle);
            } finally {
                try{
        			DBUtils.closeStatement(stmt);
        			DBUtils.closeConnection(conn);
                } catch(Exception sqle) {}
            }
        }else{
            out.print("数据库无法连接或字段名称为空");
        }
	}else if(act.equals("modfield")){
		String tblname=RequestUtil.getString(request,"table");
		String fieldname=RequestUtil.getString(request,"fieldname");
		String fieldtype=RequestUtil.getString(request,"fieldtype");
		Connection conn = DBUtils.getConnection();
		Statement stmt=null;
		if(conn!=null && fieldname!=null){
			String sql="";
            try{
				if(fieldtype.equals("varchar")){
					int varchar_len=RequestUtil.getInt(request,"varchar_len");
					if(DBUtils.getDatabaseType() == 11)
        			{
           			 	sql="alter table "+tblname+" ALTER COLUMN  "+fieldname+" "+fieldtype+" ("+varchar_len+") null";
        			} else
        			if(DBUtils.getDatabaseType() == 15)
        			{
            			sql="alter table "+tblname+" change "+fieldname+" "+fieldname+" "+fieldtype+" ("+varchar_len+") null";
        			}
					
				}else{
					if(DBUtils.getDatabaseType() == 11)
        			{
           			 	sql="alter table "+tblname+" ALTER COLUMN  "+fieldname+" "+fieldtype;
        			} else
        			if(DBUtils.getDatabaseType() == 15)
        			{
            			sql="alter table "+tblname+" change "+fieldname+" "+fieldname+" "+fieldtype;
        			}
				}
                stmt=conn.createStatement();
                stmt.execute(sql);
				out.print("成功修改字段:"+fieldname);
				out.print("<br /><a href=\""+request.getHeader("Referer")+"\">←Go Back</a>");
            } catch(SQLException sqle) {
				out.print(sqle);
            } finally {
                try{
        			DBUtils.closeStatement(stmt);
        			DBUtils.closeConnection(conn);
                } catch(Exception sqle) {}
            }
        }else{
            out.print("数据库无法连接或字段名称为空");
        }
	}else if(act.equals("delfield")){
		String tblname=RequestUtil.getString(request,"table");
		String fieldname=RequestUtil.getString(request,"fieldname");		
		Connection conn = DBUtils.getConnection();
		Statement stmt=null;
		if(conn!=null && fieldname!=null){
			String sql="";
            try{
				sql="alter table "+tblname+" DROP COLUMN "+fieldname;
                stmt=conn.createStatement();
                stmt.execute(sql);
				out.print("成功删除字段:"+fieldname);
				out.print("<br /><a href=\""+request.getHeader("Referer")+"\">←Go Back</a>");
            } catch(SQLException sqle) {
				out.print(sqle);
            } finally {
                try{
        			DBUtils.closeStatement(stmt);
        			DBUtils.closeConnection(conn);
                } catch(Exception sqle) {}
            }
        }else{
            out.print("数据库无法连接或字段名称为空");
        }
	}else if(act.equals("execute")){
		String sqlexe=request.getParameter("sqlexe");
		Connection conn = DBUtils.getConnection();
		Statement stmt=null;
		if(conn!=null && sqlexe!=null){
            try{
                stmt=conn.createStatement();
                stmt.execute(sqlexe);
				out.print("成功执行命令:"+sqlexe);
				out.print("<br /><a href=\""+request.getHeader("Referer")+"\">←Go Back</a>");
            } catch(SQLException sqle) {
				out.print(sqle);
            } finally {
                try{
        			DBUtils.closeStatement(stmt);
        			DBUtils.closeConnection(conn);
                } catch(Exception sqle) {}
            }
        }else{
            out.print("数据库无法连接或执行命令为空");
        }
	}else if(act.equals("del")){
		String tblname=RequestUtil.getString(request,"table");
		Connection conn = DBUtils.getConnection();
		Statement stmt=null;
		if(conn!=null && tblname!=null){
            try{
                stmt=conn.createStatement();
                stmt.execute("drop table "+tblname);
				out.print("成功删除表:"+tblname);
				out.print("<br /><a href=\""+request.getHeader("Referer")+"\">←Go Back</a>");
            } catch(SQLException sqle) {
				out.print(sqle);
            } finally {
                try{
        			DBUtils.closeStatement(stmt);
        			DBUtils.closeConnection(conn);
                } catch(Exception sqle) {}
            }
        }else{
            out.print("数据库无法连接或表名称为空");
        }
	}
}else{
%>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
  <tr>
    <td height="22" bgcolor="#3872b2"><div align="center"><span class="style1">◆ <fmt:message key="totcms.admin.system.database" /> ◆</span></div></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF" style="padding:10px; "><img src="images/computer.gif" width="30" height="30" align="absmiddle"> Tables：<br>
      <div>
	  <ul class="tblist">
<%
	Connection conn = DBUtils.getConnection();
	if(conn!=null){
		String[] tbltypes={"TABLE"};	
		ResultSet rs=null;
		try{
			DatabaseMetaData dbm=conn.getMetaData();
			rs=dbm.getTables(null, null, null,tbltypes);
			while(rs.next()){
				out.print("<li><img src=\"images/table.gif\" border=\"0\" align=\"absmiddle\"> <a href=\"?act=listfields&table="+rs.getString(3)+"\">"+rs.getString(3)+"</a> &nbsp;<a href=\"?act=del&table="+rs.getString(3)+"\" onClick=\"return ConfirmDel('确认删除该表?')\"><img src=\"images/icon/del.gif\" border=\"0\" /></a></li>\n");
			}
		}catch(SQLException e){}
		finally{
			try{
				DBUtils.closeResultSet(rs);
        		DBUtils.closeConnection(conn);
			}catch(Exception e){}
		}
	}else{
		out.print("连接失败");
	}
}
%></ul></div>
</td>
  </tr>
</table>
<script type="text/javascript">
function chktbl(obj){
	if(obj.table.value==""){
		alert("表名称不能为空!");
		obj.table.focus();
		obj.table.select();
		return false;
	}
	return true;
}
</script>
<div align="center" style="width:98%; margin:0px auto;">
  <p align="left"><br>
  <fieldset>
  <div align="left">
     <legend>创建新表</legend>
     <form name="form1" method="post" action="?act=create" onsubmit="return chktbl(this)">
       <div align="center">表名：
         <input type="text" name="table">
         <input type="submit" name="Submit" value="创  建==>">
       </div>
     </form>
  </div>
  </fieldset>
</div>
</fmt:bundle>
</body>
</html>