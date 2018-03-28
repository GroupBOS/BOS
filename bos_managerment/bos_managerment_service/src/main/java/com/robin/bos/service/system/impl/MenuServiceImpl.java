package com.robin.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.robin.bos.dao.system.MenuRepository;
import com.robin.bos.domain.system.Menu;
import com.robin.bos.service.system.MenuService;

/**  
 * ClassName:MenuServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午9:31:03 <br/>       
 */
@Component
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;
    
    @Override
    public List<Menu> findLevelOne() {
        return menuRepository.findByParentMenuIsNull();
    }

}
  
