var table;

function searchAttendance() {
    var dateRange = $('#daterange-btn span').text();
    var data = {
        dateRange: dateRange === '请选择时间范围' ? '' : dateRange
    };
    $('#search_text').val(JSON.stringify(data));
    table.search(JSON.stringify(data)).draw();
    $('#search_text').val('');
}

function clearSearch() {
    $('#daterange-btn span').html('请选择时间范围');
}

function init() {
    //定义locale汉化插件
    var locale = {
        "format": 'YYYY-MM-DD',
        "separator": " -222 ",
        "applyLabel": "确定",
        "cancelLabel": "取消",
        "clearLabel": "清除",
        "fromLabel": "起始时间",
        "toLabel": "结束时间'",
        "customRangeLabel": "自定义",
        "weekLabel": "W",
        "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
        "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        "firstDay": 1
    };
    //初始化显示当前时间
    // $('#daterange-btn span').html(moment().subtract('hours', 1).format('YYYY-MM-DD') + ' 至 ' + moment().format('YYYY-MM-DD'));
    //时间控件初始化
    $('#daterange-btn').daterangepicker(
        {
            'locale': locale,
            //汉化按钮部分
            ranges: {
                '清空': [null, null],
                '今日': [moment(), moment()],
                '昨日': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                '最近7日': [moment().subtract(6, 'days'), moment()],
                '最近30日': [moment().subtract(29, 'days'), moment()],
                '本月': [moment().startOf('month'), moment().endOf('month')],
                '上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            },
            startDate: null,
            endDate: null
        },
        function (start, end) {
            var s = start.format('YYYY-MM-DD');
            var e = end.format('YYYY-MM-DD');
            var t = s + ' 至 ' + e;

            if (start._isValid == false && end._isValid == false) {
                s = "";
                e = "";
                t ="请选择时间范围"
            }

            $('#daterange-btn span').html(t);
            $('#daterange-btn').next().val(s).next().val(e);
        }
    );
};

$(document).ready(function() {
    var columns = [
        {data: "index"},
        {data: "amStart"},
        {data: "amEnd"},
        {data: "pmStart"},
        {data: "pmEnd"},
        {data: null, render: function (data, type, row, meta) {
                var content = '<div class="button-list">'+
                    '<button onclick="removeOne(\''+row.id+'\', \'attendance/removeOne\')" type="button" class="btn btn-icon waves-effect waves-light btn-danger"> ' +
                        '<i class="fa fa-remove"></i> ' +
                    '</button>'+
                '</div>';
                return content;
            }}
    ];
    table = loadTabelWithCustomerSearcher('attendance/findPage', columns, 'attendance/addPage', '新增打卡');
    init();
});