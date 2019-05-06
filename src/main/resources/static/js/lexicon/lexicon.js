function loadData() {
    swal({
        title: '你确定要重新加载词库吗?',
        text: "此操作不能恢复!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonText: '加载',
        cancelButtonText: '取消',
        confirmButtonClass: 'btn btn-success',
        cancelButtonClass: 'btn btn-danger m-l-10',
        buttonsStyling: false
    }).then(function () {
        $.toast({
            text: '开始加载词库',
            heading: '系统提示',
            showHideTransition: 'slide',
            position: 'top-right',
            loaderBg: '#1ea69a',
            hideAfter: 3000,
            stack: 1
        });
        $.ajax({
            url: 'lexicon/loadData',
            type: "post",
            dataType: 'json',
            success: function(result) {
                if(result.status) {
                    swal('成功!', result.msg, 'success');
                }
            },
            error: function(result) {
                swal('系统错误', result.e.message, 'error');
            }
        });
    }, function (dismiss) {
        if (dismiss === 'cancel') {
            swal('已取消', '未加载 :)','success'
            );
        }
    });
}

function refreshDict() {
    $.toast({
        text: '开始刷新词库',
        heading: '系统提示',
        showHideTransition: 'plain',
        position: 'top-right',
        loaderBg: '#1ea69a',
        hideAfter: 3000,
        stack: 1
    })
    $.ajax({
        url: 'lexicon/refreshDict',
        type: "post",
        dataType: 'json',
        success: function(result) {
            if(result.status) {
                swal('成功!', result.msg, 'success');
            }
        },
        error: function(result) {
            swal('系统错误', result.e.message, 'error');
        }
    });
}