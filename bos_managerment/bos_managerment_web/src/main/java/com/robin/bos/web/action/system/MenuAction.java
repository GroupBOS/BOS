package com.robin.bos.web.action.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.robin.bos.domain.system.Menu;
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

}
  
