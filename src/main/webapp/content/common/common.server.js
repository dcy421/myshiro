/**
 * Created by Administrator on 2017/6/20.
 */
/**
 * 重写bootstrapTable 的默认参数
 * 减少每页的配置  服务端配置
 */
$.extend($.fn.bootstrapTable.defaults,{
    method:'post',
    search:false,
    height : getHeight(),
    pagination : true, // 分页
    pageSize:30,//设置分页条数
    striped : true, // 是否显示行间隔色
    contentType: "application/x-www-form-urlencoded",
    //clickToSelect: true,//是否启用点击选中行
    //showToggle:true,//是否显示详细视图和列表视图的切换按钮
    cache : false, // 是否使用缓存
    //pageList : [ 20,30, 50],
    pageList: [30, 50, 100],
    showColumns : true, // 显示隐藏列
    //showRefresh : true, // 显示刷新按钮
    sidePagination : "server", // 服务端处理分页
});