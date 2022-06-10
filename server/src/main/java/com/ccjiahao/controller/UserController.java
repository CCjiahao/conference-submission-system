package com.ccjiahao.controller;

import com.ccjiahao.dto.Feedback;
import com.ccjiahao.dto.Login;
import com.ccjiahao.dto.Token;
import com.ccjiahao.entity.User;
import com.ccjiahao.mapper.UserMapper;
import com.ccjiahao.mapper.VerificationCodeMapper;
import com.ccjiahao.utils.TokenUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

@CrossOrigin(origins = {"*", "null"})
@RestController
public class UserController {
    private final UserMapper userMapper;
    private Gson gson=new Gson();

    @Autowired
    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @GetMapping("/api/getUserByEmail")
    public String login(@RequestParam String email) {
        User user = userMapper.selectUserByEmail(email);
        if (user == null) {
            return Feedback.error("该用户不存在！");
        } else {
            user.setPassword(null);
            Dictionary<String, Object> data = new Hashtable<>();
            data.put("user", user);
            return Feedback.info(data);
        }
    }

    @GetMapping("/api/getUsers")
    public String getUsers(){
        List<User> users = userMapper.selectList(null);
        return gson.toJson(users);
    }

    @PostMapping("/api/getUserByToken")
    public String getUserByToken(@RequestBody Token token) {
        try {
            String username = TokenUtils.getUserByToken(token.getToken());
            User user = userMapper.selectUserByUsername(username);
            if (user == null) {
                return Feedback.error("token失效！");
            } else {
                user.setPassword(null);
                Dictionary<String, Object> data = new Hashtable<>();
                data.put("user", user);
                return Feedback.info(data);
            }
        } catch (Exception e) {
            return Feedback.error("token失效！");
        }
    }
}
