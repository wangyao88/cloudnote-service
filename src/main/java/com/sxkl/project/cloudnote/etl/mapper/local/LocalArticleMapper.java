package com.sxkl.project.cloudnote.etl.mapper.local;


import com.sxkl.project.cloudnote.etl.entity.Article;
import com.sxkl.project.cloudnote.etl.mapper.BaseLoadMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocalArticleMapper extends BaseLoadMapper<Article> {

    @Override
    @Insert("INSERT INTO cn_article(id, content, createTime, hitNum, title, nId, uId, is_shared) VALUES " +
            "(#{id}, #{content}, #{createTime}, #{hitNum}, #{title}, #{nId}, #{uId}, #{isShared})")
    void save(Article article);

    @Override
    @Delete("DELETE FROM cn_article")
    void delete();
}
