/*
 * StringUtils.java
 *
 * Created on 2005��12��29��, ����1:02
 */

package job.tot.util;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.regex.*;

// Referenced classes of package net.lybbs.util:
//            Constants

public class StringUtils {
    
    private static Log log;
    public static final String EN = "ISO-8859-1";
    public static final String CN = "GBK";
    
    public StringUtils() {
    }
    
    public static String ISO2GB(String isoStr)
    throws UnsupportedEncodingException {
        return new String(isoStr.getBytes("ISO-8859-1"), "GBK");
    }
    
    public static String GB2ISO(String gbStr)
    throws UnsupportedEncodingException {
        return new String(gbStr.getBytes("GBK"), "ISO-8859-1");
    }
    
    public static String getIP(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        return ip != null ? ip : "";
    }
    public static String subStr(String source,String from,String to){
        if(source == null) {
            return "";
        }else{
            int start=source.indexOf(from);
            if(start!=-1){
                start=start+from.length();
            }
            int end=source.indexOf(to,start);            
            if(start!=-1 && end!=-1){
                return source.substring(start,end);
            }else{
                return "";
            }
        }
    }
    public static String replaceChar(String source, char oldChar, char newChar) {
        if(source == null) {
            return "";
        } else {
            return source.replace(oldChar, newChar);
        }
    }
    
    public static String replaceString(String source, String oldString, String newString) {
        if(source == null || oldString == null || newString == null) {
            return "";
        } else {
            return replace(source, oldString, newString);
        }
    }
    
    public static String[] split(String source, String regex) {
        if(source == null || source.equals("")) {
            return new String[0];
        } else {
            return source.split(regex);
        }
    }
    
    private static String replace(String source, String oldString, String newString) {
        StringBuffer output = new StringBuffer();
        int lengthOfSource = source.length();
        int lengthOfOld = oldString.length();
        int posStart;
        int pos;
        for(posStart = 0; (pos = source.indexOf(oldString, posStart)) >= 0; posStart = pos + lengthOfOld) {
            output.append(source.substring(posStart, pos));
            output.append(newString);
        }
        
        if(posStart < lengthOfSource) {
            output.append(source.substring(posStart));
        }
        return output.toString();
    }
    
    public static String toQuoteMark(String str) {
        if(str == null || str.equals("")) {
            return str;
        }
        str = replaceString(str, "'", "&#39;");
        str = replaceString(str, "\"", "&#34;");
        str = replace(str, "\\", "&#92;");
        str = replaceString(str, "\r\n", "\n");
        str = replaceString(str, "\\n", "\n");
        return str;
    }
    
    public static String toHtml(String str) {
        if(str == null || str.equals("")) {
            return str;
        }
        str = replaceString(str, "<", "&lt;");
        str = replaceString(str, ">", "&gt;");
        return str;
    }
    
    public static String toBR(String str) {
        if(str == null || str.equals("")) {
            return str;
        }
        str = replaceString(str, "\\n", "\n");
        str = replaceString(str, "\n", "<br>\n");
        str = replaceString(str, "  ", "&nbsp;&nbsp;");
        return str;
    }
    
    public static String toSQL(String str) {
        str = replaceString(str, "\r\n", "\n");
        return str;
    }
    public static String htmlFilter(String str){
        if(str == null || str.equals("")) {
            return str;
        }
        StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch(c) {
                case 39: // '\''
                    stringbuffer.append("&#039;");
                    break;
                    
                case 34: // '"'
                    stringbuffer.append("&quot;");
                    break;
                    
                case 60: // '<'
                    stringbuffer.append("&lt;");
                    break;
                    
                case 62: // '>'
                    stringbuffer.append("&gt;");
                    break;
                    
                case 38: // '&'
                    stringbuffer.append("&amp;");
                    break;
                    
                case 32: // ' '
                    stringbuffer.append("&nbsp;");
                    break;
                    
                case 10: // '\n'
                    stringbuffer.append("<br>");
                    break;
                    
                default:
                    stringbuffer.append(c);
                    break;
            }
        }
        
