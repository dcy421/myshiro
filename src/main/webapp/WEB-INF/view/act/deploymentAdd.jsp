<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/21
  Time: 9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>部署添加</title>
    <jsp:include page="/WEB-INF/view/include/head.jsp"/>
</head>
<body>
<section class="content">
    <div class="row">
        <form class="form-horizontal" id="form-role-add"  ><%--method="post" action="${ctx}/sys/workf/deploy" enctype="multipart/form-data"--%>
            <div class="col-md-12">
                <div class="form-group">
                    <label class="col-sm-3 control-label">文件zip</label>
                    <div class="col-sm-7">
                        <div id="uploader-demo">
                            <!--用来存放item-->
                            <div id="thelist" class="uploader-list"></div>
                            <div>
                                <div id="filePicker">选择图片</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label"></label>
                    <div class="col-sm-7">
                        <button class="btn btn-primary" type="button" id="ctlBtn"><i class="fa fa-save"></i>&nbsp;上传</button>
                        <button type="button" class="btn btn-danger" onclick="layer_close();"><i class="fa fa-close"></i>&nbsp;关闭</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</section>
</body>
<jsp:include page="/WEB-INF/view/include/foot.jsp"/>
<!-- Bootstrap 3.3.6 -->
<script src="${pageContext.request.contextPath}/content/plugins/jquery.validation/jquery.validate.js"></script>
<script src="${pageContext.request.contextPath}/content/plugins/jquery.validation/validate-methods.js"></script>
<script src="${pageContext.request.contextPath}/content/common/validation/common.validation.js"></script>
<script src="${pageContext.request.contextPath}/content/plugins/webuploader/webuploader.min.js"></script>
<script>
    $(function(){
        /*init webuploader*/
        var $list=$("#thelist");   //这几个初始化全局的百度文档上没说明，好蛋疼。
        var $btn =$("#ctlBtn");   //开始上传
        var thumbnailWidth = 100;   //缩略图高度和宽度 （单位是像素），当宽高度是0~1的时候，是按照百分比计算，具体可以看api文档
        var thumbnailHeight = 100;

        var uploader = WebUploader.create({
            // 选完文件后，是否自动上传。
            auto: false,

            // swf文件路径
            swf:   '${ctxContent}/plugins/webuploader/Uploader.swf',

            // 文件接收服务端。
            server: '${ctx}/sys/workf/deploy',

            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#filePicker',

            // 只允许选择图片文件。
            accept: {
                title: 'file',
                extensions: 'zip'
            },
            method:'POST'
        });
        // 当有文件添加进来的时候
        uploader.on( 'fileQueued', function( file ) {  // webuploader事件.当选择文件后，文件被加载到文件队列中，触发该事件。等效于 uploader.onFileueued = function(file){...} ，类似js的事件定义。
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
            uploader.makeThumb( file, function( error, src ) {   //webuploader方法
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
            $( '#'+file.id ).addClass('upload-state-done');
        });

        // 文件上传失败，显示上传出错。
        uploader.on( 'uploadError', function( file ) {
            var $li = $( '#'+file.id ),
                $error = $li.find('div.error');

            // 避免重复创建
            if ( !$error.length ) {
                $error = $('<div class="error"></div>').appendTo( $li );
            }

            $error.text('上传失败');
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader.on( 'uploadComplete', function( file ) {
            var fileStatusnum = uploader.getStats();
            if (fileStatusnum.successNum>0){
                layer.msg("上传成功"+fileStatusnum.successNum+"个文件",{icon:1,time:1000},function(index){
                    layer_close();
                });
                //刷新父级页面
                parent.$table.bootstrapTable('refresh'); //再刷新DT
            }else {
                $.fn.modalMsg("上传失败"+fileStatusnum.uploadFailNum+"个文件","error");
            }
            $( '#'+file.id ).find('.progress').remove();
        });
        $btn.on( 'click', function() {
            uploader.upload();
            //console.log("上传成功");
        });
    });
</script>
</html>
