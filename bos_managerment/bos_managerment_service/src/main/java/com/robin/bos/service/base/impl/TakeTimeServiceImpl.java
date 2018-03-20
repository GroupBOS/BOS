package com.robin.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.robin.bos.dao.base.TakeTimeRepository;
import com.robin.bos.domain.base.TakeTime;
import com.robin.bos.service.base.TakeTimeService;

/**  
 * ClassName:TakeTimeServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午9:16:54 <br/>       
 */

@Component
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {

    @Autowired
    private TakeTimeRepository takeTimeRepository;
    
    @Override
    public List<TakeTime> findAll() {
        return takeTimeRepository.findAll();
    }
    

}
  
