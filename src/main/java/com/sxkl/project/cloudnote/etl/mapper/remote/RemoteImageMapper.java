package com.sxkl.project.cloudnote.etl.mapper.remote;


import com.sxkl.project.cloudnote.etl.entity.Article;
import com.sxkl.project.cloudnote.etl.entity.Image;
import com.sxkl.project.cloudnote.etl.mapper.BaseCursorExtractMapper;
import com.sxkl.project.cloudnote.etl.mapper.MyBatisSQL;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.BlobTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface RemoteImageMapper extends BaseCursorExtractMapper {

    @Override
    @SelectProvider(type = ImageMapperProvider.class, method = "findAll")
    @Results(id = "ImageResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "alt", property = "alt", jdbcType = JdbcType.VARCHAR),
            @Result(column = "aId", property = "aId", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.BLOB, typeHandler = BlobTypeHandler.class),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR)
    })
    List<Image> findAll();

    @Override
    @SelectProvider(type = ImageMapperProvider.class, method = "findOne")
    @ResultMap("ImageResult")
    Image findOne(@Param("id") String id);

    @SelectProvider(type = ImageMapperProvider.class, method = "deleteUnusedImage")
    void deleteUnusedImage();


    class ImageMapperProvider {

        public String findAll() {
            return MyBatisSQL.builder()
                             .SELECT("id ")
                             .FROM("cn_image")
                             .build();
        }

        public String findOne(final @Param("id") String id) {
            return MyBatisSQL.builder()
                    .SELECT("id, alt, aId, content, name ")
                    .FROM("cn_image ")
                    .WHERE("id=#{id}")
                    .build();
        }

        public String deleteUnusedImage() {
            return MyBatisSQL.builder()
                             .DELETE_FROM("cn_image")
                             .WHERE("id not in (select cc.id from (select distinct i.id from cn_image i left join cn_article a on i.aId=a.id where position(i.name in a.content)) cc)")
                             .build();
//            "delete from cn_image where id not in (select cc.id from (select distinct i.id from cn_image i left join cn_article a on i.aId=a.id where position(i.name in a.content)) cc);"
        }
    }
}
