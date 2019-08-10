

/*
* 前端首页
* */
var index = {

    //获取初始化信息
    url : '/frontend/listmainpageinfo',

    getInfo:function () {

        $.getJSON(index.url, function (data) {
            if (data.success) {
                var headLineList = data.headLineList;
                var swiperHtml = '';
                headLineList.map(function (item, index) {
                    swiperHtml += ''
                        + '<div class="swiper-slide img-wrap">'
                        +      '<img class="banner-img" src="'+ item.lineImg +'" alt="'+ item.lineName +'">'
                        + '</div>';
                });
                $('.swiper-wrapper').html(swiperHtml);
                $(".swiper-container").swiper({
                    autoplay: 2000,
                    //鼠标放到上面轮播图是否停止
              autoplayDisableOnInteraction: false
                });
                var shopCategoryList = data.shopCategoryList;
                var categoryHtml = '';
                shopCategoryList.map(function (item, index) {
                    categoryHtml += ''
                        +  '<div class="col-50 shop-classify" data-category='+ item.shopCategoryId +'>'
                        +      '<div class="word">'
                        +          '<p class="shop-title">'+ item.shopCategoryName +'</p>'
                        +          '<p class="shop-desc">'+ item.shopCategoryDesc +'</p>'
                        +      '</div>'
                        +      '<div class="shop-classify-img-warp">'
                        +          '<img class="shop-img" src="'+ item.shopCategoryImg +'">'
                        +      '</div>'
                        +  '</div>';
                });
                $('.row').html(categoryHtml);
            }else{
                $.toast(data.errMsg);
            }
        });

    },


    clickMe:function () {

        $('#me').click(function () {
            $.openPanel('#panel-left-demo');
        });
    },

    //点击此分类 去此分类下的商铺页面
    clickShopClassify:function () {

        $('.row').on('click', '.shop-classify', function (e) {
            var shopCategoryId = e.currentTarget.dataset.category;
            var newUrl = '/frontend/shoplist?parentId=' + shopCategoryId;
            window.location.href = newUrl;
        });

    }



};


$(function () {

    index.getInfo();

    index.clickMe();

    index.clickShopClassify();



});