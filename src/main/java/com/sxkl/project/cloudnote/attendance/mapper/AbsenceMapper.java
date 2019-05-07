package com.sxkl.project.cloudnote.attendance.mapper;

import com.sxkl.project.cloudnote.attendance.entity.Absence;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.utils.MyBatisSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface AbsenceMapper extends BaseMapper<Absence> {

    @Override
    @Insert("insert into cn_absence(id, absenceStart, absenceEnd, name, tips, createDate, userId) values " +
            "(#{id}, #{absenceStart}, #{absenceEnd}, #{name}, #{tips}, #{createDate}, #{userId})")
    void add(Absence absence);

    @Override
    @Delete("delete from cn_absence where id=#{id}")
    void removeOne(@Param("id") String id);

    @Override
    @UpdateProvider(type = AbsenceMapperProvider.class, method = "updateByCondition")
    void update(Absence absence);

    @Override
    @Results(id = "absenceResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "absenceStart", property = "absenceStart", jdbcType = JdbcType.DATE),
            @Result(column = "absenceEnd", property = "absenceEnd", jdbcType = JdbcType.DATE),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "tips", property = "tips", jdbcType = JdbcType.VARCHAR),
            @Result(column = "createDate", property = "createDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "userId", property = "userId", jdbcType = JdbcType.VARCHAR)
    })
    @Select("select id, absenceStart, absenceEnd, name, tips, createDate, userId from cn_absence where id=#{id}")
    Absence findOne(@Param("id") String id);

    @Override
    @Select("select id, absenceStart, absenceEnd, name, tips, createDate, userId from cn_absence")
    @ResultMap("absenceResult")
    List<Absence> findAll();

    @Override
    @SelectProvider(type = AbsenceMapperProvider.class, method = "findByCondition")
    @ResultMap("absenceResult")
    List<Absence> findByCondition(Absence absence);

    class AbsenceMapperProvider {

        public String findByCondition(Absence absence) {
            return MyBatisSQL.builder()
                    .SELECT("id, absenceStart, absenceEnd, name, tips, createDate, userId")
                    .FROM("cn_absence")
                    .whereIfNotNull(absence.getName(), "name like #{name}")
                    .whereIfNotNull(absence.getStartDate(), "absenceStart>=#{startDate}")
                    .whereIfNotNull(absence.getEndDate(), "absenceEnd<=#{endDate}")
                    .whereIfNotNull(absence.getUserId(), "userId=#{userId}")
                    .whereIfNotNull(absence.getId(), "id=#{id}")
                    .build();
        }

        public String updateByCondition(final Absence absence) {
            return MyBatisSQL.builder()
                    .UPDATE("cn_absence")
                    .setIfNotNull(absence.getAbsenceStart(), "absenceStart=#{absenceStart}")
                    .setIfNotNull(absence.getAbsenceEnd(), "absenceEnd=#{absenceEnd}")
                    .setIfNotNull(absence.getName(), "name=#{name}")
                    .setIfNotNull(absence.getTips(), "tips=#{tips}")
                    .setIfNotNull(absence.getUserId(), "userId=#{userId}")
                    .whereIfNotNull(absence.getId(), "id=#{id}")
                    .build();
        }
    }
}
