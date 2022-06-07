package com.ccjiahao.utils;

import com.ccjiahao.mapper.VerificationCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import java.io.UnsupportedEncodingException;

@Repository
public class EmailUtils {
    private final JavaMailSender javaMailSender;
    @Autowired
    public EmailUtils(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendVerificationCode(String toEmail, String content) throws UnsupportedEncodingException, javax.mail.MessagingException {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setText("你的验证码为：" + content + "，十分钟内使用有效。");
        message.setSubject("ECNU论文投稿系统");
        message.setTo(toEmail);
        message.setFrom("1662814199@qq.com");
        javaMailSender.send(message);
    }
}
