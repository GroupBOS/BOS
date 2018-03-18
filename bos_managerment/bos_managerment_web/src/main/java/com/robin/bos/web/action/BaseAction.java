package com.robin.bos.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.robin.bos.domain.base.Area;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:BaseAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午10:22:26 <br/>       
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

    private T model;
    
    public BaseAction(){
        Class<? extends BaseAction> childClazz = this.getClass();
        
        Type type = childClazz.getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Class<T> clazz = (Class<T>) actualTypeArguments[0];
        try {
            model = (T) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();  
        }
    }
    
    
    @Override
    public T getModel() {
        return model;
    }
    
    protected Integer page;
    protected Integer rows;
    public void setPage(Integer page) {
        this.page = page;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
    
    
    public void page2Json(Page page,JsonConfig jsonConfig) throws IOException
    {
        
        Map<String, Object> map = new HashMap<>();
        
        List list = page.getContent();
        
        map.put("total", page.getTotalElements());
        map.put("rows", list);
        String json;
        if(jsonConfig != null)
        {
            json = JSONObject.fromObject(map,jsonConfig).toString();
        }
        else
        {
            json = JSONObject.fromObject(map).toString();
        }
        
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(json);
    }
    
    public void list2Json(List<T> list,JsonConfig jsonConfig) throws IOException
    {
        String json = "";
        if(jsonConfig != null)
        {
            json = JSONArray.fromObject(list,jsonConfig).toString();
        }
        else
        {
            json = JSONArray.fromObject(list).toString();
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(json);
    }
    
    
    
    

}
  
