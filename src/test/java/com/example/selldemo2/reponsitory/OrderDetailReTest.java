package com.example.selldemo2.reponsitory;

import com.example.selldemo2.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailReTest {

    @Autowired
    private OrderDetailRe repository;
    @Test
    public void saveTest() {
        for(int i=2;i<10;i++) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setDetailId("321" + i);
            orderDetail.setOrderId("13503461"+i);
            orderDetail.setProductIcon("http://p14.qhimg.com/t0120e0e0c91bdff49a.jpg");
            orderDetail.setProductId("11111112");
            orderDetail.setProductName("皮皮虾");
            orderDetail.setProductPrice(new BigDecimal(2.2));
            orderDetail.setProductQuantity(2);

            OrderDetail result = repository.save(orderDetail);
        }
    }



    @Test
    public void findByOrderId() throws Exception {
    }

}