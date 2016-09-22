<%-- 
    Document   : chatroom
    Created on : 2015-12-9, 15:25:22
    Author     : Administrator
--%>

<%@page import="wap.wx.dao.SubscriberDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../shop/inc/common.jsp"%>

<!DOCTYPE html>
<html>
    <%        if (null != RequestUtil.getString(request, "wx")) {
            wxsid = RequestUtil.getString(request, "wx");
            session.setAttribute("wxsid", wxsid);
        }
        if (null != RequestUtil.getString(request, "openid")) {
            //此处可嵌入网页获取openid代码，也可通过图文信息获得
            openid = RequestUtil.getString(request, "openid");
            session.setAttribute("openid", openid);
        }
//取出微信信息
        Map<String, String> wx = new HashMap<String, String>();
        wx.put("id", wxsid);
        wx = new WxsDAO().getById(wx);

        String targetopenid = RequestUtil.getString(request, "targetopenid");
        //取出双方的头像和昵称
        SubscriberDAO subscriberDAO = new SubscriberDAO();
        Map<String, String> subscriber = subscriberDAO.getByOpenid(wxsid, openid);
        Map<String, String> targetsubscriber = subscriberDAO.getByOpenid(wxsid, targetopenid);
        //id,openid.targetopenid,content,isreader.times chatroom
        //查询有无未读记录
        String fieldArr = "id,openid,targetopenid,content,isreader,times";
        List<DataField> noreaderlist = (List<DataField>) DaoFactory.getMysqlDao().getList("select " + fieldArr + " from chatroom where openid='" + targetopenid + "' and targetopenid='" + openid + "' and isreader=0", fieldArr);
        int id = 0;
    %>
    <head>
        <meta http-equiv="Content-Type" content="textml; charset=utf-8">
        <meta http-equiv=”Expires” CONTENT=”0″>
        <meta http-equiv=”Cache-Control” CONTENT=”no-cache”>
        <meta http-equiv=”Pragma” CONTENT=”no-cache”>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,  user-scalable=0">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="format-detection" content="telephone=no">
        <title><%=wx.get("name")%></title>
        <link rel="stylesheet" type="text/css" href="../shop3/css/css.css">
        <style>
            div{
                font-family: "微软雅黑";
                font-size: 12px;
            }
        </style>
        <script src="${pageContext.servletContext.contextPath}/js/jquery.js"></script>
        <script>
            $(document).ready(function() {
                $("#button").click(function() {
                    var content = $("#content").val();
                    var sign = $("#sign").val();
                    $.post("${pageContext.servletContext.contextPath}/ForeServlet?method=chatroom", {"openid": "<%=openid%>", "nickname": "<%=subscriber.get("nickname")%>", "headimgurl": "<%=subscriber.get("headimgurl")%>", "targetopenid": "<%=targetopenid%>", "content": content, "sign": sign, "wxsid": "<%=wx.get("id")%>"}, function(result) {
                        $("#content").val("");
                        result = eval("(" + result + ")");
                        $("#sign").val(1);
                        var htmlStr = "<div style=\"clear: both;float:right;\"><table><tr style=\"text-align: right;\"><td>" + result.nickname + "</td><td rowspan=\"2\"><img src=\"" + result.headimgurl + "\" width='35' height='35'/></td></tr><tr style=\"text-align: right;\"><td>" + result.content + "</td></tr></table></div>";
                        $("#message").append(htmlStr);
                        var div = $("#message");
                        div.scrollTop(div.scrollTop() + 1000);
                    });
                });
                window.setInterval(function() {
                    $.post("${pageContext.servletContext.contextPath}/ForeServlet?method=chatroomAsync", {"openid": "<%=openid%>", "targetopenid": "<%=targetopenid%>", "nickname": "<%=targetsubscriber.get("nickname")%>", "headimgurl": "<%=targetsubscriber.get("headimgurl")%>", "id": $("#id").val()}, function(result) {
                        if ("" != result) {
                            result = eval("(" + result + ")");
                            var htmlStr = "";
                            var id = 0;
                            for (var i = 0; i < result.length; i++) {
                                htmlStr += "<div style=\"clear: both;float: left;\"><table><tr style=\"text-align: left;\"><td rowspan=\"2\"><img src=\"" + result[i].headimgurl + "\" width='35' height='35'/></td><td>" + result[i].nickname + "</td></tr><tr style=\"text-align: left;\"><td>" + result[i].content + "</td></tr></table></div>";
                                id = result[i].id;
                            }
                            $("#id").val(id);
                            $("#message").append(htmlStr);
                            var div = $("#message");
                            div.scrollTop(div.scrollTop() + 1000);
                        }
                    });
                }, 3000);
            });
            //                    var htmlleft = "<div style=\"clear: both;float: left;\"><table><tr><td rowspan=\"2\">sdf</td><td>name</td></tr><tr><td>content</td></tr></table></div>";
//                    var htmlright = "<div style=\"clear: both;float:right;\"><table><tr><td>name</td><td rowspan=\"2\">sdf</td></tr><tr><td>content</td></tr></table></div>";
        </script>
    </head>

    <body>
        <p style="height: 15px;">&nbsp;</p>
        <div class="zong" style="text-align: center;">
            <div id="message" style="border: #0099CC solid 1px;width: 90%;height:300px;overflow:auto;margin-left: auto;margin-right: auto;">
                <%
                    for (DataField noreader : noreaderlist) {
                        id = noreader.getInt("id");
                        if (openid.equals(noreader.getFieldValue("openid"))) {
                %>
                <div style="clear: both;float:right;">
                    <table>
                        <tr style="text-align: right;">
                            <td><%=subscriber.get("nickname")%></td>
                            <td rowspan="2"><img src="<%=subscriber.get("headimgurl")%>" width="35" height="35"/></td>
                        </tr>
                        <tr style="text-align: right;">
                            <td><%=noreader.getFieldValue("content")%></td>
                        </tr>
                    </table>
                </div>  
                <%                        } else {
                %>
                <div style="clear: both;float: left;">
                    <table>
                        <tr style="text-align: left;">
                            <td rowspan="2"><img src="<%=targetsubscriber.get("headimgurl")%>" width="35" height="35"/></td>
                            <td><%=targetsubscriber.get("nickname")%></td>
                        </tr>
                        <tr style="text-align: left;">
                            <td><%=noreader.getFieldValue("content")%></td>
                        </tr>
                    </table>
                </div>
                <%                        }
                    }
                %>
                <!--                <div style="clear: both;float: left;">
                                    <table>
                                        <tr>
                                            <td rowspan="2">sdf</td>
                                            <td>name</td>
                                        </tr>
                                        <tr>
                                            <td>content</td>
                                        </tr>
                                    </table>
                                </div>
                                <div style="clear: both;float:right;">
                                    <table>
                                        <tr>
                                            <td>name</td>
                                            <td rowspan="2">sdf</td>
                                        </tr>
                                        <tr>
                                            <td>content</td>
                                        </tr>
                                    </table>
                                </div>-->
            </div>

            <p style="clear: both;">&nbsp;</p>
            <div class="xz003">
                <input type="hidden" id="sign" value="${"callback"==param.act?1:0}"/>
                <input type="hidden" id="id" value="<%=id%>"/>
                <input type="text" id="content" style="border: coral solid 1px;color:black;background-color: white;width:70%;border-radius: 5px;height: 30px;line-height: 30px;"/>
                <input type="button" id="button" style="width:20%;border-radius: 5px;height: 30px;line-height: 30px;-webkit-appearance: none;" class="" value="发送">
            </div>
        </div>
    </body>
</html>
