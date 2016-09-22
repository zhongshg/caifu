<%-- 
    Document   : upload
    Created on : 2014-4-8, 9:08:11
    Author     : Administrator
--%>

<%@page import="java.awt.geom.AffineTransform"%>
<%@page import="java.awt.RenderingHints"%>
<%@page import="java.awt.Graphics2D"%>
<%@page import="java.awt.image.WritableRaster"%>
<%@page import="java.awt.image.ColorModel"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="wap.wx.dptc.dao.DaoFactory"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.jspsmart.upload.SmartUploadException"%>
<%@page import="com.jspsmart.upload.File"%>
<%@page import="com.jspsmart.upload.SmartUpload"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    SmartUpload su = new SmartUpload();
    su.initialize(this.getServletConfig(), request, response);
    try {
        su.upload();
    } catch (Exception e) {
        // TODO: handle exception
    }

    //获取上传的文件
    File file = su.getFiles().getFile(0);
    String projectid = su.getRequest().getParameter("projectid");
    String id = su.getRequest().getParameter("id");
    String content = su.getRequest().getParameter("content");

//    System.out.println("id" + id);
//    id = "3";
    String username = DaoFactory.getMysqlDao().getFirstData("select UserName from t_member where id=" + id, "UserName").getFieldValue("UserName");
    System.out.println("username" + username);

    //创建图片路径
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat format1 = new SimpleDateFormat("yyyyMM");
    SimpleDateFormat format2 = new SimpleDateFormat("dd");
    StringBuilder path = new StringBuilder("E:/site/luyuan/ROOT/pic/" + format1.format(new Date()) + "/" + format2.format(new Date()));
    java.io.File file2 = new java.io.File(path.toString());
    if (!file2.exists()) {
        file2.mkdirs();
    }
    String pathsta = path.append("/").append(String.valueOf(System.currentTimeMillis())).toString() + "." + file.getFileExt();
    String pathsmall = path.toString() + "_small." + file.getFileExt();
    try {
        file.saveAs(pathsta);
    } catch (SmartUploadException e) {
        e.printStackTrace();
    }

    BufferedImage srcImage;
    String imgType = "JPEG";
    if (pathsta.toString().toLowerCase().endsWith(".png")) {
        imgType = "PNG";
    }
    java.io.File saveFile = new java.io.File(pathsmall.toString());
    java.io.File fromFile = new java.io.File(pathsta.toString());
    srcImage = ImageIO.read(fromFile);
    int type = srcImage.getType();
    BufferedImage target = null;
    double sx = (double) 120 / srcImage.getWidth();
    int width = 120;
    int height = (int) (sx * srcImage.getWidth());
    if (type == BufferedImage.TYPE_CUSTOM) { //handmade             
        ColorModel cm = srcImage.getColorModel();
        WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
        boolean alphaPremultiplied = cm.isAlphaPremultiplied();
        target = new BufferedImage(cm, raster, alphaPremultiplied, null);
    } else {
        target = new BufferedImage(width, height, type);
    }
    Graphics2D g = target.createGraphics();             //smoother than exlax:             
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g.drawRenderedImage(srcImage, AffineTransform.getScaleInstance(sx, (double) height / srcImage.getWidth()));
    g.dispose();
    ImageIO.write(target, imgType, saveFile);

//    System.out.println("pathsta2   " + pathsta);
//    System.out.println("pathsmall2   " + pathsmall);
    //id,Title,PicUrl,PubDate,Content,ProjectId t_pic
    String p = pathsta.toString().split("ROOT/")[1];
    boolean flag = DaoFactory.getMysqlDao().add("insert into t_pic(Title,PicUrl,PubDate,Content,ProjectId) values ('" + username + "','" + p + "','" + format.format(new Date()) + "','" + content + "'," + projectid + ")");
    if (flag) {
        out.print("{\"upload\":\"suc\"}");
    } else {
        out.print("{\"upload\":\"false\"}");
    }
%>