package com.qilu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qilu.po.Maintainer;
import com.qilu.po.User;
import com.qilu.service.RepairService;
import com.qilu.service.StudentAndTeacherService;
import com.qilu.po.Repair;
import com.qilu.utils.JsonData;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/v1/repair")
public class RepairController {
    @Resource
    private RepairService repairService;
    //查看我得个人信息
    @PostMapping("findMyOrder")
    public JsonData findMyInfo(@Param("id")int id){
        Maintainer maintainer=repairService.findMyInfo(id);
        return JsonData.buildSuccess(maintainer);
    }
    //查看我得接单
    @PostMapping("findMyOrder")
    public PageInfo<Repair> findMyOrder(@Param("id")int id, @RequestParam(defaultValue = "1") int pageNum){
        System.out.println(pageNum);
        PageHelper.startPage(pageNum, 2);
        System.out.println(id);
        List<Repair> list = repairService.findMyOrder(id);
        System.out.println(list.size());
        PageInfo<Repair> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo);
        return pageInfo;
    }
    //查看所有单子
    @GetMapping("findOrder")
    public PageInfo<Repair> findOrder(@RequestParam(defaultValue = "1") int pageNum){
        System.out.println(pageNum);
        PageHelper.startPage(pageNum, 2);
        List<Repair> list = repairService.findOrder( );
        System.out.println(list.size());
        PageInfo<Repair> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo);
        return pageInfo;
    }
    //罚款
    @PutMapping("fine")
    public JsonData fine(int id){
        repairService.fine(id);
        return JsonData.buildSuccess();
    }
    //完工
    @PutMapping("finish")
    public JsonData finish(int id){
        repairService.finish(id);
        return JsonData.buildSuccess();
    }
    //接单
    @PutMapping("receipt")
    public JsonData receipt(int id){
        repairService.receipt(id);
        return JsonData.buildSuccess();
    }

}
