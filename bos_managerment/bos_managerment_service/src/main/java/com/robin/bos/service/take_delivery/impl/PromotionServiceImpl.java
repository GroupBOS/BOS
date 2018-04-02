package com.robin.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robin.bos.dao.take_delivery.PromotionRepository;
import com.robin.bos.domain.take_delivery.PageBean;
import com.robin.bos.domain.take_delivery.Promotion;
import com.robin.bos.service.take_delivery.PromotionService;

/**  
 * ClassName:PromotionServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年4月1日 下午10:24:11 <br/>       
 */
@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public void save(Promotion promotion) {
        promotionRepository.save(promotion);
    }

    @Override
    public Page<Promotion> findAll(Pageable pageable) {
          
        return promotionRepository.findAll(pageable);
    }

    @Override
    public PageBean<Promotion> findAll4Fore(int page, int pageSize) {
        
        PageBean<Promotion> pageBean = new PageBean<>();
        
        Pageable pageable = new PageRequest(page, pageSize);
        Page<Promotion> p = findAll(pageable);
        
        
        
        pageBean.setList(p.getContent());
        pageBean.setTotal(p.getTotalElements());
        return pageBean;
    }

}
  
