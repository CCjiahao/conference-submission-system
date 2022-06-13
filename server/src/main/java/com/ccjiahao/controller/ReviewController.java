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
    public String submitPaper(@RequestBody Review review){
        try {
            String username = TokenUtils.getUserByToken(review.getToken());
            com.ccjiahao.entity.Review review1 = new com.ccjiahao.entity.Review(0, review.getPaperId(),username,review.getIsAssociated(),review.getLogic(),review.getSci(),review.getInnovation(),review.getPassOrReject(),review.getSuggestion(),new Date(System.currentTimeMillis()));
            String paperid = String.valueOf(review.getPaperId());
            Paper paper = paperMapper.selectPaperById(paperid);
            paper.setState("待辩论");
            paperMapper.updateById(paper);
            reviewMapper.insert(review1);
            List<String> names = new ArrayList<>(List.of(paper.getCollaborators().split(",")));
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

    @GetMapping("api/getReviewByPaperId")
    public String getReviewByPaperId(@RequestParam String paperid){
        Dictionary<String, Object> data = new Hashtable<>();
        data.put("review", reviewMapper.selectReviewByPaperId(paperid));
        return Feedback.info(data);
    }

}
