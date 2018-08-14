package com.example.selldemo2.service.impl;

import com.example.selldemo2.dataobject.ProductInfo;
import com.example.selldemo2.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceimplTest {
   @Autowired
   private ProductServiceimpl serviceimpl;


    @Test
        public void findOne() throws Exception {
        ProductInfo productInfo=serviceimpl.findOne("123");
        Assert.assertEquals("123",productInfo.getProductId());
    }

    @Test
    public void findUpAll() throws Exception {
       List<ProductInfo> list= serviceimpl.findUpAll();
       Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findAll() throws Exception {
        PageRequest request=new PageRequest(0,2);
        Page<ProductInfo> productInfoPage=serviceimpl.findAll(request);
        System.out.println(productInfoPage.getTotalElements());
    }

    @Test
    public void save() throws Exception {
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("皮皮虾");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(200);
        productInfo.setProductDescription("很好吃的虾");
        productInfo.setProductIcon("http://baidu.com");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);
        ProductInfo result=serviceimpl.save(productInfo);
        Assert.assertNotNull(result);
    }

}