function initAttendanceCalendar() {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        plugins: [ 'bootstrap', 'interaction', 'dayGrid', 'timeGrid', 'list' ],
        themeSystem: 'Bootstrap 4',
        header: {
            left: 'prevYear,prev,next,nextYear today attendance',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek'
        },
        customButtons: {
            attendance: {
                text: '打卡',
                click: function() {
                    var data = JSON.stringify({
                        attendanceDate: newDate(),
                    });
                    $.ajax({
                        url : 'attendance/add',
                        type : 'post',
                        data: data,
                        contentType: "application/json; charset=utf-8",
                        dataType: 'json',
                        success : function(result) {
                            if(result.status) {
                                swal('系统提示', '新增打卡成功！', 'success');
                            }else {
                                swal('系统提示', '新增打卡失败！', 'error');
                            }
                        },
                        error : function() {
                            swal('系统提示', '新增打卡失败，请稍候重试！', 'error');
                        }
                    });
                }
            }
        },
        monthNames: ["一月", "二月", "三月", "四月",//设置月份名称，中英文均可
            "五月", "六月", "七月", "八月", "九月",
            "十月", "十一月", "十二月"
        ],
        monthNamesShort: ["一月", "二月", "三月",//设置月份的简称
            "四月", "五月", "六月", "七月", "八月",
            "九月", "十月", "十一月", "十二月"
        ],
        dayNames: ["周日", "周一", "周二", "周三",//设置星期名称
            "周四", "周五", "周六"
        ],
        dayNamesShort: ["周日", "周一", "周二",//设置星期简称
            "周三", "周四", "周五", "周六"
        ],
        today: ["今天"],//today 按钮的显示名称
        firstDay: 0,//设置每星期的第一天是星期几，0 是星期日
        buttonText: {//设置按钮文本
            today: '今天',
            prev: '上一月',
            next: '下一月',
            month: '月',
            day: '日',
            list: '列表',
            week: '周'
        },
        currentTimezone: 'Asia/Beijing',
        theme: true,
        defaultDate: new Date(),
        navLinks: true,
        eventLimit: true,
        events: function(info, successCallback, failureCallback) {
            var data = JSON.stringify({
                startDateTime: new Date(info.start),
                endDateTime: new Date(info.end)
            });
            $.ajax({
                url : 'attendance/findCalendarEvents',
                type : 'post',
                data: data,
                contentType: "application/json; charset=utf-8",
                success : function(events) {
                    successCallback(events);
                },
                error: function (data) {
                    failureCallback(data);
                }
            });
        },
    });
    calendar.render();
}