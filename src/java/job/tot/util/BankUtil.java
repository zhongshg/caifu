/*
 * BankUtil.java
 *
 * Created on 2008��2��26��, ����9:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package job.tot.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Administrator
 */
public class BankUtil {
    public static HashMap banks=new HashMap();
    /** Creates a new instance of BankUtil */
    static {
        banks.put(new Integer(1),"�й�������");
        banks.put(new Integer(2),"��������");
        banks.put(new Integer(3),"�й�ũҵ����");
        banks.put(new Integer(4),"�й�������");
        banks.put(new Integer(5),"��ͨ����");
        banks.put(new Integer(6),"�㶫��չ����");
        banks.put(new Integer(7),"��ҵ����");
        banks.put(new Integer(8),"���ڷ�չ����");
        banks.put(new Integer(9),"�й���������");
        banks.put(new Integer(10),"�Ϻ��ֶ���չ����");
        banks.put(new Integer(11),"��������");
        banks.put(new Integer(12),"�й�����");
    }
    public static String getSelect(int cid){
        StringBuffer sb=new StringBuffer();
        Iterator iterator=banks.keySet().iterator();
        while(iterator.hasNext()){
            Integer id=(Integer)iterator.next();
            String val=(String)banks.get(id);
            sb.append("<option value=\"");
            sb.append(id);
            sb.append("\"");
            if(id==cid)
                sb.append(" selected=\"selected\"");
            sb.append(">");
            sb.append(val);
            sb.append("</option>\n");
        }
        return sb.toString();
    }
    public static String getItem(int index) {
        return (String) banks.get(new Integer(index));
    }
    
    public static void main(String[] agrs) {
        Iterator iterator=banks.keySet().iterator();
        while(iterator.hasNext()){
            Integer id=(Integer)iterator.next();
            String val=(String)banks.get(id);
            System.out.print(id+":"+val+";");
        }
        
    }
}
