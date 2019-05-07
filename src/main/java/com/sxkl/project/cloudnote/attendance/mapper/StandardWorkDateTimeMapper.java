package com.sxkl.project.cloudnote.attendance.mapper;

import com.sxkl.project.cloudnote.attendance.entity.StandardWorkDateTime;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.utils.MyBatisSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface StandardWorkDateTimeMapper extends BaseMapper<StandardWorkDateTime> {

    @Override
    @Insert("insert into cn_standard_work_datetime(id, amStart, amEnd, pmStart, pmEnd, active, createDate, userId) values " +
            "(#{id}, #{amStart}, #{amEnd}, #{pmStart}, #{pmEnd}, #{active}, #{createDate}, #{userId})")
    void add(StandardWorkDateTime standardWorkDateTime);

    @Override
    @Delete("delete from cn_standard_work_datetime where id=#{id}")
    void removeOne(@Param("id") String id);

    @Override
    @UpdateProvider(type = StandardWorkDateTimeMapperProvider.class, method = "updateByCondition")
    void update(StandardWorkDateTime standardWorkDateTime);

    @Override
    @Results(id = "standardWorkDateTimeResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "amStart", property = "amStart", jdbcType = JdbcType.VARCHAR),
            @Result(column = "amEnd", property = "amEnd", jdbcType = JdbcType.VARCHAR),
            @Result(column = "pmStart", property = "pmStart", jdbcType = JdbcType.VARCHAR),
            @Result(column = "pmEnd", property = "pmEnd", jdbcType = JdbcType.VARCHAR),
            @Result(column = "active", property = "active", jdbcType = JdbcType.VARCHAR),
            @Result(column = "createDate", property = "createDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "userId", property = "userId", jdbcType = JdbcType.VARCHAR)
    })
    @Select("select id, amStart, amEnd, pmStart, pmEnd, active, createDate, userId from cn_standard_work_datetime where id=#{id}")
    StandardWorkDateTime findOne(@Param("id") String id);

    @Override
    @Select("select id, amStart, amEnd, pmStart, pmEnd, active, createDate, userId from cn_standard_work_datetime")
    @ResultMap("standardWorkDateTimeResult")
    List<StandardWorkDateTime> findAll();

    @Override
    @SelectProvider(type = StandardWorkDateTimeMapperProvider.class, method = "findByCondition")
    @ResultMap("standardWorkDateTimeResult")
    List<StandardWorkDateTime> findByCondition(StandardWorkDateTime standardWorkDateTime);

    class StandardWorkDateTimeMapperProvider {

        public String findByCondition(StandardWorkDateTime standardWorkDateTime) {
            return MyBatisSQL.builder()
                    .SELECT("id, amStart, amEnd, pmStart, pmEnd, active, createDate, userId")
                    .FROM("cn_standard_work_datetime")
                    .whereIfNotNull(standardWorkDateTime.getActive(), "active=#{active}")
                    .whereIfNotNull(standardWorkDateTime.getUserId(), "userId=#{userId}")
                    .whereIfNotNull(standardWorkDateTime.getId(), "id=#{id}")
                    .build();
        }

        public String updateByCondition(final StandardWorkDateTime standardWorkDateTime) {
            return MyBatisSQL.builder()
                    .UPDATE("cn_standard_work_datetime")
                    .setIfNotNull(standardWorkDateTime.getAmStart(), "amStart=#{amStart}")
                    .setIfNotNull(standardWorkDateTime.getAmEnd(), "amEnd=#{amEnd}")
                    .setIfNotNull(standardWorkDateTime.getPmStart(), "pmStart=#{pmStart}")
                    .setIfNotNull(standardWorkDateTime.getPmEnd(), "pmEnd=#{pmEnd}")
                    .setIfNotNull(standardWorkDateTime.getActive(), "active=#{active}")
                    .setIfNotNull(standardWorkDateTime.getUserId(), "userId=#{userId}")
                    .whereIfNotNull(standardWorkDateTime.getId(), "id=#{id}")
                    .build();
        }
    }
}
