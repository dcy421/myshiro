/**
 * Created by Administrator on 2017/6/28.
 */
//默认验证样式
$.validator.setDefaults({
    ignore:".ignore",
    success: function(label) {
        var labelValid=label.parent().parent();
        var labelValid_li=label.parent().parent().find("i");
        labelValid.removeClass("has-error");//删除父级的class
        labelValid.addClass("has-success");//添加成功的class
        if(labelValid_li.length > 0){
            labelValid_li.removeClass("fa fa-times-circle-o");//删除错误的图标
            labelValid_li.addClass("fa fa-check");//添加成功的图标
        }else {
            labelValid.find("label").prepend("<i class=\"fa fa-check\"></i>")//添加错误的图标
        }
    },
    errorClass:"help-block",
    errorPlacement: function(error, element) {
        error.appendTo(element.parent());//添加错误信心
    },
    errorElement: "span",
    //单条校验失败，后会调用此方法，在此方法中，编写错误消息如何显示 及  校验失败回调方法
    showErrors : function(errorMap, errorList) {
        // 遍历错误列表
        for(var obj in errorMap) {
            // 自定义错误提示效果
            var labelValidP =$('#' + obj).parent().parent();
            var labelValid_li =$('#' + obj).parent().parent().find("i");
            labelValidP.removeClass('has-success');
            labelValidP.addClass('has-error');
            if(labelValid_li.length > 0){
                labelValid_li.remove();//删除错误的图标
            }
            labelValidP.find("label").prepend("<i class=\"fa fa-times-circle-o\"></i>")//添加错误的图标
        }
        // 此处注意，一定要调用默认方法，这样保证提示消息的默认效果
        this.defaultShowErrors();
    }
});