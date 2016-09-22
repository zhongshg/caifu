<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="java.util.*"%>
<%@ page import="java.awt.image.BufferedImage"%>
<%@ page import="java.awt.*"%>
<%@ page import="java.io.*"%>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<%
/*
程序作者：淘特网
程序出自：http://cms.tot.name
*/
long startTime = System.currentTimeMillis(); 
long startMem = Runtime.getRuntime().freeMemory(); 
%>
<%!
public class LibInfo {

    boolean supportJNDI             = false;
    boolean supportJavaxSql         = false;
    boolean supportJAF              = false;
    boolean supportMail             = false;

    boolean supportBeanUtils        = false;
    boolean supportCommonLogging    = false;
    boolean supportCommonCodec      = false;
    boolean supportCommonCollection = false;
    boolean supportCommonDigester   = false;
    boolean supportCommonLang       = false;
    boolean supportJakartaRegExp    = false;
    boolean supportLucene           = false;

    boolean supportDom4j            = false;

    boolean supportMmMysqlDriver    = false;
    boolean supportComMysqlDriver   = false;

    boolean supportImageProcessing  = false;

    public LibInfo() {
        try {
            Class.forName("javax.naming.Name");
            supportJNDI = true;
        } catch (ClassNotFoundException ex) {}

        try {
            Class.forName("javax.sql.DataSource");
            supportJavaxSql = true;
        } catch (ClassNotFoundException ex) {}

        try {
            Class.forName("javax.activation.DataSource");
            supportJAF = true;
        } catch (ClassNotFoundException ex) {}

        try {
            Class.forName("javax.mail.Message");
            supportMail = true;
        } catch (ClassNotFoundException ex) {}

        try {
            Class.forName("org.apache.commons.beanutils.MethodUtils");
            supportBeanUtils = true;
        } catch (ClassNotFoundException ex) {}

        try {
            Class.forName("org.apache.commons.logging.LogFactory");
            supportCommonLogging = true;
        } catch (ClassNotFoundException ex) {}

        try {
            Class.forName("org.apache.commons.codec.Decoder");
            supportCommonCodec = true;
        } catch (ClassNotFoundException ex) {}

        try {
            Class.forName("org.apache.commons.collections.ArrayStack");
            supportCommonCollection = true;
        } catch (ClassNotFoundException ex) {}

        try {
            Class.forName("org.apache.commons.digester.Digester");
            supportCommonDigester = true;
        } catch (ClassNotFoundException ex) {}

        try {
            Class.forName("org.apache.commons.lang.SystemUtils");
            supportCommonLang = true;
        } catch (ClassNotFoundException ex) {}

        try {
            Class.forName("org.apache.regexp.RE");
            supportJakartaRegExp = true;
        } catch (ClassNotFoundException ex) {}

        try {
            Class.forName("org.apache.lucene.index.IndexWriter");
            supportLucene = true;
        } catch (ClassNotFoundException ex) {}

        try {
            Class.forName("org.dom4j.Document");
            supportDom4j = true;
        } catch (ClassNotFoundException ex) {}

        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            supportMmMysqlDriver = true;
        } catch (ClassNotFoundException ex) {}

        try {
            Class.forName("com.mysql.jdbc.Driver");
            supportComMysqlDriver = true;
        } catch (ClassNotFoundException ex) {}

