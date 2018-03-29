package com.robin.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.robin.bos.dao.system.PermissionRepository;
import com.robin.bos.domain.system.Permission;
import com.robin.bos.service.system.PermissionService;

/**  
 * ClassName:PermissionServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午4:38:13 <br/>       
 */
@Component
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;
    
    
    @Override
    public Page<Permission> findAll(Pageable pageable) {
        return permissionRepository.findAll(pageable);
    }


    @Override
    public Permission save(Permission permission) {
          
        return permissionRepository.save(permission);
    }


    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }


    @Override
    public Permission findOne(Long id) {
        return permissionRepository.findOne(id);
    }

}
  
