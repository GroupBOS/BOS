package com.robin.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.robin.bos.domain.take_delivery.WorkBill;

/**  
 * ClassName:WorkBillRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午8:20:35 <br/>       
 */
public interface WorkBillRepository extends JpaRepository<WorkBill, Long>,JpaSpecificationExecutor<WorkBill> {

}
  
