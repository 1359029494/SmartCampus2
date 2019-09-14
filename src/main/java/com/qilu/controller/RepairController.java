package com.qilu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qilu.po.Maintainer;
import com.qilu.po.Order;
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
import java.math.BigDecimal;
import java.util.List;

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
    public PageInfo<Repair> findMyOrder(@Param("maintainer")int maintainer, @RequestParam(defaultValue = "1") int pageNum){
        System.out.println(pageNum);
        PageHelper.startPage(pageNum, 2);
        System.out.println(maintainer);
        List<Repair> list = repairService.findMyOrder(maintainer);
        System.out.println(list.size());
        PageInfo<Repair> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo);
        return pageInfo;
    }
    //查看我的未修订单
    @PostMapping("findMyOrderWithNo")
    public PageInfo<Repair> findMyOrderWithNo(@Param("maintainer")int maintainer, @RequestParam(defaultValue = "1") int pageNum){
        System.out.println(pageNum);
        PageHelper.startPage(pageNum, 3);
        List<Repair> list = repairService.findMyOrderWithNo(maintainer);
        PageInfo<Repair> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
    //查看我的已完工订单
    @PostMapping("findMyOrderWithYes")
    public PageInfo<Repair> findMyOrderWithYes(@Param("maintainer")int maintainer, @RequestParam(defaultValue = "1") int pageNum){
        System.out.println(pageNum);
        PageHelper.startPage(pageNum, 3);

        List<Repair> list = repairService.findMyOrderWithYes(maintainer);

        PageInfo<Repair> pageInfo = new PageInfo<>(list);

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
    //新建罚款单
    @PutMapping("insertFineOrder")
    public JsonData insertFineOrder(@Param("maintainer")int repairId,@Param("money") BigDecimal money){
        int r1=(int)(Math.random()*(10));//产生2个0-9的随机数
        int r2=(int)(Math.random()*(10));
        long now = System.currentTimeMillis();//一个13位的时间戳
        String orderNo =String.valueOf(r1)+String.valueOf(r2)+String.valueOf(now);
        Order order=new Order();
        order.setOrderNo(orderNo);
        order.setMoney(money);
        order.setRepairId(repairId);
        repairService.insertFineOrder(order);
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
