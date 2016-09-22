<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ include file="../inc/common.jsp"%>
<%@ include file="session.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
            <title>分类管理</title>
            <link href="style/global.css" rel="stylesheet" type="text/css" />
            <script src="js/xmlhttp.js" type="text/javascript"></script>
            <script language="javascript" src="js/tree_view.js"></script>
            <script type="text/javascript">
                function addChild(pdiv, classid, classtitle) {
                    CreateTreeItem(pdiv, "images/tree/folder_closed.gif", "images/tree/folder_open.gif", classid, classtitle, "category_mod.jsp?id=" + classid, "_self", 1);
                }
                function ConfirmDel()
                {
                    if (confirm('确认删除吗？'))
                        return true;
                    else
                        return false;
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
        <%@ include file="top.jsp"%><br />
        <div class="style1" style="background-color:#3872b2; border:1px solid #CCCCCC; padding:5px 0px; text-align:center; width:98%; margin:0px auto; margin-bottom:10px;">分类管理</div>
        <%
            ArrayList categoryList = (ArrayList) DaoFactory.getCategoryDAO().getCategoryByParentId(0, -1, wx.get("id"));
            out.println("<script language=\"JavaScript\" type=\"text/JavaScript\">");
            out.println("function CreateProjectExplorer()");
            out.println("{");
            out.println("	Initialise();");
            out.println("	d=CreateTreeItem( rootCell, \"images/tree/project.gif\", \"images/tree/project.gif\",0,\"ROOT\", null, null,2);");
            for (Iterator iter = categoryList.iterator(); iter.hasNext();) {
                DataField df = (DataField) iter.next();
                String id = df.getFieldValue("id");
                out.println("	CreateTreeItem(d,\"images/tree/folder_closed.gif\", \"images/tree/folder_open.gif\"," + id + ", \"" + df.getFieldValue("Title") + "\", \"category_mod.jsp?id=" + id + "\",\"_self\",1);");
            }
            out.println("}");
            out.println("</script>");
        %>

        <table>
            <tr>
                <td>

                </td>
            </tr>
        </table>
    </body>
</html>
