package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.dao.ProductCategoryDao;
import cn.edu.mju.dao.ProductDao;
import cn.edu.mju.dto.ProductCategoryExecution;
import cn.edu.mju.entity.ProductCategory;
import cn.edu.mju.enums.ProductCategoryStateEnum;
import cn.edu.mju.exceptions.ProductCategoryOperationException;
import cn.edu.mju.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl extends BaseServiceImpl implements ProductCategoryService {


    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ProductDao productDao;


    @Override
    @Transactional
    public ProductCategoryExecution removeProductCategory(Long productCategoryId, Long shopId) throws ProductCategoryOperationException {

        try {

            // 需要先将此类别下的商品删除
            int effected = productDao.updateProductCategoryToNull(productCategoryId);

            if(effected < 0){
                throw new ProductCategoryOperationException("商品类别更新失败");
            }

            int effectedNum = productCategoryDao.deleteProductCategory(
                    productCategoryId, shopId);
            if (effectedNum <= 0) {
                throw new ProductCategoryOperationException("店铺类别删除失败");
            } else {
                return new ProductCategoryExecution(
                        ProductCategoryStateEnum.SUCCESS);
            }

        } catch (Exception e) {
            throw new ProductCategoryOperationException("deleteProductCategory error: "
                    + e.getMessage());
        }
    }



    @Override
    public List<ProductCategory> getProductCategoryList(Long shopId) {
        return productCategoryDao.queryProductCategory(shopId);
    }


    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {

        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                int effectedNum = productCategoryDao
                        .batchInsertProductCategory(productCategoryList);
                if (effectedNum <= 0) {
                    throw new ProductCategoryOperationException("店铺类别失败");
                } else {

                    return new ProductCategoryExecution(
                            ProductCategoryStateEnum.SUCCESS);
                }

            } catch (Exception e) {
                throw new ProductCategoryOperationException("batchAddProductCategory error: "
                        + e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(
                    ProductCategoryStateEnum.INNER_ERROR);
        }

    }


}
