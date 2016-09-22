/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wap.wx.dao.WxsDAO;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "BaseServlet", urlPatterns = {"/BaseServlet"})
public class BaseServlet extends HttpServlet {

    public static Map<String, String> guid;//全局唯一标识符，内容划分依据（用户/微信）
    public static Map<String, String> user;
    public static Map<String, String> wx;
    private WxsDAO wxsDAO = new WxsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        全局变量区初始化
        user = (Map<String, String>) request.getSession().getAttribute("users");
        if (null != user) {
            wx = wxsDAO.getWx(user);
            if (null == wx.get("id")) {
                wx.put("id", "0");
            }

            //微信控制第三方程序共享数据
            request.getSession().setAttribute("wx", wx);
        }
        guid = wx;//按微信划分内容
        /*
         * 特殊内容（关注者、关注者明细、上传素材、发送客服、群发、值【中奖记录，表单记录】）按微信区分
         */

        String methodName = request.getParameter("method");
        try {
            Method method = this.getClass().getDeclaredMethod(
                    methodName,
                    new Class[]{HttpServletRequest.class,
                        HttpServletResponse.class});
            method.invoke(this, new Object[]{request, response});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}
