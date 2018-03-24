package com.robin.bos.service.take_delivery;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.robin.bos.domain.take_delivery.Order;

/**  
 * ClassName:OrderService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午4:43:04 <br/>       
 */
public interface OrderService {

    @POST
    @Path("/saveOrder")
    public void saveOrder(Order order);
    
    
}
  
