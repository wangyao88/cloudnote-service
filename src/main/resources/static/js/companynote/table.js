var table;
$(document).ready(function() {
    var columns = [
        {data: "index"},
        {data: "createDate"},
        {data: "title"},
        {data: "content"},
        {data: "companyName"},
        {data: null, render: function (data, type, row, meta) {
                var content = '<div class="button-list">'+
                    '<button onclick="removeOne(\''+row.id+'\', \'companyNote/removeOne\')" type="button" class="btn btn-icon waves-effect waves-light btn-danger"> ' +
                        '<i class="fa fa-remove"></i> ' +
                    '</button>'+
                    '<button onclick="gotoUpdatePage(\'companyNote/updatePage\',\'更新资料信息\',\''+row.id+'\')" type="button" class="btn btn-icon waves-effect waves-light btn-warning"> ' +
                        '<i class="fa fa-wrench"></i>' +
                    '</button>'
                '</div>';
                return content;
            }}
    ];
    table = loadTabel('companyNote/findPage', columns, 'companyNote/addPage', '新增资料');
});