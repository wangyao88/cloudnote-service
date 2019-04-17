package com.sxkl.project.cloudnote.etl.mapper.local;


import com.sxkl.project.cloudnote.etl.entity.User;
import com.sxkl.project.cloudnote.etl.mapper.BaseLoadMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocalUserMapper extends BaseLoadMapper<User> {

    @Override
    @Insert("INSERT INTO cn_user(id, name, password, email, mailpass) VALUES (#{id}, #{name}, #{password}, #{email}, #{mailpass})")
    void save(User user);

    @Override
    @Delete("DELETE FROM cn_user")
    void delete();
}
