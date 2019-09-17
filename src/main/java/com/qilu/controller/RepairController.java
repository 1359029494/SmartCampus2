package com.qilu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qilu.po.*;
import com.qilu.service.RepairService;
import com.qilu.service.StudentAndTeacherService;
import com.qilu.utils.JsonData;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/repair")
public class RepairController {
    @Resource
    private RepairService repairService;
    //查看维修人员个人信息
    @PostMapping("findMyInfo")
    public JsonData findMyInfo(@Param("id")int id){
        Maintainer maintainer=repairService.findMyInfo(id);
        return JsonData.buildSuccess(maintainer);
    }
    //查看我的接单
    @PostMapping("findMyOrder")
    public PageInfo<Repair> findMyOrder(int maintainerId, @RequestParam(defaultValue = "1") int pageNum){
        System.out.println(pageNum);
        PageHelper.startPage(pageNum, 2);
        System.out.println(maintainerId);
        List<Repair> list = repairService.findMyOrder(maintainerId);
        System.out.println(list.size());
        PageInfo<Repair> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo);
        return pageInfo;
    }
    //查看我的未修订单
    @PostMapping("findMyOrderWithNo")
    public PageInfo<Repair> findMyOrderWithNo(int maintainerId, @RequestParam(defaultValue = "1") int pageNum){
        System.out.println(pageNum);
        PageHelper.startPage(pageNum, 3);
        List<Repair> list = repairService.findMyOrderWithNo(maintainerId);
        PageInfo<Repair> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
    //查看我的已完工订单
    @PostMapping("findMyOrderWithYes")
    public PageInfo<Repair> findMyOrderWithYes(int maintainerId, @RequestParam(defaultValue = "1") int pageNum){
        System.out.println(pageNum);
        PageHelper.startPage(pageNum, 3);
        List<Repair> list = repairService.findMyOrderWithYes(maintainerId);
        PageInfo<Repair> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
    //查看所有单子
    @GetMapping("findOrder")
    public PageInfo<Repair> findOrder(int maintainerId,@RequestParam(defaultValue = "1") int pageNum){
        System.out.println(pageNum);
        PageHelper.startPage(pageNum, 2);
        List<Repair> list = repairService.findOrder();
        System.out.println(list.size());
        PageInfo<Repair> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo);
        return pageInfo;
    }
    //罚款 //新建罚款单
    @PutMapping("fine")
    public JsonData fine(int id,@Param("repairId")int repairId,@Param("money") BigDecimal money){
        repairService.fine(id);
        int r1=(int)(Math.random()*(10));//产生2个0-9的随机数
        int r2=(int)(Math.random()*(10));
        long now = System.currentTimeMillis();//一个13位的时间戳
        String orderNo =String.valueOf(r1)+String.valueOf(r2)+String.valueOf(now);
        Order order=new Order();
        order.setOrderNo(orderNo);
        order.setMoney(money);
        order.setRepairId(repairId);
        repairService.insertFineOrder(order);
        return JsonData.buildSuccess("已发送罚款信息");
    }
    //完工
    @PutMapping("finish")
    public JsonData finish(int id){
        repairService.finish(id);
        return JsonData.buildSuccess();
    }
    //接单
    @PutMapping("receipt")
    public JsonData receipt(int id,int maintainerId){
        repairService.receipt(id,maintainerId);
        return JsonData.buildSuccess("成功接单");
    }
    //查询数目
    @PutMapping("getNumber")
    public JsonData getNumber(int maintainerId){
        Map<String,Integer> map=repairService.getNumber(maintainerId);
        return JsonData.buildSuccess(map);
    }
    //查询评价
    @PutMapping("check")
    public PageInfo<Evaluate> check(int maintainerId){
        List<Evaluate> list = repairService.check(maintainerId);
        System.out.println(list.size());
        PageInfo<Evaluate> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo);
        return pageInfo;
    }
    //订单详细
    @PutMapping("findOrderInfo")
    public JsonData findOrderInfo(int id){
        Repair repair=repairService.findOrderInfo(id);
        return JsonData.buildSuccess(repair);
    }

}
