/*
 * HtmlParse.java
 *
 * Created on 2007��1��15��, ����8:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package job.tot.util;
import java.util.regex.*;
/**
 *
 * @author Administrator
 */
public class HtmlParse {
    
    /** Creates a new instance of HtmlParse */
    public HtmlParse() {
    }
    public static String[] getImgs(String html){
        StringBuffer sb=new StringBuffer(512);
        int i=0;
        Pattern p=Pattern.compile("src=\\s*([^>]*)\\.[jpg|gif|jpeg|bmp|png]*");//"src=.*http://.*?\\.[gif|jpg|bmp|jpeg]\\s.?"
        Matcher m=p.matcher(html);
        while(m.find()){
            String te=m.group();
            te=te.replaceAll("src=","");
            te=te.replaceAll("\\\"","");
            if(te!=null)
                sb.append(te).append("@@");
        }
        return sb.toString().split("@@");
    }
    public static String[] getUrls(String html){
        StringBuffer sb=new StringBuffer(512);
        Pattern p=Pattern.compile("http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?");
        Matcher m=p.matcher(html);
        while(m.find()){
            String s=m.group();
            if(s!=null)
                sb.append(s).append("@@");
        }
        return sb.toString().split("@@");
    }
}
