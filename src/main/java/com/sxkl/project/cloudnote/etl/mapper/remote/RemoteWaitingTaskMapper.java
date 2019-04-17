package com.sxkl.project.cloudnote.etl.mapper.remote;


import com.sxkl.project.cloudnote.etl.entity.WaitingTask;
import com.sxkl.project.cloudnote.etl.mapper.BaseExtractMapper;
import com.sxkl.project.cloudnote.etl.mapper.MyBatisSQL;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface RemoteWaitingTaskMapper extends BaseExtractMapper {

    @Override
    @SelectProvider(type = WaitingTaskMapperProvider.class, method = "findAll")
    @Results(id = "WaitingTaskResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "createDate", property = "createDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "expireDate", property = "expireDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "taskType", property = "taskType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uId", property = "uId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "process", property = "process", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "beginDate", property = "beginDate", jdbcType = JdbcType.TIMESTAMP)
    })
    List<WaitingTask> findAll();


    class WaitingTaskMapperProvider {

        public String findAll() {
            return MyBatisSQL.builder()
                             .SELECT("id, name, createDate, expireDate, taskType, uId, process, content, beginDate ")
                             .FROM("cn_waitingtask")
                             .build();
        }
    }
}
