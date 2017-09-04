/**
 * Created by Administrator on 2017/8/29.
 */
getLessonPicPersonByYear();
selectLessonListProportion();
getUserAttByNowDayProportion();

/**
 * 获取人员和钱的对比
 */
function getLessonPicPersonByYear() {
    $.ajax({
        url : "/user/getLessonPicPersonByYear",
        type: "Post",
        dataType : "json",
        async: false,
        success : function(result) {
            //console.log(result);
            var opt1 = MyECharts.ChartOptionTemplates.Bar('', '', result);
            MyECharts.RenderChart(opt1, 'container1');
        }
    });
}

/**
 * 获取课程对比
 */
function selectLessonListProportion() {
    $.ajax({
        url : "/user/selectLessonListProportion",
        type: "Post",
        dataType : "json",
        async: false,
        success : function(result) {
            var opt3 = MyECharts.ChartOptionTemplates.Pie('', '', result);
            MyECharts.RenderChart(opt3, 'container3');
        }
    });
}

/**
 * 获取考勤数据
 */
function getUserAttByNowDayProportion() {
    $.ajax({
        url : "/user/getUserAttByNowDayProportion",
        type: "Post",
        dataType : "json",
        async: false,
        success : function(result) {
            var opt2 = MyECharts.ChartOptionTemplates.Line('', '', result);
            MyECharts.RenderChart(opt2, 'container2');
        }
    });
}
