package com.ccjiahao.controller;

import com.ccjiahao.dto.Feedback;
import com.ccjiahao.dto.Register;
import com.ccjiahao.entity.Paper;
import com.ccjiahao.entity.Rebuttal;
import com.ccjiahao.entity.Review;
import com.ccjiahao.entity.User;
import com.ccjiahao.mapper.PaperMapper;
import com.ccjiahao.mapper.RebuttalMapper;
import com.ccjiahao.mapper.ReviewMapper;
import com.ccjiahao.mapper.UserMapper;
import com.ccjiahao.utils.EmailUtils;
import com.ccjiahao.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Dictionary;
import java.util.Hashtable;

@CrossOrigin(origins = {"*", "null"})
@RestController
public class RebuttalController {
    private final UserMapper userMapper;
    private final RebuttalMapper rebuttalMapper;
    private final PaperMapper paperMapper;
    private final ReviewMapper reviewMapper;
    private final EmailUtils emailUtils;
    @Autowired
    public RebuttalController(UserMapper userMapper, RebuttalMapper rebuttalMapper, PaperMapper paperMapper, ReviewMapper reviewMapper, EmailUtils emailUtils) {
        this.userMapper = userMapper;
        this.rebuttalMapper = rebuttalMapper;
        this.paperMapper = paperMapper;
        this.reviewMapper = reviewMapper;
        this.emailUtils = emailUtils;
    }

    @PostMapping("/api/submitRebuttal")
    public String submitRebuttal(@RequestBody com.ccjiahao.dto.Rebuttal rebuttal) {
        try {
            String username = TokenUtils.getUserByToken(rebuttal.getToken());
            Paper paper = paperMapper.selectPaperById(rebuttal.getPaperId());
            if(paper == null || !paper.isAuthor(username)) {
                return Feedback.error("你没有权限执行该操作！");
            }
            if(!paper.getState().equals("待辩论")) {
                return Feedback.error("该阶段论文不能进行辩论！");
            }
            Review review = reviewMapper.selectReviewByPaperId(rebuttal.getPaperId());
            if(review == null || !review.getReviewer().equals(rebuttal.getReviewer())) {
                return Feedback.error("该审核意见不存在！");
            }
            Rebuttal rebuttal_ = new Rebuttal(0, Integer.parseInt(rebuttal.getPaperId()), rebuttal.getReviewer(), rebuttal.getContent());
            rebuttalMapper.insert(rebuttal_);
            paper.setState("已辩论");
            paperMapper.updateById(paper);
            User reviewer = userMapper.selectUserByUsername(review.getReviewer());
            emailUtils.sendReReviewRemain(reviewer.getEmail(), paper.getUsername(), paper.getTitle());
            return Feedback.info(null);
        } catch (Exception e) {
            return Feedback.error(e.toString());
        }
    }

    @GetMapping("/api/getRebuttalByPaperId")
    public String getRebuttalByPaperId(@RequestParam String paperid){
        Rebuttal rebuttal = rebuttalMapper.selectByPaperId(paperid);
        if (rebuttal == null) {
            return Feedback.debug("该辩驳不存在");
        }
        Dictionary<String, Object> data = new Hashtable<>();
        data.put("rebuttal", rebuttal);
        return Feedback.info(data);
    }
}
