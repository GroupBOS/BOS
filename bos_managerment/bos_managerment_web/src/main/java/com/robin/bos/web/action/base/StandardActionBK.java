package com.robin.bos.web.action.base;
/*
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

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.robin.bos.domain.base.Standard;
import com.robin.bos.service.base.StandardService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

*//**  
 * ClassName:StandardAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午5:57:34 <br/>       
 *//*


@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class StandardActionBK extends ActionSupport implements ModelDriven<Standard> {

    private static final long serialVersionUID = 4102264787823149516L;
    private Standard model = new Standard();
    
    @Override
    public Standard getModel() {
        return this.model;
    }
    
    
    @Autowired
    private StandardService standardService;
    
    @Action(value="standard_save",
            results={@Result(name = SUCCESS,type="redirect",location="/pages/base/standard.html"),
                     @Result(name = ERROR,type="redirect",location="/pages/base/standard.html")})
    public String save()
    {
        Standard standard = standardService.save(model);
        if(standard != null)
        {
            return SUCCESS;
        }
        return ERROR;
    }
    
    
    private Integer page;
    public void setPage(Integer page) {
        this.page = page;
    }
    
    private Integer rows;
    public void setRows(Integer rows) {
        this.rows = rows;
    }

    @Action(value="standard_pageQuery")
    public String pageQuery() throws IOException
    {
        //List<Standard> list = standardService.findAll();
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Standard> page = standardService.findAll(pageable);
        List<Standard> list = page.getContent();
        
        Map<String, Object> map = new HashMap<>();
        map.put("total", page.getNumberOfElements());
        map.put("rows", list);
        
        JSONObject jsonObject = JSONObject.fromObject(map);
        String json = jsonObject.toString();
        
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(json);
        
        return NONE;
    }
    
    @Action(value="standard_findAll")
    public String findAll() throws IOException
    {
        List<Standard> list = standardService.findAll();

        JSONArray jsonArray = JSONArray.fromObject(list);
        String json = jsonArray.toString();

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write(json);
        return NONE;
    }
    

}
  
*/