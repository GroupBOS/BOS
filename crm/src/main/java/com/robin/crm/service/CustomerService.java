package com.robin.crm.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.robin.crm.domain.Customer;

/**  
 * ClassName:CrmService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午3:00:42 <br/>       
 */
@Component
@Transactional
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) // 指定参数的数据传输格式
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) // 指定返回值的数据传输格式
public interface CustomerService {
    
    
    @GET
    @Path("/{id}/findById")
    public Customer findById(@PathParam("id")Long id);
    
    @GET
    @Path("/test")
    public void test();
    
    
}
  
