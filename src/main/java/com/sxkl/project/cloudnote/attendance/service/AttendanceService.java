package com.sxkl.project.cloudnote.attendance.service;

import com.google.common.collect.Lists;
import com.sxkl.project.cloudnote.attendance.entity.Attendance;
import com.sxkl.project.cloudnote.attendance.entity.Event;
import com.sxkl.project.cloudnote.attendance.entity.StandardWorkDateTime;
import com.sxkl.project.cloudnote.attendance.mapper.AttendanceMapper;
import com.sxkl.project.cloudnote.base.entity.OperateResult;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.etl.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttendanceService extends BaseService<Attendance> {

    @Autowired
    private AttendanceMapper attendanceMapper;
    @Autowired
    private StandardWorkDateTimeService standardWorkDateTimeService;
    @Autowired
    private AbsenceService absenceService;
    @Autowired
    private LeaveService leaveService;

    @PostConstruct
    private void init() {
        AttendanceServiceHelper.init();
    }

    @Override
    protected BaseMapper<Attendance> getMapper() {
        return attendanceMapper;
    }

    public List<Event> findEvents(Event event, String userId) {
        List<Event> absenceEvents = absenceService.getAbsenceEvents(event, userId);
        List<Event> leaveEvents = leaveService.getLeaveEvents(event, userId);
        List<Event> attendanceEvents = getAttendanceEvents(event, userId, absenceEvents, leaveEvents);

        List<Event> events = Lists.newArrayList();
        events.addAll(absenceEvents);
        events.addAll(leaveEvents);
        events.addAll(attendanceEvents);

        return events;
    }

    private List<Event> getAttendanceEvents(Event event, String userId, List<Event> absenceEvents, List<Event> leaveEvents) {
        StandardWorkDateTime standardWorkDateTime = standardWorkDateTimeService.getStandardWorkDateTime(userId);
        if(ObjectUtils.isNull(standardWorkDateTime)) {
            return Lists.newArrayList();
        }
        List<Attendance> attendances = getAttendances(event, userId);
        if(attendances.isEmpty()) {
            return Lists.newArrayList();
        }

        List<Event> attendanceEvents = Lists.newArrayList();
        Map<LocalDate, List<Attendance>> groupedAttendances = AttendanceServiceHelper.groupAttendances(attendances);
        groupedAttendances.forEach((localDate, groupedAttendance) -> {
            if(AttendanceServiceHelper.isNotInAbsence(absenceEvents, localDate)) {
                if(AttendanceServiceHelper.isNotInLeave(leaveEvents, localDate)) {
                    List<Attendance> sortedAttendances = groupedAttendance.stream().sorted().collect(Collectors.toList());
                    if(sortedAttendances.size() == 4) {
                        Event event1 = AttendanceServiceHelper.addNormalAttendance(userId, localDate, sortedAttendances, standardWorkDateTimeService);
                        attendanceEvents.add(event1);
                    }
                }
            }
        });
        return attendanceEvents;
    }

    private List<Attendance> getAttendances(Event event, String userId) {
        Attendance condition = new Attendance();
        condition.setUserId(userId);
        condition.setStartDate(event.getStartDateTime());
        condition.setEndDate(event.getEndDateTime());
        return attendanceMapper.findByCondition(condition);
    }

    public OperateResult check(String userId) {
        StandardWorkDateTime standardWorkDateTime = standardWorkDateTimeService.getStandardWorkDateTime(userId);
        if(ObjectUtils.isNull(standardWorkDateTime)) {
            return OperateResult.builder().msg("无标准工作作息时间，无法打卡").status(Boolean.FALSE).build();
        }
        return OperateResult.buildSuccess();
    }

    public Event getStatisticDateRange() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        LocalDate firstday = LocalDate.of(today.getYear(),today.getMonth(),1);
        LocalDate lastDay =today.with(TemporalAdjusters.lastDayOfMonth());
        Event event = new Event();
        event.setStart(firstday.format(formatter));
        event.setEnd(lastDay.format(formatter));
        return event;
    }

    public Map<String, Long> statistic(Event event, String userId) {
        List<Event> events = findEvents(event, userId);
        return events.stream().collect(Collectors.groupingBy(Event::getTitle, HashMap::new, Collectors.counting()));
    }
}
