/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.service;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import job.tot.bean.DataField;
import job.tot.dao.DaoFactory;
import job.tot.util.RequestUtil;
import wap.wx.dao.ApiDAO;
import wap.wx.dao.AreaproxylogsDAO;
import wap.wx.dao.SubscriberDAO;
import wap.wx.dao.WxsDAO;
import wap.wx.menu.WxMenuUtils;

/**
 *
 * @author Administrator
 */
public class PublicService {

    public static String modOrderAuto(List<Map<String, String>> wxsList, SubscriberDAO subscriberDAO, AreaproxylogsDAO areaproxylogsDAO) {
        //附加 获取ip
        Map<String, String> api = new HashMap<String, String>();
        api.put("apikey", "ip");
        api = new ApiDAO().get(api.get("apikey"), 0);

        wxsList = new WxsDAO().getList();
        boolean sendtoreceive = false;
        boolean receivetowithdraw = false;
        float areaproxymoney = 0;
        String limittime = "";
        for (Map<String, String> wx : wxsList) {
            if (null != wx.get("sendtoreceivelimit") && !"0".equals(wx.get("sendtoreceivelimit"))) {
                limittime = String.valueOf(Integer.parseInt(wx.get("sendtoreceivelimit")) * 24 * 60 * 60 - 1);//秒 防止毫秒级误差，提前1秒执行
                //更新所有超时已发货订单
                sendtoreceive = DaoFactory.getOrderDAO().modStsAuto(wx.get("id"), 2, 3, "sendtimes", limittime, "confirmtimes", "'" + WxMenuUtils.format.format(new Date()) + "'");
            }
            if (null != wx.get("receivetowithdrawlimit") && !"0".equals(wx.get("receivetowithdrawlimit"))) {
                limittime = String.valueOf(Integer.parseInt(wx.get("receivetowithdrawlimit")) * 24 * 60 * 60 - 1);//秒 防止毫秒级误差，提前1秒执行
                //更新所有超时已收货订单
                List<DataField> orderList = DaoFactory.getOrderDAO().getAutoList(wx.get("id"), 3, "confirmtimes", limittime);
                for (DataField order : orderList) {
                    //取出收货地址 省 市
                    //区域代理核算
                    String content = "";
                    //取出省级代理 优先
                    List<Map<String, String>> provinceareaproxysubscriberList = subscriberDAO.getByareaproxyList(Integer.parseInt(wx.get("id")), "areaproxyprovince", order.getFieldValue("provience"));
                    for (Map<String, String> provinceareaproxysubscriber : provinceareaproxysubscriberList) {
                        //与市级代理不同享
                        if ("0".equals(provinceareaproxysubscriber.get("areaproxycity"))) {
                            areaproxymoney = order.getFloat("F_Price") * Float.parseFloat(provinceareaproxysubscriber.get("areaproxydiscount")) / 100;
                            subscriberDAO.updateAreaproxymoney(provinceareaproxysubscriber.get("openid"), wx.get("id"), areaproxymoney);

                            //添加佣金记录 id,openid,areaproxymoney,times,remark,wxsid
                            Map<String, String> areaproxylogs = new HashMap<String, String>();
                            areaproxylogs.put("openid", provinceareaproxysubscriber.get("openid"));
                            areaproxylogs.put("areaproxymoney", String.valueOf(areaproxymoney));
                            areaproxylogs.put("times", WxMenuUtils.format.format(new Date()));
                            areaproxylogs.put("remark", order.getFieldValue("F_No"));
                            areaproxylogsDAO.add(areaproxylogs, wx.get("id"));

                            //下发佣金获取通知
                            content = "恭喜您收到省级区域代理奖励，金额：" + WxMenuUtils.decimalFormat.format(order.getFloat("F_Price") * Float.parseFloat(provinceareaproxysubscriber.get("areaproxydiscount")) / 100) + "元。";
                            WxMenuUtils.sendCustomService(provinceareaproxysubscriber.get("openid"), content, wx);
                        }
                    }
//                        取出市级代理
                    List<Map<String, String>> cityareaproxysubscriberList = subscriberDAO.getByareaproxyList(Integer.parseInt(wx.get("id")), "areaproxycity", order.getFieldValue("city"));
                    for (Map<String, String> cityareaproxysubscriber : cityareaproxysubscriberList) {
                        areaproxymoney = order.getFloat("F_Price") * Float.parseFloat(cityareaproxysubscriber.get("areaproxydiscount")) / 100;
                        subscriberDAO.updateAreaproxymoney(cityareaproxysubscriber.get("openid"), wx.get("id"), areaproxymoney);

                        //添加佣金记录 id,openid,areaproxymoney,times,remark,wxsid
                        Map<String, String> areaproxylogs = new HashMap<String, String>();
                        areaproxylogs.put("openid", cityareaproxysubscriber.get("openid"));
                        areaproxylogs.put("areaproxymoney", String.valueOf(areaproxymoney));
                        areaproxylogs.put("times", WxMenuUtils.format.format(new Date()));
                        areaproxylogs.put("remark", order.getFieldValue("F_No"));
                        areaproxylogsDAO.add(areaproxylogs, wx.get("id"));

                        //下发佣金获取通知
                        content = "恭喜您收到市级区域代理奖励，金额：" + WxMenuUtils.decimalFormat.format(order.getFloat("F_Price") * Float.parseFloat(cityareaproxysubscriber.get("areaproxydiscount")) / 100) + "元。";
                        WxMenuUtils.sendCustomService(cityareaproxysubscriber.get("openid"), content, wx);
                    }

                    //返现处理
                    String[] Demonss = order.getFieldValue("Demons").split(",");
                    if (1 < Demonss.length) {
                        if ("2".equals(Demonss[0])) {
                            wx.put("wishing", "您的订单交易已完成，恭喜您收到订单返现佣金！");
                            //这里直接计入个人返现佣金，不再派发红包
                            subscriberDAO.updateCallbackmoney(order.getFieldValue("UserId"), wx.get("id"), Float.parseFloat(Demonss[1]));
//                            new WxPayUtils().sendredpack(null, wx, order.getFieldValue("UserId"), Demonss[1], api.get("ip"));
                        }
                    }
                    //处理订单状态

                    Map<String, String> subscriber = subscriberDAO.getByOpenid(wx.get("id"), order.getFieldValue("UserId"));
                    Map<String, String> parentsubscriber = subscriberDAO.getById(wx.get("id"), subscriber.get("parentopenid"));
                    if (null != parentsubscriber.get("openid")) {
                        subscriberDAO.updateIsyj(Integer.parseInt(parentsubscriber.get("id")), wx.get("id"));
                    }
                    if (!"0".equals(parentsubscriber.get("parentopenid"))) {
                        Map<String, String> secondparentsubscriber = subscriberDAO.getById(wx.get("id"), parentsubscriber.get("parentopenid"));
                        if (null != secondparentsubscriber.get("id")) {
                            subscriberDAO.updateIsyj(Integer.parseInt(secondparentsubscriber.get("id")), wx.get("id"));
                            if (!"0".equals(secondparentsubscriber.get("parentopenid"))) {
                                Map<String, String> thirdparentsubscriber = subscriberDAO.getById(wx.get("id"), secondparentsubscriber.get("parentopenid"));
                                if (null != thirdparentsubscriber.get("id")) {
                                    subscriberDAO.updateIsyj(Integer.parseInt(thirdparentsubscriber.get("id")), wx.get("id"));
                                }
                            }
                        }
                    }
                }

                receivetowithdraw = DaoFactory.getOrderDAO().modStsAuto(wx.get("id"), 3, 5, "confirmtimes", limittime, "State", "2");
            }

        }

        return "收货 " + sendtoreceive + " 完成 " + receivetowithdraw + " 时间 " + new Date();
    }

