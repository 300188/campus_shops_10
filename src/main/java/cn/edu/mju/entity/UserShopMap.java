package cn.edu.mju.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 顾客店铺积分映射
 */

@Getter@Setter
public class UserShopMap {


    private Long userShopId;

    private Long createTime;

    private Integer point;

    private PersonInfo user;

    private Shop shop;




}
