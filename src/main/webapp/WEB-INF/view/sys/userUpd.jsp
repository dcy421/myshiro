<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/5
  Time: 10:11
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
                        <input id="username" name="username" value="${user.username}" disabled class="form-control"></input>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">真实姓名</label>
                    <div class="col-sm-7">
                        <input id="name" name="name" value="${user.name}" class="form-control"></input>
                    </div>
                </div>
                <%--<div class="form-group">
                    <label class="col-sm-3 control-label">密码</label>
                    <div class="col-sm-7">
                        <input id="password" type="password" name="password" value="${user.password}" class="form-control" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">确认密码</label>
                    <div class="col-sm-7">
                        <input id="confirm_password" type="password" value="${user.password}" name="confirm_password" class="form-control" />
                    </div>
                </div>--%>
                <div class="form-group">
                    <label class="col-sm-3 control-label">邮箱</label>
                    <div class="col-sm-7">
                        <input id="email" class="form-control" value="${user.email}" name="email" type="text"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">电话</label>
                    <div class="col-sm-7">
                        <input id="phone"  class="form-control" value="${user.phone}" name="phone" type="text"  />
                        <input id="photo"  class="form-control" name="photo" type="hidden"  /><%--头像--%>
                        <input id="id"  class="form-control" name="id" value="${user.id}" type="hidden"  /><%--id--%>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">生日</label>
                    <div class="col-sm-7">
                        <input id="birthday" class="form-control" value="<fmt:formatDate type="time" pattern="yyyy-MM-dd" value="${user.birthday}" />" onClick="WdatePicker({maxDate:'%y-%M-%d'})" name="birthday" type="text"  />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">性别</label>
                    <div class="col-sm-7">
                        <div class="row">
                            <c:forEach var="c" items="${sexList}">
                                <div class="col-sm-4">
                                    <label for="baz[${c.code}]">${c.name}</label>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <input type="radio" name="sex" id="baz[${c.code}]" value="${c.code}" class="minimal ignore" <c:if test="${c.code eq user.sex}">checked</c:if>>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">角色</label>
                    <div class="col-sm-7">
                        <div class="row">
                            <div class="col-sm-5">
                                <select class="form-control ignore" name="roleold" id="roleold" multiple>
                                    <c:forEach var="c" items="${roleoldList}">
                                        <option value="${c.id}">${c.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-2 text-center">
                                <i class="fa fa-arrows-h" style="padding-top: 20%;"></i>
                            </div>
                            <div class="col-sm-5">
                                <select class="form-control ignore" id="rolenew" name="rolenew" multiple>
                                    <c:forEach var="c" items="${rolenewList}">
                                        <option value="${c.id}">${c.name}</option>
                                    </c:forEach>
                                </select>
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
<script src="${pageContext.request.contextPath}/content/plugins/my97DatePicker/WdatePicker.js"></script>
<script src="${pageContext.request.contextPath}/content/view/sys/user.js"></script>
<script>
    $(function () {
        $("#form-admin-add").validate({
            rules:{
                phone:{
                    required:true,
                    isPhone:true
                },
                email:{
                    required:true,
                    email:true
                }
            },
            onkeyup : false,
            submitHandler:function(form){
                var roleids = [];
                $('#rolenew option').each(function () {
                    roleids.push($(this).val())
                });
                $.ajax({
                    url : "${ctx}/sys/user/save",
                    type: "Post",
                    dataType : "json",
                    data:  $("#form-admin-add").serialize()+"&flag=update&roleids="+roleids,
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
                        //console.log("error"+XMLHttpRequest+"==="+textStatus+"----"+errorThrown);
                    }
                });
            }
        });
    });

    // 图片上传demo
    $(function(){
        var $ = jQuery,
            $list = $('#fileList'),
            // 优化retina, 在retina下这个值是2
            ratio = 1 || 1,

            // 缩略图大小
            thumbnailWidth = 100 * ratio,
            thumbnailHeight = 100 * ratio,

            // 初始化Web Uploader
            uploader = WebUploader.create({

                // 选完文件后，是否自动上传。
                auto: true,
                // swf文件路径
                swf:   '${ctxContent}/plugins/webuploader/Uploader.swf',
                //fileNumLimit: 1,
                // 文件接收服务端。
                server: '${ctx}/sys/user/uploadPicture',
                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: '#filePicker',
                // 只允许选择图片文件。
                accept: {
                    title: 'Images',
                    extensions: 'gif,jpg,jpeg,bmp,png',
                    mimeTypes: 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'   //修改这行
                }
            });

        // 当有文件添加进来的时候
        uploader.on( 'fileQueued', function( file ) {
            var $list = $('#fileList');
            var $li = $(
                    '<div id="' + file.id + '" class="file-item thumbnail">' +
                    '<img>' +
                    '<div class="info">' + file.name + '</div>' +
                    '</div>'
                ),
                $img = $li.find('img');


            // $list为容器jQuery实例
            $list.append( $li );

            // 创建缩略图
            // 如果为非图片文件，可以不用调用此方法。
            // thumbnailWidth x thumbnailHeight 为 100 x 100
            uploader.makeThumb( file, function( error, src ) {
                //console.log(error+"==="+src)
                if ( error ) {
                    $img.replaceWith('<span>不能预览</span>');
                    return;
                }

                $img.attr( 'src', src );
            }, thumbnailWidth, thumbnailHeight );
        });
        // 文件上传过程中创建进度条实时显示。
        uploader.on( 'uploadProgress', function( file, percentage ) {
            var $li = $( '#'+file.id ),
                $percent = $li.find('.progress span');

            // 避免重复创建
            if ( !$percent.length ) {
                $percent = $('<p class="progress"><span></span></p>')
                    .appendTo( $li )
                    .find('span');
            }

            $percent.css( 'width', percentage * 100 + '%' );
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader.on( 'uploadSuccess', function( file ,response) {
            //alert("成功");
            //返回的成功路径
            //console.log(response._raw);
            $("#photo").val(response._raw);
            var fileStatusnum = uploader.getStats();
            $.fn.modalMsg("上传成功"+fileStatusnum.successNum+"个文件","success");
            $( '#'+file.id ).addClass('upload-state-done');
        });

        // 文件上传失败，显示上传出错。
        uploader.on( 'uploadError', function( file ) {
            //alert("失败");
            var $li = $( '#'+file.id ),
                $error = $li.find('div.error');

            // 避免重复创建
            if ( !$error.length ) {
                $error = $('<div class="error"></div>').appendTo( $li );
            }

            $error.text('上传失败');
            var fileStatusnum = uploader.getStats();
            layer.msg("上传失败"+fileStatusnum.uploadFailNum+"个文件",{icon:1,time:1000});
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader.on( 'uploadComplete', function( file ) {
            $( '#'+file.id ).find('.progress').remove();
        });
    });
</script>
</html>

