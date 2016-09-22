/*
 * RequestUtil.java
 *
 * Created on 2006��7��25��, ����4:13
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.util;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 *
 * @author Administrator
 */
public class RequestUtil {
    
    /** Creates a new instance of RequestUtil */
    public RequestUtil() {
    }
    //get int value
    public static int getInt(HttpServletRequest request,String parameter){
        int tempInt=0;
        if(request.getParameter(parameter)!=null){
            tempInt=Integer.parseInt(request.getParameter(parameter));
        }
        return tempInt;
    }
    public static long getLong(HttpServletRequest request,String parameter){
        long tempInt=0;
        if(request.getParameter(parameter)!=null){
            tempInt=Long.parseLong(request.getParameter(parameter));
        }
        return tempInt;
    }
    /*
     *get parameter and covert to float type
     */
    public static float getFloat(HttpServletRequest request,String parameter){
        float tempFloat=0;
        if(request.getParameter(parameter)!=null){
            tempFloat=Float.parseFloat(request.getParameter(parameter));
        }
        return tempFloat;
    }
    /*
     *get parameter and covert to double type
     */
    public static Double getDouble(HttpServletRequest request,String parameter){
        Double tempDouble=0.0;
        if(request.getParameter(parameter)!=null){
            tempDouble=Double.parseDouble(request.getParameter(parameter));
        }
        return tempDouble;
    }
    //get string value
    public static String getString(HttpServletRequest request,String parameter){
        String tempString=null;
        if(request.getParameter(parameter)!=null){
            tempString=request.getParameter(parameter);            
        }        
        return tempString;
    }
     /*
     * is out send
     */
    public static boolean isInsideSend(HttpServletRequest request){
        boolean ret=false;
        String httpReferer=request.getHeader("Referer");
        if(httpReferer==null)
            return false;
        String serverName=request.getServerName();
        String fromServer=httpReferer.substring(7,serverName.length()+7);
        ret=fromServer.equalsIgnoreCase(serverName);
        return ret;
    }
}
