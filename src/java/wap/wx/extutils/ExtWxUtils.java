package wap.wx.extutils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import wap.wx.menu.WxMenuUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ExtWxUtils {
	/**
	 * 获取所有房产列表
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 * */
	private static Map<Integer,Map<String, String>> estates = null;
	public Map<Integer, Map<String, String>> getEstates()
			throws ClientProtocolException, IOException {
		if(estates==null){
			StringBuffer urlpost = new StringBuffer(
					ExtWxConfig.APIADDR+"/api/get_lpxm.ashx");
			Logger.getLogger(ExtWxUtils.class).debug("获取楼盘项目请求信息:" + urlpost);
			HttpPost httpPost = new HttpPost(urlpost.toString());
			HttpResponse responses = new DefaultHttpClient().execute(httpPost);
			String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
			Logger.getLogger(ExtWxUtils.class).debug("获取楼盘项目返回信息:" + jsonStr);
			JSONObject object = JSON.parseObject(jsonStr);
			JSONArray results = object.getJSONArray("result");
			//存储所有项目列表 以项目id为主键
			 estates = new HashMap<Integer,Map<String, String>>();
			for (int i = 0; i < results.size(); i++) {
				//存储单个项目列表 以各个属性为主键
				Map<String,String> estate = new HashMap<String,String>();
				JSONObject jo = (JSONObject) results.get(i);
				Integer xmid = (Integer)jo.get("xmid");
				estate.put("xmid",jo.get("xmid").toString());
				estate.put("xmmc",jo.get("xmmc").toString());
				estate.put("province",jo.get("province").toString());
				estate.put("city",jo.get("city").toString());
				estates.put(xmid, estate);
			}
		}
		return estates;
	}
	
	 /* 获取所有房产列表
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 * */
	public JSONObject getBuildings(Integer xmid)
			throws ClientProtocolException, IOException {
		StringBuffer urlpost = new StringBuffer(
				ExtWxConfig.APIADDR+"/api/get_ldbh.ashx");
		urlpost.append("?xmid=").append(xmid.intValue());
		Logger.getLogger(ExtWxUtils.class).debug("获取楼栋列表请求信息:" + urlpost);
		HttpPost httpPost = new HttpPost(urlpost.toString());
		HttpResponse responses = new DefaultHttpClient().execute(httpPost);
		String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
		Logger.getLogger(ExtWxUtils.class).debug("获取楼栋列表返回信息:" + jsonStr);
		JSONObject object = JSON.parseObject(jsonStr);
		/*//存储所有项目列表 以项目id为主键
		Map<Integer,Map<String, String>> buildings = new HashMap<Integer,Map<String, String>>();
		//存储单个项目列表 以各个属性为主键
		Map<String,String> building = new HashMap<String,String>();
		for (int i = 0; i < results.size(); i++) {
			JSONObject jo = (JSONObject) results.get(i);
			building.put("xmid",jo.get("xmid").toString());
			building.put("ldbh",jo.get("ldbh").toString());
			building.put("ldmc",jo.get("ldmc").toString());
			building.put("unitcount",jo.get("unitcount").toString());
			buildings.put(xmid, building);
		}*/
		return object;
	}
//	/**
//	 * 获取所有房产列表
//	 * 
//	 * @throws IOException
//	 * @throws ClientProtocolException
//	 * */
//	public static Map<String, Object> getBuilding(String name)
//			throws ClientProtocolException, IOException {
//		StringBuffer urlpost = new StringBuffer(
//				"http://222.43.124.156:9007/api/get_repairlist.ashx");
//		urlpost.append("?ukey=").append(name).append("&endindex=").append(20)
//				.append("&startindex=").append(1);
//		Logger.getLogger(ExtWxUtils.class).debug("获取报修列表请求信息:" + urlpost);
//		HttpPost httpPost = new HttpPost(urlpost.toString());
//		HttpResponse responses = new DefaultHttpClient().execute(httpPost);
//		String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
//		Logger.getLogger(ExtWxUtils.class).debug("获取报修列表返回信息:" + jsonStr);
//		JSONObject object = JSON.parseObject(jsonStr);
//
//		return null;
//	}
	
	/**
	 * 获取报修列表
	 * */
	private static JSONArray repResults = null;
	public JSONArray getRepairType() throws ClientProtocolException, IOException {
		if(repResults==null || repResults.size()>0){
			StringBuffer urlpost = new StringBuffer(ExtWxConfig.APIADDR+"/api/get_getRepairType.ashx");
			Logger.getLogger(ExtWxUtils.class).debug("报修类别列表请求信息:" + urlpost);
			HttpPost httpPost = new HttpPost(urlpost.toString());
			HttpResponse responses = new DefaultHttpClient().execute(httpPost);
			String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
			Logger.getLogger(ExtWxUtils.class).debug("报修类别列表返回信息:" + jsonStr);
			JSONObject object = JSON.parseObject(jsonStr);
			repResults = object.getJSONArray("result");
		}
		return repResults;
	}
	
	/**
	 * 获取投诉建议类型
	 * */
	private static JSONArray comResults = null;
	public JSONArray getComplainType() throws ClientProtocolException, IOException {
		if(comResults==null || comResults.size()>0){
			StringBuffer urlpost = new StringBuffer(ExtWxConfig.APIADDR+"/api/get_SuggestionType.ashx");
			Logger.getLogger(ExtWxUtils.class).debug("投诉建议类型请求信息:" + urlpost);
			HttpPost httpPost = new HttpPost(urlpost.toString());
			HttpResponse responses = new DefaultHttpClient().execute(httpPost);
			String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
			Logger.getLogger(ExtWxUtils.class).debug("投诉建议类型返回信息:" + jsonStr);
			JSONObject object = JSON.parseObject(jsonStr);
			comResults = object.getJSONArray("result");
		}
		return comResults;
	}
}
