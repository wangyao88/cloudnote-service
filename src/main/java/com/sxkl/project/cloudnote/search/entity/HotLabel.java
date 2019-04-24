package com.sxkl.project.cloudnote.search.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotLabel {

    private int id;
    private String label;
    private double score;
}
