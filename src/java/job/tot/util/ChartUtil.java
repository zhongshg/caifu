/*
 * VoteChart.java
 *
 * Created on 2006锟斤拷3锟斤拷7锟斤拷, 锟斤拷锟斤拷5:14
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */
package job.tot.util;

import java.awt.Font;
import java.awt.Color;
import java.io.PrintWriter;
import javax.servlet.http.HttpSession;
import org.jfree.data.general.*;
import org.jfree.data.category.*;
import org.jfree.chart.*;
import org.jfree.chart.title.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.entity.*;
import org.jfree.chart.urls.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.servlet.*;
import org.jfree.chart.labels.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.ui.TextAnchor;

/**
 * @author tot
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ChartUtil {

    private DefaultPieDataset piedata = new DefaultPieDataset();
    private DefaultCategoryDataset bardata = new DefaultCategoryDataset();

    public void setPieValue(String key, double value) {
        piedata.setValue(key, value);
    }

    public void setBarValue(double value, String row, String col) {
        bardata.addValue(value, row, col);
    }

    public String generatePieChart(String title, HttpSession session, PrintWriter pw, String url) {
        String filename = null;
        try {
            //锟斤拷锟斤拷chart锟斤拷锟斤拷
            PiePlot plot = new PiePlot(piedata);
            //锟斤拷统锟斤拷图片锟较斤拷l锟斤拷
            //plot.setURLGenerator(new StandardPieURLGenerator(url,"category"));
            plot.setToolTipGenerator(new StandardPieToolTipGenerator());
            plot.setNoDataMessage("No data available");
            plot.setExplodePercent(1, 0.5D);
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})"));
            JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

            chart.setBackgroundPaint(java.awt.Color.white);//锟斤拷锟斤拷图片锟侥憋拷锟斤拷色

            Font font = new Font("锟斤拷锟斤拷", Font.CENTER_BASELINE, 20);//锟斤拷锟斤拷图片锟斤拷锟斤拷锟斤拷锟斤拷锟酵达拷小            
            TextTitle _title = new TextTitle(title);
            _title.setFont(font);
            chart.setTitle(_title);
            //锟斤拷锟斤拷傻锟酵计?拷诺锟斤拷锟绞蹦匡拷?
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            //500锟斤拷图片锟斤拷锟饺ｏ拷300锟斤拷图片锟竭讹拷
            filename = ServletUtilities.saveChartAsPNG(chart, 500, 300, info, session);
            ChartUtilities.writeImageMap(pw, filename, info, true);
            pw.flush();
        } catch (Exception e) {
            System.out.println("Exception - " + e.toString());
            e.printStackTrace(System.out);
            filename = "public_error_500x300.png";
        }
        return filename;
    }

    public String generate3dPieChart(String title, HttpSession session, PrintWriter pw, String url) {
        String filename = null;
        try {
            //锟斤拷锟斤拷chart锟斤拷锟斤拷
            PiePlot plot = new PiePlot(piedata);
            //锟斤拷统锟斤拷图片锟较斤拷l锟斤拷
            plot.setURLGenerator(new StandardPieURLGenerator(url, "category"));
            plot.setToolTipGenerator(new StandardPieToolTipGenerator());
            //JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT,plot, true);
            JFreeChart chart = ChartFactory.createPieChart3D("", piedata, true, false, false);
            PiePlot pie = (PiePlot) chart.getPlot();
            //锟借定锟劫分憋拷锟斤拷示锟斤拷式
            pie.setBackgroundPaint(Color.white);
            //锟借定锟斤拷锟斤拷透锟斤拷龋锟?-1.0之锟戒）
            pie.setBackgroundAlpha(0.6f);
            pie.setForegroundAlpha(0.90f);

            chart.setBackgroundPaint(java.awt.Color.white);//锟斤拷锟斤拷图片锟侥憋拷锟斤拷色

            Font font = new Font("锟斤拷锟斤拷", Font.CENTER_BASELINE, 20);//锟斤拷锟斤拷图片锟斤拷锟斤拷锟斤拷锟斤拷锟酵达拷小
            TextTitle _title = new TextTitle(title);
            _title.setFont(font);
            chart.setTitle(_title);

            //锟斤拷锟斤拷傻锟酵计?锟脚碉拷锟斤拷时目锟??
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            //500锟斤拷图片锟斤拷锟饺ｏ拷300锟斤拷图片锟竭讹拷
            filename = ServletUtilities.saveChartAsPNG(chart, 500, 300, info, session);
            ChartUtilities.writeImageMap(pw, filename, info, true);
            pw.flush();
        } catch (Exception e) {
            System.out.println("Exception - " + e.toString());
            e.printStackTrace(System.out);
            filename = "public_error_500x300.png";
        }
        return filename;
    }

    public String generateBarChart(String title, HttpSession session, PrintWriter pw, String url) {
        String filename = null;
        try {
            //  Create the chart object
            CategoryAxis categoryAxis = new CategoryAxis("");
            ValueAxis valueAxis = new NumberAxis("");
            BarRenderer renderer = new BarRenderer();
            //renderer.setItemURLGenerator(new StandardCategoryURLGenerator(url,"series","category"));
            renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
            //CategoryPlot plot =new CategoryPlot();
            CategoryPlot plot = new CategoryPlot(bardata, categoryAxis, valueAxis, renderer);
            JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
            chart.setBackgroundPaint(java.awt.Color.white);
            Font font = new Font("隶书", Font.CENTER_BASELINE, 20);//锟斤拷锟斤拷图片锟斤拷锟斤拷锟斤拷锟斤拷锟酵达拷小
            TextTitle _title = new TextTitle(title);
            _title.setFont(font);
            chart.setTitle(_title);
            //  Write the chart image to the temporary directory
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            filename = ServletUtilities.saveChartAsPNG(chart, bardata.getColumnCount() * 100, 300, info, session);

            //  Write the image map to the PrintWriter
            ChartUtilities.writeImageMap(pw, filename, info, true);
            pw.flush();

        } catch (Exception e) {
            System.out.println("Exception - " + e.toString());
            e.printStackTrace(System.out);
            filename = "public_error_500x300.png";
        }
        return filename;
    }

    public String generate3dBarChart(String title, HttpSession session, PrintWriter pw, String url) {
        String filename = null;
        try {
            //  Create the chart object
            CategoryAxis categoryAxis = new CategoryAxis("");
            ValueAxis valueAxis = new NumberAxis("");
            BarRenderer renderer = new BarRenderer();

            //////////////////////////////////////////////
            JFreeChart jfreechart = ChartFactory.createBarChart3D(title, url.split(",")[1], url.split(",")[0], bardata, PlotOrientation.VERTICAL, true, true, false);
            CategoryPlot categoryplot = jfreechart.getCategoryPlot();
            categoryplot.setForegroundAlpha(1.0F);
            categoryplot.setRangeGridlinePaint(Color.pink);
            renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            renderer.setBaseItemLabelsVisible(true);
            renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
            renderer.setItemLabelAnchorOffset(10D);
            renderer.setItemMargin(0.4);
            categoryplot.setRenderer(renderer);
            //CategoryAxis categoryaxis = categoryplot.getDomainAxis();
            //CategoryLabelPositions categorylabelpositions = categoryaxis.getCategoryLabelPositions();
            //CategoryLabelPosition categorylabelposition = new CategoryLabelPosition(RectangleAnchor.LEFT, TextBlockAnchor.CENTER_LEFT, TextAnchor.CENTER_LEFT, 0.0D, CategoryLabelWidthType.RANGE, 0.3F);
            //categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.replaceLeftPosition(categorylabelpositions, categorylabelposition));

            ////////////////////////////////////////////
            //  Write the chart image to the temporary directory
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            filename = ServletUtilities.saveChartAsPNG(jfreechart, 700, 500, info, session);

            //  Write the image map to the PrintWriter
            ChartUtilities.writeImageMap(pw, filename, info, true);
            pw.flush();

        } catch (Exception e) {
            System.out.println("Exception - " + e.toString());
            e.printStackTrace(System.out);
            filename = "public_error_500x300.png";
        }
        return filename;
    }
}
