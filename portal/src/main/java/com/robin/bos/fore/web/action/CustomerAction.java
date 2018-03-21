package com.robin.bos.fore.web.action;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.robin.crm.domain.Customer;
import com.robin.utils.MailUtils;
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
    
    private String activeCode;
    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }
    
    
    private Customer model = new Customer();
    
    @Override
    public Customer getModel() {
        return model;
    }
    
    
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    
    
    
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
            //2.将该用户保存到数据库中
            Response response = WebClient.create("http://localhost:8010/crm/crm/CustomerService/save").
            type(MediaType.APPLICATION_JSON).
            accept(MediaType.APPLICATION_JSON).
            put(model);
            
            //TODO:调用WebService方法,拿到返回值作为判断是否成功执行的依据
            /*Customer customer = (Customer) response.getEntity();
            System.out.println(customer);*/
            
            
            //3.生成邮件激活码,并保存到redis中
            String activeCode = RandomStringUtils.randomNumeric(32);
            System.out.println(activeCode);
            redisTemplate.opsForValue().set(getModel().getTelephone(), activeCode,1,TimeUnit.DAYS);
            
            //4.生成激活链接
            String activeURL = 
                    "http://localhost:8020/portal/customerAction_active.action?activeCode="
                    +activeCode
                    +"&telephone="+getModel().getTelephone();
            String emailBody = "感谢您注册本网站的帐号，请在24小时之内点击<a href='"+activeURL+"'>激活链接</a>来激活账号";
            
            String subject = "激活邮件";
            //5.发送邮件
            MailUtils.sendMail(getModel().getEmail(),subject, emailBody);
            
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
    
    @Action(value = "customerAction_active",
            results = {
                    @Result(name = "success", location = "/login.html",
                            type = "redirect"),
                    @Result(name = "error", location = "/signup-fail.html",
                            type = "redirect")})
    public String active()
    {
        //1.获取activeCode,telephone
        //2.向redis中取数据,对比activeCode是否一致
        String serverActiveCode = redisTemplate.opsForValue().get(getModel().getTelephone());
        if(StringUtils.isNotEmpty(serverActiveCode) && serverActiveCode.equals(activeCode))
        {
            //3.若一致,则将将Customer的type字段设为1,并跳转登录页面
            WebClient.create("http://localhost:8010/crm/crm/CustomerService/active").
            type(MediaType.APPLICATION_JSON).
            accept(MediaType.APPLICATION_JSON).
            query("telephone", getModel().getTelephone()).put(null);
            return SUCCESS;
        }
        return ERROR;
    }
    
    
    
    @Action(value = "customerAction_login",
            results = {
                    @Result(name = "success", location = "/index.html",
                            type = "redirect"),
                    @Result(name = "error", location = "/login.html",
                            type = "redirect"),
                    @Result(name = "unactived", location = "/login.html",
                            type = "redirect")})
    public String login()
    {
        //获取手机号和密码
        
        //判断是否激活,如果已激活,将Customer保存在session中
        if(StringUtils.isNotEmpty(getModel().getTelephone()))
        {
            Customer customer = WebClient.create("http://localhost:8010/crm/crm/CustomerService/findByTelephone"). 
            type(MediaType.APPLICATION_JSON).
            accept(MediaType.APPLICATION_JSON).
            query("telephone", getModel().getTelephone()).
            get(Customer.class);
            if(customer != null && customer.getType() != null)
            {
                if(customer.getType() == 1)
                {
                    //如果已经激活,则向数据库查询
                    Customer c = WebClient.create("http://localhost:8010/crm/crm/CustomerService/findByTelephoneAndPassword").
                    type(MediaType.APPLICATION_JSON).
                    accept(MediaType.APPLICATION_JSON).
                    query("telephone", getModel().getTelephone()).
                    query("password", getModel().getPassword()).
                    get(Customer.class);
                    if(c != null)
                    {
                        HttpSession session = ServletActionContext.getRequest().getSession();
                        session.setAttribute("customer", c);
                        return SUCCESS;
                    }
                }
                else
                {
                    //如果未激活,返回unactived
                    //TODO:需要加入错误信息
                    return "unactived";
                }
            }
            System.out.println("找不到该用户");
            return ERROR;
        }
        System.out.println("获取手机号失败");
        return ERROR;
    }

}
  
