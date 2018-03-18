package com.robin.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.robin.bos.dao.base.FixedAreaRepository;
import com.robin.bos.domain.base.FixedArea;
import com.robin.bos.service.base.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午4:42:54 <br/>       
 */
@Component
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    
    @Override
    public Page<FixedArea> findAll(Pageable pageable) {
        return fixedAreaRepository.findAll(pageable);
    }

    @Override
    public FixedArea save(FixedArea fixedArea) {
        return fixedAreaRepository.save(fixedArea);
    }

}
  
