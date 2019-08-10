package cn.edu.mju.exceptions;

/*
* 店铺操作异常
* */
public class ShopOperationException extends RuntimeException {

    public ShopOperationException(String message) {
        super(message);
    }
}
