package com.example.selldemo2.reponsitory;

import com.example.selldemo2.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterReTest {
    @Autowired
    private OrderMasterRe repository;

    private final String OPENID="11021";
    @Test
    public void savetest(){
        for(int i=9;i<20;i++){
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId("13503461"+i);
        orderMaster.setBuyerName("王者"+i+"号");
        orderMaster.setBuyerPhone("13672896"+i);
        orderMaster.setBuyerAddress("海淀区"+i);
        orderMaster.setBuyerOpenid(OPENID+i);
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        OrderMaster result=repository.save(orderMaster);
        Assert.assertNotNull(result);
        }
    }
    @Test
    public void findByBuyerOpenid() throws Exception {
        PageRequest request=new PageRequest(0,1);
        Page<OrderMaster> result=repository.findByBuyerOpenid(OPENID,request);
        System.out.println(result.getTotalElements());
    }

}