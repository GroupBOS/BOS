package com.robin.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.robin.bos.domain.base.SubArea;

/**  
 * ClassName:SubAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午4:31:15 <br/>       
 */
public interface SubAreaService {

    SubArea save(SubArea model);

    Page<SubArea> findAll(Pageable pageable);

    List<SubArea> findSubByfixed(Long id);

    List<Object[]> subAreaChart();
}
  
