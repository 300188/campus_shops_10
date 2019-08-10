/*
* 操作店铺
* */
var shopoperation = {

    initUrl:"",
    registerShopUrl:"",

    //根据店铺id获取店铺信息
    shopInfoUrl:"",

    //修改
    editShopUrl:"",

    isEdit:false,

    shopId:null,

    //初始化参数
    init : function () {
        shopoperation.initUrl = '/shop/getshopinitinfo';
        shopoperation.registerShopUrl =  '/shop/registershop';
        shopoperation.shopInfoUrl = '/shop/getshopbyid?shopId=';
        shopoperation.editShopUrl = '/shop/modifyshop';
    },

    //获取初始化数据
    getShopInitInfo : function () {
        $.getJSON(shopoperation.initUrl, function (data){
            if (data.success) {
                var tempHtml = '';
                var tempAreaHtml = '';
                data.shopCategoryList.map(function (item, index) {
                    //商品分类
                    tempHtml += '<option data-id = "' + item.shopCategoryId + '">'
                        +item.shopCategoryName + '</option>';

                });
                //区域
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id = "' + item.areaId + '">'
                        +item.areaName + '</option>';

                });

                $('#shop-category').html(tempHtml);

                $('#area').html(tempAreaHtml);
            }
        });
    },


    //获取修改店铺信息的数据
    getShopInfo : function(shopId){

        shopoperation.shopInfoUrl = shopoperation.shopInfoUrl + shopId;

        $.getJSON(shopoperation.shopInfoUrl,function (data) {

            if(data.success){
                var shop = data.shop;
                shop.shopName = $('#shop-name').val(shop.shopName);
                shop.shopAddr = $('#shop-addr').val(shop.shopAddr);
                shop.phone = $('#shop-phone').val(shop.phone);
                shop.shopDesc = $('#shop-desc').val(shop.shopDesc);
                var shopCategory =  '<option data-id = "' + shop.shopCategory.shopCategoryId + '">'
                    +shop.shopCategory.shopCategoryName + '</option>';

                var tempAreaHtml = "";

                //区域
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id = "' + item.areaId + '">'
                        +item.areaName + '</option>';

                });

                $("#shop-category").html(shopCategory);
                $("#shop-category").attr('disabled','disabled');
                $("#area").html(tempAreaHtml);
                $("#area option[data-id='"+shop.area.areaId+"']").attr("selected","selected");

            }else{
                //todo
                //没有找到店铺信息
                shopoperation.isEdit = false;
                shopoperation.getShopInitInfo();
            }

            
        });


    },



    //提交表单

    submit : function () {

        var shop = {};
        if(shopoperation.isEdit){
            shop.shopId = shopoperation.shopId;
        }
        shop.shopName = $('#shop-name').val();
        shop.shopAddr = $('#shop-addr').val();
        shop.phone = $('#shop-phone').val();
        shop.shopDesc = $('#shop-desc').val();
        shop.shopCategory = {
            shopCategoryId : $('#shop-category').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        shop.area = {
            areaId : $('#area').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };

        var shopImg = $('#shop-img')[0].files[0];
        var formData = new FormData();
        formData.append('shopImg',shopImg);
        formData.append('shopStr',JSON.stringify(shop));
        var verifyCodeActual = $('#j_captcha').val();
        formData.append('verifyCodeActual',verifyCodeActual);
        $.ajax({
            type:'POST',
            url: shopoperation.isEdit ? shopoperation.editShopUrl : shopoperation.registerShopUrl,
            data:formData,
            contentType:false,
            processData:false,
            cache:false,
            success:function (result) {
                if(result.success){
                    $.toast('提交成功');
                }else{
                    $.toast('提交失败!'+result.errMsg);
                }
            //    更换验证码
                $('#captcha_img').click();
            }
        })


    },


    //验证联系电话
    validatetel : function() {
        var val = $("#shop-phone").val();
        var phone = parseInt(val);

        if(!isNaN(phone)){
            if(val.length == 11){
                return true;
            }else{
                return false;
            }
        } else{
            return false;
        }
    },
    //验证码为空校验
    validateVerifyCode : function(){

        var verifyCodeActual = $('#j_captcha').val();
        if(!verifyCodeActual){
            $.toast('请输入验证码');
            return false;
        }else{
            return true;
        }

    },
    validate:function () {
        //验证电话
        if(shopoperation.validatetel()){
            //验证验证码是否为空
            return shopoperation.validateVerifyCode();
        }else{
            return false;
        }
        //todo
    }

};


$(function () {

    shopoperation.init();

    //获取链接中的shopId的值
    shopoperation.shopId = common.getQueryString('shopId');

    shopoperation.isEdit = shopoperation.shopId ? true : false;


    if(!shopoperation.isEdit){
        //不是修改操作
        shopoperation.getShopInitInfo();
    }else{
        shopoperation.getShopInfo(shopoperation.shopId);
    }


    $('#submit').click(function () {
        //todo
        if(shopoperation.validate()){
            shopoperation.submit();
        }else{
            $.toast('请填写正确的联系电话!');
        }
    });

});
