package com.robin.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.robin.bos.domain.base.TakeTime;

/**  
 * ClassName:TakeTimeRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午9:18:26 <br/>       
 */
public interface TakeTimeRepository extends JpaRepository<TakeTime , Long>,JpaSpecificationExecutor<TakeTime>{

    TakeTime findById(Long takeTimeId);
}

  
