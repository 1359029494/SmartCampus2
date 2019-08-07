package com.qilu.mapper;

import com.qilu.po.Repair;

import java.util.List;

public interface RepairMapper {
    /**
     * 功能描述:老师或者学生查看个人的全部报修信息
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    public List<Repair> findMyRepair(int roleType, int userId);
}
