package com.qilu.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 维修工实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Maintainer implements Serializable {
    private Integer id;
    private String name;//维修工名字
    private Integer sex;//性别 0男 1女
    private String phone;//手机号
    private String maiNo;//工号

    //一对多
    private List<Repair> repairs;
}
