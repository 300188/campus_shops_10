
var changepsw = {

    change:function () {

        var usertype = common.getQueryString('usertype');
        var url = '/local/changelocalpwd';
        $('#submit').click(function() {

            var userName = $('#userName').val();
            var password = $('#password').val();
            var newPassword = $('#newPassword').val();
            var formData = new FormData();
            formData.append('userName', userName);
            formData.append('password', password);
            formData.append('newPassword', newPassword);
            var verifyCodeActual = $('#j_captcha').val();
            if (!verifyCodeActual) {
                $.toast('请输入验证码！');
                return;
            }
            formData.append("verifyCodeActual", verifyCodeActual);

            if(password == newPassword){
                $.toast('新密码要和旧密码不一致！');
                return;
            }

            $.ajax({
                url : url,
                type : 'POST',
                data : formData,
                contentType : false,
                processData : false,
                cache : false,
                success : function(data) {
                    if (data.success) {
                        $.toast('修改成功！');
                        /*if(usertype == 1){
                        window.location.href = '/frontend/index';
                        }else{

                        window.location.href = '/shop/shoplist';*/
                        }
                    } else {
                        $.toast('提交失败！');
                        $('#captcha_img').click();
                    }
                }
            });
        });


        $('#back').click(function() {
            window.location.href = '/local/login?usertype=2';
        });

    }


};


$(function () {

    changepsw.change();

});