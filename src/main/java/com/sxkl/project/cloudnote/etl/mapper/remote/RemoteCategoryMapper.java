package com.sxkl.project.cloudnote.etl.mapper.remote;


import com.sxkl.project.cloudnote.etl.entity.Category;
import com.sxkl.project.cloudnote.etl.mapper.BaseExtractMapper;
import com.sxkl.project.cloudnote.etl.mapper.MyBatisSQL;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface RemoteCategoryMapper extends BaseExtractMapper {

    @Override
    @SelectProvider(type = CategoryMapperProvider.class, method = "findAll")
    @Results(id = "CategoryResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "account_book_id", property = "accountBookId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "parent_id", property = "parentId", jdbcType = JdbcType.VARCHAR)
    })
    List<Category> findAll();


    class CategoryMapperProvider {

        public String findAll() {
            return MyBatisSQL.builder()
                             .SELECT("id, name, type, account_book_id, parent_id ")
                             .FROM("cn_category")
                             .build();
        }
    }
}
