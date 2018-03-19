package com.robin.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.robin.bos.domain.base.Customer;
import com.robin.bos.domain.base.FixedArea;
import com.robin.bos.domain.base.SubArea;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午4:35:55 <br/>       
 */
public interface FixedAreaService {
    public Page<FixedArea> findAll(Pageable pageable);

    public FixedArea save(FixedArea fixedArea);

    public List<Customer> findUnAssociatedCustomers();

    public List<Customer> findAssociatedCustomers(Long customerFixedAreaId);

    public void assignCustomers2FixedArea(Long fixedAreaId, List<Long> customerIds);

    public List<SubArea> findUnAssociatedSubAreas();

    public List<SubArea> findAssociatedSubAreas(Long id);

    public void assignSubAreas2FixedArea(Long id, List<Long> customerIds);
}
  
