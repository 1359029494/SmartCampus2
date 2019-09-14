package com.qilu.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 保修表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repair  implements Serializable {

    private Integer id;
    private Integer role; //保修的角色
    private Integer userId;//保修用户id
    private String type;//维修类型
    private String local;//故障地点
    private String photo;//地点图片
    private Integer evaluateId;//评价表id
    private Date repairDate;//保修时间
    private Date serviceDate;//维修时间
    private Integer repairStatus;//是否有人接单 0未接单 1已接单
    private Integer serviceStatus;//是否维修完毕 0未  1已
    private Integer payStatus;//是否交了罚款 0未  1已
    private Integer fine;//是否罚钱 0罚钱  1不罚钱
    private String remarks;//备注
    //一对多，一个保修可以有多个评论
    private List<Evaluate> evaluates;
    //该维修被哪个维修工维修
    private Maintainer maintainer;
}
