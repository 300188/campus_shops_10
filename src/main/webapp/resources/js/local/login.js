

var login = {

    loginCount : 0,

    /**
     * 执行reset操作
     */
    doReset:function () {
        $("#username").val("");
        $("#psw").val("");
        $("#captcha_img").val("");
    },


    //登录

    doLogin:function () {

        var loginUrl = '/local/logincheck';

        var usertype = common.getQueryString('usertype');



        var userName = $('#username').val();
        var password = $('#psw').val();
        var verifyCodeActual = $("#j_captcha").val();

        var needVerify = false;

        if(login.loginCount >= 3 ){
            if(!verifyCodeActual){
                $.toast('请输入验证码');
                return;
            }else{
                needVerify =true;
            }
        }

        //后台校验

        $.ajax({

            type:"post",
            url:loginUrl,
            data:{

                userName:userName,
                password:password,
                verifyCodeActual:verifyCodeActual,
                needVerify:needVerify


            },
            success:function(data) {

                if(data.success){

                    $.toast('登录成功');
                    login.loginCount = 0;

                    if(usertype == 1){

                        window.location.href = '/frontend/index';

                    }else{

                        window.location.href = '/shop/shoplist';

                    }

                }else{

                    $.toast('登录失败' + data.errMsg);
                    login.loginCount++ ;
                    if(login.loginCount >=3){
                        $('#verifyPart').show();
                    }
                    $('#captcha_img').click();

                }


            }

        });


    }


};


$(function () {

    $('#verifyPart').hide();

});


