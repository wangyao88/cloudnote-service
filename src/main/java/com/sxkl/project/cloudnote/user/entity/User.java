package com.sxkl.project.cloudnote.user.entity;

import com.sxkl.project.cloudnote.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User extends BaseEntity {

    private String id;
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
