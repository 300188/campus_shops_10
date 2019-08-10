package cn.edu.mju.entity;
/*
* 头条实体类
* */

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class HeadLine {

    private Long lineId;

    private String lineName;

    private String lineLink;

    private String lineImg;

    private Integer priority;

//    0不可用 1可用
    private Integer enableStatus;

    private Long createTime;

    private Long lastEditTime;


}
