package com.zheyue.web.mail.executor;

import com.zheyue.web.mail.entity.SimpleMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by fd on 2016/9/14.
 */

@Service
public class MailService {


    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private JavaMailSender javaMailSender;

    public SimpleMail prepareMail() {
        return new SimpleMail();
    }

    public void accountVerify(String toAddress) {
        try {
            SimpleMail mail = prepareMail();
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setFrom("614173971@qq.com");//发件人
            helper.setTo(InternetAddress.parse(mail.getToAddress()));//收件人
            helper.setReplyTo("@sina.com");//回复到
            helper.setSubject("");//邮件主题
//            helper.setText(mail.getContent(), true);//true表示设定html格式
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    //同步发送邮件
    public void syncSendMail(final MimeMessage mimeMessage) {
        javaMailSender.send(mimeMessage);
    }
    //异步发送邮件
    public void asyncSendMail(final MimeMessage mimeMessage) {
        taskExecutor.execute(new Runnable() {
            public void run() {
                syncSendMail(mimeMessage);
            }
        });
    }
}
