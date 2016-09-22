/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 *
 * @author Administrator
 */
public class WxReader {

    public static Map<String, String> getWxInfo() {
        Map<String, String> map = new HashMap<String, String>();
        ResourceBundle bundle = ResourceBundle.getBundle("wxinfo");
        Enumeration<String> enu = bundle.getKeys();
        while (enu.hasMoreElements()) {
            String key = enu.nextElement();
            map.put(key, bundle.getString(key));
        }
        return map;
    }
}
