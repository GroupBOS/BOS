package com.robin.bos.web.action.take_delivery;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import com.robin.bos.domain.take_delivery.WayBill;
import com.robin.bos.service.take_delivery.WayBillService;
import com.robin.bos.web.action.BaseAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:WayBillAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午2:41:03 <br/>       
 */
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class WayBillAction extends BaseAction<WayBill> {

    private static final long serialVersionUID = -5854645927398981465L;
    
    
    @Autowired
    private WayBillService wayBillService;
    
    @Action("waybillAction_save")
    public String save() throws IOException
    {
        
        String msg = "0";
        try {
            int i = 1/0;
            wayBillService.save(getModel());
            msg = "1";
            
        } catch (Exception e) {
            e.printStackTrace();  
            msg = "0";
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter writer = response.getWriter();
        writer.write(msg);
        
        return NONE;
    }
    //使用属性驱动获得页面传过来的文件
    private File file;
    public void setFile(File file) {
     this.file = file;           
     
 }
    @Action(value = "waybillAction_importXLS",results={@Result(name="success",
            location="pages/take_delivery/waybill_import.html",type="redirect")})
    public String importXLS(){
       System.out.println("接收到啦请求");
       List<WayBill> list=new ArrayList<>();
       try {
           //获得POI对象
         XSSFWorkbook hssfWorkbook=new  XSSFWorkbook(new FileInputStream(file));
         //得到那张工作簿
         XSSFSheet sheet = hssfWorkbook.getSheetAt(0);
         for (Row row : sheet) {
             if (row.getRowNum()==0) {
                 continue;
             }
             String goodsType = row.getCell(1).getStringCellValue();
             String sendProNum = row.getCell(2).getStringCellValue();
             String sendName = row.getCell(3).getStringCellValue();
             System.out.println("名字:"+sendName);
             String sendMobile = row.getCell(4).getNumericCellValue()+"";
             String sendAddress = row.getCell(5).getStringCellValue();
             String recName = row.getCell(6).getStringCellValue();
             String recMobile = row.getCell(7).getNumericCellValue()+"";
             String recCompany = row.getCell(8).getStringCellValue();
             String recAddress = row.getCell(9).getStringCellValue();
             //封装数据
             WayBill wayBill=new WayBill();
             wayBill.setGoodsType(goodsType);
             wayBill.setSendProNum(sendProNum);
             wayBill.setSendName(sendName);
             wayBill.setSendMobile(sendMobile);
             wayBill.setSendAddress(sendAddress);
             wayBill.setRecName(recName);
             wayBill.setRecMobile(recMobile);
             wayBill.setRecCompany(recCompany);
             wayBill.setRecAddress(recAddress);
             list.add(wayBill);
         }
       
       wayBillService.saveWayBill(list);
       return SUCCESS;
       } catch (Exception e) {
           
       
         e.printStackTrace();  
         
     }               
        return NONE;
    }
    
    @Action(value = "waybillAction_pageQuery")
    public String pageQuery() throws IOException {

        Pageable pageable = new PageRequest(page - 1, rows);

        Page<WayBill> page = wayBillService.findAll(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas"});
        page2Json(page, jsonConfig);
      
        return NONE;
}

}
  
