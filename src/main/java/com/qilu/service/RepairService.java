package com.qilu.service;

import com.qilu.po.Evaluate;
import com.qilu.po.Maintainer;
import com.qilu.po.Order;
import com.qilu.po.Repair;

import java.util.List;
import java.util.Map;

public interface RepairService {
    public List<Repair> findMyRepair(int roleType, int userId);

    public List<Evaluate> check(int maintainer);

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
    public List<Repair> findMyOrderWithYes(int maintainerId);

    //查看订单详细
    public Repair findOrderInfo(int id);

    //罚款
    public int fine(int id);

    //插入罚款表
    public int insertFineOrder(Order order);

    //完工
    public int finish(int id);

    //接单
    public int receipt(int id,int maintainer_id);

    //查询所有已接单数、未修单数、已完工单数
    public Map getNumber(int maintainer_id);
}
