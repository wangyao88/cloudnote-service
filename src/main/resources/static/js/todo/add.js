function addTodo() {
    var title = tinyMCE.activeEditor.getContent();
    var status = $('#todo_status').val();
    var projectId = $('#projectId').val();
    var expectedStartDateStr = $('#expectedStartDate').val();
    var expectedEndDateStr = $('#expectedEndDate').val();
    var expectedStartDate = null;
    var expectedEndDate = null;
    if(expectedStartDateStr) {
        expectedStartDate = new Date(expectedStartDateStr);
    }else {
        swal('系统提示', '预计开始日期为必填项！', 'error');
        return;
    }
    if(expectedEndDateStr) {
        expectedEndDate = new Date(expectedEndDateStr);
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
        title: title,
        status: status,
        projectId: projectId,
        expectedStartDate: expectedStartDate,
        expectedEndDate: expectedEndDate
    });
    addOne('todo/add', data, 'todo/tablePage', '待办列表');
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
});