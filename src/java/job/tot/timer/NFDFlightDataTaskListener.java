package job.tot.timer;

import javax.servlet.ServletContextEvent;

public class NFDFlightDataTaskListener implements  javax.servlet.ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
	
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
	new TimerManager();
    }

}