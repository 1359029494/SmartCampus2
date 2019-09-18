package com.qilu.mapper;

import com.qilu.po.Repair;
import com.qilu.po.Student;
import com.qilu.response.RepairResp;

import java.util.List;

/**
 * @author 孙连辉
 * @version 1.0
 * @date 2019/9/17 16:20
 */
public interface AdminMapper {

    //批量插入学生数据
    int insertStudent(List<Student> list);

    //查询所有保修
    List<RepairResp> getAllRepair();
}
