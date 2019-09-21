package com.qilu.service;

import com.qilu.po.Student;
import com.qilu.response.RepairResp;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 孙连辉
 * @version 1.0
 * @date 2019/9/17 16:03
 */
public interface AdminService {

    //录入学生信息
    int insertStudent(MultipartFile file) throws Exception;

    //查询所有保修
    List<RepairResp> getAllRepair();
}
