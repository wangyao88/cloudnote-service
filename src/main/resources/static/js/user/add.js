function addUser() {
    var name = $('#name').val();
    var password = $('#password').val();
    var repeatPassword = $('#repeatPassword').val();
    if(password !== repeatPassword) {
        swal('系统错误', '两次密码输入不一致！', 'error');
    }else if(getProcess($('#password').val()) < 50) {
        swal('系统错误', '密码过于简单！请重新输入', 'error');
    }else {
        $.ajax({
            url : 'user/checkName',
            type : 'post',
            data: {
                name: name,
            },
            dataType: 'json',
            success : function(result) {
                if(result.status) {
                    var data = JSON.stringify({name: name, password: encryptPassword(password)});
                    addOne('user/add', data, 'user/tablePage', '用户');
                }else {
                    swal('系统错误', '用户名已存在！', 'error');
                }
            },
            error : function() {
                swal('系统错误', '新增用户失败，请稍候重试！', 'error');
            }
        });
    }
}

$(document).ready(function () {
    $("#password").keyup(function () {
        getProcess($('#password').val());
    })
});