package cn.edu.mju.entity;

import lombok.Getter;
import lombok.Setter;

/*
* 商品分类
* */
@Setter@Getter
public class ProductCategory {

    private Long productCategoryId;

    private Long shopId;

    private String productCategoryName;

    private Integer priority;

    private Long createTime;


}
