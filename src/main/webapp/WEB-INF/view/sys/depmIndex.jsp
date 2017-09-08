<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/8
  Time: 9:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>部门管理</title>
    <jsp:include page="/WEB-INF/view/include/head.jsp"/>

</head>
<body>
<section class="content-header">
    <h1>
        机构管理
        <%--<small>bootstrap-table管理表格</small>--%>
    </h1>
</section>
<!-- Main content -->
<section class="content">

    <div class="row">
        <div class="col-md-3">
            <ul id="treeDemo" class="ztree"></ul>
        </div>

        <div class="col-md-9">
            <div class="row">
                <div class="col-md-8">
                    <shiro:hasPermission name="sys:depm:add">
                        <button type="button" id="dep_add" class="btn btn-default" aria-label="Left Align">
                            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
                        </button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:depm:update">
                        <button type="button" id="dep_upda" class="btn btn-default" aria-label="Left Align">
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
                        </button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:depm:delete">
                        <button type="button" id="dep_del" class="btn btn-default" aria-label="Left Align">
                            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                        </button>
                    </shiro:hasPermission>
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <hr/>
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <div class="panel panel-primary" id="dep_div" >
                        <div class="panel-heading">
                            <h3 class="panel-title" id="dep_title"></h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <form class="form-horizontal" id="form-dep-add" >
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">部门名称</label>
                                            <div class="col-sm-7">
                                                <input id="name" name="name" type="text" class="form-control" />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">上级部门名称</label>
                                            <div class="col-sm-7">
                                                <input id="parentname" name="parentname" type="text" class="form-control" disabled />
                                                <input id="parentid" name="parentid" type="hidden" class="form-control" />
                                                <input id="id" name="id" type="hidden" class="form-control" />
                                                <input id="parentids" name="parentids" type="hidden" class="form-control" />
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label"></label>
                                            <div class="col-sm-7">
                                                <button class="btn btn-primary" type="button" id="save_dep"><i class="fa fa-save"></i>&nbsp;保存</button>
                                                <%--<button type="reset" id="btn_reset" class="btn btn-danger"><i class="fa fa-undo"></i>&nbsp;取消</button>--%>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

</section>
</body>
<jsp:include page="/WEB-INF/view/include/foot.jsp"/>
<script src="${pageContext.request.contextPath}/content/plugins/zTree/js/jquery.ztree.core.js"></script>
<script src="${pageContext.request.contextPath}/content/plugins/zTree/js/jquery.ztree.excheck.js"></script>
<script>
    var treeObj ;
    var DeptreeNode;
    var flag =0;
    var setting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: zTreeOnClick
        }
    };

    $(function () {
        //创建左侧tree
        getDepartment();
        $("#dep_div").hide();
        //添加按钮
        $("#dep_add").click(function () {
            flag = 0;
            if (DeptreeNode == undefined){
                $.fn.modalAlert("请选择上级部门","error");
                return;
            }
            //console.log(DeptreeNode);
            $("#dep_title").text("添加");
            setDepValAdd();
            $("#dep_div").show();
        });
        //修改
        $("#dep_upda").click(function () {
            flag = 1;
            if (DeptreeNode == undefined){
                $.fn.modalAlert("请选择修改部门","error");
                return;
            }
            setDepValUpd();
            //console.log(DeptreeNode);
            $("#dep_title").text("修改");
            $("#dep_div").show();
        });
        //删除
        $("#dep_del").click(function () {
            if (DeptreeNode == undefined){
                $.fn.modalAlert("请选择删除部门","error");
                return;
            }
            if (DeptreeNode.isParent){
                $.fn.modalAlert("请选择子部门！！","error");
                return;
            }
            //只是子节点可以删除
            $.fn.modalConfirm ('确定要删除所选部门吗？', function () {
                $.ajax({
                    url:'${ctx}/sys/depm/delete',
                    type: "Post",
                    data:{id:DeptreeNode.id},
                    dataType : "json",
                    success:function(result){
                        if(result > 0){
                            layer.msg('操作成功!',{icon:1,time:1000},function(index){
                                getDepartment();
                            });
                        }else {
                            layer.msg('操作失败!',{icon:2,time:1000},function(index){
                                getDepartment();
                            });
                        }
                        $("#dep_div").hide();
                    }
                });
            });
        });
        //保存按钮
        $("#save_dep").click(function () {
            $.ajax({
                url : "${ctx}/sys/depm/save",
                type: "Post",
                dataType : "json",
                data:  $("#form-dep-add").serialize()+"&flag="+flag,
                success : function(result) {
                    if(result > 0) {
                        layer.msg('操作成功!',{icon:1,time:1000},function(index){
                            getDepartment();
                        });
                    } else {
                        layer.msg('操作失败!',{icon:2,time:1000},function(index){
                            getDepartment();
                        });
                    }
                    $("#dep_div").hide();
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    //console.log(XMLHttpRequest);
                    //console.log("error"+XMLHttpRequest+"==="+textStatus+"----"+errorThrown);
                }
            });
        });

    });
    //添加默认赋值
    function setDepValAdd() {
        $("#id").val("");
        $("#name").val("");
        $("#parentname").val(DeptreeNode.name);
        $("#parentid").val(DeptreeNode.id);
        if(DeptreeNode.parentIDs != "" && DeptreeNode.parentIDs != null){
            $("#parentids").val(DeptreeNode.parentIDs+","+DeptreeNode.id);
        }else {
            $("#parentids").val(DeptreeNode.id);
        }
    }
    function setDepValUpd() {
        $("#parentname").val(DeptreeNode.parentname);
        $("#parentid").val(DeptreeNode.pId);
        $("#parentids").val(DeptreeNode.parentIDs);
        $("#name").val(DeptreeNode.name);
        $("#id").val(DeptreeNode.id);
    }
    //创建左侧tree
    function getDepartment() {
        $.ajax({
            url : "${ctx}/sys/depm/getDepartmentList",
            type: "Post",
            dataType : "json",
            data :{departmentID:$("#departmentid").val()},
            //async: false,
            success : function(result) {
                treeObj = $.fn.zTree.init($("#treeDemo"), setting, result);
                treeObj.expandAll(true);//默认全部展开
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
    //tree选中和取消事件
    function zTreeOnClick(event, treeId, treeNode) {
        DeptreeNode = treeNode;
    }
</script>
</html>
