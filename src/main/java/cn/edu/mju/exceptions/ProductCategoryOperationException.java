package cn.edu.mju.exceptions;

/*
* 商品分类异常* */
public class ProductCategoryOperationException extends RuntimeException {



    public ProductCategoryOperationException(String message) {
        super(message);
    }
}
