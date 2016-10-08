/*
 * VoteChart.java
 *
 * Created on 2006閿熸枻鎷�3閿熸枻鎷�7閿熸枻鎷�, 閿熸枻鎷烽敓鏂ゆ嫹5:14
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
            //閿熸枻鎷烽敓鏂ゆ嫹chart閿熸枻鎷烽敓鏂ゆ嫹
            PiePlot plot = new PiePlot(piedata);
            //閿熸枻鎷风粺閿熸枻鎷峰浘鐗囬敓杈冩枻鎷穕閿熸枻鎷�
            //plot.setURLGenerator(new StandardPieURLGenerator(url,"category"));
            plot.setToolTipGenerator(new StandardPieToolTipGenerator());
            plot.setNoDataMessage("No data available");
            plot.setExplodePercent(1, 0.5D);
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})"));
            JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

            chart.setBackgroundPaint(java.awt.Color.white);//閿熸枻鎷烽敓鏂ゆ嫹鍥剧墖閿熶茎鎲嬫嫹閿熸枻鎷疯壊

            Font font = new Font("閿熸枻鎷烽敓鏂ゆ嫹", Font.CENTER_BASELINE, 20);//閿熸枻鎷烽敓鏂ゆ嫹鍥剧墖閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熼叺杈炬嫹灏�            
            TextTitle _title = new TextTitle(title);
            _title.setFont(font);
            chart.setTitle(_title);
            //閿熸枻鎷烽敓鏂ゆ嫹鍌婚敓閰佃?鎷疯閿熸枻鎷烽敓缁炶功鍖℃嫹?
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            //500閿熸枻鎷峰浘鐗囬敓鏂ゆ嫹閿熼ズ锝忔嫹300閿熸枻鎷峰浘鐗囬敓绔鎷�
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
            //閿熸枻鎷烽敓鏂ゆ嫹chart閿熸枻鎷烽敓鏂ゆ嫹
            PiePlot plot = new PiePlot(piedata);
            //閿熸枻鎷风粺閿熸枻鎷峰浘鐗囬敓杈冩枻鎷穕閿熸枻鎷�
            plot.setURLGenerator(new StandardPieURLGenerator(url, "category"));
            plot.setToolTipGenerator(new StandardPieToolTipGenerator());
            //JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT,plot, true);
            JFreeChart chart = ChartFactory.createPieChart3D("", piedata, true, false, false);
            PiePlot pie = (PiePlot) chart.getPlot();
            //閿熷�熷畾閿熷姭鍒嗘唻鎷烽敓鏂ゆ嫹绀洪敓鏂ゆ嫹寮�
            pie.setBackgroundPaint(Color.white);
            //閿熷�熷畾閿熸枻鎷烽敓鏂ゆ嫹閫忛敓鏂ゆ嫹榫嬮敓?-1.0涔嬮敓鎴掞級
            pie.setBackgroundAlpha(0.6f);
            pie.setForegroundAlpha(0.90f);

            chart.setBackgroundPaint(java.awt.Color.white);//閿熸枻鎷烽敓鏂ゆ嫹鍥剧墖閿熶茎鎲嬫嫹閿熸枻鎷疯壊

            Font font = new Font("閿熸枻鎷烽敓鏂ゆ嫹", Font.CENTER_BASELINE, 20);//閿熸枻鎷烽敓鏂ゆ嫹鍥剧墖閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熼叺杈炬嫹灏�
            TextTitle _title = new TextTitle(title);
            _title.setFont(font);
            chart.setTitle(_title);

            //閿熸枻鎷烽敓鏂ゆ嫹鍌婚敓閰佃?閿熻剼纰夋嫹閿熸枻鎷锋椂鐩敓??
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            //500閿熸枻鎷峰浘鐗囬敓鏂ゆ嫹閿熼ズ锝忔嫹300閿熸枻鎷峰浘鐗囬敓绔鎷�
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
            Font font = new Font("闅朵功", Font.CENTER_BASELINE, 20);//閿熸枻鎷烽敓鏂ゆ嫹鍥剧墖閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熼叺杈炬嫹灏�
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
