package cn.edu.mju.dto;


import cn.edu.mju.entity.ProductCategory;
import cn.edu.mju.entity.Shop;
import cn.edu.mju.enums.ProductCategoryStateEnum;
import cn.edu.mju.enums.ShopStateEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
* 商品类别
* */
@Getter@Setter
public class ProductCategoryExecution {

    //结果状态

    private int state;

    //状态标识
    private String stateInfo;

    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution() {
    }

    /*
    * 操作失败
    * */
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //成功
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, List<ProductCategory> productCategoryList){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;

    }


}
