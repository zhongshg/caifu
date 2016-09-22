<%-- 
    Document   : public_do
    Created on : 2015-9-3, 0:24:38
    Author     : ASUS
--%>

<%@page import="wap.wx.menu.WxMenuUtils"%>
<%@page import="wap.wx.dao.SubscriberDAO"%>
<%@page import="job.tot.dao.DaoFactory"%>
<%@page import="job.tot.util.RequestUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../shop/inc/common.jsp"%>
<%    Map<String, String> wx = new HashMap<String, String>();
    wx.put("id", wxsid);
    wx = new WxsDAO().getById(wx);
    SubscriberDAO subscriberDAO = new SubscriberDAO();
    String act = RequestUtil.getString(request, "act");
    String Fnos = WxMenuUtils.format2.format(new java.util.Date()) + String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    if ("addshop".equals(act)) {
        int id = RequestUtil.getInt(request, "id");
        int count = RequestUtil.getInt(request, "count");
//            String name = RequestUtil.getString(request, "name");
//            String phone = RequestUtil.getString(request, "phone");
//            String weixin = RequestUtil.getString(request, "weixin");
//            String address = RequestUtil.getString(request, "address");
        String remark = RequestUtil.getString(request, "note");
        String propertys = RequestUtil.getString(request, "propertys");

        DataField df = DaoFactory.getProductDAO().get(id);

        //按照属性重新设置产品价
        String testPropertys = "";
        String[] perPropertys = propertys.split(" ");
        for (int i = 0; i < perPropertys.length; i++) {
            if ("".equals(perPropertys[i])) {
                continue;
            }
            testPropertys += "*" + perPropertys[i] + ",";
        }
        DataField psv = DaoFactory.getPsvDaoImplJDBC().getBySvids(testPropertys, id, 0, wx.get("id"));
        if (null != psv) {
            df.setField("Price", psv.getFieldValue("price"), 1);
            df.setField("distributionmoney", psv.getFieldValue("price"), 1);
            df.setField("Psku", psv.getFieldValue("psvcode"), 1);
        }

        Float FirstYJ = df.getFloat("distributionfirstdiscount") * count;
        Float SecondYJ = df.getFloat("distributionseconddiscount") * count;
        Float ThirdYJ = df.getFloat("distributionthirddiscount") * count;
        if (1 == df.getInt("distributiontype")) {
            FirstYJ = df.getFloat("distributionmoney") * df.getFloat("distributionfirstdiscount") * count;
            SecondYJ = df.getFloat("distributionmoney") * df.getFloat("distributionseconddiscount") * count;
            ThirdYJ = df.getFloat("distributionmoney") * df.getFloat("distributionthirddiscount") * count;
        }
//            //判断有无地址相同
//            DataField temp = DaoFactory.getBasketDAO().getBySuserIdUidAddress(wxsid, openid, name, phone, weixin, address, remark, 1);
//            if (null != temp) {
//                fNum = temp.getFieldValue("F_No");
//            } else {
//                DaoFactory.getAddressDAO().add(openid, name, address, "", "", "", phone, "", weixin, remark);
//            }
        //判断优惠价
        //判断有无已完成订单
        List<DataField> successendList = (List<DataField>) DaoFactory.getOrderDAO().getList(wxsid, openid, 5, -1);
        float Bas_Price = df.getFloat("Price");//单件商品原价
        float Sale_Price = Bas_Price;//单件商品优惠价
        float Per_Price = df.getFloat("Price") * count;//总计原价
        float Tot_Price = Per_Price;//总计优惠价
        if ("1".equals(wx.get("preferentialtype")) && 0 != successendList.size()) {
            Sale_Price = Bas_Price * Float.parseFloat(wx.get("discountratio")) / 100;
            Tot_Price = Per_Price * Float.parseFloat(wx.get("discountratio")) / 100;
        }
        boolean bl = DaoFactory.getBasketDAO().add(id, df.getFieldValue("Title"), openid, wxsid, request.getRemoteAddr(), Fnos, df.getFieldValue("ProCode"), count, 1, new Timestamp(System.currentTimeMillis()), Bas_Price, Sale_Price, Per_Price, Tot_Price, df.getString("ViewImg"), "", "", "", "", remark, FirstYJ, SecondYJ, ThirdYJ, propertys);
        if (bl) {
//                if ("buy".equals(act)) {
////                    response.sendRedirect(request.getContextPath() + "/shop2/shop.jsp?act=cart&wx=" + wxsid + "&openid=" + openid);
//                    response.sendRedirect(request.getContextPath() + "/shop3/myshop.jsp?wx=" + wxsid + "&openid=" + openid);
//                }
            out.println("0");
        } else {
            out.println("1");
        }
        //整理冗余数据1
        DaoFactory.getBasketDAO().delByUidp(openid, wxsid, 0);
    }

    if ("buy".equals(act)) {
        int id = RequestUtil.getInt(request, "id");
        int count = RequestUtil.getInt(request, "count");
//            String name = RequestUtil.getString(request, "name");
//            String phone = RequestUtil.getString(request, "phone");
//            String weixin = RequestUtil.getString(request, "weixin");
//            String address = RequestUtil.getString(request, "address");
        String remark = RequestUtil.getString(request, "note");
        String propertys = RequestUtil.getString(request, "propertys");

        DataField df = DaoFactory.getProductDAO().get(id);

        //按照属性重新设置产品价
        String testPropertys = "";
        String[] perPropertys = propertys.split(" ");
        for (int i = 0; i < perPropertys.length; i++) {
            if ("".equals(perPropertys[i])) {
                continue;
            }
            testPropertys += "*" + perPropertys[i] + ",";
        }
        DataField psv = DaoFactory.getPsvDaoImplJDBC().getBySvids(testPropertys, id, 0, wx.get("id"));
        if (null != psv) {
            df.setField("Price", psv.getFieldValue("price"), 1);
            df.setField("distributionmoney", psv.getFieldValue("price"), 1);
            df.setField("Psku", psv.getFieldValue("psvcode"), 1);
        }

        Float FirstYJ = df.getFloat("distributionfirstdiscount") * count;
        Float SecondYJ = df.getFloat("distributionseconddiscount") * count;
        Float ThirdYJ = df.getFloat("distributionthirddiscount") * count;
        if (1 == df.getInt("distributiontype")) {
            FirstYJ = df.getFloat("distributionmoney") * df.getFloat("distributionfirstdiscount") * count;
            SecondYJ = df.getFloat("distributionmoney") * df.getFloat("distributionseconddiscount") * count;
            ThirdYJ = df.getFloat("distributionmoney") * df.getFloat("distributionthirddiscount") * count;
        }
//            //判断有无地址相同
//            DataField temp = DaoFactory.getBasketDAO().getBySuserIdUidAddress(wxsid, openid, name, phone, weixin, address, remark, 1);
//            if (null != temp) {
//                fNum = temp.getFieldValue("F_No");
//            } else {
//                DaoFactory.getAddressDAO().add(openid, name, address, "", "", "", phone, "", weixin, remark);
//            }
        //判断优惠价
        //判断有无已完成订单
        List<DataField> successendList = (List<DataField>) DaoFactory.getOrderDAO().getList(wxsid, openid, 5, -1);
        float Bas_Price = df.getFloat("Price");//单件商品原价
        float Sale_Price = Bas_Price;//单件商品优惠价
        float Per_Price = df.getFloat("Price") * count;//总计原价
        float Tot_Price = Per_Price;//总计优惠价
        if ("1".equals(wx.get("preferentialtype")) && 0 != successendList.size()) {
            Sale_Price = Bas_Price * Float.parseFloat(wx.get("discountratio")) / 100;
            Tot_Price = Per_Price * Float.parseFloat(wx.get("discountratio")) / 100;
        }
        boolean bl = DaoFactory.getBasketDAO().add(id, df.getFieldValue("Title"), openid, wxsid, request.getRemoteAddr(), Fnos, df.getFieldValue("ProCode"), count, 0, new Timestamp(System.currentTimeMillis()), Bas_Price, Sale_Price, Per_Price, Tot_Price, df.getString("ViewImg"), "", "", "", "", remark, FirstYJ, SecondYJ, ThirdYJ, propertys);
        if (bl) {
            out.println("0");
        } else {
            out.println("1");
        }
    }
    if ("testaddorder".equals(act)) {
        //查询当天订单数量
        int count = DaoFactory.getOrderDAO().getTodayNum(wxsid);
        if (count < Integer.parseInt(wx.get("orderlimitperday")) || 0 == Integer.parseInt(wx.get("orderlimitperday"))) {
            out.print("0");
        } else {
            out.print("1");
        }
    }
    if ("addorder".equals(act)) {
    	Map<String, String> subscriber = subscriberDAO.getByOpenid(wxsid, openid);
    	String name = RequestUtil.getString(request, "name");
        String phone = RequestUtil.getString(request, "phone");
        String weixin = RequestUtil.getString(request, "weixin")==null?subscriber.get("nickname"):RequestUtil.getString(request, "weixin");
        String address = RequestUtil.getString(request, "address");

        String provience = RequestUtil.getString(request, "provience");
        String city = RequestUtil.getString(request, "city");
        String area = RequestUtil.getString(request, "area");
        //保存地址id,UserId,Name,Address,Sname,Ssname,Xname,Phone,TelePhone,ZipCode,MoRen,Xid
        if (!DaoFactory.getAddressDAO().exitsaddress(openid, wxsid)) {
            DaoFactory.getAddressDAO().add(openid, name, address, provience, city, area, phone, "", weixin, "", wxsid);
        } else {
            DaoFactory.getAddressDAO().modadd(openid, name, address, provience, city, area, phone, "", weixin, "", wxsid);
        }

        String carts = RequestUtil.getString(request, "carts");
        String[] cartArr = carts.split(",");
        boolean flag1 = false;
        boolean flag2 = false;
//        boolean flag3 = false;
        Timestamp times = new Timestamp(System.currentTimeMillis());
        String totalFnos = "";
        for (int i = 0; i < cartArr.length; i++) {
            DataField cart = DaoFactory.getBasketDAO().getId(Integer.parseInt(cartArr[i]));
            DaoFactory.getBasketDAO().modFno(Integer.parseInt(cartArr[i]), Fnos);
            //添加订单
            int count = DaoFactory.getOrderDAO().getDataCount("select count(*) counts from t_order where F_No='" + Fnos + "'");
            if (0 < count) {
                flag1 = DaoFactory.getOrderDAO().modadd(Fnos, times, cart.getFloat("Per_Price") + cart.getFloat("KuaiDi"), cart.getFloat("KuaiDi"), cart.getFloat("Per_Price"), cart.getFloat("Tot_Price"), cart.getFloat("Per_Price") - cart.getFloat("Tot_Price"), cart.getFloat("FirstYJ"), cart.getFloat("SecondYJ"), cart.getFloat("ThirdYJ"), 0, cart.getFieldValue("Remark"));
                totalFnos = Fnos;
            } else {
                flag1 = DaoFactory.getOrderDAO().add(Fnos, times, cart.getFloat("Per_Price") + cart.getFloat("KuaiDi"), cart.getFloat("KuaiDi"), cart.getFloat("Per_Price"), cart.getFloat("Tot_Price"), cart.getFloat("Per_Price") - cart.getFloat("Tot_Price"), openid, wxsid, request.getRemoteAddr(), 0, 1, 0, 0, 0, 1, name, address, phone, phone, "", "", "", 0, 0, "", "", 0, 0, cart.getFieldValue("Remark"), cart.getFloat("FirstYJ"), cart.getFloat("SecondYJ"), cart.getFloat("ThirdYJ"), name, phone, weixin, address, cart.getFieldValue("Remark"), 7, provience, city, area, WxMenuUtils.format.format(new java.util.Date()), WxMenuUtils.format.format(new java.util.Date()), 0);
                totalFnos = Fnos;
            }
            //更新购物车状态
            flag2 = DaoFactory.getBasketDAO().modWxUidId(openid, wxsid, cartArr[i], 2);
            //减少商品数量
            int signid = 0;
            String propertysStr = "";
            String propertys[] = cart.getFieldValue("propertys").trim().split(" ");
            for (String property : propertys) {
                propertysStr += "*" + property + ",";
            }

            List<DataField> psvList = (List<DataField>) DaoFactory.getPsvDaoImplJDBC().getList(cart.getInt("Pid"), signid, wx.get("id"));
            //判断是否该属性组
            boolean containflag = true;
            int j = 0;
            for (; j < psvList.size(); j++) {
                containflag = true;//初始化
                DataField psv = psvList.get(j);
                for (String property : propertys) {
                    if (containflag && -1 != psv.getFieldValue("svids").indexOf("*" + property + ",")) {
                        containflag = true;
                    } else {
                        containflag = false;
                    }
                }
                if (containflag) {//-1 != psv.getFieldValue("svids").indexOf(propertysStr)) {
                    break;
                }
            }
            if (containflag) {
                DaoFactory.getPsvDaoImplJDBC().modStock(propertysStr, cart.getInt("Pnum"), cart.getInt("Pid"), signid, wx.get("id"));
            }
            if (0 == j) {
                DaoFactory.getProductDAO().modIsRem(cart.getInt("Pid"), cart.getInt("Pnum"));
            }
            if (!(flag1 && flag2)) {//&& flag3
                out.print("1");
                break;
            }
        }
        if (flag1 && flag2) {//&& flag3
            if (!"".equals(totalFnos)) {
                DataField order = DaoFactory.getOrderDAO().get(totalFnos);
                out.print(Fnos);

                //判断有无优惠
                //判断有无已完成订单
                List<DataField> successendList = (List<DataField>) DaoFactory.getOrderDAO().getList(wxsid, openid, 5, -1);
                String Demons = "0,0";
                String str = "";
                if ("1".equals(wx.get("preferentialtype")) && 0 != successendList.size()) {//折扣
                    Demons = "1," + order.getFieldValue("CF_Price");
                    str = "；会员折扣后金额为：" + order.getFieldValue("SF_Price") + "元；共计优惠：" + order.getFieldValue("CF_Price") + "元";
                }
                if ("2".equals(wx.get("preferentialtype")) && 0 != successendList.size()) {//返现
                    float money = order.getFloat("TF_Price") * Float.parseFloat(wx.get("returnratio")) / 100;
                    Demons = "2," + money;
                }
                DaoFactory.getOrderDAO().modDemons(totalFnos, Demons);

              //  Map<String, String> subscriber = subscriberDAO.getByOpenid(wxsid, openid);
                Map<String, String> parentsubscriber = subscriberDAO.getById(wxsid, subscriber.get("parentopenid"));
                String content = "您的" + wx.get("subtitle1") + "【" + subscriber.get("nickname") + "】在" + WxMenuUtils.format.format(times) + "下单，订单号为：" + order.getFieldValue("F_No") + "；订单金额为：" + order.getFieldValue("TF_Price") + "元" + str + "；您将获得的提成为：" + order.getFieldValue("FirstYJ") + "元。";
                //判断有无上级
                if (null != parentsubscriber.get("openid")) {
                    WxMenuUtils.sendCustomService(parentsubscriber.get("openid"), content, wx);
                    subscriberDAO.updateIsdownorder(Integer.parseInt(parentsubscriber.get("id")), wx.get("id"));
                } else {
                    //给商家用户发送通知
                    if (!"0".equals(wx.get("adminsubscriber"))) {
                        WxMenuUtils.sendCustomService(wx.get("adminsubscriber"), content, wx);
                    }
                }

                //往上再取两级，三级都通知
                if (!"0".equals(parentsubscriber.get("parentopenid"))) {
                    Map<String, String> secondparentsubscriber = subscriberDAO.getById(wxsid, parentsubscriber.get("parentopenid"));
                    content = "您的" + wx.get("subtitle2") + "【" + subscriber.get("nickname") + "】在" + WxMenuUtils.format.format(times) + "下单，订单号为：" + order.getFieldValue("F_No") + "；订单金额为：" + order.getFieldValue("TF_Price") + "元" + str + "；您将获得的提成为：" + order.getFieldValue("SecondYJ") + "元。";
                    if (null != secondparentsubscriber.get("id")) {
                        WxMenuUtils.sendCustomService(secondparentsubscriber.get("openid"), content, wx);
                        subscriberDAO.updateIsdownorder(Integer.parseInt(secondparentsubscriber.get("id")), wx.get("id"));
                        if (!"0".equals(secondparentsubscriber.get("parentopenid"))) {
                            Map<String, String> thirdparentsubscriber = subscriberDAO.getById(wxsid, secondparentsubscriber.get("parentopenid"));
                            content = "您的" + wx.get("subtitle3") + "【" + subscriber.get("nickname") + "】在" + WxMenuUtils.format.format(times) + "下单，订单号为：" + order.getFieldValue("F_No") + "；订单金额为：" + order.getFieldValue("TF_Price") + "元" + str + "；您将获得的提成为：" + order.getFieldValue("ThirdYJ") + "元。";
                            if (null != thirdparentsubscriber.get("id")) {
                                WxMenuUtils.sendCustomService(thirdparentsubscriber.get("openid"), content, wx);
                                subscriberDAO.updateIsdownorder(Integer.parseInt(thirdparentsubscriber.get("id")), wx.get("id"));
                            }
                        }
                    }
                }

                subscriberDAO.updateIsorder(Integer.parseInt(subscriber.get("id")), wx.get("id"));
            } else {
                out.print("1");
            }
        }
        //整理冗余数据2
        DaoFactory.getBasketDAO().delByUidp(openid, wxsid, 0);
        act = null;
    }

    if ("confirmreceipt".equals(act)) {
        String F_No = RequestUtil.getString(request, "F_No");
        if (DaoFactory.getOrderDAO().modSts(F_No, 3) && DaoFactory.getOrderDAO().modConfirmtimes(F_No, WxMenuUtils.format.format(new java.util.Date()))) {
            out.print("0");
        } else {
            out.print("1");
        }
    }
%>
