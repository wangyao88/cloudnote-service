package com.sxkl.project.cloudnote.attendance.mapper;

import com.sxkl.project.cloudnote.attendance.entity.Attendance;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.utils.MyBatisSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface AttendanceMapper extends BaseMapper<Attendance> {

    @Override
    @Insert("insert into cn_attendance(id, amStart, amEnd, pmStart, pmEnd, createDate, userId) values " +
            "(#{id}, #{amStart}, #{amEnd}, #{pmStart}, #{pmEnd}, #{createDate}, #{userId})")
    void add(Attendance attendance);

    @Override
    @Delete("delete from cn_attendance where id=#{id}")
    void removeOne(@Param("id") String id);

    @Override
    @UpdateProvider(type = AttendanceMapperProvider.class, method = "updateByCondition")
    void update(Attendance attendance);

    @Override
    @Results(id = "attendanceResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "amStart", property = "amStart", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "amEnd", property = "amEnd", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "pmStart", property = "pmStart", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "pmEnd", property = "pmEnd", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "createDate", property = "createDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "userId", property = "userId", jdbcType = JdbcType.VARCHAR)
    })
    @Select("select id, amStart, amEnd, pmStart, pmEnd, createDate, userId from cn_attendance where id=#{id}")
    Attendance findOne(@Param("id") String id);

    @Override
    @Select("select id, amStart, amEnd, pmStart, pmEnd, createDate, userId from cn_attendance")
    @ResultMap("attendanceResult")
    List<Attendance> findAll();

    @Override
    @SelectProvider(type = AttendanceMapperProvider.class, method = "findByCondition")
    @ResultMap("attendanceResult")
    List<Attendance> findByCondition(Attendance attendance);

    class AttendanceMapperProvider {

        public String findByCondition(Attendance attendance) {
            return MyBatisSQL.builder()
                    .SELECT("id, amStart, amEnd, pmStart, pmEnd, createDate, userId")
                    .FROM("cn_attendance")
                    .whereIfNotNull(attendance.getStartDate(), "amStart>=#{startDate}")
                    .whereIfNotNull(attendance.getEndDate(), "pmEnd<=#{endDate}")
                    .whereIfNotNull(attendance.getUserId(), "userId=#{userId}")
                    .whereIfNotNull(attendance.getId(), "id=#{id}")
                    .build();
        }

        public String updateByCondition(final Attendance attendance) {
            return MyBatisSQL.builder()
                    .UPDATE("cn_attendance")
                    .setIfNotNull(attendance.getAmStart(), "amStart=#{amStart}")
                    .setIfNotNull(attendance.getAmEnd(), "amEnd=#{amEnd}")
                    .setIfNotNull(attendance.getPmStart(), "pmStart=#{pmStart}")
                    .setIfNotNull(attendance.getPmEnd(), "pmEnd=#{pmEnd}")
                    .setIfNotNull(attendance.getUserId(), "userId=#{userId}")
                    .whereIfNotNull(attendance.getId(), "id=#{id}")
                    .build();
        }
    }
}
