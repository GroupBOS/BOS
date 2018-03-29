package com.robin.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.robin.bos.domain.system.Menu;

/**  
 * ClassName:MenuService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午9:30:33 <br/>       
 */
public interface MenuService {

    List<Menu> findLevelOne();

    Menu save(Menu menu);

    Page<Menu> findAll(Pageable pageable);

}
  
