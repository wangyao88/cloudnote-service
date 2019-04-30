package com.sxkl.project.cloudnote.project.mapper;

import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.project.entity.ProjectNote;
import com.sxkl.project.cloudnote.utils.MyBatisSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface ProjectNoteMapper extends BaseMapper<ProjectNote> {

    @Override
    @Insert("insert into cn_project_note(id, title, content, projectId, createDate, userId) values " +
            "(#{id}, #{title}, #{content}, #{projectId}, #{createDate}, #{userId})")
    void add(ProjectNote projectNote);

    @Override
    @Delete("delete from cn_project_note where id=#{id}")
    void removeOne(@Param("id") String id);

    @Override
    @UpdateProvider(type = ProjectNoteMapperProvider.class, method = "updateByCondition")
    void update(ProjectNote projectNote);

    @Override
    @Results(id = "projectNoteResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "projectName", property = "projectName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "projectId", property = "projectId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "createDate", property = "createDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "userId", property = "userId", jdbcType = JdbcType.VARCHAR)
    })
    @Select("select a.id, a.title, a.content, b.name as projectName, a.projectId, a.userId from cn_project_note a left join cn_project b on a.projectId=b.id where a.id=#{id}")
    ProjectNote findOne(@Param("id") String id);

    @Override
    @Select("select a.id, a.title, a.content, b.name as projectName, a.projectId, a.userId from cn_project_note a left join cn_project b on a.projectId=b.id")
    @ResultMap("projectNoteResult")
    List<ProjectNote> findAll();

    @Override
    @SelectProvider(type = ProjectNoteMapperProvider.class, method = "findByCondition")
    @ResultMap("projectNoteResult")
    List<ProjectNote> findByCondition(ProjectNote projectNote);

    class ProjectNoteMapperProvider {

        public String findByCondition(ProjectNote projectNote) {
            return MyBatisSQL.builder()
                    .SELECT("a.id, a.createDate, a.title, a.content, b.name as projectName, a.projectId, a.userId")
                    .FROM("cn_project_note a")
                    .LEFT_OUTER_JOIN("cn_project b on a.projectId=b.id")
                    .whereIfNotNull(projectNote.getTitle(), "a.title like #{title}")
                    .whereIfNotNull(projectNote.getContent(), "a.content like #{content}")
                    .whereIfNotNull(projectNote.getProjectId(), "a.projectId=#{projectId}")
                    .whereIfNotNull(projectNote.getStartDate(), "a.createDate>=#{startDate}")
                    .whereIfNotNull(projectNote.getEndDate(), "a.createDate<=#{endDate}")
                    .whereIfNotNull(projectNote.getId(), "a.id=#{id}")
                    .build();
        }

        public String updateByCondition(final ProjectNote projectNote) {
            return MyBatisSQL.builder()
                    .UPDATE("cn_project_note")
                    .setIfNotNull(projectNote.getTitle(), "title=#{title}")
                    .setIfNotNull(projectNote.getContent(), "content=#{content}")
                    .whereIfNotNull(projectNote.getId(), "id=#{id}")
                    .build();
        }
    }
}
