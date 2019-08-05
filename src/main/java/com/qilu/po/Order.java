package com.qilu.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 罚钱订单表
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Order implements Serializable {
    private Integer id;
    private String orderNo;//订单编号
    private Integer repairId;//保修的id
    private BigDecimal money;//罚钱金额
    private Date orderDate;//下单时间
    private Date payDate;//支付时间
    private Integer payState;//是否支付 0未 1已
    //该订单属于哪个保修
    private Repair repair;
}
