
/*
* 商品操作
* */


var productoperation = {

    productId : common.getQueryString('productId'),
    shopId : 1,
    infoUrl : '/shop/getproductbyid?productId=',
    categoryUrl : '/shop/getproductcategorylistbyshopId?shopId=',
    productPostUrl : '/shop/modifyproduct',
    isEdit : false,

    /*
    * 获取初始化信息
    * */
    getInfo : function (id) {
        $.getJSON(productoperation.infoUrl + id, function(data) {
                    if (data.success) {
                        var product = data.product;
                        $('#product-name').val(product.productName);
                        $('#product-desc').val(product.productDesc);
                        $('#priority').val(product.priority);
                        $('#point').val(product.point);
                        $('#normal-price').val(product.normalPrice);
                        $('#promotion-price').val(
                            product.promotionPrice);

                        var optionHtml = '';
                        var optionArr = data.productCategoryList;
                        var optionSelected = product.productCategory.productCategoryId;
                        optionArr
                            .map(function(item, index) {
                                var isSelect = optionSelected === item.productCategoryId ? 'selected'
                                    : '';
                                optionHtml += '<option data-value="'
                                    + item.productCategoryId
                                    + '"'
                                    + isSelect
                                    + '>'
                                    + item.productCategoryName
                                    + '</option>';
                            });
                        $('#category').html(optionHtml);
                    }else{
                        productoperation.isEdit =false;
                        productoperation.getCategory();
                    }
                });
     },


    getCategory:function (id) {
        $.getJSON(productoperation.categoryUrl + id, function(data) {
            if (data.success) {
                var productCategoryList = data.productCategoryList;
                var optionHtml = '';
                productCategoryList.map(function(item, index) {
                    optionHtml += '<option data-value="'
                        + item.productCategoryId + '">'
                        + item.productCategoryName + '</option>';
                });
                $('#category').html(optionHtml);
            }
        });
    },


    submit:function () {
        var product = {};
        product.productName = $('#product-name').val();
        product.productDesc = $('#product-desc').val();
        product.priority = $('#priority').val();
        product.normalPrice = $('#normal-price').val();
        product.point = $('#point').val();
        product.promotionPrice = $('#promotion-price').val();
        product.productCategory = {
            productCategoryId : $('#category').find('option').not(
                function() {
                    return !this.selected;
                }).data('value')
        };
        product.productId = productoperation.productId;

        var thumbnail = $('#small-img')[0].files[0];
        console.log(thumbnail);
        var formData = new FormData();
        formData.append('thumbnail', thumbnail);
        $('.detail-img').map(
            function(index, item) {
                if ($('.detail-img')[index].files.length > 0) {
                    formData.append('productImg' + index,
                        $('.detail-img')[index].files[0]);
                }
            });
        formData.append('productStr', JSON.stringify(product));
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            $.toast('请输入验证码！');
            return;
        }
        formData.append("verifyCodeActual", verifyCodeActual);
        $.ajax({
            url : productoperation.productPostUrl,
            type : 'POST',
            data : formData,
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if (data.success) {
                    $.toast('提交成功！');
                    $('#captcha_img').click();
                } else {
                    $.toast('提交失败！'+ data.errMsg);
                    $('#captcha_img').click();
                }
            }
        });

    },


    appendFile:function () {
        $('.detail-img-div').on('change', '.detail-img:last-child', function() {
            if ($('.detail-img').length < 6) {
                $('#detail-img').append('<input type="file" class="detail-img">');
            }
        });
    },




};


$(function () {

    if (productoperation.productId) {
        productoperation.getInfo(productoperation.productId);
        productoperation.isEdit = true;
    } else {
        productoperation.getCategory(productoperation.shopId);
        productoperation.productPostUrl = '/shop/addproduct';
        productoperation.isEdit = false;
    }

    productoperation.appendFile();


});