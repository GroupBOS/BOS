package com.robin.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.robin.crm.domain.Customer;

/**  
 * ClassName:CrmService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午3:00:42 <br/>       
 */

@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) // 指定参数的数据传输格式
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) // 指定返回值的数据传输格式
public interface CustomerService {
    
    
    @GET
    @Path("/{id}/findById")
    public Customer findById(@PathParam("id")Long id);
    
    @GET
    @Path("/test")
    public void test();
    
    @GET
    @Path("/findUnAssociatedCustomers")
    public List<Customer> findUnAssociatedCustomers();
    
    @GET
    @Path("/findAssociatedCustomers")
    public List<Customer> findAssociatedCustomers(@QueryParam("id")Long id);
    
    @PUT
    @Path("/assignCustomers2FixedArea")
    public void assignCustomers2FixedArea(@QueryParam("fixedAreaId")Long fixedAreaId,@QueryParam("customerIds")List<Long> customerIds);
    
    @PUT
    @Path("save")
    public Customer save(Customer customer);
    
    
}
  
