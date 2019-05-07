function addAbsence() {
    var name = $('#name').val();
    var tips = $('#tips').val();
    var absenceStartStr = $('#absenceStart').val();
    var absenceEndStr = $('#absenceEnd').val();
    var absenceStart = null;
    var absenceEnd = null;
    if(absenceStartStr) {
        absenceStart = newDate(absenceStartStr);
    }else {
        swal('系统提示', '开始日期为必填项！', 'error');
        return;
    }
    if(absenceEndStr) {
        absenceEnd = newDate(absenceEndStr);
    }
    if(absenceStartStr && absenceEndStr) {
        if(absenceStart > absenceEnd) {
            swal('系统提示', '开始日期大于结束日期！', 'error');
            return;
        }
    }
    var data = JSON.stringify({
        name: name,
        tips: tips,
        absenceStart: absenceStart,
        absenceEnd: absenceEnd
    });
    addOne('absence/add', data, 'absence/tablePage', '节假日');
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
    initDatepicker('#absenceStart');
    initDatepicker('#absenceEnd');
});