package com.robin.bos.web.action.system;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.robin.bos.domain.system.Menu;
import com.robin.bos.domain.system.Permission;
import com.robin.bos.domain.system.Role;
import com.robin.bos.service.system.MenuService;
import com.robin.bos.service.system.PermissionService;
import com.robin.bos.service.system.RoleService;
import com.robin.bos.web.action.BaseAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:RoleAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午5:00:09 <br/>       
 */
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class RoleAction extends BaseAction<Role> {

    private static final long serialVersionUID = 8988289604021901053L;
    
    private String menuIds;
    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
    
    private String permissionIds;
    public void setPermissionIds(String permissionIds) {
        this.permissionIds = permissionIds;
    }
    
    
    @Autowired
    private RoleService roleService;
    
    
    @Autowired
    private MenuService menuService;
    
    @Autowired
    private PermissionService permissionService;
    
    
    @Action(value="roleAction_pageQuery")
    public String pageQuery() throws IOException
    {
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Role> page = roleService.findAll(pageable);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"users","permissions","menus"});
        page2Json(page, jsonConfig);
        return NONE;
    }
    
    @Action(value="roleAction_save",
            results={@Result(name=SUCCESS,type="redirect",location="/pages/system/role.html"),
                     @Result(name=ERROR,type="redirect",location="/index.html")})
    public String save()
    {
        Set<Menu> menuSet = new HashSet<>();
        Set<Permission> permissionSet = new HashSet<>();
        
        Role role = getModel();
        String[] menus = StringUtils.split(menuIds, ",");
        for (String menuId : menus) {
            Menu menu = menuService.findOne(Long.parseLong(menuId));
            System.out.println("menuId:"+menuId);
            menuSet.add(menu);
        }
        
        //struts获取数组格式分隔符是", ",注意后面带个空格
        String[] permissions = StringUtils.split(permissionIds, ", ");
        for (String permissionId : permissions) {
            Permission permission = permissionService.findOne(Long.parseLong(permissionId));
            System.out.println("permissionId:"+permissionId);
            permissionSet.add(permission);
        }
        
        role.setMenus(menuSet);
        role.setPermissions(permissionSet);
        
        Role roleRet = roleService.save(role);
        if(roleRet != null )
        {
            return SUCCESS;
        }
        return ERROR;
    }
    
    @Action(value="roleAction_findAll")
    public String findAll() throws IOException
    {
        List<Role> list = roleService.findAll();
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"users","permissions","menus"});
        list2Json(list, jsonConfig);
        return NONE;
    }
    
    
    
    
    

}
  