    public String initPsv(int signid, DataField product, Map<String, String> wx) {
        String totalstr = "";
        List<DataField> psvList = (List<DataField>) DaoFactory.getPsvDaoImplJDBC().getList(product.getInt("pid"), signid, wx.get("id"));
        for (DataField psv : psvList) {
            totalstr += psv.getFieldValue("svids") + " ";
        }
        return totalstr;
    }

    public void setPsv(int signid, int id, Map<String, String> wx, HttpServletRequest request) {
        //清空属性组
        DaoFactory.getPsvDaoImplJDBC().delList(id, signid, wx.get("id"));
        //添加属性组
        String totalstr = RequestUtil.getString(request, "totalstr");
        String totalstrs[] = totalstr.split(" ");
        for (int i = 0; i < totalstrs.length; i++) {
            String pav = totalstrs[i].trim();
            if ("".equals(pav)) {
                continue;
            }
            String psvs[] = pav.split(",");
            //属性个数不确定，因此从后往前区
            String psvcode = psvs[psvs.length - 1].replace("*", "");
            String stock = psvs[psvs.length - 2].replace("*", "");
            String price = psvs[psvs.length - 3].replace("*", "");
            DaoFactory.getPsvDaoImplJDBC().add(id, totalstrs[i], Float.parseFloat(price), Integer.parseInt(stock), psvcode, signid, wx.get("id"));
        }
    }

