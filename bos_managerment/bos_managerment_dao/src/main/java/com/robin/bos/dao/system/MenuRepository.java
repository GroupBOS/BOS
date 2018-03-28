package com.robin.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.robin.bos.domain.system.Menu;

/**  
 * ClassName:MenuRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午9:34:54 <br/>       
 */
public interface MenuRepository extends JpaRepository<Menu,Long>,JpaSpecificationExecutor<Menu>{

    List<Menu> findByParentMenuIsNull();

}
  