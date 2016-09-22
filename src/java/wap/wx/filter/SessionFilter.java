/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.filter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class SessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        if (null != request.getSession().getAttribute("users") || -1 != request.getQueryString().indexOf("method=login") || -1 != request.getQueryString().indexOf("method=checkIdentity") || -1 != request.getQueryString().indexOf("method=checkIdentity") || -1 != request.getQueryString().indexOf("method=checkPassword")) {
            chain.doFilter(request, response);
        } else {
            PrintWriter out = response.getWriter();
            out.print("<script>parent.location.href='" + request.getContextPath() + "/index.jsp?message=0';</script>");
            out.close();
//            response.sendRedirect(request.getContextPath() + "/index.jsp?message=0");
        }
    }

    @Override
    public void destroy() {
    }
}
