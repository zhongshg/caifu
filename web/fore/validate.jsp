<%@page import="job.tot.util.CodeUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java"
	errorPage="error.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="job.tot.global.Sysconfig"%>
<%@ page import="job.tot.db.DBUtils"%>
<%@ page import="job.tot.util.RequestUtil"%>
<%@ page import="job.tot.util.Forward"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="job.tot.dao.DaoFactory"%>
<%@ page import="job.tot.bean.DataField"%>
<%@ page import="job.tot.util.MD5"%>
<%@page import="job.tot.util.DateUtil"%>
<%
    // 返回json数据根据code判断是否成功
    String uname = RequestUtil.getString(request, "uname");
    String password = RequestUtil.getString(request, "password");
    String uid = RequestUtil.getString(request, "uid");
    String cardid = RequestUtil.getString(request, "cardid");
    String bankcard = RequestUtil.getString(request, "bankcard");
    String tel = RequestUtil.getString(request, "tel");
    String nick = RequestUtil.getString(request, "nick");
    String store = RequestUtil.getString(request, "store");
    String allid = RequestUtil.getString(request, "allid");
    String allVAL = RequestUtil.getString(request, "allVAL");
    String allPrice = RequestUtil.getString(request, "allPrice");
    String allName = RequestUtil.getString(request, "allName");
    String sum = RequestUtil.getString(request, "sum");
    int code = -1;

    try {
		String parentid = String.valueOf(session.getAttribute("user_id"));
		DataField assetsDF = DaoFactory.getAssetsDao().getByCol("id=" + parentid);
		Float balance = assetsDF.getFloat("balance");
		Float old_assets = assetsDF.getFloat("assets");
		if(balance < Float.valueOf(sum)){
		    code = 7;
		}else if (uname == null || password == null || uid == null || bankcard == null || cardid == null) {
		    code = 2;
		} else {
		    boolean flag = DaoFactory.getUserDao().validate(uid);
		    if (flag) {
				DataField count = DaoFactory.getUserDao().getByCol("phone=" + tel, "id");
				if (count != null && count.getInt("id") > 0) {
				    code = 4;
				} else {
				    DataField bcCount = DaoFactory.getUserDao().getByCol("cardid=" + cardid, "id");
				    if (bcCount != null && bcCount.getInt("id") > 0) {
					code = 3;
				    } else {
						DataField identityCount = DaoFactory.getUserDao().getByCol("bankcard=" + bankcard, "id");
						if (identityCount != null && identityCount.getInt("id") > 0) {
						    code = 5;
						} else {
						    password = new MD5().getMD5of32(password);
						    String oNum = DaoFactory.getOrdersDao().getNewProcode();
							Map<String,String> orders = new HashMap<String,String>();
							orders.put("oDt", DateUtil.getStringDate());
							orders.put("oLastUpdateDt", DateUtil.getStringDate());
							orders.put("oNum", oNum);
							orders.put("oPrice", allPrice);
							orders.put("oCount", allVAL);
							orders.put("oAmountMoney", sum);
							orders.put("ouserid", uid);
							orders.put("ousername", uname);
							orders.put("pid",allid);
							orders.put("pName", allName);
						    flag = DaoFactory.getUserDao().add(uname, password, parentid, cardid, bankcard, tel, uid, nick, store,orders);
						   
						    if(flag){
							    Float new_assets = old_assets - Float.valueOf(sum);
							    Float new_balance = balance - Float.valueOf(sum);
							    Map<String,String> assets = new HashMap<String,String>();
							    assets.put("assets", String.valueOf(new_assets));
							    assets.put("balance", String.valueOf(new_balance));
							    assets.put("id", parentid);
								flag = DaoFactory.getAssetsDao().update(null, assets);
						    }
						    if(flag && assetsDF.getString("viplvl")!=null){
								int viplvl = Integer.parseInt(session.getAttribute("user_viplvl").toString());
								if(viplvl!=4){//金级会员
								    List<DataField> dfList = DaoFactory.getAgencyDao().getByCol("parentid="+session.getAttribute("user_id"));
								    int number = dfList.size();	
								    Map<String,String> users = new HashMap<String,String>();
									String user_id = session.getAttribute("user_id").toString();
									if(number >= 5){//升级为钻级会员
										users.put("viplvl", "4");
									}else if(number >= 3){
									    users.put("viplvl", "3");
									}else if(number >= 1){
									    users.put("viplvl", "2");
									}
								    flag = DaoFactory.getUserDao().update(user_id, users);
								}
						    }
						    if (flag) {
								//DaoFactory.getuCodeDao().del(uid);
								code = 0;
						    } 
						}
				    }
				}
			 }else{
			     code = 6;
			 }
		}

		// 返回json数据根据code判断是否成功
		String jsons = "{\"code\":" + code + "}";
		PrintWriter out1 = response.getWriter();
		out1.print(jsons);
		out1.flush();
    } catch (Exception e) {
		e.printStackTrace();
		String jsons = "{\"code\":11}";
		PrintWriter out1 = response.getWriter();
		out1.print(jsons);
		out1.flush();
    }
%>
