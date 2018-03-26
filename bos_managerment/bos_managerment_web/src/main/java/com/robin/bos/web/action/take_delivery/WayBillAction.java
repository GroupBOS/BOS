package com.robin.bos.web.action.take_delivery;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.robin.bos.domain.take_delivery.WayBill;
import com.robin.bos.service.take_delivery.WayBillService;
import com.robin.bos.web.action.BaseAction;

/**  
 * ClassName:WayBillAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午2:41:03 <br/>       
 */
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class WayBillAction extends BaseAction<WayBill> {

    private static final long serialVersionUID = -5854645927398981465L;
    
    
    @Autowired
    private WayBillService wayBillService;
    
    @Action("waybillAction_save")
    public String save() throws IOException
    {
        
        String msg = "0";
        try {
            int i = 1/0;
            wayBillService.save(getModel());
            msg = "1";
            
        } catch (Exception e) {
            e.printStackTrace();  
            msg = "0";
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter writer = response.getWriter();
        writer.write(msg);
        
        return NONE;
    }
    

}
  
