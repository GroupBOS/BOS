package com.robin.bos.web.action.system;

import java.io.IOException;

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
import com.robin.bos.service.system.PermissionService;
import com.robin.bos.web.action.BaseAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:PermissionAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午4:30:39 <br/>       
 */
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class PermissionAction extends BaseAction<Permission> {

    private static final long serialVersionUID = 5304592397775861042L;
    
    @Autowired
    private PermissionService permissionService;
    
    @Action(value="permissionAction_pageQuery")
    public String pageQuery() throws IOException
    {
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Permission> page = permissionService.findAll(pageable);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles"});
        page2Json(page, jsonConfig);
        return NONE;
    }
    
    @Action(value="permissionAction_save",
            results={@Result(name=SUCCESS,type="redirect",location="/pages/system/permission.html"),
                     @Result(name=ERROR,type="redirect",location="/index.html")})
    public String save()
    {
        Permission permission = permissionService.save(getModel());
        if(permission != null)
        {
            return SUCCESS;
        }
        return ERROR;
    }

}
  
