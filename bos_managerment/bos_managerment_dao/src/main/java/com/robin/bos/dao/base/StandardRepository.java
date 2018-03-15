package com.robin.bos.dao.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.robin.bos.domain.base.Standard;

/**  
 * ClassName:StandardDao <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午8:05:23 <br/>       
 */
public interface StandardRepository extends JpaRepository<Standard, Long> {
    List<Standard> findByName(String name);
    
    @Query("from Standard where name=?")
    List<Standard> test_findByName(String name);
    
    @Override
    Standard save(Standard standard);
    
    Standard findById(Long id);

    @Override
    Page<Standard> findAll(Pageable page);
}
  
