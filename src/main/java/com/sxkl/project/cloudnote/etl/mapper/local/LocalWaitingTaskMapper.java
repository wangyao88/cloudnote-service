package com.sxkl.project.cloudnote.etl.mapper.local;


import com.sxkl.project.cloudnote.etl.entity.WaitingTask;
import com.sxkl.project.cloudnote.etl.mapper.BaseLoadMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocalWaitingTaskMapper extends BaseLoadMapper<WaitingTask> {

    @Override
    @Insert("INSERT INTO cn_waitingtask(id, name, createDate, expireDate, taskType, uId, process, content, beginDate) VALUES " +
            "(#{id}, #{name}, #{createDate}, #{expireDate}, #{taskType}, #{uId}, #{process}, #{content}, #{beginDate})")
    void save(WaitingTask waitingTask);

    @Override
    @Delete("DELETE FROM cn_waitingtask")
    void delete();
}
