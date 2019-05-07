function getTodoById() {
    $.ajax({
        url : 'todo/findOne',
        type : 'get',
        data: {id: id},
        dataType: 'json',
        success : function(todo) {
            tinyMCE.activeEditor.setContent(todo.title);
            $('#todo_status').val(todo.status);
            $('#projectId').val(todo.projectId);
            $("#expectedStartDate").datetimepicker("setDate", convertDate(todo.expectedStartDate));
            $("#expectedEndDate").datetimepicker("setDate", convertDate(todo.expectedEndDate));
        },
        error : function() {
            swal('系统错误', '获取待办事项失败，请稍候重试！', 'error');
        }
    });
}

function updateTodo() {
    var title = tinyMCE.activeEditor.getContent();
    var status = $('#todo_status').val();
    var projectId = $('#projectId').val();
    var expectedStartDateStr = $('#expectedStartDate').val();
    var expectedEndDateStr = $('#expectedEndDate').val();
    var expectedStartDate = null;
    var expectedEndDate = null;
    if(expectedStartDateStr) {
        expectedStartDate = newDate(expectedStartDateStr);
    }else {
        swal('系统提示', '预计开始日期为必填项！', 'error');
        return;
    }
    if(expectedEndDateStr) {
        expectedEndDate = newDate(expectedEndDateStr);
    }else {
        swal('系统提示', '预计完成日期为必填项！', 'error');
        return;
    }
    if(expectedStartDateStr && expectedEndDateStr) {
        if(expectedStartDate > expectedEndDate) {
            swal('系统提示', '预计开始日期大于预计完成日期！', 'error');
            return;
        }
    }

    var data = JSON.stringify({
        id: id,
        title: title,
        status: status,
        projectId: projectId,
        expectedStartDate: expectedStartDate,
        expectedEndDate: expectedEndDate
    });
    updateOne('todo/update', data, 'todo/tablePage', '项目资料');
}

function initDatepicker(dateId) {
    $(dateId).datetimepicker({
        language: "zh-CN",
        autoclose: true,
        clearBtn: true,
        todayBtn: true,
        minView: "month",
        format: "yyyy-mm-dd"
    });
}

$(document).ready(function () {
    initDatepicker('#expectedStartDate');
    initDatepicker('#expectedEndDate');
    tinyMCE.activeEditor.remove("#todo_title");
    initTodoTitleContainer();
    getTodoById();
});