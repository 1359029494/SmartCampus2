package com.qilu.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBean implements Serializable {

    private String respDesc;

    private String smsId;

    private List<Object> list;

    private String respCode;

    private String code;

}
