package com.example.selldemo2.service.impl;

import com.example.selldemo2.dto.Orderdto;
import com.example.selldemo2.service.OrderService;
import com.example.selldemo2.service.PayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class PayServiceimplTest {
    @Autowired
    private PayService payService;
    @Autowired
    private OrderService orderService;

    @Test
    public void create() throws Exception {
        Orderdto orderdto=orderService.findOne("1234567");
        payService.create(orderdto);

    }

}