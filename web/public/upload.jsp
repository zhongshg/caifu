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
        <form action="${pageContext.servletContext.contextPath}/servlet/PublicServlet?method=upload" method="post" enctype="multipart/form-data">
            <input type="hidden" name="oldimg" value="${param.oldimg }"/>
            <input type="hidden" name="img" value="${requestScope.img }"/>
            <input type="file" name="file"/>
            <input type="submit" value="上传" style="color: blue;"/>
        </form>
        <c:if test="${!empty requestScope.img }">
            <script>
                parent.document.getElementById("img").value = "${requestScope.img}";
                parent.document.getElementById("result").innerHTML = "<img src='<%=request.getScheme() + "://" + request.getServerName()%>${pageContext.servletContext.contextPath}${requestScope.img}' width='200' height='200'/>";
            </script>
        </c:if>
    </body>
</html>
