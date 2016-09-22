/*
 * DataField.java
 *
 * Created on 2005��7��10��, ����9:28
 */

package job.tot.bean;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author  ������
 */
public class DataField {
    private static Log log = LogFactory.getLog(DataField.class);
    private Hashtable fields;
    DataBean dataBean;
    /** Creates a new instance of DateField */
    public DataField() {
        fields = new Hashtable();
    }

    public DataBean getField(String fieldname) {
        return (DataBean)fields.get(fieldname);
    }
    /** ��ȡ�����ֶ�
     * @return Hashtable
     */
    public Hashtable getFields(){
        return fields;
    }

    public String getFieldValue(String fieldname) {
        dataBean = getField(fieldname);
        if(dataBean == null) {
            return null;
        } else {
            return dataBean.FieldValue;
        }
    }
    public String getString(String fieldname) {
        dataBean = getField(fieldname);
        if(dataBean == null) {
            return null;
        } else {
            return dataBean.FieldValue;
        }
    }
    public int getInt(String fieldname) {
        dataBean = getField(fieldname);
        if(dataBean == null) {
            return 0;
        } else {
            return Integer.parseInt(dataBean.FieldValue);
        }
    }
    
    public float getFloat(String fieldname) {
        dataBean = getField(fieldname);
        if(dataBean == null) {
            return 0;
        } else {
            return Float.parseFloat(dataBean.FieldValue);
        }
    }

    public void setField(String fieldname, String fieldvalue, int fieldtype) {
        fields.put(fieldname, new DataBean(fieldname, fieldvalue, fieldtype));
    }

    public void clear() {
        try {
            if(!fields.isEmpty()) {
                fields.clear();
            }
        } catch(Exception e) {
            log.error("DataField Clear Error:",e);
        }
    }

    public int getLength() {
        return fields.size();
    }
    
}
