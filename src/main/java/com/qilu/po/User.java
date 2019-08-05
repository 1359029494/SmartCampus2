package com.qilu.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private Integer id;
    private String username; //登录的编号
    private String password;//密码
    private Integer role;//角色  0学生 1老师 2维修人员

    //学生实体类
     private Student student;
    //老师实体类
    private Teacher teacher;
    //维修人员实体类
    private Maintainer maintainer;

}
