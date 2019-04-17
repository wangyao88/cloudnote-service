package com.sxkl.project.cloudnote.etl.mapper.remote;


import com.sxkl.project.cloudnote.etl.entity.User;
import com.sxkl.project.cloudnote.etl.mapper.BaseExtractMapper;
import com.sxkl.project.cloudnote.etl.mapper.MyBatisSQL;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface RemoteUserMapper extends BaseExtractMapper {

    @Override
    @SelectProvider(type = UserMapperProvider.class, method = "findAll")
    @Results(id = "UserResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "mailpass", property = "mailpass", jdbcType = JdbcType.VARCHAR)
    })
    List<User> findAll();


    class UserMapperProvider {

        public String findAll() {
            return MyBatisSQL.builder()
                             .SELECT("id, name, password, email, mailpass ")
                             .FROM("cn_user")
                             .build();
        }
    }
}
