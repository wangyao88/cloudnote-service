function addAttendance() {
    var attendance_type = $('#attendance_type').val();
    if(!attendance_type) {
        swal('系统提示', '打卡类型为必填项！', 'error');
        return;
    }
    var attendance_dateStr = $('#attendance_date').val();
    var attendance_date = null;
    if(attendance_dateStr) {
        attendance_date = newDate(attendance_dateStr);
    }else {
        swal('系统提示', '打卡时间为必填项！', 'error');
        return;
    }
    var data = JSON.stringify({
        attendanceType: attendance_type,
        attendanceDate: attendance_date
    });
    addOne('attendance/add', data, 'attendance/tablePage', '打卡');
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
    initDatepicker('#attendance_date');
});