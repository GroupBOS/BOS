package com.robin.bos.fore.web.action;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.robin.crm.domain.Customer;
import com.robin.utils.SMSUtils;

/**  
 * ClassName:CustomerAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月20日 下午4:30:08 <br/>       
 */


@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {

    private static final long serialVersionUID = 647405746675749889L;
    
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    
    private String confirm_password;
    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }
    
    
    private Customer model = new Customer();
    
    @Override
    public Customer getModel() {
        return model;
    }
    
    
    @Action(value="customerAction_regist",results={@Result(name=SUCCESS,type="redirect",location="/signup-success.html"),
                                                   @Result(name=ERROR,type="redirect",location="/signup-fail.html")})
    public String regist()
    {
        System.out.println(model);
        System.out.println(checkcode);
        //1.判断验证码是否一致
        //TODO:实际上可能需要以key-value(telephone:code)保存在数据库中
        HttpSession session = ServletActionContext.getRequest().getSession();
        String code = (String) session.getAttribute("code");
        if(code.equals(checkcode))
        {
            Response response = WebClient.create("http://localhost:8010/crm/crm/CustomerService/save").
            type(MediaType.APPLICATION_JSON).
            accept(MediaType.APPLICATION_JSON).
            put(model);
            
            //TODO:调用WebService方法,拿到返回值作为判断是否成功执行的依据
            /*Customer customer = (Customer) response.getEntity();
            System.out.println(customer);*/
            return SUCCESS;
        }
        return ERROR;
    }
    
    @Action("customerAction_sendSMS")
    public String sendSMS() throws ClientException{
        //生成验证码
        String code = RandomStringUtils.randomNumeric(6);
        System.out.println(code);
        
        //保存在session中
        //TODO:实际上可能需要以key-value(telephone:code)保存在数据库中
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.setAttribute("code",code);
        
        //获取手机号
        //发送验证码
        String telePhone = model.getTelephone();
        System.out.println("telephone:"+telePhone);
        
        SendSmsResponse smsResponse = SMSUtils.sendSms(telePhone, code);
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + smsResponse.getCode());
        System.out.println("Message=" + smsResponse.getMessage());
        System.out.println("RequestId=" + smsResponse.getRequestId());
        System.out.println("BizId=" + smsResponse.getBizId());
        return NONE;
    }




    

}
  
