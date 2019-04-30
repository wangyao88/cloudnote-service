var table;
$(document).ready(function() {
    var columns = [
        {data: "index"},
        {data: "name"},
        {data: "projectDescribe", render: function (data, type, row, meta) {
                if(row.projectDescribe.length > 20) {
                    return row.projectDescribe.substr(0, 20);
                }
                return row.projectDescribe;
            }},
        {data: null, render: function (data, type, row, meta) {
                var content = '<div class="button-list">'+
                    '<button onclick="removeOne(\''+row.id+'\', \'project/removeOne\')" type="button" class="btn btn-icon waves-effect waves-light btn-danger"> ' +
                        '<i class="fa fa-remove"></i> ' +
                    '</button>'+
                    '<button onclick="gotoUpdatePage(\'project/updatePage\',\'更新项目信息\',\''+row.id+'\')" type="button" class="btn btn-icon waves-effect waves-light btn-warning"> ' +
                        '<i class="fa fa-wrench"></i>' +
                    '</button>'
                '</div>';
                return content;
            }}
    ];
    table = loadTableWithSimpleSearcher('project/findPage', columns, 'project/addPage', '新增项目');
});