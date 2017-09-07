<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/7
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <meta charset="utf-8" />
    <title>菜单列表</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport" />
    <meta content="" name="description" />
    <meta content="" name="author" />
    <jsp:include page="/WEB-INF/view/include/head.jsp"/>
    <link href="${pageContext.request.contextPath}/content/plugins/jquery-treetable/css/jquery.treetable.css" rel="stylesheet" />
</head>
<body>
<section class="content-header">
    <h1>
        菜单管理管理
    </h1>
</section>
<!-- Main content -->
<section class="content">

    <div class="row">
        <!-- BEGIN SAMPLE TABLE PORTLET-->
        <div class="col-md-12">
            <div class="table-scrollable">
                <table class="table table-striped table-hover table-bordered"  id="menuList" width="100%">
                    <thead class="text-center">
                        <tr>
                            <th></th>
                            <th class="text-center">名称</th>
                            <th class="text-center">排序</th>
                            <th class="text-center">地址</th>
                            <th class="text-center">跳转方式</th>
                            <th class="text-center">图标</th>
                            <th class="text-center">启动</th>
                            <th class="text-center">权限</th>
                            <th class="text-center">备注</th>
                        </tr>
                    </thead>
                    <tbody class="text-center">
                        <c:forEach var="c" items="${menuList}">
                            <tr data-tt-id="${c.id}" <c:if test="${!empty c.parentid && c.parentid != 0}">data-tt-parent-id="${c.parentid}"</c:if>>
                                <td></td>
                                <td>${c.name}</td>
                                <td>${c.sort}</td>
                                <td>${c.href}</td>
                                <td>${c.target}</td>
                                <td>${c.icon}</td>
                                <td>${c.available}</td>
                                <td>${c.permission}</td>
                                <td>${c.remarks}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</section>
</body>
<jsp:include page="/WEB-INF/view/include/foot.jsp"/>
<script src="${pageContext.request.contextPath}/content/plugins/jquery-treetable/js/jquery.treetable.js"></script>
<script>
    $(function(){
        $('#menuList').treetable({expandable : true});
    });
</script>
</html>
