package com.robin.bos.service.base.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.robin.bos.dao.base.CourierRepository;
import com.robin.bos.domain.base.Courier;
import com.robin.bos.service.base.CourierService;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午10:34:53 <br/>       
 */

@Component("courierSerivce")
@Transactional
public class CourierServiceImpl implements CourierService {
    
    @Autowired
    private CourierRepository courierRepository;

    @Override
    public Courier save(Courier courier) {
        return courierRepository.save(courier);
    }

    @Override
    public Courier findById(Long id) {
        return courierRepository.findById(id);
    }

    @Override
    public List<Courier> findAll() {
        return courierRepository.findAll();
    }

    @Override
    public Page<Courier> findAll(Pageable page) {
        return courierRepository.findAll(page);
    }

    
    @Override
    public Page<Courier> findAll(Specification<Courier> specification, Pageable pageable) {
        return courierRepository.findAll(specification,pageable);
    }

    @Override
    public List<Courier> findAvalible() {
        
        return courierRepository.findAvalible();
    }

  //还原快递员
  	@Override
  	public void rest(String ids) {
  		//为空判断
  				if(StringUtils.isNotEmpty(ids)) {
  					//切割数据
  					String[] split = ids.split(",");
  					for (String id : split) {
  						courierRepository.updateDelTagByIds(Long.parseLong(id));
  					}
  				}
  	}
    
}
  
