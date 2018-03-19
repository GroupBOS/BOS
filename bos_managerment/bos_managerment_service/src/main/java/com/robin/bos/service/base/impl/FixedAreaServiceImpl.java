package com.robin.bos.service.base.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.robin.bos.dao.base.FixedAreaRepository;
import com.robin.bos.dao.base.SubAreaRepository;
import com.robin.bos.domain.base.Customer;
import com.robin.bos.domain.base.FixedArea;
import com.robin.bos.domain.base.SubArea;
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
    
    @Autowired
    private SubAreaRepository subAreaRepository;
    
    @Override
    public Page<FixedArea> findAll(Pageable pageable) {
        return fixedAreaRepository.findAll(pageable);
    }

    @Override
    public FixedArea save(FixedArea fixedArea) {
        return fixedAreaRepository.save(fixedArea);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Customer> findUnAssociatedCustomers() {
        List<Customer> customers = (List<Customer>) WebClient.
                create("http://localhost:8010/crm/crm/CustomerService/findUnAssociatedCustomers").
                type(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).
                getCollection(Customer.class);
        return customers;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Customer> findAssociatedCustomers(Long customerFixedAreaId) {
        List<Customer> customers = (List<Customer>) WebClient.
                create("http://localhost:8010/crm/crm/CustomerService/findAssociatedCustomers").
                type(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).
                query("id", customerFixedAreaId).
                getCollection(Customer.class);
        return customers;
    }

    @Override
    public void assignCustomers2FixedArea(Long fixedAreaId, List<Long> customerIds) {
        if(customerIds != null)
        {
            WebClient.create("http://localhost:8010/crm/crm/CustomerService/assignCustomers2FixedArea").
            type(MediaType.APPLICATION_JSON).
            accept(MediaType.APPLICATION_JSON).
            query("fixedAreaId", fixedAreaId).
            query("customerIds", customerIds).put(null);
        }else
        {
            WebClient.create("http://localhost:8010/crm/crm/CustomerService/assignCustomers2FixedArea").
            type(MediaType.APPLICATION_JSON).
            accept(MediaType.APPLICATION_JSON).
            query("fixedAreaId", fixedAreaId).
            put(null);
        }
        
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SubArea> findUnAssociatedSubAreas() {
        return subAreaRepository.findByFixedAreaIsNull();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SubArea> findAssociatedSubAreas(Long id) {
        FixedArea fixedArea = new FixedArea();
        fixedArea.setId(id);
        return subAreaRepository.findByFixedArea(fixedArea);
    }

    
    @SuppressWarnings("unused")
    public void assignSubAreas2FixedArea(Long fixedAreaId, List<Long> subAreaIds) {
        
        System.out.println("fixedAreaId:"+fixedAreaId);
        FixedArea fixedArea = fixedAreaRepository.findById(fixedAreaId);
        if(subAreaIds != null)
        {
            List<SubArea> subAreas_old = subAreaRepository.findByFixedArea(fixedArea);
            if(subAreaIds != null)
            {
                for (SubArea subArea : subAreas_old) {
                    subArea.setFixedArea(null);
                    subAreaRepository.saveAndFlush(subArea);
                }
            }
            
            
            
            System.out.println("Fuck not null");
            Set<SubArea> subAreas = new HashSet<>();
            System.out.println("fix:"+fixedArea);
            for (Long areaId : subAreaIds) {
                SubArea subArea = subAreaRepository.findById(areaId);
                subArea.setFixedArea(fixedArea);
                subAreaRepository.saveAndFlush(subArea);
                subAreas.add(subArea);
                System.out.println(areaId+":"+subArea);
            }
            
            fixedArea.setSubareas(subAreas);
            fixedAreaRepository.saveAndFlush(fixedArea);
            
        }else
        {
            System.out.println("Fuck null");
            
            
            List<SubArea> subAreas = subAreaRepository.findByFixedArea(fixedArea);
            if(subAreaIds != null)
            {
                for (SubArea subArea : subAreas) {
                    subArea.setFixedArea(null);
                    subAreaRepository.saveAndFlush(subArea);
                }
            }
            
            fixedArea.setSubareas(null);
            fixedAreaRepository.saveAndFlush(fixedArea);
        }
        
    }

}
  
