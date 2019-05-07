package com.sxkl.project.cloudnote.attendance.entity;

import com.sxkl.project.cloudnote.base.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Absence extends BaseEntity {

    private LocalDate absenceStart;
    private LocalDate absenceEnd;
    /**
     * 元旦 春节 清明节 劳动节 端午节 中秋节 国庆节 周末
     */
    private String name;
    private String tips;
}
