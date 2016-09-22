<%-- 
    Document   : uploadmaterial
    Created on : 2014-7-9, 14:31:48
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
    </head>
    <c:if test="${null!=message}">
        <script>
            alert("${message}");
        </script>
    </c:if>
    <c:if test="${null!=(null!=media_id?media_id:thumb_media_id)&&null!=type}" var="flag">
        <script>
//            parent.document.getElementById("result").innerHTML = "<img src='<%=request.getScheme() + "://" + request.getServerName()%>${pageContext.servletContext.contextPath}${requestScope.img}' width='200' height='200'/>";
            parent.document.getElementById("mediaid").value = "${media_id}";
            parent.document.getElementById("url").value = "${url}";
            parent.document.getElementById("type").value = "${type}";
        </script>
    </c:if>
    <c:if test="${'-1'==errcode}">
        <script>
            alert("系统繁忙，请稍后再试！");
        </script>
    </c:if>
    <c:if test="${'40004'==errcode||'40005'==errcode||'40007'==errcode}">
        <script>
            alert("媒体类型错误！");
        </script>
    </c:if>
    <c:if test="${'40006'==errcode}">
        <script>
            alert("上传媒体太大！");
        </script>
    </c:if>
    <body>
        <form action="${pageContext.servletContext.contextPath}/servlet/PublicServlet?method=peruploadmaterial" method="post" enctype="multipart/form-data" onsubmit="return test();">
            文件类型：<select id="type" name="type" onchange="javascript:typechange();">
                <!--群发图文类型也为news，这里不再区分-->
                <option value="news" ${"news"==type||"news"==param.type?"selected='selected'":""}>图文</option>
                <option value="image" ${"image"==type||"image"==param.type?"selected='selected'":""}>图片(JPG/<1mb)</option>
                <option value="voice" ${"voice"==type||"voice"==param.type?"selected='selected'":""}>语音(AMR\MP3/<2mb\60s)</option>
                <option value="video" ${"video"==type||"video"==param.type?"selected='selected'":""}>视频(MP4/<10mb)</option>
                <option value="thumb" ${"thumb"==type||"thumb"==param.type?"selected='selected'":""}>缩略图(JPG/<64kb)</option>
            </select><br/>
            <div id="mediadiv" style="display: none;">
                文件:<input type="file" id="media" name="media"/>
            </div>
            <div id="newsmdiv">
                图文信息：<select id="newsm" name="newsm">
                    <option value="0">请选择上传图文信息</option>
                    <c:forEach items="${newsmList}" var="newsm">
                        <option value="${newsm.id}">${newsm.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div id="titlediv" style="display: none;">
                视频名称(视频)<input class="text-input large-input datepicker" type="text" id="title" name="title" value=""/>
            </div>
            <div id="introductiondiv" style="display: none;">
                视频描述(视频)<textarea class="text-input" id="introduction" name="introduction" cols="79" rows="5" ></textarea></div>
            <input type="submit" value="上传"/>
        </form>
        <script>
            function test() {
                var type = document.getElementById("type").value;
                var newsm = document.getElementById("newsm").value;
                var title = document.getElementById("title").value;
                var introduction = document.getElementById("introduction").value;
                if ("news" == type && "0" == newsm) {
                    alert("请选择上传图文信息");
                    return false;
                } else if ("video" == type && ("" == title || "" == introduction)) {
                    alert("视频信息不能为空！");
                    return false;
                }
            }
            var type = document.getElementById("type").value;
            if ("news" == type) {
                document.getElementById("newsmdiv").style.display = "block";
                document.getElementById("mediadiv").style.display = "none";
                document.getElementById("titlediv").style.display = "none";
                document.getElementById("introductiondiv").style.display = "none";
            } else if ("video" == type) {
                document.getElementById("newsmdiv").style.display = "none";
                document.getElementById("mediadiv").style.display = "block";
                document.getElementById("titlediv").style.display = "block";
                document.getElementById("introductiondiv").style.display = "block";
            } else {
                document.getElementById("newsmdiv").style.display = "none";
                document.getElementById("mediadiv").style.display = "block";
                document.getElementById("titlediv").style.display = "none";
                document.getElementById("introductiondiv").style.display = "none";
            }
            function typechange() {
                type = document.getElementById("type").value;
                if ("news" == type) {
                    document.getElementById("newsmdiv").style.display = "block";
                    document.getElementById("mediadiv").style.display = "none";
                    document.getElementById("titlediv").style.display = "none";
                    document.getElementById("introductiondiv").style.display = "none";
                } else if ("video" == type) {
                    document.getElementById("newsmdiv").style.display = "none";
                    document.getElementById("mediadiv").style.display = "block";
                    document.getElementById("titlediv").style.display = "block";
                    document.getElementById("introductiondiv").style.display = "block";
                } else {
                    document.getElementById("newsmdiv").style.display = "none";
                    document.getElementById("mediadiv").style.display = "block";
                    document.getElementById("titlediv").style.display = "none";
                    document.getElementById("introductiondiv").style.display = "none";
                }
            }
        </script>
    </body>
</html>
