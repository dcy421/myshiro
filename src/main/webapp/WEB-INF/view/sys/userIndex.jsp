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
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="text" class="form-control" id="email" name="email" placeholder="Email">
                            </div>
                            <div class="form-group">
                                <label for="phone">手机号</label>
                                <input type="text" class="form-control" id="phone" name="phone" placeholder="手机号">
                            </div>
                            <button type="button" id="btn_query" class="btn btn-success"><i class="fa fa-search"></i>&nbsp;查询</button>
                            <button type="reset" id="btn_reset" class="btn btn-primary"><i class="fa fa-undo"></i>&nbsp;重置</button>

                        </form>
                    </div>
                </div>

                <div id="toolbar" class="btn-group">
                    <button id="btn_add" type="button" class="btn btn-default">
                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                    </button>

                    <button id="btn_edit" type="button" class="btn btn-default" disabled>
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
                    </button>

                    <button id="btn_delete" type="button" class="btn btn-default" disabled>
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                    </button>

                    <button id="btn_grant" type="button" class="btn btn-default" disabled>
                        <span class="fa fa-edit" aria-hidden="true"></span>授权
                    </button>

                    <button id="btn_export" type="button" class="btn btn-default" >
                        <span class="glyphicon glyphicon-export" aria-hidden="true"></span>导出
                    </button>
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
<script src="${pageContext.request.contextPath}/content/view/sys/userIndex.js"></script>
</html>
