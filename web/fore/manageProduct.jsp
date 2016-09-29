<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ include file="common.jsp"%>
<%
    String rm = RequestUtil.getString(request, "rm");
    String pid = RequestUtil.getString(request, "pid");
    String amount = RequestUtil.getString(request, "amount");
    if (rm != null) {
		if (rm.equals("add") && amount!=null && pid!=null) { //新增商品
		    //根据pid查询商品信息
		    DataField pdf = DaoFactory.getProductDao().getByCol("id="+pid, null);
			Float oamountmoney = Float.parseFloat(pdf.getString("price")) * Integer.parseInt(amount);
			
		
			String onum = DaoFactory.getProductDao().getNewProcode();//获取新订单号
		    Map<String, String> orders = new HashMap<String, String>();
		    //oid,otitle,odt,osenddt,olastupdatedt,ostatus
		    orders.put("procode", pid);//商品id
		    orders.put("ouserid", user_id);//用户id即会员号
		    orders.put("ousername", session.getAttribute("user_name").toString());
		    orders.put("oprice", pdf.getString("price"));
		    orders.put("onum", onum);
		    orders.put("ocount", amount);
		    orders.put("oamountmoney", oamountmoney.toString());
		    orders.put("ostatus", "0");//未处理订单
		    boolean flag = DaoFactory.getOrdersDao().add(orders);
		    if (flag) {
			response.sendRedirect("manageProducts.jsp?msg=suca");
			//Forward.forward(request, response, "manageProducts.jsp?msg=suca");
		    } else {
			response.sendRedirect("manageProducts.jsp?msg=error");
		    }
		}
    }
%>
</html>