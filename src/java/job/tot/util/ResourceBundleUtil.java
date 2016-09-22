/*
 * ResourceBundleUtil.java
 *
 * Created on 2007��1��24��, ����10:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package job.tot.util;
import java.util.Locale;
import java.util.ResourceBundle;
import job.tot.i18n.ResourceBundleCache;
/**
 *
 * @author Administrator
 */
public class ResourceBundleUtil {
    private static ResourceBundleCache ResourceBundleCache = new ResourceBundleCache("resources.totcms_i18n");

    private ResourceBundleUtil() {
    }

    public static ResourceBundle getResourceBundle(Locale locale) {
        return ResourceBundleCache.getResourceBundle(locale);
    }

    public static String getString(Locale locale, String key) {
        return ResourceBundleCache.getString(locale, key);
    }

    public static String getString(Locale locale, String key, Object[] args) {
        return ResourceBundleCache.getString(locale, key, args);
    }
}
