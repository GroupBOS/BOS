package com.robin.bos.service.system.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.robin.bos.dao.system.RoleRepository;
import com.robin.bos.dao.system.UserRepository;
import com.robin.bos.domain.system.Role;
import com.robin.bos.domain.system.User;
import com.robin.bos.service.system.UserService;

/**  
 * ClassName:UserServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午9:39:01 <br/>       
 */
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User save(User user, Long[] roleIds) {
        
        Set<Role> roleSet = new HashSet<>();
        
        for (Long roleId : roleIds) {
            Role role = roleRepository.findOne(roleId);
            roleSet.add(role);
        }

        user.setRoles(roleSet);
        return userRepository.save(user);
    }

}
  
