/*
 * SmallImageCode.java
 *
 * Created on 2008锟斤拷1锟斤拷10锟斤拷, 锟斤拷锟斤拷9:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package job.tot.image;

import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import javax.imageio.*;


/**
 *
 * @author Administrator
 */
public class SmallImageCode extends HttpServlet {
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);       
        HttpSession session=request.getSession();
        // 锟斤拷锟节达拷锟叫达拷锟斤拷图锟斤拷
        int width=62, height=20;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        // 锟斤拷取图锟斤拷锟斤拷锟斤拷锟斤拷
        Graphics g = image.getGraphics();
        
        //锟斤拷锟斤拷锟斤拷锟斤拷
        Random random = new Random();
        
        // 锟借定锟斤拷锟斤拷色
        //g.setColor(getRandColor(240,250));
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        
        //锟借定锟斤拷锟斤拷
        g.setFont(new Font("Arial",Font.ITALIC,18));
        
        //锟斤拷锟竭匡拷
        //g.setColor(new Color());
        //g.drawRect(0,0,width-1,height-1);
        
        
        // 锟斤拷锟斤拷锟斤拷15锟斤拷锟斤拷锟斤拷撸锟绞雇硷拷锟斤拷械锟斤拷锟街わ拷氩伙拷妆锟斤拷锟斤拷锟斤拷锟斤拷探锟解到
        g.setColor(getRandColor(160,180));
        for (int i=0;i<15;i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(48);
            int yl = random.nextInt(10);
            g.drawLine(x,y,x+xl,y+yl);
        }
        
        // 取锟斤拷锟斤拷锟斤拷锟斤拷锟街わ拷锟?4位锟斤拷锟斤拷)
        String sRand="";
        for (int i=0;i<4;i++){
            String rand=String.valueOf(random.nextInt(10));
            sRand+=rand;
            // 锟斤拷锟斤拷证锟斤拷锟斤拷示锟斤拷图锟斤拷锟斤拷
            g.setColor(new Color(20+random.nextInt(230),20+random.nextInt(110),20+random.nextInt(230)));//锟斤拷锟矫猴拷锟斤拷锟?锟斤拷锟斤拷色锟斤拷同锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷为锟斤拷锟斤拷太锟接斤拷锟斤拷锟斤拷只锟斤拷直锟斤拷锟斤拷锟?
            //g.setColor(Color.blue);
            g.drawString(rand,13*i+6,16);
        }
        
        // 锟斤拷锟斤拷证锟斤拷锟斤拷锟絊ESSION
        session.setAttribute("totcms_vertify",sRand);
        // 图锟斤拷锟斤拷效
        g.dispose();
        ServletOutputStream responseOutputStream =response.getOutputStream();
        // 锟斤拷锟酵硷拷锟揭筹拷锟?
        ImageIO.write(image, "JPEG", responseOutputStream);

        //锟斤拷锟铰关憋拷锟斤拷锟斤拷锟斤拷
        responseOutputStream.flush();
        responseOutputStream.close();
    }
    Color getRandColor(int fc,int bc){//锟斤拷围锟斤拷锟斤拷锟斤拷锟斤拷色
        Random random = new Random();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "tot cms certified code";
    }
    // </editor-fold>
    
}
