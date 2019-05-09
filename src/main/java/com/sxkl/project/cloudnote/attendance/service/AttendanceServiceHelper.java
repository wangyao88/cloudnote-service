package com.sxkl.project.cloudnote.attendance.service;

import com.google.common.collect.Maps;
import com.sxkl.project.cloudnote.attendance.entity.Attendance;
import com.sxkl.project.cloudnote.attendance.entity.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceServiceHelper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String ABSENCE = "absence";
    public static final String LEAVE = "leave";
    public static final String ATTENDANCE_LATE = "attendance_late";
    public static final String ATTENDANCE_LEAVE_EARLY = "attendance_leave_early";
    public static final String NEGLECT_WORK= "neglect_work";
    public static final String ATTENDANCE_SUCCESS= "attendance_success";
    public static final String OVERTIME_WORK= "overtime_work";
    public static final String COLOR = "color";
    public static final String TITLE = "title";
    private static Map<String, Map<String, String>> map;

    public static Map<String, Map<String, String>> init() {
        map = Maps.newHashMap();

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

        Map<String, String> attendanceNeglectWorkEvent = Maps.newHashMap();
        attendanceEarlyEvent.put(TITLE, "旷工");
        attendanceEarlyEvent.put(COLOR, "black");
        map.put(NEGLECT_WORK, attendanceNeglectWorkEvent);

        Map<String, String> attendanceSuccessEvent = Maps.newHashMap();
        attendanceSuccessEvent.put(TITLE, "正常出勤");
        attendanceSuccessEvent.put(COLOR, "green");
        map.put(ATTENDANCE_SUCCESS, attendanceSuccessEvent);

        Map<String, String> overtimeWorkEvent = Maps.newHashMap();
        overtimeWorkEvent.put(TITLE, "加班");
        overtimeWorkEvent.put(COLOR, "yellow");
        map.put(OVERTIME_WORK, overtimeWorkEvent);

        return map;
    }

    public static Event initEvent(String id, String type, LocalDateTime start, LocalDateTime end) {
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

    public static Map<LocalDate, List<Attendance>> groupAttendances(List<Attendance> attendances) {
        return attendances.stream()
                .collect(Collectors.groupingBy(attendance -> attendance.getAttendanceDate().toLocalDate(),
                        HashMap::new,
                        Collectors.toList()));
    }

    public static boolean isInAbsence(List<Event> absenceEvents, LocalDate localDate) {
        LocalDateTime mid = localDate.atTime(12, 0, 0);
        return absenceEvents.stream()
                .anyMatch(absenceEvent-> mid.isAfter(absenceEvent.getStartDateTime()) &&
                        mid.isBefore(absenceEvent.getEndDateTime()));
    }

    public static boolean isNotInAbsence(List<Event> absenceEvents, LocalDate localDate) {
        return !isInAbsence(absenceEvents, localDate);
    }

    public static boolean isInLeave(List<Event> leaveEvents, LocalDate localDate) {
        return leaveEvents.stream()
                .anyMatch(absenceEvent-> localDate.isEqual(absenceEvent.getStartDateTime().toLocalDate()) ||
                        localDate.isEqual(absenceEvent.getEndDateTime().toLocalDate()));
    }

    public static boolean isNotInLeave(List<Event> leaveEvents, LocalDate localDate) {
        return !isInLeave(leaveEvents, localDate);
    }

    public static Event addNormalAttendance(String userId, LocalDate localDate, List<Attendance> sortedAttendances, StandardWorkDateTimeService standardWorkDateTimeService) {
        Attendance attendanceAmStart = sortedAttendances.get(0);
        Attendance attendanceAmEnd = sortedAttendances.get(1);
        Attendance attendancePmStart = sortedAttendances.get(2);
        Attendance attendancePmEnd = sortedAttendances.get(3);
        if(attendanceAmStart.getAttendanceDate().isAfter(standardWorkDateTimeService.getAmStart(userId, localDate))) {
            return initEvent(attendanceAmStart.getId(), ATTENDANCE_LATE, attendanceAmStart.getAttendanceDate(), attendanceAmEnd.getAttendanceDate());
        }else if(attendanceAmEnd.getAttendanceDate().isBefore(standardWorkDateTimeService.getAmEnd(userId, localDate))) {
            return initEvent(attendanceAmEnd.getId(), ATTENDANCE_LEAVE_EARLY, attendanceAmStart.getAttendanceDate(), attendanceAmEnd.getAttendanceDate());
        }else if(attendancePmStart.getAttendanceDate().isBefore(standardWorkDateTimeService.getPmStart(userId, localDate))) {
            return initEvent(attendancePmStart.getId(), ATTENDANCE_LEAVE_EARLY, attendancePmStart.getAttendanceDate(), attendancePmEnd.getAttendanceDate());
        }else if(attendancePmEnd.getAttendanceDate().isBefore(standardWorkDateTimeService.getPmEnd(userId, localDate))) {
            return initEvent(attendancePmEnd.getId(), ATTENDANCE_LEAVE_EARLY, attendancePmStart.getAttendanceDate(), attendancePmEnd.getAttendanceDate());
        }else {
            return initEvent(attendanceAmStart.getId(), ATTENDANCE_SUCCESS, attendanceAmStart.getAttendanceDate(), attendancePmEnd.getAttendanceDate());
        }
    }
}
