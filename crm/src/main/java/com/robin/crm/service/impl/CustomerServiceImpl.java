package com.robin.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.robin.crm.dao.CustomerRepository;
import com.robin.crm.domain.Customer;
import com.robin.crm.service.CustomerService;

/**  
 * ClassName:CrmServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午3:10:12 <br/>       
 */
@Component
@Transactional
public class CustomerServiceImpl implements CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
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

    @Override
    public List<Customer> findUnAssociatedCustomers() {
        return customerRepository.findByFixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findAssociatedCustomers(Long id) {
          
        return customerRepository.findByFixedAreaId(id.toString());
    }

    @Override
    public void assignCustomers2FixedArea(Long fixedAreaId, List<Long> customerIds) {
        customerRepository.unbindCustomerByFixedArea(fixedAreaId.toString());
    
        for (Long customerId : customerIds) {
            customerRepository.bindCustomer2FixedArea(customerId, fixedAreaId.toString());
        }
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer); 
    }

    @Override
    public void active(String telephone) {
        customerRepository.actvive(telephone);
    }

    @Override
    public Customer findByTelephone(String telephone) {
        return customerRepository.findByTelephone(telephone);
    }

    @Override
    public Customer findByTelephoneAndPassword(String telephone, String password) {
        return customerRepository.findByTelephoneAndPassword(telephone,password);
    }


}
  
