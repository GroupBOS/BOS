package com.robin.bos.service.take_delivery.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.robin.bos.dao.take_delivery.WayBillRepository;
import com.robin.bos.domain.take_delivery.WayBill;
import com.robin.bos.service.take_delivery.WayBillService;

/**  
 * ClassName:WayBillServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午3:17:50 <br/>       
 */
@Component
@Transactional
public class WayBillServiceImpl implements WayBillService {

    @Autowired
    private WayBillRepository wayBillRepository;
    
    
    @Override
    public WayBill save(WayBill wayBill) {
        return wayBillRepository.save(wayBill);
    }


    @Override
    public void saveWayBill(List<WayBill> list) {
          
      wayBillRepository.save(list);
        
    }
    @Override
    public Page<WayBill> findAll(Pageable pageable) {
          
   
        return wayBillRepository.findAll(pageable);
    }


}
  
