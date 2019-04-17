package com.sxkl.project.cloudnote.etl.mapper.local;


import com.sxkl.project.cloudnote.etl.entity.Category;
import com.sxkl.project.cloudnote.etl.mapper.BaseLoadMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocalCategoryMapper extends BaseLoadMapper<Category> {

    @Override
    @Insert("INSERT INTO cn_category(id, name, type, account_book_id, parent_id) VALUES (#{id}, #{name}, #{type}, #{accountBookId}, #{parentId})")
    void save(Category category);

    @Override
    @Delete("DELETE FROM cn_category")
    void delete();
}
