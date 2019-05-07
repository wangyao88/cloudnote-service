function getAbsenceById() {
    $.ajax({
        url : 'absence/findOne',
        type : 'get',
        data: {id: id},
        dataType: 'json',
        success : function(absence) {
            $('#name').val(absence.name);
            $('#tips').val(absence.tips);
            $('#address').val(absence.address);
            $("#absenceStart").datetimepicker("setDate", convertDate(absence.absenceStart));
            $("#absenceEnd").datetimepicker("setDate", convertDate(absence.absenceEnd));
        },
        error : function() {
            swal('系统错误', '获取节假日信息失败，请稍候重试！', 'error');
        }
    });
}

function updateAbsence() {
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
        id: id,
        name: name,
        tips: tips,
        absenceStart: absenceStart,
        absenceEnd: absenceEnd
    });
    updateOne('absence/update', data, 'absence/tablePage', '节假日');
}

function initDatepicker(dateId) {
    $(dateId).datetimepicker({
        language: "zh-CN",
        autoclose: true,
        clearBtn: true,
        todayBtn: true,
        minView: "",
        format: "yyyy-mm-dd"
    });
}

$(document).ready(function () {
    initDatepicker('#absenceStart');
    initDatepicker('#absenceEnd');
    getAbsenceById();
});