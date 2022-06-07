package com.ccjiahao.controller;

import com.ccjiahao.dto.Feedback;
import com.ccjiahao.dto.Login;
import com.ccjiahao.entity.User;
import com.ccjiahao.mapper.UserMapper;
import com.ccjiahao.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

@CrossOrigin(origins = {"*", "null"})
@RestController
public class LoginController {
    private final UserMapper userMapper;
    @Autowired
    public LoginController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @PostMapping("/api/login")
    public String login(@RequestBody Login user){
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("username", user.getUsername());
        queryMap.put("password", user.getPassword());
        List<User> users = userMapper.selectByMap(queryMap);
        if(users.size() == 0) {
            return Feedback.error("用户名或密码不正确！");
        }
        else {
            String token = TokenUtils.createToken(user.getUsername(), user.getRemember());
            Dictionary<String, Object> data = new Hashtable<>();
            data.put("token", token);
            return Feedback.info(data);
        }
    }
}
