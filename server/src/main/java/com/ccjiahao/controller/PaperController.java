package com.ccjiahao.controller;

import com.ccjiahao.dto.*;
import com.ccjiahao.entity.Review;
import com.ccjiahao.entity.User;
import com.ccjiahao.mapper.PaperMapper;
import com.ccjiahao.mapper.ReviewMapper;
import com.ccjiahao.mapper.UserMapper;
import com.ccjiahao.utils.EmailUtils;
import com.ccjiahao.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = {"*", "null"})
@RestController
public class PaperController {
    private final UserMapper userMapper;
    private final PaperMapper paperMapper;
    private final ReviewMapper reviewMapper;
    private final EmailUtils emailUtils;

    @Autowired
    public PaperController(UserMapper userMapper, PaperMapper paperMapper, ReviewMapper reviewMapper, EmailUtils emailUtils) {
        this.userMapper = userMapper;
        this.paperMapper = paperMapper;
        this.reviewMapper = reviewMapper;
        this.emailUtils = emailUtils;
    }

    @PostMapping("/api/submitPaper")
    public String submitPaper(@RequestBody Paper paper) {
        try {
            String username = TokenUtils.getUserByToken(paper.getToken());
            com.ccjiahao.entity.Paper paper1 = new com.ccjiahao.entity.Paper(0, username, paper.getTitle(), paper.getAbstracts(), paper.getExpertise(), paper.getCollaborators(), paper.getPaper(), new Date(System.currentTimeMillis()), "待审核");
            paperMapper.insert(paper1);
            List<User> users = userMapper.selectList(null);
            List<String> emails = new ArrayList<String>();
            for (User user : users) {
                if (user.getRole().equals("chairman") || (user.getRole().equals("reviewer") && user.isExpertise(paper.getExpertise()) && !paper1.isAuthor(username))) {
                    emails.add(user.getEmail());
                }
            }
            emailUtils.sendReviewRemain(emails.toArray(String[]::new), username, paper.getTitle());
        } catch (Exception e) {
            return Feedback.error("Token失效！");
        }
        return Feedback.info(null);
    }

    @GetMapping("/api/getPapers")
    public String getPapers() {
        Dictionary<String, Object> data = new Hashtable<>();
        data.put("papers", paperMapper.selectList(null));
        return Feedback.info(data);
    }

    @GetMapping("/api/getPapersWithReviewer")
    public String getPapersWithManInCharge() {
        Dictionary<String, Object> data = new Hashtable<>();
        data.put("papers", paperMapper.getPapersWithReviewer());
        return Feedback.info(data);
    }

    @GetMapping("api/getPaperById")
    public String getPaperById(@RequestParam String id) {
        Dictionary<String, Object> data = new Hashtable<>();
        data.put("paper", paperMapper.selectPaperById(id));
        return Feedback.info(data);
    }

    @GetMapping("api/getPapersByAuthor")
    public String getPapersByAuthor(@RequestParam String author) {
        Dictionary<String, Object> data = new Hashtable<>();
        data.put("papers", paperMapper.selectPaperByAuthor(author));
        return Feedback.info(data);
    }

    @GetMapping("api/getPapersByReviewer")
    public String getPapersByReviewer(@RequestParam String token) {
        try {
            String username = TokenUtils.getUserByToken(token);
            User user = userMapper.selectUserByUsername(username);
            if (user == null) {
                return Feedback.error("该用户不存在");
            }
            List<com.ccjiahao.entity.Paper> papers = paperMapper.selectPaperByState("待审核");
            List<com.ccjiahao.entity.Paper> true_papers = new ArrayList<>();
            for (com.ccjiahao.entity.Paper paper : papers) {
                if (user.isExpertise(paper.getExpertise()) && !paper.isAuthor(username)) {
                    true_papers.add(paper);
                }
            }
            Dictionary<String, Object> data = new Hashtable<>();
            data.put("papers", true_papers);
            return Feedback.info(data);
        } catch (Exception e) {
            return Feedback.error("token失效！");
        }
    }

    @GetMapping("api/getReviewedPapersByReviewer")
    public String getReviewedPapersByReviewer(@RequestParam String token) {
        try {
            String username = TokenUtils.getUserByToken(token);
            List<Review> reviews = reviewMapper.selectReviewByReviewer(username);
            List<com.ccjiahao.entity.Paper> papers = new ArrayList<>();
            for (Review review : reviews) {
                papers.add(paperMapper.selectPaperById(String.valueOf(review.getPaperId())));
            }
            Dictionary<String, Object> data = new Hashtable<>();
            data.put("papers", papers);
            return Feedback.info(data);
        } catch (Exception e) {
            return Feedback.error("token失效！");
        }
    }

    @GetMapping("api/deletePaperById")
    public String deletePaperById(@RequestParam String token, @RequestParam String id) {
        try {
            String username = TokenUtils.getUserByToken(token);
            com.ccjiahao.entity.Paper paper = paperMapper.selectPaperById(id);
            if (paper == null) {
                return Feedback.error("没有找到该论文");
            }
            if (!paper.isAuthor(username)) {
                return Feedback.error("你没有该权限");
            }
            if (paper.getState().equals("待审核") || paper.getPaper().equals("未中选")) {
                paperMapper.deleteById(id);
                return Feedback.info(null);
            }
            return Feedback.error(paper.getState() + "阶段论文不支持删除");
        } catch (Exception e) {
            return Feedback.error("token失效！");
        }
    }

    @GetMapping("api/deletePaperByIdAdmin")
    public String deletePaperByIdAdmin(@RequestParam String token, @RequestParam String id) {
        try {
            String username = TokenUtils.getUserByToken(token);
            com.ccjiahao.entity.Paper paper = paperMapper.selectPaperById(id);
            if (paper == null) {
                return Feedback.error("没有找到该论文");
            }
            if (!username.equals("chairman")) {
                return Feedback.error(username + ":你没有该权限");
            }
            paperMapper.deleteById(id);
            return Feedback.info(null);
        } catch (Exception e) {
            return Feedback.error("token失效！");
        }
    }

    @GetMapping("api/getPaperDetailById")
    public String getPaperDetailById(@RequestParam String id) {
        com.ccjiahao.entity.Paper paper = paperMapper.selectPaperById(id);
        List<String> usernames;
        if (paper.getCollaborators().equals("")) {
            usernames = new ArrayList<>();
        } else {
            usernames = new ArrayList<>(List.of(paper.getCollaborators().split(",")));
        }
        usernames.add(paper.getUsername());
        List<Author> authors = new ArrayList<>();
        for (String username : usernames) {
            User user = userMapper.selectUserByUsername(username);
            Author author = new Author(user.getUsername(), user.getName(), user.getEmail(), user.getSchool(), user.getCountry());
            authors.add(author);
        }
        Detail detail = new Detail(paper.getPaper(), paper.getTitle(), paper.getAbstracts(), paper.getExpertise(), paper.getCommitTime(), paper.getState(), authors);
        Dictionary<String, Object> data = new Hashtable<>();
        data.put("detail", detail);
        return Feedback.info(data);
    }
}
