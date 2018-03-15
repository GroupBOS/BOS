package com.robin.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import com.robin.bos.domain.base.Courier;

/**  
 * ClassName:CourierRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午10:35:35 <br/>       
 */
public interface CourierRepository extends JpaRepository<Courier, Long> {

    Courier findById(Long id);

}
  
