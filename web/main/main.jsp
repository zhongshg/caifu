<%@page import="java.util.ArrayList"%>
<%@page import="job.tot.dao.DaoFactory"%>
<%@page import="job.tot.bean.DataField"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta HTTP-EQUIV = "content-type" CONTENT = "text/html; charset=UTF-8" />
        <title> 库存不足提醒 </title>
        <link href = "style/global.css" rel = "stylesheet" type = "text/css" />
        <link rel = "stylesheet" type = "text/css" href = "${pageContext.servletContext.contextPath}/css/time/themes/icon.css" />
        <link rel = "stylesheet" type = "text/css" href = "${pageContext.servletContext.contextPath}/css/time/demo.css" />
        <link rel = "stylesheet" type = "text/css" href = "${pageContext.servletContext.contextPath}/css/time/themes/default/easyui.css" />
        <script type="text/javascript" src="/js/jquery.js"></script>
        <script type = "text/javascript" src = "${pageContext.servletContext.contextPath}/css/time/jquery-1.8.0.min.js" ></script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/css/time/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/wdp/WdatePicker.js" charset="utf-8" defer="defer"></script>
        <style type="text/css">
            <!--
            .style1 {color: #FFFFFF}
            .style2 {
                color: #FFFFFF;
                font-weight: bold;
                font-size: 12px;
            }
            .style3 {
                color: #FF9900;
                font-weight: bold;
            }
            -->
        </style>
        <style type="text/css">
            /*body { background:#333333;}*/
            /*#winpop { width:300px; height:0px; position:absolute; right:0; bottom:0; border:1px solid #999999; margin:0; padding:1px; overflow:hidden;  background:#FFFFFF}*/
            #winpop .title { width:100%; height:20px; line-height:20px; background:#FF9900; font-weight:bold; text-align:center; font-size:12px;}
            #winpop .con { width:100%; height:30px; line-height:30px; font-weight:bold; font-size:12px; color:#FF0000; text-decoration:underline; text-align:center}
            #silu { font-size:13px; color:#999999; position:absolute; right:0; text-align:right; text-decoration:underline; line-height:22px;}
            .close { position:absolute; right:4px; top:-1px; color:#FFFFFF; cursor:pointer}
            a{
                color: red;
            }
        </style>
    </head>
    <%
        Map<String, String> wx = (Map<String, String>) request.getAttribute("wx");
        //取出库存不足产品
        List<DataField> productWarnList = new ArrayList<DataField>();
        List<DataField> productList = (List<DataField>) DaoFactory.getProductDAO().getList(wx.get("id"));
        List<DataField> psvList = null;
        int signid = 0;
        for (DataField product : productList) {
            //判断有无属性
            psvList = (List<DataField>) DaoFactory.getPsvDaoImplJDBC().getByStockList(product.getInt("id"), signid, wx.get("id"));
            if (0 < psvList.size()) {//以属性为主
                for (DataField psv : psvList) {
                    if (product.getInt("id") == psv.getInt("pid")) {
                        productWarnList.add(product);
                        break;
                    }
                }
            } else {
                if (product.getInt("IsRem") <= product.getInt("JiangShi")) {
                    productWarnList.add(product);
                }
            }
        }
    %>
    <body>
        <p style="height: 30px;">&nbsp;</p>
        <body>
            <div id="winpop">
                <div class="title">库存不足提醒！<span class="close" onclick="tips_pop()"></span></div>
                    <%
                        for (DataField product : productWarnList) {
                    %>
                <div class="con">
                    <%--a href="${pageContext.servletContext.contextPath}/shop/manage/p_mod.jsp?id=<%=product.getFieldValue("id")%>"--%>
                    <%=product.getFieldValue("ProCode")%>&nbsp;<%=product.getFieldValue("Title")%>&nbsp;库存量：<%=product.getFieldValue("IsRem")%>
                    <%--</a>--%>
                    <p/>
                </div>
                <%
                    }
                %>
            </div>
        </body>
    </body>
</html>
