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
    @Insert("insert into cn_attendance(id, attendanceDate, createDate, userId) values " +
            "(#{id}, #{attendanceDate}, #{createDate}, #{userId})")
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
            @Result(column = "attendanceDate", property = "attendanceDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "createDate", property = "createDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "userId", property = "userId", jdbcType = JdbcType.VARCHAR)
    })
    @Select("select id, attendanceDate, createDate, userId from cn_attendance where id=#{id}")
    Attendance findOne(@Param("id") String id);

    @Override
    @Select("select id, attendanceDate, createDate, userId from cn_attendance")
    @ResultMap("attendanceResult")
    List<Attendance> findAll();

    @Override
    @SelectProvider(type = AttendanceMapperProvider.class, method = "findByCondition")
    @ResultMap("attendanceResult")
    List<Attendance> findByCondition(Attendance attendance);

    class AttendanceMapperProvider {

        public String findByCondition(Attendance attendance) {
            return MyBatisSQL.builder()
                    .SELECT("id, attendanceDate, createDate, userId")
                    .FROM("cn_attendance")
                    .whereIfNotNull(attendance.getStartDate(), "attendanceDate>=#{startDate}")
                    .whereIfNotNull(attendance.getEndDate(), "attendanceDate<=#{endDate}")
                    .whereIfNotNull(attendance.getUserId(), "userId=#{userId}")
                    .whereIfNotNull(attendance.getId(), "id=#{id}")
                    .build();
        }

        public String updateByCondition(final Attendance attendance) {
            return MyBatisSQL.builder()
                    .UPDATE("cn_attendance")
                    .setIfNotNull(attendance.getAttendanceDate(), "amStart=#{attendanceDate}")
                    .setIfNotNull(attendance.getUserId(), "userId=#{userId}")
                    .whereIfNotNull(attendance.getId(), "id=#{id}")
                    .build();
        }
    }
}
