/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.service;

import com.alibaba.fastjson.JSONObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import job.tot.bean.DataField;
import job.tot.dao.DaoFactory;
import wap.wx.dao.SubscriberDAO;
import wap.wx.dao.WxsDAO;
import wap.wx.menu.WxMenuUtils;
import wap.wx.util.DbConn;
import wap.wx.util.PageUtil;

/**
 *
 * @author Administrator
 */
public class SubscriberService {

    /**
     * 该方法仅用于测试，否则造成佣金波动，已提现佣金恢复
     *
     * @param subscriber
     * @param wxsid
     * @param request
     */
    public void delete(Map<String, String> subscriber, int wxsid, HttpServletRequest request) {
        Connection conn = DbConn.getConn();
        try {
            conn.setAutoCommit(false);
            if (!"".equals(subscriber.get("qrcode")) && null != subscriber.get("qrcode")) {
                java.io.File oldFile = new java.io.File(request.getServletContext()
                        .getRealPath(subscriber.get("qrcode")));
                oldFile.delete();
            }
            SubscriberDAO subscriberDAO = new SubscriberDAO();
            //删除用户订单（防止关注其他上家时商家免费获得佣金）
            DaoFactory.getOrderDAO().delete(String.valueOf(wxsid), subscriber.get("openid"), conn);
            //删除用户提现（防止因丢失下家提成造成提成后负值）
            DaoFactory.getFundsDao().delete(String.valueOf(wxsid), subscriber.get("openid"), conn);
            //删除上三家提现记录（防止上三家因丢失提成造成提成后负值）
            Map<String, String> firstsubscriber = subscriberDAO.getById(String.valueOf(wxsid), subscriber.get("parentopenid"));
            if (null != firstsubscriber.get("id")) {
                DaoFactory.getFundsDao().delete(String.valueOf(wxsid), firstsubscriber.get("openid"), conn);
                Map<String, String> secondsubscriber = subscriberDAO.getById(String.valueOf(wxsid), firstsubscriber.get("parentopenid"));
                if (null != secondsubscriber.get("id")) {
                    DaoFactory.getFundsDao().delete(String.valueOf(wxsid), secondsubscriber.get("openid"), conn);
                    Map<String, String> thirdsubscriber = subscriberDAO.getById(String.valueOf(wxsid), secondsubscriber.get("parentopenid"));
                    if (null != thirdsubscriber.get("id")) {
                        DaoFactory.getFundsDao().delete(String.valueOf(wxsid), thirdsubscriber.get("openid"), conn);
                    }
                }
            }
            //删除用户
            subscriberDAO.delete(subscriber, wxsid, conn);
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteqrcode(String wxsid, HttpServletRequest request) {
        Connection conn = DbConn.getConn();
        try {
            conn.setAutoCommit(false);
            SubscriberDAO subscriberDAO = new SubscriberDAO();
            List<Map<String, String>> subscriberList = subscriberDAO.getList(Integer.parseInt(wxsid));
            for (Map<String, String> subscriber : subscriberList) {
                if (!"".equals(subscriber.get("qrcode")) && null != subscriber.get("qrcode")) {
                    java.io.File oldFile = new java.io.File(request.getServletContext()
                            .getRealPath(subscriber.get("qrcode")));
                    oldFile.delete();
                }
            }
            new SubscriberDAO().deleteqrcode(wxsid, "", "", conn);
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, String> addSubscriber(Map<String, String> wx, String openid) {
        return this.addSubscriber(wx, openid, "0");
    }

    public Map<String, String> addSubscriber(Map<String, String> wx, String openid, String parentopenid) {
        SubscriberDAO subscriberDAO = new SubscriberDAO();
        Map<String, String> subscriber = new HashMap<String, String>();
        subscriber.put("openid", openid);
        subscriber.put("percent", "0");
        subscriber.put("remark", "");
        subscriber.put("times", WxMenuUtils.format.format(new Date()));
        //parentopenid,salemoney,commission,isvip,vipid
        subscriber.put("parentopenid", parentopenid);
        subscriber.put("salemoney", "0");
        subscriber.put("commission", "0");
        subscriber.put("isvip", "0");

        //vipid 作为相对id
//                        int tempcount = subscriberDAO.getCount(wxsid);
        int maxvipid = subscriberDAO.getMaxVipid(Integer.parseInt(wx.get("id")));
//                        int vipid = subscriberDAO.getMaxid();
        subscriber.put("vipid", String.valueOf(maxvipid + 1));

        subscriber.put("qrcode", "");
        subscriber.put("qrcodemediaid", "");
        subscriber.put("qrcodemediaidtimes", String.valueOf(System.currentTimeMillis() / 1000 - 9 * 24 * 60 * 60));

        //                        username,areaproxymoney,areaproxyprovince,areaproxycity,areaproxydiscount
        subscriber.put("areaproxymoney", "0");
        subscriber.put("areaproxyprovince", "0");
        subscriber.put("areaproxycity", "0");
        subscriber.put("areaproxydiscount", "0");
        subscriber.put("callbackmoney", "0");
        subscriber.put("isorder", "0");
        subscriber.put("isdownorder", "0");
        subscriber.put("isdownsubscriber", "0");
        subscriber.put("isyj", "0");

        JSONObject object = WxMenuUtils.getUserInfo(openid, wx);
        object = null != object ? object : new JSONObject();
        subscriber.put("nickname", object.getString("nickname"));
        subscriber.put("username", "");
        subscriber.put("sex", null != object.getString("sex") ? object.getString("sex") : "0");
        subscriber.put("language", object.getString("language"));
        subscriber.put("city", object.getString("city"));
        subscriber.put("province", object.getString("province"));
        subscriber.put("country", object.getString("country"));
        subscriber.put("headimgurl", object.getString("headimgurl"));
        subscriber.put("unionid", object.getString("unionid"));

        Map<String, String> testsubscriber = subscriberDAO.getByOpenid(String.valueOf(wx.get("id")), openid);
        if (null != testsubscriber.get("id")) {
            subscriberDAO.updateInfo(openid, Integer.parseInt(wx.get("id")), subscriber);
        } else {
            try {
                subscriberDAO.add(subscriber, Integer.parseInt(wx.get("id")));
            } catch (Exception e) {
                System.out.println("用户关注nickname特殊字符,数据库存储error,存储空！");
                subscriber.put("nickname", "");
                subscriber.put("username", "");
                subscriberDAO.add(subscriber, Integer.parseInt(wx.get("id")));
            }
        }

        subscriberDAO.updateIsdownsubscriber(Integer.parseInt(parentopenid), wx.get("id"));
        return subscriber;
    }

    public float totalcanmoney(Connection conn, SubscriberDAO subscriberDAO, Map<String, String> subscriber, Map<String, String> wx) {
        //计算可提现金额
        float totalorderalendYJ = 0;//已完成订单总佣金
        List<Map<String, String>> firstsubscriberList = subscriberDAO.getByParentopenidList(wx.get("id"), subscriber.get("id"), conn);
//        firstsubscribercount = firstsubscriberList.size();
        for (Map<String, String> firstsubscriber : firstsubscriberList) {
            //计算单数、金额
            List<DataField> firstorderList = (List<DataField>) DaoFactory.getOrderDAO().getList(wx.get("id"), firstsubscriber.get("openid"), -1, conn);
            for (DataField order : firstorderList) {
                switch (order.getInt("Sts")) {
//                    case 0:
//                        totalordernobuyYJ += order.getFloat("FirstYJ");
//                        totalordernobuymoney += order.getFloat("SF_Price");
//                        firstordernobuycount++;
//                        break;
//                    case 1:
//                        totalorderYJ += order.getFloat("FirstYJ");
//                        totalordermoney += order.getFloat("SF_Price");
//                        firstordercount++;
//                        break;
//                    case 2://已发货视为已付款
//                        totalorderYJ += order.getFloat("FirstYJ");
//                        totalordermoney += order.getFloat("SF_Price");
//                        firstordercount++;
//                        break;
//                    case 3:
//                        totalorderalthingYJ += order.getFloat("FirstYJ");
//                        totalordermoney += order.getFloat("SF_Price");
//                        firstordercount++;
//                        break;
                    case 5:
                        totalorderalendYJ += order.getFloat("FirstYJ");
                        break;
                }
            }

            List<Map<String, String>> secondsubscriberList = subscriberDAO.getByParentopenidList(wx.get("id"), firstsubscriber.get("id"), conn);
//            secondsubscribercount += secondsubscriberList.size();
            for (Map<String, String> secondsubscriber : secondsubscriberList) {

                //计算单数、金额
                List<DataField> secondorderList = (List<DataField>) DaoFactory.getOrderDAO().getList(wx.get("id"), secondsubscriber.get("openid"), -1, conn);
                for (DataField order : secondorderList) {
                    switch (order.getInt("Sts")) {
//                        case 0:
//                            totalordernobuyYJ += order.getFloat("SecondYJ");
//                            totalordernobuymoney += order.getFloat("SF_Price");
//                            secondordernobuycount++;
//                            break;
//                        case 1:
//                            totalorderYJ += order.getFloat("SecondYJ");
//                            totalordermoney += order.getFloat("SF_Price");
//                            secondordercount++;
//                            break;
//                        case 2://已发货视为已付款
//                            totalorderYJ += order.getFloat("SecondYJ");
//                            totalordermoney += order.getFloat("SF_Price");
//                            secondordercount++;
//                            break;
//                        case 3:
//                            totalorderalthingYJ += order.getFloat("SecondYJ");
//                            totalordermoney += order.getFloat("SF_Price");
//                            secondordercount++;
//                            break;
                        case 5:
                            totalorderalendYJ += order.getFloat("SecondYJ");
                            break;
                    }
                }

                List<Map<String, String>> thirdsubscriberList = subscriberDAO.getByParentopenidList(wx.get("id"), secondsubscriber.get("id"), conn);
//                thirdsubscribercount += thirdsubscriberList.size();

                for (Map<String, String> thirdsubscriber : thirdsubscriberList) {

                    //计算单数、金额
                    List<DataField> thirdorderList = (List<DataField>) DaoFactory.getOrderDAO().getList(wx.get("id"), thirdsubscriber.get("openid"), -1, conn);
                    for (DataField order : thirdorderList) {
                        switch (order.getInt("Sts")) {
//                            case 0:
//                                totalordernobuyYJ += order.getFloat("ThirdYJ");
//                                totalordernobuymoney += order.getFloat("SF_Price");
//                                thirdordernobuycount++;
//                                break;
//                            case 1:
//                                totalorderYJ += order.getFloat("ThirdYJ");
//                                totalordermoney += order.getFloat("SF_Price");
//                                thirdordercount++;
//                                break;
//                            case 2://已发货视为已付款
//                                totalorderYJ += order.getFloat("ThirdYJ");
//                                totalordermoney += order.getFloat("SF_Price");
//                                thirdordercount++;
//                                break;
//                            case 3:
//                                totalorderalthingYJ += order.getFloat("ThirdYJ");
//                                totalordermoney += order.getFloat("SF_Price");
//                                thirdordercount++;
//                                break;
                            case 5:
                                totalorderalendYJ += order.getFloat("ThirdYJ");
                                break;
                        }
                    }
                }
            }
        }
        //已提现财富(含申请)
        float totaltx = 0;
        List<DataField> txList = (List<DataField>) DaoFactory.getFundsDao().getList(0, 0, "", subscriber.get("openid"), "", -1, wx.get("id"), -1, -1);
        for (DataField tx : txList) {
            totaltx += tx.getFloat("F_Price");
        }

        //加上区域代理佣金
        return totalorderalendYJ + Float.parseFloat(subscriber.get("areaproxymoney")) - totaltx;
    }

//    public List<Map<String, String>> getSubscriberSelectList(PageUtil pu, String wxsid, int sign, int selectsign, String text, String starttime, String endtime) {
//        Map<String, String> wx = new HashMap<String, String>();
//        wx.put("id", wxsid);
//        wx = new WxsDAO().getById(wx);
////        自己订单总额
//        float ordermoney = 0;
////        上级id
//        String upfirstid = "0";
//        String upsecondid = "0";
//        String upthirdid = "0";
////        下级人数
//        int downfirstcount = 0;
//        int downsecondcount = 0;
//        int downthirdcount = 0;
////        下级购买数
//        int downfirstordercount = 0;
//        int downsecondordercount = 0;
//        int downthirdordercount = 0;
////        时间段销售额（不含已退货，未支付）
//        float firstSaleMoney = 0f;
//        float secondSaleMoney = 0f;
//        float thirdSaleMoney = 0f;
//        //        时间段销售额(不含已退货)
//        float firstSaleMoney2 = 0f;
//        float secondSaleMoney2 = 0f;
//        float thirdSaleMoney2 = 0f;
////        时间段粉丝量
//        int firstFans = 0;
//        int secondFans = 0;
//        int thirdFans = 0;
////        时间段佣金
//        float firstYJ = 0f;
//        float secondYJ = 0f;
//        float thirdYJ = 0f;
//
//        SubscriberDAO subscriberDAO = new SubscriberDAO();
//        Connection conn = DbConn.getConn();
////        用户取集合
//        List<Map<String, String>> subscriberList = subscriberDAO.getList(null, wxsid, sign, selectsign, text, starttime, endtime);
//        //        特殊 点击下级人数查询
//        System.out.println("特殊查询开始 " + WxMenuUtils.format.format(new Date()));
//        if (3 == sign) { //这里取出当前用户的指定下级列表，下面是列表中人员获取信息，并不矛盾
//            List<Map<String, String>> firstsubscriberTotalList = new ArrayList<Map<String, String>>();
//            List<Map<String, String>> secondsubscriberTotalList = new ArrayList<Map<String, String>>();
//            List<Map<String, String>> thirdsubscriberTotalList = new ArrayList<Map<String, String>>();
//            //第一级
//            List<Map<String, String>> firstsubscriberList = subscriberDAO.getByParentopenidList(wxsid, text, null, null, conn);
//            firstsubscriberTotalList.addAll(firstsubscriberList);
//
//            //点击下级text为id
//            Map<String, String> subscriber = subscriberDAO.getById(text);//获取用户openid
//            if (null != subscriber.get("id")) {
//                //准一级掌柜 公众号商家用户（商家用户限设一个）
//                if (subscriber.get("openid").equals(wx.get("adminsubscriber"))) {//是商家用户
//                    List<Map<String, String>> associatefirstsubscriberList = subscriberDAO.getByParentopenidList(wxsid, "0", conn);
//                    //删除自己内容
//                    List<Map<String, String>> tempassociatefirstsubscriberList = associatefirstsubscriberList;
//                    for (Map<String, String> associatefirstsubscriber : tempassociatefirstsubscriberList) {
//                        if (subscriber.get("openid").equals(associatefirstsubscriber.get("openid"))) {
//                            associatefirstsubscriberList.remove(associatefirstsubscriber);
//                            break;
//                        }
//                    }
//                    firstsubscriberTotalList.addAll(associatefirstsubscriberList);
//                }
//            }
//
//            List<Map<String, String>> secondsubscriberList = null;
//            List<Map<String, String>> thirdsubscriberList = null;
//            for (Map<String, String> firstsubscriber : firstsubscriberTotalList) {//firstsubscriberTotalList 这里包含了无上级用户（点击商家用户）
//                //第二级
//                secondsubscriberList = subscriberDAO.getByParentopenidList(wxsid, firstsubscriber.get("id"), null, null, conn);
//                secondsubscriberTotalList.addAll(secondsubscriberList);
//                for (Map<String, String> secondsubscriber : secondsubscriberList) {
//                    //第三级
//                    thirdsubscriberList = subscriberDAO.getByParentopenidList(wxsid, secondsubscriber.get("id"), null, null, conn);
//                    thirdsubscriberTotalList.addAll(thirdsubscriberList);
//                }
//            }
//            if (1 == selectsign) {
//                subscriberList = firstsubscriberTotalList;
//            } else if (2 == selectsign) {
//                subscriberList = secondsubscriberTotalList;
//            } else if (3 == selectsign) {
//                subscriberList = thirdsubscriberTotalList;
//            }
//        }
//        System.out.println("特殊查询结束 " + WxMenuUtils.format.format(new Date()));
//        Map<String, String> upfirstsubscriber = null;
//        Map<String, String> upsecondsubscriber = null;
//        Map<String, String> upthirdsubscriber = null;
//        List<Map<String, String>> firstsubscriberList = null;
//        List<Map<String, String>> secondsubscriberList = null;
//        List<Map<String, String>> thirdsubscriberList = null;
//        List<Map<String, String>> associatefirstsubscriberList = null;
//        List<Map<String, String>> tempassociatefirstsubscriberList = null;
//        System.out.println("主体方法开始 " + WxMenuUtils.format.format(new Date()));
//        for (Map<String, String> subscriber : subscriberList) {
//            //初始化数值
////            自己订单总额
//            ordermoney = 0;
//            //        上级id
//            upfirstid = "0";
//            upsecondid = "0";
//            upthirdid = "0";
////        下级人数
//            downfirstcount = 0;
//            downsecondcount = 0;
//            downthirdcount = 0;
////        下级购买数
//            downfirstordercount = 0;
//            downsecondordercount = 0;
//            downthirdordercount = 0;
////        时间段销售额（不含已退货，未支付）
//            firstSaleMoney = 0f;
//            secondSaleMoney = 0f;
//            thirdSaleMoney = 0f;
//            //        时间段销售额(不含已退货)
//            firstSaleMoney2 = 0f;
//            secondSaleMoney2 = 0f;
//            thirdSaleMoney2 = 0f;
////        时间段粉丝量
//            firstFans = 0;
//            secondFans = 0;
//            thirdFans = 0;
////        时间段佣金
//            firstYJ = 0f;
//            secondYJ = 0f;
//            thirdYJ = 0f;
//            //初始化结束
//
//            /*
//             * 每个用户的数据获取
//             */
////            自己订单总额
//            ordermoney = DaoFactory.getOrderDAO().getordermoney(wxsid, subscriber.get("openid"), -1, starttime, endtime);
//            //上级
//            upfirstsubscriber = subscriberDAO.getDataById(wxsid, subscriber.get("parentopenid"), conn);
//            if (null != upfirstsubscriber.get("id")) {
//                upfirstid = upfirstsubscriber.get("id");
//                upsecondsubscriber = subscriberDAO.getDataById(wxsid, upfirstsubscriber.get("parentopenid"), conn);
//                if (null != upsecondsubscriber.get("id")) {
//                    upsecondid = upsecondsubscriber.get("id");
//                    upthirdsubscriber = subscriberDAO.getDataById(wxsid, upsecondsubscriber.get("parentopenid"), conn);
//                    if (null != upthirdsubscriber.get("id")) {
//                        upthirdid = upthirdsubscriber.get("id");
//                    }
//                }
//            }
//            //下级
//            //第一级
//            firstsubscriberList = subscriberDAO.getTotalDataByParentopenidList(wxsid, subscriber.get("id"), starttime, endtime, conn);
////                    subscriberDAO.getDataByParentopenidList(wxsid, subscriber.get("id"), null, null, conn);
//            downfirstcount += firstsubscriberList.size();
//            //准一级掌柜 公众号商家用户（商家用户限设一个）
//            if (subscriber.get("openid").equals(wx.get("adminsubscriber"))) {//是商家用户
//                associatefirstsubscriberList = subscriberDAO.getTotalDataByParentopenidList(wxsid, "0", starttime, endtime, conn);
////                        subscriberDAO.getDataByParentopenidList(wxsid, "0", null, null, conn);
//                //删除自己内容
//                tempassociatefirstsubscriberList = associatefirstsubscriberList;
//                for (Map<String, String> associatefirstsubscriber : tempassociatefirstsubscriberList) {
//                    if (subscriber.get("openid").equals(associatefirstsubscriber.get("openid"))) {
//                        associatefirstsubscriberList.remove(associatefirstsubscriber);
//                        break;
//                    }
//                }
//                firstsubscriberList.addAll(associatefirstsubscriberList);
//                downfirstcount += associatefirstsubscriberList.size();
//            }
//
//            firstFans += subscriberDAO.getfans(wxsid, subscriber.get("id"), starttime, endtime);
//
//            //准一级掌柜 公众号商家用户（商家用户限设一个）
//            if (subscriber.get("openid").equals(wx.get("adminsubscriber"))) {//是商家用户
//                firstFans += subscriberDAO.getfans(wxsid, "0", starttime, endtime);
//                if ("0".equals(subscriber.get("parentopenid"))) {//把自己去掉
//                    firstFans--;
//                }
//            }
//
//            for (Map<String, String> firstsubscriber : firstsubscriberList) {
////                downfirstordercount += DaoFactory.getOrderDAO().getdownordercount(wxsid, firstsubscriber.get("openid"), -1, null, null);
////                firstSaleMoney += DaoFactory.getOrderDAO().getsalemoney(wxsid, firstsubscriber.get("openid"), -1, starttime, endtime, " and Sts!=7 and Sts!=0");
////                firstSaleMoney2 += DaoFactory.getOrderDAO().getsalemoney(wxsid, firstsubscriber.get("openid"), -1, starttime, endtime, " and Sts!=7");
////                firstYJ += DaoFactory.getOrderDAO().getyj(wxsid, firstsubscriber.get("openid"), 5, starttime, endtime, "").getFloat("FirstYJ");
//
//                downfirstordercount += null != firstsubscriber.get("downordercount") ? Integer.parseInt(firstsubscriber.get("downordercount")) : 0;
//                firstSaleMoney += null != firstsubscriber.get("salemoney1") ? Float.parseFloat(firstsubscriber.get("salemoney1")) : 0;
//                firstSaleMoney2 += null != firstsubscriber.get("salemoney2") ? Float.parseFloat(firstsubscriber.get("salemoney2")) : 0;
//                firstYJ += null != firstsubscriber.get("FirstYJ") ? Float.parseFloat(firstsubscriber.get("FirstYJ")) : 0;
//                //第二级
//                secondsubscriberList = subscriberDAO.getTotalDataByParentopenidList(wxsid, firstsubscriber.get("id"), starttime, endtime, conn);
////                        subscriberDAO.getDataByParentopenidList(wxsid, firstsubscriber.get("id"), null, null, conn);
//                downsecondcount += secondsubscriberList.size();
//                secondFans += subscriberDAO.getfans(wxsid, firstsubscriber.get("id"), starttime, endtime);
//                for (Map<String, String> secondsubscriber : secondsubscriberList) {
////                    downsecondordercount += DaoFactory.getOrderDAO().getdownordercount(wxsid, secondsubscriber.get("openid"), -1, null, null);
////                    secondSaleMoney += DaoFactory.getOrderDAO().getsalemoney(wxsid, secondsubscriber.get("openid"), -1, starttime, endtime, " and Sts!=7 and Sts!=0");
////                    secondSaleMoney2 += DaoFactory.getOrderDAO().getsalemoney(wxsid, secondsubscriber.get("openid"), -1, starttime, endtime, " and Sts!=7");
////                    secondYJ += DaoFactory.getOrderDAO().getyj(wxsid, secondsubscriber.get("openid"), 5, starttime, endtime, "").getFloat("SecondYJ");
//
//                    downsecondordercount += null != secondsubscriber.get("downordercount") ? Integer.parseInt(secondsubscriber.get("downordercount")) : 0;
//                    secondSaleMoney += null != secondsubscriber.get("salemoney1") ? Float.parseFloat(secondsubscriber.get("salemoney1")) : 0;
//                    secondSaleMoney2 += null != secondsubscriber.get("salemoney2") ? Float.parseFloat(secondsubscriber.get("salemoney2")) : 0;
//                    secondYJ += null != secondsubscriber.get("SecondYJ") ? Float.parseFloat(secondsubscriber.get("SecondYJ")) : 0;
//                    //第三级
//                    thirdsubscriberList = subscriberDAO.getTotalDataByParentopenidList(wxsid, secondsubscriber.get("id"), starttime, endtime, conn);
////                            subscriberDAO.getDataByParentopenidList(wxsid, secondsubscriber.get("id"), null, null, conn);
//                    downthirdcount += thirdsubscriberList.size();
//                    thirdFans += subscriberDAO.getfans(wxsid, secondsubscriber.get("id"), starttime, endtime);
//                    for (Map<String, String> thirdsubscriber : thirdsubscriberList) {
////                        downthirdordercount += DaoFactory.getOrderDAO().getdownordercount(wxsid, thirdsubscriber.get("openid"), -1, null, null);
////                        thirdSaleMoney += DaoFactory.getOrderDAO().getsalemoney(wxsid, thirdsubscriber.get("openid"), -1, starttime, endtime, " and Sts!=7 and Sts!=0");
////                        thirdSaleMoney2 += DaoFactory.getOrderDAO().getsalemoney(wxsid, thirdsubscriber.get("openid"), -1, starttime, endtime, " and Sts!=7");
////                        thirdYJ += DaoFactory.getOrderDAO().getyj(wxsid, thirdsubscriber.get("openid"), 5, starttime, endtime, "").getFloat("ThirdYJ");
//
//                        downthirdordercount += null != thirdsubscriber.get("downordercount") ? Integer.parseInt(thirdsubscriber.get("downordercount")) : 0;
//                        thirdSaleMoney += null != thirdsubscriber.get("salemoney1") ? Float.parseFloat(thirdsubscriber.get("salemoney1")) : 0;
//                        thirdSaleMoney2 += null != thirdsubscriber.get("salemoney2") ? Float.parseFloat(thirdsubscriber.get("salemoney2")) : 0;
//                        thirdYJ += null != thirdsubscriber.get("ThirdYJ") ? Float.parseFloat(thirdsubscriber.get("ThirdYJ")) : 0;
//                    }
//                }
//            }
//            subscriber.put("ordermoney", String.valueOf(WxMenuUtils.decimalFormat.format(ordermoney)));
//            subscriber.put("upfirstid", upfirstid);
//            subscriber.put("upsecondid", upsecondid);
//            subscriber.put("upthirdid", upthirdid);
//            subscriber.put("downfirstcount", String.valueOf(downfirstcount));
//            subscriber.put("downsecondcount", String.valueOf(downsecondcount));
//            subscriber.put("downthirdcount", String.valueOf(downthirdcount));
//            subscriber.put("downfirstordercount", String.valueOf(downfirstordercount));
//            subscriber.put("downsecondordercount", String.valueOf(downsecondordercount));
//            subscriber.put("downthirdordercount", String.valueOf(downthirdordercount));
//            subscriber.put("firstSaleMoney", String.valueOf(WxMenuUtils.decimalFormat.format(firstSaleMoney)));
//            subscriber.put("secondSaleMoney", String.valueOf(WxMenuUtils.decimalFormat.format(secondSaleMoney)));
//            subscriber.put("thirdSaleMoney", String.valueOf(WxMenuUtils.decimalFormat.format(thirdSaleMoney)));
//            subscriber.put("totalSaleMoney", String.valueOf(WxMenuUtils.decimalFormat.format(firstSaleMoney + secondSaleMoney + thirdSaleMoney)));
//            subscriber.put("totalSaleMoney2", String.valueOf(WxMenuUtils.decimalFormat.format(firstSaleMoney2 + secondSaleMoney2 + thirdSaleMoney2)));
//            subscriber.put("firstFans", String.valueOf(firstFans));
//            subscriber.put("secondFans", String.valueOf(secondFans));
//            subscriber.put("thirdFans", String.valueOf(thirdFans));
//            subscriber.put("firstYJ", String.valueOf(WxMenuUtils.decimalFormat.format(firstYJ)));
//            subscriber.put("secondYJ", String.valueOf(WxMenuUtils.decimalFormat.format(secondYJ)));
//            subscriber.put("thirdYJ", String.valueOf(WxMenuUtils.decimalFormat.format(thirdYJ)));
//            //已提现财富(含申请)
//            float totaltx = 0;
//            totaltx = DaoFactory.getFundsDao().getTotalMoney(0, 0, "", subscriber.get("openid"), "", -1, wxsid, -1, -1);
//            subscriber.put("totalYJ", String.valueOf(WxMenuUtils.decimalFormat.format(firstYJ + secondYJ + thirdYJ + Float.parseFloat(subscriber.get("areaproxymoney")) + Float.parseFloat(subscriber.get("callbackmoney")) - totaltx)));
//        }
//        System.out.println("主体方法结束 " + WxMenuUtils.format.format(new Date()));
//        try {
//            conn.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(SubscriberService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        if (2 == sign) {
//            String ordersign = "";
//            //对列表进行排序
//            if (1 == selectsign) {//第一级销售额
//                ordersign = "firstSaleMoney";
//            } else if (2 == selectsign) {//第二级销售额
//                ordersign = "secondSaleMoney";
//            } else if (3 == selectsign) {//第三级销售额
//                ordersign = "thirdSaleMoney";
//            } else if (4 == selectsign) {//总三级销售额
//                ordersign = "totalSaleMoney";
//            } else if (5 == selectsign) {//第一级粉丝数
//                ordersign = "firstFans";
//            } else if (6 == selectsign) {//第二级粉丝数
//                ordersign = "secondFans";
//            } else if (7 == selectsign) {//第三级粉丝数
//                ordersign = "thirdFans";
//            } else if (8 == selectsign) {//佣金总额
//                ordersign = "totalYJ";
//            } else if (9 == selectsign) {//区域代理佣金
//                ordersign = "areaproxymoney";
//            } else if (10 == selectsign) {//个人返现佣金
//                ordersign = "callbackmoney";
//            } else {
//                ordersign = "id";
//            }
//            //排序，倒序
//            System.out.println("排序开始 " + WxMenuUtils.format.format(new Date()));
//            Map<String, String> temp = null;
//            int subscriberListsize = subscriberList.size();
//            for (int i = 0; i < subscriberListsize; i++) {
//                for (int j = 0; j < i; j++) {
//                    if (null != subscriberList.get(i)) {
//                        if (Float.parseFloat(subscriberList.get(i).get(ordersign)) > Float.parseFloat(subscriberList.get(j).get(ordersign))) {
//                            temp = subscriberList.get(j);
//                            subscriberList.set(j, subscriberList.get(i));
//                            subscriberList.set(i, temp);
//                        }
//                    }
//                }
//            }
//            System.out.println("排序结束 " + WxMenuUtils.format.format(new Date()));
//        }
//        return subscriberList;
//    }
    public List<Map<String, String>> getSubscriberSelectList(PageUtil pu, String wxsid, int sign, int selectsign, String text, String starttime, String endtime) {
        Map<String, String> wx = new HashMap<String, String>();
        wx.put("id", wxsid);
        wx = new WxsDAO().getById(wx);
//        自己订单总额
        float ordermoney = 0;
//        上级id
        String upfirstid = "0";
        String upsecondid = "0";
        String upthirdid = "0";
//        下级人数
        int downfirstcount = 0;
        int downsecondcount = 0;
        int downthirdcount = 0;
//        下级购买数
        int downfirstordercount = 0;
        int downsecondordercount = 0;
        int downthirdordercount = 0;
//        时间段销售额（不含已退货，未支付）
        float firstSaleMoney = 0f;
        float secondSaleMoney = 0f;
        float thirdSaleMoney = 0f;
        //        时间段销售额(不含已退货)
        float firstSaleMoney2 = 0f;
        float secondSaleMoney2 = 0f;
        float thirdSaleMoney2 = 0f;
//        时间段粉丝量
        int firstFans = 0;
        int secondFans = 0;
        int thirdFans = 0;
//        时间段佣金
        float firstYJ = 0f;
        float secondYJ = 0f;
        float thirdYJ = 0f;

        SubscriberDAO subscriberDAO = new SubscriberDAO();
        Connection conn = DbConn.getConn();
//        用户取集合
        List<Map<String, String>> subscriberList = subscriberDAO.getList(null, wxsid, sign, selectsign, text, starttime, endtime);
        //        特殊 点击下级人数查询
        if (3 == sign) { //这里取出当前用户的指定下级列表，下面是列表中人员获取信息，并不矛盾
            List<Map<String, String>> firstsubscriberTotalList = new ArrayList<Map<String, String>>();
            List<Map<String, String>> secondsubscriberTotalList = new ArrayList<Map<String, String>>();
            List<Map<String, String>> thirdsubscriberTotalList = new ArrayList<Map<String, String>>();
            //第一级
            List<Map<String, String>> firstsubscriberList = subscriberDAO.getByParentopenidList(wxsid, text, null, null, conn);
            firstsubscriberTotalList.addAll(firstsubscriberList);

            //点击下级text为id
            Map<String, String> subscriber = subscriberDAO.getById(text);//获取用户openid
            if (null != subscriber.get("id")) {
                //准一级掌柜 公众号商家用户（商家用户限设一个）
                if (subscriber.get("openid").equals(wx.get("adminsubscriber"))) {//是商家用户
                    List<Map<String, String>> associatefirstsubscriberList = subscriberDAO.getByParentopenidList(wxsid, "0", null, null, conn);
                    //删除自己内容
                    List<Map<String, String>> tempassociatefirstsubscriberList = associatefirstsubscriberList;
                    for (Map<String, String> associatefirstsubscriber : tempassociatefirstsubscriberList) {
                        if (subscriber.get("openid").equals(associatefirstsubscriber.get("openid"))) {
                            associatefirstsubscriberList.remove(associatefirstsubscriber);
                            break;
                        }
                    }
                    firstsubscriberTotalList.addAll(associatefirstsubscriberList);
                }
            }

            List<Map<String, String>> secondsubscriberList = null;
            List<Map<String, String>> thirdsubscriberList = null;
            for (Map<String, String> firstsubscriber : firstsubscriberTotalList) {//firstsubscriberTotalList 这里包含了无上级用户（点击商家用户）
                //第二级
                secondsubscriberList = subscriberDAO.getByParentopenidList(wxsid, firstsubscriber.get("id"), null, null, conn);
                secondsubscriberTotalList.addAll(secondsubscriberList);
                for (Map<String, String> secondsubscriber : secondsubscriberList) {
                    //第三级
                    thirdsubscriberList = subscriberDAO.getByParentopenidList(wxsid, secondsubscriber.get("id"), null, null, conn);
                    thirdsubscriberTotalList.addAll(thirdsubscriberList);
                }
            }
            if (1 == selectsign) {
                subscriberList = firstsubscriberTotalList;
            } else if (2 == selectsign) {
                subscriberList = secondsubscriberTotalList;
            } else if (3 == selectsign) {
                subscriberList = thirdsubscriberTotalList;
            }
        }
        Map<String, String> upfirstsubscriber = null;
        Map<String, String> upsecondsubscriber = null;
        Map<String, String> upthirdsubscriber = null;
        List<Map<String, String>> firstsubscriberList = null;
        List<Map<String, String>> secondsubscriberList = null;
        List<Map<String, String>> thirdsubscriberList = null;
        List<Map<String, String>> associatefirstsubscriberList = null;
        List<Map<String, String>> tempassociatefirstsubscriberList = null;
        for (Map<String, String> subscriber : subscriberList) {
            //初始化数值
//            自己订单总额
            ordermoney = 0;
            //        上级id
            upfirstid = "0";
            upsecondid = "0";
            upthirdid = "0";
//        下级人数
            downfirstcount = 0;
            downsecondcount = 0;
            downthirdcount = 0;
//        下级购买数
            downfirstordercount = 0;
            downsecondordercount = 0;
            downthirdordercount = 0;
//        时间段销售额（不含已退货，未支付）
            firstSaleMoney = 0f;
            secondSaleMoney = 0f;
            thirdSaleMoney = 0f;
            //        时间段销售额(不含已退货)
            firstSaleMoney2 = 0f;
            secondSaleMoney2 = 0f;
            thirdSaleMoney2 = 0f;
//        时间段粉丝量
            firstFans = 0;
            secondFans = 0;
            thirdFans = 0;
//        时间段佣金
            firstYJ = 0f;
            secondYJ = 0f;
            thirdYJ = 0f;
            //初始化结束

            /*
             * 每个用户的数据获取
             */
//            自己订单总额
            ordermoney = DaoFactory.getOrderDAO().getordermoney(wxsid, subscriber.get("openid"), -1, starttime, endtime);
            //上级
            upfirstsubscriber = subscriberDAO.getDataById(wxsid, subscriber.get("parentopenid"), conn);
            if (null != upfirstsubscriber.get("id")) {
                upfirstid = upfirstsubscriber.get("id");
                upsecondsubscriber = subscriberDAO.getDataById(wxsid, upfirstsubscriber.get("parentopenid"), conn);
                if (null != upsecondsubscriber.get("id")) {
                    upsecondid = upsecondsubscriber.get("id");
                    upthirdsubscriber = subscriberDAO.getDataById(wxsid, upsecondsubscriber.get("parentopenid"), conn);
                    if (null != upthirdsubscriber.get("id")) {
                        upthirdid = upthirdsubscriber.get("id");
                    }
                }
            }
            //下级
            //第一级
            firstsubscriberList = subscriberDAO.getDataByParentopenidList(wxsid, subscriber.get("id"), null, null, conn, sign, selectsign);
            downfirstcount += firstsubscriberList.size();
            //准一级掌柜 公众号商家用户（商家用户限设一个）
            if (subscriber.get("openid").equals(wx.get("adminsubscriber"))) {//是商家用户
                associatefirstsubscriberList = subscriberDAO.getDataByParentopenidList(wxsid, "0", null, null, conn, sign, selectsign);
                //删除自己内容
                tempassociatefirstsubscriberList = associatefirstsubscriberList;
                for (Map<String, String> associatefirstsubscriber : tempassociatefirstsubscriberList) {
                    if (subscriber.get("openid").equals(associatefirstsubscriber.get("openid"))) {
                        associatefirstsubscriberList.remove(associatefirstsubscriber);
                        break;
                    }
                }
                firstsubscriberList.addAll(associatefirstsubscriberList);
                downfirstcount += associatefirstsubscriberList.size();
            }

            firstFans += subscriberDAO.getfans(wxsid, subscriber.get("id"), starttime, endtime);

            //准一级掌柜 公众号商家用户（商家用户限设一个）
            if (subscriber.get("openid").equals(wx.get("adminsubscriber"))) {//是商家用户
                firstFans += subscriberDAO.getfans(wxsid, "0", starttime, endtime);
                if ("0".equals(subscriber.get("parentopenid"))) {//把自己去掉
                    firstFans--;
                }
            }

            for (Map<String, String> firstsubscriber : firstsubscriberList) {
                downfirstordercount += DaoFactory.getOrderDAO().getdownordercount(wxsid, firstsubscriber.get("openid"), -1, null, null);
                firstSaleMoney += DaoFactory.getOrderDAO().getsalemoney(wxsid, firstsubscriber.get("openid"), -1, starttime, endtime, " and Sts!=7 and Sts!=0");
                firstSaleMoney2 += DaoFactory.getOrderDAO().getsalemoney(wxsid, firstsubscriber.get("openid"), -1, starttime, endtime, " and Sts!=7");
                firstYJ += DaoFactory.getOrderDAO().getyj(wxsid, firstsubscriber.get("openid"), 5, starttime, endtime, "").getFloat("FirstYJ");
                //第二级
                secondsubscriberList = subscriberDAO.getDataByParentopenidList(wxsid, firstsubscriber.get("id"), null, null, conn, sign, selectsign);
                downsecondcount += secondsubscriberList.size();
                secondFans += subscriberDAO.getfans(wxsid, firstsubscriber.get("id"), starttime, endtime);
                for (Map<String, String> secondsubscriber : secondsubscriberList) {
                    downsecondordercount += DaoFactory.getOrderDAO().getdownordercount(wxsid, secondsubscriber.get("openid"), -1, null, null);
                    secondSaleMoney += DaoFactory.getOrderDAO().getsalemoney(wxsid, secondsubscriber.get("openid"), -1, starttime, endtime, " and Sts!=7 and Sts!=0");
                    secondSaleMoney2 += DaoFactory.getOrderDAO().getsalemoney(wxsid, secondsubscriber.get("openid"), -1, starttime, endtime, " and Sts!=7");
                    secondYJ += DaoFactory.getOrderDAO().getyj(wxsid, secondsubscriber.get("openid"), 5, starttime, endtime, "").getFloat("SecondYJ");
                    //第三级
                    thirdsubscriberList = subscriberDAO.getDataByParentopenidList(wxsid, secondsubscriber.get("id"), null, null, conn, sign, selectsign);
                    downthirdcount += thirdsubscriberList.size();
                    thirdFans += subscriberDAO.getfans(wxsid, secondsubscriber.get("id"), starttime, endtime);
                    for (Map<String, String> thirdsubscriber : thirdsubscriberList) {
                        downthirdordercount += DaoFactory.getOrderDAO().getdownordercount(wxsid, thirdsubscriber.get("openid"), -1, null, null);
                        thirdSaleMoney += DaoFactory.getOrderDAO().getsalemoney(wxsid, thirdsubscriber.get("openid"), -1, starttime, endtime, " and Sts!=7 and Sts!=0");
                        thirdSaleMoney2 += DaoFactory.getOrderDAO().getsalemoney(wxsid, thirdsubscriber.get("openid"), -1, starttime, endtime, " and Sts!=7");
                        thirdYJ += DaoFactory.getOrderDAO().getyj(wxsid, thirdsubscriber.get("openid"), 5, starttime, endtime, "").getFloat("ThirdYJ");
                    }
                }
            }
            subscriber.put("ordermoney", String.valueOf(WxMenuUtils.decimalFormat.format(ordermoney)));
            subscriber.put("upfirstid", upfirstid);
            subscriber.put("upsecondid", upsecondid);
            subscriber.put("upthirdid", upthirdid);
            subscriber.put("downfirstcount", String.valueOf(downfirstcount));
            subscriber.put("downsecondcount", String.valueOf(downsecondcount));
            subscriber.put("downthirdcount", String.valueOf(downthirdcount));
            subscriber.put("downfirstordercount", String.valueOf(downfirstordercount));
            subscriber.put("downsecondordercount", String.valueOf(downsecondordercount));
            subscriber.put("downthirdordercount", String.valueOf(downthirdordercount));
            subscriber.put("firstSaleMoney", String.valueOf(WxMenuUtils.decimalFormat.format(firstSaleMoney)));
            subscriber.put("secondSaleMoney", String.valueOf(WxMenuUtils.decimalFormat.format(secondSaleMoney)));
            subscriber.put("thirdSaleMoney", String.valueOf(WxMenuUtils.decimalFormat.format(thirdSaleMoney)));
            subscriber.put("totalSaleMoney", String.valueOf(WxMenuUtils.decimalFormat.format(firstSaleMoney + secondSaleMoney + thirdSaleMoney)));
            subscriber.put("totalSaleMoney2", String.valueOf(WxMenuUtils.decimalFormat.format(firstSaleMoney2 + secondSaleMoney2 + thirdSaleMoney2)));
            subscriber.put("firstFans", String.valueOf(firstFans));
            subscriber.put("secondFans", String.valueOf(secondFans));
            subscriber.put("thirdFans", String.valueOf(thirdFans));
            subscriber.put("firstYJ", String.valueOf(WxMenuUtils.decimalFormat.format(firstYJ)));
            subscriber.put("secondYJ", String.valueOf(WxMenuUtils.decimalFormat.format(secondYJ)));
            subscriber.put("thirdYJ", String.valueOf(WxMenuUtils.decimalFormat.format(thirdYJ)));
            //已提现财富(含申请)
            float totaltx = 0;
            totaltx = DaoFactory.getFundsDao().getTotalMoney(0, 0, "", subscriber.get("openid"), "", -1, wxsid, -1, -1);
            subscriber.put("totalYJ", String.valueOf(WxMenuUtils.decimalFormat.format(firstYJ + secondYJ + thirdYJ + Float.parseFloat(subscriber.get("areaproxymoney")) + Float.parseFloat(subscriber.get("callbackmoney")) - totaltx)));
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberService.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (2 == sign) {
            String ordersign = "";
            //对列表进行排序
            if (1 == selectsign) {//第一级销售额
                ordersign = "firstSaleMoney";
            } else if (2 == selectsign) {//第二级销售额
                ordersign = "secondSaleMoney";
            } else if (3 == selectsign) {//第三级销售额
                ordersign = "thirdSaleMoney";
            } else if (4 == selectsign) {//总三级销售额
                ordersign = "totalSaleMoney";
            } else if (5 == selectsign) {//第一级粉丝数
                ordersign = "firstFans";
            } else if (6 == selectsign) {//第二级粉丝数
                ordersign = "secondFans";
            } else if (7 == selectsign) {//第三级粉丝数
                ordersign = "thirdFans";
            } else if (8 == selectsign) {//佣金总额
                ordersign = "totalYJ";
            } else if (9 == selectsign) {//区域代理佣金
                ordersign = "areaproxymoney";
            } else if (10 == selectsign) {//个人返现佣金
                ordersign = "callbackmoney";
            } else {
                ordersign = "id";
            }
            //排序，倒序
            Map<String, String> temp = null;
            int subscriberListsize = subscriberList.size();
            for (int i = 0; i < subscriberListsize; i++) {
                for (int j = 0; j < i; j++) {
                    if (null != subscriberList.get(i)) {
                        if (Float.parseFloat(subscriberList.get(i).get(ordersign)) > Float.parseFloat(subscriberList.get(j).get(ordersign))) {
                            temp = subscriberList.get(j);
                            subscriberList.set(j, subscriberList.get(i));
                            subscriberList.set(i, temp);
                        }
                    }
                }
            }
        }
        return subscriberList;
    }
//    public List<Map<String, String>> getSubscriberSelectList(PageUtil pu, String wxsid, int sign, int selectsign, String text, String starttime, String endtime) {
//        Map<String, String> wx = new HashMap<String, String>();
//        wx.put("id", wxsid);
//        wx = new WxsDAO().getById(wx);
//
////        自己订单总额
//        float ordermoney = 0;
////        上级id
//        String upfirstid = "0";
//        String upsecondid = "0";
//        String upthirdid = "0";
////        下级人数
//        int downfirstcount = 0;
//        int downsecondcount = 0;
//        int downthirdcount = 0;
////        下级购买数
//        int downfirstordercount = 0;
//        int downsecondordercount = 0;
//        int downthirdordercount = 0;
////        时间段销售额（不含已退货，未支付）
//        float firstSaleMoney = 0f;
//        float secondSaleMoney = 0f;
//        float thirdSaleMoney = 0f;
//        //        时间段销售额(不含已退货)
//        float firstSaleMoney2 = 0f;
//        float secondSaleMoney2 = 0f;
//        float thirdSaleMoney2 = 0f;
////        时间段粉丝量
//        int firstFans = 0;
//        int secondFans = 0;
//        int thirdFans = 0;
////        时间段佣金
//        float firstYJ = 0f;
//        float secondYJ = 0f;
//        float thirdYJ = 0f;
//
//        SubscriberDAO subscriberDAO = new SubscriberDAO();
//        Connection conn = DbConn.getConn();
//
////        用户取集合
//        List<Map<String, String>> subscriberList = subscriberDAO.getList(null, wxsid, sign, selectsign, text, starttime, endtime);
//
//        //        特殊 点击下级人数查询
//        if (3 == sign) { //这里取出当前用户的指定下级列表，下面是列表中人员获取信息，并不矛盾
//            List<Map<String, String>> firstsubscriberTotalList = new ArrayList<Map<String, String>>();
//            List<Map<String, String>> secondsubscriberTotalList = new ArrayList<Map<String, String>>();
//            List<Map<String, String>> thirdsubscriberTotalList = new ArrayList<Map<String, String>>();
//            //第一级
//            List<Map<String, String>> firstsubscriberList = subscriberDAO.getByParentopenidList(wxsid, text, null, null, conn);
//            firstsubscriberTotalList.addAll(firstsubscriberList);
//
//            //点击下级text为id
//            Map<String, String> subscriber = subscriberDAO.getById(text);//获取用户openid
//            if (null != subscriber.get("id")) {
//                //准一级掌柜 公众号商家用户（商家用户限设一个）
//                if (subscriber.get("openid").equals(wx.get("adminsubscriber"))) {//是商家用户
//                    List<Map<String, String>> associatefirstsubscriberList = subscriberDAO.getByParentopenidList(wxsid, "0", conn);
//                    //删除自己内容
//                    List<Map<String, String>> tempassociatefirstsubscriberList = associatefirstsubscriberList;
//                    for (Map<String, String> associatefirstsubscriber : tempassociatefirstsubscriberList) {
//                        if (subscriber.get("openid").equals(associatefirstsubscriber.get("openid"))) {
//                            associatefirstsubscriberList.remove(associatefirstsubscriber);
//                            break;
//                        }
//                    }
//                    firstsubscriberTotalList.addAll(associatefirstsubscriberList);
//                }
//            }
//
//            List<Map<String, String>> secondsubscriberList = null;
//            List<Map<String, String>> thirdsubscriberList = null;
//            for (Map<String, String> firstsubscriber : firstsubscriberTotalList) {//firstsubscriberTotalList 这里包含了无上级用户（点击商家用户）
//                //第二级
//                secondsubscriberList = subscriberDAO.getByParentopenidList(wxsid, firstsubscriber.get("id"), null, null, conn);
//                secondsubscriberTotalList.addAll(secondsubscriberList);
//                for (Map<String, String> secondsubscriber : secondsubscriberList) {
//                    //第三级
//                    thirdsubscriberList = subscriberDAO.getByParentopenidList(wxsid, secondsubscriber.get("id"), null, null, conn);
//                    thirdsubscriberTotalList.addAll(thirdsubscriberList);
//                }
//            }
//            if (1 == selectsign) {
//                subscriberList = firstsubscriberTotalList;
//            } else if (2 == selectsign) {
//                subscriberList = secondsubscriberTotalList;
//            } else if (3 == selectsign) {
//                subscriberList = thirdsubscriberTotalList;
//            }
//        }
//
////        List<DataField> orderList = null;
//        Map<String, String> upfirstsubscriber = null;
//        Map<String, String> upsecondsubscriber = null;
//        Map<String, String> upthirdsubscriber = null;
//        List<Map<String, String>> firstsubscriberList = null;
//        List<Map<String, String>> secondsubscriberList = null;
//        List<Map<String, String>> thirdsubscriberList = null;
//        List<Map<String, String>> associatefirstsubscriberList = null;
//        List<Map<String, String>> tempassociatefirstsubscriberList = null;
////        List<Map<String, String>> sefirstsubscriberList = null;
////        List<Map<String, String>> sesecondsubscriberList = null;
////        List<Map<String, String>> sethirdsubscriberList = null;
////        List<DataField> firstorderList = null;
////        List<DataField> sefirstorderList = null;
////        List<DataField> secondorderList = null;
////        List<DataField> sesecondorderList = null;
////        List<DataField> thirdorderList = null;
////        List<DataField> sethirdorderList = null;
//        List<DataField> txList = null;
//        for (Map<String, String> subscriber : subscriberList) {
//
//            //初始化数值
////            自己订单总额
//            ordermoney = 0;
//            //        上级id
//            upfirstid = "0";
//            upsecondid = "0";
//            upthirdid = "0";
////        下级人数
//            downfirstcount = 0;
//            downsecondcount = 0;
//            downthirdcount = 0;
////        下级购买数
//            downfirstordercount = 0;
//            downsecondordercount = 0;
//            downthirdordercount = 0;
////        时间段销售额（不含已退货，未支付）
//            firstSaleMoney = 0f;
//            secondSaleMoney = 0f;
//            thirdSaleMoney = 0f;
//            //        时间段销售额(不含已退货)
//            firstSaleMoney2 = 0f;
//            secondSaleMoney2 = 0f;
//            thirdSaleMoney2 = 0f;
////        时间段粉丝量
//            firstFans = 0;
//            secondFans = 0;
//            thirdFans = 0;
////        时间段佣金
//            firstYJ = 0f;
//            secondYJ = 0f;
//            thirdYJ = 0f;
//            //初始化结束
//
//            /*
//             * 每个用户的数据获取
//             */
////            自己订单总额
////            orderList = (List<DataField>) DaoFactory.getOrderDAO().getList(wxsid, subscriber.get("openid"), -1, conn);
////            for (DataField order : orderList) {
////                ordermoney += order.getFloat("TF_Price");
////            }
//            ordermoney = DaoFactory.getOrderDAO().getordermoney(wxsid, subscriber.get("openid"), -1);
//
//            //上级
//            upfirstsubscriber = subscriberDAO.getDataById(wxsid, subscriber.get("parentopenid"),conn);
//            if (null != upfirstsubscriber.get("id")) {
//                upfirstid = upfirstsubscriber.get("id");
//                upsecondsubscriber = subscriberDAO.getDataById(wxsid, upfirstsubscriber.get("parentopenid"),conn);
//                if (null != upsecondsubscriber.get("id")) {
//                    upsecondid = upsecondsubscriber.get("id");
//                    upthirdsubscriber = subscriberDAO.getDataById(wxsid, upsecondsubscriber.get("parentopenid"),conn);
//                    if (null != upthirdsubscriber.get("id")) {
//                        upthirdid = upthirdsubscriber.get("id");
//                    }
//                }
//            }
//            //下级
//            //第一级
//            firstsubscriberList = subscriberDAO.getDataByParentopenidList(wxsid, subscriber.get("id"),null,null, conn);
//            downfirstcount += firstsubscriberList.size();
//
//            //准一级掌柜 公众号商家用户（商家用户限设一个）
//            if (subscriber.get("openid").equals(wx.get("adminsubscriber"))) {//是商家用户
//                associatefirstsubscriberList = subscriberDAO.getDataByParentopenidList(wxsid, "0",null,null, conn);
//                //删除自己内容
//                tempassociatefirstsubscriberList = associatefirstsubscriberList;
//                for (Map<String, String> associatefirstsubscriber : tempassociatefirstsubscriberList) {
//                    if (subscriber.get("openid").equals(associatefirstsubscriber.get("openid"))) {
//                        associatefirstsubscriberList.remove(associatefirstsubscriber);
//                        break;
//                    }
//                }
//                firstsubscriberList.addAll(associatefirstsubscriberList);
//                downfirstcount += associatefirstsubscriberList.size();
//            }
//
////            sefirstsubscriberList = subscriberDAO.getDataByParentopenidList(wxsid, subscriber.get("id"), starttime, endtime, conn);
////            firstFans += sefirstsubscriberList.size();
//            firstFans += subscriberDAO.getfans(wxsid, subscriber.get("id"), starttime, endtime);
//
//            //准一级掌柜 公众号商家用户（商家用户限设一个）
//            if (subscriber.get("openid").equals(wx.get("adminsubscriber"))) {//是商家用户
////                associatefirstsubscriberList = subscriberDAO.getDataByParentopenidList(wxsid, "0", starttime, endtime, conn);
////                //删除自己内容
////                tempassociatefirstsubscriberList = associatefirstsubscriberList;
////                for (Map<String, String> associatefirstsubscriber : tempassociatefirstsubscriberList) {
////                    if (subscriber.get("openid").equals(associatefirstsubscriber.get("openid"))) {
////                        associatefirstsubscriberList.remove(associatefirstsubscriber);
////                        break;
////                    }
////                }
//////                sefirstsubscriberList.addAll(associatefirstsubscriberList);
////                firstFans += associatefirstsubscriberList.size();
//                firstFans += subscriberDAO.getfans(wxsid, "0", starttime, endtime);
//                if("0".equals(subscriber.get("parentopenid"))){
//                    firstFans--;
//                }
//            }
//
//            for (Map<String, String> firstsubscriber : firstsubscriberList) {
//                //订单
////                firstorderList = (List<DataField>) DaoFactory.getOrderDAO().getListSE(wxsid, firstsubscriber.get("openid"), -1, null, null, conn);
////                downfirstordercount += firstorderList.size();
////                sefirstorderList = (List<DataField>) DaoFactory.getOrderDAO().getListSE(wxsid, firstsubscriber.get("openid"), -1, starttime, endtime, conn);
////                for (DataField firstdorder : sefirstorderList) {
////                    //                    销售额
////                    if (7 != firstdorder.getInt("Sts") && 0 != firstdorder.getInt("Sts")) {
////                        firstSaleMoney += firstdorder.getFloat("F_Price");//一级销售额
////                    }
////                    if (7 != firstdorder.getInt("Sts")) {
////                        firstSaleMoney2 += firstdorder.getFloat("F_Price");//一级销售额
////                    }
////                    if (5 == firstdorder.getInt("Sts")) {
////                        firstYJ += firstdorder.getFloat("FirstYJ");//一级佣金
////                    }
////                }
//                downfirstordercount += DaoFactory.getOrderDAO().getdownordercount(wxsid, firstsubscriber.get("openid"), -1, null, null);
//                firstSaleMoney += DaoFactory.getOrderDAO().getsalemoney(wxsid, firstsubscriber.get("openid"), -1, starttime, endtime, " and Sts!=7 and Sts!=0");
//                firstSaleMoney2 += DaoFactory.getOrderDAO().getsalemoney(wxsid, firstsubscriber.get("openid"), -1, starttime, endtime, " and Sts!=7");
//                firstYJ += DaoFactory.getOrderDAO().getyj(wxsid, firstsubscriber.get("openid"), 5, starttime, endtime, "").getFloat("FirstYJ");
//                //第二级
//                secondsubscriberList = subscriberDAO.getDataByParentopenidList(wxsid, firstsubscriber.get("id"),null,null, conn);
//                downsecondcount += secondsubscriberList.size();
////                sesecondsubscriberList = subscriberDAO.getDataByParentopenidList(wxsid, firstsubscriber.get("id"), starttime, endtime, conn);
////                secondFans += sesecondsubscriberList.size();
//                secondFans += subscriberDAO.getfans(wxsid, firstsubscriber.get("id"), starttime, endtime);
//                for (Map<String, String> secondsubscriber : secondsubscriberList) {
//                    //订单
////                    secondorderList = (List<DataField>) DaoFactory.getOrderDAO().getListSE(wxsid, secondsubscriber.get("openid"), -1, null, null, conn);
////                    downsecondordercount += secondorderList.size();
////                    sesecondorderList = (List<DataField>) DaoFactory.getOrderDAO().getListSE(wxsid, secondsubscriber.get("openid"), -1, starttime, endtime, conn);
////                    for (DataField secondorder : sesecondorderList) {
////                        //                    销售额不含已退货
////                        if (7 != secondorder.getInt("Sts") && 0 != secondorder.getInt("Sts")) {
////                            secondSaleMoney += secondorder.getFloat("F_Price");//二级销售额
////                        }
////                        if (7 != secondorder.getInt("Sts")) {
////                            secondSaleMoney2 += secondorder.getFloat("F_Price");//二级销售额
////                        }
////                        if (5 == secondorder.getInt("Sts")) {
////                            secondYJ += secondorder.getFloat("SecondYJ");//二级佣金
////                        }
////                    }
//                    downsecondordercount += DaoFactory.getOrderDAO().getdownordercount(wxsid, secondsubscriber.get("openid"), -1, null, null);
//                    secondSaleMoney += DaoFactory.getOrderDAO().getsalemoney(wxsid, secondsubscriber.get("openid"), -1, starttime, endtime, " and Sts!=7 and Sts!=0");
//                    secondSaleMoney2 += DaoFactory.getOrderDAO().getsalemoney(wxsid, secondsubscriber.get("openid"), -1, starttime, endtime, " and Sts!=7");
//                    secondYJ += DaoFactory.getOrderDAO().getyj(wxsid, secondsubscriber.get("openid"), 5, starttime, endtime, "").getFloat("SecondYJ");
//
//                    //第三级
//                    thirdsubscriberList = subscriberDAO.getDataByParentopenidList(wxsid, secondsubscriber.get("id"),null,null, conn);
//                    downthirdcount += thirdsubscriberList.size();
////                    sethirdsubscriberList = subscriberDAO.getDataByParentopenidList(wxsid, secondsubscriber.get("id"), starttime, endtime, conn);
////                    thirdFans += sethirdsubscriberList.size();
//                    thirdFans += subscriberDAO.getfans(wxsid, secondsubscriber.get("id"), starttime, endtime);
//                    for (Map<String, String> thirdsubscriber : thirdsubscriberList) {
//                        //订单
////                        thirdorderList = (List<DataField>) DaoFactory.getOrderDAO().getListSE(wxsid, thirdsubscriber.get("openid"), -1, null, null, conn);
////                        downthirdordercount += thirdorderList.size();
////                        sethirdorderList = (List<DataField>) DaoFactory.getOrderDAO().getListSE(wxsid, thirdsubscriber.get("openid"), -1, starttime, endtime, conn);
////                        for (DataField thirdorder : sethirdorderList) {
////                            //                    销售额不含已退货
////                            if (7 != thirdorder.getInt("Sts") && 0 != thirdorder.getInt("Sts")) {
////                                thirdSaleMoney += thirdorder.getFloat("F_Price");//三级销售额
////                            }
////                            if (7 != thirdorder.getInt("Sts")) {
////                                thirdSaleMoney2 += thirdorder.getFloat("F_Price");//三级销售额
////                            }
////                            if (5 == thirdorder.getInt("Sts")) {
////                                thirdYJ += thirdorder.getFloat("ThirdYJ");//三级佣金
////                            }
////                        }
//                        downthirdordercount += DaoFactory.getOrderDAO().getdownordercount(wxsid, thirdsubscriber.get("openid"), -1, null, null);
//                        thirdSaleMoney += DaoFactory.getOrderDAO().getsalemoney(wxsid, thirdsubscriber.get("openid"), -1, starttime, endtime, " and Sts!=7 and Sts!=0");
//                        thirdSaleMoney2 += DaoFactory.getOrderDAO().getsalemoney(wxsid, thirdsubscriber.get("openid"), -1, starttime, endtime, " and Sts!=7");
//                        thirdYJ += DaoFactory.getOrderDAO().getyj(wxsid, thirdsubscriber.get("openid"), 5, starttime, endtime, "").getFloat("ThirdYJ");
//                    }
//                }
//            }
//
//            subscriber.put("ordermoney", String.valueOf(WxMenuUtils.decimalFormat.format(ordermoney)));
//            subscriber.put("upfirstid", upfirstid);
//            subscriber.put("upsecondid", upsecondid);
//            subscriber.put("upthirdid", upthirdid);
//            subscriber.put("downfirstcount", String.valueOf(downfirstcount));
//            subscriber.put("downsecondcount", String.valueOf(downsecondcount));
//            subscriber.put("downthirdcount", String.valueOf(downthirdcount));
//            subscriber.put("downfirstordercount", String.valueOf(downfirstordercount));
//            subscriber.put("downsecondordercount", String.valueOf(downsecondordercount));
//            subscriber.put("downthirdordercount", String.valueOf(downthirdordercount));
//            subscriber.put("firstSaleMoney", String.valueOf(WxMenuUtils.decimalFormat.format(firstSaleMoney)));
//            subscriber.put("secondSaleMoney", String.valueOf(WxMenuUtils.decimalFormat.format(secondSaleMoney)));
//            subscriber.put("thirdSaleMoney", String.valueOf(WxMenuUtils.decimalFormat.format(thirdSaleMoney)));
//            subscriber.put("totalSaleMoney", String.valueOf(WxMenuUtils.decimalFormat.format(firstSaleMoney + secondSaleMoney + thirdSaleMoney)));
//            subscriber.put("totalSaleMoney2", String.valueOf(WxMenuUtils.decimalFormat.format(firstSaleMoney2 + secondSaleMoney2 + thirdSaleMoney2)));
//            subscriber.put("firstFans", String.valueOf(firstFans));
//            subscriber.put("secondFans", String.valueOf(secondFans));
//            subscriber.put("thirdFans", String.valueOf(thirdFans));
//            subscriber.put("firstYJ", String.valueOf(WxMenuUtils.decimalFormat.format(firstYJ)));
//            subscriber.put("secondYJ", String.valueOf(WxMenuUtils.decimalFormat.format(secondYJ)));
//            subscriber.put("thirdYJ", String.valueOf(WxMenuUtils.decimalFormat.format(thirdYJ)));
//
//            //已提现财富(含申请)
//            float totaltx = 0;
////            txList = (List<DataField>) DaoFactory.getFundsDao().getList(0, 0, "", subscriber.get("openid"), "", -1, wxsid, -1, -1);
////            for (DataField tx : txList) {
////                totaltx += tx.getFloat("F_Price");
////            }
//            totaltx=DaoFactory.getFundsDao().getTotalMoney(0, 0, "", subscriber.get("openid"), "", -1, wxsid, -1, -1);
//            subscriber.put("totalYJ", String.valueOf(WxMenuUtils.decimalFormat.format(firstYJ + secondYJ + thirdYJ + Float.parseFloat(subscriber.get("areaproxymoney")) + Float.parseFloat(subscriber.get("callbackmoney")) - totaltx)));
//        }
//
//        try {
//            conn.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(SubscriberService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        if (2 == sign) {
//            String ordersign = "";
//            //对列表进行排序
//            if (1 == selectsign) {//第一级销售额
//                ordersign = "firstSaleMoney";
//            } else if (2 == selectsign) {//第二级销售额
//                ordersign = "secondSaleMoney";
//            } else if (3 == selectsign) {//第三级销售额
//                ordersign = "thirdSaleMoney";
//            } else if (4 == selectsign) {//总三级销售额
//                ordersign = "totalSaleMoney";
//            } else if (5 == selectsign) {//第一级粉丝数
//                ordersign = "firstFans";
//            } else if (6 == selectsign) {//第二级粉丝数
//                ordersign = "secondFans";
//            } else if (7 == selectsign) {//第三级粉丝数
//                ordersign = "thirdFans";
//            } else if (8 == selectsign) {//佣金总额
//                ordersign = "totalYJ";
//            } else if (9 == selectsign) {//区域代理佣金
//                ordersign = "areaproxymoney";
//            } else if (10 == selectsign) {//个人返现佣金
//                ordersign = "callbackmoney";
//            } else {
//                ordersign = "id";
//            }
//
//            //排序，倒序
//            Map<String, String> temp = null;
//            int subscriberListsize = subscriberList.size();
//            for (int i = 0; i < subscriberListsize; i++) {
//                for (int j = 0; j < i; j++) {
//                    if (null != subscriberList.get(i)) {
//                        if (Float.parseFloat(subscriberList.get(i).get(ordersign)) > Float.parseFloat(subscriberList.get(j).get(ordersign))) {
//                            temp = subscriberList.get(j);
//                            subscriberList.set(j, subscriberList.get(i));
//                            subscriberList.set(i, temp);
//                        }
//                    }
//                }
//            }
//
//        }
//        return subscriberList;
//    }
}
