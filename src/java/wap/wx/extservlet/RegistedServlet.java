package wap.wx.extservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import wap.wx.extutils.ExtWxConfig;
import wap.wx.util.Forward;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * add_zhongshg
 * 2016年8月11日 16:46:39
 * 绿地集团微信平台用户注册Servlet类
 * */
@WebServlet(name = "RegistedServlet", urlPatterns = { "/extservlet/RegistedServlet" })
public class RegistedServlet extends ExtBaseServlet {

	public void addUser(HttpServletRequest request, HttpServletResponse response)
			throws ClientProtocolException, IOException {
		String phonenum = request.getParameter("_phonenum");
		String password = request.getParameter("_phonepwd");
		String phonecode = request.getParameter("_phonecode");
		String uname = request.getParameter("_phonename");
		if(phonenum==null || phonenum=="" || password=="" || password==null || phonecode=="" || phonecode==null){
			request.setAttribute("message","请填写所有信息");
			Forward.forward(request, response, "../extweb/registed.jsp");
		}
		String repwd = request.getParameter("pwd");
		StringBuffer urlpost = new StringBuffer(ExtWxConfig.APIADDR+"/api/post_register.ashx");
		urlpost.append("?name=").append(phonenum).append("&p1=").append(password).append("&p2=").append(repwd)
		.append("&vk=").append(phonecode).append("&uname=").append(uname);
		//Logger.getLogger(RegistedServlet.class).debug("用户注册请求信息:" + urlpost);
		HttpPost httpPost = new HttpPost(urlpost.toString());
		HttpResponse responses = HttpClients.createDefault().execute(httpPost);
		String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
		Logger.getLogger(RegistedServlet.class).debug("用户注册返回信息:" + jsonStr);
		JSONObject object = JSON.parseObject(jsonStr);
		//返回json数据根据code判断是否成功
		request.setAttribute("name", phonenum);
		request.setAttribute("code",object.getString("code"));
		request.setAttribute("message",object.getString("message"));
		Logger.getLogger(RegistedServlet.class).debug("code:"+object.getString("code")+"******message"+object.getString("message"));
		String code = (String)request.getAttribute("code");
		if("403".equals(code)){ //403表示已经注册可以直接登录
			Forward.forward(request, response,"../extweb/registed.jsp");
//			response.sendRedirect("../extweb/wxlogin.jsp");
		}else if("1".equals(code)){
			request.getSession().setAttribute("curuser", request.getAttribute("name"));
			response.sendRedirect("../extweb/bidingestate.jsp");
		}else{//注册信息不对，需重新注册
			Forward.forward(request, response,"../extweb/registed.jsp");
		}
	}
	
	public void getCode(HttpServletRequest request, HttpServletResponse response) 
			throws ClientProtocolException, IOException{
		String tel = request.getParameter("tel");
		if(tel == "" || tel == null){
			return;
		}
		StringBuffer urlpost = new StringBuffer(ExtWxConfig.APIADDR+"/api/sendsms.ashx");
		urlpost.append("?tel=").append(tel);
		//Logger.getLogger(RegistedServlet.class).debug("验证码请求信息:" + urlpost);
		HttpPost httpPost = new HttpPost(urlpost.toString());
		HttpResponse responses =  HttpClients.createDefault().execute(httpPost);
		String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
//		{"code":1,"message":"发送成功！","result":[]}
//		String jsonStr = "{\"code\":\"500\",\"message\":\"发送失败，请不要频繁发送。\"}";
		Logger.getLogger(RegistedServlet.class).debug("验证码返回信息:" + jsonStr);
		PrintWriter out = response.getWriter();  
		out.print(jsonStr.substring(jsonStr.lastIndexOf("message\":\"")+10, jsonStr.lastIndexOf("\"")));
		out.flush();
		out.close();
	}
}
