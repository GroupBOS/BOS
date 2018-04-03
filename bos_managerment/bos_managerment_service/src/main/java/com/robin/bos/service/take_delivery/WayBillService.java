package com.robin.bos.service.take_delivery;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.robin.bos.domain.take_delivery.WayBill;

/**  
 * ClassName:WayBillService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午3:17:32 <br/>       
 */
public interface WayBillService {

    WayBill save(WayBill wayBill);

    void saveWayBill(List<WayBill> list);

    Page<WayBill> findAll(Pageable pageable);

}
  
