/**
 * Created by Administrator on 2017/6/16.
 */
var DataTablePaging = {
    language : {
        zh_cn : {
            processing : "数据正在加载中...",
            search : "查询:",
            lengthMenu : "每页显示 _MENU_ 条记录",
            info : "从 _START_ 到 _END_ /共 _TOTAL_ 条记录",
            infoEmpty : "从 0 到  0  共 0  条记录",
            infoFiltered : "(从 _MAX_ 条数据中检索)",
            infoPostFix : "",
            thousands : ",",
            loadingRecords : "数据加载中...",
            zeroRecords : "没有检索到数据",
            emptyTable : "没有数据",
            paginate : {
                first : "首页",
                previous : "前一页",
                next : "后一页",
                last : "尾页"
            },
            aria : {
                sortAscending : ": 升序",
                sortDescending : ": 降序"
            }
        }
    },
    /**
     * 获取ajax分页options设置
     */
    /*getAjaxPagingOptions : function(settings) {
        var options = {
            ajax : {
                url : settings.ajaxUrl,
                type : "post"
            },
            serverSide : true,
            destroy : true,
            processing : true,
            ordering : true,
            searching : false,
            paging : true,
            pagingType : "full_numbers",
            lengthMenu : [ 10, 20, 50, 100 ],
            pageLength : 20,
            order : settings.order,// [index,'asc|desc']
            language : DataTablePaging.language.zh_cn,
            columns : settings.colums,
            columnDefs : settings.columsdefs,
        };
        return options;
    },
    /!**
     * 获取ajax不分页options设置
     *!/
    getOptions : function(settings) {
        var options = {
            ajax : {
                url : settings.ajaxUrl,
                type : "post"
            },
            serverSide : false,
            destroy : true,
            processing : true,
            ordering : true,
            searching : false,
            pagingType : "full_numbers",
            lengthMenu : [ 10, 20, 50, 100 ],
            pageLength : 20,
            language : DataTablePaging.language.zh_cn,
            columns : settings.colums,
            columnDefs : settings.columsdefs,
        };
        return options;
    },
    /!**
     * 获取非ajax分页options设置
     *!/
    getNotAjaxPagingOptions : function(settings) {
        var options = {
            serverSide : false,
            destroy : true,
            processing : true,
            ordering : false,
            searching : false,
            paging : true,
            pagingType : "full_numbers",
            lengthChange : false,
            pageLength : settings.pageLength,
            info : false,
            language : DataTablePaging.language.zh_cn,
            columns : settings.colums,
            columnDefs : settings.columsdefs
        };
        return options;
    },
    /!**
     * 获取非ajax不分页options设置
     *!/
    getNotAjaxOptions : function(settings) {
        var options = {
            serverSide : false,
            destroy : true,
            processing : true,
            ordering : false,
            searching : false,
            paging : false,
            scrollX : true,
            info : false,
            language : DataTablePaging.language.zh_cn,
            columns : settings.colums,
            columnDefs : settings.columsdefs
        };
        return options;
    }*/
};
$.extend($.fn.dataTable.defaults, {
    dom: 't<"dataTables_info"il>p',
    processing: true, //当datatable获取数据时候是否显示正在处理提示信息。
    serverSide: true, //处理分页
    autoWidth : false,//控制Datatables是否自适应宽度
    pagingType : "full_numbers",
    lengthMenu : [ 10, 20, 50, 100 ],
    responsive: {
        details: false
    },
    language : DataTablePaging.language.zh_cn,
    initComplete: function(settings) {
        var _$this = this;
        /**
         * 重写搜索事件
         */
        $('#doSearch').bind('click', function(e) {
            _$this.api().ajax.reload();
        });
        //回车响应事件
        $('#search').bind('keyup', function(e) {
            if(e.keyCode == 13 || (e.keyCode == 8 && (this.value.length == 0))) {
                _$this.api().ajax.reload();
            }
        });
    },
    drawCallback: drawCallbackDefault
});

/**
 * DT绘制完成回调
 * 单独写出来是方便二次定制
 *
 * 默认回调函数功能：
 * 1.DT第一列checkbox初始化从icheck
 * 2.iCheck全选、取消多选、多选与单选双向关联
 * 3.选中的tr加上selected class
 *
 * @param {Object} settings
 */
function drawCallbackDefault(settings, _$this) {
    //console.log('drawCallbackDefault');
    _$this = (isExitsVariable('_$this') && _$this) ? _$this : this;
    selector = _$this.selector;
    $(selector + ' input').iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue',
        increaseArea: '20%', // optional
        labelHover: true
    });

    /**
     * DT thead iCheck 点击事件
     */
    $(selector + ' input[name=all]').on('ifChecked ifUnchecked', function(e) {
        $(this).closest('table').find('input[name=single]').each(function() {
            if(e.type == 'ifChecked') {
                $(this).iCheck('check');
                $(this).closest('tr').addClass('selected');
            } else {
                $(this).iCheck('uncheck');
                $(this).closest('tr').removeClass('selected');
            }
        });
    });

    /**
     * DT tbody iCheck点击事件
     */
    $(selector + ' input[name=single]').on('ifChecked ifUnchecked', function(e) {
        if(e.type == 'ifChecked') {
            $(this).iCheck('check');
            $(this).closest('tr').addClass('selected');
            //全选单选框的状态处理
            var selected = _$this.api().rows('.selected').data().length; //被选中的行数
            var recordsDisplay = _$this.api().page.info().recordsDisplay; //搜索条件过滤后的总行数
            var iDisplayStart = _$this.api().page.info().start; // 起始行数
            if(selected === _$this.api().page.len() || selected === recordsDisplay || selected === (recordsDisplay - iDisplayStart)) {
                $(selector + ' input[name=all]').iCheck('check');
            }
        } else {
            $(this).iCheck('uncheck');
            $(this).closest('tr').removeClass('selected');
            $(selector + ' input[name=all]').attr('checked', false);
            $(selector + ' input[name=all]').iCheck('update');
        }
    });

    /**
     * 检测参数是否定义
     * @param {Object} variableName
     */
    function isExitsVariable(variableName) {
        try {
            if(typeof(variableName) == "undefined") {
                return false;
            } else {
                return true;
            }
        } catch(e) {}
        return false;
    }
}



