/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package job.tot.util;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 *
 * @author Administrator jxl操作excel
 */
public class ExportExcel {

    public static boolean createExcel(String path, Map<String, Object[][]> map) {
        boolean flag = true;
        try {
            WritableWorkbook book = Workbook.createWorkbook(new File(path));
            int i = 0;
            for (String name : map.keySet()) {
                WritableSheet sheet = book.createSheet(name, i);
                i++;
                Object[][] object = map.get(name);
                for (int j = 0; j < object.length; j++) {
                    for (int k = 0; k < object[j].length; k++) {
//                        try {
//                            jxl.write.Number number = new jxl.write.Number(k, j, Double.parseDouble((String) object[j][k]));
//                            sheet.addCell(number);
//                        } catch (Exception e) {
                        Label label = new Label(k, j, (String) object[j][k]);
                        sheet.addCell(label);
//                        }
                    }
                }
            }
            book.write();
            book.close();
        } catch (Exception e) {
            flag = false;
            System.out.println(e);
        }
        return flag;
    }

    public static List<Map<String, String>> readExcel(String url) {
        List<Map<String, String>> list = new LinkedList<Map<String, String>>();
        try {
            Workbook book = Workbook.getWorkbook(new File(url));
            int sheetcount = book.getNumberOfSheets();
            for (int i = 0; i < sheetcount; i++) {
                Sheet sheet = book.getSheet(i);
                int rowcount = sheet.getRows();
                for (int j = 1; j < rowcount; j++) {//过滤字段行
                    Map<String, String> map = new HashMap<String, String>();
//                    Cell cell = sheet.getCell(0, j);
//                     cell.getContents();
                    map.put("orderno", sheet.getCell(1, j).getContents());
                    map.put("logisno", sheet.getCell(10, j).getContents());
                    map.put("logisname", sheet.getCell(11, j).getContents());
                    list.add(map);
                }
            }
            book.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public static void updateExcel() {
        try {
            //  Excel获得文件 
            Workbook wb = Workbook.getWorkbook(new File(" test.xls "));
            //  打开一个文件的副本，并且指定数据写回到原文件 
            WritableWorkbook book = Workbook.createWorkbook(new File(" test.xls "),
                    wb);
            //  添加一个工作表 
            WritableSheet sheet = book.createSheet(" 第二页 ", 1);
            sheet.addCell(new Label(0, 0, " 第二页的测试数据 "));
            book.write();
            book.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        Map<String, Object[][]> map = new LinkedHashMap<String, Object[][]>();
        Object[][] object = {{"订单编号", "商品", "价格"}, {"编号1", "红牛", "99"}};
        map.put("订单表", object);
        ExportExcel.createExcel("E:\\site\\jiamei\\ROOT\\upload\\test.xls", map);
//        ExportExcel.readExcel();
//        ExportExcel.updateExcel();
    }
}
