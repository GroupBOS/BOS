package com.robin.crm.service.impl;

import com.robin.crm.domain.Customer;
import com.robin.crm.service.CustomerService;

/**  
 * ClassName:CrmServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午3:10:12 <br/>       
 */
public class CustomerServiceImpl implements CustomerService {

    @Override
    public Customer findById(Long id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setUsername("zs");
        System.out.println("This is findById...");
        return customer;
    }

    @Override
    public void test() {
       System.out.println("This is Test");
    }

}
  
