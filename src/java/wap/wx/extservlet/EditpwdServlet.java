package wap.wx.extservlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;

@WebServlet(name = "EditpwdServlet", urlPatterns = { "/extservlet/EditpwdServlet" })
public class EditpwdServlet extends ExtBaseServlet {
	
	public void editPwd(HttpServletRequest request, HttpServletResponse response) throws ClientProtocolException, IOException {
	
	}
}
