package cn.edu.mju.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
* 商品
* */
@Getter@Setter
public class Product {

    private Long productId;

    private String productName;

    private String productDesc;

    //简略图
    private String imgAddr;

    //正常价格
    private String normalPrice;

    //折扣价
    private String promotionPrice;

    //积分
    private Integer point;

    private Integer priority;

    private Long createTime;

    private Long lastEditTime;

    //-1 不可用 0 下架 1 在前端站式系统展示
    private Integer enableStatus;

    private List<ProductImg> productImgList;

    private ProductCategory productCategory;

    private Shop shop;

}
