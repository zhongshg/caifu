package job.tot.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import job.tot.dao.DaoFactory;

/**
 * 随机数生成器 本工具用于生成指定位数的随机数
 */
public class CodeUtils {

    /**
     * 构造方法
     */
    public CodeUtils() {
    }

    /**
     * 随机数
     * 
     * @param index
     *            生成位数
     * @param count
     *            生成的个数 注意：因为随机数是按照int生成的 所以最大值不能超过2147483648 请注意位数不能超过10位
     */
    public List<String> generate(int index, int count) throws SQLException {
	List<String> codeList = new ArrayList<String>();
	Random random = new Random();
	String maxtem = "8";
	String mintem = "1";
	for (int i = 1; i < index; i++) {
	    maxtem += "9";
	    mintem += "0";
	}
	int max = Integer.parseInt(maxtem);
	int min = Integer.parseInt(mintem);
	for (int i = 0; i <= count; i++) {
	    int randomInt = random.nextInt(max) + min;
	    codeList.add(String.valueOf(randomInt));
	}
	return codeList;
    }


}
