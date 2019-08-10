
/*
* 商品类别管理
* */

var productcategorymanage = {


    getList:function () {

        $.ajax({
            url:"/shop/getproductcategorylist",
            type:"get",
            dataType:"json",
            success:function (data) {
                if(data.success){

                    var html = "";

                    data.productCategoryList.map(function (item,index) {

                        html += ''
                            + '<div class="row row-product-category now">'
                            + '<div class="col-33 product-category-name">'
                            + item.productCategoryName
                            + '</div>'
                            + '<div class="col-33">'
                            + item.priority
                            + '</div>'
                            + '<div class="col-33"><a href="#" onclick="productcategorymanage.removeNow()" class="button delete" data-id="'
                            + item.productCategoryId
                            + '">删除</a></div>'
                            + '</div>';
                    });

                    $('.category-wrap').html(html);

                }

            }

        })

    },

    /*
    * 点击新增时 动态的增加一行数据
    * */
    clickAddProductCategory:function () {


        var html = '<div class="row row-product-category temp">'
            + '<div class="col-33"><input class="category-input category" type="text" placeholder="分类名"></div>'
            + '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'
            + '<div class="col-33"><a href="#" class="button delete" onclick="productcategorymanage.removeTemp()">删除</a></div>'
            + '</div>';

        $('.category-wrap').append(html);

    },

    submit:function () {

        var tempArr = $('.temp');
        var productCategoryList = [];
        tempArr.map(function(index, item) {
            var tempObj = {};
            tempObj.productCategoryName = $(item).find('.category').val();
            tempObj.priority = $(item).find('.priority').val();

            if (tempObj.productCategoryName && tempObj.priority) {
                productCategoryList.push(tempObj);
            }
        });
        $.ajax({
            url : '/shop/addproductcategories',
            type : 'POST',
            data : JSON.stringify(productCategoryList),
            contentType : 'application/json',
            success : function(data) {
                if (data.success) {
                    $.toast('提交成功！');
                    productcategorymanage.getList();
                } else {
                    $.toast('提交失败,请填写完整再次提交！');
                }
            }
        });


    },


//    删除
    removeNow:function () {

        $('.category-wrap').on('click', '.row-product-category.now .delete',
            function(e) {
                var target = e.currentTarget;
                $.confirm('确定么?', function() {
                    $.ajax({
                        url : '/shop/removeproductcategory',
                        type : 'POST',
                        data : {
                            productCategoryId : target.dataset.id,
                            shopId : 1
                        },
                        dataType : 'json',
                        success : function(data) {
                            if (data.success) {
                                $.toast('删除成功！');
                                getList();
                            } else {
                                $.toast('删除失败！');
                            }
                        }
                    });
                });
            });

    },

    removeTemp:function () {

        $('.category-wrap').on('click', '.row-product-category.temp .delete',
            function(e) {
                console.log($(this).parent().parent());
                $(this).parent().parent().remove();

            });

    },


    
};


$(function () {

    productcategorymanage.getList();

});

