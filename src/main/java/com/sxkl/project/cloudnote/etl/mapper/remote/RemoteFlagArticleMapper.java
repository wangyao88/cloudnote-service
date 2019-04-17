package com.sxkl.project.cloudnote.etl.mapper.remote;


import com.sxkl.project.cloudnote.etl.entity.FlagArticle;
import com.sxkl.project.cloudnote.etl.mapper.BaseExtractMapper;
import com.sxkl.project.cloudnote.etl.mapper.MyBatisSQL;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface RemoteFlagArticleMapper extends BaseExtractMapper {

    @Override
    @SelectProvider(type = FlagArticleMapperProvider.class, method = "findAll")
    @Results(id = "FlagArticleResult", value = {
            @Result(column = "flag_id", property = "flagId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "article_id", property = "articleId", jdbcType = JdbcType.VARCHAR)
    })
    List<FlagArticle> findAll();


    class FlagArticleMapperProvider {

        public String findAll() {
            return MyBatisSQL.builder()
                             .SELECT("flag_id, article_id ")
                             .FROM("cn_flag_artile")
                             .build();
        }
    }
}
