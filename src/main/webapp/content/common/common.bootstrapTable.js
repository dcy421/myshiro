/**
 * Created by Administrator on 2017/6/19.
 */



/** 替换数据为文字 */
function genderFormatter(value) {
    if (value == null || value == undefined) {
        return "-";
    } else if (value==1) {
        return "已删除";
    } else if(value==0){
        return "正常";
    }
}
//隔行变色
/*function rowStyle(row, index) {
    //console.log(index);
    var classes = ['active', 'success', 'info', 'warning', 'danger'];

    if (index % 2 === 0 && index / 2 < classes.length) {
        return {
            classes: classes[index / 2]
        };
    }
    return {};
}*/
/** 刷新页面 */
function refresh() {
    $('#empUserList').bootstrapTable('refresh');
}
/**查询条件与分页数据 */
/*function queryParams(params) {
    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        limit: params.limit,   //页面大小
        offset: params.offset, //页码
        sort: params.sort,  //排序列名
        order:params.order, //排序方式
        search:params.search,   //搜索框参数
        //searchText:params.search,   //搜索框参数
        //username:"223232"
    };
    return temp;
}*/
/** 编辑数据 */
function editHr() {
    var selectRow = $("#empUserList").bootstrapTable('getSelections');
    if (selectRow.length != 1) {
        layer.alert('请选择并只能选择一条数据进行编辑！', {icon: 2});
        return false;
    } else {
        window.location = createUrl("admin/hrEmployee/view?username=" + selectRow[0].userName);
    }
}
/**
 * 删除数据
 */
function deleteHr() {
    var hrs = $("#empUserList").bootstrapTable('getSelections');
    if (hrs.length < 1) {
        layer.alert('请选择一条或多条数据进行删除！', {icon: 2});
    } else {
        layer.confirm('确定要删除所选数据？', {icon: 3, title:'提示'}, function(){
            var userNames = [];
            for (var i=0;i<hrs.length;i++){
                userNames.push(hrs[i].userName);
            }
            $.ajax({
                url:'../../../admin/hrEmployee/delete',
                traditional: true,  //阻止深度序列化，向后台传送数组
                data:{userNames:userNames},
                contentType:'application/json',
                success:function(msg){
                    if(msg.success){
                        layer.alert(msg.msg,{icon:1});
                    }else{
                        layer.alert(msg.msg,{icon:2});
                    }
                    refresh();
                    $("#edit").attr({"disabled":"disabled"});
                    $("#delete").attr({"disabled":"disabled"});
                }
            })
        });
    }
}