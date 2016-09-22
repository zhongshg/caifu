/*
 * Ubb.java
 *
 * Created on 2006��5��17��, ����2:21
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author Administrator
 */
public class Ubb {
    private String source;
    private String ubbTags[];
    private String htmlTags[];
    /** Creates a new instance of Ubb */
    public Ubb() {
        byte byte0 = 52;
        source = new String();
        ubbTags = new String[byte0];
        htmlTags = new String[byte0];
        ubbTags[0] = "[b]";
        htmlTags[0] = "<b>";
        ubbTags[1] = "[/b]";
        htmlTags[1] = "</b>";
        ubbTags[2] = "[i]";
        htmlTags[2] = "<em>";
        ubbTags[3] = "[/i]";
        htmlTags[3] = "</em>";
        ubbTags[4] = "[quote]";
        htmlTags[4] = "<div style=\"border-style:dashed;background-color:#CCCCCC;border-width:thin;bord" +
"er-color:#999999\"><br><em>"
;
        ubbTags[5] = "[/quote]";
        htmlTags[5] = "</em><br><br></div>";
        ubbTags[6] = "[/size]";
        htmlTags[6] = "</font>";
        ubbTags[7] = "[size=6]";
        htmlTags[7] = "<font style=\"font-size:6px\">";
        ubbTags[8] = "[size=8]";
        htmlTags[8] = "<font style=\"font-size:8px\">";
        ubbTags[9] = "[size=10]";
        htmlTags[9] = "<font style=\"font-size:10px\">";
        ubbTags[10] = "[size=12]";
        htmlTags[10] = "<font style=\"font-size:12px\">";
        ubbTags[11] = "[size=14]";
        htmlTags[11] = "<font style=\"font-size:14px\">";
        ubbTags[12] = "[size=18]";
        htmlTags[12] = "<font style=\"font-size:18px\">";
        ubbTags[13] = "[size=24]";
        htmlTags[13] = "<font style=\"font-size:24px\">";
        ubbTags[14] = "[size=36]";
        htmlTags[14] = "<font style=\"font-size:36px\">";
        ubbTags[15] = "[/font]";
        htmlTags[15] = "</font>";
        ubbTags[16] = "[font=\u5B8B\u4F53]";
        htmlTags[16] = "<font face=\"\u5B8B\u4F53\">";
        ubbTags[17] = "[font=\u9ED1\u4F53]";
        htmlTags[17] = "<font face=\"\u9ED1\u4F53\">";
        ubbTags[18] = "[font=\u96B6\u4E66]";
        htmlTags[18] = "<font face=\"\u96B6\u4E66\">";
        ubbTags[19] = "[font=\u4EFF\u5B8B_GB2312]";
        htmlTags[19] = "<font face=\"\u4EFF\u5B8B_GB2312\">";
        ubbTags[20] = "[font=\u5E7C\u5706]";
        htmlTags[20] = "<font face=\"\u5E7C\u5706\">";
        ubbTags[21] = "[font=Arial]";
        htmlTags[21] = "<font face=\"Arial\">";
        ubbTags[22] = "[font=Times New Roman]";
        htmlTags[22] = "<font face=\"Times New Roman\">";
        ubbTags[23] = "[red]";
        htmlTags[23] = "<font color=\"red\">";
        ubbTags[24] = "[/red]";
        htmlTags[24] = "</font>";
        ubbTags[25] = "[blue]";
        htmlTags[25] = "<font color=\"blue\">";
        ubbTags[26] = "[/blue]";
        htmlTags[26] = "</font>";
        ubbTags[27] = "[yellow]";
        htmlTags[27] = "<font color=\"yellow\">";
        ubbTags[28] = "[/yellow]";
        htmlTags[28] = "</font>";
        ubbTags[29] = "[green]";
        htmlTags[29] = "<font color=\"green\">";
        ubbTags[30] = "[/green]";
        htmlTags[30] = "</font>";
        ubbTags[31] = "[h1]";
        htmlTags[31] = "<h1>";
        ubbTags[32] = "[/h1]";
        htmlTags[32] = "</h1>";
        ubbTags[33] = "[h2]";
        htmlTags[33] = "<h2>";
        ubbTags[34] = "[/h2]";
        htmlTags[34] = "</h2>";
        ubbTags[35] = "[h3]";
        htmlTags[35] = "<h3>";
        ubbTags[36] = "[/h3]";
        htmlTags[36] = "</h3>";
        ubbTags[37] = "[h4]";
        htmlTags[37] = "<h4>";
        ubbTags[38] = "[/h4]";
        htmlTags[38] = "</h4>";
        ubbTags[39] = "[h5]";
        htmlTags[39] = "<h5>";
        ubbTags[40] = "[/h5]";
        htmlTags[40] = "</h5>";
        ubbTags[41] = "[h6]";
        htmlTags[41] = "<h6>";
        ubbTags[42] = "[/h6]";
        htmlTags[42] = "</h6>";
        ubbTags[43] = "[hr]";
        htmlTags[43] = "<hr>";
        ubbTags[44] = "[img]";
        htmlTags[44] = "<br><img src=\"";
        ubbTags[45] = "[/img]";
        htmlTags[45] = "\"><br>";
        ubbTags[46] = "[center]";
        htmlTags[46] = "<div align=\"center\">";
        ubbTags[47] = "[/center]";
        htmlTags[47] = "</div>";
        ubbTags[48] = "[left]";
        htmlTags[48] = "<div align=\"left\">";
        ubbTags[49] = "[/left]";
        htmlTags[49] = "</div>";
        ubbTags[50] = "[right]";
        htmlTags[50] = "<div align=\"right\">";
        ubbTags[51] = "[/right]";
        htmlTags[51] = "</div>";
    }
    private String replace(String s, String s1, String s2)
    {
        StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0; i < s1.length(); i++)
        {
            char c = s1.charAt(i);
            switch(c)
            {
            case 91: // '['
                stringbuffer.append("\\[");
                break;

            case 93: // ']'
                stringbuffer.append("\\]");
                break;

            default:
                stringbuffer.append(c);
                break;
            }
        }

