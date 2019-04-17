package com.sxkl.project.cloudnote.etl.mapper;

import com.google.common.collect.Maps;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Service
public class MapperFactory {

    private static Map<String, BaseExtractMapper> extractMappers = Maps.newConcurrentMap();
    private static Map<String, BaseLoadMapper> loadMappers = Maps.newConcurrentMap();
    private static SqlSessionTemplate publicLocalSqlSessionTemplate;
    private static SqlSessionTemplate publicRemoteSqlSessionTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("localSqlSessionTemplate")
    private SqlSessionTemplate localSqlSessionTemplate;

    @Autowired
    @Qualifier("remoteSqlSessionTemplate")
    private SqlSessionTemplate remoteSqlSessionTemplate;

    @PostConstruct
    private void init() {
        Map<String, Object> mappers = applicationContext.getBeansWithAnnotation(Mapper.class);
        mappers.forEach((beanName, mapper) -> {
            if(StringUtils.isNotEmpty(beanName) && beanName.toLowerCase().contains("remote")) {
                extractMappers.put(beanName, (BaseExtractMapper)mapper);
            }
            if(StringUtils.isNotEmpty(beanName) && beanName.toLowerCase().contains("local")) {
                loadMappers.put(beanName, (BaseLoadMapper)mapper);
            }
        });
        publicLocalSqlSessionTemplate = localSqlSessionTemplate;
        publicRemoteSqlSessionTemplate = remoteSqlSessionTemplate;
    }

    public static Optional<BaseExtractMapper> getExtractMapper(String mapperName) {
       return Optional.ofNullable(extractMappers.get(mapperName));
    }

    public static Optional<BaseLoadMapper> getLoadMapper(String mapperName) {
        return Optional.ofNullable(loadMappers.get(mapperName));
    }

    public static SqlSessionTemplate getPublicLocalSqlSessionTemplate() {
        return publicLocalSqlSessionTemplate;
    }

    public static SqlSessionTemplate getPublicRemoteSqlSessionTemplate() {
        return publicRemoteSqlSessionTemplate;
    }
}
