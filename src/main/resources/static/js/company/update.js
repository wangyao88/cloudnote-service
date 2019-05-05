function getCompanyById() {
    $.ajax({
        url : 'company/findOne',
        type : 'get',
        data: {id: id},
        dataType: 'json',
        success : function(company) {
            $('#name').val(company.name);
            $('#flag').val(company.flag);
            $('#address').val(company.address);
            console.log(company.inDate);
            $("#inDate").datetimepicker("setDate", new Date(convertDate(company.inDate)));
            if(company.outDate) {
                $("#outDate").datetimepicker("setDate", new Date(convertDate(company.outDate)));
            }
        },
        error : function() {
            swal('系统错误', '获取公司信息失败，请稍候重试！', 'error');
        }
    });
}

function updateCompany() {
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
    var data = JSON.stringify({
        id: id,
        name: name,
        flag: $('#flag').val(),
        address: $('#address').val(),
        inDate: inDate,
        outDate: outDate
    });
    updateOne('company/update', data, 'company/tablePage', '公司');
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
    getCompanyById();
    initDatepicker('#inDate');
    initDatepicker('#outDate');
});