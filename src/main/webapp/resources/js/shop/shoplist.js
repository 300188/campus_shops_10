
/*
* 店铺列表
* */

var shoplist = {

    loading : false,
    maxItems : 9999,
    pageSize : 9999,
    pageNum : 1,

    //判断店铺状态  -1 不可用 0 审核中 1 允许使用
    goShop:function (status, id) {
        if (status != 0 && status != -1) {
            return '<a href="/shop/shopmanage?shopId='+ id +'">进入</a>';
        } else {
            return '';
        }
    },

    shopStatus:function (status) {
        if (status == 0) {
            return '审核中';
        } else if (status == -1) {
            return '店铺非法';
        } else {
            return '审核通过';
        }
    },

    //处理店铺列表
    handleList:function (data) {

        var html = "";

        shoplist.maxItems = data.count;
        data.map(function (item,index) {

            html += '<div class="row row-shop"><div class="col-40">'+ item.shopName +'</div><div class="col-40">'+ shoplist.shopStatus(item.enableStatus) +'</div><div class="col-20">'+ shoplist.goShop(item.enableStatus, item.shopId) +'</div></div>';

        });

        $('.shop-wrap').html(html);

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


    },


    //处理用户相关
    handleUser:function (data) {

        $('#user-name').text(data.name);

    },


    //获取店铺列表
    getlist:function (e) {


        shoplist.loading = true;
        $.ajax({
            url:"/shop/getshoplist"+ '?' + 'pageIndex=' + shoplist.pageNum + '&pageSize='
            + shoplist.pageSize,
            type:"get",
            dataType:"json",
            success:function (data) {
                if(data.success){
                    //处理店铺列表
                    shoplist.handleList(data.shopList);

                //    处理用户相关
                    shoplist.handleUser(data.user);
                }

            }

        })

    }




};



$(function () {

    shoplist.getlist(this);

    //下滑屏幕自动进行分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function() {
        if (shoplist.loading)
            return;
        shoplist.getlist(this);
    });

});