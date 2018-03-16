package com.robin.bos.web.action.base;

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

import com.robin.bos.domain.base.SubArea;
import com.robin.bos.service.base.SubAreaService;
import com.robin.bos.web.action.BaseAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:SubAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午4:28:00 <br/>       
 */
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class SubAreaAction extends BaseAction<SubArea> {
    
    
    @Autowired
    SubAreaService subAreaService;
    
    @Action(value="subareaAction_save",results={@Result(name=SUCCESS,type="redirect",location="/pages/base/sub_area.html"),
                                                @Result(name=ERROR,type="redirect",location="/pages/base/sub_area.html")})
    public String save()
    {
       SubArea subArea = subAreaService.save(getModel());
       if(subArea != null)
       {
           return SUCCESS;
       }
       return ERROR;
    }
    
    @Action(value="subareaAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page-1, rows);
        
        Page<SubArea> page = subAreaService.findAll(pageable);
        
        //JsonConfig jsonConfig = new JsonConfig();
        //jsonConfig.setExcludes(new String[]{"area"});
        
        page2Json(page, null);
        
        return NONE;
    }
    
}
  
