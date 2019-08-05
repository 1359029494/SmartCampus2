package com.qilu.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 校园小知识类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Knowledge implements Serializable {
    private Integer id;
    private String title;//标题
    private String content;//内容
    private Date pubDate;//发布日期
    private String photo;//图片或者照片

}
