package cn.edu.mju.dao;

import java.util.List;

import cn.edu.mju.entity.ProductImg;
import org.apache.ibatis.annotations.Param;

public interface ProductImgDao extends BaseDao {


	/*
	* 通过商品id查询详情图片
	* */
	List<ProductImg> queryProductImgList(Long productId);

	/*
	* 批量添加详情图片
	* */
	int batchInsertProductImg(@Param("productImgList") List<ProductImg> productImgList);

	/*
	* 根据商品id删除详情图片
	* */
	int deleteProductImgByProductId(Long productId);
}
