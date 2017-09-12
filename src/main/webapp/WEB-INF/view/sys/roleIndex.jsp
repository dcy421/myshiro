<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/30
  Time: 8:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <head>
        <meta charset="utf-8" />
        <title>角色列表</title>
        <jsp:include page="/WEB-INF/view/include/head.jsp"/>
    </head>
</head>

<body>
<section class="content-header">
    <h1>
        角色管理
        <%--<small>bootstrap-table管理表格</small>--%>
    </h1>
</section>
<!-- Main content -->
<section class="content">

    <div class="row">
        <!-- BEGIN SAMPLE TABLE PORTLET-->
        <div class="col-md-12">
            <!-- BEGIN SAMPLE TABLE PORTLET-->
            <div class="box-body" style="padding-bottom:0px;">
                <div class="panel panel-default">
                    <div class="panel-heading">高级检索</div>
                    <div class="panel-body">
                        <form class="form-inline" method="post" id="userForm" name="userForm" >
                            <div class="form-group">
                                <label for="name">角色名称</label>
                                <input type="text" class="form-control" id="name" placeholder="角色名称">
                            </div>
                            <shiro:hasPermission name="sys:role:search">
                                <button type="button" id="btn_query" class="btn btn-success"><i class="fa fa-search"></i>&nbsp;查询</button>
                            </shiro:hasPermission>
                            <button type="reset" id="btn_reset" class="btn btn-primary"><i class="fa fa-undo"></i>&nbsp;重置</button>
                        </form>
                    </div>
                </div>

                <div id="toolbar" class="btn-group">
                    <shiro:hasPermission name="sys:role:add">
                        <button id="btn_add" type="button" class="btn btn-default">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                        </button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:role:update">
                        <button id="btn_edit" type="button" class="btn btn-default" disabled>
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
                        </button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:role:delete">
                        <button id="btn_delete" type="button" class="btn btn-default" disabled>
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                        </button>
                    </shiro:hasPermission>
                </div>
                <div class="table-scrollable">
                    <table class="table-striped table-hover table-bordered"  id="empUserList">
                    </table>
                </div>
            </div>
            <!-- END SAMPLE TABLE PORTLET-->
        </div>
    </div>
</section>
</body>
<jsp:include page="/WEB-INF/view/include/foot.jsp"/>
<script src="${pageContext.request.contextPath}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
<script src="${pageContext.request.contextPath}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/content/common/common.server.js"></script>
<script>
    var $table = $('#empUserList'),
        $query = $('#btn_query'),
        $remove = $('#btn_delete'),
        $edit = $('#btn_edit'),
        $add = $('#btn_add'),
        selections = [];
    $(function() {
        $table.bootstrapTable({
            url : '${ctx}/sys/role/getRolePageList',
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
            },{
                title : '角色名',
                field : 'name', // 字段
                align : 'center', // 对齐方式（左 中 右）
                valign : 'middle', //
                sortable : true
            },{
                title : '英文名',
                field : 'enname', // 字段
                align : 'center', // 对齐方式（左 中 右）
                valign : 'middle', //
                sortable : true
            },{
                title : '启动',
                field : 'available',
                align : 'center',
                valign : 'middle',
                sortable : true,
                formatter :operateFormatter
            } ],
            onLoadSuccess: function(){  //加载成功时执行
                //layer.msg("加载成功");
                //$('#empUserList').bootstrapTable("refresh");
                //默认最小
                $('.switch input').bootstrapSwitch({
                    size:"mini"
                });
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
            layer_show("角色添加","${ctx}/sys/role/add","800","600");
        });
        $edit.click(function () {
            if (selections.length != 1) {
                $.fn.modalAlert('请选择一条数据进行编辑！','error');
                return false;
            } else {
                layer_show("角色修改","${ctx}/sys/role/update?id="+selections[0],"800","600");
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
                        url:'${ctx}/sys/role/delete',
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
                if ($query.length > 0){
                    $table.bootstrapTable('refresh');	//从新加载数据
                }
            }
        });
        $table.on('switchChange.bootstrapSwitch','.switch',function (event,state) {
            var id = $(this).attr('data-id');
            $.ajax({
                url:'${ctx}/sys/role/save',
                type: "Post",
                data:{available:!state,id:id,flag:"grant"},
                dataType : "json",
                success:function(result){
                    if(result > 0){
                        $.fn.modalMsg("操作成功","success");
                    }else {
                        $.fn.modalMsg("操作失败","error");
                    }
                    //$table.bootstrapTable('refresh');	//从新加载数据
                }
            });
        });
    });


    function operateFormatter(value, row, index) {
        //console.log(value);
        var str = "";
        if(!value){
            str = "<div class='switch switch-mini' data-on='primary' data-off='info' data-id='"+row.id+"'><input type='checkbox' checked /></div>";
        }else {
            str = "<div class='switch switch-mini' data-on='primary' data-off='info' data-id='"+row.id+"'><input type='checkbox' /></div>";
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
</script>
</html>

