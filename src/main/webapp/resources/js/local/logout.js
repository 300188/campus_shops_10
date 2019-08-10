

var logout = {


    logout:function () {

        $.ajax({

            type:"post",
            url:"/local/logout",
            async:false,
            cache:false,
            success:function (data) {

                if(data.success){

                    var usertype = $("#log-out").attr("usuerType");

                    window.location.href = '/local/login?usertype=' + usertype;
                    return false;

                }

            },
            error:function (data,error) {
                alert(error);
            }



        });



    },


}