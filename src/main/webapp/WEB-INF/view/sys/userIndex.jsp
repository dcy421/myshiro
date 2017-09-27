<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/4
  Time: 13:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <head>
        <meta charset="utf-8" />
        <title>用户列表</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <jsp:include page="/WEB-INF/view/include/head.jsp"/>
    </head>


</head>

<body>
<section class="content-header">
    <h1>
        人员管理
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
                                <label for="username">用户名</label>
                                <input type="text" class="form-control" id="username" name="username" placeholder="用户名">
                                <input id="hiddenText" type="text" style="display:none" /><%-- 隐藏的  控制回车提交表单--%>
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="text" class="form-control" id="email" name="email" placeholder="Email">
                            </div>
                            <div class="form-group">
                                <label for="phone">手机号</label>
                                <input type="text" class="form-control" id="phone" name="phone" placeholder="手机号">
                            </div>
                            <shiro:hasPermission name="sys:user:search">
                                <button type="button" id="btn_query" class="btn btn-success"><i class="fa fa-search"></i>&nbsp;查询</button>
                            </shiro:hasPermission>
                            <button type="reset" id="btn_reset" class="btn btn-primary"><i class="fa fa-undo"></i>&nbsp;重置</button>

                        </form>
                    </div>
                </div>

                <div id="toolbar" class="btn-group">
                    <shiro:hasPermission name="sys:user:add">
                        <button id="btn_add" type="button" class="btn btn-default">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                        </button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:user:delete">
                        <button id="btn_delete" type="button" class="btn btn-default" disabled>
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>批量删除
                        </button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:user:export">
                        <button id="btn_export" type="button" class="btn btn-default" >
                            <span class="glyphicon glyphicon-export" aria-hidden="true"></span>导出
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
<script src="${pageContext.request.contextPath}/content/plugins/moment/moment.js"></script>
<script>
    var $table = $('#empUserList'),
        $query = $('#btn_query'),
        $remove = $('#btn_delete'),
        $add = $('#btn_add'),
        $export = $('#btn_export'),
        selections = [],
        permissionButton=[
            '<shiro:hasPermission name="sys:user:update"><a class="btn btn-icon-only"  data-type="edit" href="javascript:void(0)" title="修改">',
            '<i class="glyphicon glyphicon-edit"></i></a></shiro:hasPermission>',
            '<shiro:hasPermission name="sys:user:delete"><a class="btn btn-icon-only" data-type="delete" href="javascript:void(0)" title="删除">',
            '<i class="glyphicon glyphicon-remove"></i></a></shiro:hasPermission>'
        ].join('');//权限按钮
    $(function() {
        $table.bootstrapTable({
            url : '${ctx}/sys/user/getUserPageList',
            /*detailView:true,
            detailFormatter:detailFormatter,*/
            queryParams : queryParams,
            buttonsAlign:"right",  //按钮位置
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
                title : '用户名',
                field : 'username', // 字段
                align : 'center', // 对齐方式（左 中 右）
                valign : 'middle', //
                sortable : true
            },{
                title : '真实姓名',
                field : 'name', // 字段
                align : 'center', // 对齐方式（左 中 右）
                valign : 'middle', //
                sortable : true
            }, {
                title : '邮箱',
                field : 'email',
                align : 'center',
                valign : 'middle',
                sortable : true
            }, {
                title : '电话',
                field : 'phone',
                align : 'center',
                valign : 'middle',
                sortable : true
            },{
                title : '性别',
                field : 'sexname',
                align : 'center',
                valign : 'middle',
                sortable : true,
                formatter:sexFormatter
            }, {
                title : '生日',
                field : 'birthday',
                align : 'center',
                valign : 'middle',
                sortable : true,
                formatter :birthdayFormatter
            },  {
                title : '启动',
                field : 'locked',
                align : 'center',
                valign : 'middle',
                sortable : true,
                formatter :lockedFormatter
            } ,{
                title : '操作',
                field : 'id',
                align : 'center',
                valign : 'middle',
                sortable : true,
                formatter :actionFormatter,
                events:actionEvents
            }],
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
        $add.click(function () {
            layer_show("用户添加","${ctx}/sys/user/add","800","600");
            //新增加一个iframe
            //parent.addTabs({ id: '10008111', title: '用户添加', close: true, url: '${ctx}/sys/user/add' });
        });
        $remove.click(function () {
            if (selections.length < 1) {
                $.fn.modalAlert('请选择一条或多条数据进行删除！','error');
            } else {
                //询问框
                $.fn.modalConfirm ('确定要删除所选数据？', function () {
                    $.ajax({
                        url:'${ctx}/sys/user/batchDelete',
                        type: "Post",
                        data:{ids:selections},
                        dataType : "json",
                        success:function(result){
                            if(result > 0){
                                $.fn.modalMsg("操作成功","success");
                            }else {
                                $.fn.modalMsg("操作失败","error");
                            }
                            $remove.prop('disabled', true);
                            $table.bootstrapTable('refresh');	//从新加载数据
                        }
                    });
                });
            }
        });
        $export.click(function () {
            $('#userForm').attr('action', "${ctx}/sys/user/exportUserList");
            $('#userForm').submit();
        });

        /* input 获取焦点 才能触发 刷新事件*/
        $("input").keydown(function() {
            if (event.keyCode == "13") {//keyCode=13是回车键
                if ($query.length > 0){
                    $table.bootstrapTable('refresh');	//从新加载数据
                }
            }
        });
        $table.on('switchChange.bootstrapSwitch','.switch',function (event,state) {
            //console.log(state);
            //console.log($(this).attr('data-id'));
            var id = $(this).attr('data-id');
            $.ajax({
                url:'${ctx}/sys/user/save',
                type: "Post",
                data:{locked:!state,id:id,flag:"update"},
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

    function detailFormatter(index, row) {
        var html = [];
        $.each(row, function (key, value) {
            html.push('<p><b>' + key + ':</b> ' + value + '</p>');
        });
        return html.join('');
    }

    function actionFormatter(value, row, index) {
        return permissionButton;
    }

    window.actionEvents = {
        'click [data-type="edit"]': function (e, value, row) {
            layer_show("用户修改","${ctx}/sys/user/update?id="+value,"800","600");
        },
        'click [data-type="delete"]': function (e, value, row) {
            $.fn.modalConfirm ('确定要删除所选数据？', function () {
                $.ajax({
                    url:'${ctx}/sys/user/delete',
                    type: "Post",
                    data:{id:value},
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
    };


    function sexFormatter(value, row, index) {
        var str = "";
        if(value == "男"){
            str = "<span class=\"label label-warning\">男</span>";
        }else {
            str = "<span class=\"label label-info\">女</span>";
        }
        return str;
    }

    function lockedFormatter(value, row, index) {
        var str = "";
        /*if(!value){
            str = "<span class=\"label label-success\">启动</span>";
        }else {
            str = "<span class=\"label label-danger\">关闭</span>";
        }*/
        if(!value){
            str = "<div class='switch switch-mini' data-on='primary' data-off='info' data-id='"+row.id+"'><input type='checkbox' checked /></div>";
        }else {
            str = "<div class='switch switch-mini' data-on='primary' data-off='info' data-id='"+row.id+"'><input type='checkbox' /></div>";
        }
        return str;
    }
    function birthdayFormatter(value, row, index) {
        var str = "";
        if(value != "" && value != null){
            str = moment(value).format('YYYY-MM-DD');
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
            username:$("#username").val(),
            email:$("#email").val(),
            phone:$("#phone").val()
            //loginFlag:$("#loginFlag").val()
        };
        return temp;
    }
</script>
</html>
