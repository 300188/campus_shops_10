package cn.edu.mju.entity;

import lombok.Getter;
import lombok.Setter;

/*
* 商品详情图片
* */
@Getter@Setter
public class ProductImg {

    private Long productImgId;

    //图片地址
    private String imgAddr;

    //图片描述
    private String imgDesc;

    private Integer priority;

    private Long createTime;

    //商品id
    private Long productId;

}
