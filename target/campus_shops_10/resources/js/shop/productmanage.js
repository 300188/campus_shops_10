/*
* 商品管理
* */

var productmanage = {

    loading : false,
    maxItems : 9999,
    pageSize : 9999,
    pageNum : 1,
    shopId : 1,
    listUrl : '/shop/listproductsbyshop',
    deleteUrl : '/shop/modifyproduct',

    getList:function(shopId) {

        productmanage.loading = true;

        $.getJSON(productmanage.listUrl + '?' + 'pageIndex=' + productmanage.pageNum + '&pageSize='
            + productmanage.pageSize + '&+ shopId=' + productmanage.shopId, function(data) {
            if (data.success) {
                var productList = data.productList;
                productmanage.maxItems = data.count;
                var tempHtml = '';
                productList.map(function(item, index) {
                    var textOp = "下架";
                    var contraryStatus = 0;
                    if (item.enableStatus == 0) {
                        textOp = "上架";
                        contraryStatus = 1;
                    } else {
                        contraryStatus = 0;
                    }
                    tempHtml += '' + '<div class="row row-product">'
                        + '<div class="col-30">'
                        + item.productName
                        + '</div>'
                        + '<div class="col-20">'
                        + item.point
                        + '</div>'
                        + '<div class="col-50">'
                        + '<a href="#" class="edit" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">编辑</a>'
                        + '<a href="#" class="delete" data-id="'
                        + item.productId
                        + '" data-status="'
                        + contraryStatus
                        + '">'
                        + textOp
                        + '</a>'
                        + '<a href="#" class="preview" data-id="'
                        + item.productId
                        + '" data-status="'
                        + item.enableStatus
                        + '">预览</a>'
                        + '</div>'
                        + '</div>';
                });
                $('.product-wrap').html(tempHtml);
                var total = $('.list-div .card').length;
                if (total >= productmanage.maxItems) {
                    // 加载完毕，则注销无限加载事件，以防不必要的加载
                    // $.detachInfiniteScroll($('.infinite-scroll'));
                    // 删除加载提示符
                    $('.infinite-scroll-preloader').hide();
                }else{
                    $('.infinite-scroll-preloader').show();

                }
                productmanage.pageNum += 1;
                productmanage.loading = false;
                $.refreshScroller();
            }else{

            }
        });

    },

    deleteItem:function (id, enableStatus) {
        var product = {};
        product.productId = id;
        product.enableStatus = enableStatus;
        $.confirm('确定么?', function() {
            $.ajax({
                url : productmanage.deleteUrl,
                type : 'POST',
                data : {
                    productStr : JSON.stringify(product),
                    statusChange : true
                },
                dataType : 'json',
                success : function(data) {
                    if (data.success) {
                        $.toast('操作成功！');
                        productmanage.getList();
                    } else {
                        $.toast('操作失败！');
                    }
                }
            });
    });
}



};


$(function () {


    productmanage.getList(productmanage.shopId);

    $('.product-wrap').on('click', 'a', function(e) {
                var target = $(e.currentTarget);
                if (target.hasClass('edit')) {
                    window.location.href = '/shop/productoperation?productId='
                        + e.currentTarget.dataset.id;
                } else if (target.hasClass('delete')) {
                    productmanage.deleteItem(e.currentTarget.dataset.id,
                        e.currentTarget.dataset.status);
                } else if (target.hasClass('preview')) {
                    window.location.href = '/frontend/productdetail?productId='
                        + e.currentTarget.dataset.id;
                }
            });

    $('#new').click(function() {
        window.location.href = '/shop/productedit';
    });

    //下滑屏幕自动进行分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function() {
        if (productmanage.loading)
            return;
        productmanage.getList(productmanage.shopId);
    });

});