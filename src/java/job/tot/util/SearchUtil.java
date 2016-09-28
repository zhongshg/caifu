package job.tot.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SearchUtil {

    public static Map<Integer, String> userSearchMap = new HashMap<Integer, String>();
    public static Map<Integer, String> userSelectMap = new HashMap<Integer, String>();

    static {
	userSearchMap.put(new Integer("1"), "id");
	userSearchMap.put(new Integer("2"), "name");
	userSearchMap.put(new Integer("3"), "viplvl");
	userSearchMap.put(new Integer("4"), "cardid");
	userSearchMap.put(new Integer("5"), "bankcard");
	userSearchMap.put(new Integer("6"), "phone");
	userSearchMap.put(new Integer("7"), "parentid");
	userSearchMap.put(new Integer("8"), "ts");
	userSearchMap.put(new Integer("9"), "roleid");
	userSearchMap.put(new Integer("10"), "nick");
	userSearchMap.put(new Integer("11"), "storecode");

	userSelectMap.put(new Integer("1"), "会员号");
	userSelectMap.put(new Integer("2"), "用户名");
	userSelectMap.put(new Integer("3"), "会员等级");
	userSelectMap.put(new Integer("4"), "身份证号");
	userSelectMap.put(new Integer("5"), "银行卡号");
	userSelectMap.put(new Integer("6"), "手机号");
	userSelectMap.put(new Integer("7"), "上级会员号");
	userSelectMap.put(new Integer("8"), "加入时间");
	userSelectMap.put(new Integer("9"), "角色");
	userSelectMap.put(new Integer("10"), "昵称");
	userSelectMap.put(new Integer("11"), "店铺号");
    }

    public static String getSelect(int cid) {
	StringBuffer sb = new StringBuffer();
	Iterator<Integer> iterator = userSelectMap.keySet().iterator();
	while (iterator.hasNext()) {
	    Integer id = (Integer) iterator.next();
	    String val = (String) userSelectMap.get(id);
	    sb.append("<option value=\"");
	    sb.append(id);
	    sb.append("\"");
	    if (id == cid)
		sb.append(" selected=\"selected\"");
	    sb.append(">");
	    sb.append(val);
	    sb.append("</option>\n");
	}
	return sb.toString();
    }

    public static String getItem(int index) {
	return (String) userSearchMap.get(new Integer(index));
    }
}
