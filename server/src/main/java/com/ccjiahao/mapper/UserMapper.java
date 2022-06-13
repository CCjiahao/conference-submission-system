package com.ccjiahao.mapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ccjiahao.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {
    public default void updateByUsername(User user) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username",user.getUsername());
        this.update(user, updateWrapper);
    }

    public default User selectUserByUsernameAndPassword(String username, String password) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("username", username);
        queryMap.put("password", password);
        List<User> users = this.selectByMap(queryMap);
        if (users.size() == 0) return null;
        else return users.get(0);
    }

    public default User selectUserByUsername(String username) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("username", username);
        List<User> users = this.selectByMap(queryMap);
        if (users.size() == 0) return null;
        else return users.get(0);
    }

    public default User selectUserByEmail(String email) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("email", email);
        List<User> users = this.selectByMap(queryMap);
        if (users.size() == 0) return null;
        else return users.get(0);
    }
}
