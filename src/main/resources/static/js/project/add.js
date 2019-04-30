function addProject() {
    var name = $('#name').val();
    var projectDescribe = $('#projectDescribe').val();
    if(!name) {
        swal('系统提示', '项目名称为必填项！', 'error');
    }
    $.ajax({
        url : 'project/checkName',
        type : 'post',
        data: {name: name},
        dataType: 'json',
        success : function(result) {
            if(result.status) {
                var data = JSON.stringify({
                    name: name,
                    projectDescribe: projectDescribe
                });
                addOne('project/add', data, 'project/tablePage', '项目');
            }else {
                swal('系统错误', '项目名已存在！', 'error');
            }
        },
        error : function() {
            swal('系统错误', '新增项目失败，请稍候重试！', 'error');
        }
    });
}