package cn.edu.mju.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
* 区域实体类
* */
@Getter@Setter@ToString
public class Area {

    private Long areaId;

    private String areaName;

    //权重
    private Integer priority;

    //创建时间
    private Long createTime;

    //更新时间
    private Long lastEditTime;


}
