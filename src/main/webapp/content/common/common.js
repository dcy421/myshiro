/**
 * Created by Administrator on 2017/6/19.
 */
var common ={
    height:$(window).height() - 240 //页面剪掉的高度
}
function getHeight() {
    return $(window).height() - 50-39-15-119;
}
function rowStyle(row, index) {
    //console.log(index);
    var classes = ['active', 'success', 'info', 'warning', 'danger'];

    if (index % 2 === 0 && index / 2 < classes.length) {
        return {
            classes: classes[index / 2]
        };
    }
    return {};
}
//去左空格;
function ltrim(s) {
    return s.replace(/(^\s*)/g, "");
}
//去右空格;
function rtrim(s) {
    return s.replace(/(\s*$)/g, "");
}
//去左右空格;
function trim(s) {
    return s.replace(/(^\s*)|(\s*$)/g, "");
}