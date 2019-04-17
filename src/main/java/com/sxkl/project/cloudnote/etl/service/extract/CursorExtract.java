package com.sxkl.project.cloudnote.etl.service.extract;

import com.sxkl.project.cloudnote.etl.entity.BaseEntity;
import com.sxkl.project.cloudnote.etl.mapper.BaseCursorExtractMapper;
import com.sxkl.project.cloudnote.etl.mapper.BaseExtractMapper;
import com.sxkl.project.cloudnote.etl.mapper.MapperFactory;
import com.sxkl.project.cloudnote.etl.service.EtlInfo;
import com.sxkl.project.cloudnote.etl.service.exception.LoadException;
import com.sxkl.project.cloudnote.etl.service.exception.MapperNullException;
import com.sxkl.project.cloudnote.etl.service.transform.CursorTransform;

import java.util.List;
import java.util.Optional;

public class CursorExtract extends Extract {

    private CursorTransform transform;

    public CursorExtract(CursorTransform transform, EtlInfo etlInfo) {
        super(etlInfo);
        this.transform = transform;
    }


    @Override
    public void cursorExtract() throws MapperNullException {
        Optional<BaseExtractMapper> extractMapperOptional = MapperFactory.getExtractMapper(getMapperName());
        if(!extractMapperOptional.isPresent()) {
            throw new MapperNullException();
        }
        initEtlStartTime();
        BaseCursorExtractMapper baseCursorExtractMapper = (BaseCursorExtractMapper)extractMapperOptional.get();
        List<? extends BaseEntity> entities = baseCursorExtractMapper.findAll();
        transform.delete();
        entities.forEach(entity -> {
            try {
                String id = ((BaseEntity) entity).getId();
                BaseEntity one = baseCursorExtractMapper.findOne(id);
                etlInfo.setExtractTotal(etlInfo.getExtractTotal()+1);
                transform.cursorTransform(one);
            } catch (LoadException | MapperNullException e) {
                etlInfo.setSuccess(false);
                etlInfo.setErrorInfo(e);
            }
        });
        initEtlEndTime();
    }

    private void initEtlStartTime() {
        long start = System.currentTimeMillis();
        etlInfo.setExtractStart(start);
        etlInfo.setTransformStart(start);
        etlInfo.setLoadStart(start);
    }

    private void initEtlEndTime() {
        long end = System.currentTimeMillis();
        etlInfo.setExtractEnd(end);
        etlInfo.setTransformEnd(end);
        etlInfo.setLoadEnd(end);
    }
}
