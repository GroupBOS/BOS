package com.robin.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
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

import com.robin.bos.domain.take_delivery.Promotion;
import com.robin.bos.service.take_delivery.PromotionService;
import com.robin.bos.web.action.BaseAction;

/**  
 * ClassName:PromotionAction <br/>  
 * Function:  <br/>  
 * Date:     2018年4月1日 下午10:12:56 <br/>       
 */
@Namespace("/") // 等价于struts.xml文件中package节点namespace属性
@ParentPackage("struts-default") // 等价于struts.xml文件中package节点extends属性
@Controller // spring 的注解,控制层代码
@Scope("prototype") // spring 的注解,多例
public class PromotionAction extends BaseAction<Promotion>{
 // 使用属性驱动获取封面图片和图片的文件名
    private File titleImgFile;
    private String titleImgFileFileName;

    public void setTitleImgFile(File titleImgFile) {
        this.titleImgFile = titleImgFile;
    }

    public void setTitleImgFileFileName(String titleImgFileFileName) {
        this.titleImgFileFileName = titleImgFileFileName;
    }

    @Autowired
    private PromotionService promotionService;

    @Action(value = "promotionAction_save",
            results = {@Result(name = "success",
                    location = "/pages/take_delivery/promotion.html",
                    type = "redirect")})
    public String save() {

        Promotion promotion = getModel();
        // 保存封面图片
        try {
            // 指定保存图片的文件夹
            String dirPath = "/upload";
            // D:aa/upload/a.jpg
            // 获取保存图片的文件夹的绝对磁盘路径
            ServletContext servletContext =
                    ServletActionContext.getServletContext();
            String dirRealPath = servletContext.getRealPath(dirPath);

            // 获取文件名的后缀
            // a.jpg =>不加1 .jpg ,加1 jpg
            String suffix = titleImgFileFileName
                    .substring(titleImgFileFileName.lastIndexOf("."));
            // 使用UUId生成文件名
            String fileName =
                    UUID.randomUUID().toString().replaceAll("-", "") + suffix;
            File destFile = new File(dirRealPath + "/" + fileName);

            // 保存文件
            FileUtils.copyFile(titleImgFile, destFile);
            // http://localhost:8080/bos_management_web/upload/a.jpg
            promotion.setTitleImg("/upload/" + fileName);
        } catch (IOException e) {

            e.printStackTrace();
            promotion.setTitleImg(null);

        }

        promotion.setStatus("1");

        promotionService.save(promotion);
        return SUCCESS;
    }
    
    @Action(value = "promotionAction_pageQuery")
    public String pageQuery() throws IOException {

        // EasyUI的页码是从1开始的
        // SPringDataJPA的页码是从0开始的
        // 所以要-1

        Pageable pageable = new PageRequest(page - 1, rows);

        Page<Promotion> page = promotionService.findAll(pageable);

        page2Json(page, null);
        return NONE;
    }
    
}
