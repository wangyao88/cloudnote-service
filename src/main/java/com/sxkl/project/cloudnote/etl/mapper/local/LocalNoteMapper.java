package com.sxkl.project.cloudnote.etl.mapper.local;


import com.sxkl.project.cloudnote.etl.entity.Note;
import com.sxkl.project.cloudnote.etl.mapper.BaseLoadMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocalNoteMapper extends BaseLoadMapper<Note> {

    @Override
    @Insert("INSERT INTO cn_note(id, name, uId) VALUES (#{id}, #{name}, #{uId})")
    void save(Note note);

    @Override
    @Delete("DELETE FROM cn_note")
    void delete();
}
