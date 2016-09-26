/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package job.tot.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Administrator
 */
public class ImageUtils {

    public static void main(String[] args) {
        String srcImgPath = "C:/Users/Administrator/Desktop/activity-lottery-4.png";
        String logoText = "[ 测试文字水印 http://sjsky.iteye.com ]";

        // 给图片添加水印 
//        System.out.println(ImageUtils.markByText(logoText, srcImgPath, "C:/Users/Administrator/Desktop/", 150, 200));
        // 给图片添加水印,水印旋转-45 
//        System.out.println(ImageUtils.markImageByIcon(logoText, srcImgPath, "C:/Users/Administrator/Desktop/", 150, 200));
    }

    /**
     * 给图片添加水印
     *
     * @param logoText
     * @param srcImgPath
     * @param targerPath
     */
    public static String markByText(Map<String, String> wx, String logoText, String srcImgPath, String basePath,
            int x, int y) {
        return markByText(wx, logoText, srcImgPath, basePath, x, y, null);
    }

    /**
     * 给图片添加水印、可设置水印的旋转角度
     *
     * @param logoText
     * @param srcImgPath 模板文件
     * @param degree
     */
    public static String markByText(Map<String, String> wx, String logoText, String srcImgPath, String basePath,
            int x, int y, Integer degree) {
        InputStream is = null;
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            // 得到画笔对象 
            // Graphics g= buffImg.getGraphics(); 
            Graphics2D g = buffImg.createGraphics();

            // 设置对线段的锯齿状边缘处理 
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg
                    .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            if (null != degree) {
                // 设置水印旋转 
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2, (double) buffImg
                        .getHeight() / 2);
            }

            // 设置颜色
            int rColor = Integer.parseInt(wx.get("qrcodewarnsfontcolor").substring(1, 3), 16);
            int gColor = Integer.parseInt(wx.get("qrcodewarnsfontcolor").substring(3, 5), 16);
            int bColor = Integer.parseInt(wx.get("qrcodewarnsfontcolor").substring(5, 7), 16);
            g.setColor(new Color(rColor, gColor, bColor));
            // 设置 Font 
            g.setFont(new Font(wx.get("qrcodewarnsfontstyle"), Font.BOLD, Integer.parseInt(wx.get("qrcodewarnsfontsize"))));

            float alpha = 1f;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));

            // 第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y) . 
            g.drawString(logoText, x, y);
            g.dispose();
            os = new FileOutputStream(srcImgPath);
            // 生成图片 
            ImageIO.write(buffImg, "JPG", os);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (null != os) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return srcImgPath;
    }

    public static String markImageByIcon(String iconPath, String srcImgPath, String basePath,
            int x, int y, int imgwhtype, float imgwhcase) {
        return markImageByIcon(iconPath, srcImgPath, basePath, x, y, null, imgwhtype, imgwhcase);
    }

    /**
     * 给图片添加水印、可设置水印图片旋转角度
     *
     * @param iconPath 水印图片路径
     * @param srcImgPath 源图片路径
     * @param degree 水印图片旋转角度
     */
    public static String markImageByIcon(String iconPath, String srcImgPath, String basePath,
            int x, int y, Integer degree, int imgwhtype, float imgwhcase) {
        //修改二维码尺寸
        java.io.File iconFromFile = new java.io.File(iconPath);
        String iconTargerPath = basePath + new StringBuilder("upload/qrcode_target_")
                .append(String.valueOf(System.currentTimeMillis())).append(".")
                .append(iconFromFile.getName().substring(iconFromFile.getName().lastIndexOf(".") + 1)).toString();
        BufferedImage srcImage;
        java.io.File iconSaveFile = new java.io.File(iconTargerPath);
        try {
            srcImage = ImageIO.read(iconFromFile);
            int type = srcImage.getType();
            BufferedImage target = null;
//            double sx = (double) srcImage.getWidth() / 2;
            int width = 0;// (int) (srcImage.getWidth() * 0.7);
            int height = 0;//(int) (srcImage.getHeight() * 0.7);// (int) (sx * srcImage.getWidth());
            if (0 == imgwhtype) {
                width = (int) (srcImage.getWidth() * imgwhcase);
                height = (int) (srcImage.getHeight() * imgwhcase);// (int) (sx * srcImage.getWidth());
            } else {
                width = (int) imgwhcase;
                height = (int) imgwhcase;
            }
            imgwhcase = (float) width / (float) srcImage.getWidth();//换成比例应用

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
            g.drawRenderedImage(srcImage, AffineTransform.getScaleInstance(imgwhcase, imgwhcase));
            g.dispose();
            ImageIO.write(target, iconSaveFile.getName().substring(iconSaveFile.getName().lastIndexOf(".") + 1), iconSaveFile);
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // 得到画笔对象 
            // Graphics g= buffImg.getGraphics(); 
            Graphics2D g = buffImg.createGraphics();
            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg
                    .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            if (null != degree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2, (double) buffImg
                        .getHeight() / 2);
            }

            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconTargerPath);

            // 得到Image对象。
            Image img = imgIcon.getImage();
            float alpha = 1f; // 透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));

            // 表示水印图片的位置
            g.drawImage(img, x, y, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();
            os = new FileOutputStream(srcImgPath);

            // 生成图片
            ImageIO.write(buffImg, "JPG", os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (0 == imgwhtype) {//这里是防止头像被删除
                    new java.io.File(iconPath).delete();
                }
                new java.io.File(iconTargerPath).delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return srcImgPath;

    }
}
