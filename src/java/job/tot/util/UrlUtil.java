/*
 * UrlUtil.java
 *
 * Created on 2007��1��14��, ����8:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package job.tot.util;
import java.io.*;
import java.net.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author Administrator
 */
public class UrlUtil {
    private static Log log = LogFactory.getLog(UrlUtil.class);
    /** Creates a new instance of UrlUtil */
    public UrlUtil() {
    }
    public static String getHtml(String urls,String encode){
        URL url= null;
        InputStream is=null;
        BufferedReader br=null;
        try{
            url=new URL(urls);
        } catch ( MalformedURLException e) {
            return null;
        }
        StringBuffer bf=new StringBuffer(1024);
        try {
            String fileline=null;
            is = url.openStream();
            //InputStreamReader read =new InputStreamReader(is);
            InputStreamReader read =new InputStreamReader(is,encode);
            br=new BufferedReader(read);
            while ((fileline = br.readLine()) != null) {
                bf.append(fileline+"\n");
            }
        } catch (IOException e) {
            log.fatal("error on read from html:"+urls);
            return null;
        } finally{
            try{
                if(br!=null){
                    br.close();
                }
            } catch(IOException e){
                e.printStackTrace();
            }
        }
        return bf.toString();
    }
    public static void getRemoteFile(String fileurl,String savepath,String encoder){
        URL url= null;
        InputStream is=null;
        try{
            url=new URL(fileurl);
        } catch ( MalformedURLException e) {
            log.warn("get error on read remote file "+fileurl);
        }
        try{
            is=url.openStream();
        }catch(IOException e){
            log.warn("openstream error:"+fileurl);
        }
        if(is!=null){
            StringBuffer bf=new StringBuffer(1024);
            BufferedReader br=null;
            try {
                String fileline=null;
                InputStreamReader read =new InputStreamReader(is,encoder);
                br=new BufferedReader(read);
                while ((fileline = br.readLine()) != null) {
                    bf.append(fileline+"\n");
                }
                FileUtil.createFile(bf.toString(),savepath,encoder);
            } catch (IOException e) {
                log.error("io exception:"+fileurl);
            } finally{
                try{
                    br.close();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static void getRemoteFile(String picurl,String savepath){
        URL url= null;
        InputStream is=null;
        try{
            url=new URL(picurl);
        } catch ( MalformedURLException e) {
            log.warn("get error on read remote file "+picurl);
        }
        try{
            is=url.openStream();
        }catch(IOException e){
            log.warn("openstream error:"+picurl);
        }
        if(is!=null){
            BufferedInputStream inputStream = null;
            BufferedOutputStream outputStream = null;
            byte[] block = new byte[1024];
            try {
                inputStream = new BufferedInputStream(is);
                outputStream = new BufferedOutputStream(new FileOutputStream(savepath));
                while (true) {
                    int readLength = inputStream.read(block);
                    if (readLength == -1) break;// end of file
                    outputStream.write(block, 0, readLength);
                }
            } catch(IOException e){
                log.error("io exception",e);
            }finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException ex) {
                        // just ignore
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException ex) {
                        // just ignore
                    }
                }
            }
        }
    }
}
