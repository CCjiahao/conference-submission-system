package com.ccjiahao.controller;

import com.ccjiahao.dto.Feedback;
import com.ccjiahao.dto.Login;
import com.ccjiahao.dto.Register;
import com.ccjiahao.entity.User;
import com.ccjiahao.mapper.UserMapper;
import com.ccjiahao.mapper.VerificationCodeMapper;
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
    private final VerificationCodeMapper verificationCodeMapper;
    @Autowired
    public LoginController(UserMapper userMapper, VerificationCodeMapper verificationCodeMapper) {
        this.userMapper = userMapper;
        this.verificationCodeMapper = verificationCodeMapper;
    }
    @PostMapping("/api/login")
    public String login(@RequestBody Login user){
        User user1 = userMapper.selectUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if(user1 == null) {
            return Feedback.error("用户名或密码不正确！");
        }
        else {
            String token = TokenUtils.createToken(user.getUsername(), user.getRemember());
            Dictionary<String, Object> data = new Hashtable<>();
            data.put("token", token);
            return Feedback.info(data);
        }
    }

    @PostMapping("/api/register")
    public String register(@RequestBody Register register){
        User user = userMapper.selectUserByUsername(register.getUsername());
        if(user != null) {
            return Feedback.error("该用户名已注册！");
        }
        user = userMapper.selectUserByEmail(register.getEmail());
        if(user != null) {
            return Feedback.error("该邮箱已注册！");
        }
        if(!verificationCodeMapper.selectByEmailAndCodeAndTime(register.getEmail(), register.getCode())) {
            return Feedback.error("验证码过期或不正确！");
        }
        user = new User(register.getUsername(), register.getName(), register.getPassword(), register.getEmail(), register.getSchool(), register.getCountry(), register.getExpertise(), "researcher");
        userMapper.insert(user);
        return Feedback.info(null);
    }
}
