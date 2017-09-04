var $table = $('#empUserList'),
    $query = $('#btn_query'),
    $remove = $('#btn_delete'),
    $edit = $('#btn_edit'),
    $grant = $('#btn_grant'),
    $add = $('#btn_add'),
    selections = [];
$(function() {
    $table.bootstrapTable({
        url : '/attendance/getAttendanceLists',
        /*detailView:true,
        detailFormatter:detailFormatter,*/
        queryParams : queryParams,
        buttonsAlign:"right",  //按钮位置
        toolbar : "#toolbar",// 指定工具栏
        uniqueId : "id", // 每一行的唯一标识
        // rowStyle:rowStyle,//隔行变色
        sortName: "attendtime",
        sortOrder:"desc",
        columns : [ {
            checkbox : true
        },{
            title : '序号',
            field : 'attendid', // 字段
            align : 'center', // 对齐方式（左 中 右）
            valign : 'middle', //
            sortable : true,
            formatter: indexFormatter
        },{
            title : '部门',
            field : 'departmentname', // 字段
            align : 'center', // 对齐方式（左 中 右）
            valign : 'middle', //
            sortable : true
        },{
            title : '课程名称',
            field : 'lessonname', // 字段
            align : 'center', // 对齐方式（左 中 右）
            valign : 'middle', //
            sortable : true
        },{
            title : '学生名称',
            field : 'username', // 字段
            align : 'center', // 对齐方式（左 中 右）
            valign : 'middle', //
            sortable : true
            },{
            title : '老师名称',
            field : 'teachername', // 字段
            align : 'center', // 对齐方式（左 中 右）
            valign : 'middle', //
            sortable : true
        },{
            title : '授课地点',
            field : 'lessonaddress', // 字段
            align : 'center', // 对齐方式（左 中 右）
            valign : 'middle', //
            sortable : true
        },{
            title : '开始时间',
            field : 'starttime', // 字段
            align : 'center', // 对齐方式（左 中 右）
            valign : 'middle', //
            formatter: attendtimeFormatter,
            sortable : true
        },{
            title : '结束时间',
            field : 'endtime', // 字段
            align : 'center', // 对齐方式（左 中 右）
            valign : 'middle', //
            formatter: attendtimeFormatter,
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
        $edit.prop('disabled', !$table.bootstrapTable('getSelections').length);
        $grant.prop('disabled', !$table.bootstrapTable('getSelections').length);
        // save your data, here just save the current page
        selections = getIdSelections();
    });
    $query.click(function () {
        $table.bootstrapTable('refresh');	//从新加载数据
    });
    $("body").keydown(function() {
        if (event.keyCode == "13") {//keyCode=13是回车键
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
        starttime:$("#starttime").val(),
        endtime:$("#endtime").val()
    };
    return temp;
}
function attendtimeFormatter(value, row, index) {
    var str = "";
    if(value!="" && value!=null){
        str = moment(value).format('YYYY-MM-DD HH:mm');
    }
    return str;
}
function indexFormatter(value, row, index) {
    return index+1;
}