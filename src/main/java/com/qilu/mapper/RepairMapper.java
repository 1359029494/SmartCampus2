package com.qilu.mapper;

import com.qilu.po.Evaluate;
import com.qilu.po.Maintainer;
import com.qilu.po.Order;
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
    @Insert("insert into t_repair values(default,#{role},#{userId},#{type},#{local},#{photo},null,#{repairDate},null,0,0,0,0,#{remarks},#{phone})")
    public int insRepairSchool(Repair r);

    /**
     * 功能描述:维修人查看评价
     * @param:
     * @return:
     * @auther: xy
     * @date:
     */
    @Select("select * from t_evaluate where maintainer_id=#{maintainerId}")
    List<Evaluate> findEvaluate(int maintainerId);
    /**
     * 功能描述:维修人查看个人信息
     * @param:
     * @return:
     * @auther: xy
     * @date:
     */
    @Select("select * from t_maintainer  where id=#{id}")
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
    @Select("select t.* from t_repair t left join t_receipt s on t.id=s.id where s.maintainer_id=#{maintainerId} ")
    public List<Repair> findMyOrder(int maintainerId);

    /**
     * 功能描述:维修人查看待修的订单
     * @param:
     * @return:
     * @auther: xy
     * @date:
     */
    @Select("select t.* from t_repair t left join t_receipt s on t.id=s.id where s.maintainer_id=#{maintainerId} AND service_status=0")
    public List<Repair> findMyOrderWithNo(int maintainerId);

    /**
     * 功能描述:维修人查看订单详细
     * @param:
     * @return:
     * @auther: xy
     * @date:
     */
    @Select("SELECT *,\n" +
            "(CASE WHEN (select role from t_repair where id=#{id})=1 \n" +
            "THEN (select name from t_student where id=#{id}) \n" +
            "WHEN (select role from t_repair where id=#{id})=2 \n" +
            "THEN (select name from t_teacher where id=#{id})  END) \n" +
            "AS name FROM t_repair where id=#{id}")
    public Repair findOrderInfo(int id);
    /**
     * 功能描述:维修人查看完工的订单
     * @param:
     * @return:
     * @auther: xy
     * @date:
     */
    @Select("select * from t_repair t left join t_receipt s on t.id=s.id where s.maintainer_id=#{maintainerId} AND service_status=1 ")
    public List<Repair> findMyOrderWithYes(int maintainerId);
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
     * 功能描述:新建罚款单
     * @param:
     * @return:
     * @auther: xy
     * @date:
     */
    @Insert("insert into t_order (orderNo,repair_id,money,order_date) values (#{orderNo},#{repairId},#{money},#{orderDate})")
    public int insertFineOrder(Order order);
    /**
     * 功能描述:完工
     * @param:
     * @return:
     * @auther: xy
     * @date:
     */
    @Update("update t_repair set service_status=1,service_date=NOW()  where id=#{id}")
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
    @Insert("Insert into t_receipt (id,maintainer_id) values (#{id},#{maintainerId})")
    public int receiptT(int id,int maintainerId);

    /**
     * 功能描述:查询所有已接单数、未修单数、已完工单数
     * @param:
     * @return:
     * @auther: xy
     * @date:
     */
    @Select("select count(*) from t_repair t left join t_receipt s on t.id=s.id where s.maintainer_id=#{maintainerId}")
    public int countMyOrder(int maintainerId);
    @Select("select count(*) from t_repair t left join t_receipt s on t.id=s.id where s.maintainer_id=#{maintainerId} AND service_status=0")
    public int countMyOrderNo(int maintainerId);
}