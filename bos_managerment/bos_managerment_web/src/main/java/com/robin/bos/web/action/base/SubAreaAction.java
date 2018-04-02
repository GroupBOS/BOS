package com.robin.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

import com.robin.bos.domain.base.SubArea;
import com.robin.bos.service.base.SubAreaService;
import com.robin.bos.web.action.BaseAction;
import com.robin.utils.FileDownloadUtils;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**  
 * ClassName:SubAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午4:28:00 <br/>       
 */
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class SubAreaAction extends BaseAction<SubArea> {
    
    
    @Autowired
    SubAreaService subAreaService;
    
    @Action(value="subareaAction_save",results={@Result(name=SUCCESS,type="redirect",location="/pages/base/sub_area.html"),
                                                @Result(name=ERROR,type="redirect",location="/pages/base/sub_area.html")})
    public String save()
    {
       SubArea subArea = subAreaService.save(getModel());
       if(subArea != null)
       {
           return SUCCESS;
       }
       return ERROR;
    }
    
    @Action(value="subareaAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page-1, rows);

        Page<SubArea> page = subAreaService.findAll(pageable);

        JsonConfig jsonConfig = new JsonConfig();
        //jsonConfig.setExcludes(new String[]{"fixedArea","area"});
        jsonConfig.setExcludes(new String[]{"subareas","fixedArea"});

        page2Json(page, jsonConfig);

        return NONE;
    }
    @Action("SubareaAction_findSubByfixed")
    public String findSubByfixed(){
        List<SubArea>list =subAreaService.findSubByfixed(getModel().getId());
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"subareas","fixedArea"});
        String string = JSONArray.fromObject(list,config).toString();
        ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
        try {
            ServletActionContext.getResponse().getWriter().write(string);
        } catch (IOException e) {
              
            
            e.printStackTrace();  
            
        }
        return null;
    }
    
    
    
    @Action(value="SubareaAction_exportExcel",results={
            @Result(name=SUCCESS,type="redirect",location="/pages/base/sub_area.html")})
    public String exportExcel() throws IOException{
    	Page<SubArea> page = subAreaService.findAll(null);

    	List<SubArea> list = page.getContent();

    	HSSFWorkbook workbook = new HSSFWorkbook();

    	HSSFSheet sheet = workbook.createSheet();
    	
    	HSSFRow titleRow = sheet.createRow(0);
    	titleRow.createCell(0).setCellValue("分拣编号");
    	titleRow.createCell(1).setCellValue("省");
    	titleRow.createCell(2).setCellValue("市");
    	titleRow.createCell(3).setCellValue("区");
    	titleRow.createCell(4).setCellValue("关键字");
    	titleRow.createCell(5).setCellValue("起始号");
    	titleRow.createCell(6).setCellValue("终止号");
    	titleRow.createCell(7).setCellValue("单双号");
    	titleRow.createCell(8).setCellValue("辅助关键字");
    	
    	for(int i=0;i<list.size();i++)
    	{
    		HSSFRow dataRow = sheet.createRow(i+1);
    		dataRow.createCell(0).setCellValue(list.get(i).getId());
    		dataRow.createCell(1).setCellValue(list.get(i).getArea().getProvince());
    		dataRow.createCell(2).setCellValue(list.get(i).getArea().getCity());
    		dataRow.createCell(3).setCellValue(list.get(i).getArea().getDistrict());
    		dataRow.createCell(4).setCellValue(list.get(i).getKeyWords());
    		dataRow.createCell(5).setCellValue(list.get(i).getStartNum());
    		dataRow.createCell(6).setCellValue(list.get(i).getEndNum());
    		dataRow.createCell(7).setCellValue(list.get(i).getSingle());
    		dataRow.createCell(8).setCellValue(list.get(i).getAssistKeyWords());
    	}
    	
    	 String filename = "分区数据统计.xls";
         
         //文件下载功能,设置两头一流
         //一流:输出流
         ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();
         
         String mimeType = ServletActionContext.getServletContext().getMimeType(filename);

         String userAgent = ServletActionContext.getRequest().getHeader("User-Agent");

         filename = FileDownloadUtils.encodeDownloadFilename(filename, userAgent);
         
         HttpServletResponse response = ServletActionContext.getResponse();
         //两头:
         //Content-Disposition: attachment; filename= 'filename'
         //setContentType: mimeType
         response.setContentType(mimeType);
         response.setHeader("Content-Disposition",
                 "attachment; filename=" + filename);

         workbook.write(outputStream);
         workbook.close();
    	
    	
        return NONE;
    }
}
  
