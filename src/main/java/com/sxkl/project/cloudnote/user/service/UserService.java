package com.sxkl.project.cloudnote.user.service;

import com.sxkl.project.cloudnote.base.entity.BaseEntity;
import com.sxkl.project.cloudnote.base.entity.OperateResult;
import com.sxkl.project.cloudnote.base.mapper.BaseMapper;
import com.sxkl.project.cloudnote.base.service.BaseService;
import com.sxkl.project.cloudnote.etl.utils.StringUtils;
import com.sxkl.project.cloudnote.etl.utils.UUIDUtil;
import com.sxkl.project.cloudnote.user.entity.User;
import com.sxkl.project.cloudnote.user.mapper.UserMapper;
import com.sxkl.project.cloudnote.utils.DESUtil;
import com.sxkl.project.cloudnote.utils.RSACoder;
import com.sxkl.project.cloudnote.utils.RsaKeyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseService<User> {

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

    public OperateResult updatePassword(String id, String password) {
        try {
            User user = new User();
            user.setId(id);
            password = getfrontPassword(password);
            DESUtil des = new DESUtil();
            user.setPassword(des.encrypt(password));
            userMapper.update(user);
            return OperateResult.buildSuccess();
        }catch (Exception e) {
            return OperateResult.buildFail(e);
        }
    }

    private String getfrontPassword(String password) throws Exception {
        byte[] decodedData = RSACoder.decryptByPrivateKey(password,RsaKeyManager.getPrivateKey());
        password = new String(decodedData);
        return password;
    }

    public OperateResult checkOldPassword(String id, String password) {
        try {
            password = getfrontPassword(password);
        } catch (Exception e) {
            return OperateResult.builder().e(e).status(Boolean.FALSE).build();
        }
        DESUtil des = new DESUtil();
        User user = userMapper.findUserById(id);
        if(user.getPassword().equals(des.encrypt(password))) {
            return OperateResult.buildSuccess();
        }
        return OperateResult.buildFail();
    }

    public OperateResult getPublicKey() {
        try {
            String publicKey = RsaKeyManager.getPublickey();
            return OperateResult.builder().data(publicKey).status(Boolean.TRUE).build();
        } catch (Exception e) {
            return OperateResult.buildFail();
        }
    }

    public OperateResult checkName(String name) {
        User user = new User();
        user.setName(name);
        List<User> users = userMapper.findByName(user);
        if(users.isEmpty()) {
            return OperateResult.buildSuccess();
        }
        return OperateResult.buildFail();
    }

    @Override
    public OperateResult add(User user) throws Exception {
        String password = user.getPassword();
        password = getfrontPassword(password);
        DESUtil des = new DESUtil();
        user.setPassword(des.encrypt(password));
        return super.add(user);
    }
}
