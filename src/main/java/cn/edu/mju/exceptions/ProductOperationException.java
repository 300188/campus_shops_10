package cn.edu.mju.exceptions;

public class ProductOperationException extends RuntimeException {


    public ProductOperationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
