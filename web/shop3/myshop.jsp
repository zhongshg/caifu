<%-- 
    Document   : myshop
    Created on : 2015-8-27, 16:13:51
    Author     : Administrator
--%>

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
    %>
    <head>
        <meta http-equiv="Content-Type" content="textml; charset=utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,  user-scalable=0">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="format-detection" content="telephone=no">
        <meta http-equiv=”Expires” CONTENT=”0″>
        <meta http-equiv=”Cache-Control” CONTENT=”no-cache”>
        <meta http-equiv=”Pragma” CONTENT=”no-cache”>
        <title><%=wx.get("name")%></title>
        <link rel="stylesheet" type="text/css" href="/shop3/css/css.css">
        <style>
            .xz001{ width:100%; height:50px; line-height:50px; font-size:20px; text-align:center; background-color:<%=wx.get("weimyshopcolor")%>; color:<%=wx.get("weimyshoptextcolor")%>; text-decoration:none}
            .xz003 input[type=button]{ width:90%; height:45px; line-height:45px; font-size:18px; text-align:center; background-color:<%=wx.get("weimyshopcolor")%>; color:<%=wx.get("weimyshoptextcolor")%>; border-radius:30px; border:none; line-height:40px;cursor: pointer; -webkit-appearance: none;}
        </style>
        <script type="text/javascript" src="/js/jquery.js"></script>
    </head>
    <%
        String act = RequestUtil.getString(request, "act");
        if ("delshop".equals(act)) {
            String id = RequestUtil.getString(request, "id");
            String s[] = {id};
            DaoFactory.getBasketDAO().batDel(s);
            act = null;
        }
        String F_No = "";
