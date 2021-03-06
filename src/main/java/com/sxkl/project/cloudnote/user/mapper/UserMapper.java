package com.sxkl.project.cloudnote.user.mapper;

import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import com.sxkl.project.cloudnote.user.entity.User;
import com.sxkl.project.cloudnote.utils.MyBatisSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Override
    @Insert("insert into cn_user(id, name, password) values (#{id}, #{name}, #{password})")
    void add(User user);

    @Override
    @Delete("delete from cn_user where id=#{id}")
    void removeOne(@Param("id") String id);

    @Override
    @UpdateProvider(type = UserMapperProvider.class, method = "updateByCondition")
    void update(User user);

    @Results(id = "userResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR)
    })
    @Select("select id, name, password from cn_user where id=#{id}")
    User findUserById(@Param("id") String id);

    @Override
    @ResultMap("userResult")
    @Select("select id, name from cn_user where id=#{id}")
    User findOne(@Param("id") String id);

    @Override
    @Select("select id, name from cn_user")
    @ResultMap("userResult")
    List<User> findAll();

    @Override
    @SelectProvider(type = UserMapperProvider.class, method = "findByCondition")
    @ResultMap("userResult")
    List<User> findByCondition(User user);

    @Select("select id, name from cn_user where name=#{name}")
    @ResultMap("userResult")
    List<User> findByName(User user);

    class UserMapperProvider {

        public String findByCondition(User user) {
            return MyBatisSQL.builder()
                    .SELECT("id, name")
                    .FROM("cn_user")
                    .whereIfNotNull(user.getName(), "name like #{name}")
                    .whereIfNotNull(user.getId(), "id=#{id}")
                    .build();
        }

        public String updateByCondition(final User user) {
            return MyBatisSQL.builder()
                    .UPDATE("cn_user")
                    .setIfNotNull(user.getName(), "name=#{name}")
                    .setIfNotNull(user.getPassword(), "password=#{password}")
                    .whereIfNotNull(user.getId(), "id=#{id}")
                    .build();
        }
    }
}
