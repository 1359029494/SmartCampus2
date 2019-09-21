package com.qilu.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.swing.SwingUtilities2;

import java.io.Serializable;

/**
 * @author 孙连辉
 * @version 1.0
 * @date 2019/9/18 16:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用于接收维修信息")
public class RepairResp implements Serializable {

    private Integer id;
    private String username;
    private Integer role;
    private String  phone;
    private String local;
    private String repairDate;
    private String name;
    private Integer find;
    private Integer serviceStatus;
    private String serviceDate;

}
