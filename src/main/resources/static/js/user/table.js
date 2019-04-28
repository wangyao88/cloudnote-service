var table;
$(document).ready(function() {
    var columns = [
        {data: "index"},
        {data: "name"},
        {data: null, render: function (data, type, row, meta) {
                var content = '<div class="button-list">'+
                    '<button onclick="removeOne(\''+row.id+'\', \'user/removeOne\')" type="button" class="btn btn-icon waves-effect waves-light btn-danger"> ' +
                    '<i class="fa fa-remove"></i> ' +
                    '</button>'+
                    '<button onclick="updateName(\''+row.id+'\')" type="button" class="btn btn-icon waves-effect waves-light btn-info"> ' +
                    '<i class="fa fa-wrench"></i>' +
                    '</button>'+
                    '<button onclick="gotoUpdatePage(\'user/updatePage\',\'更新用户\',\''+row.id+'\')" type="button" class="btn btn-icon waves-effect waves-light btn-warning"> ' +
                    '<i class="fa fa-keyboard-o"></i>' +
                    '</button>'
                '</div>';
                return content;
            }}
    ];
    table = loadTabel('user/findPage', columns, 'user/addPage', '新增用户');
});

function updateName(id) {
    swal({
        title: '修改用户名',
        input: 'text',
        showCancelButton: true,
        confirmButtonText: '提交',
        showLoaderOnConfirm: true,
        cancelButtonText: '取消',
        confirmButtonClass: 'btn btn-success',
        cancelButtonClass: 'btn btn-danger m-l-10',
        preConfirm: function (name) {
            return new Promise(function (resolve, reject) {
                if(name === '') {
                    reject('用户名不能为空');
                }else {
                    $.ajax({
                        url: 'user/update',
                        type: 'post',
                        data: JSON.stringify({id: id, name: name}),
                        contentType: "application/json; charset=utf-8",
                        dataType: 'json',
                        success: function (result) {
                            if(result.status) {
                                table.ajax.reload(function() {

                                }, false);
                                resolve();
                            }
                            if(!result.status) {
                                swal('系统错误', '更新姓名失败，请稍候重试！', 'error');
                            }
                        },
                        error: function (result) {
                            swal('系统错误', '更新姓名失败，请稍候重试！', 'error');
                        }
                    });
                }
            })
        },
        allowOutsideClick: false
    }).then(function (name) {
        swal({type: 'success',title: '更新姓名成功！',})
    })
}