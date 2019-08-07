package com.qilu;

import com.qilu.mapper.RepairMapper;
import com.qilu.po.Repair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmartcampusApplicationTests {

    @Resource
    private RepairMapper repairMapper;
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

}
