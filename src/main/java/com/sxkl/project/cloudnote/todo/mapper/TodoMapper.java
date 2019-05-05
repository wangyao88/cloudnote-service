package com.sxkl.project.cloudnote.todo.mapper;

import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.todo.entity.Todo;
import com.sxkl.project.cloudnote.utils.MyBatisSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface TodoMapper extends BaseMapper<Todo> {

    @Override
    @Insert("insert into cn_todo(id, title, status, projectId, expectedStartDate, expectedEndDate, createDate, userId) values " +
            "(#{id}, #{title}, #{status}, #{projectId}, #{expectedStartDate}, #{expectedEndDate}, #{createDate}, #{userId})")
    void add(Todo todo);

    @Override
    @Delete("delete from cn_todo where id=#{id}")
    void removeOne(@Param("id") String id);

    @Override
    @UpdateProvider(type = TodoMapperProvider.class, method = "updateByCondition")
    void update(Todo todo);

    @Override
    @Results(id = "todoResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.VARCHAR),
            @Result(column = "projectName", property = "projectName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "projectId", property = "projectId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "expectedStartDate", property = "expectedStartDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "expectedEndDate", property = "expectedEndDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "createDate", property = "createDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "userId", property = "userId", jdbcType = JdbcType.VARCHAR)
    })
    @Select("select a.id, a.title, a.status, b.name as projectName, a.projectId, a.expectedStartDate, a.expectedEndDate, a.userId from cn_todo a left join cn_project b on a.projectId=b.id where a.id=#{id}")
    Todo findOne(@Param("id") String id);

    @Override
    @Select("select a.id, a.title, a.status, b.name as projectName, a.projectId, a.expectedStartDate, a.expectedEndDate, a.userId from cn_todo a left join cn_project b on a.projectId=b.id")
    @ResultMap("todoResult")
    List<Todo> findAll();

    @Override
    @SelectProvider(type = TodoMapperProvider.class, method = "findByCondition")
    @ResultMap("todoResult")
    List<Todo> findByCondition(Todo todo);

    class TodoMapperProvider {

        public String findByCondition(Todo todo) {
            return MyBatisSQL.builder()
                    .SELECT("a.id, a.createDate, a.title, a.status, b.name as projectName, a.projectId, a.expectedStartDate, a.expectedEndDate, a.userId")
                    .FROM("cn_todo a")
                    .LEFT_OUTER_JOIN("cn_project b on a.projectId=b.id")
                    .whereIfNotNull(todo.getTitle(), "a.title like #{title}")
                    .whereIfNotNull(todo.getStatus(), "a.status=#{status}")
                    .whereIfNotNull(todo.getProjectId(), "a.projectId=#{projectId}")
                    .whereIfNotNull(todo.getStartDate(), "a.expectedEndDate>=#{startDate}")
                    .whereIfNotNull(todo.getEndDate(), "a.expectedEndDate<=#{endDate}")
                    .whereIfNotNull(todo.getUserId(), "a.userId=#{userId}")
                    .whereIfNotNull(todo.getId(), "a.id=#{id}")
                    .build();
        }

        public String updateByCondition(final Todo todo) {
            return MyBatisSQL.builder()
                    .UPDATE("cn_todo")
                    .setIfNotNull(todo.getTitle(), "title=#{title}")
                    .setIfNotNull(todo.getStatus(), "status=#{status}")
                    .setIfNotNull(todo.getExpectedStartDate(), "expectedStartDate=#{expectedStartDate}")
                    .setIfNotNull(todo.getExpectedEndDate(), "expectedEndDate=#{expectedEndDate}")
                    .whereIfNotNull(todo.getId(), "id=#{id}")
                    .build();
        }
    }
}
