/*
* 商铺管理
* */

var shopmanage = {

    getInfo:function () {

        var shopId = common.getQueryString("shopId");

        var shopInfoUrl = '/shop/getshopmanagementinfo?shopId=' + shopId;

        $.getJSON(shopInfoUrl,function (data) {

            if(data.redirect){
                window.location.href = data.url;
            }else{
                if(data.shopId != undefined && data.shopId != null){
                    shopId = data.shopId;
                }
                $('#shopInfo').attr('href','/shop/shopoperation?shopId=' + shopId);
            }

        });

    }


};


$(function () {

    shopmanage.getInfo();

});