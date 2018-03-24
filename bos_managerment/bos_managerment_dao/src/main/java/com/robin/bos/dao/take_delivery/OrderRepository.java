package com.robin.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.robin.bos.domain.take_delivery.Order;

/**  
 * ClassName:OrderRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午4:53:02 <br/>       
 */
public interface OrderRepository extends JpaRepository<Order, Long>,JpaSpecificationExecutor<Order> {

}
  
