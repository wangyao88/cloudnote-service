package com.sxkl.project.cloudnote.base.entity;

import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class BaseEntity implements Serializable {

    protected String id;
    protected LocalDateTime createDate;
    protected int index;
    protected String userId;
    protected LocalDateTime startDate;
    protected LocalDateTime endDate;
    protected String dateRange;

    public LocalDateTime getStartDate() {
        if(StringUtils.isBlank(getDateRange())) {
            return this.startDate;
        }
        return parse(0, " 00:00:00");
    }

    public LocalDateTime getEndDate() {
        if(StringUtils.isBlank(getDateRange())) {
            return this.endDate;
        }
        return parse(1, " 23:59:59");
    }

    private LocalDateTime parse(int index, String time) {
        String[] ranges = getDateRange().split("至");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startDateStr = StringUtils.appendJoinEmpty(ranges[index].trim(), time);
        return LocalDateTime.parse(startDateStr, dtf);
//        LocalDateTime parse = LocalDateTime.parse(startDateStr, dtf);
//        ZoneId zone = ZoneId.systemDefault();
//        Instant instant = parse.atZone(zone).toInstant();
//        return Date.from(instant);
    }
}
