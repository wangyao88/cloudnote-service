package com.sxkl.project.cloudnote.attendance.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sxkl.project.cloudnote.attendance.entity.Absence;
import com.sxkl.project.cloudnote.attendance.mapper.AbsenceMapper;
import com.sxkl.project.cloudnote.base.entity.OperateResult;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.etl.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AbsenceService extends BaseService<Absence> {

    private static final String SATURDAYS = "saturdays";
    private static final String SUNDAYS = "sundays";

    @Autowired
    private AbsenceMapper absenceMapper;

    @Override
    protected BaseMapper<Absence> getMapper() {
        return absenceMapper;
    }

    @Transactional(transactionManager = "mainTransactionManager", rollbackFor = Exception.class)
    public OperateResult initWeekendOfCurrentYear(String userId) {
        try{
            int year = LocalDateTime.now().getYear();
            Map<String, List<LocalDate>> weekend = getWeekend(year);
            List<LocalDate> sundays = weekend.get(SUNDAYS);
            List<LocalDate> saturdays = weekend.get(SATURDAYS);
            List<Absence> absences = Lists.newArrayListWithCapacity(sundays.size()+saturdays.size());
            List<Absence> absencesOfSunday = sundays.stream()
                    .map(localDate -> initWeekendAbsence("周日", localDate, userId))
                    .collect(Collectors.toList());
            absences.addAll(absencesOfSunday);
            List<Absence> absencesOfSaturday = saturdays.stream()
                    .map(localDate -> initWeekendAbsence("周六", localDate, userId))
                    .collect(Collectors.toList());
            absences.addAll(absencesOfSaturday);
            absences.forEach(absence -> absenceMapper.add(absence));
        }catch (Exception e) {
            return OperateResult.buildFail(e);
        }
        return OperateResult.buildSuccess();
    }

    private Map<String, List<LocalDate>> getWeekend(int year) {
        Map<String, List<LocalDate>> resultMap = Maps.newHashMap();
        List<LocalDate> sundays = Lists.newArrayList();
        List<LocalDate> saturdays = Lists.newArrayList();
        Calendar calendar = new GregorianCalendar(year,0,1);
        int i = 1;
        while(calendar.get(Calendar.YEAR) < year+1){
            calendar.set(Calendar.WEEK_OF_YEAR, i++);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            if(calendar.get(Calendar.YEAR) == year){
                sundays.add(convertCalendarToLocalDate(calendar));
            }
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            if(calendar.get(Calendar.YEAR) == year){
                saturdays.add(convertCalendarToLocalDate(calendar));
            }
        }
        resultMap.put(SUNDAYS, sundays);
        resultMap.put(SATURDAYS, saturdays);
        return resultMap;
    }

    private LocalDate convertCalendarToLocalDate(Calendar calendar) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
        return localDateTime.toLocalDate();
    }

    private Absence initWeekendAbsence(String tips, LocalDate localDate, String userId) {
        Absence absence = new Absence();
        absence.setId(UUIDUtil.getUUID());
        absence.setUserId(userId);
        ZoneId zone = ZoneId.systemDefault();
        absence.setCreateDate(LocalDateTime.now(zone));
        absence.setAbsenceStart(localDate);
        absence.setAbsenceEnd(localDate);
        absence.setName("周末");
        absence.setTips(tips);
        return absence;
    }
}
