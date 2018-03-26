package com.robin.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.robin.bos.domain.take_delivery.WayBill;

/**  
 * ClassName:WayBillRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午3:18:59 <br/>       
 */
public interface WayBillRepository extends JpaRepository<WayBill, Long>,JpaSpecificationExecutor<WayBill> {

}
  
