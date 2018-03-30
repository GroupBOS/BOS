package com.robin.bos.web.action.base;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
import org.springframework.ui.Model;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.robin.bos.domain.base.Area;
import com.robin.bos.service.base.AreaService;
import com.robin.bos.web.action.BaseAction;
import com.robin.utils.FileDownloadUtils;
import com.robin.utils.PinYin4jUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:AreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:15:28 <br/>       
 */
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class AreaAction extends BaseAction<Area>{
    private String q;
    public void setQ(String q) {
        this.q = q;
    }
    
    private File file;
    public void setFile(File file) {
        this.file = file;
    }
    
    private String filename;
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    @Autowired
    private AreaService areaService;
    
    
    @Action(value="areaAction_importXLS",results={@Result(name=SUCCESS,type="redirect",location="/pages/base/area.html")})
    public String importXLS()
    {
        if(file != null)
        {
            System.out.println(file.getName());
        }
        System.out.println("filename:"+filename);
        
        List<Area> list = new ArrayList<>();
        
        HSSFWorkbook workbook;
        try {
            workbook = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet hssfSheet = workbook.getSheetAt(0);
            for (Row row : hssfSheet) {
                if(row.getRowNum() == 0)
                {
                    continue;
                }
                
                String province = row.getCell(1).getStringCellValue();
                String city =     row.getCell(2).getStringCellValue();
                String district = row.getCell(3).getStringCellValue();
                String postcode = row.getCell(4).getStringCellValue();
                
                province = province.substring(0, province.length()-1);
                city = city.substring(0, city.length()-1);
                district = district.substring(0, district.length()-1);
                
                //获取城市编码
                String citycode = PinYin4jUtils.hanziToPinyin(city, "").toUpperCase();
                String[] headByString = PinYin4jUtils.getHeadByString(province + city + district, true);
                
                // 获取简码
                String shortcode = PinYin4jUtils.stringArrayToString(headByString);
                
                Area area = new Area();
                area.setProvince(province);
                area.setCity(city);
                area.setDistrict(district);
                area.setPostcode(postcode);
                area.setCitycode(citycode);
                area.setShortcode(shortcode);

                list.add(area);
            }
            
            areaService.save(list);
            
            workbook.close();
            
        } catch (Exception e) {
            e.printStackTrace();  
            return ERROR;
        }
        return SUCCESS;
    }
    
    @Action(value="areaAction_pageQuery",results={@Result(name=SUCCESS,type="redirect",location="/pages/base/area.html")})
    public String pageQuery() throws IOException
    {
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Area> page = areaService.findAll(pageable);
        
        // 灵活控制输出的内容
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas"});
        page2Json(page,jsonConfig);
        
        return NONE;
    }
    
    
    
    @Action(value="areaAction_findAll",results={@Result(name=SUCCESS,type="redirect",location="/pages/base/area.html")})
    public String findAll() throws IOException
    {
        Page<Area> page;
        List<Area> list;
        if(StringUtils.isNotEmpty(q))
        {
            System.out.println("q:"+q);
            list = areaService.findByQ(q);
        }
        else
        {
            page = areaService.findAll(null);
            list = page.getContent();
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas"});

        list2Json(list, jsonConfig);
        return NONE;
    }
    
    
    @Action(value="areaAction_exportExcel",results={
            @Result(name=SUCCESS,type="redirect",location="/pages/base/area.html")})
    public String exportExcel() throws IOException
    {
        List<Area> list = areaService.findAll();
        
        //1.在内存中创建excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        HSSFSheet sheet = workbook.createSheet();
        
        HSSFRow titleRow = sheet.createRow(0);
        
        titleRow.createCell(0).setCellValue("省");
        titleRow.createCell(1).setCellValue("市");
        titleRow.createCell(2).setCellValue("区");
        titleRow.createCell(3).setCellValue("邮编");
        titleRow.createCell(4).setCellValue("简码");
        titleRow.createCell(5).setCellValue("城市编码");
        
        for(Area area:list)
        {
            //获取最后一行行号
            int lastRowNum = sheet.getLastRowNum();
            HSSFRow dataRow = sheet.createRow(lastRowNum + 1);
            
            dataRow.createCell(0).setCellValue(area.getProvince());
            dataRow.createCell(1).setCellValue(area.getCity());
            dataRow.createCell(2).setCellValue(area.getDistrict());
            dataRow.createCell(3).setCellValue(area.getPostcode());
            dataRow.createCell(4).setCellValue(area.getShortcode());
            dataRow.createCell(5).setCellValue(area.getCitycode());
        }
        
        String filename = "区域数据统计.xls";
        
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
  
