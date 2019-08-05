package com.qilu.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 故障类型实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fault implements Serializable {
    private Integer id;
    private String type; //类型
}
