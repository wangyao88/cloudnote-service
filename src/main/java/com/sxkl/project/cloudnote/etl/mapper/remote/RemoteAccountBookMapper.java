package com.sxkl.project.cloudnote.etl.mapper.remote;


import com.sxkl.project.cloudnote.etl.entity.AccountBook;
import com.sxkl.project.cloudnote.etl.mapper.BaseExtractMapper;
import com.sxkl.project.cloudnote.etl.mapper.MyBatisSQL;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface RemoteAccountBookMapper extends BaseExtractMapper {

    @Override
    @SelectProvider(type = AccountBookMapperProvider.class, method = "findAll")
    @Results(id = "AccountBookResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "mark", property = "mark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date", property = "createDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.VARCHAR)
    })
    List<AccountBook> findAll();


    class AccountBookMapperProvider {

        public String findAll() {
            return MyBatisSQL.builder()
                             .SELECT("id, name, mark, create_date, user_id ")
                             .FROM("cn_account_book")
                             .build();
        }
    }
}
