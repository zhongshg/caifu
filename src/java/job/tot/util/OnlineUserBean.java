/*
 * OnlineUserBean.java
 *
 * Created on 2007��6��21��, ����2:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package job.tot.util;

/**
 *
 * @author Administrator
 */
public class OnlineUserBean {
    private String memberName = "";
    private boolean isvisible =true;
    private int newMessageCount = 0;
    /** Creates a new instance of OnlineUserBean */
    public OnlineUserBean() {
    }
    public OnlineUserBean(String membername,boolean isvisible,int newmessagecount){
        this.memberName=membername;
        this.isvisible=isvisible;
        this.newMessageCount=newmessagecount;
    }
    public String getMemberName() {
        return memberName;
    }
    public void setMemberName(String membername){
        this.memberName=membername;
    }
    public boolean getIsVisible() {
        // @todo: temp implementation
        return this.isvisible;
    } 
    public void setIsVisible(boolean isvisible) {
        // @todo: temp implementation
        this.isvisible=isvisible;
    } 
    public int getNewMessageCount(){
        return this.newMessageCount;
    }
    public void setNewMessageCount(int newmsgcount){
        this.newMessageCount=newmsgcount;
    }
}