        try {
            BufferedImage bufferedImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawLine(0, 0, 10, 10);
            g.dispose();// free resource

            supportImageProcessing = true;
        } catch (Throwable ex) {}

    }

    public boolean isSupportJAF() {
        return supportJAF;
    }

    public boolean isSupportJavaxSql() {
        return supportJavaxSql;
    }

    public boolean isSupportJNDI() {
        return supportJNDI;
    }

    public boolean isSupportMail() {
        return supportMail;
    }

    public boolean isSupportBeanUtils() {
        return supportBeanUtils;
    }

    public boolean isSupportCommonLogging() {
        return supportCommonLogging;
    }

    public boolean isSupportCommonCodec() {
        return supportCommonCodec;
    }

    public boolean isSupportCommonCollection() {
        return supportCommonCollection;
    }

    public boolean isSupportCommonDigester() {
        return supportCommonDigester;
    }

    public boolean isSupportCommonLang() {
        return supportCommonLang;
    }

    public boolean isSupportJakartaRegExp() {
        return supportJakartaRegExp;
    }

    public boolean isSupportLucene() {
        return supportLucene;
    }

    public boolean isSupportDom4j() {
        return supportDom4j;
    }

    public boolean isSupportMmMysqlDriver() {
        return supportMmMysqlDriver;
    }

    public boolean isSupportComMysqlDriver() {
        return supportComMysqlDriver;
    }

    public boolean isSupportImageProcessing() {
        return supportImageProcessing;
    }

}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>淘特JSP探针</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
<!--
body{margin:0 10px;}
.style1 {color: #FFFFFF; font-size:14px; font-weight:bold;}
.line20{line-height:20px;}
.table-header-text {  color: #FFFFFF;
  font-weight: normal;
  margin: 3px 5px 1px 15px;
  font-family:  Arial, Verdana,Helvetica, Sans-Serif;
}
td{white-space:normal; word-break:break-all;}
-->
</style>
</head>

<body>
<table width="100%" height="25" border="0" cellpadding="0" cellspacing="0" bgcolor="#7171A5">
  <tr>
    <td bgcolor="#009900"><div align="center"><span class="style1">JSP探针</span></div></td>
  </tr>
</table>
<h3>Request Variables:</h3>
<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#DEE3ED" class="line20">
  <tr>
    <td width="50%" height="22" bgcolor="#009900"><span class="table-header-text">Property </span></td>
    <td width="50%" height="22" bgcolor="#009900"><span class="table-header-text">Value</span></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getAuthType</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getAuthType()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getCharacterEncoding</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getCharacterEncoding()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getContentLength</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getContentLength()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getContentType</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getContentType()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getContextPath</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getContextPath()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getLocale</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getLocale()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getLocales</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getLocales()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getMethod</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getMethod()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getPathInfo</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getPathInfo()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getPathTranslated</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getPathTranslated()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getProtocol</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getProtocol()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getReader</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getReader()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getRealPath</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getRealPath("/")%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getRemoteAddr</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getRemoteAddr()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getRemoteHost</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getRemoteHost()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getRemoteUser</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getRemoteUser()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getRequestDispatcher</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getRequestDispatcher("/")%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getRequestURI</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getRequestURI()%></td>
  </tr>  
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getRequestURL</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getRequestURL()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getServerName</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getServerName()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getServerPort</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getServerPort()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getServletPath</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getServletPath()%></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;getUserPrincipal</td>
    <td bgcolor="#FFFFFF">&nbsp;<%=request.getUserPrincipal()%></td>
  </tr>  
</table>
<h3>Session Variables:</h3>
<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#DEE3ED" class="line20">
  <tr>
    <td width="50%" height="22" bgcolor="#009900"><span class="table-header-text">Property </span></td>
    <td width="50%" height="22" bgcolor="#009900"><span class="table-header-text">Value</span></td>
  </tr>
  <%
  	Enumeration e = session.getAttributeNames();
    while (e.hasMoreElements()) {
            String name = (String)e.nextElement();
            //String value = (String);            
  %>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;<%=name%></td>
    <td bgcolor="#FFFFFF">&nbsp;<%=session.getAttribute(name)%></td>
  </tr>
  <%
  	}
  %>
</table>
<h3>Cookies Variables:</h3>
<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#DEE3ED" class="line20">
  <tr>
    <td width="50%" height="22" bgcolor="#009900"><span class="table-header-text">Property </span></td>
    <td width="50%" height="22" bgcolor="#009900"><span class="table-header-text">Value</span></td>
  </tr>
  <%
  	Cookie[] cos=request.getCookies();
   	if(cos!=null){
		for(int i=0;i<cos.length;i++)
		{   
  %>
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;<%=cos[i].getName()%></td>
    <td bgcolor="#FFFFFF">&nbsp;<%=cos[i].getValue()%></td>
  </tr>
  <%
		}
  	}
  %>
</table>

<h3>&nbsp;</h3>
<p><br>
</p>
</body>
</html>
<%
long endMem = Runtime.getRuntime().freeMemory(); 
long total= Runtime.getRuntime().maxMemory();
out.println("Total Memory:"+total);
out.println("Start Memory:"+startMem);
out.println("End Memory:"+endMem);
out.println("Use memory: "+ (startMem - endMem)); 
long endTime = System.currentTimeMillis(); 
out.println("Use Time: "+ (endTime-startTime));
%>