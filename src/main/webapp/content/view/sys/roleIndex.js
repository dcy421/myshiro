/**
 * Created by Administrator on 2017/8/21.
 */
var $table = $('#empUserList'),
    $query = $('#btn_query'),
    $remove = $('#btn_delete'),
    $edit = $('#btn_edit'),
    $add = $('#btn_add'),
    selections = [];
$(function() {
    $table.bootstrapTable({
        url : '/role/getRolePageList',
        queryParams : queryParams,
        //showExport:true,//是否显示导出按钮
        //exportDataType:"basic",
        //buttonsAlign:"right",  //按钮位置
        toolbar : "#toolbar",// 指定工具栏
        uniqueId : "id", // 每一行的唯一标识
        columns : [ {
            checkbox : true
        },{
            title : '部门',
            field : 'departmentname', // 字段
            align : 'center', // 对齐方式（左 中 右）
            valign : 'middle', //
            sortable : true
        }, {
            title : '角色名',
            field : 'name', // 字段
            align : 'center', // 对齐方式（左 中 右）
            valign : 'middle', //
            sortable : true
        },{
            title : '是否启动',
            field : 'roleflag',
            align : 'center',
            valign : 'middle',
            sortable : true,
            formatter :operateFormatter
        } ],
        onLoadSuccess: function(){  //加载成功时执行
            //layer.msg("加载成功");
            //$('#empUserList').bootstrapTable("refresh");
        },
        onLoadError: function(){  //加载失败时执行
            layer.msg("加载数据失败", {time : 1500, icon : 2});
        }
    });
    // sometimes footer render error.
    setTimeout(function () {
        $table.bootstrapTable('resetView', {
            height:getHeight()
        });
        /*$table.bootstrapTable('resetView');*/
    }, 300);
    $(window).resize(function () {
        $table.bootstrapTable('resetView', {
            height:getHeight()
        });
    });
    //点击行中的checkbox  和全选的checkbox事件
    $table.on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        $remove.prop('disabled', !$table.bootstrapTable('getSelections').length);
        $edit.prop('disabled', !$table.bootstrapTable('getSelections').length);
        // save your data, here just save the current page
        selections = getIdSelections();
    });
    $query.click(function () {
        $table.bootstrapTable('refresh');	//从新加载数据
    });
    $add.click(function () {
        layer_show("角色添加","/role/roleAdd","800","600");
    });
    $edit.click(function () {
        if (selections.length != 1) {
            $.fn.modalAlert('请选择一条数据进行编辑！','error');
            return false;
        } else {
            layer_show("角色修改","/role/roleUpd?id="+selections[0],"800","600");
        }
    });
    $remove.click(function () {
        console.log(selections.length);
        if (selections.length < 1) {
            $.fn.modalAlert('请选择一条或多条数据进行删除！','error');
        } else {
            //询问框
            $.fn.modalConfirm ('确定要删除所选数据？', function () {
                $.ajax({
                    url:'/role/DelSysRole',
                    type: "Post",
                    data:{ids:selections},
                    dataType : "json",
                    success:function(result){
                        if(result > 0){
                            $.fn.modalMsg("操作成功","success");
                        }else {
                            $.fn.modalMsg("操作失败","error");
                        }
                        $table.bootstrapTable('refresh');	//从新加载数据
                    }
                });
            });
        }
    });

    $("body").keydown(function() {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $table.bootstrapTable('refresh');	//从新加载数据
        }
    });
});


function operateFormatter(value, row, index) {
    //console.log(value);
    var str = "";
    if(value== 0){
        str = "<span class=\"label label-success\">启动</span>";
    }else {
        str = "<span class=\"label label-danger\">关闭</span>";
    }
    return str;
}
/**
 * 返回所有的checked选中的值
 */
function getIdSelections() {
    return $.map($table.bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}

/**查询条件与分页数据 */
function queryParams(params) {
    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        limit: params.limit,   //页面大小
        offset: params.offset, //页码
        sort: params.sort,  //排序列名
        order:params.order, //排序方式
        search:params.search,   //搜索框参数
        name:$("#name").val()
        //username:$("#username").val()
        //loginFlag:$("#loginFlag").val()
    };
    return temp;
}