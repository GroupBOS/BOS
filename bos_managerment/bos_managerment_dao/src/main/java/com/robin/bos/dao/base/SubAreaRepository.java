package com.robin.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.robin.bos.domain.base.FixedArea;
import com.robin.bos.domain.base.SubArea;

/**  
 * ClassName:SubAreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午4:34:30 <br/>       
 */
public interface SubAreaRepository extends JpaRepository<SubArea, Long>,JpaSpecificationExecutor<SubArea>{

    List<SubArea> findByFixedAreaIsNull();

    List<SubArea> findByFixedArea(FixedArea fixedArea);

    SubArea findById(Long areaId);
    
    @Query("update SubArea set fixedArea = null where fixedArea.id = ?")
    @Modifying
    void unbindSubAreaByFixedArea(Long fixedAreaId);
    
    
    @Query("update SubArea set fixedArea.id = ?2 where id = ?1")
    @Modifying
    void bindSubArea2FixedArea(Long subAreaId, Long fixedAreaId);

    SubArea findByKeyWords(String keyWord);
     @Query("from SubArea where fixedArea = ?")
    List<SubArea> findSubByfixed(FixedArea fixedArea);

    

}
  
