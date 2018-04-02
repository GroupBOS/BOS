package com.robin.bos.service.jobs;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.robin.bos.dao.take_delivery.PromotionRepository;
import com.robin.bos.domain.take_delivery.Promotion;

/**  
 * ClassName:PromotionJob <br/>  
 * Function:  <br/>  
 * Date:     2018年4月2日 上午9:15:23 <br/>       
 */
@Component
@Transactional
public class PromotionJob {

    @Autowired
    private PromotionRepository promotionRepository;
    
    public void checkPromotion(){
        List<Promotion> list = promotionRepository.findAll();
        System.out.println("发起了一次检查任务");
        for (Promotion promotion : list) {
           Date endDate = promotion.getEndDate();
           if(endDate.before(new Date())){
               promotionRepository.appendEndDate(endDate);
               System.out.println(promotion.getTitle()+"已结束!");
           }
           
        }
    }
    
}
  
