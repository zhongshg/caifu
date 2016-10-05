package job.tot.global;

import java.util.HashMap;
import java.util.Map;

/**
 * 本类为财富客户系统专用
 * 包含了整个系统中所有的固定值
 * */
public class GlobalEnum {
    private static Map<String,String> ASSETS_IN = new HashMap<String,String>();
    private static Map<String,String> ASSETS_OUT = new HashMap<String,String>();
    private static Map<String,String> ORDERSTATUS = new HashMap<String,String>();
    private static Map<String,String> VIPLVL = new HashMap<String,String>();
    
    static{
	ASSETS_IN.put("1", "佣金收入");
	ASSETS_IN.put("2", "福利收入");
	ASSETS_IN.put("3", "转账收入");
	ASSETS_IN.put("4", "充值收入");
	
	ASSETS_OUT.put("1", "佣金支出");
	ASSETS_OUT.put("2", "福利支出");
	ASSETS_OUT.put("3", "提现支出");
	ASSETS_OUT.put("4", "商品支出");
	ASSETS_OUT.put("5", "转账支出");
	
	ORDERSTATUS.put("-2", "订单删除");
	ORDERSTATUS.put("-1", "订单取消");
	ORDERSTATUS.put("0", "未处理");
	ORDERSTATUS.put("1", "订单确认");
	ORDERSTATUS.put("2", "订单发货");
	ORDERSTATUS.put("3", "订单完成");
	
	VIPLVL.put("0", "非会员");
	VIPLVL.put("1", "普通会员");
	VIPLVL.put("2", "银级会员");
	VIPLVL.put("3", "金级会员");
	VIPLVL.put("4", "钻级会员");
    }
}
