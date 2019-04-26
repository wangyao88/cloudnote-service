function gotoPage(pageUrl, title) {
    $("#title").html(title);
    $.ajax({
        url : pageUrl,
        type : "get",
        success : function(result) {
            $("#main_content").html(result);
        },
        error : function() {
            swal(
                '系统错误',
                '获取页面信息失败，请稍候重试！',
                'error'
            );
        }
    });
}

function gotoUpdatePage(pageUrl, title, id) {
    $("#title").html(title);
    $.ajax({
        url : pageUrl,
        type : "get",
        data: {
            id: id
        },
        success : function(result) {
            $("#main_content").html(result);
        },
        error : function() {
            swal(
                '系统错误',
                '获取页面信息失败，请稍候重试！',
                'error'
            );
        }
    });
}

function logout() {
    $.get("logout").done(function () {
        window.location.href = "login";
    });
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
            data: {
                id: id
            },
            dataType: 'json',
            success: function(result) {
                if(result.status) {
                    swal(
                        '成功!',
                        result.msg,
                        'success'
                    );
                    table.ajax.reload(function() {

                    }, false);
                }
            },
            error: function(result) {
                swal(
                    '系统错误',
                    result.e.message,
                    'error'
                );
            }
        });
    }, function (dismiss) {
        if (dismiss === 'cancel') {
            swal(
                '已取消',
                '未删除 :)',
                'error'
            );
        }
    });
}