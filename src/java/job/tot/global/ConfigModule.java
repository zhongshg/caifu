/*
 * ConfigModule.java
 *
 * Created on 2007��7��7��, ����10:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package job.tot.global;
import job.tot.global.Sysconfig;
import java.io.*;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.*;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import javax.servlet.*;
import javax.servlet.http.*;
import job.tot.util.FileUtil;
import job.tot.util.RequestUtil;
/**
 *
 * @author totcms
 */
public class ConfigModule {
    private static Log log = LogFactory.getLog(ConfigModule.class);
    /** Creates a new instance of ConfigModule */
    public ConfigModule() {
    }
    public void updateConfig(HttpServletRequest request) throws  DocumentException, IOException{
        //��ȡ����
        String login_action_url=RequestUtil.getString(request,"login_action_url");
        String field_userid=RequestUtil.getString(request,"field_userid");
        String field_password=RequestUtil.getString(request,"field_password");
        String cookie_name=RequestUtil.getString(request,"cookie_name");
        String member_gate=RequestUtil.getString(request,"member_gate");
        String member_key=RequestUtil.getString(request,"member_key");
        int database_type=RequestUtil.getInt(request,"database_type");
        String use_datasource=RequestUtil.getString(request,"use_datasource");
        String driver_class_name=RequestUtil.getString(request,"driver_class_name");
        String database_url=RequestUtil.getString(request,"database_url");
        String database_user=RequestUtil.getString(request,"database_user");
        String database_password=RequestUtil.getString(request,"database_password");
        int max_connection=RequestUtil.getInt(request,"max_connection");
        int max_time_to_wait=RequestUtil.getInt(request,"max_time_to_wait");
        int minutes_between_refresh=RequestUtil.getInt(request,"minutes_between_refresh");
        String datasource_name=RequestUtil.getString(request,"datasource_name");
        String default_mail_from=RequestUtil.getString(request,"default_mail_from");
        String use_mailsource=RequestUtil.getString(request,"use_mailsource");
        String mail_server=RequestUtil.getString(request,"mail_server");
        String username=RequestUtil.getString(request,"username");
        String password=RequestUtil.getString(request,"password");
        String port=RequestUtil.getString(request,"port");
        String mailsource_name=RequestUtil.getString(request,"mailsource_name");
        String context_path=RequestUtil.getString(request,"context_path");
        String ask_url=RequestUtil.getString(request,"ask_url");
        String ask_name=RequestUtil.getString(request,"ask_name");
        String cookie_domain=RequestUtil.getString(request,"cookie_domain");
        String enable_upload=RequestUtil.getString(request,"enable_upload");
        String upload_photo_ext=RequestUtil.getString(request,"upload_photo_ext");
        int upload_photo_maxsize=RequestUtil.getInt(request,"upload_photo_maxsize");
        int max_posts_per_hour=RequestUtil.getInt(request,"max_posts_per_hour");
        int max_replys_per_hour=RequestUtil.getInt(request,"max_replys_per_hour");
        int default_check=RequestUtil.getInt(request,"default_check");
        String indexdir=RequestUtil.getString(request,"indexdir");
        String lucene_analyzer_implementation=RequestUtil.getString(request,"lucene_analyzer_implementation");
        int server_hour_offset=RequestUtil.getInt(request,"server_hour_offset");
        String blocked_ip=RequestUtil.getString(request,"blocked_ip");
        int postpernum=RequestUtil.getInt(request,"postpernum");
        int minfetchmoney=RequestUtil.getInt(request,"minfetchmoney");
        int generalask=RequestUtil.getInt(request,"generalask");
        int mediumask=RequestUtil.getInt(request,"mediumask");
        int joinpernum=RequestUtil.getInt(request,"joinpernum");
        //��������
        String strPathName = FileUtil.getServletClassesPath();
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(strPathName + "shop.xml"));

