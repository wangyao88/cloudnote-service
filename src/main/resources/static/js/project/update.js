function getProjectById() {
    $.ajax({
        url : 'project/findOne',
        type : 'get',
        data: {id: id},
        dataType: 'json',
        success : function(project) {
            $('#name').val(project.name);
            $('#projectDescribe').val(project.projectDescribe);
        },
        error : function() {
            swal('系统错误', '获取项目信息失败，请稍候重试！', 'error');
        }
    });
}

function updateProject() {
    var name = $('#name').val();
    var projectDescribe = $('#projectDescribe').val();
    if(!name) {
        swal('系统提示', '项目名称为必填项！', 'error');
    }
    var data = JSON.stringify({
        id: id,
        name: name,
        projectDescribe: $('#projectDescribe').val()
    });
    updateOne('project/update', data, 'project/tablePage', '项目');
}

$(document).ready(function () {
    getProjectById();
});