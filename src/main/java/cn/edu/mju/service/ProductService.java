package cn.edu.mju.service;

import cn.edu.mju.dto.ProductExecution;
import cn.edu.mju.entity.Product;
import cn.edu.mju.exceptions.ProductOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

/*
* 商品
* */
public interface ProductService extends BaseService {

    ProductExecution getProductList(Product product, int pageIndex, int pageSize);

    /*
    * 根据id获取商品
    * */
    Product getProductById(Long productId);

    /*
    * 添加商品
    * */
    ProductExecution addProduct(Product product, CommonsMultipartFile thumbnail, List<CommonsMultipartFile> productImgs)
            throws ProductOperationException;

    /*
    * 修改商品
    * */
    ProductExecution modifyProduct(Product product, CommonsMultipartFile thumbnail,
                                   List<CommonsMultipartFile> productImgs) throws ProductOperationException;

}