        Element root = document.getRootElement();
        // iterate through child elements of root
        for (Iterator i = root.elementIterator(); i.hasNext(); ) {
            Element element = (Element) i.next();
            for (int j = 0, size = element.nodeCount(); j < size; j++) {
                Node node = element.node(j);
                if (node.getNodeTypeName().equals("Element")) {
                    if (node.getName().equals("login_action_url")) {
                        node.setText(login_action_url);
                    } else if (node.getName().equals("field_userid")) {
                        node.setText(field_userid);
                    } else if (node.getName().equals("field_password")) {
                        node.setText(field_password);
                    } else if (node.getName().equals("cookie_name")) {
                        node.setText(cookie_name);
                    } else if (node.getName().equals("member_gate")) {
                        node.setText(member_gate);
                    } else if (node.getName().equals("member_key")) {
                        node.setText(member_key);
                    }else if (node.getName().equals("database_type")) {
                        node.setText(String.valueOf(database_type));
                    } else if (node.getName().equals("use_datasource")) {
                        node.setText(use_datasource);
                    } else if (node.getName().equals("driver_class_name")) {
                        node.setText(driver_class_name);
                    } else if (node.getName().equals("database_url")) {
                        node.setText(database_url);
                    } else if (node.getName().equals("database_user")) {
                        node.setText(database_user);
                    } else if (node.getName().equals("database_password")) {
                        node.setText(database_password);

                    } else if (node.getName().equals("max_connection")) {
                        node.setText(String.valueOf(max_connection));
                    } else if (node.getName().equals("max_time_to_wait")) {
                        node.setText(String.valueOf(max_time_to_wait));
                    } else if (node.getName().equals("minutes_between_refresh")) {
                        node.setText(String.valueOf(minutes_between_refresh));
                    } else if (node.getName().equals("datasource_name")) {
                        node.setText(datasource_name);
                    } else if (node.getName().equals("default_mail_from")) {
                        node.setText(default_mail_from);
                    } else if (node.getName().equals("use_mailsource")) {
                        node.setText(use_mailsource);

                    } else if (node.getName().equals("mail_server")) {
                        node.setText(mail_server);
                    } else if (node.getName().equals("username")) {
                        node.setText(username);
                    } else if (node.getName().equals("password")) {
                        node.setText(password);
                    } else if (node.getName().equals("port")) {
                        node.setText(port);
                    } else if (node.getName().equals("mailsource_name")) {
                        node.setText(mailsource_name);
                    } else if (node.getName().equals("context_path")) {
                        node.setText(context_path);
                    } else if (node.getName().equals("ask_url")) {
                        node.setText(ask_url);
                    } else if (node.getName().equals("ask_name")) {
                        node.setText(ask_name);
                    } else if (node.getName().equals("cookie_domain")) {
                        node.setText(cookie_domain);
                    } else if(node.getName().equals("enable_upload")){
                        node.setText(enable_upload);
                    }else if(node.getName().equals("upload_photo_ext")){
                        node.setText(upload_photo_ext);
                    }else if (node.getName().equals("upload_photo_maxsize")) {
                        node.setText(String.valueOf(upload_photo_maxsize));
                    } else if (node.getName().equals("max_posts_per_hour")) {
                        node.setText(String.valueOf(max_posts_per_hour));
                    }else if (node.getName().equals("max_replys_per_hour")) {
                        node.setText(String.valueOf(max_replys_per_hour));
                    }else if (node.getName().equals("default_check")) {
                        node.setText(String.valueOf(default_check));
                    }else if (node.getName().equals("indexdir")) {
                        node.setText(indexdir);
                    } else if (node.getName().equals("lucene_analyzer_implementation")) {
                        node.setText(lucene_analyzer_implementation);
                    } else if (node.getName().equals("server_hour_offset")) {
                        node.setText(String.valueOf(server_hour_offset));
                    } else if (node.getName().equals("blocked_ip")) {
                        node.setText(blocked_ip);
                    } else if (node.getName().equals("postpernum")) {
                        node.setText(String.valueOf(postpernum));
                    } else if (node.getName().equals("minfetchmoney")) {
                        node.setText(String.valueOf(minfetchmoney));
                    } else if (node.getName().equals("generalask")) {
                        node.setText(String.valueOf(generalask));
                    } else if (node.getName().equals("mediumask")) {
                        node.setText(String.valueOf(mediumask));
                    } else if (node.getName().equals("joinpernum")) {
                        node.setText(String.valueOf(joinpernum));
                    } 
                }
            }
        }

        saveDocument(document, strPathName + "shop.xml");
    }
    private void saveDocument(Document doc, String fileName) throws IOException {
        XMLWriter writer = new XMLWriter(new FileWriter(fileName));
        writer.write(doc);
        writer.close();
    }
}
