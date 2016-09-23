package wap.wx.extservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import wap.wx.extutils.ExtWxConfig;
import wap.wx.extutils.ExtWxUtils;
import wap.wx.util.Forward;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@WebServlet(name = "BidingEstateServlet", urlPatterns = { "/extservlet/BidingEstateServlet" })
public class BidingEstateServlet extends ExtBaseServlet {

	/**
	 * 绑定楼栋
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws NumberFormatException 
	 * */
	private Map<String,Integer> units = new HashMap<String,Integer>();
	public void getBuilding(HttpServletRequest request,HttpServletResponse response) 
			throws NumberFormatException, ClientProtocolException, IOException {
		String xmid = (String) request.getParameter("xmid");
		JSONObject buildings = new ExtWxUtils().getBuildings(Integer.valueOf(xmid));
		JSONArray results = buildings.getJSONArray("result");
		dealUnit(results);
		PrintWriter out = response.getWriter();  
		out.print(results.toJSONString());
	}

	public void getUnit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=UTF-8"); 
		String ldbh = (String) request.getParameter("ldbh");
		Integer curunits = units.get(ldbh);
		PrintWriter out = response.getWriter();  
		out.print(curunits); 
	}
	
	private void dealUnit(JSONArray results){
		if(results==null || results.size()==0){
			return;
		}
		for(int i = 0;i<results.size();i++){
			JSONObject jo = (JSONObject) results.get(i);
			String key = jo.get("ldbh").toString();
			units.put(key,Integer.valueOf(jo.get("unitcount").toString()));
		}
	}
	
	public void sucBiding(HttpServletRequest request,HttpServletResponse response) 
			throws ClientProtocolException, IOException{
		request.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html;charset=UTF-8");
		String xmid = request.getParameter("xmid");//项目id
		String ldbh = request.getParameter("ldbh");//楼栋编号
		String ukey = request.getParameter("ukey");//用户登录手机号
		String unit = request.getParameter("unit");//单元号
		String room = request.getParameter("room");//房间号
		StringBuffer urlpost = new StringBuffer(ExtWxConfig.APIADDR+"/api/UserUpdate.ashx");
		urlpost.append("?ukey=").append(ukey).append("&xmid=").append(xmid).append("&ldbh=").append(ldbh);
		urlpost.append("&dybh=").append(unit).append("&room=").append(room);
		//Logger.getLogger(RegistedServlet.class).debug(ukey+"用户绑定房产信息请求信息:" + urlpost);
		HttpPost httpPost = new HttpPost(urlpost.toString());
		HttpResponse responses = HttpClients.createDefault().execute(httpPost);
		String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
//		{"code":"1","message":"操作成功。"}
//		String jsonStr = "{\"code\":\"1\",\"message\":\"操作成功。\"}";
		Logger.getLogger(RegistedServlet.class).debug(ukey+"用户绑定房产信息返回信息:" + jsonStr);
		JSONObject object = JSON.parseObject(jsonStr);
		//返回json数据根据code判断是否成功
		String code = object.getString("code");
		if("1".equals(code)){
			request.getSession().setAttribute("curuser", ukey);
			request.setAttribute("code",code);
			request.setAttribute("message",object.getString("message"));
			PrintWriter out = response.getWriter();
			out.print(object.getString("message"));
			out.flush();
			out.close();
//			response.sendRedirect("../extweb/wxlogin.jsp");
//			Forward.forward(request, response, "../extweb/wxlogin.jsp");
		}else{
			Forward.forward(request, response, "../extweb/bidingestate.jsp");
		}
	}
}
