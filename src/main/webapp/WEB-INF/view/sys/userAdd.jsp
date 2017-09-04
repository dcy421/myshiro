<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/21
  Time: 8:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>用户添加</title>
    <jsp:include page="/WEB-INF/view/include/head.jsp"/>
</head>
<body>
    <section class="content">
        <div class="row">
            <form class="form-horizontal" id="form-admin-add"  onsubmit="return false;">
                <div class="col-md-12">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">头像</label>
                        <div class="col-sm-7">
                            <!--dom结构部分-->
                            <div id="uploader-demo">
                                <!--用来存放item-->
                                <div id="fileList" class="uploader-list"></div>
                                <div id="filePicker">选择图片</div>
                            </div>
                        </div>
                    </div>

                    <%--<div class="form-group">
                        <label class="col-sm-3 control-label">部门名称</label>
                        <div class="col-sm-7">
                            <select class="form-control ignore" name="departmentid" id="departmentid">
                                <c:forEach var="c" items="${depList}">
                                    <option value="${c.id}">${c.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>--%>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">用户名</label>
                        <div class="col-sm-7">
                            <input id="username" name="username" class="form-control"></input>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">密码</label>
                        <div class="col-sm-7">
                            <input id="password" type="password" name="password" class="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">确认密码</label>
                        <div class="col-sm-7">
                            <input id="confirm_password" type="password" name="confirm_password" class="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">邮箱</label>
                        <div class="col-sm-7">
                            <input id="email" class="form-control" name="email" type="text"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">电话</label>
                        <div class="col-sm-7">
                            <input id="phone"  class="form-control" name="phone" type="text"  />
                            <input id="photo"  class="form-control" name="photo" type="hidden"  /><%--头像--%>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">性别</label>
                        <div class="col-sm-7">
                            <div class="row">
                                <c:forEach var="c" items="${sexList}">
                                    <div class="col-sm-4">
                                        <label for="baz[${c.code}]">${c.name}</label>&nbsp;&nbsp;&nbsp;&nbsp;
                                        <input type="radio" name="sex" id="baz[${c.code}]" value="${c.code}" class="minimal ignore" <c:if test="${c.code eq 1}">checked</c:if>>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label">是否登陆</label>
                        <div class="col-sm-7">
                            <div class="row">
                                <div class="col-sm-4">
                                    <label for="baz[1]">是</label>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <input type="radio" name="locked" id="baz[1]" value="0" class="minimal ignore"  checked>
                                </div>
                                <div class="col-sm-4">
                                    <label for="baz[2]">否</label>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <input type="radio" name="locked" id="baz[2]" value="1" class="minimal ignore">
                                </div>
                            </div>
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
<script src="${pageContext.request.contextPath}/content/plugins/webuploader/webuploader.min.js"></script>
<script src="${pageContext.request.contextPath}/content/plugins/jquery.validation/jquery.validate.js"></script>
<script src="${pageContext.request.contextPath}/content/plugins/jquery.validation/validate-methods.js"></script>
<script src="${pageContext.request.contextPath}/content/common/validation/common.validation.js"></script>
<script src="${pageContext.request.contextPath}/content/view/sys/userAdd.js"></script>
</html>
