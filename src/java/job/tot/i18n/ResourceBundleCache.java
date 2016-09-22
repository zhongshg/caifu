/*
 * ResourceBundleCache.java
 *
 * Created on 2007��1��24��, ����10:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package job.tot.i18n;
import java.text.MessageFormat;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author Administrator
 */
public class ResourceBundleCache {
    private static Log log = LogFactory.getLog(ResourceBundleCache.class);
    
    private String bundleName = null;

    private Hashtable cacheResourceBundle = new Hashtable();

    public ResourceBundleCache(String bundleName) {
        if (bundleName == null) {
            throw new IllegalArgumentException("bundleName cannot be null."); 
        }
        this.bundleName = bundleName;
    }

    /**
     * Get the ResourceBundle from a locale, if locale is null, then get from locale English
     * @param locale Locale
     * @return ResourceBundle
     */
    public ResourceBundle getResourceBundle(Locale locale) {
        if (locale == null) {
            locale = Locale.ENGLISH;
        }
        ResourceBundle resourceBundle = (ResourceBundle)cacheResourceBundle.get(locale);
        if (resourceBundle == null) {
            try {
                resourceBundle = ResourceBundle.getBundle(bundleName, locale);
            } catch (MissingResourceException e) {
                log.error("Cannot load the ResourceBundle = " + bundleName);
                
                log.info("Using EmptyResourceBundle because cannot load ResourceBundle = " + bundleName);
                resourceBundle = new EmptyResourceBundle();
            }
            cacheResourceBundle.put(locale, resourceBundle);
        }
        return resourceBundle;
    }

    public String getString(Locale locale, String key) {
        ResourceBundle resourceBundle = getResourceBundle(locale);
        try {
            return resourceBundle.getString(key);
        } catch (Exception ex) {
            return "[[" + key + "]]";
        }
    }

    public String getString(Locale locale, String key, Object[] args) {
        ResourceBundle resourceBundle = getResourceBundle(locale);
        try {
            String message = resourceBundle.getString(key);

            MessageFormat formatter = new MessageFormat(message);
            if (locale != null) {
                formatter.setLocale(locale);
            }
            message = formatter.format(args);
            return message;
        } catch (Exception ex) {
            return "[[[" + key + "]]]";
        }
    }
}