        return stringbuffer.toString();
    }
    public static String base64Encode(String str) {
        if(str != null && str.length() > 0) {
            str = new String((new Base64()).encode(str.getBytes()));
        }
        return str;
    }
    
    public static String base64Decode(String str) {
        if(str != null && str.length() > 0) {
            byte buf[] = (new Base64()).decode(str.getBytes());
            str = new String(buf);
        }
        return str;
    }
    
    public static String base64Encode(byte str[]) {
        String encodeStr = "";
        if(str != null && str.length > 0) {
            encodeStr = new String((new Base64()).encode(str));
        }
        return encodeStr;
    }
    
    public static byte[] base64DecodeForByte(String str) {
        byte buf[] = (byte[])null;
        if(str != null && str.length() > 0) {
            buf = (new Base64()).decode(str.getBytes());
        }
        return buf;
    }
    
    public static String getRandomNumber(int bits, int to) {
        StringBuffer randBuffer = new StringBuffer();
        Random RANDOM = new Random();
        for(int i = 1; i <= bits; i++) {
            randBuffer.append(RANDOM.nextInt(to));
        }
        
        return randBuffer.toString();
    }
    
    public static String urlEncode(String str)
    throws EncoderException {
        URLCodec URLCODEC = new URLCodec("UTF-8");
        return URLCODEC.encode(str);
    }
    
    public static String urlDecode(String str)
    throws DecoderException {
        return str;
    }
    
    public static String getSQL(String sql, boolean hasOffset, int from, int to) {
        return getMysqlSQL(sql, hasOffset);
    }
    
    public static String getMysqlSQL(String sql, boolean hasOffset) {
        StringBuffer sb = new StringBuffer(sql.length() + 20);
        sb.append(sql).append(hasOffset ? " limit ?, ?" : " limit ?");
        return sb.toString();
    }
    
    public static String getDb2SQL(String sql, boolean hasOffset) {
        StringBuffer sb = new StringBuffer(sql.length() + 100);
        return sb.toString();
    }
    
    public static String getOracleSQL(String sql, boolean hasOffset) {
        StringBuffer sb = new StringBuffer(sql.length() + 100);
        return sb.toString();
    }
    
    public static String getMssqlSQL(String sql, boolean hasOffset, int from, int to) {
        StringBuffer sb = new StringBuffer(sql.length() + 100);
        return sb.toString();
    }
    
    private static String getRowNumber(String sql) {
        StringBuffer rownumber = new StringBuffer(50);
        return rownumber.toString();
    }
    
    private static int getAfterSelectInsertPoint(String sql) {
        int selectIndex = sql.indexOf("select");
        return selectIndex + 6;
    }
    public static String getTopic(String str,int max){
        String tempStr=null;
        tempStr=str;
        if(tempStr!=null && tempStr.length()>max){
            tempStr=tempStr.substring(0,max)+"..";
            return tempStr;
        } else{
            return tempStr;
        }
    }
    public static String removeHtml(String str){
        String tempStr=null;
        tempStr=str;
        if(tempStr==null) return "";
        Pattern pattern = Pattern.compile("<.+?>");
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()){
            tempStr=matcher.replaceAll("");
        }
        return tempStr;
    }
    public static String returnToBr(String s) {
        if(s == null || s.equals("")) {
            return s;
        }
        StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0; i <= s.length() - 1; i++) {
            if(s.charAt(i) == '\r') {
                stringbuffer = stringbuffer.append("<br>");
            } else
                if(s.charAt(i) == ' ') {
                stringbuffer = stringbuffer.append("&nbsp;");
                } else {
                stringbuffer = stringbuffer.append(s.substring(i, i + 1));
                }
        }
        
        String s1 = stringbuffer.toString();
        return s1;
    }
    
    public static String returnToHTML(String s) {
        if(s == null || s.equals("")) {
            return s;
        }
        StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0; i <= s.length() - 1; i++) {
            if(s.charAt(i) == '<') {
                stringbuffer = stringbuffer.append("&lt;");
            } else
                if(s.charAt(i) == '>') {
                stringbuffer = stringbuffer.append("&gt;");
                } else {
                stringbuffer = stringbuffer.append(s.substring(i, i + 1));
                }
        }
        
        String s1 = stringbuffer.toString();
        return s1;
    }
    static
    {
        log = LogFactory.getLog(StringUtils.class);
    }
}
