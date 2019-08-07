package com.qilu.mapper;

import com.qilu.po.Repair;
import org.apache.ibatis.annotations.Insert;

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

    /**
     * 功能描述:报修（主要针对校园随手拍）
     * @param:
     * @return:
     * @auther: 治毅
     * @date:
     */
    @Insert("insert into t_repair values(default,#{role},#{userId},#{type},#{local},#{photo},null,#{repairDate},null,0,0,0)")
    public int insRepairSchool(Repair r);
}
