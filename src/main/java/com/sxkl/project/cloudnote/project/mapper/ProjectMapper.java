package com.sxkl.project.cloudnote.project.mapper;

import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.project.entity.Project;
import com.sxkl.project.cloudnote.utils.MyBatisSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface ProjectMapper extends BaseMapper<Project> {

    @Override
    @Insert("insert into cn_project(id, name, projectDescribe, createDate, userId) values " +
            "(#{id}, #{name}, #{projectDescribe}, #{createDate}, #{userId})")
    void add(Project project);

    @Override
    @Delete("delete from cn_project where id=#{id}")
    void removeOne(@Param("id") String id);

    @Override
    @UpdateProvider(type = ProjectMapperProvider.class, method = "updateByCondition")
    void update(Project project);

    @Override
    @Results(id = "projectResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "projectDescribe", property = "projectDescribe", jdbcType = JdbcType.VARCHAR),
            @Result(column = "createDate", property = "createDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "userId", property = "userId", jdbcType = JdbcType.VARCHAR)
    })
    @Select("select id, name, projectDescribe, createDate, userId from cn_project where id=#{id}")
    Project findOne(@Param("id") String id);

    @Override
    @Select("select id, name, projectDescribe, createDate, userId from cn_project")
    @ResultMap("projectResult")
    List<Project> findAll();

    @Override
    @SelectProvider(type = ProjectMapperProvider.class, method = "findByCondition")
    @ResultMap("projectResult")
    List<Project> findByCondition(Project project);

    @Select("select id, name from cn_project where name=#{name}")
    @ResultMap("projectResult")
    List<Project> findByName(Project project);

    class ProjectMapperProvider {

        public String findByCondition(Project project) {
            return MyBatisSQL.builder()
                    .SELECT("id, name, projectDescribe, createDate, userId")
                    .FROM("cn_project")
                    .whereIfNotNull(project.getName(), "name like #{name}")
                    .whereIfNotNull(project.getProjectDescribe(), "projectDescribe like #{projectDescribe}")
                    .whereIfNotNull(project.getId(), "id=#{id}")
                    .build();
        }

        public String updateByCondition(final Project project) {
            return MyBatisSQL.builder()
                    .UPDATE("cn_project")
                    .setIfNotNull(project.getName(), "name=#{name}")
                    .setIfNotNull(project.getProjectDescribe(), "projectDescribe=#{projectDescribe}")
                    .setIfNotNull(project.getUserId(), "userId=#{userId}")
                    .whereIfNotNull(project.getId(), "id=#{id}")
                    .build();
        }
    }
}
