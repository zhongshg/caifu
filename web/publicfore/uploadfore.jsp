<%-- 
    Document   : upload
    Created on : 2014-7-9, 14:31:48
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
    </head>
    <body>
        <form action="${pageContext.servletContext.contextPath}/ForeServlet?method=upload" method="post" enctype="multipart/form-data" id="fileform">
            <input type="hidden" name="oldimg" value="${param.oldimg }"/>
            <input type="hidden" name="img" value="${requestScope.img }"/>
            <input type="file" name="file" onchange="javascript:submitform()"/>
            <!--<input type="submit" value="上传"/>-->
        </form>
        <script language=javascript>
                function submitform() {
                    parent.document.getElementById("submitflag").value = "1";
                    document.getElementById("fileform").submit();
                }
        </script>
        <c:if test="${!empty requestScope.img }">
            <script>
                parent.document.getElementById("img").value = "${requestScope.img}";
                parent.document.getElementById("submitflag").value = "0";
                parent.document.getElementById("result").innerHTML = "<img src='<%=request.getScheme() + "://" + request.getServerName()%>${pageContext.servletContext.contextPath}${requestScope.img}' width='50px;' height='50px;'>";
            </script>
        </c:if>
    </body>
</html>
