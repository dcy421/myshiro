/**
 * Created by Administrator on 2017/8/17.
 */
var treeObj ;
var DeptreeNode;
var ids =[];
var flag =0;
var setting = {
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        onClick: zTreeOnClick
    }
};

$(function () {
    //创建左侧tree
    getDepartment();
    $("#dep_div").hide();
    //添加按钮
    $("#dep_add").click(function () {
        flag = 0;
        if (DeptreeNode == undefined){
            $.fn.modalAlert("请选择上级部门","error");
            return;
        }
        if(DeptreeNode != undefined && DeptreeNode.type == 5){
            $.fn.modalAlert("班级下面不可以再添加！！","error");
            return;
        }
        //console.log(DeptreeNode);
        $("#dep_title").text("添加");
        //添加类型
        getDepTypeAdd(DeptreeNode.type);
        setDepValAdd();
        $("#dep_div").show();
    });
    //修改
    $("#dep_upda").click(function () {
        flag = 1;
        if (DeptreeNode == undefined){
            $.fn.modalAlert("请选择修改部门","error");
            return;
        }
        //添加类型
        getDepTypeUpd(DeptreeNode.type);
        setDepValUpd();
        //console.log(DeptreeNode);
        $("#dep_title").text("修改");
        $("#dep_div").show();
    });
    //删除
    $("#dep_del").click(function () {
        if (DeptreeNode.isParent){
            $.fn.modalAlert("请选择子部门！！","error");
            return;
        }
        //只是子节点可以删除
        $.fn.modalConfirm ('确定要删除所选部门吗？', function () {
            $.ajax({
                url:'/depm/delDepartment',
                type: "Post",
                data:{id:DeptreeNode.id},
                dataType : "json",
                success:function(result){
                    if(result > 0){
                        layer.msg('操作成功!',{icon:1,time:1000},function(index){
                            getDepartment();
                        });
                    }else {
                        layer.msg('操作失败!',{icon:2,time:1000},function(index){
                            getDepartment();
                        });
                    }
                    $("#dep_div").hide();
                }
            });
        });
    });
    //保存按钮
    $("#save_dep").click(function () {
        //添加
        if(flag == 0){
            $.ajax({
                url : "/depm/saveDepartment",
                type: "Post",
                dataType : "json",
                data:  $("#form-dep-add").serialize()+"&flag="+flag,
                success : function(result) {
                    if(result > 0) {
                        layer.msg('操作成功!',{icon:1,time:1000},function(index){
                            getDepartment();
                        });
                    } else {
                        layer.msg('操作失败!',{icon:2,time:1000},function(index){
                            getDepartment();
                        });
                    }
                    $("#dep_div").hide();
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    //console.log(XMLHttpRequest);
                    //console.log("error"+XMLHttpRequest+"==="+textStatus+"----"+errorThrown);
                }
            });
        }else {//修改
            $.ajax({
                url : "/depm/saveDepartment",
                type: "Post",
                dataType : "json",
                data:  $("#form-dep-add").serialize()+"&flag="+flag,
                success : function(result) {
                    if(result > 0) {
                        layer.msg('操作成功!',{icon:1,time:1000},function(index){
                            getDepartment();
                        });
                    } else {
                        layer.msg('操作失败!',{icon:2,time:1000},function(index){
                            getDepartment();
                        });
                    }
                    $("#dep_div").hide();
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    //console.log(XMLHttpRequest);
                    //console.log("error"+XMLHttpRequest+"==="+textStatus+"----"+errorThrown);
                }
            });
        }

    });

});

//根据type 查询应该添加哪些
function getDepTypeAdd(type) {
    var selectStr = "";
    switch (type){
        case 0://国级添加使用
            selectStr += "<option value='1'>省</option>";
            break;
        case 1://省级添加使用
            selectStr += "<option value='2'>市</option>";
            break;
        case 2://市级添加使用
            selectStr += "<option value='3'>区（县）</option>";
            break;
        case 3://区 县级添加使用
            selectStr += "<option value='4'>学校</option>";
            break;
        case 4://学校级添加使用
            selectStr += "<option value='5'>班级</option>";
            break;
        case 5://班级添加
            break;
    }
    $("#type").html(selectStr);
    $("#type").prop("disabled",false);
}
function getDepTypeUpd(type) {
    var selectStr = "";
    switch (type){
        case 0://国级添加使用
            selectStr += "<option value='0'>国</option>";
            break;
        case 1://国级添加使用
            selectStr += "<option value='1'>省</option>";
            break;
        case 2://省级添加使用
            selectStr += "<option value='2'>市</option>";
            break;
        case 3://市级添加使用
            selectStr += "<option value='3'>区（县）</option>";
            break;
        case 4://区 县级添加使用
            selectStr += "<option value='4'>学校</option>";
            break;
        case 5://学校级添加使用
            selectStr += "<option value='5'>班级</option>";
            break;
        case 5://班级添加
            break;
    }
    $("#type").html(selectStr);
    $("#type").prop("disabled",true);
}
//添加默认赋值
function setDepValAdd() {
    $("#name").val("");
    $("#parentname").val(DeptreeNode.name);
    $("#parent").val(DeptreeNode.id);
    $("#province").val(DeptreeNode.province);
    $("#city").val(DeptreeNode.city);
    $("#area").val(DeptreeNode.area);
    $("#school").val(DeptreeNode.school);
    if(DeptreeNode.parentids != "" && DeptreeNode.parentids != null){
        $("#parentids").val(DeptreeNode.parentids+","+DeptreeNode.id);
    }else {
        $("#parentids").val(DeptreeNode.id);
    }
    $("#datarange").val(3);
}
function setDepValUpd() {
    $("#parentname").val(DeptreeNode.parentname);
    $("#parent").val(DeptreeNode.pid);
    $("#province").val(DeptreeNode.province);
    $("#city").val(DeptreeNode.city);
    $("#area").val(DeptreeNode.area);
    $("#school").val(DeptreeNode.school);
    $("#parentids").val(DeptreeNode.parentids);
    $("#datarange").val(DeptreeNode.dataRange);
    $("#name").val(DeptreeNode.name);
    $("#id").val(DeptreeNode.id);
}
//创建左侧tree
function getDepartment() {
    $.ajax({
        url : "/depm/getDepartmentList",
        type: "Post",
        dataType : "json",
        data :{departmentID:$("#departmentid").val()},
        //async: false,
        success : function(result) {
            treeObj = $.fn.zTree.init($("#treeDemo"), setting, result);
            treeObj.expandAll(true);//默认全部展开
        },
        error:function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}
//tree选中和取消事件
function zTreeOnClick(event, treeId, treeNode) {
    DeptreeNode = treeNode;
}