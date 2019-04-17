package com.sxkl.project.cloudnote.etl.mapper.remote;


import com.sxkl.project.cloudnote.etl.entity.Flag;
import com.sxkl.project.cloudnote.etl.mapper.BaseExtractMapper;
import com.sxkl.project.cloudnote.etl.mapper.MyBatisSQL;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface RemoteFlagMapper extends BaseExtractMapper {

    @Override
    @SelectProvider(type = FlagMapperProvider.class, method = "findAll")
    @Results(id = "FlagResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "fId", property = "fId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uId", property = "uId", jdbcType = JdbcType.VARCHAR)
    })
    List<Flag> findAll();


    class FlagMapperProvider {

        public String findAll() {
            return MyBatisSQL.builder()
                             .SELECT("id, name, fId, uId ")
                             .FROM("cn_flag")
                             .build();
        }
    }
}
