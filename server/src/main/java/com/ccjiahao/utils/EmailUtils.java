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

    public void sendEmail(String toEmail, String content) throws UnsupportedEncodingException, javax.mail.MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("ECNU论文投稿系统");
        message.setFrom("1662814199@qq.com");
        message.setText(content);
        message.setTo(toEmail);
        javaMailSender.send(message);
    }

    public void sendVerificationCode(String toEmail, String code) throws UnsupportedEncodingException, javax.mail.MessagingException {
        sendEmail(toEmail, "你的验证码为：" + code + "，十分钟内使用有效。");
    }

    public void sendReviewRemain(String[] toEmails, String username, String title) throws UnsupportedEncodingException, javax.mail.MessagingException {
        for (String toEmail : toEmails) {
            sendEmail(toEmail, username + "投递了一篇名为《" + title + "》论文，欢迎你前来进行审阅。审阅地址：http://localhost:8080/review/hall。");
        }
    }

    public void sendRebuttalRemain(String[] toEmails, String[] usernames, String title) throws UnsupportedEncodingException, javax.mail.MessagingException {
        for(int i = 0; i < toEmails.length; i++) {
            sendEmail(toEmails[i], usernames[i] + "你好，你作为作者/协作者投递的一篇名为《" + title + "》论文，已经被审稿人审阅，请尽早提交辩驳意见。辩驳地址：http://localhost:8080/submission/my。");
        }
    }

    public void sendReReviewRemain(String toEmail, String username, String title) throws UnsupportedEncodingException, javax.mail.MessagingException {
        sendEmail(toEmail, username + "已经就名为《" + title + "》的论文进行辩驳，请及时查阅，如有必要请尽快修改你的审阅意见。查看地址：http://localhost:8080/review/log。");
    }

    public void sendReviewConfirm(String[] toEmails, String[] usernames, String title) throws UnsupportedEncodingException, javax.mail.MessagingException {
        for(int i = 0; i < toEmails.length; i++) {
            sendEmail(toEmails[i], usernames[i] + "您好，《" + title + "》论文，已经完成最终审核，请尽早登录系统查看论文投递进度。查看地址：http://localhost:8080/。");
        }
    }
}
