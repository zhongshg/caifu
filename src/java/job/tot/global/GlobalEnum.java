package job.tot.global;

import java.util.HashMap;
import java.util.Map;

/**
 * 本类为财富客户系统专用
 * 包含了整个系统中所有的固定值
 * */
public class GlobalEnum {
    /**
     * 11-佣金收入
     * 12-福利收入
     * 13-转账收入
     * 14-充值收入
     * 21-佣金支出
     * 22-福利支出
     * 23-提现支出
     * 24-商品支出
     * 25-转账支出
     * 26-注册支出
     * */
    public static Map<String,String> ASSETS = new HashMap<String,String>();
    /**
     * -2-订单删除
     * -1-订单取消
     * 0-未处理
     * 1-订单确认
     * 2-订单发货
     * 3-订单完成
     * */
    public static Map<String,String> ORDERSTATUS = new HashMap<String,String>();
    public static Map<String,String> VIPLVL = new HashMap<String,String>();
    public static Map<String,String> ASSETS_FLAG = new HashMap<String,String>();
    static{
	ASSETS.put("11", "佣金收入");
	ASSETS.put("12", "福利收入");
	ASSETS.put("13", "转账收入");
	ASSETS.put("14", "充值收入");
	ASSETS.put("21", "佣金支出");
	ASSETS.put("22", "福利支出");
	ASSETS.put("23", "提现支出");
	ASSETS.put("24", "商品支出");
	ASSETS.put("25", "转账支出");
	ASSETS.put("26", "注册支出");
	
	ORDERSTATUS.put("-2", "已删除");
	ORDERSTATUS.put("-1", "已取消");
	ORDERSTATUS.put("0", "未处理");
	ORDERSTATUS.put("1", "已确认");
	ORDERSTATUS.put("2", "已发货");
	ORDERSTATUS.put("3", "已完成");
	
	VIPLVL.put("0", "非会员");
	VIPLVL.put("1", "普通会员");
	VIPLVL.put("2", "银级会员");
	VIPLVL.put("3", "金级会员");
	VIPLVL.put("4", "钻级会员");
	
	ASSETS_FLAG.put("0", "处理中");
	ASSETS_FLAG.put("1", "成功");
	ASSETS_FLAG.put("2", "失败");
    }
}
