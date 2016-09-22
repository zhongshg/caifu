/*
 * CountCache.java
 *
 * Created on 2006��10��18��, ����5:01
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.count;
import java.util.*;
/**
 *
 * @author Administrator
 */
public class CountCache {
    public static LinkedList list=new LinkedList(); 
    /** Creates a new instance of CountCache */
    public CountCache() {
    }
    public  static void add(CountBean cb){
        if(cb!=null){
            list.add(cb);
        }
    }
}
