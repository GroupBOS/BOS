package com.robin.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.robin.bos.domain.system.User;

/**  
 * ClassName:UserService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午9:38:20 <br/>       
 */
public interface UserService {

    Page<User> findAll(Pageable pageable);

    User save(User model, Long[] roleIds);

}
  