    public String getPsvDiv(String property, int signid, int pid, Map<String, String> wx) {
        int testsignid = 0;
        Map<DataField, List<DataField>> map = new LinkedHashMap<DataField, List<DataField>>();
        List<DataField> propertyDFList = null;
        DataField propertyDF = null;
        DataField testDF = new DataField();
        testDF.setField("id", "0", 1);
        StringBuilder param = new StringBuilder("<table id=\"tb\" width=\"100%\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" bgcolor=\"#A3B2CC\" >");
        String propertys[] = property.split(" ");
        String sign = "";
        String th = "";
        String td = "";
        th += "<tr>";
        for (int i = 0; i < propertys.length; i++) {
            if ("".equals(propertys[i])) {
                continue;
            }
            String propertyArr[] = propertys[i].split(",");
            if (0 == propertyArr.length) {
                continue;
            }
            testsignid = Integer.parseInt(propertyArr[0]);
            if (testsignid != signid) {
                continue;
            }
            propertyDF = DaoFactory.getPropertysDaoImplJDBC().get(propertyArr[1].split("-")[0]);
            if (null == propertyDF) {
                continue;
            }

            String propertyname = propertyDF.getFieldValue("svname");//属性名称
            if (!sign.equals(propertyname)) {//过滤相同属性名称
                propertyDFList = new LinkedList<DataField>();
                sign = propertyname;
                th += "<th bgcolor=\"#F2F2F2\">" + propertyname + "</th>";
            }

            DataField propertyvalueDF = DaoFactory.getPropertysDaoImplJDBC().get(propertyArr[1]);//属性值
            if (null == propertyvalueDF) {
                continue;
            }
            propertyDFList.add(propertyvalueDF);
            if (testDF.getInt("id") == propertyDF.getInt("id")) {
                map.remove(testDF);
            }
            map.put(propertyDF, propertyDFList);
            testDF = propertyDF;
        }

//        System.out.println("map " + map.size());
        Set<DataField> set = map.keySet();
//        for(DataField testmap:set){
//            System.out.println("key "+testmap.getFieldValue("svname"));
//            List<DataField> tempMapList=map.get(testmap);
//            for(DataField tempMap:tempMapList){
//                System.out.println("value "+tempMap.getFieldValue("svname"));
//            }
//        }

        //附加输入框
        th += "<th bgcolor=\"#F2F2F2\">价格</th>";
        th += "<th bgcolor=\"#F2F2F2\">库存</th>";
        th += "<th bgcolor=\"#F2F2F2\">编码</th>";
        th += "</tr>";
        td += "";

        //第一次循环
        String rowtd1 = "";
        for (DataField propertyDF1 : set) {
            for (int i = 0; i < map.get(propertyDF1).size(); i++) {
                DataField propertyvalueDF1 = map.get(propertyDF1).get(i);
                rowtd1 = "<td bgcolor=\"#F2F2F2\">" + propertyvalueDF1.getFieldValue("svname") + "<input type=\"hidden\" name=\"propertyvalueDF1\" value=\"" + propertyvalueDF1.getFieldValue("svid") + "\"/></td>";
                if (1 == map.size()) {
                    //取出sku值
                    DataField psv = DaoFactory.getPsvDaoImplJDBC().getBySvids("*" + propertyvalueDF1.getFieldValue("svid"), pid, testsignid, wx.get("id"));
                    if (null == psv) {
                        psv = new DataField();
                        psv.setField("price", "0", 1);
                        psv.setField("stock", "0", 1);
                        psv.setField("psvcode", "", 1);
                    }
                    td += "<tr>" + rowtd1 + "<td bgcolor=\"#F2F2F2\"><input type=\"text\" name=\"price\" value=\"" + psv.getFieldValue("price") + "\"/></td><td bgcolor=\"#F2F2F2\"><input type=\"text\" name=\"stock\" value=\"" + psv.getFieldValue("stock") + "\"/></td><td bgcolor=\"#F2F2F2\"><input type=\"text\" name=\"psvcode\" value=\"" + psv.getFieldValue("psvcode") + "\"/></td></tr>";
                }
                //第二次循环
                String rowtd2 = "";
                for (DataField propertyDF2 : set) {
                    if (propertyDF2.getInt("id") == propertyDF1.getInt("id")) {
                        continue;
                    }
                    for (int j = 0; j < map.get(propertyDF2).size(); j++) {
                        DataField propertyvalueDF2 = map.get(propertyDF2).get(j);
                        rowtd2 = "<td bgcolor=\"#F2F2F2\">" + propertyvalueDF2.getFieldValue("svname") + "<input type=\"hidden\" name=\"propertyvalueDF2\" value=\"" + propertyvalueDF2.getFieldValue("svid") + "\"/></td>";
                        if (2 == map.size()) {
                            //取出sku值
                            DataField psv = DaoFactory.getPsvDaoImplJDBC().getBySvids("*" + propertyvalueDF1.getFieldValue("svid") + ",*" + propertyvalueDF2.getFieldValue("svid"), pid, testsignid, wx.get("id"));
                            if (null == psv) {
                                psv = new DataField();
                                psv.setField("price", "0", 1);
                                psv.setField("stock", "0", 1);
                                psv.setField("psvcode", "", 1);
                            }
                            td += "<tr>" + rowtd1 + rowtd2 + "<td bgcolor=\"#F2F2F2\"><input type=\"text\" name=\"price\" value=\"" + psv.getFieldValue("price") + "\"/></td><td bgcolor=\"#F2F2F2\"><input type=\"text\" name=\"stock\" value=\"" + psv.getFieldValue("stock") + "\"/></td><td bgcolor=\"#F2F2F2\"><input type=\"text\" name=\"psvcode\" value=\"" + psv.getFieldValue("psvcode") + "\"/></td></tr>";
                        }
                        //第三次循环
                        String rowtd3 = "";
                        for (DataField propertyDF3 : set) {
                            if (propertyDF3.getInt("id") == propertyDF2.getInt("id") || propertyDF3.getInt("id") == propertyDF1.getInt("id")) {
                                continue;
                            }

                            for (int k = 0; k < map.get(propertyDF3).size(); k++) {
                                DataField propertyvalueDF3 = map.get(propertyDF3).get(k);
                                rowtd3 = "<td bgcolor=\"#F2F2F2\">" + propertyvalueDF3.getFieldValue("svname") + "<input type=\"hidden\" name=\"propertyvalueDF3\" value=\"" + propertyvalueDF3.getFieldValue("svid") + "\"/></td>";
                                if (3 == map.size()) {
                                    //取出sku值
                                    DataField psv = DaoFactory.getPsvDaoImplJDBC().getBySvids("*" + propertyvalueDF1.getFieldValue("svid") + ",*" + propertyvalueDF2.getFieldValue("svid") + ",*" + propertyvalueDF3.getFieldValue("svid"), pid, testsignid, wx.get("id"));
                                    if (null == psv) {
                                        psv = new DataField();
                                        psv.setField("price", "0", 1);
                                        psv.setField("stock", "0", 1);
                                        psv.setField("psvcode", "", 1);
                                    }
                                    td += "<tr>" + rowtd1 + rowtd2 + rowtd3 + "<td bgcolor=\"#F2F2F2\"><input type=\"text\" name=\"price\" value=\"" + psv.getFieldValue("price") + "\"/></td><td bgcolor=\"#F2F2F2\"><input type=\"text\" name=\"stock\" value=\"" + psv.getFieldValue("stock") + "\"/></td><td bgcolor=\"#F2F2F2\"><input type=\"text\" name=\"psvcode\" value=\"" + psv.getFieldValue("psvcode") + "\"/></td></tr>";
                                }

                                //第四次循环
                                String rowtd4 = "";
                                for (DataField propertyDF4 : set) {
                                    if (propertyDF4.getInt("id") == propertyDF3.getInt("id") || propertyDF4.getInt("id") == propertyDF2.getInt("id") || propertyDF4.getInt("id") == propertyDF1.getInt("id")) {
                                        continue;
                                    }
                                    for (int l = 0; l < map.get(propertyDF4).size(); l++) {
                                        DataField propertyvalueDF4 = map.get(propertyDF4).get(l);
                                        rowtd4 = "<td bgcolor=\"#F2F2F2\">" + propertyvalueDF4.getFieldValue("svname") + "<input type=\"hidden\" name=\"propertyvalueDF4\" value=\"" + propertyvalueDF4.getFieldValue("svid") + "\"/></td>";
                                        if (4 == map.size()) {
                                            //取出sku值
                                            DataField psv = DaoFactory.getPsvDaoImplJDBC().getBySvids("*" + propertyvalueDF1.getFieldValue("svid") + ",*" + propertyvalueDF2.getFieldValue("svid") + ",*" + propertyvalueDF3.getFieldValue("svid") + ",*" + propertyvalueDF4.getFieldValue("svid"), pid, testsignid, wx.get("id"));
                                            if (null == psv) {
                                                psv = new DataField();
                                                psv.setField("price", "0", 1);
                                                psv.setField("stock", "0", 1);
                                                psv.setField("psvcode", "", 1);
                                            }
                                            td += "<tr>" + rowtd1 + rowtd2 + rowtd3 + rowtd4 + "<td bgcolor=\"#F2F2F2\"><input type=\"text\" name=\"price\" value=\"" + psv.getFieldValue("price") + "\"/></td><td bgcolor=\"#F2F2F2\"><input type=\"text\" name=\"stock\" value=\"" + psv.getFieldValue("stock") + "\"/></td><td bgcolor=\"#F2F2F2\"><input type=\"text\" name=\"psvcode\" value=\"" + psv.getFieldValue("psvcode") + "\"/></td></tr>";
                                        }
                                        //第五次循环
                                        String rowtd5 = "";
                                        for (DataField propertyDF5 : set) {
                                            if (propertyDF5.getInt("id") == propertyDF4.getInt("id") || propertyDF5.getInt("id") == propertyDF3.getInt("id") || propertyDF5.getInt("id") == propertyDF2.getInt("id") || propertyDF5.getInt("id") == propertyDF1.getInt("id")) {
                                                continue;
                                            }

                                            for (int m = 0; m < map.get(propertyDF5).size(); m++) {
                                                DataField propertyvalueDF5 = map.get(propertyDF5).get(m);
                                                rowtd5 = "<td bgcolor=\"#F2F2F2\">" + propertyvalueDF5.getFieldValue("svname") + "<input type=\"hidden\" name=\"propertyvalueDF5\" value=\"" + propertyvalueDF5.getFieldValue("svid") + "\"/></td>";
                                                if (5 == map.size()) {
                                                    //取出sku值
                                                    DataField psv = DaoFactory.getPsvDaoImplJDBC().getBySvids("*" + propertyvalueDF1.getFieldValue("svid") + ",*" + propertyvalueDF2.getFieldValue("svid") + ",*" + propertyvalueDF3.getFieldValue("svid") + ",*" + propertyvalueDF4.getFieldValue("svid") + ",*" + propertyvalueDF5.getFieldValue("svid"), pid, testsignid, wx.get("id"));
                                                    if (null == psv) {
                                                        psv = new DataField();
                                                        psv.setField("price", "0", 1);
                                                        psv.setField("stock", "0", 1);
                                                        psv.setField("psvcode", "", 1);
                                                    }
                                                    td += "<tr>" + rowtd1 + rowtd2 + rowtd3 + rowtd4 + rowtd5 + "<td bgcolor=\"#F2F2F2\"><input type=\"text\" name=\"price\" value=\"" + psv.getFieldValue("price") + "\"/></td><td bgcolor=\"#F2F2F2\"><input type=\"text\" name=\"stock\" value=\"" + psv.getFieldValue("stock") + "\"/></td><td bgcolor=\"#F2F2F2\"><input type=\"text\" name=\"psvcode\" value=\"" + psv.getFieldValue("psvcode") + "\"/></td></tr>";
                                                }
                                                //第六次循环 用于拓展
//                                                String rowtd6 = "";
//                                                for (DataField propertyDF6 : set) {
//
//                                                    if (propertyDF6.getInt("id") == propertyDF5.getInt("id") || propertyDF6.getInt("id") == propertyDF4.getInt("id") || propertyDF6.getInt("id") == propertyDF3.getInt("id") || propertyDF6.getInt("id") == propertyDF2.getInt("id") || propertyDF6.getInt("id") == propertyDF1.getInt("id")) {
//                                                        continue;
//                                                    }
//                                                    for (int n = 0; n < map.get(propertyDF6).size(); n++) {
//                                                        DataField propertyvalueDF6 = map.get(propertyDF6).get(n);
//                                                        rowtd6 = "<td bgcolor=\"#F2F2F2\">" + propertyvalueDF6.getFieldValue("svname") + "<input type=\"hidden\" name=\"propertyvalueDF6\" value=\"" + propertyvalueDF6.getFieldValue("svid") + "\"/></td>";
//                                                        if (6 == map.size()) {
//                                                            //取出sku值
//                                                            DataField psv = DaoFactory.getPsvDaoImplJDBC().getBySvids("*" + propertyvalueDF1.getFieldValue("svid") + ",*" + propertyvalueDF2.getFieldValue("svid") + ",*" + propertyvalueDF3.getFieldValue("svid") + ",*" + propertyvalueDF4.getFieldValue("svid") + ",*" + propertyvalueDF5.getFieldValue("svid") + ",*" + propertyvalueDF6.getFieldValue("svid"), pid, testsignid, wx.get("id"));
//                                                            if (null == psv) {
//                                                                psv = new DataField();
//                                                                psv.setField("price", "0", 1);
//                                                                psv.setField("stock", "0", 1);
//                                                                psv.setField("psvcode", "", 1);
//                                                            }
//                                                            td += "<tr>" + rowtd1 + rowtd2 + rowtd3 + rowtd4 + rowtd5 + rowtd6 + "<td bgcolor=\"#F2F2F2\"><input type=\"text\" name=\"price\" value=\"" + psv.getFieldValue("price") + "\"/></td><td bgcolor=\"#F2F2F2\"><input type=\"text\" name=\"stock\" value=\"" + psv.getFieldValue("stock") + "\"/></td><td bgcolor=\"#F2F2F2\"><input type=\"text\" name=\"psvcode\" value=\"" + psv.getFieldValue("psvcode") + "\"/></td></tr>";
//                                                        }
//                                                        //继续拓展区
//                                                    }
//                                                    if (propertyDF6.getInt("id") != propertyDF5.getInt("id") && propertyDF6.getInt("id") != propertyDF4.getInt("id") && propertyDF6.getInt("id") != propertyDF3.getInt("id") && propertyDF6.getInt("id") != propertyDF2.getInt("id") && propertyDF6.getInt("id") != propertyDF1.getInt("id")) {
//                                                        break;
//                                                    }
//                                                }
                                            }
                                            if (propertyDF5.getInt("id") != propertyDF4.getInt("id") && propertyDF5.getInt("id") != propertyDF3.getInt("id") && propertyDF5.getInt("id") != propertyDF2.getInt("id") && propertyDF5.getInt("id") != propertyDF1.getInt("id")) {
                                                break;
                                            }
                                        }
                                    }
                                    if (propertyDF4.getInt("id") != propertyDF3.getInt("id") && propertyDF4.getInt("id") != propertyDF2.getInt("id") && propertyDF4.getInt("id") != propertyDF1.getInt("id")) {
                                        break;
                                    }
                                }
                            }
                            if (propertyDF3.getInt("id") != propertyDF2.getInt("id") && propertyDF3.getInt("id") != propertyDF1.getInt("id")) {
                                break;
                            }
                        }
                    }
                    if (propertyDF2.getInt("id") != propertyDF1.getInt("id")) {
                        break;
                    }
                }
            }
            break;
        }
        param.append(th);
        param.append(td);
        param.append("</table>");
        //附加批量操作
        param.append(
                "<input type=\"button\" value=\"批量价格设置\" onclick=\"javascript:psvset('price')\"/>"
                + "                        <input type=\"button\" value=\"批量库存设置\" onclick=\"javascript:psvset('stock')\"/>" //                + "                        <input type=\"button\" value=\"批量编码设置\" onclick=\"javascript:psvset('psvcode')\"/>"
                );
        param.append("<script>"
                + "                            function psvset(name){"
                + "                                var warn=\"\";"
                + "                                if('price'==name)warn=\"请输入价格：\";"
                + "                                if('stock'==name)warn=\"请输入库存：\";"
                + "                                if('psvcode'==name)warn=\"请输入编码：\";"
                + "                                warn=window.prompt(warn,\"0\");"
                + "                                var eles=document.getElementsByName(name);"
                + "                                for(var i=0;i<eles.length;i++){"
                + "                                    eles[i].value=warn;"
                + "                                }"
                + "                            }"
                + "                            </script>");
        return param.toString();
    }
}
