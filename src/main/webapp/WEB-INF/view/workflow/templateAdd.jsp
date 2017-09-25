<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/22
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>模板添加</title>
    <jsp:include page="/WEB-INF/view/include/head.jsp"/>
</head>
<body>
<section class="content">
    <div class="row">
        <form class="form-horizontal" id="form-admin-add" action="${ctx}/workflow/model/create" target="_blank" method="post">
            <div class="col-md-12">
                <div class="form-group">
                    <label class="col-sm-3 control-label">名称</label>
                    <div class="col-sm-7">
                        <input id="name" name="name" class="form-control"></input>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">key</label>
                    <div class="col-sm-7">
                        <input id="key" class="form-control" name="key" type="text"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">描述</label>
                    <div class="col-sm-7">
                        <textarea id="description" name="description" class="form-control" style="width:300px;height: 50px;"></textarea>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label"></label>
                    <div class="col-sm-7">
                        <button class="btn btn-primary" type="submit"><i class="fa fa-save"></i>&nbsp;保存</button>
                        <button type="button" class="btn btn-danger" onclick="layer_close();"><i class="fa fa-close"></i>&nbsp;关闭</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</section>
</body>
<jsp:include page="/WEB-INF/view/include/foot.jsp"/>
<script src="${pageContext.request.contextPath}/content/plugins/jquery.validation/jquery.validate.js"></script>
<script src="${pageContext.request.contextPath}/content/plugins/jquery.validation/validate-methods.js"></script>
<script src="${pageContext.request.contextPath}/content/common/validation/common.validation.js"></script>
<script>
    $(function () {

        $("#form-admin-add").validate({
            rules:{
                name:{
                    required:true
                },
                key:{
                    required:true
                }
            },
            onkeyup : false,
            submitHandler:function(form){
                form.submit();
            }
        });
    });
</script>
</html>
