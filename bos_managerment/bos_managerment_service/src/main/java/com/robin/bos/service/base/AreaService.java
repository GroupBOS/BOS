package com.robin.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.robin.bos.domain.base.Area;

/**  
 * ClassName:AreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午9:41:28 <br/>       
 */
public interface AreaService {
    public void save(List<Area> list);
    
    public Page<Area> findAll(Pageable pageable);

}
  
