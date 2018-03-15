package com.robin.bos.web.action.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
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
import org.springframework.ui.Model;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.robin.bos.domain.base.Courier;
import com.robin.bos.domain.base.Standard;
import com.robin.bos.service.base.CourierService;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CourierAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午10:22:39 <br/>       
 */

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class CourierAction extends ActionSupport implements ModelDriven<Courier>{

    private static final long serialVersionUID = -5344671714414993043L;

    private Courier model = new Courier();
    
    @Override
    public Courier getModel() {
        return model;
    }
    
    
    private Integer page;
    private Integer rows;
    public void setPage(Integer page) {
        this.page = page;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    @Autowired
    private CourierService courierSerivce;
    
    
    @Action(value="courier_pageQuery")
    public String pageQuery() throws IOException
    {
        //List<Standard> list = standardService.findAll();
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Courier> page = courierSerivce.findAll(pageable);
        List<Courier> list = page.getContent();
        
        Map<String, Object> map = new HashMap<>();
        map.put("total", page.getNumberOfElements());
        map.put("rows", list);
        
        // 灵活控制输出的内容
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"fixedAreas", "takeTime"});
        
        JSONObject jsonObject = JSONObject.fromObject(map,jsonConfig);
        //JSONObject jsonObject = JSONObject.fromObject(map);
        String json = jsonObject.toString();
        
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(json);
        
        return NONE;
    }
    
    @Action(value="courier_save",
            results={@Result(name=SUCCESS,type="redirect",location="/pages/base/courier.html"),
                     @Result(name=ERROR,type="redirect",location="/pages/base/courier.html")})
    public String save()
    {
        System.out.println(model);
        Courier courier = courierSerivce.save(model);
        if(courier != null)
        {
            return SUCCESS;
        }
        return ERROR;
    }
    
    private String ids;
    
    public void setIds(String ids) {
        this.ids = ids;
    }

    @Action(value="courier_batchDel",
            results={@Result(name=SUCCESS,type="redirect",location="/pages/base/courier.html"),
                     @Result(name=ERROR,type="redirect",location="/pages/base/courier.html")})
    public String batchDel()
    {
        String[] str = ids.split(",");
      
        for (String string : str) {
            System.out.println(string);
            Courier courier = courierSerivce.findById(Long.parseLong(string));
            courier.setDeltag('1');
            courierSerivce.save(courier);
        }
        return SUCCESS;
    }

}
  
