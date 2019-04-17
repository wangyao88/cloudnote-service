package com.sxkl.project.cloudnote.etl.service;

import com.google.common.collect.Lists;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class EtlGroup {

    private String etlGroupNo;
    private Date etlDate;
    private List<BaseEtl> etls = Lists.newArrayList();

    public static EtlGroup init() {
        EtlGroup etlGroup = new EtlGroup();
        etlGroup.setEtlGroupNo(initEtlGroupNo());
        etlGroup.setEtlDate(new Date());
        return etlGroup;
    }

    private static String initEtlGroupNo() {
        LocalDateTime now = LocalDateTime.now();
        return StringUtils.appendJoinEmpty(
                toString(now.getYear()),
                toString(now.getMonthValue()),
                toString(now.getDayOfMonth()),
                toString(now.getHour()),
                toString(now.getMinute()),
                toString(now.getSecond()),
                toString(now.getNano()));
    }

    private static String toString(int time) {
        return time+"";
    }

    public void batchAddEtl(List<BaseEtl> baseEtls) {
        this.etls.addAll(baseEtls);
    }
}
