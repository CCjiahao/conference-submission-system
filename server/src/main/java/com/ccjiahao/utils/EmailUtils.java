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

    public void sendEmail(String toEmail, String content) {
        Thread thread = new Thread(() -> {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setSubject("ECNU论文投稿系统");
                message.setFrom("1662814199@qq.com");
                message.setText(content);
                message.setTo(toEmail);
                javaMailSender.send(message);
            } catch (Exception e) {
                System.out.println(e);
            }
        });
        thread.start();
    }

    public void sendVerificationCode(String toEmail, String code) {
        sendEmail(toEmail, "你的验证码为：" + code + "，十分钟内使用有效。");
    }

    public void sendReviewRemain(String[] toEmails, String username, String title) {
        for (String toEmail : toEmails) {
            sendEmail(toEmail, username + "投递了一篇名为《" + title + "》论文，欢迎你前来进行审阅。审阅地址：http://localhost:8080/review/hall。");
        }
    }

    public void sendRebuttalRemain(String[] toEmails, String[] usernames, String title) {
        for(int i = 0; i < toEmails.length; i++) {
            sendEmail(toEmails[i], usernames[i] + "你好，你作为作者/协作者投递的一篇名为《" + title + "》论文，已经被审稿人审阅，请尽早提交辩驳意见。辩驳地址：http://localhost:8080/submission/my。");
        }
    }

    public void sendReReviewRemain(String toEmail, String username, String title) {
        sendEmail(toEmail, username + "已经就名为《" + title + "》的论文进行辩驳，请及时查阅，如有必要请尽快修改你的审阅意见。查看地址：http://localhost:8080/review/log。");
    }

    public void sendReviewUpdate(String[] toEmails, String[] usernames, String title) {
        for(int i = 0; i < toEmails.length; i++) {
            sendEmail(toEmails[i], usernames[i] + "您好，《" + title + "》论文的审核意见已经更新，请查看。");
        }
    }
    public void sendReviewConfirm(String toEmail, String title) {
        sendEmail(toEmail, "主席您好，《" + title + "》论文已经完成审核，请及时确认是否录取该论文。");
    }

    public void sendAcceptRemain(String[] toEmails, String[] usernames, String title) {
        for(int i = 0; i < toEmails.length; i++) {
            sendEmail(toEmails[i], usernames[i] + "你好，恭喜你作为作者/协作者投递的一篇名为《" + title + "》论文，已经被接收！");
        }
    }

    public void sendRejectRemain(String[] toEmails, String[] usernames, String title) {
        for(int i = 0; i < toEmails.length; i++) {
            sendEmail(toEmails[i], usernames[i] + "你好，你作为作者/协作者投递的一篇名为《" + title + "》论文，已经被拒绝，欢迎再次投稿。");
        }
    }
}
