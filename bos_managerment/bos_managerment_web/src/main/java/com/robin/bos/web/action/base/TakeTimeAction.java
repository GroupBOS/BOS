package com.robin.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.robin.bos.domain.base.TakeTime;
import com.robin.bos.service.base.TakeTimeService;
import com.robin.bos.web.action.BaseAction;

/**  
 * ClassName:TakeTimeAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午8:54:47 <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class TakeTimeAction extends BaseAction<TakeTime> {

    private static final long serialVersionUID = 612002218190710587L;
    
    @Autowired
    private TakeTimeService takeTimeService;
    
    
    
    @Action(value="takeTimeAction_listajax")
    public String listajax() throws IOException
    {
        List<TakeTime> list=takeTimeService.findAll();
        list2Json(list, null);
        
        return NONE;
    }

}
  
