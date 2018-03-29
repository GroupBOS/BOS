package com.robin.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.robin.bos.domain.system.Menu;
import com.robin.bos.domain.system.Permission;

/**  
 * ClassName:PermissionService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午4:37:48 <br/>       
 */
public interface PermissionService {

    Page<Permission> findAll(Pageable pageable);

    Permission save(Permission permission);

}
  
