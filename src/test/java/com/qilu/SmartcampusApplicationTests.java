package com.qilu;

import com.qilu.mapper.EvaluateMapper;
import com.qilu.mapper.RepairMapper;
import com.qilu.mapper.StudentMapper;
import com.qilu.mapper.TeacherMapper;
import com.qilu.po.Evaluate;
import com.qilu.po.Repair;
import com.qilu.po.Student;
import com.qilu.po.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmartcampusApplicationTests {

    @Resource
    private RepairMapper repairMapper;
    @Resource
    private EvaluateMapper evaluateMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private TeacherMapper teacherMapper;
    @Test
    public void contextLoads() {
    }

    /**
     * 功能描述:测试师生查看自己所有的报修信息
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    @Test
    public void test1(){
        List<Repair> list = repairMapper.findMyRepair(1, 1);
        for (Repair r:list){
            System.out.println(r);
        }
    }

    //测试师生通过评价id查询评价
    @Test
    public void test2(){
        Evaluate evaluate = evaluateMapper.findByIdAboutStudent(1);
        System.out.println(evaluate);
    }

    //测试报修
    @Test
    public void test3(){
        Repair r = new Repair();
        r.setRole(1);
        r.setUserId(1);
        r.setType("垃圾桶坏了");
        r.setLocal("山东济南长清区紫薇路");
        r.setEvaluateId(1);
        r.setRepairDate(new Date());
        r.setServiceDate(new Date());
        int flag = repairMapper.insRepairSchool(r);
        if (flag > 0){
            System.out.println("成功");
        }
    }

    //师生查看自己的信息
    @Test
    public void test4(){
        Student student = studentMapper.selectById(1);
        Teacher teacher = teacherMapper.selectById(1);
        System.out.println(student);
        System.out.println(teacher);
    }

    @Test
    public void test5(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        System.out.println(sdf.format(new Date()));
    }
}
