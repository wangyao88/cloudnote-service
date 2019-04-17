package com.sxkl.project.cloudnote.etl.service.load;

import com.sxkl.project.cloudnote.etl.entity.BaseEntity;
import com.sxkl.project.cloudnote.etl.mapper.BaseLoadMapper;
import com.sxkl.project.cloudnote.etl.mapper.MapperFactory;
import com.sxkl.project.cloudnote.etl.service.EtlInfo;
import com.sxkl.project.cloudnote.etl.service.exception.LoadException;
import com.sxkl.project.cloudnote.etl.service.exception.MapperNullException;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class Load {

    private static final String LOAD_MAPPER_PREFIX = "local";
    private static final String MAPPER_SUFFIX = "Mapper";
    private static final int BATCH_SIZE = 1000;

    protected EtlInfo etlInfo;

    public Load(EtlInfo etlInfo) {
        this.etlInfo = etlInfo;
    }

    public EtlInfo load(List<? extends BaseEntity> entities) throws MapperNullException, LoadException {
        etlInfo.setLoadStart(System.currentTimeMillis());
        Optional<BaseLoadMapper> loadMapperOptional = MapperFactory.getLoadMapper(getMapperName());
        if(!loadMapperOptional.isPresent()) {
            throw new MapperNullException();
        }
        BaseLoadMapper loadMapper = loadMapperOptional.get();
        doLoad(entities, loadMapper);
        etlInfo.setLoadEnd(System.currentTimeMillis());
        return etlInfo;
    }

    private void doLoad(List<? extends BaseEntity> entities, BaseLoadMapper loadMapper) throws LoadException {
        if(etlInfo.isDelete()) {
            loadMapper.delete();
        }
        SqlSession session = MapperFactory.getPublicLocalSqlSessionTemplate().getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        int total = entities.size();
        try {
            int i = 0, count = 0;
            for (BaseEntity entity : entities) {
                loadMapper.save(entity);
                i++;
                if (i % BATCH_SIZE == 0) {
                    session.commit();
                    session.clearCache();
                    count += BATCH_SIZE;
                    continue;
                }
                if(i == total) {
                    session.commit();
                    session.clearCache();
                    count += total % BATCH_SIZE;
                }
            }
            etlInfo.setLoadTotal(count);
        }catch (Exception e) {
            session.rollback();
            throw new LoadException(e);
        } finally{
            session.close();
        }
    }

    protected String getMapperName() {
        return StringUtils.appendJoinEmpty(LOAD_MAPPER_PREFIX, etlInfo.getEntityName(), MAPPER_SUFFIX);
    }
}
