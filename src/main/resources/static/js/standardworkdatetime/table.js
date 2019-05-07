var table;
$(document).ready(function() {
    var columns = [
        {data: "index"},
        {data: "amStart"},
        {data: "amEnd"},
        {data: "pmStart"},
        {data: "pmEnd"},
        {data: "active"},
        {data: null, render: function (data, type, row, meta) {
                var content = '<div class="button-list">'+
                    '<button onclick="removeOne(\''+row.id+'\', \'standardWorkDateTime/removeOne\')" type="button" class="btn btn-icon waves-effect waves-light btn-danger"> ' +
                        '<i class="fa fa-remove"></i> ' +
                    '</button>'+
                    '<button onclick="gotoUpdatePage(\'standardWorkDateTime/updatePage\',\'更新工作作息时间信息\',\''+row.id+'\')" type="button" class="btn btn-icon waves-effect waves-light btn-warning"> ' +
                        '<i class="fa fa-wrench"></i>' +
                    '</button>'
                '</div>';
                return content;
            }}
    ];
    table = loadTableWithSimpleSearcher('standardWorkDateTime/findPage', columns, 'standardWorkDateTime/addPage', '新增工作作息时间');
});