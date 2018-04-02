package com.robin.activemq;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

@Component
public class MailConsumer implements MessageListener{

	@Override
	public void onMessage(Message mail) {
		
	MapMessage message=(MapMessage) mail;
		
		try {
			String email = message.getString("mail");
			String title = message.getString("title");
			String emailBody = message.getString("emailBody");
			System.out.println(email+title+emailBody);
			//发送邮件
			MailUtils.sendMail(getModel().getEmail(),subject, emailBody);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
