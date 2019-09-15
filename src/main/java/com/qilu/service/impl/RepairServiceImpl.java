package com.qilu.service.impl;

import com.qilu.mapper.RepairMapper;
import com.qilu.po.Evaluate;
import com.qilu.po.Maintainer;
import com.qilu.po.Order;
import com.qilu.po.Repair;
import com.qilu.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RepairServiceImpl implements RepairService {
    @Autowired
    private RepairMapper repairMapper;

    @Override
    public List<Repair> findMyRepair(int roleType, int userId) {
        return repairMapper.findMyRepair(roleType, userId);
    }

    @Override
    public List<Evaluate> check(int maintainer) {
        return repairMapper.findEvaluate(maintainer);
    }

    @Override
    public int insRepairSchool(Repair r) {
        return repairMapper.insRepairSchool(r);
    }

    @Override
    public Maintainer findMyInfo(int id) {
        return null;
    }

    @Override
    public List<Repair> findOrder() {
        return repairMapper.findOrder();
    }

    @Override
    public List<Repair> findMyOrder(int maintainer_id) {
        return repairMapper.findMyOrder(maintainer_id);
    }

    @Override
    public List<Repair> findMyOrderWithNo(int maintainer_id) {
        return null;
    }

    @Override
    public List<Repair> findMyOrderWithYes(int maintainer_id) {
        return null;
    }

    @Override
    public Repair findOrderInfo(int id) {
        return repairMapper.findOrderInfo(id);
    }

    @Override
    public int fine(int id) {
        return repairMapper.fine(id);
    }

    @Override
    public int insertFineOrder(Order order) {
        return repairMapper.insertFineOrder(order);
    }

    @Override
    public int finish(int id) {
        return repairMapper.finish(id);
    }

    @Override
    public int receipt(int id, int maintainerId) {
        repairMapper.receiptT(id, maintainerId);
        return repairMapper.receipt(id);
    }

    @Override
    public Map getNumber(int maintainer_id) {
        int allNumber=repairMapper.countMyOrder(maintainer_id);
        int unfinishedNumber=repairMapper.countMyOrderNo(maintainer_id);
        int finishedNumber=allNumber-unfinishedNumber;
        Map<String,Integer> map=new HashMap<>();
        map.put("allNumber",allNumber);
        map.put("unfinishedNumber",unfinishedNumber);
        map.put("finishedNumber",finishedNumber);
        return map;
    }

    ;
}
