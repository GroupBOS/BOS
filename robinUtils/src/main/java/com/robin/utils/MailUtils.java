package com.robin.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {
    // 发件服务器地址.例如 localhost
    public static final String SMTP_HOST_ADDRESS = "localhost";
    // 发件人邮箱的用户名.例如 username
    public static final String USERNAME = "service";
    // 发件人邮箱的密码.例如 password
    public static final String PASSWORD = "123";
    // 发件人邮箱地址.例如 zhangsan@qq.com
    public static final String SENDER = "service@store.com";

    /**
     * @param receiver : 收件人邮箱地址.例如 lisi@qq.com
     * @param subject : 收件主题
     * @param emailBody : 邮件内容
     */
    public static void sendMail(String receiver, String subject,
            String emailBody) {
        try {
            // 1.创建一个程序与邮件服务器会话对象 Session

            Properties props = new Properties();
            // 设置发送的协议
            props.setProperty("mail.transport.protocol", "SMTP");

            // 设置发送邮件的服务器
            props.setProperty("mail.host", SMTP_HOST_ADDRESS);
            props.setProperty("mail.smtp.auth", "true");// 指定验证为true

            // 创建验证器
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    // 设置发送人的帐号和密码
                    return new PasswordAuthentication(USERNAME, PASSWORD);
                }
            };

            Session session = Session.getInstance(props, auth);

            // 2.创建一个Message，它相当于是邮件内容
            Message message = new MimeMessage(session);

            // 设置发送者
            message.setFrom(new InternetAddress(SENDER));

            // 设置发送方式与接收者
            message.setRecipient(RecipientType.TO,
                    new InternetAddress(receiver));

            // 设置邮件主题
            message.setSubject(subject);

            // 设置邮件内容
            message.setContent(emailBody, "text/html;charset=utf-8");

            // 3.创建 Transport用于将邮件发送
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("邮件发送失败....................");
        }
    }
}
