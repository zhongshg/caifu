<%@page import="job.tot.db.DBUtils"%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="common.jsp"%>
<%
    String rm = RequestUtil.getString(request, "rm");
    String pid = RequestUtil.getString(request, "pid");
    String amount = RequestUtil.getString(request, "amount");
    if (rm != null) {
		if (rm.equals("buy") && amount!=null && pid!=null) { //购买商品
		    //根据pid查询商品信息
		    DataField pdf = DaoFactory.getProductDao().getByCol("id="+pid, null);
			Float oamountmoney = Float.parseFloat(pdf.getString("price")) * Integer.parseInt(amount);
			//校验用户的总额是否足够
			DaoFactory.getAssetsDao().getByCol(" id ="+user_id);
			
			String onum = DaoFactory.getProductDao().getNewProcode();//获取新订单号
		    Map<String, String> orders = new HashMap<String, String>();
		    //oid,otitle,odt,osenddt,olastupdatedt,ostatus
		    orders.put("pid", pid);//商品id
		    orders.put("ouserid", user_id);//用户id即会员号
		    orders.put("ousername", user_name);
		    orders.put("pName",pdf.getString("pname"));
		    orders.put("oPrice", pdf.getString("price"));
		    orders.put("oNum", onum);
		    orders.put("oCount", amount);
		    orders.put("oAmountMoney", oamountmoney.toString());
		    orders.put("oStatus", "0");//未处理订单
		    orders.put("oDt", DateUtil.getStringDate());
		    orders.put("oLastUpdateDt", DateUtil.getStringDate());
		    Connection conn = DbConn.getConn();
		    boolean flag = false;
		    try{
			    //新增订单
			    //校验余额是否够用，并扣除费用
			    DataField adf = DaoFactory.getAssetsDao().getByCol("id=" + user_id);
			    Float assets = adf.getFloat("assets");
			    if(assets > oamountmoney){
				    conn.setAutoCommit(false);
				    Map<String, String> cur_assets = new HashMap<String, String>();
				    assets = assets - oamountmoney;
				    cur_assets.put("id", user_id);
				    cur_assets.put("assets", assets.toString());
				    DaoFactory.getAssetsDao().update(conn, cur_assets);
					flag = DaoFactory.getOrdersDao().add(conn,orders);
				    //新增入账记录
				    Map<String, String> assets_out = new HashMap<String, String>();
				    //uid,amount,type,oid
				    assets_out.put("uid", user_id);
				    assets_out.put("amount", oamountmoney.toString());
				    assets_out.put("type", "24");
				    assets_out.put("oid", onum);
				    flag = DaoFactory.getAssetsOUTDao().add(assets_out, conn);
				    if (flag) {
						conn.commit();
						response.sendRedirect("manageProducts.jsp?msg=suca");
						//  Forward.forward(request, response, "manageProducts.jsp?msg=suca");
				    } else {
						response.sendRedirect("manageProducts.jsp?msg=error");
				    }
			    }else{
					response.sendRedirect("manageProducts.jsp?msg=NOMny");
			    }
		    }catch(Exception e){
				e.printStackTrace();
		    }finally{
				DBUtils.closeConnection(conn);
		    }
		}
    }
%>
</html>