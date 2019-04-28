package com.sxkl.project.cloudnote.user.entity;

import com.sxkl.project.cloudnote.base.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User extends BaseEntity {

    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
