package com.sxkl.project.cloudnote.attendance.mapper;

import com.sxkl.project.cloudnote.attendance.entity.Leave;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.utils.MyBatisSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface LeaveMapper extends BaseMapper<Leave> {

    @Override
    @Insert("insert into cn_leave(id, leaveStart, leaveEnd, name, tips, createDate, userId) values " +
            "(#{id}, #{leaveStart}, #{leaveEnd}, #{name}, #{tips}, #{createDate}, #{userId})")
    void add(Leave leave);

    @Override
    @Delete("delete from cn_leave where id=#{id}")
    void removeOne(@Param("id") String id);

    @Override
    @UpdateProvider(type = LeaveMapperProvider.class, method = "updateByCondition")
    void update(Leave leave);

    @Override
    @Results(id = "leaveResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "leaveStart", property = "leaveStart", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "leaveEnd", property = "leaveEnd", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "tips", property = "tips", jdbcType = JdbcType.VARCHAR),
            @Result(column = "createDate", property = "createDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "userId", property = "userId", jdbcType = JdbcType.VARCHAR)
    })
    @Select("select id, leaveStart, leaveEnd, name, tips, createDate, userId from cn_leave where id=#{id}")
    Leave findOne(@Param("id") String id);

    @Override
    @Select("select id, leaveStart, leaveEnd, name, tips, createDate, userId from cn_leave")
    @ResultMap("leaveResult")
    List<Leave> findAll();

    @Override
    @SelectProvider(type = LeaveMapperProvider.class, method = "findByCondition")
    @ResultMap("leaveResult")
    List<Leave> findByCondition(Leave leave);

    class LeaveMapperProvider {

        public String findByCondition(Leave leave) {
            return MyBatisSQL.builder()
                    .SELECT("id, leaveStart, leaveEnd, name, tips, createDate, userId")
                    .FROM("cn_leave")
                    .whereIfNotNull(leave.getName(), "name like #{name}")
                    .whereIfNotNull(leave.getStartDate(), "leaveStart>=#{startDate}")
                    .whereIfNotNull(leave.getEndDate(), "leaveEnd<=#{endDate}")
                    .whereIfNotNull(leave.getUserId(), "userId=#{userId}")
                    .whereIfNotNull(leave.getId(), "id=#{id}")
                    .build();
        }

        public String updateByCondition(final Leave leave) {
            return MyBatisSQL.builder()
                    .UPDATE("cn_leave")
                    .setIfNotNull(leave.getLeaveStart(), "leaveStart=#{leaveStart}")
                    .setIfNotNull(leave.getLeaveEnd(), "leaveEnd=#{leaveEnd}")
                    .setIfNotNull(leave.getName(), "name=#{name}")
                    .setIfNotNull(leave.getTips(), "tips=#{tips}")
                    .setIfNotNull(leave.getUserId(), "userId=#{userId}")
                    .whereIfNotNull(leave.getId(), "id=#{id}")
                    .build();
        }
    }
}
