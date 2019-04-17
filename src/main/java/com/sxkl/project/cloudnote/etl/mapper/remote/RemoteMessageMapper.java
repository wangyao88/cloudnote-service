package com.sxkl.project.cloudnote.etl.mapper.remote;


import com.sxkl.project.cloudnote.etl.entity.Message;
import com.sxkl.project.cloudnote.etl.mapper.BaseExtractMapper;
import com.sxkl.project.cloudnote.etl.mapper.MyBatisSQL;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface RemoteMessageMapper extends BaseExtractMapper {

    @Override
    @SelectProvider(type = MessageMapperProvider.class, method = "findAll")
    @Results(id = "MessageResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "fromId", property = "fromId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "fromName", property = "fromName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "toId", property = "toId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "text", property = "text", jdbcType = JdbcType.VARCHAR),
            @Result(column = "date", property = "date", jdbcType = JdbcType.TIMESTAMP)
    })
    List<Message> findAll();


    class MessageMapperProvider {

        public String findAll() {
            return MyBatisSQL.builder()
                             .SELECT("id, fromId, fromName, toId, text, date ")
                             .FROM("cn_message")
                             .build();
        }
    }
}
