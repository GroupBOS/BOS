package com.robin.bos.fore.web.action;

import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.robin.bos.domain.base.Area;
import com.robin.bos.domain.take_delivery.Order;



/**  
 * ClassName:OrderAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午2:27:20 <br/>       
 */
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class OrderAction extends ActionSupport implements ModelDriven<Order>{

    private Order model = new Order();
    
    @Override
    public Order getModel() {
        return model;
    }
    
    
    private String recAreaInfo;
    private String sendAreaInfo;
    
    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }
    
    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }
    
    
    @Action(value="orderAction_add",results={@Result(name=SUCCESS,type="redirect",location="/index.html"),
            @Result(name=ERROR,type="redirect",location="/index.html")})
    public String add()
    {
        String[] sendAreaArray = sendAreaInfo.split("/");
        String[] recAreaArray = recAreaInfo.split("/");
        //TODO:前端要做表单检查,后端如果检查到sendAreaInfo小于2,就直接退出,以防有人直接在地址栏输入参数
        if(sendAreaArray.length == 3)
        {
            String province = sendAreaArray[0];
            String city = sendAreaArray[1];
            String district = sendAreaArray[2];

            province = province.substring(0, province.length()-1);
            city = city.substring(0, city.length()-1);
            district = district.substring(0, district.length()-1);
            
            Area sendArea = new Area();
            sendArea.setProvince(province);
            sendArea.setCity(city);
            sendArea.setDistrict(district);
            model.setSendArea(sendArea);
        }
        
        if(recAreaArray.length == 3)
        {
            String province = recAreaArray[0];
            String city = recAreaArray[1];
            String district = recAreaArray[2];

            province = province.substring(0, province.length()-1);
            city = city.substring(0, city.length()-1);
            district = district.substring(0, district.length()-1);
            
            Area recArea = new Area();
            recArea.setProvince(province);
            recArea.setCity(city);
            recArea.setDistrict(district);
            model.setRecArea(recArea);
        }
        
        
        WebClient.create("http://localhost:8080/bos_managerment_web/webService/orderService/saveOrder").
        type(MediaType.APPLICATION_JSON).
        accept(MediaType.APPLICATION_JSON).
        post(model);
        
        return SUCCESS;
    }
    
    

}
  
