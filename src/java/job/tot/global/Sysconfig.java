/*
 * Sysconfig.java
 *
 * Created on 2006??7??25??, ????11:25
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.global;
import java.io.File;
import job.tot.xml.DOM4JConfiguration;
import job.tot.util.FileUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Sysconfig {

    private static Log log = LogFactory.getLog(Sysconfig.class);

    final static String OPTION_FILE_NAME = "shop.xml";
    /*<global_options> */
    private static String totask_version="Tot Job 0.1";
    public static String getTotAskVersion() {
        return totask_version;
    }
    /* user login face */
    private static String login_action_url="";
    public static String getLoginActionUrl() {
        return login_action_url;
    }
    private static String field_userid="UserId";
    public static String getFieldUserId() {
        return field_userid;
    }
    private static String field_password="UserPass";
    public static String getFieldPassWord() {
        return field_password;
    }    
    private static String cookie_name="totcms_member";
    public static String getCookieName(){
        return cookie_name;
    }
    private static String member_gate="";
    public static String getMemberGate(){
        return member_gate;
    }
    private static String member_key="";
    public static String getMemberKey(){
        return member_key;
    }
    /* <Database Options> */
    // MUST NOT refer to DBUtils, or we will get an infinite recurse ???
    private static int databaseType = 0;//DATABASE_UNKNOWN
    public static int getDatabaseType() {
        return databaseType;
    }
    private static boolean useDataSource = false;
    public static boolean isUseDataSource() {
        return useDataSource;
    }

    private static String dataSourceName = "";
    public static String getDataSourceName() {
        return dataSourceName;
    }
    
    private static String driverClassName = "org.gjt.mm.mysql.Driver";
    public static String getDriverClassName() {
        return driverClassName;
    }

    private static String databaseURL = "jdbc:mysql://222.43.124.155/caifu?useUnicode=true&characterEncoding=utf-8";
    public static String getDatabaseURL() {
        return databaseURL;
    }

    private static String databaseUser = "test";
    public static String getDatabaseUser() {
        return databaseUser;
    }

    private static String databasePassword = "123456";
    public static String getDatabasePassword() {
        return databasePassword;
    }

    private static int maxConnection = 20;
    public static int getMaxConnection() {
        return maxConnection;
    }

    /* by default, the unit of time is milisecond */
    private static int maxTimeToWait = 2000;// 2 seconds
    public static int getMaxTimeToWait() {
        return maxTimeToWait;
    }

    private static int minutesBetweenRefresh = 30;// 30 minutes
    public static int getMinutesBetweenRefresh() {
        return minutesBetweenRefresh;
    }
    /* </Database Options> */
    /* <Mail Options> */
    private static boolean useMailSource = false;
    public static boolean isUseMailSource() {
        return useMailSource;
    }

    private static String mailSourceName = "";
    public static String getMailSourceName() {
        return mailSourceName;
    }

    private static String mailServer = "mail.yourdomain";
    public static String getMailServer() {
        return mailServer;
    }

    private static int mailServerPort = 25;
    public static int getMailServerPort() {
        return mailServerPort;
    }

    private static String defaultMailFrom = "youruser@yourdomain";
    public static String getDefaultMailFrom() {
        return defaultMailFrom;
    }

    private static String mailUserName = "";// allow empty username
    public static String getMailUserName() {
        return mailUserName;
    }

    private static String mailPassword = "";
    public static String getMailPassword() {
        return mailPassword;
    }
    /* </Mail Options> */
    
   /* <Param Options> */
    private static String contextPath = "";// allow ROOT context
    public static String getContextPath() {
        return contextPath;
    }

    private static String ask_url = "http://localhost:8080";
    public static String getAskUrl() {
        return ask_url;
    }
    private static String ask_name="totcms";
    public static String getAskName() {
        return ask_name;
    }    
    private static String cookie_domain="totcms.com";
    public static String getCookieDomain(){
        return cookie_domain;
    }
    
    private static boolean enable_upload=false;
    public static boolean isEnableUpload(){
        return enable_upload;
    }
    private static String upload_photo_ext="gif,jpg,rar,doc,txt,png,bmp";
    public static String getUploadPhotoExt(){
        return upload_photo_ext;
    }
    private static int upload_photo_maxsize=200;
    public static int getUploadPhotoMaxsize(){
        return upload_photo_maxsize;
    }
    private static int max_posts_per_hour = 20;
    public static int getMaxPostsPerHour() {
        return max_posts_per_hour;
    }
    private static int max_replys_per_hour = 30;
    public static int getMaxReplysPerHour() {
        return max_replys_per_hour;
    }
    private static int default_check=0;
    public static int getDefaultCheck() {
        return default_check;
    }
     /* search index options */
    private static String indexdir="";
    public static String getIndexDir(){
        return indexdir;
    }
    private static String luceneAnalyzerClassName    = "org.apache.lucene.analysis.standard.StandardAnalyzer";
    public static String getLuceneAnalyzerClassName(){
        return luceneAnalyzerClassName;
    }
    /* the unit of the server offset is hour*/
    private static int serverHourOffset = 0; /* GMT timezone */
    public static int getServerHourOffset() {
        return serverHourOffset;
    }    
    /* <IP Options>*/
    private static String blockedIPs = "";
    public static String getBlockedIPs() {
        return blockedIPs;
    }
    /* </IP Options>*/
    
    /* <score options> */
    private static int postpernum=3;
    public static int getPostPerNum() {
        return postpernum;
    }
    private static int minfetchmoney=1;
    public static int getMinFetchMoney() {
        return minfetchmoney;
    }
    private static int generalask=5;
    public static int getGeneralAskNum() {
        return generalask;
    }
    private static int mediumask=3;
    public static int getMediumAsk() {
        return mediumask;
    }
    private static int joinpernum=3;
    public static int getJoinPerNum() {
        return joinpernum;
    }
    /* </score options> */
    static {
        load();
    }

    public static void load() {
        reload();
    }   
    public static void reload() {
        String classPath = FileUtil.getServletClassesPath();        
        String configFilename = classPath + OPTION_FILE_NAME;        
        try {
            DOM4JConfiguration conf = new DOM4JConfiguration(new File(configFilename));
            totask_version=conf.getString("global_options.totask_version");
            /* user database para */
            login_action_url=conf.getString("user_login_face.login_action_url");
            field_userid=conf.getString("user_login_face.field_userid");
            field_password=conf.getString("user_login_face.field_password");   
            cookie_name=conf.getString("user_login_face.cookie_name");   
            member_gate=conf.getString("user_login_face.member_gate"); 
            member_key=conf.getString("user_login_face.member_key"); 
            /* <Database Options> */
            useDataSource = conf.getBoolean("dboptions.use_datasource", false);
            databaseType = conf.getInt("dboptions.database_type", 0);           
            if (useDataSource) {
                dataSourceName = conf.getString("dboptions.datasource_name");
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
            /* <Database Options> */            
            /* <Mail Options> */
            useMailSource = conf.getBoolean("mailoptions.use_mailsource", false);

            if (useMailSource) {
                mailSourceName = conf.getString("mailoptions.mailsource_name");
            } else {
                mailServer = conf.getString("mailoptions.mail_server", mailServer);
                defaultMailFrom = conf.getString("mailoptions.default_mail_from", defaultMailFrom);
                mailUserName = conf.getString("mailoptions.username", mailUserName);
                mailPassword = conf.getString("mailoptions.password", mailPassword);
                mailServerPort = conf.getInt("mailoptions.port", mailServerPort);
            }
            /* </Mail Options> */
            
            /* <Parameter Options> */
            contextPath = conf.getString("paramoptions.context_path", contextPath);
            if (contextPath.endsWith("/")) {
                contextPath = contextPath.substring(0, contextPath.length() - 1);
            }
            ask_url = conf.getString("paramoptions.ask_url", ask_url);
            if (ask_url.endsWith("/")) {
                ask_url = ask_url.substring(0, ask_url.length() - 1);
            }
            ask_name=conf.getString("paramoptions.ask_name", ask_name);   
            cookie_domain=conf.getString("paramoptions.cookie_domain", cookie_domain);
            enable_upload=conf.getBoolean("paramoptions.enable_upload", false);
            upload_photo_ext=conf.getString("paramoptions.upload_photo_ext", upload_photo_ext);   
            upload_photo_maxsize=conf.getInt("paramoptions.upload_photo_maxsize", upload_photo_maxsize);
            max_posts_per_hour=conf.getInt("paramoptions.max_posts_per_hour", max_posts_per_hour);
            max_replys_per_hour=conf.getInt("paramoptions.max_replys_per_hour", max_replys_per_hour);
            default_check=conf.getInt("paramoptions.default_check", default_check);
            /* </Parameter Options> */
            
            /* search index*/
            indexdir=conf.getString("searchindex.indexdir", indexdir);
            luceneAnalyzerClassName    = conf.getString("searchindex.lucene_analyzer_implementation", luceneAnalyzerClassName);
            /* <Date Time Options> */
            serverHourOffset = conf.getInt("dateoptions.server_hour_offset", serverHourOffset);
            if ((serverHourOffset < -13) || (serverHourOffset > 13)) {
                serverHourOffset = 0;
            }
            /* </Date Time Options> */           
            
            /* <IP Options>*/
            blockedIPs = conf.getString("ipoptions.blocked_ip", blockedIPs);
            /* </IP Options>*/
            
            /* <score options> */
            postpernum = conf.getInt("scoreoptions.postpernum", postpernum);
            minfetchmoney = conf.getInt("scoreoptions.minfetchmoney", minfetchmoney);
            generalask = conf.getInt("scoreoptions.generalask", generalask);
            mediumask = conf.getInt("scoreoptions.mediumask", mediumask);
            joinpernum = conf.getInt("scoreoptions.joinpernum", joinpernum);
            /* <score options> */
        } catch (Exception e) {
            String message = "global.Sysconfig: Can't read the configuration file: '" + configFilename + "'. Make sure the file is in your CLASSPATH";
            log.error(message, e);
        }
    }
}