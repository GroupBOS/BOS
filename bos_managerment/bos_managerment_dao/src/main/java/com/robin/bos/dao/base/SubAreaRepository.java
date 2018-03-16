package com.robin.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.robin.bos.domain.base.SubArea;

/**  
 * ClassName:SubAreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午4:34:30 <br/>       
 */
public interface SubAreaRepository extends JpaRepository<SubArea, Long>,JpaSpecificationExecutor<SubArea>{

}
  
