package cn.edu.mju.dto;


import cn.edu.mju.entity.Shop;
import cn.edu.mju.enums.ShopStateEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
* 店铺操作返回结果封装
* */
@Getter@Setter
public class ShopExecution {

    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    //店铺的数量
    private int count;

    //操作的shop
    private Shop shop;

    //shop列表
    private List<Shop> shopList;

    public ShopExecution(){}

    //针对店铺操作失败的情况下 只返回相对的信息
    public ShopExecution(ShopStateEnum stateEnum){

        this.state = stateEnum.getState();

        this.stateInfo = stateEnum.getStateInfo();

    }

    //成功
    public ShopExecution(ShopStateEnum stateEnum,Shop shop){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shop = shop;

    }

    //成功
    public ShopExecution(ShopStateEnum stateEnum,List<Shop> shopList){

        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;

    }




}
