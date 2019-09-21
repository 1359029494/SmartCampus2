package com.qilu.mapper;

import com.qilu.po.Order;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

public interface OrderMapper {
    //将订单修改为已支付
    @Update("update t_order set pay_date=#{pay_date} pay_state=1 where repair_id=#{repair_id}")
    public int updOrder2HasPay(Order order);
}
