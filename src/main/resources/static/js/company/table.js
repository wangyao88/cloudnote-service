var table;
$(document).ready(function() {
    var columns = [
        {data: "index"},
        {data: "name"},
        {data: "flag"},
        {data: "address"},
        {data: "inDate"},
        {data: "outDate"},
        {data: null, render: function (data, type, row, meta) {
                var content = '<div class="button-list">'+
                    '<button onclick="removeOne(\''+row.id+'\', \'company/removeOne\')" type="button" class="btn btn-icon waves-effect waves-light btn-danger"> ' +
                        '<i class="fa fa-remove"></i> ' +
                    '</button>'+
                    '<button onclick="gotoUpdatePage(\'company/updatePage\',\'更新公司信息\',\''+row.id+'\')" type="button" class="btn btn-icon waves-effect waves-light btn-warning"> ' +
                        '<i class="fa fa-keyboard-o"></i>' +
                    '</button>'
                '</div>';
                return content;
            }}
    ];
    table = loadTabel('company/findPage', columns, 'company/addPage', '新增公司');
});