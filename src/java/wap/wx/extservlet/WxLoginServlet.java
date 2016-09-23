package wap.wx.extservlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import wap.wx.extutils.ExtWxConfig;
import wap.wx.util.Forward;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@WebServlet(name = "WxLoginServlet", urlPatterns = { "/extservlet/WxLoginServlet" })
public class WxLoginServlet extends ExtBaseServlet {

	public void login(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pin = request.getParameter("pin").trim();
		String password = request.getParameter("txt_pwd").trim();
		StringBuffer urlpost = new StringBuffer(ExtWxConfig.APIADDR+"/api/UserLogin.ashx");
		urlpost.append("?name=").append(pin).append("&pwd=").append(password);
		//Logger.getLogger(WxLoginServlet.class).debug("用户登录请求信息:" + urlpost);
		HttpPost httpPost = new HttpPost(urlpost.toString());
		HttpResponse responses = HttpClients.createDefault().execute(httpPost);
		String jsonStr = EntityUtils.toString(responses.getEntity(), "utf-8");
		Logger.getLogger(WxLoginServlet.class).debug("用户登录返回信息:" + jsonStr);
		JSONObject object = JSON.parseObject(jsonStr);
		//返回json数据根据code判断是否成功
		String code = object.getString("code");
		request.setAttribute("code",code);
		request.setAttribute("message", object.getString("message"));
		if("500".equals(code)){//密码错误
			Forward.forward(request, response, "/extweb/wxlogin.jsp");
		}else if("1".equals(code)){ //1表示成功
			request.getSession().setAttribute("curuser", pin);
			JSONArray results = object.getJSONArray("result");
			JSONObject jo = (JSONObject) results.get(0);
			if(jo.getIntValue("xmid")==0) {//判断用户是否绑定房产
				Forward.forward(request, response, "../extweb/bidingestate.jsp");
			}
			request.getSession().setAttribute("json", results);
//			Forward.forward(request, response, "/extweb/addrepair.jsp");//"/extweb/addrepair.jsp"
			response.sendRedirect("../extweb/index.jsp");
		}else if("505".equals(code)){
//			response.sendRedirect("../extweb/wxlogin.jsp");
			Forward.forward(request, response, "../extweb/wxlogin.jsp");
		}else{//不存在该用户
			response.sendRedirect("../extweb/registed.jsp");
//			Forward.forward(request, response, "/extweb/registed.jsp");
		}
	}


}
