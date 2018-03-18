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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.robin.bos.domain.base.FixedArea;
import com.robin.bos.service.base.FixedAreaService;
import com.robin.bos.web.action.BaseAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:FixedAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午4:32:38 <br/>       
 */

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class FixedAreaAction extends BaseAction<FixedArea> {

    private static final long serialVersionUID = 669794071376309198L;
    
    @Autowired
    FixedAreaService fixedAreaService;
    
    
    @Action(value ="fixedAreaAction_pageQuery")
    public String pageQuery() throws IOException
    {
        Pageable pageable = new PageRequest(page -1, rows);
        Page<FixedArea> pagefixedArea = fixedAreaService.findAll(pageable);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas","couriers"});
        
        page2Json(pagefixedArea, jsonConfig);
        
        return NONE;
    }
    
    @Action(value="fixedAreaAction_save",
            results={@Result(name=SUCCESS,type="redirect",location="/pages/base/fixed_area.html"),
                     @Result(name=ERROR,type="redirect",location="/pages/base/fixed_area.html")})
    public String save() {
        FixedArea fixedArea = fixedAreaService.save(getModel());
        if(fixedArea != null)
        {
            return SUCCESS;
        }
        return ERROR;
    }
    

}
  
