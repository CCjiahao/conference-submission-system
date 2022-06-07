package com.ccjiahao.controller;

import com.ccjiahao.dto.Feedback;
import com.ccjiahao.entity.User;
import com.ccjiahao.entity.VerificationCode;
import com.ccjiahao.mapper.VerificationCodeMapper;
import com.ccjiahao.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Random;

@CrossOrigin(origins = {"*", "null"})
@RestController
public class VerificationCodeController {
    private final VerificationCodeMapper verificationCodeMapper;
    private final EmailUtils emailUtils;
    @Autowired
    public VerificationCodeController(VerificationCodeMapper verificationCodeMapper, EmailUtils emailUtils) {
        this.verificationCodeMapper = verificationCodeMapper;
        this.emailUtils = emailUtils;
    }

    @GetMapping("/api/getVerificationCode")
    public String getVerificationCode(@RequestParam String email){
        verificationCodeMapper.deleteByEmail(email);
        String code = String.valueOf(new Random().nextInt(899999) + 100000);
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setEmail(email);
        verificationCode.setCode(code);
        verificationCode.setExpirationTime(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
        try {
            this.emailUtils.sendVerificationCode(email, code);
            verificationCodeMapper.insert(verificationCode);
            return Feedback.info(null);
        } catch (Exception e) {
            return Feedback.error(e.toString());
        }
    }
}
