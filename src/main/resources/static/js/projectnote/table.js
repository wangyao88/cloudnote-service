var table;

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

function searchProjectNote() {
    var title = $('#search_project_note_title').val();
    var content = $('#search_project_note_content').val();
    var conpanyId = $('#search_project_note_projectId').val();
    var dateRange = $('#daterange-btn span').text();
    var data = {
        title: '%' + title + '%',
        content: '%' + content + '%',
        projectId: conpanyId,
        dateRange: dateRange === '请选择日期范围' ? '' : dateRange
    };
    $('#search_text').val(JSON.stringify(data));
    table.search(JSON.stringify(data)).draw();
    $('#search_text').val('');
}

function clearSearch() {
    $('#search_project_note_title').val('');
    $('#search_project_note_content').val('');
    $('#search_project_note_projectId').val('');
    $('#daterange-btn span').html('请选择日期范围');
}

function showDetail(title, content) {
    layer.open({
        skin: 'layui-layer-lan',
        closeBtn: 0,
        area: ['600px', '400px'],
        title: title,
        content: content,
        anim: randomNum(0,6)
    });
}

$(document).ready(function() {
    var columns = [
        {data: "index"},
        {data: "createDate"},
        {data: "title"},
        {data: "content", render: function (data, type, row, meta) {
                if(row.content.length > 20) {
                    return row.content.substr(0, 20);
                }
                return row.content;
            }},
        {data: "projectName"},
        {data: null, render: function (data, type, row, meta) {
                var content = '<div class="button-list">'+
                                '<button onclick="removeOne(\''+row.id+'\', \'projectNote/removeOne\')" type="button" class="btn btn-icon waves-effect waves-light btn-danger"> ' +
                                    '<i class="fa fa-remove"></i> ' +
                                '</button>'+
                                '<button onclick="gotoUpdatePage(\'projectNote/updatePage\',\'更新资料信息\',\''+row.id+'\')" type="button" class="btn btn-icon waves-effect waves-light btn-warning"> ' +
                                    '<i class="fa fa-wrench"></i>' +
                                '</button>'+
                                '<button onclick="showDetail(\''+row.title+'\',\''+row.content+'\')" type="button" class="btn btn-icon waves-effect waves-light btn-info"> ' +
                                    '<i class="fa fa-thumbs-o-up"></i>' +
                                '</button>'+
                              '</div>';
                return content;
            }}
    ];

    table = loadTabelWithCustomerSearcher('projectNote/findPage', columns, 'projectNote/addPage', '新增资料');
    init();
});