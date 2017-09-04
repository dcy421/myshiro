/**
 * Created by Administrator on 2017/8/21.
 */
var ids =[];
$(function () {
    //iCheck for checkbox and radio inputs
    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    });
    $("#form-role-add").validate({
        rules:{
            name:{
                required:true,
                minlength:3,
                maxlength:16,
                remote: {
                    url: "/role/getRepeatRoleName",     //后台处理程序
                    type: "post",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {
                        roleName: function() {
                            return $("#name").val();
                        }
                    },//要传递的数据
                    dataFilter: function(data) { //返回结果
                        if (data == 0)
                            return true;
                        else
                            return false;
                    }
                }
            },
            loginFlag:{
                required:true,
            }
        },
        messages:{
            name:{
                remote:"角色名已经注册，请重新输入"
            }
        },
        onkeyup : false,
        submitHandler:function(form){
            $.ajax({
                url : "/role/SaveSysRole",
                type: "Post",
                traditional: true,//这里设置为true
                dataType : "json",
                data:  $("#form-role-add").serialize()+"&flag=add&ids="+ids,
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
                    //console.log("error"+XMLHttpRequest+"==="+textStatus+"----"+errorThrown);
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
    },
    callback: {
        onCheck: zTreeOnCheck
    }
};
$.ajax({
    url : "/menu/getMenuList",
    type: "Post",
    dataType : "json",
    data :{flag:0},
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
//tree选中和取消事件
function zTreeOnCheck() {
    ids = $.map(treeObj.getCheckedNodes(true), function (nodes) {
        return nodes.id
    });//获取选中的数据  返回数组
    //console.log(ids);
}