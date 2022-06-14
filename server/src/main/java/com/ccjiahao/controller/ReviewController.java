package com.ccjiahao.controller;

import com.ccjiahao.dto.Feedback;
import com.ccjiahao.dto.Review;
import com.ccjiahao.entity.Paper;
import com.ccjiahao.entity.User;
import com.ccjiahao.mapper.ReviewMapper;
import com.ccjiahao.mapper.UserMapper;
import com.ccjiahao.utils.EmailUtils;
import com.ccjiahao.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ccjiahao.mapper.PaperMapper;
import com.ccjiahao.mapper.ReviewMapper;

import java.util.*;

@CrossOrigin(origins = {"*", "null"})
@RestController
public class ReviewController {
    private final UserMapper userMapper;
    private final ReviewMapper reviewMapper;
    private final PaperMapper paperMapper;
    private final EmailUtils emailUtils;

    @Autowired
    public ReviewController(UserMapper userMapper, ReviewMapper reviewMapper,PaperMapper paperMapper, EmailUtils emailUtils){
        this.userMapper = userMapper;
        this.reviewMapper = reviewMapper;
        this.paperMapper = paperMapper;
        this.emailUtils = emailUtils;
    }

    @PostMapping("/api/submitReview")
    public String submitReview(@RequestBody Review review){
        try {
            String username = TokenUtils.getUserByToken(review.getToken());
            com.ccjiahao.entity.Review review1 = new com.ccjiahao.entity.Review(0, review.getPaperId(),username,review.getIsAssociated(),review.getLogic(),review.getSci(),review.getInnovation(),review.getPassOrReject(),review.getSuggestion(),new Date(System.currentTimeMillis()));
            String paperid = String.valueOf(review.getPaperId());
            Paper paper = paperMapper.selectPaperById(paperid);
            paper.setState("待辩论");
            paperMapper.updateById(paper);
            reviewMapper.insert(review1);
            List<String> names;
            if(paper.getCollaborators().equals("")) {
                names = new ArrayList<>();
            }
            else{
                names = new ArrayList<>(List.of(paper.getCollaborators().split(",")));
            }
            List<String> emails = new ArrayList<>();
            names.add(paper.getUsername());
            for (String name: names) {
                User user = userMapper.selectUserByUsername(name);
                emails.add(user.getEmail());
            }
            emailUtils.sendRebuttalRemain(emails.toArray(String[]::new), names.toArray(String[]::new), paper.getTitle());
        } catch (Exception e) {
            return Feedback.error("Token失效！");
        }
        return Feedback.info(null);
    }

    @PostMapping("/api/updateReview")
    public String updateReview(@RequestBody Review review){
        try{
            String paperid = String.valueOf(review.getPaperId());
            com.ccjiahao.entity.Review review1 = reviewMapper.selectReviewByPaperId(paperid);
            review1.setLogic(review.getLogic());
            review1.setInnovation(review.getInnovation());
            review1.setSci(review.getSci());
            review1.setPassOrReject(review.getPassOrReject());
            review1.setSuggestion(review.getSuggestion());
            reviewMapper.updateById(review1);
            Paper paper = paperMapper.selectPaperById(paperid);
            paper.setState("已确认");
            paperMapper.updateById(paper);
            // 发送邮件逻辑
            List<String> names;
            if(paper.getCollaborators().equals("")) {
                names = new ArrayList<>();
            }
            else{
                names = new ArrayList<>(List.of(paper.getCollaborators().split(",")));
            }
            List<String> emails = new ArrayList<>();
            names.add(paper.getUsername());
            for (String name: names) {
                User user = userMapper.selectUserByUsername(name);
                emails.add(user.getEmail());
            }
            User chairman = userMapper.selectUserByUsername("chairman");
            emails.add(chairman.getEmail());
            emailUtils.sendReviewConfirm(emails.toArray(String[]::new), names.toArray(String[]::new), paper.getTitle());
        } catch (Exception e) {
            return Feedback.error("Token失效！");
        }
        return Feedback.info(null);
    }

    @GetMapping("api/getReviewByPaperId")
    public String getReviewByPaperId(@RequestParam String paperid){
        com.ccjiahao.entity.Review review = reviewMapper.selectReviewByPaperId(paperid);
        if (review == null) {
            return Feedback.error("该审核意见不存在");
        }
        Dictionary<String, Object> data = new Hashtable<>();
        data.put("review", review);
        return Feedback.info(data);
    }

}
