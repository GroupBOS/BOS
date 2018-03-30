package com.robin.bos.service.realms;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robin.bos.dao.system.PermissionRepository;
import com.robin.bos.dao.system.RoleRepository;
import com.robin.bos.dao.system.UserRepository;
import com.robin.bos.domain.system.Permission;
import com.robin.bos.domain.system.Role;
import com.robin.bos.domain.system.User;

/**  
 * ClassName:UserRealm <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午8:22:43 <br/>       
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PermissionRepository permissionRepository;
    
    
    //完成授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //所有请求都会走进这里,判断是否需要授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //info.addStringPermission("courierAction_pageQuery");

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        
        if(user == null){
            System.out.println("未登录!");
            return null;
        }
        //1.内置超级管理员,获取到所有权限
        if (user.getUsername().equals("admin")) {
            //1.1 添加角色
            List<Role> roles = roleRepository.findAll();
            for (Role role : roles) {
                info.addRole(role.getKeyword());
            }
            
            //1.2 添加权限
            List<Permission> permissions = permissionRepository.findAll();
            for (Permission permission : permissions) {
                info.addStringPermission(permission.getKeyword());
            }
            return info;
        }
        else{
        //2.其他用户,根据uid,查询role以及permission
            System.out.println("user id:"+user.getId());
            System.out.println("user name:"+user.getUsername());
            //2.1 添加角色
            List<Role> roles = roleRepository.findbyUid(user.getId());
            for (Role role : roles) {
                System.out.println("role name:"+role.getName());
                info.addRole(role.getKeyword());
            }
            //2.2 添加权限
            List<Permission> permissions = permissionRepository.findbyUid(user.getId());
            for (Permission permission : permissions) {
                System.out.println("permission name:"+permission.getName());
                info.addStringPermission(permission.getKeyword());
            }
            return info;
        }
    }

    
    //完成认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        
        String username = userToken.getUsername();

        User user = userRepository.findByusername(username);
        if(user != null)
        {
            /*
             * @param principal   the 'primary' principal associated with the specified realm.
             * @param credentials the credentials that verify the given principal.
             * @param realmName   the realm from where the principal and credentials were acquired.
             * */
            //交给authenticationInfo去校验,它自己会去抛异常
            AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            return info;
        }
        //无法找到user,抛异常
        return null;
    }

}
  
