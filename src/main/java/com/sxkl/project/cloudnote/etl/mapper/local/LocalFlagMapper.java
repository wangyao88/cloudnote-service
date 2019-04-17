package com.sxkl.project.cloudnote.etl.mapper.local;


import com.sxkl.project.cloudnote.etl.entity.Flag;
import com.sxkl.project.cloudnote.etl.mapper.BaseLoadMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocalFlagMapper extends BaseLoadMapper<Flag> {

    @Override
    @Insert("INSERT INTO cn_flag(id, name, fId, uId) VALUES (#{id}, #{name}, #{fId}, #{uId})")
    void save(Flag flag);

    @Override
    @Delete("DELETE FROM cn_flag")
    void delete();
}
