<%@page import="wap.wx.service.PublicService"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Random"%>
<%@page import="java.util.Iterator"%>
<%@page import="job.tot.bean.DataField"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="job.tot.util.DateUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="job.tot.dao.DaoFactory"%>
<%@page import="job.tot.util.RequestUtil"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ include file="session.jsp"%>
<%    ArrayList list = null;
    DataField df = null;
    boolean bl = false;
    Random random = new Random();
    String sRand = "";
    for (int j = 0; j < 4; j++) {
        String rand = String.valueOf(random.nextInt(10));
        sRand += rand;
    }
    String act = RequestUtil.getString(request, "act");

    if ("addproperty".equals(act)) {
        String pid = RequestUtil.getString(request, "pid");
        int signid = RequestUtil.getInt(request, "signid");
        String propertyname = RequestUtil.getString(request, "propertyname");
        String wxsid = RequestUtil.getString(request, "wxsid");
        String propertyid = "";
        try {
            propertyid = String.valueOf(DaoFactory.getPropertysDaoImplJDBC().getMaxId().getInt("counts") + 1);
        } catch (Exception e) {
            propertyid = "1";
        }
        if (!"0".equals(pid)) {
            propertyid = pid + "-" + propertyid;
        }
        if (DaoFactory.getPropertysDaoImplJDBC().add(propertyid, propertyname, pid, signid, wxsid)) {
            out.print(propertyid);
        } else {
            out.print("-1");
        };
    }

    //属性标签
    if ("shuxingsx".equals(RequestUtil.getString(request, "act"))) {
        df = null;
        String svid = RequestUtil.getString(request, "shuxing");
        int signid = RequestUtil.getInt(request, "signid");
        int pid = RequestUtil.getInt(request, "pid");
        String propertysDF = (String) session.getAttribute("propertysDF");
        out.clear();
        //df=DaoFactory.getBiaoQianConDao().get(0, 0, shuxing, 0, time_pid);
        df = DaoFactory.getPropertysDaoImplJDBC().get(svid);
        if (df != null) {
            List<DataField> propertysList = (List<DataField>) DaoFactory.getPropertysDaoImplJDBC().getList(String.valueOf(svid), signid, df.getFieldValue("wxsid"));
            StringBuilder sub = new StringBuilder("");
            for (DataField property : propertysList) {
                if (propertysDF.indexOf(property.getFieldValue("svid")) != -1) {
                    sub.append("<input name =\"propertycheckbox\" id=\"propertycheckbox\" type=\"checkbox\" value=\"" + property.getFieldValue("svid") + "\" checked> " + property.getFieldValue("svname") + "</input><!--属性标签--> ");
                } else {
                    sub.append("<input name =\"propertycheckbox\" id=\"propertycheckbox\" type=\"checkbox\" value=\"" + property.getFieldValue("svid") + "\"> " + property.getFieldValue("svname") + "</input><!--属性标签--> ");
                }
            }
            out.print(sub.toString());
        } else {
            out.print("0");
        }
    }

    if ("sxdo".equals(RequestUtil.getString(request, "act"))) {
        String wxsid = wx.get("id");
        int signid = RequestUtil.getInt(request, "signid");
        int pid = RequestUtil.getInt(request, "pid");
        String property = ((String) session.getAttribute("propertysDF"));
        int sx_id = RequestUtil.getInt(request, "sx_id");
        String strsx_con_id = new String(RequestUtil.getString(request, "sx_con_id").getBytes("iso-8859-1"), "utf-8");//前台传递的sx_con_id 集合
        int sx_con_id = 0;
        //取出所有属性进行删除
        List<DataField> delpropertyList = (List<DataField>) DaoFactory.getPropertysDaoImplJDBC().getList(String.valueOf(sx_id), signid, wxsid);
        for (DataField delproperty : delpropertyList) {
            property = property.replace(signid + "," + delproperty.getFieldValue("svid"), "");//0在前面用于标识位，作为sid用，区分多类属性
        }
        property += strsx_con_id;
//        boolean flag = DaoFactory.getProductDAO().modpropertys(pid, property);
        session.setAttribute("propertysDF", property);

        //组织显示数据
//        StringBuilder param = new StringBuilder("<table id=\"tb\" width=\"100%\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" bgcolor=\"#A3B2CC\" >");
//        String propertys[] = property.split(" ");
//        String sign = "";
//        param.append("<tr>");
//        for (int i = 0; i < propertys.length; i++) {
//            if ("".equals(propertys[i].trim())) {
//                continue;
//            }
//            String propertyArr[] = propertys[i].split(",");
//            if (0 == propertyArr.length) {
//                continue;
//            }
//            DataField propertyDF = DaoFactory.getPropertysDaoImplJDBC().get(propertyArr[1].split("-")[0]);
//            if (null == propertyDF) {
//                continue;
//            }
//            String propertyname = propertyDF.getFieldValue("svname");
//            if (!sign.equals(propertyname)) {
//                sign = propertyname;
//                param.append("</tr><tr> <td bgcolor=\"#F2F2F2\">" + propertyname + ":</td>");
//            }
//            propertyDF = DaoFactory.getPropertysDaoImplJDBC().get(propertyArr[1]);
//            if (null == propertyDF) {
//                continue;
//            }
//            String propertyvaluename = propertyDF.getFieldValue("svname");
//            param.append("<td bgcolor=\"#F2F2F2\"><div align=\"center\">" + propertyvaluename + "</div></td>");
//        }
//        param.append("</tr>");
//        param.append("</table>");
        if (null != property) {
            out.print(new PublicService().getPsvDiv(property, signid, pid, wx));
        }
    }
%>
