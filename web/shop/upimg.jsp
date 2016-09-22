<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="error.jsp" %>
<%@ page import="java.io.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.disk.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<%@ include file="inc/common.jsp"%>
<%@ include file="session.jsp"%>
<%
//---------------------------------------------------------
//if(!DaoFactory.getAdminDAO().ifHasPrivilege(haspriv,"p031"))
//throw new NoPrivilegeException("没有权限");
//---------------------------------------------------------
%>
<html>
<head>
<meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
<title>upload picture</title>
<link href="style/global.css" rel="stylesheet" type="text/css" />
</head>
<body style="background-color:#D4D0C8;"> 
<%
String act=RequestUtil.getString(request,"act");
String rid=RequestUtil.getString(request,"rid");
if(rid==null || rid.equals("")){
	rid="Photo";
}
if(act!=null && act.equals("do")){
	//param
	int maxsize=Sysconfig.getUploadPhotoMaxsize();
	String ContextPath=Sysconfig.getContextPath();
	String returnValue="";
	boolean isbool=false;
	//随机数
	Random r=new Random(System.currentTimeMillis());
	int n=r.nextInt();
	if(n<0){
		n=0-n;
	}
	//定义目标目录 
	String monthStr=DateUtil.getStringDateShort();
	String destination="/upload/"+monthStr+"/"; 
	String diagonal=FileUtil.getSeparator();
	String uploadpath=application.getRealPath("/")+"upload"+diagonal+monthStr+diagonal;
	FileUtil.createDir(uploadpath,true);
	String strfilesize="";
	//
	DiskFileItemFactory factory = new DiskFileItemFactory();	
	factory.setSizeThreshold(4096);
	factory.setRepository(new File(request.getRealPath("/")));	
	ServletFileUpload upload = new ServletFileUpload(factory);
	upload.setSizeMax(maxsize*1024);//200k
	String file_name=null;
	// Parse the request
	List items = upload.parseRequest(request);
	Iterator iter = items.iterator();
	Timestamp addtime=DateUtil.getCurrentGMTTimestamp();
	if (iter.hasNext()) {		
    	FileItem item = (FileItem) iter.next();
    	if (item.isFormField()) {        
			String name = item.getFieldName();
    		String value = item.getString();
			/*out.print("name:"+name+"<br>value:"+value);*/
    	} else {	
        	String fieldName = item.getFieldName();
    		String fileName = item.getName();
			String s="\\\\";
			String[] files=fileName.split(s);
   	 		String contentType = item.getContentType();
    		boolean isInMemory = item.isInMemory();
    		int filesize = (int)item.getSize();
			if(filesize>1024)
			{
				strfilesize=(filesize/1024)+"K";
			}
			if(filesize>1048576)
			{
				java.text.NumberFormat  formater  =  java.text.DecimalFormat.getInstance(); 
				formater.setMaximumFractionDigits(2); 
				formater.setMinimumFractionDigits(2); 
				strfilesize=formater.format((float)(filesize/1048576))+"M";
			}
			if(filesize>1024*maxsize)
			{
				out.print("<script language='javascript'>alert('你上传的图片太大，请修改后再传!/The file is too large!');history.back();</script>");
				isbool=false;
			}
			String temp=fileName;
			temp=temp.substring(temp.indexOf(".")+1);		
			if(temp.equalsIgnoreCase("jpeg")){
				temp=".jpg";
				isbool=true;
			}
			else if(temp.equalsIgnoreCase("jpg")){
				temp=".jpg";
				isbool=true;
			}
			else if(temp.equalsIgnoreCase("png")){
				temp=".png";
				isbool=true;
			}
			else if(temp.equalsIgnoreCase("gif")){
				temp=".gif";
				isbool=true;
			}
			else if(temp.equalsIgnoreCase("bmp")){
				temp=".bmp";
				isbool=true;
			}
			else if(temp.equalsIgnoreCase("swf")){
				temp=".swf";
				isbool=true;
			}
			else if(temp.equalsIgnoreCase("jsp")){
				temp=".gif";
				out.print("<script language='javascript'>alert('你上传的文件不合法');history.back();</script>");
				isbool=false;
			}else{
				temp=".gif";
				out.print("<script language='javascript'>alert('你上传的文件不合法');history.back();</script>");
				isbool=false;
			}					
			file_name=n+temp;
			if(isbool){
				item.write(new File(uploadpath+file_name));
				returnValue=ContextPath+destination+file_name;
				out.print("<script language=\"javascript\">");
				out.print("alert(\"Success upload file with "+strfilesize+"\");	");
				out.print("window.opener.document.getElementById('"+rid+"').value='"+returnValue+"';");
				out.print("window.close();");
				out.print("</script>");
			}
		}
	}
	////////////////////////////////////
}
%>
<fieldset>上传图片</legend>
<table width="400"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><form action="?act=do&rid=<%=rid%>" method="post" enctype="multipart/form-data" name="form2" style="margin:0 0 0 0 ">
        <table width="400"  border="0" align="center" cellpadding="1" cellspacing="1" bgcolor="#D4D0C8">
          <tr bgcolor="#D4D0C8">
            <td width="82%" height="30">
              
              <div align="center">
                <input name="FileName" type="file" id="FileName" size="40" maxlength="100">
                </div></td>
          </tr>
          <tr bgcolor="#D4D0C8">
            <td><hr align="center" width="92%" size="1" noshade></td>
          </tr>
          <tr bgcolor="#D4D0C8">
            <td height="30" bgcolor="#D4D0C8"><div align="center">
              <input type="submit" name="Submit3" value="提 交" style="font-size:12px; ">&nbsp;&nbsp;
              <input type="button" name="Submit32" value="取 消" style="font-size:12px; " onClick="window.close();">
            </div></td>
          </tr>
        </table>
</form></td>
  </tr>
</table></fieldset>
</body> 
</html> 
