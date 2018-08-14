package com.example.selldemo2.service.impl;

import com.example.selldemo2.dataobject.OrderDetail;
import com.example.selldemo2.dto.Orderdto;
import com.example.selldemo2.enums.OrderStatusEnum;
import com.example.selldemo2.enums.PayStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceimplTest {
    @Autowired
    private OrderServiceimpl orderService;
    private final String BUYER_OPENID = "1101110";
    private final String ORDER_ID = "1234567";
    @Test
    public void create() throws Exception {
        Orderdto orderDTO = new Orderdto();
        orderDTO.setBuyerName("廖师兄");
        orderDTO.setBuyerAddress("幕课网");
        orderDTO.setBuyerPhone("123456789012");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("1234568");
        o1.setProductQuantity(1);
        OrderDetail o2 = new OrderDetail();
        o2.setProductId("123457");
        o2.setProductQuantity(2);
        orderDetailList.add(o1);
        orderDetailList.add(o2);
        orderDTO.setOrderDetailList(orderDetailList);
        Orderdto result = orderService.create(orderDTO);
        System.out.println(result);
    }

    @Test
    public void findOne() throws Exception {
      Orderdto orderdto=orderService.findOne(ORDER_ID);
        System.out.println(orderdto);
        Assert.assertEquals(ORDER_ID,orderdto.getOrderId());
    }

    @Test
    public void findList() throws Exception {
        PageRequest request=new PageRequest(0,2);
        Page<Orderdto> orderdtoPage=orderService.findList(BUYER_OPENID,request);
        Assert.assertNotEquals(0,orderdtoPage.getTotalElements());

    }

    @Test
    public void cancel() throws Exception {
        Orderdto orderdto=orderService.findOne(ORDER_ID);
        Orderdto result=orderService.cancel(orderdto);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() throws Exception {
        Orderdto orderdto=orderService.findOne(ORDER_ID);
        Orderdto result=orderService.finish(orderdto);
        Assert.assertEquals(OrderStatusEnum.FINISHEN.getCode(),result.getOrderStatus());

    }

    @Test
    public void paid() throws Exception {
        Orderdto orderdto=orderService.findOne(ORDER_ID);
        Orderdto result=orderService.paid(orderdto);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());

    }

    @Test
    public void findList1() throws Exception {
        PageRequest request=new PageRequest(0,2);
        Page<Orderdto> orderdtoPage=orderService.findList(request);
        Assert.assertNotEquals(0,orderdtoPage.getTotalElements());
    }

}