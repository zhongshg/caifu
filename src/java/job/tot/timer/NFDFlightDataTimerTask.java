package job.tot.timer;

import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;

public class NFDFlightDataTimerTask  extends TimerTask{
    private static Logger log = Logger.getLogger(NFDFlightDataTimerTask.class);

    @Override
    public void run() {
	try {
	    // 在这里写你要执行的内容
	    System.out.println(new Date()+"程序自动定期执行了");
	} catch (Exception e) {
	    log.info("-------------解析信息发生异常--------------");
	}
    }
}
