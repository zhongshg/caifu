package job.tot.util;

import java.util.HashMap;
import java.util.Map;

public class SearchUtil {
    
    public static final Map<String,String> userSearchMap = new HashMap<String,String>();
    
    static{
	userSearchMap.put("1", "id");
	userSearchMap.put("2", "name");
	userSearchMap.put("3", "viplvl");
	userSearchMap.put("4", "cardid");
	userSearchMap.put("5", "bankcard");
	userSearchMap.put("6", "phone");
	userSearchMap.put("7", "parentid");
	userSearchMap.put("8", "ts");
	userSearchMap.put("9", "roleid");
	userSearchMap.put("10", "nick");
	userSearchMap.put("11", "storecode");
    }
    
    
}
