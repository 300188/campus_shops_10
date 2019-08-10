
/*
* 前端店铺列表展示
* */

 var shoplist = {

     loading : false,
     maxItems : 99999,
     pageSize : 10,
     listUrl : '/frontend/listshops',
     searchDivUrl : '/frontend/listshopspageinfo',
     pageNum : 1,
     parentId : common.getQueryString('parentId'),
     areaId : '',
     shopCategoryId : '',
     shopName : '',

     //提供搜索的条件信息
     getSearchDivData:function () {

         var url = shoplist.searchDivUrl + '?' + 'parentId=' + shoplist.parentId;
         $.getJSON(url, function(data) {
                     if (data.success) {
                         var shopCategoryList = data.shopCategoryList;
                         var html = '';
                         shoplist.maxItems = data.count;
                         html += '<a href="#" class="button" data-category-id=""> 全部类别  </a>';
                         shopCategoryList
                             .map(function(item, index) {
                                 html += '<a href="#" class="button" data-category-id='
                                     + item.shopCategoryId
                                     + '>'
                                     + item.shopCategoryName
                                     + '</a>';
                             });
                         $('#shoplist-search-div').html(html);
                         var selectOptions = '<option value="">全部街道</option>';
                         var areaList = data.areaList;

                         areaList.map(function(item, index) {
                             selectOptions += '<option value="'
                                 + item.areaId + '">'
                                 + item.areaName + '</option>';
                         });
                         $('#area-search').html(selectOptions);
                     }
                 });


     },

     //显示列表
     addItems:function (pageSize, pageIndex) {

         // 生成新条目的HTML
         var url = shoplist.listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
             + pageSize + '&parentId=' + shoplist.parentId + '&areaId=' + shoplist.areaId
             + '&shopCategoryId=' + shoplist.shopCategoryId + '&shopName=' + shoplist.shopName;
         shoplist.loading = true;
         $.getJSON(url, function(data) {
             if (data.success) {
                 shoplist.maxItems = data.count;
                 var html = '';
                 data.shopList.map(function(item, index) {
                     html += '' + '<div class="card" data-shop-id="'
                         + item.shopId + '">' + '<div class="card-header">'
                         + item.shopName + '</div>'
                         + '<div class="card-content">'
                         + '<div class="list-block media-list">' + '<ul>'
                         + '<li class="item-content">'
                         + '<div class="item-media">' + '<img src="'
                         + item.shopImg + '" width="44">' + '</div>'
                         + '<div class="item-inner">'
                         + '<div class="item-subtitle">' + item.shopDesc
                         + '</div>' + '</div>' + '</li>' + '</ul>'
                         + '</div>' + '</div>' + '<div class="card-footer">'
                         + '<p class="color-gray">'
                         + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                         + '更新</p>' + '<span>点击查看</span>' + '</div>'
                         + '</div>';
                 });
                 $('.list-div').append(html);
                 var total = $('.list-div .card').length;
                 if (total >= shoplist.maxItems) {
                     // 加载完毕，则注销无限加载事件，以防不必要的加载
                     $.detachInfiniteScroll($('.infinite-scroll'));
                     // 删除加载提示符
                     $('.infinite-scroll-preloader').hide();
                 // }else{
                 //     $('.infinite-scroll-preloader').show();

                 }
                 shoplist.pageNum += 1;
                 shoplist.loading = false;
                 $.refreshScroller();
             }
         });


     },

     clickMethod:function () {

         //下滑屏幕自动进行分页搜索
         $(document).on('infinite', '.infinite-scroll-bottom', function() {
             if (shoplist.loading)
                 return;
             shoplist.addItems(shoplist.pageSize, shoplist.pageNum);
         });

         //点击店铺卡片进入店铺详情页
         $('.shop-list').on('click', '.card', function(e) {
             var shopId = e.currentTarget.dataset.shopId;
             window.location.href = '/frontend/shopdetail?shopId=' + shopId;
         });

         //选择新的店铺类别之后，重置页码，清空原先的店铺列表，安装新的类别去查询
         $('#shoplist-search-div').on(
             'click',
             '.button',
             function(e) {
                 if (shoplist.parentId) {// 如果传递过来的是一个父类下的子类
                     shoplist.shopCategoryId = e.target.dataset.categoryId;
                     if ($(e.target).hasClass('button-fill')) {
                         $(e.target).removeClass('button-fill');
                         shoplist.shopCategoryId = '';
                     } else {
                         $(e.target).addClass('button-fill').siblings()
                             .removeClass('button-fill');
                     }
                     $('.list-div').empty();
                     shoplist.pageNum = 1;
                     shoplist.addItems(shoplist.pageSize, shoplist.pageNum);
                 } else {// 如果传递过来的父类为空，则按照父类查询
                     shoplist.parentId = e.target.dataset.categoryId;
                     if ($(e.target).hasClass('button-fill')) {
                         $(e.target).removeClass('button-fill');
                         shoplist.parentId = '';
                     } else {
                         $(e.target).addClass('button-fill').siblings()
                             .removeClass('button-fill');
                     }
                     $('.list-div').empty();
                     shoplist.pageNum = 1;
                     shoplist.addItems(shoplist.pageSize, shoplist.pageNum);
                     shoplist.parentId = '';
                 }

             });

         $('#search').on('change', function(e) {
             shoplist.shopName = e.target.value;
             $('.list-div').empty();
             shoplist.pageNum = 1;
             shoplist.addItems( shoplist.pageSize, shoplist.pageNum);
         });

         $('#area-search').on('change', function() {
             shoplist.areaId = $('#area-search').val();
             $('.list-div').empty();
             shoplist.pageNum = 1;
             shoplist.addItems(shoplist.pageSize, shoplist.pageNum);
         });

         $('#me').click(function() {
             $.openPanel('#panel-left-demo');
         });

         $.init();
     }



};


$(function () {


    shoplist.getSearchDivData();

    shoplist.addItems(shoplist.pageSize,shoplist.pageNum);

    shoplist.clickMethod();


});



