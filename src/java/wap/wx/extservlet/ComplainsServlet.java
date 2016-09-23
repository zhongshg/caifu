package wap.wx.extservlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import wap.wx.extutils.ExtWxConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

@WebServlet(name = "ComplainsServlet", urlPatterns = { "/extservlet/ComplainsServlet" })
public class ComplainsServlet extends ExtBaseServlet {

	String path = null;
	public void complains(HttpServletRequest request,
			HttpServletResponse response) throws ClientProtocolException,
			IOException, Base64DecodingException {
		path = request.getServletContext().getRealPath("/");
		StringBuffer json = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				json.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObj = JSON.parseObject(json.toString());

		if (jsonObj != null) {
			String ukey = (String) jsonObj.get("ukey");
			String reptype = (String) jsonObj.get("reptype");
			String saywhat = (String) jsonObj.get("saywhat");
			JSONArray temppic = (JSONArray) jsonObj.get("pict");
			String pic = null;
			FileBody images = null;
			if(temppic!=null && temppic.size()>0){
				for (int i = 0; i < temppic.size(); i++) {
					pic = temppic.getString(i);
				}
				pic = pic.substring(22);
				images = new FileBody(getPic(pic,ukey),ContentType.MULTIPART_FORM_DATA);
			}
			StringBuffer urlpost = new StringBuffer(
					ExtWxConfig.APIADDR+"/api/post_addSuggestionInfo.ashx");
			//Logger.getLogger(AddRepairServlet.class).debug("投诉请求信息:" + urlpost);
			HttpPost httpPost = new HttpPost(urlpost.toString());
			StringBody bukey = new StringBody(ukey,ContentType.MULTIPART_FORM_DATA.withCharset("UTF-8"));
			StringBody breptype = new StringBody(reptype,ContentType.MULTIPART_FORM_DATA.withCharset("UTF-8"));
			StringBody bsaywhat = new StringBody(saywhat,ContentType.MULTIPART_FORM_DATA.withCharset("UTF-8"));
			
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.STRICT);
			builder.addPart("ukey", bukey).addPart("type", breptype)
			.addPart("content", bsaywhat);
			if(images!=null) builder.addPart("images", images);
			httpPost.setEntity(builder.build());

			HttpResponse responses = HttpClients.createDefault().execute(httpPost);
			String jsonStr = EntityUtils.toString(responses.getEntity(),"utf-8");
			Logger.getLogger(AddRepairServlet.class).debug("投诉建议返回信息:" + jsonStr);
			JSONObject object = JSON.parseObject(jsonStr);
			// 返回json数据根据code判断是否成功
			request.setAttribute("code", object.getString("code"));
			String message = object.getString("message");
			request.setAttribute("message", message);
			PrintWriter out = response.getWriter();
			out.print(message);
			out.flush();
			out.close();
		}
	}

	private File getPic(String picStr,String ukey) {
		if (picStr == null) // 图像数据为空
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(picStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			//检查图片暂存路径是否存在，不存在就创建
			String filepath = path+"temps\\";
			File filep = new File(filepath);
			if(!filep.exists()) filep.mkdir();
			// 生成png图片
			String imgFilePath = path+"temps/"+ukey+".png";//新生成的图片路径
			Logger.getLogger(AddRepairServlet.class).debug("图片保存路径:" + imgFilePath);
			File pic = new File(imgFilePath);
			if(pic.exists()) {
				pic.delete();
			}
			pic.createNewFile();
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return pic;
		} catch (Exception e) {
			return null;
		}
	}
}
