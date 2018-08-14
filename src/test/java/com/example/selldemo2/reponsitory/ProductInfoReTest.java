package com.example.selldemo2.reponsitory;

import com.example.selldemo2.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoReTest {

    @Autowired
    private ProductInfoRe reponsitory;

    @Test
    public void test(){
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("123");
        productInfo.setProductName("皮蛋粥");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("非常棒的粥");
        productInfo.setProductIcon("http://baidu.com");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(1);

        ProductInfo info=reponsitory.save(productInfo);
        Assert.assertNotNull(info);
    }


    @Test
    public void findByProductStatus() throws Exception {
       List<ProductInfo> infos= reponsitory.findByProductStatus(0);
        Assert.assertNotEquals(0,infos.size());
    }

}