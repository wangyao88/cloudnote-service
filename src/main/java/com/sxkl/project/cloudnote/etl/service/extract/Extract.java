package com.sxkl.project.cloudnote.etl.service.extract;


import com.sxkl.project.cloudnote.etl.entity.BaseEntity;
import com.sxkl.project.cloudnote.etl.mapper.BaseExtractMapper;
import com.sxkl.project.cloudnote.etl.mapper.MapperFactory;
import com.sxkl.project.cloudnote.etl.service.EtlInfo;
import com.sxkl.project.cloudnote.etl.service.exception.MapperNullException;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;

import java.util.List;
import java.util.Optional;

public class Extract {

    private static final String EXTRACT_MAPPER_PREFIX = "remote";
    private static final String MAPPER_SUFFIX = "Mapper";

    protected EtlInfo etlInfo;

    public Extract(EtlInfo etlInfo) {
        this.etlInfo = etlInfo;
    }

    public List<? extends BaseEntity> extract() throws MapperNullException {
        etlInfo.setExtractStart(System.currentTimeMillis());
        Optional<BaseExtractMapper> extractMapperOptional = MapperFactory.getExtractMapper(getMapperName());
        if(!extractMapperOptional.isPresent()) {
            throw new MapperNullException();
        }
        BaseExtractMapper extractMapper = extractMapperOptional.get();
        List<? extends BaseEntity> entities = extractMapper.findAll();
        etlInfo.setExtractTotal(entities.size());
        etlInfo.setExtractEnd(System.currentTimeMillis());
        return entities;
    }

    public void cursorExtract() throws MapperNullException {

    }

    protected String getMapperName() {
        return StringUtils.appendJoinEmpty(EXTRACT_MAPPER_PREFIX, etlInfo.getEntityName(), MAPPER_SUFFIX);
    }

}
