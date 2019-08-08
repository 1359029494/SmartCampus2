package com.qilu.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 学生实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    private Integer id;
    private String name;//学生名字
    private Integer sex;//性别 0男  1女
    private String phone;//手机号
    private String stuNo;//学生学号
    private String college;//大学名字
    private String photo;//头像地址
    //学生保修过得
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Repair> repairs;
    //学生评价过得
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Evaluate> evaluates;
}
