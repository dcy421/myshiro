/**
 * Created by Administrator on 2017/8/21.
 */
$(function () {
    //iCheck for checkbox and radio inputs
    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    });
    $("#form-admin-add").validate({
        rules:{
            password:{
                required:true,
                minlength:5,
                maxlength:16,
            },
            newpassword:{
                required:true,
                minlength:5,
                maxlength:16
            },
            qrpassword:{
                required:true,
                minlength:5,
                maxlength:16,
                equalTo: "#newpassword"
            },
        },
        onkeyup : false,
        submitHandler:function(form){
            $.ajax({
                url: "/user/ModifyUser",
                type: "Post",
                dataType: "json",
                data: $("#form-admin-add").serialize(),
                success: function (result) {
                    if (result == 2 && result != "") {
                        opalerNO();
                    } else if(result == 1 && result != ""){
                        opaler();
                    } else{
                        opalerNO();
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    //console.log(XMLHttpRequest);
                    //console.log("error"+XMLHttpRequest+"==="+textStatus+"----"+errorThrown);
                }
            });
        }
    });
});