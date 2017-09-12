<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/30
  Time: 8:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>角色修改</title>
    <jsp:include page="/WEB-INF/view/include/head.jsp"/>
</head>
<body>
<section class="content">
    <div class="row">
        <form class="form-horizontal" id="form-role-update"  onsubmit="return false;">
            <div class="col-md-12">
                <div class="form-group">
                    <label class="col-sm-3 control-label">部门名称</label>
                    <div class="col-sm-7">
                        <select class="form-control ignore" name="departmentid" id="departmentid">
                            <c:forEach var="c" items="${depList}">
                                <option value="${c.id}" <c:if test="${c.id eq role.departmentid }">selected</c:if>>${c.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">角色名称</label>
                    <div class="col-sm-7">
                        <input id="name" name="name" class="form-control" type="text" value="${role.name}" disabled/>
                        <input id="id" name="id" class="form-control" type="hidden" value="${role.id}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">英文名称</label>
                    <div class="col-sm-7">
                        <input id="enname" name="enname" class="form-control" type="text" value="${role.enname}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">权限级</label>
                    <div class="col-sm-7">
                        <ul id="treeDemo" class="ztree"></ul>
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
<jsp:include page="/WEB-INF/view/include/foot.jsp"/>
<script src="${pageContext.request.contextPath}/content/plugins/zTree/js/jquery.ztree.core.js"></script>
<script src="${pageContext.request.contextPath}/content/plugins/zTree/js/jquery.ztree.excheck.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${pageContext.request.contextPath}/content/plugins/jquery.validation/jquery.validate.js"></script>
<script src="${pageContext.request.contextPath}/content/plugins/jquery.validation/validate-methods.js"></script>
<script src="${pageContext.request.contextPath}/content/common/validation/common.validation.js"></script>
<script>
    $(function () {
        //iCheck for checkbox and radio inputs
        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
        $("#form-role-update").validate({
            rules:{
                name:{
                    required:true,
                    minlength:3,
                    maxlength:16
                }
            },
            onkeyup : false,
            submitHandler:function(form){
                var ids = $.map(treeObj.getCheckedNodes(true), function (nodes) {
                    return nodes.id
                });
                $.ajax({
                    url : "${ctx}/sys/role/save",
                    type: "Post",
                    dataType : "json",
                    traditional: true,//这里设置为true
                    data:  $("#form-role-update").serialize()+"&flag=update&ids="+ids,
                    success : function(result) {
                        //console.log(result);
                        if(result > 0) {
                            opaler();
                        } else {
                            opalerNO();
                        }
                        //刷新父级页面
                        parent.$table.bootstrapTable('refresh'); //再刷新DT
                    },
                    error:function (XMLHttpRequest, textStatus, errorThrown) {
                        //console.log(XMLHttpRequest);
                        //console.log("error"+XMLHttpRequest+"==="+textStatus+"----"+errorThrown);  程阔   18179761000
                    }
                });
            }
        });
    });
    var treeObj ;
    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };
    $.ajax({
        url : "${ctx}/sys/menu/getMenuList",
        type: "Post",
        dataType : "json",
        data:{ flag : 1,roleId : $("#id").val()},
        async: false,
        success : function(result) {
            treeObj = $.fn.zTree.init($("#treeDemo"), setting, result);
            treeObj.expandAll(true);//默认全部展开
        },
        error:function (XMLHttpRequest, textStatus, errorThrown) {
            //console.log(XMLHttpRequest);
            //console.log("error"+XMLHttpRequest+"==="+textStatus+"----"+errorThrown);
        }
    });
</script>

</body>
</html>


