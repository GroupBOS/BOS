package com.robin.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.robin.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午4:35:55 <br/>       
 */
public interface FixedAreaService {
    public Page<FixedArea> findAll(Pageable pageable);

    public FixedArea save(FixedArea fixedArea);
}
  
