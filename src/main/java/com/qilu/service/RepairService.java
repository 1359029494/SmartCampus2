package com.qilu.service;

import com.qilu.po.Maintainer;
import com.qilu.po.Order;
import com.qilu.po.Repair;

import java.util.List;

public interface RepairService {
    public List<Repair> findMyRepair(int roleType, int userId);

    public int insRepairSchool(Repair r);

    //查看维修人员个人信息
    public Maintainer findMyInfo(int id);

    //查看所有未接订单
    public List<Repair> findOrder();

    //查看已接订单
    public List<Repair> findMyOrder(int maintainer_id);

    //查看已接没修的订单
    public List<Repair> findMyOrderWithNo(int maintainer_id);

    //查看已接完工的订单
    public List<Repair> findMyOrderWithYes(int maintainer_id);

    //罚款
    public int fine(int id);

    //插入罚款表
    public int insertFineOrder(Order order);

    //完工
    public int finish(int id);

    //接单
    public int receipt(int id);
}
