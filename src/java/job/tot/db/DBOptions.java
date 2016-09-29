/*
 * DBOptions.java
 *
 * Created on 2006��7��25��, ����11:22
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.db;
import java.io.File;

import job.tot.xml.DOM4JConfiguration;
import job.tot.util.FileUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class DBOptions {

    private static Log log = LogFactory.getLog(DBOptions.class);

    private static final String OPTION_FILE_NAME = "totgb.xml";

    //default values
    boolean useDatasource   = false;
    // MUST NOT refer to DBUtils, or we will get an infinite recurse
    int databaseType        = 0; //DATABASE_UNKNOWN

    // if useDatasource = true
    String datasourceName   = "";

    // if useDatasource = false
    String driverClassName  = "org.gjt.mm.mysql.Driver";
    String databaseURL      = "jdbc:mysql://222.124.43.155/caifu?useUnicode=true&characterEncoding=utf-8";
    String databaseUser     = "test";
    String databasePassword = "123456";
    int maxConnection       = 20;
    int maxTimeToWait       = 2000;// 2 seconds
    int minutesBetweenRefresh = 30;// 30 minutes

    private DBOptions() {
        try {
            String strPathName = FileUtil.getServletClassesPath();
            String configFilename = strPathName + OPTION_FILE_NAME;
            DOM4JConfiguration conf = new DOM4JConfiguration(new File(configFilename));
            useDatasource = conf.getBoolean("dboptions.use_datasource", false);
            databaseType = conf.getInt("dboptions.database_type", 0);

            if (useDatasource) {
                datasourceName = conf.getString("dboptions.datasource_name");
            } else {
                driverClassName = conf.getString("dboptions.driver_class_name", driverClassName);
                databaseURL = conf.getString("dboptions.database_url", databaseURL);
                databaseUser = conf.getString("dboptions.database_user", databaseUser);
                databasePassword = conf.getString("dboptions.database_password",databasePassword);

                maxConnection = conf.getInt("dboptions.max_connection", maxConnection);
                maxTimeToWait = conf.getInt("dboptions.max_time_to_wait", maxTimeToWait);

                minutesBetweenRefresh = conf.getInt("dboptions.minutes_between_refresh", minutesBetweenRefresh);
                if (minutesBetweenRefresh < 1) {
                    minutesBetweenRefresh = 1; //min is 1 minute
                }
            }
        } catch (Exception e) {
            String message = "net.myvietnam.mvncore.db.DBOptions: Can't read the configuration file: '" + OPTION_FILE_NAME + "'. Make sure the file is in your CLASSPATH";
            log.error(message, e);
        }
    } //constructor
}