package com.robin.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.robin.bos.domain.base.Standard;


/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午8:01:43 <br/>       
 */
public interface StandardService {
    public Standard save(Standard standard);
    
    public Standard findById(Long id);

    public List<Standard> findAll();
    
    public Page<Standard> findAll(Pageable page);
}
  
