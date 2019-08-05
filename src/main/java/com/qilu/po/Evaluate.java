package com.qilu.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 评价表
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Evaluate implements Serializable {
    private Integer id;
    private Integer role;//角色
    private Integer userId;//角色id
    private String content;//评价内容
    private Integer start;//星级
    //学生实体类
    private Student student;
    //老师实体类
    private Teacher teacher;
    //评价属于哪个维修
    private Repair repair;
}
