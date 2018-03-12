package com.robin.bos.dao.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.robin.bos.dao.StandardRepository;
import com.robin.bos.domain.base.Standard;

/**  
 * ClassName:StandardRepositoryTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午9:31:25 <br/>       
 */



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {

    @Autowired
    public StandardRepository standardRepository;
    
    @Test
    public void test01()
    {
        Standard standard = new Standard();
        standard.setName("zs");
        standard.setMaxLength(100);
        standardRepository.save(standard);
    }
    
    @Test
    public void test02()
    {
       /* List<Standard> findByName(String name);
        
        @Query("from Standard where name=?")
        List<Standard> test_findByName(String name);*/
        
        List<Standard> test_findByName = standardRepository.test_findByName("zs");
        for (Standard standard : test_findByName) {
            System.out.println(standard);
        }
    }
    
    
}
  
