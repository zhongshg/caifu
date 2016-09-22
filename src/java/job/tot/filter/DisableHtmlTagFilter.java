/*
 * DisableHtmlTagFilter.java
 *
 * Created on 2006��7��25��, ����11:24
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.filter;

/**
 *
 * @author Administrator
 */
public final class DisableHtmlTagFilter {
    
    private DisableHtmlTagFilter() { //prevent instantiation
    }
    
    public static String filter(String input) {
        if (input == null) {
            return null;
        }
        
        char[] s = input.toCharArray();
        int length = s.length;
        StringBuffer ret = new StringBuffer(length + 100);// add more room to the result String
        
        for (int i = 0; i < length; i++) {
            if (s[i] == '<') {
                ret.append("&lt;");
            } else if (s[i] == '>') {
                ret.append("&gt;");
            } else if (s[i] == '&') {
                // this hack the escape for unicode character, eg : &2345;
                if ( ((i + 3) < length) &&
                        (s[i+1] == '#') &&
                        (s[i+2]>='0'&&s[i+1]<='9') &&
                        (s[i+3]>='0'&&s[i+2]<='9') ) {
                    ret.append(s[i]);
                    // hack &lt; (dont escape this char more than once)
                } else if ( ((i + 3) < length) &&
                        (s[i+1] == 'l') &&
                        (s[i+2] == 't') &&
                        (s[i+3] == ';') ) {
                    ret.append(s[i]);
                    // hack &gt; (dont escape this char more than once)
                } else if ( ((i + 3) < length) &&
                        (s[i+1] == 'g') &&
                        (s[i+2] == 't') &&
                        (s[i+3] == ';') ) {
                    ret.append(s[i]);
                    // hack &amp; (dont escape this char more than once)
                } else if ( ((i + 4) < length) &&
                        (s[i+1] == 'a') &&
                        (s[i+2] == 'm') &&
                        (s[i+3] == 'p') &&
                        (s[i+4] == ';') ) {
                    ret.append(s[i]);
                    // hack &quot; (dont escape this char more than once)
                } else if ( ((i + 5) < length) &&
                        (s[i+1] == 'q') &&
                        (s[i+2] == 'u') &&
                        (s[i+3] == 'o') &&
                        (s[i+4] == 't') &&
                        (s[i+5] == ';') ) {
                    ret.append(s[i]);
                } else {
                    ret.append("&amp;");
                }
            } else if (s[i] == '"') {
                ret.append("&quot;");
            } else {
                ret.append(s[i]);
            }
        }// for
        return ret.toString();
    }
}
