package com.robin.activemq;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.robin.utils.SMSUtils;

/**  
 * ClassName:SMSConsumer <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午7:58:20 <br/>       
 */
@Component
public class SMSConsumer implements MessageListener {

    @Override
    public void onMessage(Message msg) {

        MapMessage message = (MapMessage) msg;
        try {
            String tel = message.getString("tel");
            String code = message.getString("code");

            SendSmsResponse smsResponse = SMSUtils.sendSms(tel, code);
            System.out.println("短信接口返回的数据----------------");
            System.out.println("Code=" + smsResponse.getCode());
            System.out.println("Message=" + smsResponse.getMessage());
            System.out.println("RequestId=" + smsResponse.getRequestId());
            System.out.println("BizId=" + smsResponse.getBizId());

         } catch (JMSException | IOException |ClientException e) {
                e.printStackTrace();  
         }
    }
}
  
