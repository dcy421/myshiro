/**
 * Created by Administrator on 2017/8/21.
 */
$(function () {
    //iCheck for checkbox and radio inputs
    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    });
    $("#form-role-update").validate({
        rules:{
            name:{
                required:true,
                minlength:3,
                maxlength:16
            }
        },
        onkeyup : false,
        submitHandler:function(form){
            var ids = $.map(treeObj.getCheckedNodes(true), function (nodes) {
                return nodes.id
            });
            $.ajax({
                url : "/role/SaveSysRole",
                type: "Post",
                dataType : "json",
                traditional: true,//这里设置为true
                data:  $("#form-role-update").serialize()+"&flag=update&ids="+ids,
                success : function(result) {
                    //console.log(result);
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
                    //console.log("error"+XMLHttpRequest+"==="+textStatus+"----"+errorThrown);  程阔   18179761000
                }
            });
        }
    });
});
var treeObj ;
var setting = {
    check: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true
        }
    }
};
$.ajax({
    url : "/menu/getMenuList",
    type: "Post",
    dataType : "json",
    data:{ flag : 1,roleId : $("#id").val()},
    async: false,
    success : function(result) {
        treeObj = $.fn.zTree.init($("#treeDemo"), setting, result);
        treeObj.expandAll(true);//默认全部展开
    },
    error:function (XMLHttpRequest, textStatus, errorThrown) {
        //console.log(XMLHttpRequest);
        //console.log("error"+XMLHttpRequest+"==="+textStatus+"----"+errorThrown);
    }
});