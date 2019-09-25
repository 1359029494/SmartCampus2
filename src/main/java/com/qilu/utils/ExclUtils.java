package com.qilu.utils;

import com.qilu.po.Student;
import com.qilu.po.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 孙连辉
 * @version 1.0
 * @date 2019/9/17 11:43
 */
@Slf4j
public class ExclUtils {

    public static final  String XLS=".xls";
    public static final String XLSX=".xlsx";

    public static boolean isSuffix( MultipartFile file){
        if(Objects.isNull(file)){
            return false;
        }
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if(StringUtils.equals(suffix,XLS)||StringUtils.equals(suffix,XLSX)){
           return true;
        }else
            return false;
    }

    public static Map getExcelData(MultipartFile file) throws IOException {
        Map<String,List> map=new HashMap<>();
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<Student> studentList = new ArrayList<>();
        List<Teacher> teacherList = new ArrayList<>();
        if(workbook != null){
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行
                for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){
                    Student student=new Student();
                    Teacher teacher=new Teacher();
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if(row == null){
                        continue;
                    }
                    Cell roleName = row.getCell(5);
                    String role = getCellValue(roleName);
                    if(StringUtils.equals("学生",role)){
                        Cell cellName = row.getCell(0);
                        String name = getCellValue(cellName);
                        student.setName(name);
                        Cell cellSex = row.getCell(1);
                        String sexString = getCellValue(cellSex);
                        if(StringUtils.equals(sexString,"男")){
                            student.setSex(0);
                        }else
                            student.setSex(1);
                        Cell cellPhone = row.getCell(2);
                        String phone =getCellValue(cellPhone);
                        student.setPhone(phone);

                        Cell cellStuNo = row.getCell(3);
                        String stuNo = getCellValue(cellStuNo);
                        student.setStuNo(stuNo);

                        Cell cellCollege = row.getCell(4);
                        String college = getCellValue(cellCollege);
                        student.setCollege(college);
                        studentList.add(student);
                    }else{
                        Cell cellName = row.getCell(0);
                        String name = getCellValue(cellName);
                        teacher.setName(name);
                        Cell cellSex = row.getCell(1);
                        String sexString = getCellValue(cellSex);
                        if(StringUtils.equals(sexString,"男")){
                            teacher.setSex(0);
                        }else
                            teacher.setSex(1);
                        Cell cellPhone = row.getCell(2);
                        String phone =getCellValue(cellPhone);
                        teacher.setPhone(phone);

                        Cell cellStuNo = row.getCell(3);
                        String stuNo = getCellValue(cellStuNo);
                        teacher.setTeaNo(stuNo);

                        Cell cellCollege = row.getCell(4);
                        String college = getCellValue(cellCollege);
                        teacher.setCollege(college);
                        teacherList.add(teacher);

                    }
                    map.put("学生",studentList);
                    map.put("老师",teacherList);


                }
            }
        }
        return map;
    }
    public static  Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith("xls")){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith("xlsx")){
                //2007 及2007以上
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return workbook;
    }
    public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = stringDateProcess(cell);
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * 时间格式处理
     * @return
     * @author Liu Xin Nan
     * @data 2017年11月27日
     */
    public static String stringDateProcess(Cell cell){
        String result = new String();
        if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
            SimpleDateFormat sdf = null;
            if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                sdf = new SimpleDateFormat("HH:mm");
            } else {// 日期
                sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            }
            Date date = cell.getDateCellValue();
            result = sdf.format(date);
        } else if (cell.getCellStyle().getDataFormat() == 58) {
            // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            double value = cell.getNumericCellValue();
            Date date = org.apache.poi.ss.usermodel.DateUtil
                    .getJavaDate(value);
            result = sdf.format(date);
        } else {
            double value = cell.getNumericCellValue();
            CellStyle style = cell.getCellStyle();
            DecimalFormat format = new DecimalFormat();
            String temp = style.getDataFormatString();
            // 单元格设置成常规
            if (temp.equals("General")) {
                format.applyPattern("#");
            }
            result = format.format(value);
        }

        return result;
    }

}
