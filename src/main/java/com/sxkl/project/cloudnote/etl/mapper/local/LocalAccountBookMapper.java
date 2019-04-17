package com.sxkl.project.cloudnote.etl.mapper.local;


import com.sxkl.project.cloudnote.etl.entity.AccountBook;
import com.sxkl.project.cloudnote.etl.mapper.BaseLoadMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocalAccountBookMapper extends BaseLoadMapper<AccountBook> {

    @Override
    @Insert("INSERT INTO cn_account_book(id, name, mark, create_date, user_id) VALUES (#{id}, #{name}, #{mark}, #{createDate}, #{userId})")
    void save(AccountBook accountBook);

    @Override
    @Delete("DELETE FROM cn_account_book")
    void delete();
}
