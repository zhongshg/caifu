package job.tot.util;

import java.util.List;

/**
 * SQL生成帮助工具
 * 
 * @author zhonshg
 * @since 2016年9月22日 17:25:56
 */
public class SQLUtil {

    /** 将List的值转换为"a,a,a,a,a,a"的形式 */
    public static String listToStr(List<String> list) {
	StringBuffer sqlStr = new StringBuffer("");
	if (list != null && list.size() > 0) {
	    sqlStr.append(list.get(0));
	    for (int i = 1; i < list.size(); i++) {
		sqlStr.append(",");
		sqlStr.append(list.get(i));
	    }
	}
	return sqlStr.toString();
    }

    /** 将String数组的值转换为"a,a,a"的形式 */
    public static String arrayToStr(String[] arr) {
	StringBuffer sqlStr = new StringBuffer("");
	if (arr != null && arr.length > 0) {
	    sqlStr.append(arr[0]);
	    for (int i = 1; i < arr.length; i++) {
		sqlStr.append(",");
		sqlStr.append(arr[i]);
	    }
	}
	return sqlStr.toString();
    }
}
