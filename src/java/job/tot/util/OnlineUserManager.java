/*
 * OnlineUserListener.java
 *
 * Created on 2007��6��21��, ����2:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package job.tot.util;
import java.util.*;
import java.sql.*;
import java.net.*;
/**
 *
 * @author Administrator
 */
public class OnlineUserManager {
    private static OnlineUserManager instance = new OnlineUserManager();
    /** Creates a new instance of OnlineUserListener */
    private static Map userMap = new TreeMap();
    private long timeOfLastRemoveAction = 0;    

    private OnlineUserManager() {
    }

    public static OnlineUserManager getInstance() {
        return instance;
    }
    public static OnlineUserBean getOnlineUser(String sessionID) {

        return (OnlineUserBean)userMap.get(sessionID);
    }

    public synchronized void setOnlineUser(String sessionID, OnlineUserBean user) {

        if (null == user) {
            userMap.remove(sessionID);
        } else {
            userMap.put(sessionID, user);
        }
    }
    public synchronized boolean isUserOnline(String username) {

        Collection collection = userMap.values();
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            OnlineUserBean onlineUser = (OnlineUserBean)iterator.next();
            String currentUser = onlineUser.getMemberName();
            if (username.equalsIgnoreCase(currentUser)) {
                return true;
            }
        }
        return false;
    }
    public static Collection getOnlineUsers(){
        Collection collection = userMap.values();
        return collection;
    } 
}
