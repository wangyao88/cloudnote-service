package com.sxkl.project.cloudnote.etl.service;

import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import lombok.Data;


@Data
public class EtlInfo {

    private String etlGroupNo;
    private String tableName;
    private String entityName;
    private boolean success = true;
    private long extractStart;
    private long extractEnd;
    private long transformStart;
    private long transformEnd;
    private long loadStart;
    private long loadEnd;
    private int extractTotal;
    private int loadTotal;
    private Throwable errorInfo;
    private boolean delete = true;

    private static final String TABLE_PREFIX = "cn_";

    public static EtlInfo init(EtlGroup etlGroup, String name) {
        EtlInfo etlInfo = new EtlInfo();
        etlInfo.setEtlGroupNo(etlGroup.getEtlGroupNo());
        etlInfo.setEntityName(name);
        etlInfo.setTableName(getTableName(name));
        return etlInfo;
    }

    private static String getTableName(String name) {
        return StringUtils.appendJoinEmpty(TABLE_PREFIX, name.toLowerCase());
    }
}
