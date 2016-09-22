/*
 * CountData.java
 *
 * Created on 2006��10��18��, ����4:44
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.count;

/**
 *
 * @author Administrator
 */
public class CountBean {
    private String countType;
    int countId;
    /** Creates a new instance of CountData */
    public CountBean() {
    }
    public void setCountType(String countTypes){
        this.countType=countTypes;
    }
    public void setCountId(int countIds){
        this.countId=countIds;
    }
    public String getCountType(){
        return countType;
    }
    public int getCountId(){
        return countId;
    }
}