        Pattern pattern = Pattern.compile(stringbuffer.toString());
        Matcher matcher = pattern.matcher(s);
        StringBuffer stringbuffer1 = new StringBuffer();
        for(boolean flag = matcher.find(); flag; flag = matcher.find())
        {
            matcher.appendReplacement(stringbuffer1, s2);
        }

        return matcher.appendTail(stringbuffer1).toString();
    }

    private String replaceNormalUBBCode(String s)
    {
        String s1 = new String(s);
        for(int i = 0; i < ubbTags.length; i++)
        {
            s1 = replace(s1, ubbTags[i], htmlTags[i]);
        }

        return s1;
    }

    private String replaceURL(String s)
    {
        StringBuffer stringbuffer = new StringBuffer(s);
        String s1 = new String();
        int i = s.indexOf("[url]");
        int j = s.indexOf("[/url]");
        if(i != -1 && j != -1 && i < j)
        {
            String s2 = s.substring(i + 5, j);
            String s3 = "<a href=\"" + s2 + "\">" + s2 + "</a>";
            stringbuffer.replace(i, j + 6, s3);
        }
        return stringbuffer.toString();
    }

    private String replaceEmail(String s)
    {
        StringBuffer stringbuffer = new StringBuffer(s);
        String s1 = new String();
        int i = s.indexOf("[email]");
        int j = s.indexOf("[/email]");
        if(i != -1 && j != -1 && i < j)
        {
            String s2 = s.substring(i + 7, j);
            String s3 = "<a href=\"mailto:" + s2 + "\">" + s2 + "</a>";
            stringbuffer.replace(i, j + 8, s3);
        }
        return stringbuffer.toString();
    }

    public void setSource(String s)
    {
        source = s;
    }

    public String getResult()
    {
        return source;
    }

    public void run()
    {
        for(source = replaceNormalUBBCode(source); source.indexOf("[url]") != -1 && source.indexOf("[/url]") != -1; source = replaceURL(source)) { }
        for(; source.indexOf("[email]") != -1 && source.indexOf("[/email]") != -1; source = replaceEmail(source)) { }
    }
}
