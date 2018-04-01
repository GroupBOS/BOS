package com.robin.bos.service.system.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.robin.bos.dao.system.RoleRepository;
import com.robin.bos.domain.system.Menu;
import com.robin.bos.domain.system.Permission;
import com.robin.bos.domain.system.Role;
import com.robin.bos.service.system.RoleService;

/**  
 * ClassName:RoleServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午5:05:00 <br/>       
 */

@Component
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

	@Override
	public Map<String, Object> findPmsAndMenuById(Long id) {
		List<Permission> permissions = roleRepository.findPermissionById(id);  
        List<Menu> menus = roleRepository.findMemuById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("permissions", permissions);
        map.put("menus", menus);
	        
	        return map;
	
		
	}

}
  
