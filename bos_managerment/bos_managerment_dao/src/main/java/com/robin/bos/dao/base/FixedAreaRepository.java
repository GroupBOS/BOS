package com.robin.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import com.robin.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午4:43:40 <br/>       
 */
public interface FixedAreaRepository extends JpaRepository<FixedArea, Long>{

    

    FixedArea findById(Long fixedAreaId);

}
  
