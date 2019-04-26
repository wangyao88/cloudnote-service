package com.sxkl.project.cloudnote.user.service;

import com.sxkl.project.cloudnote.base.entity.OperateResult;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import com.sxkl.project.cloudnote.user.entity.User;
import com.sxkl.project.cloudnote.user.mapper.UserMapper;
import com.sxkl.project.cloudnote.utils.DESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseService {

    @Autowired
    private UserMapper userMapper;

    @Override
    protected BaseMapper getMapper() {
        return userMapper;
    }

    public OperateResult login(String name, String password) {
        if(StringUtils.isEmpty(name)) {
            return OperateResult.builder().msg("用户名不能为空").status(Boolean.FALSE).build();
        }
        if(StringUtils.isEmpty(password)) {
            return OperateResult.builder().msg("密码不能为空").status(Boolean.FALSE).build();
        }
        DESUtil des = new DESUtil();
        User user = new User(name, des.encrypt(password));
        List<User> users = findByCondition(user);
        if(users.isEmpty()) {
            return OperateResult.builder().msg("用户名密码错误").status(Boolean.FALSE).build();
        }
        return OperateResult.builder().status(Boolean.TRUE).data(users).build();
    }
}
