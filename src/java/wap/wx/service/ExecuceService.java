/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.service;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import wap.wx.dao.AreaproxylogsDAO;
import wap.wx.dao.SubscriberDAO;

/**
 *
 * @author Administrator
 */
public class ExecuceService extends TimerTask {

    private List<Map<String, String>> wxsList = null;
    private SubscriberDAO subscriberDAO = new SubscriberDAO();
    private AreaproxylogsDAO areaproxylogsDAO = new AreaproxylogsDAO();

    @Override
    public void run() {
        String result = PublicService.modOrderAuto(wxsList, subscriberDAO, areaproxylogsDAO);
        System.out.println("分销自动更新：" + result);
    }
}
