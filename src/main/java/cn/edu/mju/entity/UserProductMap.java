package cn.edu.mju.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 顾客消费的商品映射
 */
@Setter@Getter
public class UserProductMap {

    private Long userProductId;

    private Long createTime;

    private Integer point;

    //顾客信息
    private PersonInfo user;

    //商品信息
    private Product product;

    //店铺信息
    private Shop shop;

    //操作员信息
    private PersonInfo operator;



}
