package cn.edu.mju.entity;


import lombok.Getter;
import lombok.Setter;

/*
* 店铺类别表
* */
@Setter@Getter
public class ShopCategory {

    private Long shopCategoryId;

    private String shopCategoryName;

    private String shopCategoryDesc;

    private String shopCategoryImg;

    private Integer priority;

    private Long createTime;

    private Long lastEditTime;

    //父亲节点
    private ShopCategory parent;


}
