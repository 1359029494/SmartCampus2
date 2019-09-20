package com.qilu.service.impl;

import com.github.pagehelper.PageHelper;
import com.qilu.service.StudentAndTeacherService;
import com.qilu.mapper.*;
import com.qilu.po.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

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

    @Resource
    private KnowledgeMapper knowledgeMapper;
    
    @Resource
    private OrderMapper orderMapper;

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
    public int repirSchool(HttpSession session, String type, String local, String describe, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        Repair repair = new Repair();
        StringBuffer path = new StringBuffer();
            if (user.getRole() == 1){
                repair.setRole(1);
                Student student = user.getStudent();
                repair.setUserId(student.getId());
                repair.setPhone(student.getPhone());
            }
            if (user.getRole() == 2){
                repair.setRole(2);
                Teacher teacher = user.getTeacher();
                repair.setUserId(teacher.getId());
                repair.setPhone(teacher.getPhone());
            }
            
            repair.setType(type);
            repair.setLocal(local);
            repair.setRemarks(describe);
            repair.setRepairDate(new Date());
            MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
            Iterator<String> files = mrequest.getFileNames();
            while (files.hasNext()){
                MultipartFile file = mrequest.getFile(files.next());
                String fileName1 = file.getOriginalFilename();
                System.out.println(fileName1);
                String suffix = fileName1.substring(fileName1.lastIndexOf("."));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String fileName = sdf.format(new Date()) + suffix;
                File dir = null;
                if (System.getProperties().getProperty("os.name").toLowerCase().startsWith("win")) {
                    if (user.getRole() == 1){
                        dir = new File("G:/student/repair/" + user.getStudent().getStuNo());
                        path.append("G:/student/repair" + user.getStudent().getStuNo() + fileName + ";");
                    }
                    if (user.getRole() == 2){
                        dir = new File("G:/teacher/repair/" + user.getTeacher().getTeaNo());
                        path.append("G:/teacher/repair" + user.getStudent().getStuNo() + fileName + ";");
                    }
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                }else {
                    if (user.getRole() == 1){
                        dir = new File("/usr/local/static/student/repair" + user.getStudent().getStuNo());
                        path.append("/usr/local/static/student/repair" + user.getStudent().getStuNo() + fileName + ";");
                    }
                    if (user.getRole() == 2){
                        dir = new File("/usr/local/static/teacher/repair" + user.getTeacher().getTeaNo());
                        path.append("/usr/local/static/teacher/repair" + user.getTeacher().getTeaNo() + fileName + ";");
                    }
                    
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                }
                try {
                    FileUtils.copyInputStreamToFile(file.getInputStream(), new File(dir + "/" + fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
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
    public int insEvaluate(HttpSession session, String name, String content, int star) {
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
        evaluate.setMaintainerName(name);
        return evaluateMapper.insEvaluate(evaluate);
    }

    @Override
    public List<Knowledge> findAllKnowledge() {
        return knowledgeMapper.findAll();
    }

    @Override
    public Knowledge findOneKnowledgeByid(int id) {
        return knowledgeMapper.findByid(id);
    }

    @Override
    public Repair findRepairById(int id) {
        return repairMapper.findOrderInfo(id);
    }

    @Override
    public String uploadPhoto(HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String flag = "失败";
        MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
        Iterator<String> files = mrequest.getFileNames();
        while (files.hasNext()){
            MultipartFile file = mrequest.getFile(files.next());
            String fileName1 = file.getOriginalFilename();
            System.out.println(fileName1);
            String suffix = fileName1.substring(fileName1.lastIndexOf("."));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String fileName = sdf.format(new Date()) + suffix;
            File dir = null;
            //如果是windows系统
            if (System.getProperties().getProperty("os.name").toLowerCase().startsWith("win")){
                dir = null;
                if (user.getRole() == 1){
                    dir = new File("E:\\studnet\\" + user.getStudent().getStuNo());
                }
                if (user.getRole() == 2){
                    dir = new File("E:\\teacher\\" + user.getTeacher().getTeaNo());
                }
                //如果不存在就创建一个
                if (!dir.exists()){
                    dir.mkdirs();
                }
                String uuid = UUID.randomUUID().toString();
                //将字节流从输入流复制到文件目标
                try {
                    FileUtils.copyInputStreamToFile(file.getInputStream(), new File(dir + "/" + uuid + suffix));//返回InputStream读取文件的内容, new File(dir + "\\" + uuid + suffix)//目标路径);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //将路径存入数据库
                if (user.getRole() == 1){
                    int num = studentMapper.uploadPhoto(dir + "/" + uuid + suffix, user.getStudent().getId());
                    if (num > 0){
                        flag = "成功";
                    }
                }
                if (user.getRole() == 2){
                    int num = teacherMapper.uploadPhoto(dir + "/" + uuid + suffix, user.getTeacher().getId());
                    if (num > 0){
                        flag = "成功";
                    }
                }
            }else { //如果是Linux系统
                String path = "/user/student/head_img";
                String path2 = "/user/teacher/head_img";
                dir = null;
                if (user.getRole() == 1){
                    dir = new File(path + "/" + user.getStudent().getStuNo());
                }
                if (user.getRole() == 2){
                    dir = new File(path2 + "/" + user.getStudent().getStuNo());
                }
                //如果不存在就创建一个
                if (!dir.exists()){
                    dir.mkdirs();
                }
                String uuid = UUID.randomUUID().toString();
                try {
                    FileUtils.copyInputStreamToFile(file.getInputStream(), new File(dir + "/" + uuid + suffix));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //将路径存入数据库
                if (user.getRole() == 1){
                    int num = studentMapper.uploadPhoto(dir + "/" + uuid + suffix, user.getStudent().getId());
                    if (num > 0){
                        flag = "成功";
                    }
                }
                if (user.getRole() == 2){
                    int num = teacherMapper.uploadPhoto(dir + "/" + uuid + suffix, user.getTeacher().getId());
                    if (num > 0){
                        flag = "成功";
                    }
                }
            }
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File(dir + "/" + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    @Override
    public int updOrder2HasPay(int repairId) {
        Order order = new Order();
        order.setPayDate(new Date());
        order.setRepairId(repairId);
        int flag1 = repairMapper.updRepair2Fine(repairId);
        int flag2 = orderMapper.updOrder2HasPay(order);
        if (flag1> 0 && flag2 > 0){
            return 1;
        } else {
            return 0;
        }
    }


    @Override
    public void doRepair(Repair repair) {
        repairMapper.insRepairSchool(repair);
    }
}
