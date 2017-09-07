/**
 * Created by Administrator on 2017/9/7.
 */
$(function () {
    //iCheck for checkbox and radio inputs
    $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%' // optional
    });
    $('body').on('click','#roleold option',function () {
        //console.log($(this).parent().html());
        //删除自己
        $(this).remove();
        //添加新的
        $('#rolenew').append('<option value="'+$(this).val()+'">'+$(this).html()+'</option>');
    });
    $('body').on('click','#rolenew option',function () {
        //console.log($(this).parent().html());
        //删除自己
        $(this).remove();
        //添加新的
        $('#roleold').append('<option value="'+$(this).val()+'">'+$(this).html()+'</option>');
    });
});