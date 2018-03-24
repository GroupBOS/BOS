package com.robin.bos.dao.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.robin.bos.domain.base.Area;

/**  
 * ClassName:AreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午9:43:37 <br/>       
 */
public interface AreaRepository extends JpaRepository<Area , Long>,JpaSpecificationExecutor<Area>{

    
    @Query("from Area where province like ?1 or city like ?1 or district like ?1 or citycode like ?1 or shortcode like ?1")
    List<Area> findByQ(String q);

    Area findByProvinceAndCityAndDistrict(String province,String city,String district);

    
}
  
