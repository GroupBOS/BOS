package com.robin.bos.fore.web.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.robin.bos.domain.take_delivery.PageBean;
import com.robin.bos.domain.take_delivery.Promotion;

import net.sf.json.JSONObject;

/**  
 * ClassName:PromotionAction <br/>  
 * Function:  <br/>  
 * Date:     2018年4月1日 下午10:51:46 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class PromotionAction extends ActionSupport {

    private int pageIndex;// 当前页码
    private int pageSize;// 每一页显示多少条数据

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Action(value = "promotionAction_pageQuery")
    public String pageQuery() throws IOException {

        PageBean<Promotion> pageBean = WebClient.create(
                "http://localhost:8080/bos_managerment_web/webService/promotionService/findAll4Fore")
                .type(MediaType.APPLICATION_JSON)//
                .accept(MediaType.APPLICATION_JSON)//
                .query("pageIndex", pageIndex)//
                .query("pageSize", pageSize).get(PageBean.class);

        String json = JSONObject.fromObject(pageBean).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);

        return NONE;
    }
}
  
