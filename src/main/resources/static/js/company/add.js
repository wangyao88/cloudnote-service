function addCompany() {
    var name = $('#name').val();
    var inDateStr = $('#inDate').val();
    var outDateStr = $('#outDate').val();
    var inDate = null;
    var outDate = null;
    if(inDateStr) {
        inDate = new Date(inDateStr);
    }else {
        swal('系统提示', '入职时间为必填项！', 'error');
        return;
    }
    if(outDateStr) {
        outDate = new Date(outDateStr);
    }
    if(inDateStr && outDateStr) {
        if(inDate > outDate) {
            swal('系统提示', '入职时间大于离职时间！', 'error');
            return;
        }
    }
    $.ajax({
        url : 'company/checkName',
        type : 'post',
        data: {name: name},
        dataType: 'json',
        success : function(result) {
            if(result.status) {
                var data = JSON.stringify({
                    name: name,
                    flag: $('#flag').val(),
                    address: $('#address').val(),
                    inDate: inDate,
                    outDate: outDate
                });
                addOne('company/add', data, 'company/tablePage', '公司');
            }else {
                swal('系统错误', '公司名已存在！', 'error');
            }
        },
        error : function() {
            swal('系统错误', '新增公司失败，请稍候重试！', 'error');
        }
    });
}

function initDatepicker(dateId) {
    $(dateId).datetimepicker({
        language: "zh-CN",
        autoclose: true,//选中之后自动隐藏日期选择框
        clearBtn: true,//清除按钮
        todayBtn: true,//今日按钮
        minView: "month",
        format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
    });
}

$(document).ready(function () {
    initDatepicker('#inDate');
    initDatepicker('#outDate');
});