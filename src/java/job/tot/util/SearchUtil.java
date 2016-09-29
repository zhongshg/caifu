package job.tot.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SearchUtil {

    public static Map<String, String> userSearchMap = new HashMap<String, String>();
    public static Map<String, String> productSearchMap = new HashMap<String, String>();
    public static Map<String,String>  orderSearchMap = new HashMap<String, String>();

    static {
	userSearchMap.put("1", "id#会员号");
	userSearchMap.put("2", "name#用户名");
	userSearchMap.put("3", "viplvl#会员等级");
	userSearchMap.put("4", "cardid#身份证号");
	userSearchMap.put("5", "bankcard#银行卡号");
	userSearchMap.put("6", "phone#手机号");
	userSearchMap.put("7", "parentid#上级会员号");
	userSearchMap.put("8", "ts#加入时间");
	userSearchMap.put("9", "roleid#角色");
	userSearchMap.put("10", "nick#昵称");
	userSearchMap.put("11", "storecode#商铺号");

	productSearchMap.put("1", "procode#商品编号");
	productSearchMap.put("2", "proname#商品名称");
	productSearchMap.put("3", "propertys#商品属性");
	
	//oid,otitle,odt,osenddt,olastupdatedt,ostatus,onum,ocount,oamountmoney,oprice,ouserid,ousername,ousercode
	orderSearchMap.put("1", "ouserid#用户编号");
	orderSearchMap.put("2", "ousername#用户名称");
	orderSearchMap.put("3", "odt#生成时间");
	orderSearchMap.put("4", "osenddt#发货时间");
	orderSearchMap.put("5", "olastupdatedt#最后修改时间");
	orderSearchMap.put("6", "ostatus#订单状态");
	orderSearchMap.put("7", "onum#订单号");
    }

    public static String getProductSelect(String cid) {
	StringBuffer sb = new StringBuffer();
	Iterator<String> iterator = productSearchMap.keySet().iterator();
	while (iterator.hasNext()) {
	    String id = (String) iterator.next();
	    String val =  (String) productSearchMap.get(id);
	    sb.append("<option value=\"");
	    sb.append(id);
	    sb.append("\"");
	    if (id.equals(cid))
		sb.append(" selected=\"selected\"");
	    sb.append(">");
	    sb.append(val.split("#")[1]);
	    sb.append("</option>\n");
	}
	return sb.toString();
    }

    public static String getUsersSelect(String cid) {
	StringBuffer sb = new StringBuffer();
	Iterator<String> iterator = userSearchMap.keySet().iterator();
	
	while (iterator.hasNext()) {
	    String id = (String) iterator.next();
	    String val =  (String) userSearchMap.get(id);
	    sb.append("<option value=\"");
	    sb.append(id);
	    sb.append("\"");
	    if (id.equals(cid))
		sb.append(" selected=\"selected\"");
	    sb.append(">");
	    sb.append(val.split("#")[1]);
	    sb.append("</option>\n");
	}
	return sb.toString();
    }
    
    public static String getOrdersSelect(String cid) {
	StringBuffer sb = new StringBuffer();
	Iterator<String> iterator = orderSearchMap.keySet().iterator();
	
	while (iterator.hasNext()) {
	    String id = (String) iterator.next();
	    String val =  (String) orderSearchMap.get(id);
	    sb.append("<option value=\"");
	    sb.append(id);
	    sb.append("\"");
	    if (id.equals(cid))
		sb.append(" selected=\"selected\"");
	    sb.append(">");
	    sb.append(val.split("#")[1]);
	    sb.append("</option>\n");
	}
	return sb.toString();
    }
    
    public static String getItem(int index) {
	return (String) userSearchMap.get(index);
    }
}
