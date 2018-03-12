package com.robin.bos.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.robin.bos.domain.base.Standard;

/**  
 * ClassName:StandardRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午8:33:12 <br/>       
 */
public interface StandardRepository extends JpaRepository<Standard, Long>{

    
    
    List<Standard> findByName(String name);
    
    @Query("from Standard where name=?")
    List<Standard> test_findByName(String name);

}
  
