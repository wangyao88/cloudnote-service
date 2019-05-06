package com.sxkl.project.cloudnote.attendance.entity;

import com.sxkl.project.cloudnote.base.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Absence extends BaseEntity {

    private LocalDateTime absenceStart;
    private LocalDateTime absenceEnd;
    /**
     * 元旦 春节 清明节 劳动节 端午节 中秋节 国庆节 周末
     */
    private String type;
    private String tips;
}
