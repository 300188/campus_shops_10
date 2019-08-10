package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.dao.ProductDao;
import cn.edu.mju.dao.ProductImgDao;
import cn.edu.mju.dto.ProductExecution;
import cn.edu.mju.entity.Product;
import cn.edu.mju.entity.ProductCategory;
import cn.edu.mju.entity.ProductImg;
import cn.edu.mju.enums.ProductStateEnum;
import cn.edu.mju.exceptions.ProductOperationException;
import cn.edu.mju.service.ProductService;
import cn.edu.mju.util.ImageUtil;
import cn.edu.mju.util.PageCalculator;
import cn.edu.mju.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl extends BaseServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;


    @Override
    public ProductExecution getProductList(Product product, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        try{
            List<Product> productList = productDao.queryProductList(product, rowIndex, pageSize);
            //获取数量
            int count = productDao.queryProductCount(product);
            ProductExecution pe = new ProductExecution();
            pe.setProductList(productList);
            pe.setCount(count);
            return pe;
        }catch (Exception e){
            throw new ProductOperationException(e.getMessage());
        }

    }

    @Override
    public Product getProductById(Long productId) {
        Product product = productDao.queryProductByProductId(productId);
        if(null == product.getProductCategory()){
            product.setProductCategory(new ProductCategory());
        }
        return product;
    }


    @Override
    @Transactional
    public ProductExecution addProduct(Product product, CommonsMultipartFile thumbnail, List<CommonsMultipartFile> productImgs) throws ProductOperationException {

        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setCreateTime(System.currentTimeMillis());
            product.setLastEditTime(System.currentTimeMillis());
            product.setEnableStatus(1);
            if (thumbnail != null) {
                //如果商品缩略图不为空则添加
                addThumbnail(product, thumbnail);
            }
            try {
                //创建商品信息
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品失败");
                }else{
                    if (productImgs != null && productImgs.size() > 0) {
                        //如果商品详情图不为空
                        addProductImgs(product, productImgs);
                    }
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品失败:" + e.toString());
            }

            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }


    }


    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, CommonsMultipartFile thumbnail, List<CommonsMultipartFile> productImgs) throws ProductOperationException {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setLastEditTime(System.currentTimeMillis());
            if (thumbnail != null) {
                //先查出此商品
                Product tempProduct = productDao.queryProductByProductId(product.getProductId());
                if (tempProduct.getImgAddr() != null) {
                    //删除原有的图片文件
                    PathUtil.deleteFile(tempProduct.getImgAddr());
                }
                addThumbnail(product, thumbnail);
            }
            if (productImgs != null && productImgs.size() > 0) {
                //删除数据库原有的图片地址
                deleteProductImgs(product.getProductId());
                addProductImgs(product, productImgs);
            }
            try {
                //更新
                int effectedNum = productDao.updateProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("更新商品信息失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (Exception e) {
                throw new ProductOperationException("更新商品信息失败:" + e.toString());
            }
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }


    private void addProductImgs(Product product, List<CommonsMultipartFile> productImgs) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());

        List<ProductImg> productImgList = new ArrayList<>();
        for (CommonsMultipartFile img : productImgs) {
            String imgAddr = ImageUtil.generateNormalImg(img,dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(System.currentTimeMillis());
            productImgList.add(productImg);
        }
        try {
            int effectedNum = productImgDao.batchInsertProductImg(productImgList);
            if (effectedNum <= 0) {
                throw new ProductOperationException("创建商品详情图片失败");
            }
        } catch (Exception e) {
            throw new ProductOperationException("创建商品详情图片失败:" + e.toString());
        }

    }

    private void deleteProductImgs(long productId) {
        //获取此商品的详情图
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        for (ProductImg productImg : productImgList) {
            PathUtil.deleteFile(productImg.getImgAddr());
        }
        productImgDao.deleteProductImgByProductId(productId);
    }

    private void addThumbnail(Product product, CommonsMultipartFile thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }

}
