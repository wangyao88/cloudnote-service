package com.sxkl.project.cloudnote.etl.mapper.local;


import com.sxkl.project.cloudnote.etl.entity.Image;
import com.sxkl.project.cloudnote.etl.mapper.BaseLoadMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocalImageMapper extends BaseLoadMapper<Image> {

    @Override
    @Insert("INSERT INTO cn_image(id, alt, aId, content, name) VALUES " +
            "(#{id}, #{alt}, #{aId}, #{content}, #{name})")
    void save(Image image);

    @Override
    @Delete("DELETE FROM cn_image")
    void delete();
}
