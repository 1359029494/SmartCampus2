package com.qilu.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 老师实体类
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Teacher implements Serializable {
    private Integer id;
    private String name;//老师名字
    private Integer sex;//性别 0男 1女
    private String phone;//手机号
    private String teaNo;//老师编号（工号）
    private String college;//所属学院
    private String photo;//头像

    //老师保修过得
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Repair> repairs;
    //老师评价过得
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Evaluate> evaluates;
}
