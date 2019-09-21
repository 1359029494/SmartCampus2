package com.qilu.service.impl;

import com.qilu.exception.CustomException;
import com.qilu.mapper.AdminMapper;
import com.qilu.po.Student;
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
        List<Student> students = ExclUtils.getExcelData(file);
            log.info("============开始录入学生信息===============");
        int sum = adminMapper.insertStudent(students);
        return sum;
    }

    @Override
    public List<RepairResp> getAllRepair() {
        return adminMapper.getAllRepair();
    }
}
