package com.ccjiahao.controller;

import com.ccjiahao.dto.Feedback;
import com.ccjiahao.dto.Review;
import com.ccjiahao.entity.Paper;
import com.ccjiahao.mapper.ReviewMapper;
import com.ccjiahao.mapper.UserMapper;
import com.ccjiahao.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ccjiahao.mapper.PaperMapper;
import com.ccjiahao.mapper.ReviewMapper;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

@CrossOrigin(origins = {"*", "null"})
@RestController
public class ReviewController {
    private final ReviewMapper reviewMapper;
    private final PaperMapper paperMapper;

    @Autowired
    public ReviewController(ReviewMapper reviewMapper,PaperMapper paperMapper){
        this.reviewMapper = reviewMapper;
        this.paperMapper = paperMapper;
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
            //com.ccjiahao.entity.Paper paper1 = new com.ccjiahao.entity.Paper(0, username, paper.getTitle(), paper.getAbstracts(), paper.getExpertise(), paper.getCollaborators(), paper.getPaper(), new Date(System.currentTimeMillis()), "待审核");
            //paperMapper.insert(paper1);
            // 添加 一条评审意见 需要从表单获取数据
            // 是否符合会议主题 逻辑性 创新性 接收情况 评审意见
            // 获取当前用户token  从前端传入的数据找到文章的id
            // 修改paper表中的论文的状态 更新表
            // 获取时间并添加到review中

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
