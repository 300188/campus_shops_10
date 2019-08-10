
var shopdetail = {


     loading : false,
     maxItems : 20,
     pageSize : 10,

     listUrl : '/frontend/listproductsbyshop',

     pageNum : 1,
     shopId : common.getQueryString('shopId'),
     productCategoryId : '',
     productName : '',

     searchDivUrl : '/frontend/listshopdetailpageinfo?shopId=',


    getSearchDivData:function () {

        var url = shopdetail.searchDivUrl + shopdetail.shopId;
        $.getJSON(url, function(data) {
                    if (data.success) {
                        var shop = data.shop;
                        $('#shop-cover-pic').attr('src', shop.shopImg);
                        $('#shop-update-time').html(
                            new Date(shop.lastEditTime)
                                .Format("yyyy-MM-dd"));
                        $('#shop-name').html(shop.shopName);
                        $('#shop-desc').html(shop.shopDesc);
                        $('#shop-addr').html(shop.shopAddr);
                        $('#shop-phone').html(shop.phone);

                        var productCategoryList = data.productCategoryList;
                        var html = '';
                        productCategoryList
                            .map(function(item, index) {
                                html += '<a href="#" class="button" data-product-search-id='
                                    + item.productCategoryId
                                    + '>'
                                    + item.productCategoryName
                                    + '</a>';
                            });
                        $('#shopdetail-button-div').html(html);
                    }
                });


    },


    addItems:function (pageSize,pageIndex) {

// 生成新条目的HTML
        var url = shopdetail.listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&productCategoryId=' + shopdetail.productCategoryId
            + '&productName=' + shopdetail.productName + '&shopId=' + shopdetail.shopId;
        shopdetail.loading = true;
        $.getJSON(url, function(data) {
            if (data.success) {
                shopdetail.maxItems = data.count;
                var html = '';
                data.productList.map(function(item, index) {
                    html += '' + '<div class="card" data-product-id='
                        + item.productId + '>'
                        + '<div class="card-header">' + item.productName
                        + '</div>' + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">' + '<img src="'
                        + item.imgAddr + '" width="44">' + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + item.productDesc
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + '更新</p>' + '<span>点击查看</span>' + '</div>'
                        + '</div>';
                });
                $('.list-div').append(html);
                var total = $('.list-div .card').length;
                if (total >= shopdetail.maxItems) {
                    $.detachInfiniteScroll($('.infinite-scroll'));
                    // 删除加载提示符
                    $('.infinite-scroll-preloader').hide();
                // }else{
                //
                //     $('.infinite-scroll-preloader').show();
                //
                }
                shopdetail.pageNum += 1;
                shopdetail.loading = false;
                $.refreshScroller();
            }
        });


    },

    onClick:function () {

        $(document).on('infinite', '.infinite-scroll-bottom', function() {
            if (shopdetail.loading)
                return;
            shopdetail.addItems(shopdetail.pageSize, shopdetail.pageNum);
        });

        $('#shopdetail-button-div').on(
            'click',
            '.button',
            function(e) {
                shopdetail.productCategoryId = e.target.dataset.productSearchId;
                if (shopdetail.productCategoryId) {
                    if ($(e.target).hasClass('button-fill')) {
                        $(e.target).removeClass('button-fill');
                        shopdetail.productCategoryId = '';
                    } else {
                        $(e.target).addClass('button-fill').siblings()
                            .removeClass('button-fill');
                    }
                    $('.list-div').empty();
                    shopdetail.pageNum = 1;
                    shopdetail.addItems(shopdetail.pageSize, shopdetail.pageNum);
                }
            });

        $('.list-div')
            .on(
                'click',
                '.card',
                function(e) {
                    var productId = e.currentTarget.dataset.productId;
                    window.location.href = '/frontend/productdetail?productId='
                        + productId;
                });

        $('#search').on('change', function(e) {
            shopdetail.productName = e.target.value;
            $('.list-div').empty();
            shopdetail.pageNum = 1;
            shopdetail.addItems(shopdetail.pageSize, shopdetail.pageNum);
        });

        $('#me').click(function() {
            $.openPanel('#panel-left-demo');
        });
        $.init();

    }




};


$(function () {


    shopdetail.getSearchDivData();

    shopdetail.addItems(shopdetail.pageSize,shopdetail.pageNum);

    shopdetail.onClick();


});