/*
 * SmallImageService.java
 *
 * Created on 2008��1��21��, ����11:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package job.tot.image;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 *
 * @author Administrator
 */
public class SmallImageService {
    
    /** Creates a new instance of SmallImageService */
    public SmallImageService() {
    }
    public static boolean isValid(HttpServletRequest request,String str){
        boolean val=false;
        HttpSession session=request.getSession();
        if(session.getAttribute("totcms_vertify")!=null){
            String totcms_vertify=(String)session.getAttribute("totcms_vertify");
            if(str!=null && str.equalsIgnoreCase(totcms_vertify)){
                val=true;
            }
            else{
                val=false;
            }
        }
        return val;
    }
}
