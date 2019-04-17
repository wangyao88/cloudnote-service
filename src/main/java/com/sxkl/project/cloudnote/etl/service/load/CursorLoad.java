package com.sxkl.project.cloudnote.etl.service.load;

import com.sxkl.project.cloudnote.etl.entity.BaseEntity;
import com.sxkl.project.cloudnote.etl.mapper.BaseLoadMapper;
import com.sxkl.project.cloudnote.etl.mapper.MapperFactory;
import com.sxkl.project.cloudnote.etl.service.EtlInfo;
import com.sxkl.project.cloudnote.etl.service.exception.LoadException;
import com.sxkl.project.cloudnote.etl.service.exception.MapperNullException;

import java.util.Optional;

public class CursorLoad extends Load {

    public CursorLoad(EtlInfo etlInfo) {
        super(etlInfo);
    }

    public EtlInfo cursorLoad(BaseEntity entity) throws MapperNullException, LoadException {
        etlInfo.setLoadStart(System.currentTimeMillis());
        Optional<BaseLoadMapper> loadMapperOptional = MapperFactory.getLoadMapper(getMapperName());
        if(!loadMapperOptional.isPresent()) {
            throw new MapperNullException();
        }
        BaseLoadMapper loadMapper = loadMapperOptional.get();
        loadMapper.save(entity);
        etlInfo.setLoadTotal(etlInfo.getLoadTotal()+1);
        return etlInfo;
    }

    public void delete() throws MapperNullException {
        if(etlInfo.isDelete()) {
            Optional<BaseLoadMapper> loadMapperOptional = MapperFactory.getLoadMapper(getMapperName());
            if(!loadMapperOptional.isPresent()) {
                throw new MapperNullException();
            }
            BaseLoadMapper loadMapper = loadMapperOptional.get();
            loadMapper.delete();
        }
    }
}
