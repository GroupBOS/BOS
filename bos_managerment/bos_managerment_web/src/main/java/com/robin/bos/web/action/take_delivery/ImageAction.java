package com.robin.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;

/**  
 * ClassName:imageAction <br/>  
 * Function:  <br/>  
 * Date:     2018年4月1日 下午9:51:57 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class ImageAction extends ActionSupport{
    
    private File imgFile;
    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }
    private String imgFileFileName;
    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }
    @Action("imageAction_upload")
    public String upload() throws IOException{
        Map<String, Object> map = new HashMap<>();
        try {
            String realDirPath = "upload";
            String realPath = ServletActionContext.getServletContext().getRealPath(realDirPath);
            //获取文件后缀名
            String suffix = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
            
            //随机生成文件名
            String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase()+suffix;
            
            //保存文件
            FileUtils.copyFile(imgFile, new File(realPath+"/"+fileName));
            
            //本项目路径
            String contextPath = ServletActionContext.getServletContext().getContextPath();
            
            //返回的路径
            String relativePath = contextPath+"/"+realDirPath+"/"+fileName;
            
            map.put("error", 0);
            map.put("url", relativePath);
            
        } catch (Exception e) {
            e.printStackTrace();  
            map.put("error", 1);
            map.put("message", "上传文件失败");
        }
        
        String json = JSONObject.fromObject(map).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        
        return NONE;
    }
    
    @Action("imageAction_manager")
    public String manager() throws IOException{
     // 图片扩展名
        String[] fileTypes = new String[] {"gif", "jpg", "jpeg", "png", "bmp"};
        String realDirPath = "upload";
        String realPath = ServletActionContext.getServletContext().getRealPath(realDirPath);
        File currentPathFile = new File(realPath);
        
     // 遍历目录获取文件信息
        List<Hashtable> fileList = new ArrayList<Hashtable>();
        if (currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                Hashtable<String, Object> hash =
                        new Hashtable<String, Object>();
                String fileName = file.getName();
                if (file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if (file.isFile()) {
                    String fileExt =
                            fileName.substring(fileName.lastIndexOf(".") + 1)
                                    .toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo",
                            Arrays.<String>asList(fileTypes).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(file.lastModified()));
                fileList.add(hash);
            }
        }
        JSONObject result = new JSONObject();
        //获取本项目路径
        String contextPath = ServletActionContext.getServletContext().getContextPath();
        result.put("current_url", contextPath+"/upload/");
        result.put("file_list", fileList);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(result.toString());
        
        return NONE;
    }
    
}
  
