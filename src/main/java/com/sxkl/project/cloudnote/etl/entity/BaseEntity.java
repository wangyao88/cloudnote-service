package com.sxkl.project.cloudnote.etl.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;


@Data
public class BaseEntity implements Serializable {

    @Id
    @Field(store = true)
    protected String id;
}