//        if ("buy".equals(act)) {
//            int id = RequestUtil.getInt(request, "id");
//            int count = RequestUtil.getInt(request, "count");
////            String name = RequestUtil.getString(request, "name");
////            String phone = RequestUtil.getString(request, "phone");
////            String weixin = RequestUtil.getString(request, "weixin");
////            String address = RequestUtil.getString(request, "address");
//            String remark = RequestUtil.getString(request, "note");
//            String propertys = RequestUtil.getString(request, "propertys");
//
//            DataField df = DaoFactory.getProductDAO().get(id);
//            String fNum = String.valueOf(System.currentTimeMillis());
//            Float FirstYJ = df.getFloat("distributionfirstdiscount") * count;
//            Float SecondYJ = df.getFloat("distributionseconddiscount") * count;
//            Float ThirdYJ = df.getFloat("distributionthirddiscount") * count;
//            if (1 == df.getInt("distributiontype")) {
//                FirstYJ = df.getFloat("distributionmoney") * df.getFloat("distributionfirstdiscount") * count;
//                SecondYJ = df.getFloat("distributionmoney") * df.getFloat("distributionseconddiscount") * count;
//                ThirdYJ = df.getFloat("distributionmoney") * df.getFloat("distributionthirddiscount") * count;
//            }
////            //判断有无地址相同
////            DataField temp = DaoFactory.getBasketDAO().getBySuserIdUidAddress(wxsid, openid, name, phone, weixin, address, remark, 1);
////            if (null != temp) {
////                fNum = temp.getFieldValue("F_No");
////            } else {
////                DaoFactory.getAddressDAO().add(openid, name, address, "", "", "", phone, "", weixin, remark);
////            }
//            boolean bl = DaoFactory.getBasketDAO().add(id, df.getFieldValue("Title"), openid, wxsid, request.getRemoteAddr(), fNum, df.getFieldValue("ProCode"), count, 1, new Timestamp(System.currentTimeMillis()), df.getFloat("Price"), df.getFloat("Price") * count, df.getString("ViewImg"), "", "", "", "", remark, FirstYJ, SecondYJ, ThirdYJ, propertys);
//            if (bl) {
////                if ("buy".equals(act)) {
//////                    response.sendRedirect(request.getContextPath() + "/shop2/shop.jsp?act=cart&wx=" + wxsid + "&openid=" + openid);
////                    response.sendRedirect(request.getContextPath() + "/shop3/myshop.jsp?wx=" + wxsid + "&openid=" + openid);
////                }
//                act = null;
//            }
//        }
        List<DataField> cartList = (List<DataField>) DaoFactory.getBasketDAO().getListByUidp(openid, wxsid, 1, -1, -1);
        //购买时隐藏购物车
        if ("buy".equals(act)) {
            cartList = (List<DataField>) DaoFactory.getBasketDAO().getListByUidp(openid, wxsid, 0, 1, 1);
        }
    %>
    <body>
        <script type='text/javascript'>
            function delshops(id) {
                if (confirm("确认删除？")) {
                    location.replace("?act=delshop&id=" + id + "&wx=<%=wxsid%>&openid=<%=openid%>");
                }
            }
        </script>
        <div class="xz001"><%="buy".equals(act) ? "我的订单" : "购物车"%></div>
        <div class="zong">
            <%
                if (0 < cartList.size()) {
                    float totalMoney = 0;
                    for (DataField cart : cartList) {
                        totalMoney += cart.getFloat("Tot_Price");
            %>
            <table width="100%" border="0" cellspacing="0" cellpadding="10" class="dingdan00">
                <tr>
                    <td width="50" height="40" align="center"><input class="dingdan00_an" type="checkbox" name="carts" id="<%=cart.getFieldValue("id")%>" value="<%=cart.getFieldValue("id")%>" onclick="checkboxclick('<%=cart.getFieldValue("id")%>');" checked>
                        <label for="checkbox"> </label></td>
                    <td width="0" align="left" style="padding:10px;"><%=cart.getFieldValue("Pcode")%>&nbsp;&nbsp;&nbsp;<%=cart.getFieldValue("Pname")%></td>
                    <td width="0" align="right" style="padding:10px;"><span style="padding:10px;color:#b9b9b9">
                            <input style="height:30px;" type="button" name="submit" id="submit" value="删除" onclick="delshops(<%=cart.getFieldValue("id")%>);">
                        </span></td>
                </tr>
                <tr>
                    <td height="96" colspan="3" style=" background-color:#f6f6f6; border-radius: 5px; "><table width="100%" border="0" cellspacing="0" cellpadding="10">
                            <tr>
                                <td width="100" rowspan="2" align="center"><img src="<%=cart.getFieldValue("ViewImg")%>" width="80" height="80"/></td>
                                <td width="" height="21">
                                    数量：<%=cart.getFieldValue("Pnum")%>&nbsp;
                                    <%
                                        String propertys = cart.getFieldValue("propertys").trim();
                                        if (!"".equals(propertys)) {
                                            String propertysArr[] = propertys.split(" ");
                                            for (int i = 0; i < propertysArr.length; i++) {
                                                if ("".equals(propertysArr[i])) {
                                                    continue;
                                                }
                                                DataField property = DaoFactory.getPropertysDaoImplJDBC().get(propertysArr[i].split("-")[0]);
                                                DataField propertySub = DaoFactory.getPropertysDaoImplJDBC().get(propertysArr[i]);
                                                if (null == property || null == propertySub) {
                                                    continue;
                                                }
                                    %>
                                    <%=property.getFieldValue("svname")%>:<%=propertySub.getFieldValue("svname")%>&nbsp;
                                    <%
                                            }
                                        }
                                    %>
                                </td>
                            </tr>
                            <tr>
                                <td height="21">价格：<font color="#FF0000" id="ptotalprice_<%=cart.getFieldValue("id")%>"><%=cart.getFieldValue("Tot_Price")%></font>元</td>
                            </tr>
                        </table></td>
                </tr>
            </table> 
            <%
                }
                DataField df = DaoFactory.getAddressDAO().getUserId(openid, wxsid);
            %>
            <table width="100%" border="0" cellspacing="0" cellpadding="10" class="dingdan00">
                <tr>
                    <td width="50" height="40" align="center"><label for="checkbox"> </label></td>
                    <td width="0" align="left" ><font color="#FF0000" id="totalPrice">总价格:<%=totalMoney%></font></td>
                </tr>
            </table>
            <form name="infoForm2" id="infoForm" method="post" action="">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="xz006">
                    <tr>
                        <td width="100%" height="29" bgcolor="#DADADA">&nbsp;&nbsp;收货信息</td>
                    </tr>
                    <tr>
                        <td height="32">
                            <div class="xz002"><label><span style="color:red">*</span>联系人：</label><input type="text" id="name" name="name" value="<%=null != df ? df.getString("Name") : ""%>"></div>
                            <div class="xz002"><label><span style="color:red">*</span>联系电话：</label><input type="tel" id="phone" name="phone"  value="<%=null != df ? df.getString("Phone") : ""%>"></div>
                            <div class="xz002"><label>微信号：</label><input type="text" id="weixin" name="weixin"  value="<%=null != df ? df.getString("Weixin") : ""%>"></div>
                            <div class="xz002">
                                <!--                                </div>
                                                            <div class="xz002">-->
                                <label><span style="color:red">*</span>联系地址：</label>
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <%
                                            List<DataField> prolist = (List<DataField>) DaoFactory.getAreaDaoImplJDBC().getList(0);
                                            Iterator<DataField> proit = prolist.iterator();
                                        %>
                                        <!--                                        <td align="right" bgcolor="#FDFDFD" class="form_td">省：</td>-->
                                        <td  bgcolor="#FDFDFD" class="form_td">
                                            <select name="provience" id="province" style="width: 75px;">
                                                <option value="0">省份</option>
                                                <%
                                                    while (proit.hasNext()) {
                                                        DataField pro = proit.next();
                                                %>
                                                <option value="<%=pro.getFieldValue("id")%>" <%=pro.getFieldValue("id").equals((null != df ? df.getFieldValue("Sname") : "0")) ? "selected='selected'" : ""%>><%=pro.getFieldValue("title")%></option>
                                                <%
                                                    }
                                                %>
                                            </select>
                                        </td>
                                        <!--<td align="right" bgcolor="#FDFDFD" class="form_td">市：</td>-->
                                        <td  bgcolor="#FDFDFD" class="form_td">
                                            <select name="city" id="city" style="width: 75px;">
                                                <option value="0">城市</option>
                                                <%
                                                    List<DataField> citylist = (List<DataField>) DaoFactory.getAreaDaoImplJDBC().getList(null != df ? df.getInt("Sname") : -1);
                                                    Iterator<DataField> cityit = citylist.iterator();
                                                    while (cityit.hasNext()) {
                                                        DataField city = cityit.next();
                                                %>
                                                <option value="<%=city.getFieldValue("id")%>" <%=city.getFieldValue("id").equals((null != df ? df.getFieldValue("Ssname") : "0")) ? "selected='selected'" : ""%>><%=city.getFieldValue("title")%></option>
                                                <%
                                                    }
                                                %>
                                            </select>
                                        </td>
                                        <!--<td align="right" bgcolor="#FDFDFD" class="form_td">区：</td>-->
                                        <td  bgcolor="#FDFDFD" class="form_td">
                                            <select name="area" id="area" style="width: 75px;">
                                                <option value="0">区域</option>
                                                <%
                                                    List<DataField> arealist = (List<DataField>) DaoFactory.getAreaDaoImplJDBC().getList(null != df ? df.getInt("Ssname") : -1);
                                                    Iterator<DataField> areait = arealist.iterator();
                                                    while (areait.hasNext()) {
                                                        DataField area = areait.next();
                                                %>
                                                <option value="<%=area.getFieldValue("id")%>" <%=area.getFieldValue("id").equals((null != df ? df.getFieldValue("Xname") : "0")) ? "selected='selected'" : ""%>><%=area.getFieldValue("title")%></option>
                                                <%
                                                    }
                                                %>
                                            </select>
                                        </td>
                                    </tr>
                                </table>
                                <br/>
                                <textarea id="address" name="address"><%=null != df ? df.getString("Address") : ""%></textarea></div></td>
                    </tr>
                    <tr>
                        <td height="33">&nbsp;</td>
                    </tr>
                    <tr>
                        <td height="36">&nbsp;</td>
                    </tr>
                </table>
            </form>
            <div class="xz003">
                <input type="button" id="submitbutton" class="" value="立刻下单" onclick="javascript:addorder();">
            </div>
            <script type='text/javascript'>
            testbutton();
            $("#name").keyup(function() {
                testbutton();
            });
            $("#phone").keyup(function() {
                testbutton();
            });
            $("#weixin").keyup(function() {
                testbutton();
            });
            $("#address").keyup(function() {
                testbutton();
            });
            $("#province").change(function() {
                $("#submitbutton").css("backgroundColor", "grey");
            });
            $("#city").change(function() {
                $("#submitbutton").css("backgroundColor", "grey");
            });
            $("#area").change(function() {
                testbutton();
            });
            function testbutton() {
                var name = $('#name').val();
                var phone = $('#phone').val();
                var weixin = $('#weixin').val();//weixin.length > 0 &&
                var address = $('#address').val();
                var province = $('#province').val();
                var city = $('#city').val();
                var area = $('#area').val();
                if (name.length > 0 && phone.length > 0 && /^[0-9]*$/.test(phone) &&  province != 0 && city != 0 && area != 0 && address.length > 0)
                {
                    $("#submitbutton").css("backgroundColor", "<%=wx.get("weimyshopcolor")%>");
                } else {
                    $("#submitbutton").css("backgroundColor", "grey");
                }
            }
            function addorder() {
                var infoForm = $("#infoForm").serialize();
                //判断当天订单限数量
                $.post("public_do.jsp?act=testaddorder", infoForm, function(result) {
                    if ("0" != result.trim()) {
                        alert('今日已超订单限额，请明天再下单！');
                        return false;
                    } else {

                        var cartArr = $('input:checkbox[name=carts]:checked');
                        if (0 >= cartArr.length) {
                            alert("您没有选择任何产品，请先选择产品吧！");
//                    setTimeout(function() {
//                        window.location.replace("/shop2/shop.jsp?wx=<%=wxsid%>&openid=<%=openid%>")
//                    }, 3000);
                            return false;

                        } else {
                            var carts = "";
                            for (var i = 0; i < cartArr.length; i++) {
                                carts += cartArr[i].value + ",";
                            }

                            var name = $('#name').val();
                            var phone = $('#phone').val();
                            var weixin = $('#weixin').val();
                            var address = $('#address').val();

                            var province = $('#province').val();
                            var city = $('#city').val();
                            var area = $('#area').val();
                            if (name.length <= 0)
                            {
                                alert('请输入联系人');
                                return false;
                            } else

                            if (phone.length <= 0)
                            {
                                alert('请输入电话');
                                return false;
                            } else
                            if (!/^[0-9]*$/.test(phone))
                            {
                                alert('请输入正确电话');
                                return false;
                            } else
                           // if (weixin.length <= 0)
                           // {
                           //     alert('请输入微信号');
                           //     return false;
                           // } else

                            if (province == 0)
                            {
                                alert('请选择省');
                                return false;
                            } else
                            if (city == 0)
                            {
                                alert('请选择市');
                                return false;
                            } else
                            if (area == 0)
                            {
                                alert('请选择县');
                                return false;
                            } else
                            if (address.length <= 0)
                            {
                                alert('请输入地址');
                                return false;
                            } else {
                                $("#submitbutton").attr("disabled", true);
                                $.post("public_do.jsp?act=addorder&carts=" + carts, infoForm, function(result) {
                                    if ("1" != result.trim()) {
                                        window.location.replace("/shop3/orderbuy.jsp?wx=<%=wxsid%>&openid=<%=openid%>&carts=" + carts + "&Fnos=" + result.trim());
                                    } else {
                                        alert('系统繁忙，请稍后重试！');
                                        window.go(-1);
                                    }
                                });
//                            var form = document.getElementById("infoForm");
//                            form.action = "/shop3/orderbuy.jsp?wx=<%=wxsid%>&openid=<%=openid%>&carts=" + carts + "&act=addorder";
//                            form.submit();
                            }
                        }
                    }
                });
            }
            function checkboxclick(id) {
                var totalPrice = $("#totalPrice").html().split(":")[1];
                var checkbox = $("#" + id);
                if (checkbox.attr("checked")) {
                    var totalPrice = Number(totalPrice) + Number($("#ptotalprice_" + id).html());
                    $("#totalPrice").html("总价格:" + totalPrice.toFixed(2));
                } else {
                    var totalPrice = Number(totalPrice) - Number($("#ptotalprice_" + id).html());
                    $("#totalPrice").html("总价格:" + totalPrice.toFixed(2));
                }
            }
            </script>
            <%} else {
            %>
            <table width="100%" border="0" cellspacing="0" cellpadding="10" class="dingdan00">
                <tr>
                    <td width="0" align="left" style="padding:10px;">您的购物车还是空的</td>
                </tr>
            </table>
            <%                }%>
        </div>
        <script type="text/javascript" src="js/area.js"></script>
    </body>
</html>
