/**
 * Created by Administrator on 2017/8/23.
 */
$(function() {
    addTabs(({ id: '10008', title: '欢迎页', close: false, url: '/user/welcome' }));
    App.fixIframeCotent();
    var menus = [
        { id: "10010", text: "我的工作台", isHeader: true },
        {
            id: "10020",
            isOpen: false,
            text: "SuperShopUI",
            icon: "fa fa-bookmark-o",
            children: [
                {
                    id: "10021",
                    text: "页面加载",
                    isOpen: false,
                    icon: "fa fa-circle-o",
                    children: [
                        { id: "10022", text: "iframe加载", url: "${pageContext.request.contextPath}/admin/index_iframe.html", targetType: "blank", icon: "fa fa-spinner" },
                        { id: "10023", text: "ajax加载", url: "${pageContext.request.contextPath}/admin/index.html", targetType: "blank", icon: "fa fa-refresh" },
                        { id: "10023", text: "原生页面加载", url: "${pageContext.request.contextPath}/admin/index_page.html", targetType: "blank", icon: "fa fa-refresh" }

                    ]
                }
            ]
        },

        {
            id: "10001",
            text: "基础UI",

            icon: "fa fa-circle-o",
            children: [
                { id: "10004", text: "按钮", url: "${pageContext.request.contextPath}/components/buttons.html", targetType: "iframe-tab", icon: "fa fa-square" },
                { id: "100041", text: "用户管理", url: "${pageContext.request.contextPath}/user/userIndex?number="+Math.random(), targetType: "iframe-tab", icon: "fa fa-square" },
                { id: "100042", text: "用户管理BT", url: "${pageContext.request.contextPath}/user/userIndexBT?number="+Math.random(), targetType: "iframe-tab", icon: "fa fa-square" },
                { id: "100044", text: "角色管理", url: "${pageContext.request.contextPath}/role/roleIndex?number="+Math.random(), targetType: "iframe-tab", icon: "fa fa-square" },
                { id: "100043", text: "sql监视", url: "${pageContext.request.contextPath}/druid/index.html", targetType: "iframe-tab", icon: "fa fa-square" },
                { id: "10003", text: "常用组件", url: "${pageContext.request.contextPath}/components/general.html", targetType: "iframe-tab", icon: "fa fa-list-alt" },

                { id: "10012", text: "图标库", url: "${pageContext.request.contextPath}/components/icons.html", targetType: "iframe-tab", icon: "fa fa-circle-o" },
                {
                    id: "10203",
                    text: "表单组件",
                    url: "${pageContext.request.contextPath}/forms/general.html",
                    targetType: "iframe-tab",
                    icon: "fa fa-plus-square-o"
                },
                {
                    id: "10204",
                    text: "表单扩展组件",
                    url: "${pageContext.request.contextPath}/forms/advanced.html",
                    targetType: "iframe-tab",
                    icon: "fa fa-plus-square-o"
                },
                { id: "10005", text: "Block UI", url: "${pageContext.request.contextPath}/components/blockui.html", targetType: "iframe-tab", icon: "fa fa-spinner" },
                { id: "10013", text: "sliders组件",tips:5, url: "${pageContext.request.contextPath}/components/sliders.html", targetType: "iframe-tab", icon: "fa fa-list-ol" },
                { id: "10204", text: "switch按钮", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/components/bootstrapswitch.html", icon: "fa fa-toggle-on" },
                { id: "10017", text: "面板", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/components/widgets.html", icon: "fa fa-circle-o" }
            ]
        },

        {
            id: "10202",
            text: "插件",

            targetType: "iframe-tab",
            icon: "fa fa-circle-o",
            children: [
                { id: "10026", text: "layer弹出层", targetType: "iframe-tab",  url: "${pageContext.request.contextPath}/components/layer.html", icon: "fa fa-circle-o" },
                { id: "10006", text: "日历选择控件", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/component-extend/calendar.html", icon: "fa fa-circle-o" },
                { id: "10014", text: "时间轴", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/component-extend/timeline.html", icon: "fa fa-circle-o" },
                { id: "10010", text: "页面加载效果", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/component-extend/pageprogress.html", icon: "fa fa-circle-o" },
                { id: "10016", text: "树", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/component-extend/jstree.html", icon: "fa fa-circle-o" },
                //{ id: "10241", text: "下拉框", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/component-extend/bootstrap_select.html", icon: "fa fa-minus-square-o" },

                { id: "10014", text: "日起选择组件", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/component-extend/datetimepickers.html", icon: "fa fa-calendar" },
                { id: "10242", text: "select2下拉框", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/component-extend/select2.html", icon: "fa fa-circle-o" },
                { id: "10205", text: "多选框", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/component-extend/bootstraptagsinput.html", icon: " fa fa-check-square-o" },
                { id: "10206", text: "多文件上传组件", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/component-extend/formfileupload.html", icon: "  fa fa-circle-o " }
            ]

        },
        {
            id: "10208",
            text: "表格组件",

            icon: "fa fa-circle-o",
            children: [
                { id: "10211", text: "bootstraptable表格", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/tables/basetable.html", icon: "fa fa-table" },
                { id: "10212", text: "管理表格", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/tables/managetable.html", icon: "fa fa-table" },
                { id: "10213", text: "jqgrid表格", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/tables/jqgrid.html", icon: "fa fa-table" }
            ]
        },
        {
            id: "10209", text: "通用模板", isOpen: false,  icon: "fa fa-circle-o", children: [
            { id: "10214", text: "企业站", targetType: "blank", url: "http://www.supermgr.cn", icon: "fa fa-circle-o" }//,
            //{ id: "10215", text: "微信端", targetType: "blank", url: "${pageContext.request.contextPath}/template/test2.html", icon: "fa fa-circle-o" }
        ]

        },

        {
            id: "20209", text: "布局", isOpen: false, icon: "fa fa-circle-o", children: [
            { id: "20214", text: "盒式布局", targetType: "blank", url: "${pageContext.request.contextPath}/layout/boxed.html", icon: "fa fa-circle-o" },
            { id: "20215", text: "自适应布局", targetType: "blank", url: "${pageContext.request.contextPath}/layout/fixed.html", icon: "fa fa-circle-o" },
            { id: "20216", text: "顶部菜单", targetType: "blank", url: "${pageContext.request.contextPath}/layout/top-nav.html", icon: "fa fa-circle-o" },
            { id: "20217", text: "左侧菜单收缩", targetType: "blank", url: "${pageContext.request.contextPath}/layout/collapsed-sidebar.html", icon: "fa fa-circle-o" }
        ]

        },
        {
            id: "30209", text: "图表", isOpen: false, icon: "fa fa-circle-o", children: [
            { id: "30214", text: "chart图表", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/charts/chartjs.html", icon: "fa fa-circle-o" },
            { id: "30215", text: "flot图表", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/charts/flot.html", icon: "fa fa-circle-o" },
            { id: "30216", text: "inline图表", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/charts/inline.html", icon: "fa fa-circle-o" },
            { id: "30217", text: "morris图表", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/charts/morris.html", icon: "fa fa-circle-o" },
            {
                id: "40001",
                text: "echart图表",

                icon: "fa fa-circle-o",
                children: [
                    { id: "40002", text: "折线图", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/charts/echarts/echarts_line.html", icon: "fa fa-circle-o" },
                    { id: "40003", text: "柱状图", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/charts/echarts/echarts_column.html", icon: "fa fa-circle-o" },
                    { id: "40004", text: "仪表盘", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/charts/echarts/echarts_dashboard.html", icon: "fa fa-circle-o" },
                    { id: "40005", text: "热力图", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/charts/echarts/echarts_hotMap.html", icon: "fa fa-circle-o" },
                    { id: "40006", text: "雷达图", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/charts/echarts/echarts_radar.html", icon: "fa fa-circle-o" },
                    { id: "40007", text: "k线图", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/charts/echarts/echarts_kLine.html", icon: "fa fa-circle-o" },
                    { id: "40008", text: "地图", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/charts/echarts/echarts_map.html", icon: "fa fa-circle-o" },
                    { id: "40009", text: "更多案例", targetType: "blank", url: "http://echarts.baidu.com/examples.html", icon: "fa fa-circle-o" }
                ]
            }
        ]

        },
        {
            id: "30209", text: "页面实例", isOpen: false, icon: "fa fa-circle-o", children: [
            {
                id: "30208",
                text: "邮件管理器",

                icon: "fa fa-circle-o",
                children: [
                    { id: "30211", text: "邮件管理", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/pages/mailbox/mailbox.html", icon: "fa fa-table" },
                    { id: "30212", text: "阅读邮件", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/pages/mailbox/read-mail.html", icon: "fa fa-table" },
                    { id: "30213", text: "发送邮件", targetType: "iframe-tab", url: "${pageContext.request.contextPath}/pages/mailbox/compose.html", icon: "fa fa-table" }
                ]
            },
            {
                id: "40208",
                text: "SuperMgr后台Demo",
                icon: "fa fa-circle-o",
                targetType: "blank", url: "${pageContext.request.contextPath}/pages/supermgr/index.html"
            }
        ]

        }
    ];
    /*$.ajax({
        url : "/menu/getMenuListByUserName",
        type: "Post",
        dataType : "json",
        async: false,
        data:  {userName:userName},
        success : function(result) {
            //console.log(result);
            menus = result;
        }
    });*/
    $('.sidebar-menu').sidebarMenu({ url: "/menu/getMenuListByUserName", param: { userName: userName } });

    //我的选课
    $('body').on('click','#mylesson',function () {
        //alert("我的选课");
        var num = Math.random()*700 + 10000;
        addTabs(({ id:  parseInt(num, 10), title: '我的选课', close: true, url: '/sched/index?type=3' }));
    });
    //修改密码
    $('body').on('click','#modifyPassword',function () {
        layer_show('修改密码','/user/modify','780','360');
    });
    $('body').on('click','#editUser',function () {
        layer_show('修改资料','/user/edituser','800','600');
    });
});