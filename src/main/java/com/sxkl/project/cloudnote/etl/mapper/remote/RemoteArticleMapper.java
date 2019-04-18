package com.sxkl.project.cloudnote.etl.mapper.remote;


import com.sxkl.project.cloudnote.etl.entity.Article;
import com.sxkl.project.cloudnote.etl.mapper.BaseCursorExtractMapper;
import com.sxkl.project.cloudnote.etl.mapper.MyBatisSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface RemoteArticleMapper extends BaseCursorExtractMapper {

    @Override
    @SelectProvider(type = ArticleMapperProvider.class, method = "findAll")
    @Results(id = "ArticleResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "content", property = "content", jdbcType = JdbcType.LONGVARCHAR),
            @Result(column = "createTime", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "hitNum", property = "hitNum", jdbcType = JdbcType.INTEGER),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "nId", property = "nId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uId", property = "uId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "is_shared", property = "isShared", jdbcType = JdbcType.INTEGER)
    })
    List<Article> findAll();

    @Override
    @SelectProvider(type = ArticleMapperProvider.class, method = "findOne")
    @ResultMap("ArticleResult")
    Article findOne(@Param("id") String id);


    class ArticleMapperProvider {

        public String findAll() {
            return MyBatisSQL.builder()
                             .SELECT("id ")
                             .FROM("cn_article")
                             .build();
        }

        public String findOne(final @Param("id")String id) {
            return MyBatisSQL.builder()
                    .SELECT("id, content, createTime, hitNum, title, nId, uId, is_shared ")
                    .FROM("cn_article ")
                    .WHERE("id=#{id}")
                    .build();
        }
    }
}
