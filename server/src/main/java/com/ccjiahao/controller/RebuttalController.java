package com.ccjiahao.controller;

import com.ccjiahao.dto.Feedback;
import com.ccjiahao.dto.Register;
import com.ccjiahao.entity.Paper;
import com.ccjiahao.entity.Rebuttal;
import com.ccjiahao.entity.Review;
import com.ccjiahao.mapper.PaperMapper;
import com.ccjiahao.mapper.RebuttalMapper;
import com.ccjiahao.mapper.ReviewMapper;
import com.ccjiahao.mapper.UserMapper;
import com.ccjiahao.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*", "null"})
@RestController
public class RebuttalController {
    private final RebuttalMapper rebuttalMapper;
    private final PaperMapper paperMapper;
    private final ReviewMapper reviewMapper;
    @Autowired
    public RebuttalController(RebuttalMapper rebuttalMapper, UserMapper userMapper, PaperMapper paperMapper, ReviewMapper reviewMapper) {
        this.rebuttalMapper = rebuttalMapper;
        this.paperMapper = paperMapper;
        this.reviewMapper = reviewMapper;
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
            return Feedback.info(null);
        } catch (Exception e) {
            return Feedback.error(e.toString());
        }
    }
}
