package com.qilu.service.impl;

import com.qilu.exception.CustomException;
import com.qilu.mapper.AdminMapper;
import com.qilu.po.Student;
import com.qilu.po.Teacher;
import com.qilu.response.RepairResp;
import com.qilu.service.AdminService;
import com.qilu.utils.ExclUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 孙连辉
 * @version 1.0
 * @date 2019/9/17 16:04
 */
@Service
@Slf4j
public class AdminServiceImpl  implements AdminService {

    @Autowired
    private AdminMapper adminMapper;


    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int insertStudent(MultipartFile file) throws Exception {

        if(!ExclUtils.isSuffix(file)){
            throw new CustomException(-1,"文件格式不正确");
        }
        log.info("excl名称:{}",file.getOriginalFilename());
        Map map = ExclUtils.getExcelData(file);
        List<Student> studentList =(List<Student>) map.get("学生");
        List<Teacher> teacherList =(List<Teacher>) map.get("老师");
        int studentCount=0;
        int teacherCount=0;
        if(studentList.size()>0){
            log.info("============开始录入学生信   息===============");
           studentCount = adminMapper.insertStudent(studentList);
        }

       if(teacherList.size()>0){
           log.info("============开始录入老师信息===============");
            teacherCount = adminMapper.insertTeacher(teacherList);
       }

        return studentCount+teacherCount;
    }

    @Override
    public List<RepairResp> getAllRepair() {
        return adminMapper.getAllRepair();
    }
}
