package com.sxkl.project.cloudnote.etl.entity;


import lombok.Data;

@Data
public class FlagArticle extends BaseEntity {

    private static final long serialVersionUID = 2415797711956871613L;

    private String flagId;
    private String articleId;
}
