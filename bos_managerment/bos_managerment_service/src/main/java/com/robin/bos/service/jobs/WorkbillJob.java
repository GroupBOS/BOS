package com.robin.bos.service.jobs;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robin.bos.dao.take_delivery.WorkBillRepository;
import com.robin.bos.domain.take_delivery.WorkBill;

/**  
 * ClassName:WorkbillJob <br/>  
 * Function:  <br/>  
 * Date:     2018年3月30日 下午4:33:19 <br/>       
 */
@Component
public class WorkbillJob {

    @Autowired
    private WorkBillRepository workBillRepository;
    
    public void sendMail()
    {
        
        List<WorkBill> workBills = workBillRepository.findAll();
        
        String emailBody = "编号\t快递员\t取件状态\t时间<br/>";

        for (WorkBill workBill : workBills) {
            emailBody += workBill.getId() + "\t"
                    + workBill.getCourier().getName() + "\t"
                    + workBill.getPickstate() + "\t"
                    + workBill.getBuildtime().toLocaleString() + "<br/>";
        }

        System.out.println("模拟发送工单通知邮件:"+emailBody+"  "+new Date());
    }
}
  
