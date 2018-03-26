package com.robin.bos.service.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robin.bos.dao.system.UserRepository;
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
    
    
    //完成授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //所有请求都会走进这里,判断是否需要授权
        //这里暂时写死了,所有都会授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("courierAction_pageQuery");

        return info;
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
  
