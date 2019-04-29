var table;

function getompanySelectorForSearch() {
    var content = '<select id="search_company_note_companyId" class="form-control">'+
                    '<option value="" selected>请选择所属公司</option>';
    $(companies).each(function (index, company) {
        content += '<option value="'+company.id+'">'+company.name+'</option>';
    });
    content += '</select>';
    return content;
}

function searchCompanyNote() {
    
}

function getSearcher(addUrl, addTitle) {
    var toolsHtml = '' +
        '<div class="row">'+
            '<div class="col-md-12">'+
                '<div class="card-box">'+
                    '<div class="button-list">'+
                        '<button onclick="gotoPage(\''+addUrl+'\',\''+addTitle+'\')"type="button" class="btn btn-gradient waves-light waves-effect w-md">'+
                            '新增'+
                        '</button>'+
                        '<button onclick="searchCompanyNote()" type="button" class="btn btn-pink waves-light waves-effect w-md">'+
                            '搜索'+
                        '</button>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>'+
        '<div class="row">'+
            '<div class="col-md-12">'+
                '<div class="card-box">'+
                    '<div class="form-row">'+
                        '<div class="form-group col-md-2">'+
                            '<input id="search_company_note_title" type="text" class="form-control" placeholder="标题">'+
                        '</div>'+
                        '<div class="form-group col-md-2">'+
                            '<input id="search_company_note_content" type="text" class="form-control" placeholder="内容">'+
                        '</div>'+
                        '<div class="form-group col-md-2">'+
                            getompanySelectorForSearch()+
                        '</div>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>';
    return toolsHtml;
}

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

    var searcher = getSearcher('companyNote/addPage', '新增资料');

    table = loadTabelWithCustomerSearcher('companyNote/findPage', columns, 'companyNote/addPage', '新增资料', searcher);
});