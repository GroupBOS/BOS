package com.robin.bos.web.action.base;

import java.io.IOException;
import java.util.List;
import java.util.Set;

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

import com.robin.bos.domain.base.Customer;
import com.robin.bos.domain.base.FixedArea;
import com.robin.bos.domain.base.SubArea;
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
    
    
    private List<Long> customerIds;
    public void setCustomerIds(List<Long> customerIds) {
        this.customerIds = customerIds;
    }
    
    private List<Long>  areaIds;
    public void setAreaIds(List<Long> areaIds) {
        this.areaIds = areaIds;
    }
    
    
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
    
    
    @Action(value="fixedAreaAction_findUnAssociatedCustomers")
    public String findUnAssociatedCustomers() throws IOException
    {
        List<Customer> customers = fixedAreaService.findUnAssociatedCustomers();
        list2Json(customers, null);
        return NONE;
    }
    
    @Action(value="fixedAreaAction_findAssociatedCustomers")
    public String findAssociatedCustomers() throws IOException
    {
        List<Customer> customers = fixedAreaService.findAssociatedCustomers(getModel().getId());
        list2Json(customers, null);
        return NONE;
    }
    
    @Action(value="fixedAreaAction_assignCustomers2FixedArea",
            results={@Result(name=SUCCESS,type="redirect",location="/pages/base/fixed_area.html"),
                     @Result(name=ERROR,type="redirect",location="/pages/base/fixed_area.html")})
    public String assignCustomers2FixedArea() {
        fixedAreaService.assignCustomers2FixedArea(getModel().getId(),customerIds);
        
        return SUCCESS;
    }
    
    @Action(value="fixedAreaAction_findUnAssociatedSubAreas")
    public String findUnAssociatedSubAreas() throws IOException
    {
        List<SubArea> subAreas = fixedAreaService.findUnAssociatedSubAreas();
        JsonConfig jsonConfig = new JsonConfig();
        //jsonConfig.setExcludes(new String[]{"fixedArea","area"});
        jsonConfig.setExcludes(new String[]{"subareas","couriers"});
        list2Json(subAreas, jsonConfig);
        return NONE;
    }
    
    
    
    @Action(value="fixedAreaAction_findAssociatedSubAreas")
    public String findAssociatedSubAreas() throws IOException
    {
        List<SubArea> subAreas = fixedAreaService.findAssociatedSubAreas(getModel().getId());
        
        JsonConfig jsonConfig = new JsonConfig();
        //jsonConfig.setExcludes(new String[]{"fixedArea","area"});
        jsonConfig.setExcludes(new String[]{"subareas","couriers"});
        list2Json(subAreas, jsonConfig);
        return NONE;
    }
    
    
    @Action(value="AreaAction_assignSubAreas2FixedArea",
            results={@Result(name=SUCCESS,type="redirect",location="/pages/base/fixed_area.html"),
                     @Result(name=ERROR,type="redirect",location="/pages/base/fixed_area.html")})
    public String assignSubAreas2FixedArea() {
        fixedAreaService.assignSubAreas2FixedArea(getModel().getId(),areaIds);
        
        return SUCCESS;
    }
    
    

}
  
