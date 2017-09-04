/**
 * Created by Administrator on 2017/8/22.
 */
/**
 * Created by Administrator on 2017/8/18.
 */
var $table = $('#empUserList'),
    $query = $('#btn_query'),
    $remove = $('#btn_delete'),
    selections = [];
    // selection = null;
$(function() {
    $table.bootstrapTable({
        url : '/mylesson/getMylessonPageList',
        /*detailView:true,
         detailFormatter:detailFormatter,*/
        queryParams : queryParams,
        buttonsAlign:"right",  //按钮位置
        toolbar : "#toolbar",// 指定工具栏
        uniqueId : "id", // 每一行的唯一标识
        rowStyle:rowStyle,//隔行变色
        columns : [ {
            checkbox : true
        },  {
            title : '部门名称',
            field : 'departmentname', // 字段
            align : 'center', // 对齐方式（左 中 右）
            valign : 'middle', //
            sortable : true
        },{
            title : '老师名称',
            field : 'teachername', // 字段
            align : 'center', // 对齐方式（左 中 右）
            valign : 'middle', //
            sortable : true
        }, {
            title : '课程名称',
            field : 'lessonname',
            align : 'center',
            valign : 'middle',
            sortable : true
        }, {
            title : '开始时间',
            field : 'starttime',
            align : 'center',
            valign : 'middle',
            sortable : true,
            formatter: starttimeFormatter
        }, {
            title : '结束时间',
            field : 'endtime',
            align : 'center',
            valign : 'middle',
            sortable : true,
            formatter: endtimeFormatter
        },{
            title : '授课地点',
            field : 'address',
            align : 'center',
            valign : 'middle',
            sortable : true
        },{
            title : '课程单价',
            field : 'unitprice',
            align : 'center',
            valign : 'middle',
            sortable : true
        }],
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
        // save your data, here just save the current page
        selections = getIdSelections();
    });
    $query.click(function () {
        $table.bootstrapTable('refresh');	//从新加载数据
    });

    $remove.click(function () {
        if (selections.length < 1) {
            $.fn.modalAlert('请选择一条或多条数据进行删除！','error');
        } else {
            //询问框
            $.fn.modalConfirm ('确定要删除所选数据？', function () {
                $.ajax({
                    url:'/mylesson/mylessonDelete',
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
            if ($query.length > 0)
                $table.bootstrapTable('refresh');	//从新加载数据
        }
    });
});

function detailFormatter(index, row) {
    var html = [];
    $.each(row, function (key, value) {
        html.push('<p><b>' + key + ':</b> ' + value + '</p>');
    });
    return html.join('');
}

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
        lessonid:$("#lessonid").val(),
        teacherid:$("#teacherid").val(),
        address:$("#address").val()
        //loginFlag:$("#loginFlag").val()
    };
    return temp;
}
function starttimeFormatter(value, row, index) {
    //console.log(value);
    var str = "";
    if(value!="" && value!=null){
        str = moment(value).format('YYYY-MM-DD');
    }
    return str;
}
function endtimeFormatter(value, row, index) {
    //console.log(value);
    var str = "";
    if(value!="" && value!=null){
        str = moment(value).format('YYYY-MM-DD');
    }
    return str;
}
function personcountFormatter(value, row, index) {
    //console.log(value);
    var str = "";
    if(value!="" && value!=null){
        str = value - row.currentpersoncount;
    }
    return str;
}