/*
 * IpFilter.java
 *
 * Created on 2007��7��7��, ����10:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package job.tot.filter;
import job.tot.global.Sysconfig;
import job.tot.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.regexp.RE;
/**
 *
 * @author totcms
 */
public final class IpFilter {
    private static Log log = LogFactory.getLog(IpFilter.class);
    private static RE[] blockedIPs = null;
    private IpFilter() { //prevent instantiation
    }

    static {
        //IPOptions ipOptions = new IPOptions();
        //String[] blockedIPArray = StringUtil.getStringArray(ipOptions.blockedIP, ";");

        String[] blockedIPArray = StringUtils.split(Sysconfig.getBlockedIPs(), ";");
        blockedIPs = new RE[blockedIPArray.length];
        for (int i = 0; i < blockedIPArray.length; i++) {
            String currentIPRegExp = StringUtils.replaceString(blockedIPArray[i],"*", "(\\d{1,3})");
            currentIPRegExp = "^" + currentIPRegExp + "$";
            try {
                log.debug("currentIPRegExp = " + currentIPRegExp);
                blockedIPs[i] = new RE(currentIPRegExp);
            } catch (Exception ex) {
                log.error("Cannot parse the regular expression = " + currentIPRegExp, ex);
            }
        }
    }

    /**
     * Filter the IP
     * @param request
     * @return true  if the IP in this request is ok
     *         false if the IP in this request is blocked
     */
    public static boolean filter(String ip) {
        if (ip == null) {
           return false;
        }
        String checkIP =ip;

        for (int i = 0; i < blockedIPs.length; i++) {
            RE currentBlockedIP = blockedIPs[i];
            if (currentBlockedIP != null) {
                synchronized (currentBlockedIP) {
                    if (currentBlockedIP.match(checkIP)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}