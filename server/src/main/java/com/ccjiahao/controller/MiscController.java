package com.ccjiahao.controller;


import com.ccjiahao.dto.Feedback;
import com.ccjiahao.dto.Login;
import com.ccjiahao.dto.Token;
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
public class MiscController {

    private final UserMapper userMapper;
    @Autowired
    public MiscController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @PostMapping("/api/getUserByToken")
    public String getUserByToken(@RequestBody Token token){
        try {
            String username = TokenUtils.getUserByToken(token.getToken());
            User user = userMapper.selectUserByUsername(username);
            if(user == null) {
                return Feedback.error("token失效！");
            }
            else {
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
