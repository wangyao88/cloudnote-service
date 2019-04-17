package com.sxkl.project.cloudnote.etl.mapper.local;


import com.sxkl.project.cloudnote.etl.entity.Message;
import com.sxkl.project.cloudnote.etl.mapper.BaseLoadMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocalMessageMapper extends BaseLoadMapper<Message> {

    @Override
    @Insert("INSERT INTO cn_message(id, fromId, fromName, toId, text, date) VALUES " +
            "(#{id}, #{fromId}, #{fromName}, #{toId}, #{text}, #{date})")
    void save(Message message);

    @Override
    @Delete("DELETE FROM cn_message")
    void delete();
}
