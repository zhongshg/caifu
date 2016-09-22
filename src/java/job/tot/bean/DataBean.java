/*
 * DataBean.java
 *
 * Created on 2006��7��25��, ����4:23
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.bean;

/**
 *
 * @author ������
 */
public class DataBean {
    public String FieldName;
    public String FieldValue;
    public int DataType;

    public DataBean(String f_name, String f_value, int f_type) {
        FieldName =f_name;
        FieldValue =f_value;
        DataType = f_type;
    }
    
}
