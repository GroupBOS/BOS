package com.robin.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.robin.crm.domain.Customer;

/**  
 * ClassName:CustomerRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午8:28:32 <br/>       
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>,JpaSpecificationExecutor<Customer>{

    List<Customer> findByFixedAreaIdIsNull();

    List<Customer> findByFixedAreaId(String id);
    
    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
    @Modifying
    void unbindCustomerByFixedArea(String fixedAreaId);
    
    
    @Query("update Customer set fixedAreaId = ?2 where id = ?1")
    @Modifying
    void bindCustomer2FixedArea(Long customerId, String fixedAreaId);

    
    @Query("update Customer set type = 1 where telephone = ?")
    @Modifying
    void actvive(String telephone);

    @Query("from Customer where telephone = ? and nvl(type,0) = 1")
    Customer isActived(String telephone);

    Customer findByTelephone(String telephone);

    Customer findByTelephoneAndPassword(String telephone, String password);

    @Query("select fixedAreaId from Customer where address = ?")
    String findFixedAreaIdByAddress(String address);

}
  
