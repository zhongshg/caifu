/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wap.wx.listener;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import wap.wx.service.ExecuceService;

/**
 *
 * @author Administrator
 */
public class PublicListener implements ServletContextListener {

    private Timer timer = new Timer(true);
    private ExecuceService execuceService = new ExecuceService();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Calendar calendar = Calendar.getInstance();
        long period = 1000 * 60 * 60;// * 24;
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date firstTime = calendar.getTime();
        timer.scheduleAtFixedRate(execuceService, firstTime, period);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        timer.cancel();
    }
}
