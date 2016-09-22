/*
 * OutputHtml.java
 *
 * Created on 2008��2��29��, ����5:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package job.tot.util;
import java.util.*;
import job.tot.bean.DataField;
/**
 *
 * @author Administrator
 */
public class OutputHtml {
    
    /** Creates a new instance of OutputHtml */
    public OutputHtml() {
    }
    public static String Write(Collection collection,String url,String id,String value){
        StringBuffer sb=new StringBuffer(1024);
        ArrayList list=(ArrayList)collection;
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            DataField df=(DataField)iter.next();
            sb.append("<li><a href=\"").append(url);
            sb.append("?"+df.getFieldValue(id)+"=\">");
            sb.append(df.getFieldValue(value));
            sb.append("</a></li>\n");            
        }
        return sb.toString();
    }
}
