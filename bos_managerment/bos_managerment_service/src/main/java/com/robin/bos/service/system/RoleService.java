package com.robin.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.robin.bos.domain.system.Role;

/**  
 * ClassName:RoleService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午5:04:39 <br/>       
 */
public interface RoleService {

    Page<Role> findAll(Pageable pageable);

    Role save(Role role);

}
  
