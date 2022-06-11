package com.ccjiahao.controller;

import com.ccjiahao.dto.Feedback;
import com.ccjiahao.dto.Login;
import com.ccjiahao.dto.Paper;
import com.ccjiahao.entity.User;
import com.ccjiahao.mapper.PaperMapper;
import com.ccjiahao.mapper.UserMapper;
import com.ccjiahao.mapper.VerificationCodeMapper;
import com.ccjiahao.utils.TokenUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = {"*", "null"})
@RestController
public class PaperController {
    private final UserMapper userMapper;
    private final PaperMapper paperMapper;
    @Autowired
    public PaperController(UserMapper userMapper, PaperMapper paperMapper) {
        this.userMapper = userMapper;
        this.paperMapper = paperMapper;
    }

    @PostMapping("/api/submitPaper")
    public String submitPaper(@RequestBody Paper paper) {
        try {
            String username = TokenUtils.getUserByToken(paper.getToken());
            com.ccjiahao.entity.Paper paper1 = new com.ccjiahao.entity.Paper(0, username, paper.getTitle(), paper.getAbstracts(), paper.getExpertise(), paper.getCollaborators(), paper.getPaper(), new Date(System.currentTimeMillis()), "待审核");
            paperMapper.insert(paper1);
        } catch (Exception e) {
            return Feedback.error("Token失效！");
        }
        return Feedback.info(null);
    }

    private Gson gson=new Gson();
    @GetMapping("/api/getPapers")
    public String getPapers(){
        List<com.ccjiahao.entity.Paper> papers = paperMapper.selectList(null);
        return gson.toJson(papers);
    }

    @GetMapping("api/getPaperById")
    public String getPaperById(@RequestParam String id){
        com.ccjiahao.entity.Paper paper = paperMapper.selectPaperById(id);
        return gson.toJson(paper);
    }
}
