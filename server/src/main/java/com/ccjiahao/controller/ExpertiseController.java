package com.ccjiahao.controller;

import com.ccjiahao.dto.Feedback;
import com.ccjiahao.entity.Expertise;
import com.ccjiahao.entity.User;
import com.ccjiahao.mapper.ExpertiseMapper;
import com.ccjiahao.mapper.UserMapper;
import com.ccjiahao.utils.TokenUtils;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = {"*", "null"})
@RestController
public class ExpertiseController {
    private final ExpertiseMapper expertiseMapper;
    private final UserMapper userMapper;

    public ExpertiseController(ExpertiseMapper expertiseMapper, UserMapper userMapper) {
        this.expertiseMapper = expertiseMapper;
        this.userMapper = userMapper;
    }

    @GetMapping("/api/getExpertises")
    public String getExpertises() {
        List<Expertise> expertises = expertiseMapper.selectList(null);
        Dictionary<String, Object> data = new Hashtable<>();
        data.put("expertises", expertises);
        return Feedback.info(data);
    }

    private String verifyToken(String token) {
        String username;
        try {
            username = TokenUtils.getUserByToken(token);
        } catch (Exception e) {
            return Feedback.error("token失效");
        }
        if(!username.equals("chairman")) {
            return Feedback.error("你没有该权限");
        }
        return null;
    }

    @PostMapping("/api/addExpertise")
    public String addExpertise(@RequestBody com.ccjiahao.dto.Expertise expertise) {
        String info = verifyToken(expertise.getToken());
        if(info != null) {
            return Feedback.error(info);
        }
        Expertise expertise1 = expertiseMapper.selectByName(expertise.getName());
        if(expertise1 != null){
            return Feedback.error("该名称的方向已经存在");
        }
        expertise1 = new Expertise(expertise.getId(), expertise.getName(), expertise.getDescription());
        expertiseMapper.insert(expertise1);
        User user = userMapper.selectUserByUsername("chairman");
        user.setExpertise(user.getExpertise() + "," + expertise.getName());
        userMapper.updateByUsername(user);
        return Feedback.info(null);
    }

    @PostMapping("/api/updateExpertise")
    public String updateExpertise(@RequestBody com.ccjiahao.dto.Expertise expertise) {
        String info = verifyToken(expertise.getToken());
        if(info != null) {
            return Feedback.error(info);
        }
        Expertise expertise1 = expertiseMapper.selectById(expertise.getId());
        String old_name = expertise1.getName(), new_name = expertise.getName();
        expertise1.setName(new_name);
        expertise1.setDescription(expertise.getDescription());
        expertiseMapper.updateById(expertise1);
        if(!old_name.equals(new_name)) {
            List<User> users = userMapper.selectList(null);
            for (User user : users) {
                List<String> expertises = new ArrayList<>(List.of(user.getExpertise().split(",")));
                if (expertises.contains(old_name)) {
                    expertises.remove(old_name);
                    expertises.add(new_name);
                    user.setExpertise(StringUtils.join(expertises, ','));
                    userMapper.updateByUsername(user);
                }
            }
        }
        return Feedback.info(null);
    }

    @PostMapping("/api/deleteExpertise")
    public String deleteExpertise(@RequestBody com.ccjiahao.dto.Expertise expertise) {
        String info = verifyToken(expertise.getToken());
        if(info != null) {
            return Feedback.error(info);
        }
        Expertise expertise1 = new Expertise(expertise.getId(), expertise.getName(), expertise.getDescription());
        expertiseMapper.deleteById(expertise1);
        List<User> users = userMapper.selectList(null);
        for(User user : users) {
            List<String> expertises = new ArrayList<>(List.of(user.getExpertise().split(",")));
            if(expertises.contains(expertise.getName())){
                expertises.remove(expertise.getName());
                user.setExpertise(StringUtils.join(expertises, ','));
                userMapper.updateByUsername(user);
            }
        }
        return Feedback.info(null);
    }
}
