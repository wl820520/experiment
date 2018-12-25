package com.justh5.experiment.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Create date: 2017/12/27
 * Author jw
 * Description:
 */
public class ExcelUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    private static final String DEFAULT_DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * @Description:生成POI WorkBook对象方法,取第一个Map中的所有key作为列名,每个map都是一行数据,必须保证第一个map包含全部的列名
     * @author jw
     * @date 2017/12/27
     * @param content List<Map<String,Object>>形式的数据集
     * @return org.apache.poi.ss.usermodel.Workbook
     */
    public static Workbook createExcel(List<Map<String,Object>> content){
        SXSSFWorkbook wb = new SXSSFWorkbook();
        SXSSFSheet sheet = wb.createSheet();
        CellStyle cellStyle = wb.createCellStyle();
        //自动换行
        cellStyle.setWrapText(true);

        if (content ==null || content.size()<1){
            return wb;
        }

        try {
            //获取列名
            String[] titleArray = new String[content.get(0).size()];
            int titleNum = 0;
            for (Map.Entry<String,Object> titleEntry : content.get(0).entrySet()) {
                titleArray[titleNum] = titleEntry.getKey();
                titleNum++;
            }
            //标题行
            Row titleRow = sheet.createRow(0);
            for (int i = 0; i < titleArray.length; i++) {
                Cell titleCell = titleRow.createCell(i);
                //第2行第i个单元格的值(列名)
                Object value = titleArray[i];
                    titleCell.setCellValue(String.valueOf(value));

            }
            //内容
            int size = content.size();
            for (int i = 1; i <=size ; i++) {
                //当前行的map
                Map<String, Object> stringObjectMap = content.get(i - 1);
                Row contentRow = sheet.createRow(i);
                for (int j = 0; j < titleArray.length; j++) {
                    Cell contentCell = contentRow.createCell(j);
                    contentCell.setCellStyle(cellStyle);
                    //第i行第j个单元格的值
                    Object value = stringObjectMap.get(titleArray[j]);
                    if(value!=null){

                        if(value.getClass() == Date.class){
                            contentCell.setCellValue(new SimpleDateFormat(DEFAULT_DATEFORMAT).format((Date)value));
                        }else if (value.getClass() == Timestamp.class){
                            Timestamp tValue = (Timestamp) value;
                            Date date = new Date(tValue.getTime());
                            contentCell.setCellValue(new SimpleDateFormat(DEFAULT_DATEFORMAT).format(date));
                        }else{
                            contentCell.setCellValue(String.valueOf(value));
                        }
                    }else{
                        contentCell.setCellValue("");
                    }

                }
            }

            sheet.trackAllColumnsForAutoSizing();
            for (int i = 0; i < titleNum; i++) {
                sheet.autoSizeColumn(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导出失败 ,错误详情{}",ExceptionUtil.exception2String(e));
        }
        return wb;
    }

    /**
     * @Description:生成POI WorkBook对象方法,取第一个Map中的所有key作为列名,每个map都是一行数据,必须保证第一个map包含全部的列名
     * @author jw
     * @date 2017/12/27
     * @param content List<Map<String,Object>>形式的数据集
     * @return org.apache.poi.ss.usermodel.Workbook
     */
    public static Workbook createExcel(SXSSFWorkbook wb, List<Map<String,Object>> content){
        if (wb==null){
            wb = new SXSSFWorkbook();
        }
        SXSSFSheet sheet = wb.createSheet();
        CellStyle cellStyle = wb.createCellStyle();
        //自动换行
        cellStyle.setWrapText(true);

        if (content ==null || content.size()<1){
            return wb;
        }

        try {

            //获取列名
            String[] titleArray = new String[content.get(0).size()];
            int titleNum = 0;
            for (Map.Entry<String,Object> titleEntry : content.get(0).entrySet()) {
                titleArray[titleNum] = titleEntry.getKey();
                titleNum++;
            }
            //标题行
            Row titleRow = sheet.createRow(0);
            for (int i = 0; i < titleArray.length; i++) {
                Cell titleCell = titleRow.createCell(i);
                //第2行第i个单元格的值(列名)
                Object value = titleArray[i];
                titleCell.setCellValue(String.valueOf(value));

            }

            //内容
            int size = content.size();
            for (int i = 1; i <=size ; i++) {
                //当前行的map
                Map<String, Object> stringObjectMap = content.get(i - 1);
                Row contentRow = sheet.createRow(i);
                for (int j = 0; j < titleArray.length; j++) {
                    Cell contentCell = contentRow.createCell(j);
                    contentCell.setCellStyle(cellStyle);
                    //第i行第j个单元格的值
                    Object value = stringObjectMap.get(titleArray[j]);
                    if(value!=null){
                        if(value.getClass() == Date.class){
                            contentCell.setCellValue(new SimpleDateFormat(DEFAULT_DATEFORMAT).format((Date)value));
                        }else if (value.getClass() == Timestamp.class){
                            Timestamp tValue = (Timestamp) value;
                            Date date = new Date(tValue.getTime());
                            contentCell.setCellValue(new SimpleDateFormat(DEFAULT_DATEFORMAT).format(date));
                        }else{
                            int test=0;
                            contentCell.setCellValue(String.valueOf(value));
                        }
                    }else{
                        contentCell.setCellValue("");
                    }

                }
            }

            sheet.trackAllColumnsForAutoSizing();
            for (int i = 0; i < titleNum; i++) {
                sheet.autoSizeColumn(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导出失败,错误详情{}",ExceptionUtil.exception2String(e));
        }
        return wb;
    }

}
