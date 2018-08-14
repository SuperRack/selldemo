package com.example.selldemo2.controller;

import com.example.selldemo2.SellException.SellException;
import com.example.selldemo2.dto.Orderdto;
import com.example.selldemo2.enums.ResultEnum;
import com.example.selldemo2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/create")
    public void create(@RequestParam("orderId")String orderId,
                       @RequestParam("returnUrl")String returnUrl){
        //查询订单
        Orderdto orderdto=orderService.findOne(orderId);
        if(orderdto==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //发起支付

    }

}
