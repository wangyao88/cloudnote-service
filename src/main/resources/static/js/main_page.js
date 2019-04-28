function gotoPage(pageUrl, title) {
    $("#title").html(title);
    $.ajax({
        url : pageUrl,
        type : "get",
        success : function(result) {
            $("#main_content").html(result);
        },
        error : function() {
            swal('系统错误', '获取页面信息失败，请稍候重试！', 'error');
        }
    });
}

function gotoUpdatePage(pageUrl, title, id) {
    $("#title").html(title);
    $.ajax({
        url : pageUrl,
        type : "get",
        data: {id: id},
        success : function(result) {
            $("#main_content").html(result);
        },
        error : function() {
            swal('系统错误', '获取页面信息失败，请稍候重试！', 'error');
        }
    });
}

function logout() {
    $.get("logout").done(function () {
        window.location.href = "login";
    });
}
var publicKey;
function getPublicKey() {
    $.ajax({
        url : 'user/getPublicKey',
        type : 'get',
        dataType: 'json',
        success : function(result) {
            if(result.status) {
                publicKey = result.data;
            }else {
                swal('系统提示', '获取公钥失败！', 'error');
            }
        },
        error : function() {
            swal('系统提示', '获取公钥失败，请稍候重试！', 'error');
        }
    });
}

function encryptPassword(password) {
    var encrypt = new JSEncrypt();
    encrypt.setPublicKey(publicKey);
    return encrypt.encrypt(password);
}

function removeOne(id, url) {
    swal({
        title: '你确定要删除吗?',
        text: "此操作不能恢复!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        confirmButtonClass: 'btn btn-success',
        cancelButtonClass: 'btn btn-danger m-l-10',
        buttonsStyling: false
    }).then(function () {
        $.ajax({
            url: url,
            type: "post",
            data: {id: id},
            dataType: 'json',
            success: function(result) {
                if(result.status) {
                    swal('成功!', result.msg, 'success');
                    table.ajax.reload(function() {

                    }, false);
                }
            },
            error: function(result) {
                swal('系统错误', result.e.message, 'error');
            }
        });
    }, function (dismiss) {
        if (dismiss === 'cancel') {
            swal('已取消', '未删除 :)','error'
            );
        }
    });
}

function loadTabel(tableUrl, columns, addUrl, addTitle) {
    var table = $('#datatable-buttons').DataTable({
        lengthChange: false,
        processing: true,
        searching: false,
        serverSide: true,
        ordering: false,
        pagingType: "full_numbers",
        columns: columns,
        ajax: {
            "url": tableUrl,
            "data": function (params) {
                var info = $('#datatable-buttons').DataTable().page.info();
                params.pageNum = info.page+1;
                params.pageSize = info.length;
                var search = $('#searchforme').val();
                if(search) {
                    params.name = '%' + search + '%';
                }else {
                    params.name = '%%';
                }
            }
        }
    });

    var toolsHtml = '' +
        '<div class="row">'+
            '<div class="col-md-6">'+
                '<button onclick="gotoPage(\''+addUrl+'\',\''+addTitle+'\')" type="button" class="btn btn-gradient btn-rounded waves-light waves-effect w-md">'+
                    '新增'+
                '</button>'+
            '</div>'+
            '<div class="col-md-6">'+
                '<div class="input-group pull-right">'+
                    '<input id="searchforme" type="text" class="form-control" placeholder="Search for...">'+
                    '<button id="clearsearchinput" type="button" class="btn btn-round btn-xs" style="position:absolute; top:6px;right:42px; z-index:99;display:none;background-color:transparent;">'+
                        '<i class="fa fa-close"></i>'+
                    '</button>'+
                    '<span class="input-group-btn">'+
                        '<button id="go" class="btn btn-default" type="button">'+
                            '<i class="fa fa-search"></i>'+
                        '</button>'+
                    '</span>'+
                '</div>'+
            '</div>'+
        '</div>';

    $("#datatable-buttons_filter").css("display","none");
    $("#datatable-buttons_wrapper").prepend(toolsHtml);

    $(document).on( 'click','#go', function () {
        table.search($('#searchforme').val()).draw();
    });

    $(document).on('keyup','#searchforme', function() {
        if($(this).val().length > 0){
            $('#clearsearchinput').css('display','block');
        }else{
            $('#clearsearchinput').css('display','none');
        }
        $(document).on('click','#clearsearchinput', function () {
            $('#searchforme').val('');
            $(this).css('display','none');
            table.search('').draw();
        });
    });
    return table;
}

function addOne(url, data, tableUrl, title) {
    $.ajax({
        url : url,
        type : 'post',
        data: data,
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        success : function(result) {
            if(result.status) {
                swal('系统提示', '新增'+title+'成功！', 'success');
                setTimeout(gotoPage(tableUrl, title+'列表'), 20);
            }else {
                swal('系统提示', '新增'+title+'失败！', 'error');
            }
        },
        error : function() {
            swal('系统提示', '新增'+title+'失败，请稍候重试！', 'error');
        }
    });
}

function updateOne(url, data, tableUrl, title) {
    $.ajax({
        url : url,
        type : 'post',
        data: data,
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        success : function(result) {
            if(result.status) {
                swal('系统提示', title+'更新成功！', 'success');
                setTimeout(gotoPage(tableUrl, title+'列表'), 20);
            }else {
                swal('系统提示', title+'更新错误！', 'error');
            }
        },
        error : function() {
            swal('系统提示', title+'更新失败，请稍候重试！', 'error');
        }
    });
}

$(document).ready(function () {
    getPublicKey();
});