package com.robin.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.robin.bos.domain.base.Area;

/**  
 * ClassName:AreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午9:43:37 <br/>       
 */
public interface AreaRepository extends JpaRepository<Area , Long>,JpaSpecificationExecutor<Area>{

    
}
  
