/*
 * TimerUtil.java
 *
 * Created on 2006��12��6��, ����3:51
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.util;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class TimerUtil {

    // static variable
    private static Log log = LogFactory.getLog(TimerUtil.class);

    // static variable
    private static TimerUtil instance = null;

    // static variable
    private static boolean isCanceled = false;

    // instance variables
    private Timer timer = null;

    // private constructor will prevent any instatiation
    private TimerUtil() {
        log.debug("TimerUtil is instantiated.");
        timer = new Timer();
    }

    private void reloadTimer() {
        log.info("Reload Timer in TimerUtil.");
        if (!isCanceled) {
            timer.cancel(); // Cancel the errored timer
            timer = new Timer();
        }
    }

    /**
     * This static method is used to get the Singleton instance of TimerUtil
     * @return the singleton instance of TimerUtil
     */
    public static synchronized TimerUtil getInstance() {
        if (instance == null) {
            instance = new TimerUtil();
        }
        return instance;
    }

    public void cancel() {
        isCanceled = true;
        timer.cancel();
    }

    public void schedule(TimerTask task, Date firstTime, long period) {
        if (!isCanceled) {
            try {
                timer.schedule(task, firstTime, period);
            } catch (IllegalStateException ex) {
                log.error("Cannot schedule task!", ex);
                reloadTimer();
            }
        }
    }

    public void schedule(TimerTask task, Date time) {
        if (!isCanceled) {
            try {
                timer.schedule(task, time);
            } catch (IllegalStateException ex) {
                log.error("Cannot schedule task!", ex);
                reloadTimer();
            }
        }
    }

    public void schedule(TimerTask task, long delay) {
        if (!isCanceled) {
            try {
                timer.schedule(task, delay);
            } catch (IllegalStateException ex) {
                log.error("Cannot schedule task!", ex);
                reloadTimer();
            }
        }
    }

    public void schedule(TimerTask task, long delay, long period) {
        if (!isCanceled) {
            try {
                timer.schedule(task, delay, period);
            } catch (IllegalStateException ex) {
                log.error("Cannot schedule task!", ex);
                reloadTimer();
            }
        }
    }

    public void scheduleAtFixedRate(TimerTask task, Date firstTime, long period) {
        if (!isCanceled) {
            try {
                timer.schedule(task, firstTime, period);
            } catch (IllegalStateException ex) {
                log.error("Cannot schedule task!", ex);
                reloadTimer();
            }
        }
    }

    public void scheduleAtFixedRate(TimerTask task, long delay, long period) {
        if (!isCanceled) {
            try {
                timer.schedule(task, delay, period);
            } catch (IllegalStateException ex) {
                log.error("Cannot schedule task!", ex);
                reloadTimer();
            }
        }
    }
}
