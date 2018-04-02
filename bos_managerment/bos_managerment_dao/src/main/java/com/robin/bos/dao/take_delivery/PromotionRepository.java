package com.robin.bos.dao.take_delivery;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.robin.bos.domain.take_delivery.Promotion;

/**  
 * ClassName:PromotionRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年4月1日 下午10:25:16 <br/>       
 */
public interface PromotionRepository extends JpaRepository<Promotion, Long>{
    @Modifying
    @Query("update Promotion set status = 2 where endDate = ?")
    void appendEndDate(Date endDate);

}
  
