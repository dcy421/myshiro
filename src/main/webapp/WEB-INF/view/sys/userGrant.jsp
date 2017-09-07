<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/3
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>用户修改</title>
    <jsp:include page="/WEB-INF/view/include/head.jsp"/>
</head>
<body>
<section class="content">
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <label class="col-sm-2 control-label"></label>
                <div class="col-sm-7">
                    <button class="btn btn-primary" type="button"><i class="fa fa-save"></i>&nbsp;保存</button>
                    <button type="button" class="btn btn-danger" onclick="layer_close();"><i class="fa fa-close"></i>&nbsp;关闭</button>
                </div>
            </div>

        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <input type="hidden" id="id" value="${user.id}"/>
                <label class="col-sm-2 control-label"></label>
                <div class="col-sm-8">
                    <table id="role_table">
                    </table>
                </div>
            </div>

        </div>


    </div>

</section>
<jsp:include page="/WEB-INF/view/include/foot.jsp"/>
</body>
</html>


