package com.qilu;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qilu.mapper.*;
import com.qilu.po.*;
import com.qilu.utils.JsonData;
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
    @Resource
    private KnowledgeMapper knowledgeMapper;

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
        List<Repair> list = repairMapper.findMyRepair(1, 2);
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

    @Test
    public void test6(){
        PageHelper.startPage(1, 1);
        List<Repair> list = repairMapper.findMyRepair(1, 2);
        PageInfo<Repair> pageInfo = new PageInfo<>(list);
        System.out.println(JsonData.buildSuccess(pageInfo));
    }

    /**
     * 功能描述:校园小知识查询全部
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    @Test
    public void test7(){
        PageHelper.startPage(1, 2);
        List<Knowledge> list = knowledgeMapper.findAll();
        PageInfo<Knowledge> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo);
        System.out.println(JsonData.buildSuccess(pageInfo));
    }

    /**
     * 功能描述:校园小知识查询通过id
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    @Test
    public void test8(){
        Knowledge knowledge = knowledgeMapper.findByid(1);
        System.out.println(knowledge);
    }
    
    /**
     * 功能描述:通过id查报修
     * @param: 
     * @return: 
     * @auther: 治毅
     * @date:  
     */
    @Test
    public void test9(){
        System.out.println(repairMapper.findRepairById(1));
    }
}
