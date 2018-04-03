package com.robin.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.robin.bos.dao.base.AreaRepository;
import com.robin.bos.dao.base.FixedAreaRepository;
import com.robin.bos.dao.base.SubAreaRepository;

import com.robin.bos.dao.take_delivery.OrderRepository;
import com.robin.bos.dao.take_delivery.WorkBillRepository;
import com.robin.bos.domain.base.Area;
import com.robin.bos.domain.base.Courier;
import com.robin.bos.domain.base.FixedArea;
import com.robin.bos.domain.base.SubArea;
import com.robin.bos.domain.take_delivery.Order;
import com.robin.bos.domain.take_delivery.WorkBill;
import com.robin.bos.service.take_delivery.OrderService;

/**  
 * ClassName:OrderServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午4:43:40 <br/>       
 */

@Component
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private AreaRepository areaRepository;
    
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    
    @Autowired
    private SubAreaRepository subAreaRepository;
    
    @Autowired
    private WorkBillRepository workBillRepository;
    
    @Override
    public void saveOrder(Order order) {
        
        order.setOrderNum(UUID.randomUUID().toString().replaceAll("-", ""));
        order.setOrderTime(new Date());
        
        Area recArea = order.getRecArea();
        Area sendArea = order.getSendArea();
        if(recArea != null)
        {
            String province = recArea.getProvince();
            String city = recArea.getCity();
            String district = recArea.getDistrict();
            recArea = areaRepository.findByProvinceAndCityAndDistrict(province, city, district);
            order.setRecArea(recArea);
        }
        
        if(sendArea != null)
        {
            String province = sendArea.getProvince();
            String city = sendArea.getCity();
            String district = sendArea.getDistrict();
            sendArea = areaRepository.findByProvinceAndCityAndDistrict(province, city, district);
            order.setSendArea(sendArea);
        }
        orderRepository.save(order);
        
        //分单逻辑放到BOS后台里面做
        //1.尝试根据Customer表的address去找出fixedAreaId
        String fixedAreaId = WebClient.create("http://localhost:8180/crm/crm/CustomerService/findFixedAreaIdByAddress").
        type(MediaType.APPLICATION_JSON).
        accept(MediaType.APPLICATION_JSON).
        query("address", order.getSendAddress()).
        get(String.class);
            //1.1匹配成功
        if(StringUtils.isNotEmpty(fixedAreaId))
        {
            //找出FixedArea对象,并存进order里面
            FixedArea fixedArea = fixedAreaRepository.findById(Long.parseLong(fixedAreaId));
            if(fixedArea != null)
            {
                Set<Courier> couriers = fixedArea.getCouriers();
                Iterator<Courier> iterator = couriers.iterator();
                if(iterator.hasNext())
                {
                    Courier courier = iterator.next();
                    order.setCourier(courier);
                    order.setOrderType("自动分单");
                    order.setStatus("待取件");

                    WorkBill workBill = new WorkBill();
                    workBill.setType("新");
                    workBill.setPickstate("待取件");
                    workBill.setBuildtime(new Date());
                    workBill.setRemark(order.getRemark());
                    workBill.setCourier(courier);
                    workBill.setOrder(order);

                    workBillRepository.save(workBill);
                    return;
                }
            }
        }
        else {
        //2.尝试将sendAddress,匹配subArea的keyword,去确定subArea,然后再找到fixedArea    
            String keyWord = order.getSendAddress();
            SubArea subArea = subAreaRepository.findByKeyWords(keyWord);
            if(subArea != null)
            {
                FixedArea fixedArea = subArea.getFixedArea();
                if(fixedArea != null)
                {
                    Set<Courier> couriers = fixedArea.getCouriers();
                    Iterator<Courier> iterator = couriers.iterator();
                    if(iterator.hasNext())
                    {
                        Courier courier = iterator.next();
                        order.setCourier(courier);
                        order.setOrderType("自动分单");
                        order.setStatus("待取件");
                        
                        WorkBill workBill = new WorkBill();
                        workBill.setType("新");
                        workBill.setPickstate("待取件");
                        workBill.setBuildtime(new Date());
                        workBill.setRemark(order.getRemark());
                        workBill.setCourier(courier);
                        workBill.setOrder(order);

                        workBillRepository.save(workBill);
                        return;
                    }
                }
            }
        }

        //3.如果前两种都不行,只能进行人工分单
        order.setOrderType("手动分单");
        return;
    }

}
  
