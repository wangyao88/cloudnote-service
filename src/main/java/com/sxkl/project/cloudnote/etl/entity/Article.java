package com.sxkl.project.cloudnote.etl.entity;


import com.sxkl.project.cloudnote.search.service.ESConstant;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Document(indexName = ESConstant.INDEX, type = ESConstant.TYPE)
public class Article extends BaseEntity {

    private static final long serialVersionUID = 2662489199820987983L;

    @Field(type = FieldType.Text, fielddata = true, analyzer="ik_max_word", searchAnalyzer="ik_max_word", store = true)
    private String content;
    @Field(type = FieldType.Date, store = true)
    private Date createTime;
    @Field(type = FieldType.Integer, store = true)
    private int hitNum;
    @Field(type = FieldType.Text, analyzer="ik_max_word", searchAnalyzer="ik_max_word", store = true)
    private String title;
    @Field(type = FieldType.Keyword, store = true)
    private String nId;
    @Field(type = FieldType.Keyword, store = true)
    private String uId;
    @Field(type = FieldType.Integer, store = true)
    private int isShared;
}
