var table;
function showDetail(content) {
    layer.open({
        skin: 'layui-layer-lan',
        closeBtn: 0,
        area: ['600px', '400px'],
        title: '待办事项',
        content: content,
        anim: randomNum(0,6)
    });
}

$(document).ready(function() {
    var columns = [
        {data: "title", render: function (data, type, row, meta) {
                if(row.title.length > 20) {
                    return row.title.substr(0, 20);
                }
                return row.title;
            }},
        {data: "expectedStartDate"},
        {data: "expectedEndDate"},
        {data: null, render: function (data, type, row, meta) {
                return statusMap[row.status];
            }},
        {data: null, render: function (data, type, row, meta) {
                var content = '<div class="button-list">'+
                                '<button onclick="showDetail(\''+row.title+'\')" type="button" class="btn btn-icon waves-effect waves-light btn-info"> ' +
                                    '<i class="fa fa-thumbs-o-up"></i>' +
                                '</button>'+
                              '</div>';
                return content;
            }}
    ];

    table = loadTabelWithCustomerSearcher('todo/findPage', columns, 'todo/addPage', '新增待办事项');
});