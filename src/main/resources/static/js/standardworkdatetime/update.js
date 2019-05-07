function getStandardWorkDateTimeById() {
    $.ajax({
        url : 'standardWorkDateTime/findOne',
        type : 'get',
        data: {id: id},
        dataType: 'json',
        success : function(standardWorkDateTime) {
            $('#active').val(standardWorkDateTime.active);
            $("#amStart").val(standardWorkDateTime.amStart);
            $("#amEnd").val(standardWorkDateTime.amEnd);
            $("#pmStart").val(standardWorkDateTime.pmStart);
            $("#pmEnd").val(standardWorkDateTime.pmEnd);
        },
        error : function() {
            swal('系统错误', '获取工作作息时间失败，请稍候重试！', 'error');
        }
    });
}

function updateStandardWorkDateTime() {
    var active = $('#active').val();
    var amStart = $('#amStart').val();
    var amEnd = $('#amEnd').val();
    var pmStart = $('#pmStart').val();
    var pmEnd = $('#pmEnd').val();
    if(!amStart) {
        swal('系统提示', '上午上班时间为必填项！', 'error');
        return;
    }
    if(!amEnd) {
        swal('系统提示', '上午下班时间为必填项！', 'error');
        return;
    }
    if(!pmStart) {
        swal('系统提示', '下午上班时间为必填项！', 'error');
        return;
    }
    if(!pmEnd) {
        swal('系统提示', '下午下班时间为必填项！', 'error');
        return;
    }
    var data = JSON.stringify({
        id: id,
        active: active,
        amStart: amStart,
        amEnd: amEnd,
        pmStart: pmStart,
        pmEnd: pmEnd
    });
    updateOne('standardWorkDateTime/update', data, 'standardWorkDateTime/tablePage', '工作作息时间');
}

$(document).ready(function () {
    $('.clockpicker').clockpicker({
        donetext: 'Done'
    });
    getStandardWorkDateTimeById();
});