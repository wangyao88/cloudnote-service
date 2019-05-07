function getLeaveById() {
    $.ajax({
        url : 'leave/findOne',
        type : 'get',
        data: {id: id},
        dataType: 'json',
        success : function(leave) {
            $('#name').val(leave.name);
            $('#tips').val(leave.tips);
            $('#address').val(leave.address);
            $("#leaveStart").datetimepicker("setDate", convertDate(leave.leaveStart));
            $("#leaveEnd").datetimepicker("setDate", convertDate(leave.leaveEnd));
        },
        error : function() {
            swal('系统错误', '获取请假信息失败，请稍候重试！', 'error');
        }
    });
}

function updateLeave() {
    var name = $('#name').val();
    var tips = $('#tips').val();
    var leaveStartStr = $('#leaveStart').val();
    var leaveEndStr = $('#leaveEnd').val();
    var leaveStart = null;
    var leaveEnd = null;
    if(leaveStartStr) {
        leaveStart = newDate(leaveStartStr);
    }else {
        swal('系统提示', '开始时间为必填项！', 'error');
        return;
    }
    if(leaveEndStr) {
        leaveEnd = newDate(leaveEndStr);
    }
    if(leaveStartStr && leaveEndStr) {
        if(leaveStart > leaveEnd) {
            swal('系统提示', '开始时间大于结束时间！', 'error');
            return;
        }
    }
    var data = JSON.stringify({
        id: id,
        name: name,
        tips: tips,
        leaveStart: leaveStart,
        leaveEnd: leaveEnd
    });
    updateOne('leave/update', data, 'leave/tablePage', '请假');
}

function initDatepicker(dateId) {
    $(dateId).datetimepicker({
        language: "zh-CN",
        autoclose: true,
        clearBtn: true,
        todayBtn: true,
        dateFormat: 'yyyy-mm-dd',
        timeFormat:'HH:mm:ss',
        minView: 0,
        minuteStep: 1
    });
}

$(document).ready(function () {
    initDatepicker('#leaveStart');
    initDatepicker('#leaveEnd');
    getLeaveById();
});