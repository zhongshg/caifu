<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<%
//---------------------------------------------------------
//if(!DaoFactory.getAdminDAO().ifHasPrivilege(haspriv,"p01"))
//throw new NoPrivilegeException("you don't have privilege");
//---------------------------------------------------------
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <fmt:bundle basename="resources.totcms_i18n">
        <head>
            <meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
                <title><fmt:message key="totcms.admin.category.select" /></title>
                <link href="style/global.css" rel="stylesheet" type="text/css" />
                <script src="js/xmlhttp.js" type="text/javascript"></script>
                <script language="javascript" src="js/tree_view.js"></script>
                <script type="text/javascript">
                    function addChild(pdiv, classid, classtitle) {
                        CreateTreeItem(pdiv, "images/tree/folder_closed.gif", "images/tree/folder_open.gif", classid, classtitle, "javascript:SelectFile('" + classid + "','" + classtitle + "');", "_self", 0);
                    }
                    function isIE() {
                        var agentStr = navigator.userAgent;
                        if (agentStr.indexOf("MSIE") != -1)
                        {
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                </script>
                <style type="text/css">
                    <!--
                    .style1 {
                        color: #FFFFFF;
                        font-weight: bold;
                    }
                    -->
                </style>
        </head>
        <body ondragstart="return false;" onselectstart="return false;"  onLoad="CreateProjectExplorer()">
            <%
                ArrayList categoryList = (ArrayList) DaoFactory.getCategoryDAO().getCategoryByParentId(0, wx.get("id"));
                out.println("<script language=\"JavaScript\" type=\"text/JavaScript\">");
                out.println("function CreateProjectExplorer()");
                out.println("{");
                out.println("	Initialise();");
                out.println("	d=CreateTreeItem( rootCell, \"images/tree/project.gif\", \"images/tree/project.gif\",0,\"ROOT\", \"javascript:SelectFile('0','ROOT')\", \"_self\",0);");
                for (Iterator iter = categoryList.iterator(); iter.hasNext();) {
                    DataField df = (DataField) iter.next();
                    String id = df.getFieldValue("id");
                    String title = df.getFieldValue("Title");
                    out.println("	CreateTreeItem(d,\"images/tree/folder_closed.gif\", \"images/tree/folder_open.gif\"," + id + ", \"" + title + "\", \"javascript:SelectFile('" + id + "','" + title + "')\",\"_self\",0);");
                }
                out.println("}");
                out.println("</script>");
            %>
            <script type="text/javascript">
                    function SelectFile(classid, classtitle)
                    {
                        var opener = window.opener;
                        //var cateobj=opener.document.addCategory.parentid;
                        var cateobj = opener.document.getElementById('CategoryId');
                        if (isIE()) {
                            var newOpt = opener.document.createElement("option");
                            newOpt.text = classtitle;
                            newOpt.value = classid;
                            opener.document.all.CategoryId.options[0] = newOpt;
                        }
                        else {
                            cateobj.options[0] = new Option(classtitle, classid);
                        }
                        window.close();
                    }
            </script>
    </fmt:bundle>
</body>
</html>
