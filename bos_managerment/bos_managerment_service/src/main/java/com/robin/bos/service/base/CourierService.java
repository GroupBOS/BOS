package com.robin.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.robin.bos.domain.base.Courier;
import com.robin.bos.domain.base.Standard;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午10:32:57 <br/>       
 */
public interface CourierService {
    public Courier save(Courier courier);
    
    public Courier findById(Long id);

    public List<Courier> findAll();
    
    public Page<Courier> findAll(Pageable page);

    public Page<Courier> findAll(Specification<Courier> specification, Pageable pageable);

    public List<Courier> findAvalible();

	public List<Courier> findCourierByfixedArea(Long id);
    
    //还原
    void rest(String ids);
}
  
