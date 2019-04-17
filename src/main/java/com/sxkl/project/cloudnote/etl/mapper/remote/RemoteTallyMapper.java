package com.sxkl.project.cloudnote.etl.mapper.remote;


import com.sxkl.project.cloudnote.etl.entity.Tally;
import com.sxkl.project.cloudnote.etl.mapper.BaseExtractMapper;
import com.sxkl.project.cloudnote.etl.mapper.MyBatisSQL;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface RemoteTallyMapper extends BaseExtractMapper {

    @Override
    @SelectProvider(type = TallyMapperProvider.class, method = "findAll")
    @Results(id = "TallyResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "money", property = "money", jdbcType = JdbcType.FLOAT),
            @Result(column = "mark", property = "mark", jdbcType = JdbcType.VARCHAR),
            @Result(column = "category_id", property = "categoryId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_date", property = "createDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "account_book_id", property = "accountBookId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.VARCHAR)
    })
    List<Tally> findAll();


    class TallyMapperProvider {

        public String findAll() {
            return MyBatisSQL.builder()
                             .SELECT("id, money, mark, category_id, create_date, account_book_id, type, user_id ")
                             .FROM("cn_tally")
                             .build();
        }
    }
}
