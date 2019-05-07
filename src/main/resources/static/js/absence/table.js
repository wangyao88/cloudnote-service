var table;

function initWeekendOfCurrentYear() {
    $.ajax({
        url : 'absence/initWeekendOfCurrentYear',
        type : 'post',
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        success : function(result) {
            if(result.status) {
                swal('系统提示', '一键添加本年周末成功！', 'success');
                table.ajax.reload(false);
            }else {
                swal('系统提示', '一键添加本年周末失败！', 'error');
            }
        },
        error : function() {
            swal('系统提示', '一键添加本年周末失败，请稍候重试！', 'error');
        }
    });
}

function searchAbsence() {
    var name = $('#search_absence_name').val();
    var dateRange = $('#daterange-btn span').text();
    var data = {
        name: '%' + name + '%',
        dateRange: dateRange === '请选择日期范围' ? '' : dateRange
    };
    $('#search_text').val(JSON.stringify(data));
    table.search(JSON.stringify(data)).draw();
    $('#search_text').val('');
}

function clearSearch() {
    $('#search_absence_name').val('');
    $('#daterange-btn span').html('请选择日期范围');
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
    //日期控件初始化
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
                t ="请选择日期范围"
            }

            $('#daterange-btn span').html(t);
            $('#daterange-btn').next().val(s).next().val(e);
        }
    );
};

$(document).ready(function() {
    var columns = [
        {data: "index"},
        {data: "name"},
        {data: "tips"},
        {data: "absenceStart"},
        {data: "absenceEnd"},
        {data: null, render: function (data, type, row, meta) {
                var content = '<div class="button-list">'+
                    '<button onclick="removeOne(\''+row.id+'\', \'absence/removeOne\')" type="button" class="btn btn-icon waves-effect waves-light btn-danger"> ' +
                        '<i class="fa fa-remove"></i> ' +
                    '</button>'+
                    '<button onclick="gotoUpdatePage(\'absence/updatePage\',\'更新节假日信息\',\''+row.id+'\')" type="button" class="btn btn-icon waves-effect waves-light btn-warning"> ' +
                        '<i class="fa fa-wrench"></i>' +
                    '</button>'
                '</div>';
                return content;
            }}
    ];
    table = loadTabelWithCustomerSearcher('absence/findPage', columns, 'absence/addPage', '新增节假日');
    init();
});