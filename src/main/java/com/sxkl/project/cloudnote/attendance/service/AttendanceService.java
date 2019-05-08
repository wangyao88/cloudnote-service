package com.sxkl.project.cloudnote.attendance.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sxkl.project.cloudnote.attendance.entity.*;
import com.sxkl.project.cloudnote.attendance.mapper.AbsenceMapper;
import com.sxkl.project.cloudnote.attendance.mapper.AttendanceMapper;
import com.sxkl.project.cloudnote.attendance.mapper.LeaveMapper;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.etl.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttendanceService extends BaseService<Attendance> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String ABSENCE = "absence";
    private static final String LEAVE = "leave";
    private static final String ATTENDANCE_LATE = "attendance_late";
    private static final String ATTENDANCE_LEAVE_EARLY = "attendance_leave_early";
    private static final String ATTENDANCE_SUCCESS= "attendance_success";
    private static final String OVERTIME_WORK= "overtime_work";
    private static final String COLOR = "color";
    private static final String TITLE = "title";
    private static final Map<String, Map<String, String>> map = Maps.newHashMap();

    @Autowired
    private AttendanceMapper attendanceMapper;
    @Autowired
    private AbsenceMapper absenceMapper;
    @Autowired
    private LeaveMapper leaveMapper;
    @Autowired
    private StandardWorkDateTimeService standardWorkDateTimeService;

    @PostConstruct
    private void init() {
        Map<String, String> absenceEvent = Maps.newHashMap();
        absenceEvent.put(TITLE, "节假日");
        absenceEvent.put(COLOR, "blue");
        map.put(ABSENCE, absenceEvent);

        Map<String, String> leaveEvent = Maps.newHashMap();
        leaveEvent.put(TITLE, "请假");
        leaveEvent.put(COLOR, "red");
        map.put(LEAVE, leaveEvent);

        Map<String, String> attendanceLateEvent = Maps.newHashMap();
        attendanceLateEvent.put(TITLE, "迟到");
        attendanceLateEvent.put(COLOR, "purple");
        map.put(ATTENDANCE_LATE, attendanceLateEvent);

        Map<String, String> attendanceEarlyEvent = Maps.newHashMap();
        attendanceEarlyEvent.put(TITLE, "早退");
        attendanceEarlyEvent.put(COLOR, "violet");
        map.put(ATTENDANCE_LEAVE_EARLY, attendanceEarlyEvent);

        Map<String, String> attendanceSuccessEvent = Maps.newHashMap();
        attendanceSuccessEvent.put(TITLE, "正常出勤");
        attendanceSuccessEvent.put(COLOR, "green");
        map.put(ATTENDANCE_SUCCESS, attendanceSuccessEvent);

        Map<String, String> overtimeWorkEvent = Maps.newHashMap();
        overtimeWorkEvent.put(TITLE, "加班");
        overtimeWorkEvent.put(COLOR, "yellow");
        map.put(OVERTIME_WORK, overtimeWorkEvent);
    }

    @Override
    protected BaseMapper<Attendance> getMapper() {
        return attendanceMapper;
    }

    public List<Event> findEvents(Event event, String userId) {
        List<Event> absenceEvents = getAbsenceEvents(event, userId);
        List<Event> leaveEvents = getLeaveEvents(event, userId);
        List<Event> attendanceEvents = getAttendanceEvents(event, userId, absenceEvents, leaveEvents);

        List<Event> events = Lists.newArrayList();
        events.addAll(absenceEvents);
        events.addAll(leaveEvents);
        events.addAll(attendanceEvents);

        return events;
    }

    private List<Event> getAbsenceEvents(Event event, String userId) {
        Absence condition = new Absence();
        condition.setUserId(userId);
        condition.setStartDate(event.getStartDateTime());
        condition.setEndDate(event.getEndDateTime());
        List<Absence> absences = absenceMapper.findByCondition(condition);
        return absences.stream().map(absence -> {
            LocalDateTime start = absence.getAbsenceStart().atTime(0, 0, 0);
            LocalDateTime end = absence.getAbsenceEnd().atTime(23, 59, 59);
            return initEvent(absence.getId(), ABSENCE, start, end);
        }).collect(Collectors.toList());
    }

    private List<Event> getLeaveEvents(Event event, String userId) {
        Leave condition = new Leave();
        condition.setUserId(userId);
        condition.setStartDate(event.getStartDateTime());
        condition.setEndDate(event.getEndDateTime());
        List<Leave> leaves = leaveMapper.findByCondition(condition);
        return leaves.stream().map(leave -> {
            LocalDateTime start = leave.getLeaveStart();
            LocalDateTime end = leave.getLeaveEnd();
            return initEvent(leave.getId(), LEAVE, start, end);
        }).collect(Collectors.toList());
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
        Map<LocalDate, List<Attendance>> groupedAttendances = groupAttendances(attendances);
        groupedAttendances.forEach((localDate, groupedAttendance) -> {
            LocalDateTime start = localDate.atTime(0, 0, 0);
            LocalDateTime end = localDate.atTime(23, 59, 59);
            LocalDateTime mid = localDate.atTime(12, 0, 0);
            boolean match = absenceEvents.stream()
                                         .anyMatch(absenceEvent-> mid.isAfter(absenceEvent.getStartDateTime()) &&
                                                                  mid.isBefore(absenceEvent.getEndDateTime()));
            if(!match) {
                List<Attendance> sortedAttendances = groupedAttendance.stream().sorted().collect(Collectors.toList());
                if(sortedAttendances.size() == 4) {
                    Attendance attendanceAmStart = sortedAttendances.get(0);
                    Attendance attendanceAmEnd = sortedAttendances.get(1);
                    Attendance attendancePmStart = sortedAttendances.get(2);
                    Attendance attendancePmEnd = sortedAttendances.get(3);
                    if(attendanceAmStart.getAttendanceDate().isAfter(standardWorkDateTimeService.getAmStart(userId, localDate))) {
                        Event lateEvent = initEvent(attendanceAmStart.getId(), ATTENDANCE_LATE, attendanceAmStart.getAttendanceDate(), attendanceAmEnd.getAttendanceDate());
                        attendanceEvents.add(lateEvent);
                    }else if(attendanceAmEnd.getAttendanceDate().isBefore(standardWorkDateTimeService.getAmEnd(userId, localDate))) {
                        Event earlyEvent = initEvent(attendanceAmEnd.getId(), ATTENDANCE_LEAVE_EARLY, attendanceAmStart.getAttendanceDate(), attendanceAmEnd.getAttendanceDate());
                        attendanceEvents.add(earlyEvent);
                    }else if(attendancePmStart.getAttendanceDate().isBefore(standardWorkDateTimeService.getPmStart(userId, localDate))) {
                        Event lateEvent = initEvent(attendancePmStart.getId(), ATTENDANCE_LEAVE_EARLY, attendancePmStart.getAttendanceDate(), attendancePmEnd.getAttendanceDate());
                        attendanceEvents.add(lateEvent);
                    }else if(attendancePmEnd.getAttendanceDate().isBefore(standardWorkDateTimeService.getPmEnd(userId, localDate))) {
                        Event earlyEvent = initEvent(attendancePmEnd.getId(), ATTENDANCE_LEAVE_EARLY, attendancePmStart.getAttendanceDate(), attendancePmEnd.getAttendanceDate());
                        attendanceEvents.add(earlyEvent);
                    }else {
                        Event successEvent = initEvent(attendanceAmStart.getId(), ATTENDANCE_SUCCESS, attendanceAmStart.getAttendanceDate(), attendancePmEnd.getAttendanceDate());
                        attendanceEvents.add(successEvent);
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

    private Map<LocalDate, List<Attendance>> groupAttendances(List<Attendance> attendances) {
        return attendances.stream()
                          .collect(Collectors.groupingBy(attendance -> attendance.getAttendanceDate().toLocalDate(),
                                    HashMap::new,
                                    Collectors.toList()));
    }

    private Event initEvent(String id, String type, LocalDateTime start, LocalDateTime end) {
        Map<String, String> subEvent = map.get(type);
        Event event = new Event();
        event.setId(id);
        event.setType(type);
        event.setTitle(subEvent.get(TITLE));
        event.setStartDateTime(start);
        event.setStart(start.format(FORMATTER));
        event.setEndDateTime(end);
        event.setEnd(end.format(FORMATTER));
        event.setColor(subEvent.get(COLOR));
        return event;
    }
}
