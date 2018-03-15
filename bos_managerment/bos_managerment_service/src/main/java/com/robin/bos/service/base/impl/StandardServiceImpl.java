package com.robin.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.robin.bos.dao.base.StandardRepository;
import com.robin.bos.domain.base.Standard;
import com.robin.bos.service.base.StandardService;

/**  
 * ClassName:StandardServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午8:03:30 <br/>       
 */
@Component
@Transactional
public class StandardServiceImpl implements StandardService {

    @Autowired
    private StandardRepository standardRepository;
    
    
    @Override
    public Standard save(Standard standard) {
        return standardRepository.save(standard);
    }


    @Override
    public Standard findById(Long id) {
        return standardRepository.findById(id);
    }


    @Override
    public List<Standard> findAll() {
        return standardRepository.findAll();
    }


    @Override
    public Page<Standard> findAll(Pageable page) {
          
        return standardRepository.findAll(page);
    }

}
  
