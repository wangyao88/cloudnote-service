package com.sxkl.project.cloudnote.etl.mapper.local;


import com.sxkl.project.cloudnote.etl.entity.FlagArticle;
import com.sxkl.project.cloudnote.etl.mapper.BaseLoadMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocalFlagArticleMapper extends BaseLoadMapper<FlagArticle> {

    @Override
    @Insert("INSERT INTO cn_flag_artile(flag_id, article_id) VALUES (#{flagId}, #{articleId})")
    void save(FlagArticle flagArticle);

    @Override
    @Delete("DELETE FROM cn_flag_artile")
    void delete();
}
