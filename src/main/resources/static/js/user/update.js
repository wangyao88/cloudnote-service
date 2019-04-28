function getUserById() {
    $.ajax({
        url : 'user/findOne',
        type : 'get',
        data: {
            id: id
        },
        dataType: 'json',
        success : function(user) {
            $('#name').val(user.name);
        },
        error : function() {
            swal('系统错误', '获取用户失败，请稍候重试！', 'error');
        }
    });
}

function updateUser() {
    // setProcess(oldPassword);
    var oldPassword = $('#oldPassword').val();
    var newPassword = $('#newPassword').val();
    var repeatNewPassword = $('#repeatNewPassword').val();
    if(newPassword !== repeatNewPassword) {
        swal('系统错误', '两次密码输入不一致！', 'error');
    }else if(getProcess($('#newPassword').val()) < 50) {
        swal('系统错误', '密码过于简单！请重新输入', 'error');
    }else {
        $.ajax({
            url : 'user/checkOldPassword',
            type : 'post',
            data: {
                id: id,
                password: encryptPassword(oldPassword)
            },
            dataType: 'json',
            success : function(result) {
                if(result.status) {
                    var data = JSON.stringify({id: id, password: encryptPassword(newPassword)});
                    updateOne('user/updatePassword', data, 'user/tablePage', '用户');
                }else {
                    swal('系统错误', '原密码输入错误！', 'error');
                }
            },
            error : function() {
                swal('系统错误', '更新密码失败，请稍候重试！', 'error');
            }
        });
    }
}

$(document).ready(function () {
    getUserById();
    $("#newPassword").keyup(function () {
        getProcess($('#newPassword').val());
    })
});