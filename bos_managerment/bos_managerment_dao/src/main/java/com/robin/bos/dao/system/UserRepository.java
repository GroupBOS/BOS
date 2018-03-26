package com.robin.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.robin.bos.domain.system.User;

/**  
 * ClassName:UserRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午8:36:57 <br/>       
 */
public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User>{

    User findByusername(String username);

}
  
