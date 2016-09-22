/*
 * FileUtil.java
 *
 * Created on 2006��7��25��, ����11:26
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package job.tot.util;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.text.DecimalFormat;

import job.tot.exception.BadInputException;
import job.tot.filter.DisableHtmlTagFilter;
import job.tot.bean.DataField;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class FileUtil {
    
    private static Log log = LogFactory.getLog(FileUtil.class);
    
    private static FileUtil instance = new FileUtil();
    
    private static String servletClassesPath = null;
    
    private FileUtil() { // prevent instantiation
    }
    public static String getSeparator(){
        return System.getProperty("file.separator");
    }
    /** Get System default Encoding */
    public static String getDefaultEncoding(){
        String temp=System.getProperty("file.encoding");
        return temp;
    }
    /** Get File list */
    public static Collection getList(String strPath){
        Collection returnList = new ArrayList();
        DataField df=null;
        String isdirectory="0";
        File files=new File(strPath);
        if(files.isDirectory()){
            File listFile[]=files.listFiles();
            for(int i=0;i<listFile.length;i++){
                df=new DataField();
                if(listFile[i].isDirectory()) isdirectory="1";
                else isdirectory="0";
                Date mod=new Date(listFile[i].lastModified());
                df.setField("name",listFile[i].getName(),0);
                df.setField("path",listFile[i].getPath(),0);    
                df.setField("size",listFile[i].length()+"",0);
                df.setField("moditime",DateUtil.dateToStr(mod),0);
                df.setField("isdirectory",isdirectory,0);
                returnList.add(df);
            }
        }
        return returnList;
    }
    public static void checkGoodFilePath(String str) throws BadInputException {
        byte[] s = str.getBytes();
        int length = s.length;
        byte b = 0;
        
        for (int i = 0; i < length; i++) {
            b = s[i];
            if ((b == '*') ||
                    (b == '?') ||
                    (b == '<') ||
                    (b == '>') ||
                    (b == '"') ||
                    (b == '|') ||
                    (b == '\0')) {//null char : is it correct ????
                // not good char, throw an BadInputException
                //@todo : localize me
                throw new BadInputException("The string '" + DisableHtmlTagFilter.filter(str) + "' is not a good file path. Reason: character '" + (char)(b) + "' is not allowed.");
            }
        }// for
    }
    
    public static void checkGoodFileName(String str) throws BadInputException {
        // must be a good file path first
        checkGoodFilePath(str);
        byte[] s = str.getBytes();
        int length = s.length;
        byte b = 0;
        
        for (int i = 0; i < length; i++) {
            b = s[i];
            if ((b == '/') ||
                    (b == '\\') ||
                    (b == ':')) {
                // not good char, throw an BadInputException
                //@todo : localize me
                throw new BadInputException("The string '" + DisableHtmlTagFilter.filter(str) + "' is not a good file name. Reason: character '" + (char)(b) + "' is not allowed.");
            }
        }// for
    }
    
    public static void createDir(String dir, boolean ignoreIfExitst) throws IOException {
        File file = new File(dir);
        
        if (ignoreIfExitst && file.exists()) {
            return;
        }
        
        if ( file.mkdir() == false) {
            throw new IOException("Cannot create the directory = " + dir);
        }
    }
    
    public static void createDirs(String dir, boolean ignoreIfExitst) throws IOException {
        File file = new File(dir);
        
        if (ignoreIfExitst && file.exists()) {
            return;
        }
        
        if ( file.mkdirs() == false) {
            throw new IOException("Cannot create directories = " + dir);
        }
    }
    
    public static void deleteFile(String filename) throws IOException {
        File file = new File(filename);
        log.trace("Delete file = " + filename);
        if (file.isDirectory()) {
            throw new IOException("IOException -> BadInputException: not a file.");
        }
        if (file.exists() == false) {
            throw new IOException("IOException -> BadInputException: file is not exist.");
        }
        if (file.delete() == false) {
            throw new IOException("Cannot delete file. filename = " + filename);
        }
    }
    
    public static void deleteDir(File dir) throws IOException {
        if (dir.isFile()) throw new IOException("IOException -> BadInputException: not a directory.");
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isFile()) {
                    file.delete();
                } else {
                    deleteDir(file);
                }
            }
        }//if
        dir.delete();
    }
    
    public static long getDirLength(File dir) throws IOException {
        if (dir.isFile()) throw new IOException("BadInputException: not a directory.");
        long size = 0;
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                long length = 0;
                if (file.isFile()) {
                    length = file.length();
                } else {
                    length = getDirLength(file);
                }
                size += length;
            }//for
        }//if
        return size;
    }
    
    public static long getDirLength_onDisk(File dir) throws IOException {
        if (dir.isFile()) throw new IOException("BadInputException: not a directory.");
        long size = 0;
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                long length = 0;
                if (file.isFile()) {
                    length = file.length();
                } else {
                    length = getDirLength_onDisk(file);
                }
                double mod = Math.ceil(((double)length)/512);
                if (mod == 0) mod = 1;
                length = ((long)mod) * 512;
                size += length;
            }
        }//if
        return size;
    }
    
    public static void emptyFile(String srcFilename) throws IOException {
        File srcFile = new File(srcFilename);
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Cannot find the file: " + srcFile.getAbsolutePath());
        }
        if (!srcFile.canWrite()) {
            throw new IOException("Cannot write the file: " + srcFile.getAbsolutePath());
        }
        
        FileOutputStream outputStream = new FileOutputStream(srcFilename);
        outputStream.close();
    }
    
    public static void copyFile(String srcFilename, String destFilename, boolean overwrite) throws IOException {
        
        File srcFile = new File(srcFilename);
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Cannot find the source file: " + srcFile.getAbsolutePath());
        }
        if (!srcFile.canRead()) {
            throw new IOException("Cannot read the source file: " + srcFile.getAbsolutePath());
        }
        
        File destFile = new File(destFilename);
        if (overwrite == false) {
            if (destFile.exists()) return;
        } else {
            if (destFile.exists()) {
                if (!destFile.canWrite()) {
                    throw new IOException("Cannot write the destination file: " + destFile.getAbsolutePath());
                }
            } else {
                if (!destFile.createNewFile()) {
                    throw new IOException("Cannot write the destination file: " + destFile.getAbsolutePath());
                }
            }
        }
        
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        byte[] block = new byte[1024];
        try {
            inputStream = new BufferedInputStream(new FileInputStream(srcFile));
            outputStream = new BufferedOutputStream(new FileOutputStream(destFile));
            while (true) {
                int readLength = inputStream.read(block);
                if (readLength == -1) break;// end of file
                outputStream.write(block, 0, readLength);
            }
        } finally {
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
    
    //@todo: why this method does not close the inputStream ???
    public static byte[] getBytes(InputStream inputStream) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        byte[] block = new byte[512];
        while (true) {
            int readLength = bufferedInputStream.read(block);
            if (readLength == -1) break;// end of file
            byteArrayOutputStream.write(block, 0, readLength);
        }
        byte[] retValue = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return retValue;
    }
    
    public static String getFileName(String fullFilePath) {
        if (fullFilePath == null) {
            return "";
        }
        int index1 = fullFilePath.lastIndexOf('/');
        int index2 = fullFilePath.lastIndexOf('\\');
        
        //index is the maximum value of index1 and index2
        int index = (index1 > index2) ? index1 : index2;
        if (index == -1) {
            // not found the path separator
            return fullFilePath;
        }
        String fileName = fullFilePath.substring(index + 1);
        return fileName;
    }
    
    /**
     * This method write srcFile to the output, and does not close the output
     * @param srcFile File the source (input) file
     * @param output OutputStream the stream to write to, this method will not buffered the output
     * @throws IOException
     */
    public static void popFile(File srcFile, OutputStream output) throws IOException {
        
        BufferedInputStream input = null;
        byte[] block = new byte[1024];
        try {
            input = new BufferedInputStream(new FileInputStream(srcFile), 1024);
            while (true) {
                int length = input.read(block);
                if (length == -1) break;// end of file
                output.write(block, 0, length);
            }
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    // just ignore
                }
            }
        }
    }
    
    /**
     * This method could be used to override the path to WEB-INF/classes
     * It can be set when the web app is inited
     * @param path String : new path to override the default path
     */
    public static void setServletClassesPath(String path) {
        log.debug("FileUtil.setServletClassesPath called with path = " + path);
        
        servletClassesPath = path;
        
        if (servletClassesPath == null) {
            // From mvnForum.com thread 2243:
            // I am deploying the MVNForum as an ear in Linux box so context real path turns out to be null.
            return;
        }
        if (servletClassesPath.endsWith(File.separator) == false) {
            servletClassesPath = servletClassesPath + File.separatorChar;
            log.debug("FileUtil.setServletClassesPath change path to value = " + servletClassesPath);
        }
    }
    
    /**
     * This function is used to get the classpath of a reference of one class
     * First, this method tries to get the path from system properties
     * named "mvncore.context.path" (can be configed in web.xml). If it cannot
     * find this parameter, then it will tries to load from the ClassLoader
     * FIXME: load from ClassLoader is not correct on Resin/Linux
     */
    public static String getServletClassesPath() {
        if (servletClassesPath == null) {
            String strPath = System.getProperty("mvncore.context.path");
            if (strPath != null && (strPath.length() > 0)) {
                servletClassesPath = strPath;
            } else {
                ClassLoader classLoader = instance.getClass().getClassLoader();
                URL url = classLoader.getResource("/");
                if (url == null) {
                    // not run on the Servlet environment
                    servletClassesPath = ".";
                } else {
                    servletClassesPath = url.getPath();
                }
            }
            log.debug("servletClassesPath = " + servletClassesPath);
            if (servletClassesPath.endsWith(File.separator) == false) {
                servletClassesPath = servletClassesPath + File.separatorChar;
                //log.warn("servletClassesPath does not end with /: " + servletClassesPath);
            }
        }
        return servletClassesPath;
    }
    
    /**
     * This method create a file text/css
     * NOTE: This method closes the inputStream after it have done its work.
     *
     * @param inputStream     the stream of a text/css file
     * @param textFile   the output file, have the ".css" extension or orther extension
     * @throws IOException
     * @throws BadInputException
     * @throws AssertionException
     */
    public static void createTextFile(InputStream inputStream, String textFile)
    throws IOException {
        
        if (inputStream == null) {
            throw new IllegalArgumentException("Does not accept null input");
        }
        OutputStream outputStream = null;
        try {
            byte[] srcByte = FileUtil.getBytes(inputStream);
            outputStream = new FileOutputStream(textFile);
            outputStream.write(srcByte);
            return;
        } catch (IOException e) {
            log.error("Error", e);
            throw e;
        } finally { // this finally is very important
            inputStream.close();
            if (outputStream != null) outputStream.close();
        }
    }
    
    /**
     * Write content to a fileName with the destEncoding
     *
     * @param content String
     * @param fileName String
     * @param destEncoding String
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void writeFile(String content, String fileName, String destEncoding)
    throws FileNotFoundException, IOException {
        
        File file = null;
        try {
            file = new File(fileName);
            if (file.isFile() == false) {
                throw new IOException("'" + fileName + "' is not a file.");
            }
            if (file.canWrite() == false) {
                throw new IOException("'" + fileName + "' is a read-only file.");
            }
        } finally {
            // we dont have to close File here
        }
        
        BufferedWriter out = null;
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            out = new BufferedWriter(new OutputStreamWriter(fos, destEncoding));
            
            out.write(content);
            out.flush();
        } catch (FileNotFoundException fe) {
            log.error("Error", fe);
            throw fe;
        } catch (IOException e) {
            log.error("Error", e);
            throw e;
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException ex) {}
        }
    }
    public static void createFile(String content, String fileName, String destEncoding){
        BufferedWriter bw=null;
       try{
            File files=new File(fileName);       
            bw= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(files), destEncoding));
            bw.write(content,0,content.length());
            bw.flush();  
        }
       catch(IOException e){
            log.error("Error", e);
        }
        finally{
            try{
                if (bw!=null) bw.close();
            }
            catch(IOException e){
                e.printStackTrace();                
            }
        }
    }
    public static String readFile(String fileName, String srcEncoding)
    throws FileNotFoundException, IOException {
        
        File file = null;
        try {
            file = new File(fileName);
            if (file.isFile() == false) {
                throw new IOException("'" + fileName + "' is not a file.");
            }
        } finally {
            // we dont have to close File here
        }
        
        BufferedReader reader = null;
        try {
            StringBuffer result = new StringBuffer(1024);
            FileInputStream fis = new FileInputStream(fileName);
            reader = new BufferedReader(new InputStreamReader(fis, srcEncoding));
            
            char[] block = new char[512];
            while (true) {
                int readLength = reader.read(block);
                if (readLength == -1) break;// end of file
                result.append(block, 0, readLength);
            }
            return result.toString();
        } catch (FileNotFoundException fe) {
            log.error("Error", fe);
            throw fe;
        } catch (IOException e) {
            log.error("Error", e);
            throw e;
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException ex) {}
        }
    }
    
    /*
     *  1  ABC
     *  2  abC Gia su doc tu dong 1 lay ca thay 5 dong => 1 --> 5
     *  3  ABC
     */
    public static String[] getLastLines(File file, int linesToReturn)
    throws IOException, FileNotFoundException {
        
        final int AVERAGE_CHARS_PER_LINE = 250;
        final int BYTES_PER_CHAR = 2;
        
        RandomAccessFile randomAccessFile = null;
        StringBuffer buffer = new StringBuffer(linesToReturn * AVERAGE_CHARS_PER_LINE);
        int lineTotal = 0;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            long byteTotal = randomAccessFile.length();
            long byteEstimateToRead = linesToReturn * AVERAGE_CHARS_PER_LINE * BYTES_PER_CHAR;
            
            long offset = byteTotal - byteEstimateToRead;
            if (offset < 0) {
                offset = 0;
            }
            
            randomAccessFile.seek(offset);
            //log.debug("SKIP IS ::" + offset);
            
            String line = null;
            String lineUTF8 = null;
            while ((line = randomAccessFile.readLine()) != null) {
                lineUTF8 = new String(line.getBytes("ISO8859_1"), "UTF-8");
                lineTotal++;
                buffer.append(lineUTF8).append("\n");
            }
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException ex) {
                }
            }
        }
        
        String[] resultLines = new String[linesToReturn];
        BufferedReader in = null;
        try {
            in = new BufferedReader(new StringReader(buffer.toString()));
            
            int start = lineTotal /* + 2 */ - linesToReturn; // Ex : 55 - 10 = 45 ~ offset
            if (start < 0) start = 0; // not start line
            for (int i = 0; i < start; i++) {
                in.readLine(); // loop until the offset. Ex: loop 0, 1 ~~ 2 lines
            }
            
            int i = 0;
            String line = null;
            while ((line = in.readLine()) != null) {
                resultLines[i] = line;
                i++;
            }
        } catch (IOException ie) {
            log.error("Error" + ie);
            throw ie;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                }
            }
        }
        return resultLines;
    }
    
    public static String getHumanSize(long size) {
        
        int sizeToStringLength = String.valueOf(size).length();
        String humanSize = "";
        DecimalFormat formatter = new DecimalFormat("##0.##");
        if (sizeToStringLength > 9) {
            humanSize += formatter.format((double) size / (1024 * 1024 * 1024)) + " GB";
        } else if (sizeToStringLength > 6) {
            humanSize += formatter.format((double) size / (1024 * 1024)) + " MB";
        } else if (sizeToStringLength > 3) {
            humanSize += formatter.format((double) size / 1024) + " KB";
        } else {
            humanSize += String.valueOf(size) + " Bytes";
        }
        return humanSize;
    }
}