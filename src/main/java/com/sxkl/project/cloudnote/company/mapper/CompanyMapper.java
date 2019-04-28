package com.sxkl.project.cloudnote.company.mapper;

import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.company.entity.Company;
import com.sxkl.project.cloudnote.utils.MyBatisSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface CompanyMapper extends BaseMapper<Company> {

    @Override
    @Insert("insert into cn_company(id, name, flag, address, inDate, outDate, userId) values " +
            "(#{id}, #{name}, #{flag}, #{address}, #{inDate}, #{outDate}, #{userId})")
    void add(Company company);

    @Override
    @Delete("delete from cn_company where id=#{id}")
    void removeOne(@Param("id") String id);

    @Override
    @UpdateProvider(type = CompanyMapperProvider.class, method = "updateByCondition")
    void update(Company company);

    @Override
    @Results(id = "companyResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "flag", property = "flag", jdbcType = JdbcType.VARCHAR),
            @Result(column = "address", property = "address", jdbcType = JdbcType.VARCHAR),
            @Result(column = "inDate", property = "inDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "outDate", property = "outDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "userId", property = "userId", jdbcType = JdbcType.VARCHAR)
    })
    @Select("select id, name, flag, address, inDate, outDate, userId from cn_company where id=#{id}")
    Company findOne(@Param("id") String id);

    @Override
    @Select("select id, name, flag, address, inDate, outDate, userId from cn_company")
    @ResultMap("companyResult")
    List<Company> findAll();

    @Override
    @SelectProvider(type = CompanyMapperProvider.class, method = "findByCondition")
    @ResultMap("companyResult")
    List<Company> findByCondition(Company company);

    @Select("select id, name from cn_company where name=#{name}")
    @ResultMap("companyResult")
    List<Company> findByName(Company company);

    class CompanyMapperProvider {

        public String findByCondition(Company company) {
            return MyBatisSQL.builder()
                    .SELECT("id, name, flag, address, inDate, outDate, userId")
                    .FROM("cn_company")
                    .whereIfNotNull(company.getName(), "name like #{name}")
                    .whereIfNotNull(company.getId(), "id=#{id}")
                    .build();
        }

        public String updateByCondition(final Company company) {
            return MyBatisSQL.builder()
                    .UPDATE("cn_company")
                    .setIfNotNull(company.getName(), "name=#{name}")
                    .setIfNotNull(company.getFlag(), "flag=#{flag}")
                    .setIfNotNull(company.getAddress(), "address=#{address}")
                    .setIfNotNull(company.getInDate(), "inDate=#{inDate}")
                    .setIfNotNull(company.getOutDate(), "outDate=#{outDate}")
                    .setIfNotNull(company.getUserId(), "userId=#{userId}")
                    .whereIfNotNull(company.getId(), "id=#{id}")
                    .build();
        }
    }
}
