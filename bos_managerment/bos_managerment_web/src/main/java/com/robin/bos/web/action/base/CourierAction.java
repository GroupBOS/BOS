package com.robin.bos.web.action.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.robin.bos.domain.base.Courier;
import com.robin.bos.domain.base.Standard;
import com.robin.bos.service.base.CourierService;
import com.robin.bos.web.action.BaseAction;

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
public class CourierAction extends BaseAction<Courier>{

    private static final long serialVersionUID = -5344671714414993043L;

    //@Autowired
    private CourierService courierSerivce;
    public void setCourierSerivce(CourierService courierSerivce) {
        this.courierSerivce = courierSerivce;
    }
    
    
    @Action(value="courier_pageQuery")
    public String pageQuery() throws IOException
    {
   //   {"courierNum":"1","standard.name":"2","company":"3","type":"4"}
        
        //Specification接口:
        //我们将复杂条件Specification传给spring-data-jpa的时候,只要实现Specification接口的toPredicate方法就好
        Specification<Courier> specification = new Specification<Courier>() {

            @Override
            //root:根对象,简单的理解为泛型的对象(就是要从那个对象里面进行复杂查询)
            //criteriaQuery:就是QBC中的criteria查询对象
            //cb:查询条件构造器(and or like...)
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> criteriaQuery,
                    CriteriaBuilder cb) {
                
                String courierNum = getModel().getCourierNum();
                Standard standard = getModel().getStandard();
                String company = getModel().getCompany();
                String type = getModel().getType();
                
                List<Predicate> list = new ArrayList<>();
                
                if(StringUtils.isNotEmpty(courierNum))
                {
                    //root为Courier对象
                    //查询条件为:Courier的"courierNum"字段 == courierNum
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p1);
                }
                if(standard != null)
                {
                    String name = standard.getName();
                    if(StringUtils.isNotEmpty(name))
                    {
                        //root为Courier对象
                        //但是要查询的standard.name是Standard对象里的
                        //所以构建一个连接查询:Join<Object, Object> join = root.join("standard")
                        //<Z> the source type of the join
                        //<X> the target type of the join
                        Join<Courier, Standard> join = root.join("standard");
                        
                        //查询条件为:standard的"name"字段 == name
                        Predicate p2 = cb.equal(join.get("name").as(String.class),name);
                        list.add(p2);
                    }
                }
                if(StringUtils.isNotEmpty(company))
                {
                    //查询条件:courier的"company"字段  模糊查询  company
                    Predicate p3 = cb.like(root.get("company").as(String.class), "%"+company+"%");
                    list.add(p3);
                }
                if(StringUtils.isNotEmpty(type))
                {
                    //查询条件:courier的"type"字段 == type
                    Predicate p4 = cb.equal(root.get("type").as(String.class), type);
                    list.add(p4);
                }
                
                //如果没有值,说明没有复杂查询条件
                if(list.size() == 0){
                    return null;
                }
                
                Predicate[] arr = new Predicate[list.size()];
                list.toArray(arr);
                // 用户输入了多少个条件,就让多少个条件同时都满足
                Predicate predicate = cb.and(arr);
                
                
                return predicate;
            }};
        
        //List<Standard> list = standardService.findAll();
        Pageable pageable = new PageRequest(page-1, rows);
        
        Page<Courier> page = courierSerivce.findAll(specification,pageable);

        // 灵活控制输出的内容
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"fixedAreas", "takeTime"});
        
        
        page2Json(page, jsonConfig);
        
        return NONE;
    }
    
    @Action(value="courier_save",
            results={@Result(name=SUCCESS,type="redirect",location="/pages/base/courier.html"),
                     @Result(name=ERROR,type="redirect",location="/pages/base/courier.html")})
    public String save()
    {
        Courier courier = courierSerivce.save(getModel());
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

    
    @RequiresPermissions(value="courier_batchDel")
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
    
    
    @Action(value="courierAction_findAvalible")
    public String listajax() throws IOException
    {
        List<Courier> list = courierSerivce.findAvalible();

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"fixedAreas","takeTime"});
        list2Json(list, jsonConfig);
        return NONE;
    }
    
    
  //还原快递员
  	@Action(value="courierAction_rest" ,results = {
  			@Result(name = "success", location = "/pages/base/courier.html", type = "redirect") })
  	public String rest() {
  		courierService.rest(ids);
  		return SUCCESS;
  	}
    

}
  
