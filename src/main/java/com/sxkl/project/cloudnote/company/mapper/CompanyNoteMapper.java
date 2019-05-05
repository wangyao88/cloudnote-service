package com.sxkl.project.cloudnote.company.mapper;

import com.sxkl.project.cloudnote.base.aop.ShowSql;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.company.entity.CompanyNote;
import com.sxkl.project.cloudnote.utils.MyBatisSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface CompanyNoteMapper extends BaseMapper<CompanyNote> {

    @Override
    @Insert("insert into cn_company_note(id, title, content, companyId, createDate, userId) values " +
            "(#{id}, #{title}, #{content}, #{companyId}, #{createDate}, #{userId})")
    void add(CompanyNote companyNote);

    @Override
    @Delete("delete from cn_company_note where id=#{id}")
    void removeOne(@Param("id") String id);

    @Override
    @UpdateProvider(type = CompanyNoteMapperProvider.class, method = "updateByCondition")
    void update(CompanyNote companyNote);

    @Override
    @Results(id = "companyNoteResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "companyName", property = "companyName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "companyId", property = "companyId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "createDate", property = "createDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "userId", property = "userId", jdbcType = JdbcType.VARCHAR)
    })
    @Select("select a.id, a.title, a.content, b.name as companyName, a.companyId, a.userId from cn_company_note a left join cn_company b on a.companyId=b.id where a.id=#{id}")
    CompanyNote findOne(@Param("id") String id);

    @Override
    @Select("select a.id, a.title, a.content, b.name as companyName, a.companyId, a.userId from cn_company_note a left join cn_company b on a.companyId=b.id")
    @ResultMap("companyNoteResult")
    List<CompanyNote> findAll();

    @Override
    @SelectProvider(type = CompanyNoteMapperProvider.class, method = "findByCondition")
    @ResultMap("companyNoteResult")
    List<CompanyNote> findByCondition(CompanyNote companyNote);

    class CompanyNoteMapperProvider {

        public String findByCondition(CompanyNote companyNote) {
            return MyBatisSQL.builder()
                    .SELECT("a.id, a.createDate, a.title, a.content, b.name as companyName, a.companyId, a.userId")
                    .FROM("cn_company_note a")
                    .LEFT_OUTER_JOIN("cn_company b on a.companyId=b.id")
                    .whereIfNotNull(companyNote.getTitle(), "a.title like #{title}")
                    .whereIfNotNull(companyNote.getContent(), "a.content like #{content}")
                    .whereIfNotNull(companyNote.getCompanyId(), "a.companyId=#{companyId}")
                    .whereIfNotNull(companyNote.getStartDate(), "a.createDate>=#{startDate}")
                    .whereIfNotNull(companyNote.getEndDate(), "a.createDate<=#{endDate}")
                    .whereIfNotNull(companyNote.getUserId(), "a.userId=#{userId}")
                    .whereIfNotNull(companyNote.getId(), "a.id=#{id}")
                    .build();
        }

        public String updateByCondition(final CompanyNote companyNote) {
            return MyBatisSQL.builder()
                    .UPDATE("cn_company_note")
                    .setIfNotNull(companyNote.getTitle(), "title=#{title}")
                    .setIfNotNull(companyNote.getContent(), "content=#{content}")
                    .whereIfNotNull(companyNote.getId(), "id=#{id}")
                    .build();
        }
    }
}
