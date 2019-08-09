package com.qilu.Service.impl;

import com.qilu.Service.StudentAndTeacherService;
import com.qilu.mapper.*;
import com.qilu.po.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
@Service
public class StudentAndTeacherServiceImpl implements StudentAndTeacherService {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private MaintainerMapper maintainerMapper;

    @Autowired
    private RepairMapper repairMapper;

    @Autowired
    private EvaluateMapper evaluateMapper;

    public List<Repair> findProgress(HttpSession session) {
        User user = (User) session.getAttribute("user");
        int role = 0;
        int id = 0;
        if (user.getRole() == 1){
            role = 1;
            id = user.getStudent().getId();
        }
        if (user.getRole() == 2){
            role = 2;
            id = user.getTeacher().getId();
        }
        return repairMapper.findMyRepair(role, id);
    }

    @Override
    public int repirSchool(HttpSession session, String type, String local, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        Repair repair = new Repair();
        StringBuffer path = new StringBuffer();
            Student student = user.getStudent();
            if (user.getRole() == 1){
                repair.setRole(1);
            }
            if (user.getRole() == 2){
                repair.setRole(2);
            }
            repair.setUserId(student.getId());
            repair.setType(type);
            repair.setLocal(local);
            MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
            Iterator<String> files = mrequest.getFileNames();
            while (files.hasNext()){
                MultipartFile file = mrequest.getFile(files.next());
                String fileName1 = file.getOriginalFilename();
                String suffix = fileName1.substring(fileName1.lastIndexOf("."));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String fileName = sdf.format(new Date()) + suffix;
                File dir = null;
                if (System.getProperties().getProperty("os.name").toLowerCase().startsWith("win")) {
                    dir = new File("G:/student/repair" + student.getStuNo());
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                }else {
                    dir = new File("/usr/local/static/student/repair" + student.getStuNo());
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                }
                try {
                    FileUtils.copyInputStreamToFile(file.getInputStream(), new File(dir + "/" + fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                path.append("repair" + student.getStuNo() + fileName);
            }

        repair.setPhoto(path.toString());
        int flag = repairMapper.insRepairSchool(repair);
        return flag;
    }

    @Override
    public Evaluate findOneEvaByid4Student(int id) {
        return evaluateMapper.findByIdAboutStudent(id);
    }

    @Override
    public Student findMyInfo4Student(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return studentMapper.selectById(user.getStudent().getId());
    }

    @Override
    public Teacher findMyInfo4Teacher(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return teacherMapper.selectById(user.getTeacher().getId());
    }

    @Override
    public int insEvaluate(HttpSession session, String content, int star) {
        User user = (User) session.getAttribute("user");
        Evaluate evaluate = new Evaluate();
        if (user.getRole() == 1){
            evaluate.setRole(1);
            evaluate.setUserId(user.getStudent().getId());
        }
        if (user.getRole() == 2){
            evaluate.setRole(2);
            evaluate.setUserId(user.getTeacher().getId());
        }
        evaluate.setContent(content);
        evaluate.setStar(star);
        return evaluateMapper.insEvaluate(evaluate);
    }
}
