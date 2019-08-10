
/*
* 商品详情
* */

var productdetail = {

    productId : common.getQueryString('productId'),
    productUrl : '/frontend/listproductdetailpageinfo?productId=',


    getinfo:function () {

        $.getJSON(productdetail.productUrl + productdetail.productId,
            function(data) {
                if (data.success) {
                    var product = data.product;
                    $('#product-img').attr('src', product.imgAddr);
                    $('#product-time').text(
                        new Date(product.lastEditTime)
                            .Format("yyyy-MM-dd"));
                    $('#product-name').text(product.productName);
                    $('#product-desc').text(product.productDesc);
                    if(product.point != undefined && product.point != null){

                        $('#product-point').text('购买可得' + product.point + '积分');
                    }

                    if(product.normalPrice != undefined){
                        $('#normalPrice').html('<del>' + product.normalPrice + '元' + '</del>');
                    }

                    if(product.promotionPrice != undefined){
                        $('#promotionPrice').text(product.promotionPrice + '元');
                    }

                    var imgListHtml = '';
                    product.productImgList.map(function(item, index) {
                        imgListHtml += '<div> <img src="'
                            + item.imgAddr + '"/></div>';
                    });
                    // 生成购买商品的二维码供商家扫描
                    imgListHtml += '<div> <img src="/frontend/generateqrcode4product?productId='
                        + product.productId + '"/></div>';
                    $('#imgList').html(imgListHtml);
                }
            });
        $('#me').click(function() {
            $.openPanel('#panel-left-demo');
        });
        $.init();
    }


};


$(function () {


    productdetail.getinfo();

});
