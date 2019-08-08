package com.qilu.Service.impl;

import com.qilu.Service.UserService;
import com.qilu.mapper.EvaluateMapper;
import com.qilu.mapper.RepairMapper;
import com.qilu.mapper.StudentMapper;
import com.qilu.mapper.TeacherMapper;
import com.qilu.po.Evaluate;
import com.qilu.po.Repair;
import com.qilu.po.Student;
import com.qilu.po.Teacher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private EvaluateMapper evaluateMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private RepairMapper repairMapper;

    @Override
    public List<Repair> findProgress(int roleType, int id) {
        return repairMapper.findMyRepair(roleType, id);
    }

    @Override
    public int repirSchool(HttpSession session, String type, String local, HttpServletRequest request) {

        return 0;
    }

    @Override
    public Evaluate findOneEvaByid4Student(int id) {
        return evaluateMapper.findByIdAboutStudent(id);
    }

    @Override
    public Student findMyInfo4Student(HttpSession session) {
        return null;
    }

    @Override
    public Teacher findMyInfo4Teacher(HttpSession session) {
        return null;
    }
}
