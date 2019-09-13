package com.qilu.mapper;

import com.qilu.po.Evaluate;
import com.qilu.po.Maintainer;
import com.qilu.po.Repair;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    /**
     * 功能描述:维修人查看评价
     * @param:
     * @return:
     * @auther: xy
     * @date:
     */
    @Select("select * from s_evaluate where maintainer_id=#{id}")
    List<Evaluate> findEvaluate(int id);
    /**
     * 功能描述:维修人查看个人信息
     * @param:
     * @return:
     * @auther: xy
     * @date:
     */
    @Select("select * from s_maintainer  where id=#{id}")
    Maintainer findMaintenance(int id);
    /**
     * 功能描述:维修人查看所有订单
     * @param:
     * @return:
     * @auther: xy
     * @date:
     */
    @Select("select * from t_repair where repair_status=0")
    List<Repair> findOrder();
    /**
     * 功能描述:维修人查看个人的订单
     * @param:
     * @return:
     * @auther: xy
     * @date:
     */
    @Select("select * from t_repair t left join s_receipt s on t.id=s.id where s.maintainer_id=#{maintainer_id} ")
    public List<Repair> findMyOrder(int maintainer_id);
    /**
     * 功能描述:罚钱
     * @param:
     * @return:
     * @auther: xy
     * @date:
     */
    @Update("update t_repair set fine=1 where id=#{id}")
    public int fine(int id);
    /**
     * 功能描述:完工
     * @param:
     * @return:
     * @auther: xy
     * @date:
     */
    @Update("update t_repair set service_status=1 where id=#{id}")
    public int finish(int id);
    /**
     * 功能描述:接单
     * @param:
     * @return:
     * @auther: xy
     * @date:
     */
    @Insert("update t_repair set repair_status=1  where id=#{id}")
    public int receipt(int id);
}
