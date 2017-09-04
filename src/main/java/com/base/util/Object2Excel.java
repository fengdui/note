package com.base.util;

import org.apache.ibatis.ognl.Ognl;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Java对象转Excel工具类
 * User: qufengfu
 * Date: 13-10-14
 * Time: 16:14
 */
public class Object2Excel {

    public interface SheetAdapter {

        public static final double UNIT_WIDTH = 35.7*60;
        /**
         * 每列列明
         * @return
         */
        public String[] getColumnNames();

        /**
         * 列宽
         * @return
         */
        public Integer[] getColumnWidths();

        /**
         * 数据总列数
         * @return
         */
        public Integer getColumnCount();

        /**
         * 数据总行数
         * @return
         */
        public Integer getCount();

        /**
         * 每行的数据
         * @param position
         * @return
         */
        public String[] getColumnValues(int position);

        /**
         * sheet的名称
         * @return
         */
        public String getName();

    }
    //调用反射，实现pojo到excel文件的映射
    private Object2Excel() {
    }

    public static List<String> parsePojoProps(Object obj) {
        if (obj == null) return null;
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();//访问所有字段
        List<String> list = new ArrayList<String>();
        for (Field f : fields) {
            list.add(f.getName());
        }
        return list;
    }

    //实现通用pojo对象的表格化,通过list操作，反射得到字段与字段值，自动填充表头和表体
    public static void output(List list,String[] props, String[] labels,  OutputStream os) {
        if (list == null || list.size() == 0) return;
        //List<String> props = parsePojoProps(list.get(0));
        //添加表头
        HSSFWorkbook book;
        try {
            book = new HSSFWorkbook();
            HSSFSheet sheet = book.createSheet();
            //生成表头
            HSSFRow row = sheet.createRow(0);
            for (int i = 0; i < labels.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(labels[i]);
            }
            //添加内容
            int rows = 1;
            for (Object obj : list) {
                row = sheet.createRow(rows);
                //调用ognl取属性值
                int colIndex = 0;//列索引
                for (String pro : props) {
                    HSSFCell cell = row.createCell(colIndex++);
                    cell.setCellValue(String.valueOf(Ognl.getValue(pro, obj)));
                }
                rows++;//下一行
            }
            book.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //实现通用pojo对象的表格化,通过list操作，反射得到字段与字段值，自动填充表头和表体
    public static void output(int rownumber, List list,String[] props, String[] labels, OutputStream os, HSSFWorkbook book) {
        if (list == null || list.size() == 0) return;
        //List<String> props = parsePojoProps(list.get(0));
        //添加表头
        try {
            if (book == null){
                book = new HSSFWorkbook();
            }
            HSSFSheet sheet = (book.getNumberOfSheets() == 0 ? null : book.getSheetAt(0));
            if (sheet == null){
                sheet = book.createSheet();
                HSSFRow row = sheet.createRow(0);
                //生成表头
                for (int i = 0; i < labels.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    cell.setCellValue(labels[i]);
                }
            }

            //添加内容
            int rowIndex = rownumber;
            for (Object obj : list) {
                //调用ognl取属性值
                int colIndex = 0;//列索引
                HSSFRow row = sheet.createRow(rowIndex);
                for (String pro : props) {
                    Object objValue = Ognl.getValue(pro, obj);
                    String outPutString = "";
                    if (null != objValue) {
                        // 获取当前数据的类型
                        String dataType = objValue.getClass().toString();
                        if (dataType.equals("class java.util.Date")) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            outPutString = sdf.format(objValue);
                        } else {
                            outPutString = objValue.toString();
                        }
                    }
                    HSSFCell cell = row.createCell(colIndex++);
                    cell.setCellValue(outPutString);
                }
                rowIndex++;//下一行
            }
            book.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportExcel(OutputStream output,SheetAdapter adapter){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(adapter.getName());// 为文件的sheet设置名字
        HSSFFont font = workbook.createFont();//生成新字体
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 设置样式
        HSSFCellStyle style = workbook.createCellStyle();//生成新样式
        style.setFont(font);//把字体应用到样式
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(HSSFColor.WHITE.index);
        HSSFRow row = sheet.createRow(0);
        for(int i=0;i<adapter.getColumnCount();i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(adapter.getColumnNames()[i]);
            cell.setCellValue(text);
            sheet.setColumnWidth(i,(short)(SheetAdapter.UNIT_WIDTH*adapter.getColumnWidths()[i]));
        }
        font = workbook.createFont();//生成新字体
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.U_NONE);
        style = workbook.createCellStyle();//生成新样式
        style.setFont(font);//把字体应用到样式
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(HSSFColor.WHITE.index);
        for(int i=0;i<adapter.getCount();i++){
            row = sheet.createRow(i+1);
            for(int j=0;j<adapter.getColumnCount();j++){
                HSSFCell cell = row.createCell(j);
                cell.setCellStyle(style);
                HSSFRichTextString text = new HSSFRichTextString(adapter.getColumnValues(i)[j]);
                cell.setCellValue(text);
            }
        }
        try {
            workbook.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
