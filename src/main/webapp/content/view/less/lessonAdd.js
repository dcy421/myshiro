$(function () {
    //iCheck for checkbox and radio inputs
    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    });
    $("#form-admin-add").validate({
        rules:{
            lessonid:{
                required:true
            },
            teacherid:{
                required:true
            },
            address:{
                required:true
            },
            starttime:{
                required:true
            },
            endtime:{
                required:true
            },
            personcount:{
                required:true
            },
            unitprice:{
                required:true
            }

        },
        submitHandler:function(form){
            $.ajax({
                // url : "/lesson/SaveSysLesson",
                url : "/sched/SaveLessonschedule",
                type: "Post",
                dataType : "json",
                data:  $("#form-admin-add").serialize()+"&flag=add",
                success : function(result) {
                    if(result > 0) {
                        opaler();
                    } else {
                        opalerNO();
                    }
                    //刷新父级页面
                    parent.$table.bootstrapTable('refresh'); //再刷新DT
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    //console.log(XMLHttpRequest);
                    //console.log("error"+XMLHttpRequest+"==="+textStatus+"----"+errorThrown);
                }
            });
        }

    });

});