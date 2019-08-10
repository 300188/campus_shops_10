package cn.edu.mju.entity;

import lombok.Getter;
import lombok.Setter;

/*
* 店铺实体类
* */
@Getter@Setter
public class Shop {

    private Long shopId;

    private String shopName;

    //店铺描述
    private String shopDesc;

    //店铺的地址
    private String shopAddr;

    private String phone;

    private String shopImg;

    //权重
    private Integer priority;

    private Long createTime;

    private Long lastEditTime;

    //-1 不可用 0 审核中 1 可用
    private Integer enableStatus;

    //超级管理员给店家的建议
    private String advice;

    //区域
    private Area area;

    //拥有者
    private PersonInfo owner;

    //店铺类别
    private ShopCategory shopCategory;




}
