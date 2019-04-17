package com.sxkl.project.cloudnote.etl.mapper.local;


import com.sxkl.project.cloudnote.etl.entity.Tally;
import com.sxkl.project.cloudnote.etl.mapper.BaseLoadMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocalTallyMapper extends BaseLoadMapper<Tally> {

    @Override
    @Insert("INSERT INTO cn_tally(id, money, mark, category_id, create_date, account_book_id, type, user_id) VALUES " +
            "(#{id}, #{money}, #{mark}, #{categoryId}, #{createDate}, #{accountBookId}, #{type}, #{userId})")
    void save(Tally tally);

    @Override
    @Delete("DELETE FROM cn_tally")
    void delete();
}
