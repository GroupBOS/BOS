package com.robin.bos.service.base.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.robin.bos.dao.base.AreaRepository;
import com.robin.bos.domain.base.Area;
import com.robin.bos.service.base.AreaService;

/**  
 * ClassName:AreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午9:42:28 <br/>       
 */

@Component
@Transactional
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;
    
    @Override
    public void save(List<Area> list) {
        areaRepository.save(list);
    }

    @Override
    public Page<Area> findAll(Pageable pageable) {
        return areaRepository.findAll(pageable);
    }

    @Override
    public List<Area> findByQ(String q) {
        
        q = "%"+q.toUpperCase()+"%";
          
        // TODO Auto-generated method stub  
        return areaRepository.findByQ(q);
    }



}
  
