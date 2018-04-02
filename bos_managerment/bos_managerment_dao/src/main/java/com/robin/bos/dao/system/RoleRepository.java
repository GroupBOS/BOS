package com.robin.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.robin.bos.domain.system.Menu;
import com.robin.bos.domain.system.Permission;
import com.robin.bos.domain.system.Role;

/**  
 * ClassName:RoleRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午5:03:54 <br/>       
 */
public interface RoleRepository extends JpaRepository<Role, Long>,JpaSpecificationExecutor<Role> {

    //@Query("select roles from User u where u.id = ?")
    @Query("select r from Role r inner join r.users u where u.id = ?")
    List<Role> findbyUid(Long uid);
    @Query("select p from Role r join r.permissions p where r.id = ? ")
	List<Permission> findPermissionById(Long id);
    @Query("select m from Role r join r.menus m where r.id = ? ")
	List<Menu> findMemuById(Long id);

}
  
