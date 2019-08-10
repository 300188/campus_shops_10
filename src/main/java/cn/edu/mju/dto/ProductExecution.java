package cn.edu.mju.dto;

import cn.edu.mju.entity.Product;
import cn.edu.mju.enums.ProductStateEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
* 商品
* */
@Getter@Setter
public class ProductExecution {

    // 结果状态
    private int state;

    // 状态标识
    private String stateInfo;

    // 数量
    private int count;

    // 操作的product（增删改商品的时候用）
    private Product product;

    // 获取的product列表(查询商品列表的时候用)
    private List<Product> productList;

    public ProductExecution() {
    }

    // 失败的构造器
    public ProductExecution(ProductStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 成功的构造器
    public ProductExecution(ProductStateEnum stateEnum, Product product) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.product = product;
    }

    // 成功的构造器
    public ProductExecution(ProductStateEnum stateEnum,
                            List<Product> productList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productList = productList;
    }

}
