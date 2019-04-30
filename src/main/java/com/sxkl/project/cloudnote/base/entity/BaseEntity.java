package com.sxkl.project.cloudnote.base.entity;

import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    protected String id;
    protected Date createDate;
    protected int index;
    protected String userId;
    protected Date startDate;
    protected Date endDate;
    protected String dateRange;

    public Date getStartDate() {
        if(StringUtils.isBlank(getDateRange())) {
            return null;
        }
        return parse(0, " 00:00:00");
    }

    public Date getEndDate() {
        if(StringUtils.isBlank(getDateRange())) {
            return null;
        }
        return parse(1, " 23:59:59");
    }

    private Date parse(int index, String time) {
        String[] ranges = getDateRange().split("至");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startDateStr = StringUtils.appendJoinEmpty(ranges[index].trim(), time);
        LocalDateTime parse = LocalDateTime.parse(startDateStr, dtf);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = parse.atZone(zone).toInstant();
        return Date.from(instant);
    }
}
