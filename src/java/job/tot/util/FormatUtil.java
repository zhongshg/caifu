/*
 * FormatUtil.java
 *
 * Created on 2008��2��25��, ����2:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package job.tot.util;
import java.text.*;
import java.util.*;
import java.math.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author Administrator
 */
public class FormatUtil {
    private static Log log = LogFactory.getLog(FormatUtil.class);
    /** Creates a new instance of FormatUtil */
    public FormatUtil() {
    }

    public static String formatNumber(double d,String pattern,Locale l){
        String s = "";
        try{
            DecimalFormat nf = (DecimalFormat) NumberFormat.getInstance(l);
            nf.applyPattern(pattern);
            s = nf.format(d);
            
        }catch(Exception e){
            e.printStackTrace();
            log.error(" formatNumber is error!");
        }
        return s;
        
    }

    public static String formatNumber(double d,String pattern){
        return formatNumber(d,pattern,Locale.getDefault());
    }

    public static String formatCurrency(double d,String pattern,Locale l){
        String s = "";
        try{
            DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance(l);
            nf.applyPattern(pattern);
            s = nf.format(d);
            
        }catch(Exception e){
            e.printStackTrace();
            log.error(" formatNumber is error!");
        }
        return s;
        
    }

    public static String formatCurrency(double d,String pattern){
        return formatCurrency(d,pattern,Locale.getDefault());
    }

    public static String formatCurrency(double d){
        String s = "";
        try{
            DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance();
            s = nf.format(d);
            
        }catch(Exception e){
            e.printStackTrace();
            log.error(" formatNumber is error!");
        }
        return s;
        
    }
    

    public static String formatPercent(double d,String pattern,Locale l){
        String s = "";
        try{
            DecimalFormat df = (DecimalFormat)NumberFormat.getPercentInstance(l);
            df.applyPattern(pattern);
            s = df.format(d);
        }catch(Exception e){
            log.error("formatPercent is error!");
        }
        return s;
    }

    public static String formatPercent(double d,String pattern){
        return formatPercent(d,pattern,Locale.getDefault());
    }

    public static String formatPercent(double d){
        String s = "";
        try{
            DecimalFormat df = (DecimalFormat)NumberFormat.getPercentInstance();
            s = df.format(d);
        }catch(Exception e){
            log.error("formatPercent is error!");
        }
        return s;
    }
    

    public static String numberFormat(BigDecimal bd, String format) {
        
        if (bd == null || "0".equals(bd.toString())) {
            return "";
        }
        
        DecimalFormat bf = new DecimalFormat(format);
        return bf.format(bd);
    }
    
    public static void main(String[] args) {
        String s = formatCurrency(11123.89343,"$##,##.000");
        System.out.println(s);
    }
    
    
}
