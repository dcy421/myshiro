<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/21
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <meta charset="utf-8" />
    <title>工作流流程首页</title>
    <jsp:include page="/WEB-INF/view/include/head.jsp"/>
</head>
<body>
<section class="content-header">
    <h1>
        流程定义管理
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
                                <label for="processName">流程定义名称</label>
                                <input type="text" class="form-control" id="processName" placeholder="流程定义名称">
                                <input id="hiddenText" type="text" style="display:none" /><%-- 隐藏的  控制回车提交表单--%>
                            </div>
                            <button type="button" id="btn_query" class="btn btn-success"><i class="fa fa-search"></i>&nbsp;查询</button>
                            <button type="reset" id="btn_reset" class="btn btn-primary"><i class="fa fa-undo"></i>&nbsp;重置</button>
                        </form>
                    </div>
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
</body>
<script>
    var $table = $('#empUserList'),
        $query = $('#btn_query');
    $(function() {
        $table.bootstrapTable({
            url : '${ctx}/sys/workf/getProcessDeList',
            queryParams : queryParams,
            //showExport:true,//是否显示导出按钮
            //exportDataType:"basic",
            //buttonsAlign:"right",  //按钮位置
            toolbar : "#toolbar",// 指定工具栏
            uniqueId : "id", // 每一行的唯一标识
            sortable:false,//禁用排序
            columns : [ {
                checkbox : true
            },{
                title : '编号',
                field : 'id', // 字段
                align : 'center', // 对齐方式（左 中 右）
                valign : 'middle', //
                sortable : true
            },{
                title : '流程名称',
                field : 'name', // 字段
                align : 'center', // 对齐方式（左 中 右）
                valign : 'middle', //
                sortable : true
            },{
                title : '流程定义的key',
                field : 'key', // 字段
                align : 'center', // 对齐方式（左 中 右）
                valign : 'middle', //
                sortable : true
            },{
                title : '版本',
                field : 'version', // 字段
                align : 'center', // 对齐方式（左 中 右）
                valign : 'middle', //
                sortable : true
            },{
                title : '流程定义的规则文件名称',
                field : 'resourceName', // 字段
                align : 'center', // 对齐方式（左 中 右）
                valign : 'middle', //
                sortable : true
            },{
                title : '流程定义的规则图片名称',
                field : 'diagramResourceName', // 字段
                align : 'center', // 对齐方式（左 中 右）
                valign : 'middle', //
                sortable : true,
                formatter :resourceNameFormatter
            },{
                title : '流程部署Id',
                field : 'deploymentId', // 字段
                align : 'center', // 对齐方式（左 中 右）
                valign : 'middle', //
                sortable : true
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
            /*$table.bootstrapTable('resetView');*/
        }, 300);
        $(window).resize(function () {
            $table.bootstrapTable('resetView', {
                height:getHeight()
            });
        });
        $query.click(function () {
            $table.bootstrapTable('refresh');	//从新加载数据
        });


        /* input 获取焦点 才能触发 刷新事件*/
        $("input").keydown(function() {
            if (event.keyCode == "13") {//keyCode=13是回车键
                if ($query.length > 0){
                    $table.bootstrapTable('refresh');	//从新加载数据
                }
            }
        });
    });


    /**查询条件与分页数据 */
    function queryParams(params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset, //页码
            sort: params.sort,  //排序列名
            order:params.order, //排序方式
            search:params.search,   //搜索框参数
            processName:$("#processName").val()
            //username:$("#username").val()
            //loginFlag:$("#loginFlag").val()
        };
        return temp;
    }

    function resourceNameFormatter(value, row, index) {
        return "<a href='${ctx}/sys/workf/showView?deploymentId="+row.deploymentId+"&diagramResourceName="+row.diagramResourceName+"' target='_blank'>"+value+"</a>";
    }

</script>
</html>
