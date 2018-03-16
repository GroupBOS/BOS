package com.robin.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.robin.bos.dao.base.SubAreaRepository;
import com.robin.bos.domain.base.SubArea;
import com.robin.bos.service.base.SubAreaService;

/**  
 * ClassName:SubAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午4:31:42 <br/>       
 */
@Component
@Transactional
public class SubAreaServiceImpl implements SubAreaService {

    @Autowired
    private SubAreaRepository subAreaRepository;
    
    
    @Override
    public SubArea save(SubArea model) {
        return subAreaRepository.save(model);
    }

}
  
