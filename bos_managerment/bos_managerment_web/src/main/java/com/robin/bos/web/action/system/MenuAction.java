package com.robin.bos.web.action.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import com.robin.bos.domain.system.User;
import com.robin.bos.service.system.MenuService;
import com.robin.bos.web.action.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

/**  
 * ClassName:MenuAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午9:26:28 <br/>       
 */
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class MenuAction extends BaseAction<Menu> {

    private static final long serialVersionUID = 4332290224359815343L;
    
    @Autowired
    private MenuService menuService;
    
    
    @Action("menuAction_findLevelOne")
    public String findLevelOne() throws IOException
    {
        List<Menu> list = new ArrayList<>();
        list = menuService.findLevelOne();
        
        JsonConfig jsonConfig = new JsonConfig();
        
       /* jsonConfig.registerJsonBeanProcessor(Menu.class, new JsonBeanProcessor() {
            
            @Override
            public JSONObject processBean(Object obj, JsonConfig cfg) {
                Menu menu = (Menu) obj;
                return new JSONObject().
                        element("text", menu.getName()).
                        element("id", menu.getId()).
                        element("children", menu.getChildrenMenus());
            }
        });*/
        jsonConfig.setExcludes(new String[]{"parentMenu","childrenMenus","roles"});
        list2Json(list, jsonConfig);

        return NONE;
    }
    
    @Action(value="menuAction_save",
            results={@Result(name=SUCCESS,type="redirect",location="/pages/system/menu.html"),
                     @Result(name=ERROR,type="redirect",location="/index.html")})
    public String save()
    {
        Menu menu = menuService.save(getModel());
        if(menu != null)
        {
            return SUCCESS;
        }
        return ERROR;
    }
    
    @Action(value="menuAction_pageQuery")
    public String pageQuery() throws IOException
    {
        //由于page这个属性,也是Menu里面的属性之一,模型驱动会优先封装到Menu,导致page无法被属性驱动获取,造成空指针
        Pageable pageable = new PageRequest(Integer.parseInt(getModel().getPage())-1, rows);
        Page<Menu> page = menuService.findAll(pageable);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"parentMenu","childrenMenus","roles"});
        page2Json(page, jsonConfig);
        return NONE;
    }
    
    @Action(value="menuAction_findbyUser")
    public String findbyUser() throws IOException
    {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Menu> list = menuService.findbyUser(user);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"parentMenu","childrenMenus","roles","children"});
        list2Json(list, jsonConfig);
        return NONE;
    }
    
    
    

}
  
